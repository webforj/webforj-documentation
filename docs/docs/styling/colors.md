---
sidebar_position: 3
title: Colors
---

import ColorPalette from '@site/src/components/DWCTheme/ColorPalette/ColorPalette';

webforJ provides a color system built on CSS custom properties. These color variables keep consistent visual style across your app while giving you full control to customize palettes according to your design needs.

You can reference any color using the `--dwc-color-{palette}-{shade}` syntax, where `{palette}` is the name of the color group (e.g., `primary`, `danger`, ..) and `{shade}` is a number from `5` to `95` in increments of `5`, representing the color’s lightness.

```css
.element {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

:::tip Shade Value Scale
Shade values range from `5` (darkest) to `95` (lightest), increasing in steps of `5`.
:::

## Color palettes

There are several built-in color palettes, each designed for semantic use cases such as branding, success messages, warnings, and more. Each palette is composed of dynamically generated tints and shades based on three key properties: `hue`, `saturation`, and `contrast-threshold`.

### Available palettes

- **default**: A neutral gray-based palette tinted with the primary color, used for most components.
- **primary**: Typically represents your brand’s primary color.
- **success**, **warning**, **danger**: Semantic palettes used for appropriate status indicators.
- **info**: An optional complementary palette for secondary emphasis.
- **gray**: A true gray-scale palette, untinted.
- **black/white**: Static color values

<ColorPalette></ColorPalette>


<br/>

:::tip  DWC HueCraft
To simplify the process of generating WCAG-compliant palettes for your webforJ applications, you can use the [DWC HueCraft](https://webforj.github.io/huecraft/) tool. It supports palette creation based on brand colors or logos and allows quick CSS export.
:::


### Dark mode behavior

webforJ supports a flipped color strategy in dark mode. Rather than using entirely separate color palettes, it inverts the way lightness values are interpreted.

This means that in **dark themes**, lower shade values (e.g., `--dwc-color-primary-5`) become light and higher values (e.g., `--dwc-color-primary-95`) become dark. The logic is reversed to ensure optimal contrast and readability across backgrounds.

Your component code stays the same, regardless of the theme. For example:

```css
.button {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

In light mode, this gives a mid-tone background. In dark mode, it still gives a mid-tone, but flipped visually to work on dark surfaces. This approach avoids duplication, keeps your styles consistent, and keeps visual transitions smooth when switching between light and dark themes.

### Palette configuration variables

Each palette is generated based on the following variables:

| Variable               | Description |
|------------------------|-------------|
| `hue`                  | The angle (in degrees) on the color wheel. Unitless values are interpreted as degrees. |
| `saturation`           | A percentage indicating color intensity. `100%` is fully saturated; `0%` is grayscale. |
| `contrast-threshold`   | A value between `0` and `100` that determines whether text should be light or dark based on background lightness. |

You can adjust a palette by redefining these variables in your root styles. For example, to modify the primary palette:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

## Theming components with abstract variables

To simplify styling and consistency across components, webforJ introduces an abstraction layer over the base color palettes. This layer is built on **abstract theme variables** - CSS custom properties that refer to specific shades within a color palette.

These variables make it easier to apply themes across all components without directly referencing raw color values or swatches. You can think of them as *semantic styling shortcuts* that reflect your app's intent rather than its implementation details.

### Variable groups

Abstract theme variables are organized into four groups:

1. [Normal](#normal-state) Used for the default appearance, such as backgrounds and text on inactive components.
2. [Dark](#darker-variant) Used for active or selected states.
3. [Light](#lighter-variant) Used for hover and focus states.
4. [Alt](#alt-variant) Used for secondary highlights, such as keyboard focus or subtle UI accents.

Each group defines:

- A background color
- A foreground (text) color
- A border color (for focused/hovered/active states)
- A focus ring (used when the component receives visible focus)

Each tab below shows the abstract variables defined for a specific palette (`primary`, `success`, `danger`, etc.). These variables pull values from the underlying palette (e.g., `--dwc-color-primary-40`) and make them reusable across your app.

For example, instead of directly using `--dwc-color-primary-40`, you can apply `--dwc-color-primary`, which abstracts the role of that color as the *default background* for a primary-styled component.

Changing the palette values in one place will update the look of all components that rely on these abstract variables.

### Normal state

Used for the base, neutral appearance of a component—when it’s idle and not being interacted with.

| Variable                           | Description                                                             |
| ---------------------------------- | ----------------------------------------------------------------------- |
| `--dwc-color-${name}`              | The default background color. Also used for borders in many components. |
| `--dwc-color-on-${name}-text`      | The text color shown on top of the default background.                 |
| `--dwc-color-${name}-text`         | The text color when the component is placed on the surface background. |
| `--dwc-border-color-${name}`       | Border color, mainly used for hover, focus, and active states.         |
| `--dwc-focus-ring-${name}`         | Focus ring shadow when the component receives focus-visible styling.   |

---

### Darker variant

Used for selected or active states—usually a deeper tone for stronger contrast and emphasis.

| Variable                                | Description                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-dark`              | A darker version of the base color. Often used for pressed or selected states. |
| `--dwc-color-on-${name}-text-dark`      | Text color when used on a dark background.                               |
| `--dwc-color-${name}-text-dark`         | A slightly darker text alternative when shown on the surface.            |

---

### Lighter variant

Used for hover, focus, and less dominant visual cues. These are soft tones designed for subtle interaction feedback.

| Variable                                | Description                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-light`             | A lighter version of the base color. Typically used for hover/focus backgrounds. |
| `--dwc-color-on-${name}-text-light`     | Text color when shown on a light background.                             |
| `--dwc-color-${name}-text-light`        | A lighter text tone for use in less prominent states.                    |

---

### Alt variant

Used for secondary emphasis or UI highlights—such as keyboard navigation focus outlines or auxiliary indicators.

| Variable                                | Description                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-alt`               | A very light version of the color, mainly used for highlights or background glows. |
| `--dwc-color-on-${name}-text-alt`       | Text color when the background is the alternate (`alt`) color.           |

<Tabs>

<TabItem value="Default / Tone">

```css
--dwc-color-default-dark: var(--dwc-color-default-85);
--dwc-color-on-default-text-dark: var(--dwc-color-default-text-85);
--dwc-color-default-text-dark: var(--dwc-color-default-35);

--dwc-color-default: var(--dwc-color-default-90);
--dwc-color-on-default-text: var(--dwc-color-default-text-90);
--dwc-color-default-text: var(--dwc-color-default-40);

--dwc-color-default-light: var(--dwc-color-default-95);
--dwc-color-on-default-text-light: var(--dwc-color-default-text-95);
--dwc-color-default-text-light: var(--dwc-color-default-45);

--dwc-color-default-alt: var(--dwc-color-primary-alt);
--dwc-color-on-default-text-alt: var(--dwc-color-on-primary-text-alt);

--dwc-border-color-default: var(--dwc-border-color-primary);
--dwc-focus-ring-default: var(--dwc-focus-ring-primary);
```

</TabItem>

<TabItem value="Primary">

```css
--dwc-color-primary-dark: var(--dwc-color-primary-35);
--dwc-color-on-primary-text-dark: var(--dwc-color-primary-text-35);
--dwc-color-primary-text-dark: var(--dwc-color-primary-30);

--dwc-color-primary: var(--dwc-color-primary-40);
--dwc-color-on-primary-text: var(--dwc-color-primary-text-40);
--dwc-color-primary-text: var(--dwc-color-primary-35);

--dwc-color-primary-light: var(--dwc-color-primary-45);
--dwc-color-on-primary-text-light: var(--dwc-color-primary-text-45);
--dwc-color-primary-text-light: var(--dwc-color-primary-40);

--dwc-color-primary-alt: var(--dwc-color-primary-95);
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text-95);

--dwc-border-color-primary: var(--dwc-color-primary);
--dwc-focus-ring-primary: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-primary-h),
    var(--dwc-color-primary-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Success">

```css
--dwc-color-success-dark: var(--dwc-color-success-20);
--dwc-color-on-success-text-dark: var(--dwc-color-success-text-20);
--dwc-color-success-text-dark: var(--dwc-color-success-15);

--dwc-color-success: var(--dwc-color-success-25);
--dwc-color-on-success-text: var(--dwc-color-success-text-25);
--dwc-color-success-text: var(--dwc-color-success-20);

--dwc-color-success-light: var(--dwc-color-success-30);
--dwc-color-on-success-text-light: var(--dwc-color-success-text-30);
--dwc-color-success-text-light: var(--dwc-color-success-25);

--dwc-color-success-alt: var(--dwc-color-success-95);
--dwc-color-on-success-text-alt: var(--dwc-color-success-text-95);

--dwc-border-color-success: var(--dwc-color-success);
--dwc-focus-ring-success: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-success-h),
    var(--dwc-color-success-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Warning">

```css
--dwc-color-warning-dark: var(--dwc-color-warning-35);
--dwc-color-on-warning-text-dark: var(--dwc-color-warning-text-35);
--dwc-color-warning-text-dark: var(--dwc-color-warning-15);

--dwc-color-warning: var(--dwc-color-warning-40);
--dwc-color-on-warning-text: var(--dwc-color-warning-text-40);
--dwc-color-warning-text: var(--dwc-color-warning-20);

--dwc-color-warning-light: var(--dwc-color-warning-45);
--dwc-color-on-warning-text-light: var(--dwc-color-warning-text-45);
--dwc-color-warning-text-light: var(--dwc-color-warning-25);

--dwc-color-warning-alt: var(--dwc-color-warning-95);
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text-95);

--dwc-border-color-warning: var(--dwc-color-warning);
--dwc-focus-ring-warning: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-warning-h),
    var(--dwc-color-warning-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Danger">

```css
--dwc-color-danger-dark: var(--dwc-color-danger-35);
--dwc-color-on-danger-text-dark: var(--dwc-color-danger-text-35);
--dwc-color-danger-text-dark: var(--dwc-color-danger-30);

--dwc-color-danger: var(--dwc-color-danger-40);
--dwc-color-on-danger-text: var(--dwc-color-danger-text-40);
--dwc-color-danger-text: var(--dwc-color-danger-35);

--dwc-color-danger-light: var(--dwc-color-danger-45);
--dwc-color-on-danger-text-light: var(--dwc-color-danger-text-45);
--dwc-color-danger-text-light: var(--dwc-color-danger-40);

--dwc-color-danger-alt: var(--dwc-color-danger-95);
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text-95);

--dwc-border-color-danger: var(--dwc-color-danger);
--dwc-focus-ring-danger: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-danger-h),
    var(--dwc-color-danger-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Info">

```css
--dwc-color-info-dark: var(--dwc-color-info-35);
--dwc-color-on-info-text-dark: var(--dwc-color-info-text-35);
--dwc-color-info-text-dark: var(--dwc-color-info-35);

--dwc-color-info: var(--dwc-color-info-40);
--dwc-color-on-info-text: var(--dwc-color-info-text-40);
--dwc-color-info-text: var(--dwc-color-info-40);

--dwc-color-info-light: var(--dwc-color-info-45);
--dwc-color-on-info-text-light: var(--dwc-color-info-text-45);
--dwc-color-info-text-light: var(--dwc-color-info-45);

--dwc-color-info-alt: var(--dwc-color-info-95);
--dwc-color-on-info-text-alt: var(--dwc-color-info-text-95);

--dwc-border-color-info: var(--dwc-color-info);
--dwc-focus-ring-info: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-info-h),
    var(--dwc-color-info-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Gray">

```css
--dwc-color-gray-dark: var(--dwc-color-gray-10);
--dwc-color-on-gray-text-dark: var(--dwc-color-gray-text-10);
--dwc-color-gray-text-dark: var(--dwc-color-gray-15);

--dwc-color-gray: var(--dwc-color-gray-15);
--dwc-color-on-gray-text: var(--dwc-color-gray-text-15);
--dwc-color-gray-text: var(--dwc-color-gray-20);

--dwc-color-gray-light: var(--dwc-color-gray-20);
--dwc-color-on-gray-text-light: var(--dwc-color-gray-text-20);
--dwc-color-gray-text-light: var(--dwc-color-gray-25);

--dwc-color-gray-alt: var(--dwc-color-gray-95);
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text-95);

--dwc-border-color-gray: var(--dwc-color-gray);
--dwc-focus-ring-gray: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-gray-h),
    var(--dwc-color-gray-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```
</TabItem>

</Tabs>

<GiscusComments />