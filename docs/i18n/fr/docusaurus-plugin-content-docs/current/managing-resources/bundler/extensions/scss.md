---
title: SCSS et Sass
sidebar_position: 30
description: >-
  Compilez du SCSS ou du Sass en CSS avec l’extension webforj-scss, associez une
  classe à la feuille de style et définissez un chemin de chargement dans
  bun.config.ts.
_i18n_title_hash: 9bd51327ee5db5248d9c4d0c207c9650
_i18n_description_hash: 241a3d6fadb39e11156b12f9a5d54e15
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
