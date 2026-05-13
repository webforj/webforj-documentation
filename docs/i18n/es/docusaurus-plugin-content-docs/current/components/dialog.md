---
title: Dialog
sidebar_position: 30
sidebar_class_name: new-content
_i18n_hash: 750f3d1f7c1c905274eac22a90b270de
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

El componente `Dialog` muestra una ventana emergente que se superpone a la vista actual, llamando la atención sobre contenido enfocado, como formularios, confirmaciones o mensajes informativos. 

<!-- INTRO_END -->

## Estructura del `Dialog` {#dialog-structure}

El `Dialog` está organizado en tres secciones: un encabezado, un área de contenido y un pie de página. Se pueden agregar componentes a cada sección utilizando `addToHeader()`, `addToContent()` y `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Usos {#usages}

1. **Retroalimentación y Confirmación del Usuario**: Los componentes `Dialog` se utilizan a menudo para proporcionar retroalimentación o solicitar la confirmación del usuario. Pueden mostrar varios mensajes de retroalimentación importantes, como:

  >- Mensajes de éxito 
  >- Alertas de error
  >- Confirmaciones de envíos

2. **Entrada y Edición de Formularios**: Puede usar diálogos para recopilar datos del usuario o permitirles editar información de manera controlada y enfocada. Por ejemplo, un diálogo puede aparecer para editar detalles del perfil del usuario o completar un formulario de varios pasos.

3. **Información Contextual**: Mostrar información adicional contextual o sugerencias en un diálogo puede ayudar a los usuarios a entender características o datos complejos. Los diálogos pueden proporcionar explicaciones detalladas, gráficos o documentación de ayuda.

4. **Vistas Previas de Imágenes y Medios**: Cuando los usuarios necesitan ver piezas de medios, se puede usar un `Dialog` para mostrar vistas previas más grandes o galerías, como al interactuar con:
  >- Imágenes
  >- Videos
  >- Otros medios

## Fondo y desenfoque {#backdrop-and-blur}

Al habilitar el atributo de fondo del componente `Dialog` de webforJ, se mostrará un fondo detrás del `Dialog`. Además, cuando se habilita, el atributo de desenfoque del `Dialog` desenfocará el fondo del mismo. Modificar estas configuraciones puede ayudar a los usuarios al proporcionar profundidad, jerarquía visual y contexto, lo que lleva a una orientación más clara para el usuario.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='300px'
/>

## Abrir y cerrar el `Dialog` {#opening-and-closing-the-dialog}

Después de crear un nuevo objeto `Dialog`, use el método `open()` para mostrar el diálogo. Luego, el componente `Dialog` puede cerrarse mediante una de estas acciones:
- Usando el método `close()`
- Presionando la tecla <kbd>ESC</kbd>
- Haciendo clic fuera del `Dialog`

Los desarrolladores pueden elegir qué interacciones cierran el `Dialog` con `setCancelOnEscKey()` y `setCancelOnOutsideClick()`. Además, el método `setClosable()` puede prevenir o permitir tanto presionar la tecla <kbd>ESC</kbd> como hacer clic fuera del `Dialog` para cerrar el componente.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Foco automático {#auto-focus}

Cuando se habilita, el enfoque automático le dará automáticamente el enfoque al primer elemento dentro del diálogo que puede ser enfocado. Esto es útil para ayudar a dirigir la atención de los usuarios y es personalizable a través del método `setAutoFocus()`.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Arrastrable {#draggable}

El `Dialog` tiene una funcionalidad incorporada para ser arrastrable, permitiendo al usuario trasladar la ventana del `Dialog` haciendo clic y arrastrando. La posición del `Dialog` puede ser manipulada desde cualquiera de los campos dentro de él: el encabezado, el contenido o el pie de página.

### Ajustar al borde {#snap-to-edge}
También es posible calibrar este comportamiento para ajustarse al borde de la pantalla, lo que significa que el `Dialog` se alineará automáticamente con el borde del display cuando se suelte desde su fecha de arrastre y caída. El ajuste se puede cambiar a través del método `setSnapToEdge()`. El `setSnapThreshold()` toma un número de píxeles, que establecerá cuán lejos debe estar el `Dialog` de los lados de la pantalla antes de que se ajuste automáticamente a los bordes.  

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

Además de la asignación manual de la posición X y Y de un diálogo, es posible utilizar la clase enum incorporada del diálogo para alinear el `Dialog`. Hay tres valores posibles: `TOP`, `CENTER` y `BOTTOM`, cada uno de los cuales se puede utilizar con el método `setAlignment()`. 

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Pantalla completa y puntos de quiebre {#full-screen-and-breakpoints}

El `Dialog` se puede configurar para entrar en modo de pantalla completa. Cuando se habilita la pantalla completa, el `Dialog` no se puede mover ni posicionar. Este modo se puede manipular con el atributo de punto de quiebre del `Dialog`. El punto de quiebre es una consulta de medios que determina cuándo el `Dialog` cambiará automáticamente al modo de pantalla completa. Cuando la consulta coincide, el `Dialog` cambia a pantalla completa; de lo contrario, se posiciona.

### Ancho automático <DocChip chip='since' label='26.00' /> {#auto-width}

Por defecto, el `Dialog` se estira para llenar el espacio horizontal disponible. Cuando se habilita el ancho automático a través de `setAutoWidth(true)`, el `Dialog` ajusta su tamaño según el ancho de su contenido.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Estilo {#styling}

### Temas {#themes}

Los componentes `Dialog` vienen con <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 temas discretos </JavadocLink> integrados para un estilo rápido sin el uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los botones para cambiar su apariencia y presentación visual. Ofrecen una manera rápida y consistente de personalizar la apariencia de los botones a lo largo de una aplicación. 

Si bien hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos son:

  - **Peligro**: Acciones con graves consecuencias, como borrar información completada o eliminar permanentemente una cuenta/datos es un buen caso de uso para diálogos con el tema Peligro.
  - **Por Defecto**: El tema predeterminado es apropiado para acciones a lo largo de una aplicación que no requieren atención especial y que son genéricas, como alternar una configuración.
  - **Primario**: Este tema es apropiado como un "llamado a la acción" principal en una página, como registrarse, guardar cambios o continuar a otra página.
  - **Éxito**: Los diálogos con el tema de Éxito son excelentes para visualizar la finalización exitosa de un elemento en una aplicación, como el envío de un formulario o la finalización de un proceso de registro. El tema de éxito puede aplicarse programáticamente una vez que se haya completado una acción exitosa.
  - **Advertencia**: Los diálogos de advertencia son útiles para indicar a los usuarios que están a punto de realizar una acción potencialmente arriesgada, como al navegar fuera de una página con cambios no guardados. Estas acciones a menudo son menos impactantes que aquellas que usarían el tema Peligro.
  - **Gris**: Bueno para acciones sutiles, como configuraciones menores o acciones que son más complementarias a una página y no parte de la funcionalidad principal.
  - **Información**: El tema de Información es una buena opción para proporcionar información aclaratoria adicional a un usuario cuando lo requiera.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
