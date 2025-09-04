---
sidebar_position: 1
title: CSS Variables
_i18n_hash: b753c1b13cfcc45f72d6712e980ef952
---
Las variables CSS juegan un papel central en la personalización de la apariencia de los componentes de webforJ. Estas variables almacenan valores reutilizables como colores, tamaños de fuente y espaciado, que se pueden aplicar de manera consistente en tu aplicación.

A diferencia de los enfoques tradicionales que dependían de preprocesadores CSS como [SASS](https://sass-lang.com/) o [LESS](https://lesscss.org/), las variables CSS permiten **estilos dinámicos en tiempo de ejecución**. Reducen la repetición, mejoran el mantenimiento y hacen que las hojas de estilo sean más fáciles de leer y gestionar.

## Definir variables CSS {#defining-css-variables}

Las variables CSS se definen utilizando un prefijo de doble guion (`--`), y se pueden delimitar dentro de cualquier selector CSS. Sin embargo, la práctica más común es definirlas en el selector `:root`, que las delimita globalmente.

```css
:root {
  --app-background: orange;
}
```

:::tip La pseudo-clase `:root`
La [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) pseudo-clase apunta al elemento raíz del documento—típicamente `<html>` en HTML. Se comporta como `html`, pero con mayor especificidad.
:::

Las variables CSS pueden contener cualquier cadena, no solo valores CSS válidos. Esta flexibilidad es particularmente útil al trabajar con JavaScript.

```css
html {
  --app-title: webforJ;
}
```

## Variables específicas de componentes {#component-specific-variables}

Para definir o sobrescribir una variable para un componente específico, declárala dentro del selector del componente:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Referencia de Estilización Específica de Componentes
Cada componente de webforJ soporta un conjunto específico de variables CSS que controlan su apariencia. Estas se documentan en la sección **Estilización > Propiedades CSS** para cada componente. 
:::


## Usar variables CSS {#using-css-variables}

Utiliza la función [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) para aplicar el valor de una variable en tus estilos:

```css
.panel {
  background-color: var(--app-background);
}
```

También puedes especificar un valor por defecto en caso de que la variable no esté definida:

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Manipular variables con webforJ {#manipulating-variables-with-webforj}

Las variables CSS se pueden actualizar dinámicamente a través de la API de webforJ, permitiendo estilos en tiempo real:

```java
// Establecer una variable CSS
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip Manipulando Variables CSS con JavaScript
webforJ te permite ejecutar JavaScript en el lado del cliente utilizando la API Page o Element. Esto significa que puedes manipular dinámicamente las variables CSS en tiempo de ejecución tal como lo harías en aplicaciones web estándar.

```javascript
// Establecer una variable CSS
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Obtener una variable CSS
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Recursos adicionales {#additional-resources}

- [Usando Propiedades Personalizadas CSS (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [Una Guía Completa de Propiedades Personalizadas (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
