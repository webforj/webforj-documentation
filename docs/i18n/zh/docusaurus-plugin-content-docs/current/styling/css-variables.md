---
sidebar_position: 1
title: CSS Variables
description: >-
  Define, scope, and consume CSS custom properties to control webforJ component
  styling at runtime without preprocessors.
_i18n_hash: 8e09f9776dc8bb74a1d37e6ba2bdceb0
---
CSS变量在定制webforJ组件外观方面发挥着核心作用。这些变量存储可重用的值，例如颜色、字体大小和间距，可以在您的应用程序中一致地应用。

与依赖于CSS预处理器（如[SASS](https://sass-lang.com/)或[LESS](https://lesscss.org/)）的传统方法不同，CSS变量允许**在运行时动态样式**。它们减少了重复，提高了可维护性，使样式表更易于阅读和管理。

## 定义CSS变量 {#defining-css-variables}

CSS变量使用双短横线（`--`）前缀定义，并可以在任何CSS选择器内作用域。然而，最常见的做法是在`:root`选择器中定义它们，这样可以全局作用。

```css
:root {
  --app-background: orange;
}
```

:::tip `:root`伪类
[`：root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root)伪类针对文档的根元素——通常是HTML中的`<html>`。它的行为类似于`html`，但具有更高的特定性。
:::

CSS变量可以容纳任何字符串，而不仅仅是有效的CSS值。当与JavaScript一起使用时，这种灵活性尤为重要。

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
每个webforJ组件支持一组特定的CSS变量，以控制其外观。这些变量在每个组件的**样式 > CSS属性**部分有文档说明。
:::

## 使用CSS变量 {#using-css-variables}

使用[`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var())函数在样式中应用变量的值：

```css
.panel {
  background-color: var(--app-background);
}
```

您还可以指定后备值，以防变量未定义：

```css
.frame {
  background-color: var(--app-background, red);
}
```

## 使用webforJ操控变量 {#manipulating-variables-with-webforj}

CSS变量可以通过webforJ API动态更新，实现实时样式：

```java
// 设置CSS变量
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip 使用JavaScript操控CSS变量
webforJ允许您使用页面或元素API在客户端执行JavaScript。这意味着您可以像在标准Web应用程序中一样，在运行时动态操控CSS变量。

```javascript
// 设置CSS变量
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// 获取CSS变量
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## 其他资源 {#additional-resources}

- [使用CSS自定义属性（MDN）](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)
- [CSS自定义属性完整指南（CSS-Tricks）](https://css-tricks.com/a-complete-guide-to-custom-properties/)
