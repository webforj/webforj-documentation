---
sidebar_position: 7
title: Shadows
_i18n_hash: 6f5378b91beaf0c663bdc9de9b67233a
---
De schaduw eigenschappen worden gebruikt om schaduweffecten rond het frame van een element toe te voegen. Je kunt meerdere effecten instellen, gescheiden door komma's. In de meeste gevallen worden schaduwen gebruikt om items aan te duiden die bovenop elkaar zijn geplaatst in de gebruikersinterface.

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
Je controleert de schaduwkleur door de variabele `--dwc-shadow-color` in te stellen. Standaard is de schaduwkleur grijs getint met de primaire kleur.
:::

### Variabelen {#variables}

| **Variabele**       | **Voorbeeld**                             |
|--------------------|------------------------------------------|
| `--dwc-shadow-xs`  | <ShadowBox shadow="--dwc-shadow-xs" />  |
| `--dwc-shadow-s`   | <ShadowBox shadow="--dwc-shadow-s" />   |
| `--dwc-shadow-m`   | <ShadowBox shadow="--dwc-shadow-m" />   |
| `--dwc-shadow-l`   | <ShadowBox shadow="--dwc-shadow-l" />   |
| `--dwc-shadow-xl`  | <ShadowBox shadow="--dwc-shadow-xl" />  |
| `--dwc-shadow-2xl` | <ShadowBox shadow="--dwc-shadow-2xl" /> |
| `--dwc-shadow`     | <ShadowBox shadow="--dwc-shadow" />     |
