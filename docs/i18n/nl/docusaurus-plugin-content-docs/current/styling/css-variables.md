---
sidebar_position: 1
title: CSS Variables
_i18n_hash: f81f4fd4afdcb9807e10b8a35e244b20
---
CSS-variabelen spelen een centrale rol bij het aanpassen van het uiterlijk van webforJ-componenten. Deze variabelen slaan herbruikbare waarden op, zoals kleuren, lettergroottes en spatiëring, die consistent in je app kunnen worden toegepast.

In tegenstelling tot traditionele benaderingen die afhankelijk waren van CSS-preprocessors zoals [SASS](https://sass-lang.com/) of [LESS](https://lesscss.org/), maken CSS-variabelen **dynamische styling tijdens runtime** mogelijk. Ze verminderen herhaling, verbeteren de onderhoudbaarheid en maken stijlen gemakkelijker te lezen en te beheren.

## CSS-variabelen definiëren {#defining-css-variables}

CSS-variabelen worden gedefinieerd met een dubbele streepjes (`--`) prefix en kunnen binnen elke CSS-selector worden afgebakend. De meest voorkomende praktijk is echter om ze te definiëren in de `:root` selector, die ze globaal afbakent.

```css
:root {
  --app-background: orange;
}
```

:::tip De `:root` pseudo-klasse
De [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) pseudo-klasse richt zich op het root-element van het document—typisch `<html>` in HTML. Het gedraagt zich als `html`, maar met een hogere specificiteit.
:::

CSS-variabelen kunnen elke string bevatten, niet alleen geldige CSS-waarden. Deze flexibiliteit is bijzonder nuttig bij het werken met JavaScript.

```css
html {
  --app-title: webforJ;
}
```

## Component-specifieke variabelen {#component-specific-variables}

Om een variabele voor een specifieke component te definiëren of te overschrijven, declareer je deze binnen de selector van de component:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Component-specifieke stylingreferentie
Elke webforJ-component ondersteunt een specifieke set CSS-variabelen die zijn uiterlijk regelen. Deze worden gedocumenteerd in de sectie **Styling > CSS-eigenschappen** voor elke component.
:::


## CSS-variabelen gebruiken {#using-css-variables}

Gebruik de [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) functie om de waarde van een variabele in je stijlen toe te passen:

```css
.panel {
  background-color: var(--app-background);
}
```

Je kunt ook een fallback-waarde opgeven voor het geval de variabele niet is gedefinieerd:

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Variabelen manipuleren met webforJ {#manipulating-variables-with-webforj}

CSS-variabelen kunnen dynamisch worden bijgewerkt via de webforJ API, waardoor realtime styling mogelijk is:

```java
// Stel een CSS-variabele in
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip CSS-variabelen manipuleren met JavaScript
webforJ stelt je in staat om JavaScript aan de clientzijde uit te voeren met behulp van de Page of Element API. Dit betekent dat je CSS-variabelen dynamisch kunt manipuleren tijdens runtime, net zoals je zou doen in standaard webapplicaties.

```javascript
// Stel een CSS-variabele in
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Haal een CSS-variabele op
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Aanvullende bronnen {#additional-resources}

- [CSS Aangepaste Eigenschappen Gebruiken (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [Een Compleet Overzicht van Aangepaste Eigenschappen (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
