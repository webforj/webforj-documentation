function updateAppTheme() {
  var html = document.documentElement;
  if (html.getAttribute('data-theme') === 'dark') {
    html.setAttribute('data-app-theme', 'dark');
  } else {
    html.removeAttribute('data-app-theme');
  }
}

var observer = new MutationObserver(function () {
  updateAppTheme();
});

observer.observe(document.documentElement, {
  attributes: true,
  attributeFilter: ['data-theme'],
});

updateAppTheme();

// circle reveal from toggle button
document.addEventListener('click', function (e) {
  var toggle = e.target.closest('[class*="toggleButton"][class*="ColorModeToggle"], [class*="toggleButton"][class*="colorMode"]');
  if (!toggle) return;
  if (!document.startViewTransition) return;
  if (toggle._transitioning) { toggle._transitioning = false; return; }

  e.preventDefault();
  e.stopPropagation();

  var rect = toggle.getBoundingClientRect();
  var cx = rect.left + rect.width / 2;
  var cy = rect.top + rect.height / 2;

  var kfEl = document.getElementById('theme-reveal-kf');
  if (kfEl) kfEl.remove();

  var kfStyle = document.createElement('style');
  kfStyle.id = 'theme-reveal-kf';
  kfStyle.textContent =
    '@keyframes theme-reveal {' +
    '0% { clip-path: circle(0% at ' + cx + 'px ' + cy + 'px); }' +
    '100% { clip-path: circle(150% at ' + cx + 'px ' + cy + 'px); }' +
    '}';
  document.head.appendChild(kfStyle);

  document.startViewTransition(function () {
    toggle._transitioning = true;
    toggle.click();
  });
}, true);
