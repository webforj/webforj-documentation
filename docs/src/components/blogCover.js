import useBaseUrl from '@docusaurus/useBaseUrl';

// every image in the blog folder, so a card can show the cover of a post
// whose image sits next to it (only the addresses are bundled here)
const blogImages = require.context('@site/blog', true, /\.(png|jpe?g|webp|gif|svg)$/);

// The address of a post cover as config/blog-related-posts.js describes it:
// { src } for remote and static images, { file } for one next to the post
export function useCoverSrc(cover) {
  const base = useBaseUrl(cover?.src?.startsWith('/') ? cover.src : '/');
  if (!cover) {
    return null;
  }
  if (cover.src) {
    return cover.src.startsWith('/') ? base : cover.src;
  }
  try {
    const asset = blogImages(`./${cover.file}`);
    return asset.default ?? asset;
  } catch {
    return null;
  }
}
