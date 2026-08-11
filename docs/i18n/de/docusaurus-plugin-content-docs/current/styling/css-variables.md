---
sidebar_position: 1
title: CSS Variables
description: >-
  Define, scope, and consume CSS custom properties to control webforJ component
  styling at runtime without preprocessors.
_i18n_hash: 8e09f9776dc8bb74a1d37e6ba2bdceb0
---
CSS-Variablen spielen eine zentrale Rolle bei der Anpassung des Erscheinungsbildes von webforJ-Komponenten. Diese Variablen speichern wiederverwendbare Werte wie Farben, Schriftgrößen und Abstände, die konsistent in Ihrer Anwendung angewendet werden können.

Im Gegensatz zu traditionellen Ansätzen, die auf CSS-Präprozessoren wie [SASS](https://sass-lang.com/) oder [LESS](https://lesscss.org/) basierten, ermöglichen CSS-Variablen **dynamisches Styling zur Laufzeit**. Sie reduzieren Wiederholungen, verbessern die Wartbarkeit und machen Stylesheets leichter lesbar und verwaltbar.

## CSS-Variablen definieren {#defining-css-variables}

CSS-Variablen werden mit einem Doppelstrich (`--`) Prefix definiert und können innerhalb eines beliebigen CSS-Selectors eingeschränkt werden. Die gängigste Praxis besteht darin, sie im `:root` Selector zu definieren, wodurch sie global sind.

```css
:root {
  --app-background: orange;
}
```

:::tip Die `:root` Pseudo-Klasse
Die [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) Pseudo-Klasse zielt auf das Wurzelelement des Dokuments ab – typischerweise `<html>` in HTML. Sie verhält sich wie `html`, hat jedoch eine höhere Spezifität.
:::

CSS-Variablen können beliebige Zeichenfolgen halten, nicht nur gültige CSS-Werte. Diese Flexibilität ist besonders nützlich beim Arbeiten mit JavaScript.

```css
html {
  --app-title: webforJ;
}
```

## Komponentenspezifische Variablen {#component-specific-variables}

Um eine Variable für eine bestimmte Komponente zu definieren oder zu überschreiben, deklarieren Sie sie innerhalb des Selektors der Komponente:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Komponenten-spezifische Styling-Referenz
Jede webforJ-Komponente unterstützt eine spezifische Menge an CSS-Variablen, die ihr Erscheinungsbild steuern. Diese sind im Abschnitt **Styling > CSS-Eigenschaften** für jede Komponente dokumentiert.
:::

## CSS-Variablen verwenden {#using-css-variables}

Verwenden Sie die [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) Funktion, um den Wert einer Variable in Ihren Styles anzuwenden:

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

CSS-Variablen können über die webforJ API dynamisch aktualisiert werden, was ein Styling in Echtzeit ermöglicht:

```java
// Setzen einer CSS-Variable
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip Manipulation von CSS-Variablen mit JavaScript
webforJ ermöglicht es Ihnen, JavaScript auf der Client-Seite unter Verwendung der Page- oder Element-API auszuführen. Dies bedeutet, dass Sie CSS-Variablen zur Laufzeit dynamisch manipulieren können, genau wie in Standard-Webanwendungen.

```javascript
// Setzen einer CSS-Variable
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Abrufen einer CSS-Variable
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Weitere Ressourcen {#additional-resources}

- [Verwendung von CSS-Custom Properties (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)
- [Eine vollständige Anleitung zu benutzerdefinierten Eigenschaften (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
