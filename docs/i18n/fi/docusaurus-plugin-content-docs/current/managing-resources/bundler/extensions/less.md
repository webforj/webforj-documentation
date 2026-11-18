---
title: Less
sidebar_position: 40
description: >-
  Käännä Less CSS:ksi webforj-less-laajennuksella, liitä luokka tyylitiedostoon
  ja välitä Less-asetukset bun.config.ts:n kautta.
_i18n_title_hash: 1cfdf0e8d0c87a228c1f40d9bee7888b
_i18n_description_hash: 4378822835500b71adce33d9479455dd
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
