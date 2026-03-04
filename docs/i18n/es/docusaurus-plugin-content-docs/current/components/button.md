---
title: Button
sidebar_position: 15
_i18n_hash: 6c3425f6d7138e710c5222d2baf84644
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un `Button` es un elemento clickeable que desencadena una acción cuando se presiona. Puede mostrar texto, íconos, o una combinación de ambos. Los botones soportan múltiples temas visuales y tamaños, y pueden deshabilitarse para prevenir interacciones durante operaciones prolongadas o cuando ciertas condiciones no se cumplen.

<!-- INTRO_END -->

## Usos {#usages}

La clase `Button` es un componente versátil que se utiliza comúnmente en diversas situaciones donde se necesita desencadenar interacciones y acciones del usuario. Aquí hay algunos escenarios típicos donde podrías necesitar un botón en tu aplicación:

1. **Envío de Formularios**: Los botones se utilizan a menudo para enviar datos de formularios. Por ejemplo, en una aplicación, puedes usar:

  > - Un botón "Enviar" para enviar datos al servidor
  > - Un botón "Limpiar" para eliminar cualquier información ya presente en el formulario


2. **Acciones del Usuario**: Los botones se utilizan para permitir a los usuarios realizar acciones específicas dentro de la aplicación. Por ejemplo, puedes tener un botón etiquetado como:

  > - "Eliminar" para iniciar la eliminación de un ítem seleccionado
  > - "Guardar" para guardar los cambios realizados en un documento o página.

3. **Diálogos de Confirmación**: Los botones a menudo se incluyen en componentes de [`Dialog`](../components/dialog) construidos para diversas finalidades para proporcionar opciones a los usuarios para confirmar o cancelar una acción, o cualquier otra funcionalidad que esté incorporada en el [`Dialog`](../components/dialog) que estás utilizando.

4. **Desencadenadores de Interacción**: Los botones pueden servir como desencadenadores para interacciones o eventos dentro de la aplicación. Al hacer clic en un botón, los usuarios pueden iniciar acciones complejas o desencadenar animaciones, refrescando contenido o actualizando la visualización.

5. **Navegación**: Los botones se pueden usar para fines de navegación, como moverse entre diferentes secciones o páginas dentro de una aplicación. Los botones para navegación podrían incluir:

  > - "Siguiente" - Lleva al usuario a la siguiente página o sección de la aplicación o página actual.
  > - "Anterior" - Devuelve al usuario a la página anterior de la aplicación o sección en la que se encuentra.
  > - "Atrás" Devuelve al usuario a la primera parte de la aplicación o página en la que se encuentra.
  
El siguiente ejemplo demuestra botones utilizados para el envío de formularios y la limpieza de la entrada:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Agregar íconos a botones <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporar un ícono en un botón puede mejorar en gran medida el diseño de tu aplicación, permitiendo a los usuarios identificar rápidamente los elementos accionables en la pantalla. El componente [`Icon`](./icon.md) proporciona una amplia selección de íconos para elegir.

Al utilizar los métodos `setPrefixComponent()` y `setSuffixComponent()`, tienes la flexibilidad para determinar si un `Icon` debe aparecer antes o después del texto en un botón. Alternativamente, se puede usar el método `setIcon()` para agregar un `Icon` después del texto, pero antes del slot `suffix` del botón.

<!-- Agrega esto una vez que el Icono se haya fusionado -->
<!-- Consulta la página del [componente Icon](../components/icon) para obtener más información sobre cómo configurar y personalizar íconos. -->

:::tip
Por defecto, un `Icon` hereda el tema y la expansión del botón.
:::

A continuación se presentan ejemplos de botones con texto a la izquierda y derecha, así como un botón con solo un ícono:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Nombres {#names}

El componente `Button` utiliza nombres, que se utilizan para accesibilidad. Cuando un nombre no se establece explícitamente, la etiqueta del `Button` se utilizará en su lugar. Sin embargo, algunos íconos no tienen etiquetas y solo muestran elementos no textuales, como íconos. En este caso, es conveniente usar el método `setName()` para asegurarse de que el componente `Button` creado cumpla con las normas de accesibilidad.

## Deshabilitar un botón {#disabling-a-button}

Los componentes de botón, como muchos otros, pueden ser deshabilitados para comunicar a un usuario que cierta acción no está disponible aún o ya no está disponible. Un botón deshabilitado disminuirá la opacidad del botón, y está disponible para todos los temas y expansiones de botones.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Deshabilitar un botón se puede hacer en cualquier momento en el código utilizando la función <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Para mayor conveniencia, un botón también puede ser deshabilitado al hacer clic utilizando la función incorporada <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

En algunas aplicaciones, hacer clic en un botón desencadena una acción de larga duración. En la mayoría de los casos, la aplicación podría querer asegurar que solo se procese un solo clic. Esto puede ser un problema en entornos de alta latencia cuando el usuario hace clic en el botón varias veces antes de que la aplicación haya tenido la oportunidad de comenzar a procesar la acción resultante. 

:::tip
Deshabilitar al hacer clic no solo ayuda a optimizar el procesamiento de acciones, sino que también previene que el desarrollador tenga que implementar este comportamiento por su cuenta, ya que este método ha sido optimizado para reducir las comunicaciones de ida y vuelta.
:::

## Estilos {#styling}

### Temas {#themes}

Los componentes `Button` vienen con <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 temas discretos </JavadocLink> incorporados para un estilo rápido sin la necesidad de CSS. Estos temas son estilos predefinidos que se pueden aplicar a botones para cambiar su apariencia y presentación visual. Ofrecen una manera rápida y consistente de personalizar el aspecto de los botones a lo largo de una aplicación. 

Si bien hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos de uso son:

  - **Peligro**: Mejor para acciones con graves consecuencias, como limpiar información completada o eliminar permanentemente una cuenta/datos.
  - **Por Defecto**: Apropiado para acciones a lo largo de una aplicación que no requieren atención especial y son genéricas, como alternar una configuración.
  - **Primario**: Apropiado como la principal "llamada a la acción" en una página, como registrarse, guardar cambios o continuar a otra página.
  - **Éxito**: Excelente para visualizar la finalización exitosa de un elemento en una aplicación, como el envío de un formulario o la finalización de un proceso de registro. El tema de éxito puede aplicarse programáticamente una vez que se ha completado una acción exitosa.
  - **Advertencia**: Útil para indicar que un usuario está a punto de realizar una acción potencialmente arriesgada, como navegar fuera de una página con cambios no guardados. Estas acciones a menudo son menos impactantes que aquellas que usarían el tema Peligro.
  - **Gris**: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página, y no parte de la funcionalidad principal.
  - **Información**: Bueno para proporcionar información adicional aclaratoria a un usuario.

A continuación se muestran ejemplos de botones con cada uno de los temas soportados aplicados: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Expansiones {#expanses}
Los siguientes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> valores de Expansiones </JavadocLink> permiten un estilo rápido sin utilizar CSS. Esto permite manipular las dimensiones del botón sin tener que configurarlo explícitamente usando algún estilo. Además de simplificar el estilo, también ayuda a crear y mantener una uniformidad en tu aplicación. La expansión predeterminada del `Button` es `Expanse.MEDIUM`.

Los diferentes tamaños son a menudo apropiados para diferentes usos:
  - Los valores de expansión **más grandes** son adecuados para botones que deberían llamar la atención, enfatizar funcionalidad o son fundamentales para la funcionalidad principal de una aplicación o página.
  - Los botones de expansión **mediana**, el tamaño predeterminado, deben ser utilizados como un "tamaño estándar", cuando el comportamiento de un botón no es más ni menos importante que otros componentes similares.
  - Los valores de expansión **más pequeños** deben ser utilizados para botones que no tienen comportamientos integrales en la aplicación y cumplen un papel más suplementario o utilitario, en lugar de jugar un papel importante en la interacción del usuario. Esto incluye componentes `Button` que se usan solo con íconos para propósitos utilitarios.

A continuación se muestran las diversas expansiones soportadas para el componente `Button`: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al utilizar el componente `Button`, considera las siguientes mejores prácticas:

1. **Texto Apropiado**: Usa texto claro y conciso para el texto dentro de tu componente `Button` para proporcionar una clara indicación de su propósito.

2. **Estilo Visual Apropiado**: Considera el estilo visual y el tema del `Button` para asegurar consistencia con el diseño de tu aplicación. Por ejemplo:
  > - Un componente `Button` "Cancelar" debería estar estilizado con el tema adecuado o estilo CSS para asegurar que los usuarios estén seguros de que desean cancelar una acción
  > - Un `Button` "Confirmar" tendría un estilo diferente a un botón "Cancelar", pero de manera similar destacaría para asegurar que los usuarios sepan que esta es una acción especial.

3. **Manejo Eficiente de Eventos**: Maneja los eventos del `Button` de manera eficiente y proporciona retroalimentación apropiada a los usuarios. Consulta [Eventos](../building-ui/events) para revisar comportamientos de adición de eventos eficientes.

4. **Pruebas y Accesibilidad**: Prueba el comportamiento del botón en diferentes escenarios, como cuando está deshabilitado o recibe foco, para asegurar una experiencia del usuario fluida. Sigue las directrices de accesibilidad para hacer que el `Button` sea usable para todos los usuarios, incluidos aquellos que dependen de tecnologías asistivas.
