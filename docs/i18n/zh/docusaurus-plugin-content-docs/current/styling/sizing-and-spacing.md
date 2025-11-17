---
sidebar_position: 5
title: 大小和间距
_i18n_hash: 4efe9ef910459481ca90eec87c26ebe0
---
间距和大小的标记用于在您的应用中提供一致的间距和大小。所有的大小和间距属性都以 `rem` 为单位定义。

:::info REM 单位
`rem` 是一个相对长度单位。它相对于 [根元素](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html) 的字体大小。
:::

## 大小 {#sizing}

使用这些属性来调整组件的大小（宽度、高度）。`m` 是几乎所有组件的标准大小。

:::tip 选择大小
选择大小时，请始终确保它足够大，以便用于触控目标。
:::

### 示例 {#example}

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### 变量 {#variables}

| **变量**         | **默认值**        | **示例**                               |
| ---------------- | ----------------- | ------------------------------------- |
| `--dwc-size-3xs` | 1.125rem          | <SizingBox size="--dwc-size-3xs" /> |
| `--dwc-size-2xs` | 1.375rem          | <SizingBox size="--dwc-size-2xs" /> |
| `--dwc-size-xs`  | 1.625rem          | <SizingBox size="--dwc-size-xs" />  |
| `--dwc-size-s`   | 1.875rem          | <SizingBox size="--dwc-size-s" />   |
| `--dwc-size-m`   | 2.25rem           | <SizingBox size="--dwc-size-m" />   |
| `--dwc-size-l`   | 2.75rem           | <SizingBox size="--dwc-size-l" />   |
| `--dwc-size-xl`  | 3.5rem            | <SizingBox size="--dwc-size-xl" />  |
| `--dwc-size-2xl` | 4rem              | <SizingBox size="--dwc-size-2xl" /> |
| `--dwc-size-3xl` | 4.25rem           | <SizingBox size="--dwc-size-3xl" /> |
| `--dwc-size`     | var(--dwc-size-m) | <SizingBox size="--dwc-size" />     |

## 间距 {#spacing}

使用这些属性来调整组件之间的间距（边距、填充）。

### 示例 {#example-1}

```css
.element {
  padding: var(--dwc-space-m);
}
```

### 变量 {#variables-1}

| **变量**         | **默认值**       | **示例**                               |
| ---------------- | ---------------- | ------------------------------------- |
| `--dwc-space-3xs` | 0.075rem         | <SpacingBox space="--dwc-space-3xs" /> |
| `--dwc-space-2xs` | 0.15rem          | <SpacingBox space="--dwc-space-2xs" /> |
| `--dwc-space-xs`  | 0.25rem          | <SpacingBox space="--dwc-space-xs" />  |
| `--dwc-space-s`   | 0.5rem           | <SpacingBox space="--dwc-space-s" />   |
| `--dwc-space-m`   | 1rem             | <SpacingBox space="--dwc-space-m" />   |
| `--dwc-space-l`   | 1.25rem          | <SpacingBox space="--dwc-space-l" />   |
| `--dwc-space-xl`  | 1.5rem           | <SpacingBox space="--dwc-space-xl" />  |
| `--dwc-space-2xl` | 1.75rem          | <SpacingBox space="--dwc-space-2xl" /> |
| `--dwc-space-3xl` | 2rem             | <SpacingBox space="--dwc-space-3xl" /> |
| `--dwc-space`     | var(--dwc-space-s) | <SpacingBox space="--dwc-space" />     |
