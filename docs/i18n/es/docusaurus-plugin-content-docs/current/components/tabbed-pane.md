---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
_i18n_hash: 790dce3f2bce2da54e03b7407c11204b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Múltiples secciones de contenido pueden organizarse bajo un solo `TabbedPane`, donde cada sección está vinculada a una `Tab` clicable. Solo una sección es visible a la vez, y las pestañas pueden mostrar texto, íconos o ambos para ayudar a los usuarios a navegar entre ellas.

<!-- INTRO_END -->

## Usos {#usages}

La clase `TabbedPane` proporciona a los desarrolladores una herramienta poderosa para organizar y presentar múltiples pestañas o secciones dentro de una interfaz de usuario. Aquí hay algunos escenarios típicos donde podrías utilizar un `TabbedPane` en tu aplicación:

1. **Visor de Documentos**: Implementar un visor de documentos donde cada pestaña representa un documento o archivo diferente. Los usuarios pueden cambiar fácilmente entre documentos abiertos para una multitarea eficiente.

2. **Gestión de Datos:** Utiliza un `TabbedPane` para organizar tareas de gestión de datos, por ejemplo:
    >- Diferentes conjuntos de datos que se muestran en una aplicación
    >- Varios perfiles de usuario pueden mostrarse dentro de pestañas separadas
    >- Diferentes perfiles en un sistema de gestión de usuarios

3. **Selección de Módulos**: Un `TabbedPane` puede representar diferentes módulos o secciones. Cada pestaña puede encapsular las funcionalidades de un módulo específico, permitiendo a los usuarios concentrarse en un aspecto de la aplicación a la vez.

4. **Gestión de Tareas**: Las aplicaciones de gestión de tareas pueden usar un `TabbedPane` para representar varios proyectos o tareas. Cada pestaña podría corresponder a un proyecto específico, permitiendo a los usuarios gestionar y rastrear tareas por separado.

5. **Navegación del Programa**: Dentro de una aplicación que necesita ejecutar varios programas, un `TabbedPane` podría:
    >- Servir como una barra lateral que permite ejecutar diferentes aplicaciones o programas dentro de una sola aplicación, como se muestra en la plantilla [`AppLayout`](./app-layout.md)
    >- Crear una barra superior que puede cumplir una función similar o representar sub-aplicaciones dentro de una aplicación ya seleccionada.
  
## Pestañas {#tabs}

Las pestañas son elementos de interfaz de usuario que se pueden añadir a los paneles de pestañas para organizar y alternar entre diferentes vistas de contenido.

:::important
Las pestañas no están destinadas a ser utilizadas como componentes independientes. Deben utilizarse en conjunto con paneles de pestañas. Esta clase no es un `Componente` y no debe ser utilizada como tal.
:::

### Propiedades {#properties}

Las pestañas se componen de las siguientes propiedades, que se utilizan al añadirlas en un `TabbedPane`. Estas propiedades tienen métodos getter y setter para facilitar la personalización dentro de un `TabbedPane`.

1. **Key(`Object`)**: Representa el identificador único para la `Tab`.

2. **Text(`String`)**: El texto que se mostrará como título para la `Tab` dentro del `TabbedPane`. Esto también se conoce como el título a través de los métodos `getTitle()` y `setTitle(String title)`.

3. **Tooltip(`String`)**: El texto de ayuda asociado con la `Tab`, que se mostrará cuando el cursor pase sobre la `Tab`.

4. **Enabled(`boolean`)**: Representa si la `Tab` está actualmente habilitada o no. Puede ser modificada con el método `setEnabled(boolean enabled)`.

5. **Closeable(`boolean`)**: Representa si la `Tab` puede ser cerrada. Puede ser modificada con el método `setCloseable(boolean enabled)`. Esto añadirá un botón de cerrar en la `Tab` que puede ser clicado por el usuario, y dispara un evento de eliminación. El componente `TabbedPane` dicta cómo manejar la eliminación.

6. **Slot(`Component`)**: 
    Los slots proporcionan opciones flexibles para mejorar la capacidad de una `Tab`. Puedes tener íconos, etiquetas, spinners de carga, capacidad de limpiar/restablecer, fotos de avatar/perfil, y otros componentes beneficiosos anidados dentro de una `Tab` para aclarar aún más el significado intencionado para los usuarios.
    Puedes añadir un componente al slot de `prefix` de una `Tab` durante la construcción. Alternativamente, puedes usar los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de una `Tab`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## Manipulación de `Tab` {#tab-manipulation}

Existen varios métodos para permitir a los desarrolladores añadir, insertar, eliminar y manipular diversas propiedades de los elementos `Tab` dentro del `TabbedPane`.

### Añadiendo una `Tab` {#adding-a-tab}

Los métodos `addTab()` y `add()` existen en diferentes capacidades sobrecargadas para permitir a los desarrolladores flexibilidad al añadir nuevas pestañas al `TabbedPane`. Añadir una `Tab` la colocará después de cualquier pestaña previamente existente.

1. **`addTab(String text)`** - Añade una `Tab` al `TabbedPane` con el `String` especificado como el texto de la `Tab`.
2. **`addTab(Tab tab)`** - Añade la `Tab` proporcionada como parámetro al `TabbedPane`.
3. **`addTab(String text, Component component)`** - Añade una `Tab` con el `String` dado como el texto de la `Tab`, y el `Component` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Añade la `Tab` proporcionada y muestra el `Component` proporcionado en la sección de contenido del `TabbedPane`.
5. **`add(Component... component)`** - Añade una o más instancias de `Component` al `TabbedPane`, creando una `Tab` discreta para cada una, con el texto establecido en el nombre del `Component`.

:::info
El `add(Component... component)` determina el nombre del `Component` pasado al llamar a `component.getName()` en el argumento pasado.
:::

### Insertando una `Tab` {#inserting-a-tab}

Además de añadir una `Tab` al final de las pestañas existentes, también es posible crear una nueva en una posición designada. Para hacer esto, existen múltiples versiones sobrecargadas de `insertTab()`. 

1. **`insertTab(int index, String text)`** - Inserta una `Tab` en el `TabbedPane` en el índice dado con el `String` especificado como texto de la `Tab`.
2. **`insertTab(int index, Tab tab)`** - Inserta la `Tab` proporcionada como parámetro al `TabbedPane` en el índice especificado.
3. **`insertTab(int index, String text, Component component)`** - Inserta una `Tab` con el `String` dado como texto de la `Tab`, y el `Component` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Inserta la `Tab` proporcionada y muestra el `Component` proporcionado en la sección de contenido del `TabbedPane`.

### Eliminando una `Tab` {#removing-a-tab}

Para eliminar una sola `Tab` del `TabbedPane`, usa uno de los siguientes métodos:

1. **`removeTab(Tab tab)`** - Elimina una `Tab` del `TabbedPane` pasando la instancia de Tab a eliminar.
2. **`removeTab(int index)`** - Elimina una `Tab` del `TabbedPane` especificando el índice de la `Tab` a eliminar.

Además de los dos métodos anteriores para la eliminación de una sola `Tab`, usa el método **`removeAllTabs()`** para limpiar el `TabbedPane` de todas las pestañas.

:::info
Los métodos `remove()` y `removeAll()` no eliminan pestañas dentro del componente.
:::

### Asociación Tab/Componente {#tabcomponent-association}

Para cambiar el `Component` que se mostrará para una `Tab` dada, llama al método `setComponentFor()` y pasa ya sea la instancia de la `Tab`, o el índice de esa Tab dentro del `TabbedPane`.

:::info
Si este método se usa en una `Tab` que ya está asociada con un `Component`, el `Component` previamente asociado será destruido.
:::

## Configuración y diseño {#configuration-and-layout}

La clase `TabbedPane` tiene dos partes constitutivas: una `Tab` que se muestra en una ubicación especificada, y un componente que se mostrará. Este puede ser un solo componente, o un componente [`Composite`](../building-ui/composite-components), lo que permite la visualización de componentes más complejos dentro de la sección de contenido de una pestaña.

### Deslizamiento {#swiping}

El `TabbedPane` soporta la navegación a través de las diversas pestañas mediante deslizamiento. Esto es ideal para una aplicación móvil, pero también puede configurarse a través de un método incorporado para soportar el deslizamiento del mouse. Tanto el deslizamiento como el deslizamiento del mouse están desactivados por defecto, pero pueden ser habilitados con los métodos `setSwipable(boolean)` y `setSwipableWithMouse(boolean)`, respectivamente. 

### Ubicación de la pestaña {#tab-placement}

Las `Tabs` dentro de un `TabbedPane` pueden colocarse en varias posiciones dentro del componente según la preferencia del desarrollador de la aplicación. Las opciones proporcionadas se establecen utilizando el enum proporcionado, que tiene los valores de `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, o `HIDDEN`. La configuración predeterminada es `TOP`.


<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Alineación {#alignment}

Además de cambiar la ubicación de los elementos `Tab` dentro del `TabbedPane`, también es posible configurar cómo se alinearán las pestañas dentro del componente. Por defecto, la configuración `AUTO` está en efecto, lo que permite que la colocación de las pestañas dicte su alineación.

Las otras opciones son `START`, `END`, `CENTER`, y `STRETCH`. Las tres primeras describen la posición relativa al componente, mientras que `STRETCH` hace que las pestañas llenen el espacio disponible.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Borde e indicador de actividad {#border-and-activity-indicator}

El `TabbedPane` tendrá un borde mostrado para las pestañas dentro de él de forma predeterminada, colocado según la `Placement` que se haya establecido. Este borde ayuda a visualizar el espacio que las diversas pestañas dentro del panel ocupan. 

Cuando se hace clic en una `Tab`, por defecto, se muestra un indicador de actividad cerca de esa `Tab` para ayudar a resaltar cuál es la `Tab` actualmente seleccionada.

Ambas opciones pueden personalizarse por un desarrollador cambiando los valores booleanos utilizando los métodos setter apropiados. Para cambiar si se muestra o no el borde, se puede usar el método `setBorderless(boolean)`, con `true` ocultando el borde, y `false`, el valor predeterminado, mostrando el borde.

:::info
Este borde no se aplica a la totalidad del componente `TabbedPane`, y simplemente sirve como un separador entre las pestañas y el contenido del componente.
:::

Para establecer la visibilidad del indicador activo, se puede usar el método `setHideActiveIndicator(boolean)`. Pasar `true` a este método ocultará el indicador activo bajo una `Tab` activa, mientras que `false`, el valor predeterminado, mantendrá el indicador mostrado.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Modos de activación {#activation-modes}

Para un control más detallado sobre cómo se comporta el `TabbedPane` cuando se navega con el teclado, se puede establecer el modo de `Activación` para especificar cómo debe comportarse el componente.

- **`Auto`**: Cuando se configura en automático, navegar las pestañas con las teclas de flecha mostrará instantáneamente el componente de la pestaña correspondiente.

- **`Manual`**: Cuando se configura en manual, la pestaña recibirá el foco pero no se mostrará hasta que el usuario presione espacio o enter.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Opciones de eliminación {#removal-options}

Los elementos individuales de `Tab` pueden configurarse para ser cerrables. Las pestañas cerrables tendrán un botón de cerrar añadido a la pestaña, que dispara un evento de cierre al ser clicado. El `TabbedPane` dicta cómo se maneja este comportamiento.

- **`Manual`**: Por defecto, la eliminación se configura en `MANUAL`, lo que significa que se dispara el evento, pero depende del desarrollador manejar este evento de la manera que elija.

- **`Auto`**: Alternativamente, se puede utilizar `AUTO`, que disparará el evento y también eliminará la `Tab` del componente para el desarrollador, eliminando la necesidad de que el desarrollador implemente este comportamiento manualmente. 

### Control de segmento <DocChip chip='since' label='26.00' /> {#segment-control}

El `TabbedPane` puede renderizarse como un control de segmento habilitando la propiedad `segment` con `setSegment(true)`. En este modo, las pestañas se muestran con un indicador de pastilla deslizante que resalta la selección activa, proporcionando una alternativa compacta a la interfaz de pestañas estándar. 

<ComponentDemo 
path='/webforj/tabbedpanesegment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java'
height="250px"
/>

## Estilización {#styling}

### Expanse y tema {#expanse-and-theme}

El `TabbedPane` viene con opciones integradas de `Expanse` y `Tema` similares a otros componentes de webforJ. Estos se pueden utilizar para añadir rápidamente estilos que transmitan varios significados al usuario final sin necesidad de estilizar el componente con CSS.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Mejores prácticas {#best-practices}

Las siguientes prácticas se recomiendan para utilizar el `TabbedPane` dentro de las aplicaciones:

- **Agrupación Lógica**: Usa pestañas para agrupar lógicamente el contenido relacionado
    >- Cada pestaña debe representar una categoría o funcionalidad distinta dentro de tu aplicación
    >- Agrupar pestañas similares o lógicas cerca unas de otras

- **Pocas Pestañas**: Evita abrumar a los usuarios con demasiadas pestañas. Considera usar una estructura jerárquica u otros patrones de navegación donde sea aplicable para una interfaz limpia

- **Etiquetas Claras**: Etiqueta claramente tus Pestañas para un uso intuitivo
    >- Proporciona etiquetas claras y concisas para cada pestaña
    >- Las etiquetas deben reflejar el contenido o propósito, facilitando a los usuarios entender
    >- Utiliza íconos y colores distintos donde sea aplicable

- **Navegación por Teclado** Usa la compatibilidad de navegación por teclado del `TabbedPane` de webforJ para hacer que la interacción con el `TabbedPane` sea más fluida e intuitiva para el usuario final

- **Pestaña Predeterminada**: Si la pestaña predeterminada no está colocada al principio del `TabbedPane`, considera establecer esta pestaña como predeterminada para información esencial o comúnmente utilizada.
