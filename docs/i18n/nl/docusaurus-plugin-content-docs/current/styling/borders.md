---
sidebar_position: 6
title: Border
_i18n_hash: aec4d011f38db8c5a7a6c324eb76d724
---
De rand-eigenschappen worden gebruikt om de randstijl en -breedte van de component te beheersen. Zie [beschikbare randstijlen](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Voorbeeld {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
}
```

### Variabelen {#variables}

| **Variabele**              | **Standaardwaarde** |
|---------------------------|-------------------|
| `--dwc-border-width`      | 1px               |
| `--dwc-border-style`      | solid             |
| `--dwc-border-color`      | var(--dwc-border-color-default) |
| `--dwc-border-color-emphasis` | var(--dwc-border-color-default-emphasis) |

### Per-palette randkleuren {#per-palette-border-colors}

Elke kleurpalet genereert zijn eigen randkleurvariabelen:

| Variabelepatroon | Beschrijving |
|---|---|
| `--dwc-border-color-{name}` | Mode-bewuste randkleur getint met de paletkleur. |
| `--dwc-border-color-{name}-emphasis` | Sterkere variant voor hover-, focus- en actieve toestanden. |

Waar `{name}` een van de volgende is: `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`.

## Randstraal {#border-radius}

Randstraalvariabelen definiëren hoe afgerond de hoeken van een component zijn. Alle maten schalen vanaf een enkele zaadwaarde (`--dwc-border-radius-seed`). Het wijzigen van het zaad herschaalt het hele radius systeem proportioneel.

### Voorbeeld {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Variabelen {#variables-1}

| **Variabele**                | **Standaardwaarde**                  | **Berekend (bij seed=8px)** |
|-----------------------------|------------------------------------|-----------------------------|
| `--dwc-border-radius-seed`  | 0.5rem                             | 8px                        |
| `--dwc-border-radius-2xs`   | 0.0625rem                          | 1px (vast)                |
| `--dwc-border-radius-xs`    | 0.125rem                           | 2px (vast)                |
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

### Gebruiksrichtlijnen {#usage-guidelines}

- Items binnen containers: gebruik `s` (0.5x zaad)
- Structurele randen (tussen item en container): gebruik `m` (0.75x zaad)
- Containers en oppervlakken: gebruik `l` (1x zaad)
- Grote overlays: gebruik `xl` (1.5x zaad)
