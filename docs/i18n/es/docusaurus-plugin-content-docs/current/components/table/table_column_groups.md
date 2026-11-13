---
title: Grupos de columnas
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 4babef21ef20728da6aa7eec5829e4d1
---
<!-- vale off -->
# Grupos de columnas <DocChip chip='since' label='25.12' />
<!-- vale on -->

Los grupos de columnas permiten organizar columnas relacionadas bajo encabezados compartidos de varias filas. La etiqueta de un grupo abarca sus columnas secundarias, lo que facilita a los usuarios examinar y comprender la estructura de tablas complejas. Los grupos se pueden anidar a cualquier profundidad, y `Table` representa automáticamente el número correcto de filas de encabezado.

## Crear grupos de columnas {#creating-column-groups}

Crea un grupo con el método de fábrica `ColumnGroup.of()` y luego encadena llamadas a `add()` para añadir referencias a columnas, otros grupos o una combinación de ambos. Aplica los grupos a un `Table` con `setColumnGroups()`.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Cuando se establecen grupos, `Table` representa un encabezado de varias filas en el que cada etiqueta de grupo abarca sus columnas secundarias. La profundidad de anidamiento determina cuántas filas de encabezado aparecen. Un grupo sin anidar añade una fila más; un anidamiento de dos niveles añade dos, y así sucesivamente.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablenestedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableNestedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

<!-- vale Google.OxfordComma = NO -->
Los grupos se pueden establecer o modificar en cualquier momento, antes o después de representar el `Table`. Pasa `null` o una lista vacía a `setColumnGroups()` para quitar todos los grupos y volver a un encabezado de una sola fila.
<!-- vale Google.OxfordComma = YES -->

```java
// Quitar todos los grupos de columnas
table.setColumnGroups(null);
```

## Orden de las columnas {#column-ordering}

Cuando hay grupos activos, el orden visual de las columnas lo determina el árbol de grupos, no el orden en que se añadieron las columnas a `Table`. El recorrido del árbol se realiza en profundidad, de izquierda a derecha.

```
Columnas añadidas:  [A, B, C, D, E]
Grupos:             Group "G1" [C, A], Group "G2" [E, D]
Orden visual:       C, A, E, D, B
```

Las columnas sin agrupar, es decir, las que no se incluyen en ningún grupo, no se ocultan. Aparecen en su posición natural con respecto a las columnas agrupadas, según el orden en que se añadieron originalmente a `Table`.

En este ejemplo, `Number` aparece primero porque se añadió antes que `Title`. `Label` aparece entre `Genre` y `Cost` porque se añadió entre ambas en el orden original de las columnas:

```
Columnas añadidas:  [Number, Title, Artist, Genre, Label, Cost]
Grupos:             Group "Music" [Title, Artist, Genre], Group "Pricing" [Cost]
Orden visual:       Number, Title, Artist, Genre, Label, Cost
```

La siguiente demostración ilustra este comportamiento. `Number` y `Label` no se incluyen en ningún grupo, pero mantienen sus posiciones naturales según el orden en que se añadieron a `Table`.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroupordering'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

:::tip Controlar la ubicación de las columnas sin agrupar
Para controlar explícitamente la ubicación de las columnas sin agrupar, inclúyelas como referencias de columna de nivel superior en el árbol de grupos.
:::

## Mover columnas dentro de grupos {#column-moving-within-groups}

Cuando hay grupos activos, el movimiento de columnas mediante arrastrar y soltar está restringido para mantener la integridad de los grupos:

- **Dentro de un grupo**: una columna dentro de un grupo solo se puede mover dentro de su grupo principal inmediato. Si se arrastra fuera del grupo, se rechaza el movimiento y la columna vuelve a su posición original.
- **Columnas sin agrupar**: una columna sin agrupar solo se puede mover a posiciones ocupadas por otras columnas sin agrupar. No se puede soltar en medio de un grupo.
- **Reordenar grupos**: se puede arrastrar un grupo entero para reordenarlo entre sus grupos hermanos del mismo nivel de anidamiento.

```
Grupos:  Group "G1" [A, B, C], Group "G2" [D, E]

Mover A a la posición 2 -> Aceptado (dentro de G1, resultado: [B, C, A])
Mover A a la posición 3 -> Rechazado (la posición 3 está dentro de G2)
Mover D a la posición 4 -> Aceptado (dentro de G2, resultado: [E, D])
Mover D a la posición 1 -> Rechazado (la posición 1 está dentro de G1)
```

## Fijar grupos {#pinning-groups}

Se puede fijar un grupo a la izquierda o a la derecha mediante `setPinDirection()`. Todas las columnas del grupo heredan la dirección de fijación del grupo, que reemplaza la configuración de fijación de cada columna individual.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Tanto "number" como "title" se fijan a la izquierda,
// independientemente de sus propias opciones de fijación
```

Las columnas sin agrupar conservan su propia dirección de fijación, definida en la columna.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablepinnedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

## Altura de los encabezados de grupo {#group-header-height}

La altura de las filas de encabezado de grupo se puede controlar de forma independiente de la de los encabezados de columna normales mediante `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Las filas de grupo tienen una altura de 32 px
table.setHeaderHeight(48);      // La fila de encabezado de columna mantiene una altura de 48 px
```

De forma predeterminada, la altura de los encabezados de grupo coincide con la altura predeterminada de los encabezados.

## Aplicar estilos a los grupos con partes CSS {#styling-groups-with-css-parts}

Los encabezados de grupo y las columnas exponen partes CSS a las que se pueden aplicar estilos mediante `::part()`. Están disponibles las siguientes partes:

| Parte | Descripción |
| --- | --- |
| `cell-group-{ID}` | Celda del encabezado de grupo, seleccionada por ID de grupo |
| `cell-group-depth-{N}` | Celda del encabezado de grupo, seleccionada por profundidad (`0` = nivel superior, `1` = segundo nivel, etc.) |
| `cell-column-{ID}` | Todas las celdas (de encabezado y cuerpo) de un ID de columna determinado |
| `cell-content-group-{ID}` | Contenedor de contenido dentro de un encabezado de grupo |
| `cell-label-group-{ID}` | Etiqueta dentro de un encabezado de grupo |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/frontend/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### Aplicar estilos según el ID del grupo {#styling-by-group-id}

Usa el ID del grupo para aplicar colores o tipografía exclusivos a grupos específicos.

```css
dwc-table::part(cell-group-catalog) {
  background: var(--dwc-color-primary-30);
  color: var(--dwc-color-primary-text-30);
  font-weight: 600;
}

dwc-table::part(cell-group-bio) {
  background: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}

dwc-table::part(cell-column-title) {
  font-weight: 600;
}
```

### Aplicar estilos según la profundidad {#styling-by-depth}

Las partes basadas en la profundidad son útiles para aplicar estilos uniformes a todos los grupos de un nivel de anidamiento determinado, sin tener que seleccionar cada grupo individualmente.

```css
/* Aplicar estilos a todos los grupos de nivel superior */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Aplicar estilos a todos los grupos de segundo nivel */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Columnas ocultas {#hidden-columns}

Las columnas ocultas se excluyen del orden visual y del diseño del encabezado. Si un grupo contiene una combinación de columnas visibles y ocultas, solo aparecen las visibles y el valor `colspan` del grupo se ajusta en consecuencia. Si todas las columnas de un grupo están ocultas, el encabezado del grupo no se representa.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablehiddencolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->
