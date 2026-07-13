---
sidebar_position: 21
title: Dynamic Styling
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 28911597e1e885b0531bb27aa3c8e1a7
---
<!-- vale off -->
# Estilización Dinámica <DocChip chip='since' label='25.00' />
<!-- vale on -->

En webforJ 25 y versiones superiores, es posible estilizar filas y celdas individuales en la Tabla utilizando nombres de partes personalizados. Estos nombres se pueden asignar dinámicamente según la lógica de tu aplicación, dándote un control detallado sobre la apariencia de la tabla.

## Estilización de Filas {#row-styling}

El método `setRowPartProvider()` asigna nombres de partes a filas completas según el elemento de datos que contienen. Esto te permite resaltar filas completas que cumplen condiciones específicas; por ejemplo, colores de fondo alternos para filas pares.

Estos nombres de estilo se pueden dirigir utilizando el selector `::part()` en tu CSS.

:::tip Partes de sombra
El selector `::part()` es una característica especial de CSS que te permite estilizar elementos dentro del shadow DOM de un componente, siempre que esos elementos expongan un atributo `part`. Esto es especialmente útil para estilizar partes internas de componentes de webforJ, como filas o celdas en una tabla.

Para más información sobre cómo funcionan las partes de sombra y cómo definirlas y dirigirlas, consulta la sección de [Estilización](../../styling/shadow-parts).
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Estilización de Celdas {#cell-styling}

El método `setCellPartProvider()` estiliza celdas individuales según el elemento de datos y la columna a la que pertenecen. Esto lo hace ideal para resaltar valores específicos, como señalar edades que superan un umbral o entradas no válidas.

Al igual que las partes de fila, las partes de celda se definen por un nombre y se dirigen utilizando el selector `::part()`.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Reacción a actualizaciones de datos {#reacting-to-data-updates}

Si tu aplicación modifica datos programáticamente, como actualizar la edad de un usuario, la tabla reevaluará y reaplicará automáticamente cualquier estilo de fila o celda asociado una vez que el elemento actualizado se haya comprometido en el repositorio.

En esta demostración, las celdas en la columna Edad están estilizadas en función de un umbral: las edades superiores a 30 aparecen en verde, mientras que las edades de 30 o menos aparecen en rojo. Al hacer clic en el botón, la edad de Alice alterna entre 28 y 31, lo que activa `setCellPartProvider` para reaplicar el estilo apropiado cuando se compromete la información.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Filas a Rayas {#striped-rows}

Habilita colores de fondo alternos para filas para mejorar la legibilidad:

```java
// Aplicar estilo de fila a rayas
table.setStriped(true);
```

## Bordes {#borders}

Configura qué bordes se muestran alrededor de la `Table`, columnas y filas:

```java
// Habilitar todos los bordes
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Eliminar todos los bordes
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

La demostración a continuación muestra una manera simple de alinear la apariencia visual de tu `Table` con el resto de tu aplicación utilizando `setStriped()` y `setBordersVisible()`.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip Gestión y consulta de datos
Para información sobre cómo utilizar el patrón `Repository` para gestionar y consultar colecciones, consulta los [artículos de Repositorio](/docs/advanced/repository/overview).
:::
