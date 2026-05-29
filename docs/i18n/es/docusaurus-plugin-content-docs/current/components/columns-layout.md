---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: 27b0727ced855ad047db6be3e142801f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

El componente `ColumnsLayout` organiza elementos en un diseño basado en columnas responsivo que se ajusta según el ancho disponible. Los puntos de quiebre y alineaciones se manejan automáticamente, por lo que construir formularios de varias columnas y cuadrículas de contenido no requiere lógica receptiva personalizada.

<!-- INTRO_END -->

## Comportamiento predeterminado {#default-behavior}

Por defecto, un `ColumnsLayout` organiza los elementos en dos columnas y toma el ancho completo de su padre. La visualización se puede ajustar aún más con puntos de quiebre y configuraciones de alineación, que se tratan en las secciones a continuación.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Diseños horizontales 
Esto se puede usar en lugar de, o en combinación con, el componente [`FlexLayout`](./flex-layout) - una herramienta igualmente poderosa para diseños horizontales.
:::

## Puntos de quiebre {#breakpoints}

En su núcleo, el `ColumnsLayout` está diseñado para proporcionar un sistema fluido y similar a una cuadrícula que se adapta al ancho de su contenedor padre. A diferencia de los sistemas de cuadrícula fijos tradicionales, este diseño permite a los desarrolladores especificar un número de columnas a un ancho dado y calcula dinámicamente el número de columnas mostradas basado en objetos `Breakpoint` establecidos. 

Esto permite que un `ColumnsLayout` se adapte suavemente de un espacio más restringido en pantallas pequeñas a un área más amplia en pantallas más grandes, ofreciendo un diseño responsivo a un desarrollador sin necesidad de implementación personalizada.

### Entendiendo un `Breakpoint` {#understanding-a-breakpoint}

Un `Breakpoint` se puede especificar utilizando la clase `Breakpoint`, que toma tres parámetros:

1. **Nombre (opcional)**:
Nombrar un breakpoint te permite hacer referencia a él en configuraciones futuras.

2. **Ancho mínimo**:
Cada punto de quiebre tiene un rango específico que determina cuándo se aplica su diseño. El ancho mínimo se define explícitamente, y el siguiente punto de quiebre determina el ancho máximo si existe. Puedes usar un entero para definir el ancho mínimo en píxeles o usar un `String` para especificar otras unidades como `vw`, `%`, o `em`.

3. **Número de columnas**:
Especifica cuántas columnas debe tener un breakpoint con este entero.

:::info Evaluación de `Breakpoint`
Los breakpoints se evalúan en orden ascendente del ancho, lo que significa que el diseño utilizará el primer breakpoint coincidente.
:::


### Aplicando puntos de quiebre {#applying-breakpoints}

Los puntos de quiebre se aplican a un `ColumnsLayout` de una de dos maneras: durante la construcción, o en una `List` utilizando el método `setBreakpoints()`: 

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

La demostración a continuación muestra un ejemplo de establecer múltiples puntos de quiebre en la construcción, utilizando puntos de quiebre para configurar el [`Span`](#column-span-and-spans-per-breakpoint) de un componente, y demuestra las capacidades de redimensionamiento del `ColumnsLayout` cuando la aplicación es redimensionada:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## `Span` de columna y spans por `Breakpoint` {#column-span-and-spans-per-breakpoint}

Los spans de columna en `ColumnsLayout` te permiten controlar cuántas columnas ocupa un ítem, dándote más control sobre la apariencia de tu diseño a diversos anchos. Esto es especialmente útil cuando necesitas que ciertos elementos ocupen más o menos espacio dependiendo del tamaño de la pantalla o los requisitos de diseño.

### Gestionando spans de columna {#managing-column-spans}

Por defecto, cada ítem en el `ColumnsLayout` ocupa exactamente una columna. Sin embargo, puedes personalizar este comportamiento estableciendo el span de columna para ítems individuales. Un span especifica cuántas columnas debe ocupar un ítem.

```java
Button button = new Button("Clic aquí");
layout.addComponent(button);
// Ítem ocupa dos columnas
layout.setSpan(button, 2);
```

En el ejemplo anterior, el botón ocupa dos columnas en lugar de la predeterminada. El método `setSpan()` te permite especificar cuántas columnas debe abarcar un componente dentro del diseño.

### Ajustando spans de columna con puntos de quiebre {#adjusting-column-spans-with-breakpoints}

También puedes ajustar los spans de columna dinámicamente según los puntos de quiebre. Esta función es útil cuando quieres que un ítem abarque diferentes números de columnas dependiendo del tamaño de la pantalla. Por ejemplo, es posible que desees que un elemento ocupe una sola columna en dispositivos móviles, pero abarque múltiples columnas en pantallas más grandes.

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
//el campo de email abarcará dos columnas cuando el breakpoint medio esté activo
columnsLayout.setSpan(email, "medium", 2);
//...
```

Este nivel de personalización asegura que tu diseño siga siendo responsivo y apropiadamente estructurado en diferentes dispositivos.

## Colocando ítems dentro de columnas {#placing-items-within-columns}

`ColumnsLayout` proporciona la capacidad de colocar ítems en columnas específicas, otorgando más control sobre la disposición de los elementos. Puedes especificar manualmente dónde debe aparecer un ítem dentro del diseño, asegurando que los componentes importantes se muestren como se pretende.

### Colocación básica de columnas {#basic-column-placement}

Por defecto, los ítems se colocan en la siguiente columna disponible, llenando de izquierda a derecha. Sin embargo, puedes anular este comportamiento y especificar la columna exacta donde un ítem debe ser colocado. Para colocar un ítem en una columna específica, usa el método `setColumn()`. En este ejemplo, el botón se coloca en la segunda columna del diseño, independientemente del orden en que se añadió en relación con otros componentes:

```java
Button button = new Button("Enviar");
layout.addComponent(button);
// Colocar el ítem en la segunda columna
layout.setColumn(button, 2);  
```

### Ajustando la colocación por punto de quiebre {#adjusting-placement-per-breakpoint}

Al igual que con los spans de columna, utilizas puntos de quiebre para ajustar la colocación de los ítems según el tamaño de la pantalla. Esto es útil para reordenar o mover elementos en el diseño a medida que cambia la ventana de visualización.

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
//el campo de email aparecerá en la segunda columna cuando el breakpoint medio esté activo
columnsLayout.setColumn(email, "medium", 2); 
//...
```

En la siguiente demostración, observa que cuando se activa el `"medium"` breakpoint, el campo `email` abarca ambas columnas, y el campo `confirmPassword` se coloca en la primera columna, en lugar de su colocación predeterminada en la segunda columna:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Evitar colisiones
Cuando se colocan múltiples ítems en un diseño con diferentes spans y/o asignaciones de columna, asegúrate de que los spans y colocaciones combinados de los ítems en una fila no se superpongan. El diseño intenta gestionar el espaciado automáticamente, pero un diseño cuidadoso de spans y puntos de quiebre previene la visualización no intencionada de ítems.
:::

## Alineaciones verticales y horizontales de ítems {#vertical-and-horizontal-item-alignments}

Cada ítem en el `ColumnsLayout` puede ser alineado tanto horizontal como verticalmente dentro de su columna, dando control sobre cómo se posiciona el contenido dentro del diseño.

**La alineación horizontal** de un ítem se controla utilizando el método `setHorizontalAlignment()`. Esta propiedad determina cómo se alinea un ítem dentro de su columna a lo largo del eje horizontal.

**La alineación vertical** especifica cómo se posiciona un ítem dentro de su columna a lo largo del eje vertical. Esto es útil cuando las columnas tienen alturas variables y deseas controlar cómo se distribuyen verticalmente los ítems. 

Las opciones de `Alineación` disponibles incluyen:

- `INICIO`: Alinea el ítem a la izquierda de la columna (predeterminado).
- `CENTRO`: Centra el ítem horizontalmente dentro de la columna.
- `FIN`: Alinea el ítem a la derecha de la columna.
- `ESTIRAR`: Estira el componente para llenar el diseño.
- `LÍNEA BASE`: Alinea basado en el texto o contenido dentro de la columna, alineando ítems a la línea base del texto en lugar de otras opciones de alineación.
- `AUTOMÁTICO`: Alineación automática.

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

En la demostración anterior, el botón `Enviar` se le ha dado `ColumnsLayout.Alignment.END` para asegurar que aparezca al final, o en este caso a la derecha, de su columna.

## Espaciado de ítems {#item-spacing}

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

Construir diseños responsivos y atractivos es posible utilizando tanto el componente [`FlexLayout`](./flex-layout) como el componente `ColumnsLayout`, así como una combinación de ambos. A continuación se presenta una muestra del [formulario creado en el artículo de FlexLayout](./flex-layout#example-form), pero utilizando un esquema `ColumnLayout` en su lugar:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Estilo {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
