---
sidebar_position: 4
title: Typography
_i18n_hash: 5eafa3dea127703b4f573da438cbaf57
---
Les tokens typographiques sont utilisés pour maintenir un ensemble cohérent de styles de police dans toute votre application.

## Famille de polices {#font-family}

Les propriétés de famille de polices sont utilisées pour spécifier une liste priorisée de noms de familles de polices.

La pile de polices système est utilisée par défaut :

- `Segoe UI` sur Windows
- `Roboto` sur Android et Chrome OS
- `San Francisco` sur macOS et iOS
- Sur d'autres systèmes, `Helvetica, Arial` sont utilisés en tant que repli.

Vous pouvez appliquer ou changer la famille de polices en utilisant la propriété personnalisée `--dwc-font-family`.

### Exemple {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variables {#variables}

| **Variable**             | **Valeur par défaut**                                                                                                                               | **Exemple**                                                           |
| ------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------- |
| `--dwc-font-family-sans` | -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol' | <span style={{ fontFamily: "var(--dwc-font-family-sans)" }}>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-family-mono` | Menlo, Monaco, 'Courier New', monospace                                                                                                         | <span style={{ fontFamily: "var(--dwc-font-family-mono)" }}>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-family`      | `var(--dwc-font-family-sans)`                                                                                                                   | <span style={{ fontFamily: "var(--dwc-font-family)" }}>Sphinx of black quartz, judge my vow.</span>      |

## Taille de police {#font-size}

Les propriétés de taille de police définissent un ensemble de tailles de police à choisir. `s` est la taille standard et est utilisée par défaut par la plupart des composants. Toutes les tailles de police sont définies en `em`.

:::info Unité EM
`em` est une unité de longueur relative. elle est relative à [la taille de police](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) du parent, dans le cas des propriétés typographiques comme la taille de police, et à la taille de police de l'élément lui-même dans le cas d'autres propriétés comme [la largeur](https://developer.mozilla.org/en-US/docs/Web/CSS/width).
:::

### Exemple {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variables {#variables-1}

| **Variable**          | **Valeur par défaut**        | **Exemple**                                                      |
| --------------------- | ------------------------ | ---------------------------------------------------------------- |
| `--dwc-font-size-2xs` | 0.75rem                  | <span style={{ fontSize: "var(--dwc-font-size-2xs)" }}>Aa</span> |
| `--dwc-font-size-xs`  | 0.813rem                 | <span style={{ fontSize: "var(--dwc-font-size-xs)" }}>Aa</span>  |
| `--dwc-font-size-s`   | 0.875rem                 | <span style={{ fontSize: "var(--dwc-font-size-s)" }}>Aa</span>   |
| `--dwc-font-size-m`   | 1rem                     | <span style={{ fontSize: "var(--dwc-font-size-m)" }}>Aa</span>   |
| `--dwc-font-size-l`   | 1.125rem                 | <span style={{ fontSize: "var(--dwc-font-size-l)" }}>Aa</span>   |
| `--dwc-font-size-xl`  | 1.375rem                 | <span style={{ fontSize: "var(--dwc-font-size-xl)" }}>Aa</span>  |
| `--dwc-font-size-2xl` | 1.75rem                  | <span style={{ fontSize: "var(--dwc-font-size-2xl)" }}>Aa</span> |
| `--dwc-font-size-3xl` | 2.25rem                  | <span style={{ fontSize: "var(--dwc-font-size-3xl)" }}>Aa</span> |
| `--dwc-font-size`     | `var(--dwc-font-size-s)` | <span style={{ fontSize: "var(--dwc-font-size)" }}>Aa</span>     |

## Poids de police {#font-weight}

La propriété CSS [font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) définit le poids (ou la graisse) de la police.

### Exemple {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variable**                 | **Valeur par défaut** | **Exemple**                                                               |
| ---------------------------- | ----------------- | ------------------------------------------------------------------------- |
| `--dwc-font-weight-lighter`  | 200               | <span style={{ fontWeight: "var(--dwc-font-weight-lighter)" }}>Aa</span>  |
| `--dwc-font-weight-light`    | 300               | <span style={{ fontWeight: "var(--dwc-font-weight-light)" }}>Aa</span>    |
| `--dwc-font-weight-normal`   | 400               | <span style={{ fontWeight: "var(--dwc-font-weight-normal)" }}>Aa</span>   |
| `--dwc-font-weight-semibold` | 500               | <span style={{ fontWeight: "var(--dwc-font-weight-semibold)" }}>Aa</span> |
| `--dwc-font-weight-bold`     | 700               | <span style={{ fontWeight: "var(--dwc-font-weight-bold)" }}>Aa</span>     |
| `--dwc-font-weight-bolder`   | 800               | <span style={{ fontWeight: "var(--dwc-font-weight-bolder)" }}>Aa</span>   |

## Hauteur de ligne {#line-height}

La propriété CSS de hauteur de ligne définit la hauteur d'une boîte de ligne. Elle est couramment utilisée pour définir la distance entre les lignes de texte.

### Exemple {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variables {#variables-2}

| **Variable**                 | **Valeur par défaut**             | **Exemple**                                                                                                |
| ---------------------------- | ----------------------------- | ---------------------------------------------------------------------------------------------------------- |
| `--dwc-font-line-height-2xs` | 0.95                          | <span style={{ lineHeight: "var(--dwc-font-line-height-2xs)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-line-height-xs`  | 1.1                           | <span style={{ lineHeight: "var(--dwc-font-line-height-xs)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>  |
| `--dwc-font-line-height-s`   | 1.25                          | <span style={{ lineHeight: "var(--dwc-font-line-height-s)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-m`   | 1.375                         | <span style={{ lineHeight: "var(--dwc-font-line-height-m)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-l`   | 1.5                           | <span style={{ lineHeight: "var(--dwc-font-line-height-l)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-xl`  | 1.75                          | <span style={{ lineHeight: "var(--dwc-font-line-height-xl)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>  |
| `--dwc-font-line-height-2xl` | 2                             | <span style={{ lineHeight: "var(--dwc-font-line-height-2xl)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-line-height`     | var(--dwc-font-line-height-m) | <span style={{ lineHeight: "var(--dwc-font-line-height)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>     |
