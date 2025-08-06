---
title: FlexLayout
sidebar_position: 45
_i18n_hash: df051e46de48f07807bf0dc3bcaa641a
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ proporciona a los desarrolladores una manera eficiente e intuitiva de estructurar sus diversas aplicaciones y componentes: el Flex Layout. Este conjunto de herramientas permite que los elementos se muestren de manera vertical u horizontal.

## Propiedades del layout flex {#flex-layout-properties}

Las propiedades del layout flex se pueden agrupar en dos categorías: propiedades que se aplican a los elementos dentro de un layout, y propiedades que se aplican al layout en sí. El layout flex, o el elemento padre, es una caja/contenedor que puede contener uno o más componentes. Todo lo que se encuentra dentro de un Flex Layout se denomina un elemento o un elemento hijo. El Flex Layout proporciona robustas capacidades de alineación, que pueden lograrse con la ayuda de propiedades de contenedor o de elementos.

:::tip
El componente de layout de webforJ sigue el patrón del [layout flex de CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Sin embargo, estas herramientas están diseñadas para ser utilizadas completamente en Java, y no requieren la aplicación de CSS fuera de los métodos de la API de Java proporcionados.
:::

## Propiedades del contenedor {#container-properties}

Las propiedades del contenedor se aplicarán a todos los componentes dentro de un componente y no al layout en sí. No afectarán la orientación o ubicación del padre, solo los componentes hijos dentro.

### Dirección {#direction}

El Flex Layout añadirá componentes uno al lado del otro según la dirección elegida por el desarrollador, ya sea horizontal o vertical. Al utilizar el constructor, utiliza los métodos `horizontal()`, `horizontalReverse()`, `vertical()` o `verticalReverse()` al llamar al método `create()` en un objeto `FlexLayout` para configurar este layout a medida que se crea el objeto.

Alternativamente, utiliza el método `setDirection()`. Las opciones horizontales son `FlexDirection.ROW` (de izquierda a derecha) o `FlexDirection.ROW_REVERSE` (de derecha a izquierda), y las opciones verticales son `FlexDirection.COLUMN` (de arriba hacia abajo) o `FlexDirection.COLUMN_REVERSE` (de abajo hacia arriba). Esto se hace con el objeto FlexLayout, en lugar de con el constructor.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Posicionamiento {#positioning}

Los componentes que se añaden de forma horizontal también se pueden posicionar tanto horizontal como verticalmente. Utiliza los métodos `justify()`, `align()` y `contentAlign()` del Flex Layout Builder para configurar el posicionamiento al crear un nuevo Flex Layout.

Alternativamente, en el objeto FlexLayout real, puedes utilizar el método `setJustifyContent()` para posicionar los elementos horizontalmente, y el método `setAlignment()` para configurar el posicionamiento vertical. Para modificar el área alrededor de los componentes a lo largo del eje cruzado (eje y para layouts horizontales), utiliza el método `setAlignContent()`.

:::tip
El método `setAlignment()` configura cómo se mostrarán los elementos a lo largo del eje cruzado en conjunto dentro del contenedor, y es efectivo para layouts de una sola línea.

Los métodos `setAlignContent()` configuran el espacio alrededor del eje cruzado, y tendrán efecto solo cuando un layout tenga múltiples líneas.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Ajuste {#wrapping}

Para personalizar aún más el componente Flex Layout, puedes especificar el comportamiento del layout flex cuando los componentes añadidos ya no caben dentro de la visualización. Para configurar esto utilizando el constructor, utiliza los métodos `nowrap()` (predeterminado), `wrap()` y `wrapReverse()` para configurar el ajuste.

Alternativamente, si tu layout ya existe, utiliza el método `setWrap()` para dictar cómo se comportarán los componentes una vez que ya no puedan caber en una sola línea.

### Espaciado {#spacing}

Para aplicar un espaciado mínimo entre los elementos, puedes establecer la propiedad gap. Aplica este espaciado solo entre los elementos que no están en los bordes exteriores.

El comportamiento de la propiedad gap puede pensarse como una distancia mínima entre - esta propiedad solo tendrá efecto si es el espacio calculado más grande entre los elementos. Si el espacio entre los elementos es de otro modo más grande debido a otra propiedad calculada, como resultado de `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, entonces la propiedad gap será ignorada.

### Flujo {#flow}

El flujo flex, que es una combinación de la dirección y las propiedades de ajuste, se puede establecer utilizando el método `setFlow()` en un objeto Flex Layout.

:::info
Para configurar esta propiedad al crear el layout, utiliza los métodos direccionales y de ajuste apropiados. Por ejemplo, para crear un flujo de ajuste en columna, utiliza la combinación `.vertical().wrap()`.
:::

### Constructor de contenedor {#container-builder}

La siguiente demostración te permite construir un contenedor con las propiedades flex deseadas seleccionadas de los diversos menús. Esta herramienta se puede utilizar no solo para crear un ejemplo visual de los diversos métodos, sino también como una herramienta para crear tus propios layouts con las propiedades deseadas. Para usar un layout que personalices, simplemente copia el código de salida y agrega tus elementos deseados para su uso en tu programa.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

## Propiedades del elemento {#item-properties}

Las propiedades de los elementos no afectarán a ningún elemento hijo dentro del Flex Layout, sino más bien al propio Layout. Esto es útil para estilizar un único elemento de Flex Layout que es hijo de un elemento Flex Layout más grande de forma independiente de lo que se aplica a todos los hijos.

### Orden {#order}

La propiedad `ItemOrder` determina cómo se muestran los componentes dentro del Flex Layout, y cuando se utiliza en un Flex Layout asignará a un elemento un número de orden específico para ese layout. Esto anula el "orden de origen" predeterminado que está incorporado en cada elemento (el orden en el que se añade un componente a su padre), y significa que se mostrará antes que los elementos con un orden más alto y después que los elementos con un orden más bajo.

Esta propiedad acepta un valor entero sin unidad que especifica el orden relativo del elemento flex dentro del contenedor. Cuanto menor es el valor, antes aparece el elemento en el orden. Por ejemplo, un elemento con un valor de orden de 1 aparecerá antes que un elemento con un valor de orden de 2.

:::caution
Es importante destacar que la propiedad de orden solo afecta el orden visual de los elementos dentro del contenedor, no su posición real en el DOM. Esto significa que los lectores de pantalla y otras tecnologías de asistencia seguirán leyendo los elementos en el orden en que aparecen en el código fuente, no en el orden visual.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Auto alineación {#self-alignment}

La auto alineación del Flex Layout se refiere a cómo un solo objeto de Flex Layout está alineado dentro de su contenedor flex padre a lo largo del eje cruzado, que es perpendicular al eje principal. La alineación en el eje cruzado es controlada por la propiedad `Alignment`.

La propiedad align-self especifica la alineación de un solo elemento flex a lo largo del eje cruzado, anulando la alineación predeterminada establecida por la propiedad `AlignContent` en un objeto Flex Layout. Esto te permite alinear objetos individuales de Flex Layout de manera diferente a los demás en el contenedor.

:::info
La auto alineación utiliza los mismos valores que la alineación de contenido.
:::

Esta propiedad es especialmente útil cuando necesitas alinear un elemento específico de manera diferente a los otros elementos en el contenedor. Ve el siguiente ejemplo para una ilustración de cómo alinear un solo elemento:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Base flex {#flex-basis}

`Item Basis` es una propiedad que se usa en conjunto con la dirección del Flex Layout para establecer el tamaño inicial de un elemento flex antes de que se distribuya el espacio restante.

La propiedad `Item Basis` especifica el tamaño predeterminado de un elemento flex a lo largo del eje principal, que es horizontal (para una dirección de fila) o vertical (para una dirección de columna). Esta propiedad establece el ancho o la altura de un elemento flex, dependiendo del valor de la propiedad de dirección flex.

:::info
Por defecto, la propiedad `Item Basis` está configurada en auto, lo que significa que el tamaño del elemento se determina por su contenido. Sin embargo, también puedes establecer un tamaño específico para el elemento utilizando diversas unidades, como píxeles (px), ems (em), porcentajes (%), o cualquier otra unidad de longitud de CSS.
:::

La siguiente demostración te permite seleccionar una o más cajas y cambiar el `Item Basis` para los elementos seleccionados.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Crecer / reducir flex {#flex-grow--shrink}

`Item Grow` y `Item Shrink` son dos propiedades que trabajan en conjunto entre sí y con la propiedad `Item Basis` para determinar cómo los elementos flex crecen o se reducen para llenar el espacio disponible dentro de un objeto Flex Layout.

La propiedad `Item Grow` especifica cuánto puede crecer el elemento flex en relación con los otros elementos en el contenedor. Toma un valor sin unidad que representa una proporción del espacio disponible que debe asignarse al elemento. Por ejemplo, si un elemento tiene un valor de `Item Grow` de 1 y otro tiene un valor de 2, el segundo elemento crecerá el doble que el primer elemento.

La propiedad `Item Shrink`, por otro lado, especifica cuánto puede reducirse el elemento flex en relación con los otros elementos en el contenedor. También toma un valor sin unidad que representa una proporción del espacio disponible que debe asignarse al elemento. Por ejemplo, si un elemento tiene un valor de `Item Shrink` de 1 y otro tiene un valor de 2, el segundo elemento se reducirá el doble que el primer elemento.

Cuando un contenedor tiene más espacio del necesario para acomodar su contenido, los elementos flex con un valor de `Item Grow` mayor que 0 se expandirán para llenar el espacio disponible. La cantidad de espacio que obtiene cada elemento se determina por la proporción de su valor `Item Grow` al valor total de `Item Grow` de todos los elementos en el contenedor.

De manera análoga, cuando un contenedor no tiene suficiente espacio para acomodar su contenido, los elementos flex con un valor de `Item Shrink` mayor que 0 se reducirán para ajustarse al espacio disponible. La cantidad de espacio que cede cada elemento se determina por la proporción de su valor `Item Shrink` al valor total de `Item Shrink` de todos los elementos en el contenedor.

## Formulario de ejemplo {#example-form}
El formulario a continuación demuestra cómo `FlexLayout` organiza campos de entrada en un layout estructurado.

:::tip
Si prefieres una estructura basada en columnas, consulta la versión ColumnsLayout de este formulario en el artículo [`ColumnsLayout`](../components/columns-layout) para ver cómo se compara.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
