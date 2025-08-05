---
sidebar_position: 9
title: State
_i18n_hash: b7227f8d2022e7e6eab96de3f802aa20
---
Les tokens d'état définissent comment les composants réagissent visuellement à l'interaction de l'utilisateur, par exemple lorsqu'ils sont désactivés ou en focus. Ces variables aident à garantir un comportement et un style cohérents à travers tous les éléments de l'interface utilisateur, et peuvent être facilement personnalisées pour correspondre à votre système de design.

## État désactivé {#disabled-state}
Les propriétés de l'état désactivé sont utilisées pour faire apparaître un élément visuellement inactif et non interactif.

### Exemple {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variables {#variables}

| **Variable**             | **Valeur par défaut**      |
|--------------------------|----------------------------|
| `--dwc-disabled-opacity` | 0.7                        |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) |

---

## État de focus {#focus-state}

Lorsqu'un composant reçoit le focus, un anneau de focus sera affiché autour de celui-ci pour indiquer son état actif. Vous pouvez personnaliser l'apparence de l'anneau à l'aide des variables ci-dessous. Ces variables sont utilisées en conjonction avec les paramètres de l'anneau de focus du thème du composant.

### Variables {#variables-1}

| **Variable**              | **Valeur par défaut** |
|---------------------------|-----------------------|
| `--dwc-focus-ring-l`      | 45%                   |
| `--dwc-focus-ring-a`      | 0.4                   |
| `--dwc-focus-ring-width`  | 3px                   |
