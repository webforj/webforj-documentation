---
sidebar_position: 6
title: Border
---

The border properties are used to control the component border style and width. See [available border styles](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Example {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
}
```

### Variables {#variables}

| **Variable**              | **Default Value** |
|---------------------------|-------------------|
| `--dwc-border-width`      | 1px               |
| `--dwc-border-style`      | solid             |
| `--dwc-border-color`      | var(--dwc-border-color-default) |
| `--dwc-border-color-emphasis` | var(--dwc-border-color-default-emphasis) |

### Per-palette border colors {#per-palette-border-colors}

Each color palette generates its own border color variables:

| Variable Pattern | Description |
|---|---|
| `--dwc-border-color-{name}` | Mode-aware border color tinted with the palette hue. |
| `--dwc-border-color-{name}-emphasis` | Stronger variant for hover, focus, and active states. |

Where `{name}` is one of: `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`.

## Border radius {#border-radius}

Border radius variables define how rounded the corners of a component are. All sizes scale from a single seed value (`--dwc-border-radius-seed`). Changing the seed rescales the entire radius system proportionally.

### Example {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Variables {#variables-1}

| **Variable**                | **Default Value**                  | **Computed (at seed=8px)** |
|-----------------------------|------------------------------------|-----------------------------|
| `--dwc-border-radius-seed`  | 0.5rem                             | 8px                        |
| `--dwc-border-radius-2xs`   | 0.0625rem                          | 1px (fixed)                |
| `--dwc-border-radius-xs`    | 0.125rem                           | 2px (fixed)                |
| `--dwc-border-radius-s`     | calc(seed * 0.5)                   | 4px                        |
| `--dwc-border-radius-m`     | calc(seed * 0.75)                  | 6px                        |
| `--dwc-border-radius-l`     | var(--dwc-border-radius-seed)      | 8px                        |
| `--dwc-border-radius-xl`    | calc(seed * 1.5)                   | 12px                       |
| `--dwc-border-radius-2xl`   | calc(seed * 2)                     | 16px                       |
| `--dwc-border-radius-3xl`   | calc(seed * 3)                     | 24px                       |
| `--dwc-border-radius-4xl`   | calc(seed * 4)                     | 32px                       |
| `--dwc-border-radius-round` | 50%                                |                            |
| `--dwc-border-radius-pill`  | calc(var(--dwc-size-m) / 2)        |                            |
| `--dwc-border-radius`       | var(--dwc-border-radius-seed)      | 8px                        |

<dwc-doc-radii></dwc-doc-radii>

### Usage guidelines {#usage-guidelines}

- Items inside containers: use `s` (0.5x seed)
- Structural borders (between item and container): use `m` (0.75x seed)
- Containers and surfaces: use `l` (1x seed)
- Large overlays: use `xl` (1.5x seed)
