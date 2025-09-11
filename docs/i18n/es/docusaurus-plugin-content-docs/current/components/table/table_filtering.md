---
sidebar_position: 35
title: Filtering
slug: filtering
sidebar_class_name: updated-content
_i18n_hash: 008eef50f8ab27ec3f8a455fb5649f41
---
El componente `Table` te permite implementar filtrado para reducir los datos mostrados en función de criterios específicos. El filtrado se puede lograr definiendo un criterio de filtrado utilizando el método `setFilter(Predicate<T> filter)` proporcionado por el `Repository` asociado con la tabla.

El siguiente ejemplo utiliza un criterio definido por el usuario desde el campo de búsqueda y el método `setBaseFilter()` para aplicar un filtro al `CollectionRepository` basado en los títulos de `MusicRecord`. Cuando se activa el método `commit()`, la tabla se actualiza con los datos filtrados.

:::note
El método `setBaseFilter()` pertenece a la clase `CollectionRepository`, no al componente `Table`.
:::
