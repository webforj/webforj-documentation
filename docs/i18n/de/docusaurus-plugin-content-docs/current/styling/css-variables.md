---
sidebar_position: 1
title: CSS-Variablen
_i18n_hash: b753c1b13cfcc45f72d6712e980ef952
---
CSS-Variablen spielen eine zentrale Rolle bei der Anpassung des Erscheinungsbildes von webforJ-Komponenten. Diese Variablen speichern wiederverwendbare Werte wie Farben, Schriftgrößen und Abstände, die konsistent in Ihrer App angewendet werden können.

Anders als traditionelle Ansätze, die auf CSS-Präprozessoren wie [SASS](https://sass-lang.com/) oder [LESS](https://lesscss.org/) basierten, ermöglichen CSS-Variablen **dynamisches Styling zur Laufzeit**. Sie reduzieren die Wiederholung, verbessern die Wartbarkeit und machen Stylesheets leichter lesbar und verwaltbar.

## CSS-Variablen definieren {#defining-css-variables}

CSS-Variablen werden mit einem Doppelstrich (`--`) Präfix definiert und können innerhalb jedes CSS-Selectors lokalisiert werden. Die gängigste Praxis besteht jedoch darin, sie im `:root`-Selector zu definieren, der sie global scopt.

```css
:root {
  --app-background: orange;
}
```

:::tip Die `:root` Pseudo-Klasse
Die [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) Pseudo-Klasse zielt auf das Wurzelement des Dokuments ab – typischerweise `<html>` in HTML. Sie verhält sich wie `html`, hat jedoch eine höhere Spezifität.
:::

CSS-Variablen können jede Zeichenfolge enthalten, nicht nur gültige CSS-Werte. Diese Flexibilität ist besonders nützlich, wenn Sie mit JavaScript arbeiten.

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

:::tip Referenz für komponentenspezifisches Styling
Jede webforJ-Komponente unterstützt eine spezifische Reihe von CSS-Variablen, die ihr Erscheinungsbild steuern. Diese sind im Abschnitt **Styling > CSS-Eigenschaften** für jede Komponente dokumentiert. 
:::


## CSS-Variablen verwenden {#using-css-variables}

Verwenden Sie die [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) Funktion, um den Wert einer Variable in Ihren Stilen anzuwenden:

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

CSS-Variablen können über die webforJ-API dynamisch aktualisiert werden, was eine Echtzeitgestaltung ermöglicht:

```java
// Setzen Sie eine CSS-Variable
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip Manipulation von CSS-Variablen mit JavaScript
webforJ ermöglicht es Ihnen, JavaScript auf der Client-Seite mit der Page- oder Element-API auszuführen. Das bedeutet, dass Sie CSS-Variablen zur Laufzeit dynamisch manipulieren können, genau wie Sie es in Standard-Webanwendungen tun würden.

```javascript
// Setzen Sie eine CSS-Variable
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Holen Sie sich eine CSS-Variable
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Weitere Ressourcen {#additional-resources}

- [CSS Custom Properties verwenden (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [Ein umfassender Leitfaden zu benutzerdefinierten Eigenschaften (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
