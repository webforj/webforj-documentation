---
sidebar_position: 6
title: Border
_i18n_hash: aec4d011f38db8c5a7a6c324eb76d724
---
Die Rand-Eigenschaften werden verwendet, um den Randstil und die Breite der Komponente zu steuern. Siehe [verfügbare Randstile](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Beispiel {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
}
```

### Variablen {#variables}

| **Variable**              | **Standardwert** |
|---------------------------|-------------------|
| `--dwc-border-width`      | 1px               |
| `--dwc-border-style`      | solid             |
| `--dwc-border-color`      | var(--dwc-border-color-default) |
| `--dwc-border-color-emphasis` | var(--dwc-border-color-default-emphasis) |

### Randfarben pro Palette {#per-palette-border-colors}

Jede Farbpalette erzeugt ihre eigenen Randfarben-Variablen:

| Variablenmuster | Beschreibung |
|---|---|
| `--dwc-border-color-{name}` | Modusbewusste Randfarbe, die mit dem Farbton der Palette getönt ist. |
| `--dwc-border-color-{name}-emphasis` | Stärkerer Farbton für Hover-, Fokus- und aktive Zustände. |

Wobei `{name}` eines von: `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default` ist.

## Randradius {#border-radius}

Randradius-Variablen definieren, wie abgerundet die Ecken einer Komponente sind. Alle Größen skalieren von einem einzelnen Basiswert (`--dwc-border-radius-seed`). Das Ändern des Basiswerts skaliert das gesamte Radius-System proportional neu.

### Beispiel {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Variablen {#variables-1}

| **Variable**                | **Standardwert**                  | **Berechnet (bei seed=8px)** |
|-----------------------------|------------------------------------|-----------------------------|
| `--dwc-border-radius-seed`  | 0.5rem                             | 8px                        |
| `--dwc-border-radius-2xs`   | 0.0625rem                          | 1px (fest)                |
| `--dwc-border-radius-xs`    | 0.125rem                           | 2px (fest)                |
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

### Nutzungshinweise {#usage-guidelines}

- Elemente innerhalb von Containern: verwenden Sie `s` (0.5x Seed)
- Struktur-Ränder (zwischen Element und Container): verwenden Sie `m` (0.75x Seed)
- Container und Oberflächen: verwenden Sie `l` (1x Seed)
- Große Überlagerungen: verwenden Sie `xl` (1.5x Seed)
