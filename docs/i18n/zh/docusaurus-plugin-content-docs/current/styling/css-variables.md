---
sidebar_position: 1
title: CSS Variables
_i18n_hash: f81f4fd4afdcb9807e10b8a35e244b20
---
CSS 变量在自定义 webforJ 组件的外观中起着核心作用。这些变量存储可重用的值，如颜色、字体大小和间距，可以在应用程序中一致地应用。

与传统依赖于 CSS 预处理器如 [SASS](https://sass-lang.com/) 或 [LESS](https://lesscss.org/) 的方法不同，CSS 变量允许在运行时进行 **动态样式**。它们减少了重复，提高了可维护性，使样式表更易于阅读和管理。

## 定义 CSS 变量 {#defining-css-variables}

CSS 变量使用双破折号 (`--`) 前缀定义，并可以在任何 CSS 选择器内作用域化。然而，最常见的做法是在 `:root` 选择器中定义它们，从而在全局作用域内定义。

```css
:root {
  --app-background: orange;
}
```

:::tip `:root` 伪类
[`：root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) 伪类针对文档的根元素——通常是在 HTML 中的 `<html>`。它的行为类似于 `html`，但具有更高的优先级。
:::

CSS 变量可以持有任何字符串，而不仅仅是有效的 CSS 值。这种灵活性在与 JavaScript 一起使用时尤其有用。

```css
html {
  --app-title: webforJ;
}
```

## 组件特定变量 {#component-specific-variables}

要为特定组件定义或覆盖变量，请在该组件的选择器内声明它：

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip 组件特定样式参考
每个 webforJ 组件支持一组特定的 CSS 变量，以控制其外观。这些变量在每个组件的 **样式 > CSS 属性** 部分有文档说明。
:::

## 使用 CSS 变量 {#using-css-variables}

使用 [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) 函数在样式中应用变量的值：

```css
.panel {
  background-color: var(--app-background);
}
```

您还可以在变量未定义的情况下指定后备值：

```css
.frame {
  background-color: var(--app-background, red);
}
```

## 使用 webforJ 操作变量 {#manipulating-variables-with-webforj}

CSS 变量可以通过 webforJ API 动态更新，实现实时样式：

```java
// 设置 CSS 变量
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip 使用 JavaScript 操作 CSS 变量
webforJ 允许您在客户端使用 Page 或 Element API 执行 JavaScript。这意味着您可以像在标准 Web 应用程序中一样，在运行时动态操作 CSS 变量。

```javascript
// 设置 CSS 变量
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// 获取 CSS 变量
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## 其他资源 {#additional-resources}

- [使用 CSS 自定义属性 (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [自定义属性的完整指南 (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
