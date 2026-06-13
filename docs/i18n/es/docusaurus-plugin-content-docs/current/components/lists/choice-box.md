---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: cf4d092418fcf1f593b8b8d00a47344b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

El componente `ChoiceBox` presenta una lista desplegable de la que los usuarios pueden seleccionar una sola opción. Cuando se realiza una selección, el valor elegido se muestra en el botón. Es ideal cuando los usuarios necesitan elegir entre un conjunto fijo de opciones predefinidas, y se pueden usar las teclas de flecha para navegar por la lista.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="List" />

Los componentes `ChoiceBox` se utilizan para diversos propósitos, como seleccionar elementos de un menú, elegir de una lista de categorías o seleccionar opciones de conjuntos predefinidos. Proporcionan una forma organizada y visualmente agradable para que los usuarios realicen selecciones, particularmente cuando hay múltiples opciones disponibles. Los usos comunes incluyen:

1. **Selección de Opciones por Parte del Usuario**: El propósito principal de un `ChoiceBox` es permitir a los usuarios seleccionar una sola opción de una lista. Esto es valioso en aplicaciones que requieren que los usuarios tomen decisiones, como:
    - Elegir de una lista de categorías
    - Seleccionar opciones de conjuntos predefinidos

2. **Entradas de Formularios**: Al diseñar formularios que requieren que los usuarios ingresen opciones específicas, el `ChoiceBox` simplifica el proceso de selección. Ya sea seleccionando un país, estado u otra opción de una lista predefinida, el `ChoiceBox` optimiza el proceso de entrada.

3. **Filtrado y Clasificación**: El `ChoiceBox` se puede utilizar para tareas de filtrado y clasificación en aplicaciones. Los usuarios pueden elegir criterios de filtrado o preferencias de clasificación de una lista, facilitando la organización y navegación de datos.

4. **Configuración y Ajustes**: Cuando su aplicación incluye ajustes u opciones de configuración, el `ChoiceBox` proporciona una forma intuitiva para que los usuarios ajusten preferencias. Los usuarios pueden seleccionar ajustes de una lista, facilitando la personalización de la aplicación según sus necesidades.

:::tip
El `ChoiceBox` está destinado para ser utilizado cuando hay un número preestablecido de opciones disponibles, y no se deben permitir o incluir opciones personalizadas. Si se desea permitir que los usuarios ingresen valores personalizados, utilice un [`ComboBox`](./combo-box.md) en su lugar.
:::

## Tipo de desplegable {#dropdown-type}

Usar el <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> método asignará un valor al atributo `type` de un `ChoiceBox`, y un valor correspondiente para el atributo `data-dropdown-for` en el desplegable del `ChoiceBox`. Esto es útil para el estilo, ya que el desplegable se saca de su posición actual en el DOM y se reubica al final del cuerpo de la página cuando se abre.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Este desapego crea una situación en la que apuntar directamente al desplegable usando CSS o selectores de partes de sombra del componente padre se vuelve complicado, a menos que se utilice el atributo de tipo de desplegable.

En la demostración a continuación, el tipo de desplegable se establece y se utiliza en el archivo CSS para aumentar el tamaño de una opción cuando pasas el ratón sobre ella.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Conteo máximo de filas {#max-row-count}

Por defecto, el número de filas mostradas en el desplegable de un `ChoiceBox` se aumentará para ajustarse al contenido. Sin embargo, usar el <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> método permite controlar cuántos elementos se muestran.

:::tip
Usar un número que sea menor o igual a 0 resultará en desactivar esta propiedad.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Apertura y cierre {#opening-and-closing}

La visibilidad de las opciones para un `ChoiceBox` se puede controlar programáticamente con los métodos `open()` y `close()`. Estos métodos permiten mostrar la lista de opciones para la selección o ocultarla según sea necesario, proporcionando una mayor flexibilidad en la gestión del comportamiento de un `ChoiceBox`.

Además, webforJ tiene oyentes de eventos para cuando se cierra el `ChoiceBox` y cuando se abre, dándote más control para activar acciones específicas.

```Java
//Enfocar o abrir el siguiente componente en un formulario
ChoiceBox universidad = new ChoiceBox("Universidad");
ChoiceBox carrera = new ChoiceBox("Carrera");
Button enviar = new Button("Enviar");

//... Agregar listas de universidades y carreras

universidad.onClose( e ->{
  carrera.focus();
});

carrera.onClose( e ->{
  enviar.focus();
});
```

## Dimensiones de apertura {#opening-dimensions}

El componente `ChoiceBox` tiene métodos que permiten manipular las dimensiones del desplegable. La **altura máxima** y la **anchura mínima** del desplegable se pueden establecer usando el <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> y el <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> métodos, respectivamente.

:::tip
Pasar un valor de `String` a cualquiera de estos métodos permitirá que se aplique [cualquier unidad CSS válida](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), como píxeles, dimensiones de viewport u otras reglas válidas. Pasar un `int` establecerá el valor pasado en píxeles.
:::

## Prefijo y sufijo {#prefix-and-suffix}

Las ranuras ofrecen opciones flexibles para mejorar la capacidad de un `ChoiceBox`. Puedes tener íconos, etiquetas, indicadores de carga, capacidad de limpiar/restablecer, fotos de avatar/perfil y otros componentes beneficiosos anidados dentro de un `ChoiceBox` para aclarar el significado destinado a los usuarios.
El `ChoiceBox` tiene dos ranuras: las ranuras `prefix` y `suffix`. Usa los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Estilizado {#styling}

<TableBuilder name="ChoiceBox" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima para el usuario al usar el componente `ChoiceBox`, considera las siguientes mejores prácticas:

1. **Opciones Claras y Limitadas**: Mantén la lista de opciones concisa siempre que sea posible, y relevante para la tarea del usuario. Un `ChoiceBox` es ideal para presentar una lista clara de opciones.

2. **Etiquetas Amigables para el Usuario**: Asegúrate de que las etiquetas mostradas para cada opción sean amigables para el usuario y autoexplicativas. Asegúrate de que los usuarios puedan entender fácilmente el propósito de cada elección.

3. **Selección Predeterminada**: Establece una selección predeterminada cuando el ChoiceBox se muestre inicialmente. Esto asegura una opción preseleccionada, reduciendo el número de interacciones requeridas para hacer una elección.

4. **ChoiceBox vs. Otros Componentes de Lista**: Un `ChoiceBox` es la mejor opción si necesitas restringir la entrada del usuario a una sola elección de una lista de opciones predeterminadas. Otro componente de lista puede ser mejor si necesitas los siguientes comportamientos:
    - Selección Múltiple y mostrar todos los elementos a la vez: [`ListBox`](./list-box.md)
    - Permitir entrada personalizada: [`ComboBox`](./combo-box.md)
