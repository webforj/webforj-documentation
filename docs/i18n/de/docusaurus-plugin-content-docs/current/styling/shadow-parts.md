---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: 8dbd7759364573b73d0b1b00c6d7e219
---
CSS **Shadow Parts** geben Entwicklern die Möglichkeit, Elemente im Schatten-DOM einer Komponente von außen zu stylen, während die Kapselung erhalten bleibt.

## Einführung {#introduction}

Die webforJ-Komponenten sind unter Verwendung von [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components) aufgebaut, die auf dem [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) basieren, um die interne Struktur und die Stile einer Komponente zu kapseln.

:::tip Web Components
Web Components sind eine Suite von Technologien, mit denen Sie wiederverwendbare, gekapselte benutzerdefinierte Elemente für die Verwendung in Webanwendungen erstellen können.
:::

Das **Shadow DOM** verhindert, dass interne Stile und Markup aus der Komponente herausdringen oder von externen Stilen beeinflusst werden. Diese Kapselung stellt sicher, dass Komponenten eigenständig bleiben, wodurch das Risiko von Stilkonflikten verringert wird.

:::tip  Kapselung von Web Components
Kapselung ist ein wesentlicher Vorteil von Web Components. Indem Sie die Struktur, Stile und das Verhalten einer Komponente von den anderen Teilen Ihrer Anwendung trennen, vermeiden Sie Konflikte und halten den Code sauber und wartbar.
:::

Allerdings können Sie aufgrund dieser Kapselung Elemente im Schatten-DOM nicht direkt mit Standard-CSS-Selektoren stylen.

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

hat es keine Wirkung, da das `.control__label`-Element im Schatten-Wurzel lebt.

Hier kommen die **CSS Shadow Parts** ins Spiel.

## Styling mit Shadow Parts {#styling-with-shadow-parts}

Shadow Parts ermöglichen es externen Stylesheets, spezifische Elemente innerhalb eines Schattenbaums anzusprechen, jedoch **nur**, wenn diese Elemente ausdrücklich vom Komponentenautoren als „exponiert“ markiert wurden.

### Wie Teile exponiert werden {#how-parts-are-exposed}

Um ein Element für externes Styling zu exponieren, muss der Autor der Komponente ein `part`-Attribut zu diesem Element im Schatten-DOM zuweisen.

Alle webforJ-Komponenten exponieren automatisch relevante Teile für das Styling. Sie finden die Liste der unterstützten Teile im Abschnitt **Styling > Shadow Parts** der Dokumentation jeder Komponente.

Zum Beispiel exponiert die `dwc-button`-Komponente Teile wie `prefix`, `label` und `suffix`:

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Button</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

Sobald sie exponiert sind, können diese Teile von außerhalb der Komponente mit dem [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part) Pseudo-Element gestylt werden.

### Das `::part()` Pseudo-Element {#the-part-pseudo-element}

Der `::part()`-Selektor erlaubt es Ihnen, Stile auf Elemente innerhalb des Schatten-DOMs anzuwenden, die mit einem `part`-Attribut markiert wurden.

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
Sie können *nicht* innerhalb eines Schattenteils auswählen. Das folgende wird **nicht** funktionieren:

```css
/* Funktioniert NICHT */
dwc-button::part(label) span {
  /* CSS kommt hierhin */
}
```
:::
