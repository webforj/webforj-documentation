---
sidebar_position: 2
title: Themes
description: Apply built-in light, dark, and dark-pure themes with @AppTheme or define custom themes through data-app-theme selectors.
---

A theme in webforJ is a named set of CSS custom properties (design tokens) that controls how every component looks. Switching themes recalculates colors, shadows, surfaces, and borders across the whole app instantly, with no rebuild.

## Built-in themes {#built-in-themes}

webforJ ships three app themes out of the box:

| Theme       | Background      | Tint                       |
|-------------|-----------------|----------------------------|
| `light`     | Light (default) | Subtle primary-color tint  |
| `dark`      | Dark            | Subtle primary-color tint  |
| `dark-pure` | Dark            | None (pure neutral grays)  |

Any app can switch between them at runtime, and additional custom themes can be defined alongside the built-ins.

## Applying a theme {#applying-a-theme}

Set the active theme declaratively with the `@AppTheme` annotation or programmatically with `App.setTheme()`. The theme name must be one of `system`, `light`, `dark`, `dark-pure`, or the name of a custom theme.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // app code
}

// or programmatically
App.setTheme("dark-pure");
```

Calling `App.setTheme()` again at any time switches the app to a different theme.

## Color scheme {#color-scheme}

The `color-scheme` CSS declaration tells the browser how to render its built-in surfaces such as native scrollbars, form-control widgets, autofill highlights, and the default page background before CSS loads. The built-in `dark` and `dark-pure` themes already set `color-scheme: dark` for you, so the browser chrome blends with the dark surfaces automatically.

You only need to think about this when defining a custom dark theme of your own. In that case, include `color-scheme: dark` on the theme's selector:

```css
html[data-app-theme="brand-dark"] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

If you skip it, scrollbars, and autofill rectangles stay light-mode by default and look out of place over your dark surfaces. Light themes don't need the declaration, browsers default to light.

## Following the user's preference {#following-the-users-preference}

Most operating systems let users pick a light or dark appearance system-wide. webforJ can honor that preference and pick the right theme automatically.

Register which theme to apply for each appearance state with `@AppLightTheme` and `@AppDarkTheme` (or `App.setLightTheme()` and `App.setDarkTheme()`), then pass the reserved keyword `"system"` to `App.setTheme()` (or `@AppTheme("system")`) to let webforJ pick between them based on the user's OS preference.

```java
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
class MyApp extends App {
  // app code
}
```

Equivalent programmatic form:

```java
App.setLightTheme("light");
App.setDarkTheme("dark");
App.setTheme("system");
```

`"system"` is a reserved keyword. webforJ resolves it at runtime to either the registered light or dark theme and re-resolves automatically whenever the OS preference changes. Once resolved, the actual `data-app-theme` attribute on the page is `light` or `dark`, so any CSS overrides should target those names rather than `"system"`.

:::info OS-level appearance settings
Where users enable the system-wide appearance setting varies by platform:

- **Windows 10/11**: Settings > Personalization > Colors > Choose your color
- **macOS**: System Settings > Appearance
- **iOS**: Settings > Display & Brightness > Appearance
- **Android**: Settings > Display > Dark theme
:::

## Overriding default themes {#overriding-default-themes}

Most branding work is done by **overriding the existing themes** rather than creating new ones. Retune the seed colors (or any other token) for the built-in `light`, `dark`, and `dark-pure` themes, and every component picks up the new look automatically.

You can override the **light** theme by redefining CSS custom properties in the `:root` selector.

:::info `:root` pseudo-class
The `:root` CSS pseudo-class targets the root element of the document. In HTML, it represents the `<html>` element and has higher specificity than the plain `html` selector.
:::

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
  --dwc-color-primary-seed: #a855f7;
}

html[data-app-theme="dark-pure"] {
  --dwc-color-primary-seed: #a855f7;
}
```

Switching themes with `App.setTheme("dark")` activates the rebranded dark theme, no new theme name is needed.

## Creating custom themes {#creating-custom-themes}

Create a fully new theme only when you need one that coexists with the built-ins (for example, a high-contrast variant or a customer-specific skin). Pick a unique name and define it under its own `html[data-app-theme='THEME_NAME']` selector:

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

## Working with DWC tokens {#working-with-dwc-tokens}

A few habits keep custom CSS aligned with the design system and prevent it from drifting in dark mode or future versions.

### Always reference tokens with `var(...)` {#always-reference-tokens-with-var}

Hardcoded color literals (`#3b82f6`, `rgb(59 130 246)`, `oklch(0.6 0.18 250)`) don't adapt to dark mode and don't track palette changes. Use the token instead.

```css
/* avoid */
.my-panel {
  background: #ffffff;
  color: #1f2937;
  border: 1px solid #e5e7eb;
}

/* prefer */
.my-panel {
  background: var(--dwc-surface-3);
  color: var(--dwc-color-body-text);
  border: 1px solid var(--dwc-border-color);
}
```

### Prefer variation tokens over raw step numbers {#prefer-variation-tokens-over-raw-step-numbers}

Variation tokens (`--dwc-color-primary`, `-dark`, `-light`, `-text`, `-alt`) resolve to a different step in light versus dark mode automatically. Raw step numbers (`--dwc-color-primary-50`) don't.

```css
/* avoid - frozen at step 50 in both modes */
.badge {
  background: var(--dwc-color-primary-50);
}

/* prefer - shifts step in dark mode */
.badge {
  background: var(--dwc-color-primary);
}
```

### Use the suffix that matches the role {#use-the-suffix-that-matches-the-role}

| Suffix                          | Role                                                              |
|---------------------------------|-------------------------------------------------------------------|
| `--dwc-color-{name}`            | Solid fill at full strength (buttons, badges, banners)            |
| `--dwc-color-{name}-dark`       | Active / pressed state                                            |
| `--dwc-color-{name}-light`      | Hover / focus background                                          |
| `--dwc-color-{name}-alt`        | Subtle tinted background for callouts and alt rows                |
| `--dwc-color-{name}-text`       | Colored text on a neutral surface                                 |
| `--dwc-color-on-{name}-text`    | Text placed **on** the colored shade as background (auto-contrast)|
| `--dwc-border-color-{name}`     | Borders and dividers                                              |

### Reserve surfaces and borders for their roles {#reserve-surfaces-and-borders-for-their-roles}

Surfaces (`--dwc-surface-1` / `-2` / `-3`) build the page hierarchy. Borders (`--dwc-border-color`, `--dwc-border-color-*`) draw separators. Reusing palette steps for these roles works visually but loses the automatic mode adaptation that the dedicated tokens carry.

### Override at the seed level in custom themes {#override-at-the-seed-level-in-custom-themes}

When building a custom theme, set the seed (`--dwc-color-{name}-h`, `-s`, or `-seed`) rather than overriding individual steps. The generator rebuilds the full 19-step palette around the seed, keeping the whole tonal range consistent. Overriding individual steps leaves the rest of the palette drifting against your brand color.

```css
/* avoid - leaves other steps inconsistent */
html[data-app-theme="brand"] {
  --dwc-color-primary-50: #6366f1;
}

/* prefer - regenerates the whole palette */
html[data-app-theme="brand"] {
  --dwc-color-primary-seed: #6366f1;
}
```

### Use tokens for spacing, sizing, radius, and transitions {#use-tokens-for-spacing-sizing-radius-and-transitions}

The same rule extends across the rest of the design system: reference tokens, never magic numbers.

```css
/* avoid */
.my-panel {
  padding: 16px;
  border-radius: 8px;
  transition: background-color 250ms;
}

/* prefer */
.my-panel {
  padding: var(--dwc-space-m);
  border-radius: var(--dwc-border-radius);
  transition: background-color var(--dwc-transition);
}
```

Hardcoded values bypass user-preference font-size scaling, lock you into a fixed shape language, and skip the design system's eased timing curves.

### Use `::part(...)` to reach into components {#use-part-to-reach-into-components}

webforJ components are Shadow DOM. Their internal markup is hidden from outside selectors, so a rule like `.dwc-button-label { ... }` won't match anything. To style internal pieces, target the exposed parts:

```css
/* style the label inside every primary button */
dwc-button[theme="primary"]::part(label) {
  letter-spacing: 0.02em;
}
```

See [Shadow Parts](./shadow-parts) for the full mechanic, and each component's **Styling → Shadow Parts** section for the parts it exposes.

### Scope token overrides with a wrapper selector {#scope-token-overrides-with-a-wrapper-selector}

CSS custom properties cascade. Setting a token on a wrapper element retunes everything inside it without affecting the rest of the app.

```css
.danger-section {
  --dwc-color-primary-seed: #ef4444;
}
```

Every component inside `.danger-section` (buttons, links, focus rings) now uses the danger-red hue, while the global theme stays unchanged.

### Test in both light and dark mode {#test-in-both-light-and-dark-mode}

Before shipping any custom CSS, switch the theme to `dark` and `dark-pure` and walk through the screen. The most common regression is hardcoded color values that looked fine in one mode and read as illegible or out-of-palette in the other.

### Don't reach for `!important` {#dont-reach-for-important}

It escapes the cascade and makes every future override harder. If a rule isn't winning, the cause is almost always a specificity mismatch with a cleaner fix: target the same selector the framework uses, or add a parent qualifier. Reserve `!important` for genuinely third-party styling that you have no other way to defeat.

## Component themes {#component-themes}

In addition to app-level themes, webforJ components support a set of **component themes** based on the default color palettes: `default`, `primary`, `success`, `warning`, `danger`, `info`, and `gray`. This is independent of the active app theme.

Each component documents its supported themes under the **Styling → Themes** section.
