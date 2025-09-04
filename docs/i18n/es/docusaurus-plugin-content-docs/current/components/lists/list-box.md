---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 7bd48c55ca5607255c1d6503c500a25d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

<ParentLink parent="List" />

El componente `ListBox` es un elemento de interfaz de usuario diseñado para mostrar una lista desplazable de objetos y permite a los usuarios seleccionar uno o varios elementos de la lista. Los usuarios también pueden interactuar con el `ListBox` utilizando las teclas de flecha. 

## Usos {#usages}

1. **Asignación de Roles de Usuario**: En aplicaciones con control de acceso de usuarios, los administradores pueden usar un `ListBox` para asignar roles y permisos a los usuarios. Los usuarios se seleccionan de una lista y los roles o permisos se asignan según su selección. Esto garantiza un acceso preciso y controlado a diferentes características y datos dentro de la aplicación.

2. **Asignación de Tareas de Proyecto**: En software de gestión de proyectos, los componentes `ListBox` son útiles para asignar tareas a los miembros del equipo. Los usuarios pueden seleccionar tareas de una lista y asignarlas a diferentes miembros del equipo. Esto simplifica la delegación de tareas y asegura que las responsabilidades estén claramente definidas dentro del equipo.

3. **Filtrado Multi-Categoría**: En una aplicación de búsqueda, los usuarios a menudo necesitan filtrar los resultados de búsqueda en función de múltiples criterios. Un `ListBox` puede mostrar varias opciones de filtro, como 
>- Características del producto
>- Rangos de precios
>- Marcas. 

  Los usuarios pueden seleccionar elementos de cada categoría de filtro, lo que les permite refinar los resultados de búsqueda y encontrar exactamente lo que buscan.

4. **Categorizar Contenidos**: En sistemas de gestión de contenidos, los componentes `ListBox` ayudan a categorizar artículos, imágenes o archivos. Los usuarios pueden seleccionar una o más categorías para asociarlas con su contenido, facilitando la organización y búsqueda de elementos de contenido en el sistema.

## Opciones de Selección {#selection-options}

Por defecto, el cuadro de lista está configurado para permitir la selección de un solo elemento a la vez. Sin embargo, el `ListBox` implementa la <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>interfaz MultipleSelectableList</JavadocLink>, que se puede configurar con un método integrado que permite a los usuarios seleccionar múltiples elementos ***usando la tecla `Shift`*** para la selección de entrada contigua y ***la tecla `Control` (Windows) o `Command` (Mac)*** para la selección de varios elementos separados. 

Utiliza la función <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> para cambiar esta propiedad. Este método acepta `SelectionMode.SINGLE` o `SelectionMode.MULTIPLE`.

:::info Comportamiento en dispositivos táctiles
En dispositivos táctiles, cuando se habilita la selección múltiple, los usuarios pueden seleccionar múltiples elementos sin mantener presionada la tecla shift.
:::

Además, se pueden utilizar las teclas de flecha para navegar por el `ListBox`, y al teclear una letra mientras el `ListBox` tiene el foco, se seleccionará la opción que comienza con esa letra, o se ciclará a través de las opciones que comienzan con esa letra si existen múltiples opciones.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Estilos {#styling}

<TableBuilder name="ListBox" />

## Mejores Prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al utilizar el componente `ChoiceBox`, considera las siguientes mejores prácticas:

1. **Priorizar la Jerarquía de Información**: Al usar un `ListBox`, asegúrate de que los elementos estén organizados en un orden lógico y jerárquico. Coloca las opciones más importantes y de uso común en la parte superior de la lista. Esto facilita que los usuarios encuentren lo que necesitan sin desplazarse excesivamente.

2. **Limitar la Longitud de la Lista**: Evita abrumar a los usuarios con un `ListBox` excesivamente largo. Si hay una gran cantidad de elementos para mostrar, considera implementar paginación, búsqueda o opciones de filtrado para ayudar a los usuarios a localizar elementos rápidamente. Alternativamente, puedes agrupar elementos en categorías para reducir la longitud de la lista.

3. **Etiquetas Claras y Descriptivas**: Proporciona etiquetas claras y descriptivas para cada elemento en el `ListBox`. Los usuarios deberían poder entender el propósito de cada opción sin ambigüedad. Usa etiquetas de elementos concisas y significativas.

4. **Retroalimentación de Selección Múltiple**: Si tu `ListBox` permite selecciones múltiples, proporciona retroalimentación visual o textual que indique que se pueden seleccionar múltiples elementos de la lista.

5. **Selección Predeterminada**: Considera establecer una selección predeterminada para el `ListBox`, especialmente si una opción es más utilizada que las demás. Esto puede agilizar la experiencia del usuario al preseleccionar la opción más probable.
