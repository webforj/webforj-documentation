---
sidebar_position: 8
title: Surfaces
---

import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';

There are three levels of surfaces used to organize the UI hierarchy, often combined with [shadows](./shadows). All [palette colors](./colors) are tested to provide sufficient contrast against these surfaces.

### Example

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variables

| **Variable**      | **Usage**                                                               | **Example**                               |
|-------------------|-------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1` | The darkest surface. Used for body background.                         | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | Used for components (e.g. cards).                                       | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | The lightest and highest surface. Used for menus, popovers, dialogs ... | <SurfaceBox surface="--dwc-surface-3" /> |

<GiscusComments />