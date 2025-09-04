---
sidebar_position: 5
title: Browser Console
_i18n_hash: 340e3d6f1d09c67ecc3d2d93bcd23b28
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Usar la consola del navegador para imprimir información valiosa del programa es una parte integral del proceso de desarrollo. La clase utilitaria <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> viene con una serie de características para mejorar las capacidades de registro.

<!-- :::info
Antes de `24.10`, los métodos `App.consoleLog()` y `App.consoleError()` habilitaban este comportamiento, pero desde entonces han sido marcados para depreciación.
::: -->

## Instance {#instance}

Obtén una instancia de `BrowserConsole` utilizando el método `App.console()`. Imprime cualquier `Object` deseado como uno de los cinco tipos de registro: log, info, warn, error o debug.

```java
import static com.webforj.App.console;
// Tipos
console().log("Mensaje de log");
console().info("Mensaje de info");
console().warn("Mensaje de advertencia");
console().error("Mensaje de error");
console().debug("Mensaje de depuración");
```

## Styling {#styling}

Utiliza los métodos del constructor para establecer la apariencia del mensaje de log. Cada constructor tiene opciones para cambiar una propiedad específica. También es posible [mezclar múltiples estilos](#mixing-styles).
Una vez que se imprime un mensaje de consola, cualquier estilo aplicado no se trasladará a mensajes posteriores a menos que *se redefina explícitamente*.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Utiliza el método `setStyle` para cambiar las propiedades del log de `BrowserConsole` no especificadas por los constructores.
:::

### Background color {#background-color}

Establece el color de fondo con el método `background()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>.
Utiliza métodos nombrados por color, como `blue()`, o elige un valor específico con `colored(String color)`.

```java
// Ejemplos de fondo
console().background().blue().log("Fondo azul");
console().background().colored("#031f8f").log("Fondo azul personalizado");
```

### Text color {#text-color}

Establece el color del texto con el método `color()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>.
Utiliza métodos nombrados por color, como `red()`, o elige un valor específico con `colored(String color)`.

```java
// Ejemplos de color
console().background().red().log("Texto rojo");
console().color().colored("#becad2").log("Texto gris claro personalizado");
```

### Font size {#font-size}

Establece el tamaño de fuente con el método `size()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>.
Utiliza métodos nombrados por tamaño, como `small()`, o elige un valor específico con `from(String value)`.

```java
// Ejemplos de tamaño
console().size().small().log("Fuente pequeña");
console().size().from("30px").log("Fuente de 30px");
```
:::tip
El método `from(String value)` puede aceptar otros valores de tamaño de fuente, como rem y vw.
:::

### Font style {#font-style}

Establece el estilo de la fuente con el método `style()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>.
Por ejemplo, utiliza el método `italic()` para hacer que el log de la consola esté en cursiva.

```java
// Ejemplos de estilo
console().style().italic().log("Fuente en cursiva");
console().style().normal().log("Fuente normal");
```

### Text transformation {#text-transformation}

Controla la capitalización de los caracteres en un mensaje con el método `transform()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>.
Por ejemplo, utiliza el método `capitalize()` para transformar la primera letra de cada palabra en mayúscula.

```java
// Ejemplos de transformación
// Transformación de texto en mayúsculas
console().transform().capitalize().log("Transformación de texto en mayúsculas");
// TRANSFORMACIÓN DE TEXTO EN MAYÚSCULAS 
console().transform().uppercase().log("Transformación de texto en mayúsculas");
```

### Font weight {#font-weight}

Establece qué tan grueso es el texto con el método `weight()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>.
Por ejemplo, utiliza el método `ligther()` para hacer que la fuente sea más ligera que la normal.

```java
// Ejemplos de peso
console().weight().bold().log("Fuente en negrita");
console().weight().lighter().log("Fuente más ligera");
```

## Mixing styles {#mixing-styles}
Es posible mezclar y combinar métodos para una visualización de registro personalizada.

```java
// Una variedad de opciones para una visualización de registro personalizada
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Mezclando estilos");
```
