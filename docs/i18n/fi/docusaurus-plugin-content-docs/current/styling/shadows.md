---
sidebar_position: 7
title: Shadows
_i18n_hash: 6f5378b91beaf0c663bdc9de9b67233a
---
Varjostusominaisuuksia käytetään varjostusvaikutusten lisäämiseen elementin reunoille. Voit asettaa useita vaikutuksia erottamalla ne pilkuilla. Useimmissa tapauksissa varjoja käytetään merkkaamaan elementtejä, jotka ovat päällekkäin käyttäjäliittymässä.

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
Ohjaat varjon väriä asettamalla `--dwc-shadow-color` -muuttujan. Oletusarvoisesti varjon väri on harmaa, johon on sekoitettu pääväri.
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
