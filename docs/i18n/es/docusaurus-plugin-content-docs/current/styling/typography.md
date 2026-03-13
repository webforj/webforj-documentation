---
sidebar_position: 4
title: Typography
_i18n_hash: 86ea072dd194053c5a0195a6e7b7200e
---
Los tokens de tipografía se utilizan para mantener un conjunto consistente de estilos de fuente a lo largo de tu aplicación.

## Familia de fuentes {#font-family}

Las propiedades de la familia de fuentes se utilizan para especificar una lista priorizada de nombres de familias de fuentes.

La pila de fuentes del sistema se utiliza por defecto:

- `Segoe UI` en Windows
- `Roboto` en Android y Chrome OS
- `San Francisco` en macOS y iOS
- En otros sistemas, se utilizan `Helvetica, Arial` como sustitutos.

Puedes aplicar o cambiar la familia de fuentes utilizando la propiedad personalizada `--dwc-font-family`.

### Ejemplo {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variables {#variables}

<!-- vale Google.FirstPerson = NO -->
| **Variable**             | **Valor por defecto**                                                                                                                               | **Ejemplo**                                                           |
| ------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------- |
| `--dwc-font-family-sans` | -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol' | <span style={{ fontFamily: "var(--dwc-font-family-sans)" }}>Esfinge de cuarzo negro, juzga mi voto.</span> |
| `--dwc-font-family-mono` | Menlo, Monaco, 'Courier New', monospace                                                                                                         | <span style={{ fontFamily: "var(--dwc-font-family-mono)" }}>Esfinge de cuarzo negro, juzga mi voto.</span> |
| `--dwc-font-family`      | `var(--dwc-font-family-sans)`                                                                                                                   | <span style={{ fontFamily: "var(--dwc-font-family)" }}>Esfinge de cuarzo negro, juzga mi voto.</span>      |
<!-- vale Google.FirstPerson = YES -->

## Tamaño de fuente {#font-size}

Las propiedades de tamaño de fuente definen un conjunto de tamaños de fuente para elegir. `s` es el tamaño estándar y es utilizado por la mayoría de los componentes por defecto. Todos los tamaños de fuente están definidos en `em`.

:::info Unidad EM
`em` es una unidad de medida relativa. es relativa al [tamaño de fuente](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) del elemento padre, en el caso de propiedades tipográficas como font-size, y al tamaño de fuente del propio elemento en el caso de otras propiedades como [width](https://developer.mozilla.org/en-US/docs/Web/CSS/width).
:::

### Ejemplo {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variables {#variables-1}

| **Variable**          | **Valor por defecto**        | **Ejemplo**                                                      |
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

## Grosor de fuente {#font-weight}

La propiedad CSS [font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) establece el grosor (o negrita) de la fuente.

### Ejemplo {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variable**                 | **Valor por defecto** | **Ejemplo**                                                               |
| ---------------------------- | ----------------- | ------------------------------------------------------------------------- |
| `--dwc-font-weight-lighter`  | 200               | <span style={{ fontWeight: "var(--dwc-font-weight-lighter)" }}>Aa</span>  |
| `--dwc-font-weight-light`    | 300               | <span style={{ fontWeight: "var(--dwc-font-weight-light)" }}>Aa</span>    |
| `--dwc-font-weight-normal`   | 400               | <span style={{ fontWeight: "var(--dwc-font-weight-normal)" }}>Aa</span>   |
| `--dwc-font-weight-semibold` | 500               | <span style={{ fontWeight: "var(--dwc-font-weight-semibold)" }}>Aa</span> |
| `--dwc-font-weight-bold`     | 700               | <span style={{ fontWeight: "var(--dwc-font-weight-bold)" }}>Aa</span>     |
| `--dwc-font-weight-bolder`   | 800               | <span style={{ fontWeight: "var(--dwc-font-weight-bolder)" }}>Aa</span>   |

## Altura de línea {#line-height}

La propiedad CSS de altura de línea establece la altura de un cuadro de línea. Se utiliza comúnmente para establecer la distancia entre líneas de texto.

### Ejemplo {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variables {#variables-2}

<!-- vale Google.FirstPerson = NO -->
| **Variable**                 | **Valor por defecto**             | **Ejemplo**                                                                                                |
| ---------------------------- | ----------------------------- | ---------------------------------------------------------------------------------------------------------- |
| `--dwc-font-line-height-2xs` | 0.95                          | <span style={{ lineHeight: "var(--dwc-font-line-height-2xs)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span> |
| `--dwc-font-line-height-xs`  | 1.1                           | <span style={{ lineHeight: "var(--dwc-font-line-height-xs)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>  |
| `--dwc-font-line-height-s`   | 1.25                          | <span style={{ lineHeight: "var(--dwc-font-line-height-s)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>   |
| `--dwc-font-line-height-m`   | 1.375                         | <span style={{ lineHeight: "var(--dwc-font-line-height-m)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>   |
| `--dwc-font-line-height-l`   | 1.5                           | <span style={{ lineHeight: "var(--dwc-font-line-height-l)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>   |
| `--dwc-font-line-height-xl`  | 1.75                          | <span style={{ lineHeight: "var(--dwc-font-line-height-xl)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>  |
| `--dwc-font-line-height-2xl` | 2                             | <span style={{ lineHeight: "var(--dwc-font-line-height-2xl)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span> |
| `--dwc-font-line-height`     | var(--dwc-font-line-height-m) | <span style={{ lineHeight: "var(--dwc-font-line-height)", display: "block" }}>Esfinge de cuarzo negro, juzga mi voto.<br/>Esfinge de cuarzo negro, juzga mi voto.</span>     |
<!-- vale Google.FirstPerson = YES -->
