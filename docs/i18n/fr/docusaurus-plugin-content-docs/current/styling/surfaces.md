---
sidebar_position: 8
title: Surfaces
_i18n_hash: cac300e6e9c10cd9d1da6b266e536c74
---
DWC définit trois niveaux de surfaces qui sont utilisés pour organiser la hiérarchie de l'interface utilisateur combinée avec [shadows](./shadows). Toutes les [palette colors](./colors) sont testées pour avoir un contraste suffisant avec ces surfaces.

Les surfaces prennent une teinte subtile de la couleur primaire et s'adaptent automatiquement aux modes clair et sombre.

### Exemple {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variables {#variables}

| **Variable**      | **Usage**                          |
|-------------------|------------------------------------|
| `--dwc-surface-1` | Arrière-plan de la page et du corps.         |
| `--dwc-surface-2` | Barres d'outils, barres de menus, cartes.        |
| `--dwc-surface-3` | Fenêtres, menus, popovers, dialogues.|

<dwc-doc-surfaces></dwc-doc-surfaces>
