---
title: SCSS en Sass
sidebar_position: 30
description: >-
  Compileer SCSS of Sass naar CSS met de webforj-scss-extensie, koppel een
  klasse aan het stijlblad en stel een zoekpad in via bun.config.ts.
_i18n_title_hash: 9bd51327ee5db5248d9c4d0c207c9650
_i18n_description_hash: 241a3d6fadb39e11156b12f9a5d54e15
_i18n_hash: b278152eaf94d853a152b4d605c71981
---
[Sass](https://sass-lang.com/) is een stylesheet-taal die variabelen, nesting en functies aan CSS toevoegt, en SCSS is de CSS-achtige syntaxis ervan. De SCSS-extensie wordt geactiveerd wanneer er een `.scss` of `.sass` bron aanwezig is, compileert het naar CSS en laad het als stijlen. Een klasse bindt aan de gecompileerde stylesheet op dezelfde manier als het bindt aan een script.

## Voorbeeld {#example}

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

## Opties {#options}

De SCSS-extensie doorgeeft zijn opties aan de [Dart Sass-compiler](https://sass-lang.com/documentation/js-api/interfaces/options/). De meest voorkomende is een laadpad, waarmee een invoer `@use` een partial kan gebruiken die zich buiten zijn eigen map bevindt:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

Met dat laadpad, lost `@use 'tokens'` een partial op onder `src/main/frontend/styles`. De Sass-optiesreferentie lijst de rest op.
