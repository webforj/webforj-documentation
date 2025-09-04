---
sidebar_position: 1
title: CSS Variables
_i18n_hash: b753c1b13cfcc45f72d6712e980ef952
---
CSS 变量在自定义 webforJ 组件外观中发挥着核心作用。这些变量存储可重用的值，如颜色、字体大小和间距，可以在您的应用中一致地应用。

与传统方法（如 [SASS](https://sass-lang.com/) 或 [LESS](https://lesscss.org/) 等 CSS 预处理器）不同，CSS 变量允许 **在运行时动态样式**。它们减少了重复，提高了可维护性，使样式表更易于阅读和管理。

## 定义 CSS 变量 {#defining-css-variables}

CSS 变量使用双破折号 (`--`) 前缀定义，可以在任何 CSS 选择器内进行作用域限制。然而，最常见的做法是在 `:root` 选择器中定义它们，从而使其全局作用域。

```css
:root {
  --app-background: orange;
}
```

:::tip `:root` 伪类
[` :root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) 伪类针对文档的根元素——通常是 HTML 中的 `<html>`。它的行为类似于 `html`，但具有更高的特异性。
:::

CSS 变量可以容纳任何字符串，而不仅仅是有效的 CSS 值。这种灵活性在与 JavaScript 一起使用时尤其有用。

```css
html {
  --app-title: webforJ;
}
```

## 组件特定变量 {#component-specific-variables}

要为特定组件定义或覆盖变量，请在组件的选择器内声明它：

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip 组件特定样式参考
每个 webforJ 组件支持一组特定的 CSS 变量，以控制其外观。这些在每个组件的 **样式 > CSS 属性** 部分中有所记录。
:::


## 使用 CSS 变量 {#using-css-variables}

使用 [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) 函数在样式中应用变量的值：

```css
.panel {
  background-color: var(--app-background);
}
```

您还可以指定一个备用值，以防变量未定义：

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
webforJ 允许您使用页面或元素 API 在客户端执行 JavaScript。这意味着您可以像在标准 Web 应用程序中一样在运行时动态操作 CSS 变量。

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
- [CSS 属性的完整指南 (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
