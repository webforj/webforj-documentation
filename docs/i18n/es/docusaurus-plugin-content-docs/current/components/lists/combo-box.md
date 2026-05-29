---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: 199696bfbf6489520cec364f16226489
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

El componente `ComboBox` combina una lista desplegable con un campo de entrada de texto, de modo que los usuarios pueden seleccionar entre opciones predefinidas o escribir un valor personalizado. Cuando se deben permitir entradas personalizadas junto a un conjunto de opciones sugeridas, llena el vacío que `ChoiceBox` no cubre.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="List" />

El componente ComboBox es un elemento de entrada versátil que combina las características de una lista desplegable y un campo de entrada de texto. Permite a los usuarios seleccionar elementos de una lista predefinida o ingresar valores personalizados según sea necesario. Esta sección explora usos comunes del componente ComboBox en varios escenarios:

1. **Búsqueda y entrada de productos**: Usa un ComboBox para implementar una función de búsqueda y entrada de productos. Los usuarios pueden seleccionar un producto de una lista predefinida o escribir un nombre de producto personalizado. Esto es útil para aplicaciones como sitios de comercio electrónico donde los productos son vastos y diversos.

2. **Selección y entrada de etiquetas**: En aplicaciones que implican etiquetado de contenido, un ComboBox puede ser una excelente opción. Los usuarios pueden seleccionar de una lista de etiquetas existentes o agregar etiquetas personalizadas escribiéndolas. Esto es útil para organizar y clasificar contenido. Ejemplos de tales etiquetas incluyen:
    >- Etiquetas de proyectos: En una herramienta de gestión de proyectos, los usuarios pueden seleccionar etiquetas o tags (por ejemplo, "Urgente," "En Progreso," "Completado") para categorizar tareas o proyectos, y pueden crear etiquetas personalizadas según sea necesario.
    >- Ingredientes de recetas: En una aplicación de cocina o recetas, los usuarios pueden seleccionar ingredientes de una lista (por ejemplo, "Tomates," "Cebollas," "Pollo") o agregar sus propios ingredientes para recetas personalizadas.
    >- Etiquetas de ubicación: En una aplicación de mapeo o geolocalización, los usuarios pueden elegir etiquetas de ubicación predefinidas (por ejemplo, "Playa," "Ciudad," "Parque") o crear etiquetas personalizadas para marcar lugares específicos en un mapa.

3. **Entrada de datos con valores sugeridos**: En los formularios de entrada de datos, un ComboBox puede ser utilizado para acelerar la entrada proporcionando una lista de valores sugeridos en función de la entrada del usuario. Esto ayuda a los usuarios a ingresar datos de manera precisa y eficiente.

    :::tip
    El `ComboBox` debe ser utilizado cuando se permite a los usuarios ingresar valores personalizados. Si solo se desean valores preestablecidos, utiliza un [`ChoiceBox`](./choice-box.md) en su lugar.
    :::

## Valor personalizado {#custom-value}

Cambiar la propiedad de valor personalizado permite controlar si un usuario puede o no cambiar el valor en el campo de entrada del componente `ComboBox`. Si `true`, que es el valor predeterminado, entonces un usuario puede cambiar el valor. Si se establece en `false`, el usuario no podrá cambiar el valor. Esto se puede configurar utilizando el método <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Marcador de posición {#placeholder}

Se puede establecer un marcador de posición para un `ComboBox` que se mostrará en el campo de texto del componente cuando esté vacío para indicar a los usuarios la entrada deseada en el campo. Esto se puede hacer utilizando el método <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Tipo de desplegable {#dropdown-type}

Usar el método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> asignará un valor al atributo `type` de un `ComboBox`, y un valor correspondiente para el atributo `data-dropdown-for` en el desplegable del `ComboBox`. Esto es útil para darle estilo, ya que el desplegable se saca de su posición actual en el DOM y se reubica al final del cuerpo de la página cuando se abre.

Este desenganche crea una situación donde apuntar directamente al desplegable usando CSS o selectores de parte de sombra del componente padre se vuelve desafiante, a menos que utilices el atributo de tipo de desplegable.

En la demostración a continuación, el tipo de desplegable se establece y se utiliza en el archivo CSS para seleccionar el desplegable y cambiar el color de fondo.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Contador de filas máximo {#max-row-count}

Por defecto, el número de filas mostradas en el desplegable de un `ComboBox` se incrementará para ajustarse al contenido. Sin embargo, usar el método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permite controlar cuántos elementos se muestran.

:::caution
Usar un número que sea menor o igual a 0 resultará en desactivar esta propiedad.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Apertura y cierre {#opening-and-closing}

La visibilidad de las opciones para un `ComboBox` se puede controlar programáticamente con los métodos `open()` y `close()`. Estos métodos permiten mostrar la lista de opciones para selección o ocultarla según sea necesario, proporcionando una mayor flexibilidad en la gestión del comportamiento de un `ComboBox`.

Además, webforJ tiene oyentes de eventos para cuando el `ComboBox` se cierra y cuando se abre, brindándote más control para activar acciones específicas.

```Java
//Enfocar o abrir el siguiente componente en un formulario
ComboBox universidad = new ComboBox("Universidad");
ComboBox carrera = new ComboBox("Carrera");
Button enviar = new Button("Enviar");

//... Agregar listas de universidades y carreras

universidad.onClose(e -> {
  carrera.open();
});

carrera.onClose(e -> {
  enviar.focus();
});
```

## Dimensiones de apertura {#opening-dimensions}

El componente `ComboBox` tiene métodos que permiten manipular las dimensiones del desplegable. La **altura máxima** y **ancho mínimo** del desplegable se pueden establecer utilizando los métodos <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> y <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivamente.

:::tip
Pasar un valor `String` a cualquiera de estos métodos permitirá que se aplique [cualquier unidad CSS válida](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), como píxeles, dimensiones de ventana gráfica u otras reglas válidas. Pasar un `int` establecerá el valor ingresado en píxeles.
:::

## Resaltado {#highlighting}

Al trabajar con un `ComboBox`, puedes personalizar cuándo se resalta el texto en función de cómo el componente adquiere el enfoque. Esta función puede reducir los errores de entrada cuando los usuarios completan formularios y puede mejorar la experiencia general de navegación. Cambia el comportamiento de resaltado utilizando el método `setHighlightOnFocus()` con uno de los enums incorporados `HasHighlightOnFocus.Behavior`:

- `ALL`
Los contenidos del componente siempre se resaltan automáticamente cuando el componente obtiene enfoque.
- `FOCUS`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque bajo control del programa.
- `FOCUS_OR_KEY`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque bajo control del programa o al tabular hacia él.
- `FOCUS_OR_MOUSE`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque bajo control del programa o al hacer clic en él con el mouse.
- `KEY`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque al tabular hacia él.
- `KEY_MOUSE`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque al tabular hacia él o al hacer clic en él con el mouse.
- `MOUSE`
Los contenidos del componente se resaltan automáticamente cuando el componente obtiene enfoque al hacer clic en él con el mouse.
- `NONE`
Los contenidos del componente nunca se resaltan automáticamente cuando el componente obtiene enfoque.

:::note
Si el contenido se resaltó al perder el enfoque, se resaltará nuevamente al recuperar el enfoque, independientemente del comportamiento establecido.
:::

## Prefijo y sufijo {#prefix-and-suffix}

Los slots proporcionan opciones flexibles para mejorar la capacidad de un `ComboBox`. Puedes tener íconos, etiquetas, cargadores, capacidad de limpiar/restablecer, fotos de avatares/perfiles y otros componentes beneficiosos anidados dentro de un `ComboBox` para aclarar aún más el significado intencionado para los usuarios. 
El `ComboBox` tiene dos slots: los slots de `prefijo` y `sufijo`. Usa los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de las opciones dentro de un `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Estilo {#styling}

<TableBuilder name="ComboBox" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima para el usuario al utilizar el componente `ComboBox`, considera las siguientes mejores prácticas:

1. **Precargar valores comunes**: Si hay elementos comunes o utilizados con frecuencia, precárgalos en la lista del `ComboBox`. Esto acelera la selección de elementos comúnmente elegidos y fomenta la consistencia.

2. **Etiquetas amigables para el usuario**: Asegúrate de que las etiquetas mostradas para cada opción sean amigables para el usuario y autoexplicativas. Asegúrate de que los usuarios puedan entender fácilmente el propósito de cada elección.

3. **Validación**: Implementa validación de entrada para manejar entradas personalizadas. Verifica la precisión y consistencia de los datos. Puede que desees sugerir correcciones o confirmaciones para entradas ambiguas.

4. **Selección predeterminada**: Establece una selección predeterminada, especialmente si hay una elección común o recomendada. Esto mejora la experiencia del usuario al reducir la necesidad de clics adicionales.

5. **ComboBox vs. Otros componentes de lista**: Un `ComboBox` es la mejor opción si necesitas una única entrada del usuario y deseas proporcionarles opciones predeterminadas y la capacidad de personalizar su entrada. Otro componente de lista puede ser mejor si necesitas los siguientes comportamientos:
    - Selección múltiple y mostrar todos los elementos a la vez: [ListBox](./list-box.md)
    - Prevenir entradas personalizadas: [ChoiceBox](./choice-box.md)
