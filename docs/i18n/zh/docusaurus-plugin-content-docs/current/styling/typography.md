---
sidebar_position: 4
title: Typography
_i18n_hash: 5eafa3dea127703b4f573da438cbaf57
---
排版标记用于在整个应用中保持一致的字体样式集。

## 字体系列 {#font-family}

字体系列属性用于指定优先级列表的字体名称。

系统字体堆栈是默认使用的：

- Windows上为`Segoe UI`
- Android和Chrome OS上为`Roboto`
- macOS和iOS上为`San Francisco`
- 在其他系统上，使用`Helvetica, Arial`作为备用。

您可以使用`--dwc-font-family`自定义属性来应用或更改字体系列。

### 示例 {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### 变量 {#variables}

| **变量**                     | **默认值**                                                                                                                        | **示例**                                                           |
| ---------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------- |
| `--dwc-font-family-sans`     | -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol' | <span style={{ fontFamily: "var(--dwc-font-family-sans)" }}>黑色石英的斯芬克斯，裁判我的誓言。</span> |
| `--dwc-font-family-mono`     | Menlo, Monaco, 'Courier New', monospace                                                                                           | <span style={{ fontFamily: "var(--dwc-font-family-mono)" }}>黑色石英的斯芬克斯，裁判我的誓言。</span> |
| `--dwc-font-family`          | `var(--dwc-font-family-sans)`                                                                                                     | <span style={{ fontFamily: "var(--dwc-font-family)" }}>黑色石英的斯芬克斯，裁判我的誓言。</span>      |

## 字体大小 {#font-size}

字体大小属性定义了一组可选择的字体大小。`s`是标准大小，默认用于大多数组件。所有字体大小均以`em`为单位定义。

:::info EM单位
`em`是相对长度单位。 它相对于父级的[字体大小](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size)，在字体大小等排版属性的情况下，与元素自身的字体大小相对，在其他属性如[宽度](https://developer.mozilla.org/en-US/docs/Web/CSS/width)的情况下也是如此。
:::

### 示例 {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### 变量 {#variables-1}

| **变量**                   | **默认值**        | **示例**                                                        |
| ------------------------- | ---------------- | -------------------------------------------------------------- |
| `--dwc-font-size-2xs`     | 0.75rem          | <span style={{ fontSize: "var(--dwc-font-size-2xs)" }}>Aa</span> |
| `--dwc-font-size-xs`      | 0.813rem         | <span style={{ fontSize: "var(--dwc-font-size-xs)" }}>Aa</span>  |
| `--dwc-font-size-s`       | 0.875rem         | <span style={{ fontSize: "var(--dwc-font-size-s)" }}>Aa</span>   |
| `--dwc-font-size-m`       | 1rem             | <span style={{ fontSize: "var(--dwc-font-size-m)" }}>Aa</span>   |
| `--dwc-font-size-l`       | 1.125rem         | <span style={{ fontSize: "var(--dwc-font-size-l)" }}>Aa</span>   |
| `--dwc-font-size-xl`      | 1.375rem         | <span style={{ fontSize: "var(--dwc-font-size-xl)" }}>Aa</span>  |
| `--dwc-font-size-2xl`     | 1.75rem          | <span style={{ fontSize: "var(--dwc-font-size-2xl)" }}>Aa</span> |
| `--dwc-font-size-3xl`     | 2.25rem          | <span style={{ fontSize: "var(--dwc-font-size-3xl)" }}>Aa</span> |
| `--dwc-font-size`         | `var(--dwc-font-size-s)` | <span style={{ fontSize: "var(--dwc-font-size)" }}>Aa</span>     |

## 字体粗细 {#font-weight}

[font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) CSS 属性设置字体的粗细（或加粗程度）。

### 示例 {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **变量**                       | **默认值** | **示例**                                                         |
| ------------------------------ | ----------- | --------------------------------------------------------------- |
| `--dwc-font-weight-lighter`    | 200         | <span style={{ fontWeight: "var(--dwc-font-weight-lighter)" }}>Aa</span>  |
| `--dwc-font-weight-light`      | 300         | <span style={{ fontWeight: "var(--dwc-font-weight-light)" }}>Aa</span>    |
| `--dwc-font-weight-normal`     | 400         | <span style={{ fontWeight: "var(--dwc-font-weight-normal)" }}>Aa</span>   |
| `--dwc-font-weight-semibold`   | 500         | <span style={{ fontWeight: "var(--dwc-font-weight-semibold)" }}>Aa</span> |
| `--dwc-font-weight-bold`       | 700         | <span style={{ fontWeight: "var(--dwc-font-weight-bold)" }}>Aa</span>     |
| `--dwc-font-weight-bolder`     | 800         | <span style={{ fontWeight: "var(--dwc-font-weight-bolder)" }}>Aa</span>   |

## 行高 {#line-height}

line-height CSS 属性设置行框的高度。它通常用于设置文本行之间的距离。

### 示例 {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### 变量 {#variables-2}

| **变量**                       | **默认值**              | **示例**                                                                                               |
| ------------------------------ | ---------------------- | ----------------------------------------------------------------------------------------------------- |
| `--dwc-font-line-height-2xs`   | 0.95                    | <span style={{ lineHeight: "var(--dwc-font-line-height-2xs)", display: "block" }}>黑色石英的斯芬克斯，裁判我的誓言。<br/>黑色石英的斯芬克斯，裁判我的誓言。</span> |
| `--dwc-font-line-height-xs`    | 1.1                     | <span style={{ lineHeight: "var(--dwc-font-line-height-xs)", display: "block" }}>黑色石英的斯芬克斯，裁判我的誓言。<br/>黑色石英的斯芬克斯，裁判我的誓言。</span>  |
| `--dwc-font-line-height-s`     | 1.25                    | <span style={{ lineHeight: "var(--dwc-font-line-height-s)", display: "block" }}>黑色石英的斯芬克斯，裁判我的誓言。<br/>黑色石英的斯芬克斯，裁判我的誓言。</span>   |
| `--dwc-font-line-height-m`     | 1.375                   | <span style={{ lineHeight: "var(--dwc-font-line-height-m)", display: "block" }}>黑色石英的斯芬克斯，裁判我的誓言。<br/>黑色石英的斯芬克斯，裁判我的誓言。</span>   |
| `--dwc-font-line-height-l`     | 1.5                     | <span style={{ lineHeight: "var(--dwc-font-line-height-l)", display: "block" }}>黑色石英的斯芬克斯，裁判我的誓言。<br/>黑色石英的斯芬克斯，裁判我的誓言。</span>   |
| `--dwc-font-line-height-xl`    | 1.75                    | <span style={{ lineHeight: "var(--dwc-font-line-height-xl)", display: "block" }}>黑色石英的斯芬克斯，裁判我的誓言。<br/>黑色石英的斯芬克斯，裁判我的誓言。</span>  |
| `--dwc-font-line-height-2xl`   | 2                      | <span style={{ lineHeight: "var(--dwc-font-line-height-2xl)", display: "block" }}>黑色石英的斯芬克斯，裁判我的誓言。<br/>黑色石英的斯芬克斯，裁判我的誓言。</span> |
| `--dwc-font-line-height`       | var(--dwc-font-line-height-m) | <span style={{ lineHeight: "var(--dwc-font-line-height)", display: "block" }}>黑色石英的斯芬克斯，裁判我的誓言。<br/>黑色石英的斯芬克斯，裁判我的誓言。</span>     |
