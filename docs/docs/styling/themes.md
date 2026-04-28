---
sidebar_position: 2
title: Themes
---

webforJ includes three built-in app themes and supports defining your own custom themes. The default themes are:

- **light**: A bright theme with a light background (default).
- **dark**: A dark background tinted with the primary color.
- **dark-pure**: A fully neutral dark theme based on gray tones.

To apply a theme in your app, use the `@AppTheme` annotation or the `App.setTheme()` method. The theme name must be one of: `system`, `light`, `dark`, `dark-pure`, or a custom theme name.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // app code
}

// or programmatically
App.setTheme("dark-pure");
```

## Overriding default themes {#overriding-default-themes}

You can override the **light** theme by redefining CSS custom properties in the `:root` selector.

:::info `:root` pseudo-class
The `:root` CSS pseudo-class targets the root element of the document. In HTML, it represents the `<html>` element and has higher specificity than the plain `html` selector.
:::

Example:

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

To override the **dark** or **dark-pure** themes, use attribute selectors on the `<html>` element:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 80%;
}
```

## Creating custom themes {#creating-custom-themes}

You can define your own themes using the `html[data-app-theme='THEME_NAME']` selector. Custom themes can coexist with the default ones, and you can switch between them dynamically at runtime.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

To make a custom theme dark, set `--dwc-dark-mode: 1` and `color-scheme: dark`:

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Then in your app:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // app code
}

// or programmatically
App.setTheme("new-theme");
```

## Component themes {#component-themes}

In addition to app-level themes, webforJ components support a set of **component themes** based on the default color palettes: `default`, `primary`, `success`, `warning`, `danger`, `info`, and `gray`.

Each component documents its supported themes under the **Styling → Themes** section.