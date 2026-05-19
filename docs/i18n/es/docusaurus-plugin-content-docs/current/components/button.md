---
title: Button
sidebar_position: 15
_i18n_hash: 4f84dd4c618dafe32cbeb47c7dcbbe36
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un `Button` es un elemento clickeable que activa una acción cuando se presiona. Puede mostrar texto, íconos o una combinación de ambos. Los botones soportan múltiples temas visuales y tamaños, y pueden ser desactivados para evitar interacciones durante operaciones prolongadas o cuando ciertas condiciones no se cumplen.

<!-- INTRO_END -->

## Usos {#usages}

La clase `Button` es un componente versátil que se utiliza comúnmente en diversas situaciones donde se necesita activar interacciones y acciones del usuario. Aquí hay algunos escenarios típicos donde podrías necesitar un botón en tu aplicación:

1. **Envío de formularios**: Los botones se utilizan a menudo para enviar datos de formularios. Por ejemplo, en una aplicación, puedes usar:

  > - Un botón de "Enviar" para enviar datos al servidor.
  > - Un botón de "Limpiar" para eliminar cualquier información ya presente en el formulario.

2. **Acciones del usuario**: Los botones se utilizan para permitir que los usuarios realicen acciones específicas dentro de la aplicación. Por ejemplo, puedes tener un botón etiquetado:

  > - "Eliminar" para iniciar la eliminación de un elemento seleccionado.
  > - "Guardar" para guardar los cambios realizados en un documento o página.

3. **Diálogos de confirmación**: Los botones a menudo se incluyen en componentes [`Dialog`](../components/dialog) construidos para diversos propósitos, proporcionando opciones para que los usuarios confirmen o cancelen una acción, o cualquier otra funcionalidad que esté integrada en el [`Dialog`](../components/dialog) que estás usando.

4. **Disparadores de interacción**: Los botones pueden servir como disparadores para interacciones o eventos dentro de la aplicación. Al hacer clic en un botón, los usuarios pueden iniciar acciones complejas, activar animaciones, refrescar contenido o actualizar la visualización.

5. **Navegación**: Los botones pueden usarse con fines de navegación, como pasar entre diferentes secciones o páginas dentro de una aplicación. Los botones para navegación podrían incluir:

  > - "Siguiente" - Lleva al usuario a la siguiente página o sección de la aplicación o página actual.
  > - "Anterior" - Devuelve al usuario a la página anterior de la aplicación o sección en la que se encuentra.
  > - "Atrás" - Devuelve al usuario a la primera parte de la aplicación o página en la que se encuentra.

El siguiente ejemplo demuestra botones utilizados para el envío de formularios y la limpieza de entradas:

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## Agregando íconos a los botones <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporar un ícono en un botón puede mejorar significativamente el diseño de tu aplicación, permitiendo a los usuarios identificar rápidamente los elementos accionables en la pantalla. El componente [`Icon`](./icon.md) proporciona una amplia selección de íconos para elegir.

Al utilizar los métodos `setPrefixComponent()` y `setSuffixComponent()`, tienes la flexibilidad de determinar si un `Icon` debe aparecer antes o después del texto en un botón. Alternativamente, el método `setIcon()` se puede utilizar para agregar un `Icon` después del texto, pero antes del slot `suffix` del botón.

<!-- Agrega esto nuevamente una vez que Icon haya sido fusionado -->
<!-- Consulta la página del [componente Icon](../components/icon) para obtener más información sobre la configuración y personalización de íconos. -->

:::tip
Por defecto, un `Icon` hereda el tema y la expansión del botón.
:::

A continuación, se presentan ejemplos de botones con texto a la izquierda y a la derecha, así como un botón con solo un ícono:

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### Nombres {#names}

El componente `Button` utiliza nombres, que se utilizan para accesibilidad. Cuando un nombre no está establecido explícitamente, se utilizará la etiqueta del `Button`. Sin embargo, algunos íconos no tienen etiquetas y solo muestran elementos no textuales, como íconos. En este caso, es conveniente utilizar el método `setName()` para garantizar que el componente `Button` creado cumpla con los estándares de accesibilidad.

## Deshabilitando un botón {#disabling-a-button}

Los componentes de botón, como muchos otros, pueden ser deshabilitados para comunicar a un usuario que cierta acción aún no está disponible o ya no está disponible. Un botón deshabilitado disminuirá la opacidad del botón y está disponible para todos los temas y expansiones de botones.

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

Deshabilitar un botón se puede hacer en cualquier momento en el código utilizando la función <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Para mayor comodidad, un botón también puede ser deshabilitado al hacer clic utilizando la función incorporada <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

En algunos casos, hacer clic en un botón desencadena una acción de larga duración. Deshabilitar el botón hasta que tu aplicación procese la acción evita que el usuario haga clic en el botón varias veces, especialmente en entornos de alta latencia.

:::tip
Deshabilitar al hacer clic no solo ayuda a optimizar el procesamiento de acciones, sino que también evita que el desarrollador necesite implementar este comportamiento por su cuenta, ya que este método ha sido optimizado para reducir las comunicaciones de ida y vuelta.
:::

## Estilo {#styling}

### Temas {#themes}

Los componentes `Button` vienen con <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 temas discretos</JavadocLink> integrados para un estilo rápido sin el uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los botones para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar la apariencia de los botones a lo largo de una aplicación.

Si bien hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos de uso son:

  - **Peligro**: Mejor para acciones con consecuencias severas, como limpiar información llena o eliminar permanentemente una cuenta/datos.
  - **Por defecto**: Apropiado para acciones en toda una aplicación que no requieren atención especial y son genéricas, como alternar una configuración.
  - **Primario**: Apropiado como una "llamada a la acción" principal en una página, como registrarse, guardar cambios o continuar a otra página.
  - **Éxito**: Excelente para visualizar la finalización exitosa de un elemento en una aplicación, como el envío de un formulario o la finalización de un proceso de registro. El tema de éxito se puede aplicar programáticamente una vez que se ha completado una acción exitosa.
  - **Advertencia**: Útil para indicar que un usuario está a punto de realizar una acción potencialmente arriesgada, como navegar fuera de una página con cambios no guardados. Estas acciones suelen ser menos impactantes que aquellas que usarían el tema de Peligro.
  - **Gris**: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias para una página y no forman parte de la funcionalidad principal.
  - **Información**: Bueno para proporcionar información aclaratoria adicional a un usuario.

A continuación se muestran botones de ejemplo con cada uno de los temas soportados aplicados: <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### Expansiones {#expanses}
Los siguientes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> valores de Expansiones </JavadocLink> permiten un estilo rápido sin necesidad de usar CSS. Esto permite manipular las dimensiones del botón sin tener que establecerlo explícitamente utilizando algún estilo. Además de simplificar el estilo, también ayuda a crear y mantener una uniformidad en tu aplicación. La expansión de `Button` por defecto es `Expanse.MEDIUM`.

Diferentes tamaños son a menudo apropiados para diferentes usos:
  - **Valores de expansión más grandes** son adecuados para botones que deberían captar la atención, enfatizar la funcionalidad o ser parte integral de la funcionalidad principal de una aplicación o página.
  - **Botones de expansión mediana**, el tamaño por defecto, deberían ser el tamaño estándar de los botones. Las funciones de estos botones no deberían ser más ni menos críticas que componentes similares.
  - **Valores de expansión más pequeños** deberían utilizarse para botones que no tienen comportamientos integrales en la aplicación, y sirven un papel más suplementario o utilitario, en lugar de desempeñar un papel importante en la interacción del usuario. Esto incluye componentes `Button` que se utilizan solo con íconos con fines utilitarios.

A continuación se presentan las diversas expansiones soportadas para el componente `Button`: <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Button" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia de usuario óptima al utilizar el componente `Button`, considera las siguientes mejores prácticas:

1. **Texto adecuado**: Usa texto claro y conciso para el texto dentro de tu componente `Button` para proporcionar una clara indicación de su propósito.

2. **Estilo visual apropiado**: Considera el estilo visual y el tema del `Button` para asegurar consistencia con el diseño de tu aplicación. Por ejemplo:
  > - Un componente `Button` de "Cancelar" debería estar estilizado con el tema apropiado o estilo CSS para asegurar que los usuarios estén seguros de que quieren cancelar una acción.
  > - Un `Button` de "Confirmar" tendría un estilo diferente al de un botón "Cancelar", pero también destacaría para asegurar que los usuarios sepan que esta es una acción especial.

3. **Manejo eficiente de eventos**: Maneja los eventos de `Button` de manera eficiente y proporciona retroalimentación apropiada a los usuarios. Consulta [Eventos](../building-ui/events) para revisar comportamientos eficientes de agregación de eventos.

4. **Pruebas y accesibilidad**: Prueba el comportamiento del botón en diferentes escenarios, como cuando está deshabilitado o recibe enfoque, para asegurar una experiencia de usuario fluida.
Sigue las pautas de accesibilidad para hacer que el `Button` sea utilizable para todos los usuarios, incluidos aquellos que dependen de tecnologías asistivas.
