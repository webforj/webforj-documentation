---
sidebar_position: 9
title: State
_i18n_hash: b7227f8d2022e7e6eab96de3f802aa20
---
State-tunnukset määrittävät, miten komponentit reagoivat visuaalisesti käyttäjän vuorovaikutukseen - esimerkiksi kun ne ovat pois käytöstä tai tarkennettu. Nämä muuttujat auttavat varmistamaan johdonmukaisen käyttäytymisen ja tyylin kaikissa käyttöliittymäelementeissä, ja niitä voidaan helposti mukauttaa vastaamaan suunnittelujärjestelmääsi.

<!-- vale off -->
## Pois käytöstä -tila {#disabled-state}
<!-- vale on -->
Pois käytöstä -tilan ominaisuuksia käytetään tekemään elementistä visuaalisesti inaktiivinen ja ei-interaktiivinen.

### Esimerkki {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Muuttujat {#variables}

| **Muuttuja**             | **Oletusarvo**          |
|--------------------------|-------------------------|
| `--dwc-disabled-opacity` | 0.7                     |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) |

---

## Tarkennustila {#focus-state}

Kun komponentti saa tarkennuksen, sen ympärille näytetään tarkennusrenkaansa ilmoittaakseen sen aktiivisen tilan. Voit mukauttaa renkaan ulkoasua alla olevien muuttujien avulla. Näitä muuttujia käytetään yhdessä komponenttéeman tarkennusrenkaan asetusten kanssa.

### Muuttujat {#variables-1}

| **Muuttuja**              | **Oletusarvo** |
|---------------------------|-----------------|
| `--dwc-focus-ring-l`      | 45%             |
| `--dwc-focus-ring-a`      | 0.4             |
| `--dwc-focus-ring-width`  | 3px             |
