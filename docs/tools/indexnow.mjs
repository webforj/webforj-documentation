// Tell Bing (and the other IndexNow engines) that pages changed, so they
// recrawl within hours instead of weeks. Free, no account needed.
//
//   node tools/indexnow.mjs https://docs.webforj.com/blog/some-post [more URLs...]
//   node tools/indexnow.mjs --sitemap        (every URL in /sitemap.xml)
//
// Run it after the deploy has finished, so the engines fetch the new version.
// The key below must match the file static/<key>.txt that is served at the site root.
const KEY = '1eef28b35afc8d12e6d2acc2ed22e94e';
const HOST = 'docs.webforj.com';

async function sitemapUrls() {
  const xml = await (await fetch(`https://${HOST}/sitemap.xml`)).text();
  return [...xml.matchAll(/<loc>([^<]+)<\/loc>/g)].map((m) => m[1]);
}

const args = process.argv.slice(2);
const urls = args.includes('--sitemap') ? await sitemapUrls() : args;
if (!urls.length) {
  console.error('Pass page URLs, or --sitemap');
  process.exit(1);
}

// IndexNow takes up to 10,000 URLs per request
for (let i = 0; i < urls.length; i += 10000) {
  const batch = urls.slice(i, i + 10000);
  const res = await fetch('https://api.indexnow.org/indexnow', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json; charset=utf-8' },
    body: JSON.stringify({ host: HOST, key: KEY, keyLocation: `https://${HOST}/${KEY}.txt`, urlList: batch }),
  });
  console.log(`IndexNow: ${batch.length} URLs -> HTTP ${res.status}`);
}
