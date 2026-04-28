---
sidebar_position: 6
title: Border
_i18n_hash: aec4d011f38db8c5a7a6c324eb76d724
---
Reunusomina käytetään komponentin reunuksen tyylin ja leveyden hallitsemiseen. Katso [saatavilla olevat reunustyylit](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Esimerkki {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
}
```

### Muuttujat {#variables}

| **Muuttuja**              | **Oletusarvo** |
|---------------------------|-----------------|
| `--dwc-border-width`      | 1px             |
| `--dwc-border-style`      | solid           |
| `--dwc-border-color`      | var(--dwc-border-color-default) |
| `--dwc-border-color-emphasis` | var(--dwc-border-color-default-emphasis) |

### Väripaletin reunusvärit {#per-palette-border-colors}

Jokainen väripaletti tuottaa omat reunusvärimuuttujansa:

| Muuttujamalli | Kuvaus |
|---|---|
| `--dwc-border-color-{name}` | Reunusväri, joka on tilatietoinen ja sävytetty paletin sävyllä. |
| `--dwc-border-color-{name}-emphasis` | Vahvempi variaatio hover-, focus- ja aktiivisten tilojen varalle. |

Missä `{name}` on yksi seuraavista: `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`.

## Reunuksen säde {#border-radius}

Reunuksen sädemuuttujat määrittävät, kuinka pyöristetyt komponentin kulmat ovat. Kaikki koot skaalautuvat yhdestä siementä arvosta (`--dwc-border-radius-seed`). Siemenarvon muuttaminen skaalaa koko säderakenteen suhteellisesti.

### Esimerkki {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Muuttujat {#variables-1}

| **Muuttuja**                | **Oletusarvo**                  | **Laskettu (siemen=8px)** |
|-----------------------------|----------------------------------|---------------------------|
| `--dwc-border-radius-seed`  | 0.5rem                           | 8px                       |
| `--dwc-border-radius-2xs`   | 0.0625rem                        | 1px (kiinteä)            |
| `--dwc-border-radius-xs`    | 0.125rem                         | 2px (kiinteä)            |
| `--dwc-border-radius-s`     | calc(seed * 0.5)                 | 4px                       |
| `--dwc-border-radius-m`     | calc(seed * 0.75)                | 6px                       |
| `--dwc-border-radius-l`     | var(--dwc-border-radius-seed)    | 8px                       |
| `--dwc-border-radius-xl`    | calc(seed * 1.5)                 | 12px                      |
| `--dwc-border-radius-2xl`   | calc(seed * 2)                   | 16px                      |
| `--dwc-border-radius-3xl`   | calc(seed * 3)                   | 24px                      |
| `--dwc-border-radius-4xl`   | calc(seed * 4)                   | 32px                      |
| `--dwc-border-radius-round` | 50%                              |                           |
| `--dwc-border-radius-pill`  | calc(var(--dwc-size-m) / 2)      |                           |
| `--dwc-border-radius`       | var(--dwc-border-radius-seed)    | 8px                       |

<dwc-doc-radii></dwc-doc-radii>

### Käyttöohjeet {#usage-guidelines}

- Esineet säiliöissä: käytä `s` (0.5x siemen)
- Rakenteelliset reunat (esineen ja säiliön välillä): käytä `m` (0.75x siemen)
- Säiliöt ja pinnat: käytä `l` (1x siemen)
- Suuret peitteet: käytä `xl` (1.5x siemen)
