---
sidebar_position: 4
title: Typography
_i18n_hash: 7c5f036abf897a890cad14af0a64c6bd
---
Los tokens de tipografía se utilizan para mantener un conjunto consistente de estilos de fuente en toda tu aplicación.

## Familia de fuentes {#font-family}

Las propiedades de la familia de fuentes se utilizan para especificar una lista priorizada de nombres de familia de fuentes.

La pila de fuentes del sistema se utiliza por defecto a través de `system-ui`, que se resuelve automáticamente a la fuente nativa de la plataforma:

- `San Francisco` en macOS y iOS
- `Segoe UI` en Windows
- `Roboto` en Android y Chrome OS

Puedes aplicar o cambiar la familia de fuentes utilizando la propiedad personalizada `--dwc-font-family`.

### Ejemplo {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variables {#variables}

| **Variable**                  | **Valor por defecto** |
| ----------------------------- | --------------------- |
| `--dwc-font-family-sans`      | system-ui, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji' |
| `--dwc-font-family-mono`      | ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace |
| `--dwc-font-family`           | `var(--dwc-font-family-sans)` |

## Tamaño de fuente {#font-size}

Las propiedades de tamaño de fuente definen un conjunto de tamaños de fuente para elegir. `m` es el tamaño estándar y es utilizado por la mayoría de los componentes por defecto. Todos los tamaños de fuente están definidos en `rem`.

:::info Unidad REM
`rem` es una unidad de longitud relativa. Es relativa al [tamaño de fuente](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) del elemento raíz (`<html>`), que por defecto es de 16px en la mayoría de los navegadores.
:::

### Ejemplo {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variables {#variables-1}

| **Variable**              | **Valor por defecto**       | **Calculado (con raíz de 16px)** |
| ------------------------- | --------------------------- | --------------------------------- |
| `--dwc-font-size-3xs`    | 0.625rem                    | 10px                              |
| `--dwc-font-size-2xs`    | 0.6875rem                   | 11px                              |
| `--dwc-font-size-xs`     | 0.75rem                     | 12px                              |
| `--dwc-font-size-s`      | 0.8125rem                   | 13px                              |
| `--dwc-font-size-m`      | 0.875rem                    | 14px                              |
| `--dwc-font-size-l`      | 1rem                        | 16px                              |
| `--dwc-font-size-xl`     | 1.25rem                     | 20px                              |
| `--dwc-font-size-2xl`    | 1.625rem                    | 26px                              |
| `--dwc-font-size-3xl`    | 2.125rem                    | 34px                              |
| `--dwc-font-size`        | `var(--dwc-font-size-m)`   | 14px                              |

<dwc-doc-font-sizes></dwc-doc-font-sizes>

## Peso de fuente {#font-weight}

La propiedad CSS [font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) establece el peso (o grosor) de la fuente.

### Ejemplo {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variable**                  | **Valor por defecto** |
| ----------------------------- | --------------------- |
| `--dwc-font-weight-thin`      | 100                   |
| `--dwc-font-weight-lighter`   | 200                   |
| `--dwc-font-weight-light`     | 300                   |
| `--dwc-font-weight-normal`    | 400                   |
| `--dwc-font-weight-medium`    | 500                   |
| `--dwc-font-weight-semibold`  | 600                   |
| `--dwc-font-weight-bold`      | 700                   |
| `--dwc-font-weight-bolder`    | 800                   |
| `--dwc-font-weight-black`     | 900                   |
| `--dwc-font-weight`           | `var(--dwc-font-weight-normal)` |

<dwc-doc-font-weights></dwc-doc-font-weights>

## Altura de línea {#line-height}

La propiedad CSS de altura de línea establece la altura de un cuadro de línea. Se utiliza comúnmente para establecer la distancia entre líneas de texto.

### Ejemplo {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variables {#variables-2}

| **Variable**                   | **Valor por defecto**    |
| ------------------------------ | ------------------------ |
| `--dwc-font-line-height-3xs`   | 1                        |
| `--dwc-font-line-height-2xs`   | 1.1                      |
| `--dwc-font-line-height-xs`    | 1.25                     |
| `--dwc-font-line-height-s`     | 1.375                    |
| `--dwc-font-line-height-m`     | 1.5                      |
| `--dwc-font-line-height-l`     | 1.625                    |
| `--dwc-font-line-height-xl`    | 1.75                     |
| `--dwc-font-line-height-2xl`   | 1.875                    |
| `--dwc-font-line-height-3xl`   | 2                        |
| `--dwc-font-line-height`       | var(--dwc-font-line-height-xs) |

<dwc-doc-line-heights></dwc-doc-line-heights>
