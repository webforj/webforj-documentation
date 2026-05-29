---
sidebar_position: 7
title: Shadows
_i18n_hash: 423494230ee54caa83fec778e905871b
---
Las propiedades de sombra añaden efectos de sombra alrededor del marco de un elemento. Las sombras indican elementos que están apilados uno encima del otro en la interfaz de usuario.

Las sombras se adaptan automáticamente a los modos claro y oscuro, apareciendo más fuertes en el modo oscuro para una mejor visibilidad.

### Ejemplo {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

### Variables {#variables}

| **Variable**       | **Descripción**                       |
|--------------------|---------------------------------------|
| `--dwc-shadow-xs`  | Sombra extra pequeña (1 capa)        |
| `--dwc-shadow-s`   | Sombra pequeña (2 capas)             |
| `--dwc-shadow-m`   | Sombra mediana (3 capas, predeterminado) |
| `--dwc-shadow-l`   | Sombra grande (4 capas)              |
| `--dwc-shadow-xl`  | Sombra extra grande (5 capas)        |
| `--dwc-shadow-2xl` | Sombra doble extra grande (6 capas)  |
| `--dwc-shadow`     | `var(--dwc-shadow-m)`                |

<dwc-doc-shadows></dwc-doc-shadows>
