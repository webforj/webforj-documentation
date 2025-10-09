function styleStarforj() {
  const container = document.querySelector('#startforj-link .startforj-container');
  const idleText = document.querySelector('#startforj-link .startforj-idle-text');
  const hoverText = document.querySelector('#startforj-link .startforj-hover-text');

  container.style.animation = 'none';
  if (idleText) {
    idleText.remove()
  }
  hoverText.style.color = 'white';
  hoverText.style.animation = 'none';
}

function tryUpdateStyle(retries = 10) {
  if (retries <= 0) return;

  const startforjLink = document.getElementById('startforj-link');
  if (startforjLink) {
    startforjLink.addEventListener('click', styleStarforj, { once: true });
  } else {
    setTimeout(() => tryUpdateStyle(retries - 1), 50);
  }
}

document.addEventListener('DOMContentLoaded', tryUpdateStyle);
window.addEventListener('popstate', tryUpdateStyle);
window.addEventListener('pushstate', tryUpdateStyle);

tryUpdateStyle();