---
sidebar_position: 15
title: ListBox
slug: listbox
description: >-
  Show a scrollable, always-visible list with the ListBox component, supporting
  single or multiple selection and keyboard navigation.
_i18n_hash: 0a9d307607ef33fdebb557f190abd7cf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

El componente `ListBox` muestra una lista desplazable de elementos que permanece visible sin necesidad de abrir un menú desplegable. Admite la selección única y múltiple, y resulta útil cuando los usuarios necesitan ver todas las opciones disponibles a la vez.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="List" />

1. **Asignación de roles de usuario**: En aplicaciones con control de acceso de usuarios, los administradores pueden usar un `ListBox` para asignar roles y permisos a los usuarios. Los usuarios se seleccionan de una lista, y los roles o permisos se asignan según su selección. Esto garantiza un acceso preciso y controlado a las distintas funciones y datos de la aplicación.

2. **Asignación de tareas de proyecto**: En el software de gestión de proyectos, los componentes `ListBox` son útiles para asignar tareas a los miembros del equipo. Los usuarios pueden seleccionar tareas de una lista y asignarlas a distintos miembros del equipo. Esto simplifica la delegación de tareas y garantiza que las responsabilidades estén claramente definidas dentro del equipo.

3. **Filtrado por varias categorías**: En una aplicación de búsqueda, los usuarios suelen necesitar filtrar los resultados según varios criterios. Un `ListBox` puede mostrar distintas opciones de filtrado, como
>- Características del producto
>- Rangos de precios
>- Marcas.

  Los usuarios pueden seleccionar elementos de cada categoría de filtro, lo que les permite ajustar los resultados de búsqueda y encontrar exactamente lo que buscan.

4. **Clasificación de contenido**: En los sistemas de gestión de contenido, los componentes `ListBox` ayudan a clasificar artículos, imágenes o archivos. Los usuarios pueden seleccionar una o más categorías para asociarlas al contenido, lo que facilita organizar y buscar elementos en el sistema.

## Opciones de selección {#selection-options}

De forma predeterminada, el cuadro de lista está configurado para permitir la selección de un solo elemento a la vez. Sin embargo, `ListBox` implementa la interfaz <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>, que se puede configurar con un método integrado que permite a los usuarios seleccionar varios elementos ***con la tecla <kbd>Shift</kbd>*** para seleccionar elementos contiguos y ***con la tecla <kbd>Ctrl</kbd> (Windows) o <kbd>Cmd</kbd> (Mac)*** para seleccionar varios elementos separados.

Usa la función <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> para cambiar esta propiedad. Este método acepta `SelectionMode.SINGLE` o `SelectionMode.MULTIPLE`.

:::info Comportamiento en dispositivos táctiles
En los dispositivos táctiles, cuando la selección múltiple está habilitada, los usuarios pueden seleccionar varios elementos sin mantener pulsada la tecla Shift.
:::

Además, se pueden usar las teclas de flecha para navegar por el `ListBox`. Si el `ListBox` tiene el foco, al pulsar una letra se seleccionará la opción que comienza con esa letra o, si hay varias opciones, se recorrerán las que comienzan con ella.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Estilos {#styling}

<TableBuilder name="ListBox" />

## Prácticas recomendadas {#best-practices}

Para garantizar una experiencia de usuario óptima al usar el componente `ChoiceBox`, ten en cuenta las siguientes prácticas recomendadas:

1. **Prioriza la jerarquía de la información**: Al usar un `ListBox`, asegúrate de que los elementos estén organizados en un orden lógico y jerárquico. Coloca las opciones más importantes y utilizadas con mayor frecuencia al principio de la lista. Así, los usuarios podrán encontrar lo que necesitan con más facilidad y sin tener que desplazarse demasiado.

2. **Limita la longitud de la lista**: Evita abrumar a los usuarios con un `ListBox` demasiado largo. Si hay muchos elementos para mostrar, considera implementar opciones de paginación, búsqueda o filtrado para ayudarles a encontrar elementos rápidamente. También puedes agrupar los elementos en categorías para reducir la longitud de la lista.

3. **Etiquetas claras y descriptivas**: Proporciona etiquetas claras y descriptivas para cada elemento del `ListBox`. Los usuarios deben poder entender el propósito de cada opción sin ambigüedades. Usa etiquetas concisas y significativas.

4. **Indicaciones de selección múltiple**: Si tu `ListBox` permite seleccionar varios elementos, proporciona indicaciones visuales o textuales para señalar que se pueden seleccionar varios elementos de la lista.

5. **Selección predeterminada**: Considera establecer una selección predeterminada para el `ListBox`, especialmente si una opción se usa más que las demás. Esto puede agilizar la experiencia del usuario al preseleccionar la opción más probable.
