---
title: Button
sidebar_position: 15
description: >-
  Trigger click actions in webforJ with the Button component, including themes,
  expanses, prefix and suffix icons, and disabled state.
_i18n_hash: 31fa93b60126cba6b26198da5a5c15b5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un `Button` es un elemento clickeable que desencadena una acción cuando se presiona. Puede mostrar texto, íconos, o una combinación de ambos. Los botones soportan múltiples temas visuales y tamaños, y pueden ser deshabilitados para evitar interacciones durante operaciones que tardan mucho tiempo o cuando ciertas condiciones no se cumplen.

<!-- INTRO_END -->

## Usos {#usages}

La clase `Button` es un componente versátil que se utiliza comúnmente en diversas situaciones donde se necesita activar interacciones y acciones del usuario. Aquí hay algunos escenarios típicos en los que podrías necesitar un botón en tu aplicación:

1. **Envio de Formularios**: Los botones a menudo se utilizan para enviar datos de formularios. Por ejemplo, en una aplicación, puedes usar:

  > - Un botón de "Enviar" para enviar datos al servidor
  > - Un botón de "Borrar" para eliminar cualquier información ya presente en el formulario

2. **Acciones del Usuario**: Los botones se utilizan para permitir a los usuarios realizar acciones específicas dentro de la aplicación. Por ejemplo, puedes tener un botón etiquetado:

  > - "Eliminar" para iniciar la eliminación de un elemento seleccionado
  > - "Guardar" para guardar cambios realizados en un documento o página.

3. **Diálogos de Confirmación**: Los botones a menudo se incluyen en componentes de [`Dialog`](../components/dialog) construidos para diferentes propósitos para proporcionar opciones para que los usuarios confirmen o cancelen una acción, o cualquier otra funcionalidad que esté integrada en el [`Dialog`](../components/dialog) que estés utilizando.

4. **Desencadenantes de Interacción**: Los botones pueden servir como desencadenantes para interacciones o eventos dentro de la aplicación. Al hacer clic en un botón, los usuarios pueden iniciar acciones complejas o activar animaciones, refrescando contenido o actualizando la visualización.

5. **Navegación**: Los botones pueden ser utilizados con fines de navegación, como moverse entre diferentes secciones o páginas dentro de una aplicación. Los botones para navegación podrían incluir:

  > - "Siguiente" - Lleva al usuario a la siguiente página o sección de la aplicación o página actual.
  > - "Anterior" - Devuelve al usuario a la página anterior de la aplicación o sección en la que se encuentra.
  > - "Atrás" Devuelve al usuario a la primera parte de la aplicación o página en la que se encuentra.

El siguiente ejemplo demuestra botones utilizados para el envío de formularios y la limpieza de entradas:

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## Agregando íconos a los botones <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporar un ícono en un botón puede mejorar en gran medida el diseño de tu aplicación, permitiendo que los usuarios identifiquen rápidamente los elementos de acción en la pantalla. El componente [`Icon`](./icon.md) proporciona una amplia selección de íconos para elegir.

Al utilizar los métodos `setPrefixComponent()` y `setSuffixComponent()`, tienes la flexibilidad de determinar si un `Icon` debe aparecer antes o después del texto en un botón. Alternativamente, se puede utilizar el método `setIcon()` para agregar un `Icon` después del texto, pero antes del slot `suffix` del botón.

<!-- Agrega esto de nuevo una vez que el Icono haya sido fusionado -->
<!-- Consulta la página del [componente de Icono](../components/icon) para más información sobre configuración y personalización de íconos. -->

:::tip
Por defecto, un `Icon` hereda el tema y la expansión del botón.
:::

A continuación se muestran ejemplos de botones con texto a la izquierda y derecha, así como un botón con solo un ícono:

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### Nombres {#names}

El componente `Button` utiliza nombres, que se utilizan para accesibilidad. Cuando un nombre no se establece explícitamente, se utilizará la etiqueta del `Button`. Sin embargo, algunos íconos no tienen etiquetas y solo muestran elementos no textuales, como íconos. En este caso, es conveniente usar el método `setName()` para garantizar que el componente `Button` creado cumpla con los estándares de accesibilidad.

## Deshabilitando un botón {#disabling-a-button}

Los componentes de botón, como muchos otros, pueden ser deshabilitados para transmitir al usuario que una cierta acción no está disponible aún o ya no lo está. Un botón deshabilitado disminuirá la opacidad del botón y está disponible para todos los temas y expansiones de botones.

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

Deshabilitar un botón puede hacerse en cualquier momento en el código utilizando la función <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Para mayor comodidad, un botón también puede ser deshabilitado al hacer clic utilizando la función incorporada <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

En algunos casos, hacer clic en un botón desencadena una acción que tarda mucho tiempo. Deshabilitar el botón hasta que tu aplicación procese la acción evita que el usuario haga clic en el botón múltiples veces, especialmente en entornos de alta latencia.

:::tip
Deshabilitar al hacer clic no solo ayuda a optimizar el procesamiento de acciones, sino que también evita que el desarrollador necesite implementar este comportamiento por su cuenta, ya que este método ha sido optimizado para reducir las comunicaciones de ida y vuelta.
:::

## Estilo {#styling}

### Temas {#themes}

Los componentes `Button` vienen con <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 temas discretos </JavadocLink> integrados para un estilo rápido sin el uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los botones para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar la apariencia de los botones en toda una aplicación.

Si bien hay muchos casos de uso para cada uno de los distintos temas, algunos ejemplos de uso son:

  - **Peligro**: Mejor para acciones con consecuencias severas, como borrar información completada o eliminar permanentemente una cuenta/datos.
  - **Por Defecto**: Apropiado para acciones a lo largo de la aplicación que no requieren atención especial y son genéricas, como alternar una configuración.
  - **Primario**: Apropiado como un "llamado a la acción" principal en una página, como registrarse, guardar cambios o continuar a otra página.
  - **Éxito**: Excelente para visualizar la finalización exitosa de un elemento en una aplicación, como la presentación de un formulario o la finalización de un proceso de registro. El tema de éxito se puede aplicar programáticamente una vez que se ha completado una acción exitosa.
  - **Advertencia**: Útil para indicar que un usuario está a punto de realizar una acción potencialmente arriesgada, como navegar lejos de una página con cambios no guardados. Estas acciones son a menudo menos impactantes que las que usarían el tema Peligro.
  - **Gris**: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página y no son parte de la funcionalidad principal.
  - **Información**: Bueno para proporcionar información adicional aclaratoria a un usuario.

Se muestran a continuación botones de ejemplo con cada uno de los Temas soportados aplicados: <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### Expansiones {#expanses}
Los siguientes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> valores de Expansiones </JavadocLink> permiten un estilo rápido sin usar CSS. Esto permite manipular las dimensiones del Botón sin tener que configurarlo explícitamente utilizando ningún estilo. Además de simplificar el estilo, también ayuda a crear y mantener una uniformidad en tu aplicación. La expansión por defecto del `Button` es `Expanse.MEDIUM`.

Diferentes tamaños son a menudo apropiados para diferentes usos:
  - Los valores de expansión **más grandes** son adecuados para botones que deben llamar la atención, enfatizar funcionalidad o son fundamentales para la funcionalidad central de una aplicación o página.
  - Los botones de expansión **mediana**, el tamaño por defecto, deberían ser el tamaño estándar de los botones. Las funciones de estos botones no deberían ser ni más ni menos críticas que los componentes similares.
  - Los valores de expansión **más pequeños** deberían usarse para botones que no tienen comportamientos integrales en la aplicación y sirven un rol más suplementario o utilitario, en lugar de desempeñar una parte importante en la interacción del usuario. Esto incluye componentes `Button` que solo se usan con íconos para propósitos utilitarios.

A continuación se presentan las diferentes expansiones soportadas para el componente `Button`: <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Button" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al utilizar el componente `Button`, considera las siguientes mejores prácticas:

1. **Texto Apropiado**: Usa texto claro y conciso para el texto dentro de tu componente `Button` para proporcionar una indicación clara de su propósito.

2. **Estilo Visual Apropiado**: Considera el estilo visual y el tema del `Button` para asegurar la consistencia con el diseño de tu aplicación. Por ejemplo:
  > - Un componente `Button` de "Cancelar" debería estar estilizado con el tema adecuado o estilo CSS para asegurar que los usuarios estén seguros de que quieren cancelar una acción
  > - Un `Button` de "Confirmar" tendría un estilo diferente al de un botón de "Cancelar", pero de manera similar debería destacar para asegurar que los usuarios sepan que esta es una acción especial.

3. **Manejo Eficiente de Eventos**: Maneja los eventos del `Button` de manera eficiente y proporciona retroalimentación apropiada a los usuarios. Consulta [Eventos](../building-ui/events) para revisar comportamientos eficientes en la adición de eventos.

4. **Pruebas y Accesibilidad**: Prueba el comportamiento del botón en diferentes escenarios, como cuando está deshabilitado o recibe enfoque, para asegurar una experiencia fluida al usuario. Sigue las pautas de accesibilidad para hacer que el `Button` sea utilizable para todos los usuarios, incluyendo aquellos que dependen de tecnologías de asistencia.
