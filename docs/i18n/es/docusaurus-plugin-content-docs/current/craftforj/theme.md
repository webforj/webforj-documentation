---
title: Theme
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: c7dd349ebf7570b2e34953fce990173d
---
La pestaña de Tema te permite cambiar la apariencia de tu aplicación mientras se ejecuta. Funciona con los [tokens de diseño de DWC](/docs/styling/css-variables) que tu aplicación ya utiliza, por lo que un solo cambio impacta en cada componente que lee ese token en lugar de una regla a la vez.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Ajustando un tema {#adjusting-a-theme}

Los controles están agrupados por lo que afectan, cubriendo la paleta con la que se construye la aplicación, las superficies detrás de ella, la forma de sus bordes y esquinas, su tipografía y su espaciado. Cada control explica lo que hace, ya que algunos de ellos cambian cuán legible es la aplicación en lugar de solo cómo se ve.

Un tema tiene un lado claro y un lado oscuro. Puedes aplicar una edición a ambos o a uno, y alternar la aplicación entre ellos para ver el lado en el que estás trabajando. Una vista previa muestra la paleta, las superficies, una muestra tipográfica y los colores de estado juntos, para que puedas detectar una combinación que funcione en una pantalla pero no en otra antes de guardarla.

![Los controles del tema junto a la vista previa](/img/craftforj/theme/knob-rail.png#rounded-border)

## Guardando un tema {#saving-a-theme}

Un tema en el que estás trabajando se aplica a la aplicación pero aún no forma parte de tu proyecto, y recargar la página lo descarta. Guardar lo escribe en la hoja de estilos de tu aplicación, donde sobrevive a reinicios, aparece en tu diff y se envía con tu aplicación.

craftforJ escribe en una sola hoja de estilos, la cual detecta por sí misma o que nombras en la configuración de craftforJ. Si ese archivo ya contiene un tema, guardar lo reemplaza por completo en lugar de superponer uno nuevo, y craftforJ te pide confirmación primero. Si el archivo cambió después de que craftforJ lo leyó, no se escribe nada y craftforJ te pide que lo guardes nuevamente.

Puedes revertir un tema a su último estado guardado o eliminarlo de la hoja de estilos por completo sin afectar nada más en el archivo.

## Temas preestablecidos {#preset-themes}

Más allá del aspecto y la sensación predeterminados, craftforJ tiene varios temas preestablecidos para elegir. A continuación se muestra una comparación entre los temas App Default y Portico.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![Aplicación con el tema App Default](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![Aplicación con el tema Portico](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Desactivándolo {#turning-it-off}

Puedes desactivar la opción de guardar estilos para una aplicación en la configuración de craftforJ, o eliminarla por completo con la propiedad [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags). Con cualquiera de las opciones desactivadas, la pestaña aún funciona y sigue repaintando la aplicación en ejecución, pero no puedes guardar el resultado.
