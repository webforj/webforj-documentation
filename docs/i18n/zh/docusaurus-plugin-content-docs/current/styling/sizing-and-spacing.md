---
sidebar_position: 5
title: Sizing and Spacing
_i18n_hash: 13396e3bc7eb84e83ef282c219954f8a
---
间距和尺寸标记用于在您的应用中提供一致的间距和尺寸。所有尺寸和间距属性都以 `rem` 为单位定义。

:::info REM 单位
`rem` 是一种相对长度单位。它相对于 [根元素](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html) 的字体大小。
:::

## 尺寸 {#sizing}

使用这些属性来调整组件的尺寸（宽度，高度）。`m` 是几乎所有组件的标准尺寸。

:::tip 选择尺寸
选择尺寸时，请始终确保尺寸足够大，以便于触摸目标。
:::

### 示例 {#example}

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### 变量 {#variables}

| **变量**         | **默认值**    | **示例**                          |
| ---------------- | ------------- | --------------------------------- |
| `--dwc-size-3xs` | 1.125rem      | <SizingBox size="--dwc-size-3xs" /> |
| `--dwc-size-2xs` | 1.375rem      | <SizingBox size="--dwc-size-2xs" /> |
| `--dwc-size-xs`  | 1.625rem      | <SizingBox size="--dwc-size-xs" />  |
| `--dwc-size-s`   | 1.875rem      | <SizingBox size="--dwc-size-s" />   |
| `--dwc-size-m`   | 2.25rem       | <SizingBox size="--dwc-size-m" />   |
| `--dwc-size-l`   | 2.75rem       | <SizingBox size="--dwc-size-l" />   |
| `--dwc-size-xl`  | 3.5rem        | <SizingBox size="--dwc-size-xl" />  |
| `--dwc-size-2xl` | 4rem          | <SizingBox size="--dwc-size-2xl" /> |
| `--dwc-size-3xl` | 4.25rem       | <SizingBox size="--dwc-size-3xl" /> |
| `--dwc-size`     | var(--dwc-size-m) | <SizingBox size="--dwc-size" />     |

## 间距 {#spacing}

使用这些属性来调整组件之间的间距（外边距，内边距）。

### 示例 {#example-1}

```css
.element {
  padding: var(--dwc-space-m);
}
```

### 变量 {#variables-1}

| **变量**         | **默认值**    | **示例**                           |
| ---------------- | ------------- | ---------------------------------- |
| `--dwc-space-3xs` | 0.075rem      | <SpacingBox space="--dwc-space-3xs" /> |
| `--dwc-space-2xs` | 0.15rem       | <SpacingBox space="--dwc-space-2xs" /> |
| `--dwc-space-xs`  | 0.25rem       | <SpacingBox space="--dwc-space-xs" />  |
| `--dwc-space-s`   | 0.5rem        | <SpacingBox space="--dwc-space-s" />   |
| `--dwc-space-m`   | 1rem          | <SpacingBox space="--dwc-space-m" />   |
| `--dwc-space-l`   | 1.25rem       | <SpacingBox space="--dwc-space-l" />   |
| `--dwc-space-xl`  | 1.5rem        | <SpacingBox space="--dwc-space-xl" />  |
| `--dwc-space-2xl` | 1.75rem       | <SpacingBox space="--dwc-space-2xl" /> |
| `--dwc-space-3xl` | 2rem          | <SpacingBox space="--dwc-space-3xl" /> |
| `--dwc-space`     | var(--dwc-space-s) | <SpacingBox space="--dwc-space" />     |
