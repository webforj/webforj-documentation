---
sidebar_position: 7
title: Shadows
_i18n_hash: 84ad4478632d3020d57752a4827f925a
---
阴影属性用于在元素的框架周围添加阴影效果。您可以设置多个效果，以逗号分隔。在大多数情况下，阴影用于表示用户界面中层叠在一起的项目。

<Head>
  <style>{`
  table {
    width: 100%;
    display: table;
  }
  `}</style>
</Head>

### 示例 {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

:::tip 阴影颜色
通过设置 `--dwc-shadow-color` 变量来控制阴影颜色。默认情况下，阴影颜色为灰色，并带有主色调。
:::

### 变量 {#variables}

| **变量**           | **示例**                                  |
|--------------------|------------------------------------------|
| `--dwc-shadow-xs`  | <ShadowBox shadow="--dwc-shadow-xs" />  |
| `--dwc-shadow-s`   | <ShadowBox shadow="--dwc-shadow-s" />   |
| `--dwc-shadow-m`   | <ShadowBox shadow="--dwc-shadow-m" />   |
| `--dwc-shadow-l`   | <ShadowBox shadow="--dwc-shadow-l" />   |
| `--dwc-shadow-xl`  | <ShadowBox shadow="--dwc-shadow-xl" />  |
| `--dwc-shadow-2xl` | <ShadowBox shadow="--dwc-shadow-2xl" /> |
| `--dwc-shadow`     | <ShadowBox shadow="--dwc-shadow" />     |
