---
sidebar_position: 2
title: Themes
_i18n_hash: afb80b03bfe243ffa93d6f72a05809e2
---
webforJ 包含三个内置应用主题，并支持定义您自己的自定义主题。默认主题包括：

- **light**: 明亮的主题，背景为浅色（默认）。
- **dark**: 深色背景，带有主色调的色调。
- **dark-pure**: 完全中性的深色主题，以灰色调为基础。

要在您的应用中应用主题，可以使用 `@AppTheme` 注解或 `App.setTheme()` 方法。主题名称必须是：`system`、`light`、`dark`、`dark-pure` 或自定义主题名称。

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // 应用代码
}

// 或者以编程方式
App.setTheme("dark-pure");
```

## 覆盖默认主题 {#overriding-default-themes}

您可以通过在 `:root` 选择器中重新定义 CSS 自定义属性来覆盖 **light** 主题。

:::info `:root` 伪类
`:root` CSS 伪类定位文档的根元素。在 HTML 中，它表示 `<html>` 元素，并且具有比普通 `html` 选择器更高的特异性。
:::

示例：

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 50;
  --dwc-font-size: var(--dwc-font-size-m);
}
```

要覆盖 **dark** 或 **dark-pure** 主题，请在 `<html>` 元素上使用属性选择器：

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

## 创建自定义主题 {#creating-custom-themes}

您可以使用 `html[data-app-theme='THEME_NAME']` 选择器定义自己的主题。自定义主题可以与默认主题共存，并且您可以在运行时动态切换它们。

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

然后在您的应用中：

```java
@AppTheme("new-theme")
class MyApp extends App {
  // 应用代码
}

// 或者以编程方式
App.setTheme("new-theme");
```

## 组件主题 {#component-themes}

除了应用级主题外，webforJ 组件支持一组基于默认色调的 **组件主题**：`default`、`primary`、`success`、`warning`、`danger`、`info` 和 `gray`。

每个组件在 **样式 → 主题** 部分中记录其支持的主题。
