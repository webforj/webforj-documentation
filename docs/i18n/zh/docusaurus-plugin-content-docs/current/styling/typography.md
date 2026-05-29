---
sidebar_position: 4
title: Typography
_i18n_hash: 7c5f036abf897a890cad14af0a64c6bd
---
排版令牌用于在您的应用程序中保持一致的字体样式集。

## 字体家族 {#font-family}

字体家族属性用于指定优先字体家族名称的列表。

系统字体堆栈默认使用 `system-ui`，它会自动解析为平台的本地字体：

- 在 macOS 和 iOS 上为 `San Francisco`
- 在 Windows 上为 `Segoe UI`
- 在 Android 和 Chrome OS 上为 `Roboto`

您可以使用 `--dwc-font-family` 自定义属性来应用或更改字体家族。

### 示例 {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### 变量 {#variables}

| **变量**                   | **默认值**                                                                                  |
| -------------------------- | ------------------------------------------------------------------------------------------ |
| `--dwc-font-family-sans`   | system-ui, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji' |
| `--dwc-font-family-mono`   | ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace |
| `--dwc-font-family`        | `var(--dwc-font-family-sans)`                                                              |

## 字体大小 {#font-size}

字体大小属性定义了一组可供选择的字体大小。`m` 是标准大小，默认由大多数组件使用。所有字体大小以 `rem` 为单位定义。

:::info REM 单位
`rem` 是相对长度单位。它相对于根元素 (`<html>`) 的[字体大小](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size)，在大多数浏览器中默认是 16px。
:::

### 示例 {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### 变量 {#variables-1}

| **变量**                  | **默认值**                          | **计算（在 16px 根元素下）** |
| ------------------------- | ----------------------------------- | ----------------------------- |
| `--dwc-font-size-3xs`    | 0.625rem                            | 10px                          |
| `--dwc-font-size-2xs`    | 0.6875rem                           | 11px                          |
| `--dwc-font-size-xs`     | 0.75rem                             | 12px                          |
| `--dwc-font-size-s`      | 0.8125rem                           | 13px                          |
| `--dwc-font-size-m`      | 0.875rem                            | 14px                          |
| `--dwc-font-size-l`      | 1rem                                | 16px                          |
| `--dwc-font-size-xl`     | 1.25rem                             | 20px                          |
| `--dwc-font-size-2xl`    | 1.625rem                            | 26px                          |
| `--dwc-font-size-3xl`    | 2.125rem                            | 34px                          |
| `--dwc-font-size`        | `var(--dwc-font-size-m)`           | 14px                          |

<dwc-doc-font-sizes></dwc-doc-font-sizes>

## 字体粗细 {#font-weight}

[font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) CSS 属性设置字体的粗细（或加粗程度）。

### 示例 {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **变量**                     | **默认值** |
| ---------------------------- | ----------- |
| `--dwc-font-weight-thin`      | 100         |
| `--dwc-font-weight-lighter`   | 200         |
| `--dwc-font-weight-light`     | 300         |
| `--dwc-font-weight-normal`    | 400         |
| `--dwc-font-weight-medium`    | 500         |
| `--dwc-font-weight-semibold`  | 600         |
| `--dwc-font-weight-bold`      | 700         |
| `--dwc-font-weight-bolder`    | 800         |
| `--dwc-font-weight-black`     | 900         |
| `--dwc-font-weight`           | `var(--dwc-font-weight-normal)` |

<dwc-doc-font-weights></dwc-doc-font-weights>

## 行高 {#line-height}

line-height CSS 属性设置行框的高度。它通常用于设置文本行之间的距离。

### 示例 {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### 变量 {#variables-2}

| **变量**                     | **默认值**                  |
| ---------------------------- | --------------------------- |
| `--dwc-font-line-height-3xs`  | 1                           |
| `--dwc-font-line-height-2xs`  | 1.1                         |
| `--dwc-font-line-height-xs`   | 1.25                        |
| `--dwc-font-line-height-s`    | 1.375                       |
| `--dwc-font-line-height-m`    | 1.5                         |
| `--dwc-font-line-height-l`    | 1.625                       |
| `--dwc-font-line-height-xl`   | 1.75                        |
| `--dwc-font-line-height-2xl`  | 1.875                       |
| `--dwc-font-line-height-3xl`  | 2                           |
| `--dwc-font-line-height`      | var(--dwc-font-line-height-xs) |

<dwc-doc-line-heights></dwc-doc-line-heights>
