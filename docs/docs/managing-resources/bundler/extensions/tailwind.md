---
title: Tailwind
sidebar_position: 60
sidebar_class_name: new-content
description: Turn on the webforj-tailwind extension, apply utility classes from a view, and understand how it generates and scans its own stylesheet.
---

[Tailwind CSS](https://tailwindcss.com/) is a utility-first CSS framework whose class names each map to a small set of CSS declarations. It's the one curated extension that ships off, because most projects don't use it. You enable it by id, the same way you turn any extension on, see [Enabled by id](/docs/managing-resources/bundler/extensions/overview#enabled-by-id). When it's on, it does something the others don't: it generates its own entry.

## How it works {#how-it-works}

Rather than compile a file you wrote, the Tailwind extension scans your app sources for the utility class names they use, generates a stylesheet that carries only those utilities, and loads it for every view. A view then applies utilities as class names with nothing to import:

```java title="TailwindView.java"
@Route("/tailwind")
public class TailwindView extends Composite<FlexLayout> {

  public TailwindView() {
    Div card = new Div("Styled by compiled Tailwind utilities");
    card.addClassName("flex", "items-center", "gap-4", "p-8", "rounded-lg",
        "bg-blue-500", "text-white");
    getBoundComponent().add(card);
  }
}
```

The generated stylesheet imports Tailwind's theme and utilities but not its base reset. The reset, Tailwind's preflight, restyles every bare element on the page, which would override the styling webforJ already applies to its components. Leaving it out keeps the utility classes you add working without disturbing the components you didn't change.

Because the utilities come from the class names your views use, the [frontend watch](/docs/configuration/deploy-reload/frontend-watch) follows your app sources as well as `src/main/frontend`. Add or remove a utility class in a view and save, and the stylesheet regenerates and patches into the page in place, the same as editing a stylesheet you wrote.

## Where utility classes reach {#where-utility-classes-reach}

:::warning A utility class styles the element, not the inside of a component
webforJ components render with a shadow DOM that keeps their internal structure private. A utility class added to a component styles its outer box only, its spacing, sizing, and place in a layout, and never reaches the elements drawn inside it. Utilities apply the way their class names read on a layout container or a plain `Div` you build, where there's no boundary to cross, but not on the internals of a built up component.

To style what's inside a component, use the styling the component exposes instead: [shadow parts](/docs/styling/shadow-parts) through `::part()` and the component's [CSS custom properties](/docs/styling/css-variables), both listed in each component's styling reference. Keep utilities for layout and for your own elements, and use a component's own styling to retune the component.
:::

The stylesheet carries the utility class names the scan finds in your sources, and only those. A class you type into the browser inspector to try an idea won't apply, because it was never compiled in. Put the class in a view and save, and the watch regenerates the stylesheet with it.

When the same group of utilities repeats across many views, name it: define a CSS class once and add that instead. A few utilities inline stay legible, a long string repeated by hand drifts as you edit.

## Options {#options}

The Tailwind extension takes no options from `bun.config.ts`. It generates and scans its own stylesheet, and Tailwind itself is configured through that stylesheet rather than through the extension.
