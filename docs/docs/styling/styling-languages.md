---
title: Styling languages
sidebar_position: 11
sidebar_class_name: new-content
description: Author your styles in CSS, compile them from Sass or Less, or generate them with Tailwind, and load the result into a webforJ app.
---

Your styles reach the page as CSS, but you don't have to write them as CSS. webforJ loads a stylesheet you author, compiles one from a preprocessor such as Sass or Less, or generates one from Tailwind, and the result styles your views the same way wherever it came from. The DWC tokens, [CSS custom properties](/docs/styling/css-variables), and [shadow parts](/docs/styling/shadow-parts) covered in the rest of this section apply inside any of them.

## Plain CSS {#plain-css}

A stylesheet you write needs no build. Attach it to a component or the app with [`@StyleSheet`](/docs/managing-resources/importing-assets#importing-css-files). When you already run the [frontend bundler](/docs/managing-resources/bundler/overview), you can instead bind a `.css` file to a class with `@BundleEntry`, which loads it as styles for that view.

## Sass and Less {#sass-and-less}

To write your styles in [Sass](https://sass-lang.com/) or [Less](https://lesscss.org/), with variables, nesting, and functions, author the source and let the [frontend bundler](/docs/managing-resources/bundler/overview) compile it to CSS. The compiler is an [extension](/docs/managing-resources/bundler/extensions/overview) that turns itself on when a source of its type is present, so authoring a `.scss`, `.sass`, or `.less` file is the only signal it needs. Bind the source to a class the same way you bind a stylesheet:

```java title="StyledView.java"
@Route("/styled")
@BundleEntry("styles/view.scss")
public class StyledView extends Composite<FlexLayout> {
  // build the view
}
```

The extension compiles `view.scss` to CSS and loads it for the view. See [SCSS and Sass](/docs/managing-resources/bundler/extensions/scss) and [Less](/docs/managing-resources/bundler/extensions/less) for the file layout, load paths, and options each one accepts.

## Tailwind {#tailwind}

[Tailwind](https://tailwindcss.com/) generates a stylesheet from the utility class names your views use, rather than from a file you author. Turn the extension on, then add utilities as class names with nothing to import. webforJ leaves Tailwind's base reset out so it doesn't fight the styling your components already carry, and a utility reaches the element you put it on, not the inside of a component. See [Tailwind Extension](/docs/managing-resources/bundler/extensions/tailwind) for how it generates and scopes its stylesheet, and where utility classes do and don't apply.

## Another language {#another-language}

The compiler for each language is a bundler extension, and the model is open. To author your styles in a language webforJ ships no compiler for, write a small extension that contributes that compiler, on the same contract Sass and Less use. See [Writing your own extension](/docs/managing-resources/bundler/extensions/writing-your-own).
