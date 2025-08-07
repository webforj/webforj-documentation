---
sidebar_position: 6
title: Border
_i18n_hash: fe0a0386da63ff7ea085db8daa12d0fe
---
Rajausominaisuuksia käytetään ohjaamaan komponentin reunatyylin ja leveyden säätöä. Katso [saatavilla olevat reunatyylit](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Esimerkki {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) red;
}
```

### Muuttujat {#variables}

| **Muuttuja**             | **Oletusarvo**    |
|--------------------------|-------------------|
| `--dwc-border-width`     | 1px               |
| `--dwc-border-style`     | solid             |

## Reunus säde {#border-radius}

Reunus säteen muuttujat määrittelevät, kuinka pyöristetyt komponentin kulmat ovat. Kaikki arvot on määritelty `em`-yksiköissä, joten ne skaalautuvat fonttikoon mukaan.

:::info EM Yksikkö
`em` on suhteellinen yksikkö, joka skaalautuu vanhemman [fontin koon](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) mukaan.
:::

### Esimerkki {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Muuttujat {#variables-1}

| **Muuttuja**                    | **Oletusarvo**               | **Esimerkki**                           |
|----------------------------------|-------------------------------|-----------------------------------------|
| `--dwc-border-radius-2xs`       | 0.071em                        | <RadiusBox radius="--dwc-border-radius-2xs" /> |
| `--dwc-border-radius-xs`        | 0.125em                        | <RadiusBox radius="--dwc-border-radius-xs" /> |
| `--dwc-border-radius-s`         | 0.25em                         | <RadiusBox radius="--dwc-border-radius-s" />  |
| `--dwc-border-radius-m`         | 0.375em                        | <RadiusBox radius="--dwc-border-radius-m" />  |
| `--dwc-border-radius-l`         | 0.5em                          | <RadiusBox radius="--dwc-border-radius-l" />  |
| `--dwc-border-radius-xl`        | 0.75em                         | <RadiusBox radius="--dwc-border-radius-xl" /> |
| `--dwc-border-radius-2xl`       | 1em                            | <RadiusBox radius="--dwc-border-radius-2xl" /> |
| `--dwc-border-radius-round`     | 50%                            | <RadiusBox radius="--dwc-border-radius-round" /> |
| `--dwc-border-radius-pill`      | 9999px                         | <RadiusBox radius="--dwc-border-radius-pill" /> |
| `--dwc-border-radius`           | var(--dwc-border-radius-s)   | <RadiusBox radius="--dwc-border-radius" />     |
