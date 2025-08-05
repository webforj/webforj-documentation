---
sidebar_position: 5
title: Browser Console
_i18n_hash: 8a6d28f2824de2020cf5b225d9ff458e
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Usar la consola del navegador para imprimir información valiosa del programa es una parte integral del proceso de desarrollo. La clase utilitaria <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> viene con una serie de funciones para mejorar las capacidades de registro.

## Instance {#instance}

Obtén una instancia de `BrowserConsole` usando el método `App.console()`. Imprime cualquier `Object` deseado como uno de los cinco tipos de registro: log, info, warn, error o debug.

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

Utiliza los métodos de construcción para establecer la apariencia del mensaje de registro. Cada constructor tiene opciones para cambiar una propiedad específica. También es posible [mezclar múltiples estilos](#mixing-styles).
Una vez que se imprima un mensaje en la consola, cualquier estilo aplicado no se llevará a los mensajes posteriores a menos que se redefina *explícitamente*.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Utiliza el método `setStyle` para cambiar las propiedades del registro de `BrowserConsole` que no están especificadas por los constructores.
:::

### Background color {#background-color}

Establece el color de fondo con el método `background()`, que retorna el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>.
Usa métodos nombrados por color, como `blue()`, o elige un valor específico con `colored(String color)`.

```java
// Ejemplos de Fondo
console().background().blue().log("Fondo azul");
console().background().colored("#031f8f").log("Fondo azul personalizado");
```

### Text color {#text-color}

Establece el color del texto con el método `color()`, que retorna el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>.
Usa métodos nombrados por color, como `red()`, o elige un valor específico con `colored(String color)`.

```java
// Ejemplos de Color
console().background().red().log("Texto rojo");
console().color().colored("#becad2").log("Texto azul claro personalizado");
```

### Font size {#font-size}

Establece el tamaño de la fuente con el método `size()`, que retorna el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>.
Usa métodos nombrados por un tamaño, como `small()`, o elige un valor específico con `from(String value)`.

```java
//Ejemplos de Tamaño
console().size().small().log("Fuente pequeña");
console().size().from("30px").log("Fuente de 30px");
```
:::tip
El método `from(String value)` puede aceptar otros valores de tamaño de fuente, como rem y vw.
:::

### Font style {#font-style}

Establece el estilo de la fuente con el método `style()`, que retorna el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>.
Por ejemplo, utiliza el método `italic()` para hacer que el registro de la consola esté en cursiva.

```java
// Ejemplos de Estilo
console().style().italic().log("Fuente en cursiva");
console().style().normal().log("Fuente normal");
```

### Text transformation {#text-transformation}

Controla la capitalización de los caracteres en un mensaje con el método `transform()`, que retorna el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>.
Por ejemplo, utiliza el método `capitalize()` para transformar la primera letra de cada palabra a mayúscula.

```java
// Ejemplos de Transformación
// Transformación de Texto a Capitalizar
console().transform().capitalize().log("Transformación de texto a capitalizar");
// TRANSFORMACIÓN DE TEXTO A MAYÚSCULAS 
console().transform().uppercase().log("Transformación de texto a mayúsculas");
```

### Font weight {#font-weight}

Establece qué tan grueso es el texto con el método `weight()`, que retorna el <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>.
Por ejemplo, usa el método `lighter()` para hacer que la fuente sea más ligera que lo normal.

```java
// Ejemplos de Peso
console().weight().bold().log("Fuente en negrita");
console().weight().lighter().log("Fuente más ligera");
```

## Mixing styles {#mixing-styles}
Es posible mezclar y combinar métodos para una visualización personalizada de los registros.

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
