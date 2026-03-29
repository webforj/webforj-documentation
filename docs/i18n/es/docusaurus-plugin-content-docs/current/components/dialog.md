---
title: Dialog
sidebar_position: 30
_i18n_hash: 4896ea2a90b7c5155fe9ef291e69b2ad
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

El componente `Dialog` muestra una ventana emergente que se superpone a la vista actual, atrayendo la atención hacia contenido enfocado como formularios, confirmaciones o mensajes informativos.

<!-- INTRO_END -->

## Estructura del `Dialog` {#dialog-structure}

El `Dialog` se organiza en tres secciones: un encabezado, un área de contenido y un pie de página. Se pueden agregar componentes a cada sección utilizando `addToHeader()`, `addToContent()` y `addToFooter()`.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Usos {#usages}

1. **Retroalimentación y Confirmación del Usuario**: Los componentes `Dialog` se utilizan a menudo para proporcionar retroalimentación o pedir confirmación al usuario. Pueden mostrar diversas piezas importantes de retroalimentación al usuario, tales como:

  >- Mensajes de éxito 
  >- Alertas de error
  >- Confirmaciones de envíos

2. **Entrada de Formulario y Edición**: Puedes usar diálogos para recopilar la entrada del usuario o permitirles editar información de manera controlada y enfocada. Por ejemplo, un diálogo puede aparecer para editar detalles del perfil del usuario o completar un formulario de varios pasos.

3. **Información Contextual**: Mostrar información contextual adicional o herramientas en un diálogo puede ayudar a los usuarios a entender características o datos complejos. Los diálogos pueden proporcionar explicaciones profundas, gráficos o documentación de ayuda.

4. **Vistas Previas de Imágenes y Medios**: Cuando los usuarios necesitan ver piezas de medios, un `Dialog` se puede usar para mostrar vistas previas más grandes o galerías, como al interactuar con:
  >- Imágenes
  >- Videos
  >- Otros medios

## Fondo y difuminado {#backdrop-and-blur}

Al habilitar el atributo de fondo del componente `Dialog` de webforJ, se mostrará un fondo detrás del `Dialog`. Además, cuando se habilita, el atributo difuminado del Diálogo difuminará el fondo del `Dialog`. Modificar estas configuraciones puede ayudar a los usuarios al proporcionar profundidad, jerarquía visual y contexto, lo que lleva a una guía más clara para el usuario.

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

Los desarrolladores pueden elegir qué interacciones cierran el `Dialog` con `setCancelOnEscKey()` y `setCancelOnOutsideClick()`. Además, el método `setClosable()` puede prevenir o permitir tanto presionar la tecla <kbd>ESC</kbd> como hacer clic fuera del `Dialog` para cerrar el componente.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Autofocus {#auto-focus}

Cuando está habilitado, el autofocus dará automáticamente foco al primer elemento dentro del diálogo que puede ser enfocado. Esto es útil para ayudar a dirigir la atención de los usuarios y es personalizable a través del método `setAutoFocus()`.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Arrastrable {#draggable}

El `Dialog` tiene funcionalidad incorporada para ser arrastrable, permitiendo al usuario reubicar la ventana del `Dialog` haciendo clic y arrastrando. La posición del `Dialog` puede ser manipulada desde cualquiera de los campos dentro de él: el encabezado, contenido o pie de página.

### Ajuste a los bordes {#snap-to-edge}
También es posible calibrar este comportamiento para ajustarse al borde de la pantalla, lo que significa que el `Dialog` se alineará automáticamente con el borde de la pantalla al soltarse después de su arrastre y caída. El ajuste se puede cambiar a través del método `setSnapToEdge()`. El `setSnapThreshold()` toma un número de píxeles, que establecerá cuán lejos debe estar el `Dialog` de los lados de la pantalla antes de que se ajuste automáticamente a los bordes.  

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Posicionamiento {#positioning}

La posición del diálogo puede ser manipulada usando los métodos incorporados `setPosx()` y `setPosy()`. Estos métodos toman un argumento de cadena que puede representar cualquier unidad CSS aplicable de longitud, como píxeles o altura/anchura de vista. Una lista de estas medidas [se puede encontrar en este enlace](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Alineación vertical {#vertical-alignment}

Además de la asignación manual de la posición X y Y de un diálogo, es posible utilizar la clase enum incorporada del diálogo para alinear el `Dialog`. Hay tres valores posibles, `TOP`, `CENTER` y `BOTTOM`, cada uno de los cuales puede usarse con el método `setAlignment()`. 

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Pantalla completa y puntos de ruptura {#full-screen-and-breakpoints}

El `Dialog` puede ser configurado para entrar en modo de pantalla completa. Cuando la pantalla completa está habilitada, el `Dialog` no puede ser movido o posicionado. Este modo puede ser manipulado con el atributo de punto de ruptura del `Dialog`. El punto de ruptura es una consulta de medios que determina cuándo el `Dialog` cambiará automáticamente a modo de pantalla completa. Cuando la consulta coincide, el `Dialog` cambia a pantalla completa; de lo contrario, está posicionado.

## Estilo {#styling}

### Temas {#themes}

Los componentes `Dialog` vienen con <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 temas discretos </JavadocLink> incorporados para un estilo rápido sin el uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los botones para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar la apariencia de los botones en toda una aplicación. 

Si bien hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos de uso son:

  - **Peligro**: Acciones con consecuencias severas, como borrar información completada o eliminar permanentemente una cuenta/datos, son un buen caso de uso para diálogos con el tema Peligro.
  - **Predeterminado**: El tema predeterminado es apropiado para acciones en toda una aplicación que no requieren atención especial y que son genéricas, como alternar una configuración.
  - **Primario**: Este tema es apropiado como una principal "llamada a la acción" en una página, como registrarse, guardar cambios o continuar a otra página.
  - **Éxito**: Los diálogos con tema de éxito son excelentes para visualizar la finalización exitosa de un elemento en una aplicación, como la presentación de un formulario o la finalización de un proceso de registro. El tema de éxito puede aplicarse programáticamente una vez que una acción exitosa ha sido completada.
  - **Advertencia**: Los diálogos de advertencia son útiles para indicar a los usuarios que están a punto de realizar una acción potencialmente arriesgada, como al navegar fuera de una página con cambios no guardados. Estas acciones suelen ser menos impactantes que aquellas que utilizarían el tema Peligro.
  - **Gris**: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página y no forman parte de la funcionalidad principal.
  - **Información**: El tema de Información es una buena opción para proporcionar información adicional aclaratoria a un usuario cuando se le empuja.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
