---
sidebar_position: 9
title: State
_i18n_hash: a6e594262709137318ed90066759b577
---
State tokens määrittelevät, miten komponentit visuaalisesti reagoivat käyttäjän vuorovaikutukseen—esimerkiksi kun ne ovat pois käytöstä tai fokusoituja. Nämä muuttujat auttavat varmistamaan johdonmukaisen käytöksen ja tyylittelyn kaikilla UI-elementeillä, ja ne ovat helposti mukautettavissa vastaamaan suunnittelujärjestelmääsi.

## Poissa käytöstä oleva tila {#disabled-state}
Poissa käytöstä olevan tilan ominaisuuksia käytetään, jotta elementti näyttää visuaalisesti inaktiiviselta ja ei-vuorovaikutteiselta.

### Esimerkki {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Muuttujat {#variables}

| **Muuttuja**              | **Oletusarvo**          |
|--------------------------|----------------------------|
| `--dwc-disabled-opacity` | 0.7                        |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) |

---

## Fokusoitu tila {#focus-state}

Kun komponentti saa fokuksen, sen ympärille näytetään fokuskehä, joka osoittaa sen aktiivisen tilan. Voit mukauttaa kehyksen ulkonäköä alla olevia muuttujia käyttäen. Näitä muuttujia käytetään yhdessä komponenttiteeman fokuskehän asetusten kanssa.

### Muuttujat {#variables-1}

| **Muuttuja**              | **Oletusarvo** |
|---------------------------|-------------------|
| `--dwc-focus-ring-l`      | 45%               |
| `--dwc-focus-ring-a`      | 0.4               |
| `--dwc-focus-ring-width`  | 3px               |
