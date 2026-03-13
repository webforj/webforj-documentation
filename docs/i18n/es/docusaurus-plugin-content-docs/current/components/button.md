---
title: Button
sidebar_position: 15
_i18n_hash: 7df385d72b74249e5689c31575568ae8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un `Button` es un elemento clickeable que desencadena una acción al ser presionado. Puede mostrar texto, íconos, o una combinación de ambos. Los botones soportan múltiples temas visuales y tamaños, y pueden ser deshabilitados para prevenir interacciones durante operaciones que tardan mucho tiempo o cuando ciertas condiciones no se cumplen.

<!-- INTRO_END -->

## Usos {#usages}

La clase `Button` es un componente versátil que se utiliza comúnmente en diversas situaciones donde es necesario desencadenar interacciones y acciones del usuario. Aquí hay algunos escenarios típicos donde podrías necesitar un botón en tu aplicación:

1. **Envío de formularios**: Los botones se utilizan frecuentemente para enviar datos de formularios. Por ejemplo, en una aplicación, puedes utilizar:

  > - Un botón "Enviar" para enviar datos al servidor
  > - Un botón "Limpiar" para eliminar cualquier información ya presente en el formulario


2. **Acciones del usuario**: Los botones se utilizan para permitir que los usuarios realicen acciones específicas dentro de la aplicación. Por ejemplo, podrías tener un botón etiquetado:

  > - "Eliminar" para iniciar la eliminación de un elemento seleccionado
  > - "Guardar" para guardar los cambios realizados en un documento o página.

3. **Diálogos de confirmación**: Los botones a menudo se incluyen en componentes [`Dialog`](../components/dialog) diseñados para diversos propósitos para proporcionar opciones a los usuarios para confirmar o cancelar una acción, o cualquier otra funcionalidad que esté incorporada en el [`Dialog`](../components/dialog) que estás utilizando.

4. **Desencadenadores de interacción**: Los botones pueden servir como desencadenadores de interacciones o eventos dentro de la aplicación. Al hacer clic en un botón, los usuarios pueden iniciar acciones complejas o activar animaciones, refrescar contenido o actualizar la visualización.

5. **Navegación**: Los botones pueden ser utilizados con fines de navegación, como moverse entre diferentes secciones o páginas dentro de una aplicación. Los botones para la navegación podrían incluir:

  > - "Siguiente" - Lleva al usuario a la siguiente página o sección de la aplicación o página actual.
  > - "Anterior" - Devuelve al usuario a la página anterior de la aplicación o sección en la que se encuentra.
  > - "Atrás" - Devuelve al usuario a la primera parte de la aplicación o página en la que se encuentra.
  
El siguiente ejemplo demuestra botones utilizados para el envío de formularios y limpieza de entradas:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Agregar íconos a los botones <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporar un ícono en un botón puede mejorar significativamente el diseño de tu aplicación, permitiendo a los usuarios identificar rápidamente elementos accionables en la pantalla. El componente [`Icon`](./icon.md) proporciona una amplia selección de íconos para elegir.

Al utilizar los métodos `setPrefixComponent()` y `setSuffixComponent()`, tienes la flexibilidad para determinar si un `Icon` debe aparecer antes o después del texto en un botón. Alternativamente, se puede usar el método `setIcon()` para agregar un `Icon` después del texto, pero antes del slot `suffix` del botón.

<!-- Add this back in once Icon has been merged -->
<!-- Refer to the [Icon component](../components/icon) page for more information on configuring and customizing icons. -->

:::tip
Por defecto, un `Icon` hereda el tema y la expansión del botón.
:::

A continuación se presentan ejemplos de botones con texto a la izquierda y a la derecha, así como un botón con solo un ícono:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Nombres {#names}

El componente `Button` utiliza nombres, que se usan para accesibilidad. Cuando un nombre no se establece explícitamente, se usará la etiqueta del `Button`. Sin embargo, algunos íconos no tienen etiquetas y solo muestran elementos no textuales, como íconos. En este caso, es conveniente utilizar el método `setName()` para asegurar que el componente `Button` creado cumpla con los estándares de accesibilidad.

## Deshabilitar un botón {#disabling-a-button}

Los componentes de botones, como muchos otros, pueden ser deshabilitados para transmitir al usuario que una cierta acción no está disponible todavía o ya no lo está. Un botón deshabilitado disminuirá la opacidad del botón y está disponible para todos los temas y expansiones de botones.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

Deshabilitar un botón se puede hacer en cualquier momento en el código utilizando la función <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Para mayor comodidad, un botón también puede ser deshabilitado al hacer clic utilizando la función incorporada <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

En algunos casos, hacer clic en un botón desencadena una acción que toma mucho tiempo. Deshabilitar el botón hasta que tu aplicación procese la acción previene que el usuario haga clic en el botón múltiples veces, especialmente en entornos de alta latencia.

:::tip
Deshabilitar al hacer clic no solo ayuda a optimizar el procesamiento de acciones, sino que también evita que el desarrollador deba implementar este comportamiento por su cuenta, ya que este método ha sido optimizado para reducir las comunicaciones de ida y vuelta.
:::

## Estilización {#styling}

### Temas {#themes}

Los componentes `Button` vienen con <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 temas discretos </JavadocLink> integrados para un rápido estilizado sin el uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los botones para cambiar su apariencia y presentación visual. Ofrecen una manera rápida y consistente de personalizar el aspecto de los botones a lo largo de una aplicación.

Si bien hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos de uso son:

  - **Peligro**: Mejor para acciones con consecuencias severas, como limpiar información completada o eliminar permanentemente una cuenta/datos.
  - **Predeterminado**: Apropiado para acciones en toda una aplicación que no requieren atención especial y son genéricas, como alternar un ajuste.
  - **Primario**: Apropiado como una "llamada a la acción" principal en una página, como registrarse, guardar cambios o continuar a otra página.
  - **Éxito**: Excelente para visualizar la finalización exitosa de un elemento en una aplicación, como el envío de un formulario o la finalización de un proceso de registro. El tema de éxito puede ser aplicado programáticamente una vez que se ha completado una acción con éxito.
  - **Advertencia**: Útil para indicar que un usuario está a punto de realizar una acción potencialmente arriesgada, como navegar fuera de una página con cambios no guardados. Estas acciones son a menudo menos impactantes que aquellas que utilizarían el tema de Peligro.
  - **Gris**: Bueno para acciones sutiles, como ajustes menores o acciones que son más suplementarias a una página, y no parte de la funcionalidad principal.
  - **Información**: Bueno para proporcionar información adicional aclarativa a un usuario.

A continuación se muestran ejemplos de botones con cada uno de los Temas soportados aplicados: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Expansiones {#expanses}
Los siguientes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> valores de Expanses </JavadocLink> permiten un rápido estilizado sin usar CSS. Esto permite manipular las dimensiones del botón sin tener que establecerlo explícitamente utilizando algún estilo. Además de simplificar la estilización, también ayuda a crear y mantener una uniformidad en tu aplicación. La expansión predeterminada del `Button` es `Expanse.MEDIUM`.

Diferentes tamaños son a menudo apropiados para diferentes usos:
  - **Valores de expanse más grandes** son adecuados para botones que deberían llamar la atención, enfatizar funcionalidades o son esenciales para la funcionalidad central de una aplicación o página.
  - **Botones de expanse mediano**, el tamaño predeterminado, deben ser el tamaño estándar de los botones. Las funciones de estos botones no deberían ser ni más ni menos críticas que componentes similares.
  - **Valores de expanse más pequeños** deben ser utilizados para botones que no tienen comportamientos integrales en la aplicación, y sirven un rol más suplementario o utilitario, en lugar de desempeñar un papel importante en la interacción del usuario. Esto incluye componentes `Button` que se utilizan solo con íconos para propósitos utilitarios.

A continuación se presentan las diversas expansiones soportadas para el componente `Button`: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia de usuario óptima al utilizar el componente `Button`, considera las siguientes mejores prácticas:

1. **Texto adecuado**: Utiliza texto claro y conciso dentro de tu componente `Button` para proporcionar una indicación clara de su propósito.

2. **Estilización visual apropiada**: Considera la estilización visual y el tema del `Button` para asegurar consistencia con el diseño de tu aplicación. Por ejemplo:
  > - Un componente de botón "Cancelar" debería estar estilizado con el tema o estilización CSS apropiada para asegurar que los usuarios estén seguros de que quieren cancelar una acción.
  > - Un botón "Confirmar" tendría una estilización diferente de un botón "Cancelar", pero también destacaría para asegurar que los usuarios sepan que esta es una acción especial.

3. **Manejo eficiente de eventos**: Maneja los eventos del `Button` de manera eficiente y proporciona retroalimentación apropiada a los usuarios. Consulta [Eventos](../building-ui/events) para revisar comportamientos eficientes de adición de eventos.

4. **Pruebas y accesibilidad**: Prueba el comportamiento del botón en diferentes escenarios, como cuando está deshabilitado o recibe foco, para asegurar una experiencia de usuario fluida. Sigue las pautas de accesibilidad para hacer que el `Button` sea usable por todos los usuarios, incluyendo aquellos que dependen de tecnologías asistivas.
