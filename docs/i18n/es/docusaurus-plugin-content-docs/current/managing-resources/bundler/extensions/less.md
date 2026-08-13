---
title: Less
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  Compile Less to CSS with the webforj-less extension, bind a class to the
  stylesheet, and pass Less options through bun.config.ts.
_i18n_hash: febd9ab468f73672d97e3a0048d6371b
---
[Less](https://lesscss.org/) es un lenguaje de hojas de estilo que añade variables, mixins y anidamiento a CSS. La extensión Less se activa cuando hay una fuente `.less` presente y la compila a CSS.

## Ejemplo {#example}

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

## Opciones {#options}

La extensión Less reenvía sus opciones a [Less](https://lesscss.org/usage/#less-options), que llama para renderizar cada fuente. El nombre del archivo se establece por ti. Establece el resto bajo `webforj-less` en `bun.config.ts`:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-less': { /* Opciones de Less */ }
};
```
