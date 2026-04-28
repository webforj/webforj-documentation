---
sidebar_position: 4
title: Typography
_i18n_hash: 7c5f036abf897a890cad14af0a64c6bd
---
Les tokens typographiques sont utilisés pour maintenir un ensemble cohérent de styles de police dans toute votre application.

## Famille de polices {#font-family}

Les propriétés de famille de polices sont utilisées pour spécifier une liste de noms de famille de polices priorisée.

La pile de polices système est utilisée par défaut via `system-ui`, qui se résout automatiquement à la police native de la plateforme :

- `San Francisco` sur macOS et iOS
- `Segoe UI` sur Windows
- `Roboto` sur Android et Chrome OS

Vous pouvez appliquer ou changer la famille de polices en utilisant la propriété personnalisée `--dwc-font-family`.

### Exemple {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variables {#variables}

| **Variable**               | **Valeur par défaut** |
| -------------------------- | --------------------- |
| `--dwc-font-family-sans`   | system-ui, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji' |
| `--dwc-font-family-mono`   | ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace |
| `--dwc-font-family`        | `var(--dwc-font-family-sans)` |

## Taille de police {#font-size}

Les propriétés de taille de police définissent un ensemble de tailles de police parmi lesquelles choisir. `m` est la taille standard et est utilisée par la plupart des composants par défaut. Toutes les tailles de police sont définies en `rem`.

:::info Unité REM
`rem` est une unité de longueur relative. Elle est relative à la [taille de police](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) de l'élément racine (`<html>`), qui par défaut est de 16px dans la plupart des navigateurs.
:::

### Exemple {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variables {#variables-1}

| **Variable**              | **Valeur par défaut**     | **Calculée (à 16px racine)** |
| ------------------------- | ------------------------- | ------------------------------ |
| `--dwc-font-size-3xs`    | 0.625rem                  | 10px |
| `--dwc-font-size-2xs`    | 0.6875rem                 | 11px |
| `--dwc-font-size-xs`     | 0.75rem                   | 12px |
| `--dwc-font-size-s`      | 0.8125rem                 | 13px |
| `--dwc-font-size-m`      | 0.875rem                  | 14px |
| `--dwc-font-size-l`      | 1rem                      | 16px |
| `--dwc-font-size-xl`     | 1.25rem                   | 20px |
| `--dwc-font-size-2xl`    | 1.625rem                  | 26px |
| `--dwc-font-size-3xl`    | 2.125rem                  | 34px |
| `--dwc-font-size`        | `var(--dwc-font-size-m)`  | 14px |

<dwc-doc-font-sizes></dwc-doc-font-sizes>

## Poids de la police {#font-weight}

La propriété CSS [font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) définit le poids (ou la graisse) de la police.

### Exemple {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variable**               | **Valeur par défaut** |
| -------------------------- | --------------------- |
| `--dwc-font-weight-thin`    | 100 |
| `--dwc-font-weight-lighter` | 200 |
| `--dwc-font-weight-light`   | 300 |
| `--dwc-font-weight-normal`  | 400 |
| `--dwc-font-weight-medium`  | 500 |
| `--dwc-font-weight-semibold`| 600 |
| `--dwc-font-weight-bold`    | 700 |
| `--dwc-font-weight-bolder`  | 800 |
| `--dwc-font-weight-black`   | 900 |
| `--dwc-font-weight`         | `var(--dwc-font-weight-normal)` |

<dwc-doc-font-weights></dwc-doc-font-weights>

## Hauteur de ligne {#line-height}

La propriété CSS de hauteur de ligne définit la hauteur d'une boîte de ligne. Elle est couramment utilisée pour définir la distance entre les lignes de texte.

### Exemple {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variables {#variables-2}

| **Variable**                  | **Valeur par défaut**        |
| ----------------------------- | ----------------------------- |
| `--dwc-font-line-height-3xs`  | 1 |
| `--dwc-font-line-height-2xs`  | 1.1 |
| `--dwc-font-line-height-xs`   | 1.25 |
| `--dwc-font-line-height-s`    | 1.375 |
| `--dwc-font-line-height-m`    | 1.5 |
| `--dwc-font-line-height-l`    | 1.625 |
| `--dwc-font-line-height-xl`   | 1.75 |
| `--dwc-font-line-height-2xl`  | 1.875 |
| `--dwc-font-line-height-3xl`  | 2 |
| `--dwc-font-line-height`      | var(--dwc-font-line-height-xs) |

<dwc-doc-line-heights></dwc-doc-line-heights>
