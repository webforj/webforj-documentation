---
sidebar_position: 7
title: Shadows
_i18n_hash: 423494230ee54caa83fec778e905871b
---
影子属性在元素框架周围添加阴影效果。阴影表示用户界面中层叠在一起的项目。

阴影会自动适应亮色和暗色模式，在暗色模式中出现得更强以提高可见性。

### 示例 {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

### 变量 {#variables}

| **变量**           | **描述**                               |
|--------------------|---------------------------------------|
| `--dwc-shadow-xs`  | 超小阴影（1层）                       |
| `--dwc-shadow-s`   | 小阴影（2层）                         |
| `--dwc-shadow-m`   | 中等阴影（3层，默认）                 |
| `--dwc-shadow-l`   | 大阴影（4层）                         |
| `--dwc-shadow-xl`  | 超大阴影（5层）                       |
| `--dwc-shadow-2xl` | 双超大阴影（6层）                     |
| `--dwc-shadow`     | `var(--dwc-shadow-m)`                |

<dwc-doc-shadows></dwc-doc-shadows>
