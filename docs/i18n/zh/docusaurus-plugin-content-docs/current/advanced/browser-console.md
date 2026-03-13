---
sidebar_position: 15
title: Browser Console
_i18n_hash: fd0e46761a5fd8b887a39b7a51e9b66b
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

使用浏览器控制台打印程序信息是开发过程的一个重要部分。
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> 工具类提供了通过日志类型和样式改善日志记录能力的功能。

## 实例 {#instance}

使用 `App.console()` 方法获取 `BrowserConsole` 的一个实例。可以将任何 `Object` 作为五种日志类型之一打印：log, info, warn, error, 或 debug。

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

使用构建器方法设置日志消息的外观。每个构建器都有选项来更改特定属性。也可以 [混合多种样式](#mixing-styles)。
一旦控制台消息打印，任何应用的样式不会转移到后续消息，除非*显式*重新定义。

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
使用 `setStyle` 方法更改 `BrowserConsole` 日志中未通过构建器指定的属性。
:::

### 背景颜色 {#background-color}

使用 `background()` 方法设置背景颜色，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>。
使用按颜色命名的方法，例如 `blue()`，或使用 `colored(String color)` 选择特定值。

```java
// 背景示例
console().background().blue().log("蓝色背景");
console().background().colored("#031f8f").log("自定义蓝色背景");
```

### 文字颜色 {#text-color}

使用 `color()` 方法设置文字颜色，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>。
使用按颜色命名的方法，例如 `red()`，或使用 `colored(String color)` 选择特定值。

```java
// 颜色示例
console().background().red().log("红色文字");
console().color().colored("#becad2").log("自定义浅蓝灰色文字");
```

### 字体大小 {#font-size}

使用 `size()` 方法设置字体大小，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>。
使用按大小命名的方法，例如 `small()`，或使用 `from(String value)` 选择特定值。

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
例如，使用 `italic()` 方法使控制台日志变为斜体。

```java
// 样式示例
console().style().italic().log("斜体字体");
console().style().normal().log("正常字体");
```

### 文字转换 {#text-transformation}

通过 `transform()` 方法控制消息中字符的大小写，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>。
例如，使用 `capitalize()` 方法将每个单词的首字母转换为大写。

```java
// 转换示例
// 大写文本转换
console().transform().capitalize().log("大写文本转换");
// 全部大写文本转换 
console().transform().uppercase().log("全部大写文本转换");
```

### 字体粗细 {#font-weight}

使用 `weight()` 方法设置文本的粗细，该方法返回 <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>。
例如，使用 `ligther()` 方法使字体比正常字体更轻。

```java
// 粗细示例
console().weight().bold().log("粗体字体");
console().weight().lighter().log("更轻的字体");
```

## 混合样式 {#mixing-styles}
可以混合和匹配方法以实现自定义的日志显示。

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
