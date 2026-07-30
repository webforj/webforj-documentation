---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 385730b12eeec91287bcbbf77b4e9c77
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

El componente `Dialog` muestra una ventana emergente que se superpone a la vista actual, llamando la atención sobre el contenido enfocado como formularios, confirmaciones o mensajes informativos.

<!-- INTRO_END -->

## Estructura del `Dialog` {#dialog-structure}

El `Dialog` se organiza en tres secciones: un encabezado, un área de contenido y un pie. Se pueden agregar componentes a cada sección usando `addToHeader()`, `addToContent()`, y `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Usos {#usages}

1. **Retroalimentación y Confirmación del Usuario**: Los componentes `Dialog` se utilizan a menudo para proporcionar retroalimentación o pedir confirmación al usuario. Pueden mostrar varias piezas de retroalimentación importante para un usuario, como:

  >- Mensajes de éxito
  >- Alertas de error
  >- Confirmaciones de envíos

2. **Entrada y Edición de Formularios**: Puedes usar diálogos para recopilar la entrada del usuario o permitirles editar información de manera controlada y enfocada. Por ejemplo, un diálogo puede aparecer para editar detalles del perfil del usuario o completar un formulario de varios pasos.

3. **Información Contextual**: Mostrar información contextual adicional o tooltips en un diálogo puede ayudar a los usuarios a entender características o datos complejos. Los diálogos pueden proporcionar explicaciones en profundidad, gráficos o documentación de ayuda.

4. **Previews de Imágenes y Medios**: Cuando los usuarios necesitan ver piezas de medios, se puede usar un `Dialog` para mostrar vistas previas más grandes o galerías, como al interactuar con:
  >- Imágenes
  >- Videos
  >- Otros medios

## Fondo y desenfoque {#backdrop-and-blur}

Un componente `Dialog` abierto tiene un fondo atenuado que sutilmente llama la atención sobre su contenido. Al usar `setBackdrop()` y `setBlurred()`, puedes cambiar cómo webforJ muestra (o oculta) el contenido detrás del `Dialog`. Modificar estos atributos puede ayudar a los usuarios al proporcionar profundidad y jerarquía visual.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## Abrir y cerrar el `Dialog` {#opening-and-closing-the-dialog}

Después de crear un nuevo objeto `Dialog`, usa el método `open()` para mostrar el diálogo. Luego, el componente `Dialog` puede cerrarse a partir de una de estas acciones:
- Usando el método `close()`
- Presionando la tecla <kbd>ESC</kbd>
- Haciendo clic fuera del `Dialog`

Los desarrolladores pueden elegir qué interacciones cierran el `Dialog` con `setCancelOnEscKey()` y `setCancelOnOutsideClick()`. Además, el método `setClosable()` puede prevenir o permitir tanto presionar la tecla <kbd>ESC</kbd> como hacer clic fuera del `Dialog` para cerrar el componente.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Enfoque automático {#auto-focus}

Cuando está habilitado, el enfoque automático dará automáticamente el enfoque al primer elemento dentro del diálogo que puede ser enfocado. Esto es útil para ayudar a dirigir la atención de los usuarios y se puede personalizar a través del método `setAutoFocus()`.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Arrastrable {#draggable}

El `Dialog` tiene funcionalidad incorporada para ser arrastrable, permitiendo al usuario reubicar la ventana del `Dialog` al hacer clic y arrastrar. La posición del `Dialog` puede ser manipulada desde cualquiera de los campos dentro de él: el encabezado, el contenido o el pie.

### Ajuste a los bordes {#snap-to-edge}
También es posible calibrar este comportamiento para ajustarse al borde de la pantalla, lo que significa que el `Dialog` se alineará automáticamente con el borde de la pantalla cuando se suelte desde su posición de arrastre y caída. El ajuste se puede cambiar a través del método `setSnapToEdge()`. El `setSnapThreshold()` toma un número de píxeles, que establecerá cuán lejos debe estar el `Dialog` de los lados de la pantalla antes de que se ajuste automáticamente a los bordes.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Posicionamiento {#positioning}

La posición del diálogo se puede manipular utilizando los métodos incorporados `setPosx()` y `setPosy()`. Estos métodos toman un argumento de cadena que puede representar cualquier unidad de longitud CSS aplicable, como píxeles o altura/ancho de vista. Una lista de estas medidas [se puede encontrar en este enlace](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### Alineación vertical {#vertical-alignment}

Además de la asignación manual de la posición X y Y del diálogo, es posible usar la clase enum incorporada del diálogo para alinear el `Dialog`. Hay tres valores posibles, `TOP`, `CENTER` y `BOTTOM`, cada uno de los cuales se puede usar con el método `setAlignment()`.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Pantalla completa y puntos de quiebre {#full-screen-and-breakpoints}

El `Dialog` se puede configurar para entrar en modo de pantalla completa. Cuando la pantalla completa está habilitada, el `Dialog` no se puede mover ni posicionar. Este modo se puede manipular con el atributo de punto de quiebre del `Dialog`. El punto de quiebre es una consulta de medios que componentes cuando el `Dialog` se cambiará automáticamente a modo de pantalla completa. Cuando la consulta coincide, el `Dialog` cambia a pantalla completa; de lo contrario, se posiciona.

### Ancho automático <DocChip chip='since' label='26.00' /> {#auto-width}

Por defecto, el `Dialog` se estira para llenar el espacio horizontal disponible. Cuando el ancho automático está habilitado mediante `setAutoWidth(true)`, el `Dialog` se dimensiona según el ancho de su contenido.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Estilización {#styling}

### Temas {#themes}

Los componentes `Dialog` vienen con <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 temas discretos</JavadocLink> integrados para una estilización rápida sin uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los botones para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar la apariencia de los botones en toda una aplicación.

Si bien hay muchos casos de uso para cada uno de los varios temas, algunos ejemplos de uso son:

  - **Peligro**: Acciones con consecuencias severas, como borrar información completada o eliminar permanentemente una cuenta/datos representa un buen caso de uso para diálogos con el tema Peligro.
  - **Predeterminado**: El tema predeterminado es apropiado para acciones a lo largo de una aplicación que no requieren atención especial y que son genéricas, como alternar una configuración.
  - **Primario**: Este tema es apropiado como un "llamado a la acción" principal en una página, como registrarse, guardar cambios o continuar a otra página.
  - **Éxito**: Los diálogos con tema de éxito son excelentes para visualizar la finalización exitosa de un elemento en una aplicación, como la presentación de un formulario o la finalización de un proceso de registro. El tema de éxito puede aplicarse programáticamente una vez que se ha completado una acción exitosa.
  - **Advertencia**: Los diálogos de advertencia son útiles para indicar a los usuarios que están a punto de realizar una acción potencialmente arriesgada, como al navegar fuera de una página con cambios no guardados. Estas acciones a menudo son menos impactantes que aquellas que utilizarían el tema Peligro.
  - **Gris**: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página y no parte de la funcionalidad principal.
  - **Información**: El tema de Información es una buena elección para proporcionar información adicional aclaratoria a un usuario cuando se le presiona.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
