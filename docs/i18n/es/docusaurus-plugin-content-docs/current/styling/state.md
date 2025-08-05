---
sidebar_position: 9
title: State
_i18n_hash: b7227f8d2022e7e6eab96de3f802aa20
---
Los tokens de estado definen cómo los componentes responden visualmente a la interacción del usuario, como cuando están deshabilitados o enfocados. Estas variables ayudan a garantizar un comportamiento y un estilo consistentes en todos los elementos de la interfaz de usuario, y se pueden personalizar fácilmente para que coincidan con su sistema de diseño.

## Estado deshabilitado {#disabled-state}
Las propiedades del estado deshabilitado se utilizan para hacer que un elemento parezca visualmente inactivo y no interactivo.

### Ejemplo {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variables {#variables}

| **Variable**             | **Valor por Defecto**     |
|--------------------------|----------------------------|
| `--dwc-disabled-opacity` | 0.7                        |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) |

---

## Estado de enfoque {#focus-state}

Cuando un componente recibe enfoque, se mostrará un anillo de enfoque a su alrededor para indicar su estado activo. Puede personalizar la apariencia del anillo utilizando las variables a continuación. Estas variables se utilizan en conjunto con la configuración del anillo de enfoque del tema del componente.

### Variables {#variables-1}

| **Variable**              | **Valor por Defecto** |
|---------------------------|-------------------|
| `--dwc-focus-ring-l`      | 45%               |
| `--dwc-focus-ring-a`      | 0.4               |
| `--dwc-focus-ring-width`  | 3px               |
