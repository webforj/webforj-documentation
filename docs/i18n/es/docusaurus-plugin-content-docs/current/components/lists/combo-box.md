---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 9e5c0f54f07f604ee91a84210189ca30
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

El componente `ComboBox` combina una lista desplegable con un campo de entrada de texto, de modo que los usuarios pueden seleccionar de opciones predefinidas o escribir un valor personalizado. Cuando se permiten entradas personalizadas junto con un conjunto de opciones sugeridas, llena el vacío que `ChoiceBox` no cubre.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="List" />

El componente ComboBox es un elemento de entrada versátil que combina las características de una lista desplegable y un campo de entrada de texto. Permite a los usuarios seleccionar elementos de una lista predefinida o ingresar valores personalizados según sea necesario. Esta sección explora los usos comunes del componente ComboBox en varios escenarios:

1. **Búsqueda y entrada de productos**: Utiliza un ComboBox para implementar una función de búsqueda y entrada de productos. Los usuarios pueden seleccionar un producto de una lista predefinida o escribir un nombre de producto personalizado. Esto es útil para aplicaciones como sitios de comercio electrónico donde los productos son vastos y diversos.

2. **Selección y entrada de etiquetas**: En aplicaciones que implican etiquetado de contenido, un ComboBox puede ser una excelente opción. Los usuarios pueden seleccionar de una lista de etiquetas existentes o agregar etiquetas personalizadas escribiéndolas. Esto es útil para organizar y categorizar contenido. Ejemplos de tales etiquetas incluyen:
    >- Etiquetas de proyectos: En una herramienta de gestión de proyectos, los usuarios pueden seleccionar etiquetas o etiquetas (por ejemplo, "Urgente", "En Progreso", "Completado") para categorizar tareas o proyectos, y pueden crear etiquetas personalizadas según sea necesario.
    >- Ingredientes de recetas: En una aplicación de cocina o recetas, los usuarios pueden seleccionar ingredientes de una lista (por ejemplo, "Tomates", "Cebollas", "Pollo") o añadir sus propios ingredientes para recetas personalizadas.
    >- Etiquetas de ubicación: En una aplicación de mapeo o geolocalización, los usuarios pueden elegir etiquetas de ubicación predefinidas (por ejemplo, "Playa", "Ciudad", "Parque") o crear etiquetas personalizadas para marcar lugares específicos en un mapa.

3. **Entrada de datos con valores sugeridos**: En formularios de entrada de datos, un ComboBox puede utilizarse para acelerar la entrada proporcionando una lista de valores sugeridos basados en la entrada del usuario. Esto ayuda a los usuarios a ingresar datos de manera precisa y eficiente.

    :::tip
    El `ComboBox` debe utilizarse cuando se permite a los usuarios ingresar valores personalizados. Si solo se desean valores preestablecidos, utiliza un [`ChoiceBox`](./choice-box.md) en su lugar.
    :::

## Valor personalizado {#custom-value}

Cambiar la propiedad de valor personalizado permite controlar si un usuario puede o no cambiar el valor en el campo de entrada del componente `ComboBox`. Si es `true`, que es el valor predeterminado, el usuario puede cambiar el valor. Si se establece en `false`, el usuario no podrá cambiar el valor. Esto se puede establecer utilizando el <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> método.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Marcador de posición {#placeholder}

Se puede establecer un marcador de posición para un `ComboBox` que se mostrará en el campo de texto del componente cuando esté vacío para solicitar a los usuarios la entrada deseada en el campo. Esto se puede hacer mediante el <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> método.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Tipo de desplegable {#dropdown-type}

El uso del <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> método asignará un valor al atributo `type` de un `ComboBox`, y un valor correspondiente para el atributo `data-dropdown-for` en el desplegable del `ComboBox`. Esto es útil para el estilo, ya que el desplegable se saca de su posición actual en el DOM y se reubica al final del cuerpo de la página cuando se abre.

Este desajuste crea una situación en la que apuntar directamente al desplegable utilizando CSS o selectores de parte sombra desde el componente padre se vuelve complicado, a menos que utilices el atributo de tipo de desplegable.

En la demostración a continuación, el tipo de desplegable se establece y se utiliza en el archivo CSS para aumentar un opción cuando pasas el ratón sobre ella.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Contador máximo de filas {#max-row-count}

Por defecto, el número de filas mostradas en el desplegable de un `ComboBox` se incrementará para adaptarse al contenido. Sin embargo, al usar el <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> método se puede controlar cuántos elementos se muestran.

:::caution
Usar un número que sea menor o igual a 0 resultará en la desactivación de esta propiedad.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Apertura y cierre {#opening-and-closing}

La visibilidad de las opciones para un `ComboBox` puede controlarse programáticamente con los métodos `open()` y `close()`. 
Estos métodos te permiten mostrar la lista de opciones para selección o ocultarla según sea necesario, proporcionando mayor flexibilidad en la gestión del comportamiento de un `ComboBox`.

Además, webforJ tiene oyentes de eventos para cuando el `ComboBox` se cierra y cuando se abre, lo que te brinda más control para desencadenar acciones específicas.

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

El componente `ComboBox` tiene métodos que permiten manipular las dimensiones del desplegable. La **altura máxima** y **anchura mínima** del desplegable se pueden establecer utilizando los <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> y <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> métodos, respectivamente.

:::tip
Pasar un valor `String` a cualquiera de estos métodos permitirá que se apliquen [cualquier unidad CSS válida](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), tales como píxeles, dimensiones del viewport, u otras reglas válidas. Pasar un `int` establecerá el valor pasado en píxeles.
:::

## Resaltado {#highlighting}

Al trabajar con un `ComboBox`, puedes personalizar cuándo se resalta el texto en función de cómo el componente adquiere el foco. Esta característica puede reducir errores de entrada cuando los usuarios completan formularios y puede mejorar la experiencia general de navegación. Cambia el comportamiento de resaltado utilizando el `setHighlightOnFocus()` método con uno de los enums integrados `HasHighlightOnFocus.Behavior`:

- `ALL`
El contenido del componente siempre se resalta automáticamente cuando el componente obtiene foco.
- `FOCUS`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco bajo control del programa.
- `FOCUS_OR_KEY`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco bajo control del programa o al tabular hacia él.
- `FOCUS_OR_MOUSE`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco bajo control del programa o al hacer clic en él con el ratón.
- `KEY`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco al tabular hacia él.
- `KEY_MOUSE`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco al tabular hacia él o al hacer clic en él con el ratón.
- `MOUSE`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco al hacer clic en él con el ratón.
- `NONE`
El contenido del componente nunca se resalta automáticamente cuando el componente obtiene foco.

:::note
Si el contenido se resaltó al perder el foco, se volverá a resaltar al recuperar el foco, independientemente del comportamiento establecido.
:::

## Prefijo y sufijo {#prefix-and-suffix}

Los slots proporcionan opciones flexibles para mejorar la capacidad de un `ComboBox`. Puedes tener íconos, etiquetas, spinners de carga, capacidad de borrar/restablecer, imágenes de avatar/perfil y otros componentes beneficiosos anidados dentro de un `ComboBox` para aclarar aún más el significado para los usuarios. 
El `ComboBox` tiene dos slots: el slot `prefix` y el slot `suffix`. Utiliza los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de las opciones dentro de un `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Estilo {#styling}

<TableBuilder name="ComboBox" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia de usuario óptima al utilizar el componente `ComboBox`, considera las siguientes mejores prácticas:

1. **Precargar valores comunes**: Si hay elementos comunes o de uso frecuente, precárgarlos en la lista del `ComboBox`. Esto acelera la selección de elementos elegidos comúnmente y fomenta la consistencia.

2. **Etiquetas amigables para el usuario**: Asegúrate de que las etiquetas mostradas para cada opción sean amigables para el usuario y autoexplicativas. Asegúrate de que los usuarios puedan comprender fácilmente el propósito de cada opción.

3. **Validación**: Implementa validación de entrada para manejar entradas personalizadas. Verifica la precisión y consistencia de los datos. Es posible que desees sugerir correcciones o confirmaciones para entradas ambiguas.

4. **Selección predeterminada**: Establece una selección predeterminada, especialmente si hay una opción común o recomendada. Esto mejora la experiencia del usuario al reducir la necesidad de clics adicionales.

5. **ComboBox frente a otros componentes de lista**: Un `ComboBox` es la mejor opción si necesitas una sola entrada del usuario y deseas proporcionarles opciones predeterminadas junto con la capacidad de personalizar su entrada. Otro componente de lista puede ser mejor si necesitas los siguientes comportamientos:
    - Selección múltiple y exhibición de todos los elementos a la vez: [ListBox](./list-box.md)
    - Prevenir entrada personalizada: [ChoiceBox](./choice-box.md)
