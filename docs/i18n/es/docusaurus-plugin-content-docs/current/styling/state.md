---
sidebar_position: 9
title: State
_i18n_hash: 3dc9896bce3e0577b2407f8ae4c863d0
---
Los tokens de estado definen cómo los componentes responden visualmente a la interacción del usuario, como cuando están deshabilitados o enfocados. Estas variables ayudan a garantizar un comportamiento y un estilo consistentes en todos los elementos de la interfaz de usuario, y se pueden personalizar fácilmente para que coincidan con su sistema de diseño.

<!-- vale off -->
## Estado deshabilitado {#disabled-state}
<!-- vale on -->
Las propiedades del estado deshabilitado se utilizan para hacer que un elemento parezca visiblemente inactivo y no interactivo.

La opacidad se adapta al tema actual para una visibilidad óptima tanto en modos claros como oscuros.

### Ejemplo {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variables {#variables}

| **Variable**             | **Valor predeterminado**      | **Descripción** |
|--------------------------|-------------------------------|-----------------|
| `--dwc-disabled-opacity` | Se adapta al modo claro/oscuridad | Opacidad reducida para elementos deshabilitados |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled)    | |

---

## Estado enfocado {#focus-state}

Cuando un componente recibe enfoque, se muestra un anillo de enfoque a su alrededor para indicar su estado activo. El anillo de enfoque utiliza un patrón de anillo con un hueco de color de superficie en el interior y un anillo coloreado en el exterior.

### Variables {#variables-1}

| **Variable**              | **Valor predeterminado** | **Descripción** |
|---------------------------|--------------------------|-----------------|
| `--dwc-focus-ring-a`      | 0.75                     | Opacidad alfa del anillo de enfoque |
| `--dwc-focus-ring-width`  | 2px                      | Grosor del anillo de enfoque |
| `--dwc-focus-ring-gap`    | 2px                      | Hueco entre el borde del componente y el anillo |

Cada paleta de colores genera su propia variable de anillo de enfoque:

| Patrón de variable      | Descripción |
|---|---|
| `--dwc-focus-ring-{name}` | Sombra del anillo de enfoque teñida con el color de la paleta. |

Donde `{name}` es uno de: `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`. Consulte [Temas de componentes](./colors#theming-components-with-abstract-variables) para más detalles.

<dwc-doc-focus-rings></dwc-doc-focus-rings>

---

## Escalas {#scales}

Las transformaciones de escala se utilizan para las animaciones de retroalimentación de presión/clic en elementos interactivos.

| **Variable**              | **Valor predeterminado** | **Descripción** |
|---------------------------|--------------------------|-----------------|
| `--dwc-scale-press`       | 0.97                     | Escala de presión estándar (reducción del 3%) |
| `--dwc-scale-press-deep`  | 0.93                     | Escala de presión profunda (reducción del 7%) |

<dwc-doc-scales></dwc-doc-scales>
