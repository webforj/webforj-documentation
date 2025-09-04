---
title: Button
sidebar_position: 15
_i18n_hash: 0282098a1b80b4d494409d4f416caa5d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un componente `Button` es un elemento fundamental de la interfaz de usuario utilizado en el desarrollo de aplicaciones para crear elementos interactivos que desencadenan acciones o eventos cuando se hace clic o se activan. Sirve como un elemento clicable con el que los usuarios pueden interactuar para realizar diversas acciones dentro de una aplicación o sitio web.

El propósito principal del componente `Button` es proporcionar una llamada a la acción clara e intuitiva para los usuarios, guiándolos a realizar tareas específicas como enviar un formulario, navegar a otra página, desencadenar una función o iniciar un proceso. Los botones son esenciales para mejorar las interacciones del usuario, mejorar la accesibilidad y crear una experiencia de usuario más atractiva.

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

<!-- tabs={['ButtonDemo.java', 'demo_styles.css']} -->

## Usos {#usages}

La clase `Button` es un componente versátil que se utiliza comúnmente en varias situaciones donde se necesitan desencadenar interacciones y acciones del usuario. Aquí hay algunos escenarios típicos donde podrías necesitar un botón en tu aplicación:

1. **Envío de Formularios**: Los botones se utilizan a menudo para enviar datos de formularios. Por ejemplo, en una aplicación, puedes usar:

  > - Un botón "Enviar" para enviar datos al servidor
  > - Un botón "Limpiar" para eliminar cualquier información ya presente en el formulario


2. **Acciones del Usuario**: Los botones se utilizan para permitir que los usuarios realicen acciones específicas dentro de la aplicación. Por ejemplo, puedes tener un botón etiquetado:

  > - "Eliminar" para iniciar la eliminación de un elemento seleccionado
  > - "Guardar" para guardar los cambios realizados en un documento o página.

3. **Diálogos de Confirmación**: Los botones a menudo se incluyen en componentes de [`Dialog`](../components/dialog) construidos para varios propósitos para proporcionar opciones a los usuarios para confirmar o cancelar una acción, o cualquier otra funcionalidad que esté integrada en el [`Dialog`](../components/dialog) que estés utilizando.

4. **Detonadores de Interacción**: Los botones pueden servir como detonadores para interacciones o eventos dentro de la aplicación. Al hacer clic en un botón, los usuarios pueden iniciar acciones complejas o desencadenar animaciones, actualizar contenido o modificar la visualización.

5. **Navegación**: Los botones se pueden usar para fines de navegación, como moverse entre diferentes secciones o páginas dentro de una aplicación. Los botones para navegación podrían incluir:

  > - "Siguiente" - Lleva al usuario a la siguiente página o sección de la aplicación o página actual.
  > - "Anterior" - Devuelve al usuario a la página anterior de la aplicación o sección en la que están.
  > - "Volver" - Devuelve al usuario a la primera parte de la aplicación o página en la que están.

## Agregando íconos a los botones <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporar un ícono en un botón puede mejorar en gran medida el diseño de tu aplicación, permitiendo a los usuarios identificar rápidamente elementos accionables en la pantalla. El componente [`Icon`](./icon.md) proporciona una amplia selección de íconos para elegir.

Al utilizar los métodos `setPrefixComponent()` y `setSuffixComponent()`, tienes la flexibilidad de determinar si un `Icon` debe aparecer antes o después del texto en un botón. Alternativamente, se puede usar el método `setIcon()` para agregar un `Icon` después del texto, pero antes de la ranura del `suffix` del botón.

<!-- Add this back in once Icon has been merged -->
<!-- Refer to the [Icon component](../components/icon) page for more information on configuring and customizing icons. -->

:::tip
Por defecto, un `Icon` hereda el tema y la expansión del botón.
:::

A continuación se muestran ejemplos de botones con texto a la izquierda y a la derecha, así como un botón con solo un ícono:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Nombres {#names}

El componente `Button` utiliza nombres, que se usan para accesibilidad. Cuando no se establece un nombre explícitamente, se utilizará la etiqueta del `Button`. Sin embargo, algunos íconos no tienen etiquetas y solo muestran elementos no-textuales, como íconos. En este caso, es conveniente utilizar el método `setName()` para garantizar que el componente `Button` creado cumpla con los estándares de accesibilidad.

## Deshabilitando un botón {#disabling-a-button}

Los componentes de botón, como muchos otros, pueden ser deshabilitados para transmitir a un usuario que una cierta acción no está disponible aún o ya no está. Un botón deshabilitado disminuirá la opacidad del botón y está disponible para todos los temas y expansiones de botón.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Deshabilitar un botón se puede hacer en cualquier momento en el código utilizando la función <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Para mayor comodidad, un botón también se puede deshabilitar al hacer clic utilizando la función incorporada <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

En algunas aplicaciones, hacer clic en un botón desencadena una acción de larga duración. En la mayoría de los casos, la aplicación podría querer asegurarse de que solo se procese un solo clic. Esto puede ser un problema en entornos de alta latencia cuando el usuario hace clic en el botón varias veces antes de que la aplicación haya tenido la oportunidad de comenzar a procesar la acción resultante.

:::tip
Deshabilitar al hacer clic no solo ayuda a optimizar el procesamiento de acciones, sino que también evita que el desarrollador tenga que implementar este comportamiento por su cuenta, ya que este método ha sido optimizado para reducir las comunicaciones de ida y vuelta.
:::

## Estilo {#styling}

### Temas {#themes}

Los componentes `Button` vienen con <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 temas discretos</JavadocLink> incorporados para un rápido estilo sin el uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los botones para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar la apariencia de los botones a lo largo de una aplicación. 

Mientras que hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos de uso son:

  - **Peligroso**: Mejor para acciones con graves consecuencias, como limpiar información completada o eliminar permanentemente una cuenta/datos.
  - **Predeterminado**: Apropiado para acciones a lo largo de una aplicación que no requieren atención especial y son genéricas, como alternar una configuración.
  - **Primario**: Apropiado como una "llamada a la acción" principal en una página, como registrarse, guardar cambios o continuar a otra página.
  - **Éxito**: Excelente para visualizar la finalización exitosa de un elemento en una aplicación, como el envío de un formulario o la finalización de un proceso de registro. El tema de éxito se puede aplicar programáticamente una vez que se ha completado una acción exitosa.
  - **Advertencia**: Útil para indicar que un usuario está a punto de realizar una acción potencialmente arriesgada, como navegar lejos de una página con cambios no guardados. Estas acciones son a menudo menos impactantes que aquellas que usarían el tema Peligroso.
  - **Gris**: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página y no forman parte de la funcionalidad principal.
  - **Información**: Bueno para proporcionar información aclaratoria adicional a un usuario.

A continuación se muestran botones de ejemplo con cada uno de los Temas soportados aplicados: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Expansiones {#expanses}
Los siguientes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> valores de Expansiones </JavadocLink> permiten un estilo rápido sin el uso de CSS. Esto permite manipular las dimensiones del botón sin tener que establecerlo explícitamente mediante ningún estilo. Además de simplificar el estilo, también ayuda a crear y mantener una uniformidad en tu aplicación. La expansión predeterminada del `Button` es `Expanse.MEDIUM`.

Diferentes tamaños son apropiados para diferentes usos:
  - Valores de expansión **más grandes** son adecuados para botones que deben captar la atención, enfatizar funcionalidad o son parte integral de la funcionalidad central de una aplicación o página.
  - Botones de expansión **mediana**, el tamaño predeterminado, deberían ser utilizados como un "tamaño estándar", cuando el comportamiento de un botón no es más o menos importante que otros componentes similares.
  - Valores de expansión **más pequeños** deben ser utilizados para botones que no tienen comportamientos integrales en la aplicación, y sirven un rol más suplementario o utilitario, en lugar de desempeñar un papel importante en la interacción del usuario. Esto incluye componentes `Button` que se utilizan únicamente con íconos para propósitos utilitarios.

A continuación se muestran las diversas expansiones soportadas para el componente `Button`: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia de usuario óptima al utilizar el componente `Button`, considera las siguientes mejores prácticas:

1. **Texto Apropiado**: Usa texto claro y conciso para el texto dentro de tu componente `Button` para proporcionar una indicación clara de su propósito.

2. **Estilo Visual Apropiado**: Considera el estilo visual y el tema del `Button` para garantizar la consistencia con el diseño de tu aplicación. Por ejemplo:
  > - Un componente `Button` "Cancelar" debería estar estilizado con el tema o estilo CSS apropiado para asegurarse de que los usuarios estén seguros de que desean cancelar una acción.
  > - Un `Button` "Confirmar" tendría un estilo diferente al de un botón "Cancelar", pero también debería destacarse para asegurar que los usuarios sepan que esta es una acción especial.

3. **Manejo Eficiente de Eventos**: Maneja los eventos del `Button` de manera eficiente y proporciona retroalimentación apropiada a los usuarios. Consulta [Eventos](../building-ui/events) para revisar comportamientos de adición de eventos eficientes.

4. **Pruebas y Accesibilidad**: Prueba el comportamiento del botón en diferentes escenarios, como cuando está deshabilitado o recibe enfoque, para garantizar una experiencia de usuario fluida. Sigue las pautas de accesibilidad para hacer que el `Button` sea utilizable para todos los usuarios, incluidos aquellos que dependen de tecnologías de asistencia.
