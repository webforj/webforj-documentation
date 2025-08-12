---
sidebar_position: 9
title: State
_i18n_hash: a6e594262709137318ed90066759b577
---
状态令牌定义了组件如何在用户交互时在视觉上响应——例如，当它们被禁用或聚焦时。这些变量有助于确保所有用户界面元素之间的一致行为和样式，并且可以很容易地自定义以匹配你的设计系统。

<!-- vale off -->
## 禁用状态 {#disabled-state}
<!-- vale on -->
禁用状态属性用于使元素在视觉上看起来不活动且不可交互。

### 示例 {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### 变量 {#variables}

| **变量**                 | **默认值**                |
|--------------------------|----------------------------|
| `--dwc-disabled-opacity` | 0.7                        |
| `--dwc-disabled-cursor`  | var(--dwc-cursor-disabled) |

---

## 聚焦状态 {#focus-state}

当组件获得焦点时，将在其周围显示一个聚焦环以指示其活动状态。你可以使用下面的变量自定义环的外观。这些变量与组件主题的聚焦环设置结合使用。

### 变量 {#variables-1}

| **变量**                | **默认值**                |
|-------------------------|---------------------------|
| `--dwc-focus-ring-l`    | 45%                       |
| `--dwc-focus-ring-a`    | 0.4                       |
| `--dwc-focus-ring-width` | 3px                       |
