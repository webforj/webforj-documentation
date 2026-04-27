---
sidebar_position: 5
title: Sizing and Spacing
---

Spacing and sizing tokens are used to provide consistent spacing and sizing in your app. All sizing and spacing properties are defined in `rem`.

:::info REM Unit
`rem` is a relative length unit. it's relative to the font size of the [root element](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html).
:::

## Sizing {#sizing}

Use these properties to adjust the sizing of the component (width, height). `m` is the standard size for almost all components.

:::tip Choosing a size
When choosing a size, always make sure to keep it large enough for tap targets.
:::

### Example {#example}

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### Variables {#variables}

| **Variable**     | **Default Value** | **Computed (at 16px root)** |
| ---------------- | ----------------- | --------------------------- |
| `--dwc-size-3xs` | 1.125rem          | 18px |
| `--dwc-size-2xs` | 1.375rem          | 22px |
| `--dwc-size-xs`  | 1.625rem          | 26px |
| `--dwc-size-s`   | 1.875rem          | 30px |
| `--dwc-size-m`   | 2.25rem           | 36px |
| `--dwc-size-l`   | 2.75rem           | 44px |
| `--dwc-size-xl`  | 3.25rem           | 52px |
| `--dwc-size-2xl` | 4rem              | 64px |
| `--dwc-size-3xl` | 4.25rem           | 68px |
| `--dwc-size`     | var(--dwc-size-m) | 36px |

<dwc-doc-sizes></dwc-doc-sizes>

## Spacing {#spacing}

Use these properties to adjust the inter-component spacing (margin, padding).

### Example {#example-1}

```css
.element {
  padding: var(--dwc-space-m);
}
```

### Variables {#variables-1}

| **Variable**      | **Default Value**  | **Computed (at 16px root)** |
| ----------------- | ------------------ | --------------------------- |
| `--dwc-space-3xs` | 0.0625rem          | 1px |
| `--dwc-space-2xs` | 0.125rem           | 2px |
| `--dwc-space-xs`  | 0.25rem            | 4px |
| `--dwc-space-s`   | 0.5rem             | 8px |
| `--dwc-space-m`   | 1rem               | 16px |
| `--dwc-space-l`   | 1.25rem            | 20px |
| `--dwc-space-xl`  | 1.5rem             | 24px |
| `--dwc-space-2xl` | 1.75rem            | 28px |
| `--dwc-space-3xl` | 2rem               | 32px |
| `--dwc-space`     | var(--dwc-space-s) | 8px |

<dwc-doc-spaces></dwc-doc-spaces>
