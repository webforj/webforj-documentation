---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: e35c9b340f9faa796a4dbf5635f59495
---
El componente `Table` te permite implementar filtrado para reducir los datos mostrados en función de criterios específicos. El filtrado se puede lograr definiendo un criterio de filtrado utilizando el método `setFilter(Predicate<T> filter)` proporcionado por el `Repository` asociado con la tabla.

El siguiente ejemplo utiliza un criterio definido por el usuario desde el campo de búsqueda y el método `setBaseFilter()` para aplicar un filtro al `CollectionRepository` basado en los títulos de `MusicRecord`. Cuando se activa el método `commit()`, la tabla se actualiza con los datos filtrados.

:::note
El método `setBaseFilter()` pertenece a la clase `CollectionRepository`, no al componente `Table`.
:::
