---
sidebar_position: 5
title: Browser Console
_i18n_hash: 340e3d6f1d09c67ecc3d2d93bcd23b28
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

使用浏览器控制台打印有价值的程序信息是开发过程中的一个重要部分。<JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> 工具类拥有许多功能来增强日志记录能力。

<!-- :::info
在 `24.10` 之前，`App.consoleLog()` 和 `App.consoleError()` 方法启用了此行为，但它们已经被标记为即将弃用。
::: -->

## 实例 {#instance}

使用 `App.console()` 方法获取 `BrowserConsole` 的实例。可以将任何 `Object` 作为五种日志类型之一打印：日志、信息、警告、错误或调试。

```java
import static com.webforj.App.console;
// 类型
console().log("日志消息");
console().info("信息消息");
console().warn("警告消息");
console().error("错误消息");
console().debug("调试消息");
```

## 样式 {#styling}

使用构建器方法设置日志消息的外观。每个构建器都有选项来更改特定属性。还可以 [混合多种样式](#mixing-styles)。
一旦控制台消息打印，所应用的任何样式将不会传递到后续消息，除非 *明确* 重新定义。

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
使用 `setStyle` 方法更改 `BrowserConsole` 日志的未由构建器指定的属性。
:::

### 背景颜色 {#background-color}

使用 `background()` 方法设置背景颜色，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>。
使用颜色命名的方法，例如 `blue()`，或使用 `colored(String color)` 选择特定值。

```java
// 背景示例
console().background().blue().log("蓝色背景");
console().background().colored("#031f8f").log("自定义蓝色背景");
```

### 文字颜色 {#text-color}

使用 `color()` 方法设置文字颜色，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>。
使用颜色命名的方法，例如 `red()`，或使用 `colored(String color)` 选择特定值。

```java
// 颜色示例
console().background().red().log("红色文本");
console().color().colored("#becad2").log("自定义浅蓝灰色文本");
```

### 字体大小 {#font-size}

使用 `size()` 方法设置字体大小，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>。
使用指定大小的方法，例如 `small()`，或使用 `from(String value)` 选择特定值。

```java
// 大小示例
console().size().small().log("小字体");
console().size().from("30px").log("30px 字体");
```
:::tip
`from(String value)` 方法可以接受其他字体大小值，例如 rem 和 vw。
:::

### 字体样式 {#font-style}

使用 `style()` 方法设置字体样式，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>。
例如，使用 `italic()` 方法使控制台日志倾斜。

```java
// 样式示例
console().style().italic().log("斜体字体");
console().style().normal().log("正常字体");
```

### 文本转换 {#text-transformation}

使用 `transform()` 方法控制消息中文本的字符大写，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>。
例如，使用 `capitalize()` 方法将每个单词的首字母转换为大写。

```java
// 转换示例
// 大写文本转换
console().transform().capitalize().log("大写文本转换");
// 大写文本转换 
console().transform().uppercase().log("大写文本转换");
```

### 字体粗细 {#font-weight}

使用 `weight()` 方法设置文本的粗细，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>。
例如，使用 `lighter()` 方法使字体比正常字体更轻。

```java
// 粗细示例
console().weight().bold().log("粗体字体");
console().weight().lighter().log("更轻的字体");
```

## 混合样式 {#mixing-styles}
可以混合和匹配方法以实现自定义日志显示。

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
