function decorateLinks() {
  document.querySelectorAll('a').forEach(a => {
    if (a.textContent.trim() && a.children.length === 0) {
      a.classList.add('empty-link');
    }
  });
}

function tryDecorate(retries = 10) {
  if (retries <= 0) return;
  decorateLinks();
  setTimeout(() => tryDecorate(retries - 1), 50);
}

document.addEventListener('DOMContentLoaded', tryDecorate);
window.addEventListener('popstate', tryDecorate);
window.addEventListener('pushstate', tryDecorate);
tryDecorate();
