---
title: SCSS and Sass
sidebar_position: 30
sidebar_class_name: new-content
description: >-
  Compile SCSS or Sass to CSS with the webforj-scss extension, bind a class to
  the stylesheet, and set a load path through bun.config.ts.
_i18n_hash: b278152eaf94d853a152b4d605c71981
---
[Sass](https://sass-lang.com/) es un lenguaje de hojas de estilo que añade variables, anidamiento y funciones a CSS, y SCSS es su sintaxis similar a CSS. La extensión SCSS se activa cuando hay una fuente `.scss` o `.sass`, la compila a CSS y la carga como estilos. Una clase se vincula a la hoja de estilo compilada de la misma manera que se vincula a un script.

## Ejemplo {#example}

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

## Opciones {#options}

La extensión SCSS reenvía sus opciones al [compilador Dart Sass](https://sass-lang.com/documentation/js-api/interfaces/options/). La más común es una ruta de carga, que permite a una entrada `@use` un parcial que vive fuera de su propia carpeta:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

Con esa ruta de carga, `@use 'tokens'` resuelve un parcial bajo `src/main/frontend/styles`. La referencia de opciones de Sass enumera el resto.
