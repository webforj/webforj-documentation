---
sidebar_position: 7
title: Shadows
_i18n_hash: 84ad4478632d3020d57752a4827f925a
---
Varjostusominaisuuksia käytetään varjotehosteiden lisäämiseen elementin kehyksen ympärille. Voit asettaa useita tehosteita erottamalla ne pilkuilla. Useimmissa tapauksissa varjoja käytetään merkkinä esineistä, jotka ovat ristikkäin käyttöliittymässä.

<Head>
  <style>{`
  table {
    width: 100%;
    display: table;
  }
  `}</style>
</Head>

### Esimerkki {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

:::tip Varjon väri
Voit hallita varjon väriä asettamalla `--dwc-shadow-color` -muuttujan. Oletuksena varjon väri on harmaa, jossa on päävärin sävy.
:::

### Muuttujat {#variables}

| **Muuttuja**       | **Esimerkki**                             |
|--------------------|------------------------------------------|
| `--dwc-shadow-xs`  | <ShadowBox shadow="--dwc-shadow-xs" />  |
| `--dwc-shadow-s`   | <ShadowBox shadow="--dwc-shadow-s" />   |
| `--dwc-shadow-m`   | <ShadowBox shadow="--dwc-shadow-m" />   |
| `--dwc-shadow-l`   | <ShadowBox shadow="--dwc-shadow-l" />   |
| `--dwc-shadow-xl`  | <ShadowBox shadow="--dwc-shadow-xl" />  |
| `--dwc-shadow-2xl` | <ShadowBox shadow="--dwc-shadow-2xl" /> |
| `--dwc-shadow`     | <ShadowBox shadow="--dwc-shadow" />     |
