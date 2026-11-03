// "Explore" in the docs sidebar: the webforJ YouTube channel's videos and
// the latest blog posts, gathered when the site builds and handed to every
// page as global data (read by src/components/explore/ExploreCard.js).
//
// The videos come from YouTube's public feeds: no API key and no quota. Each
// channel has one feed for its shorts (playlist UUSH...) and one for its
// regular videos (UULF...). The feeds carry the latest 15 of each. When
// YouTube can't be reached the build carries on without videos.

const { relatedCover } = require('../config/blog-related-posts');

const CHANNEL_ID = 'UCiDL28nsXEodC2WJ9IYRTmQ'; // youtube.com/@webforJ
const LATEST_POSTS = 10;
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
      const posts = (blog?.blogPosts ?? [])
        .filter((post) => !post.metadata.unlisted && !post.metadata.frontMatter?.draft)
        .sort((a, b) => b.metadata.date.getTime() - a.metadata.date.getTime())
        .slice(0, LATEST_POSTS)
        .map((post) => ({
          title: post.metadata.title,
          permalink: post.metadata.permalink,
          tags: post.metadata.tags.map((tag) => tag.label),
          cover: relatedCover(post),
          // formatted in the reader's language by the card
          date: post.metadata.date.toISOString(),
        }));
      actions.setGlobalData({ videos: own.videos, posts });
    },
  };
};
