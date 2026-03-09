---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 9bf0e23b101252295342c62ce6a0dee9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

El componente `ListBox` muestra una lista desplazable de elementos que permanece visible sin necesidad de abrir un desplegable. Soporta tanto la selección única como múltiple, y funciona bien cuando los usuarios necesitan ver todas las opciones disponibles al mismo tiempo.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="List" />

1. **Asignación de Roles de Usuario**: En aplicaciones con control de acceso de usuarios, los administradores pueden utilizar un `ListBox` para asignar roles y permisos a los usuarios. Los usuarios se seleccionan de una lista, y los roles o permisos se asignan según su selección. Esto asegura un acceso preciso y controlado a diferentes características y datos dentro de la aplicación.

2. **Asignación de Tareas de Proyecto**: En software de gestión de proyectos, los componentes `ListBox` son útiles para asignar tareas a los miembros del equipo. Los usuarios pueden seleccionar tareas de una lista y asignarlas a diferentes miembros del equipo. Esto simplifica la delegación de tareas y asegura que las responsabilidades estén claramente definidas dentro del equipo.

3. **Filtrado por Múltiples Categorías**: En una aplicación de búsqueda, los usuarios a menudo necesitan filtrar los resultados de búsqueda según múltiples criterios. Un `ListBox` puede mostrar varias opciones de filtrado, como 
>- Características del producto
>- Rangos de precios
>- Marcas. 

  Los usuarios pueden seleccionar elementos de cada categoría de filtro, lo que les permite refinar los resultados de búsqueda y encontrar exactamente lo que están buscando.

4. **Categorización de Contenido**: En sistemas de gestión de contenido, los componentes `ListBox` ayudan a categorizar artículos, imágenes o archivos. Los usuarios pueden seleccionar una o más categorías para asociar con su contenido, facilitando la organización y búsqueda de elementos de contenido en el sistema.

## Opciones de Selección {#selection-options}

Por defecto, el cuadro de lista está configurado para permitir la selección de un solo elemento a la vez. Sin embargo, el `ListBox` implementa la interfaz <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>, que se puede configurar con un método integrado que permite a los usuarios seleccionar múltiples elementos ***usando la tecla `Shift`*** para selección continua y ***la tecla `Control` (Windows) o `Command` (Mac)*** para la selección de múltiples elementos separados.

Utilice la función <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> para cambiar esta propiedad. Este método acepta `SelectionMode.SINGLE` o `SelectionMode.MULTIPLE`.

:::info Comportamiento en dispositivos táctiles
En dispositivos táctiles, cuando la selección múltiple está habilitada, los usuarios pueden seleccionar múltiples elementos sin mantener presionada la tecla de mayúsculas.
:::

Además, se pueden utilizar las teclas de flecha para navegar por el `ListBox`, y al escribir una letra mientras el `ListBox` tiene el foco, se seleccionará la opción que comienza con esa letra, o se recorrerán las opciones que comienzan con esa letra si existen múltiples opciones.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Estilo {#styling}

<TableBuilder name="ListBox" />

## Mejores Prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al utilizar el componente `ChoiceBox`, considere las siguientes mejores prácticas:

1. **Dar prioridad a la jerarquía de la información**: Al usar un `ListBox`, asegúrese de que los elementos estén organizados en un orden lógico y jerárquico. Coloque las opciones más importantes y comúnmente utilizadas en la parte superior de la lista. Esto facilita a los usuarios encontrar lo que necesitan sin necesidad de desplazarse excesivamente.

2. **Limitar la longitud de la lista**: Evite abrumar a los usuarios con un `ListBox` excesivamente largo. Si hay un gran número de elementos para mostrar, considere implementar paginación, búsqueda u opciones de filtrado para ayudar a los usuarios a localizar rápidamente los elementos. Alternativamente, puede agrupar elementos en categorías para reducir la longitud de la lista.

3. **Etiquetas claras y descriptivas**: Proporcione etiquetas claras y descriptivas para cada elemento en el `ListBox`. Los usuarios deben poder entender el propósito de cada opción sin ambigüedad. Utilice etiquetas de elementos concisas y significativas.

4. **Retroalimentación de múltiples selecciones**: Si su `ListBox` permite múltiples selecciones, proporcione retroalimentación visual o textual indicando que se pueden seleccionar múltiples elementos de la lista.

5. **Selección predeterminada**: Considere establecer una selección predeterminada para el `ListBox`, especialmente si una opción se utiliza más comúnmente que otras. Esto puede simplificar la experiencia del usuario al preseleccionar la opción más probable.
