(function () {
  'use strict';

  var STEPS = [5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95];
  var PALETTES = ['primary', 'success', 'warning', 'danger', 'info', 'gray', 'default'];

  // Shared tooltip system for any element with data-var attribute
  var tip = document.createElement('div');
  tip.className = 'doc-tooltip';
  tip.setAttribute('aria-hidden', 'true');
  document.body.appendChild(tip);
  var tipTimer = null;

  function showTip(el, text) {
    clearTimeout(tipTimer);
    var rect = el.getBoundingClientRect();
    tip.textContent = text;
    tip.classList.add('doc-tooltip--visible');
    tip.style.left = (rect.left + rect.width / 2) + 'px';
    tip.style.top = (rect.top + window.scrollY - 8) + 'px';
  }

  function hideTip() {
    tip.classList.remove('doc-tooltip--visible');
  }

  function flashTip(el, text) {
    showTip(el, text);
    clearTimeout(tipTimer);
    tipTimer = setTimeout(hideTip, 900);
  }

  // Show var name on hover
  document.addEventListener('mouseover', function (e) {
    var el = e.target.closest('[data-var]');
    if (el) showTip(el, el.dataset.var);
  });
  document.addEventListener('mouseout', function (e) {
    var el = e.target.closest('[data-var]');
    if (el) hideTip();
  });

  // Copy var value on click and flash confirmation
  document.addEventListener('click', function (e) {
    var el = e.target.closest('[data-var]');
    if (!el) return;
    var value = 'var(' + el.dataset.var + ')';
    navigator.clipboard.writeText(value).then(function () {
      flashTip(el, 'Copied!');
    });
  });

  // Wrap content in a recessed demo frame
  function frame(inner) {
    return '<div class="doc-frame">' + inner + '</div>';
  }

  // Single palette row
  customElements.define('dwc-doc-palette', class extends HTMLElement {
    connectedCallback() {
      var self = this;
      var name = this.getAttribute('name') || 'primary';
      var html = '<div class="doc-swatch-row">';
      for (var i = 0; i < STEPS.length; i++) {
        var step = STEPS[i];
        var varName = '--dwc-color-' + name + '-' + step;
        html += '<button class="doc-swatch" data-var="' + varName + '" '
          + 'style="background:var(' + varName + ');color:var(--dwc-color-on-' + name + '-text-' + step + ')" '
          + '>'
          + step
          + '</button>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // All palettes overview as card grid
  customElements.define('dwc-doc-palettes', class extends HTMLElement {
    connectedCallback() {
      var html = '<div class="doc-palettes-grid">';
      for (var p = 0; p < PALETTES.length; p++) {
        var name = PALETTES[p];
        html += '<div class="doc-palette-card">'
          + '<span class="doc-palette-card__name">' + name + '</span>'
          + '<div class="doc-palette-card__swatches">';
        for (var i = 0; i < STEPS.length; i++) {
          var step = STEPS[i];
          var varName = '--dwc-color-' + name + '-' + step;
          html += '<button class="doc-swatch" data-var="' + varName + '" '
            + 'style="background:var(' + varName + ');color:var(--dwc-color-on-' + name + '-text-' + step + ')" '
            + '>'
            + step
            + '</button>';
        }
        html += '</div></div>';
      }
      html += '</div>';
      this.innerHTML = html;
    }
  });

  // Color variations demo
  customElements.define('dwc-doc-variations', class extends HTMLElement {
    connectedCallback() {
      var self = this;
      var name = this.getAttribute('name') || 'primary';
      var items = [
        { suffix: '-dark', label: 'Dark', desc: 'Active / Pressed' },
        { suffix: '', label: 'Base', desc: 'Default state' },
        { suffix: '-light', label: 'Light', desc: 'Hover / Focus' },
        { suffix: '-alt', label: 'Alt', desc: 'Subtle highlights' }
      ];

      var html = '<div class="doc-var-grid">';
      for (var i = 0; i < items.length; i++) {
        var it = items[i];
        var bgVar = '--dwc-color-' + name + it.suffix;
        var textVar = '--dwc-color-on-' + name + '-text' + it.suffix;
        html += '<button class="doc-var-card" data-var="' + bgVar + '">'
          + '<div class="doc-var-swatch" style="background:var(' + bgVar + ');color:var(' + textVar + ')">'
          + 'Aa'
          + '</div>'
          + '<span class="doc-var-label">' + it.label + '</span>'
          + '<span class="doc-var-desc">' + it.desc + '</span>'
          + '</button>';
      }
      html += '</div>';

      // Text color row
      var textItems = [
        { suffix: '-text-dark', label: 'Text Dark' },
        { suffix: '-text', label: 'Text' },
        { suffix: '-text-light', label: 'Text Light' }
      ];
      html += '<div class="doc-var-text-row">';
      for (var j = 0; j < textItems.length; j++) {
        var tv = textItems[j];
        var tVar = '--dwc-color-' + name + tv.suffix;
        html += '<button class="doc-var-text" data-var="' + tVar + '">'
          + '<span class="doc-var-text-sample" style="color:var(' + tVar + ')">Aa</span>'
          + '<span class="doc-var-label">' + tv.label + '</span>'
          + '</button>';
      }
      html += '</div>';

      this.innerHTML = frame(html);
    }
  });

  // Shadows
  customElements.define('dwc-doc-shadows', class extends HTMLElement {
    connectedCallback() {
      var levels = [
        { name: 'xs', label: 'XS' },
        { name: 's', label: 'S' },
        { name: 'm', label: 'M' },
        { name: 'l', label: 'L' },
        { name: 'xl', label: 'XL' },
        { name: '2xl', label: '2XL' }
      ];
      var html = '<div class="doc-shadow-grid">';
      for (var i = 0; i < levels.length; i++) {
        var s = levels[i];
        html += '<div class="doc-shadow-item" data-var="--dwc-shadow-' + s.name + '">'
          + '<div class="doc-shadow-card" style="box-shadow:var(--dwc-shadow-' + s.name + ')"></div>'
          + '<span class="doc-shadow-label">' + s.label + '</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // Border radius
  customElements.define('dwc-doc-radii', class extends HTMLElement {
    connectedCallback() {
      var radii = [
        { name: '2xs', label: '2XS' },
        { name: 'xs', label: 'XS' },
        { name: 's', label: 'S' },
        { name: 'm', label: 'M' },
        { name: 'l', label: 'L' },
        { name: 'xl', label: 'XL' },
        { name: '2xl', label: '2XL' },
        { name: '3xl', label: '3XL' },
        { name: 'round', label: 'Round' },
        { name: 'pill', label: 'Pill' }
      ];
      var html = '<div class="doc-radii-grid">';
      for (var i = 0; i < radii.length; i++) {
        var r = radii[i];
        var w = r.name === 'pill' ? 'width:80px' : '';
        html += '<div class="doc-radii-item" data-var="--dwc-border-radius-' + r.name + '">'
          + '<div class="doc-radii-shape" style="border-radius:var(--dwc-border-radius-' + r.name + ');' + w + '"></div>'
          + '<span class="doc-radii-label">' + r.label + '</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // Surfaces
  customElements.define('dwc-doc-surfaces', class extends HTMLElement {
    connectedCallback() {
      var levels = [
        { n: 1, label: 'Surface 1', desc: 'Page background' },
        { n: 2, label: 'Surface 2', desc: 'Toolbars, cards' },
        { n: 3, label: 'Surface 3', desc: 'Windows, dialogs' }
      ];
      var html = '<div class="doc-surface-grid">';
      for (var i = 0; i < levels.length; i++) {
        var l = levels[i];
        html += '<div class="doc-surface-card" data-var="--dwc-surface-' + l.n + '" style="background:var(--dwc-surface-' + l.n + ')">'
          + '<span class="doc-surface-title">' + l.label + '</span>'
          + '<span class="doc-surface-desc">' + l.desc + '</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // Sizes as proportional squares
  customElements.define('dwc-doc-sizes', class extends HTMLElement {
    connectedCallback() {
      var sizes = ['3xs', '2xs', 'xs', 's', 'm', 'l', 'xl', '2xl', '3xl'];
      var html = '<div class="doc-token-grid">';
      for (var i = 0; i < sizes.length; i++) {
        var s = sizes[i];
        html += '<div class="doc-token-item" data-var="--dwc-size-' + s + '">'
          + '<div class="doc-size-box" style="width:var(--dwc-size-' + s + ');height:var(--dwc-size-' + s + ')"></div>'
          + '<span class="doc-token-label">' + s.toUpperCase() + '</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // Spacing as block pairs with visible gap
  customElements.define('dwc-doc-spaces', class extends HTMLElement {
    connectedCallback() {
      var spaces = ['3xs', '2xs', 'xs', 's', 'm', 'l', 'xl', '2xl', '3xl'];
      var html = '<div class="doc-token-grid">';
      for (var i = 0; i < spaces.length; i++) {
        var s = spaces[i];
        html += '<div class="doc-token-item" data-var="--dwc-space-' + s + '">'
          + '<div class="doc-space-pair" style="gap:var(--dwc-space-' + s + ')">'
          + '<div class="doc-space-block"></div>'
          + '<div class="doc-space-block"></div>'
          + '</div>'
          + '<span class="doc-token-label">' + s.toUpperCase() + '</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // Transition speed toggles
  customElements.define('dwc-doc-transitions', class extends HTMLElement {
    connectedCallback() {
      var speeds = [
        { name: 'x-slow', label: 'X-Slow' },
        { name: 'slow', label: 'Slow' },
        { name: 'medium', label: 'Medium' },
        { name: 'fast', label: 'Fast' },
        { name: 'x-fast', label: 'X-Fast' }
      ];
      var html = '<div class="doc-token-grid">';
      for (var i = 0; i < speeds.length; i++) {
        var s = speeds[i];
        html += '<div class="doc-token-item">'
          + '<button class="doc-transition-btn" data-speed="' + s.name + '" data-var="--dwc-transition-' + s.name + '">'
          + '<div class="doc-transition-ball" style="transition:transform var(--dwc-transition-' + s.name + ') var(--dwc-ease)"></div>'
          + '</button>'
          + '<span class="doc-token-label">' + s.label + '</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);

      this.querySelectorAll('.doc-transition-btn').forEach(function (btn) {
        var ball = btn.querySelector('.doc-transition-ball');
        var active = false;
        btn.addEventListener('click', function () {
          active = !active;
          ball.style.transform = active ? 'translateX(40px)' : 'translateX(0)';
        });
      });
    }
  });

  // Font size samples
  customElements.define('dwc-doc-font-sizes', class extends HTMLElement {
    connectedCallback() {
      var sizes = [
        { name: '3xs', label: '3XS' },
        { name: '2xs', label: '2XS' },
        { name: 'xs', label: 'XS' },
        { name: 's', label: 'S' },
        { name: 'm', label: 'M' },
        { name: 'l', label: 'L' },
        { name: 'xl', label: 'XL' },
        { name: '2xl', label: '2XL' },
        { name: '3xl', label: '3XL' }
      ];
      var html = '<div class="doc-type-list">';
      for (var i = 0; i < sizes.length; i++) {
        var s = sizes[i];
        html += '<div class="doc-type-row" data-var="--dwc-font-size-' + s.name + '">'
          + '<span class="doc-type-label">' + s.label + '</span>'
          + '<span class="doc-type-sample" style="font-size:var(--dwc-font-size-' + s.name + ')">The quick brown fox</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // Font weight samples
  customElements.define('dwc-doc-font-weights', class extends HTMLElement {
    connectedCallback() {
      var weights = [
        { name: 'thin', label: 'Thin', val: '100' },
        { name: 'lighter', label: 'Lighter', val: '200' },
        { name: 'light', label: 'Light', val: '300' },
        { name: 'normal', label: 'Normal', val: '400' },
        { name: 'medium', label: 'Medium', val: '500' },
        { name: 'semibold', label: 'Semibold', val: '600' },
        { name: 'bold', label: 'Bold', val: '700' },
        { name: 'bolder', label: 'Bolder', val: '800' },
        { name: 'black', label: 'Black', val: '900' }
      ];
      var html = '<div class="doc-type-list">';
      for (var i = 0; i < weights.length; i++) {
        var w = weights[i];
        html += '<div class="doc-type-row" data-var="--dwc-font-weight-' + w.name + '">'
          + '<span class="doc-type-label">' + w.val + '</span>'
          + '<span class="doc-type-sample" style="font-weight:var(--dwc-font-weight-' + w.name + ');font-size:var(--dwc-font-size-xl)">' + w.label + '</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // Line height samples
  customElements.define('dwc-doc-line-heights', class extends HTMLElement {
    connectedCallback() {
      var heights = [
        { name: '3xs', label: '3XS', val: '1' },
        { name: '2xs', label: '2XS', val: '1.1' },
        { name: 'xs', label: 'XS', val: '1.25' },
        { name: 's', label: 'S', val: '1.375' },
        { name: 'm', label: 'M', val: '1.5' },
        { name: 'l', label: 'L', val: '1.625' },
        { name: 'xl', label: 'XL', val: '1.75' },
        { name: '2xl', label: '2XL', val: '1.875' },
        { name: '3xl', label: '3XL', val: '2' }
      ];
      var html = '<div class="doc-type-list">';
      for (var i = 0; i < heights.length; i++) {
        var h = heights[i];
        html += '<div class="doc-type-row" data-var="--dwc-font-line-height-' + h.name + '">'
          + '<span class="doc-type-label">' + h.label + '</span>'
          + '<span class="doc-type-sample doc-type-sample--lh" style="line-height:var(--dwc-font-line-height-' + h.name + ')">'
          + 'The quick brown fox jumped over the lazy dog. Pack my box with five dozen liquor jugs.'
          + '</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // Generated variables per step demo
  customElements.define('dwc-doc-step-vars', class extends HTMLElement {
    connectedCallback() {
      var name = this.getAttribute('name') || 'primary';
      var demoSteps = [10, 25, 40, 50, 60, 75, 90];
      var html = '<div class="doc-step-vars">';
      for (var i = 0; i < demoSteps.length; i++) {
        var step = demoSteps[i];
        var bg = '--dwc-color-' + name + '-' + step;
        var text = '--dwc-color-' + name + '-text-' + step;
        var onText = '--dwc-color-on-' + name + '-text-' + step;
        html += '<div class="doc-step-card">'
          + '<div class="doc-step-card__shade" data-var="' + bg + '" style="background:var(' + bg + ');color:var(' + onText + ')">'
          + '<span class="doc-step-card__on-label">on-text</span>'
          + '</div>'
          + '<div class="doc-step-card__text" data-var="' + text + '" style="color:var(' + text + ')">'
          + 'text'
          + '</div>'
          + '<span class="doc-step-card__step">' + step + '</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // Hue rotation playground
  customElements.define('dwc-doc-hue-rotate', class extends HTMLElement {
    connectedCallback() {
      var name = this.getAttribute('name') || 'primary';
      var html = '<div class="doc-hue-rotate">'
        + '<div class="doc-hue-rotate__controls">'
        + '<label class="doc-hue-rotate__label">--dwc-color-hue-rotate: <strong class="doc-hue-rotate__value">3</strong></label>'
        + '<input type="range" class="doc-hue-rotate__slider" min="0" max="90" value="3" step="1">'
        + '</div>'
        + '<div class="doc-swatch-row">';
      for (var i = 0; i < STEPS.length; i++) {
        var step = STEPS[i];
        var varName = '--dwc-color-' + name + '-' + step;
        html += '<button class="doc-swatch" data-var="' + varName + '" '
          + 'style="background:var(' + varName + ');color:var(--dwc-color-on-' + name + '-text-' + step + ')">'
          + step + '</button>';
      }
      html += '</div></div>';
      this.innerHTML = frame(html);

      var slider = this.querySelector('.doc-hue-rotate__slider');
      var valueEl = this.querySelector('.doc-hue-rotate__value');
      slider.addEventListener('input', function () {
        document.documentElement.style.setProperty('--dwc-color-hue-rotate', slider.value);
        valueEl.textContent = slider.value;
      });
    }
  });

  // Focus rings
  customElements.define('dwc-doc-focus-rings', class extends HTMLElement {
    connectedCallback() {
      var names = ['primary', 'success', 'warning', 'danger', 'info', 'default'];
      var html = '<div class="doc-ring-grid">';
      for (var i = 0; i < names.length; i++) {
        html += '<div class="doc-ring-item" data-var="--dwc-focus-ring-' + names[i] + '">'
          + '<div class="doc-ring-box" style="box-shadow:var(--dwc-focus-ring-' + names[i] + ')"></div>'
          + '<span class="doc-ring-label">' + names[i] + '</span>'
          + '</div>';
      }
      html += '</div>';
      this.innerHTML = frame(html);
    }
  });

  // Scales
  customElements.define('dwc-doc-scales', class extends HTMLElement {
    connectedCallback() {
      var html = '<div class="doc-press-grid">'
        + '<div class="doc-press-item">'
        + '<button class="doc-press-btn doc-press-btn--normal" data-var="--dwc-scale-press">Click me</button>'
        + '<span class="doc-press-label">press (0.97)</span>'
        + '</div>'
        + '<div class="doc-press-item">'
        + '<button class="doc-press-btn doc-press-btn--deep" data-var="--dwc-scale-press-deep">Click me</button>'
        + '<span class="doc-press-label">press-deep (0.93)</span>'
        + '</div>'
        + '</div>';
      this.innerHTML = frame(html);
    }
  });

})();
