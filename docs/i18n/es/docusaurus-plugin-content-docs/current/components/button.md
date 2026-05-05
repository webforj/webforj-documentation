---
title: Button
sidebar_position: 15
_i18n_hash: 5e0b4998a50b6c7d935c53c9c11009d6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un `Button` es un elemento clickeable que desencadena una acción cuando se presiona. Puede mostrar texto, íconos o una combinación de ambos. Los botones admiten múltiples temas visuales y tamaños, y pueden estar desactivados para prevenir interacciones durante operaciones de larga duración o cuando no se cumplen ciertas condiciones.

<!-- INTRO_END -->

## Usos {#usages}

La clase `Button` es un componente versátil que se utiliza comúnmente en varias situaciones donde se necesitan desencadenar interacciones y acciones del usuario. Aquí hay algunos escenarios típicos donde podrías necesitar un botón en tu aplicación:

1. **Envío de formularios**: Los botones se utilizan a menudo para enviar datos de formularios. Por ejemplo, en una aplicación, puedes usar:

  > - Un botón "Enviar" para enviar datos al servidor
  > - Un botón "Limpiar" para eliminar cualquier información ya presente en el formulario


2. **Acciones de usuario**: Los botones se utilizan para permitir que los usuarios realicen acciones específicas dentro de la aplicación. Por ejemplo, puedes tener un botón etiquetado:

  > - "Eliminar" para iniciar la eliminación de un elemento seleccionado
  > - "Guardar" para guardar los cambios realizados en un documento o página.

3. **Diálogos de confirmación**: Los botones a menudo se incluyen en componentes de [`Dialog`](../components/dialog) diseñados para diversos propósitos para proporcionar opciones a los usuarios para confirmar o cancelar una acción, o cualquier otra funcionalidad que esté integrada en el [`Dialog`](../components/dialog) que estés utilizando.

4. **Disparadores de interacciones**: Los botones pueden servir como disparadores para interacciones o eventos dentro de la aplicación. Al hacer clic en un botón, los usuarios pueden iniciar acciones complejas o desencadenar animaciones, refrescar contenido o actualizar la visualización.

5. **Navegación**: Los botones pueden usarse con fines de navegación, como moverse entre diferentes secciones o páginas dentro de una aplicación. Los botones para navegación podrían incluir:

  > - "Siguiente" - Lleva al usuario a la siguiente página o sección de la aplicación o página actual.
  > - "Anterior" - Devuelve al usuario a la página anterior de la aplicación o sección en la que está.
  > - "Atrás" - Devuelve al usuario a la primera parte de la aplicación o página en la que está.
  
El siguiente ejemplo demuestra botones utilizados para el envío de formularios y la limpieza de entradas:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Agregando íconos a botones <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporar un ícono en un botón puede mejorar significativamente el diseño de tu aplicación, permitiendo a los usuarios identificar rápidamente los elementos accionables en la pantalla. El componente [`Icon`](./icon.md) ofrece una amplia selección de íconos para elegir.

Al utilizar los métodos `setPrefixComponent()` y `setSuffixComponent()`, tienes la flexibilidad de determinar si un `Icon` debe aparecer antes o después del texto en un botón. Alternativamente, se puede usar el método `setIcon()` para agregar un `Icon` después del texto, pero antes del slot `suffix` del botón.

<!-- Agrega esto una vez que Icon se haya fusionado -->
<!-- Consulta la página del [componente Icon](../components/icon) para obtener más información sobre cómo configurar y personalizar íconos. -->

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

El componente `Button` utiliza nombres, que se emplean para accesibilidad. Cuando no se establece un nombre explícitamente, se usará la etiqueta del `Button`. Sin embargo, algunos íconos no tienen etiquetas y solo muestran elementos no textuales, como íconos. En este caso, es conveniente usar el método `setName()` para garantizar que el componente `Button` creado cumpla con los estándares de accesibilidad.

## Deshabilitando un botón {#disabling-a-button}

Los componentes de botón, al igual que muchos otros, pueden desactivarse para transmitir a un usuario que una acción determinada no está disponible aún o ya no está disponible. Un botón deshabilitado disminuirá la opacidad del botón, y está disponible para todos los temas y expansiones de los botones.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

Deshabilitar un botón se puede hacer en cualquier momento en el código utilizando la función <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Para mayor conveniencia, un botón también puede deshabilitarse al hacer clic utilizando la función incorporada <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

En algunos casos, hacer clic en un botón desencadena una acción de larga duración. Deshabilitar el botón hasta que tu aplicación procese la acción evita que el usuario haga clic en el botón varias veces, especialmente en entornos de alta latencia.

:::tip
Deshabilitar al hacer clic no solo ayuda a optimizar el procesamiento de acciones, sino que también evita que el desarrollador necesite implementar este comportamiento por su cuenta, ya que este método ha sido optimizado para reducir las comunicaciones de ida y vuelta.
:::

## Estilo {#styling}

### Temas {#themes}

Los componentes `Button` vienen con <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 temas discretos </JavadocLink> integrados para un estilo rápido sin necesidad de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los botones para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar el aspecto de los botones a lo largo de una aplicación.

Mientras que hay muchos casos de uso para cada uno de los varios temas, algunos ejemplos de uso son:

  - **Peligro**: Mejor para acciones con graves consecuencias, como eliminar información completada o eliminar permanentemente una cuenta/datos.
  - **Por defecto**: Apropiado para acciones a lo largo de una aplicación que no requieren atención especial y son genéricas, como cambiar una configuración.
  - **Primario**: Apropiado como una principal "llamada a la acción" en una página, como registrarse, guardar cambios o continuar a otra página.
  - **Éxito**: Excelente para visualizar la finalización exitosa de un elemento en una aplicación, como el envío de un formulario o la finalización de un proceso de registro. El tema de éxito se puede aplicar programáticamente una vez que se ha completado una acción exitosa.
  - **Advertencia**: Útil para indicar que un usuario está a punto de realizar una acción potencialmente arriesgada, como navegar fuera de una página con cambios no guardados. Estas acciones suelen ser menos impactantes que aquellas que usarían el tema Peligro.
  - **Gris**: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página, y no forman parte de la funcionalidad principal.
  - **Información**: Bueno para proporcionar información adicional aclaratoria a un usuario.

A continuación se muestran ejemplos de botones con cada uno de los temas compatibles aplicados: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
height='175px'
/>

### Expansiones {#expanses}
Los siguientes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> valores de Expansiones </JavadocLink> permiten un estilo rápido sin usar CSS. Esto permite manipular las dimensiones del botón sin tener que establecerlo explícitamente usando algún estilo. Además de simplificar el estilo, también ayuda a crear y mantener una uniformidad en tu aplicación. La expansión `Button` por defecto es `Expanse.MEDIUM`.

Diferentes tamaños son a menudo apropiados para diferentes usos:
  - Los valores de expansión **más grandes** son adecuados para botones que deben llamar la atención, enfatizar funcionalidad o son parte integral de la funcionalidad central de una aplicación o página.
  - Botones de expansión **mediana**, el tamaño predeterminado, deberían ser el tamaño estándar de los botones. Las funciones de estos botones no deberían ser más ni menos críticas que las de componentes similares.
  - Los valores de expansión **más pequeños** deben usarse para botones que no tienen comportamientos integrales en la aplicación, y sirven un papel más suplementario o utilitario, en lugar de jugar un papel importante en la interacción del usuario. Esto incluye componentes de `Button` que se utilizan solo con íconos para propósitos utilitarios.

A continuación se presentan las diversas expansiones admitidas para el componente `Button`: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima del usuario al utilizar el componente `Button`, considera las siguientes mejores prácticas:

1. **Texto adecuado**: Usa texto claro y conciso para el texto dentro de tu componente `Button` para proporcionar una indicación clara de su propósito.

2. **Estilo visual apropiado**: Considera el estilo visual y el tema del `Button` para asegurar consistencia con el diseño de tu aplicación. Por ejemplo:
  > - Un componente `Button` "Cancelar" debería estilizarse con el tema o estilo CSS apropiado para asegurar que los usuarios estén seguros de que quieren cancelar una acción
  > - Un `Button` "Confirmar" tendría un estilo diferente al de un botón "Cancelar", pero de manera similar destacaría para asegurar que los usuarios sepan que esta es una acción especial.

3. **Manejo eficiente de eventos**: Maneja los eventos del `Button` de manera eficiente y proporciona retroalimentación adecuada a los usuarios. Consulta [Eventos](../building-ui/events) para revisar comportamientos eficientes de adición de eventos.

4. **Pruebas y accesibilidad**: Prueba el comportamiento del botón en diferentes escenarios, como cuando está deshabilitado o recibe foco, para asegurar una experiencia de usuario fluida. Sigue las pautas de accesibilidad para hacer que el `Button` sea utilizable para todos los usuarios, incluidos aquellos que dependen de tecnologías asistivas.
