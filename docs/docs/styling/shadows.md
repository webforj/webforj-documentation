---
sidebar_position: 7
title: Shadows
---

The shadow properties add shadow effects around an element's frame. Shadows signify items that are layered on top of each other in the user interface.

Shadows adapt automatically to both light and dark modes, appearing stronger in dark mode for better visibility.

### Example {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

### Variables {#variables}

| **Variable**       | **Description**                      |
|--------------------|---------------------------------------|
| `--dwc-shadow-xs`  | Extra small shadow (1 layer)         |
| `--dwc-shadow-s`   | Small shadow (2 layers)              |
| `--dwc-shadow-m`   | Medium shadow (3 layers, default)    |
| `--dwc-shadow-l`   | Large shadow (4 layers)              |
| `--dwc-shadow-xl`  | Extra large shadow (5 layers)        |
| `--dwc-shadow-2xl` | Double extra large shadow (6 layers) |
| `--dwc-shadow`     | `var(--dwc-shadow-m)`                |

<dwc-doc-shadows></dwc-doc-shadows>
