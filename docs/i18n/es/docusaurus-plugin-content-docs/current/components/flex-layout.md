---
title: FlexLayout
sidebar_position: 45
_i18n_hash: bd3f6177603a98c20d4958a9c40dd49f
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ proporciona a los desarrolladores una manera eficiente e intuitiva de organizar diversas aplicaciones y componentes: el Flex Layout. Este conjunto de herramientas permite que los elementos se muestren ya sea de forma vertical u horizontal.

## Propiedades del layout flex {#flex-layout-properties}

Las propiedades del layout flex se pueden agrupar en dos categorías: propiedades que se aplican a los elementos dentro de un layout y propiedades que se aplican al propio layout. El layout flex, o el elemento padre, es una caja/contenedor que puede contener uno o más componentes. Todo lo que hay dentro de un Flex Layout se llama un elemento o elemento hijo. El Flex Layout proporciona algunas capacidades de alineación sólidas, que se pueden lograr con la ayuda de propiedades de contenedor o de ítem.

:::tip
El componente de layout de webforJ sigue el patrón del [layout flexbox de CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Sin embargo, estas herramientas están hechas para ser utilizadas completamente en Java y no requieren la aplicación de CSS fuera de los métodos de la API de Java proporcionados.
:::

## Propiedades del contenedor {#container-properties}

Las propiedades del contenedor se aplicarán a todos los componentes dentro de un componente y no al layout en sí. No afectarán la orientación o colocación del padre, solo los componentes hijos dentro.

### Dirección {#direction}

El Flex Layout añadirá componentes uno al lado del otro de acuerdo con la dirección elegida por el desarrollador, ya sea horizontal o vertical. Al usar el constructor, utiliza los métodos `horizontal()`, `horizontalReverse()`, `vertical()` o `verticalReverse()` al llamar al método `create()` en un objeto `FlexLayout` para configurar este layout mientras se crea el objeto.

Alternativamente, usa el método `setDirection()`. Las opciones horizontales son `FlexDirection.ROW` (de izquierda a derecha) o `FlexDirection.ROW_REVERSE` (de derecha a izquierda), y las opciones verticales son `FlexDirection.COLUMN` (de arriba a abajo) o `FlexDirection.COLUMN_REVERSE` (de abajo a arriba). Esto se hace con el objeto FlexLayout, en lugar de con el constructor.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Posicionamiento {#positioning}

Los componentes que se añaden horizontalmente también se pueden posicionar tanto horizontal como verticalmente. Usa los métodos `justify()`, `align()` y `contentAlign()` del Flex Layout Builder para configurar el posicionamiento al crear un nuevo Flex Layout.

Alternativamente, en el objeto FlexLayout real puedes usar el método `setJustifyContent()` para posicionar elementos horizontalmente, y el método `setAlignment()` para configurar la posición vertical. Para modificar el área alrededor de los componentes a lo largo del eje cruzado (eje y para layouts horizontales), usa el método `setAlignContent()`.

:::tip
El método `setAlignment()` controla cómo se mostrarán los elementos a lo largo del eje cruzado como un todo dentro del contenedor, y es efectivo para layouts de una sola línea.

El método `setAlignContent()` controla el espacio alrededor del eje cruzado, y tendrá efecto solo cuando un layout tenga múltiples líneas.
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Ajuste {#wrapping}

Para personalizar aún más el componente Flex Layout, puedes especificar el comportamiento del layout flex cuando los componentes añadidos ya no encajan dentro de la pantalla. Para configurar esto usando el constructor, utiliza los métodos `nowrap()` (predeterminado), `wrap()` y `wrapReverse()` para configurar el ajuste.

Alternativamente, si tu layout ya existe, usa el método `setWrap()` para dictar cómo se comportarán los componentes una vez que ya no puedan encajar en una sola línea.

### Espaciado {#spacing}

Para aplicar un espaciado mínimo entre los elementos, puedes establecer la propiedad gap. Aplica ese espaciado solo entre elementos y no en los bordes externos.

El comportamiento de la propiedad gap puede pensarse como una distancia mínima entre - esta propiedad solo tendrá efecto si es el mayor espacio calculado entre los elementos. Si el espacio entre elementos sería de otro modo mayor debido a otra propiedad calculada, como por ejemplo `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, entonces la propiedad gap será ignorada.

### Flujo {#flow}

El flujo flex, que es una combinación de las propiedades de dirección y ajuste, puede ser establecido usando el método `setFlow()` en un objeto Flex Layout.

:::info
Para configurar esta propiedad al crear el layout, utiliza los métodos de dirección y ajuste adecuados. Por ejemplo, para crear un flujo de ajuste de columna, utiliza la combinación `.vertical().wrap()`.
:::

### Constructor de contenedores {#container-builder}

La siguiente demo te permite construir un contenedor con las propiedades flex deseadas seleccionadas de los diversos menús. Esta herramienta puede ser utilizada no solo para crear un ejemplo visual de los diversos métodos, sino también como una herramienta para crear tus propios layouts con tus propiedades deseadas. Para usar un layout que personalices, simplemente copia el código de salida y añade tus elementos deseados para usarlos en tu programa.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Propiedades del ítem {#item-properties}

Las propiedades del ítem no afectarán a ningún elemento hijo dentro del Flex Layout, sino al propio Layout. Esto es útil para estilizar un solo elemento de Flex Layout que es un hijo de un elemento Flex Layout más grande de manera independiente de los estilos aplicados a todos los hijos.

### Orden {#order}

La propiedad `ItemOrder` determina cómo se muestran los componentes dentro del Flex Layout, y cuando se usa en un Flex Layout asignará a un ítem un número de orden específico de ese layout. Esto anula el "orden de origen" predeterminado que está integrado en cada ítem (el orden en que se añade un componente a su padre), y significa que se mostrará antes que los ítems con un orden más alto, y después que los ítems con un orden más bajo.

Esta propiedad acepta un valor entero sin unidad que especifica el orden relativo del ítem flex dentro del contenedor. Cuanto más bajo sea el valor, antes aparezca el ítem en el orden. Por ejemplo, un ítem con un valor de orden de 1 aparecerá antes que un ítem con un valor de orden de 2.

:::caution
Es importante notar que la propiedad de orden solo afecta el orden visual de los ítems dentro del contenedor, no su posición real en el DOM. Esto significa que los lectores de pantalla y otras tecnologías asistivas aún leerán los ítems en el orden en que aparecen en el código fuente, no en el orden visual.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Auto alineación {#self-alignment}

La auto alineación del Flex Layout se refiere a cómo un solo objeto Flex Layout se alinea dentro de su contenedor flex padre a lo largo del eje cruzado, que es perpendicular al eje principal. La alineación del eje cruzado se controla mediante la propiedad `Alignment`.

La propiedad align-self especifica la alineación de un solo ítem flex a lo largo del eje cruzado, sobrescribiendo la alineación predeterminada establecida por la propiedad `AlignContent` en un objeto Flex Layout. Esto te permite alinear objetos individuales Flex Layout de manera diferente a los demás en el contenedor.

:::info
La auto alineación utiliza los mismos valores que la alineación del contenido
:::

Esta propiedad es especialmente útil cuando necesitas alinear un ítem específico de manera diferente a los otros ítems en el contenedor. Consulta el siguiente ejemplo para un ejemplo de alineación de un solo ítem:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex base {#flex-basis}

`Item Basis` es una propiedad que se utiliza junto con la dirección del Flex Layout para establecer el tamaño inicial de un ítem flex antes de que se distribuyan los espacios restantes.

La propiedad `Item Basis` especifica el tamaño predeterminado de un ítem flex a lo largo del eje principal, que puede ser horizontal (para una dirección de fila) o vertical (para una dirección de columna). Esta propiedad establece el ancho o la altura de un ítem flex dependiendo del valor de la propiedad de dirección flex.

:::info
Por defecto, la propiedad `Item Basis` se establece en auto, lo que significa que el tamaño del ítem se determina por su contenido. Sin embargo, también puedes establecer un tamaño específico para el ítem utilizando varias unidades como píxeles (px), ems (em), porcentajes (%) o cualquier otra unidad de longitud CSS.
:::

La siguiente demo te permite seleccionar una o más cajas y cambiar el `Item Basis` para los ítems seleccionados.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex crecer / encoger {#flex-grow--shrink}

`Item Grow` y `Item Shrink` son dos propiedades que funcionan en conjunto entre sí y con la propiedad `Item Basis` para determinar cómo los ítems flex crecen o se encojen para llenar el espacio disponible dentro de un objeto Flex Layout.

La propiedad `Item Grow` especifica cuánto puede crecer el ítem flex en relación con los otros ítems en el contenedor. Toma un valor sin unidad que representa una proporción del espacio disponible que debe ser asignado al ítem. Por ejemplo, si un ítem tiene un valor de `Item Grow` de 1 y otro tiene un valor de 2, el segundo ítem crecerá el doble que el primer ítem.

La propiedad `Item Shrink`, por otro lado, especifica cuánto puede encogerse el ítem flex en relación con los otros ítems en el contenedor. También toma un valor sin unidad que representa una proporción del espacio disponible que debe ser asignado al ítem. Por ejemplo, si un ítem tiene un valor de `Item Shrink` de 1 y otro tiene un valor de 2, el segundo ítem se encogerá el doble que el primer ítem.

Cuando un contenedor tiene más espacio del necesario para acomodar su contenido, los ítems flex con un valor de `Item Grow` mayor que 0 se expandirán para llenar el espacio disponible. La cantidad de espacio que cada ítem recibe se determina por la proporción de su valor de `Item Grow` al valor total de `Item Grow` de todos los ítems en el contenedor.

De manera similar, cuando un contenedor no tiene suficiente espacio para acomodar su contenido, los ítems flex con un valor de `Item Shrink` mayor que 0 se encogerán para ajustarse al espacio disponible. La cantidad de espacio que cada ítem cede se determina por la proporción de su valor de `Item Shrink` al valor total de `Item Shrink` de todos los ítems en el contenedor.

## Formulario de ejemplo {#example-form}
El formulario a continuación demuestra cómo `FlexLayout` organiza los campos de entrada en un layout estructurado.

:::tip
Si prefieres una estructura basada en columnas, consulta la versión ColumnsLayout de este formulario en el artículo [`ColumnsLayout`](../components/columns-layout) para ver cómo se compara.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
