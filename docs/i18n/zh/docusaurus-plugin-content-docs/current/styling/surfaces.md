---
sidebar_position: 8
title: Surfaces
_i18n_hash: 05e79ea41c1483cb30396be9bc096c4f
---
import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';

有三个级别的表面用于组织UI层次结构，通常与[阴影](./shadows)结合使用。所有[调色板颜色](./colors)都经过测试，以确保与这些表面有足够的对比度。

### 示例 {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### 变量 {#variables}

| **变量**           | **用法**                                                               | **示例**                                   |
|-------------------|-------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1` | 最深的表面。用于主体背景。                                             | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | 用于组件（例如卡片）。                                                 | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | 最浅和最高的表面。用于菜单、弹出层、对话框等 ...                     | <SurfaceBox surface="--dwc-surface-3" /> |
