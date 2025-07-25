// Inject header title wrap CSS and ComboButton nowrap CSS
(function() {
  var style = document.createElement('style');
  style.textContent = `
    .navbar__title {
      white-space: normal !important;
      word-break: break-word;
      overflow-wrap: anywhere;
    }
    .ai-combobutton a, .ai-combobutton button {
      white-space: nowrap !important;
    }
  `;
  document.head.appendChild(style);
})();
function locateAiButton() {
  var aiButton = document.querySelector('.ai-combobutton');
  var header = document.querySelector('header');
  if (aiButton && header) {
    var leftContainer = header.querySelector('.header-left');
    var rightContainer = header.querySelector('.header-right');
    if (!leftContainer) {
      leftContainer = document.createElement('div');
      leftContainer.className = 'header-left';
      Array.from(header.children).forEach(function(child) {
        if (!child.classList.contains('ai-combobutton')) {
          leftContainer.appendChild(child);
        }
      });
      header.appendChild(leftContainer);
    }
    if (!rightContainer) {
      rightContainer = document.createElement('div');
      rightContainer.className = 'header-right';
      header.appendChild(rightContainer);
    }
    rightContainer.appendChild(aiButton);
    aiButton.style.display = 'block';
    header.style.display = 'flex';
    header.style.justifyContent = 'space-between';
    header.style.alignItems = 'center';
    leftContainer.style.display = 'flex';
    leftContainer.style.alignItems = 'center';
    rightContainer.style.display = 'flex';
    rightContainer.style.alignItems = 'center';
    return true;
  }
  return false;
}

function tryLocate(retries = 10) {
  if (retries <= 0) return;
  if (!locateAiButton()) {
    setTimeout(() => tryLocate(retries - 1), 50);
  }
}

document.addEventListener('DOMContentLoaded', tryLocate);
window.addEventListener('popstate', tryLocate);
window.addEventListener('pushstate', tryLocate);
tryLocate();
