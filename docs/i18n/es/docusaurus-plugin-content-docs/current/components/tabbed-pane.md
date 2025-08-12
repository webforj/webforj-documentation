---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: 2e67673ef0ac49904be50764ef47ecb0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

La clase `TabbedPane` proporciona una forma compacta y organizada de mostrar contenido que está dividido en múltiples secciones, cada una asociada con un `Tab`. Los usuarios pueden cambiar entre estas secciones al hacer clic en las pestañas respectivas, a menudo etiquetadas con texto y/o iconos. Esta clase simplifica la creación de interfaces multifacéticas donde diferentes contenidos o formularios necesitan ser accesibles, pero no visibles simultáneamente.

## Usos {#usages}

La clase `TabbedPane` ofrece a los desarrolladores una herramienta poderosa para organizar y presentar múltiples pestañas o secciones dentro de una interfaz de usuario. Aquí hay algunos escenarios típicos donde podrías utilizar un `TabbedPane` en tu aplicación:

1. **Visor de Documentos**: Implementar un visor de documentos donde cada pestaña representa un documento o archivo diferente. Los usuarios pueden cambiar fácilmente entre los documentos abiertos para realizar multitareas de manera eficiente.

2. **Gestión de Datos:**: Utiliza un `TabbedPane` para organizar tareas de gestión de datos, por ejemplo:
    >- Diferentes conjuntos de datos que se mostrarán en una aplicación
    >- Varios perfiles de usuarios pueden ser mostrados dentro de pestañas separadas
    >- Diferentes perfiles en un sistema de gestión de usuarios

3. **Selección de Módulos**: Un `TabbedPane` puede representar diferentes módulos o secciones. Cada pestaña puede encapsular las funcionalidades de un módulo específico, permitiendo a los usuarios concentrarse en un aspecto de la aplicación a la vez.

4. **Gestión de Tareas**: Las aplicaciones de gestión de tareas pueden utilizar un `TabbedPane` para representar varios proyectos o tareas. Cada pestaña podría corresponder a un proyecto específico, permitiendo a los usuarios gestionar y rastrear tareas por separado.

5. **Navegación en el Programa**: Dentro de una aplicación que necesita ejecutar varios programas, un `TabbedPane` podría:
    >- Servir como una barra lateral que permite que diferentes aplicaciones o programas se ejecuten dentro de una sola aplicación, como se muestra en la plantilla [`AppLayout`](./app-layout.md)
    >- Crear una barra superior que puede servir para un propósito similar, o representar sub-aplicaciones dentro de una aplicación ya seleccionada.

## Pestañas {#tabs}

Las pestañas son elementos de interfaz de usuario que se pueden agregar a paneles con pestañas para organizar y cambiar entre diferentes vistas de contenido.

:::important
Las pestañas no están destinadas a ser utilizadas como componentes independientes. Están destinadas a ser utilizadas en conjunción con paneles con pestañas. Esta clase no es un `Componente` y no debe ser utilizada como tal.
:::

### Propiedades {#properties}

Las pestañas están compuestas por las siguientes propiedades, que se utilizan al agregarlas en un `TabbedPane`. Estas propiedades tienen métodos getters y setters para facilitar la personalización dentro de un `TabbedPane`.

1. **Key(`Objeto`)**: Representa el identificador único para el `Tab`.

2. **Text(`Cadena`)**: El texto que se mostrará como título para el `Tab` dentro del `TabbedPane`. Esto también se conoce como el título a través de los métodos `getTitle()` y `setTitle(String title)`.

3. **Tooltip(`Cadena`)**: El texto emergente que está asociado con el `Tab`, que se mostrará cuando el cursor se desplace sobre el `Tab`.

4. **Enabled(`booleano`)**: Representa si el `Tab` está actualmente habilitado o no. Se puede modificar con el método `setEnabled(boolean enabled)`.

5. **Closeable(`booleano`)**: Representa si el `Tab` se puede cerrar. Se puede modificar con el método `setCloseable(boolean enabled)`. Esto añadirá un botón de cierre en el `Tab` que puede ser clickeado por el usuario, y dispara un evento de eliminación. El componente `TabbedPane` dicta cómo manejar la eliminación.

6. **Slot(`Componente`)**: 
    Los slots proporcionan opciones flexibles para mejorar la capacidad de un `Tab`. Puedes tener iconos, etiquetas, indicadores de carga, capacidad de limpiar/restablecer, imágenes de avatar/perfil, y otros componentes beneficiosos anidados dentro de un `Tab` para aclarar aún más el significado intencionado a los usuarios.
    Puedes agregar un componente al slot `prefix` de un `Tab` durante la construcción. Alternativamente, puedes usar los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de un `Tab`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documentos", TablerIcon.create("files")));
        ```

## Manipulación de `Tab` {#tab-manipulation}

Existen varios métodos que permiten a los desarrolladores agregar, insertar, eliminar y manipular varias propiedades de los elementos `Tab` dentro del `TabbedPane`.

### Agregando un `Tab` {#adding-a-tab}

Los métodos `addTab()` y `add()` existen en diferentes capacidades sobrecargadas para permitir a los desarrolladores flexibilidad al agregar nuevas pestañas al `TabbedPane`. Agregar un `Tab` lo colocará después de cualquier pestaña existente previamente.

1. **`addTab(String text)`** - Agrega un `Tab` al `TabbedPane` con el `String` especificado como el texto del `Tab`.
2. **`addTab(Tab tab)`** - Agrega el `Tab` proporcionado como parámetro al `TabbedPane`.
3. **`addTab(String text, Componente componente)`** - Agrega un `Tab` con el `String` dado como el texto del `Tab`, y el `Componente` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`addTab(Tab tab, Componente componente)`** - Agrega el `Tab` proporcionado y muestra el `Componente` proporcionado en la sección de contenido del `TabbedPane`.
5. **`add(Componente... componente)`** - Agrega una o más instancias de `Componente` al `TabbedPane`, creando un `Tab` discreto para cada uno, con el texto configurado al nombre del `Componente`.

:::info
El método `add(Componente... componente)` determina el nombre del `Componente` pasado al llamar a `component.getName()` en el argumento pasado.
:::

### Insertando un `Tab` {#inserting-a-tab}

Además de agregar un `Tab` al final de las pestañas existentes, también es posible crear uno nuevo en una posición designada. Para hacer esto, existen múltiples versiones sobrecargadas del método `insertTab()`.

1. **`insertTab(int index, String text)`** - Inserta un `Tab` en el `TabbedPane` en el índice dado con el `String` especificado como el texto del `Tab`.
2. **`insertTab(int index, Tab tab)`** - Inserta el `Tab` proporcionado como parámetro al `TabbedPane` en el índice especificado.
3. **`insertTab(int index, String text, Componente componente)`** - Inserta un `Tab` con el `String` dado como el texto del `Tab`, y el `Componente` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`insertTab(int index, Tab tab, Componente componente)`** - Inserta el `Tab` proporcionado y muestra el `Componente` proporcionado en la sección de contenido del `TabbedPane`.

### Eliminando un `Tab` {#removing-a-tab}

Para eliminar un único `Tab` del `TabbedPane`, usa uno de los siguientes métodos:

1. **`removeTab(Tab tab)`** - Elimina un `Tab` del `TabbedPane` pasando la instancia del Tab que se va a eliminar.
2. **`removeTab(int index)`** - Elimina un `Tab` del `TabbedPane` especificando el 
índice del `Tab` que se va a eliminar.

Además de los dos métodos anteriores para eliminar un único `Tab`, usa el método **`removeAllTabs()`** para limpiar el `TabbedPane` de todas las pestañas.

:::info
Los métodos `remove()` y `removeAll()` no eliminan pestañas dentro del componente.
:::

### Asociación `Tab/Componente` {#tabcomponent-association}

Para cambiar el `Componente` que se mostrará para un `Tab` dado, llama al método `setComponentFor()`, y pasa ya sea la instancia del `Tab`, o el índice de ese Tab dentro del `TabbedPane`.

:::info
Si este método se utiliza en un `Tab` que ya está asociado con un `Componente`, el `Componente` previamente asociado será destruido.
:::

## Configuración y diseño {#configuration-and-layout}

La clase `TabbedPane` tiene dos partes constitutivas: un `Tab` que se muestra en una ubicación especificada, y un componente que se mostrará. Este puede ser un único componente, o un componente [`Composite`](../building-ui/composite-components), lo que permite la visualización de componentes más complejos dentro de la sección de contenido de una pestaña.

### Deslizamiento {#swiping}

El `TabbedPane` admite la navegación a través de las diversas pestañas mediante deslizamiento. Esto es ideal para una aplicación móvil, pero también se puede configurar mediante un método incorporado para admitir el deslizamiento del mouse. Tanto el deslizamiento como el deslizamiento del mouse están deshabilitados por defecto, pero se pueden habilitar con los métodos `setSwipable(boolean)` y `setSwipableWithMouse(boolean)`, respectivamente.

### Ubicación de pestañas {#tab-placement}

Las `Pestañas` dentro de un `TabbedPane` pueden colocarse en varias posiciones dentro del componente según la preferencia del desarrollador de la aplicación. Las opciones proporcionadas se establecen mediante el enumerado proporcionado, que tiene los valores de `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, o `HIDDEN`. La configuración predeterminada es `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Alineación {#alignment}

Además de cambiar la ubicación de los elementos `Tab` dentro del `TabbedPane`, también es posible configurar cómo se alinearán las pestañas dentro del componente. Por defecto, la configuración `AUTO` está en efecto, lo que permite que la ubicación de las pestañas dicte su alineación.

Las otras opciones son `START`, `END`, `CENTER`, y `STRETCH`. Las tres primeras describen la posición relativa al componente, y `STRETCH` hace que las pestañas llenen el espacio disponible.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Borde e indicador de actividad {#border-and-activity-indicator}

El `TabbedPane` tendrá un borde mostrado para las pestañas dentro de él por defecto, colocado dependiendo de qué `Placement` se ha establecido. Este borde ayuda a visualizar el espacio que ocupan las diversas pestañas dentro del panel.

Cuando se hace clic en un `Tab`, por defecto, un indicador de actividad se muestra cerca de ese `Tab` para ayudar a resaltar cuál es el `Tab` actualmente seleccionado.

Ambas opciones pueden ser personalizadas por un desarrollador cambiando los valores booleanos utilizando los métodos setter apropiados. Para cambiar si se muestra o no el borde, se puede utilizar el método `setBorderless(boolean)`, donde `true` oculta el borde, y `false`, el valor predeterminado, muestra el borde.

:::info
Este borde no se aplica a la totalidad del componente `TabbedPane`, y simplemente sirve como un separador entre las pestañas y el contenido del componente.
:::

Para establecer la visibilidad del indicador activo, se puede utilizar el método `setHideActiveIndicator(boolean)`. Pasar `true` a este método ocultará el indicador activo debajo de un `Tab` activo, mientras que `false`, el valor predeterminado, mantiene el indicador mostrado.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Modos de activación {#activation-modes}

Para un control más detallado sobre cómo se comporta el `TabbedPane` al ser navegado por el teclado, se puede establecer el modo de `Activación` para especificar cómo debería comportarse el componente.

- **`Auto`**: Cuando se establece en automático, navegar pestañas con las teclas de flecha mostrará instantáneamente el componente correspondiente de la pestaña.

- **`Manual`**: Cuando se establece en manual, la pestaña recibirá enfoque pero no se mostrará hasta que el usuario presione la barra espaciadora o enter.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Opciones de eliminación {#removal-options}

Los elementos `Tab` individuales pueden configurarse para ser cerrables. Las pestañas cerrables tendrán un botón de cierre añadido a la pestaña, que dispara un evento de cierre cuando se clickea. El `TabbedPane` dicta cómo se maneja este comportamiento.

- **`Manual`**: Por defecto, la eliminación se establece en `MANUAL`, lo que significa que el evento se dispara, pero depende del desarrollador manejar este evento de la manera que elija.

- **`Auto`**: Alternativamente, se puede usar `AUTO`, que disparará el evento y también eliminará el `Tab` del componente para el desarrollador, eliminando la necesidad de que el desarrollador implemente este comportamiento manualmente. 

## Estilos {#styling}

### Expansión y tema {#expanse-and-theme}

El `TabbedPane` viene con opciones de `Expanse` y `Tema` incorporadas similares a otros componentes de webforJ. Estos se pueden utilizar para agregar rápidamente estilos que transmitan varios significados al usuario final sin necesidad de estilizar el componente con CSS.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Mejores prácticas {#best-practices}

Las siguientes prácticas se recomiendan para utilizar el `TabbedPane` dentro de aplicaciones:

- **Agrupación Lógica**: Utiliza pestañas para agrupar lógicamente contenidos relacionados
    >- Cada pestaña debe representar una categoría o funcionalidad distinta dentro de tu aplicación
    >- Agrupa pestañas similares o lógicas cerca unas de otras

- **Pestañas Limitadas**: Evita abrumar a los usuarios con demasiadas pestañas. Considera usar una estructura jerárquica u otros patrones de navegación donde sea aplicable para una interfaz limpia.

- **Etiquetas Claras**: Etiqueta claramente tus Pestañas para su uso intuitivo
    >- Proporciona etiquetas claras y concisas para cada pestaña
    >- Las etiquetas deben reflejar el contenido o propósito, facilitando la comprensión a los usuarios
    >- Utiliza iconos y colores distintos donde sea aplicable

- **Navegación por Teclado**: Utiliza el soporte de navegación por teclado del `TabbedPane` de webforJ para hacer la interacción con el `TabbedPane` más fluida e intuitiva para el usuario final.

- **Pestaña Predeterminada**: Si la pestaña predeterminada no está ubicada al principio del `TabbedPane`, considera establecer esta pestaña como predeterminada para información esencial o comúnmente utilizada.
