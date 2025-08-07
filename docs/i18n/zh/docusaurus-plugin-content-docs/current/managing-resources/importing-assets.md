---
sidebar_position: 1
title: Importing Assets
_i18n_hash: 356ee4ac3242f607ead40f76311ead79
---
资产注解提供了一种声明性的方法，可以在应用程序中静态嵌入外部和内联资源，如JavaScript和CSS。这些注解通过确保依赖项在适当的执行阶段加载，从而简化资源管理，减少手动配置，并提高可维护性。

## 导入JavaScript文件 {#importing-javascript-files}

通过`@JavaScript`注解支持声明性JavaScript包含，启用自动依赖加载。该注解可以在组件级别和应用级别应用。

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

该注解接受要在应用程序中加载的相对路径或完整路径。这将作为[`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script)标签插入到DOM中。此外，该注解支持以下属性：

| 属性         | 类型      | 描述                                                                                                                                   | 默认   |
| ------------ | --------- | -------------------------------------------------------------------------------------------------------------------------------------- | ------ |
| `top`        | Boolean   | 指定脚本是否应注入到顶级窗口                                                                                                          | `false` |
| `attributes` | Object    | 要应用于脚本的一组<JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>属性</JavadocLink>。        | `{}`   |

#### 示例: {#example}

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
文件仅在声明注解的组件附加到容器时加载。如果多个组件加载相同的文件，则该文件仅注入一次。
:::

## 注入JavaScript {#injecting-javascript}

在某些情况下，您可能希望直接将JavaScript代码注入到DOM中，而不是提供JavaScript路径。`InlineJavaScript`注解允许您注入JavaScript内容。

```java
@InlineJavaScript("alert('I am an inline script!');")
@JavaScript("context://js/app.js")
```

| 属性         | 类型      | 描述                                                               | 默认   |
| ------------ | --------- | ------------------------------------------------------------------ | ------ |
| `top`        | `Boolean` | 指定脚本是否应注入到顶级窗口                                        | `false` |
| `attributes` | `Object`  | 要应用于脚本的属性                                                 | `{}`   |
| `id`         | `String`  | 以确保单次注入的唯一资源ID                                         | `""`   |

:::warning
使用`InlineJavaScript`可以多次注入脚本，除非使用`id`属性分配特定的ID。
:::

## 导入CSS文件 {#importing-css-files}

通过`@StyleSheet`注解支持声明性CSS包含，启用自动依赖加载。该注解可以在组件级别和应用级别应用。

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| 属性         | 类型      | 描述                                                                   | 默认   |
| ------------ | --------- | ---------------------------------------------------------------------- | ------ |
| `top`        | Boolean   | 指定样式表是否应注入到顶级窗口                                        | `false` |
| `attributes` | Object    | 应用到样式表的属性                                                   | `{}`   |

#### 示例: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
文件仅在声明注解的组件附加到容器时加载。每个文件仅加载一次。
:::

## 注入CSS {#injecting-css}

`InlineStyleSheet`注解允许您在组件级别和应用级别直接将CSS内容注入到网页中。

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| 属性         | 类型      | 描述                                                                                                                   | 默认   |
| ------------ | --------- | ---------------------------------------------------------------------------------------------------------------------- | ------ |
| `top`        | Boolean   | 指定样式表是否应注入到页面的顶级窗口。                                                                                 | `false` |
| `attributes` | Object    | 一组[属性](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style)，应用于样式元素。                             | `{}`   |
| `id`         | String    | 唯一资源ID。如果多个资源具有相同的ID，它们将在一个样式元素中捆绑在一起。                                                  | `""`   |
| `once`       | Boolean   | 决定样式表是否只应注入到页面一次，无论组件实例的多个情况。                                                             | `true`  |

:::tip
为了在编写组件的内联CSS时获得更好的语法高亮，您可以使用webforJ VS Code扩展：[Java HTML CSS语法高亮](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html)。
:::

## 运行时动态资产 {#dynamic-assets-at-runtime}

通过在运行时以编程方式注入JavaScript和CSS，可以实现动态资源管理。您可以根据运行时上下文加载或注入资源。

### 加载和注入JavaScript {#loading-and-injecting-javascript}

使用<JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>动态加载或注入JavaScript。这样您可以从URL加载脚本或直接将内联脚本注入到DOM中。

```java
Page page = Page.getCurrent();

// 加载JavaScript文件
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// 注入内联JavaScript
page.addInlineJavaScript("console.log('Runtime Injection');");
page.addInlineJavaScript("alert('This script runs inline');");
```

| 参数         | 描述                                                                                                                 |
| ------------ |-----------------------------------------------------------------------------------------------------------------------|
| `script`     | 要注入的URL或内联脚本内容。以`context://`开头的URL解析为应用程序的根资源文件夹。                                         |
| `top`        | 决定脚本是否应注入到页面的顶部。                                                                                       |
| `attributes` | 要为脚本设置的属性的映射。                                                                                              |

### 加载和注入CSS {#loading-and-injecting-css}

使用<JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>动态加载或注入CSS。这样您可以从URL加载样式表或直接将内联样式注入到DOM中。

```java
Page page = Page.getCurrent();

// 加载CSS文件
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// 注入内联CSS
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| 参数         | 描述                                                                                                               |
| ------------ |-------------------------------------------------------------------------------------------------------------------|
| `stylesheet` | 要注入的URL或内联样式表内容。以`context://`开头的URL解析为应用程序的根资源文件夹。                                    |
| `top`        | 决定样式表是否应注入到页面的顶部。                                                                                  |
| `attributes` | 要为样式表设置的属性的映射。                                                                                          |
