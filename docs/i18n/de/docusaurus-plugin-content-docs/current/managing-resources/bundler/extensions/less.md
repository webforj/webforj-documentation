---
title: Less
sidebar_position: 40
description: >-
  Kompilieren Sie Less mit der webforj-less-Erweiterung zu CSS, binden Sie eine
  Klasse an das Stylesheet und übergeben Sie Less-Optionen über bun.config.ts.
_i18n_title_hash: 1cfdf0e8d0c87a228c1f40d9bee7888b
_i18n_description_hash: 4378822835500b71adce33d9479455dd
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
