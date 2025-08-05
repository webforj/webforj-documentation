---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
_i18n_hash: c958a549dfbac715dfce9f26d729f106
---
<!-- vale off -->
# Estilo dinámico <DocChip chip='since' label='25.00' />
<!-- vale on -->

En webforJ 25 y versiones superiores, es posible estilizar filas y celdas individuales en la tabla utilizando nombres de partes personalizados. Estos nombres pueden asignarse dinámicamente según la lógica de tu aplicación, dándote un control detallado sobre la apariencia de la tabla.

## Estilo de fila {#row-styling}

El método `setRowPartProvider()` asigna nombres de partes a filas completas según el elemento de datos que contienen. Esto te permite resaltar filas completas que cumplen condiciones específicas; por ejemplo, colores de fondo alternos para filas pares.

Estos nombres de estilo se pueden dirigir utilizando el selector `::part()` en tu CSS.

:::tip Partes de sombra
El selector `::part()` es una característica especial de CSS que te permite estilizar elementos dentro del shadow DOM de un componente, siempre que esos elementos expongan un atributo `part`. Esto es especialmente útil para estilizar partes internas de los componentes de webforJ, como filas o celdas en una tabla.

Para obtener más información sobre cómo funcionan las partes de sombra y cómo definirlas y dirigirlas, consulta la sección [Estilizando](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Estilo de celda {#cell-styling}

El método `setCellPartProvider()` estiliza celdas individuales basándose tanto en el elemento de datos como en la columna a la que pertenecen. Esto lo hace ideal para resaltar valores específicos, como señalar edades por debajo de un umbral o entradas inválidas.

Al igual que las partes de fila, las partes de celda se definen por un nombre y se dirigen utilizando el selector `::part()`.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Reaccionando a actualizaciones de datos {#reacting-to-data-updates}

Si tu aplicación modifica datos programáticamente, como actualizar la edad de un usuario, la tabla evaluará automáticamente y reaplicará cualquier estilo de fila o celda asociado una vez que el elemento actualizado se haya comprometido en el repositorio.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Filas con rayas {#striped-rows}

Habilita colores de fondo alternos para las filas para mejorar la legibilidad:

```java
// Aplicar estilo de fila con rayas
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

La demostración a continuación muestra una manera sencilla de alinear la apariencia visual de tu `Tabla` con el resto de tu aplicación utilizando `setStriped()` y `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>
