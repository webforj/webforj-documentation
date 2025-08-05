---
sidebar_position: 8
title: Surfaces
_i18n_hash: d26674c84c900aea4d63dad4dca61446
---
import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';

Hay tres niveles de superficies utilizados para organizar la jerarquía de la interfaz de usuario, a menudo combinados con [sombras](./shadows). Todos los [colores de la paleta](./colors) son probados para proporcionar un contraste suficiente contra estas superficies.

### Ejemplo {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variables {#variables}

| **Variable**      | **Uso**                                                               | **Ejemplo**                               |
|-------------------|------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1` | La superficie más oscura. Se utiliza para el fondo del cuerpo.        | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | Usada para componentes (por ejemplo, tarjetas).                       | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | La superficie más clara y alta. Usada para menús, popovers, diálogos ... | <SurfaceBox surface="--dwc-surface-3" /> |
