// Tell Bing and the other IndexNow engines (and through Bing: DuckDuckGo,
// Yahoo, ChatGPT search) that pages changed, so they recrawl them within
// hours instead of weeks. Free, no account needed. Google does not use it.
//
// Runs automatically at the end of every deploy (Build and Deploy WAR):
//   node tools/indexnow.mjs --after-deploy
//     Reads the freshly built sitemaps in build/, keeps the pages whose
//     lastmod (last git commit of the file) is within the last 2 days, waits
//     until the live site serves the new build, then submits them.
//
// Manual use:
//   node tools/indexnow.mjs https://docs.webforj.com/blog/some-post [more...]
//   node tools/indexnow.mjs --sitemap      (every URL in the live /sitemap.xml)
//
// The key must match static/<KEY>.txt, served at the site root. Keep that file.
import { readFileSync, readdirSync, statSync, existsSync } from 'node:fs';
import { join } from 'node:path';

const KEY = '1eef28b35afc8d12e6d2acc2ed22e94e';
const HOST = 'docs.webforj.com';
const RECENT_DAYS = 2;

const locs = (xml) => [...xml.matchAll(/<url><loc>([^<]+)<\/loc>(?:<lastmod>([^<]+)<\/lastmod>)?/g)]
  .map((m) => ({ url: m[1], lastmod: m[2] }));

function builtSitemaps(dir) {
  const out = [];
  for (const name of readdirSync(dir)) {
    const p = join(dir, name);
    if (name === 'sitemap.xml') out.push(p);
    else if (statSync(p).isDirectory() && /^[a-z]{2}$/.test(name) && existsSync(join(p, 'sitemap.xml'))) {
      out.push(join(p, 'sitemap.xml'));
    }
  }
  return out;
}

async function waitForLiveBuild(localSitemap) {
  // The WAR is picked up by the server some minutes after the upload. Wait
  // until the live sitemap matches the one we just built (up to 30 minutes).
  for (let i = 0; i < 60; i++) {
    try {
      const live = await (await fetch(`https://${HOST}/sitemap.xml`, { cache: 'no-store' })).text();
      if (live.trim() === localSitemap.trim()) return true;
    } catch (e) {
      // network hiccup, try again
    }
    await new Promise((r) => setTimeout(r, 30000));
  }
  return false;
}

async function submit(urls) {
  for (let i = 0; i < urls.length; i += 10000) {
    const batch = urls.slice(i, i + 10000);
    const res = await fetch('https://api.indexnow.org/indexnow', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json; charset=utf-8' },
      body: JSON.stringify({ host: HOST, key: KEY, keyLocation: `https://${HOST}/${KEY}.txt`, urlList: batch }),
    });
    console.log(`IndexNow: ${batch.length} URLs -> HTTP ${res.status}`);
  }
}

const args = process.argv.slice(2);

if (args.includes('--after-deploy')) {
  const files = builtSitemaps('build');
  const cutoff = new Date(Date.now() - RECENT_DAYS * 86400000).toISOString().slice(0, 10);
  const urls = [...new Set(files.flatMap((f) => locs(readFileSync(f, 'utf8'))
    .filter((e) => e.lastmod && e.lastmod >= cutoff)
    .map((e) => e.url)))];
  console.log(`IndexNow: ${urls.length} pages changed since ${cutoff}`);
  if (!urls.length) process.exit(0);
  const live = await waitForLiveBuild(readFileSync('build/sitemap.xml', 'utf8'));
  if (!live) console.log('IndexNow: live site still on the old build after 30 minutes, submitting anyway');
  await submit(urls);
} else {
  const urls = args.includes('--sitemap')
    ? locs(await (await fetch(`https://${HOST}/sitemap.xml`)).text()).map((e) => e.url)
    : args;
  if (!urls.length) {
    console.error('Pass page URLs, --sitemap, or --after-deploy');
    process.exit(1);
  }
  await submit(urls);
}
