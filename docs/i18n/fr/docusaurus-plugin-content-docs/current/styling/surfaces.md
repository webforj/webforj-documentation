---
sidebar_position: 8
title: Surfaces
_i18n_hash: 05e79ea41c1483cb30396be9bc096c4f
---
import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';

Il existe trois niveaux de surfaces utilisés pour organiser la hiérarchie de l'interface utilisateur, souvent combinés avec [ombres](./shadows). Toutes les [couleurs de palette](./colors) sont testées pour fournir un contraste suffisant par rapport à ces surfaces.

### Exemple {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variables {#variables}

| **Variable**      | **Utilisation**                                                          | **Exemple**                               |
|-------------------|-------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1` | La surface la plus sombre. Utilisée pour le fond du corps.              | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | Utilisée pour les composants (par exemple, les cartes).                  | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | La surface la plus claire et la plus élevée. Utilisée pour les menus, popovers, dialogues ... | <SurfaceBox surface="--dwc-surface-3" /> |
