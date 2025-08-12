---
sidebar_position: 5
title: Sizing and Spacing
_i18n_hash: 4efe9ef910459481ca90eec87c26ebe0
---
Spacing- en sizing-tokens worden gebruikt om consistente spacing en sizing in je app te bieden. Alle sizing- en spacing-eigenschappen zijn gedefinieerd in `rem`.

:::info REM Eenheid
`rem` is een relatieve lengte-eenheid. het is relatief ten opzichte van de lettergrootte van het [root element](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html).
:::

## Sizing {#sizing}

Gebruik deze eigenschappen om de sizing van de component aan te passen (breedte, hoogte). `m` is de standaardgrootte voor bijna alle componenten.

:::tip Een maat kiezen
Zorg ervoor dat de maat altijd groot genoeg is voor tapdoelen tijdens het kiezen van een maat.
:::

### Voorbeeld {#example}

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### Variabelen {#variables}

| **Variabele**     | **Standaardwaarde** | **Voorbeeld**                         |
| ------------------ | ------------------- | ------------------------------------- |
| `--dwc-size-3xs`   | 1.125rem            | <SizingBox size="--dwc-size-3xs" /> |
| `--dwc-size-2xs`   | 1.375rem            | <SizingBox size="--dwc-size-2xs" /> |
| `--dwc-size-xs`    | 1.625rem            | <SizingBox size="--dwc-size-xs" />  |
| `--dwc-size-s`     | 1.875rem            | <SizingBox size="--dwc-size-s" />   |
| `--dwc-size-m`     | 2.25rem             | <SizingBox size="--dwc-size-m" />   |
| `--dwc-size-l`     | 2.75rem             | <SizingBox size="--dwc-size-l" />   |
| `--dwc-size-xl`    | 3.5rem              | <SizingBox size="--dwc-size-xl" />  |
| `--dwc-size-2xl`   | 4rem                | <SizingBox size="--dwc-size-2xl" /> |
| `--dwc-size-3xl`   | 4.25rem             | <SizingBox size="--dwc-size-3xl" /> |
| `--dwc-size`       | var(--dwc-size-m)   | <SizingBox size="--dwc-size" />     |

## Spacing {#spacing}

Gebruik deze eigenschappen om de inter-component spacing (margin, padding) aan te passen.

### Voorbeeld {#example-1}

```css
.element {
  padding: var(--dwc-space-m);
}
```

### Variabelen {#variables-1}

| **Variabele**      | **Standaardwaarde** | **Voorbeeld**                            |
| ------------------- | ------------------- | ---------------------------------------- |
| `--dwc-space-3xs`   | 0.075rem            | <SpacingBox space="--dwc-space-3xs" /> |
| `--dwc-space-2xs`   | 0.15rem             | <SpacingBox space="--dwc-space-2xs" /> |
| `--dwc-space-xs`    | 0.25rem             | <SpacingBox space="--dwc-space-xs" />  |
| `--dwc-space-s`     | 0.5rem              | <SpacingBox space="--dwc-space-s" />   |
| `--dwc-space-m`     | 1rem                | <SpacingBox space="--dwc-space-m" />   |
| `--dwc-space-l`     | 1.25rem             | <SpacingBox space="--dwc-space-l" />   |
| `--dwc-space-xl`    | 1.5rem              | <SpacingBox space="--dwc-space-xl" />  |
| `--dwc-space-2xl`   | 1.75rem             | <SpacingBox space="--dwc-space-2xl" /> |
| `--dwc-space-3xl`   | 2rem                | <SpacingBox space="--dwc-space-3xl" /> |
| `--dwc-space`       | var(--dwc-space-s)  | <SpacingBox space="--dwc-space" />     |
