---
title: CheckBox
sidebar_position: 20
_i18n_hash: 7946f6753a03194ecdcf7e20a7053012
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

Un `CheckBox` puede ser seleccionado o deseleccionado y muestra su estado actual como una marca de verificación. Los cuadros de verificación son útiles para alternar configuraciones individuales o permitir que los usuarios elijan múltiples opciones de un conjunto.

<!-- INTRO_END -->

## Usos {#usages}

El `CheckBox` es mejor utilizado en escenarios donde los usuarios necesitan hacer múltiples selecciones de una lista de opciones. Aquí hay algunos ejemplos de cuándo usar el `CheckBox`:

1. **Selección de Tareas o Características**: Los cuadros de verificación se utilizan comúnmente cuando los usuarios necesitan seleccionar múltiples tareas o características para realizar ciertas acciones o configuraciones.

2. **Configuraciones de Preferencia**: Las aplicaciones que involucran paneles de preferencia o configuraciones a menudo utilizan cuadros de verificación para permitir que los usuarios elijan múltiples opciones de un conjunto de elecciones. Esto es mejor para opciones que no son mutuamente excluyentes. Por ejemplo:

> - Habilitar o deshabilitar notificaciones
> - Elegir un tema de modo oscuro o claro
> - Seleccionar preferencias de notificación por correo electrónico

3. **Filtrado o Clasificación**: Un `CheckBox` se puede utilizar en aplicaciones que requieren que los usuarios seleccionen múltiples filtros o categorías, como filtrar resultados de búsqueda o seleccionar múltiples elementos para acciones posteriores.

4. **Entradas de Formulario**: Los cuadros de verificación son comúnmente utilizados en formularios para permitir que los usuarios seleccionen múltiples opciones o tomen decisiones binarias. Por ejemplo:
   > - Suscribirse a un boletín
   > - Aceptar términos y condiciones
   > - Seleccionar artículos para compra o reserva

## Texto y posicionamiento {#text-and-positioning}

Los cuadros de verificación pueden utilizar el método <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>, que se posicionará cerca del cuadro de verificación de acuerdo con la <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Posición</JavadocLink> incorporada.

Los cuadros de verificación tienen funcionalidad incorporada para establecer texto que se mostrará ya sea a la derecha o a la izquierda del cuadro. Por defecto, el texto se mostrará a la derecha del componente. La posición del texto es soportada por el uso de la enumeración <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Posición</JavadocLink>. Se muestran a continuación las dos configuraciones: <br/>

<ComponentDemo
path='/webforj/checkboxhorizontaltext'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java']}
height='200px'
/>

<br/>

## Indeterminismo {#indeterminism}

El componente `CheckBox` soporta el indeterminismo, que es un patrón de UI comúnmente usado en formularios y listas para indicar que un grupo de cuadros de verificación tiene una mezcla de estados seleccionados y no seleccionados. Este estado se representa mediante un tercer estado visual, que típicamente se muestra como un cuadrado lleno o un guion dentro del cuadro de verificación. Hay algunos casos de uso comunes asociados con el indeterminismo:

- **Seleccionar múltiples elementos**: El indeterminismo es útil cuando los usuarios necesitan seleccionar múltiples elementos de una lista o un conjunto de opciones. Permite a los usuarios indicar que desean seleccionar algunos, pero no todos, de los choix disponibles.

- **Datos jerárquicos**: El indeterminismo se puede emplear en escenarios donde hay una relación jerárquica entre los cuadros de verificación. Por ejemplo, al seleccionar categorías y subcategorías, el indeterminismo puede representar que algunas subcategorías están seleccionadas mientras que otras no, y el componente padre está en estado indeterminado.

<ComponentDemo
path='/webforj/checkboxindeterminate'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java']}
height='150px'
/>

## Estilo {#styling}

### Expansiones {#expanses}

Los siguientes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> valores de Expanses </JavadocLink> permiten un estilo rápido sin usar CSS. Las expansiones son soportadas por el uso de la clase de enumeración `Expanse`. A continuación se muestran las expansiones soportadas para el componente de cuadro de verificación: <br/>

<ComponentDemo
path='/webforj/checkboxexpanse'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java']}
height='150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al utilizar el componente `Checkbox`, considere las siguientes mejores prácticas:

1. **Etiquetar Claramente las Opciones**: Proporcione etiquetas claras y concisas para cada opción de `CheckBox` para describir con precisión la elección. Las etiquetas deben ser fáciles de entender y distinguir entre sí.

2. **Agrupar Cuadros de Verificación**: Agrupe cuadros de verificación relacionados para indicar su asociación. Esto ayuda a los usuarios a entender que se pueden seleccionar múltiples opciones dentro de un grupo específico.

3. **Proporcionar Selección Predeterminada**: Si es aplicable, considere proporcionar una selección predeterminada para los cuadros de verificación para guiar a los usuarios cuando se encuentran por primera vez con las opciones. La selección predeterminada debe alinearse con la opción más común o preferida.

4. **Indeterminismo**: Si un componente `CheckBox` padre tiene múltiples componentes que le pertenecen de tal manera que algunos pueden ser seleccionados y otros deseleccionados, use la propiedad indeterminada para mostrar que no todos los componentes `CheckBox` están seleccionados o deseleccionados.
