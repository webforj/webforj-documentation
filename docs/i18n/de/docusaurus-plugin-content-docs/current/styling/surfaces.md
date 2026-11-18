---
sidebar_position: 8
title: Oberflächen
description: >-
  Gestalten Sie eine UI-Hierarchie mit drei modusabhängigen
  DWC-Oberflächentokens für Seitenhintergründe, Karten sowie Popovers oder
  Dialoge.
_i18n_title_hash: d40d1008e1abc156a84c98148422b0b9
_i18n_description_hash: 35f5960e176694545a805c578abc82b8
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
