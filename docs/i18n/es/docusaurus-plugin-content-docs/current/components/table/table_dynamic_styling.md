---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 8f910c729d1108faeaba860a2e0f3546
---
<!-- vale off -->
# Estilo Dinámico <DocChip chip='since' label='25.00' />
<!-- vale on -->

En webforJ 25 y versiones superiores, es posible estilizar filas y celdas individuales en la Tabla utilizando nombres de partes personalizadas. Estos nombres se pueden asignar dinámicamente según la lógica de tu aplicación, brindándote un control granular sobre la apariencia de la tabla.

## Estilización de filas {#row-styling}

El método `setRowPartProvider()` asigna nombres de partes a filas completas según el elemento de datos que contienen. Esto te permite resaltar filas completas que cumplen condiciones específicas; por ejemplo, colores de fondo alternos para filas pares.

Estos nombres de estilo se pueden dirigir utilizando el selector `::part()` en tu CSS.

:::tip Partes de sombra
El selector `::part()` es una característica especial de CSS que te permite estilizar elementos dentro del shadow DOM de un componente, siempre que esos elementos expongan un atributo `part`. Esto es especialmente útil para estilizar partes internas de los componentes de webforJ, como filas o celdas en una tabla.

Para más información sobre cómo funcionan las partes de sombra y cómo definirlas y dirigirte a ellas, consulta la sección [Estilo](../../styling/shadow-parts).
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/resources/static/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Estilización de celdas {#cell-styling}

El método `setCellPartProvider()` estiliza celdas individuales según el elemento de datos y la columna a la que pertenecen. Esto lo hace ideal para resaltar valores específicos, como señalar edades que superan un umbral o entradas inválidas.

Al igual que las partes de fila, las partes de celda se definen por un nombre y se dirigen utilizando el selector `::part()`.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/resources/static/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Reacción a actualizaciones de datos {#reacting-to-data-updates}

Si tu aplicación modifica datos programáticamente, como actualizar la edad de un usuario, la tabla reevaluará automáticamente y reaplicará cualquier estilo de fila o celda asociado una vez que el elemento actualizado se haya confirmado en el repositorio.

En esta demostración, las celdas en la columna de Edad están estilizadas según un umbral: las edades superiores a 30 aparecen en verde, mientras que las edades de 30 y menores aparecen en rojo. Al hacer clic en el botón, se alterna la edad de Alice entre 28 y 31, lo que desencadena el `setCellPartProvider` para reaplicar el estilo apropiado cuando se confirman los datos.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/resources/static/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Filas a rayas {#striped-rows}

Habilita colores de fondo alternos para las filas para mejorar la legibilidad:

```java
// Aplicar estilo de filas a rayas
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

La demostración a continuación muestra una forma sencilla de alinear la apariencia visual de tu `Tabla` con el resto de tu aplicación utilizando `setStriped()` y `setBordersVisible()`.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip Gestión y consulta de datos
Para información sobre cómo usar el patrón `Repository` para gestionar y consultar colecciones, consulta los [artículos de Repositorio](/docs/advanced/repository/overview).
:::
