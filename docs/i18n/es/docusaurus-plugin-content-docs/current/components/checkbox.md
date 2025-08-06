---
title: CheckBox
sidebar_position: 20
_i18n_hash: 0c55e1e2b7f92aa8f1f65151bfa3e096
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

La clase `CheckBox` crea un componente que puede ser seleccionado o deseleccionado, y que muestra su estado al usuario. Al hacer clic, aparece una marca de verificación dentro de la casilla, para indicar una elección afirmativa (encendido). Al hacer clic nuevamente, la marca de verificación desaparece, indicando una elección negativa (apagado).

Al proporcionar una indicación visual clara y sencilla del estado de selección, las casillas de verificación mejoran la interacción del usuario y la toma de decisiones, convirtiéndolas en un elemento esencial en las interfaces de usuario modernas.

## Usos {#usages}

El `CheckBox` se utiliza mejor en escenarios donde los usuarios necesitan hacer múltiples selecciones de una lista de opciones. Aquí hay algunos ejemplos de cuándo usar el `CheckBox`:

1. **Selección de Tareas o Funciones**: Las casillas de verificación se utilizan comúnmente cuando los usuarios necesitan seleccionar múltiples tareas o funciones para realizar ciertas acciones o configuraciones.

2. **Configuraciones de Preferencias**: Las aplicaciones que implican paneles de preferencias o configuraciones a menudo utilizan casillas de verificación para permitir a los usuarios elegir múltiples opciones de un conjunto de opciones. Esto es mejor para opciones que no son mutuamente excluyentes. Por ejemplo:

> - Habilitar o deshabilitar notificaciones
> - Elegir un tema de modo oscuro o claro
> - Seleccionar preferencias de notificación por correo electrónico

3. **Filtrado o Clasificación**: Un `CheckBox` se puede utilizar en aplicaciones que requieren que los usuarios seleccionen múltiples filtros o categorías, como filtrar resultados de búsqueda o seleccionar múltiples elementos para acciones posteriores.

4. **Entradas de Formularios**: Las casillas de verificación son comúnmente utilizadas en formularios para permitir a los usuarios seleccionar múltiples opciones o hacer elecciones binarias. Por ejemplo:
   > - Suscribirse a un boletín
   > - Aceptar términos y condiciones
   > - Seleccionar artículos para compra o reserva

## Texto y posicionamiento {#text-and-positioning}

Las casillas de verificación pueden utilizar el método <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>, que se posicionará cerca de la casilla de verificación de acuerdo con la <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Posición</JavadocLink> incorporada.

Las casillas de verificación tienen funcionalidad incorporada para establecer texto que se mostrará a la derecha o a la izquierda de la casilla. Por defecto, el texto se mostrará a la derecha del componente. El posicionamiento del texto se admite mediante el uso de la enumeración <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>. Se muestran a continuación las dos configuraciones: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indeterminación {#indeterminism}

El componente `CheckBox` admite la indeterminación, que es un patrón de interfaz de usuario comúnmente utilizado en formularios y listas para indicar que un grupo de casillas de verificación tiene una mezcla de estados marcados y desmarcados. Este estado se representa mediante un tercer estado visual, que se muestra típicamente como un cuadrado relleno o un guion dentro de la casilla de verificación. Hay algunos casos de uso comunes asociados con la indeterminación:

- **Seleccionando múltiples elementos**: La indeterminación es útil cuando los usuarios necesitan seleccionar múltiples elementos de una lista o un conjunto de opciones. Permite a los usuarios indicar que desean seleccionar algunos, pero no todos, de las opciones disponibles.

- **Datos jerárquicos**: La indeterminación se puede emplear en escenarios donde hay una relación jerárquica entre las casillas de verificación. Por ejemplo, al seleccionar categorías y subcategorías, la indeterminación puede representar que algunas subcategorías están seleccionadas mientras que otras no lo están, y el componente padre se encuentra en estado indeterminado.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Estilizado {#styling}

### Expansiones {#expanses}

Los siguientes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> valores de Expanses </JavadocLink> permiten un estilizado rápido sin utilizar CSS. Las expansiones son compatibles mediante el uso de la clase de enumeración `Expanse`. A continuación se muestran las expansiones admitidas para el componente de casilla de verificación: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al usar el componente `Checkbox`, considere las siguientes mejores prácticas:

1. **Etiquetar Opciones Claramente**: Proporcione etiquetas claras y concisas para cada opción del `CheckBox` para describir con precisión la elección. Las etiquetas deben ser fáciles de entender y distinguir entre sí.

2. **Agrupar Casillas de Verificación**: Agrupe las casillas de verificación relacionadas para indicar su asociación. Esto ayuda a los usuarios a entender que se pueden seleccionar múltiples opciones dentro de un grupo específico.

3. **Proporcionar Selección Predeterminada**: Si es aplicable, considere proporcionar una selección predeterminada para las casillas de verificación para guiar a los usuarios cuando se encuentran por primera vez con las opciones. La selección predeterminada debe alinearse con la elección más común o preferida.

4. **Indeterminación**: Si un componente de `CheckBox` padre tiene múltiples componentes que le pertenecen de tal manera que algunos se pueden marcar y otros desmarcar, utilice la propiedad indeterminada para mostrar que no todos los componentes de `CheckBox` están marcados o desmarcados.
