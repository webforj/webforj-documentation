---
sidebar_position: 9
title: State
---

State tokens define how components visually respond to user interaction—such as when they’re disabled or focused. These variables help ensure consistent behavior and styling across all UI elements, and can be easily customized to match your design system.

<!-- vale off -->
## Disabled state {#disabled-state}
<!-- vale on -->
The disabled state properties are used to make an element appear visually inactive and non-interactive.

### Example {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variables {#variables}

| **Variable**             | **Default Value**          |
|--------------------------|----------------------------|
| `--dwc-disabled-opacity` | 0.7                        |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) |

---

## Focus state {#focus-state}

When an component receives focus, a focus ring will be shown around it to indicate its active state. You can customize the ring's appearance using the variables below. These variables are used in conjunction with the component theme focus ring settings.

### Variables {#variables-1}

| **Variable**              | **Default Value** |
|---------------------------|-------------------|
| `--dwc-focus-ring-l`      | 45%               |
| `--dwc-focus-ring-a`      | 0.4               |
| `--dwc-focus-ring-width`  | 3px               |