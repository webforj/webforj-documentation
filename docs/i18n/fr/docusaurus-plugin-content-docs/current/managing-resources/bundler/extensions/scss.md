---
title: SCSS and Sass
sidebar_position: 30
sidebar_class_name: new-content
description: >-
  Compile SCSS or Sass to CSS with the webforj-scss extension, bind a class to
  the stylesheet, and set a load path through bun.config.ts.
_i18n_hash: b278152eaf94d853a152b4d605c71981
---
[Sass](https://sass-lang.com/) est un langage de feuille de style qui ajoute des variables, de l'imbrication et des fonctions au CSS, et SCSS est sa syntaxe semblable à CSS. L'extension SCSS s'active lorsqu'une source `.scss` ou `.sass` est présente, la compile en CSS et la charge en tant que styles. Une classe se lie à la feuille de style compilée de la même manière qu'elle se lie à un script.

## Exemple {#example}

```scss title="scss/box.scss"
@use 'tokens' as t;

.box {
  padding: 1rem;
  border: 2px solid t.$accent;
}
```

```java title="ScssView.java"
@Route("/scss")
@BundleEntry("scss/box.scss")
public class ScssView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ScssView() {
    Div box = new Div().addClassName("box");
    self.add(box);
  }
}
```

## Options {#options}

L'extension SCSS transmet ses options au [compilateur Dart Sass](https://sass-lang.com/documentation/js-api/interfaces/options/). La plus courante est un chemin de chargement, qui permet à une entrée `@use` d'accéder à un partiel qui se trouve en dehors de son propre dossier :

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

Avec ce chemin de chargement, `@use 'tokens'` résout un partiel sous `src/main/frontend/styles`. La référence des options Sass liste le reste.
