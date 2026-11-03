---
sidebar_position: 21
title: Estilos dinámicos
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 6d82241fb67ffa1ce5a1e8c974a96dea
---
<!-- vale off -->
# Estilos dinámicos <DocChip chip='since' label='25.00' />
<!-- vale on -->

En webforJ 25 y versiones posteriores, es posible aplicar estilos a filas y celdas individuales de `Table` mediante nombres de partes personalizados. Estos nombres se pueden asignar dinámicamente según la lógica de tu aplicación, lo que te ofrece un control detallado sobre la apariencia de la tabla.

## Estilos de filas {#row-styling}

El método `setRowPartProvider()` asigna nombres de partes a filas enteras según el elemento de datos que contienen. Esto te permite resaltar filas completas que cumplan condiciones específicas, por ejemplo, alternar los colores de fondo de las filas pares.

Puedes seleccionar estos nombres de estilo con el selector `::part()` en tu CSS.

:::tip Partes de sombra
El selector `::part()` es una característica especial de CSS que permite aplicar estilos a los elementos dentro del shadow DOM de un componente, siempre que esos elementos expongan un atributo `part`. Esto resulta especialmente útil para aplicar estilos a partes internas de los componentes de webforJ, como las filas o celdas de una tabla.

Para obtener más información sobre el funcionamiento de las partes de sombra y cómo definirlas y seleccionarlas, consulta la sección [Estilos](../../styling/shadow-parts).
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Estilos de celdas {#cell-styling}

El método `setCellPartProvider()` aplica estilos a celdas individuales según el elemento de datos y la columna a la que pertenecen. Es ideal para resaltar valores específicos, como señalar edades que superan un umbral o entradas no válidas.

Al igual que las partes de fila, las partes de celda se definen mediante un nombre y se seleccionan con el selector `::part()`.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableCellStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Responder a las actualizaciones de datos {#reacting-to-data-updates}

Si tu aplicación modifica datos mediante programación, por ejemplo, al actualizar la edad de un usuario, la tabla volverá a evaluar y aplicar automáticamente los estilos de fila o celda asociados una vez que el elemento actualizado se confirme en el repositorio.

En esta demostración, las celdas de la columna Age se estilizan según un umbral: las edades mayores de 30 se muestran en verde, mientras que las edades de 30 o menos se muestran en rojo. Al hacer clic en el botón, la edad de Alice alterna entre 28 y 31, lo que activa `setCellPartProvider` para volver a aplicar el estilo correspondiente cuando se confirman los datos.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Filas alternas {#striped-rows}

Activa colores de fondo alternos para las filas y mejora la legibilidad:

```java
// Apply striped row styling
table.setStriped(true);
```

## Bordes {#borders}

Configura qué bordes se muestran alrededor de `Table`, las columnas y las filas:

```java
// Enable all borders
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Remove all borders
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

La demostración siguiente muestra una forma sencilla de adaptar la apariencia visual de `Table` al resto de tu aplicación mediante `setStriped()` y `setBordersVisible()`.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
]}
height='300px'
/>

:::tip Administración y consulta de datos
Para obtener información sobre cómo usar el patrón `Repository` para administrar y consultar colecciones, consulta los [artículos sobre Repository](/docs/advanced/repository/overview).
:::
