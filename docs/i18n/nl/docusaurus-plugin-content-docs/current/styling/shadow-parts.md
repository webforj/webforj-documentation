---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: 8dbd7759364573b73d0b1b00c6d7e219
---
CSS **Shadow Parts** geven ontwikkelaars de mogelijkheid om elementen binnen de shadow DOM van een component van buitenaf te stylen, terwijl de encapsulatie behouden blijft.

## Introductie {#introduction}

De webforJ-componenten zijn gebouwd met behulp van [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components), die zich baseren op de [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) om de interne structuur en stijlen van een component te encapsuleren.

:::tip Web Components
Web Components zijn een suite van technologieën waarmee je herbruikbare, ingekapselde aangepaste elementen kunt maken voor gebruik in webapplicaties.
:::

De **Shadow DOM** voorkomt dat interne stijlen en opmaak naar buiten lekken of worden beïnvloed door externe stijlen. Deze encapsulatie zorgt ervoor dat componenten zelfvoorzienend blijven, waardoor het risico op stylingconflicten vermindert.

:::tip  Web Components Encapsulatie
Encapsulatie is een belangrijke voordelen van Web Components. Door de structuur, stijlen en gedrag van een component gescheiden te houden van de rest van je app, vermijd je conflicten en behoud je schone, onderhoudbare code.
:::

Echter, vanwege deze encapsulatie kun je **geen directe styling** toepassen op elementen binnen een shadow DOM met standaard CSS-selectors.

Bijvoorbeeld, de `dwc-button` component rendert de volgende structuur:

```html {2}
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">Button</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

Als je probeert het `label` zo te stylen:

```css
/* Werkt NIET */
dwc-button .control__label {
  color: pink;
}
```

zal het geen effect hebben, omdat het `.control__label` element zich binnen de shadow root bevindt.

Dit is waar **CSS Shadow Parts** in beeld komen.

## Stylen met shadow parts {#styling-with-shadow-parts}

Shadow parts stellen externe stylesheets in staat om specifieke elementen binnen een shadow tree te targeten, maar **alleen als** die elementen expliciet zijn gemarkeerd als "exposed" door de component.

### Hoe onderdelen worden blootgesteld {#how-parts-are-exposed}

Om een element bloot te stellen voor externe styling, moet de componentauteur een `part` attribuut eraan toekennen binnen de shadow DOM.

Alle webforJ-componenten stellen automatisch relevante onderdelen bloot voor styling. Je kunt de lijst van ondersteunde onderdelen vinden in de sectie **Styling > Shadow parts** van de documentatie van elke component.

Bijvoorbeeld, de `dwc-button` component stelt onderdelen bloot zoals `prefix`, `label` en `suffix`:

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Button</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

Eenmaal blootgesteld, kunnen deze onderdelen van buiten de component gestyled worden met de [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part) pseudo-element. 


### De `::part()` pseudo-element {#the-part-pseudo-element}

De `::part()`-selector stelt je in staat om stijlen toe te passen op elementen binnen de shadow DOM die zijn gemarkeerd met een `part` attribuut.

Bijvoorbeeld, om de kleur van het `label` onderdeel in een `dwc-button` te veranderen:

```css
dwc-button::part(label) {
  color: red;
}
```

Je kunt `::part()` combineren met andere selectors, zoals `:hover`:

```css
dwc-button::part(label):hover {
  color: pink;
}
```

:::warning Beperkingen van de ::part() Selector
Je kunt niet *binnen* een shadow part selecteren. Het volgende zal **niet** werken:

```css
/* Werkt NIET */
dwc-button::part(label) span {
  /* CSS gaat hier */
}
```
:::
