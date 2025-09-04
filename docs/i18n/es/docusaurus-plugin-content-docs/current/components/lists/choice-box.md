---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: e90d77e503b1c8f7fc20109633b1e7be
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

<ParentLink parent="List" />

El componente `ChoiceBox` es un elemento de interfaz de usuario diseñado para presentar a los usuarios una lista de opciones o elecciones. Los usuarios pueden seleccionar una sola opción de esta lista, normalmente haciendo clic en el `ChoiceBox`, lo que activa la visualización de una lista desplegable que contiene las opciones disponibles. Los usuarios también pueden interactuar con el `ChoiceBox` utilizando las teclas de flecha. Cuando un usuario realiza una selección, la opción elegida se muestra en el botón del `ChoiceBox`.

## Usos {#usages}
Los componentes `ChoiceBox` se utilizan para diversos propósitos, como seleccionar elementos de un menú, elegir de una lista de categorías o seleccionar opciones de conjuntos predefinidos. Proporcionan una forma organizada y visualmente agradable para que los usuarios realicen selecciones, especialmente cuando hay múltiples opciones disponibles. Los usos comunes incluyen:

1. **Selección de Opciones por el Usuario**: El propósito principal de un `ChoiceBox` es permitir a los usuarios seleccionar una sola opción de una lista. Esto es valioso en aplicaciones que requieren que los usuarios tomen decisiones, como:
    - Elegir de una lista de categorías
    - Seleccionar opciones de conjuntos predefinidos

2. **Entradas de Formulario**: Al diseñar formularios que requieren que los usuarios ingresen opciones específicas, el `ChoiceBox` simplifica el proceso de selección. Ya sea seleccionando un país, estado o cualquier otra opción de una lista predefinida, el `ChoiceBox` agiliza el proceso de entrada.

3. **Filtrado y Ordenamiento**: El `ChoiceBox` puede utilizarse para tareas de filtrado y ordenamiento en aplicaciones. Los usuarios pueden elegir criterios de filtrado o preferencias de ordenación de una lista, facilitando la organización y navegación de los datos.

4. **Configuración y Ajustes**: Cuando su aplicación incluye configuraciones o opciones de ajuste, el `ChoiceBox` proporciona una forma intuitiva para que los usuarios ajusten sus preferencias. Los usuarios pueden seleccionar configuraciones de una lista, lo que facilita la adaptación de la aplicación a sus necesidades.

:::tip
El `ChoiceBox` está destinado a ser utilizado cuando hay un número predefinido de opciones disponibles, y no se deben permitir ni incluir opciones personalizadas. Si se desea permitir que los usuarios ingresen valores personalizados, utilice un [`ComboBox`](./combo-box.md) en su lugar.
:::

## Tipo de desplegable {#dropdown-type}

Usar el método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> asignará un valor al atributo `type` de un `ChoiceBox`, y un valor correspondiente para el atributo `data-dropdown-for` en el desplegable del `ChoiceBox`. Esto es útil para el estilo, ya que el desplegable se saca de su posición actual en el DOM y se reubica al final del cuerpo de la página cuando se abre.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Este desapego crea una situación donde apuntar directamente al desplegable utilizando selectores CSS o de partes de sombra desde el componente padre se vuelve complicado, a menos que se utilice el atributo del tipo de desplegable.

En la demostración a continuación, el tipo de desplegable se establece y se utiliza en el archivo CSS para seleccionar el desplegable y cambiar el color de fondo.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Contador de filas máximo {#max-row-count}

Por defecto, el número de filas mostradas en el desplegable de un `ChoiceBox` se incrementará para ajustarse al contenido. Sin embargo, utilizar el método <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permite controlar cuántos elementos se muestran. 

:::tip
Usar un número que sea menor o igual a 0 resultará en deshabilitar esta propiedad.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Apertura y cierre {#opening-and-closing}

La visibilidad de las opciones para un `ChoiceBox` se puede controlar programáticamente con los métodos `open()` y `close()`. Estos métodos permiten mostrar la lista de opciones para selección o ocultarla según sea necesario, proporcionando mayor flexibilidad en la gestión del comportamiento de un `ChoiceBox`.

Además, webforJ tiene oyentes de eventos para cuando el `ChoiceBox` está cerrado y cuando se abre, dándole más control para activar acciones específicas.

```Java
//Enfocar o abrir el siguiente componente en un formulario
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
Pasar un valor `String` a cualquiera de estos métodos permitirá que se aplique [cualquier unidad CSS válida](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), como píxeles, dimensiones del viewport u otras reglas válidas. Pasar un `int` establecerá el valor pasado en píxeles.
:::

## Prefijo y sufijo {#prefix-and-suffix}

Los slots proporcionan opciones flexibles para mejorar la capacidad de un `ChoiceBox`. Puede tener íconos, etiquetas, spinners de carga, capacidad de limpiar/restablecer, fotos de avatar/perfil y otros componentes útiles anidados dentro de un `ChoiceBox` para aclarar aún más el significado previsto para los usuarios. El `ChoiceBox` tiene dos slots: el slot `prefix` y el slot `suffix`. Utilice los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Estilo {#styling}

<TableBuilder name="ChoiceBox" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima al usuario al utilizar el componente `ChoiceBox`, considere las siguientes mejores prácticas:

1. **Opciones Claras y Limitadas**: Mantenga la lista de opciones concisa siempre que sea posible, y relevante para la tarea del usuario. Un `ChoiceBox` es ideal para presentar una lista clara de opciones.

2. **Etiquetas Amigables para el Usuario**: Asegúrese de que las etiquetas mostradas para cada opción sean amigables para el usuario y autoexplicativas. Asegúrese de que los usuarios puedan entender fácilmente el propósito de cada elección.

3. **Selección Predeterminada**: Establezca una selección predeterminada cuando el `ChoiceBox` se muestre inicialmente. Esto asegura una opción preseleccionada, reduciendo el número de interacciones requeridas para tomar una decisión.

4. **ChoiceBox vs. Otros Componentes de Lista**: Un `ChoiceBox` es la mejor opción si necesita restringir la entrada del usuario a una sola elección de una lista de opciones predeterminadas. Puede que otro componente de lista sea mejor si necesita los siguientes comportamientos:
    - Selección Múltiple y mostrar todos los elementos a la vez: [`ListBox`](./list-box.md)
    - Permitir entrada personalizada: [`ComboBox`](./combo-box.md)
