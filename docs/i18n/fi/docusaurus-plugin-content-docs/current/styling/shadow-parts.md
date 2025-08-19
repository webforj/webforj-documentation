---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: bad90a86a29eaf34485d5ee9150aacb3
---
CSS **Varjostin osat** antavat kehittäjille keinon tyylitellä elementtejä komponentin varjostin DOM:issa ulkopuolelta, samalla säilyttäen kapseloinnin.

## Johdanto {#introduction}

webforJ-komponentit on rakennettu käyttäen [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components) -tekniikoita, jotka perustuvat [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) -kapselointiin komponentin sisäisen rakenteen ja tyylioston suojaamiseksi.

:::tip Web Components
Web Components on teknologiaratkaisu, joka antaa sinun luoda uudelleenkäytettäviä, kapseloituja mukautettuja elementtejä käytettäväksi verkkosovelluksissa.
:::

**Shadow DOM** estää sisäisten tyylien ja merkintöjen vuotamisen komponentista tai ulkoisten tyylien vaikutuksen. Tämä kapselointi varmistaa, että komponentit pysyvät itsenäisinä, vähentäen tyylikonfliktien riskiä.

:::tip  Web Componentsin kapselointi
Kapselointi on Web Componentsin keskeinen etu. Pitämällä komponentin rakenne, tyylit ja käyttäytyminen erillään sovelluksen muusta osasta vältät ristiriitoja ja ylläpidät siistiä, ylläpidettävää koodia.
:::

Kapseloinnin vuoksi et voi **suoraan tyylitellä** varjostin DOM:issa olevia elementtejä käyttäen normaaleja CSS-valitsimia.

Esimerkiksi `dwc-button` -komponentti renderöi seuraavan rakenteen:

```html {2}
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">Nappi</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

Jos yrität tyylitellä `label` näin:

```css
/* Ei toimi */
dwc-button .control__label {
  color: pink;
}
```

se ei vaikuta mihinkään, koska `.control__label` -elementti sijaitsee varjostinjuurella.

Tässä kohtaa tulevat mukaan **CSS varjostinosat**.

## Tyylittely varjostinosilla {#styling-with-shadow-parts}

Varjostinosat sallivat ulkoisten tyylitiedostojen kohdistaa tiettyjä elementtejä varjostinpuussa, mutta **vain jos** nämä elementit on erikseen merkitty "altistetuiksi" komponentin toimesta.

### Kuinka osat altistetaan {#how-parts-are-exposed}

Jotta elementti voidaan altistaa ulkoiselle tyylittelylle, komponentin kirjoittajan on määritettävä `part`-attribuutti sille varjostin DOM:issa.

Kaikki webforJ-komponentit altistavat automaattisesti relevanssit osat tyylittelyä varten. Voit löytää luettelon tuetuista osista kunkin komponentin dokumentaation **Tyylittely > Varjostinosat** -osiosta.

Esimerkiksi `dwc-button` -komponentti altistaa osia kuten `prefix`, `label` ja `suffix`:

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Nappi</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

Kun osat on altistettu, niitä voidaan tyylitellä komponentin ulkopuolelta käyttäen [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part) -pseudo-elementtiä.

### `::part()` pseudo-elementti {#the-part-pseudo-element}

`::part()`-valitsin antaa sinun soveltaa tyylejä varjostin DOM:issa oleviin elementteihin, joilla on `part`-attribuutti.

Esimerkiksi, jotta voit muuttaa `label`-osan väriä `dwc-button` -komponentissa:

```css
dwc-button::part(label) {
  color: red;
}
```

Voit yhdistää `::part()` muiden valitsimien, kuten `:hover`, kanssa:

```css
dwc-button::part(label):hover {
  color: pink;
}
```

:::warning `::part()`-valitsimen rajoitukset
Et voi valita *sisällä* varjostinosassa. Seuraava ei **toimi**:

```css
/* Ei toimi */
dwc-button::part(label) span {
  /* CSS:tä tähän */
}
```
:::
