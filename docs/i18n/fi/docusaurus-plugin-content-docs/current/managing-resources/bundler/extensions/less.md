---
title: Less
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  Compile Less to CSS with the webforj-less extension, bind a class to the
  stylesheet, and pass Less options through bun.config.ts.
_i18n_hash: febd9ab468f73672d97e3a0048d6371b
---
[Less](https://lesscss.org/) on tyylitiedostokieli, joka lisää muuttujia, sekoittimia ja sisennystä CSS:ään. Less-laajennus aktivoituu, kun `.less`-lähde on läsnä, ja kääntää sen CSS:ksi.

## Esimerkki {#example}

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

## Vaihtoehdot {#options}

Less-laajennus välittää vaihtoehtonsa [Less](https://lesscss.org/usage/#less-options)-sivustolle, jota se kutsuu renderöidäkseen jokaisen lähteen. Tiedostonimi määritellään puolestasi. Aseta loput `webforj-less` alle `bun.config.ts`-tiedostossa:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-less': { /* Less options */ }
};
```
