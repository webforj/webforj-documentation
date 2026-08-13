---
title: Less
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  Compile Less to CSS with the webforj-less extension, bind a class to the
  stylesheet, and pass Less options through bun.config.ts.
_i18n_hash: febd9ab468f73672d97e3a0048d6371b
---
[Less](https://lesscss.org/) est un langage de feuille de style qui ajoute des variables, des mixins et de l'imbrication au CSS. L'extension Less s'active lorsqu'une source `.less` est présente et la compile en CSS.

## Exemple {#example}

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

L'extension Less transmet ses options à [Less](https://lesscss.org/usage/#less-options), qu'elle appelle pour rendre chaque source. Le nom de fichier est défini pour vous. Définissez le reste sous `webforj-less` dans `bun.config.ts` :

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-less': { /* Options Less */ }
};
```
