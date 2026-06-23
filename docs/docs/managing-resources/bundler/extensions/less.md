---
title: Less
sidebar_position: 40
sidebar_class_name: new-content
description: Compile Less to CSS with the webforj-less extension, bind a class to the stylesheet, and pass Less options through bun.config.ts.
---

[Less](https://lesscss.org/) is a stylesheet language that adds variables, mixins, and nesting to CSS. The Less extension activates when a `.less` source is present and compiles it to CSS.

## Example {#example}

```less title="less/box.less"
@accent: #2e9e6b;

.box {
  padding: 1rem;
  border: 2px solid @accent;
}
```

```java title="LessView.java"
@Route("/less")
@BundleEntry("less/box.less")
public class LessView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public LessView() {
    Div box = new Div().addClassName("box");
    self.add(box);
  }
}
```

## Options {#options}

The Less extension forwards its options to [Less](https://lesscss.org/usage/#less-options), which it calls to render each source. The filename is set for you. Set the rest under `webforj-less` in `bun.config.ts`:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-less': { /* Less options */ }
};
```
