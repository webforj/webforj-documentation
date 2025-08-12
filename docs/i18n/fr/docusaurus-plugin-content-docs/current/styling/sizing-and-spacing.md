---
sidebar_position: 5
title: Sizing and Spacing
_i18n_hash: 4efe9ef910459481ca90eec87c26ebe0
---
Les tokens de taille et d'espacement sont utilisés pour fournir un espacement et une taille cohérents dans votre application. Toutes les propriétés de taille et d'espacement sont définies en `rem`.

:::info Unité REM
`rem` est une unité de longueur relative. elle est relative à la taille de police de l'[élément racine](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html).
:::

## Taille {#sizing}

Utilisez ces propriétés pour ajuster la taille du composant (largeur, hauteur). `m` est la taille standard pour presque tous les composants.

:::tip Choisir une taille
Lorsque vous choisissez une taille, assurez-vous toujours qu'elle est suffisamment grande pour les cibles tactiles.
:::

### Exemple {#example}

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### Variables {#variables}

| **Variable**      | **Valeur par défaut** | **Exemple**                            |
| ----------------- | --------------------- | -------------------------------------- |
| `--dwc-size-3xs`  | 1.125rem              | <SizingBox size="--dwc-size-3xs" />  |
| `--dwc-size-2xs`  | 1.375rem              | <SizingBox size="--dwc-size-2xs" />  |
| `--dwc-size-xs`   | 1.625rem              | <SizingBox size="--dwc-size-xs" />    |
| `--dwc-size-s`    | 1.875rem              | <SizingBox size="--dwc-size-s" />     |
| `--dwc-size-m`    | 2.25rem               | <SizingBox size="--dwc-size-m" />     |
| `--dwc-size-l`    | 2.75rem               | <SizingBox size="--dwc-size-l" />     |
| `--dwc-size-xl`   | 3.5rem                | <SizingBox size="--dwc-size-xl" />    |
| `--dwc-size-2xl`  | 4rem                  | <SizingBox size="--dwc-size-2xl" />   |
| `--dwc-size-3xl`  | 4.25rem               | <SizingBox size="--dwc-size-3xl" />   |
| `--dwc-size`      | var(--dwc-size-m)     | <SizingBox size="--dwc-size" />       |

## Espacement {#spacing}

Utilisez ces propriétés pour ajuster l'espacement entre les composants (marge, remplissage).

### Exemple {#example-1}

```css
.element {
  padding: var(--dwc-space-m);
}
```

### Variables {#variables-1}

| **Variable**       | **Valeur par défaut**  | **Exemple**                             |
| ------------------ | ---------------------- | --------------------------------------- |
| `--dwc-space-3xs`  | 0.075rem               | <SpacingBox space="--dwc-space-3xs" /> |
| `--dwc-space-2xs`  | 0.15rem                | <SpacingBox space="--dwc-space-2xs" /> |
| `--dwc-space-xs`   | 0.25rem                | <SpacingBox space="--dwc-space-xs" />  |
| `--dwc-space-s`    | 0.5rem                 | <SpacingBox space="--dwc-space-s" />   |
| `--dwc-space-m`    | 1rem                   | <SpacingBox space="--dwc-space-m" />   |
| `--dwc-space-l`    | 1.25rem                | <SpacingBox space="--dwc-space-l" />   |
| `--dwc-space-xl`   | 1.5rem                 | <SpacingBox space="--dwc-space-xl" />  |
| `--dwc-space-2xl`  | 1.75rem                | <SpacingBox space="--dwc-space-2xl" /> |
| `--dwc-space-3xl`  | 2rem                   | <SpacingBox space="--dwc-space-3xl" /> |
| `--dwc-space`      | var(--dwc-space-s)     | <SpacingBox space="--dwc-space" />      |
