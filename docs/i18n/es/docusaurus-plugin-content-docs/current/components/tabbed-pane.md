---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 563f9251b928e2e9426d69d4b5192880
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Se pueden organizar múltiples secciones de contenido bajo un solo `TabbedPane`, donde cada sección está vinculada a un `Tab` clickeable. Solo una sección es visible a la vez, y las pestañas pueden mostrar texto, íconos o ambos para ayudar a los usuarios a navegar entre ellas.

<!-- INTRO_END -->

## Usos {#usages}

La clase `TabbedPane` proporciona a los desarrolladores una herramienta poderosa para organizar y presentar múltiples pestañas o secciones dentro de una interfaz de usuario. Aquí hay algunos escenarios típicos donde podrías utilizar un `TabbedPane` en tu aplicación:

1. **Visor de documentos**: Implementar un visor de documentos donde cada pestaña representa un documento o archivo diferente. Los usuarios pueden cambiar fácilmente entre documentos abiertos para una multitarea eficiente.

2. **Gestión de datos:** Utiliza un `TabbedPane` para organizar tareas de gestión de datos, por ejemplo:
    >- Diferentes conjuntos de datos a ser mostrados en una aplicación
    >- Varios perfiles de usuario pueden ser mostrados en pestañas separadas
    >- Diferentes perfiles en un sistema de gestión de usuarios

3. **Selección de módulo**: Un `TabbedPane` puede representar diferentes módulos o secciones. Cada pestaña puede encapsular las funcionalidades de un módulo específico, permitiendo a los usuarios centrarse en un aspecto de la aplicación a la vez.

4. **Gestión de tareas**: Las aplicaciones de gestión de tareas pueden usar un `TabbedPane` para representar varios proyectos o tareas. Cada pestaña podría corresponder a un proyecto específico, permitiendo a los usuarios gestionar y seguir las tareas por separado.

5. **Navegación del programa**: Dentro de una aplicación que necesita ejecutar varios programas, un `TabbedPane` podría:
    >- Servir como una barra lateral que permite ejecutar diferentes aplicaciones o programas dentro de una sola aplicación, como se muestra en la plantilla [`AppLayout`](./app-layout.md)
    >- Crear una barra superior que puede cumplir un propósito similar, o representar sub-aplicaciones dentro de una aplicación ya seleccionada.

## Pestañas {#tabs}

Las pestañas son elementos de interfaz de usuario que pueden ser añadidos a paneles tabulados para organizar y cambiar entre diferentes vistas de contenido.

:::important
Las pestañas no están destinadas a ser utilizadas como componentes independientes. Deben ser utilizadas en conjunto con paneles tabulados. Esta clase no es un `Componente` y no debe ser utilizada como tal.
:::

### Propiedades {#properties}

Las pestañas están compuestas de las siguientes propiedades, que se utilizan al añadirlas en un `TabbedPane`. Estas propiedades tienen métodos de obtención y establecimiento para facilitar la personalización dentro de un `TabbedPane`.

1. **Clave(`Object`)**: Representa el identificador único para la `Pestaña`.

2. **Texto(`String`)**: El texto que se mostrará como un título para la `Pestaña` dentro del `TabbedPane`. Esto también se conoce como el título a través de los métodos `getTitle()` y `setTitle(String title)`.

3. **Tooltip(`String`)**: El texto de la etiqueta que está asociado con la `Pestaña`, que se mostrará cuando el cursor pase sobre la `Pestaña`.

4. **Habilitado(`boolean`)**: Representa si la `Pestaña` está actualmente habilitada o no. Se puede modificar con el método `setEnabled(boolean enabled)`.

5. **Cerrable(`boolean`)**: Representa si la `Pestaña` puede ser cerrada. Se puede modificar con el método `setCloseable(boolean enabled)`. Esto añadirá un botón de cierre en la `Pestaña` que puede ser clickeado por el usuario, y genera un evento de eliminación. El componente `TabbedPane` dicta cómo manejar la eliminación.

6. **Slot(`Componente`)**:
    Los slots proporcionan opciones flexibles para mejorar la capacidad de una `Pestaña`. Puedes tener íconos, etiquetas, cargadores, capacidad de limpiar/restablecer, fotos de avatar/perfil, y otros componentes beneficiosos anidados dentro de una `Pestaña` para aclarar aún más el significado pretendido a los usuarios.
    Puedes añadir un componente al slot de `prefijo` de una `Pestaña` durante la construcción. Alternativamente, puedes utilizar los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de una `Pestaña`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documentos", TablerIcon.create("files")));
        ```

## Manipulación de `Tab` {#tab-manipulation}

Existen varios métodos que permiten a los desarrolladores añadir, insertar, eliminar y manipular varias propiedades de elementos `Tab` dentro del `TabbedPane`.

### Añadiendo una `Pestaña` {#adding-a-tab}

Los métodos `addTab()` y `add()` existen en diferentes capacidades sobrecargadas para permitir a los desarrolladores flexibilidad al añadir nuevas pestañas al `TabbedPane`. Añadir una `Pestaña` la colocará después de cualquier pestaña ya existente.

1. **`addTab(String texto)`** - Añade una `Pestaña` al `TabbedPane` con el `String` especificado como el texto de la `Pestaña`.
2. **`addTab(Tab pestaña)`** - Añade la `Pestaña` proporcionada como parámetro al `TabbedPane`.
3. **`addTab(String texto, Componente componente)`** - Añade una `Pestaña` con el `String` dado como el texto de la `Pestaña`, y el `Componente` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`addTab(Tab pestaña, Componente componente)`** - Añade la `Pestaña` proporcionada y muestra el `Componente` proporcionado en la sección de contenido del `TabbedPane`.
5. **`add(Componente... componente)`** - Añade una o más instancias de `Componente` al `TabbedPane`, creando una `Pestaña` discreta para cada uno, con el texto establecido al nombre del `Componente`.

:::info
El `add(Componente... componente)` determina el nombre del `Componente` pasado llamando a `component.getName()` en el argumento pasado.
:::

### Insertando una `Pestaña` {#inserting-a-tab}

Además de añadir una `Pestaña` al final de las pestañas existentes, también es posible crear una nueva en una posición designada. Para hacer esto, existen múltiples versiones sobrecargadas de `insertTab()`.

1. **`insertTab(int índice, String texto)`** - Inserta una `Pestaña` en el `TabbedPane` en el índice dado con el `String` especificado como el texto de la `Pestaña`.
2. **`insertTab(int índice, Tab pestaña)`** - Inserta la `Pestaña` proporcionada como parámetro al `TabbedPane` en el índice especificado.
3. **`insertTab(int índice, String texto, Componente componente)`** - Inserta una `Pestaña` con el `String` dado como el texto de la `Pestaña`, y el `Componente` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`insertTab(int índice, Tab pestaña, Componente componente)`** - Inserta la `Pestaña` proporcionada y muestra el `Componente` proporcionado en la sección de contenido del `TabbedPane`.

### Eliminando una `Pestaña` {#removing-a-tab}

Para eliminar una sola `Pestaña` del `TabbedPane`, utiliza uno de los siguientes métodos:

1. **`removeTab(Tab pestaña)`** - Elimina una `Pestaña` del `TabbedPane` pasando la instancia de la Pestaña a ser eliminada.
2. **`removeTab(int índice)`** - Elimina una `Pestaña` del `TabbedPane` especificando el índice de la `Pestaña` a ser eliminada.

Además de los dos métodos anteriores para la eliminación de una sola `Pestaña`, utiliza el método **`removeAllTabs()`** para limpiar el `TabbedPane` de todas las pestañas.

:::info
Los métodos `remove()` y `removeAll()` no eliminan pestañas dentro del componente.
:::

### Asociación Tab/Componente {#tabcomponent-association}

Para cambiar el `Componente` a ser mostrado para una `Pestaña` dada, llama al método `setComponentFor()` y pasa ya sea la instancia de la `Pestaña`, o el índice de esa Pestaña dentro del `TabbedPane`.

:::info
Si este método se utiliza en una `Pestaña` que ya está asociada con un `Componente`, el `Componente` previamente asociado será destruido.
:::

## Configuración y diseño {#configuration-and-layout}

La clase `TabbedPane` tiene dos partes constitutivas: una `Pestaña` que se muestra en una ubicación específica, y un componente a ser mostrado. Esto puede ser un solo componente, o un componente [`Compuesto`](/docs/building-ui/composing-components), permitiendo la visualización de componentes más complejos dentro de la sección de contenido de una pestaña.

### Deslizamiento {#swiping}

El `TabbedPane` admite la navegación a través de varias pestañas por medio de deslizamientos. Esto es ideal para una aplicación móvil, pero también puede configurarse a través de un método incorporado para soportar el deslizamiento con el ratón. Tanto el deslizamiento como el deslizamiento con el ratón están desactivados por defecto, pero pueden habilitarse con los métodos `setSwipable(boolean)` y `setSwipableWithMouse(boolean)`, respectivamente.

### Colocación de pestañas {#tab-placement}

Las `Pestañas` dentro de un `TabbedPane` pueden colocarse en varias posiciones dentro del componente según la preferencia del desarrollador de la aplicación. Las opciones proporcionadas se establecen utilizando el enum proporcionado, que tiene los valores de `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, o `HIDDEN`. La configuración predeterminada es `TOP`.


<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Alineación {#alignment}

Además de cambiar la colocación de los elementos de `Pestaña` dentro del `TabbedPane`, también es posible configurar cómo se alinearán las pestañas dentro del componente. Por defecto, la configuración `AUTO` está en efecto, lo que permite que la colocación de las pestañas dictamine su alineación.

Las otras opciones son `START`, `END`, `CENTER`, y `STRETCH`. Las tres primeras describen la posición relativa al componente, y `STRETCH` hace que las pestañas llenen el espacio disponible.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Borde e indicador de actividad {#border-and-activity-indicator}

El `TabbedPane` tendrá un borde mostrado para las pestañas dentro de él por defecto, colocado dependiendo de qué `Placement` se haya establecido. Este borde ayuda a visualizar el espacio que las diversas pestañas dentro del panel ocupan.

Cuando se hace clic en una `Pestaña`, por defecto, se muestra un indicador de actividad cerca de esa `Pestaña` para ayudar a resaltar cuál es la `Pestaña` actualmente seleccionada.

Ambas opciones pueden ser personalizadas por un desarrollador cambiando los valores booleanos utilizando los métodos de establecimiento apropiados. Para cambiar si se muestra o no el borde, se puede utilizar el método `setBorderless(boolean)`, con `true` ocultando el borde, y `false`, el valor predeterminado, mostrando el borde.

:::info
Este borde no se aplica a la totalidad del componente `TabbedPane`, y simplemente sirve como un separador entre las pestañas y el contenido del componente.
:::

Para establecer la visibilidad del indicador activo, se puede utilizar el método `setHideActiveIndicator(boolean)`. Pasar `true` a este método ocultará el indicador activo debajo de una `Pestaña` activa, mientras que `false`, el valor predeterminado, mantiene el indicador mostrado.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Modos de activación {#activation-modes}

Para un control más preciso sobre cómo se comporta el `TabbedPane` al ser navegado por teclado, el modo de `Activación` puede configurarse para especificar cómo debe comportarse el componente.

- **`Automático`**: Cuando se establece en automático, navegar por las pestañas con las teclas de flecha mostrará instantáneamente el componente de pestaña correspondiente.

- **`Manual`**: Cuando se establece en manual, la pestaña recibirá el foco pero no se mostrará hasta que el usuario presione espacio o enter.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Opciones de eliminación {#removal-options}

Los elementos de `Pestaña` individuales pueden configurarse como cerrables. Las pestañas cerrables tendrán un botón de cierre añadido a la pestaña, que genera un evento de cierre al hacer clic. El `TabbedPane` dicta cómo se maneja este comportamiento.

- **`Manual`**: Por defecto, la eliminación se establece en `MANUAL`, lo que significa que se genera el evento, pero corresponde al desarrollador manejar este evento de la manera que elija.

- **`Automático`**: Alternativamente, se puede utilizar `AUTO`, que generará el evento, y también eliminará la `Pestaña` del componente para el desarrollador, eliminando la necesidad de que el desarrollador implemente este comportamiento manualmente.

### Control de segmento <DocChip chip='since' label='26.00' /> {#segment-control}

El `TabbedPane` puede renderizarse como un control de segmento habilitando la propiedad `segment` con `setSegment(true)`. En este modo, las pestañas se muestran con un indicador de píldora deslizante que resalta la selección activa, proporcionando una alternativa compacta a la interfaz de pestañas estándar.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Estilo {#styling}

### Expansión y tema {#expanse-and-theme}

El `TabbedPane` viene con opciones de `Expansión` y `Tema` integradas similares a otros componentes de webforJ. Estas se pueden usar para añadir rápidamente estilos que transmiten diversos significados al usuario final sin necesidad de estilizar el componente con CSS.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Mejores prácticas {#best-practices}

Las siguientes prácticas se recomiendan para utilizar el `TabbedPane` dentro de las aplicaciones:

- **Agrupamiento lógico**: Usa pestañas para agrupar lógicamente contenido relacionado
    >- Cada pestaña debe representar una categoría o funcionalidad distinta dentro de tu aplicación
    >- Agrupa pestañas similares o lógicas cerca unas de otras

- **Pocas pestañas**: Evita abrumar a los usuarios con demasiadas pestañas. Considera usar una estructura jerárquica u otros patrones de navegación cuando sea aplicable para una interfaz limpia

- **Etiquetas claras**: Etqueta claramente tus pestañas para un uso intuitivo
    >- Proporciona etiquetas claras y concisas para cada pestaña
    >- Las etiquetas deben reflejar el contenido o propósito, facilitando la comprensión a los usuarios
    >- Utiliza íconos y colores distintos cuando sea aplicable

- **Navegación por teclado**: Utiliza el soporte de navegación por teclado del `TabbedPane` de webforJ para hacer que la interacción con el `TabbedPane` sea más fluida e intuitiva para el usuario final

- **Pestaña predeterminada**: Si la pestaña predeterminada no se coloca al inicio del `TabbedPane`, considera establecer esta pestaña como predeterminada para información esencial o de uso común.
