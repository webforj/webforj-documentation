// "Explore" on docs pages: the webforJ YouTube channel's videos and the blog
// posts related to each docs page, gathered when the site builds and handed
// to every page as global data (read by src/components/explore/ExploreCard.js).
//
// The videos come from YouTube's public feeds: no API key and no quota. Each
// channel has one feed for its shorts (playlist UUSH...) and one for its
// regular videos (UULF...). The feeds carry the latest 15 of each. When
// YouTube can't be reached the build carries on without videos.

const fs = require('fs');
const path = require('path');
const matter = require('gray-matter');
const { relatedCover } = require('../config/blog-related-posts');
const { rankRelatedPosts, rankRelatedVideos } = require('../config/docs-related-posts');

const CHANNEL_ID = 'UCiDL28nsXEodC2WJ9IYRTmQ'; // youtube.com/@webforJ
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
      const description = entry.match(/<media:description>([\s\S]*?)<\/media:description>/)?.[1] ?? '';
      if (!id || !title) {
        return null;
      }
      return {
        kind,
        id,
        title: decode(title),
        // only used to match the video to docs pages, not sent to the browser
        description: decode(description),
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

// The English text of a docs page or post, whatever language is being built:
// '@site/i18n/de/docusaurus-plugin-content-docs/current/x.md' -> docs/x.md.
// Falls back to the file of the language being built, then to `fallback`.
function englishText(siteDir, source, folder, fallback = '') {
  const file = source.replace(/^@site\//, '');
  const translated = file.match(/^i18n\/[^/]+\/docusaurus-plugin-content-(?:docs\/current|blog)\/(.+)$/);
  for (const candidate of [translated && path.join(folder, translated[1]), file]) {
    if (!candidate) continue;
    try {
      return matter(fs.readFileSync(path.join(siteDir, candidate), 'utf8'));
    } catch {
      // try the next one
    }
  }
  return { data: {}, content: fallback };
}

module.exports = function explorePlugin({ siteDir }) {
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

      // Which posts to suggest on which docs page: the posts about the same
      // topic as the page, compared by their text (see
      // config/docs-related-posts.js). Both sides are read in English, so every
      // language of the site suggests the same posts.
      const docsVersions = allContent['docusaurus-plugin-content-docs']?.default?.loadedVersions ?? [];
      const docs = [];
      const seen = new Set();
      for (const version of docsVersions) {
        for (const doc of version.docs ?? []) {
          const at = doc.permalink.indexOf('/docs/');
          const page = at < 0 ? null : doc.permalink.slice(at).replace(/\/$/, '');
          if (!page || seen.has(page) || doc.unlisted || doc.draft) continue;
          seen.add(page);
          const text = englishText(siteDir, doc.source, 'docs');
          docs.push({
            page,
            title: text.data.title ?? doc.title,
            description: text.data.description ?? doc.description,
            markdown: text.content,
          });
        }
      }
      const posts = listed.map((post) => {
        const text = englishText(siteDir, post.metadata.source, 'blog', post.content);
        const tags = [].concat(text.data.tags ?? post.metadata.tags.map((tag) => tag.label));
        return {
          title: text.data.title ?? post.metadata.title,
          description: text.data.description ?? post.metadata.description,
          tags,
          markdown: text.content,
          date: post.metadata.date,
          release: tags.includes('release'),
        };
      });
      const ranked = Object.fromEntries(
        Object.entries(rankRelatedPosts(docs, posts)).map(([page, list]) => [page, list.map((entry) => entry.index)]),
      );
      const used = [...new Set(Object.values(ranked).flat())].sort((a, b) => a - b);
      const position = new Map(used.map((index, i) => [index, i]));
      const cards = used.map((index) => {
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
      // the one video of each docs page, as an index into the videos shown
      const matched = rankRelatedVideos(docs, posts, own.videos);
      const shown = [...new Set(Object.values(matched))].sort((a, b) => a - b);
      const videos = shown.map((index) => {
        const { description, ...video } = own.videos[index];
        return video;
      });
      const relatedVideo = Object.fromEntries(
        Object.entries(matched).map(([page, index]) => [page, shown.indexOf(index)]),
      );
      actions.setGlobalData({ videos, relatedVideo, posts: cards, related });
    },
  };
};
