---
sidebar_position: 8
title: Surfaces
_i18n_hash: ac1f587cd1039f9bf083c610c29c27b9
---
Hay tres niveles de superficies utilizadas para organizar la jerarquía de la interfaz de usuario, a menudo combinados con [sombras](./shadows). Todos los [colores de la paleta](./colors) se prueban para proporcionar un contraste suficiente contra estas superficies.

### Ejemplo {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variables {#variables}

| **Variable**      | **Uso**                                                                | **Ejemplo**                               |
|-------------------|------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1` | La superficie más oscura. Usada para el fondo del cuerpo.             | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | Usada para componentes (por ejemplo, tarjetas).                       | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | La superficie más clara y alta. Usada para menús, popovers, diálogos... | <SurfaceBox surface="--dwc-surface-3" /> |
