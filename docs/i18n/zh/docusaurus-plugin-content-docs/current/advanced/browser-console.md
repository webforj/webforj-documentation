---
sidebar_position: 15
title: Browser Console
_i18n_hash: 843587956991faa037138ce8e8563e7a
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

使用浏览器控制台打印程序信息是开发过程中的一个重要部分。 
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> 工具类通过日志类型和样式提供的功能改进了日志记录能力。

## Instance {#instance}

通过 `App.console()` 方法获取 `BrowserConsole` 的实例。将任何所需的 `Object` 作为五种日志类型的一种打印：日志、信息、警告、错误或调试。

```java
import static com.webforj.App.console;
// 类型
console().log("日志消息");
console().info("信息消息");
console().warn("警告消息");
console().error("错误消息");
console().debug("调试消息");
```

## Styling {#styling}

使用构建器方法设置日志消息的外观。每个构建器都有选项来更改特定属性。也可以 [混合多种样式](#mixing-styles)。
一旦控制台消息打印，所应用的样式不会继续应用于后续消息，除非*显式*重新定义。

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
使用 `setStyle` 方法更改 `BrowserConsole` 日志中未由构建器指定的属性。
:::

### Background color {#background-color}

使用 `background()` 方法设置背景颜色，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>。
使用颜色命名的方法，如 `blue()`，或者用 `colored(String color)` 选择特定值。

```java
// 背景示例
console().background().blue().log("蓝色背景");
console().background().colored("#031f8f").log("自定义蓝色背景");
```

### Text color {#text-color}

使用 `color()` 方法设置文本颜色，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>。
使用颜色命名的方法，如 `red()`，或者用 `colored(String color)` 选择特定值。

```java
// 颜色示例
console().background().red().log("红色文本");
console().color().colored("#becad2").log("自定义浅蓝灰色文本");
```

### Font size {#font-size}

使用 `size()` 方法设置字体大小，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>。
使用命名为大小的方法，如 `small()`，或者用 `from(String value)` 选择特定值。

```java
// 大小示例
console().size().small().log("小字体");
console().size().from("30px").log("30px 字体");
```
:::tip
`from(String value)` 方法可以接受其他字体大小值，如 rem 和 vw。
:::

### Font style {#font-style}

使用 `style()` 方法设置字体样式，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>。
例如，使用 `italic()` 方法将控制台日志变为斜体。

```java
// 样式示例
console().style().italic().log("斜体字");
console().style().normal().log("正常字体");
```

### Text transformation {#text-transformation}

使用 `transform()` 方法控制消息中字符的大小写，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>。
例如，使用 `capitalize()` 方法将每个单词的首字母转换为大写。

```java
// 转换示例
// 首字母大写文本转化
console().transform().capitalize().log("首字母大写文本转化");
// 大写文本转化
console().transform().uppercase().log("大写文本转化");
```

### Font weight {#font-weight}

使用 `weight()` 方法设置文本的粗细，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>。
例如，使用 `lighter()` 方法使字体比正常字体更轻。

```java
// 粗细示例
console().weight().bold().log("粗体字");
console().weight().lighter().log("轻体字");
```

## Mixing styles {#mixing-styles}
可以混合和匹配方法，以获得自定义日志显示。

```java
// 自定义日志显示的多种选项
console()
  .weight().bolder()
  .size().larger()
  .color().gray()
  .style().italic()
  .transform().uppercase()
  .background().blue()
  .warn("混合样式");
```
