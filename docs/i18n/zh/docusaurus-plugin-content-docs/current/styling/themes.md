---
sidebar_position: 2
title: Themes
_i18n_hash: afbc96c2eb0da1c5e0eb2e24a69827c2
---
webforJ 包含三种内置应用主题，并支持定义自定义主题。默认主题包括：

- **light**: 一种明亮的主题，背景为浅色（默认）。
- **dark**: 背景为暗色，带有主色调。
- **dark-pure**: 完全中性的深色主题，基于灰色调。

要在应用中应用主题，请使用 `@AppTheme` 注解或 `App.setTheme()` 方法。主题名称必须是以下之一：`system`、`light`、`dark`、`dark-pure`，或自定义主题名称。

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // 应用代码
}

// 或以编程方式
App.setTheme("dark-pure");
```

## 覆盖默认主题 {#overriding-default-themes}

您可以通过在 `:root` 选择器中重新定义 CSS 自定义属性来覆盖 **light** 主题。

:::info `:root` 伪类
`:root` CSS 伪类用于目标文档的根元素。在 HTML 中，它表示 `<html>` 元素，具有比普通 `html` 选择器更高的优先级。
:::

示例：

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

要覆盖 **dark** 或 **dark-pure** 主题，请在 `<html>` 元素上使用属性选择器：

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 80%;
}
```

## 创建自定义主题 {#creating-custom-themes}

您可以使用 `html[data-app-theme='THEME_NAME']` 选择器定义自己的主题。自定义主题可以与默认主题共存，您可以在运行时动态切换它们。

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

要使自定义主题变为深色，请设置 `--dwc-dark-mode: 1` 和 `color-scheme: dark`：

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

然后在您的应用中：

```java
@AppTheme("new-theme")
class MyApp extends App {
  // 应用代码
}

// 或以编程方式
App.setTheme("new-theme");
```

## 组件主题 {#component-themes}

除了应用级主题，webforJ 组件支持一组基于默认调色板的 **组件主题**：`default`、`primary`、`success`、`warning`、`danger`、`info` 和 `gray`。

每个组件在 **Styling → Themes** 部分记录其支持的主题。
