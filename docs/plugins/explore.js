// "Explore" on docs pages: the webforJ YouTube channel's videos and the blog
// posts related to each docs page, gathered when the site builds and handed
// to every page as global data (read by src/components/explore/ExploreCard.js).
//
// The videos come from YouTube's public feeds: no API key and no quota. Each
// channel has one feed for its shorts (playlist UUSH...) and one for its
// regular videos (UULF...). The feeds carry the latest 15 of each. When
// YouTube can't be reached the build carries on without videos.

const { relatedCover } = require('../config/blog-related-posts');

const CHANNEL_ID = 'UCiDL28nsXEodC2WJ9IYRTmQ'; // youtube.com/@webforJ
// related posts kept per docs page, shown in the "Keep learning" carousel at
// the end of the page
const MAX_RELATED = 8;
const FEED_TIMEOUT_MS = 8000;

const FEEDS = [
  { kind: 'short', playlist: `UUSH${CHANNEL_ID.slice(2)}` },
  { kind: 'video', playlist: `UULF${CHANNEL_ID.slice(2)}` },
];

function decode(text) {
  return text
    .replace(/&amp;/g, '&')
    .replace(/&lt;/g, '<')
    .replace(/&gt;/g, '>')
    .replace(/&quot;/g, '"')
    .replace(/&#39;/g, "'");
}

function parseFeed(xml, kind) {
  return [...xml.matchAll(/<entry>([\s\S]*?)<\/entry>/g)]
    .map(([, entry]) => {
      const id = entry.match(/<yt:videoId>([^<]+)<\/yt:videoId>/)?.[1];
      const title = entry.match(/<title>([^<]*)<\/title>/)?.[1];
      const published = entry.match(/<published>([^<]+)<\/published>/)?.[1];
      if (!id || !title) {
        return null;
      }
      return {
        kind,
        id,
        title: decode(title),
        published,
        url: kind === 'short' ? `https://www.youtube.com/shorts/${id}` : `https://www.youtube.com/watch?v=${id}`,
        // shorts have a portrait thumbnail (4:5); the standard one is 4:3,
        // with the vertical video letterboxed inside it
        thumbnail:
          kind === 'short'
            ? `https://i.ytimg.com/vi/${id}/oar2.jpg`
            : `https://i.ytimg.com/vi/${id}/hqdefault.jpg`,
        fallback: `https://i.ytimg.com/vi/${id}/hqdefault.jpg`,
      };
    })
    .filter(Boolean);
}

async function fetchFeed({ kind, playlist }) {
  const controller = new AbortController();
  const timer = setTimeout(() => controller.abort(), FEED_TIMEOUT_MS);
  try {
    const response = await fetch(`https://www.youtube.com/feeds/videos.xml?playlist_id=${playlist}`, {
      signal: controller.signal,
    });
    if (!response.ok) {
      throw new Error(`HTTP ${response.status}`);
    }
    return parseFeed(await response.text(), kind);
  } finally {
    clearTimeout(timer);
  }
}

module.exports = function explorePlugin() {
  return {
    name: 'webforj-explore',

    async loadContent() {
      const videos = [];
      for (const feed of FEEDS) {
        try {
          videos.push(...(await fetchFeed(feed)));
        } catch (error) {
          console.warn(`[webforj-explore] YouTube ${feed.kind} feed unavailable: ${error.message}`);
        }
      }
      return { videos };
    },

    async allContentLoaded({ allContent, actions }) {
      const own = allContent['webforj-explore']?.default ?? { videos: [] };
      const blog = allContent['docusaurus-plugin-content-blog']?.default;
      const listed = (blog?.blogPosts ?? [])
        .filter((post) => !post.metadata.unlisted && !post.metadata.frontMatter?.draft)
        .sort((a, b) => b.metadata.date.getTime() - a.metadata.date.getTime());

      // Which posts to suggest on which docs page. Nearly every post links the
      // docs pages it is about, so a post that links a page is related to it
      // by construction. For each docs page a post scores:
      //   links to this page        focus x early
      //   links to its section      half of that (sibling pages count)
      // where focus = 1 / docs pages the post links (a focused post wins) and
      // early = 1.5 when the first link sits in the first 30% of the post.
      // The total is multiplied by 1 + 3 x the page's topic words found in the
      // post's title and tags. Best three per page, ties to the newer post.
      // A page that no post links only gets section posts about its topic,
      // and nothing at all when there are none: never an off-topic post.
      const IGNORED = new Set(['doc', 'docs', 'overview', 'introduction', 'the', 'and', 'with', 'for',
        'webforj', 'java', 'component', 'getting', 'started']);
      const words = (text) => new Set(text.toLowerCase().split(/[^a-z0-9]+/)
        .map((w) => w.replace(/s$/, '')).filter((w) => w.length > 2 && !IGNORED.has(w)));
      const sectionOf = (page) => page.split('/').slice(0, -1).join('/');
      const bump = (map, key, index, value) => {
        if (!map.has(key)) map.set(key, new Map());
        map.get(key).set(index, (map.get(key).get(index) ?? 0) + value);
      };
      const direct = new Map();
      const section = new Map();
      const topicWords = listed.map((post) =>
        words(`${post.metadata.title} ${post.metadata.tags.map((tag) => tag.label).join(' ')}`));
      listed.forEach((post, index) => {
        const content = post.content ?? '';
        const firstAt = new Map();
        for (const match of content.matchAll(/\]\((\/docs\/[^)\s#?]+)/g)) {
          const page = match[1].replace(/\/$/, '');
          if (!firstAt.has(page)) firstAt.set(page, match.index / Math.max(content.length, 1));
        }
        const focus = 1 / Math.max(firstAt.size, 1);
        for (const [page, at] of firstAt) {
          const value = focus * (at < 0.3 ? 1.5 : 1);
          bump(direct, page, index, value);
          bump(section, sectionOf(page), index, value);
        }
      });
      // every docs page of the site, as /docs/... whatever the language
      const docsVersions = allContent['docusaurus-plugin-content-docs']?.default?.loadedVersions ?? [];
      const pages = new Set(direct.keys());
      for (const version of docsVersions) {
        for (const doc of version.docs ?? []) {
          const at = doc.permalink.indexOf('/docs/');
          if (at >= 0) pages.add(doc.permalink.slice(at).replace(/\/$/, ''));
        }
      }
      const ranked = {};
      for (const page of pages) {
        const total = new Map();
        for (const [index, value] of direct.get(page) ?? []) total.set(index, (total.get(index) ?? 0) + value);
        for (const [index, value] of section.get(sectionOf(page)) ?? []) {
          total.set(index, (total.get(index) ?? 0) + value / 2);
        }
        if (!total.size) continue;
        const pageWords = words(page);
        const linked = direct.has(page);
        const list = [...total]
          .map(([index, value]) => {
            const topic = [...pageWords].filter((w) => topicWords[index].has(w)).length;
            return [index, value * (1 + 3 * topic), topic];
          })
          // a page no post links yet only borrows section posts about its topic
          .filter(([, , topic]) => linked || topic > 0)
          .sort((a, b) => b[1] - a[1] || a[0] - b[0])
          .slice(0, MAX_RELATED)
          .map(([index]) => index);
        if (list.length) ranked[page] = list;
      }
      const used = [...new Set(Object.values(ranked).flat())].sort((a, b) => a - b);
      const position = new Map(used.map((index, i) => [index, i]));
      const posts = used.map((index) => {
        const post = listed[index];
        return {
          title: post.metadata.title,
          permalink: post.metadata.permalink,
          cover: relatedCover(post),
          // formatted in the reader's language by the card
          date: post.metadata.date.toISOString(),
        };
      });
      const related = Object.fromEntries(
        Object.entries(ranked).map(([key, list]) => [key, list.map((index) => position.get(index))]),
      );
      actions.setGlobalData({ videos: own.videos, posts, related });
    },
  };
};
