---
title: Less
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  Compile Less to CSS with the webforj-less extension, bind a class to the
  stylesheet, and pass Less options through bun.config.ts.
_i18n_hash: febd9ab468f73672d97e3a0048d6371b
---
[Less](https://lesscss.org/) is een stylesheettaal die variabelen, mixins en nesting aan CSS toevoegt. De Less-extensie wordt geactiveerd wanneer een `.less`-bron aanwezig is en compileert deze naar CSS.

## Voorbeeld {#example}

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

## Opties {#options}

De Less-extensie forwarded zijn opties naar [Less](https://lesscss.org/usage/#less-options), die het aanroept om elke bron weer te geven. De bestandsnaam wordt voor je ingesteld. Stel de rest in onder `webforj-less` in `bun.config.ts`:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-less': { /* Less opties */ }
};
```
