---
title: SCSS and Sass
sidebar_position: 30
sidebar_class_name: new-content
description: >-
  Compile SCSS or Sass to CSS with the webforj-scss extension, bind a class to
  the stylesheet, and set a load path through bun.config.ts.
_i18n_hash: b278152eaf94d853a152b4d605c71981
---
[Sass](https://sass-lang.com/) ist eine Stylesheet-Sprache, die Variablen, Verschachtelung und Funktionen zu CSS hinzufügt, und SCSS ist ihre CSS-ähnliche Syntax. Die SCSS-Erweiterung wird aktiviert, wenn eine `.scss` oder `.sass` Quelle vorhanden ist, kompiliert sie zu CSS und lädt sie als Styles. Eine Klasse bindet an das kompilierte Stylesheet, genau wie sie an ein Skript bindet.

## Beispiel {#example}

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

## Optionen {#options}

Die SCSS-Erweiterung leitet ihre Optionen an den [Dart Sass-Compiler](https://sass-lang.com/documentation/js-api/interfaces/options/) weiter. Die häufigste ist ein Ladepfad, der es einem Eintrag ermöglicht, `@use` ein Partial zu verwenden, das sich außerhalb seines eigenen Ordners befindet:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

Mit diesem Ladepfad wird `@use 'tokens'` zu einem Partial unter `src/main/frontend/styles`. Die Sass-Optionsreferenz listet den Rest auf.
