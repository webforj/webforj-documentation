---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: 8dbd7759364573b73d0b1b00c6d7e219
---
CSS **阴影部分**为开发人员提供了一种从外部对组件阴影 DOM 内部元素进行样式设置的方法，同时仍然保持封装性。

## 介绍 {#introduction}

webforJ 组件是使用 [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components) 构建的，这依赖于 [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) 来封装组件的内部结构和样式。

:::tip Web Components
Web Components 是一套技术，允许您创建可重用和封装的自定义元素，以用于网络应用程序。
:::

**阴影 DOM** 防止内部样式和标记泄漏到组件外部，或受外部样式的影响。这种封装确保组件保持自我-contained，减少样式冲突的风险。

:::tip Web Components 封装
封装是 Web Components 的一个关键优势。通过将组件的结构、样式和行为与应用程序的其余部分分开，您可以避免冲突并维护整洁、可维护的代码。
:::

然而，由于这种封装，您 **无法直接使用** 标准 CSS 选择器对阴影 DOM 内部的元素进行样式设置。

例如，`dwc-button` 组件呈现以下结构：

```html {2}
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">Button</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

如果您尝试像这样对 `label` 进行样式设置：

```css
/* 不起作用 */
dwc-button .control__label {
  color: pink;
}
```

它不会有任何效果，因为 `.control__label` 元素位于阴影根内部。

这就是 **CSS 阴影部分** 的作用。

## 使用阴影部分进行样式设置 {#styling-with-shadow-parts}

阴影部分允许外部样式表针对阴影树内部的特定元素进行样式设置，但 **仅当** 这些元素被组件明确标记为“公开”时。

### 如何公开部分 {#how-parts-are-exposed}

要公开一个元素以便外部样式，可以在阴影 DOM 内为其分配 `part` 属性。

所有 webforJ 组件自动公开相关部分以供样式设置。您可以在每个组件文档的 **样式 > 阴影部分** 部分找到支持的部分列表。

例如，`dwc-button` 组件公开了 `prefix`、`label` 和 `suffix` 等部分：

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Button</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

一旦公开，这些部分就可以通过 [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part) 伪元素在组件外部进行样式设置。

### `::part()` 伪元素 {#the-part-pseudo-element}

`::part()` 选择器允许您对已标记为 `part` 属性的阴影 DOM 内的元素应用样式。

例如，要更改 `dwc-button` 中 `label` 部分的颜色：

```css
dwc-button::part(label) {
  color: red;
}
```

您可以将 `::part()` 与其他选择器结合使用，如 `:hover`：

```css
dwc-button::part(label):hover {
  color: pink;
}
```

:::warning ::part() 选择器的限制
您无法选择阴影部分 *内部* 的内容。以下内容 **不起作用**：

```css
/* 不起作用 */
dwc-button::part(label) span {
  /* CSS goes here */
}
```
:::
