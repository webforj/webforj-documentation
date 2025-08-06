---
sidebar_position: 1
title: CSS Variables
_i18n_hash: f81f4fd4afdcb9807e10b8a35e244b20
---
Las variables CSS juegan un papel central en la personalización de la apariencia de los componentes de webforJ. Estas variables almacenan valores reutilizables como colores, tamaños de fuente y espacios, que se pueden aplicar de manera consistente en tu aplicación.

A diferencia de los enfoques tradicionales que dependían de preprocesadores CSS como [SASS](https://sass-lang.com/) o [LESS](https://lesscss.org/), las variables CSS permiten un **estilo dinámico en tiempo de ejecución**. Reducen la repetición, mejoran el mantenimiento y facilitan la lectura y gestión de las hojas de estilo.

## Definición de variables CSS {#defining-css-variables}

Las variables CSS se definen utilizando un prefijo de doble guion (`--`), y se pueden limitar dentro de cualquier selector CSS. Sin embargo, la práctica más común es definirlas en el selector `:root`, que las limita globalmente.

```css
:root {
  --app-background: orange;
}
```

:::tip La pseudo-clase `:root`
La [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) pseudo-clase apunta al elemento raíz del documento, típicamente `<html>` en HTML. Comporta como `html`, pero con mayor especificidad.
:::

Las variables CSS pueden contener cualquier cadena, no solo valores CSS válidos. Esta flexibilidad es particularmente útil al trabajar con JavaScript.

```css
html {
  --app-title: webforJ;
}
```

## Variables específicas del componente {#component-specific-variables}

Para definir o sobrescribir una variable para un componente específico, declárala dentro del selector del componente:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Referencia de Estilo Específico del Componente
Cada componente de webforJ admite un conjunto específico de variables CSS que controlan su apariencia. Estas están documentadas en la sección **Estilo > Propiedades CSS** para cada componente.
:::

## Uso de variables CSS {#using-css-variables}

Utiliza la función [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) para aplicar el valor de una variable en tus estilos:

```css
.panel {
  background-color: var(--app-background);
}
```

También puedes especificar un valor de respaldo en caso de que la variable no esté definida:

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Manipulación de variables con webforJ {#manipulating-variables-with-webforj}

Las variables CSS pueden actualizarse dinámicamente a través de la API de webforJ, lo que permite un estilo en tiempo real:

```java
// Establecer una variable CSS
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip Manipulación de Variables CSS con JavaScript
webforJ te permite ejecutar JavaScript del lado del cliente utilizando la API de Página o Elemento. Esto significa que puedes manipular dinámicamente las variables CSS en tiempo de ejecución, tal como lo harías en aplicaciones web estándar.

```javascript
// Establecer una variable CSS
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Obtener una variable CSS
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Recursos adicionales {#additional-resources}

- [Uso de Propiedades CSS Personalizadas (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [Una Guía Completa de Propiedades Personalizadas (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
