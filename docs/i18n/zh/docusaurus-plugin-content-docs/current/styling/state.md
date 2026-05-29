---
sidebar_position: 9
title: State
_i18n_hash: 3dc9896bce3e0577b2407f8ae4c863d0
---
状态令牌定义了组件如何在用户交互时进行视觉响应，例如在禁用或聚焦时。这些变量有助于确保所有 UI 元素的一致行为和样式，并且可以轻松自定义以匹配您的设计系统。

<!-- vale off -->
## 禁用状态 {#disabled-state}
<!-- vale on -->
禁用状态属性用于使元素看起来在视觉上处于非活动和不可交互的状态。

不透明度根据当前主题进行调整，以在亮色和暗色模式下实现最佳可见性。

### 示例 {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### 变量 {#variables}

| **变量**                   | **默认值**                 | **描述**                      |
|----------------------------|----------------------------|-------------------------------|
| `--dwc-disabled-opacity`   | 适应亮色/暗色模式           | 禁用元素的减少不透明度        |
| `--dwc-disabled-cursor`    | var(--dwc-cursor-disabled) |                               |

---

## 聚焦状态 {#focus-state}

当组件获得焦点时，会在其周围显示一个聚焦环以指示其活动状态。聚焦环使用缺口环模式，内侧缺口为表面颜色，外侧环为彩色。

### 变量 {#variables-1}

| **变量**                   | **默认值**     | **描述**                      |
|----------------------------|----------------|-------------------------------|
| `--dwc-focus-ring-a`      | 0.75           | 聚焦环的不透明度              |
| `--dwc-focus-ring-width`  | 2px            | 聚焦环的厚度                  |
| `--dwc-focus-ring-gap`    | 2px            | 组件边缘与环之间的间隙       |

每个颜色调色板生成其自己的聚焦环变量：

| 变量模式                  | 描述                      |
|---------------------------|---------------------------|
| `--dwc-focus-ring-{name}` | 带有调色板颜色的聚焦环阴影。 |

其中 `{name}` 为：`primary`（主要）、`success`（成功）、`warning`（警告）、`danger`（危险）、`info`（信息）、`gray`（灰色）、`default`（默认）。有关详细信息，请参见 [组件主题](./colors#theming-components-with-abstract-variables)。

<dwc-doc-focus-rings></dwc-doc-focus-rings>

---

## 缩放 {#scales}

缩放变换用于在交互元素上进行按压/点击反馈动画。

| **变量**                   | **默认值**     | **描述**                      |
|----------------------------|----------------|-------------------------------|
| `--dwc-scale-press`       | 0.97           | 标准按压缩放（缩小3%）       |
| `--dwc-scale-press-deep`  | 0.93           | 深度按压缩放（缩小7%）       |

<dwc-doc-scales></dwc-doc-scales>
