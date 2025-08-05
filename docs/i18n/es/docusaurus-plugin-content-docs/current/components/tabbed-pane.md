---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: 4f2421ca62abb88a3edd750e7887d2db
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

La clase `TabbedPane` proporciona una forma compacta y organizada de mostrar contenido que se divide en múltiples secciones, cada una asociada con una `Tab`. Los usuarios pueden alternar entre estas secciones haciendo clic en las pestañas respectivas, a menudo etiquetadas con texto y/o íconos. Esta clase simplifica la creación de interfaces complejas donde diferentes contenidos o formularios necesitan ser accesibles pero no visibles al mismo tiempo.

## Usos {#usages}

La clase `TabbedPane` ofrece a los desarrolladores una herramienta poderosa para organizar y presentar múltiples pestañas o secciones dentro de una interfaz de usuario. Aquí hay algunos escenarios típicos en los que podrías utilizar un `TabbedPane` en tu aplicación:

1. **Visor de Documentos**: Implementando un visor de documentos donde cada pestaña representa un documento o archivo diferente. Los usuarios pueden alternar fácilmente entre documentos abiertos para una multitarea eficiente.

2. **Gestión de Datos:** Utiliza un `TabbedPane` para organizar tareas de gestión de datos, por ejemplo:
    >- Diferentes conjuntos de datos que se mostrarán en una aplicación
    >- Varios perfiles de usuario pueden mostrarse dentro de pestañas separadas
    >- Diferentes perfiles en un sistema de gestión de usuarios

3. **Selección de Módulos**: Un `TabbedPane` puede representar diferentes módulos o secciones. Cada pestaña puede encapsular las funcionalidades de un módulo específico, permitiendo a los usuarios centrarse en un aspecto de la aplicación a la vez.

4. **Gestión de Tareas**: Las aplicaciones de gestión de tareas pueden usar un `TabbedPane` para representar varios proyectos o tareas. Cada pestaña podría corresponder a un proyecto específico, permitiendo a los usuarios gestionar y seguir tareas por separado.

5. **Navegación de Programas**: Dentro de una aplicación que necesita ejecutar varios programas, un `TabbedPane` podría:
    >- Servir como una barra lateral que permite ejecutar diferentes aplicaciones o programas dentro de una sola aplicación, como se muestra en la plantilla [`AppLayout`](./app-layout.md)
    >- Crear una barra superior que puede cumplir un propósito similar, o representar sub-aplicaciones dentro de una aplicación ya seleccionada.

## Pestañas {#tabs}

Las pestañas son elementos de interfaz de usuario que se pueden agregar a los paneles de pestañas para organizar y alternar entre diferentes vistas de contenido.

:::important
Las pestañas no están destinadas a ser utilizadas como componentes independientes. Deben usarse en conjunto con paneles de pestañas. Esta clase no es un `Component` y no debe usarse como tal.
:::

### Propiedades {#properties}

Las pestañas se componen de las siguientes propiedades, que luego se utilizan al agregarlas a un `TabbedPane`. Estas propiedades tienen getters y setters para facilitar la personalización dentro de un `TabbedPane`.

1. **Clave(`Object`)**: Representa el identificador único para la `Tab`.

2. **Texto(`String`)**: El texto que se mostrará como título para la `Tab` dentro del `TabbedPane`. Esto también se conoce como el título a través de los métodos `getTitle()` y `setTitle(String title)`.

3. **Tooltip(`String`)**: El texto de tooltip que se asocia con la `Tab`, que se mostrará cuando el cursor pase sobre la `Tab`.

4. **Habilitado(`boolean`)**: Representa si la `Tab` está actualmente habilitada o no. Se puede modificar con el método `setEnabled(boolean enabled)`.

5. **Cerrable(`boolean`)**: Representa si la `Tab` se puede cerrar. Se puede modificar con el método `setCloseable(boolean enabled)`. Esto agregará un botón de cierre en la `Tab` que puede ser clickeado por el usuario y dispara un evento de eliminación. El componente `TabbedPane` dictará cómo manejar la eliminación.

6. **Slot(`Component`)**: 
    Los slots proporcionan opciones flexibles para mejorar la capacidad de una `Tab`. Puedes tener íconos, etiquetas, spinners de carga, capacidad de limpiar/restablecer, imágenes de avatar/perfil, y otros componentes beneficiosos anidados dentro de una `Tab` para aclarar aún más el significado previsto a los usuarios.
    Puedes agregar un componente al slot `prefix` de una `Tab` durante la construcción. Alternativamente, puedes usar los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de una `Tab`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documentos", TablerIcon.create("files")));
        ```

## Manipulación de `Tab` {#tab-manipulation}

Existen varios métodos que permiten a los desarrolladores agregar, insertar, eliminar y manipular varias propiedades de elementos `Tab` dentro del `TabbedPane`.

### Agregando una `Tab` {#adding-a-tab}

Los métodos `addTab()` y `add()` existen en diferentes capacidades sobrecargadas para permitir a los desarrolladores flexibilidad al agregar nuevas pestañas al `TabbedPane`. Agregar una `Tab` la colocará después de cualquier pestaña existente previamente.

1. **`addTab(String text)`** - Agrega una `Tab` al `TabbedPane` con el `String` especificado como texto de la `Tab`.
2. **`addTab(Tab tab)`** - Agrega la `Tab` proporcionada como parámetro al `TabbedPane`.
3. **`addTab(String text, Component component)`** - Agrega una `Tab` con el `String` dado como texto de la `Tab`, y el `Component` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Agrega la `Tab` proporcionada y muestra el `Component` proporcionado en la sección de contenido del `TabbedPane`.
5. **`add(Component... component)`** - Agrega una o más instancias de `Component` al `TabbedPane`, creando una `Tab` discreta para cada una, con el texto establecido en el nombre del `Component`.

:::info
El `add(Component... component)` determina el nombre del `Component` pasado llamando a `component.getName()` en el argumento pasado.
:::

### Insertando una `Tab` {#inserting-a-tab}

Además de agregar una `Tab` al final de las pestañas existentes, también es posible crear una nueva en una posición designada. Para hacer esto, hay múltiples versiones sobrecargadas del `insertTab()`.

1. **`insertTab(int index, String text)`** - Inserta una `Tab` en el `TabbedPane` en el índice dado con el `String` especificado como texto de la `Tab`.
2. **`insertTab(int index, Tab tab)`** - Inserta la `Tab` proporcionada como parámetro en el `TabbedPane` en el índice especificado.
3. **`insertTab(int index, String text, Component component)`** - Inserta una `Tab` con el `String` dado como texto de la `Tab`, y el `Component` proporcionado mostrado en la sección de contenido del `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Inserta la `Tab` proporcionada y muestra el `Component` proporcionado en la sección de contenido del `TabbedPane`.

### Eliminando una `Tab` {#removing-a-tab}

Para eliminar una sola `Tab` del `TabbedPane`, utiliza uno de los siguientes métodos:

1. **`removeTab(Tab tab)`** - Elimina una `Tab` del `TabbedPane` pasando la instancia de la Tab a eliminar.
2. **`removeTab(int index)`** - Elimina una `Tab` del `TabbedPane` especificando el índice de la `Tab` a eliminar.

Además de los dos métodos anteriores para la eliminación de una sola `Tab`, utiliza el método **`removeAllTabs()`** para limpiar el `TabbedPane` de todas las pestañas.

:::info
Los métodos `remove()` y `removeAll()` no eliminan pestañas dentro del componente.
:::

### Asociación Tab/Componente {#tabcomponent-association}

Para cambiar el `Component` que se mostrará para una dada `Tab`, llama al método `setComponentFor()` y pasa ya sea la instancia de la `Tab`, o el índice de esa Tab dentro del `TabbedPane`.

:::info
Si este método se usa en una `Tab` que ya está asociada con un `Component`, el `Component` previamente asociado será destruido.
:::

## Configuración y diseño {#configuration-and-layout}

La clase `TabbedPane` tiene dos partes constitutivas: una `Tab` que se muestra en una ubicación especificada, y un componente que se mostrará. Esto puede ser un solo componente, o un [`Composite`](../building-ui/composite-components), permitiendo la visualización de componentes más complejos dentro de la sección de contenido de una pestaña.

### Deslizamiento {#swiping}

El `TabbedPane` admite la navegación a través de las diferentes pestañas mediante deslizamiento. Esto es ideal para una aplicación móvil, pero también se puede configurar mediante un método integrado para admitir el deslizamiento con el mouse. Tanto el deslizamiento como el deslizamiento del mouse están desactivados por defecto, pero se pueden habilitar con los métodos `setSwipable(boolean)` y `setSwipableWithMouse(boolean)` respectivamente. 

### Colocación de pestañas {#tab-placement}

Las `Tabs` dentro de un `TabbedPane` se pueden colocar en varias posiciones dentro del componente según la preferencia de los desarrolladores de la aplicación. Las opciones proporcionadas se establecen utilizando el enum proporcionado, que tiene los valores de `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, o `HIDDEN`. La configuración predeterminada es `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Alineación {#alignment}

Además de cambiar la colocación de los elementos `Tab` dentro del `TabbedPane`, también es posible configurar cómo se alinearán las pestañas dentro del componente. Por defecto, el ajuste `AUTO` está en efecto, lo que permite que la colocación de las pestañas dicte su alineación.

Las otras opciones son `START`, `END`, `CENTER`, y `STRETCH`. Las primeras tres describen la posición en relación con el componente, mientras que `STRETCH` hace que las pestañas llenen el espacio disponible.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Borde e indicador de actividad {#border-and-activity-indicator}

El `TabbedPane` tendrá un borde mostrado para las pestañas dentro de él por defecto, colocado dependiendo de qué `Placement` se haya establecido. Este borde ayuda a visualizar el espacio que las diversas pestañas dentro del panel ocupan. 

Cuando se hace clic en una `Tab`, por defecto, se muestra un indicador de actividad cerca de esa `Tab` para ayudar a resaltar cuál es la `Tab` actualmente seleccionada.

Ambas opciones se pueden personalizar por un desarrollador cambiando los valores booleanos utilizando los métodos setter apropiados. Para cambiar si se muestra o no el borde, se puede utilizar el método `setBorderless(boolean)`, donde `true` oculta el borde, y `false`, el valor predeterminado, muestra el borde.

:::info
Este borde no se aplica a la totalidad del componente `TabbedPane`, y simplemente sirve como separador entre las pestañas y el contenido del componente.
:::

Para establecer la visibilidad del indicador activo, se puede usar el método `setHideActiveIndicator(boolean)`. Pasar `true` a este método ocultará el indicador activo debajo de una `Tab` activa, mientras que `false`, el valor predeterminado, mantendrá el indicador mostrado.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Modos de activación {#activation-modes}

Para un control más detallado sobre cómo se comporta el `TabbedPane` al ser navegado mediante el teclado, se puede establecer el modo de `Activation` para especificar cómo debe comportarse el componente.

- **`Auto`**: Cuando se establece en automático, navegar por las pestañas con las teclas de dirección mostrará instantáneamente el componente de la pestaña correspondiente.

- **`Manual`**: Cuando se establece en manual, la pestaña recibirá el enfoque, pero no se mostrará hasta que el usuario presione espacio o enter.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Opciones de eliminación {#removal-options}

Los elementos individuales `Tab` pueden configurarse para ser cerrables. Las pestañas cerrables tendrán un botón de cierre agregado a la pestaña, que dispara un evento de cierre cuando se hace clic. El `TabbedPane` dicta cómo se maneja este comportamiento.

- **`Manual`**: Por defecto, la eliminación se establece en `MANUAL`, lo que significa que se dispara el evento, pero depende del desarrollador manejar este evento de la manera que elija.

- **`Auto`**: Alternativamente, se puede usar `AUTO`, que disparará el evento, y también eliminará la `Tab` del componente para el desarrollador, eliminando la necesidad de que el desarrollador implemente este comportamiento manualmente.

## Estilo {#styling}

### Expansión y tema {#expanse-and-theme}

El `TabbedPane` viene con opciones de `Expanse` y `Theme` integradas similares a otros componentes de webforJ. Estos se pueden usar para agregar rápidamente estilos que transmitan varios significados al usuario final sin necesidad de estilizar el componente con CSS.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Mejores prácticas {#best-practices}

Las siguientes prácticas se recomiendan para utilizar el `TabbedPane` dentro de aplicaciones:

- **Agrupación Lógica**: Usa pestañas para agrupar lógicamente contenido relacionado
    >- Cada pestaña debe representar una categoría o funcionalidad distinta dentro de tu aplicación
    >- Agrupa pestañas similares o lógicas cerca unas de otras

- **Pocas Pestañas**: Evita abrumar a los usuarios con demasiadas pestañas. Considera usar una estructura jerárquica u otros patrones de navegación donde sea aplicable para una interfaz limpia.

- **Etiquetas Claras**: Etiqueta claramente tus Pestañas para un uso intuitivo
    >- Proporciona etiquetas claras y concisas para cada pestaña
    >- Las etiquetas deben reflejar el contenido o propósito, facilitando la comprensión a los usuarios
    >- Utiliza íconos y colores distintos donde sea aplicable

- **Navegación por Teclado**: Usa el soporte de navegación por teclado del `TabbedPane` de webforJ para hacer la interacción con el `TabbedPane` más fluida e intuitiva para el usuario final.

- **Pestaña Predeterminada**: Si la pestaña predeterminada no está colocada al principio del `TabbedPane`, considera establecer esta pestaña como predeterminada para información esencial o comúnmente utilizada.
