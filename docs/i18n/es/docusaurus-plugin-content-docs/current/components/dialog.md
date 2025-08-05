---
title: Dialog
sidebar_position: 30
_i18n_hash: d0087974ac244db9b082133be7966a3e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

El componente de diálogo webforJ está diseñado para permitir que un desarrollador muestre rápida y fácilmente un diálogo en su aplicación, para situaciones como un menú de inicio de sesión o un cuadro de información.

El componente está construido con tres secciones, cada una de las cuales son componentes `Panel`: el **encabezado**, el **contenido** y el **pie**.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Usos {#usages}

1. **Retroalimentación y Confirmación del Usuario**: Los componentes `Dialog` se utilizan a menudo para proporcionar retroalimentación o solicitar confirmación al usuario. Pueden mostrar varias piezas importantes de retroalimentación a un usuario, tales como:

  >- Mensajes de éxito 
  >- Alertas de error
  >- Confirmaciones de envíos

2. **Entrada y Edición de Formularios**: Puedes usar diálogos para recolectar entradas del usuario o permitirles editar información de manera controlada y enfocada. Por ejemplo, un diálogo puede aparecer para editar detalles del perfil del usuario o completar un formulario de múltiples pasos.

3. **Información Contextual**: Mostrar información contextual adicional o tooltips en un diálogo puede ayudar a los usuarios a entender características o datos complejos. Los diálogos pueden proporcionar explicaciones detalladas, gráficos o documentación de ayuda.

4. **Visores de Imágenes y Medios**: Cuando los usuarios necesitan ver piezas de medios, se puede usar un `Dialog` para mostrar vistas previas más grandes o galerías, como al interactuar con:
  >- Imágenes
  >- Videos
  >- Otros medios

## Fondo y desenfoque {#backdrop-and-blur}

Al habilitar el atributo de fondo del componente `Dialog` de webforJ, se mostrará un fondo detrás del `Dialog`. Además, cuando está habilitado, el atributo desenfocado del diálogo desenfocará el fondo del `Dialog`. Modificar estas configuraciones puede ayudar a los usuarios al proporcionar profundidad, jerarquía visual y contexto, lo que lleva a una guía más clara para un usuario.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Abrir y cerrar el `Dialog` {#opening-and-closing-the-dialog}

Después de crear un nuevo objeto `Dialog`, usa el método `open()` para mostrar el diálogo. Luego, el componente `Dialog` se puede cerrar por una de estas acciones:
- Usando el método `close()`
- Presionando la tecla <kbd>ESC</kbd>
- Haciendo clic fuera del `Dialog`

Los desarrolladores pueden elegir qué interacciones cierran el `Dialog` con `setCancelOnEscKey()` y `setCancelOnOutsideClick()`. Además, el método `setClosable()` puede prevenir o permitir tanto presionar la tecla <kbd>ESC</kbd> como hacer clic fuera del `Dialog` para cerrar el componente.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Auto-enfoque {#auto-focus}

Cuando se habilita, el auto-enfoque dará automáticamente el foco al primer elemento dentro del diálogo que puede ser enfocado. Esto es útil para ayudar a dirigir la atención de los usuarios, y es personalizable a través del método `setAutoFocus()`.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Arrastrable {#draggable}

El `Dialog` tiene funcionalidad incorporada para ser arrastrable, permitiendo al usuario reubicar la ventana del `Dialog` haciendo clic y arrastrando. La posición del `Dialog` se puede manipular desde cualquiera de los campos dentro de él: el encabezado, el contenido o el pie.

### Ajustar al borde {#snap-to-edge}
También es posible calibrar este comportamiento para ajustarse al borde de la pantalla, lo que significa que el `Dialog` se alineará automáticamente con el borde de la pantalla cuando se suelte después de su arrastre. El ajuste se puede cambiar a través del método `setSnapToEdge()`. El `setSnapThreshold()` toma un número de píxeles, que establecerá qué tan lejos debe estar el `Dialog` de los lados de la pantalla antes de que se ajuste automáticamente a los bordes.  

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Posicionamiento {#positioning}

La posición del diálogo se puede manipular utilizando los métodos incorporados `setPosx()` y `setPosy()`. Estos métodos toman un argumento de cadena que puede representar cualquier unidad de longitud CSS aplicable, como píxeles o altura/anchura de la vista. Una lista de estas medidas [se puede encontrar en este enlace](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Alineación Vertical {#vertical-alignment}

Además de la asignación manual de la posición X y Y de un diálogo, es posible utilizar la clase enum incorporada del diálogo para alinear el `Dialog`. Hay tres valores posibles, `TOP`, `CENTER` y `BOTTOM`, cada uno de los cuales se puede utilizar con el método `setAlignment()`.

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Pantalla completa y puntos de interrupción {#full-screen-and-breakpoints}

El `Dialog` se puede configurar para entrar en modo de pantalla completa. Cuando la pantalla completa está habilitada, el `Dialog` no se puede mover ni posicionar. Este modo se puede manipular con el atributo de punto de interrupción del `Dialog`. El punto de interrupción es una consulta de medios que determina cuándo el `Dialog` se cambiará automáticamente a modo de pantalla completa. Cuando la consulta coincide, el `Dialog` cambia a pantalla completa; de lo contrario, se posiciona.

## Estilización {#styling}

### Temas {#themes}

Los componentes `Dialog` vienen con <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 temas discretos</JavadocLink> incorporados para estilizar rápidamente sin el uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los botones para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar la apariencia de los botones en toda una aplicación.

Si bien hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos de usos son:

  - **Peligro**: Acciones con consecuencias severas, como borrar información completada o eliminar permanentemente una cuenta/datos, representa un buen caso de uso para diálogos con el tema de Peligro.
  - **Predeterminado**: El tema predeterminado es apropiado para acciones en toda una aplicación que no requieren atención especial y que son genéricas, como alternar una configuración.
  - **Primario**: Este tema es apropiado como una "llamada a la acción" principal en una página, como registrarse, guardar cambios o continuar a otra página.
  - **Éxito**: Los diálogos con tema de éxito son excelentes para visualizar la finalización exitosa de un elemento en una aplicación, como la presentación de un formulario o la finalización de un proceso de registro. El tema de éxito puede aplicarse programáticamente una vez que se ha completado una acción exitosa.
  - **Advertencia**: Los diálogos de advertencia son útiles para indicar a los usuarios que están a punto de realizar una acción potencialmente arriesgada, como al navegar fuera de una página con cambios no guardados. Estas acciones son a menudo menos impactantes que aquellas que usarían el tema de Peligro.
  - **Gris**: Bueno para acciones sutiles, como configuraciones menores o acciones que son más complementarias a una página y no parte de la funcionalidad principal.
  - **Información**: El tema de Información es una buena opción para proporcionar información aclaratoria adicional a un usuario cuando es necesario.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
