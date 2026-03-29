---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: b1ed30653bdca5af11b2f138a491baef
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

El componente `ComboBox` combina una lista desplegable con un campo de entrada de texto, para que los usuarios puedan seleccionar de opciones predefinidas o escribir un valor personalizado. Cuando se necesitan permitir entradas personalizadas junto a un conjunto de opciones sugeridas, llena el vacío que no cubre `ChoiceBox`.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="Lista" />

El componente ComboBox es un elemento de entrada versátil que combina las características de una lista desplegable y un campo de entrada de texto. Permite a los usuarios seleccionar elementos de una lista predefinida o ingresar valores personalizados según sea necesario. Esta sección explora los usos comunes del componente ComboBox en varios escenarios:

1. **Búsqueda y entrada de productos**: Utiliza un ComboBox para implementar una función de búsqueda y entrada de productos. Los usuarios pueden seleccionar un producto de una lista predefinida o escribir un nombre de producto personalizado. Esto es útil para aplicaciones como sitios de comercio electrónico donde los productos son vastos y diversos.

2. **Selección y entrada de etiquetas**: En aplicaciones que implican etiquetado de contenido, un ComboBox puede ser una excelente elección. Los usuarios pueden seleccionar de una lista de etiquetas existentes o agregar etiquetas personalizadas al escribirlas. Esto es útil para organizar y categorizar contenido. Ejemplos de tales etiquetas incluyen:
    >- Etiquetas de proyecto: En una herramienta de gestión de proyectos, los usuarios pueden seleccionar etiquetas o tags (por ejemplo, "Urgente", "En Progreso", "Completado") para categorizar tareas o proyectos, y pueden crear etiquetas personalizadas según sea necesario.
    >- Ingredientes de recetas: En una aplicación de cocina o recetas, los usuarios pueden seleccionar ingredientes de una lista (por ejemplo, "Tomates", "Cebollas", "Pollo") o añadir sus propios ingredientes para recetas personalizadas.
    >- Etiquetas de ubicación: En una aplicación de mapeo o geolocalización, los usuarios pueden elegir etiquetas de ubicación predefinidas (por ejemplo, "Playa", "Ciudad", "Parque") o crear etiquetas personalizadas para marcar lugares específicos en un mapa.

3. **Entrada de datos con valores sugeridos**: En formularios de entrada de datos, un ComboBox puede ser utilizado para acelerar la entrada proporcionando una lista de valores sugeridos basada en la entrada del usuario. Esto ayuda a los usuarios a ingresar datos de manera precisa y eficiente.

    :::tip
    El `ComboBox` debe ser utilizado cuando se permite a los usuarios ingresar valores personalizados. Si solo se desean valores preestablecidos, utiliza un [`ChoiceBox`](./choice-box.md) en su lugar.
    :::

## Valor personalizado {#custom-value}

Cambiar la propiedad de valor personalizado permite controlar si un usuario puede o no cambiar el valor en el campo de entrada del componente `ComboBox`. Si `true`, que es el valor predeterminado, entonces un usuario puede cambiar el valor. Si se establece en `false`, el usuario no podrá cambiar el valor. Esto se puede establecer utilizando el método <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Marcador de posición {#placeholder}

Se puede establecer un marcador de posición para un `ComboBox` que se mostrará en el campo de texto del componente cuando esté vacío para indicar a los usuarios la entrada deseada en el campo. Esto se puede hacer usando el método <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Tipo de desplegable {#dropdown-type}

Usar el método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> asignará un valor al atributo `type` de un `ComboBox`, y un valor correspondiente al atributo `data-dropdown-for` en el desplegable del `ComboBox`. Esto es útil para el estilo, ya que el desplegable se extrae de su posición actual en el DOM y se reubica al final del cuerpo de la página cuando se abre.

Este desapego crea una situación donde dirigir directamente el desplegable usando CSS o selectores de parte de sombra desde el componente padre se vuelve desafiante, a menos que se haga uso del atributo tipo de desplegable.

En la demostración a continuación, el tipo de desplegable se establece y se utiliza en el archivo CSS para seleccionar el desplegable y cambiar el color de fondo.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Conteo máximo de filas {#max-row-count}

Por defecto, el número de filas mostradas en el desplegable de un `ComboBox` se aumentará para ajustarse al contenido. Sin embargo, el uso del método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permite controlar cuántos elementos se muestran.

:::caution
Usar un número menor o igual a 0 resultará en la anulación de esta propiedad.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Apertura y cierre {#opening-and-closing}

La visibilidad de las opciones para un `ComboBox` se puede controlar programáticamente con los métodos `open()` y `close()`. 
Estos métodos te permiten mostrar la lista de opciones para selección o esconderla según sea necesario, proporcionando mayor flexibilidad en la gestión del comportamiento de un `ComboBox`.

Además, webforJ tiene oyentes de eventos para cuando el `ComboBox` se cierra y cuando se abre, dándote más control para activar acciones específicas.

```Java
//Enfocar o abrir el siguiente componente en un formulario
ComboBox universidad = new ComboBox("Universidad");
ComboBox major = new ComboBox("Carrera");
Button enviar = new Button("Enviar");

//... Agregar listas de universidades y carreras

universidad.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  enviar.focus();
});
```

## Dimensiones de apertura {#opening-dimensions}

El componente `ComboBox` tiene métodos que permiten manipular las dimensiones del desplegable. La **altura máxima** y **ancho mínimo** del desplegable se pueden establecer utilizando los métodos <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> y <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivamente.

:::tip
Pasar un valor `String` a cualquiera de estos métodos permitirá aplicar [cualquier unidad CSS válida](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), como píxeles, dimensiones de vista o otras reglas válidas. Pasar un `int` establecerá el valor transmitido en píxeles.
:::

## Resaltado {#highlighting}

Al trabajar con un `ComboBox`, puedes personalizar cuándo se resalta el texto, según cómo el componente gana enfoque. Esta característica puede reducir errores de entrada cuando los usuarios completan formularios y puede mejorar la experiencia general de navegación. Cambia el comportamiento de resaltado usando el método `setHighlightOnFocus()` con uno de los enumerados `HasHighlightOnFocus.Behavior` incorporados:

- `ALL`
Los contenidos del componente siempre se resaltan automáticamente cuando el componente recibe enfoque.
- `FOCUS`
Los contenidos del componente se resaltan automáticamente cuando el componente recibe enfoque bajo control del programa.
- `FOCUS_OR_KEY`
Los contenidos del componente se resaltan automáticamente cuando el componente recibe enfoque bajo control del programa o al tabular en él.
- `FOCUS_OR_MOUSE`
Los contenidos del componente se resaltan automáticamente cuando el componente recibe enfoque bajo control del programa o al hacer clic en él con el mouse.
- `KEY`
Los contenidos del componente se resaltan automáticamente cuando el componente recibe enfoque al tabular en él.
- `KEY_MOUSE`
Los contenidos del componente se resaltan automáticamente cuando el componente recibe enfoque al tabular en él o al hacer clic en él con el mouse.
- `MOUSE`
Los contenidos del componente se resaltan automáticamente cuando el componente recibe enfoque al hacer clic en él con el mouse.
- `NONE`
Los contenidos del componente nunca se resaltan automáticamente cuando el componente recibe enfoque.

:::note
Si el contenido fue resaltado al perder enfoque, se resaltará nuevamente al recuperar el enfoque, independientemente del comportamiento establecido.
:::

## Prefijo y sufijo {#prefix-and-suffix}

Los slots proporcionan opciones flexibles para mejorar la capacidad de un `ComboBox`. Puedes tener íconos, etiquetas, spinners de carga, capacidad de limpiar/restablecer, imágenes de avatar/perfil y otros componentes beneficiosos anidados dentro de un `ComboBox` para aclarar aún más el significado intencionado a los usuarios.
El `ComboBox` tiene dos slots: los slots `prefix` y `suffix`. Usa los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de las opciones dentro de un `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Estilización {#styling}

<TableBuilder name="ComboBox" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima para el usuario al usar el componente `ComboBox`, considera las siguientes mejores prácticas:

1. **Precargar valores comunes**: Si hay elementos comunes o usados frecuentemente, precárgalos en la lista del `ComboBox`. Esto acelera la selección de elementos comunes elegidos e incentiva la consistencia.

2. **Etiquetas amigables para el usuario**: Asegúrate de que las etiquetas mostradas para cada opción sean amigables para el usuario y autoexplicativas. Asegúrate de que los usuarios puedan entender fácilmente el propósito de cada elección.

3. **Validación**: Implementa validación de entrada para manejar entradas personalizadas. Verifica la precisión y consistencia de los datos. Puede que desees sugerir correcciones o confirmaciones para entradas ambiguas.

4. **Selección por defecto**: Establece una selección predeterminada, especialmente si hay una opción común o recomendada. Esto mejora la experiencia del usuario al reducir la necesidad de clics adicionales.

5. **ComboBox vs. Otros componentes de lista**: Un `ComboBox` es la mejor opción si necesitas una única entrada del usuario y deseas proporcionarles opciones predeterminadas y la capacidad de personalizar su entrada. Otro componente de lista puede ser mejor si necesitas los siguientes comportamientos:
    - Selección múltiple y mostrar todos los elementos a la vez: [ListBox](./list-box.md)
    - Prevenir entradas personalizadas: [ChoiceBox](./choice-box.md)
