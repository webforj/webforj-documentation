---
title: Dialog
sidebar_position: 30
_i18n_hash: e0d440fddf7ad6be7a78958ae1ddde1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

El componente de diálogo webforJ está diseñado para permitir a un desarrollador mostrar rápidamente y con facilidad un diálogo en su aplicación, en instancias como un menú de inicio de sesión o un cuadro de información.

El componente está construido con tres secciones, cada una de las cuales son componentes `Panel`: el **encabezado**, el **contenido** y el **pie de página**.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Usos {#usages}

1. **Retroalimentación y Confirmación del Usuario**: Los componentes `Dialog` se utilizan a menudo para proporcionar retroalimentación o pedir confirmación al usuario. Pueden mostrar varios mensajes importantes al usuario, como:

  >- Mensajes de éxito 
  >- Alertas de error
  >- Confirmaciones de envíos

2. **Entrada y Edición de Formularios**: Puedes usar diálogos para recopilar la entrada del usuario o permitirles editar información de una manera controlada y enfocada. Por ejemplo, un diálogo puede aparecer para editar detalles del perfil de usuario o completar un formulario de varios pasos.

3. **Información Contextual**: Mostrar información contextual adicional o mensajes emergentes en un diálogo puede ayudar a los usuarios a comprender características o datos complejos. Los diálogos pueden proporcionar explicaciones detalladas, gráficos o documentación de ayuda.

4. **Previsiones de Imágenes y Medios**: Cuando los usuarios necesitan ver medios, un `Dialog` puede usarse para mostrar previos más grandes o galerías, como cuando se interactúa con:
  >- Imágenes
  >- Videos
  >- Otros medios

## Fondo y desenfoque {#backdrop-and-blur}

Al habilitar el atributo de fondo del componente `Dialog` de webforJ, se mostrará un fondo detrás del `Dialog`. Además, cuando está habilitado, el atributo de desenfoque del Diálogo desenfocará el fondo del `Dialog`. Modificar estas configuraciones puede ayudar a los usuarios proporcionando profundidad, jerarquía visual y contexto, lo que lleva a una orientación más clara para el usuario.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Abrir y cerrar el `Dialog` {#opening-and-closing-the-dialog}

Después de crear un nuevo objeto `Dialog`, usa el método `open()` para mostrar el diálogo. Luego, el componente `Dialog` puede cerrarse mediante una de estas acciones:
- Usando el método `close()`
- Presionando la tecla <kbd>ESC</kbd>
- Haciendo clic fuera del `Dialog`

Los desarrolladores pueden elegir qué interacciones cierran el `Dialog` con `setCancelOnEscKey()` y `setCancelOnOutsideClick()`. Además, el método `setClosable()` puede prevenir o permitir tanto la pulsación de la tecla <kbd>ESC</kbd> como el clic fuera del `Dialog` para cerrar el componente.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Enfoque automático {#auto-focus}

Cuando está habilitado, el enfoque automático dará automáticamente el enfoque al primer elemento dentro del diálogo que puede ser enfocado. Esto es útil para ayudar a dirigir la atención de los usuarios y es personalizable a través del método `setAutoFocus()`.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Arrastrable {#draggable}

El `Dialog` tiene funcionalidad integrada para ser arrastrable, permitiendo al usuario reubicar la ventana del `Dialog` haciendo clic y arrastrando. La posición del `Dialog` puede manipularse desde cualquiera de los campos dentro de él: el encabezado, el contenido o el pie de página.

### Ajustar a los bordes {#snap-to-edge}
También es posible calibrar este comportamiento para ajustarse a los bordes de la pantalla, lo que significa que el `Dialog` se alineará automáticamente con el borde de la pantalla cuando se suelte de su fecha de arrastre y caída. El ajuste puede cambiarse a través del método `setSnapToEdge()`. El `setSnapThreshold()` toma un número de píxeles, que establecerá qué tan lejos debe estar el `Dialog` de los lados de la pantalla antes de que se ajuste automáticamente a los bordes.  

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Posicionamiento {#positioning}

La posición del diálogo puede manipularse utilizando los métodos integrados `setPosx()` y `setPosy()`. Estos métodos toman un argumento de cadena que puede representar cualquier unidad de longitud CSS aplicable, como píxeles o altura/ancho de visualización. Una lista de estas medidas [se puede encontrar en este enlace](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Alineación vertical {#vertical-alignment}

Además de la asignación manual de la posición X e Y de un diálogo, es posible utilizar la clase enum integrada del diálogo para alinear el `Dialog`. Hay tres valores posibles: `TOP`, `CENTER` y `BOTTOM`, cada uno de los cuales puede usarse con el método `setAlignment()`.

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Pantalla completa y puntos de quiebre {#full-screen-and-breakpoints}

El `Dialog` puede configurarse para entrar en modo de pantalla completa. Cuando se habilita la pantalla completa, el `Dialog` no puede moverse ni posicionarse. Este modo puede manipularse con el atributo de punto de quiebre del `Dialog`. El punto de quiebre es una consulta de medios que establece cuándo el `Dialog` cambiará automáticamente a modo de pantalla completa. Cuando la consulta coincide, el `Dialog` cambia a pantalla completa; de lo contrario, se posiciona.

## Estilo {#styling}

### Temas {#themes}

Los componentes `Dialog` vienen con <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 temas discretos </JavadocLink> integrados para una rápida estilización sin uso de CSS. Estos temas son estilos predefinidos que pueden aplicarse a los botones para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar la apariencia de los botones en toda una aplicación. 

Si bien hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos de uso son:

  - **Peligro**: Acciones con graves consecuencias, como borrar información completada o eliminar permanentemente una cuenta/datos representan un buen caso de uso para diálogos con el tema Peligro.
  - **Predeterminado**: El tema predeterminado es apropiado para acciones a lo largo de una aplicación que no requieren atención especial y que son genéricas, como alternar una configuración.
  - **Primario**: Este tema es apropiado como una "llamada a la acción" principal en una página, como inscribirse, guardar cambios, o continuar a otra página.
  - **Éxito**: Los diálogos con tema de éxito son excelentes para visualizar la finalización exitosa de un elemento en una aplicación, como el envío de un formulario o la finalización de un proceso de inscripción. El tema de éxito puede aplicarse programáticamente una vez que se ha completado una acción exitosa.
  - **Advertencia**: Los diálogos de advertencia son útiles para indicar a los usuarios que están a punto de realizar una acción potencialmente arriesgada, como cuando navegan fuera de una página con cambios no guardados. Estas acciones suelen ser menos impactantes que aquellas que utilizarían el tema Peligro.
  - **Gris**: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página y no parte de la funcionalidad principal.
  - **Información**: El tema de Información es una buena opción para proporcionar información adicional y aclaratoria a un usuario cuando se solicita.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
