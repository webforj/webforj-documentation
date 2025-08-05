---
sidebar_position: 4
title: Typography
---

Typography tokens are used to maintain a consistent set of font styles throughout your app.

## Font family {#font-family}

The font family properties are used to specify a prioritized list of font family names.

The system font stack is used by default:

- `Segoe UI` on Windows
- `Roboto` on Android and Chrome OS
- `San Francisco` on macOS and iOS
- On other systems, `Helvetica, Arial` are used as fallbacks.

You can apply or change the font family using the `--dwc-font-family` custom property.

### Example {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variables {#variables}

| **Variable**             | **Default Value**                                                                                                                               | **Example**                                                           |
| ------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------- |
| `--dwc-font-family-sans` | -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol' | <span style={{ fontFamily: "var(--dwc-font-family-sans)" }}>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-family-mono` | Menlo, Monaco, 'Courier New', monospace                                                                                                         | <span style={{ fontFamily: "var(--dwc-font-family-mono)" }}>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-family`      | `var(--dwc-font-family-sans)`                                                                                                                   | <span style={{ fontFamily: "var(--dwc-font-family)" }}>Sphinx of black quartz, judge my vow.</span>      |

## Font size {#font-size}

The font size properties define a set of font sizes to choose from. `s` is the standard size, and is used by most components by default. All font sizes are defined in `em`.

:::info EM Unit
`em` is a relative length unit. it's relative to [font size](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) of the parent, in the case of typographical properties like font-size, and font size of the element itself and in the case of other properties like [width](https://developer.mozilla.org/en-US/docs/Web/CSS/width).
:::

### Example {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variables {#variables-1}

| **Variable**          | **Default Value**        | **Example**                                                      |
| --------------------- | ------------------------ | ---------------------------------------------------------------- |
| `--dwc-font-size-2xs` | 0.75rem                  | <span style={{ fontSize: "var(--dwc-font-size-2xs)" }}>Aa</span> |
| `--dwc-font-size-xs`  | 0.813rem                 | <span style={{ fontSize: "var(--dwc-font-size-xs)" }}>Aa</span>  |
| `--dwc-font-size-s`   | 0.875rem                 | <span style={{ fontSize: "var(--dwc-font-size-s)" }}>Aa</span>   |
| `--dwc-font-size-m`   | 1rem                     | <span style={{ fontSize: "var(--dwc-font-size-m)" }}>Aa</span>   |
| `--dwc-font-size-l`   | 1.125rem                 | <span style={{ fontSize: "var(--dwc-font-size-l)" }}>Aa</span>   |
| `--dwc-font-size-xl`  | 1.375rem                 | <span style={{ fontSize: "var(--dwc-font-size-xl)" }}>Aa</span>  |
| `--dwc-font-size-2xl` | 1.75rem                  | <span style={{ fontSize: "var(--dwc-font-size-2xl)" }}>Aa</span> |
| `--dwc-font-size-3xl` | 2.25rem                  | <span style={{ fontSize: "var(--dwc-font-size-3xl)" }}>Aa</span> |
| `--dwc-font-size`     | `var(--dwc-font-size-s)` | <span style={{ fontSize: "var(--dwc-font-size)" }}>Aa</span>     |

## Font weight {#font-weight}

The [font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) CSS property sets the weight (or boldness) of the font.

### Example {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variable**                 | **Default Value** | **Example**                                                               |
| ---------------------------- | ----------------- | ------------------------------------------------------------------------- |
| `--dwc-font-weight-lighter`  | 200               | <span style={{ fontWeight: "var(--dwc-font-weight-lighter)" }}>Aa</span>  |
| `--dwc-font-weight-light`    | 300               | <span style={{ fontWeight: "var(--dwc-font-weight-light)" }}>Aa</span>    |
| `--dwc-font-weight-normal`   | 400               | <span style={{ fontWeight: "var(--dwc-font-weight-normal)" }}>Aa</span>   |
| `--dwc-font-weight-semibold` | 500               | <span style={{ fontWeight: "var(--dwc-font-weight-semibold)" }}>Aa</span> |
| `--dwc-font-weight-bold`     | 700               | <span style={{ fontWeight: "var(--dwc-font-weight-bold)" }}>Aa</span>     |
| `--dwc-font-weight-bolder`   | 800               | <span style={{ fontWeight: "var(--dwc-font-weight-bolder)" }}>Aa</span>   |

## Line height {#line-height}

The line-height CSS property sets the height of a line box. It's commonly used to set the distance between lines of text.

### Example {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variables {#variables-2}

| **Variable**                 | **Default Value**             | **Example**                                                                                                |
| ---------------------------- | ----------------------------- | ---------------------------------------------------------------------------------------------------------- |
| `--dwc-font-line-height-2xs` | 0.95                          | <span style={{ lineHeight: "var(--dwc-font-line-height-2xs)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-line-height-xs`  | 1.1                           | <span style={{ lineHeight: "var(--dwc-font-line-height-xs)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>  |
| `--dwc-font-line-height-s`   | 1.25                          | <span style={{ lineHeight: "var(--dwc-font-line-height-s)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-m`   | 1.375                         | <span style={{ lineHeight: "var(--dwc-font-line-height-m)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-l`   | 1.5                           | <span style={{ lineHeight: "var(--dwc-font-line-height-l)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-xl`  | 1.75                          | <span style={{ lineHeight: "var(--dwc-font-line-height-xl)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>  |
| `--dwc-font-line-height-2xl` | 2                             | <span style={{ lineHeight: "var(--dwc-font-line-height-2xl)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-line-height`     | var(--dwc-font-line-height-m) | <span style={{ lineHeight: "var(--dwc-font-line-height)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>     |
