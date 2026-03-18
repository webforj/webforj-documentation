---
title: Column Groups
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: fccec5a60bfd614d344758d3624bc602
---
<DocChip chip='since' label='25.12' />

Los grupos de columnas te permiten organizar columnas relacionadas bajo encabezados compartidos de varias filas. Una etiqueta de grupo se extiende a través de sus columnas hijas, lo que facilita a los usuarios escanear y comprender la estructura de tablas complejas. Los grupos se pueden anidar a cualquier profundidad, y el `Table` renderiza automáticamente el número correcto de filas de encabezado.

## Creando grupos de columnas {#creating-column-groups}

Crea un grupo con el método de fábrica `ColumnGroup.of()`, luego encadena llamadas a `add()` para poblarlo con referencias de columna, otros grupos, o una mezcla de ambos. Aplica los grupos a un `Table` con `setColumnGroups()`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Cuando los grupos están configurados, el `Table` renderiza un encabezado de varias filas donde cada etiqueta de grupo se extiende a través de sus columnas hijas. La profundidad de anidamiento determina cuántas filas de encabezado aparecen. Un grupo plano agrega una fila extra, mientras que un anidamiento de dos niveles agrega dos, y así sucesivamente.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablenestedcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableNestedColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

<!-- vale Google.OxfordComma = NO -->
Los grupos se pueden establecer o cambiar en cualquier momento, antes o después de que el `Table` sea renderizado. Pasa `null` o una lista vacía a `setColumnGroups()` para eliminar todo el agrupamiento y regresar a un encabezado de una sola fila.
<!-- vale Google.OxfordComma = YES -->

```java
// Eliminar todos los grupos de columnas
table.setColumnGroups(null);
```

## Ordenación de columnas {#column-ordering}

Cuando los grupos están activos, el orden visual de las columnas se determina por el árbol de grupos en lugar del orden en que se añadieron las columnas al `Table`. El árbol se recorre en profundidad, de izquierda a derecha.

```
Columnas añadidas:  [A, B, C, D, E]
Grupos:            Grupo "G1" [C, A], Grupo "G2" [E, D]
Orden visual:      C, A, E, D, B
```

Las columnas no agrupadas, que no están referenciadas en ningún grupo, no están ocultas. Aparecen en su posición natural respecto a las columnas agrupadas, basándose en el orden en que fueron originalmente añadidas al `Table`.

En este ejemplo, `Number` aparece primero porque se agregó antes que `Title`. `Label` aparece entre `Genre` y `Cost` porque se agregó entre ellos en el orden original de las columnas:

```
Columnas añadidas:  [Number, Title, Artist, Genre, Label, Cost]
Grupos:            Grupo "Music" [Title, Artist, Genre], Grupo "Pricing" [Cost]
Orden visual:      Number, Title, Artist, Genre, Label, Cost
```

La siguiente demostración ilustra este comportamiento. `Number` y `Label` no están referenciados en ningún grupo, pero mantienen sus posiciones naturales basadas en el orden en que fueron añadidos al `Table`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroupordering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

:::tip Controlando la ubicación de columnas no agrupadas
Para controlar explícitamente la ubicación de columnas no agrupadas, inclúyelas como referencias de columna de nivel superior en el árbol de grupos.
:::

## Movimiento de columnas dentro de grupos {#column-moving-within-groups}

Cuando los grupos están activos, el movimiento de columnas por arrastre y soltura está restringido para mantener la integridad del grupo:

- **Dentro de un grupo**: una columna dentro de un grupo solo puede moverse dentro de su grupo padre inmediato. Arrastrarla fuera del grupo es rechazado, y la columna vuelve a su posición original.
- **Columnas no agrupadas**: una columna no agrupada solo puede moverse a posiciones ocupadas por otras columnas no agrupadas. No se puede soltar en medio de un grupo.
- **Reordenar grupos**: un grupo entero puede ser arrastrado para reordenarlo entre sus hermanos en el mismo nivel de anidamiento.

```
Grupos:  Grupo "G1" [A, B, C], Grupo "G2" [D, E]

Mover A a la posición 2 -> OK (dentro de G1, resultado: [B, C, A])
Mover A a la posición 3 -> Rechazado (la posición 3 está dentro de G2)
Mover D a la posición 4 -> OK (dentro de G2, resultado: [E, D])
Mover D a la posición 1 -> Rechazado (la posición 1 está dentro de G1)
```

## Fijando grupos {#pinning-groups}

Un grupo puede ser fijado a la izquierda o a la derecha usando `setPinDirection()`. Todas las columnas dentro del grupo heredan la dirección de fijación del grupo, y la configuración de fijación individual de las columnas es anulada por el grupo.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Tanto "number" como "title" están fijados a la izquierda,
// independientemente de sus propias configuraciones de fijación
```

Las columnas no agrupadas mantienen su propia dirección de fijación de su definición de columna.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablepinnedcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

## Altura de encabezado del grupo {#group-header-height}

La altura de la fila de encabezado del grupo se puede controlar de forma independiente de los encabezados de columna regulares usando `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Las filas de grupo tienen 32px de altura
table.setHeaderHeight(48);      // La fila de encabezado de columna se mantiene en 48px
```

La altura de encabezado de grupo predeterminada coincide con la altura de encabezado predeterminada.

## Estilizando grupos con partes de CSS {#styling-groups-with-css-parts}

Los encabezados y columnas de grupo exponen partes de CSS para estilización a través de `::part()`. Las siguientes partes están disponibles:

| Parte | Descripción |
| --- | --- |
| `cell-group-{ID}` | Celda de encabezado de grupo, dirigida por ID de grupo |
| `cell-group-depth-{N}` | Celda de encabezado de grupo, dirigida por profundidad (`0` = nivel superior, `1` = segundo nivel, etc.) |
| `cell-column-{ID}` | Todas las celdas (encabezado y cuerpo) para un dado ID de columna |
| `cell-content-group-{ID}` | Contenedor de contenido dentro de un encabezado de grupo |
| `cell-label-group-{ID}` | Etiqueta dentro de un encabezado de grupo |

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablestyledcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java'
cssURL='/css/table/tablestyledcolumngroups.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

### Estilizando por ID de grupo {#styling-by-group-id}

Utiliza el ID de grupo para dirigirte a grupos específicos con colores o tipografías únicos.

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

Las partes basadas en profundidad son útiles cuando deseas aplicar un estilo consistente a todos los grupos en un cierto nivel de anidamiento sin dirigirte a cada grupo individualmente.

```css
/* Estila todos los grupos de nivel superior */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Estila todos los grupos de segundo nivel */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Columnas ocultas {#hidden-columns}

Las columnas ocultas están excluidas del orden visual y del diseño del encabezado. Si un grupo contiene una mezcla de columnas visibles y ocultas, solo las visibles aparecen y el `colspan` del grupo se ajusta en consecuencia. Si todas las columnas de un grupo están ocultas, el encabezado del grupo no se renderiza en absoluto.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablehiddencolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->
