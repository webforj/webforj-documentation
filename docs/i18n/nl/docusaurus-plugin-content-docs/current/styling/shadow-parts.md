---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: bad90a86a29eaf34485d5ee9150aacb3
---
CSS **Schaduw Onderdelen** geven ontwikkelaars een manier om elementen binnen de schaduw DOM van een component van buitenaf te stylen, terwijl de encapsulatie behouden blijft.

## Introductie {#introduction}

De webforJ-componenten zijn gebouwd met behulp van [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components), die vertrouwen op de [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) om de interne structuur en stijlen van een component te encapsuleren.

:::tip Web Components
Web Components zijn een suite van technologieën waarmee je herbruikbare, geëncapsuleerde op maat gemaakte elementen kunt maken voor gebruik in webapplicaties.
:::

De **Shadow DOM** voorkomt dat interne stijlen en markup naar buiten lekken of worden beïnvloed door externe stijlen. Deze encapsulatie zorgt ervoor dat componenten zelfvoorzienend blijven, wat het risico op stijlconflicten vermindert.

:::tip Web Components Encapsulatie
Encapsulatie is een belangrijk voordeel van Web Components. Door de structuur, stijlen en gedrag van een component gescheiden te houden van de rest van je app, voorkom je conflicten en behoud je schone, onderhoudbare code.
:::

Echter, vanwege deze encapsulatie **kun je elementen binnen een schaduw DOM niet direct stilen** met standaard CSS-selectors.

Bijvoorbeeld, de `dwc-button` component render de volgende structuur:

```html {2}
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">Button</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

Als je probeert de `label` zo te stylen:

```css
/* Werkt NIET */
dwc-button .control__label {
  color: pink;
}
```

zal het geen effect hebben, omdat het `.control__label` element zich binnen de schadow root bevindt.

Hier komen de **CSS Schaduw Onderdelen** in beeld.

## Stylen met schaduw onderdelen {#styling-with-shadow-parts}

Schaduw onderdelen laten externe stylesheets specifieke elementen binnen een schaduw boom targetten, maar **alleen als** die elementen expliciet zijn gemarkeerd als "blootgesteld" door de component.

### Hoe onderdelen worden blootgesteld {#how-parts-are-exposed}

Om een element voor externe styling bloot te stellen, moet de component auteur een `part` attribuut toewijzen aan het element binnen de schaduw DOM.

Alle webforJ-componenten stellen automatisch relevante onderdelen voor styling bloot. Je kunt de lijst van ondersteunde onderdelen vinden in de sectie **Styling > Schaduw onderdelen** van de documentatie van elke component.

Bijvoorbeeld, de `dwc-button` component stelt onderdelen bloot zoals `prefix`, `label` en `suffix`:

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Button</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

Zodra deze onderdeel zijn blootgesteld, kunnen ze van buiten de component gestyled worden met behulp van de [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part) pseudo-element.


### De `::part()` pseudo-element {#the-part-pseudo-element}

De `::part()` selector stelt je in staat om stijlen toe te passen op elementen binnen de schaduw DOM die zijn gemarkeerd met een `part` attribuut.

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
Je kunt niet *binnen* een schaduw onderdeel selecteren. Het volgende zal **niet** werken:

```css
/* Werkt NIET */
dwc-button::part(label) span {
  /* CSS gaat hier */
}
```
:::
