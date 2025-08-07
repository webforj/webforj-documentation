---
sidebar_position: 2
title: Themes
_i18n_hash: aa6eb0baae2b881b24c45ae970a079dc
---
webforJ 包含三个内置应用主题，并支持定义您自己的自定义主题。默认主题为：

- **light**：具有明亮背景的亮主题（默认）。
- **dark**：使用主色调的深色背景。
- **dark-pure**：基于灰色调的完全中性的深色主题。

要在您的应用中应用主题，请使用 `@AppTheme` 注解或 `App.setTheme()` 方法。主题名称必须是以下之一：`system`、`light`、`dark`、`dark-pure`，或自定义主题名称。

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // 应用代码
}

// 或程序matically
App.setTheme("dark-pure");
```

## 覆盖默认主题 {#overriding-default-themes}

您可以通过在 `:root` 选择器中重新定义 CSS 自定义属性来覆盖 **light** 主题。

:::info `:root` 伪类
`:root` CSS 伪类用于定位文档的根元素。在 HTML 中，它表示 `<html>` 元素，并具有比普通 `html` 选择器更高的优先级。
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

您可以使用 `html[data-app-theme='THEME_NAME']` 选择器定义自己的主题。自定义主题可以与默认主题共存，您可以在运行时动态切换它们。

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

// 或程序matically
App.setTheme("new-theme");
```

## 组件主题 {#component-themes}

除了应用级主题外，webforJ 组件还支持基于默认色板的一组 **组件主题**：`default`、`primary`、`success`、`warning`、`danger`、`info` 和 `gray`。

每个组件都在 **样式 → 主题** 部分记录其支持的主题。
