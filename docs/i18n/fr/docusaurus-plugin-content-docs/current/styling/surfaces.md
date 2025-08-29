---
sidebar_position: 8
title: Surfaces
_i18n_hash: ac1f587cd1039f9bf083c610c29c27b9
---
Il existe trois niveaux de surfaces utilisés pour organiser la hiérarchie de l'interface utilisateur, souvent combinés avec [des ombres](./shadows). Toutes les [couleurs de la palette](./colors) sont testées pour fournir un contraste suffisant par rapport à ces surfaces.

### Exemple {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variables {#variables}

| **Variable**      | **Utilisation**                                                         | **Exemple**                               |
|-------------------|-------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1` | La surface la plus sombre. Utilisée pour l'arrière-plan du corps.      | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | Utilisée pour les composants (par exemple, les cartes).                 | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | La surface la plus claire et la plus élevée. Utilisée pour les menus, les popovers, les dialogues ... | <SurfaceBox surface="--dwc-surface-3" /> |
