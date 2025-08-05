---
sidebar_position: 1
title: Importing Assets
_i18n_hash: 356ee4ac3242f607ead40f76311ead79
---
Las anotaciones de activos proporcionan un enfoque declarativo para incrustar recursos externos e inline, como JavaScript y CSS, dentro de una aplicación de manera estática. Estas anotaciones optimizan la gestión de recursos al asegurar que las dependencias se carguen en la fase de ejecución apropiada, reduciendo la configuración manual y mejorando la mantenibilidad.

## Importando archivos JavaScript {#importing-javascript-files}

La inclusión declarativa de JavaScript es compatible a través de la anotación `@JavaScript`, que permite la carga automática de dependencias. La anotación se puede aplicar tanto a nivel de componente como a nivel de aplicación.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

La anotación acepta una ruta relativa o completa que se cargará en la aplicación. Esto se insertará en el DOM como una etiqueta [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script). Además, la anotación admite las siguientes propiedades:

| Propiedad    | Tipo    | Descripción                                                                                                                                       | Predeterminado |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | -------------- |
| `top`        | Boolean | Especifica si el script debe inyectarse en la ventana de nivel superior                                                                         | `false`        |
| `attributes` | Object  | Un conjunto de <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>atributos</JavadocLink> que se aplicarán al script. | `{}`           |

#### Ejemplo: {#example}

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Los archivos se cargan solo cuando el componente que declara la anotación está adjunto a un contenedor. Si múltiples componentes cargan el mismo archivo, el archivo se inyecta solo una vez.
:::

## Inyectando JavaScript {#injecting-javascript}

En algunos casos, puede que desees inyectar código JavaScript directamente en el DOM en lugar de proporcionar una ruta de JavaScript. La anotación `InlineJavaScript` te permite inyectar contenido JavaScript.

```java
@InlineJavaScript("alert('¡Soy un script inline!');")
@JavaScript("context://js/app.js")
```

| Propiedad    | Tipo      | Descripción                                                               | Predeterminado |
| ------------ | --------- | ------------------------------------------------------------------------- | -------------- |
| `top`        | `Boolean` | Especifica si el script debe inyectarse en la ventana de nivel superior | `false`        |
| `attributes` | `Object`  | Atributos que se aplicarán al script                                     | `{}`           |
| `id`         | `String`  | Un ID de recurso único para asegurar una única inyección                | `""`           |

:::warning
Los scripts se pueden inyectar múltiples veces usando `InlineJavaScript`, a menos que se asigne un ID específico utilizando la propiedad `id`.
:::

## Importando archivos CSS {#importing-css-files}

La inclusión declarativa de CSS es compatible a través de la anotación `@StyleSheet`, que permite la carga automática de dependencias. La anotación se puede aplicar tanto a nivel de componente como a nivel de aplicación.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Propiedad    | Tipo    | Descripción                                                                   | Predeterminado |
| ------------ | ------- | ----------------------------------------------------------------------------- | -------------- |
| `top`        | Boolean | Especifica si la hoja de estilos debe inyectarse en la ventana de nivel superior | `false`        |
| `attributes` | Object  | Atributos que se aplicarán a la hoja de estilos                              | `{}`           |

#### Ejemplo: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Los archivos se cargan solo cuando el componente que declara la anotación está adjunto a un contenedor. Cada archivo se carga solo una vez.
:::

## Inyectando CSS {#injecting-css}

La anotación `InlineStyleSheet` te permite inyectar contenido CSS directamente en una página web tanto a nivel de componente como a nivel de aplicación.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Propiedad    | Tipo    | Descripción                                                                                                               | Predeterminado |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------- | -------------- |
| `top`        | Boolean | Especifica si la hoja de estilos debe inyectarse en la ventana de nivel superior de la página.                           | `false`        |
| `attributes` | Object  | Un conjunto de [atributos](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) que se aplicarán al elemento de estilo. | `{}`           |
| `id`         | String  | Un ID de recurso único. Si múltiples recursos tienen el mismo ID, se combinarán en un único elemento de estilo.         | `""`           |
| `once`       | Boolean | Determina si la hoja de estilos debe inyectarse en la página solo una vez, independientemente de múltiples instancias de componente. | `true`         |

:::tip 
Para una mejor resaltado de sintaxis al escribir CSS inline para tus componentes, puedes usar la extensión de VS Code de webforJ: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Activos dinámicos en tiempo de ejecución {#dynamic-assets-at-runtime}

La gestión dinámica de recursos es posible a través de la inyección programática de JavaScript y CSS en tiempo de ejecución. Puedes cargar o inyectar recursos según el contexto de tiempo de ejecución.

### Cargando e inyectando JavaScript {#loading-and-injecting-javascript}

Carga o inyecta dinámicamente JavaScript en tiempo de ejecución utilizando la <JavadocLink type="foundation" location="com/webforj/Page" code='true'>API Page</JavadocLink>. Esto te permite cargar scripts desde URLs o inyectar scripts inline directamente en el DOM.

```java
Page page = Page.getCurrent();

// Cargando archivos JavaScript
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inyectando JavaScript inline
page.addInlineJavaScript("console.log('Inyección en tiempo de ejecución');");
page.addInlineJavaScript("alert('Este script se ejecuta inline');");
```

| Parámetro    | Descripción                                                                                                             |
| ------------ | ----------------------------------------------------------------------------------------------------------------------- |
| `script`     | El URL o contenido de script inline para inyectar. Los URL que comienzan con `context://` se resuelven a la carpeta de recursos raíz de la aplicación. |
| `top`        | Determina si el script debe inyectarse en la parte superior de la página.                                            |
| `attributes` | Un mapa de atributos que establecer para el script.                                                                    |

### Cargando e inyectando CSS {#loading-and-injecting-css}

Carga o inyecta dinámicamente CSS en tiempo de ejecución utilizando la <JavadocLink type="foundation" location="com/webforj/Page" code='true'>API Page</JavadocLink>. Esto te permite cargar hojas de estilos desde URLs o inyectar estilos inline directamente en el DOM.

```java
Page page = Page.getCurrent();

// Cargando archivos CSS
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Inyectando CSS inline
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parámetro    | Descripción                                                                                                                 |
| ------------ | --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet` | El URL o contenido de hoja de estilos inline para inyectar. Los URL que comienzan con `context://` se resuelven a la carpeta de recursos raíz de la aplicación. |
| `top`        | Determina si la hoja de estilos debe inyectarse en la parte superior de la página.                                          |
| `attributes` | Un mapa de atributos que establecer para la hoja de estilos.                                                                |
