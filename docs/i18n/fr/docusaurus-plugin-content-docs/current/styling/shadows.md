---
sidebar_position: 7
title: Shadows
_i18n_hash: 423494230ee54caa83fec778e905871b
---
Les propriétés d'ombre ajoutent des effets d'ombre autour du cadre d'un élément. Les ombres signifient des éléments qui sont superposés les uns aux autres dans l'interface utilisateur.

Les ombres s'adaptent automatiquement aux modes clair et sombre, apparaissant plus fortes en mode sombre pour une meilleure visibilité.

### Exemple {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

### Variables {#variables}

| **Variable**       | **Description**                      |
|--------------------|---------------------------------------|
| `--dwc-shadow-xs`  | Ombre extra petite (1 couche)        |
| `--dwc-shadow-s`   | Ombre petite (2 couches)             |
| `--dwc-shadow-m`   | Ombre moyenne (3 couches, par défaut)|
| `--dwc-shadow-l`   | Ombre grande (4 couches)             |
| `--dwc-shadow-xl`  | Ombre extra grande (5 couches)       |
| `--dwc-shadow-2xl` | Ombre double extra grande (6 couches)|
| `--dwc-shadow`     | `var(--dwc-shadow-m)`                |

<dwc-doc-shadows></dwc-doc-shadows>
