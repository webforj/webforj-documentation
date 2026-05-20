---
title: Upgrading to the v26 Design System
description: Reference for the design-system updates in DWC 26 - color engine, dark mode, surfaces, shadows, typography, radius, focus ring, and interaction feedback.
sidebar_position: 2
---

DWC 26 introduces a refreshed design system. The update is incremental rather than a full rewrite: most v25 CSS variables remain available, the public API of the theme engine is preserved, and existing customizations continue to work without changes.

This guide documents what changed, where the visual output differs, and the upgrade steps required when an application depends on a specific v25 behavior.

## Quick verdict {#quick-verdict}

| If your app... | What to expect |
| --- | --- |
| Uses default styling | Visual refresh. Default palette hues were retuned (e.g. primary moved from `h: 211 / s: 100%` to `h: 223 / s: 91%`), shadows look more layered, and components feel rounder. No code change needed, but the brand-default colors shift. |
| Overrides `--dwc-color-{name}-h` and `-s` | Still works. The HSL seed path is preserved. |
| Overrides individual palette steps (e.g. `--dwc-color-primary-40`) | Numbers may resolve to different colors. See [Color palette](#color-palette-step-5-is-always-darkest). |
| Relies on `--dwc-color-{name}-c` | Remove. The light/dark text flip is now computed automatically per shade. |
| References named font-size tokens (`--dwc-font-size-m`, `-l`...) | The scale shifted down one bucket. `m` is now 14px instead of 16px. See [Typography](#typography). |
| Uses `--dwc-font-weight-semibold` to get 500-weight | `semibold` is now 600. Switch to the new `--dwc-font-weight-medium` for 500. |
| Reserves padding around focusable elements with `--dwc-focus-ring-width` | The ring now has a gap. Add `--dwc-focus-ring-gap` to that padding, or the ring will overflow. See [Focus ring](#focus-ring). |
| Customized button hover / ripple effects | Ripples are gone. Press feedback is now a small scale-down. |

If none of those apply, you can stop reading here. Your upgrade is done.

## What's new at a glance {#whats-new-at-a-glance}

- **Modern color engine.** Palettes are generated in OKLCH instead of HSL. Lightness steps are perceptually uniform (so adjacent steps look like adjacent steps), and dark mode no longer flips the palette.
- **Dark mode via one variable.** `--dwc-dark-mode: 1` flips the whole UI. Mode adaptation happens in the variation layer, not by remapping every step.
- **Automatic `on-text` colors.** Every palette step gets a `--dwc-color-on-{name}-text-{step}` companion clamped for WCAG AA contrast on that shade. You don't have to compute contrast manually.
- **Direct seed override.** Pass any CSS color (hex, `rgb()`, `oklch()`, `lab()`...) into `--dwc-color-{name}-seed` and the whole palette regenerates from it.
- **Retuned shadows.** The same six levels (`xs` through `2xl`), now with realistic layer falloff and an automatic dark-mode strength boost via `--dwc-shadow-strength`.
- **Surfaces and `default` use their own lightness curve.** Both now adapt to light/dark via `--dwc-dark-mode` and a small primary tint, instead of redefining surfaces in the dark theme and aliasing `default` to palette steps.
- **Scale press feedback.** Ripples are replaced by a small scale-down on press. Tokens: `--dwc-scale-press`, `--dwc-scale-press-deep`.
- **Focus ring with gap.** The ring now has a small surface-colored gap (`--dwc-focus-ring-gap`) before the colored shadow, so it stays visible on solid buttons and tight layouts.
- **Border radius is seeded.** Change `--dwc-border-radius-seed` and the `s` through `4xl` steps rescale proportionally (`2xs` and `xs` stay at fixed pixel values). New `3xl` and `4xl` steps.

## The color system {#the-color-system}

This is the biggest change under the hood. The behavior you see should be familiar, the internals are different.

### Two ways to define a color {#two-ways-to-define-a-color}

You can keep using hue + saturation as before, or override the seed directly with any CSS color.

```css
/* Hue + saturation (still the default path) */
:root {
  --dwc-color-primary-h: 223;
  --dwc-color-primary-s: 91%;
}

/* Direct seed - any CSS color format */
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

If you were already using `-h` and `-s`, you don't need to do anything. The seed override is the new path for direct brand colors.

### Color palette: step 5 is always darkest {#color-palette-step-5-is-always-darkest}

In v25, the palette flipped in dark mode (step 5 darkest in light, lightest in dark). In v26, step 5 is always the darkest shade and step 95 is always the lightest, regardless of mode. Mode adaptation now happens one layer up, in the variation tokens:

```css
/* v26 - variations point at fixed steps */
--dwc-color-primary-dark:  var(--dwc-color-primary-45);
--dwc-color-primary:       var(--dwc-color-primary-50);
--dwc-color-primary-light: var(--dwc-color-primary-55);
```

| If you... | What changes |
| --- | --- |
| Use `--dwc-color-primary` (or `-dark`, `-light`, `-text`) | Nothing. Variations still behave the same across modes. |
| Hardcoded a step like `--dwc-color-primary-40` | That step now resolves to the same OKLCH lightness in both modes. The color you saw in dark mode came from a different step. Switch to the variation token if you want mode-aware behavior. |
| Wrote `hsl(var(--dwc-color-primary-h), ...)` directly | Still works. The HSL seed is still constructed from h + s. |

### Colors are derived, not promised {#colors-are-derived-not-promised}

:::info Heads up
The hue you set is a **seed**, not a target. The color you pass via `--dwc-color-{name}-h` / `-s` (or `-seed`) will not necessarily appear at step 50.
:::

Because the palette uses absolute OKLCH lightness per step, where your seed lands depends on its natural lightness. Bright hues (cyan, yellow) have high OKLCH lightness and end up around step 80-85. Darker hues (blue) sit near step 50 by coincidence.

If you need an exact color at an exact step, set the step explicitly:

```css
:root {
  --dwc-color-primary-50: #1d4ed8;
}
```

### `--dwc-color-{name}-c` is gone {#dwc-color-name-c-is-gone}

In v25, `-c` was the contrast threshold: the background lightness at which the companion text color flipped from white to black. A value of `50` meant text was white on backgrounds darker than 50% and black on backgrounds lighter than 50%.

In v26 you don't pick a flip point. Each step gets a tinted `on-text` color computed automatically from the shade itself. The result is always WCAG AA-safe and keeps a hint of the palette hue instead of falling to pure black or white.

If you have any `--dwc-color-{name}-c` overrides, you can delete them, they have no effect.

### Text and `on-text` colors {#text-and-on-text-colors}

v25 had one per-step text token, `--dwc-color-{name}-text-{step}`, which was a pure black-or-white color computed from the `-c` threshold and intended for text **on** that step as a background.

v26 keeps the same token name but changes its meaning, and adds a second per-step token:

| Token | v25 meaning | v26 meaning |
| --- | --- | --- |
| `--dwc-color-{name}-text-{step}` | Pure black/white, intended for text on the shade as a background | Tinted, **surface-safe** text, readable on neutral page backgrounds |
| `--dwc-color-on-{name}-text-{step}` | (didn't exist as a per-step token) | Tinted text for use **on** that step as a background |

Both v26 tokens are clamped for WCAG AA contrast on their intended background. If you used `--dwc-color-{name}-text-{step}` as the foreground on a colored background, switch to `--dwc-color-on-{name}-text-{step}` (the new `on-text` token) to preserve that semantic.

### Tints and borders {#tints-and-borders}

The generator now emits three tokens per palette, one truly new, one new variant, and one whose source moved:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-tint` | (didn't exist) | Seed at 12% opacity, for alt backgrounds |
| `--dwc-border-color-{name}-emphasis` | (didn't exist) | Stronger mode-aware border for hover/focus/active |
| `--dwc-border-color-{name}` | Set per variation as `var(--dwc-color-{name})` (the saturated shade) | Computed in the generator: mode-aware lightened tone of the seed |

If your CSS read `--dwc-border-color-primary` expecting the saturated primary color, the visual is now a subtle separator tone instead. If you specifically want the saturated look, switch to `--dwc-color-primary` directly.

## Dark mode {#dark-mode}

Dark mode is controlled by a single variable, `--dwc-dark-mode`. Set it to `1` for dark, `0` for light:

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

It participates in `calc()` expressions throughout the system, which is how mode adaptation propagates to surfaces, shadows, borders, and text colors.

In v25, the built-in `dark` and `dark-pure` themes had to redefine surfaces, shadows, and many palette variations manually. In v26, all of that is derived from `--dwc-dark-mode` and the seed colors. A typical custom dark theme that used to be a 20-line override block becomes:

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  color-scheme: dark;
}
```

If you have a custom dark theme copied from the v25 structure, you can usually delete most of the inner block and keep only the seed and the dark-mode flag.

## Surfaces and `default` {#surfaces-and-default}

In v25, surfaces were defined twice, once in `:root` for light mode (`hsl(default-h, default-s, 96%)` etc.) and again in the dark theme (`hsl(default-h, default-s, 8%)` etc.). The `default` variation pointed at palette steps and similarly needed dark-theme overrides.

In v26, both are computed once with a `--dwc-dark-mode` term baked into the lightness calculation, which guarantees:

- Surfaces always sit slightly below `default`, so cards visually float above the page.
- A small primary tint is applied via the seed's chroma in both modes.
- `dark-pure` theme sets `--dwc-color-default-s: 0%`, which drops the tint to zero automatically.

If your app overrides `--dwc-surface-1` (or any other surface) with a fixed color, it still works; you just opt out of the automatic mode adaptation.

The `--dwc-color-{name}-alt` token's source also changed:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-alt` | Palette step 95 (near-white background) | Seed at 12% opacity (translucent tint) |

If you used `-alt` as a solid near-white background, it will now read as a translucent tinted overlay. Either pick a specific step (`--dwc-color-{name}-95`) or design around the translucent semantic.

## Shadows {#shadows}

The six-level scale (`xs`, `s`, `m`, `l`, `xl`, `2xl`) is unchanged in name and count, but the layer offsets were rebuilt for realistic falloff and dark-mode shadows are now automatically 5x stronger via `--dwc-shadow-strength` because dark surfaces need more contrast to convey depth.

If you only use `var(--dwc-shadow)` you get the retuned medium shadow and nothing else changes. The `--dwc-shadow-color` variable is still emitted, but its value format changed:

| | v25 | v26 |
| --- | --- | --- |
| `--dwc-shadow-color` | HSL triplet (`h, s%, l%`) | Full OKLCH color |

If your CSS uses the legacy triplet form like `hsla(var(--dwc-shadow-color), 0.07)`, switch to a full shadow token (`var(--dwc-shadow-m)`) or rewrite with `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

## Typography {#typography}

The component sizing tokens (`--dwc-size-*`) are unchanged. The font scale was retuned to anchor the `m` tier at the same lightweight body size used by other DWC tokens, so the bucket names shifted down by one step:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-size-3xs` | 10px | 10px |
| `--dwc-font-size-2xs` | 12px | 11px |
| `--dwc-font-size-xs` | 13px | 12px |
| `--dwc-font-size-s` | 14px | 13px |
| `--dwc-font-size-m` | 16px | 14px |
| `--dwc-font-size-l` | 18px | 16px |
| `--dwc-font-size-xl` | 22px | 20px |
| `--dwc-font-size-2xl` | 28px | 26px |
| `--dwc-font-size-3xl` | 36px | 34px |

The default `--dwc-font-size` still resolves to **14px**, it just gets there via `--dwc-font-size-m` (v26) instead of `--dwc-font-size-s` (v25).

If your CSS references font-size tokens by name (e.g. `font-size: var(--dwc-font-size-l)`), the visible result will be smaller in v26. Step up one bucket to preserve the v25 size.

Font weights gained three tokens (`thin`, `medium`, `black`) and one existing token shifted:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-weight-semibold` | 500 | 600 |
| `--dwc-font-weight-medium` | (didn't exist) | 500 |

If you used `--dwc-font-weight-semibold` to get 500-weight text, switch to the new `--dwc-font-weight-medium`.

Line-height buckets shifted in the same direction as font sizes; the default `--dwc-font-line-height` still resolves to 1.25.

`--dwc-font-family-sans` and `--dwc-font-family-mono` were modernized to use `system-ui` and `ui-monospace` stacks. If you targeted a specific named font from the old stack (`Dank Mono`, `Operator Mono`, `Roboto`, etc.) and want it back, set `--dwc-font-family` to a stack you control.

## Spacing {#spacing}

The space scale is unchanged from `xs` onward. Only the two smallest tokens were rounded to whole-pixel values:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-space-3xs` | 1.2px | 1px |
| `--dwc-space-2xs` | 2.4px | 2px |

No action needed in almost any app.

## Border radius {#border-radius}

Border radius is now seeded. Change `--dwc-border-radius-seed` and every step (`s`, `m`, `l`, `xl`, `2xl`, `3xl`, `4xl`) rescales proportionally. The `2xs` and `xs` steps are still pinned to fixed pixel values (too small to derive meaningfully).

Three things changed:

| | v25 | v26 |
| --- | --- | --- |
| Unit | `em` (scales with parent font-size) | `rem` (scales with root font-size) |
| Default `--dwc-border-radius` | `--dwc-border-radius-s` (4px) | `--dwc-border-radius-seed` (8px) |
| Steps available | up to `2xl` | adds `3xl`, `4xl` |

Components feel rounder out of the box. If a component nested inside larger text used to inherit a bigger radius via `em`, that scaling no longer happens, radii are now anchored to the root. If you want the v25 default size back, halve the seed:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, halves the whole scale */
}
```

## Easings {#easings}

The easing catalog is mostly the same, with new shortcut tokens for the common cases: `--dwc-ease`, `--dwc-ease-in`, `--dwc-ease-out`, `--dwc-ease-outGlide`. See the [Transitions](/docs/styling/transitions-easing) page for the full list.

## Transitions {#transitions}

Transition durations were rebalanced for a snappier feel:

| Variable | v25 | v26 |
| --- | --- | --- |
| `--dwc-transition-slow` | 500ms | 300ms |
| `--dwc-transition-medium` | 250ms | 250ms |
| `--dwc-transition-fast` | 150ms | 150ms |
| `--dwc-transition-x-fast` | 50ms | 100ms |

If you depend on a specific duration, override it in `:root`.

## Focus ring {#focus-ring}

The focus ring now uses a double-ring pattern: a small surface-colored gap, then the colored ring. This keeps the ring readable on solid buttons and dense layouts.

| Variable | v25 | v26 |
| --- | --- | --- |
| `--dwc-focus-ring-width` | 3px | 2px |
| `--dwc-focus-ring-a` | 0.4 | 0.75 |
| `--dwc-focus-ring-gap` | (none) | 2px |
| `--dwc-focus-ring-l` | 45% | (removed, lightness is computed per mode) |

If you reserve space around focusable elements with `padding: var(--dwc-focus-ring-width)`, add the gap to that padding so the new ring has room to render:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

## Interaction feedback {#interaction-feedback}

Material-style ripple effects are no longer used by any DWC component. The new feedback for any clickable element is a small scale-down:

```css
--dwc-scale-press: 0.97;      /* Standard 3% shrink */
--dwc-scale-press-deep: 0.93; /* Deeper 7% shrink for buttons */
```

The `ripple` SCSS mixin and the `--dwc-ripple-color` CSS variable still exist in the build, but nothing imports them by default. If your own components opted into the mixin, switch to the press-scale tokens to match the new feel.

## Browser support {#browser-support}

The new system uses two CSS features whose browser compatibility tables you can check on MDN:

- [OKLCH color space](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/oklch#browser_compatibility), includes relative color syntax (`oklch(from ...)`)
- [`color-mix()`](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/color-mix#browser_compatibility)

Both have shipped in evergreen Chrome, Edge, Firefox, and Safari.

## A pragmatic upgrade checklist {#a-pragmatic-upgrade-checklist}

1. Search for `--dwc-color-*-c` and delete those declarations.
2. Search for `hsla(var(--dwc-shadow-color)` and replace with a shadow token (`var(--dwc-shadow-m)`) or rewrite as `oklch(from ...)`.
3. Search for direct palette step references (`--dwc-color-{name}-{number}`). If any feed dark-mode-specific styling, switch to variation tokens (`--dwc-color-{name}`, `-dark`, `-light`).
4. Search for named font-size references (`--dwc-font-size-m`, `-l`, ...). If you want the v25 size, step up one bucket.
5. Search for `--dwc-font-weight-semibold`. If you wanted 500, switch to `--dwc-font-weight-medium`.
6. If you reserve space around focusable elements with `--dwc-focus-ring-width`, add `--dwc-focus-ring-gap` to the padding.
7. Open the app, click around. Most apps need nothing else.
