---
title: FlexLayout
sidebar_position: 45
_i18n_hash: ddb7d5ef1e583af6e3a7072d91329c7b
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

El componente `FlexLayout` organiza componentes secundarios en una fila o columna utilizando el modelo CSS Flexbox. Te da control sobre la alineación, el espaciado, el ajuste y cómo los elementos crecen o se reducen para llenar el espacio disponible.

<!-- INTRO_END -->

## Propiedades de `FlexLayout` {#flex-layout-properties}

Las propiedades de `FlexLayout` se pueden agrupar en dos categorías: propiedades que se aplican a los elementos dentro de un diseño y propiedades que se aplican al propio diseño. El `FlexLayout`, o el elemento padre, es una caja/contenedor que puede contener uno o más componentes. Todo lo que está dentro de un `FlexLayout` se llama un ítem o elemento secundario. El `FlexLayout` proporciona algunas capacidades de alineación, que se pueden lograr con la ayuda de propiedades de contenedor o de ítem.

:::tip
El componente `FlexLayout` sigue el patrón de [el diseño flexbox de CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Sin embargo, `FlexLayout` está hecho para ser usado completamente en Java y no requiere el uso de CSS fuera de los métodos proporcionados por la API de Java.
:::

## Propiedades del contenedor {#container-properties}

Las propiedades del contenedor se aplicarán a todos los componentes dentro de un componente y no al diseño en sí. No afectarán la orientación o colocación del padre, solo los componentes secundarios dentro.

### Dirección {#direction}

El `FlexLayout` agrega componentes uno al lado del otro de acuerdo con su dirección, ya sea horizontal o vertical. Al usar el constructor, encadena los métodos `horizontal()`, `horizontalReverse()`, `vertical()`, o `verticalReverse()` con el método `FlexLayout.create()` para configurar el diseño mientras se crea el objeto.

Para establecer la dirección en un objeto `FlexLayout` existente, utiliza el método `setDirection()`. Las opciones horizontales son `FlexDirection.ROW` (de izquierda a derecha) o `FlexDirection.ROW_REVERSE` (de derecha a izquierda), y las opciones verticales son `FlexDirection.COLUMN` (de arriba a abajo) o `FlexDirection.COLUMN_REVERSE` (de abajo hacia arriba).

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Posicionamiento {#positioning}

Los componentes que se añaden horizontalmente también se pueden posicionar tanto horizontal como verticalmente. Utiliza los métodos `justify()`, `align()` y `contentAlign()` del constructor `FlexLayout` para configurar el posicionamiento cuando creas un nuevo `FlexLayout`.

Alternativamente, en el objeto `FlexLayout` real, puedes usar el método `setJustifyContent()` para posicionar ítems horizontalmente, y el método `setAlignment()` para configurar el posicionamiento vertical. Para modificar el área alrededor de los componentes a lo largo del eje transversal (eje y para diseños horizontales), usa el método `setAlignContent()`.

:::tip
El método `setAlignment()` controla cómo se mostrarán los ítems a lo largo del eje transversal como un todo dentro del contenedor, y es efectivo para diseños de una sola línea.

El método `setAlignContent()` controla el espacio alrededor del eje transversal, y tendrá efecto solo cuando un diseño tenga múltiples líneas.
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Ajuste {#wrapping}

Para personalizar aún más el componente `FlexLayout`, puedes especificar su comportamiento cuando se agregan componentes que ya no caben dentro de la visualización. Para configurar esto usando el constructor, utiliza los métodos `nowrap()` (por defecto), `wrap()`, y `wrapReverse()` para configurar el ajuste. Para configurar esto en un objeto `FlexLayout` existente, usa el método `setWrap()`.

### Espaciado {#spacing}

Para aplicar un espaciado mínimo entre los ítems, puedes establecer la propiedad `gap`. Aplica ese espaciado solo entre ítems, no en los bordes exteriores.

El comportamiento de la propiedad gap se puede pensar como una distancia mínima entre, por lo que solo tendrá efecto si es el mayor espacio calculado entre ítems. Si el espacio entre ítems fuera de otro modo mayor debido a otra propiedad calculada, como debido a `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, entonces la propiedad gap será ignorada.

### Flujo {#flow}

El flujo flex, que es una combinación de ambas propiedades, la dirección y el ajuste, se puede establecer usando el método `setFlow()` en un objeto `FlexLayout`. 

:::info
Para configurar esta propiedad al crear el diseño, utiliza los métodos de dirección y ajuste apropiados. Por ejemplo, para crear un flujo de ajuste en columna, usa la combinación `.vertical().wrap()`.
:::

### Constructor de contenedor {#container-builder}

La siguiente demostración te permite construir un contenedor con las propiedades flex deseadas seleccionadas de los varios menús. Esta herramienta no solo se puede usar para crear un ejemplo visual de los varios métodos, sino también para crear tus propios diseños con tus propiedades deseadas. Para usar un diseño que personalices, simplemente copia el código de salida y añade tus elementos deseados para usarlos en tu programa.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Propiedades de ítem {#item-properties}

Las propiedades de ítem no afectarán a ningún elemento secundario dentro del `FlexLayout`, pero afectan al diseño real en sí. Esto es útil para estilizar un solo elemento `FlexLayout` que es un hijo de un elemento `FlexLayout` más grande de forma independiente a los estilos que se aplican a todos los hijos.

### Orden {#order}

La propiedad `ItemOrder` determina el orden en que se muestran los componentes dentro del `FlexLayout`, y cuando se utiliza en un `FlexLayout`, asignará a un ítem el número de orden específico de ese diseño. Esto sobrescribe el "orden de origen" predeterminado integrado en cada ítem (el orden en que se añade un componente a su padre), y significa que se mostrará antes que los ítems con un orden mayor, y después que los ítems con un orden menor.

Esta propiedad acepta un valor entero sin unidad que especifica el orden relativo del ítem flex dentro del contenedor. Cuanto más bajo sea el valor, antes aparecerá el ítem en el orden. Por ejemplo, un ítem con un valor de orden de 1 aparecerá antes que un ítem con un valor de orden de 2.

:::caution
Es importante notar que la propiedad de orden solo afecta al orden visual de los ítems dentro del contenedor, no a su posición real en el DOM. Esto significa que los lectores de pantalla y otras tecnologías de asistencia seguirán leyendo los ítems en el orden en que aparecen en el código fuente, no en el orden visual.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Auto alineación {#self-alignment}

La auto alineación del `FlexLayout` se refiere a cómo se alinea un solo objeto `FlexLayout` dentro de su contenedor flex padre a lo largo del eje transversal, que es perpendicular al eje principal. La alineación del eje transversal se controla mediante la propiedad `Alignment`.

La propiedad align-self especifica la alineación de un solo ítem flex a lo largo del eje transversal, sobreescribiendo la alineación predeterminada establecida por la propiedad `AlignContent` en un objeto `FlexLayout`. Esto te permite alinear objetos `FlexLayout` individuales de manera diferente a los demás en el contenedor.

:::info
La auto alineación utiliza los mismos valores que la alineación del contenido.
:::

Esta propiedad es especialmente útil cuando necesitas alinear un ítem específico de manera diferente a los otros ítems en el contenedor. Consulta el ejemplo a continuación para un ejemplo de cómo alinear un solo ítem:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Bases flexibles {#flex-basis}

`Item Basis` es una propiedad que se utiliza en conjunto con la dirección de `FlexLayout` para establecer el tamaño inicial de un ítem flex antes de que se distribuya el espacio restante.

La propiedad `Item Basis` especifica el tamaño predeterminado de un ítem flex a lo largo del eje principal, que puede ser horizontal (para una dirección de Fila) o vertical (para una dirección de Columna). Esta propiedad establece el ancho o alto de un ítem flex dependiendo del valor de la propiedad de dirección flex.

:::info
Por defecto, la propiedad `Item Basis` está establecida en `auto`, lo que significa que el tamaño del ítem se determina por su contenido. Sin embargo, también puedes establecer un tamaño específico para el ítem utilizando varias unidades como píxeles (px), ems (em), porcentajes (%) o cualquier otra unidad de longitud CSS.
:::

La siguiente demostración te permite seleccionar una o más cajas y cambiar la `Item Basis` para los ítems seleccionados.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Crecimiento y reducción flexibles {#flex-grow--shrink}

`Item Grow` y `Item Shrink` son dos propiedades que trabajan en conjunto entre sí y con la propiedad `Item Basis` para determinar cómo los ítems flex crecen o se reducen para llenar el espacio disponible dentro de un objeto `FlexLayout`.

La propiedad `Item Grow` especifica cuánto puede crecer un ítem flex en relación con los otros ítems en el contenedor. Toma un valor sin unidad que representa una proporción del espacio disponible que debe asignarse al ítem. Por ejemplo, si un ítem tiene un valor de `Item Grow` de 1 y otro tiene un valor de 2, el segundo ítem crecerá el doble que el primer ítem.

La propiedad `Item Shrink`, por otro lado, especifica cuánto puede reducirse un ítem flex en relación con los otros ítems en el contenedor. También toma un valor sin unidad que representa una proporción del espacio disponible que debe asignarse al ítem. Por ejemplo, si un ítem tiene un valor de `Item Shrink` de 1 y otro tiene un valor de 2, el segundo ítem se reducirá el doble que el primer ítem.

Cuando un contenedor tiene más espacio del necesario para acomodar su contenido, los ítems flex con un valor de `Item Grow` mayor que 0 se expandirán para llenar el espacio disponible. La cantidad de espacio que obtiene cada ítem se determina por la proporción de su valor de `Item Grow` al valor total de `Item Grow` de todos los ítems en el contenedor.

De manera similar, cuando un contenedor no tiene suficiente espacio para acomodar su contenido, los ítems flex con un valor de `Item Shrink` mayor que 0 se reducirán para ajustarse al espacio disponible. La cantidad de espacio que cada ítem cede se determina por la proporción de su valor de `Item Shrink` al valor total de `Item Shrink` de todos los ítems en el contenedor.

## Formulario de ejemplo {#example-form}
El formulario a continuación demuestra cómo `FlexLayout` organiza los campos de entrada en un diseño estructurado.

:::tip
Si prefieres una estructura basada en columnas, consulta la versión de `ColumnsLayout` de este formulario en el artículo [`ColumnsLayout`](../components/columns-layout) para ver cómo se compara.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
