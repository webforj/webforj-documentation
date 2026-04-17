---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 35cb02c29edafbe9a0715b4630be56c1
---
El componente `Table` proporciona diversas capacidades de selección. Hay métodos para seleccionar un solo ítem, múltiples ítems o gestionar selecciones de manera programática.

:::tip Gestión y consulta de datos
Para obtener información sobre cómo utilizar el patrón `Repository` para gestionar y consultar colecciones, consulte los [artículos del Repositorio](/docs/advanced/repository/overview).
:::

## Modo de selección {#selection-mode}

El modo de selección en la tabla determina cómo los ítems pueden ser seleccionados por el usuario. Proporciona opciones para configurar el comportamiento de la selección de ítems. La clase Table proporciona un método para establecer el modo de selección:

```java
setSelectionMode(SelectionMode selectionMode)
```

Las opciones de SelectionMode disponibles incluyen:

>- `SINGLE` - (selección única) 
>- `MULTI` - (selección múltiple)
>- `NONE` - (sin selección).

## Evento de selección {#selection-event}

El paquete del componente `Table` emite varios eventos relacionados con la selección de filas. Estos eventos capturan cambios en el estado de selección de las filas de `Table`. A continuación se presentan los principales eventos de selección junto con sus descripciones:

>- `TableItemSelectEvent` - Emitido cuando un ítem de la tabla es seleccionado.
>- `TableItemDeselectEvent` - Emitido cuando un ítem de la tabla es deseleccionado.
>- `TableItemSelectionChange` - Emitido cuando la selección general en la tabla cambia, o cuando se elige una selección adicional.

:::info
Los eventos `TableItemSelectEvent` y `TableItemDeselectEvent` no se activan cuando el modo de selección múltiple está activo y la selección se realiza a través de la casilla de verificación del encabezado. En este caso, se debe utilizar `TableItemSelectionChange`.
:::

En el ejemplo a continuación, se disparará un evento `TableItemSelectEvent` cada vez que un usuario seleccione una fila. El evento puede ser manejado añadiendo un listener a la tabla utilizando el método `onItemSelect()`.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Selección con casillas de verificación {#checkbox-selection}

La selección con casillas de verificación se habilita cuando el modo de selección es `MULTI`, y permite a los usuarios seleccionar de manera conveniente uno o más ítems utilizando las casillas de verificación asociadas con cada fila. Esta función es particularmente útil para escenarios donde los usuarios necesitan realizar acciones en bloque sobre ítems seleccionados. La clase Table proporciona métodos para habilitar y personalizar la selección con casillas de verificación.

Al usar el método `setCheckboxSelection(boolean checkboxSelection)`, se pueden configurar las casillas de verificación para mostrarse junto a cada fila, permitiendo a los usuarios seleccionar ítems. El siguiente programa muestra la selección múltiple y la selección con casillas de verificación habilitadas:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Selección programática {#programatic-selection}

El componente `Table` proporciona métodos de selección programática, permitiéndole manipular los ítems seleccionados ya sea por sus claves o por los ítems completos. 

### Seleccionar por clave {#select-by-key}

El método `selectKey(Object... keys)` le permite seleccionar ítems programáticamente utilizando sus claves. Puede pasar una o más claves a este método, y actualizará la selección en consecuencia.

### Seleccionando ítems de entrada {#selecting-entry-items}

Finalmente, el método `select(T... items)` le permite seleccionar ítems programáticamente pasando uno o más ítems a este método para actualizar la selección en consecuencia.
