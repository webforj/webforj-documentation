---
title: CheckBox
sidebar_position: 20
_i18n_hash: c2be55222401962b275faf28ff6ddba3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

La clase `CheckBox` crea un componente que puede ser seleccionado o deseleccionado, y que muestra su estado al usuario. Cuando se hace clic, aparece una marca de verificación dentro de la caja, para indicar una elección afirmativa (activado). Al hacer clic nuevamente, la marca de verificación desaparece, indicando una elección negativa (desactivado).

Al proporcionar una indicación visual clara y sencilla del estado de selección, las casillas de verificación mejoran la interacción del usuario y la toma de decisiones, convirtiéndolas en un elemento esencial en las interfaces de usuario modernas.

## Usos {#usages}

El `CheckBox` se utiliza mejor en escenarios donde los usuarios necesitan realizar múltiples selecciones de una lista de opciones. Aquí hay algunos ejemplos de cuándo usar el `CheckBox`:

1. **Selección de tareas o características**: Las casillas de verificación se utilizan comúnmente cuando los usuarios necesitan seleccionar múltiples tareas o características para realizar ciertas acciones o configuraciones.

2. **Configuraciones de preferencias**: Las aplicaciones que involucran paneles de preferencias o configuraciones a menudo utilizan casillas de verificación para permitir que los usuarios elijan múltiples opciones de un conjunto de elecciones. Esto es mejor para opciones que no son mutuamente exclusivas. Por ejemplo:

> - Habilitar o deshabilitar notificaciones
> - Elegir un tema de modo oscuro o claro
> - Seleccionar preferencias de notificación por correo electrónico

3. **Filtrado o clasificación**: Un `CheckBox` se puede usar en aplicaciones que requieren que los usuarios seleccionen múltiples filtros o categorías, como filtrar resultados de búsqueda o seleccionar múltiples elementos para acciones adicionales.

4. **Entradas en formularios**: Las casillas de verificación se usan comúnmente en formularios para permitir que los usuarios seleccionen múltiples opciones o realicen elecciones binarias. Por ejemplo:
   > - Suscribirse a un boletín informativo
   > - Aceptar términos y condiciones
   > - Seleccionar elementos para compra o reserva

## Texto y posicionamiento {#text-and-positioning}

Las casillas de verificación pueden utilizar el método <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>, que se posicionará cerca de la casilla de verificación de acuerdo con la <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Posición</JavadocLink> incorporada.

Las casillas de verificación tienen funcionalidad incorporada para establecer el texto que se mostrará a la derecha o a la izquierda de la caja. Por defecto, el texto se mostrará a la derecha del componente. La posicionamiento del texto es compatible mediante el uso del enum <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>. A continuación se muestran las dos configuraciones: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indeterminismo {#indeterminism}

El componente `CheckBox` admite el indeterminismo, que es un patrón de UI comúnmente utilizado en formularios y listas para indicar que un grupo de casillas de verificación tiene una mezcla de estados verificados y no verificados. Este estado se representa mediante un tercer estado visual, que generalmente se muestra como un cuadrado lleno o un guion dentro de la casilla de verificación. Hay algunos casos de uso comunes asociados con el indeterminismo:

- **Seleccionar múltiples elementos**: El indeterminismo es útil cuando los usuarios necesitan seleccionar múltiples elementos de una lista o un conjunto de opciones. Permite a los usuarios indicar que desean seleccionar algunos, pero no todos, de los elementos disponibles.

- **Datos jerárquicos**: El indeterminismo se puede emplear en escenarios donde existe una relación jerárquica entre las casillas de verificación. Por ejemplo, al seleccionar categorías y subcategorías, el indeterminismo puede representar que algunas subcategorías están seleccionadas mientras que otras no, y el componente padre está en estado indeterminado.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Estilo {#styling}

### Expansiones {#expanses}

Los siguientes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> valores de Expanses </JavadocLink> permiten un estilo rápido sin usar CSS. 
Las expansiones son compatibles mediante el uso de la clase enum `Expanse`. A continuación se presentan las expansiones compatibles para el componente de casilla de verificación: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia de usuario óptima al usar el componente `Checkbox`, considere las siguientes mejores prácticas:

1. **Etiquetar claramente las opciones**: Proporcione etiquetas claras y concisas para cada opción de `CheckBox` para describir con precisión la elección. Las etiquetas deben ser fáciles de entender y distinguir entre sí.

2. **Agrupar las casillas de verificación**: Agrupe las casillas de verificación relacionadas para indicar su asociación. Esto ayuda a los usuarios a comprender que múltiples opciones pueden ser seleccionadas dentro de un grupo específico.

3. **Proporcionar selección predeterminada**: Si es aplicable, considere proporcionar una selección predeterminada para las casillas de verificación para guiar a los usuarios cuando se encuentren con las opciones por primera vez. La selección predeterminada debe alinearse con la elección más común o preferida.

4. **Indeterminismo**: Si un componente `CheckBox` padre tiene múltiples componentes que le pertenecen de tal manera que algunos pueden ser seleccionados y otros no seleccionados, utilice la propiedad indeterminada para mostrar que no todos los componentes `CheckBox` están seleccionados o no seleccionados.
