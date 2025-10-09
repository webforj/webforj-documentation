---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: 25558ea9869bae96974e292e7cc1939d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

El componente `ColumnsLayout` en webforJ permite a los desarrolladores crear diseños utilizando un diseño vertical flexible y receptivo. Este diseño proporciona columnas dinámicas que se ajustan según el ancho disponible. Este componente simplifica la creación de diseños de varias columnas al gestionar automáticamente los puntos de ruptura y las alineaciones.

:::info Diseños Horizontales 
Esto se puede utilizar en lugar de, o en combinación con, el componente [`FlexLayout`](./flex-layout) - una herramienta igualmente potente para diseños horizontales.
:::

## Basics {#basics}

Cuando se instancia por primera vez, el `ColumnsLayout` utiliza dos columnas para mostrar los elementos añadidos al diseño. Por defecto, ocupa el ancho completo de sus elementos padre y crece según sea necesario para ajustarse al contenido adicional. La visualización de los elementos añadidos se puede calibrar más con el uso de los ajustes de [`Breakpoint`](./columns-layout#breakpoints) y [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments), que se discuten en las siguientes secciones con más detalle.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Breakpoints {#breakpoints}

En su núcleo, el `ColumnsLayout` está diseñado para proporcionar un sistema fluido y similar a una cuadrícula que se adapta al ancho de su contenedor padre. A diferencia de los sistemas de cuadrícula fijos tradicionales, este diseño permite a los desarrolladores especificar un número de columnas a un ancho determinado y calcula dinámicamente el número de columnas mostradas en función de los objetos `Breakpoint` establecidos.

Esto permite que un `ColumnsLayout` se adapte suavemente desde un espacio más restringido en pantallas pequeñas hasta un área más amplia en pantallas más grandes, ofreciendo un diseño receptivo a un desarrollador sin necesidad de una implementación personalizada.

### Understanding a `Breakpoint` {#understanding-a-breakpoint}

Un `Breakpoint` se puede especificar utilizando la clase `Breakpoint`, que toma tres parámetros:

1. **Nombre (opcional)**:
Nombrar un punto de ruptura te permite hacer referencia a él en configuraciones futuras.

2. **Ancho mínimo**:
Cada punto de ruptura tiene un rango específico que determina cuándo se aplica su diseño. El ancho mínimo se define explícitamente, y el siguiente punto de ruptura determina el ancho máximo si existe. Puedes usar un entero para definir el ancho mínimo en píxeles o usar un `String` para especificar otras unidades como `vw`, `%`, o `em`.

3. **Número de columnas**:
Especifica cuántas columnas debe tener un punto de ruptura con este entero.

:::info Evaluación de `Breakpoint`
Los puntos de ruptura se evalúan en orden ascendente de ancho, lo que significa que el diseño utilizará el primer punto de ruptura coincidente.
:::

### Aplicando puntos de ruptura {#applying-breakpoints}

Los puntos de ruptura se aplican a un `ColumnsLayout` de una de dos maneras: durante la construcción o en una `List` utilizando el método `setBreakpoints()`:

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
    // Una columna en anchos >= 0px
    new Breakpoint(0, 1),
    // Dos columnas en anchos >= 600px
    new Breakpoint(600, 2),
    // Cuatro columnas en anchos >= 1200px
    new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

La demostración a continuación muestra un ejemplo de configurar múltiples puntos de ruptura en la construcción, utilizando puntos de ruptura para configurar el [`Span`](#column-span-and-spans-per-breakpoint) de un componente y demuestra las capacidades de redimensionamiento del `ColumnsLayout` cuando se redimensiona la aplicación:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Column `Span` y spans por `Breakpoint` {#column-span-and-spans-per-breakpoint}

Los spans de columna en `ColumnsLayout` te permiten controlar cuántas columnas ocupa un elemento, dándote más control sobre la apariencia de tu diseño a diferentes anchos. Esto es especialmente útil cuando necesitas que ciertos elementos ocupen más o menos espacio según el tamaño de la pantalla o los requisitos de diseño.

### Managing column spans {#managing-column-spans}

Por defecto, cada elemento en el `ColumnsLayout` ocupa exactamente una columna. Sin embargo, puedes personalizar este comportamiento configurando el span de columna para elementos individuales. Un span especifica cuántas columnas debe ocupar un elemento.

```java
Button button = new Button("Click Me");
layout.addComponent(button);
// El elemento ocupa dos columnas
layout.setSpan(button, 2);
```

En el ejemplo anterior, el botón ocupa dos columnas en lugar de la predeterminada. El método `setSpan()` te permite especificar cuántas columnas debe abarcar un componente dentro del diseño.

### Ajustando los spans de columna con puntos de ruptura {#adjusting-column-spans-with-breakpoints}

También puedes ajustar los spans de columna dinámicamente según los puntos de ruptura. Esta característica es útil cuando deseas que un elemento abarque diferentes cantidades de columnas según el tamaño de la pantalla. Por ejemplo, es posible que desees que un elemento ocupe una columna en dispositivos móviles, pero que abarque múltiples columnas en pantallas más grandes.

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
//el campo de correo electrónico abarcará dos columnas cuando el punto de ruptura medio esté activo
columnsLayout.setSpan(email, "medium", 2);
//...
```

Este nivel de personalización asegura que tu diseño se mantenga receptivo y apropiadamente estructurado en diferentes dispositivos.

## Colocando elementos dentro de columnas {#placing-items-within-columns}

`ColumnsLayout` proporciona la capacidad de colocar elementos en columnas específicas, brindando un mayor control sobre la disposición de los elementos. Puedes especificar manualmente dónde debería aparecer un elemento dentro del diseño, asegurando que los componentes importantes se muestren como se pretende.

### Colocación básica de columnas {#basic-column-placement}

Por defecto, los elementos se colocan en la siguiente columna disponible, llenando de izquierda a derecha. Sin embargo, puedes anular este comportamiento y especificar la columna exacta donde debe colocarse un elemento. Para colocar un elemento en una columna específica, utiliza el método `setColumn()`. En este ejemplo, el botón se coloca en la segunda columna del diseño, independientemente del orden en que se añadió respecto a otros componentes:

```java
Button button = new Button("Submit");
layout.addComponent(button);
// Coloca el elemento en la segunda columna
layout.setColumn(button, 2);  
```

### Ajustando la colocación por punto de ruptura {#adjusting-placement-per-breakpoint}

Al igual que con los spans de columna, utilizas puntos de ruptura para ajustar la colocación de los elementos según el tamaño de la pantalla. Esto es útil para reordenar o desplazar elementos en el diseño a medida que cambia el viewport.

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
//el campo de correo electrónico aparecerá en la segunda columna cuando el punto de ruptura medio esté activo
columnsLayout.setColumn(email, "medium", 2); 
//...
```

En la siguiente demostración, observa que cuando se activa el punto de ruptura `"medium"`, el campo `email` abarca ambas columnas, y el campo `confirmPassword` se coloca en la primera columna, en lugar de su colocación predeterminada en la segunda columna:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Evitar colisiones
Cuando múltiples elementos se colocan en un diseño con diferentes spans y/o asignaciones de columna, asegúrate de que los spans y las colocaciones combinadas de los elementos en una fila no se superpongan. El diseño intenta gestionar el espaciado automáticamente, pero un diseño cuidadoso de spans y puntos de ruptura evita la visualización no deseada de elementos.
:::

## Alineaciones de elementos verticales y horizontales {#vertical-and-horizontal-item-alignments}

Cada elemento en el `ColumnsLayout` se puede alinear tanto horizontal como verticalmente dentro de su columna, dando control sobre cómo se posiciona el contenido dentro del diseño.

La **alineación horizontal** de un elemento se controla mediante el método `setHorizontalAlignment()`. Esta propiedad determina cómo se alinea un elemento dentro de su columna a lo largo del eje horizontal.

La **alineación vertical** especifica cómo se posiciona un elemento dentro de su columna a lo largo del eje vertical. Esto es útil cuando las columnas tienen alturas variables y se quiere controlar cómo se distribuyen verticalmente los elementos. 

Las opciones de `Alignment` disponibles incluyen:

- `START`: Alinea el elemento a la izquierda de la columna (predeterminado).
- `CENTER`: Centra el elemento horizontalmente dentro de la columna.
- `END`: Alinea el elemento a la derecha de la columna.
- `STRETCH`: Estira el componente para llenar el diseño.
- `BASELINE`: Se alinea según el texto o contenido dentro de la columna, alineando los elementos a la línea base del texto en lugar de otras opciones de alineación.
- `AUTO`: Alineación automática.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

En la demo anterior, al botón `Submit` se le ha dado `ColumnsLayout.Alignment.END` para asegurar que aparezca al final, o en este caso a la derecha, de su columna.

## Espaciado de elementos {#item-spacing}

Controlar el espacio entre columnas en el `ColumnsLayout` entre columnas (espaciado horizontal) y entre filas (espaciado vertical) ayuda a los desarrolladores a ajustar el diseño.

Para establecer el espaciado horizontal del diseño, utiliza el método `setHorizontalSpacing()`:

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
Puedes usar un entero para definir el ancho mínimo en píxeles o usar un `String` para especificar otras unidades como `vw`, `%`, o `em`.
:::

## Diseños horizontales y verticales {#horizontal-and-vertical-layouts}

Construir diseños receptivos y atractivos es posible utilizando tanto el componente [`FlexLayout`](./flex-layout) como el componente `ColumnsLayout`, así como una combinación de ambos. A continuación se muestra un ejemplo del artículo [formulario creado en el FlexLayout](./flex-layout#example-form), pero utilizando un esquema `ColumnLayout` en su lugar:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Estilo {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
