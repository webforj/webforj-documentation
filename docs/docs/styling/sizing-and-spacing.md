---
sidebar_position: 5
title: Sizing and Spacing
---

import SizingBox from '@site/src/components/DWCTheme/SizingBox/SizingBox';
import SpacingBox from '@site/src/components/DWCTheme/SpacingBox/SpacingBox';

Spacing and sizing tokens are used to provide consistent spacing and sizing in your app. All sizing and spacing properties are defined in `rem`.

:::info REM Unit
`rem` is a relative length unit. it's relative to the font size of the [root element](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html).
:::

## Sizing

Use these properties to adjust the sizing of the component (width, height). `m` is the standard size for almost all components.

:::tip Choosing a size
When choosing a size, always make sure to keep it large enough for tap targets.
:::

### Example

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### Variables

| **Variable**     | **Default Value** | **Example**                         |
| ---------------- | ----------------- | ----------------------------------- |
| `--dwc-size-3xs` | 1.125rem          | <SizingBox size="--dwc-size-3xs" /> |
| `--dwc-size-2xs` | 1.375rem          | <SizingBox size="--dwc-size-2xs" /> |
| `--dwc-size-xs`  | 1.625rem          | <SizingBox size="--dwc-size-xs" />  |
| `--dwc-size-s`   | 1.875rem          | <SizingBox size="--dwc-size-s" />   |
| `--dwc-size-m`   | 2.25rem           | <SizingBox size="--dwc-size-m" />   |
| `--dwc-size-l`   | 2.75rem           | <SizingBox size="--dwc-size-l" />   |
| `--dwc-size-xl`  | 3.5rem            | <SizingBox size="--dwc-size-xl" />  |
| `--dwc-size-2xl` | 4rem              | <SizingBox size="--dwc-size-2xl" /> |
| `--dwc-size-3xl` | 4.25rem           | <SizingBox size="--dwc-size-3xl" /> |
| `--dwc-size`     | var(--dwc-size-m) | <SizingBox size="--dwc-size" />     |

## Spacing

Use these properties to adjust the inter-component spacing (margin, padding).

### Example

```css
.element {
  padding: var(--dwc-space-m);
}
```

### Variables

| **Variable**      | **Default Value**  | **Example**                            |
| ----------------- | ------------------ | -------------------------------------- |
| `--dwc-space-3xs` | 0.075rem           | <SpacingBox space="--dwc-space-3xs" /> |
| `--dwc-space-2xs` | 0.15rem            | <SpacingBox space="--dwc-space-2xs" /> |
| `--dwc-space-xs`  | 0.25rem            | <SpacingBox space="--dwc-space-xs" />  |
| `--dwc-space-s`   | 0.5rem             | <SpacingBox space="--dwc-space-s" />   |
| `--dwc-space-m`   | 1rem               | <SpacingBox space="--dwc-space-m" />   |
| `--dwc-space-l`   | 1.25rem            | <SpacingBox space="--dwc-space-l" />   |
| `--dwc-space-xl`  | 1.5rem             | <SpacingBox space="--dwc-space-xl" />  |
| `--dwc-space-2xl` | 1.75rem            | <SpacingBox space="--dwc-space-2xl" /> |
| `--dwc-space-3xl` | 2rem               | <SpacingBox space="--dwc-space-3xl" /> |
| `--dwc-space`     | var(--dwc-space-s) | <SpacingBox space="--dwc-space" />     |
