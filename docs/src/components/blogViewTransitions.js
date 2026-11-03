// Page transitions inside the blog, built on the browser's View Transitions
// API. Docusaurus routes on the client, so the transition wraps the client
// navigation: a click on a blog link starts the transition (which captures
// the current page), the navigation runs inside it, and the transition ends
// once the next blog page has rendered.
//
// It works like a hero transition on iOS: the cover and title of the card
// that was clicked travel into the cover and title of the post it opens. The
// page around them clears as they lift off and the post settles in as they
// land, so nothing passes over text. Only opening a post from a blog page takes part;
// tags, authors, the archive, pagination and the rest of the site navigate
// as usual.

const COVER = 'blog-cover';
const TITLE = 'blog-title';
// set on <html> while a post opens, so these styles never touch other view
// transitions such as the theme switch. A data attribute, not a class:
// Docusaurus rewrites the class attribute of <html> when the next page
// renders, which would drop it mid-transition.
const ACTIVE_ATTR = 'data-blog-morph';
const FALLBACK_MS = 3000;
const DECODE_MS = 800;

let installed = false;
let bypass = false;
let pending = null;

function isBlogPath(pathname) {
  return /^\/blog(\/|$)/.test(pathname);
}

// a post, as opposed to a listing (home, pages, tags, authors, archive)
function isPostPath(pathname) {
  return (
    isBlogPath(pathname) &&
    !/^\/blog(\/page\/\d+|\/tags(\/.*)?|\/authors(\/.*)?|\/archive)?\/?$/.test(pathname)
  );
}

function samePath(a, b) {
  return a.replace(/\/$/, '') === b.replace(/\/$/, '');
}

function reducedMotion() {
  return window.matchMedia('(prefers-reduced-motion: reduce)').matches;
}

function name(element, value) {
  if (element) element.style.viewTransitionName = value;
}

function onClick(event) {
  if (bypass || event.defaultPrevented || event.button !== 0) return;
  if (event.metaKey || event.ctrlKey || event.shiftKey || event.altKey) return;

  const link = event.target.closest('a[href]');
  if (!link || (link.target && link.target !== '_self')) return;

  const url = new URL(link.href, window.location.href);
  if (url.origin !== window.location.origin) return;
  if (!isBlogPath(window.location.pathname) || !isPostPath(url.pathname)) return;
  if (samePath(url.pathname, window.location.pathname)) return;

  event.preventDefault();
  event.stopPropagation();

  // the cover and title of the clicked card travel into the post
  const card = link.closest('main article');
  const oldParts = [
    [card?.querySelector('.markdown > p:has(img) img'), COVER],
    [card?.querySelector('h2'), TITLE],
  ];
  oldParts.forEach(([element, value]) => name(element, value));
  document.documentElement.setAttribute(ACTIVE_ATTR, '');

  let newParts = [];

  const transition = document.startViewTransition(
    () =>
      new Promise((resolve) => {
        const timer = setTimeout(resolve, FALLBACK_MS);
        pending = {
          pathname: url.pathname,
          finish(parts) {
            clearTimeout(timer);
            newParts = parts;
            resolve();
          },
        };
        // the current page is captured; navigate through the site's router
        bypass = true;
        link.click();
        bypass = false;
      }),
  );

  transition.finished.finally(() => {
    document.documentElement.removeAttribute(ACTIVE_ATTR);
    [...oldParts, ...newParts].forEach(([element]) => name(element, ''));
  });
}

// Called once on the client by the blog layout
export function installBlogViewTransitions() {
  if (installed || typeof document === 'undefined') return;
  if (!document.startViewTransition || reducedMotion()) return;
  installed = true;
  document.addEventListener('click', onClick, true);
}

// Called by the blog layout after a blog page has rendered. When it is the
// page a transition is waiting for, its cover and title take the names of
// the card's, and the transition ends once that cover can be painted.
export function blogPageRendered(pathname) {
  if (!pending || !samePath(pathname, pending.pathname)) return;
  const { finish } = pending;
  pending = null;

  const post = document.querySelector('main article');
  const cover = post?.querySelector('.markdown > p:first-child img');
  const parts = [
    [cover, COVER],
    [post?.querySelector('header h1'), TITLE],
  ];
  parts.forEach(([element, value]) => name(element, value));

  if (!cover) {
    finish(parts);
    return;
  }
  cover.loading = 'eager';
  const timeout = new Promise((resolve) => setTimeout(resolve, DECODE_MS));
  Promise.race([cover.decode().catch(() => {}), timeout]).then(() => finish(parts));
}
