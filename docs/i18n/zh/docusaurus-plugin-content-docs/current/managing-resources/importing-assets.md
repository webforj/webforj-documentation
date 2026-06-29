---
sidebar_position: 1
title: Importing Assets
description: >-
  Attach JavaScript and CSS to webforJ components or the app using JavaScript,
  InlineJavaScript, StyleSheet, and InlineStyleSheet annotations.
_i18n_hash: 8ebb2cb8863f97fcddea40fac13f71ce
---
资产注释提供了一种声明性的方法，可以在应用程序中静态嵌入外部和内联资源，例如JavaScript和CSS。这些注释通过确保在适当的执行阶段加载依赖项，从而简化资源管理，减少手动配置，提高可维护性。

:::tip 打包器是 npm 和框架的默认选项
资产注释附加了您已经拥有的脚本或样式表，没有构建步骤。要引入 npm 包，使用像 React 这样的组件框架，或样式表语言如 SCSS，请使用 [前端打包器](/docs/managing-resources/bundler/overview)。这是执行该工作的默认路径，能够完成注释所做的一切。
:::

## 导入 JavaScript 文件 {#importing-javascript-files}

通过 `@JavaScript` 注释支持声明性 JavaScript 包含，实现自动依赖加载。该注释可以在组件级别和应用级别应用。

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

该注释接受相对路径或完整路径，以在应用程序中加载。这将作为 [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) 标签插入到 DOM 中。此外，该注释支持以下属性：

| 属性         | 类型     | 描述                                                                                                                                       | 默认值  |
| ------------ | -------- | ------------------------------------------------------------------------------------------------------------------------------------------ | ------- |
| `top`        | Boolean  | 指定脚本是否应注入到顶级窗口                                                                                                               | `false` |
| `attributes` | Object   | 要应用于脚本的一组 <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>属性</JavadocLink>。          | `{}`    |

#### 示例: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
只有当声明注释的组件附加到一个容器时，文件才会被加载。如果多个组件加载相同的文件，该文件只会注入一次。
:::

## 注入 JavaScript {#injecting-javascript}

在某些情况下，您可能希望直接将 JavaScript 代码注入到 DOM 中，而不是提供 JavaScript 路径。`InlineJavaScript` 注释允许您注入 JavaScript 内容。

```java
@InlineJavaScript("alert('我是一段内联脚本！');")
@JavaScript("context://js/app.js")
```

| 属性         | 类型      | 描述                                                               | 默认值  |
| ------------ | --------- | ------------------------------------------------------------------ | ------- |
| `top`        | `Boolean` | 指定脚本是否应注入到顶级窗口                                       | `false` |
| `attributes` | `Object`  | 要应用于脚本的属性                                                 | `{}`    |
| `id`         | `String`  | 用于确保单次注入的唯一资源 ID                                      | `""`    |

:::warning
使用 `InlineJavaScript` 可以多次注入脚本，除非使用 `id` 属性分配特定 ID。
:::

## 导入 CSS 文件 {#importing-css-files}

通过 `@StyleSheet` 注释支持声明性 CSS 包含，实现自动依赖加载。该注释可以在组件级别和应用级别应用。

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| 属性         | 类型     | 描述                                                                   | 默认值  |
| ------------ | -------- | ---------------------------------------------------------------------- | ------- |
| `top`        | Boolean  | 指定样式表是否应注入到顶级窗口                                       | `false` |
| `attributes` | Object   | 要应用于样式表的属性                                                 | `{}`    |

#### 示例: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
只有当声明注释的组件附加到一个容器时，文件才会被加载。每个文件只加载一次。
:::

## 注入 CSS {#injecting-css}

`InlineStyleSheet` 注释允许您直接在组件级别和应用级别向网页注入 CSS 内容。

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| 属性         | 类型     | 描述                                                                                                               | 默认值  |
| ------------ | -------- | ----------------------------------------------------------------------------------------------------------------- | ------- |
| `top`        | Boolean  | 指定样式表是否应注入到页面的顶层窗口。                                                                           | `false` |
| `attributes` | Object   | 要应用于样式元素的一组 [属性](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style)。                  | `{}`    |
| `id`         | String   | 唯一资源 ID。如果多个资源具有相同的 ID，它们将合并在一个样式元素中。                                            | `""`    |
| `once`       | Boolean  | 确定样式表是否应只注入一次，无论组件实例的数量。                                                                | `true`  |

:::tip 
为了在编写组件的内联 CSS 时获得更好的语法高亮，可以使用 webforJ VS Code 扩展：[Java HTML CSS 语法高亮](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html)。
:::

## 运行时的动态资产 {#dynamic-assets-at-runtime}

通过在运行时编程注入 JavaScript 和 CSS，动态资源管理成为可能。您可以根据运行时上下文加载或注入资源。

### 加载和注入 JavaScript {#loading-and-injecting-javascript}

使用 <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink> 在运行时动态加载或注入 JavaScript。这允许您从 URL 加载脚本或直接将内联脚本注入到 DOM 中。

```java
Page page = Page.getCurrent();

// 加载 JavaScript 文件
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// 注入内联 JavaScript
page.addInlineJavaScript("console.log('运行时注入');");
page.addInlineJavaScript("alert('此脚本以内联方式运行');");
```

| 参数        | 描述                                                                                           |
| ----------- | ---------------------------------------------------------------------------------------------- |
| `script`    | 要注入的 URL 或内联脚本内容。以 `context://` 开头的 URL 解析为应用的根资源文件夹。          |
| `top`       | 确定脚本是否应注入到页面顶部。                                                                  |
| `attributes`| 要为脚本设置的属性映射。                                                                        |

### 加载和注入 CSS {#loading-and-injecting-css}

使用 <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink> 在运行时动态加载或注入 CSS。这允许您从 URL 加载样式表或直接将内联样式注入到 DOM 中。

```java
Page page = Page.getCurrent();

// 加载 CSS 文件
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// 注入内联 CSS
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| 参数        | 描述                                                                                                  |
| ----------- | ----------------------------------------------------------------------------------------------------- |
| `stylesheet`| 要注入的 URL 或内联样式表内容。以 `context://` 开头的 URL 解析为应用的根资源文件夹。                |
| `top`       | 确定样式表是否应注入到页面顶部。                                                                       |
| `attributes`| 要设置的样式表属性的映射。                                                                             |
