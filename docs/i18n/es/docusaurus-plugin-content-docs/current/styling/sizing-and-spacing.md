---
sidebar_position: 5
title: Tamaño y Espaciado
_i18n_hash: 4efe9ef910459481ca90eec87c26ebe0
---
Tokens de espaciado y tamaño se utilizan para proporcionar un espaciado y tamaño consistentes en tu aplicación. Todas las propiedades de tamaño y espaciado se definen en `rem`.

:::info Unidad REM
`rem` es una unidad de longitud relativa. Es relativa al tamaño de fuente del [elemento raíz](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html).
:::

## Tamaño {#sizing}

Utiliza estas propiedades para ajustar el tamaño del componente (ancho, alto). `m` es el tamaño estándar para casi todos los componentes.

:::tip Elegir un tamaño
Al elegir un tamaño, asegúrate siempre de que sea lo suficientemente grande para los objetivos de toque.
:::

### Ejemplo {#example}

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### Variables {#variables}

| **Variable**      | **Valor por Defecto** | **Ejemplo**                            |
| ----------------- | --------------------- | -------------------------------------- |
| `--dwc-size-3xs`  | 1.125rem              | <SizingBox size="--dwc-size-3xs" />  |
| `--dwc-size-2xs`  | 1.375rem              | <SizingBox size="--dwc-size-2xs" />  |
| `--dwc-size-xs`   | 1.625rem              | <SizingBox size="--dwc-size-xs" />   |
| `--dwc-size-s`    | 1.875rem              | <SizingBox size="--dwc-size-s" />    |
| `--dwc-size-m`    | 2.25rem               | <SizingBox size="--dwc-size-m" />    |
| `--dwc-size-l`    | 2.75rem               | <SizingBox size="--dwc-size-l" />    |
| `--dwc-size-xl`   | 3.5rem                | <SizingBox size="--dwc-size-xl" />   |
| `--dwc-size-2xl`  | 4rem                  | <SizingBox size="--dwc-size-2xl" />  |
| `--dwc-size-3xl`  | 4.25rem               | <SizingBox size="--dwc-size-3xl" />  |
| `--dwc-size`      | var(--dwc-size-m)    | <SizingBox size="--dwc-size" />      |

## Espaciado {#spacing}

Utiliza estas propiedades para ajustar el espaciado entre componentes (margen, padding).

### Ejemplo {#example-1}

```css
.element {
  padding: var(--dwc-space-m);
}
```

### Variables {#variables-1}

| **Variable**       | **Valor por Defecto**  | **Ejemplo**                             |
| ------------------ | ---------------------  | --------------------------------------- |
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
