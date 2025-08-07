---
sidebar_position: 8
title: Surfaces
_i18n_hash: d26674c84c900aea4d63dad4dca61446
---
import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';

有三种表面级别用于组织用户界面层次，通常与[阴影](./shadows)结合使用。所有[调色板颜色](./colors)都经过测试，以提供与这些表面之间的充分对比。

### 示例 {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### 变量 {#variables}

| **变量**          | **用法**                                                               | **示例**                                   |
|-------------------|-------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1` | 最暗的表面。用于主体背景。                                             | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | 用于组件（例如卡片）。                                                  | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | 最亮也是最高的表面。用于菜单、弹出层、对话框等...                       | <SurfaceBox surface="--dwc-surface-3" /> |
