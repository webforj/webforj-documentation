---
sidebar_position: 5
title: Sizing and Spacing
_i18n_hash: 05261a33707bc38ade5e855f5ae5ce47
---
间距和尺寸代币用于在您的应用程序中提供一致的间距和尺寸。所有尺寸和间距属性以`rem`为单位定义。

:::info REM 单位
`rem` 是一个相对长度单位。它相对于 [根元素](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html) 的字体大小。
:::

## 尺寸 {#sizing}

使用这些属性来调整组件的尺寸（宽度、高度）。`m` 是几乎所有组件的标准尺寸。

:::tip 选择尺寸
选择尺寸时，请务必确保它足够大，以便于触摸目标。
:::

### 示例 {#example}

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### 变量 {#variables}

| **变量**         | **默认值**         | **计算值（在 16px 根元）** |
| ---------------- | ----------------- | --------------------------- |
| `--dwc-size-3xs` | 1.125rem          | 18px |
| `--dwc-size-2xs` | 1.375rem          | 22px |
| `--dwc-size-xs`  | 1.625rem          | 26px |
| `--dwc-size-s`   | 1.875rem          | 30px |
| `--dwc-size-m`   | 2.25rem           | 36px |
| `--dwc-size-l`   | 2.75rem           | 44px |
| `--dwc-size-xl`  | 3.25rem           | 52px |
| `--dwc-size-2xl` | 4rem              | 64px |
| `--dwc-size-3xl` | 4.25rem           | 68px |
| `--dwc-size`     | var(--dwc-size-m) | 36px |

<dwc-doc-sizes></dwc-doc-sizes>

## 间距 {#spacing}

使用这些属性来调整组件之间的间距（边距、填充）。

### 示例 {#example-1}

```css
.element {
  padding: var(--dwc-space-m);
}
```

### 变量 {#variables-1}

| **变量**          | **默认值**        | **计算值（在 16px 根元）** |
| ----------------- | ---------------- | --------------------------- |
| `--dwc-space-3xs` | 0.0625rem        | 1px |
| `--dwc-space-2xs` | 0.125rem         | 2px |
| `--dwc-space-xs`  | 0.25rem          | 4px |
| `--dwc-space-s`   | 0.5rem           | 8px |
| `--dwc-space-m`   | 1rem             | 16px |
| `--dwc-space-l`   | 1.25rem          | 20px |
| `--dwc-space-xl`  | 1.5rem           | 24px |
| `--dwc-space-2xl` | 1.75rem          | 28px |
| `--dwc-space-3xl` | 2rem             | 32px |
| `--dwc-space`     | var(--dwc-space-s) | 8px |

<dwc-doc-spaces></dwc-doc-spaces>
