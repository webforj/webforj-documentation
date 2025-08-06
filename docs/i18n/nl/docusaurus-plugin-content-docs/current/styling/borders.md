---
sidebar_position: 6
title: Border
_i18n_hash: fe0a0386da63ff7ea085db8daa12d0fe
---
De rand eigenschappen worden gebruikt om de randstijl en -breedte van de component te regelen. Zie [beschikbare randstijlen](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Voorbeeld {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) red;
}
```

### Variabelen {#variables}

| **Variabele**            | **Standaardwaarde** |
|--------------------------|---------------------|
| `--dwc-border-width`     | 1px                 |
| `--dwc-border-style`     | solid               |

## Randradius {#border-radius}

Randradius variabelen definiëren hoe rond de hoeken van een component zijn. Alle waarden zijn gedefinieerd in `em`, zodat ze schaale met de lettergrootte.

:::info EM Eenheid
`em` is een relatieve eenheid die schaalt met de [lettergrootte](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) van de ouder.
:::

### Voorbeeld {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Variabelen {#variables-1}

| **Variabele**                       | **Standaardwaarde**             | **Voorbeeld**                                            |
|-------------------------------------|---------------------------------|---------------------------------------------------------|
| `--dwc-border-radius-2xs`          | 0.071em                         | <RadiusBox radius="--dwc-border-radius-2xs" />        |
| `--dwc-border-radius-xs`           | 0.125em                         | <RadiusBox radius="--dwc-border-radius-xs" />         |
| `--dwc-border-radius-s`            | 0.25em                          | <RadiusBox radius="--dwc-border-radius-s" />          |
| `--dwc-border-radius-m`            | 0.375em                         | <RadiusBox radius="--dwc-border-radius-m" />          |
| `--dwc-border-radius-l`            | 0.5em                           | <RadiusBox radius="--dwc-border-radius-l" />          |
| `--dwc-border-radius-xl`           | 0.75em                          | <RadiusBox radius="--dwc-border-radius-xl" />         |
| `--dwc-border-radius-2xl`          | 1em                             | <RadiusBox radius="--dwc-border-radius-2xl" />        |
| `--dwc-border-radius-round`        | 50%                             | <RadiusBox radius="--dwc-border-radius-round" />      |
| `--dwc-border-radius-pill`         | 9999px                          | <RadiusBox radius="--dwc-border-radius-pill" />       |
| `--dwc-border-radius`              | var(--dwc-border-radius-s)     | <RadiusBox radius="--dwc-border-radius" />            |
