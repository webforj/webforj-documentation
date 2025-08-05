---
sidebar_position: 8
title: Surfaces
_i18n_hash: d26674c84c900aea4d63dad4dca61446
---
import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';

Es gibt drei Ebenen von Oberflächen, die zur Organisation der UI-Hierarchie verwendet werden, oft kombiniert mit [Schatten](./shadows). Alle [Farben der Palette](./colors) wurden getestet, um einen ausreichenden Kontrast zu diesen Oberflächen zu gewährleisten.

### Beispiel {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variablen {#variables}

| **Variable**      | **Verwendung**                                                          | **Beispiel**                             |
|-------------------|-------------------------------------------------------------------------|------------------------------------------|
| `--dwc-surface-1` | Die dunkelste Oberfläche. Wird für den Hintergrund des Körpers verwendet. | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | Wird für Komponenten (z. B. Karten) verwendet.                        | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | Die hellste und höchste Oberfläche. Wird für Menüs, Popovers, Dialoge ... | <SurfaceBox surface="--dwc-surface-3" /> |
