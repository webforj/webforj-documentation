---
sidebar_position: 4
title: Typography
_i18n_hash: 9ecc89dbb5b06c51ad61a22ddb69ce7d
---
Los tokens de tipografía se utilizan para mantener un conjunto coherente de estilos de fuente en toda su aplicación.

## Familia tipográfica {#font-family}

Las propiedades de la familia tipográfica se utilizan para especificar una lista priorizada de nombres de familias tipográficas.

La pila de fuentes del sistema se utiliza por defecto:

- `Segoe UI` en Windows
- `Roboto` en Android y Chrome OS
- `San Francisco` en macOS y iOS
- En otros sistemas, se utilizan `Helvetica, Arial` como alternativas.

Puede aplicar o cambiar la familia tipográfica usando la propiedad personalizada `--dwc-font-family`.

### Ejemplo {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variables {#variables}

| **Variable**             | **Valor por Defecto**                                                                                                                               | **Ejemplo**                                                           |
| ------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------- |
| `--dwc-font-family-sans` | -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol' | <span style={{ fontFamily: "var(--dwc-font-family-sans)" }}>Esfinge de cuarzo negro, juzga mi voto.</span> |
| `--dwc-font-family-mono` | Menlo, Monaco, 'Courier New', monospace                                                                                                         | <span style={{ fontFamily: "var(--dwc-font-family-mono)" }}>Esfinge de cuarzo negro, juzga mi voto.</span> |
| `--dwc-font-family`      | `var(--dwc-font-family-sans)`                                                                                                                   | <span style={{ fontFamily: "var(--dwc-font-family)" }}>Esfinge de cuarzo negro, juzga mi voto.</span>      |

## Tamaño de fuente {#font-size}

Las propiedades del tamaño de fuente definen un conjunto de tamaños de fuente entre los que elegir. `s` es el tamaño estándar, y se usa por defecto en la mayoría de los componentes. Todos los tamaños de fuente se definen en `em`.

:::info Unidad EM
`em` es una unidad de longitud relativa. es relativa al [tamaño de fuente](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) del padre, en el caso de propiedades tipográficas como el tamaño de fuente, y al tamaño de fuente del elemento en sí y en el caso de otras propiedades como [ancho](https://developer.mozilla.org/en-US/docs/Web/CSS/width).
:::

### Ejemplo {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variables {#variables-1}

| **Variable**          | **Valor por Defecto**        | **Ejemplo**                                                      |
| --------------------- | ---------------------------- | ---------------------------------------------------------------- |
| `--dwc-font-size-2xs` | 0.75rem                      | <span style={{ fontSize: "var(--dwc-font-size-2xs)" }}>Aa</span> |
| `--dwc-font-size-xs`  | 0.813rem                     | <span style={{ fontSize: "var(--dwc-font-size-xs)" }}>Aa</span>  |
| `--dwc-font-size-s`   | 0.875rem                     | <span style={{ fontSize: "var(--dwc-font-size-s)" }}>Aa</span>   |
| `--dwc-font-size-m`   | 1rem                         | <span style={{ fontSize: "var(--dwc-font-size-m)" }}>Aa</span>   |
| `--dwc-font-size-l`   | 1.125rem                     | <span style={{ fontSize: "var(--dwc-font-size-l)" }}>Aa</span>   |
| `--dwc-font-size-xl`  | 1.375rem                     | <span style={{ fontSize: "var(--dwc-font-size-xl)" }}>Aa</span>  |
| `--dwc-font-size-2xl` | 1.75rem                      | <span style={{ fontSize: "var(--dwc-font-size-2xl)" }}>Aa</span> |
| `--dwc-font-size-3xl` | 2.25rem                      | <span style={{ fontSize: "var(--dwc-font-size-3xl)" }}>Aa</span> |
| `--dwc-font-size`     | `var(--dwc-font-size-s)`     | <span style={{ fontSize: "var(--dwc-font-size)" }}>Aa</span>     |

## Peso de fuente {#font-weight}

La propiedad CSS [font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) establece el peso (o negrita) de la fuente.

### Ejemplo {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variable**                 | **Valor por Defecto** | **Ejemplo**                                                               |
| ---------------------------- | --------------------- | ------------------------------------------------------------------------- |
| `--dwc-font-weight-lighter`  | 200                   | <span style={{ fontWeight: "var(--dwc-font-weight-lighter)" }}>Aa</span>  |
| `--dwc-font-weight-light`    | 300                   | <span style={{ fontWeight: "var(--dwc-font-weight-light)" }}>Aa</span>    |
| `--dwc-font-weight-normal`   | 400                   | <span style={{ fontWeight: "var(--dwc-font-weight-normal)" }}>Aa</span>   |
| `--dwc-font-weight-semibold` | 500                   | <span style={{ fontWeight: "var(--dwc-font-weight-semibold)" }}>Aa</span> |
| `--dwc-font-weight-bold`     | 700                   | <span style={{ fontWeight: "var(--dwc-font-weight-bold)" }}>Aa</span>     |
| `--dwc-font-weight-bolder`   | 800                   | <span style={{ fontWeight: "var(--dwc-font-weight-bolder)" }}>Aa</span>   |

## Altura de línea {#line-height}

La propiedad CSS de altura de línea establece la altura de un cuadro de línea. Se usa comúnmente para establecer la distancia entre líneas de texto.

### Ejemplo {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variables {#variables-2}

| **Variable**                 | **Valor por Defecto**             | **Ejemplo**                                                                                                |
| ---------------------------- | --------------------------------- | ---------------------------------------------------------------------------------------------------------- |
| `--dwc-font-line-height-2xs` | 0.95                              | <span style={{ lineHeight: "var(--dwc-font-line-height-2xs)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span> |
| `--dwc-font-line-height-xs`  | 1.1                               | <span style={{ lineHeight: "var(--dwc-font-line-height-xs)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>  |
| `--dwc-font-line-height-s`   | 1.25                              | <span style={{ lineHeight: "var(--dwc-font-line-height-s)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>   |
| `--dwc-font-line-height-m`   | 1.375                             | <span style={{ lineHeight: "var(--dwc-font-line-height-m)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>   |
| `--dwc-font-line-height-l`   | 1.5                               | <span style={{ lineHeight: "var(--dwc-font-line-height-l)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>   |
| `--dwc-font-line-height-xl`  | 1.75                              | <span style={{ lineHeight: "var(--dwc-font-line-height-xl)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>  |
| `--dwc-font-line-height-2xl` | 2                                 | <span style={{ lineHeight: "var(--dwc-font-line-height-2xl)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span> |
| `--dwc-font-line-height`     | var(--dwc-font-line-height-m)   | <span style={{ lineHeight: "var(--dwc-font-line-height)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>     |
