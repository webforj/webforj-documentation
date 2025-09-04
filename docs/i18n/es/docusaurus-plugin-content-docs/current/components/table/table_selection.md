---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 91df1121dac6d410883e3b43ddf767d5
---
El componente `Table` ofrece diversas capacidades de selección. Existen métodos para seleccionar un solo elemento, múltiples elementos o gestionar selecciones de manera programática.

## Modo de selección {#selection-mode}

El modo de selección en la tabla determina cómo los elementos pueden ser seleccionados por el usuario. Proporciona opciones para configurar el comportamiento de la selección de elementos. La clase Table ofrece un método para establecer el modo de selección:

```java
setSelectionMode(SelectionMode selectionMode)
```

Las opciones de SelectionMode disponibles incluyen:

>- `SINGLE` - (selección única) 
>- `MULTI` - (selección múltiple)
>- `NONE` - (sin selección).

## Evento de selección {#selection-event}

El componente `Table` emite varios eventos relacionados con la selección de filas. Estos eventos capturan los cambios en el estado de selección de las filas de `Table`. A continuación, se describen los eventos clave de selección junto con sus descripciones:

>- `TableItemSelectEvent` - Emitido cuando un elemento de la tabla es seleccionado.
>- `TableItemDeselectEvent` - Emitido cuando un elemento de la tabla es deseleccionado.
>- `TableItemSelectionChange` - Emitido cuando la selección general en la tabla cambia, o cuando se elige una selección adicional.

:::info
Los eventos `TableItemSelectEvent` y `TableItemDeselectEvent` no se activan cuando el modo de selección múltiple está activo y la selección se realiza a través de la casilla de verificación del encabezado. En este caso, se debe utilizar `TableItemSelectionChange` en su lugar.
:::

En el ejemplo a continuación, se disparará un evento `TableItemSelectEvent` cada vez que un usuario selecciona una fila. El evento se puede manejar añadiendo un oyente a la tabla utilizando el método `onItemSelect()`.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Selección con casillas de verificación {#checkbox-selection}

La selección con casillas de verificación está habilitada cuando el modo de selección es `MULTI`, y permite a los usuarios seleccionar de manera conveniente uno o más elementos utilizando casillas de verificación asociadas a cada fila. Esta característica es particularmente útil para escenarios donde los usuarios necesitan realizar acciones en bulk sobre los elementos seleccionados. La clase Table proporciona métodos para habilitar y personalizar la selección con casillas de verificación.

Utilizando el método `setCheckboxSelection(boolean checkboxSelection)`, se pueden configurar las casillas de verificación para que se muestren junto a cada fila, permitiendo a los usuarios seleccionar elementos. El programa a continuación muestra la selección múltiple y la selección con casillas de verificación habilitadas:

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Selección programática {#programatic-selection}

El componente `Table` proporciona métodos de selección programática, permitiendo manipular elementos seleccionados ya sea por sus claves o por los propios elementos completos. 

### Seleccionar por clave {#select-by-key}

El método `selectKey(Object... keys)` te permite seleccionar programáticamente elementos utilizando sus claves. Puedes pasar una o más claves a este método, y actualizará la selección de acuerdo a ello.

### Seleccionar por índice {#select-by-index}

Utilizando el método `selectIndex(int... indices)` puedes pasar uno o más índices al método, actualizando así los elementos seleccionados en consecuencia.

### Seleccionar elementos completos {#selecting-entire-items}

Finalmente, el método `select(T... items)` te permite seleccionar programáticamente elementos pasando uno o más elementos a este método para actualizar la selección en consecuencia.
