---
sidebar_position: 7
title: Shadows
---

The shadow properties are used to add shadow effects around an element's frame. You can set multiple effects separated by commas. In most cases, shadows are utilized to signify items that are layered on top of each other in the user interface.


import ShadowBox from '@site/src/components/DWCTheme/ShadowBox/ShadowBox';

<Head>
  <style>{`
  table {
    width: 100%;
    display: table;
  }
  `}</style>
</Head>

### Example

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

:::tip Shadow Color
You control the shadow color by setting the `--dwc-shadow-color` variable. By default the shadow color is gray tinted with the primary color.
:::

### Variables

| **Variable**       | **Example**                             |
|--------------------|------------------------------------------|
| `--dwc-shadow-xs`  | <ShadowBox shadow="--dwc-shadow-xs" />  |
| `--dwc-shadow-s`   | <ShadowBox shadow="--dwc-shadow-s" />   |
| `--dwc-shadow-m`   | <ShadowBox shadow="--dwc-shadow-m" />   |
| `--dwc-shadow-l`   | <ShadowBox shadow="--dwc-shadow-l" />   |
| `--dwc-shadow-xl`  | <ShadowBox shadow="--dwc-shadow-xl" />  |
| `--dwc-shadow-2xl` | <ShadowBox shadow="--dwc-shadow-2xl" /> |
| `--dwc-shadow`     | <ShadowBox shadow="--dwc-shadow" />     |