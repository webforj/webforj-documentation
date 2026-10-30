---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 2d2e78638206283a7423cea736e3bf2c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Múltiples secciones de contenido pueden organizarse dentro de un solo `TabbedPane`, donde cada sección está vinculada a una `Tab` clickeable. Solo una sección es visible a la vez, y las pestañas pueden mostrar texto, íconos o ambos para ayudar a los usuarios a navegar entre ellas.

<!-- INTRO_END -->

## Usos {#usages}

La clase `TabbedPane` proporciona a los desarrolladores una herramienta poderosa para organizar y presentar múltiples pestañas o secciones dentro de una interfaz de usuario. Aquí hay algunos escenarios típicos donde podrías usar un `TabbedPane` en tu aplicación:

1. **Visor de documentos**: Implementar un visor de documentos donde cada pestaña representa un documento o archivo diferente. Los usuarios pueden cambiar fácilmente entre documentos abiertos para trabajar de manera eficiente.

2. **Gestión de datos**: Utiliza un `TabbedPane` para organizar las tareas de gestión de datos, por ejemplo:
    - Diferentes conjuntos de datos que se mostrarán en una aplicación.
    - Varios perfiles de usuario pueden mostrarse dentro de pestañas separadas.
    - Diferentes perfiles en un sistema de gestión de usuarios.

3. **Selección de módulos**: Un `TabbedPane` puede representar diferentes módulos o secciones. Cada pestaña puede encapsular las funcionalidades de un módulo específico, permitiendo a los usuarios enfocarse en un aspecto de la aplicación a la vez.

4. **Gestión de tareas**: Las aplicaciones de gestión de tareas pueden utilizar un `TabbedPane` para representar varios proyectos o tareas. Cada pestaña podría corresponder a un proyecto específico, permitiendo a los usuarios gestionar y realizar un seguimiento de las tareas por separado.

5. **Navegación por el programa**: Dentro de una aplicación que necesita ejecutar varios programas, un `TabbedPane` podría:
    - Servir como una barra lateral que permite ejecutar diferentes aplicaciones o programas dentro de una sola aplicación, como se muestra en la plantilla [`AppLayout`](./app-layout.md).
    - Crear una barra superior que puede cumplir una función similar o representar sub-aplicaciones dentro de una aplicación ya seleccionada.

## Pestañas <DocChip chip='name' label="dwc-tab" /> {#tabs}

Las pestañas son elementos de interfaz de usuario que se pueden agregar a los paneles de pestañas para organizar y cambiar entre diferentes vistas de contenido.

:::important
Las pestañas no están destinadas a ser utilizadas como componentes independientes. Están destinadas a ser utilizadas en conjunto con paneles de pestañas. Esta clase no es un `Component` y no debe usarse como tal.
:::

### Propiedades {#properties}

Las pestañas están compuestas por las siguientes propiedades, que se utilizan al agregarlas en un `TabbedPane`. Estas propiedades tienen métodos de acceso y modificación para facilitar su personalización dentro de un `TabbedPane`.

1. **Texto(`String`)**: El texto que se muestra como un título para la `Tab` dentro del `TabbedPane`. Esto también se conoce como el título a través de los métodos `getTitle()` y `setTitle(String title)`.

2. **Tooltip(`String`)**: El texto del tooltip asociado con la `Tab`, que se muestra cuando el cursor pasa por encima de la `Tab`.

3. **Habilitado(`boolean`)**: Representa si la `Tab` está habilitada o no. Puede modificarse con el método `setEnabled(boolean enabled)`.

4. **Cerrable(`boolean`)**: Representa si la `Tab` puede cerrarse. Puede modificarse con el método `setClosable(boolean closable)`. Esto añadirá un botón de cierre en la `Tab` el cual puede ser clickeado y genera un evento de eliminación. El componente `TabbedPane` dictará cómo manejar la eliminación.

5. **Slot(`Component`)**:
    Los slots proporcionan opciones flexibles para mejorar la capacidad de una `Tab`. Puedes tener íconos, etiquetas, indicadores de carga, capacidades para limpiar/restablecer, imágenes de avatar/perfil y otros componentes beneficiosos anidados dentro de una `Tab` para aclarar mejor el significado deseado para los usuarios.
    Puedes agregar un componente al slot `prefix` de una `Tab` durante la construcción. Alternativamente, puedes usar los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de una `Tab`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documentos", TablerIcon.create("files")));
        ```

## Manipulación de `Tab` {#tab-manipulation}

Existen varios métodos que permiten a los desarrolladores agregar, insertar, eliminar y manipular varias propiedades de elementos `Tab` dentro del `TabbedPane`.

### Agregando una `Tab` {#adding-a-tab}

Los métodos `addTab()` y `add()` existen en diferentes capacidades sobrecargadas para permitir a los desarrolladores flexibilidad en la adición de nuevas pestañas al `TabbedPane`. Agregar una `Tab` la colocará después de cualquier pestaña existente previamente.

1. **`addTab(String text)`**: agrega una `Tab` al `TabbedPane` con el `String` especificado como el texto de la `Tab`.
2. **`addTab(Tab tab)`**: agrega la `Tab` proporcionada como parámetro al `TabbedPane`.
3. **`addTab(String text, Component component)`**: agrega una `Tab` con el `String` dado como el texto de la `Tab`, y el `Component` proporcionado que se muestra en la sección de contenido del `TabbedPane`.
4. **`addTab(Tab tab, Component component)`**: agrega la `Tab` proporcionada y muestra el `Component` proporcionado en la sección de contenido del `TabbedPane`.
5. **`add(Component... component)`**: agrega una o más instancias de `Component` al `TabbedPane`, creando una `Tab` discreta para cada uno, y el texto se establecerá como el nombre del `Component`.

:::info
El método `add(Component... component)` determina el nombre del `Component` pasado llamando a `component.getName()` en el argumento pasado.
:::

### Insertando una `Tab` {#inserting-a-tab}

Además de agregar una `Tab` al final de las pestañas existentes, también es posible crear una nueva en una posición designada. Para hacer esto, existen múltiples versiones sobrecargadas del método `insertTab()`.

1. **`insertTab(int index, String text)`**: inserta una `Tab` en el `TabbedPane` en el índice dado con el `String` especificado como el texto de la `Tab`.
2. **`insertTab(int index, Tab tab)`**: inserta la `Tab` proporcionada como parámetro al `TabbedPane` en el índice especificado.
3. **`insertTab(int index, String text, Component component)`**: inserta una `Tab` con el `String` dado como el texto de la `Tab`, y el `Component` proporcionado que se muestra en la sección de contenido del `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`**: inserta la `Tab` proporcionada y muestra el `Component` proporcionado en la sección de contenido del `TabbedPane`.

### Eliminando una `Tab` {#removing-a-tab}

Para eliminar una sola `Tab` del `TabbedPane`, utiliza uno de los siguientes métodos:

1. **`removeTab(Tab tab)`**: elimina una `Tab` del `TabbedPane` pasando la instancia de Tab que se desea eliminar.
2. **`removeTab(int index)`**: elimina una `Tab` del `TabbedPane` especificando el índice de la `Tab` que se desea eliminar.

Además de los dos métodos anteriores para la eliminación de una sola `Tab`, utiliza el método **`removeAllTabs()`** para limpiar el `TabbedPane` de todas las pestañas.

:::info
Los métodos `remove()` y `removeAll()` no eliminan pestañas dentro del componente.
:::

### Asociación Tab/Componente {#tabcomponent-association}

Para cambiar el `Component` que se mostrará para una `Tab` dada, llama al método `setComponentFor()`, y pasa ya sea la instancia de la `Tab`, o el índice de esa Tab dentro del `TabbedPane`.

:::info
Si se usa este método en una `Tab` que ya está asociada con un `Component`, el `Component` previamente asociado será destruido.
:::

## Configuración y diseño {#configuration-and-layout}

La clase `TabbedPane` tiene dos partes constitutivas: una `Tab` que se muestra en una ubicación especificada, y un componente que se mostrará. Esto puede ser un solo componente, o un componente [`Composite`](/docs/building-ui/composing-components), permitiendo la visualización de componentes más complejos dentro de la sección de contenido de una pestaña.

### Deslizamiento {#swiping}

El `TabbedPane` soporta la navegación a través de las diversas pestañas mediante deslizamiento. Esto es ideal para una aplicación móvil, pero también se puede configurar mediante un método integrado para soportar el deslizamiento con el mouse. Tanto el deslizamiento como el deslizamiento con mouse están desactivados por defecto, pero pueden ser habilitados con los métodos `setSwipeable(boolean)` y `setSwipeWithMouse(boolean)`, respectivamente.

### Colocación de pestañas {#tab-placement}

Las `Tabs` dentro de un `TabbedPane` pueden colocarse en varias posiciones dentro del componente según la preferencia del desarrollador de la aplicación. Las opciones proporcionadas se establecen utilizando el enum provisto, que tiene los valores de `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, o `HIDDEN`. La configuración predeterminada es `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Alineación {#alignment}

Además de cambiar la colocación de los elementos `Tab` dentro del `TabbedPane`, también es posible configurar cómo se alinearán las pestañas dentro del componente. Por defecto, la configuración `AUTO` está en efecto, lo que permite que la colocación de las pestañas dictamine su alineación.

Las otras opciones son `START`, `END`, `CENTER`, y `STRETCH`. Las tres primeras describen la posición en relación con el componente, siendo `STRETCH` para que las pestañas llenen el espacio disponible.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Borde e indicador de actividad {#border-and-activity-indicator}

El `TabbedPane` tendrá un borde mostrado para las pestañas dentro de él por defecto, colocado dependiendo de la `Placement` que se haya establecido. Este borde ayuda a visualizar el espacio que ocupan las diversas pestañas dentro del panel.

Cuando una `Tab` es clickeada, por defecto, se muestra un indicador de actividad cerca de esa `Tab` para ayudar a resaltar cuál es la `Tab` actualmente seleccionada.

Ambas opciones pueden ser personalizadas mediante el cambio de los valores booleanos utilizando los métodos de establecimiento apropiados. Para cambiar si se muestra o no el borde, se puede usar el método `setBorderless(boolean)`, con `true` ocultando el borde y `false`, el valor predeterminado, mostrando el borde.

:::info
Este borde no se aplica a la totalidad del componente `TabbedPane`, y simplemente sirve como un separador entre las pestañas y el contenido del componente.
:::

Para establecer la visibilidad del indicador activo, se puede usar el método `setHideActiveIndicator(boolean)`. Pasar `true` a este método ocultará el indicador activo debajo de una `Tab` activa, mientras que `false`, el valor predeterminado, mantiene el indicador visible.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Modos de activación {#activation-modes}

Para un control más detallado sobre cómo se comporta el `TabbedPane` con la navegación por teclado, se puede establecer el modo de `Activation` para especificar cómo debería comportarse el componente.

- **`Auto`**: cuando se establece en automático, navegar por las pestañas con las teclas de flecha mostrará instantáneamente el componente de la pestaña correspondiente.

- **`Manual`**: cuando se establece en manual, la pestaña recibirá el foco pero no se mostrará hasta que el usuario presione espacio o enter.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Opciones de eliminación {#removal-options}

Los elementos `Tab` individuales pueden configurarse para que sean cerrables. Las pestañas cerrables tendrán un botón de cierre agregado a la pestaña, que genera un evento de cierre al ser clickeado. El `TabbedPane` dicta cómo se maneja este comportamiento.

- **`Manual`**: por defecto, la eliminación se establece en `MANUAL`, lo que significa que se genera el evento, pero depende del desarrollador manejar este evento de cualquier manera que elija.

- **`Auto`**: alternativamente, se puede utilizar `AUTO`, que generará el evento y también eliminará la `Tab` del componente para el desarrollador, eliminando la necesidad de que el desarrollador implemente este comportamiento manualmente.

### Control de segmento <DocChip chip='since' label='26.00' /> {#segment-control}

El `TabbedPane` puede representarse como un control de segmento habilitando la propiedad `segment` con `setSegment(true)`. En este modo, las pestañas se muestran con un indicador de píldora deslizante que resalta la selección activa, proporcionando una alternativa compacta a la interfaz de pestañas estándar.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Estilo {#styling}

### Expansión y tema {#expanse-and-theme}

El `TabbedPane` viene con opciones integradas de `Expanse` y `Theme` similares a otros componentes de webforJ. Estos se pueden usar para agregar rápidamente estilo que transmita varios significados al usuario final sin la necesidad de estilizar el componente con CSS.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Mejores prácticas {#best-practices}

Las siguientes prácticas se recomiendan para usar el `TabbedPane` dentro de aplicaciones:

- **Agrupación lógica**: Usa pestañas para agrupar lógicamente contenido relacionado:
    - Cada pestaña debe representar una categoría o funcionalidad distinta dentro de tu aplicación.
    - Agrupa pestañas similares o lógicas cerca unas de otras.

- **Pocas pestañas**: Evita abrumar a los usuarios con demasiadas pestañas. Considera usar una estructura jerárquica u otros patrones de navegación donde sea aplicable para una interfaz limpia.

- **Etiquetas claras**: Etiqueta claramente tus pestañas para un uso intuitivo:
    - Proporciona etiquetas claras y concisas para cada pestaña.
    - Las etiquetas deben reflejar el contenido o propósito, facilitando la comprensión a los usuarios.
    - Usa íconos y colores distintos donde sea aplicable.

- **Navegación por teclado**: Usa el soporte de navegación por teclado de webforJ para hacer la interacción con el `TabbedPane` más fluida e intuitiva para el usuario final.

- **Pestaña predeterminada**: Si la pestaña predeterminada no está colocada al principio del `TabbedPane`, considera establecer esta pestaña como predeterminada para información esencial o de uso común.
