---
title: CheckBox
sidebar_position: 20
_i18n_hash: e5ace9c598a0892cfa456f376035c87a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

Un `CheckBox` se puede seleccionar o deseleccionar y muestra su estado actual como una marca de verificación. Los checkboxes son útiles para alternar configuraciones individuales o permitir que los usuarios elijan múltiples opciones de un conjunto.

<!-- INTRO_END -->

## Usos {#usages}

El `CheckBox` se utiliza mejor en situaciones donde los usuarios necesitan hacer múltiples selecciones de una lista de opciones. Aquí hay algunos ejemplos de cuándo usar el `CheckBox`:

1. **Selección de Tareas o Características**: Los checkboxes se utilizan comúnmente cuando los usuarios necesitan seleccionar múltiples tareas o características para realizar ciertas acciones o configuraciones.

2. **Configuraciones de Preferencia**: Las aplicaciones que implican paneles de preferencias o configuraciones a menudo usan checkboxes para permitir que los usuarios elijan múltiples opciones de un conjunto de elecciones. Esto es mejor para opciones que no son mutuamente excluyentes. Por ejemplo:

> - Activar o desactivar notificaciones
> - Elegir un tema de modo oscuro o claro
> - Seleccionar preferencias de notificación por correo electrónico

3. **Filtrado o Clasificación**: Un `CheckBox` se puede usar en aplicaciones que requieren que los usuarios seleccionen múltiples filtros o categorías, como filtrar resultados de búsqueda o seleccionar múltiples elementos para acciones adicionales.

4. **Entradas de Formularios**: Los checkboxes se utilizan comúnmente en formularios para permitir que los usuarios seleccionen múltiples opciones o realicen elecciones binarias. Por ejemplo:
   > - Suscribirse a un boletín
   > - Aceptar términos y condiciones
   > - Seleccionar artículos para compra o reserva

## Texto y posicionamiento {#text-and-positioning}

Los checkboxes pueden utilizar el <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> método, que se posicionará cerca del checkbox de acuerdo con la posición incorporada <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>.

Los checkboxes tienen funcionalidad incorporada para establecer texto que se mostrará ya sea a la derecha o a la izquierda de la caja. Por defecto, el texto se mostrará a la derecha del componente. La posición del texto es compatible con el uso del enum <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>. A continuación se muestran las dos configuraciones: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indeterminismo {#indeterminism}

El componente `CheckBox` soporta el indeterminismo, un patrón de UI comúnmente utilizado en formularios y listas para indicar que un grupo de checkboxes tiene una mezcla de estados marcados y desmarcados. Este estado se representa por un tercer estado visual, típicamente mostrado como un cuadrado con relleno o un guion dentro del checkbox. Hay algunos casos de uso comunes asociados con el indeterminismo:

- **Seleccionando múltiples elementos**: El indeterminismo es útil cuando los usuarios necesitan seleccionar múltiples elementos de una lista o un conjunto de opciones. Permite a los usuarios indicar que desean seleccionar algunos, pero no todos, de las opciones disponibles.

- **Datos jerárquicos**: El indeterminismo se puede emplear en escenarios donde existe una relación jerárquica entre los CheckBoxes. Por ejemplo, al seleccionar categorías y subcategorías, el indeterminismo puede representar que algunas subcategorías están seleccionadas mientras que otras no, y el componente padre está en estado indeterminado.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Estilo {#styling}

### Expansiones {#expanses}

Los siguientes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> valores de Expanses </JavadocLink> permiten un estilo rápido sin usar CSS. Las expanses son compatibles con el uso de la clase enum `Expanse`. A continuación se presentan las expanses soportadas para el componente de checkbox: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima del usuario al utilizar el componente `Checkbox`, considere las siguientes mejores prácticas:

1. **Etiquetas Claras para las Opciones**: Proporcione etiquetas claras y concisas para cada opción de `CheckBox` para describir con precisión la elección. Las etiquetas deben ser fáciles de entender y distinguir entre sí.

2. **Agrupar CheckBoxes**: Agrupe checkboxes relacionados para indicar su asociación. Esto ayuda a los usuarios a comprender que se pueden seleccionar múltiples opciones dentro de un grupo específico.

3. **Proporcionar Selección Predeterminada**: Si es aplicable, considere proporcionar una selección predeterminada para los CheckBoxes para guiar a los usuarios cuando se encuentren por primera vez con las opciones. La selección predeterminada debe alinearse con la opción más común o preferida.

4. **Indeterminismo**: Si un componente padre `CheckBox` tiene múltiples componentes que le pertenecen de tal manera que algunos pueden ser marcados y otros desmarcados, use la propiedad indeterminada para mostrar que no todos los componentes de `CheckBox` están marcados o desmarcados.
