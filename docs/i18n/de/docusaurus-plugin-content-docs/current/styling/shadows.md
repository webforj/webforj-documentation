---
sidebar_position: 7
title: Shadows
_i18n_hash: 6f5378b91beaf0c663bdc9de9b67233a
---
Die Schatteneigenschaften werden verwendet, um Schatteneffekte um den Rahmen eines Elements hinzuzufügen. Sie können mehrere Effekte durch Kommas getrennt festlegen. In den meisten Fällen werden Schatten verwendet, um Elemente darzustellen, die in der Benutzeroberfläche übereinander liegen.

<Head>
  <style>{`
  table {
    width: 100%;
    display: table;
  }
  `}</style>
</Head>

### Beispiel {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

:::tip Schattenfarbe
Sie steuern die Schattenfarbe, indem Sie die Variable `--dwc-shadow-color` festlegen. Standardmäßig ist die Schattenfarbe grau, getönt mit der Primärfarbe.
:::

### Variablen {#variables}

| **Variable**       | **Beispiel**                             |
|--------------------|------------------------------------------|
| `--dwc-shadow-xs`  | <ShadowBox shadow="--dwc-shadow-xs" />  |
| `--dwc-shadow-s`   | <ShadowBox shadow="--dwc-shadow-s" />   |
| `--dwc-shadow-m`   | <ShadowBox shadow="--dwc-shadow-m" />   |
| `--dwc-shadow-l`   | <ShadowBox shadow="--dwc-shadow-l" />   |
| `--dwc-shadow-xl`  | <ShadowBox shadow="--dwc-shadow-xl" />  |
| `--dwc-shadow-2xl` | <ShadowBox shadow="--dwc-shadow-2xl" /> |
| `--dwc-shadow`     | <ShadowBox shadow="--dwc-shadow" />     |
