---
sidebar_position: 8
title: Superficies
description: >-
  Define la jerarquía visual con tres tokens de superficie DWC que se adaptan al
  modo para fondos de página, tarjetas y ventanas emergentes o diálogos.
_i18n_title_hash: d40d1008e1abc156a84c98148422b0b9
_i18n_description_hash: 35f5960e176694545a805c578abc82b8
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
