---
sidebar_position: 8
title: Surfaces
_i18n_hash: cac300e6e9c10cd9d1da6b266e536c74
---
DWC 定义了三个表面级别，用于组织 UI 层次结构，并结合 [shadows](./shadows)。所有 [palette colors](./colors) 都经过测试，以确保与这些表面具有足够的对比度。

表面从主要色调获取微妙的色彩，并自动适应明亮和暗模式。

### 示例 {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### 变量 {#variables}

| **变量**           | **用法**                      |
|-------------------|-------------------------------|
| `--dwc-surface-1` | 页面和主体背景。              |
| `--dwc-surface-2` | 工具栏、菜单栏、卡片。        |
| `--dwc-surface-3` | 窗口、菜单、弹出框、对话框。  |

<dwc-doc-surfaces></dwc-doc-surfaces>
