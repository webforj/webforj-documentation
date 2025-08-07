---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: 8dbd7759364573b73d0b1b00c6d7e219
---
CSS **Shadow Parts** antavat kehittäjille tavan muokata komponentin varjo-DOM:issa olevia elementtejä ulkoapäin säilyttäen samalla kapseloinnin.

## Johdanto {#introduction}

webforJ-komponentit perustuvat [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components) -tekniikoihin, jotka hyödyntävät [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) -ratkaisua komponentin sisäisen rakenteen ja tyylien kapselointiin.

:::tip Web Components
Web Components ovat teknologioiden kokoelma, joka mahdollistaa uusiutuvaa, kapseloitua mukautettua elementtiä luomisen verkkosovelluksille.
:::

**Shadow DOM** estää sisäisten tyylien ja merkintöjen vuotamisen komponentista tai ulkoisten tyylien vaikutuksen. Tämä kapselointi varmistaa, että komponentit pysyvät itsenäisinä, vähentäen tyylikonfliktien riskiä.

:::tip Web Components Kapselointi
Kapselointi on Web Componentsin keskeinen etu. Pitämällä komponentin rakenne, tyylit ja käyttäytyminen erillään sovelluksesi muusta osiosta, vältät ristiriidat ja ylläpidät selkeää, helposti ylläpidettävää koodia.
:::

Kuitenkin tämän kapseloinnin vuoksi et voi **suoraan muokata** varjo-DOM:issa olevia elementtejä vakio CSS-valitsimilla.

Esimerkiksi `dwc-button` komponentti luo seuraavan rakenteen:

```html {2}
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">Nappi</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

Jos yrität tyylitellä `label`-elementtiä näin:

```css
/* Ei TOIMI */
dwc-button .control__label {
  color: pink;
}
```

se ei vaikuta, koska `.control__label` elementti sijaitsee varjojuuren sisällä.

Tässä vaiheessa **CSS Shadow Parts** tulevat kuvaan.

## Tyylit varjo-osilla {#styling-with-shadow-parts}

Varjo-osat mahdollistavat ulkoisten tyylitiedostojen kohdistamisen tiettyihin elementteihin varjopuussa, mutta **vain jos** nämä elementit on selkeästi merkitty "altistettaviksi" komponentissa.

### Kuinka osat altistetaan {#how-parts-are-exposed}

Jotta elementti voidaan altistaa ulkoista muokkausta varten, komponentin kirjoittajan on annettava `part`-attribuutti sille varjo-DOM:issa.

Kaikki webforJ-komponentit altistavat automaattisesti asianmukaiset osat tyylittelyä varten. Löydät tuetut osat **Tyylittely > Varjo-osat** -osasta kunkin komponentin dokumentaatiossa.

Esimerkiksi `dwc-button` komponentti altistaa osia kuten `prefix`, `label` ja `suffix`:

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Nappi</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

Kun osat on altistettu, niitä voidaan tyylitellä komponentin ulkopuolelta käyttämällä [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part) -pseudoelementtiä.

### `::part()` pseudoelementti {#the-part-pseudo-element}

`::part()`-valitsin sallii sinun soveltaa tyylejä varjo-DOM:in elementteihin, jotka on merkitty `part`-attribuutille.

Esimerkiksi, jos haluat vaihtaa `label`-osan väriä `dwc-button`-komponentissa:

```css
dwc-button::part(label) {
  color: red;
}
```

Voit yhdistää `::part()` muihin valitsimiin, kuten `:hover`:

```css
dwc-button::part(label):hover {
  color: pink;
}
```

:::warning `::part()` valitsimen rajoitukset
Et voi valita *sisällä* varjo-osassa. Seuraava **ei** toimi:

```css
/* Ei TOIMI */
dwc-button::part(label) span {
  /* CSS tulee tähän */
}
```
:::
