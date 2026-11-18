---
title: Less
sidebar_position: 40
description: >-
  Compileer Less naar CSS met de webforj-less-extensie, koppel een klasse aan de
  stylesheet en geef Less-opties door via bun.config.ts.
_i18n_title_hash: 1cfdf0e8d0c87a228c1f40d9bee7888b
_i18n_description_hash: 4378822835500b71adce33d9479455dd
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
