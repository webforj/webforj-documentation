---
sidebar_position: 10
title: Selection
slug: selection
description: >-
  Configure single, multi, or no-selection modes on the Table and respond to row
  selection events with appropriate listeners.
_i18n_hash: 47f6b24c0a0621d810e5e2975a0b4749
---
El componente `Table` ofrece diversas capacidades de selección. Hay métodos para seleccionar un solo elemento, múltiples elementos o gestionar selecciones de manera programática.

:::tip Gestión y consulta de datos
Para obtener información sobre cómo utilizar el patrón `Repository` para gestionar y consultar colecciones, consulte los [artículos del repositorio](/docs/advanced/repository/overview).
:::

## Modo de selección {#selection-mode}

El modo de selección en la tabla determina cómo los elementos pueden ser seleccionados por el usuario. Proporciona opciones para configurar el comportamiento de la selección de elementos. La clase Table proporciona un método para establecer el modo de selección:

```java
setSelectionMode(SelectionMode selectionMode)
```

Las opciones disponibles de SelectionMode incluyen:

>- `SINGLE` - (selección única)
>- `MULTI` - (selección múltiple)
>- `NONE` - (sin selección).

## Evento de selección {#selection-event}

El paquete del componente `Table` emite varios eventos relacionados con la selección de filas. Estos eventos capturan cambios en el estado de selección de las filas de `Table`. A continuación se presentan los principales eventos de selección junto con sus descripciones:

>- `TableItemSelectEvent` - Emitido cuando se selecciona un ítem de la tabla.
>- `TableItemDeselectEvent` - Emitido cuando se deselecciona un ítem de la tabla.
>- `TableItemSelectionChange` - Emitido cuando la selección total en la tabla cambia, o cuando se elige una selección adicional.

:::info
Los eventos `TableItemSelectEvent` y `TableItemDeselectEvent` no se activan cuando está activo el modo de selección múltiple y la selección se realiza a través de la casilla de verificación del encabezado. En este caso, se debe utilizar `TableItemSelectionChange` en su lugar.
:::

En el ejemplo a continuación, se disparará un evento `TableItemSelectEvent` cada vez que un usuario seleccione una fila. El evento puede ser manejado agregando un oyente a la tabla utilizando el método `onItemSelect()`.

<ComponentDemo
path='/webforj/tablesingleselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Selección por casilla de verificación {#checkbox-selection}

La selección por casilla de verificación se activa cuando el modo de selección es `MULTI`, y permite a los usuarios seleccionar de manera conveniente uno o más elementos utilizando las casillas de verificación asociadas con cada fila. Esta función es particularmente útil en escenarios donde los usuarios necesitan realizar acciones masivas sobre elementos seleccionados. La clase Table proporciona métodos para habilitar y personalizar la selección con casillas de verificación.

Al utilizar el método `setCheckboxSelection(boolean checkboxSelection)`, se pueden configurar las casillas de verificación para mostrarse junto a cada fila, lo que permite a los usuarios seleccionar elementos. El programa a continuación muestra habilitada la selección múltiple y la selección con casillas de verificación:

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Selección programática {#programmatic-selection}

El componente `Table` proporciona métodos de selección programática, lo que le permite manipular los elementos seleccionados ya sea por sus claves o por los elementos completos.

### Selección por clave {#select-by-key}

El método `selectKey(Object... keys)` le permite seleccionar programáticamente elementos utilizando sus claves. Puede pasar una o más claves a este método, y actualizará la selección en consecuencia.

### Selección de elementos de entrada {#selecting-entry-items}

Finalmente, el método `select(T... items)` le permite seleccionar programáticamente elementos pasando uno o más elementos en sí a este método para actualizar la selección en consecuencia.
