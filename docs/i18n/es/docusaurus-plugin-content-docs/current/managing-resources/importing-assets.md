---
sidebar_position: 1
title: Importing Assets
description: >-
  Attach JavaScript and CSS to webforJ components or the app using JavaScript,
  InlineJavaScript, StyleSheet, and InlineStyleSheet annotations.
_i18n_hash: 4343173cf0cbef440f24c05cf9ee3fbd
---
Las anotaciones de activos proporcionan un enfoque declarativo para incrustar recursos externos e internos como JavaScript y CSS dentro de una aplicación de manera estática. Estas anotaciones agilizan la gestión de recursos al asegurar que las dependencias se carguen en la fase de ejecución adecuada, reduciendo la configuración manual y mejorando la mantenibilidad.

:::tip El bundler es el predeterminado para npm y frameworks
Las anotaciones de activos adjuntan un script o una hoja de estilo que ya tienes, sin ningún paso de construcción. Para incorporar paquetes de npm, un framework de componentes como React o un lenguaje de hojas de estilo como SCSS, utiliza el [frontend bundler](/docs/managing-resources/bundler/overview). Es la ruta predeterminada para ese trabajo, y hace todo lo que hacen las anotaciones.
:::

## Importando archivos JavaScript {#importing-javascript-files}

La inclusión declarativa de JavaScript es compatible a través de la anotación `@JavaScript`, lo que permite la carga automática de dependencias. La anotación se puede aplicar tanto a nivel de componente como a nivel de aplicación.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

La anotación acepta una ruta relativa o completa que se cargará en la aplicación. Esto se insertará en el DOM como una etiqueta [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script). Además, la anotación admite las siguientes propiedades:

| Propiedad    | Tipo    | Descripción                                                                                                                                     | Predeterminado |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------| ---------------- |
| `top`        | Boolean | Especifica si el script debe inyectarse en la ventana de nivel superior                                                                         | `false`         |
| `attributes` | Object  | Un conjunto de <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>atributos</JavadocLink> que se aplican al script. | `{}`            |

#### Ejemplo: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Los archivos se cargan solo cuando el componente que declara la anotación está adjunto a un contenedor. Si múltiples componentes cargan el mismo archivo, el archivo se inyecta solo una vez.
:::

## Inyectando JavaScript {#injecting-javascript}

En algunos casos, es posible que desees inyectar código JavaScript directamente en el DOM en lugar de proporcionar una ruta de JavaScript. La anotación `InlineJavaScript` te permite inyectar contenido de JavaScript.

```java
@InlineJavaScript("alert('¡Soy un script en línea!');")
@JavaScript("context://js/app.js")
```

| Propiedad    | Tipo    | Descripción                                                                | Predeterminado |
| ------------ | ------- | -------------------------------------------------------------------------- | ---------------- |
| `top`        | `Boolean` | Especifica si el script debe inyectarse en la ventana de nivel superior   | `false`         |
| `attributes` | `Object`  | Atributos que se aplican al script                                       | `{}`            |
| `id`         | `String`  | Un ID de recurso único para asegurar una única inyección                  | `""`            |

:::warning
Los scripts se pueden inyectar múltiples veces utilizando `InlineJavaScript` a menos que se asigne un ID específico utilizando la propiedad `id`.
:::

## Importando archivos CSS {#importing-css-files}

La inclusión declarativa de CSS es compatible a través de la anotación `@StyleSheet`, lo que permite la carga automática de dependencias. La anotación se puede aplicar tanto a nivel de componente como a nivel de aplicación.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Propiedad    | Tipo    | Descripción                                                                    | Predeterminado |
| ------------ | ------- | ------------------------------------------------------------------------------ | ---------------- |
| `top`        | Boolean | Especifica si la hoja de estilo debe inyectarse en la ventana de nivel superior | `false`         |
| `attributes` | Object  | Atributos que se aplican a la hoja de estilo                                   | `{}`            |

#### Ejemplo: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Los archivos se cargan solo cuando el componente que declara la anotación está adjunto a un contenedor. Cada archivo se carga solo una vez.
:::

## Inyectando CSS {#injecting-css}

La anotación `InlineStyleSheet` te permite inyectar contenido de CSS directamente en una página web tanto a nivel de componente como a nivel de aplicación.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Propiedad    | Tipo    | Descripción                                                                                                                 | Predeterminado |
| ------------ | ------- |-----------------------------------------------------------------------------------------------------------------------------| ---------------- |
| `top`        | Boolean | Especifica si la hoja de estilo debe inyectarse en la ventana de nivel superior de la página.                             | `false`         |
| `attributes` | Object  | Un conjunto de [atributos](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) que se aplican al elemento de estilo. | `{}`            |
| `id`         | String  | Un ID de recurso único. Si múltiples recursos tienen el mismo ID, se agruparán en un único elemento de estilo.           | `""`            |
| `once`       | Boolean | Determina si la hoja de estilo debe inyectarse en la página solo una vez, independientemente de múltiples instancias de componente. | `true`          |

:::tip
Para una mejor resaltación de sintaxis al escribir CSS en línea para tus componentes, puedes utilizar la extensión de VS Code de webforJ: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Activos dinámicos en tiempo de ejecución {#dynamic-assets-at-runtime}

La gestión dinámica de recursos es posible a través de la inyección programática de JavaScript y CSS en tiempo de ejecución. Puedes cargar o inyectar recursos en función del contexto de tiempo de ejecución.

### Cargando e inyectando JavaScript {#loading-and-injecting-javascript}

Carga o inyecta JavaScript dinámicamente en tiempo de ejecución utilizando la <JavadocLink type="foundation" location="com/webforj/Page" code='true'>API de Page</JavadocLink>. Esto te permite cargar scripts desde URLs o inyectar scripts en línea directamente en el DOM.

```java
Page page = Page.getCurrent();

// Cargando archivos JavaScript
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inyectando JavaScript en línea
page.addInlineJavaScript("console.log('Inyección en tiempo de ejecución');");
page.addInlineJavaScript("alert('Este script se ejecuta en línea');");
```

| Parámetro    | Descripción                                                                                                             |
| ------------ | ----------------------------------------------------------------------------------------------------------------------- |
| `script`     | La URL o el contenido del script en línea a inyectar. Las URLs que comienzan con `context://` se resuelven a la carpeta de recursos raíz de la aplicación. |
| `top`        | Determina si el script debe inyectarse en la parte superior de la página.                                            |
| `attributes` | Un mapa de atributos que se establecen para el script.                                                                  |

### Cargando e Inyectando CSS {#loading-and-injecting-css}

Carga o inyecta CSS dinámicamente en tiempo de ejecución utilizando la <JavadocLink type="foundation" location="com/webforj/Page" code='true'>API de Page</JavadocLink>. Esto te permite cargar hojas de estilo desde URLs o inyectar estilos en línea directamente en el DOM.

```java
Page page = Page.getCurrent();

// Cargando archivos CSS
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Inyectando CSS en línea
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parámetro    | Descripción                                                                                                                 |
| ------------ | --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet` | La URL o el contenido de la hoja de estilo en línea a inyectar. Las URLs que comienzan con `context://` se resuelven a la carpeta de recursos raíz de la aplicación. |
| `top`        | Determina si la hoja de estilo debe inyectarse en la parte superior de la página.                                        |
| `attributes` | Un mapa de atributos que se establecen para la hoja de estilo.                                                              |
