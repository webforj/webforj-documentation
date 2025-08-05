---
sidebar_position: 6
title: Border
_i18n_hash: fe0a0386da63ff7ea085db8daa12d0fe
---
Les propriétés de bordure sont utilisées pour contrôler le style et la largeur de la bordure du composant. Voir [les styles de bordure disponibles](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Exemple {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) red;
}
```

### Variables {#variables}

| **Variable**         | **Valeur par défaut** |
|----------------------|-----------------------|
| `--dwc-border-width` | 1px                   |
| `--dwc-border-style` | solide                |

## Rayon de bordure {#border-radius}

Les variables de rayon de bordure définissent à quel point les coins d'un composant sont arrondis. Toutes les valeurs sont définies en `em`, donc elles s'adaptent à la taille de la police.

:::info Unité EM
`em` est une unité relative qui s'adapte à la [taille de la police](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) du parent.
:::

### Exemple {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Variables {#variables-1}

| **Variable**                | **Valeur par défaut**           | **Exemple**                         |
|-----------------------------|----------------------------------|-------------------------------------|
| `--dwc-border-radius-2xs`   | 0.071em                         | <RadiusBox radius="--dwc-border-radius-2xs" /> |
| `--dwc-border-radius-xs`    | 0.125em                         | <RadiusBox radius="--dwc-border-radius-xs" />  |
| `--dwc-border-radius-s`     | 0.25em                          | <RadiusBox radius="--dwc-border-radius-s" />   |
| `--dwc-border-radius-m`     | 0.375em                         | <RadiusBox radius="--dwc-border-radius-m" />   |
| `--dwc-border-radius-l`     | 0.5em                           | <RadiusBox radius="--dwc-border-radius-l" />   |
| `--dwc-border-radius-xl`    | 0.75em                          | <RadiusBox radius="--dwc-border-radius-xl" />  |
| `--dwc-border-radius-2xl`   | 1em                             | <RadiusBox radius="--dwc-border-radius-2xl" /> |
| `--dwc-border-radius-round` | 50%                             | <RadiusBox radius="--dwc-border-radius-round" /> |
| `--dwc-border-radius-pill`  | 9999px                          | <RadiusBox radius="--dwc-border-radius-pill" />  |
| `--dwc-border-radius`       | var(--dwc-border-radius-s)      | <RadiusBox radius="--dwc-border-radius" />      |
