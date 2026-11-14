---
title: Tema
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: fe089afe4f44e457a98622707bf995e5
---
La pestaña Tema te permite cambiar el aspecto de tu aplicación mientras se ejecuta. Funciona con los [tokens de diseño de DWC](/docs/styling/css-variables) que ya usa tu aplicación, así que un solo cambio llega a todos los componentes que leen ese token, en lugar de tener que cambiar una regla a la vez.

<VideoPlayer video="craftforj/theme-knobs" width={1920} height={992} title="Theme knobs" />

## Ajustar un tema {#adjusting-a-theme}

Los controles están agrupados según lo que afectan: la paleta en la que se basa la aplicación, las superficies de fondo, las formas de los bordes y las esquinas, la tipografía y el espaciado. Cada control explica su función, ya que algunos afectan a la legibilidad de la aplicación, no solo a su aspecto.

Un tema tiene una variante clara y una oscura. Puedes aplicar un cambio a ambas o a una sola, y alternar entre ellas en la aplicación para ver la variante que estás editando. Una vista previa muestra la paleta, las superficies, una muestra tipográfica y los colores de estado a la vez. Así puedes detectar antes de guardar una combinación que funciona en una pantalla, pero no en otra.

![Los controles del tema junto a la vista previa](/img/craftforj/theme/knob-rail.png#rounded-border)

## Guardar un tema {#saving-a-theme}

El tema en el que estás trabajando se aplica a la aplicación, pero todavía no forma parte de tu proyecto; si vuelves a cargar la página, se descarta. Al guardarlo, se escribe en la hoja de estilos de tu aplicación, donde se conserva tras los reinicios, aparece en tu diff y se incluye con tu aplicación.

craftforJ escribe en una sola hoja de estilos, que detecta automáticamente o que puedes indicar en la configuración de craftforJ. Si ese archivo ya contiene un tema, al guardar se reemplaza por completo en lugar de superponerle otro, y craftforJ te pide confirmación antes de hacerlo. Si el archivo cambia después de que craftforJ lo lea, no se escribe nada y craftforJ te pide que vuelvas a guardar.

Puedes revertir un tema a su último estado guardado o eliminarlo por completo de la hoja de estilos sin afectar al resto del archivo.

## Temas predefinidos {#preset-themes}

Además del aspecto predeterminado, craftforJ ofrece varios temas predefinidos entre los que puedes elegir. A continuación, se comparan los temas App Default y Portico.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![Aplicación con el tema App Default](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![Aplicación con el tema Portico](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Desactivarlo {#turning-it-off}

Puedes desactivar el guardado de estilos para una aplicación en la configuración de craftforJ, o eliminarlo por completo con la propiedad [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags). Si desactivas cualquiera de estas opciones, la pestaña seguirá funcionando y actualizando la aplicación en ejecución, pero no podrás guardar el resultado.
