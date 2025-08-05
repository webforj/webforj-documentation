---
sidebar_position: 6
title: Border
_i18n_hash: fe0a0386da63ff7ea085db8daa12d0fe
---
Las propiedades de borde se utilizan para controlar el estilo y el ancho del borde del componente. Consulta los [estilos de borde disponibles](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Ejemplo {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) red;
}
```

### Variables {#variables}

| **Variable**         | **Valor por defecto** |
|----------------------|-------------------|
| `--dwc-border-width` | 1px               |
| `--dwc-border-style` | sólido            |

## Radio del borde {#border-radius}

Las variables de radio del borde definen qué tan redondeadas están las esquinas de un componente. Todos los valores están definidos en `em`, por lo que escalan con el tamaño de la fuente.

:::info Unidad EM
`em` es una unidad relativa que escala con el [tamaño de fuente](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) del elemento padre.
:::

### Ejemplo {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Variables {#variables-1}

| **Variable**                | **Valor por defecto**          | **Ejemplo**                         |
|-----------------------------|-----------------------------|--------------------------------------|
| `--dwc-border-radius-2xs`   | 0.071em                    | <RadiusBox radius="--dwc-border-radius-2xs" /> |
| `--dwc-border-radius-xs`    | 0.125em                    | <RadiusBox radius="--dwc-border-radius-xs" /> |
| `--dwc-border-radius-s`     | 0.25em                     | <RadiusBox radius="--dwc-border-radius-s" />  |
| `--dwc-border-radius-m`     | 0.375em                    | <RadiusBox radius="--dwc-border-radius-m" />  |
| `--dwc-border-radius-l`     | 0.5em                      | <RadiusBox radius="--dwc-border-radius-l" />  |
| `--dwc-border-radius-xl`    | 0.75em                     | <RadiusBox radius="--dwc-border-radius-xl" /> |
| `--dwc-border-radius-2xl`   | 1em                        | <RadiusBox radius="--dwc-border-radius-2xl" /> |
| `--dwc-border-radius-round` | 50%                        | <RadiusBox radius="--dwc-border-radius-round" /> |
| `--dwc-border-radius-pill`  | 9999px                     | <RadiusBox radius="--dwc-border-radius-pill" /> |
| `--dwc-border-radius`       | var(--dwc-border-radius-s) | <RadiusBox radius="--dwc-border-radius" />     |
