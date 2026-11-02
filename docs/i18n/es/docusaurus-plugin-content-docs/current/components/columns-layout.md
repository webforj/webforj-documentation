---
title: ColumnsLayout
sidebar_position: 25
description: >-
  Arrange child components into a responsive multi-column grid with the
  ColumnsLayout component using configurable breakpoints and alignment.
_i18n_hash: 3705994e0ddda1a54c0582b290942549
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

El componente `ColumnsLayout` organiza los elementos en un diseño de columnas responsivo que se ajusta según el ancho disponible. Los puntos de ruptura y las alineaciones se gestionan automáticamente, por lo que construir formularios de múltiples columnas y cuadrículas de contenido no requiere lógica responsiva personalizada.

<!-- INTRO_END -->

## Comportamiento predeterminado {#default-behavior}

Por defecto, un `ColumnsLayout` ocupa todo el ancho de su contenedor padre y organiza los elementos en una sola columna, cambiando a dos columnas a anchos de `40em` o más.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Diseños horizontales
Esto se puede usar en lugar de, o en combinación con, el componente [`FlexLayout`](./flex-layout) - una herramienta igualmente poderosa para diseños horizontales.
:::

## Puntos de ruptura {#breakpoints}

En su núcleo, el `ColumnsLayout` está diseñado para proporcionar un sistema fluido tipo cuadrícula que se adapta al ancho de su contenedor padre. A diferencia de los sistemas de cuadrícula fijos tradicionales, este diseño permite a los desarrolladores especificar un número de columnas a un ancho dado y calcula dinámicamente el número de columnas visibles en función de los objetos `Breakpoint` establecidos.

Esto permite que un `ColumnsLayout` se adapte suavemente de un espacio más restringido en pantallas pequeñas a un área más amplia en pantallas más grandes, ofreciendo un diseño responsivo a un desarrollador sin necesidad de implementación personalizada.

### Entendiendo un `Breakpoint` {#understanding-a-breakpoint}

Un `Breakpoint` se puede especificar utilizando la clase `Breakpoint`, que toma tres parámetros:

1. **Nombre (opcional)**:
Nombrar un punto de ruptura permite hacer referencia a él en configuraciones futuras.

2. **Ancho mínimo**:
Cada punto de ruptura tiene un rango específico que determina cuándo se aplica su diseño. El ancho mínimo se define explícitamente, y el siguiente punto de ruptura determina el ancho máximo si existe. Puedes usar un entero para definir el ancho mínimo en píxeles o usar un `String` para especificar otras unidades como `vw`, `%`, o `em`.

3. **Número de columnas**:
Especifica cuántas columnas debe tener un punto de ruptura con este entero.

:::info Evaluación de `Breakpoint`
Los puntos de ruptura se evalúan en orden ascendente del ancho, lo que significa que el diseño utilizará el primer punto de ruptura que coincida.
:::

### Aplicando puntos de ruptura {#applying-breakpoints}

Los puntos de ruptura se aplican a un `ColumnsLayout` de una de dos maneras: durante la construcción, o en una `List` utilizando el método `setBreakpoints()`:

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // Una columna a anchos >= 0px
  new Breakpoint(0, 1),
  // Dos columnas a anchos >= 600px
  new Breakpoint(600, 2),
  // Cuatro columnas a anchos >= 1200px
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

La demostración a continuación muestra un ejemplo de cómo establecer múltiples puntos de ruptura en la construcción, utilizando puntos de ruptura para configurar el [`Span`](#column-span-and-spans-per-breakpoint) de un componente, y demuestra las capacidades de redimensionamiento del `ColumnsLayout` cuando la aplicación se redimensiona:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## `Span` de columna y spans por `Breakpoint` {#column-span-and-spans-per-breakpoint}

Los spans de columna en `ColumnsLayout` permiten controlar cuántas columnas ocupa un elemento, dándole más control sobre la apariencia de su diseño en diferentes anchos. Esto es especialmente útil cuando necesitas que ciertos elementos ocupen más o menos espacio dependiendo del tamaño de la pantalla o los requisitos de diseño.

### Gestionando spans de columna {#managing-column-spans}

Por defecto, cada elemento en el `ColumnsLayout` ocupa exactamente una columna. Sin embargo, puedes personalizar este comportamiento estableciendo el span de columna para elementos individuales. Un span especifica cuántas columnas debe ocupar un elemento.

```java
Button button = new Button("Click Me");
layout.add(button);
// El elemento ocupa dos columnas
layout.setSpan(button, 2);
```

En el ejemplo anterior, el botón ocupa dos columnas en lugar de la predeterminada de una. El método `setSpan()` te permite especificar cuántas columnas debe abarcar un componente dentro del diseño.

### Ajustando spans de columna con puntos de ruptura {#adjusting-column-spans-with-breakpoints}

También puedes ajustar los spans de columna dinámicamente según los puntos de ruptura. Esta característica es útil cuando deseas que un elemento abarque diferentes números de columnas dependiendo del tamaño de la pantalla. Por ejemplo, puedes querer que un elemento ocupe una sola columna en dispositivos móviles, pero abarque múltiples columnas en pantallas más grandes.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
// El campo de email abarcará dos columnas cuando el punto de ruptura medio esté activo
columnsLayout.setSpan(email, "medium", 2);
//...
```

Este nivel de personalización asegura que tu diseño permanezca responsivo y apropiadamente estructurado en diferentes dispositivos.

## Colocando elementos dentro de columnas {#placing-items-within-columns}

`ColumnsLayout` proporciona la capacidad de colocar elementos en columnas específicas, dando más control sobre la disposición de los elementos. Puedes especificar manualmente dónde debe aparecer un elemento dentro del diseño, asegurando que los componentes importantes se muestren como se esperaba.

### Colocación básica en columnas {#basic-column-placement}

Por defecto, los elementos se colocan en la siguiente columna disponible, llenando de izquierda a derecha. Sin embargo, puedes anular este comportamiento y especificar la columna exacta donde debe colocarse un elemento. Para colocar un elemento en una columna específica, usa el método `setColumn()`. En este ejemplo, el botón se coloca en la segunda columna del diseño, independientemente del orden en que se agregó en relación con otros componentes:

```java
Button button = new Button("Submit");
layout.add(button);
// Colocar el elemento en la segunda columna
layout.setColumn(button, 2);
```

### Ajustando la colocación por punto de ruptura {#adjusting-placement-per-breakpoint}

Al igual que con los spans de columna, usas puntos de ruptura para ajustar la colocación de los elementos según el tamaño de la pantalla. Esto es útil para reordenar o reubicar elementos en el diseño a medida que cambia el viewport.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
// El campo de email aparecerá en la segunda columna cuando el punto de ruptura medio esté activo
columnsLayout.setColumn(email, "medium", 2);
//...
```

En la siguiente demostración, nota que cuando se activa el punto de ruptura `"medium"`, el campo `email` abarca ambas columnas:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Evitar colisiones
Cuando múltiples elementos se colocan en un diseño con diferentes spans y/o asignaciones de columna, asegúrate de que los spans y colocaciones combinados de los elementos en una fila no se superpongan. El diseño intenta gestionar el espaciado de manera elegante automáticamente, pero un diseño cuidadoso de spans y puntos de ruptura previene la visualización no intencionada de elementos.
:::

## Alineaciones de elementos verticales y horizontales {#vertical-and-horizontal-item-alignments}

Cada elemento en el `ColumnsLayout` puede ser alineado tanto horizontal como verticalmente dentro de su columna, dando control sobre cómo se posiciona el contenido dentro del diseño.

**La alineación horizontal** de un elemento se controla mediante el método `setHorizontalAlignment()`. Esta propiedad determina cómo se alinea un elemento dentro de su columna a lo largo del eje horizontal.

**La alineación vertical** especifica cómo se posiciona un elemento dentro de su columna a lo largo del eje vertical. Esto es útil cuando las columnas tienen alturas variables y deseas controlar cómo se distribuyen verticalmente los elementos.

Las opciones de `Alignment` disponibles incluyen:

- `START`: Alinea el elemento a la izquierda de la columna.
- `CENTER`: Centra el elemento horizontalmente dentro de la columna.
- `END`: Alinea el elemento a la derecha de la columna.
- `STRETCH`: Estira el componente para llenar el diseño.
- `BASELINE`: Alinea en función del texto o contenido dentro de la columna, alineando elementos a la línea base del texto en lugar de otras opciones de alineación.
- `AUTO`: Alineación automática (predeterminada).

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

En la demostración anterior, el botón `Submit` se le ha dado `ColumnsLayout.Alignment.END` para asegurar que aparezca al final, o en este caso a la derecha, de su columna.

## Espaciado de elementos {#item-spacing}

Controlar el espacio en el `ColumnsLayout` entre columnas (espaciado horizontal) y entre filas (espaciado vertical) ayuda a los desarrolladores a afinar el diseño.

Para establecer el espaciado horizontal del diseño, usa el método `setHorizontalSpacing()`:

```java
// Establecer 20px de espacio entre columnas
layout.setHorizontalSpacing(20);
```

De manera similar, usa el método `setVerticalSpacing()` para configurar el espacio entre filas del diseño:

```java
// Establecer 15px de espacio entre filas
layout.setVerticalSpacing(15);
```

:::tip Unidades CSS
El espaciado acepta un entero para píxeles, o un `String` para otras unidades como `vw` o `%`.
:::

## Diseños horizontales y verticales {#horizontal-and-vertical-layouts}

Construir diseños responsivos y atractivos es posible utilizando tanto el componente [`FlexLayout`](./flex-layout) como el componente `ColumnsLayout`, así como una combinación de los dos. A continuación se muestra un ejemplo del [formulario creado en el artículo sobre FlexLayout](./flex-layout#example-form), pero utilizando un esquema de `ColumnLayout` en su lugar:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Estilizando {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
