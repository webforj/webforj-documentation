---
sidebar_position: 8
title: Pinnat
description: >-
  Jäsennä käyttöliittymän hierarkiaa kolmella tilan mukaan mukautuvalla
  DWC-pintatokenilla sivujen taustoille, korteille sekä ponnahdusikkunoille tai
  dialogeille.
_i18n_title_hash: d40d1008e1abc156a84c98148422b0b9
_i18n_description_hash: 35f5960e176694545a805c578abc82b8
_i18n_hash: cac300e6e9c10cd9d1da6b266e536c74
---
DWC määrittelee kolme tasoa pintoja, joita käytetään käyttöliittymän hierarkian järjestämiseen yhdessä [varjojen](./shadows) kanssa. Kaikki [väripaletin värit](./colors) on testattu siten, että niiden ja näiden pintojen välillä on tarpeeksi kontrastia.

Pinnat saavat hienovaraisen sävyn pääväristä ja mukautuvat automaattisesti vaaleisiin ja tummiin tiloihin.

### Esimerkki {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Muuttujat {#variables}

| **Muuttuja**      | **Käyttö**                          |
|-------------------|------------------------------------|
| `--dwc-surface-1` | Sivun ja kehon tausta.            |
| `--dwc-surface-2` | Työkalurivit, valikkorivit, kortit.|
| `--dwc-surface-3` | Ikkunat, valikot, popoverit, dialogit.|

<dwc-doc-surfaces></dwc-doc-surfaces>
