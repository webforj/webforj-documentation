---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: d0112ef19b8ef7b0b2621af5c500a6c9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

<ParentLink parent="List" />

El componente `ComboBox` es un elemento de interfaz de usuario diseñado para presentar a los usuarios una lista de opciones o elecciones, así como un campo para ingresar sus propios valores personalizados. Los usuarios pueden seleccionar una opción única de esta lista, generalmente haciendo clic en el `ComboBox`, lo que activa la visualización de una lista desplegable que contiene las opciones disponibles, o escribir un valor personalizado. Los usuarios también pueden interactuar con el `ComboBox` utilizando las teclas de flecha. Cuando un usuario hace una selección, la opción elegida se muestra en el `ComboBox`. 

## Usos {#usages}

El componente ComboBox es un elemento de entrada versátil que combina las características tanto de una lista desplegable como de un campo de entrada de texto. Permite a los usuarios seleccionar elementos de una lista predefinida o ingresar valores personalizados según sea necesario. Esta sección explora los usos comunes del componente ComboBox en varios escenarios:

1. **Búsqueda y entrada de productos**: Utiliza un ComboBox para implementar una función de búsqueda y entrada de productos. Los usuarios pueden seleccionar un producto de una lista predefinida o escribir un nombre de producto personalizado. Esto es útil para aplicaciones como sitios de comercio electrónico donde los productos son vastos y diversos.

2. **Selección y entrada de etiquetas**: En aplicaciones que involucren etiquetado de contenido, un ComboBox puede ser una excelente opción. Los usuarios pueden seleccionar de una lista de etiquetas existentes o agregar etiquetas personalizadas escribiéndolas. Esto es útil para organizar y categorizar contenido. Ejemplos de tales etiquetas incluyen:
    >- Etiquetas de proyecto: En una herramienta de gestión de proyectos, los usuarios pueden seleccionar etiquetas o tags (por ejemplo, "Urgente", "En Progreso", "Completado") para categorizar tareas o proyectos, y pueden crear etiquetas personalizadas según sea necesario.
    >- Ingredientes de recetas: En una aplicación de cocina o recetas, los usuarios pueden seleccionar ingredientes de una lista (por ejemplo, "Tomates", "Cebollas", "Pollo") o agregar sus propios ingredientes para recetas personalizadas.
    >- Etiquetas de ubicación: En una aplicación de mapeo o geolocalización, los usuarios pueden elegir etiquetas de ubicación predefinidas (por ejemplo, "Playa", "Ciudad", "Parque") o crear etiquetas personalizadas para marcar lugares específicos en un mapa.

3. **Entrada de datos con valores sugeridos**: En formularios de entrada de datos, se puede utilizar un ComboBox para acelerar la entrada al proporcionar una lista de valores sugeridos basada en la entrada del usuario. Esto ayuda a los usuarios a ingresar datos de manera precisa y eficiente.

    :::tip
    El `ComboBox` debe utilizarse cuando se permite a los usuarios ingresar valores personalizados. Si solo se desean valores preestablecidos, utilice un [`ChoiceBox`](./choice-box.md) en su lugar.
    :::

## Valor personalizado {#custom-value}

Cambiar la propiedad de valor personalizado permite el control sobre si un usuario puede o no cambiar el valor en el campo de entrada del componente `ComboBox`. Si es `true`, que es el valor predeterminado, el usuario puede cambiar el valor. Si se establece en `false`, el usuario no podrá cambiar el valor. Esto se puede establecer utilizando el método <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Placeholder {#placeholder}

Se puede establecer un placeholder para un `ComboBox` que se mostrará en el campo de texto del componente cuando esté vacío para invitar a los usuarios a realizar la entrada deseada en el campo. Esto se puede hacer utilizando el método <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Tipo de dropdown {#dropdown-type}

El uso del método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> asignará un valor al atributo `type` de un `ComboBox`, y un valor correspondiente para el atributo `data-dropdown-for` en la lista desplegable del `ComboBox`. Esto es útil para el estilo, ya que la lista desplegable se retira de su posición actual en el DOM y se reubica al final del cuerpo de la página cuando se abre.

Este desapego crea una situación donde targeting directamente la lista desplegable utilizando CSS o selectores de partes sombra del componente padre se vuelve un desafío, a menos que se utilice el atributo tipo de dropdown.

En la demostración a continuación, el tipo de Dropdown se establece y se utiliza en el archivo CSS para seleccionar la lista desplegable y cambiar el color de fondo.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Conteo máximo de filas {#max-row-count}

Por defecto, el número de filas mostradas en la lista desplegable de un `ComboBox` se incrementará para ajustar el contenido. Sin embargo, el uso del método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permite controlar cuántos elementos se muestran.

:::caution
Usar un número que sea menor o igual a 0 resultará en la anulación de esta propiedad.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Apertura y cierre {#opening-and-closing}

La visibilidad de las opciones para un `ComboBox` se puede controlar programáticamente con los métodos `open()` y `close()`. Estos métodos permiten mostrar la lista de opciones para selección o ocultarla según sea necesario, proporcionando una mayor flexibilidad en la gestión del comportamiento de un `ComboBox`.

Además, webforJ tiene oyentes de eventos para cuando el `ComboBox` se cierra y cuando se abre, brindando más control para activar acciones específicas.

```Java
//Enfocar o abrir el siguiente componente en un formulario
ComboBox university = new ComboBox("Universidad");
ComboBox major = new ComboBox("Carrera");
Button submit = new Button("Enviar");

//... Agregar listas de universidades y carreras

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## Dimensiones de apertura {#opening-dimensions}

El componente `ComboBox` tiene métodos que permiten la manipulación de las dimensiones de la lista desplegable. La **altura máxima** y **ancho mínimo** de la lista desplegable se pueden establecer utilizando los métodos <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> y <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivamente. 

:::tip
Pasar un valor `String` a cualquiera de estos métodos permitirá que se apliquen [cualquier unidad CSS válida](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), como píxeles, dimensiones de vista o otras reglas válidas. Pasar un `int` establecerá el valor pasado en píxeles.
:::

## Resaltado {#highlighting}

Al trabajar con un `ComboBox`, puedes personalizar cuándo se resalta el texto basado en cómo el componente recibe enfoque. Esta función puede reducir errores de entrada cuando los usuarios están completando formularios y mejorar la experiencia general de navegación. Cambia el comportamiento de resaltado utilizando el método `setHighlightOnFocus()` con uno de los enumerados `HasHighlightOnFocus.Behavior` integrados:

- `ALL`
Los contenidos del componente están siempre automáticamente resaltados cuando el componente obtiene enfoque.
- `FOCUS`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque bajo control del programa.
- `FOCUS_OR_KEY`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque bajo control del programa o al tabular hacia él.
- `FOCUS_OR_MOUSE`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque bajo control del programa o al hacer clic en él con el ratón.
- `KEY`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque al tabular hacia él.
- `KEY_MOUSE`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque al tabular hacia él o haciendo clic en él con el ratón.
- `MOUSE`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque al hacer clic en él con el ratón.
- `NONE`
Los contenidos del componente nunca se resaltan automáticamente cuando el componente obtiene enfoque.

:::note
Si el contenido fue resaltado al perder el enfoque, se resaltará nuevamente al recuperar el enfoque, independientemente del comportamiento establecido.
:::

## Prefijo y sufijo {#prefix-and-suffix}

Los slots proporcionan opciones flexibles para mejorar la capacidad de un `ComboBox`. Puedes tener íconos, etiquetas, spinners de carga, capacidad de limpiar/restablecer, imágenes de avatar/perfil, y otros componentes beneficiosos anidados dentro de un `ComboBox` para aclarar aún más el significado a los usuarios. El `ComboBox` tiene dos slots: los slots de `prefijo` y `sufijo`. Utiliza los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de las opciones dentro de un `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Estilo {#styling}

<TableBuilder name="ComboBox" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima para el usuario al usar el componente `ComboBox`, considera las siguientes mejores prácticas:

1. **Precargar valores comunes**: Si hay elementos comunes o utilizados frecuentemente, precárgalos en la lista del `ComboBox`. Esto acelera la selección de los elementos comúnmente elegidos y fomenta la consistencia.

2. **Etiquetas amigables para el usuario**: Asegúrate de que las etiquetas mostradas para cada opción sean amigables para el usuario y autoexplicativas. Asegúrate de que los usuarios puedan comprender fácilmente el propósito de cada elección.

3. **Validación**: Implementa validación de entrada para manejar entradas personalizadas. Verifica la precisión y consistencia de los datos. Es posible que desees sugerir correcciones o confirmaciones para entradas ambiguas.

4. **Selección predeterminada**: Establece una selección predeterminada, especialmente si hay una opción común o recomendada. Esto mejora la experiencia del usuario al reducir la necesidad de clics adicionales.

5. **ComboBox vs. otros componentes de lista**: Un `ComboBox` es la mejor opción si necesitas una sola entrada del usuario y deseas proporcionarles opciones predeterminadas y la capacidad de personalizar su entrada. Otro componente de lista puede ser mejor si necesitas los siguientes comportamientos:
    - Selección múltiple y mostrar todos los elementos a la vez: [ListBox](./list-box.md)
    - Prevenir entradas personalizadas: [ChoiceBox](./choice-box.md)
