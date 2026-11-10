---
title: "A Java design system built in: webforJ DWC tokens and themes"
description: "A Java design system ships with webforJ: DWC gives you CSS tokens, three themes, generated palettes and Shadow Parts. Here are the myths worth dropping."
slug: java-design-system
date: 2026-10-03
authors: Lauren Alamo
tags: [styling, themes]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-03-java-design-system/cover.jpg
hide_table_of_contents: false
---

<!-- vale Google.FirstPerson = NO -->

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-03-java-design-system/cover.jpg)

If you've been wondering whether a Java design system is something your team has to build on top of the framework, I have good news. webforJ ships one called DWC: a set of CSS custom properties for color, typography, spacing, borders and shadows, three built-in themes, palettes generated from a seed color, and Shadow Parts for the places tokens don't reach. Override a few variables and the whole app takes on your brand.

<!-- truncate -->

Styling is usually where Java UI frameworks hand you a pile of parts and wish you luck. Here it arrives finished, and that changes how the work feels. Let's walk through the myths I'd drop first, and what's true instead.

## Myth: you need a separate library {#myth-separate-library}

**Fact.** In webforJ the design system is the component layer. DWC is the visual language behind every webforJ component, and its core is a set of CSS variables that cover colors, typography, borders, and spacing. Components read those variables, so there's no second styling package to install, version, or keep in sync.

That also answers the "UI kit for Java" question. You don't pick a kit and then wire it to your components. The components arrive already built on the tokens, and the [styling overview](/docs/styling/overview) lists each token family.

## Myth: dark mode needs a stylesheet {#myth-dark-mode}

**Fact.** webforJ ships `light`, `dark` and `dark-pure` themes. A theme is a named set of tokens, and switching recalculates colors, shadows, surfaces and borders across the app with no rebuild. I like this a lot, because nobody has to maintain a second stylesheet that slowly drifts away from the first.

You choose the theme with an annotation. To follow the user's operating system preference, register one theme per appearance and set the reserved `system` keyword:

```java
@StyleSheet("ws://css/brand.css")
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
public class BrandApp extends App {
}
```

webforJ resolves `system` to the light or dark theme at runtime and re-resolves when the OS preference changes. The `data-app-theme` attribute on the page then holds `light` or `dark`, which is what your CSS overrides should target. The full walkthrough, including a toggle that remembers the choice, is in [dark mode in a Java web app](/blog/java-dark-mode).

## Myth: branding means restyling all {#myth-branding}

**Fact.** Branding is one seed value. Set a palette's seed to your brand color and all 19 steps of that palette are generated from it. Here's what that looks like when you retune the built-in themes instead of creating new ones:

```css
:root {
  --dwc-color-primary-seed: #2563eb;
}

html[data-app-theme="dark"] {
  --dwc-color-primary-seed: #a855f7;
}
```

The first rule rebrands the light theme and the second rebrands the dark one. Buttons, links and focus rings pick up the change because they consume the primary palette and not a hardcoded color. Seven palettes exist (default, primary, success, warning, danger, info and gray), plus a mode-aware black and white. Details are on the [colors page](/docs/styling/colors).

## Myth: tokens can't be scoped {#myth-scoping}

**Fact.** CSS custom properties cascade, so any wrapper element can retune everything inside it. This is how you build a danger zone, a customer-specific panel or a high-contrast section without a new theme.

```java
public class DangerZone extends Composite<Div> {
  public DangerZone() {
    getBoundComponent().addClassName("danger-section");
    getBoundComponent().add(
        new Paragraph("Deleting the project cannot be undone."),
        new Button("Delete project", ButtonTheme.PRIMARY));
  }
}
```

```css
.danger-section {
  --dwc-color-primary-seed: #ef4444;
}
```

The button inside the section renders in red while the rest of the app keeps the global theme. It's a small trick, but it feels right to me, since the red lives exactly where the warning lives. A new theme name is only worth creating when a variant has to live next to the built-in ones for the whole app.

## Myth: Shadow DOM blocks custom styling {#myth-shadow-dom}

**Fact.** webforJ components use Shadow DOM, so a selector like `.dwc-button-label` matches nothing. The components expose named parts instead, and you target them with `::part()`:

```css
dwc-button[theme="primary"]::part(label) {
  letter-spacing: 0.02em;
}
```

Each component page lists the parts and CSS properties it exposes under its Styling section. You get fine-grained control while the component internals stay encapsulated. For values like `--dwc-button-font-weight`, set them on the component's selector or from Java with `setStyle`.

## What a consistent Java UI looks like {#consistent-ui}

The rules that keep an app on the design system are short:

| Instead of | Use |
|---|---|
| `#ffffff`, `#1f2937` | `var(--dwc-surface-3)`, `var(--dwc-color-body-text)` |
| `padding: 16px` | `padding: var(--dwc-space-m)` |
| `border-radius: 8px` | `var(--dwc-border-radius)` |
| `var(--dwc-color-primary-50)` | `var(--dwc-color-primary)` |
| A new palette step override | A seed override |

Hardcoded colors don't adapt to dark mode. Raw step numbers stay frozen in both modes, while variation tokens such as `--dwc-color-primary` shift automatically. Spacing in tokens also respects user font-size scaling, which fixed pixel values skip.

I'd make dark mode testing a habit. Before shipping custom CSS, switch to `dark` and `dark-pure` and walk through each screen. The usual regression is a hardcoded color that looked fine in one mode and unreadable in the other.

Fonts follow the same rule. The default is the system font stack via `system-ui`, so each platform renders its native face. To use your brand typeface, set `--dwc-font-family` once on `:root` and every component picks it up, with no per-component font declarations to chase.

I'd also steer clear of `!important`. When a rule isn't winning, the cause is nearly always specificity, and the cleaner fix is to target the same selector the framework uses or add a parent qualifier.

This is why I think of tokens as the contract between design and code. A designer hands over a brand color and a type scale. You map them to a seed and a font-size token once, and every screen inherits them. The CSS can live in any file you load with `@StyleSheet`, as covered in [importing assets](/docs/managing-resources/importing-assets), and the whole thing stays plain CSS. For the theming basics, the earlier post on [professional theming](/blog/guide-webforj-themes) is a good companion.

## Why this beats assembling your own {#why-it-wins}

Assembling your own design system means picking a CSS framework, mapping it to component internals, and then maintaining that mapping yourself. With webforJ the tokens, themes, palettes and parts come with the framework, and each component page documents its themes and parts. You end up with one place to change a brand color, one annotation to switch a theme, and one cascade to reason about.

The other tokens follow the same pattern. Typography has a font family property and a size scale in `rem`, spacing and sizing are `rem` scales too, and three surface tokens build the page hierarchy: `--dwc-surface-1` for the page, `--dwc-surface-2` for toolbars and cards, and `--dwc-surface-3` for dialogs and popovers. All palette colors are tested for contrast against those surfaces, so a brand change doesn't quietly break readability.

## Next step {#next-step}

Open the [themes page](/docs/styling/themes) and change `--dwc-color-primary-seed` in a fresh project. Then switch to `dark` and walk through your screens. If you need a project to try it on, generate one with [startforJ](https://docs.webforj.com/startforj) or follow [getting started](/docs/introduction/getting-started).

## Frequently asked questions {#faq}

### Does webforJ have a UI kit for Java? {#faq-ui-kit}

Yes. The components themselves are the kit, and they share one design system, DWC. Components support a shared set of component themes based on the default palettes: default, primary, success, warning, danger, info, and gray. Each component page lists the themes it supports.

### Can I use my own design tokens? {#faq-own-tokens}

Yes. Tokens are standard CSS custom properties, so you can define your own next to the `--dwc-*` ones and reference them with `var()`. To make your brand color drive the components, set it as the palette seed instead of overriding individual steps.

### How do I change tokens at runtime from Java? {#faq-runtime-tokens}

Call `setStyle` on a component with the variable name and value, for example `button.setStyle("--dwc-button-font-weight", "400")`. For app-wide changes, switch the theme with `App.setTheme()`.
