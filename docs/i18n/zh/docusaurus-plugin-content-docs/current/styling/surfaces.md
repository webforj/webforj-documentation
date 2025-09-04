---
sidebar_position: 8
title: Surfaces
_i18n_hash: ac1f587cd1039f9bf083c610c29c27b9
---
有三个级别的表面用于组织UI层次，通常结合[阴影](./shadows)使用。所有[调色板颜色](./colors)经过测试，以提供与这些表面之间的足够对比度。

### 示例 {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### 变量 {#variables}

| **变量**             | **用法**                                                               | **示例**                                   |
|---------------------|------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1`   | 最暗的表面。用于主体背景。                                          | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2`   | 用于组件（例如卡片）。                                               | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3`   | 最亮和最高的表面。用于菜单、弹出框、对话框等...                     | <SurfaceBox surface="--dwc-surface-3" /> |
