---
title: FlexLayout
sidebar_position: 45
description: >-
  Arrange children in rows or columns with the FlexLayout component, controlling
  direction, justification, alignment, wrapping, and growth.
_i18n_hash: cd16392e244062d863d403e50cc56ddd
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

El componente `FlexLayout` organiza los componentes secundarios en una fila o columna utilizando el modelo CSS Flexbox. Te proporciona control sobre la alineación, el espaciado, el ajuste y cómo los elementos crecen o se encojen para llenar el espacio disponible.

<!-- INTRO_END -->

## Propiedades de `FlexLayout` {#flex-layout-properties}

Las propiedades de `FlexLayout` se pueden agrupar en dos categorías: propiedades que se aplican a los elementos dentro de un diseño y propiedades que se aplican al propio diseño. El `FlexLayout`, o el elemento padre, es una caja/contenedor que puede contener uno o más componentes. Todo lo que hay dentro de un `FlexLayout` se llama un elemento o elemento hijo. El `FlexLayout` proporciona algunas capacidades de alineación, que se pueden lograr con la ayuda de propiedades de contenedor o de elementos.

:::tip
El componente `FlexLayout` sigue el patrón del [diseño flexbox de CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Sin embargo, `FlexLayout` está diseñado para ser utilizado completamente en Java y no requiere el uso de CSS fuera de los métodos de la API de Java proporcionados.
:::

## Propiedades del contenedor {#container-properties}

Las propiedades del contenedor se aplicarán a todos los componentes dentro de un componente y no al diseño en sí. No afectarán la orientación o la colocación del padre, solo los componentes secundarios dentro.

### Dirección {#direction}

El `FlexLayout` agrega componentes uno al lado del otro de acuerdo con su dirección, ya sea horizontal o vertical. Al usar el generador, encadena los métodos `horizontal()`, `horizontalReverse()`, `vertical()` o `verticalReverse()` con el método `FlexLayout.create()` para configurar el diseño a medida que se crea el objeto.

Para establecer la dirección en un objeto `FlexLayout` existente, utiliza el método `setDirection()`. Las opciones horizontales son `FlexDirection.ROW` (de izquierda a derecha) o `FlexDirection.ROW_REVERSE` (de derecha a izquierda), y las opciones verticales son `FlexDirection.COLUMN` (de arriba hacia abajo) o `FlexDirection.COLUMN_REVERSE` (de abajo hacia arriba).

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### Posicionamiento {#positioning}

Los componentes que se agregan horizontalmente también se pueden posicionar tanto horizontal como verticalmente. Utiliza los métodos `justify()`, `align()` y `contentAlign()` del generador `FlexLayout` para configurar el posicionamiento al crear un nuevo `FlexLayout`.

Alternativamente, en el objeto `FlexLayout` real puedes usar el método `setJustifyContent()` para posicionar elementos horizontalmente, y el método `setAlignment()` para configurar el posicionamiento vertical. Para modificar el área alrededor de los componentes a lo largo del eje transversal (eje y para diseños horizontales), utiliza el método `setAlignContent()`.

:::tip
El método `setAlignment()` controla cómo se mostrarán los elementos a lo largo del eje transversal en su totalidad dentro del contenedor y es eficaz para diseños de una sola línea.

El método `setAlignContent()` controla el espacio alrededor del eje transversal y tendrá efecto solo cuando un diseño tenga múltiples líneas.
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### Ajuste {#wrapping}

Para personalizar aún más el componente `FlexLayout`, puedes especificar su comportamiento cuando se agregan componentes que ya no caben dentro de la visualización. Para configurar esto usando el generador, utiliza los métodos `nowrap()` (predeterminado), `wrap()` y `wrapReverse()` para configurar el ajuste. Para configurar esto en un objeto `FlexLayout` existente, usa el método `setWrap()`.

### Espaciado {#spacing}

Para aplicar un espaciado mínimo entre los elementos, puedes establecer la propiedad `gap`. Aplica ese espaciado solo entre elementos, no en los bordes exteriores.

El comportamiento de la propiedad gap puede pensarse como una distancia mínima entre ellos, por lo que solo tendrá efecto si es el espacio calculado más grande entre los elementos. Si el espacio entre los elementos sería más grande por otra propiedad calculada, como debido a `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, entonces se ignorará la propiedad gap.

### Flujo {#flow}

El flujo flex, que es una combinación de ambas propiedades de dirección y ajuste, se puede establecer utilizando el método `setFlow()` en un objeto `FlexLayout`.

:::info
Para configurar esta propiedad al crear el diseño, utiliza los métodos de dirección y ajuste adecuados. Por ejemplo, para crear un flujo de ajuste en columna, usa la combinación `.vertical().wrap()`.
:::

### Generador de contenedores {#container-builder}

La siguiente demostración te permite construir un contenedor con las propiedades flex deseadas seleccionadas de varios menús. Esta herramienta se puede utilizar no solo para crear un ejemplo visual de los diversos métodos, sino también para crear tus propios diseños con tus propiedades deseadas. Para usar un diseño que personalices, simplemente copia el código de salida y agrega tus elementos deseados para usarlos en tu programa.

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>

<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Propiedades del ítem {#item-properties}

Las propiedades del ítem no afectarán a ningún elemento hijo dentro del `FlexLayout`, pero afectarán al propio diseño. Esto es útil para estilizar un único elemento `FlexLayout` que sea hijo de un elemento `FlexLayout` más grande, de forma independiente de los estilos que se aplican a todos los hijos.

### Orden {#order}

La propiedad `ItemOrder` determina el orden en que se muestran los componentes dentro del `FlexLayout`, y cuando se usa en un `FlexLayout`, asignará un número de orden específico a ese ítem. Esto anula el "orden de fuente" predeterminado incorporado en cada ítem (el orden en que un componente se agrega a su padre), y significa que se mostrará antes de los ítems con un orden más alto y después de los ítems con un orden más bajo.

Esta propiedad acepta un valor entero sin unidad que especifica el orden relativo del ítem flex dentro del contenedor. Cuanto más bajo sea el valor, antes aparecerá el ítem en el orden. Por ejemplo, un ítem con un valor de orden de 1 aparecerá antes que un ítem con un valor de orden de 2.

:::caution
Es importante notar que la propiedad de orden solo afecta el orden visual de los ítems dentro del contenedor, no su posición real en el DOM. Esto significa que los lectores de pantalla y otras tecnologías de asistencia aún leerán los ítems en el orden en que aparecen en el código fuente, no en el orden visual.
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### Autoalineación {#self-alignment}

La autoalineación de `FlexLayout` se refiere a cómo se alinea un solo objeto `FlexLayout` dentro de su contenedor flex padre a lo largo del eje transversal, que es perpendicular al eje principal. La alineación del eje transversal se controla mediante la propiedad `Alignment`.

La propiedad align-self especifica la alineación de un solo ítem flex a lo largo del eje transversal, anulando la alineación predeterminada establecida por la propiedad `AlignContent` en un objeto `FlexLayout`. Esto te permite alinear objetos `FlexLayout` individuales de manera diferente a los demás en el contenedor.

:::info
La autoalineación utiliza los mismos valores que la alineación del contenido.
:::

Esta propiedad es especialmente útil cuando necesitas alinear un ítem específico de manera diferente a los otros ítems en el contenedor. Consulta el ejemplo a continuación para un ejemplo de alineación de un solo ítem:

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### Base flex {#flex-basis}

`Item Basis` es una propiedad que se utiliza junto con la dirección de `FlexLayout` para establecer el tamaño inicial de un ítem flex antes de distribuir cualquier espacio restante.

La propiedad `Item Basis` especifica el tamaño predeterminado de un ítem flex a lo largo del eje principal, que es horizontal (para una dirección de fila) o vertical (para una dirección de columna). Esta propiedad establece el ancho o la altura de un ítem flex dependiendo del valor de la propiedad de dirección flex.

:::info
Por defecto, la propiedad `Item Basis` está configurada en `auto`, lo que significa que el tamaño del ítem se determina por su contenido. Sin embargo, también puedes establecer un tamaño específico para el ítem utilizando varias unidades como píxeles (px), ems (em), porcentajes (%), o cualquier otra unidad de longitud de CSS.
:::

La siguiente demostración te permite seleccionar uno o más cuadros y cambiar la `Item Basis` para los elementos seleccionados.

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### Crecimiento y reducción flex {#flex-grow--shrink}

`Item Grow` y `Item Shrink` son dos propiedades que trabajan en conjunto entre sí y con la propiedad `Item Basis` para determinar cómo los ítems flex crecen o se encojen para llenar el espacio disponible dentro de un objeto `FlexLayout`.

La propiedad `Item Grow` especifica cuánto puede crecer el ítem flex en relación con los otros ítems en el contenedor. Toma un valor sin unidad que representa una proporción del espacio disponible que debe ser asignado al ítem. Por ejemplo, si un ítem tiene un valor de `Item Grow` de 1 y otro tiene un valor de 2, el segundo ítem crecerá el doble que el primer ítem.

La propiedad `Item Shrink`, por otro lado, especifica cuánto puede encojerse el ítem flex en relación con los otros ítems en el contenedor. También toma un valor sin unidad que representa una proporción del espacio disponible que debe ser asignado al ítem. Por ejemplo, si un ítem tiene un valor de `Item Shrink` de 1 y otro tiene un valor de 2, el segundo ítem se encojerá el doble que el primer ítem.

Cuando un contenedor tiene más espacio del necesario para acomodar su contenido, los ítems flex con un valor de `Item Grow` mayor que 0 se expandirán para llenar el espacio disponible. La cantidad de espacio que recibe cada ítem se determina por la proporción de su valor de `Item Grow` al valor total de `Item Grow` de todos los ítems en el contenedor.

Del mismo modo, cuando un contenedor no tiene suficiente espacio para acomodar su contenido, los ítems flex con un valor de `Item Shrink` mayor que 0 se encogerán para adaptarse al espacio disponible. La cantidad de espacio que cada ítem cede se determina por la proporción de su valor de `Item Shrink` al valor total de `Item Shrink` de todos los ítems en el contenedor.

## Ejemplo de formulario {#example-form}
El formulario a continuación demuestra cómo `FlexLayout` organiza campos de entrada en un diseño estructurado.

:::tip
Si prefieres una estructura basada en columnas, consulta la versión de este formulario en `ColumnsLayout` en el artículo de [`ColumnsLayout`](../components/columns-layout) para ver cómo se compara.
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/frontend/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
