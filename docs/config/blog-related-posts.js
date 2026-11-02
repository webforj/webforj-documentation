// Related posts for the blog: each post carries up to three posts that share
// the most tags with it (newest first), shown at its end as "Keep reading".
// Wired into the blog plugin as processBlogPosts in docusaurus.config.js.

const path = require('path');

const SITE_DIR = path.join(__dirname, '..');

/**
 * The cover of a blog post (its first image) in a form a related-post card
 * can show: an address for remote and static images, or a path inside the
 * blog folder for an image that sits next to the post, which the card
 * resolves through webpack.
 */
function relatedCover(post) {
  const match =
    post.content.match(/!\[[^\]]*\]\(([^)\s]+)/) ||
    post.content.match(/<img[^>]+src=["']([^"']+)/);
  if (!match) {
    return null;
  }
  const src = match[1];
  if (/^https?:\/\//.test(src) || src.startsWith('/')) {
    return { src };
  }
  if (src.includes('/static/')) {
    return { src: `/${src.split('/static/')[1]}` };
  }
  const blogDir = path.join(SITE_DIR, 'blog');
  const postFile = post.metadata.source.replace(/^@site\//, `${SITE_DIR}/`);
  const file = path.relative(blogDir, path.resolve(path.dirname(postFile), src));
  return { file: file.split(path.sep).join('/') };
}

/** @type {import('@docusaurus/plugin-content-blog').Options['processBlogPosts']} */
async function addRelatedPosts({ blogPosts }) {
  const listed = blogPosts.filter((post) => !post.metadata.unlisted);
  for (const post of blogPosts) {
    const tags = new Set(post.metadata.tags.map((tag) => tag.label));
    post.metadata.relatedPosts = listed
      .filter((other) => other.metadata.permalink !== post.metadata.permalink)
      .map((other) => ({
        other,
        shared: other.metadata.tags.filter((tag) => tags.has(tag.label)).length,
      }))
      .filter(({ shared }) => shared > 0)
      .sort(
        (a, b) =>
          b.shared - a.shared ||
          b.other.metadata.date.getTime() - a.other.metadata.date.getTime(),
      )
      .slice(0, 3)
      .map(({ other }) => ({
        title: other.metadata.title,
        permalink: other.metadata.permalink,
        description: other.metadata.description,
        cover: relatedCover(other),
        formattedDate: other.metadata.date.toLocaleDateString('en-US', {
          year: 'numeric',
          month: 'long',
          day: 'numeric',
        }),
      }));
  }
  return blogPosts;
}

module.exports = { addRelatedPosts, relatedCover };
