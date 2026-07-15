---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: f897ac9d3f5c252ac323762080e1edcf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

El componente `ChoiceBox` presenta una lista desplegable de la cual los usuarios pueden seleccionar una sola opción. Cuando se hace una selección, el valor elegido se muestra en el botón. Es ideal cuando los usuarios necesitan elegir de un conjunto fijo de opciones predefinidas, y se pueden utilizar las teclas de flecha para navegar por la lista.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="Lista" />

Los componentes `ChoiceBox` se utilizan para diversos propósitos, como seleccionar elementos de un menú, elegir de una lista de categorías o seleccionar opciones de conjuntos predefinidos. Proporcionan una forma organizada y visualmente atractiva para que los usuarios realicen selecciones, particularmente cuando hay múltiples opciones disponibles. Los usos comunes incluyen:

1. **Selección de Opciones por Parte del Usuario**: El propósito principal de un `ChoiceBox` es permitir que los usuarios seleccionen una sola opción de una lista. Esto es valioso en aplicaciones que requieren que los usuarios tomen decisiones, como:
    - Elegir de una lista de categorías
    - Seleccionar opciones de conjuntos predefinidos

2. **Entradas de Formularios**: Al diseñar formularios que requieren que los usuarios ingresen opciones específicas, el `ChoiceBox` simplifica el proceso de selección. Ya sea seleccionando un país, estado u otra opción de una lista predefinida, el `ChoiceBox` agiliza el proceso de entrada.

3. **Filtrado y Ordenamiento**: El `ChoiceBox` se puede utilizar para tareas de filtrado y ordenamiento en aplicaciones. Los usuarios pueden elegir criterios de filtrado o preferencias de ordenación de una lista, facilitando la organización y navegación de datos.

4. **Configuración y Ajustes**: Cuando su aplicación incluye opciones de configuración o ajustes, el `ChoiceBox` proporciona una manera intuitiva para que los usuarios ajusten sus preferencias. Los usuarios pueden seleccionar configuraciones de una lista, facilitando la adaptación de la aplicación a sus necesidades.

:::tip
El `ChoiceBox` está destinado a usarse cuando hay un número preestablecido de opciones disponibles, y no se deben permitir o incluir opciones personalizadas. Si se desea permitir a los usuarios ingresar valores personalizados, utilice un [`ComboBox`](./combo-box.md) en su lugar.
:::

## Tipo de desplegable {#dropdown-type}

Utilizando el método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>, se asignará un valor al atributo `type` de un `ChoiceBox`, y un valor correspondiente para el atributo `data-dropdown-for` en el desplegable del `ChoiceBox`. Esto es útil para el estilo, ya que el desplegable se extrae de su posición actual en el DOM y se reubica al final del cuerpo de la página al abrirse.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Esta separación crea una situación donde apuntar directamente al desplegable utilizando CSS o selectores de partes de sombra del componente padre se vuelve complicado, a menos que se utilice el atributo de tipo de desplegable.

En la demo a continuación, el tipo de desplegable se establece y se utiliza en el archivo CSS para aumentar el tamaño de una opción cuando pasas el ratón sobre ella.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Conteo máximo de filas {#max-row-count}

Por defecto, el número de filas mostradas en el desplegable de un `ChoiceBox` se incrementará para ajustarse al contenido. Sin embargo, utilizar el método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permite controlar cuántos elementos se muestran.

:::tip
Usar un número que sea menor o igual a 0 resultará en desactivar esta propiedad.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Apertura y cierre {#opening-and-closing}

La visibilidad de las opciones para un `ChoiceBox` se puede controlar programáticamente con los métodos `open()` y `close()`. Estos métodos te permiten mostrar la lista de opciones para la selección o ocultarla según sea necesario, brindando mayor flexibilidad en la gestión del comportamiento de un `ChoiceBox`.

Además, webforJ tiene oyentes de eventos para cuando el `ChoiceBox` se cierra y cuando se abre, dándote más control para activar acciones específicas.

```Java
// Enfocar o abrir el siguiente componente en un formulario
ChoiceBox universidad = new ChoiceBox("Universidad");
ChoiceBox especialidad = new ChoiceBox("Especialidad");
Button enviar = new Button("Enviar");

//... Agregar listas de universidades y especialidades

universidad.onClose( e ->{
  especialidad.focus();
});

especialidad.onClose( e ->{
  enviar.focus();
});
```

## Dimensiones de apertura {#opening-dimensions}

El componente `ChoiceBox` tiene métodos que permiten manipular las dimensiones del desplegable. La **altura máxima** y **ancho mínimo** del desplegable se pueden establecer utilizando los métodos <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> y <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivamente.

:::tip
Pasar un valor de `String` a cualquiera de estos métodos permitirá que se aplique [cualquier unidad CSS válida](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), como píxeles, dimensiones de viewport, u otras reglas válidas. Pasar un `int` establecerá el valor pasado en píxeles.
:::

## Prefijo y sufijo {#prefix-and-suffix}

Los slots proporcionan opciones flexibles para mejorar la capacidad de un `ChoiceBox`. Puedes tener íconos, etiquetas, indicadores de carga, capacidad de borrar/resetear, imágenes de avatar/perfil, y otros componentes útiles anidados dentro de un `ChoiceBox` para aclarar aún más el significado pretendido para los usuarios. 
El `ChoiceBox` tiene dos slots: el slot `prefix` y el slot `suffix`. Utiliza los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox();
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Estilización {#styling}

<TableBuilder name="ChoiceBox" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al utilizar el componente `ChoiceBox`, considera las siguientes mejores prácticas:

1. **Opciones Claras y Limitadas**: Mantén la lista de elecciones concisa siempre que sea posible y relevante para la tarea del usuario. Un `ChoiceBox` es ideal para presentar una lista clara de opciones.

2. **Etiquetas Amigables para el Usuario**: Asegúrate de que las etiquetas mostradas para cada opción sean amigables para el usuario y autoexplicativas. Asegúrate de que los usuarios puedan entender fácilmente el propósito de cada elección.

3. **Selección Predeterminada**: Establece una selección predeterminada cuando el `ChoiceBox` se muestre inicialmente. Esto asegura una opción preseleccionada, reduciendo la cantidad de interacciones requeridas para hacer una elección.

4. **ChoiceBox vs. Otros Componentes de Lista**: Un `ChoiceBox` es la mejor opción si necesitas restringir la entrada del usuario a una sola elección de una lista de opciones predeterminadas. Otro componente de lista puede ser mejor si necesitas los siguientes comportamientos:
    - Selección múltiple y mostrar todos los elementos a la vez: [`ListBox`](./list-box.md)
    - Permitir entrada personalizada: [`ComboBox`](./combo-box.md)
