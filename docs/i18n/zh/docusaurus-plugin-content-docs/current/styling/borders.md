---
sidebar_position: 6
title: Border
_i18n_hash: fe0a0386da63ff7ea085db8daa12d0fe
---
边框属性用于控制组件的边框样式和宽度。请参见 [可用边框样式](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style)。

### 示例 {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) red;
}
```

### 变量 {#variables}

| **变量**                | **默认值**            |
|------------------------|----------------------|
| `--dwc-border-width`   | 1px                  |
| `--dwc-border-style`   | solid                |

## 边框半径 {#border-radius}

边框半径变量定义了组件角落的圆角程度。所有值都以 `em` 为单位，因此它们会随字体大小而缩放。

:::info EM单位
`em` 是一个相对单位，会随着父元素的 [字体大小](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) 进行缩放。
:::

### 示例 {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### 变量 {#variables-1}

| **变量**                   | **默认值**               | **示例**                                  |
|---------------------------|------------------------|-------------------------------------------|
| `--dwc-border-radius-2xs` | 0.071em                | <RadiusBox radius="--dwc-border-radius-2xs" /> |
| `--dwc-border-radius-xs`  | 0.125em                | <RadiusBox radius="--dwc-border-radius-xs" />  |
| `--dwc-border-radius-s`   | 0.25em                 | <RadiusBox radius="--dwc-border-radius-s" />   |
| `--dwc-border-radius-m`   | 0.375em                | <RadiusBox radius="--dwc-border-radius-m" />   |
| `--dwc-border-radius-l`   | 0.5em                  | <RadiusBox radius="--dwc-border-radius-l" />   |
| `--dwc-border-radius-xl`  | 0.75em                 | <RadiusBox radius="--dwc-border-radius-xl" />  |
| `--dwc-border-radius-2xl` | 1em                    | <RadiusBox radius="--dwc-border-radius-2xl" /> |
| `--dwc-border-radius-round`| 50%                    | <RadiusBox radius="--dwc-border-radius-round" /> |
| `--dwc-border-radius-pill` | 9999px                 | <RadiusBox radius="--dwc-border-radius-pill" />  |
| `--dwc-border-radius`     | var(--dwc-border-radius-s) | <RadiusBox radius="--dwc-border-radius" />      |
