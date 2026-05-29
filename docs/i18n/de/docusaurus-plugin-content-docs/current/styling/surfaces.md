---
sidebar_position: 8
title: Surfaces
_i18n_hash: cac300e6e9c10cd9d1da6b266e536c74
---
DWC definiert drei Ebenen von Flächen, die verwendet werden, um die UI-Hierarchie zu organisieren, kombiniert mit [shadows](./shadows). Alle [palette colors](./colors) sind getestet, um sicherzustellen, dass sie ausreichend Kontrast mit diesen Flächen bieten.

Flächen nehmen einen subtilen Farbton aus dem primären Farbton auf und passen sich automatisch an helle und dunkle Modi an.

### Beispiel {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variablen {#variables}

| **Variable**      | **Verwendung**                          |
|-------------------|----------------------------------------|
| `--dwc-surface-1` | Hintergrund von Seite und Body.       |
| `--dwc-surface-2` | Toolbars, Menübars, Karten.           |
| `--dwc-surface-3` | Fenster, Menüs, Popovers, Dialoge.    |

<dwc-doc-surfaces></dwc-doc-surfaces>
