---
sidebar_position: 6
title: Border
_i18n_hash: aec4d011f38db8c5a7a6c324eb76d724
---
Les propriétés de bordure sont utilisées pour contrôler le style et la largeur de la bordure du composant. Voir [les styles de bordure disponibles](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Exemple {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
}
```

### Variables {#variables}

| **Variable**              | **Valeur par défaut** |
|---------------------------|-------------------|
| `--dwc-border-width`      | 1px               |
| `--dwc-border-style`      | solide            |
| `--dwc-border-color`      | var(--dwc-border-color-default) |
| `--dwc-border-color-emphasis` | var(--dwc-border-color-default-emphasis) |

### Couleurs de bordure par palette {#per-palette-border-colors}

Chaque palette de couleurs génère ses propres variables de couleur de bordure :

| Modèle de variable | Description |
|---|---|
| `--dwc-border-color-{name}` | Couleur de bordure consciente du mode teintée avec la teinte de la palette. |
| `--dwc-border-color-{name}-emphasis` | Variante plus forte pour les états survol, focus et actif. |

Où `{name}` est un des suivants : `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`.

## Rayon de bordure {#border-radius}

Les variables de rayon de bordure définissent la rondeur des coins d'un composant. Toutes les tailles sont basées sur une seule valeur de départ (`--dwc-border-radius-seed`). Changer la valeur de départ redimensionne proportionnellement l'ensemble du système de rayons.

### Exemple {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Variables {#variables-1}

| **Variable**                | **Valeur par défaut**                  | **Calculée (à seed=8px)** |
|-----------------------------|------------------------------------|-----------------------------|
| `--dwc-border-radius-seed`  | 0.5rem                             | 8px                        |
| `--dwc-border-radius-2xs`   | 0.0625rem                          | 1px (fixe)                |
| `--dwc-border-radius-xs`    | 0.125rem                           | 2px (fixe)                |
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

### Directives d'utilisation {#usage-guidelines}

- Éléments à l'intérieur des conteneurs : utilisez `s` (0.5x seed)
- Bordures structurelles (entre l'élément et le conteneur) : utilisez `m` (0.75x seed)
- Conteneurs et surfaces : utilisez `l` (1x seed)
- Grandes superpositions : utilisez `xl` (1.5x seed)
