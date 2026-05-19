---
title: FlexLayout
sidebar_position: 45
_i18n_hash: cf7ba76f1e13488c6fa3a419ba6ceaca
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

El componente `FlexLayout` organiza los componentes secundarios en una fila o una columna utilizando el modelo CSS Flexbox. Te brinda control sobre la alineación, el espacio, el ajuste y cómo los elementos crecen o disminuyen para llenar el espacio disponible.

<!-- INTRO_END -->

## Propiedades de `FlexLayout` {#flex-layout-properties}

Las propiedades de `FlexLayout` se pueden agrupar en dos categorías: propiedades que se aplican a los elementos dentro de un diseño y propiedades que se aplican al diseño en sí. El `FlexLayout`, o el elemento padre, es una caja/contenedor que puede contener uno o más componentes. Todo lo que hay dentro de un `FlexLayout` se llama un elemento o un elemento hijo. El `FlexLayout` proporciona algunas capacidades de alineación, que se pueden lograr con la ayuda de propiedades de contenedor o de elementos.

:::tip
El componente `FlexLayout` sigue el patrón del [layout flexbox de CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Sin embargo, `FlexLayout` está diseñado para ser utilizado completamente en Java y no requiere el uso de CSS fuera de los métodos de la API de Java proporcionados.
:::

## Propiedades del contenedor {#container-properties}

Las propiedades del contenedor se aplicarán a todos los componentes dentro de un componente y no al diseño en sí. No afectarán la orientación o la colocación del padre, solo los componentes hijos dentro.

### Dirección {#direction}

El `FlexLayout` agrega componentes uno al lado del otro de acuerdo a su dirección, ya sea horizontal o vertical. Al usar el constructor, encadena los métodos `horizontal()`, `horizontalReverse()`, `vertical()` o `verticalReverse()` con el método `FlexLayout.create()` para configurar el diseño mientras se crea el objeto.

Para establecer la dirección en un objeto `FlexLayout` existente, utiliza el método `setDirection()`. Las opciones horizontales son `FlexDirection.ROW` (de izquierda a derecha) o `FlexDirection.ROW_REVERSE` (de derecha a izquierda), y las opciones verticales son `FlexDirection.COLUMN` (de arriba hacia abajo) o `FlexDirection.COLUMN_REVERSE` (de abajo hacia arriba). 

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### Posicionamiento {#positioning}

Los componentes que se agregan horizontalmente también se pueden posicionar tanto horizontal como verticalmente. Utiliza los métodos `justify()`, `align()` y `contentAlign()` del Constructor `FlexLayout` para configurar la posición al crear un nuevo `FlexLayout`.

Alternativamente, en el objeto `FlexLayout` real puedes usar el método `setJustifyContent()` para posicionar elementos horizontalmente y el método `setAlignment()` para configurar el posicionamiento vertical. Para modificar el área alrededor de los componentes a lo largo del eje cruzado (eje y para diseños horizontales), utiliza el método `setAlignContent()`.

:::tip
El método `setAlignment()` controla cómo se mostrarán los elementos a lo largo del eje cruzado en su conjunto dentro del contenedor, y es efectivo para diseños de una sola línea.

El método `setAlignContent()` controla el espacio alrededor del eje cruzado y solo tendrá efecto cuando un diseño tenga varias líneas.
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### Ajuste {#wrapping}

Para personalizar aún más el componente `FlexLayout`, puedes especificar su comportamiento cuando se agregan componentes que ya no caben en la pantalla. Para configurar esto utilizando el constructor, utiliza los métodos `nowrap()` (por defecto), `wrap()` y `wrapReverse()` para configurar el ajuste. Para configurar esto en un objeto `FlexLayout` existente, utiliza el método `setWrap()`.

### Espaciado {#spacing}

Para aplicar un espaciado mínimo entre elementos, puedes establecer la propiedad `gap`. Esta aplica ese espaciado solo entre los elementos, no en los bordes exteriores. 

El comportamiento de la propiedad gap se puede pensar como una distancia mínima entre, por lo que solo tendrá efecto si es el espacio más grande calculado entre los elementos. Si el espacio entre los elementos es más grande debido a otra propiedad calculada, como `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, entonces se ignorará la propiedad gap.

### Flujo {#flow}

El flujo flexible, que es una combinación de las propiedades de dirección y ajuste, se puede establecer utilizando el método `setFlow()` en un objeto `FlexLayout`. 

:::info
Para configurar esta propiedad al crear el diseño, utiliza los métodos de dirección y ajuste adecuados. Por ejemplo, para crear un flujo de ajuste en columna, utiliza la combinación `.vertical().wrap()`.
:::

### Constructor de contenedor {#container-builder}

La siguiente demostración te permite construir un contenedor con las propiedades flex deseadas seleccionadas de los diversos menús. Esta herramienta se puede utilizar no solo para crear un ejemplo visual de los diversos métodos, sino también para crear tus propios diseños con tus propiedades deseadas. Para usar un diseño que personalizaste, simplemente copia el código de salida y agrega tus elementos deseados para usar en tu programa.

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Propiedades del elemento {#item-properties}

Las propiedades de los elementos no afectarán a ningún elemento hijo dentro del `FlexLayout`, sino que afectan el diseño real en sí. Esto es útil para dar estilo a un único elemento `FlexLayout` que es un hijo de un elemento `FlexLayout` más grande, independientemente de los estilos que se apliquen a todos los hijos.

### Orden {#order}

La propiedad `ItemOrder` determina el orden en que se muestran los componentes dentro del `FlexLayout`, y cuando se usa en un `FlexLayout` asignará a un elemento el número de orden específico de ese diseño. Esto anula el "orden de origen" predeterminado integrado en cada elemento (el orden en el que se agrega un componente a su padre), y significa que se mostrará antes que los elementos con un orden más alto y después que los elementos con un orden más bajo.

Esta propiedad acepta un valor entero sin unidad que especifica el orden relativo del elemento flex dentro del contenedor. Cuanto menor sea el valor, antes aparecerá el elemento en el orden. Por ejemplo, un elemento con un valor de orden de 1 aparecerá antes que un elemento con un valor de orden de 2.

:::caution
Es importante tener en cuenta que la propiedad de orden solo afecta el orden visual de los elementos dentro del contenedor, no su posición real en el DOM. Esto significa que los lectores de pantalla y otras tecnologías asistenciales seguirán leyendo los elementos en el orden en que aparecen en el código fuente, y no en el orden visual.
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### Auto alineación {#self-alignment}

La auto-alineación de `FlexLayout` se refiere a cómo un único objeto `FlexLayout` está alineado dentro de su contenedor flex padre a lo largo del eje cruzado, que es perpendicular al eje principal. La alineación en el eje cruzado se controla mediante la propiedad `Alignment`.

La propiedad align-self especifica la alineación de un solo elemento flex a lo largo del eje cruzado, anulando la alineación predeterminada establecida por la propiedad `AlignContent` en un objeto `FlexLayout`. Esto te permite alinear objetos `FlexLayout` individuales de manera diferente a los demás en el contenedor.

:::info
La auto-alineación utiliza los mismos valores que la alineación de contenido.
:::

Esta propiedad es especialmente útil cuando necesitas alinear un elemento específico de manera diferente a los otros elementos en el contenedor. Consulta el ejemplo a continuación para ver un ejemplo de cómo alinear un único elemento:

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### Base flex {#flex-basis}

`Item Basis` es una propiedad que se utiliza junto con la dirección de `FlexLayout` para establecer el tamaño inicial de un elemento flex antes de que se distribuya el espacio restante.

La propiedad `Item Basis` especifica el tamaño predeterminado de un elemento flex a lo largo del eje principal, que es horizontal (para una dirección de fila) o vertical (para una dirección de columna). Esta propiedad establece el ancho o la altura de un elemento flex dependiendo del valor de la propiedad de dirección flex.

:::info
Por defecto, la propiedad `Item Basis` se establece en `auto`, lo que significa que el tamaño del elemento se determina por su contenido. Sin embargo, también puedes establecer un tamaño específico para el elemento utilizando diversas unidades como píxeles (px), ems (em), porcentajes (%) o cualquier otra unidad de longitud CSS.
:::

La siguiente demostración te permite seleccionar una o más cajas y cambiar la `Item Basis` para los elementos seleccionados.

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### Crecimiento y disminución flex {#flex-grow--shrink}

`Item Grow` y `Item Shrink` son dos propiedades que trabajan en conjunto entre sí y con la propiedad `Item Basis` para determinar cómo los elementos flex crecen o disminuyen para llenar el espacio disponible dentro de un objeto `FlexLayout`.

La propiedad `Item Grow` especifica cuánto puede crecer el elemento flex en relación con los otros elementos en el contenedor. Toma un valor sin unidad que representa una proporción del espacio disponible que debe asignarse al elemento. Por ejemplo, si un elemento tiene un valor de `Item Grow` de 1 y otro tiene un valor de 2, el segundo elemento crecerá el doble que el primer elemento.

La propiedad `Item Shrink`, por otro lado, especifica cuánto puede encogerse el elemento flex en relación con los otros elementos en el contenedor. También toma un valor sin unidad que representa una proporción del espacio disponible que debe asignarse al elemento. Por ejemplo, si un elemento tiene un valor de `Item Shrink` de 1 y otro tiene un valor de 2, el segundo elemento se encogerá el doble que el primer elemento.

Cuando un contenedor tiene más espacio del necesario para acomodar su contenido, los elementos flex con un valor de `Item Grow` mayor que 0 se expandirán para llenar el espacio disponible. La cantidad de espacio que obtiene cada elemento se determina por la proporción de su valor de `Item Grow` al valor total de `Item Grow` de todos los elementos en el contenedor.

De manera similar, cuando un contenedor no tiene suficiente espacio para acomodar su contenido, los elementos flex con un valor de `Item Shrink` mayor que 0 se reducirán para ajustarse al espacio disponible. La cantidad de espacio que cada elemento cede se determina por la proporción de su valor de `Item Shrink` al valor total de `Item Shrink` de todos los elementos en el contenedor.

## Formulario de ejemplo {#example-form}
El formulario a continuación demuestra cómo `FlexLayout` organiza los campos de entrada en un diseño estructurado. 

:::tip
Si prefieres una estructura basada en columnas, consulta la versión `ColumnsLayout` de este formulario en el artículo [`ColumnsLayout`](../components/columns-layout) para ver cómo se compara.
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/resources/static/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
