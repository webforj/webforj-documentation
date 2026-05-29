---
sidebar_position: 8
title: Surfaces
_i18n_hash: cac300e6e9c10cd9d1da6b266e536c74
---
DWC define tres niveles de superficies que se utilizan para organizar la jerarquía de la interfaz de usuario combinada con [shadows](./shadows). Todos los [palette colors](./colors) se prueban para tener suficiente contraste con estas superficies.

Las superficies adquieren un sutil matiz del tono primario y se adaptan automáticamente a los modos claro y oscuro.

### Ejemplo {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variables {#variables}

| **Variable**      | **Uso**                             |
|-------------------|------------------------------------|
| `--dwc-surface-1` | Fondo de página y cuerpo.          |
| `--dwc-surface-2` | Barras de herramientas, menús, tarjetas. |
| `--dwc-surface-3` | Ventanas, menús, popovers, diálogos. |

<dwc-doc-surfaces></dwc-doc-surfaces>
