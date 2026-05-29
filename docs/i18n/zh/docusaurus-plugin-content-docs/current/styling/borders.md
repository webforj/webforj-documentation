---
sidebar_position: 6
title: Border
_i18n_hash: aec4d011f38db8c5a7a6c324eb76d724
---
边框属性用于控制组件的边框样式和宽度。请参阅[可用边框样式](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style)。

### 示例 {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
}
```

### 变量 {#variables}

| **变量**                     | **默认值**           |
|---------------------------|-------------------|
| `--dwc-border-width`      | 1px               |
| `--dwc-border-style`      | solid             |
| `--dwc-border-color`      | var(--dwc-border-color-default) |
| `--dwc-border-color-emphasis` | var(--dwc-border-color-default-emphasis) |

### 每种调色板的边框颜色 {#per-palette-border-colors}

每种颜色调色板生成其自己的边框颜色变量：

| 变量模式                | 描述                                   |
|---------------------|--------------------------------------|
| `--dwc-border-color-{name}` | 与调色板色调相匹配的模式感知边框颜色。          |
| `--dwc-border-color-{name}-emphasis` | 用于悬停、聚焦和活动状态的更强变体。 |

其中`{name}`是以下之一：`primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`。

## 边框半径 {#border-radius}

边框半径变量定义组件角的圆润程度。所有尺寸均从单一种子值(`--dwc-border-radius-seed`)缩放。改变种子会按比例重新调整整个半径系统。

### 示例 {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### 变量 {#variables-1}

| **变量**                    | **默认值**                        | **计算值（种子=8px时）** |
|---------------------------|----------------------------------|-----------------------------|
| `--dwc-border-radius-seed`  | 0.5rem                          | 8px                        |
| `--dwc-border-radius-2xs`   | 0.0625rem                       | 1px（固定）                 |
| `--dwc-border-radius-xs`    | 0.125rem                        | 2px（固定）                 |
| `--dwc-border-radius-s`     | calc(seed * 0.5)                | 4px                        |
| `--dwc-border-radius-m`     | calc(seed * 0.75)               | 6px                        |
| `--dwc-border-radius-l`     | var(--dwc-border-radius-seed)    | 8px                        |
| `--dwc-border-radius-xl`    | calc(seed * 1.5)                | 12px                       |
| `--dwc-border-radius-2xl`   | calc(seed * 2)                  | 16px                       |
| `--dwc-border-radius-3xl`   | calc(seed * 3)                  | 24px                       |
| `--dwc-border-radius-4xl`   | calc(seed * 4)                  | 32px                       |
| `--dwc-border-radius-round` | 50%                             |                             |
| `--dwc-border-radius-pill`  | calc(var(--dwc-size-m) / 2)     |                             |
| `--dwc-border-radius`       | var(--dwc-border-radius-seed)    | 8px                        |

<dwc-doc-radii></dwc-doc-radii>

### 使用指南 {#usage-guidelines}

- 容器内部的项目：使用`s`（0.5x种子）
- 结构边框（项目和容器之间）：使用`m`（0.75x种子）
- 容器和表面：使用`l`（1x种子）
- 大覆盖层：使用`xl`（1.5x种子）
