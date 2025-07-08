---
sidebar_position: 1
title: Importing Assets
---

Assets annotations provide a declarative approach to embedding external and inline resources such as JavaScript and CSS within an app statically. These annotations streamline resource management by ensuring dependencies are loaded at the appropriate execution phase, reducing manual configuration and enhancing maintainability.

## Importing JavaScript files

Declarative JavaScript inclusion is supported through the `@JavaScript` annotation, enabling automatic dependency loading. The annotation can be applied at both the component level and the app level.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

The annotation accepts a relative or full path to be loaded in the app. This will be inserted into the DOM as a [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) tag. Additionally, the annotation supports the following properties:

| Property     | Type    | Description                                                                                                                                       | Default |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | ------- |
| `top`        | Boolean | Specifies whether the script should be injected into the top-level window                                                                         | `false` |
| `attributes` | Object  | A set of <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attributes</JavadocLink> to apply to the script. | `{}`    |

#### Example:

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Files are loaded only when the component declaring the annotation is attached to a container. If multiple components load the same file, the file is injected only once.
:::

## Injecting JavaScript

In some cases, you may want to inject JavaScript code directly into the DOM instead of providing a JavaScript path. The `InlineJavaScript` annotation allows you to inject JavaScript content.

```java
@InlineJavaScript("alert('I am an inline script!');")
@JavaScript("context://js/app.js")
```

| Property     | Type    | Description                                                               | Default |
| ------------ | ------- | ------------------------------------------------------------------------- | ------- |
| `top`        | `Boolean` | Specifies whether the script should be injected into the top-level window | `false` |
| `attributes` | `Object`  | Attributes to apply to the script                                         | `{}`    |
| `id`         | `String`  | A unique resource ID to ensure a single injection                         | `""`    |

:::warning
Scripts can be injected multiple times using `InlineJavaScript` unless a specific ID is assigned using the `id` property.
:::

## Importing CSS files

Declarative CSS inclusion is supported through the `@StyleSheet` annotation, enabling automatic dependency loading. The annotation can be applied at both the component level and the app level.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Property     | Type    | Description                                                                   | Default |
| ------------ | ------- | ----------------------------------------------------------------------------- | ------- |
| `top`        | Boolean | Specifies whether the StyleSheet should be injected into the top-level window | `false` |
| `attributes` | Object  | Attributes to apply to the StyleSheet                                         | `{}`    |

#### Example:

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Files are loaded only when the component declaring the annotation is attached to a container. Each file is loaded only once.
:::

## Injecting CSS

The `InlineStyleSheet` annotation allows you to inject CSS content directly into a web page at both the component level and the app level.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Property     | Type    | Description                                                                                                               | Default |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------- | ------- |
| `top`        | Boolean | Specifies whether the StyleSheet should be injected into the top-level window of the page.                                | `false` |
| `attributes` | Object  | A set of [attributes](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) to apply to the style element.     | `{}`    |
| `id`         | String  | A unique resource ID. If multiple resources have the same ID, they will be bundled together in a single style element.    | `""`    |
| `once`       | Boolean | Determines whether the StyleSheet should be injected into the page only once, regardless of multiple component instances. | `true`  |

:::tip 
For better syntax highlighting when writing inline CSS for your components, you can use webforJ VS Code extension: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamic assets at runtime

Dynamic resource management is possible through programmatic injection of JavaScript and CSS at runtime. You can load or inject resources based on runtime context.

### Loading and injecting JavaScript

Dynamically load or inject JavaScript at runtime using the <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. This allows you to load scripts from URLs or inject inline scripts directly into the DOM.

```java
Page page = Page.getCurrent();

// Loading JavaScript files
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Injecting inline JavaScript
page.addInlineJavaScript("console.log('Runtime Injection');");
page.addInlineJavaScript("alert('This script runs inline');");
```

| Parameter    | Description                                                                                                             |
| ------------ | ----------------------------------------------------------------------------------------------------------------------- |
| `script`     | The URL or inline script content to inject. URLs starting with `context://` resolve to the app's root resources folder. |
| `top`        | Determines whether the script should be injected at the top of the page.                                                |
| `attributes` | A map of attributes to set for the script.                                                                              |

### Loading and Injecting CSS

Dynamically load or inject CSS at runtime using the <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. This allows you to load stylesheets from URLs or inject inline styles directly into the DOM.

```java
Page page = Page.getCurrent();

// Loading CSS files
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Injecting inline CSS
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parameter    | Description                                                                                                                 |
| ------------ | --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet` | The URL or inline StyleSheet content to inject. URLs starting with `context://` resolve to the app's root resources folder. |
| `top`        | Determines whether the StyleSheet should be injected at the top of the page.                                                |
| `attributes` | A map of attributes to set for the StyleSheet.                                                                              

<GiscusComments />|