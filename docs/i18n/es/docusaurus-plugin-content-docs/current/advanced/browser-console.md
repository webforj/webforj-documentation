---
sidebar_position: 15
title: Browser Console
_i18n_hash: fd0e46761a5fd8b887a39b7a51e9b66b
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Utilizar la consola del navegador para imprimir información del programa es una parte integral del proceso de desarrollo. La clase utilitaria <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> proporciona características que mejoran las capacidades de registro a través de tipos de registro y estilos.

## Instance {#instance}

Obtenga una instancia de `BrowserConsole` utilizando el método `App.console()`. Imprima cualquier `Object` deseado como uno de los cinco tipos de registro: log, info, warn, error o debug.

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

Utilice los métodos del constructor para establecer la apariencia del mensaje de registro. Cada constructor tiene opciones para cambiar una propiedad específica. También es posible [mezclar múltiples estilos](#mixing-styles). Una vez que se imprime un mensaje en la consola, cualquier estilo aplicado no se transferirá a los mensajes subsiguientes a menos que se redefina *explícitamente*.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Utilice el método `setStyle` para cambiar las propiedades del registro `BrowserConsole` no especificadas por los constructores.
:::

### Background color {#background-color}

Establezca el color de fondo con el método `background()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>. Utilice métodos nombrados por color, como `blue()`, o elija un valor específico con `colored(String color)`.

```java
// Ejemplos de Fondo
console().background().blue().log("Fondo azul");
console().background().colored("#031f8f").log("Fondo azul personalizado");
```

### Text color {#text-color}

Establezca el color del texto con el método `color()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>. Utilice métodos nombrados por color, como `red()`, o elija un valor específico con `colored(String color)`.

```java
// Ejemplos de Color
console().background().red().log("Texto rojo");
console().color().colored("#becad2").log("Texto personalizado gris azulado claro");
```

### Font size {#font-size}

Establezca el tamaño de fuente con el método `size()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>. Utilice métodos nombrados por un tamaño, como `small()`, o elija un valor específico con `from(String value)`.

```java
//Ejemplos de Tamaño
console().size().small().log("Fuente pequeña");
console().size().from("30px").log("Fuente de 30px");
```
:::tip
El método `from(String value)` puede aceptar otros valores de tamaño de fuente, como rem y vw.
:::

### Font style {#font-style}

Establezca el estilo de fuente con el método `style()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>. Por ejemplo, utilice el método `italic()` para hacer que el registro de la consola esté en cursiva.

```java
// Ejemplos de Estilo
console().style().italic().log("Fuente en cursiva");
console().style().normal().log("Fuente normal");
```

### Text transformation {#text-transformation}

Controle la capitalización de los caracteres en un mensaje con el método `transform()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>. Por ejemplo, utilice el método `capitalize()` para transformar la primera letra de cada palabra a mayúscula.

```java
// Ejemplos de Transformación
// Transformación de Texto en Mayúscula
console().transform().capitalize().log("Transformación de texto capitalizada");
// TRANSFORMACIÓN DE TEXTO EN MAYÚSCULAS 
console().transform().uppercase().log("Transformación de texto en mayúsculas");
```

### Font weight {#font-weight}

Establezca qué tan grueso es el texto con el método `weight()`, que devuelve el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>. Por ejemplo, utilice el método `ligther()` para hacer que la fuente sea más ligera que lo normal.

```java
// Ejemplos de Peso
console().weight().bold().log("Fuente en negrita");
console().weight().lighter().log("Fuente más ligera");
```

## Mixing styles {#mixing-styles}
Es posible mezclar y combinar métodos para una visualización personalizada de registros.

```java
// Una variedad de opciones para visualización personalizada de registros
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Mezclando estilos");
```
