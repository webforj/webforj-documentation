---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: bad90a86a29eaf34485d5ee9150aacb3
---
CSS **Shadow Parts** bieten Entwicklern eine Möglichkeit, Elemente innerhalb des Shadow DOM eines Komponents von außen zu stylen, während die Kapselung erhalten bleibt.

## Einführung {#introduction}

Die webforJ-Komponenten sind mit [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components) aufgebaut, die auf dem [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) basieren, um die interne Struktur und die Stile eines Komponents zu kapseln.

:::tip Web Components
Web Components sind eine Suite von Technologien, mit denen Sie wiederverwendbare, kapselbare benutzerdefinierte Elemente für die Verwendung in Webanwendungen erstellen können.
:::

Der **Shadow DOM** verhindert, dass interne Stile und Markup aus dem Komponent entwichen oder von externen Stilen beeinflusst werden. Diese Kapselung stellt sicher, dass Komponenten eigenständig bleiben und das Risiko von Stilkonflikten verringert wird.

:::tip Web Components Kapselung
Kapselung ist ein wesentlicher Vorteil von Web Components. Indem Sie die Struktur, Stile und das Verhalten eines Komponents vom Rest Ihrer App getrennt halten, vermeiden Sie Konflikte und erhalten sauberen, wartbaren Code.
:::

Da diese Kapselung jedoch besteht, können Sie **Elemente innerhalb eines Shadow DOM nicht direkt** mit Standard-CSS-Selektoren stylen.

Zum Beispiel rendert die `dwc-button`-Komponente die folgende Struktur:

```html {2}
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">Button</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

Wenn Sie versuchen, das `label` so zu stylen:

```css
/* Funktioniert NICHT */
dwc-button .control__label {
  color: pink;
}
```

hat es keine Auswirkungen, da das `.control__label`-Element im Shadow Root lebt.

Hier kommen die **CSS Shadow Parts** ins Spiel.

## Stylisieren mit Shadow Parts {#styling-with-shadow-parts}

Shadow Parts ermöglichen es externen Stylesheets, spezifische Elemente innerhalb eines Shadow Trees anzusprechen, aber **nur wenn** diese Elemente explizit vom Komponent als „exponiert“ markiert sind.

### Wie Parts exponiert werden {#how-parts-are-exposed}

Um ein Element für das externe Styling zu exponieren, muss der Autor des Komponents dem Element im Shadow DOM ein `part`-Attribut zuweisen.

Alle webforJ-Komponenten exponieren automatisch relevante Teile zum Stylen. Sie finden die Liste der unterstützten Teile im Abschnitt **Styling > Shadow Parts** der Dokumentation jedes Komponents.

Zum Beispiel exponiert die `dwc-button`-Komponente Teile wie `prefix`, `label` und `suffix`:

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Button</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

Sobald sie exponiert sind, können diese Teile von außerhalb des Komponents mit dem [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part) Pseudoelement gestylt werden.

### Das `::part()` Pseudoelement {#the-part-pseudo-element}

Der `::part()`-Selektor ermöglicht es Ihnen, Stile auf Elemente im Shadow DOM anzuwenden, die mit einem `part`-Attribut markiert wurden.

Zum Beispiel, um die Farbe des `label`-Teils in einem `dwc-button` zu ändern:

```css
dwc-button::part(label) {
  color: red;
}
```

Sie können `::part()` mit anderen Selektoren kombinieren, wie `:hover`:

```css
dwc-button::part(label):hover {
  color: pink;
}
```

:::warning Einschränkungen des ::part() Selektors
Sie können *innerhalb* eines Shadow Parts nicht auswählen. Folgendes wird **nicht** funktionieren:

```css
/* Funktioniert NICHT */
dwc-button::part(label) span {
  /* CSS hier */
}
```
:::
