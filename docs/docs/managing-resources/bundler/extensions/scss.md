---
title: SCSS and Sass
sidebar_position: 30
sidebar_class_name: new-content
description: Compile SCSS or Sass to CSS with the webforj-scss extension, bind a class to the stylesheet, and set a load path through bun.config.ts.
---

[Sass](https://sass-lang.com/) is a stylesheet language that adds variables, nesting, and functions to CSS, and SCSS is its CSS-like syntax. The SCSS extension activates when a `.scss` or `.sass` source is present, compiles it to CSS, and loads it as styles. A class binds to the compiled stylesheet the same way it binds to a script.

## Example {#example}

```scss title="scss/box.scss"
@use 'tokens' as t;

.box {
  padding: 1rem;
  border: 2px solid t.$accent;
}
```

```java title="ScssView.java"
@Route("/scss")
@BundleEntry("scss/box.scss")
public class ScssView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ScssView() {
    Div box = new Div().addClassName("box");
    self.add(box);
  }
}
```

## Options {#options}

The SCSS extension forwards its options to the [Dart Sass compiler](https://sass-lang.com/documentation/js-api/interfaces/options/). The most common is a load path, which lets an entry `@use` a partial that lives outside its own folder:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

With that load path, `@use 'tokens'` resolves a partial under `src/main/frontend/styles`. The Sass options reference lists the rest.
