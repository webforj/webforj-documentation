---
sidebar_position: 9
title: State
_i18n_hash: a6e594262709137318ed90066759b577
---
Los tokens de estado definen cómo los componentes responden visualmente a la interacción del usuario, como cuando están deshabilitados o enfocados. Estas variables ayudan a garantizar un comportamiento y un estilo consistentes en todos los elementos de la interfaz de usuario, y se pueden personalizar fácilmente para que coincidan con su sistema de diseño.

## Estado deshabilitado {#disabled-state}
Las propiedades del estado deshabilitado se utilizan para hacer que un elemento aparezca visualmente inactivo y no interactivo.

### Ejemplo {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Variables {#variables}

| **Variable**             | **Valor Predeterminado**     |
|--------------------------|------------------------------|
| `--dwc-disabled-opacity` | 0.7                          |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled)  |

---

## Estado enfocado {#focus-state}

Cuando un componente recibe el enfoque, se mostrará un anillo de enfoque alrededor de él para indicar su estado activo. Puede personalizar la apariencia del anillo utilizando las variables a continuación. Estas variables se utilizan junto con la configuración del anillo de enfoque del tema del componente.

### Variables {#variables-1}

| **Variable**              | **Valor Predeterminado** |
|---------------------------|--------------------------|
| `--dwc-focus-ring-l`      | 45%                      |
| `--dwc-focus-ring-a`      | 0.4                      |
| `--dwc-focus-ring-width`  | 3px                      |
