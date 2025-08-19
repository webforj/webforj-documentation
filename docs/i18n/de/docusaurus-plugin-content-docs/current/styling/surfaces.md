---
sidebar_position: 8
title: Surfaces
_i18n_hash: 05e79ea41c1483cb30396be9bc096c4f
---
import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';

Es gibt drei Ebenen von Oberflächen, die verwendet werden, um die UI-Hierarchie zu organisieren, oft kombiniert mit [Schatten](./shadows). Alle [Farben der Palette](./colors) werden getestet, um einen ausreichenden Kontrast gegen diese Oberflächen zu bieten.

### Beispiel {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variablen {#variables}

| **Variable**      | **Verwendung**                                                          | **Beispiel**                               |
|-------------------|-------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1` | Die dunkelste Oberfläche. Wird für den Hintergrund des Körpers verwendet. | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | Wird für Komponenten (z. B. Karten) verwendet.                         | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | Die hellste und höchste Oberfläche. Wird für Menüs, Popovers, Dialoge ... | <SurfaceBox surface="--dwc-surface-3" /> |
