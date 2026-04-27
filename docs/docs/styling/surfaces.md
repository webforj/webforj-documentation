---
sidebar_position: 8
title: Surfaces
---

DWC defines three levels of surfaces which are used to organize the UI hierarchy combined with [shadows](./shadows). All [palette colors](./colors) are tested to have enough contrast with these surfaces.

Surfaces pick up a subtle tint from the primary hue and adapt automatically to light and dark modes.

### Example {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variables {#variables}

| **Variable**      | **Usage**                          |
|-------------------|------------------------------------|
| `--dwc-surface-1` | Page and body background.         |
| `--dwc-surface-2` | Toolbars, menubars, cards.        |
| `--dwc-surface-3` | Windows, menus, popovers, dialogs.|

<dwc-doc-surfaces></dwc-doc-surfaces>
