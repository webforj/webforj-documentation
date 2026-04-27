---
sidebar_position: 9
title: State
_i18n_hash: 3dc9896bce3e0577b2407f8ae4c863d0
---
Les jetons d'état définissent comment les composants réagissent visuellement à l'interaction de l'utilisateur, par exemple lorsque ceux-ci sont désactivés ou en focus. Ces variables aident à assurer un comportement et un style cohérents à travers tous les éléments de l'interface utilisateur, et peuvent être facilement personnalisées pour correspondre à votre système de design.

## État désactivé {#disabled-state}
Les propriétés d'état désactivé sont utilisées pour faire apparaître un élément visuellement inactif et non interactif.

L'opacité s'adapte au thème actuel pour une visibilité optimale dans les modes clair et sombre.

### Exemple {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variables {#variables}

| **Variable**             | **Valeur par défaut**          | **Description** |
|--------------------------|----------------------------|-----------------|
| `--dwc-disabled-opacity` | S'adapte au mode clair/sombre  | Opacité réduite pour les éléments désactivés |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) | |

---

## État de focus {#focus-state}

Lorsqu'un composant reçoit le focus, un anneau de focus est affiché autour de lui pour indiquer son état actif. L'anneau de focus utilise un motif d'anneau avec un espace coloré à l'intérieur et un anneau extérieur coloré.

### Variables {#variables-1}

| **Variable**              | **Valeur par défaut** | **Description** |
|---------------------------|-------------------|-----------------|
| `--dwc-focus-ring-a`      | 0.75              | Opacité alpha de l'anneau de focus |
| `--dwc-focus-ring-width`  | 2px               | Épaisseur de l'anneau de focus |
| `--dwc-focus-ring-gap`    | 2px               | Espace entre le bord du composant et l'anneau |

Chaque palette de couleurs génère sa propre variable d'anneau de focus :

| Modèle de variable | Description |
|---|---|
| `--dwc-focus-ring-{name}` | Ombre de l'anneau de focus teintée avec la couleur de la palette. |

Où `{name}` est l'un des suivants : `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`. Voir [Thèmes de composants](./colors#theming-components-with-abstract-variables) pour plus de détails.

<dwc-doc-focus-rings></dwc-doc-focus-rings>

---

## Échelles {#scales}

Les transformations d'échelle sont utilisées pour les animations de retour d'expérience de pression/clic sur les éléments interactifs.

| **Variable**              | **Valeur par défaut** | **Description** |
|---------------------------|-------------------|-----------------|
| `--dwc-scale-press`       | 0.97              | Échelle de pression standard (réduction de 3%) |
| `--dwc-scale-press-deep`  | 0.93              | Échelle de pression profonde (réduction de 7%) |

<dwc-doc-scales></dwc-doc-scales>
