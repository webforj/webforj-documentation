function updateAppTheme() {
  const htmlElement = document.documentElement;
  if (htmlElement.getAttribute('data-theme') === 'dark') {
    htmlElement.setAttribute('data-app-theme', 'dark');
  } else {
    htmlElement.removeAttribute('data-app-theme');
  }
}

const observer = new MutationObserver(() => {
  updateAppTheme();
});

observer.observe(document.documentElement, {
  attributes: true,
  attributeFilter: ['data-theme'],
});

updateAppTheme();
