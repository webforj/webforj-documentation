---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: 110061605b615701c1832988833fe959
---
El componente `Table` te permite implementar la funcionalidad de filtrado para reducir los datos mostrados según criterios específicos. El filtrado se puede lograr definiendo un criterio de filtrado utilizando el método `setFilter(Predicate<T> filter)` proporcionado por el `Repository` asociado con la tabla.

<ComponentDemo
path='/webforj/tablefiltering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableFilteringView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

En el ejemplo anterior, se utiliza el método `setFilter()` para definir un criterio de filtrado basado en el título de `MusicRecord`. 

:::tip
El método `setFilter()` pertenece a la clase `Repository`, y no es un comportamiento integrado de la `Table` en sí misma.
:::

El filtro se aplica cuando el usuario modifica el contenido del campo de búsqueda, actualizando el searchTerm y activando el método `commit()` para refrescar los datos mostrados.
