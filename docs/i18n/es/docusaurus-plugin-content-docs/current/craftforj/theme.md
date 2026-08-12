---
title: Theme
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: 98545075c2ac2777380812af08d71345
---
La pestaña de Tema te permite cambiar la apariencia de tu aplicación mientras se ejecuta. Funciona con los [tokens de diseño de DWC](/docs/styling/css-variables) que tu aplicación ya utiliza, por lo que un solo cambio afecta a todos los componentes que leen ese token en lugar de aplicar una regla a la vez.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4" type="video/mp4" />
  </video>
</div>

## Ajustando un tema {#adjusting-a-theme}

Los controles están agrupados según lo que afectan, cubriendo la paleta de la que se construye la aplicación, las superficies detrás de ella, la forma de sus bordes y esquinas, su tipografía y su espaciado. Cada control explica lo que hace, ya que algunos de ellos cambian la legibilidad de la aplicación y no solo su apariencia.

Un tema tiene un lado claro y un lado oscuro. Puedes aplicar una edición a ambos o a uno solo, y alternar entre ellos para ver el lado en el que estás trabajando. Una vista previa muestra la paleta, las superficies, un espécimen tipográfico y los colores de estado juntos, para que puedas detectar una combinación que funcione en una pantalla pero no en otra antes de guardarla.

![Los controles del tema al lado de la vista previa](/img/craftforj/theme/knob-rail.png#rounded-border)

## Guardando un tema {#saving-a-theme}

Un tema en el que estás trabajando se aplica a la aplicación, pero aún no es parte de tu proyecto, y volver a cargar la página lo descarta. Guardar lo escribe en la hoja de estilos de tu aplicación, donde sobrevive a los reinicios, aparece en tu diff y se envía con tu aplicación.

craftforJ escribe en una única hoja de estilos, que detecta por su cuenta o que nombras en la configuración de craftforJ. Si ese archivo ya tiene un tema, al guardar lo reemplaza completamente en lugar de superponer uno nuevo, y craftforJ te pide que lo confirmes primero. Si el archivo cambió después de que craftforJ lo leyó, no se escribe nada y craftforJ te pide que guardes de nuevo.

Puedes revertir un tema a su último estado guardado, o eliminarlo completamente de la hoja de estilos sin afectar nada más en el archivo.

## Temas preestablecidos {#preset-themes}

Más allá de la apariencia y funcionalidad predeterminadas, craftforJ tiene varios temas preestablecidos para elegir. Lo siguiente muestra una comparación entre los temas App Default y Portico.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![Aplicación con el tema App Default](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![Aplicación con el tema Portico](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Desactivándolo {#turning-it-off}

Puedes desactivar el guardado de estilos para una aplicación en la configuración de craftforJ, o eliminarlo por completo con la propiedad [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags). Con cualquiera de las opciones desactivadas, la pestaña sigue funcionando y sigue repintando la aplicación en ejecución, pero no puedes guardar el resultado.
