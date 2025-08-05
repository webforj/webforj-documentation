---
sidebar_position: 7
title: Shadows
_i18n_hash: 84ad4478632d3020d57752a4827f925a
---
De schaduw eigenschappen worden gebruikt om schaduweffecten rondom het frame van een element toe te voegen. Je kunt meerdere effecten instellen, gescheiden door komma's. In de meeste gevallen worden schaduwen gebruikt om items aan te duiden die bovenop elkaar zijn gestapeld in de gebruikersinterface.

<Head>
  <style>{`
  table {
    width: 100%;
    display: table;
  }
  `}</style>
</Head>

### Voorbeeld {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

:::tip Schaduwkleur
Je beheert de schaduwkleur door de variabele `--dwc-shadow-color` in te stellen. Standaard is de schaduwkleur grijs getint met de primaire kleur.
:::

### Variabelen {#variables}

| **Variabele**      | **Voorbeeld**                             |
|--------------------|------------------------------------------|
| `--dwc-shadow-xs`  | <ShadowBox shadow="--dwc-shadow-xs" />  |
| `--dwc-shadow-s`   | <ShadowBox shadow="--dwc-shadow-s" />   |
| `--dwc-shadow-m`   | <ShadowBox shadow="--dwc-shadow-m" />   |
| `--dwc-shadow-l`   | <ShadowBox shadow="--dwc-shadow-l" />   |
| `--dwc-shadow-xl`  | <ShadowBox shadow="--dwc-shadow-xl" />  |
| `--dwc-shadow-2xl` | <ShadowBox shadow="--dwc-shadow-2xl" /> |
| `--dwc-shadow`     | <ShadowBox shadow="--dwc-shadow" />     |
