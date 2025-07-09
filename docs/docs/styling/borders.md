---
sidebar_position: 6
title: Border
---

import RadiusBox from '@site/src/components/DWCTheme/RadiusBox/RadiusBox';

The border properties are used to control the component border style and width. See [available border styles](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Example

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) red;
}
```

### Variables

| **Variable**         | **Default Value** |
|----------------------|-------------------|
| `--dwc-border-width` | 1px               |
| `--dwc-border-style` | solid             |

## Border radius

Border radius variables define how rounded the corners of a component are. All values are defined in `em`, so they scale with the font size.

:::info EM Unit
`em` is a relative unit that scales with the [font size](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) of the parent.
:::

### Example

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Variables

| **Variable**                | **Default Value**          | **Example**                         |
|-----------------------------|-----------------------------|--------------------------------------|
| `--dwc-border-radius-2xs`   | 0.071em                    | <RadiusBox radius="--dwc-border-radius-2xs" /> |
| `--dwc-border-radius-xs`    | 0.125em                    | <RadiusBox radius="--dwc-border-radius-xs" /> |
| `--dwc-border-radius-s`     | 0.25em                     | <RadiusBox radius="--dwc-border-radius-s" />  |
| `--dwc-border-radius-m`     | 0.375em                    | <RadiusBox radius="--dwc-border-radius-m" />  |
| `--dwc-border-radius-l`     | 0.5em                      | <RadiusBox radius="--dwc-border-radius-l" />  |
| `--dwc-border-radius-xl`    | 0.75em                     | <RadiusBox radius="--dwc-border-radius-xl" /> |
| `--dwc-border-radius-2xl`   | 1em                        | <RadiusBox radius="--dwc-border-radius-2xl" /> |
| `--dwc-border-radius-round` | 50%                        | <RadiusBox radius="--dwc-border-radius-round" /> |
| `--dwc-border-radius-pill`  | 9999px                     | <RadiusBox radius="--dwc-border-radius-pill" /> |
| `--dwc-border-radius`       | var(--dwc-border-radius-s) | <RadiusBox radius="--dwc-border-radius" />     |