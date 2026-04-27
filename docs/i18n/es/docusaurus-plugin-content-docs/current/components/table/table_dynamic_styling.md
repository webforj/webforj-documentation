---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 03c0fb81b4bcabef5db6dfb9785eef3d
---
<!-- vale off -->
# Estilo Dinámico <DocChip chip='since' label='25.00' />
<!-- vale on -->

En webforJ 25 y superior, es posible estilizar filas y celdas individuales en la Tabla utilizando nombres de parte personalizados. Estos nombres pueden asignarse dinámicamente según la lógica de tu aplicación, dándote un control detallado sobre la apariencia de la tabla.

## Estilo de fila {#row-styling}

El método `setRowPartProvider()` asigna nombres de parte a filas enteras según el elemento de datos que contienen. Esto te permite resaltar filas completas que cumplen con condiciones específicas, por ejemplo, colores de fondo alternantes para filas pares.

Estos nombres de estilo se pueden dirigir utilizando el selector `::part()` en tu CSS.

:::tip Partes de sombra
El selector `::part()` es una característica especial de CSS que te permite estilizar elementos dentro del shadow DOM de un componente, siempre y cuando esos elementos expongan un atributo `part`. Esto es especialmente útil para estilizar partes internas de los componentes webforJ, como filas o celdas en una tabla.

Para más información sobre cómo funcionan las partes de sombra y cómo definirlas y dirigirlas, consulta la sección [Estilo](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
cssURL='/css/table/table-row-styling-view.css'
height='300px'
/>

## Estilo de celda {#cell-styling}

El método `setCellPartProvider()` estiliza celdas individuales en función tanto del elemento de datos como de la columna a la que pertenecen. Esto lo hace ideal para resaltar valores específicos, como señalar edades que superan un umbral o entradas inválidas.

Al igual que las partes de fila, las partes de celda se definen por un nombre y se dirigen utilizando el selector `::part()`.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
cssURL='/css/table/table-cell-styling-view.css'
height='300px'
/>

## Reacción a actualizaciones de datos {#reacting-to-data-updates}

Si tu aplicación modifica datos de forma programática, como actualizar la edad de un usuario, la tabla volverá a evaluar y reaplicará automáticamente cualquier estilo asociado a filas o celdas una vez que el elemento actualizado esté comprometido en el repositorio.

En esta demostración, las celdas en la columna de Edad están estilizadas según un umbral: las edades superiores a 30 aparecen en verde, mientras que las edades de 30 o menos aparecen en rojo. Hacer clic en el botón alterna la edad de Alice entre 28 y 31, lo que activa el `setCellPartProvider` para reaplicar el estilo apropiado cuando los datos se comprometen.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
cssURL='/css/table/table-dynamic-styling-view.css'
height='475px'
/>

## Filas rayadas {#striped-rows}

Habilita colores de fondo alternantes para las filas para mejorar la legibilidad:

```java
// Aplicar estilo de fila rayada
table.setStriped(true);
```

## Bordes {#borders}

Configura qué bordes se muestran alrededor de la `Tabla`, columnas y filas:

```java
// Habilitar todos los bordes
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Eliminar todos los bordes
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

La demostración a continuación muestra una forma simple de alinear la apariencia visual de tu `Tabla` con el resto de tu aplicación utilizando `setStriped()` y `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Gestión y consulta de datos
Para información sobre cómo utilizar el patrón `Repository` para gestionar y consultar colecciones, consulta los [artículos de Repository](/docs/advanced/repository/overview).
:::
