---
sidebar_position: 35
title: Filtrado
slug: filtering
description: >-
  Filtra las filas de la tabla aplicando un Predicate mediante el Repository
  vinculado y actualiza los resultados al confirmar los cambios.
_i18n_title_hash: 9a588db8471730dbfebac65cd5467ad8
_i18n_description_hash: 16cf6eedec1585c3016bf61678214883
_i18n_hash: 4e0709a55b763f553eeb8ddb8a3abb32
---
El componente `Table` te permite implementar filtrado para reducir los datos mostrados en función de criterios específicos. El filtrado se puede lograr definiendo un criterio de filtrado utilizando el método `setFilter(Predicate<T> filter)` proporcionado por el `Repository` asociado con la tabla.

El siguiente ejemplo utiliza un criterio definido por el usuario desde el campo de búsqueda y el método `setBaseFilter()` para aplicar un filtro al `CollectionRepository` basado en los títulos de `MusicRecord`. Cuando se activa el método `commit()`, la tabla se actualiza con los datos filtrados.

:::note
El método `setBaseFilter()` pertenece a la clase `CollectionRepository`, no al componente `Table`.
:::
