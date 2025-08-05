---
sidebar_position: 1
title: CSS Variables
_i18n_hash: f81f4fd4afdcb9807e10b8a35e244b20
---
CSS-Variablen spielen eine zentrale Rolle bei der Anpassung des Aussehens von webforJ-Komponenten. Diese Variablen speichern wiederverwendbare Werte wie Farben, Schriftgrößen und Abstände, die konsistent in Ihrer Anwendung angewendet werden können.

Im Gegensatz zu traditionellen Ansätzen, die auf CSS-Präprozessoren wie [SASS](https://sass-lang.com/) oder [LESS](https://lesscss.org/) angewiesen waren, ermöglichen CSS-Variablen **dynamisches Styling zur Laufzeit**. Sie reduzieren Wiederholungen, verbessern die Wartbarkeit und machen Stylesheets leichter lesbar und verwaltbar.

## CSS-Variablen definieren {#defining-css-variables}

CSS-Variablen werden mit einem Doppelstrich (`--`) Präfix definiert und können innerhalb jedes CSS-Selectors lokalisiert werden. Die gängigste Praxis besteht jedoch darin, sie im `:root`-Selector zu definieren, wodurch sie global verfügbar sind.

```css
:root {
  --app-background: orange;
}
```

:::tip Die `:root` Pseudo-Klasse
Die [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) Pseudo-Klasse zielt auf das Root-Element des Dokuments ab—typischerweise `<html>` in HTML. Sie verhält sich wie `html`, hat jedoch eine höhere Spezifität.
:::

CSS-Variablen können jeden String halten, nicht nur gültige CSS-Werte. Diese Flexibilität ist besonders nützlich, wenn Sie mit JavaScript arbeiten.

```css
html {
  --app-title: webforJ;
}
```

## Komponentenspezifische Variablen {#component-specific-variables}

Um eine Variable für eine spezifische Komponente zu definieren oder zu überschreiben, deklarieren Sie sie innerhalb des Selectors der Komponente:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Referenz für komponentenspezifisches Styling
Jede webforJ-Komponente unterstützt ein spezifisches Set von CSS-Variablen, die ihr Aussehen steuern. Diese sind im Abschnitt **Styling > CSS-Eigenschaften** für jede Komponente dokumentiert. 
:::

## CSS-Variablen verwenden {#using-css-variables}

Verwenden Sie die [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var())-Funktion, um den Wert einer Variablen in Ihren Styles anzuwenden:

```css
.panel {
  background-color: var(--app-background);
}
```

Sie können auch einen Fallback-Wert angeben, falls die Variable nicht definiert ist:

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Variablen mit webforJ manipulieren {#manipulating-variables-with-webforj}

CSS-Variablen können über die webforJ-API dynamisch aktualisiert werden, was ein Styling in Echtzeit ermöglicht:

```java
// Setzen Sie eine CSS-Variable
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip Manipulieren von CSS-Variablen mit JavaScript
webforJ ermöglicht es Ihnen, JavaScript auf der Client-Seite mithilfe der Page- oder Element-API auszuführen. Das bedeutet, dass Sie CSS-Variablen zur Laufzeit dynamisch manipulieren können, genau wie Sie es in herkömmlichen Webanwendungen tun würden.

```javascript
// Setzen Sie eine CSS-Variable
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Holen Sie sich eine CSS-Variable
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Zusätzliche Ressourcen {#additional-resources}

- [Verwenden von CSS-Benutzerdefinierten Eigenschaften (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [Ein vollständiger Leitfaden zu benutzerdefinierten Eigenschaften (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
