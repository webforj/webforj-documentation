---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: ed7626149e8b31e663de874e83935567
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

El componente `ColumnsLayout` en webforJ permite a los desarrolladores crear diseños utilizando un diseño vertical flexible y responsivo. Este diseño proporciona columnas dinámicas que se ajustan según el ancho disponible. Este componente simplifica la creación de diseños de múltiples columnas al gestionar automáticamente los puntos de ruptura y las alineaciones.

:::info Diseños horizontales 
Esto se puede usar en lugar de, o en combinación con, el componente [`FlexLayout`](./flex-layout) - una herramienta igualmente poderosa para diseños horizontales.
:::

## Básicos {#basics}

Cuando se instancia por primera vez, el `ColumnsLayout` utiliza dos columnas para mostrar los elementos añadidos al diseño. Por defecto, ocupa todo el ancho de sus elementos padres y crece según sea necesario para ajustarse al contenido adicional. La visualización de los elementos agregados se puede calibrar aún más utilizando las configuraciones de [`Breakpoint`](./columns-layout#breakpoints) y [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments), las cuales se describen en las siguientes secciones con más detalle.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Puntos de ruptura {#breakpoints}

En su núcleo, el `ColumnsLayout` está diseñado para proporcionar un sistema fluido, similar a una cuadrícula que se adapta al ancho de su contenedor padre. A diferencia de los sistemas de cuadrícula fijos tradicionales, este diseño permite a los desarrolladores especificar un número de columnas en un ancho dado, y calcula dinámicamente el número de columnas mostradas basado en los objetos `Breakpoint` establecidos. 

Esto permite que un `ColumnsLayout` se adapte suavemente de un espacio más restringido en pantallas pequeñas a un área más amplia en pantallas más grandes, ofreciendo un diseño responsivo a un desarrollador sin necesidad de implementación personalizada.

### Comprendiendo un `Breakpoint` {#understanding-a-breakpoint}

Un `Breakpoint` se puede especificar utilizando la clase `Breakpoint`, que toma tres parámetros:

1. **Nombre (opcional)**:
Nombrar un punto de ruptura permite hacer referencia a él en configuraciones futuras.

2. **Ancho mínimo**:
Cada punto de ruptura tiene un rango específico que determina cuándo se aplica su diseño. El ancho mínimo se define explícitamente, y el siguiente punto de ruptura determina el ancho máximo si existe. Puedes usar un entero para definir el ancho mínimo en píxeles o usar un `String` para especificar otras unidades como `vw`, `%`, o `em`.

3. **Número de columnas**:
Especifica cuántas columnas debería tener un punto de ruptura con este número entero.

:::info Evaluación de `Breakpoint`
Los puntos de ruptura se evalúan en orden ascendente del ancho, lo que significa que el diseño utilizará el primer punto de ruptura coincidente.
:::

### Aplicando puntos de ruptura {#applying-breakpoints}

Los puntos de ruptura se aplican a un `ColumnsLayout` de dos formas: durante la construcción, o utilizando el método `addBreakpoint(Breakpoint)` como se muestra a continuación. 

```java
ColumnsLayout layout = new ColumnsLayout()
    // Una columna en anchos >= 0px
    .addBreakpoint(new Breakpoint(0, 1))
    // Dos columnas en anchos >= 600px
    .addBreakpoint(new Breakpoint(600, 2))
    // Cuatro columnas en anchos >= 1200px
    .addBreakpoint(new Breakpoint(1200, 4));  
```

La demostración a continuación muestra un ejemplo de establecer múltiples puntos de ruptura durante la construcción, utilizando puntos de ruptura para configurar el [`Span`](#column-span-and-spans-per-breakpoint) de un componente, y demuestra las capacidades de redimensionamiento del `ColumnsLayout` cuando la aplicación es redimensionada:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## `Span` de columna y spans por `Breakpoint` {#column-span-and-spans-per-breakpoint}

Los spans de columna en `ColumnsLayout` te permiten controlar cuántas columnas ocupa un ítem, dándote más control sobre la apariencia de tu diseño a diferentes anchos. Esto es especialmente útil cuando necesitas que ciertos elementos ocupen más o menos espacio dependiendo del tamaño de la pantalla o de los requisitos de diseño.

### Gestionando los spans de columna {#managing-column-spans}

Por defecto, cada ítem en el `ColumnsLayout` ocupa exactamente una columna. Sin embargo, puedes personalizar este comportamiento estableciendo el span de columna para ítems individuales. Un span especifica cuántas columnas debería ocupar un ítem.

```java
Button button = new Button("Click Me");
layout.addComponent(button);
// El ítem ocupa dos columnas
layout.setSpan(button, 2);
```

En el ejemplo anterior, el botón ocupa dos columnas en lugar de la una por defecto. El método `setSpan()` te permite especificar cuántas columnas debería ocupar un componente dentro del diseño.

### Ajustando los spans de columna con puntos de ruptura {#adjusting-column-spans-with-breakpoints}

También puedes ajustar los spans de columna dinámicamente basándote en puntos de ruptura. Esta función es útil cuando quieres que un ítem ocupe diferentes números de columnas dependiendo del tamaño de la pantalla. Por ejemplo, es posible que desees que un elemento ocupe una sola columna en dispositivos móviles, pero que ocupe múltiples columnas en pantallas más grandes.

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
//el campo de email ocupará dos columnas cuando el punto de ruptura medio esté activo
columnsLayout.setSpan(email, "medium", 2);
//...
```

Este nivel de personalización asegura que tu diseño permanezca responsivo y apropiadamente estructurado a través de diferentes dispositivos.

## Colocando ítems dentro de columnas {#placing-items-within-columns}

`ColumnsLayout` proporciona la capacidad de colocar ítems en columnas específicas, brindando más control sobre la disposición de los elementos. Puedes especificar manualmente dónde debería aparecer un ítem dentro del diseño, asegurando que los componentes importantes se muestren como se pretende.

### Colocación básica de columnas {#basic-column-placement}

Por defecto, los ítems se colocan en la siguiente columna disponible, llenando de izquierda a derecha. Sin embargo, puedes anular este comportamiento y especificar la columna exacta donde debe colocarse un ítem. Para colocar un ítem en una columna específica, utiliza el método `setColumn()`. En este ejemplo, el botón se coloca en la segunda columna del diseño, independientemente del orden en el que se haya agregado en relación con otros componentes:

```java
Button button = new Button("Submit");
layout.addComponent(button);
// Coloca el ítem en la segunda columna
layout.setColumn(button, 2);  
```

### Ajustando la colocación por punto de ruptura {#adjusting-placement-per-breakpoint}

Al igual que con los spans de columna, utilizas puntos de ruptura para ajustar la colocación de ítems según el tamaño de la pantalla. Esto es útil para reordenar o reubicar elementos en el diseño a medida que cambia el viewport.

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
//el campo de email aparecerá en la segunda columna cuando el punto de ruptura medio esté activo
columnsLayout.setColumn(email, "medium", 2); 
//...
```

En la siguiente demostración, nota que cuando se activa el punto de ruptura `"medium"`, el campo `email` ocupa ambas columnas, y el campo `confirmPassword` se coloca en la primera columna, en lugar de su colocación por defecto en la segunda columna:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Evitar colisiones
Cuando varios ítems están colocados en un diseño con diferentes spans y/o asignaciones de columnas, asegúrate de que las combinaciones de spans y colocaciones de ítems en una fila no se superpongan. El diseño intenta gestionar el espacio automáticamente, pero un diseño cuidadoso de spans y puntos de ruptura previene la visualización no intencionada de ítems.
:::

## Alineaciones de ítems verticales y horizontales {#vertical-and-horizontal-item-alignments}

Cada ítem en el `ColumnsLayout` puede ser alineado tanto horizontal como verticalmente dentro de su columna, lo que brinda control sobre cómo se posiciona el contenido dentro del diseño.

**La alineación horizontal** de un ítem se controla utilizando el método `setHorizontalAlignment()`. Esta propiedad determina cómo se alinea un ítem dentro de su columna a lo largo del eje horizontal.

**La alineación vertical** especifica cómo se posiciona un ítem dentro de su columna a lo largo del eje vertical. Esto es útil cuando las columnas tienen alturas variables y deseas controlar cómo se distribuyen verticalmente los ítems. 

Las opciones de `Alignment` disponibles incluyen:

- `START`: Alinea el ítem a la izquierda de la columna (por defecto).
- `CENTER`: Centra el ítem horizontalmente dentro de la columna.
- `END`: Alinea el ítem a la derecha de la columna.
- `STRETCH`: Estira el componente para llenar el diseño.
- `BASELINE`: Alinea según el texto o contenido dentro de la columna, alineando ítems a la línea de base del texto en lugar de otras opciones de alineación.
- `AUTO`: Alineación automática.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

En la demostración anterior, al botón `Submit` se le ha dado `ColumnsLayout.Alignment.END` para asegurarse de que aparezca al final, o en este caso a la derecha, de su columna.

## Espaciado de ítems {#item-spacing}

Controlar el espacio entre columnas en el `ColumnsLayout` entre columnas (espaciado horizontal) y entre filas (espaciado vertical) ayuda a los desarrolladores a afinar el diseño.

Para establecer el espaciado horizontal del diseño, utiliza el método `setHorizontalSpacing()`:

```java
// Establecer 20px de espacio entre columnas
layout.setHorizontalSpacing(20);  
```

De manera similar, utiliza el método `setVerticalSpacing()` para configurar el espacio entre filas del diseño:

```java
// Establecer 15px de espacio entre filas
layout.setVerticalSpacing(15);  
```

:::tip Unidades CSS
Puedes usar un entero para definir el ancho mínimo en píxeles o usar un `String` para especificar otras unidades como `vw`, `%`, o `em`.
:::

## Diseños horizontales y verticales {#horizontal-and-vertical-layouts}

Construir diseños responsivos y atractivos es posible utilizando tanto el componente [`FlexLayout`](./flex-layout) como el componente `ColumnsLayout`, así como una combinación de ambos. A continuación se muestra una muestra del [formulario creado en el artículo FlexLayout](./flex-layout#example-form), pero utilizando un esquema de `ColumnLayout` en su lugar:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Estilización {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
