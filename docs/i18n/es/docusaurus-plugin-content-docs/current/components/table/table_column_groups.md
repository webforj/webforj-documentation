---
title: Column Groups
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 7a0ad76ea48fafddc0aa9965309b48b8
---
<DocChip chip='since' label='25.12' />

Los grupos de columnas permiten organizar columnas relacionadas bajo encabezados compartidos de múltiples filas. Una etiqueta de grupo se extiende a través de sus columnas hijas, facilitando a los usuarios escanear y comprender la estructura de tablas complejas. Los grupos pueden anidarse a cualquier profundidad, y el `Table` renderiza automáticamente el número correcto de filas de encabezado.

## Creando grupos de columnas {#creating-column-groups}

Crea un grupo con el método de fábrica `ColumnGroup.of()`, luego encadena llamadas a `add()` para poblarlo con referencias de columnas, otros grupos, o una mezcla de ambos. Aplica los grupos a un `Table` con `setColumnGroups()`.

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

Cuando los grupos están configurados, el `Table` renderiza un encabezado de múltiples filas donde cada etiqueta de grupo se extiende a través de sus columnas hijas. La profundidad de anidación determina cuántas filas de encabezado aparecen. Un grupo plano agrega una fila adicional, mientras que una anidación de dos niveles agrega dos, y así sucesivamente.

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
Los grupos se pueden establecer o cambiar en cualquier momento, antes o después de que el `Table` se haya renderizado. Pasa `null` o una lista vacía a `setColumnGroups()` para eliminar toda agrupación y volver a un encabezado de una sola fila.
<!-- vale Google.OxfordComma = YES -->

```java
// Eliminar todos los grupos de columnas
table.setColumnGroups(null);
```

## Orden de las columnas {#column-ordering}

Cuando los grupos están activos, el orden visual de las columnas se determina por el árbol de grupos en lugar del orden en que se agregaron las columnas al `Table`. El árbol se recorre en profundidad, de izquierda a derecha.

```
Columnas agregadas:  [A, B, C, D, E]
Grupos:             Grupo "G1" [C, A], Grupo "G2" [E, D]
Orden visual:       C, A, E, D, B
```

Las columnas no agrupadas, aquellas que no están referenciadas en ningún grupo, no están ocultas. Aparecen en su posición natural relativa a las columnas agrupadas, basado en el orden en que fueron originalmente agregadas al `Table`.

En este ejemplo, `Number` aparece primero porque fue agregado antes que `Title`. `Label` aparece entre `Genre` y `Cost` porque fue agregado entre ellos en el orden de columna original:

```
Columnas agregadas:  [Number, Title, Artist, Genre, Label, Cost]
Grupos:             Grupo "Music" [Title, Artist, Genre], Grupo "Pricing" [Cost]
Orden visual:       Number, Title, Artist, Genre, Label, Cost
```

La siguiente demostración ilustra este comportamiento. `Number` y `Label` no están referenciados en ningún grupo, pero mantienen sus posiciones naturales basadas en el orden en que fueron agregadas al `Table`.

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

:::tip Controlando la colocación de columnas no agrupadas
Para controlar explícitamente la colocación de columnas no agrupadas, inclúyelas como referencias de columna de nivel superior en el árbol del grupo.
:::

## Movimiento de columnas dentro de grupos {#column-moving-within-groups}

Cuando los grupos están activos, el movimiento de columnas mediante arrastrar y soltar está restringido para mantener la integridad del grupo:

- **Dentro de un grupo**: una columna dentro de un grupo solo puede moverse dentro de su grupo padre inmediato. Arrastrarla fuera del grupo es rechazado y la columna regresa a su posición original.
- **Columnas no agrupadas**: una columna no agrupada solo puede moverse a posiciones ocupadas por otras columnas no agrupadas. No puede ser colocada en medio de un grupo.
- **Reordenar grupos**: un grupo completo puede ser arrastrado para reordenarse entre sus hermanos en el mismo nivel de anidación.

```
Grupos:  Grupo "G1" [A, B, C], Grupo "G2" [D, E]

Mover A a la posición 2 -> OK (dentro de G1, resultado: [B, C, A])
Mover A a la posición 3 -> Rechazado (la posición 3 está dentro de G2)
Mover D a la posición 4 -> OK (dentro de G2, resultado: [E, D])
Mover D a la posición 1 -> Rechazado (la posición 1 está dentro de G1)
```

## Fijando grupos {#pinning-groups}

Un grupo puede ser fijado a la izquierda o a la derecha usando `setPinDirection()`. Todas las columnas dentro del grupo heredan la dirección de pin del grupo, y las configuraciones de pin individuales de las columnas son sobreescritas por el grupo.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "Información de ID")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Tanto "number" como "title" están fijados a la izquierda,
// independientemente de sus propias configuraciones de pin
```

Las columnas no agrupadas retienen su propia dirección de pin de su definición de columna.

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

## Altura del encabezado del grupo {#group-header-height}

La altura de la fila del encabezado del grupo se puede controlar de forma independiente de los encabezados de columna regulares utilizando `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Las filas del grupo tienen 32px de altura
table.setHeaderHeight(48);      // La fila del encabezado de columna permanece en 48px
```

La altura de encabezado del grupo predeterminada coincide con la altura de encabezado predeterminada.

## Estilizando grupos con partes de CSS {#styling-groups-with-css-parts}

Los encabezados de grupos y columnas exponen partes de CSS para estilización a través de `::part()`. Las siguientes partes están disponibles:

| Parte | Descripción |
| --- | --- |
| `cell-group-{ID}` | Celda del encabezado del grupo, dirigida por ID del grupo |
| `cell-group-depth-{N}` | Celda del encabezado del grupo, dirigida por profundidad (`0` = nivel superior, `1` = segundo nivel, etc.) |
| `cell-column-{ID}` | Todas las celdas (encabezado y cuerpo) para un ID de columna dado |
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

### Estilizando por ID de grupo {#styling-by-group-id}

Utiliza el ID del grupo para dirigir grupos específicos con colores o tipografías únicas.

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

### Estilizando por profundidad {#styling-by-depth}

Las partes basadas en profundidad son útiles cuando quieres aplicar un estilo consistente a todos los grupos en un cierto nivel de anidación sin dirigirte a cada grupo individualmente.

```css
/* Estilo para todos los grupos de nivel superior */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Estilo para todos los grupos de segundo nivel */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Columnas ocultas {#hidden-columns}

Las columnas ocultas son excluidas del orden visual y el diseño del encabezado. Si un grupo contiene una mezcla de columnas visibles y ocultas, solo las visibles aparecen y el `colspan` del grupo se ajusta en consecuencia. Si cada columna en un grupo está oculta, el encabezado del grupo no se renderiza en absoluto.

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
