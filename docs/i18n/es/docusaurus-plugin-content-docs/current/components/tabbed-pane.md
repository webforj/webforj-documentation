---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 544ab11783e8369075f1c02aba2d8dc8
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Se pueden organizar múltiples secciones de contenido bajo un único `TabbedPane`, donde cada sección está asociada a un `Tab` que se puede hacer clic. Solo una sección es visible a la vez, y los tabs pueden mostrar texto, íconos o ambos para ayudar a los usuarios a navegar entre ellos.

<!-- INTRO_END -->

## Usos {#usages}

La clase `TabbedPane` ofrece a los desarrolladores una herramienta poderosa para organizar y presentar múltiples tabs o secciones dentro de una interfaz de usuario. Aquí hay algunos escenarios típicos donde podrías usar un `TabbedPane` en tu aplicación:

1. **Visor de Documentos**: Implementando un visor de documentos donde cada tab representa un documento o archivo diferente. Los usuarios pueden cambiar fácilmente entre los documentos abiertos para un multitasking eficiente.

2. **Gestión de Datos**: Usa un `TabbedPane` para organizar tareas de gestión de datos, por ejemplo:
    - Diferentes conjuntos de datos para ser mostrados en una aplicación
    - Varios perfiles de usuario que pueden ser mostrados en tabs separados
    - Diferentes perfiles en un sistema de gestión de usuarios

3. **Selección de Módulos**: Un `TabbedPane` puede representar diferentes módulos o secciones. Cada tab puede encapsular las funcionalidades de un módulo específico, permitiendo a los usuarios enfocarse en un aspecto de la aplicación a la vez.

4. **Gestión de Tareas**: Las aplicaciones de gestión de tareas pueden utilizar un `TabbedPane` para representar varios proyectos o tareas. Cada tab podría corresponder a un proyecto específico, permitiendo a los usuarios gestionar y rastrear tareas por separado.

5. **Navegación de Aplicaciones**: Dentro de una aplicación que necesita ejecutar varios programas, un `TabbedPane` podría:
    - Servir como una barra lateral que permite ejecutar diferentes aplicaciones o programas dentro de una única aplicación, como se muestra en la plantilla [`AppLayout`](./app-layout.md)
    - Crear una barra superior que puede servir un propósito similar, o representar subaplicaciones dentro de una aplicación ya seleccionada

## Tabs {#tabs}

Los tabs son elementos de interfaz de usuario que pueden ser añadidos a los paneles tabulados para organizarse y cambiar entre diferentes vistas de contenido.

:::important
Los tabs no están destinados a ser utilizados como componentes independientes. Están destinados a ser usados en conjunto con paneles tabulados. Esta clase no es un `Component` y no debería ser utilizada como tal.
:::

### Propiedades {#properties}

Los tabs están compuestos de las siguientes propiedades, que se utilizan al añadirlos en un `TabbedPane`. Estas propiedades tienen métodos getters y setters para facilitar la personalización dentro de un `TabbedPane`.

1. **Texto(`String`)**: El texto que se muestra como título para el `Tab` dentro del `TabbedPane`. Esto también se refiere como el título a través de los métodos `getTitle()` y `setTitle(String title)`.

2. **Tooltip(`String`)**: El texto del tooltip asociado con el `Tab`, que se muestra cuando el cursor se desplaza sobre el `Tab`.

3. **Habilitado(`boolean`)**: Representa si el `Tab` está habilitado o no. Se puede modificar con el método `setEnabled(boolean enabled)`.

4. **Cerrable(`boolean`)**: Representa si el `Tab` puede ser cerrado. Se puede modificar con el método `setClosable(boolean closable)` que añadirá un botón de cerrar en el `Tab` que puede ser clicado, y genera un evento de eliminación. El componente `TabbedPane` dicta cómo manejar la eliminación.

5. **Slot(`Component`)**:
    Los slots proporcionan opciones flexibles para mejorar la capacidad de un `Tab`. Puedes tener íconos, etiquetas, indicadores de carga, capacidad de limpiar/restablecer, imágenes de avatar/perfil y otros componentes beneficiosos anidados dentro de un `Tab` para aclarar aún más el significado destinado a los usuarios.
    Puedes añadir un componente al slot `prefix` de un `Tab` durante la construcción. Alternativamente, puedes usar los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de un `Tab`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## Manipulación de `Tab` {#tab-manipulation}

Existen varios métodos que permiten a los desarrolladores añadir, insertar, eliminar y manipular varias propiedades de elementos `Tab` dentro del `TabbedPane`.

### Añadiendo un `Tab` {#adding-a-tab}

Los métodos `addTab()` y `add()` existen en diferentes capacidades sobrecargadas para permitir a los desarrolladores flexibilidad en la adición de nuevos tabs al `TabbedPane`. Añadir un `Tab` lo colocará después de cualquier tab existente previamente.

1. **`addTab(String text)`**: añade un `Tab` al `TabbedPane` con el `String` especificado como el texto del `Tab`.
2. **`addTab(Tab tab)`**: añade el `Tab` proporcionado como parámetro al `TabbedPane`.
3. **`addTab(String text, Component component)`**: añade un `Tab` con el `String` dado como el texto del `Tab`, y el `Component` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`addTab(Tab tab, Component component)`**: añade el `Tab` proporcionado y muestra el `Component` proporcionado en la sección de contenido del `TabbedPane`.
5. **`add(Component... component)`**: añade una o más instancias de `Component` al `TabbedPane`, creando un `Tab` discreto para cada uno, con el texto establecido en el nombre del `Component`.

:::info
El `add(Component... component)` determina el nombre del `Component` pasado llamando a `component.getName()` en el argumento pasado.
:::

### Insertando un `Tab` {#inserting-a-tab}

Además de añadir un `Tab` al final de los tabs existentes, también es posible crear uno nuevo en una posición designada. Para hacerlo, múltiples versiones sobrecargadas del `insertTab()`.

1. **`insertTab(int index, String text)`**: inserta un `Tab` en el `TabbedPane` en el índice dado con el `String` especificado como el texto del `Tab`.
2. **`insertTab(int index, Tab tab)`**: inserta el `Tab` proporcionado como parámetro al `TabbedPane` en el índice especificado.
3. **`insertTab(int index, String text, Component component)`**: inserta un `Tab` con el `String` dado como el texto del `Tab`, y el `Component` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`**: inserta el `Tab` proporcionado y muestra el `Component` proporcionado en la sección de contenido del `TabbedPane`.

### Eliminando un `Tab` {#removing-a-tab}

Para eliminar un único `Tab` del `TabbedPane`, utiliza uno de los siguientes métodos:

1. **`removeTab(Tab tab)`**: elimina un `Tab` del `TabbedPane` pasando la instancia del Tab a eliminar.
2. **`removeTab(int index)`**: elimina un `Tab` del `TabbedPane` especificando el índice del `Tab` a eliminar.

Además de los dos métodos anteriores para la eliminación de un solo `Tab`, usa el método **`removeAllTabs()`** para limpiar el `TabbedPane` de todos los tabs.

:::info
Los métodos `remove()` y `removeAll()` no eliminan tabs dentro del componente.
:::

### Asociación Tab/Componente {#tabcomponent-association}

Para cambiar el `Component` que se mostrará para un dado `Tab`, llama al método `setComponentFor()`, y pasa ya sea la instancia del `Tab`, o el índice de ese Tab dentro del `TabbedPane`.

:::info
Si este método se utiliza en un `Tab` que ya está asociado con un `Component`, el `Component` previamente asociado será destruido.
:::

## Configuración y diseño {#configuration-and-layout}

La clase `TabbedPane` tiene dos partes constitutivas: un `Tab` que se muestra en una ubicación específica, y un componente que se va a mostrar. Esto puede ser un solo componente, o un componente [`Composite`](/docs/building-ui/composing-components), permitiendo la visualización de componentes más complejos dentro de la sección de contenido de un tab.

### Deslizamiento {#swiping}

El `TabbedPane` admite la navegación a través de los diversos tabs mediante deslizamiento. Esto es ideal para una aplicación móvil, pero también puede configurarse a través de un método incorporado para soportar el deslizamiento del mouse. Tanto el deslizamiento como el deslizamiento del mouse están deshabilitados por defecto, pero se pueden habilitar con los métodos `setSwipeable(boolean)` y `setSwipeWithMouse(boolean)`, respectivamente.

### Colocación de Tabs {#tab-placement}

Los `Tabs` dentro de un `TabbedPane` pueden colocarse en varias posiciones dentro del componente según la preferencia del desarrollador de la aplicación. Las opciones proporcionadas se establecen utilizando el enum proporcionado, que tiene los valores de `TOP`, `BOTTOM`, `LEFT`, `RIGHT` o `HIDDEN`. La configuración predeterminada es `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Alineación {#alignment}

Además de cambiar la colocación de los elementos `Tab` dentro del `TabbedPane`, también es posible configurar cómo se alinearán los tabs dentro del componente. Por defecto, la configuración `AUTO` está en efecto, lo que permite que la colocación de los tabs dicte su alineación.

Las otras opciones son `START`, `END`, `CENTER` y `STRETCH`. Las tres primeras describen la posición relativa al componente, mientras que `STRETCH` hace que los tabs llenen el espacio disponible.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Borde e indicador de actividad {#border-and-activity-indicator}

El `TabbedPane` mostrará un borde para los tabs dentro de él de forma predeterminada, colocado dependiendo de cuál `Placement` se haya configurado. Este borde ayuda a visualizar el espacio que ocupan los diferentes tabs dentro del panel.

Cuando se hace clic en un `Tab`, por defecto, se muestra un indicador de actividad cerca de ese `Tab` para ayudar a resaltar cuál es el `Tab` actualmente seleccionado.

Ambas opciones pueden personalizarse cambiando los valores booleanos usando los métodos setter apropiados. Para cambiar si se muestra o no el borde, se puede usar el método `setBorderless(boolean)`, donde `true` oculta el borde, y `false`, el valor predeterminado, muestra el borde.

:::info
Este borde no se aplica a la totalidad del componente `TabbedPane`, y simplemente sirve como un separador entre los tabs y el contenido del componente.
:::

Para establecer la visibilidad del indicador activo, se puede usar el método `setHideActiveIndicator(boolean)`. Pasar `true` a este método ocultará el indicador activo bajo un `Tab` activo, mientras que `false`, el valor predeterminado, mantendrá el indicador mostrado.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Modos de activación {#activation-modes}

Para un control más fino sobre cómo se comporta el `TabbedPane` con la navegación por teclado, se puede establecer el modo de `Activation` para especificar cómo debería comportarse el componente.

- **`Auto`**: cuando se establece en automático, navegar por los tabs con las teclas de flecha mostrará instantáneamente el componente de tab correspondiente.

- **`Manual`**: cuando se establece en manual, el tab recibirá enfoque pero no se mostrará hasta que el usuario presione espacio o enter.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Opciones de eliminación {#removal-options}

Los elementos individuales `Tab` pueden configurarse para ser cerrables. Los tabs cerrables tendrán un botón de cerrar añadido al tab, que genera un evento de cierre cuando se hace clic. El `TabbedPane` dicta cómo se maneja este comportamiento.

- **`Manual`**: por defecto, la eliminación se establece en `MANUAL`, lo que significa que el evento se genera, pero depende del desarrollador manejar este evento de la forma que elija.

- **`Auto`**: alternativamente, se puede usar `AUTO`, que generará el evento y también eliminará el `Tab` del componente para el desarrollador, eliminando la necesidad de que el desarrollador implemente este comportamiento manualmente.

### Control de segmento <DocChip chip='since' label='26.00' /> {#segment-control}

El `TabbedPane` puede representarse como un control de segmento habilitando la propiedad `segment` con `setSegment(true)`. En este modo, los tabs se muestran con un indicador deslizante que resalta la selección activa, proporcionando una alternativa compacta a la interfaz de tabs estándar.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Estilización {#styling}

### Expansión y tema {#expanse-and-theme}

El `TabbedPane` viene con opciones de `Expanse` y `Theme` integradas similares a otros componentes de webforJ. Estos se pueden usar para añadir rápidamente un estilo que transmita diversos significados al usuario final sin necesidad de estilizar el componente con CSS.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Mejores prácticas {#best-practices}

Las siguientes prácticas se recomiendan para usar el `TabbedPane` dentro de las aplicaciones:

- **Agrupación Lógica**: Usa tabs para agrupar lógicamente contenido relacionado:
    - Cada tab debe representar una categoría o funcionalidad distinta dentro de tu aplicación.
    - Agrupa tabs similares o lógicos cerca unos de otros.

- **Tabs Limitados**: Evita abrumar a los usuarios con demasiados tabs. Considera usar una estructura jerárquica u otros patrones de navegación donde sea aplicable para una interfaz limpia.

- **Etiquetas Claras**: Etiqueta claramente tus Tabs para un uso intuitivo:
    - Proporciona etiquetas claras y concisas para cada tab.
    - Las etiquetas deben reflejar el contenido o propósito, facilitando que los usuarios comprendan.
    - Usa íconos y colores distintos donde sea aplicable.

- **Navegación por Teclado**: Usa el soporte de navegación por teclado de webforJ para que la interacción con el `TabbedPane` sea más fluida e intuitiva para el usuario final.

- **Tab Predeterminado**: Si el tab predeterminado no está colocado al principio del `TabbedPane`, considera establecer este tab como predeterminado para información esencial o de uso común.
