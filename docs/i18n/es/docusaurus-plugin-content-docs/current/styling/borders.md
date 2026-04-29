---
sidebar_position: 6
title: Border
_i18n_hash: aec4d011f38db8c5a7a6c324eb76d724
---
Las propiedades de borde se utilizan para controlar el estilo y el ancho del borde del componente. Consulta los [estilos de borde disponibles](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Ejemplo {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
}
```

### Variables {#variables}

| **Variable**              | **Valor Predeterminado** |
|---------------------------|--------------------------|
| `--dwc-border-width`      | 1px                      |
| `--dwc-border-style`      | sólido                   |
| `--dwc-border-color`      | var(--dwc-border-color-default) |
| `--dwc-border-color-emphasis` | var(--dwc-border-color-default-emphasis) |

### Colores de borde por paleta {#per-palette-border-colors}

Cada paleta de colores genera sus propias variables de color de borde:

| Patrón de Variable         | Descripción |
|---------------------------|-------------|
| `--dwc-border-color-{name}` | Color de borde consciente del modo teñido con el matiz de la paleta. |
| `--dwc-border-color-{name}-emphasis` | Variante más fuerte para los estados de hover, foco y activo. |

Donde `{name}` es uno de: `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`.

## Radio de borde {#border-radius}

Las variables del radio de borde definen cuán redondeadas están las esquinas de un componente. Todos los tamaños se escalan a partir de un solo valor base (`--dwc-border-radius-seed`). Cambiar la semilla reescala todo el sistema de radio proporcionalmente.

### Ejemplo {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Variables {#variables-1}

| **Variable**                | **Valor Predeterminado**                | **Computado (en seed=8px)** |
|-----------------------------|-----------------------------------------|-------------------------------|
| `--dwc-border-radius-seed`  | 0.5rem                                  | 8px                          |
| `--dwc-border-radius-2xs`   | 0.0625rem                               | 1px (fijo)                   |
| `--dwc-border-radius-xs`    | 0.125rem                                | 2px (fijo)                   |
| `--dwc-border-radius-s`     | calc(seed * 0.5)                        | 4px                          |
| `--dwc-border-radius-m`     | calc(seed * 0.75)                       | 6px                          |
| `--dwc-border-radius-l`     | var(--dwc-border-radius-seed)          | 8px                          |
| `--dwc-border-radius-xl`    | calc(seed * 1.5)                        | 12px                         |
| `--dwc-border-radius-2xl`   | calc(seed * 2)                          | 16px                         |
| `--dwc-border-radius-3xl`   | calc(seed * 3)                          | 24px                         |
| `--dwc-border-radius-4xl`   | calc(seed * 4)                          | 32px                         |
| `--dwc-border-radius-round` | 50%                                     |                               |
| `--dwc-border-radius-pill`  | calc(var(--dwc-size-m) / 2)            |                               |
| `--dwc-border-radius`       | var(--dwc-border-radius-seed)          | 8px                          |

<dwc-doc-radii></dwc-doc-radii>

### Pautas de uso {#usage-guidelines}

- Elementos dentro de contenedores: utiliza `s` (0.5x seed)
- Bordes estructurales (entre el ítem y el contenedor): utiliza `m` (0.75x seed)
- Contenedores y superficies: utiliza `l` (1x seed)
- Grandes superposiciones: utiliza `xl` (1.5x seed)
