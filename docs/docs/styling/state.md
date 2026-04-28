---
sidebar_position: 9
title: State
---

State tokens define how components visually respond to user interaction, such as when they're disabled or focused. These variables help ensure consistent behavior and styling across all UI elements, and can be easily customized to match your design system.

<!-- vale off -->
## Disabled state {#disabled-state}
<!-- vale on -->
The disabled state properties are used to make an element appear visually inactive and non-interactive.

The opacity adapts to the current theme for optimal visibility in both light and dark modes.

### Example {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variables {#variables}

| **Variable**             | **Default Value**          | **Description** |
|--------------------------|----------------------------|-----------------|
| `--dwc-disabled-opacity` | Adapts to light/dark mode  | Reduced opacity for disabled elements |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) | |

---

## Focus state {#focus-state}

When a component receives focus, a focus ring is shown around it to indicate its active state. The focus ring uses a gap-ring pattern with a surface-colored inner gap and a colored outer ring.

### Variables {#variables-1}

| **Variable**              | **Default Value** | **Description** |
|---------------------------|-------------------|-----------------|
| `--dwc-focus-ring-a`      | 0.75              | Alpha opacity of the focus ring |
| `--dwc-focus-ring-width`  | 2px               | Thickness of the focus ring |
| `--dwc-focus-ring-gap`    | 2px               | Gap between the component edge and the ring |

Each color palette generates its own focus ring variable:

| Variable Pattern | Description |
|---|---|
| `--dwc-focus-ring-{name}` | Focus ring shadow tinted with the palette color. |

Where `{name}` is one of: `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`. See [Component Themes](./colors#theming-components-with-abstract-variables) for details.

<dwc-doc-focus-rings></dwc-doc-focus-rings>

---

## Scales {#scales}

Scale transforms are used for press/click feedback animations on interactive elements.

| **Variable**              | **Default Value** | **Description** |
|---------------------------|-------------------|-----------------|
| `--dwc-scale-press`       | 0.97              | Standard press scale (3% shrink) |
| `--dwc-scale-press-deep`  | 0.93              | Deep press scale (7% shrink) |

<dwc-doc-scales></dwc-doc-scales>
