---
sidebar_position: 15
title: Browser Console
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

Using the browser's console to print valuable program information is an integral part of the development process. The <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> utility class comes with a slew of features to enhance logging capabilities.

<!-- :::info
Before `24.10`, the `App.consoleLog()` and `App.consoleError()` methods enabled this behavior, but they have since been marked for deprecation.
::: -->

## Instance

Get an instance of `BrowserConsole` using the `App.console()` method. Print any `Object` desired as one of the five log types: log, info, warn, error, or debug.

```java
import static com.webforj.App.console;
// Types
console().log("Log message");
console().info("Info message");
console().warn("Warn message");
console().error("Error message");
console().debug("Debug message");
```

## Styling

Use the builder methods to set the appearance of the log message. Each builder has options to change a specific property. It's also possible to [mix multiple styles](#mixing-styles).
Once a console message prints, any styling applied won't carry over to subsequent messages unless *explicitly* redefined.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Use the `setStyle` method to change the properties of the `BrowserConsole` log not specified by the builders.
:::

### Background color 

Set the background color with the `background()` method, which returns the <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>.
Use methods named by color, like `blue()`, or choose a specific value with `colored(String color)`.

```java
// Background Examples
console().background().blue().log("Blue background");
console().background().colored("#031f8f").log("Custom blue background");
```

### Text color

Set the text color with the `color()` method, which returns the <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>.
Use methods named by color, like `red()`, or choose a specific value with `colored(String color)`.

```java
// Color Examples
console().background().red().log("Red text");
console().color().colored("#becad2").log("Custom light bluish-gray text");
```

### Font size

Set the font size with the `size()` method, which returns the <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>.
Use methods named by a size, like `small()`, or choose a specific value with `from(String value)`.

```java
//Size Examples
console().size().small().log("Small font");
console().size().from("30px").log("30px font");
```
:::tip
The `from(String value)` method can take other font size values, such as rem and vw.
:::

### Font style

Set the font style with the `style()` method, which returns the <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>.
For example, use the `italic()` method to make the console log italicized.

```java
// Style Examples
console().style().italic().log("Italic font");
console().style().normal().log("Normal font");
```

### Text transformation

Control the capitalization of the characters in a message with the `transform()` method, which returns the <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>.
For example, use the `capitalize()` method to transform the first letter of each word to uppercase.

```java
// Transform Examples
// Capitalize Text Transformation
console().transform().capitalize().log("Capitalize text transformation");
// UPPERCASE TEXT TRANSFORMATION 
console().transform().uppercase().log("Uppercase text transformation");
```

### Font weight

Set how thick the text is with the `weight()` method, which returns the <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>.
For example, use the `ligther()` method to make the font lighter than normal.

```java
// Weight Examples
console().weight().bold().log("Bold font");
console().weight().lighter().log("Lighter font");
```

## Mixing styles
It's possible to mix and match methods for a custom logging display.

```java
// A variety of options for custom logging display
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Mixing styles");
```