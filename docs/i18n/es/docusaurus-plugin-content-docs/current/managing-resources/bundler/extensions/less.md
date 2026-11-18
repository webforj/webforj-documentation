---
title: Less
sidebar_position: 40
description: >-
  Compila Less a CSS con la extensión webforj-less, vincula una clase a la hoja
  de estilos y pasa las opciones de Less mediante bun.config.ts.
_i18n_title_hash: 1cfdf0e8d0c87a228c1f40d9bee7888b
_i18n_description_hash: 4378822835500b71adce33d9479455dd
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
