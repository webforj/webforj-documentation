---
sidebar_position: 4
title: Typography
---

Typography tokens are used to maintain a consistent set of font styles throughout your app.

## Font family {#font-family}

The font family properties are used to specify a prioritized list of font family names.

The system font stack is used by default via `system-ui`, which automatically resolves to the platform's native font:

- `San Francisco` on macOS and iOS
- `Segoe UI` on Windows
- `Roboto` on Android and Chrome OS

You can apply or change the font family using the `--dwc-font-family` custom property.

### Example {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variables {#variables}

| **Variable**             | **Default Value** |
| ------------------------ | ----------------- |
| `--dwc-font-family-sans` | system-ui, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji' |
| `--dwc-font-family-mono` | ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace |
| `--dwc-font-family`      | `var(--dwc-font-family-sans)` |

## Font size {#font-size}

The font size properties define a set of font sizes to choose from. `m` is the standard size and is used by most components by default. All font sizes are defined in `rem`.

:::info REM Unit
`rem` is a relative length unit. It is relative to the [font size](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) of the root element (`<html>`), which defaults to 16px in most browsers.
:::

### Example {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variables {#variables-1}

| **Variable**          | **Default Value**        | **Computed (at 16px root)** |
| --------------------- | ------------------------ | --------------------------- |
| `--dwc-font-size-3xs` | 0.625rem                 | 10px |
| `--dwc-font-size-2xs` | 0.6875rem                | 11px |
| `--dwc-font-size-xs`  | 0.75rem                  | 12px |
| `--dwc-font-size-s`   | 0.8125rem                | 13px |
| `--dwc-font-size-m`   | 0.875rem                 | 14px |
| `--dwc-font-size-l`   | 1rem                     | 16px |
| `--dwc-font-size-xl`  | 1.25rem                  | 20px |
| `--dwc-font-size-2xl` | 1.625rem                 | 26px |
| `--dwc-font-size-3xl` | 2.125rem                 | 34px |
| `--dwc-font-size`     | `var(--dwc-font-size-m)` | 14px |

<dwc-doc-font-sizes></dwc-doc-font-sizes>

## Font weight {#font-weight}

The [font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) CSS property sets the weight (or boldness) of the font.

### Example {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variable**                 | **Default Value** |
| ---------------------------- | ----------------- |
| `--dwc-font-weight-thin`     | 100 |
| `--dwc-font-weight-lighter`  | 200 |
| `--dwc-font-weight-light`    | 300 |
| `--dwc-font-weight-normal`   | 400 |
| `--dwc-font-weight-medium`   | 500 |
| `--dwc-font-weight-semibold` | 600 |
| `--dwc-font-weight-bold`     | 700 |
| `--dwc-font-weight-bolder`   | 800 |
| `--dwc-font-weight-black`    | 900 |
| `--dwc-font-weight`          | `var(--dwc-font-weight-normal)` |

<dwc-doc-font-weights></dwc-doc-font-weights>

## Line height {#line-height}

The line-height CSS property sets the height of a line box. It's commonly used to set the distance between lines of text.

### Example {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variables {#variables-2}

| **Variable**                  | **Default Value**              |
| ----------------------------- | ------------------------------ |
| `--dwc-font-line-height-3xs`  | 1 |
| `--dwc-font-line-height-2xs`  | 1.1 |
| `--dwc-font-line-height-xs`   | 1.25 |
| `--dwc-font-line-height-s`    | 1.375 |
| `--dwc-font-line-height-m`    | 1.5 |
| `--dwc-font-line-height-l`    | 1.625 |
| `--dwc-font-line-height-xl`   | 1.75 |
| `--dwc-font-line-height-2xl`  | 1.875 |
| `--dwc-font-line-height-3xl`  | 2 |
| `--dwc-font-line-height`      | var(--dwc-font-line-height-xs) |

<dwc-doc-line-heights></dwc-doc-line-heights>
