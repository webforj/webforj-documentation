---
sidebar_position: 1
title: CSS Variables
_i18n_hash: b753c1b13cfcc45f72d6712e980ef952
---
CSS-variabelen spelen een centrale rol bij het aanpassen van de uitstraling van webforJ-componenten. Deze variabelen slaan herbruikbare waarden op, zoals kleuren, lettergroottes en ruimte, die consistent in uw app kunnen worden toegepast.

In tegenstelling tot traditionele benaderingen die afhankelijk waren van CSS-preprocessors zoals [SASS](https://sass-lang.com/) of [LESS](https://lesscss.org/), stellen CSS-variabelen **dynamische styling tijdens runtime** mogelijk. Ze verminderen herhaling, verbeteren het onderhoud en maken stijlen gemakkelijker te lezen en te beheren.

## Definiëren van CSS-variabelen {#defining-css-variables}

CSS-variabelen worden gedefinieerd met een dubbele streepje (`--`) prefix en kunnen worden gescopeerd binnen elke CSS-selector. De meest gebruikelijke praktijk is echter om ze te definiëren in de `:root` selector, die ze globaal scopeert.

```css
:root {
  --app-background: orange;
}
```

:::tip De `:root` pseudo-klasse
De [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) pseudo-klasse richt zich op het root-element van het document—meestal `<html>` in HTML. Het gedraagt zich als `html`, maar met een hogere specificiteit.
:::

CSS-variabelen kunnen elke string bevatten, niet alleen geldige CSS-waarden. Deze flexibiliteit is bijzonder nuttig bij het werken met JavaScript.

```css
html {
  --app-title: webforJ;
}
```

## Component-specifieke variabelen {#component-specific-variables}

Om een variabele voor een specifieke component te definiëren of te overschrijven, declareer hem binnen de selector van de component:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Referentie voor component-specifieke styling
Elke webforJ-component ondersteunt een specifieke set CSS-variabelen die de uitstraling ervan beheersen. Deze zijn gedocumenteerd in de sectie **Styling > CSS-eigenschappen** voor elke component. 
:::


## Gebruik van CSS-variabelen {#using-css-variables}

Gebruik de [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) functie om de waarde van een variabele in uw stijlen toe te passen:

```css
.panel {
  background-color: var(--app-background);
}
```

U kunt ook een fallback-waarde opgeven voor het geval de variabele niet gedefinieerd is:

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Manipuleren van variabelen met webforJ {#manipulating-variables-with-webforj}

CSS-variabelen kunnen dynamisch worden bijgewerkt via de webforJ API, waarmee realtime styling mogelijk is:

```java
// Stel een CSS-variabele in
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip Manipuleren van CSS-variabelen met JavaScript
webforJ stelt u in staat om JavaScript aan de clientzijde uit te voeren met behulp van de Page of Element API. Dit betekent dat u CSS-variabelen dynamisch tijdens runtime kunt manipuleren, net zoals u zou doen in standaard webtoepassingen.

```javascript
// Stel een CSS-variabele in
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Verkrijg een CSS-variabele
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Aanvullende bronnen {#additional-resources}

- [Gebruik van CSS Aangepaste Eigenschappen (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [Een Compleet Overzicht van Aangepaste Eigenschappen (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
