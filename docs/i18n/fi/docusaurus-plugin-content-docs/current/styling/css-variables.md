---
sidebar_position: 1
title: CSS Variables
_i18n_hash: f81f4fd4afdcb9807e10b8a35e244b20
---
CSS-muuttujat ovat keskeisessä roolissa webforJ-komponenttien ulkoasun mukauttamisessa. Nämä muuttujat tallentavat käytettäviä arvoja, kuten värejä, fonttikokoja ja väliä, joita voidaan soveltaa johdonmukaisesti sovelluksessasi.

Toisin kuin perinteiset lähestymistavat, jotka nojautuivat CSS-esikäsittelijöihin kuten [SASS](https://sass-lang.com/) tai [LESS](https://lesscss.org/), CSS-muuttujat mahdollistavat **dynaamisen tyylittelyn ajon aikana**. Ne vähentävät toistoa, parantavat ylläpidettävyyttä ja tekevät tyylitiedostoista helpommin luettavia ja hallittavia.

## CSS-muuttujien määrittäminen {#defining-css-variables}

CSS-muuttujat määritellään käyttäen kaksoisviivaa (`--`) etuliitteenä, ja ne voidaan rajata mihin tahansa CSS-valitsimeen. Yleisimmän käytännön mukaan ne määritellään `:root`-valitsimessa, joka rajaa ne globaalisti.

```css
:root {
  --app-background: orange;
}
```

:::tip `:root` pseudoluokka
[`_root_`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) pseudoluokka kohdistaa asiakirjan juurielementtiin—yleensä `<html>` HTML:ssä. Se käyttäytyy kuin `html`, mutta sillä on korkeampi spesifisyys.
:::

CSS-muuttujat voivat pitää sisällään mitä tahansa merkkijonoa, eivät vain voimassa olevia CSS-arvoja. Tämä joustavuus on erityisen hyödyllistä työskennellessäsi JavaScriptin kanssa.

```css
html {
  --app-title: webforJ;
}
```

## Komponentti-spesifiset muuttujat {#component-specific-variables}

Määritelläksesi tai ylittääksesi muuttujan tietyssä komponentissa, ilmoita se komponentin valitsimen sisällä:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Komponenttispefiset tyylittelyviitteet
Jokainen webforJ-komponentti tukee erityistä joukkoa CSS-muuttujia, jotka hallitsevat sen ulkoasua. Nämä on dokumentoitu **Tyylitys > CSS-ominaisuudet** -osiossa kullekin komponentille.
:::

## CSS-muuttujien käyttö {#using-css-variables}

Käytä [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var())-funktiota soveltaaksesi muuttujan arvoa tyyleihisi:

```css
.panel {
  background-color: var(--app-background);
}
```

Voit myös määritellä varavarauksen, jos muuttujaa ei ole määritelty:

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Muuttujien käsittely webforJ:llä {#manipulating-variables-with-webforj}

CSS-muuttujia voidaan päivittää dynaamisesti webforJ API:n kautta, mahdollistaen reaaliaikaisen tyylittelyn:

```java
// Asetetaan CSS-muuttuja
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip CSS-muuttujien käsittely JavaScriptillä
webforJ mahdollistaa JavaScriptin suorittamisen asiakaspuolella käyttäen Page- tai Element API:ta. Tämä tarkoittaa, että voit dynaamisesti käsitellä CSS-muuttujia ajon aikana aivan kuten tekisit tavanomaisissa verkkosovelluksissa.

```javascript
// Asetetaan CSS-muuttuja
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Haetaan CSS-muuttuja
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Lisäresurssit {#additional-resources}

- [CSS-käyttötilojen käyttäminen (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [Täydellinen opas mukautettuihin ominaisuuksiin (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
