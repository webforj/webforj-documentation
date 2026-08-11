---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: 1c1224ca662a0e268606dc1cb6a0e96a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

El componente `ChoiceBox` presenta una lista desplegable de la cual los usuarios pueden seleccionar una sola opción. Cuando se realiza una selección, el valor elegido se muestra en el botón. Es una buena opción cuando los usuarios necesitan elegir entre un conjunto fijo de opciones predefinidas, y se pueden usar las teclas de flecha para navegar por la lista.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="List" />

Los componentes `ChoiceBox` se utilizan para diversos propósitos, como seleccionar elementos de un menú, elegir de una lista de categorías, o seleccionar opciones de conjuntos predefinidos. Proporcionan una manera organizada y visualmente agradable para que los usuarios realicen selecciones, particularmente cuando hay múltiples opciones disponibles. Los usos comunes incluyen:

1. **Selección de Opciones por Parte del Usuario**: El propósito principal de un `ChoiceBox` es permitir que los usuarios seleccionen una sola opción de una lista. Esto es valioso en aplicaciones que requieren que los usuarios tomen decisiones, tales como:
    - Elegir de una lista de categorías
    - Seleccionar opciones de conjuntos predefinidos

2. **Entradas de Formularios**: Al diseñar formularios que requieren que los usuarios ingresen opciones específicas, el `ChoiceBox` simplifica el proceso de selección. Ya sea seleccionando un país, estado, o cualquier otra opción de una lista predefinida, el `ChoiceBox` agiliza el proceso de entrada.

3. **Filtrado y Ordenamiento**: El `ChoiceBox` se puede emplear para tareas de filtrado y ordenamiento en aplicaciones. Los usuarios pueden elegir criterios de filtrado o preferencias de ordenamiento de una lista, facilitando la organización y navegación de datos.

4. **Configuración y Ajustes**: Cuando su aplicación incluye configuraciones u opciones, el `ChoiceBox` proporciona una forma intuitiva para que los usuarios ajusten preferencias. Los usuarios pueden seleccionar configuraciones de una lista, facilitando la personalización de la aplicación según sus necesidades.

:::tip
El `ChoiceBox` está destinado a ser utilizado cuando hay un número predefinido de opciones disponibles, y no se deben permitir o incluir opciones personalizadas. Si se desea permitir que los usuarios ingresen valores personalizados, utilice un [`ComboBox`](./combo-box.md) en su lugar.
:::

## Tipo de despliegue {#dropdown-type}

Usando el método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink>, se asignará un valor al atributo `type` de un `ChoiceBox`, y un valor correspondiente para el atributo `data-dropdown-for` en el desplegable del `ChoiceBox`. Esto es útil para el estilo, ya que el desplegable se saca de su posición actual en el DOM y se reubica al final del cuerpo de la página cuando se abre.

Este desprendimiento crea una situación donde dirigir directamente el desplegable usando CSS o selectores de parte sombra desde el componente padre se vuelve complicado, a menos que utilice el atributo de tipo de desplegable.

En la demostración a continuación, el tipo de despliegue se establece y se utiliza en el archivo CSS para agrandar una opción cuando pasas el cursor sobre ella.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Conteo máximo de filas {#max-row-count}

Por defecto, el número de filas mostradas en el desplegable de un `ChoiceBox` se incrementará para ajustarse al contenido. Sin embargo, utilizando el método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>, permite controlar cuántos elementos se muestran.

:::tip
Utilizar un número que sea menor o igual a 0 resultará en eliminar esta propiedad.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Apertura y cierre {#opening-and-closing}

La visibilidad de las opciones para un `ChoiceBox` se puede controlar programáticamente con los métodos `open()` y `close()`. Estos métodos permiten mostrar la lista de opciones para la selección o ocultarla según sea necesario, proporcionando mayor flexibilidad en la gestión del comportamiento de un `ChoiceBox`.

Además, webforJ tiene oyentes de eventos para cuando el `ChoiceBox` se cierra y cuando se abre, dándole más control para activar acciones específicas.

```Java
//Enfocar o abrir el siguiente componente en un formulario
ChoiceBox universidad = new ChoiceBox("Universidad");
ChoiceBox major = new ChoiceBox("Carrera");
Button enviar = new Button("Enviar");

//... Agregar listas de universidades y carreras

universidad.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  enviar.focus();
});
```

## Dimensiones de apertura {#opening-dimensions}

El componente `ChoiceBox` tiene métodos que permiten manipular las dimensiones del desplegable. La **altura máxima** y la **anchura mínima** del desplegable se pueden establecer utilizando los métodos <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> y <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivamente.

:::tip
Pasar un valor de `String` a cualquiera de estos métodos permitirá aplicar [cualquier unidad CSS válida](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), como píxeles, dimensiones del viewport, u otras reglas válidas. Pasar un `int` establecerá el valor pasado en píxeles.
:::

## Prefijo y sufijo {#prefix-and-suffix}

Los slots proporcionan opciones flexibles para mejorar la capacidad de un `ChoiceBox`. Puede tener íconos, etiquetas, spinners de carga, capacidad de borrar/restablecer, fotos de avatar/perfil, y otros componentes beneficiosos anidados dentro de un `ChoiceBox` para clarificar aún más el significado previsto para los usuarios. El `ChoiceBox` tiene dos slots: los slots `prefix` y `suffix`. Utilice los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Estilizando {#styling}

<TableBuilder name="ChoiceBox" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima para el usuario al utilizar el componente `ChoiceBox`, considere las siguientes mejores prácticas:

1. **Opciones Claras y Limitadas**: Mantenga la lista de opciones concisa siempre que sea posible y relevante para la tarea del usuario. Un `ChoiceBox` es ideal para presentar una lista clara de opciones.

2. **Etiquetas Amigables para el Usuario**: Asegúrese de que las etiquetas mostradas para cada opción sean amigables y autoexplicativas. Haga que los usuarios puedan entender fácilmente el propósito de cada elección.

3. **Selección Predeterminada**: Establezca una selección predeterminada cuando el `ChoiceBox` se muestre inicialmente. Esto asegura una opción preseleccionada, reduciendo el número de interacciones requeridas para hacer una elección.

4. **ChoiceBox vs. Otros Componentes de Lista**: Un `ChoiceBox` es la mejor opción si necesita restringir la entrada del usuario a una sola elección de una lista de opciones predefinidas. Otro componente de lista puede ser mejor si necesita los siguientes comportamientos:
    - Selección Múltiple y mostrar todos los elementos a la vez: [`ListBox`](./list-box.md)
    - Permitir entrada personalizada: [`ComboBox`](./combo-box.md)
