---
sidebar_position: 1
title: 导入资产
_i18n_hash: a2aecab2ea12370f1e494703c2ec05af
---
资产注释提供了一种声明性方法，可以在应用中静态嵌入外部和内联资源，如 JavaScript 和 CSS。这些注释通过确保依赖项在适当的执行阶段加载，从而简化了资源管理，减少了手动配置，提高了可维护性。

## 导入 JavaScript 文件 {#importing-javascript-files}

支持通过 `@JavaScript` 注释进行声明性 JavaScript 包含，从而实现自动依赖加载。该注释可以在组件级别和应用级别使用。

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

该注释接受一个相对路径或完整路径以在应用中加载。这将作为 [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) 标签插入到 DOM 中。此外，该注释支持以下属性：

| 属性         | 类型     | 描述                                                                                                                                   | 默认值   |
| ------------ | ------- | -------------------------------------------------------------------------------------------------------------------------------------- | ------- |
| `top`        | Boolean | 指定脚本是否应注入到顶层窗口                                                                                                            | `false` |
| `attributes` | Object  | 要应用于脚本的一组 <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>属性</JavadocLink>。        | `{}`    |

#### 示例: {#example}

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
文件仅在声明注释的组件附加到容器时加载。如果多个组件加载相同的文件，该文件只会被注入一次。
:::

## 注入 JavaScript {#injecting-javascript}

在某些情况下，您可能希望直接将 JavaScript 代码注入到 DOM 中，而不是提供 JavaScript 路径。`InlineJavaScript` 注释允许您注入 JavaScript 内容。

```java
@InlineJavaScript("alert('I am an inline script!');")
@JavaScript("context://js/app.js")
```

| 属性         | 类型      | 描述                                                                 | 默认值   |
| ------------ | ------- | --------------------------------------------------------------------- | ------- |
| `top`        | `Boolean` | 指定脚本是否应注入到顶层窗口                                         | `false` |
| `attributes` | `Object`  | 要应用于脚本的属性                                                   | `{}`    |
| `id`         | `String`  | 唯一资源 ID，以确保单个注入                                          | `""`    |

:::warning
使用 `InlineJavaScript` 可以多次注入脚本，除非通过 `id` 属性分配了特定 ID。
:::

## 导入 CSS 文件 {#importing-css-files}

支持通过 `@StyleSheet` 注释进行声明性 CSS 包含，从而实现自动依赖加载。该注释可以在组件级别和应用级别使用。

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| 属性         | 类型     | 描述                                                                     | 默认值   |
| ------------ | ------- | ------------------------------------------------------------------------- | ------- |
| `top`        | Boolean | 指定样式表是否应注入到顶层窗口                                         | `false` |
| `attributes` | Object  | 要应用于样式表的属性                                                     | `{}`    |

#### 示例: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
文件仅在声明注释的组件附加到容器时加载。每个文件仅加载一次。
:::

## 注入 CSS {#injecting-css}

`InlineStyleSheet` 注释允许您在组件级别和应用级别直接将 CSS 内容注入到网页中。

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| 属性         | 类型     | 描述                                                                                                           | 默认值   |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------- | ------- |
| `top`        | Boolean | 指定样式表是否应注入到页面的顶层窗口。                                                                         | `false` |
| `attributes` | Object  | 要应用于样式元素的一组 [属性](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style)。              | `{}`    |
| `id`         | String  | 唯一资源 ID。如果多个资源具有相同的 ID，它们将被捆绑在一个样式元素中。                                          | `""`    |
| `once`       | Boolean | 确定样式表是否只应注入到页面一次，而不管多个组件实例的情况。                                               | `true`  |

:::tip
为了在为组件编写内联 CSS 时获得更好的语法高亮，您可以使用 webforJ VS Code 扩展：[Java HTML CSS 语法高亮](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html)。
:::

## 运行时的动态资源 {#dynamic-assets-at-runtime}

通过在运行时编程注入 JavaScript 和 CSS，可以实现动态资源管理。您可以根据运行时上下文加载或注入资源。

### 加载和注入 JavaScript {#loading-and-injecting-javascript}

使用 <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink> 动态加载或注入 JavaScript。这允许您从 URL 加载脚本或直接将内联脚本注入到 DOM 中。

```java
Page page = Page.getCurrent();

// 加载 JavaScript 文件
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// 注入内联 JavaScript
page.addInlineJavaScript("console.log('Runtime Injection');");
page.addInlineJavaScript("alert('This script runs inline');");
```

| 参数         | 描述                                                                                                               |
| ------------ | ------------------------------------------------------------------------------------------------------------------ |
| `script`     | 要注入的 URL 或内联脚本内容。以 `context://` 开头的 URL 解析为应用的根资源文件夹。                          |
| `top`        | 确定脚本是否应注入到页面顶端。                                                                                     |
| `attributes` | 要为脚本设置的属性映射。                                                                                           |

### 加载和注入 CSS {#loading-and-injecting-css}

使用 <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink> 动态加载或注入 CSS。这允许您从 URL 加载样式表或直接将内联样式注入到 DOM 中。

```java
Page page = Page.getCurrent();

// 加载 CSS 文件
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// 注入内联 CSS
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| 参数         | 描述                                                                                                                   |
| ------------ | -------------------------------------------------------------------------------------------------------------------- |
| `stylesheet` | 要注入的 URL 或内联样式表内容。以 `context://` 开头的 URL 解析为应用的根资源文件夹。                           |
| `top`        | 确定样式表是否应注入到页面的顶端。                                                                                     |
| `attributes` | 要为样式表设置的属性映射。                                                                                           |
