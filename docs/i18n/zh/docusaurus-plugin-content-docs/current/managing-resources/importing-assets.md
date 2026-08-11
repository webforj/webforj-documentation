---
sidebar_position: 1
title: Importing Assets
description: >-
  Attach JavaScript and CSS to webforJ components or the app using JavaScript,
  InlineJavaScript, StyleSheet, and InlineStyleSheet annotations.
_i18n_hash: 4343173cf0cbef440f24c05cf9ee3fbd
---
资产注释提供了一种声明性的方法来静态嵌入应用程序中的外部和内联资源，例如 JavaScript 和 CSS。这些注释通过确保在适当的执行阶段加载依赖项，从而简化了资源管理，减少了手动配置并增强了可维护性。

:::tip 打包器是 npm 和框架的默认选择
资产注释可以附加您已有的脚本或样式表，无需构建步骤。要引入 npm 包，您可以使用 React 等组件框架或 SCSS 等样式表语言，使用 [前端打包器](/docs/managing-resources/bundler/overview)。这是执行该工作的默认路径，并且它执行注释所做的一切。
:::

## 导入 JavaScript 文件 {#importing-javascript-files}

声明性 JavaScript 包含通过 `@JavaScript` 注释支持，能够自动加载依赖项。该注释可以应用于组件级别和应用级别。

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

该注释接受相对路径或完整路径，在应用程序中加载。这将作为 [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) 标签插入到 DOM 中。此外，注释支持以下属性：

| 属性        | 类型     | 描述                                                                                                                                   | 默认    |
| ----------- | -------- | -------------------------------------------------------------------------------------------------------------------------------------- | ------- |
| `top`       | Boolean  | 指定脚本是否应该注入到顶级窗口                                                                                                        | `false` |
| `attributes`| Object   | 一组 <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>属性</JavadocLink> 应用于脚本。               | `{}`    |

#### 示例: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
仅当声明注释的组件附加到容器时，文件才会加载。如果多个组件加载相同的文件，该文件只会被注入一次。
:::

## 注入 JavaScript {#injecting-javascript}

在某些情况下，您可能希望将 JavaScript 代码直接注入到 DOM 中，而不是提供 JavaScript 路径。`InlineJavaScript` 注释允许您注入 JavaScript 内容。

```java
@InlineJavaScript("alert('I am an inline script!');")
@JavaScript("context://js/app.js")
```

| 属性        | 类型      | 描述                                                               | 默认    |
| ----------- | --------- | ------------------------------------------------------------------ | ------- |
| `top`       | `Boolean` | 指定脚本是否应注入到顶级窗口                                       | `false` |
| `attributes`| `Object`  | 应用于脚本的属性                                                  | `{}`    |
| `id`        | `String`  | 确保单个注入的唯一资源 ID                                         | `""`    |

:::warning
使用 `InlineJavaScript` 可以多次注入脚本，除非使用 `id` 属性分配特定 ID。
:::

## 导入 CSS 文件 {#importing-css-files}

声明性 CSS 包含通过 `@StyleSheet` 注释支持，能够自动加载依赖项。该注释可以应用于组件级别和应用级别。

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| 属性        | 类型      | 描述                                                                       | 默认    |
| ----------- | --------- | -------------------------------------------------------------------------- | ------- |
| `top`       | Boolean   | 指定样式表是否应注入到顶级窗口                                           | `false` |
| `attributes`| Object    | 应用于样式表的属性                                                        | `{}`    |

#### 示例: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
仅当声明注释的组件附加到容器时，文件才会加载。每个文件仅加载一次。
:::

## 注入 CSS {#injecting-css}

`InlineStyleSheet` 注释允许您在组件级别和应用级别直接注入 CSS 内容到网页中。

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| 属性        | 类型      | 描述                                                                                                               | 默认   |
| ----------- | --------- | ----------------------------------------------------------------------------------------------------------------- | ------ |
| `top`       | Boolean   | 指定样式表是否应注入到页面的顶层窗口                                                                              | `false` |
| `attributes`| Object    | 一组 [属性](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) 应用于样式元素                        | `{}`   |
| `id`        | String    | 一个唯一的资源 ID。如果多个资源具有相同的 ID，则它们将一起打包到单个样式元素中。                                  | `""`   |
| `once`      | Boolean   | 确定样式表是否仅在页面中注入一次，而不管多个组件实例的存在                                                      | `true` |

:::tip
为了在编写组件的内联 CSS 时获得更好的语法高亮，您可以使用 webforJ VS Code 扩展：[Java HTML CSS 语法高亮](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html)。
:::

## 运行时动态资产 {#dynamic-assets-at-runtime}

通过在运行时程序性注入 JavaScript 和 CSS，可以实现动态资源管理。您可以根据运行时上下文加载或注入资源。

### 加载和注入 JavaScript {#loading-and-injecting-javascript}

使用 <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink> 在运行时动态加载或注入 JavaScript。这样您可以从 URL 加载脚本或将内联脚本直接注入 DOM。

```java
Page page = Page.getCurrent();

// 加载 JavaScript 文件
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// 注入内联 JavaScript
page.addInlineJavaScript("console.log('Runtime Injection');");
page.addInlineJavaScript("alert('This script runs inline');");
```

| 参数        | 描述                                                                                                          |
| ----------- | -------------------------------------------------------------------------------------------------------------- |
| `script`    | 要注入的 URL 或内联脚本内容。以 `context://` 开头的 URL 将解析到应用的根资源文件夹。                          |
| `top`       | 确定脚本是否应注入到页面顶部。                                                                                |
| `attributes`| 要为脚本设置的属性映射。                                                                                     |

### 加载和注入 CSS {#loading-and-injecting-css}

使用 <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink> 在运行时动态加载或注入 CSS。这使您可以从 URL 加载样式表或将内联样式直接注入 DOM。

```java
Page page = Page.getCurrent();

// 加载 CSS 文件
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// 注入内联 CSS
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| 参数        | 描述                                                                                                      |
| ----------- | ---------------------------------------------------------------------------------------------------------- |
| `stylesheet`| 要注入的 URL 或内联样式表内容。以 `context://` 开头的 URL 将解析到应用的根资源文件夹。                   |
| `top`       | 确定样式表是否应注入到页面顶部。                                                                          |
| `attributes`| 要为样式表设置的属性映射。                                                                                |
