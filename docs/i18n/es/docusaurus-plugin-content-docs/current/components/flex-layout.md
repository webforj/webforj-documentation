---
title: FlexLayout
sidebar_position: 45
_i18n_hash: 5c12042a5890f07259e77e0d2111a5c6
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

El componente `FlexLayout` organiza componentes secundarios en una fila o columna utilizando el modelo CSS Flexbox. Te brinda control sobre la alineación, el espaciado, el ajuste y cómo los elementos crecen o se reducen para llenar el espacio disponible.

<!-- INTRO_END -->

## Propiedades del diseño flexible {#flex-layout-properties}

Las propiedades del diseño flexible se pueden agrupar en dos categorías: propiedades que se aplican a los elementos dentro de un diseño y propiedades que se aplican al propio diseño. El diseño flexible, o el elemento padre, es una caja/contenedor que puede contener uno o más componentes. Todo lo que hay dentro de un Flex Layout se llama elemento o elemento secundario. El Flex Layout proporciona capacidades de alineación robustas, que se pueden lograr con la ayuda de propiedades de contenedor o de elementos.

:::tip
El componente de diseño de webforJ sigue el patrón del [diseño flexbox de CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Sin embargo, estas herramientas están diseñadas para ser utilizadas completamente en Java y no requieren la aplicación de CSS fuera de los métodos de API de Java proporcionados.
:::

## Propiedades del contenedor {#container-properties}

Las propiedades del contenedor se aplicarán a todos los componentes dentro de un componente y no al diseño en sí. No afectarán la orientación o la colocación del padre: solo los componentes secundarios dentro.

### Dirección {#direction}

El Flex Layout añadirá componentes uno al lado del otro según la dirección elegida por el desarrollador: ya sea horizontal o vertical. Al utilizar el constructor, utiliza los métodos `horizontal()`, `horizontalReverse()`, `vertical()` o `verticalReverse()` al llamar al método `create()` en un objeto `FlexLayout` para configurar este diseño a medida que se crea el objeto.

Alternativamente, usa el método `setDirection()`. Las opciones horizontales son `FlexDirection.ROW` (de izquierda a derecha) o `FlexDirection.ROW_REVERSE` (de derecha a izquierda), y las opciones verticales son `FlexDirection.COLUMN` (de arriba hacia abajo) o `FlexDirection.COLUMN_REVERSE` (de abajo hacia arriba). Esto se hace con el objeto FlexLayout, en lugar de con el constructor.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Posicionamiento {#positioning}

Los componentes que se añaden horizontalmente también se pueden posicionar tanto horizontal como verticalmente. Usa los métodos `justify()`, `align()` y `contentAlign()` del Flex Layout Builder para configurar la posición al crear un nuevo Flex Layout.

Alternativamente, en el objeto FlexLayout real puedes usar el método `setJustifyContent()` para posicionar elementos horizontalmente, y el método `setAlignment()` para configurar la posición vertical. Para modificar el área alrededor de los componentes a lo largo del eje transversal (eje y para diseños horizontales), utiliza el método `setAlignContent()`.

:::tip
El método `setAlignment()` componentes cómo se mostrarán los elementos a lo largo del eje transversal como un todo dentro del contenedor y es efectivo para diseños de una sola línea.

Los métodos `setAlignContent()` componentes el espacio alrededor del eje transversal, y solo tendrá efecto cuando un diseño tenga múltiples líneas.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Ajuste {#wrapping}

Para personalizar aún más el componente Flex Layout, puedes especificar el comportamiento del diseño flexible cuando los componentes añadidos ya no caben en la pantalla. Para configurar esto usando el constructor, utiliza los métodos `nowrap()` (predeterminado), `wrap()` y `wrapReverse()` para configurar el ajuste.

Alternativamente, si tu diseño ya existe, usa el método `setWrap()` para dictar cómo se comportarán los componentes una vez que ya no puedan caber en una sola línea.

### Espaciado {#spacing}

Para aplicar un espaciado mínimo entre elementos, puedes establecer la propiedad gap. Esta propiedad aplica ese espaciado solo entre elementos, no en los bordes exteriores.

El comportamiento de la propiedad gap se puede pensar como una distancia mínima entre elementos: esta propiedad solo tendrá efecto si es el espacio calculado más grande entre los elementos. Si el espacio entre los elementos sería más grande debido a otra propiedad calculada, como debido a `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, entonces se ignorará la propiedad gap.

### Flujo {#flow}

El flujo flex, que es una combinación tanto de las propiedades de dirección como de ajuste, se puede establecer utilizando el método `setFlow()` en un objeto Flex Layout.

:::info
Para configurar esta propiedad al crear el diseño, utiliza los métodos adecuados de dirección y ajuste. Por ejemplo, para crear un flujo de ajuste de columna, utiliza la combinación `.vertical().wrap()`.
:::

### Constructor de contenedor {#container-builder}

La siguiente demostración te permite construir un contenedor con las propiedades flex deseadas seleccionadas de los diversos menús. Esta herramienta puede utilizarse no solo para crear un ejemplo visual de los diversos métodos, sino también como una herramienta para crear tus propios diseños con las propiedades que desees. Para utilizar un diseño personalizado, simplemente copia el código de salida y añade tus elementos deseados para usarlos en tu programa.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>



<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Propiedades de los elementos {#item-properties}

Las propiedades de los elementos no afectarán a ningún elemento secundario dentro del Flex Layout, sino más bien al propio diseño. Esto es útil para estilizar un solo elemento de Flex Layout que es un hijo de un elemento de Flex Layout más grande de forma independiente de los estilos que se aplican a todos los hijos.

### Orden {#order}

La propiedad `ItemOrder` determina cómo se muestran los componentes dentro del Flex Layout, y cuando se usa en un Flex Layout asignará a un elemento el número de orden específico de ese diseño. Esto sobrescribe el "orden de fuente" predeterminado que se incorpora a cada elemento (el orden en el que se agrega un componente a su padre), y significa que se mostrará antes que los elementos con un orden más alto y después que los elementos con un orden más bajo.

Esta propiedad acepta un valor entero sin unidad que especifica el orden relativo del elemento flexible dentro del contenedor. Cuanto más bajo sea el valor, antes aparecerá el elemento en el orden. Por ejemplo, un elemento con un valor de orden 1 aparecerá antes que un elemento con un valor de orden 2.

:::caution
Es importante tener en cuenta que la propiedad de orden solo afecta el orden visual de los elementos dentro del contenedor, no su posición real en el DOM. Esto significa que las tecnologías de asistencia y los lectores de pantalla aún leerán los elementos en el orden en que aparecen en el código fuente, no en el orden visual.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Auto alineación {#self-alignment}

La auto alineación del Flex Layout se refiere a cómo un único objeto Flex Layout se alinea dentro de su contenedor flexible padre a lo largo del eje transversal, que es perpendicular al eje principal. La alineación del eje transversal se controla mediante la propiedad `Alignment`.

La propiedad align-self especifica la alineación de un único elemento flexible a lo largo del eje transversal, anulando la alineación predeterminada establecida por la propiedad `AlignContent` en un objeto Flex Layout. Esto permite alinear objetos individuales de Flex Layout de manera diferente de los demás en el contenedor.

:::info
La auto alineación utiliza los mismos valores que la alineación del contenido
:::

Esta propiedad es especialmente útil cuando necesitas alinear un elemento específico de manera diferente a los otros elementos en el contenedor. Consulta el ejemplo a continuación para ver un ejemplo de alineación de un solo elemento:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Base flexible {#flex-basis}

`Item Basis` es una propiedad que se usa junto con la dirección del Flex Layout para establecer el tamaño inicial de un elemento flexible antes de que se distribuya el espacio restante.

La propiedad `Item Basis` especifica el tamaño predeterminado de un elemento flexible a lo largo del eje principal, que es horizontal (para una dirección de fila) o vertical (para una dirección de columna). Esta propiedad establece el ancho o la altura de un elemento flexible dependiendo del valor de la propiedad de dirección flexible.

:::info
Por defecto, la propiedad `Item Basis` se establece en auto, lo que significa que el tamaño del elemento se determina por su contenido. Sin embargo, también puedes establecer un tamaño específico para el elemento utilizando varias unidades como píxeles (px), ems (em), porcentajes (%) o cualquier otra unidad de longitud de CSS.
:::

La siguiente demostración te permite seleccionar una o más cajas y cambiar la `Item Basis` para los elementos seleccionados.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Crecimiento / contracción flexible {#flex-grow--shrink}

`Item Grow` y `Item Shrink` son dos propiedades que funcionan juntas entre sí y con la propiedad `Item Basis` para determinar cómo los elementos flexibles crecen o se reducen para llenar el espacio disponible dentro de un objeto Flex Layout.

La propiedad `Item Grow` especifica cuánto puede crecer el elemento flexible en relación con los otros elementos en el contenedor. Toma un valor sin unidad que representa una proporción del espacio disponible que se debe asignar al elemento. Por ejemplo, si un elemento tiene un valor de `Item Grow` de 1 y otro tiene un valor de 2, el segundo elemento crecerá el doble que el primer elemento.

La propiedad `Item Shrink`, por otro lado, especifica cuánto puede reducirse el elemento flexible en relación con los otros elementos en el contenedor. También toma un valor sin unidad que representa una proporción del espacio disponible que se debe asignar al elemento. Por ejemplo, si un elemento tiene un valor de `Item Shrink` de 1 y otro tiene un valor de 2, el segundo elemento se reducirá el doble que el primer elemento.

Cuando un contenedor tiene más espacio del necesario para acomodar su contenido, los elementos flexibles con un valor de `Item Grow` mayor que 0 se expandirán para llenar el espacio disponible. La cantidad de espacio que recibe cada elemento se determina por la relación de su valor `Item Grow` al valor total de `Item Grow` de todos los elementos en el contenedor.

De manera similar, cuando un contenedor no tiene suficiente espacio para acomodar su contenido, los elementos flexibles con un valor de `Item Shrink` mayor que 0 se reducirán para ajustarse al espacio disponible. La cantidad de espacio que cada elemento cede se determina por la relación de su valor `Item Shrink` al valor total de `Item Shrink` de todos los elementos en el contenedor.


## Formulario de ejemplo {#example-form}
El formulario a continuación demuestra cómo `FlexLayout` organiza los campos de entrada en un diseño estructurado.

:::tip
Si prefieres una estructura basada en columnas, consulta la versión ColumnsLayout de este formulario en el artículo [`ColumnsLayout`](../components/columns-layout) para ver cómo se compara.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
