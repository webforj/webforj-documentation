---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: f903eeae452aae41b3eb04c170b9e98e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Múltiples secciones de contenido pueden organizarse bajo un solo `TabbedPane`, donde cada sección está vinculada a una `Tab` clicable. Solo una sección es visible a la vez, y las pestañas pueden mostrar texto, iconos o ambos para ayudar a los usuarios a navegar entre ellas.

<!-- INTRO_END -->

## Usos {#usages}

La clase `TabbedPane` proporciona a los desarrolladores una herramienta poderosa para organizar y presentar múltiples pestañas o secciones dentro de una interfaz de usuario. Aquí hay algunos escenarios típicos en los que podrías utilizar un `TabbedPane` en tu aplicación:

1. **Visualizador de Documentos**: Implementando un visualizador de documentos donde cada pestaña representa un documento o archivo diferente. Los usuarios pueden cambiar fácilmente entre documentos abiertos para una multitarea eficiente.

2. **Gestión de Datos:**: Utiliza un `TabbedPane` para organizar tareas de gestión de datos, por ejemplo:
    >- Diferentes conjuntos de datos para ser mostrados en una aplicación
    >- Varios perfiles de usuario pueden ser mostrados en pestañas separadas
    >- Diferentes perfiles en un sistema de gestión de usuarios

3. **Selección de Módulo**: Un `TabbedPane` puede representar diferentes módulos o secciones. Cada pestaña puede encapsular las funcionalidades de un módulo específico, permitiendo a los usuarios concentrarse en un aspecto de la aplicación a la vez.

4. **Gestión de Tareas**: Las aplicaciones de gestión de tareas pueden utilizar un `TabbedPane` para representar varios proyectos o tareas. Cada pestaña podría corresponder a un proyecto específico, permitiendo a los usuarios gestionar y rastrear tareas por separado.

5. **Navegación por el Programa**: Dentro de una aplicación que necesita ejecutar varios programas, un `TabbedPane` podría:
    >- Servir como una barra lateral que permite ejecutar diferentes aplicaciones o programas dentro de una sola aplicación, como se muestra en la plantilla [`AppLayout`](./app-layout.md)
    >- Crear una barra superior que puede servir un propósito similar, o representar sub-aplicaciones dentro de una aplicación ya seleccionada.
  
## Pestañas {#tabs}

Las pestañas son elementos de interfaz de usuario que se pueden agregar a los paneles tabulados para organizar y cambiar entre diferentes vistas de contenido.

:::important
Las pestañas no están destinadas a ser utilizadas como componentes autónomos. Están destinadas a ser utilizadas en conjunto con paneles tabulados. Esta clase no es un `Componente` y no debe ser utilizada como tal.
:::

### Propiedades {#properties}

Las pestañas están compuestas por las siguientes propiedades, que se utilizan al agregarlas en un `TabbedPane`. Estas propiedades tienen métodos getter y setter para facilitar la personalización dentro de un `TabbedPane`.

1. **Clave(`Object`)**: Representa el identificador único para la `Tab`.

2. **Texto(`String`)**: El texto que se mostrará como título para la `Tab` dentro del `TabbedPane`. Esto también se refiere como el título a través de los métodos `getTitle()` y `setTitle(String title)`.

3. **Tooltip(`String`)**: El texto del tooltip que está asociado con la `Tab`, que se mostrará cuando el cursor pase sobre la `Tab`.

4. **Habilitado(`boolean`)**: Representa si la `Tab` está actualmente habilitada o no. Puede ser modificado con el método `setEnabled(boolean enabled)`.

5. **Cerrable(`boolean`)**: Representa si la `Tab` puede ser cerrada. Puede ser modificado con el método `setCloseable(boolean enabled)` que agregará un botón de cerrar en la `Tab` que el usuario puede hacer clic y dispara un evento de eliminación. El componente `TabbedPane` dicta cómo manejar la eliminación.

6. **Slot(`Component`)**: 
    Los slots proporcionan opciones flexibles para mejorar la capacidad de una `Tab`. Puedes tener íconos, etiquetas, indicadores de carga, capacidad de limpiar/restablecer, fotos de avatar/perfil y otros componentes beneficiosos anidados dentro de una `Tab` para clarificar el significado intencionado a los usuarios.
    Puedes agregar un componente al slot `prefix` de una `Tab` durante la construcción. Alternativamente, puedes usar los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de una `Tab`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## Manipulación de `Tab` {#tab-manipulation}

Existen varios métodos que permiten a los desarrolladores agregar, insertar, eliminar y manipular diversas propiedades de los elementos `Tab` dentro del `TabbedPane`.

### Agregar una `Tab` {#adding-a-tab}

Los métodos `addTab()` y `add()` existen en diferentes capacidades sobrecargadas para permitir a los desarrolladores flexibilidad al agregar nuevas pestañas al `TabbedPane`. Agregar una `Tab` la colocará después de cualquier pestaña previamente existente.

1. **`addTab(String text)`** - Agrega una `Tab` al `TabbedPane` con el `String` especificado como el texto de la `Tab`.
2. **`addTab(Tab tab)`** - Agrega la `Tab` proporcionada como parámetro al `TabbedPane`.
3. **`addTab(String text, Component component)`** - Agrega una `Tab` con el `String` dado como texto de la `Tab`, y el `Component` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Agrega la `Tab` proporcionada y muestra el `Component` proporcionado en la sección de contenido del `TabbedPane`.
5. **`add(Component... component)`** - Agrega una o más instancias de `Component` al `TabbedPane`, creando una `Tab` discreta para cada una, con el texto establecido en el nombre del `Component`

:::info
El `add(Component... component)` determina el nombre del `Component` pasado llamando a `component.getName()` en el argumento pasado.
:::

### Insertar una `Tab` {#inserting-a-tab}

Además de agregar una `Tab` al final de las pestañas existentes, también es posible crear una nueva en una posición designada. Para hacer esto, existen múltiples versiones sobrecargadas de `insertTab()`. 

1. **`insertTab(int index, String text)`** - Inserta una `Tab` en el `TabbedPane` en el índice dado con el `String` especificado como el texto de la `Tab`.
2. **`insertTab(int index, Tab tab)`** - Inserta la `Tab` proporcionada como parámetro al `TabbedPane` en el índice especificado.
3. **`insertTab(int index, String text, Component component)`** - Inserta una `Tab` con el `String` dado como texto de la `Tab`, y el `Component` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Inserta la `Tab` proporcionada y muestra el `Component` proporcionado en la sección de contenido del `TabbedPane`.

### Eliminar una `Tab` {#removing-a-tab}

Para eliminar una sola `Tab` del `TabbedPane`, usa uno de los siguientes métodos:

1. **`removeTab(Tab tab)`** - Elimina una `Tab` del `TabbedPane` pasando la instancia de la Tab a ser eliminada.
2. **`removeTab(int index)`** - Elimina una `Tab` del `TabbedPane` especificando el índice de la `Tab` a eliminar.

Además de los dos métodos anteriores para la eliminación de una sola `Tab`, usa el método **`removeAllTabs()`** para limpiar el `TabbedPane` de todas las pestañas.

:::info
Los métodos `remove()` y `removeAll()` no eliminan pestañas dentro del componente.
:::

### Asociación Tab/Componente {#tabcomponent-association}

Para cambiar el `Component` que se mostrará para una `Tab` dada, llama al método `setComponentFor()` y pasa ya sea la instancia de la `Tab`, o el índice de esa Tab dentro del `TabbedPane`.

:::info
Si este método se usa en una `Tab` que ya está asociada con un `Component`, el `Component` anteriormente asociado será destruido.
:::

## Configuración y diseño {#configuration-and-layout}

La clase `TabbedPane` tiene dos partes constitutivas: una `Tab` que se muestra en una ubicación especificada, y un componente que se mostrará. Este puede ser un solo componente, o un componente [`Composite`](../building-ui/composite-components), permitiendo la visualización de componentes más complejos dentro de la sección de contenido de una pestaña.

### Deslizamiento {#swiping}

El `TabbedPane` soporta la navegación a través de las varias pestañas mediante deslizamiento. Esto es ideal para una aplicación móvil, pero también puede configurarse mediante un método incorporado para soportar el deslizamiento con el mouse. Tanto el deslizamiento como el deslizamiento con el mouse están deshabilitados por defecto, pero pueden habilitarse con los métodos `setSwipable(boolean)` y `setSwipableWithMouse(boolean)` respectivamente. 

### Colocación de Pestañas {#tab-placement}

Las `Tabs` dentro de un `TabbedPane` pueden colocarse en varias posiciones dentro del componente según la preferencia del desarrollador de la aplicación. Las opciones proporcionadas se establecen utilizando el enum proporcionado, que tiene los valores de `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, o `HIDDEN`. La configuración predeterminada es `TOP`.


<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Alineación {#alignment}

Además de cambiar la colocación de los elementos `Tab` dentro del `TabbedPane`, también es posible configurar cómo se alinearán las pestañas dentro del componente. Por defecto, la configuración `AUTO` está en efecto, lo que permite que la colocación de las pestañas dicte su alineación.

Las otras opciones son `START`, `END`, `CENTER`, y `STRETCH`. Las tres primeras describen la posición relativa al componente, mientras que `STRETCH` hace que las pestañas llenen el espacio disponible.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Borde e indicador de actividad {#border-and-activity-indicator}

El `TabbedPane` tendrá un borde mostrado para las pestañas dentro de él por defecto, colocado dependiendo de qué `Placement` se haya establecido. Este borde ayuda a visualizar el espacio que ocupan las diversas pestañas dentro del panel. 

Cuando se hace clic en una `Tab`, por defecto, se muestra un indicador de actividad cerca de esa `Tab` para ayudar a resaltar cuál es la `Tab` seleccionada actualmente.

Ambas opciones pueden ser personalizadas por un desarrollador cambiando los valores booleanos utilizando los métodos setter apropiados. Para cambiar si se muestra o no el borde, se puede usar el método `setBorderless(boolean)`, con `true` ocultando el borde, y `false`, el valor predeterminado, mostrando el borde.

:::info
Este borde no se aplica a la totalidad del componente `TabbedPane`, y simplemente sirve como un separador entre las pestañas y el contenido del componente.
:::

Para establecer la visibilidad del indicador activo, se puede usar el método `setHideActiveIndicator(boolean)`. Pasar `true` a este método ocultará el indicador activo bajo una `Tab` activa, mientras que `false`, el valor predeterminado, mantiene el indicador mostrado.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Modos de activación {#activation-modes}

Para un control más detallado sobre cómo se comporta el `TabbedPane` al navegar por el teclado, se puede establecer el modo de `Activation` para especificar cómo debe comportarse el componente.

- **`Automático`**: Cuando se establece en automático, navegar por las pestañas con las teclas de flecha mostrará instantáneamente el componente de pestaña correspondiente.

- **`Manual`**: Cuando se establece en manual, la pestaña recibirá enfoque pero no se mostrará hasta que el usuario presione espacio o enter.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Opciones de eliminación {#removal-options}

Los elementos `Tab` individuales pueden configurarse para ser cerrables. Las pestañas cerrables tendrán un botón de cerrar agregado a la pestaña, que dispara un evento de cierre cuando se hace clic. El `TabbedPane` dicta cómo se maneja este comportamiento.

- **`Manual`**: Por defecto, la eliminación se establece en `MANUAL`, lo que significa que se dispara el evento, pero depende del desarrollador manejar este evento de la manera que elija.

- **`Automático`**: Alternativamente, se puede utilizar `AUTO`, lo que disparará el evento y también eliminará la `Tab` del componente para el desarrollador, eliminando la necesidad de que el desarrollador implemente este comportamiento manualmente. 

## Estilo {#styling}

### Expansión y tema {#expanse-and-theme}

El `TabbedPane` viene con opciones de `Expansión` y `Tema` integradas similares a otros componentes de webforJ. Estos se pueden utilizar para agregar rápidamente estilo que transmite varios significados al usuario final sin necesidad de estilizar el componente con CSS.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Mejores prácticas {#best-practices}

Las siguientes prácticas se recomiendan para utilizar el `TabbedPane` dentro de las aplicaciones:

- **Agrupación Lógica**: Usa pestañas para agrupar lógicamente contenido relacionado
    >- Cada pestaña debe representar una categoría o funcionalidad distinta dentro de tu aplicación
    >- Agrupa pestañas similares o lógicas cerca unas de otras

- **Pocas Pestañas**: Evita abrumar a los usuarios con demasiadas pestañas. Considera usar una estructura jerárquica u otros patrones de navegación donde sea aplicable para una interfaz limpia

- **Etiquetas Claras**: Etiqueta claramente tus Pestañas para un uso intuitivo
    >- Proporciona etiquetas claras y concisas para cada pestaña
    >- Las etiquetas deben reflejar el contenido o propósito, facilitando a los usuarios su comprensión
    >- Utiliza íconos y colores distintos donde sea aplicable

- **Navegación por Teclado**: Utiliza el soporte de navegación por teclado del `TabbedPane` de webforJ para hacer la interacción con el `TabbedPane` más fluida e intuitiva para el usuario final

- **Pestaña Predeterminada**: Si la pestaña predeterminada no se coloca al principio del `TabbedPane`, considera establecer esta pestaña como predeterminada para información esencial o comúnmente utilizada.
