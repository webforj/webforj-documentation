---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: bad90a86a29eaf34485d5ee9150aacb3
---
CSS **阴影部分** 为开发人员提供了一种从外部样式化组件的阴影 DOM 中元素的方法，同时仍然保持封装。

## 介绍 {#introduction}

webforJ 组件是使用 [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components) 构建的，这些组件依赖于 [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) 来封装组件的内部结构和样式。

:::tip Web Components
Web Components 是一组技术，使您能够创建可重用、封装的自定义元素，以供在 Web 应用程序中使用。
:::

**阴影 DOM** 防止内部样式和标记泄漏出组件或受到外部样式的影响。这种封装确保组件保持独立，减少样式冲突的风险。

:::tip Web Components 封装
封装是 Web Components 的一个关键好处。通过将组件的结构、样式和行为与应用程序的其余部分分开，您可以避免冲突并保持代码简洁、易于维护。
:::

然而，由于这种封装，您 **无法直接样式化** 阴影 DOM 中的元素，使用标准 CSS 选择器。

例如，`dwc-button` 组件呈现以下结构：

```html {2}
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">按钮</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

如果您尝试像这样样式化 `label`：

```css
/* 不起作用 */
dwc-button .control__label {
  color: pink;
}
```

将没有任何效果，因为 `.control__label` 元素存在于阴影根中。

这就是 **CSS 阴影部分** 的用武之地。

## 使用阴影部分样式 {#styling-with-shadow-parts}

阴影部分允许外部样式表定位阴影树中的特定元素，但 **仅当** 这些元素被组件明确标记为“公开”时。

### 如何公开部分 {#how-parts-are-exposed}

要公开元素以供外部样式，这个组件的作者必须在阴影 DOM 中为其分配一个 `part` 属性。

所有 webforJ 组件自动公开相关部分以供样式化。您可以在每个组件文档的 **样式 > 阴影部分** 部分找到支持的部分列表。

例如，`dwc-button` 组件公开了 `prefix`、`label` 和 `suffix` 等部分：

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">按钮</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

一旦公开，这些部分可以通过使用 [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part) 伪元素从外部进行样式化。

### `::part()` 伪元素 {#the-part-pseudo-element}

`::part()` 选择器允许您为已标记为 `part` 属性的阴影 DOM 中的元素应用样式。

例如，改变 `dwc-button` 中 `label` 部分的颜色：

```css
dwc-button::part(label) {
  color: red;
}
```

您可以将 `::part()` 与其他选择器结合使用，例如 `:hover`：

```css
dwc-button::part(label):hover {
  color: pink;
}
```

:::warning `::part()` 选择器的限制
您无法选择 *阴影部分* 内部的内容。以下内容将 **不起作用**：

```css
/* 不起作用 */
dwc-button::part(label) span {
  /* CSS 代码在这里 */
}
```
:::
