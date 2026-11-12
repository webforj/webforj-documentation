---
title: "Dark mode in a Java web app: themes, toggle, saved choice"
description: "Add dark mode in a Java web app with webforJ: built-in themes, OS preference, a header toggle, a remembered choice and custom colors that work in both modes."
slug: java-dark-mode
date: 2026-09-22
authors: Eric Handtke
tags: [styling, themes, web development]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-22-java-dark-mode/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-22-java-dark-mode/cover.jpg)

To add dark mode in a Java web app with webforJ, put `@AppTheme("system")` on your `App` class and register a light and a dark theme with `@AppLightTheme` and `@AppDarkTheme`. The app then follows the operating system setting. To let users choose for themselves, call `App.setTheme()` from a control in your header and save the answer with `LocalStorage`.

webforJ ships a `light` and a `dark` theme, so every component already has a dark variant. The only work left for you is your own CSS. This post covers the setup, the switch, the saved choice and how to write custom styles that hold up in both modes.

<!-- truncate -->

## Which themes does webforJ include? {#built-in-themes}

The framework comes with a `light` theme, which is the default, and a `dark` theme. Both give their surfaces a subtle tint of the primary color, so the dark surfaces carry a hint of your brand. The [themes documentation](/docs/styling/themes) describes them.

A theme is a set of CSS custom properties. Switching it recalculates colors, surfaces, shadows and borders for every component at once, with no rebuild and no second stylesheet. The [theming guide](/blog/guide-webforj-themes) covers the general theming model, so this post sticks to dark mode.

## How do you add dark mode in a Java web app? {#default-theme}

Annotate your `App` class:

```java
@AppTheme("dark")
public class MyApp extends App {
}
```

The value is one of `system`, `light`, `dark`, or the name of a custom theme. You can also set it in code with `App.setTheme("dark")`, and read the current value with `App.getTheme()`.

## How do you follow the operating system preference? {#follow-the-os}

Most users already told their device whether they like light or dark. Use the reserved value `system` and tell webforJ which theme to use for each case:

```java
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
public class MyApp extends App {
}
```

The `system` keyword resolves at runtime to the registered light or dark theme, and it resolves again whenever the user changes the OS setting while the app is open.

One detail matters for your CSS. After resolution, the `data-app-theme` attribute on the page holds the resolved theme name, such as `light` or `dark`, never `system`. Target those names in your selectors.

## How do you switch themes at runtime from Java? {#switch-at-runtime}

`App.setTheme()` works at any time, so a theme switcher is a normal component with a click or select listener. This layout puts a `ChoiceBox` in the header toolbar of an `AppLayout`:

```java
public class MainLayout extends Composite<AppLayout> {

  public MainLayout() {
    ChoiceBox themes = new ChoiceBox();
    themes.insert("system", "light", "dark");
    themes.selectKey(App.getTheme());
    themes.onSelect(e -> {
      String choice = (String) e.getSelectedItem().getKey();
      App.setTheme(choice);
    });

    Toolbar header = new Toolbar();
    header.addToEnd(themes);
    getBoundComponent().addToHeader(header);
  }
}
```

Offering `system` as an option matters. It lets a user who tried a fixed theme go back to following their device. A two-state button that flips between `light` and `dark` is fine too, but then the OS preference is gone once it's clicked.

The change applies to the whole page at once. See the [AppLayout documentation](/docs/components/app-layout) for the header and drawer slots.

## How do you remember the user's choice? {#remember-the-choice}

`App.setTheme()` changes the page, but it doesn't persist anything. The web storage API handles that. `LocalStorage` keeps a value in the browser until it's deleted, and the [web storage documentation](/docs/advanced/web-storage) lists `CookieStorage` and `SessionStorage` as alternatives with different lifetimes.

Save the value when the user picks a theme, and read it back when the layout is created:

```java
public MainLayout() {
  String saved = LocalStorage.getCurrent().get("theme");
  if (saved != null) {
    App.setTheme(saved);
  }

  ChoiceBox themes = new ChoiceBox();
  themes.insert("system", "light", "dark");
  themes.selectKey(saved != null ? saved : App.getTheme());
  themes.onSelect(e -> {
    String choice = (String) e.getSelectedItem().getKey();
    App.setTheme(choice);
    LocalStorage.getCurrent().add("theme", choice);
  });

  // add the ChoiceBox to the header as before
}
```

If nothing is stored, the annotation defaults apply, so new visitors still get the `system` behavior. The value lives in the user's browser, so it's per device. If you want the choice to follow the account across devices, store it next to the user's profile in your own database instead and call `App.setTheme()` after login.

## How do you make custom colors work in both modes? {#custom-colors}

Dark mode breaks when CSS hardcodes colors. A white card with `#ffffff` stays white on a dark page. The fix is to reference the [design tokens webforJ already switches for you](/blog/java-design-system).

```css
.status-card {
  background: var(--dwc-surface-2);
  color: var(--dwc-color-body-text);
  border: 1px solid var(--dwc-border-color);
  border-radius: var(--dwc-border-radius);
  padding: var(--dwc-space-m);
}

.status-card.ok {
  background: var(--dwc-color-success-alt);
  color: var(--dwc-color-success-text);
  border-color: var(--dwc-border-color-success);
}
```

Each token resolves to a different value per theme. `--dwc-surface-1`, `--dwc-surface-2` and `--dwc-surface-3` form the page hierarchy, as described on the [surfaces page](/docs/styling/surfaces). `--dwc-color-body-text` flips from near-black to near-white. The `success-alt` and `success-text` pair gives a tinted callout that stays readable in both modes.

Prefer the variation tokens such as `--dwc-color-primary` over raw steps such as `--dwc-color-primary-50`. A variation moves to a different step in dark mode and a raw step doesn't. The [colors page](/docs/styling/colors) explains the palette system.

## How do you set a brand color per mode? {#brand-color}

Override the built-in themes instead of creating new ones. The light theme is the `:root` selector. The dark theme uses an attribute selector on `<html>`:

```css
:root {
  --dwc-color-primary-seed: #2563eb;
}

html[data-app-theme="dark"] {
  --dwc-color-primary-seed: #60a5fa;
}
```

A single seed regenerates the full palette, so buttons, links, and focus rings all follow. A slightly lighter seed in the dark theme keeps contrast comfortable against dark surfaces.

Create a separate custom theme only when it has to exist next to the built-ins. If that theme is dark, declare it, so the browser also draws scrollbars and form widgets in dark:

```css
html[data-app-theme="brand-dark"] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

## What about images and icons? {#images-and-icons}

Tokens cover colors but not bitmaps. A logo drawn for a white background can disappear on a dark page. Ship a dark variant of the asset and show the one that matches the theme. Files in `src/main/resources/static` are reachable through the `ws://` protocol, which the [assets protocols page](/docs/managing-resources/assets-protocols) describes.

```java
public class Logo extends Composite<Div> {
  public Logo() {
    Img light = new Img("ws://img/logo-light.svg", "Acme");
    light.addClassName("logo-light");
    Img dark = new Img("ws://img/logo-dark.svg", "Acme");
    dark.addClassName("logo-dark");
    getBoundComponent().add(light, dark);
  }
}
```

```css
.logo-dark {
  display: none;
}

html[data-app-theme="dark"] .logo-light {
  display: none;
}

html[data-app-theme="dark"] .logo-dark {
  display: inline;
}
```

Icons from the built-in icon sets inherit the theme of their parent component, so an icon inside a button follows the button's colors and needs no extra rule.

## How do you test it? {#testing}

Before you ship, step through each screen in `light` and `dark`. The usual failures are a hardcoded color literal that looked right in one mode, a raw palette step that didn't move, and a raster image with a white background. A search of your stylesheets for `#` and `rgb(` finds most of them.

To try this in a new project, generate one with [startforJ](https://docs.webforj.com/startforj) or follow the [getting started guide](/docs/introduction/getting-started), then add the three annotations from the OS preference section.

## Frequently asked questions {#faq}

### Does webforJ support dark mode out of the box? {#faq-out-of-the-box}

Yes. The `dark` theme ships with the framework and every component adapts to it. You enable it with `@AppTheme` or `App.setTheme()`.

### How do I follow the user's system dark mode setting? {#faq-system-preference}

Set `@AppTheme("system")` together with `@AppLightTheme` and `@AppDarkTheme`. webforJ resolves `system` to the matching theme and updates it when the operating system setting changes.

### Where should I store the user's theme choice? {#faq-storing-choice}

For a per-device preference, use `LocalStorage`. `CookieStorage` and `SessionStorage` are there when you need a different lifetime. For a preference that follows the account, keep it in your own user data and call `App.setTheme()` after login.

### Why does one of my components look wrong in dark mode? {#faq-wrong-in-dark}

The cause is usually a hardcoded color or a raw palette step in your CSS. Replace it with a surface, border or variation token, which each resolve per theme.
