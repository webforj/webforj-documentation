---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 4ef8ce7040bed877e314790f155f728a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

El componente `ComboBox` combina una lista desplegable con una entrada de texto, de modo que los usuarios pueden seleccionar entre opciones predefinidas o escribir un valor personalizado. Cuando es necesario permitir entradas personalizadas junto a un conjunto de opciones sugeridas, llena el vacío que `ChoiceBox` no cubre.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="Lista" />

El componente ComboBox es un elemento de entrada versátil que combina las características de una lista desplegable y un campo de entrada de texto. Permite a los usuarios seleccionar elementos de una lista predefinida o ingresar valores personalizados según sea necesario. Esta sección explora los usos comunes del componente ComboBox en varios escenarios:

1. **Búsqueda y entrada de productos**: Usa un ComboBox para implementar una función de búsqueda y entrada de productos. Los usuarios pueden seleccionar un producto de una lista predefinida o escribir el nombre de un producto personalizado. Esto es útil para aplicaciones como sitios de comercio electrónico donde los productos son vastos y diversos.

2. **Selección y entrada de etiquetas**: En aplicaciones que implican etiquetado de contenido, un ComboBox puede ser una excelente opción. Los usuarios pueden seleccionar de una lista de etiquetas existentes o agregar etiquetas personalizadas escribiéndolas. Esto es útil para organizar y categorizar contenido. Ejemplos de tales etiquetas incluyen:
    >- Etiquetas de proyecto: En una herramienta de gestión de proyectos, los usuarios pueden seleccionar etiquetas o tags (por ejemplo, "Urgente", "En Progreso", "Completado") para categorizar tareas o proyectos, y pueden crear etiquetas personalizadas según sea necesario.
    >- Ingredientes de recetas: En una aplicación de cocina o recetas, los usuarios pueden seleccionar ingredientes de una lista (por ejemplo, "Tomates", "Cebollas", "Pollo") o agregar sus propios ingredientes para recetas personalizadas.
    >- Etiquetas de ubicación: En una aplicación de mapeo o geolocalización, los usuarios pueden elegir etiquetas de ubicación predefinidas (por ejemplo, "Playa", "Ciudad", "Parque") o crear etiquetas personalizadas para marcar lugares específicos en un mapa.

3. **Entrada de datos con valores sugeridos**: En formularios de entrada de datos, un ComboBox se puede usar para acelerar la entrada al proporcionar una lista de valores sugeridos basada en la entrada del usuario. Esto ayuda a los usuarios a ingresar datos de manera precisa y eficiente.

    :::tip
    El `ComboBox` debe usarse cuando se permite a los usuarios ingresar valores personalizados. Si solo se desean valores preestablecidos, usa un [`ChoiceBox`](./choice-box.md) en su lugar.
    :::

## Valor personalizado {#custom-value}

Cambiar la propiedad de valor personalizado permite controlar si un usuario puede o no cambiar el valor en el campo de entrada del componente `ComboBox`. Si es `true`, que es el valor predeterminado, un usuario puede cambiar el valor. Si se establece en `false`, el usuario no podrá cambiar el valor. Esto se puede establecer utilizando el <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink> método.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Placeholder {#placeholder}

Se puede establecer un placeholder para un `ComboBox`, que se mostrará en el campo de texto del componente cuando esté vacío para indicar a los usuarios la entrada deseada en el campo. Esto se puede hacer utilizando el <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink> método.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Tipo de desplegable {#dropdown-type}

Usar el <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> método asignará un valor al atributo `type` de un `ComboBox`, y un valor correspondiente para el atributo `data-dropdown-for` en el desplegable del `ComboBox`. Esto es útil para el estilo, ya que el desplegable se saca de su posición actual en el DOM y se reubica al final del cuerpo de la página cuando se abre.

Este desenganche crea una situación en la que se vuelve complicado dirigir CSS o selectores de partes sombra desde el componente padre, a menos que se utilice el atributo tipo de desplegable.

En el demo a continuación, el tipo de desplegable se establece y se utiliza en el archivo CSS para aumentar el tamaño de una opción cuando pasas el mouse sobre ella.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Contador máximo de filas {#max-row-count}

Por defecto, el número de filas que se muestran en el desplegable de un `ComboBox` aumentará para ajustarse al contenido. Sin embargo, usar el <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> método permite controlar cuántos elementos se muestran.

:::caution
Usar un número que sea menor o igual a 0 resultará en desconfigurar esta propiedad.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Abrir y cerrar {#opening-and-closing}

La visibilidad de las opciones para un `ComboBox` se puede controlar programáticamente con los métodos `open()` y `close()`. Estos métodos permiten mostrar la lista de opciones para selección o esconderla según sea necesario, proporcionando mayor flexibilidad en la gestión del comportamiento de un `ComboBox`.

Además, webforJ tiene escuchas de eventos para cuando el `ComboBox` se cierra y cuando se abre, dándote más control para activar acciones específicas.

```Java
//Enfocar o abrir el siguiente componente en un formulario
ComboBox universidad = new ComboBox("Universidad");
ComboBox especialidad = new ComboBox("Especialidad");
Button enviar = new Button("Enviar");

//... Agregar listas de universidades y especialidades

universidad.onClose( e ->{
  especialidad.open();
});

especialidad.onClose( e ->{
  enviar.focus();
});
```

## Dimensiones de apertura {#opening-dimensions}

El componente `ComboBox` tiene métodos que permiten manipular las dimensiones del desplegable. La **altura máxima** y **ancho mínimo** del desplegable se pueden establecer utilizando el <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> y <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> métodos, respectivamente.

:::tip
Pasar un valor `String` a cualquiera de estos métodos permitirá aplicar [cualquier unidad CSS válida](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), como píxeles, dimensiones de viewport u otras reglas válidas. Pasar un `int` establecerá el valor pasado en píxeles.
:::

## Resaltado {#highlighting}

Al trabajar con un `ComboBox`, puedes personalizar cuándo se resalta el texto según cómo el componente obtiene foco. Esta característica puede reducir errores de entrada cuando los usuarios completan formularios y puede mejorar la experiencia de navegación general. Cambia el comportamiento de resaltado usando el método `setHighlightOnFocus()` con uno de los enums `HasHighlightOnFocus.Behavior` incorporados:

- `ALL`
El contenido del componente siempre se resalta automáticamente cuando el componente obtiene foco.
- `FOCUS`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco bajo control del programa.
- `FOCUS_OR_KEY`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco bajo control del programa o al tabular hacia él.
- `FOCUS_OR_MOUSE`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco bajo control del programa o al hacer clic en él con el mouse.
- `KEY`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco al tabular hacia él.
- `KEY_MOUSE`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco al tabular hacia él o al hacer clic en él con el mouse.
- `MOUSE`
El contenido del componente se resalta automáticamente cuando el componente obtiene foco al hacer clic en él con el mouse.
- `NONE`
El contenido del componente nunca se resalta automáticamente cuando el componente obtiene foco.

:::note
Si el contenido fue resaltado al perder el foco, se resaltará nuevamente al volver a obtener el foco, independientemente del comportamiento establecido.
:::

## Prefijo y sufijo {#prefix-and-suffix}

Los slots proporcionan opciones flexibles para mejorar la capacidad de un `ComboBox`. Puedes tener íconos, etiquetas, spinners de carga, capacidad de limpiar/restablecer, fotos de avatar/perfil y otros componentes beneficiosos anidados dentro de un `ComboBox` para aclarar aún más el significado previsto a los usuarios.
El `ComboBox` tiene dos slots: los slots de `prefijo` y `sufijo`. Usa los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de las opciones dentro de un `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Estilo {#styling}

<TableBuilder name="ComboBox" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia de usuario óptima al utilizar el componente `ComboBox`, considera las siguientes mejores prácticas:

1. **Precargar valores comunes**: Si hay elementos comunes o de uso frecuente, precárgalos en la lista del `ComboBox`. Esto acelera la selección de elementos comúnmente elegidos y fomenta la consistencia.

2. **Etiquetas amigables para el usuario**: Asegúrate de que las etiquetas mostradas para cada opción sean amigables para el usuario y autoexplicativas. Asegúrate de que los usuarios puedan comprender fácilmente el propósito de cada elección.

3. **Validación**: Implementa validación de entradas para manejar entradas personalizadas. Verifica la precisión y consistencia de los datos. Puede que quieras sugerir correcciones o confirmaciones para entradas ambiguas.

4. **Selección predeterminada**: Establece una selección predeterminada, especialmente si hay una opción común o recomendada. Esto mejora la experiencia del usuario al reducir la necesidad de clics adicionales.

5. **ComboBox vs. Otros componentes de lista**: Un `ComboBox` es la mejor opción si necesitas una única entrada del usuario y deseas proporcionarles opciones predeterminadas y la capacidad de personalizar su entrada. Otro componente de lista puede ser mejor si necesitas los siguientes comportamientos:
    - Selección múltiple y mostrar todos los elementos a la vez: [ListBox](./list-box.md)
    - Prevenir entrada personalizada: [ChoiceBox](./choice-box.md)
