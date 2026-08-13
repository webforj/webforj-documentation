---
sidebar_position: 1
title: CSS Variables
description: >-
  Define, scope, and consume CSS custom properties to control webforJ component
  styling at runtime without preprocessors.
_i18n_hash: 8e09f9776dc8bb74a1d37e6ba2bdceb0
---
CSS-muuttujat ovat keskiroolissa webforJ-komponenttien ulkoasun mukauttamisessa. Nämä muuttujat tallentavat uudelleenkäytettäviä arvoja, kuten värejä, fonttikokoja ja välistystä, joita voidaan soveltaa johdonmukaisesti sovelluksessasi.

Toisin kuin perinteiset lähestymistavat, jotka luottivat CSS-esikäsittelijöihin kuten [SASS](https://sass-lang.com/) tai [LESS](https://lesscss.org/), CSS-muuttujat mahdollistavat **dynaamisen tyylittelyn ajon aikana**. Ne vähentävät toistoa, parantavat ylläpidettävyyttä ja tekevät tyylitiedostoista helpommin luettavia ja hallittavia.

## CSS-muuttujien määrittäminen {#defining-css-variables}

CSS-muuttujat määritellään kaksoisdashilla (`--`) ja ne voidaan rajoittaa mihin tahansa CSS-valitsimeen. Yleisintä käytäntöä on määrittää ne `:root`-valitsimessa, mikä rajoittaa ne globaalisti.

```css
:root {
  --app-background: orange;
}
```

:::tip `:root`-pseudo-luokka
[` :root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) pseudo-luokka kohdistaa asiakirjan juurielementtiin—tyypillisesti `<html>` HTML:ssä. Se käyttäytyy kuin `html`, mutta sillä on korkeampi spesifisyys.
:::

CSS-muuttujat voivat sisältää mitä tahansa merkkijonoja, eivät vain voimassa olevia CSS-arvoja. Tämä joustavuus on erityisen hyödyllistä työskennellessäsi JavaScriptin kanssa.

```css
html {
  --app-title: webforJ;
}
```

## Komponentti-spesifiset muuttujat {#component-specific-variables}

Määrittääksesi tai ohjataksesi muuttujan tietyssä komponentissa, ilmoita se komponentin valitsimen sisällä:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Komponentti-spesifinen tyylikirjasto
Jokainen webforJ-komponentti tukee tiettyä joukkoa CSS-muuttujia, jotka hallitsevat sen ulkoasua. Nämä on dokumentoitu **Tyylit > CSS-ominaisuudet**-osiolla jokaiselle komponentille.
:::

## CSS-muuttujien käyttäminen {#using-css-variables}

Käytä [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var())-funktiota soveltaaksesi muuttujan arvoa tyyleihisi:

```css
.panel {
  background-color: var(--app-background);
}
```

Voit myös määrittää varaympäristön, jos muuttujaa ei ole määritelty:

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Muuttujien käsittely webforJ:llä {#manipulating-variables-with-webforj}

CSS-muuttujia voidaan päivittää dynaamisesti webforJ API:n kautta, mahdollistaen reaaliaikaisen tyylittelyn:

```java
// Aseta CSS-muuttuja
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip CSS-muuttujien käsittely JavaScriptillä
webforJ mahdollistaa JavaScriptin suorittamisen asiakaspuolella Page- tai Element API:n avulla. Tämä tarkoittaa, että voit dynaamisesti manipuloida CSS-muuttujia ajon aikana aivan kuten standardeissa verkkosovelluksissa.

```javascript
// Aseta CSS-muuttuja
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Hanki CSS-muuttuja
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Lisäresurssit {#additional-resources}

- [CSS-muokattavien ominaisuuksien käyttäminen (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)
- [Täydellinen opas mukautettaville ominaisuuksille (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
