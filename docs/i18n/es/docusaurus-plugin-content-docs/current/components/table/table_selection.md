---
sidebar_position: 10
title: Selection
slug: selection
description: >-
  Configure single, multi, or no-selection modes on the Table and respond to row
  selection events with appropriate listeners.
_i18n_hash: 3dc9f9e7462f97e260e1112a2966dc18
---
El componente `Table` proporciona diversas capacidades de selección. Hay métodos para seleccionar un solo elemento, múltiples elementos o gestionar las selecciones programáticamente.

:::tip Gestión y consulta de datos
Para obtener información sobre cómo utilizar el patrón `Repository` para gestionar y consultar colecciones, consulta los [artículos de Repository](/docs/advanced/repository/overview).
:::

## Modo de selección {#selection-mode}

El modo de selección en la tabla determina cómo los elementos pueden ser seleccionados por el usuario. Proporciona opciones para configurar el comportamiento de la selección de elementos. La clase Table proporciona un método para establecer el modo de selección:

```java
setSelectionMode(SelectionMode selectionMode)
```

Las opciones de SelectionMode disponibles incluyen:

>- `SINGLE` - (selección única)
>- `MULTI` - (selección múltiple)
>- `NONE` - (sin selección).

## Evento de selección {#selection-event}

El paquete del componente `Table` emite varios eventos relacionados con la selección de filas. Estos eventos capturan cambios en el estado de selección de las filas de `Table`. A continuación se presentan los eventos de selección clave junto con sus descripciones:

>- `TableItemSelectEvent` - Emitido cuando se selecciona un elemento de la tabla.
>- `TableItemDeselectEvent` - Emitido cuando se deselecciona un elemento de la tabla.
>- `TableItemSelectionChange` - Emitido cuando la selección general en la tabla cambia, o cuando se elige una selección adicional.

:::info
El `TableItemSelectEvent` y `TableItemDeselectEvent` no se activan cuando el modo de selección múltiple está activo y la selección se realiza a través de la casilla de verificación del encabezado. En este caso, se debe utilizar el `TableItemSelectionChange` en su lugar.
:::

En el siguiente ejemplo, un evento `TableItemSelectEvent` se activará cada vez que un usuario seleccione una fila. El evento se puede manejar añadiendo un listener a la tabla utilizando el método `onItemSelect()`.

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

La selección por casilla de verificación se habilita cuando el modo de selección es `MULTI`, y permite a los usuarios seleccionar de manera conveniente uno o más elementos utilizando casillas de verificación asociadas a cada fila. Esta función es particularmente útil para escenarios donde los usuarios necesitan realizar acciones en lote sobre los elementos seleccionados. La clase Table proporciona métodos para habilitar y personalizar la selección por casilla de verificación.

Usando el método `setCheckboxSelection(boolean checkboxSelection)`, se pueden configurar casillas de verificación para que se muestren junto a cada fila, permitiendo a los usuarios seleccionar elementos. El programa a continuación muestra la selección múltiple y la selección por casilla de verificación habilitadas:

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Selección programática {#programatic-selection}

El componente `Table` proporciona métodos de selección programática, permitiéndote manipular elementos seleccionados ya sea por sus claves o por los elementos completos.

### Seleccionar por clave {#select-by-key}

El método `selectKey(Object... keys)` te permite seleccionar programáticamente elementos utilizando sus claves. Puedes pasar una o más claves a este método, y actualizará la selección en consecuencia.

### Selección de elementos de entrada {#selecting-entry-items}

Finalmente, el método `select(T... items)` te permite seleccionar programáticamente elementos pasando uno o más elementos en sí a este método para actualizar la selección en consecuencia.
