// Tell Bing and the other IndexNow engines (and through Bing: DuckDuckGo,
// Yahoo, ChatGPT search) that pages changed, so they recrawl them within
// hours instead of weeks. Free, no account needed. Google does not use it.
//
// Runs automatically in its own workflow (.github/workflows/indexnow.yml)
// after each successful deploy, so the deploy itself never waits for it:
//   node tools/indexnow.mjs --recent
//     Waits until the server serves the new build (the live sitemap changes,
//     up to 20 minutes), then submits the pages whose lastmod (last git
//     commit of the file) changed since the previous run, in all languages.
//     Each changed page is sent once, in small groups with a pause between
//     them, because Bing asks for a steady stream instead of large batches.
//     The previous run's lastmod values are read from and written to the
//     file given with --state. Without that file, the pages changed in the
//     last 2 days are sent. Add --dry-run to print the count without sending.
//
// Manual use:
//   node tools/indexnow.mjs https://docs.webforj.com/blog/some-post [more...]
//   node tools/indexnow.mjs --sitemap      (every URL in the live /sitemap.xml)
//
// The key must match static/<KEY>.txt, served at the site root. Keep that file.

import { existsSync, mkdirSync, readFileSync, writeFileSync } from 'node:fs';
import { dirname } from 'node:path';

const KEY = '1eef28b35afc8d12e6d2acc2ed22e94e';
const HOST = 'docs.webforj.com';
const RECENT_DAYS = 2;
const GROUP_SIZE = 50;
const GROUP_PAUSE_MS = 15000;

const locs = (xml) => [...xml.matchAll(/<url><loc>([^<]+)<\/loc>(?:<lastmod>([^<]+)<\/lastmod>)?/g)]
  .map((m) => ({ url: m[1], lastmod: m[2] }));

const LOCALES = ['', 'de/', 'es/', 'fr/', 'nl/', 'fi/', 'zh/'];
const fetchText = async (url) => (await fetch(url, { cache: 'no-store' })).text();

async function waitForNewBuild() {
  // The WAR is picked up by the server some minutes after the deploy job ends.
  // Wait until the live sitemap changes, or 20 minutes, whichever comes first.
  const before = await fetchText(`https://${HOST}/sitemap.xml`).catch(() => '');
  for (let i = 0; i < 40; i++) {
    await new Promise((r) => setTimeout(r, 30000));
    const now = await fetchText(`https://${HOST}/sitemap.xml`).catch(() => before);
    if (now !== before) return;
  }
}

async function submit(urls) {
  for (let i = 0; i < urls.length; i += GROUP_SIZE) {
    if (i > 0) await new Promise((r) => setTimeout(r, GROUP_PAUSE_MS));
    const batch = urls.slice(i, i + GROUP_SIZE);
    const res = await fetch('https://api.indexnow.org/indexnow', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json; charset=utf-8' },
      body: JSON.stringify({ host: HOST, key: KEY, keyLocation: `https://${HOST}/${KEY}.txt`, urlList: batch }),
    });
    console.log(`IndexNow: ${batch.length} URLs -> HTTP ${res.status}`);
  }
}

const args = process.argv.slice(2);

if (args.includes('--recent')) {
  const dryRun = args.includes('--dry-run');
  const stateFile = args.includes('--state') ? args[args.indexOf('--state') + 1] : null;
  const sent = stateFile && existsSync(stateFile) ? JSON.parse(readFileSync(stateFile, 'utf8')) : null;
  if (!dryRun) await waitForNewBuild();
  const cutoff = new Date(Date.now() - RECENT_DAYS * 86400000).toISOString().slice(0, 10);
  const lists = await Promise.all(LOCALES.map((l) => fetchText(`https://${HOST}/${l}sitemap.xml`).catch(() => '')));
  const live = Object.fromEntries(lists.flatMap((xml) => locs(xml))
    .filter((e) => e.lastmod)
    .map((e) => [e.url, e.lastmod]));
  const urls = Object.keys(live).filter((url) => (sent ? sent[url] !== live[url] : live[url] >= cutoff));
  console.log(`IndexNow: ${urls.length} pages changed since ${sent ? 'the previous run' : cutoff}`);
  if (dryRun) process.exit(0);
  if (urls.length) await submit(urls);
  if (stateFile && Object.keys(live).length) {
    mkdirSync(dirname(stateFile), { recursive: true });
    writeFileSync(stateFile, JSON.stringify({ ...sent, ...live }));
  }
} else {
  const urls = args.includes('--sitemap')
    ? locs(await (await fetch(`https://${HOST}/sitemap.xml`)).text()).map((e) => e.url)
    : args;
  if (!urls.length) {
    console.error('Pass page URLs, --sitemap, or --recent');
    process.exit(1);
  }
  await submit(urls);
}
