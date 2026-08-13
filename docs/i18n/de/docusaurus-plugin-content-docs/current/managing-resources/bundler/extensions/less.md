---
title: Less
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  Compile Less to CSS with the webforj-less extension, bind a class to the
  stylesheet, and pass Less options through bun.config.ts.
_i18n_hash: febd9ab468f73672d97e3a0048d6371b
---
[Less](https://lesscss.org/) ist eine Stylesheet-Sprache, die Variablen, Mixins und Verschachtelung zu CSS hinzufügt. Die Less-Erweiterung wird aktiviert, wenn eine `.less`-Quelle vorhanden ist, und kompiliert sie zu CSS.

## Beispiel {#example}

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

## Optionen {#options}

Die Less-Erweiterung leitet ihre Optionen an [Less](https://lesscss.org/usage/#less-options) weiter, das sie aufruft, um jede Quelle zu rendern. Der Dateiname wird für dich festgelegt. Setze den Rest unter `webforj-less` in `bun.config.ts`:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-less': { /* Less-Optionen */ }
};
```
