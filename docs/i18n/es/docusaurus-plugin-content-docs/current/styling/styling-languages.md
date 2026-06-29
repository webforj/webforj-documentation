---
title: Styling languages
sidebar_position: 11
sidebar_class_name: new-content
description: >-
  Author your styles in CSS, compile them from Sass or Less, or generate them
  with Tailwind, and load the result into a webforJ app.
_i18n_hash: 98eca77023e33bac367a1a250da900d7
---
Sus estilos llegan a la página como CSS, pero no tiene que escribirlos como CSS. webforJ carga una hoja de estilos que usted autoriza, compila una desde un preprocesador como Sass o Less, o genera una desde Tailwind, y el resultado estiliza sus vistas de la misma manera, independientemente de su origen. Los tokens de DWC, [propiedades personalizadas de CSS](/docs/styling/css-variables), y [partes de sombra](/docs/styling/shadow-parts) cubiertos en el resto de esta sección se aplican dentro de cualquiera de ellos.

## CSS Plano {#plain-css}

Una hoja de estilos que usted escribe no necesita construcción. Adjúntela a un componente o a la aplicación con [`@StyleSheet`](/docs/managing-resources/importing-assets#importing-css-files). Cuando ya está ejecutando el [empaquetador de frontend](/docs/managing-resources/bundler/overview), puede en su lugar vincular un archivo `.css` a una clase con `@BundleEntry`, lo que lo carga como estilos para esa vista.

## Sass y Less {#sass-and-less}

Para escribir sus estilos en [Sass](https://sass-lang.com/) o [Less](https://lesscss.org/), con variables, anidamiento y funciones, autorice el origen y deje que el [empaquetador de frontend](/docs/managing-resources/bundler/overview) lo compile a CSS. El compilador es una [extensión](/docs/managing-resources/bundler/extensions/overview) que se activa cuando hay un origen de su tipo presente, así que autorizar un archivo `.scss`, `.sass` o `.less` es la única señal que necesita. Vincule el origen a una clase de la misma manera que vincula una hoja de estilos:

```java title="StyledView.java"
@Route("/styled")
@BundleEntry("styles/view.scss")
public class StyledView extends Composite<FlexLayout> {
  // construir la vista
}
```

La extensión compila `view.scss` a CSS y lo carga para la vista. Consulte [SCSS y Sass](/docs/managing-resources/bundler/extensions/scss) y [Less](/docs/managing-resources/bundler/extensions/less) para obtener la estructura de archivos, rutas de carga y opciones que cada uno acepta.

## Tailwind {#tailwind}

[Tailwind](https://tailwindcss.com/) genera una hoja de estilos a partir de los nombres de clases de utilidades que utilizan sus vistas, en lugar de a partir de un archivo que usted autoriza. Active la extensión y luego agregue utilidades como nombres de clases sin nada que importar. webforJ omite el reinicio base de Tailwind para que no interfiera con el estilo que sus componentes ya llevan, y una utilidad llega al elemento en el que la coloca, no al interior de un componente. Consulte [Extensión Tailwind](/docs/managing-resources/bundler/extensions/tailwind) para ver cómo genera y delimita su hoja de estilos, y dónde se aplican y no se aplican las clases de utilidad.

## Otro lenguaje {#another-language}

El compilador para cada lenguaje es una extensión del empaquetador, y el modelo es abierto. Para autorizar sus estilos en un lenguaje para el cual webforJ no envía compilador, escriba una pequeña extensión que contribuya con ese compilador, bajo el mismo contrato que utilizan Sass y Less. Consulte [Escribiendo su propia extensión](/docs/managing-resources/bundler/extensions/writing-your-own).
