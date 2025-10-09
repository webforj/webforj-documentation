---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 8e9f61685fbb3a7fb830463f1320e8cf
---
<!-- vale off -->
# Estilización Dinámica <DocChip chip='since' label='25.00' />
<!-- vale on -->

En webforJ 25 y superiores, es posible estilizar filas y celdas individuales en la Tabla utilizando nombres de partes personalizados. Estos nombres pueden asignarse dinámicamente según la lógica de tu aplicación, dándote un control detallado sobre la apariencia de la tabla.

## Estilización de filas {#row-styling}

El método `setRowPartProvider()` asigna nombres de partes a filas enteras según el ítem de datos que contienen. Esto te permite resaltar filas completas que cumplen con condiciones específicas, por ejemplo, colores de fondo alternos para filas pares.

Estos nombres de estilo pueden ser dirigidos utilizando el selector `::part()` en tu CSS.

:::tip Partes de sombra
El selector `::part()` es una característica especial de CSS que te permite estilizar elementos dentro del shadow DOM de un componente, siempre que esos elementos expongan un atributo `part`. Esto es especialmente útil para estilizar partes internas de componentes webforJ, como filas o celdas en una tabla.

Para más información sobre cómo funcionan las partes de sombra y cómo definirlas y dirigirse a ellas, consulta la sección de [Estilos](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Estilización de celdas {#cell-styling}

El método `setCellPartProvider()` estiliza celdas individuales según tanto el ítem de datos como la columna a la que pertenecen. Esto lo hace ideal para resaltar valores específicos, como resaltar edades que están por debajo de un umbral o entradas inválidas.

Al igual que las partes de fila, las partes de celda se definen por un nombre y se dirigen utilizando el selector `::part()`.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reacción a actualizaciones de datos {#reacting-to-data-updates}

Si tu aplicación modifica los datos programáticamente, como actualizar la edad de un usuario, la tabla volverá a evaluar automáticamente y reaplicará cualquier estilo asociado de fila o celda una vez que el ítem actualizado se haya confirmado en el repositorio.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Filas a rayas {#striped-rows}

Habilita colores de fondo alternos para las filas para mejorar la legibilidad:

```java
// Aplicar estilización de filas a rayas
table.setStriped(true);
```

## Bordes {#borders}

Configura qué bordes se muestran alrededor de la `Tabla`, columnas y filas:

```java
// Habilitar todos los bordes
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Quitar todos los bordes
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

La demostración a continuación muestra una manera sencilla de alinear la apariencia visual de tu `Tabla` con el resto de tu aplicación utilizando `setStriped()` y `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Gestión y consulta de datos
Para información sobre cómo usar el patrón `Repository` para gestionar y consultar colecciones, consulta los [artículos de Repository](/docs/advanced/repository/overview).
:::
