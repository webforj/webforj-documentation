---
sidebar_position: 1
title: CSS Variables
_i18n_hash: b753c1b13cfcc45f72d6712e980ef952
---
CSS-muuttujat ovat keskeisessä roolissa webforJ-komponenttien ulkoasun mukauttamisessa. Nämä muuttujat tallentavat uudelleenkäytettäviä arvoja, kuten värejä, fonttikokoja ja välejä, joita voidaan soveltaa johdonmukaisesti sovelluksessasi.

Toisin kuin perinteiset lähestymistavat, jotka perustuivat CSS-esikäsittelijöihin kuten [SASS](https://sass-lang.com/) tai [LESS](https://lesscss.org/), CSS-muuttujat mahdollistavat **dynaamisten tyylien määrittämisen ajonaikaisesti**. Ne vähentävät toistoa, parantavat ylläpidettävyyttä ja tekevät tyylitiedostoista helpommin luettavia ja hallittavia.

## CSS-muuttujien määrittäminen {#defining-css-variables}

CSS-muuttujat määritetään käyttämällä kaksoisdashia (`--`) etuliitteenä, ja ne voidaan rajata mihin tahansa CSS-valitsimeen. Yleisimmät käytännöt ovat määritellä ne `:root`-valitsimessa, joka rajaa ne globaalisti.

```css
:root {
  --app-background: orange;
}
```

:::tip `:root` -pseudo-luokka
[` :root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) -pseudo-luokka kohdistuu asiakirjan juurielementtiin—yleensä `<html>` HTML:ssä. Se käyttäytyy kuin `html`, mutta sillä on suurempi spesifisyys.
:::

CSS-muuttujat voivat sisältää minkä tahansa merkkijonon, eivät vain kelvollisia CSS-arvoja. Tämä joustavuus on erityisen hyödyllistä työskenneltäessä JavaScriptin kanssa.

```css
html {
  --app-title: webforJ;
}
```

## Komponenttikohtaiset muuttujat {#component-specific-variables}

Määritelläksesi tai ohjataksesi muuttujan tietylle komponentille, ilmoita se komponentin valitsimen sisällä:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Komponenttikohtainen tyyliviite
Jokainen webforJ-komponentti tukee tiettyä CSS-muuttujien joukkoa, joka ohjaa sen ulkoasua. Nämä on dokumentoitu **Tyylit > CSS-ominaisuudet** -osiossa jokaiselle komponentille. 
:::

## CSS-muuttujien käyttäminen {#using-css-variables}

Käytä [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) -funktiota sovellaksesi muuttujan arvoa tyyleihisi:

```css
.panel {
  background-color: var(--app-background);
}
```

Voit myös määrittää varaväriarvon, jos muuttujaa ei ole määritelty:

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Muuttujien manipulointi webforJ:llä {#manipulating-variables-with-webforj}

CSS-muuttujia voidaan päivittää dynaamisesti webforJ-API:n kautta, mahdollistaen reaaliaikaisen tyylin:

```java
// Määritä CSS-muuttuja
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip CSS-muuttujien manipulointi JavaScriptilla
webforJ mahdollistaa JavaScriptin suorittamisen asiakkaan puolella käyttäen Sivusivun tai Elementin APIa. Tämä tarkoittaa, että voit dynaamisesti manipuloida CSS-muuttujia ajonaikaisesti aivan kuten normaalissa verkkosovelluksessa.

```javascript
// Määritä CSS-muuttuja
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Hanki CSS-muuttuja
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Lisäresurssit {#additional-resources}

- [CSS-muuttujien käyttö (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [Täydellinen opas mukautetuille ominaisuuksille (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
