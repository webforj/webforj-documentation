---
sidebar_position: 1
title: CSS Variables
description: >-
  Define, scope, and consume CSS custom properties to control webforJ component
  styling at runtime without preprocessors.
_i18n_hash: 8e09f9776dc8bb74a1d37e6ba2bdceb0
---
Las variables CSS juegan un papel central en la personalización de la apariencia de los componentes de webforJ. Estas variables almacenan valores reutilizables, como colores, tamaños de fuente y espaciado, que se pueden aplicar de manera consistente en su aplicación.

A diferencia de los enfoques tradicionales que dependían de preprocesadores CSS como [SASS](https://sass-lang.com/) o [LESS](https://lesscss.org/), las variables CSS permiten un **estilo dinámico en tiempo de ejecución**. Reducen la repetición, mejoran el mantenimiento y facilitan la lectura y gestión de las hojas de estilo.

## Definiendo variables CSS {#defining-css-variables}

Las variables CSS se definen utilizando un prefijo de doble guion (`--`), y se pueden limitar dentro de cualquier selector CSS. Sin embargo, la práctica más común es definirlas en el selector `:root`, que las delimita globalmente.

```css
:root {
  --app-background: orange;
}
```

:::tip La pseudo-clase `:root`
La pseudo-clase [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) apunta al elemento raíz del documento—típicamente `<html>` en HTML. Se comporta como `html`, pero con mayor especificidad.
:::

Las variables CSS pueden contener cualquier cadena, no solo valores CSS válidos. Esta flexibilidad es particularmente útil al trabajar con JavaScript.

```css
html {
  --app-title: webforJ;
}
```

## Variables específicas de componentes {#component-specific-variables}

Para definir o anular una variable para un componente específico, declárela dentro del selector del componente:

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Referencia de Estilo Específica del Componente
Cada componente de webforJ admite un conjunto específico de variables CSS que controlan su apariencia. Estas se documentan en la sección **Estilos > Propiedades CSS** para cada componente.
:::


## Usando variables CSS {#using-css-variables}

Use la función [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) para aplicar el valor de una variable en sus estilos:

```css
.panel {
  background-color: var(--app-background);
}
```

También puede especificar un valor de respaldo en caso de que la variable no esté definida:

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Manipulando variables con webforJ {#manipulating-variables-with-webforj}

Las variables CSS se pueden actualizar dinámicamente a través de la API de webforJ, lo que permite estilos en tiempo real:

```java
// Establecer una variable CSS
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip Manipulando Variables CSS con JavaScript
webforJ le permite ejecutar JavaScript del lado del cliente utilizando la API de Página o Elemento. Esto significa que puede manipular dinámicamente las variables CSS en tiempo de ejecución, como lo haría en aplicaciones web estándar.

```javascript
// Establecer una variable CSS
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Obtener una variable CSS
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Recursos adicionales {#additional-resources}

- [Usando Propiedades CSS Personalizadas (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)
- [Una Guía Completa de Propiedades Personalizadas (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
