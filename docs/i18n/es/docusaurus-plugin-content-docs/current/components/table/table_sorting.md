---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 94345d1313939015f02cf46ecaa5af99
---
Ordenar permite a los usuarios organizar datos en columnas por orden, haciendo que la información sea más fácil de leer y analizar. Esto es útil cuando los usuarios necesitan encontrar rápidamente los valores más altos o más bajos en una columna en particular.

:::tip Gestión y consulta de datos
Para obtener información sobre cómo utilizar el patrón `Repository` para gestionar y consultar colecciones, consulte los [artículos del Repositorio](/docs/advanced/repository/overview).
:::

<ComponentDemo
path='/webforj/tablesorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Por defecto, una columna no es ordenable a menos que se active explícitamente. Para permitir el ordenamiento en una columna específica, use el método `setSortable(true)`:

```java
table.getColumn("Age").setSortable(true);
```

## Ordenamiento múltiple {#multi-sorting}

:::warning Ordenamiento de múltiples columnas desactivado por defecto en webforJ `25.00`
Antes de webforj `25.00`, las tablas soportaban el ordenamiento de múltiples columnas por defecto. A partir de la versión `25.00`, este comportamiento cambió: los desarrolladores ahora deben habilitar explícitamente el ordenamiento de múltiples columnas.
:::

Si se necesita el ordenamiento múltiple, se debe aplicar `setMultiSorting(true)` a la tabla. Esto permite a los usuarios ordenar múltiples columnas en secuencia:

```java
table.setMultiSorting(true);
```

Con el ordenamiento múltiple habilitado, hacer clic en múltiples encabezados de columnas los ordenará secuencialmente. La prioridad de ordenamiento se indica visualmente en la interfaz de usuario de la tabla.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

También puede definir la prioridad de ordenamiento programáticamente para el ordenamiento del lado del servidor. Use `setSortIndex()` en las columnas que desea ordenar, en orden de prioridad:

```java
// Orden de clasificación del lado del servidor
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info El orden de las columnas es importante
A menos que se use `setSortIndex()`, la tabla por defecto se ordena según el orden en el que se declaran las columnas.
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Dirección de ordenamiento {#sort-direction}

Hay tres configuraciones disponibles para la dirección en la que se puede ordenar una columna:

- `SortDirection.ASC`: Ordena la columna en orden ascendente.
- `SortDirection.DESC`: Ordena la columna en orden descendente.
- `SortDirection.NONE`: No se aplica ningún ordenamiento a la columna.

Cuando una columna tiene el ordenamiento habilitado, verá un conjunto de indicadores de flecha vertical aparecer en la parte superior de la columna establecida. Estas flechas permiten al usuario alternar entre las diferentes direcciones de ordenamiento.

Cuando se selecciona el orden ascendente, se mostrará un `^`, mientras que el orden descendente mostrará un `v`.


## Ordenamiento del cliente vs. ordenamiento del lado del servidor {#client-vs-server-side-sorting}

El ordenamiento de datos se puede clasificar en dos enfoques principales: **Ordenamiento del Cliente** y **Ordenamiento del Servidor**.

### Ordenamiento del cliente {#client-sorting}

El ordenamiento del cliente implica organizar y presentar datos directamente dentro de la interfaz de usuario de la aplicación cliente. Es el ordenamiento con el que interactúan los usuarios cuando hacen clic en los encabezados de las columnas, influyendo en la representación visual de los datos en la pantalla.

El desarrollador no tiene control directo sobre el ordenamiento del lado del cliente, sino que se determina por el tipo de columna proporcionada en Java. Los siguientes tipos son los que se soportan actualmente:

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
El ordenamiento del cliente no funciona cuando solo una parte de los datos está disponible en el cliente.
:::

### Ordenamiento del servidor {#server-sorting}

En contraste con el ordenamiento del lado del cliente, el ordenamiento del servidor implica organizar y preparar los datos en el servidor antes de enviarlos al cliente. Este enfoque es especialmente beneficioso cuando se manejan grandes conjuntos de datos que podrían ser poco prácticos de transferir completamente al cliente.

Los desarrolladores tienen más control sobre la lógica de ordenamiento del servidor. Esto permite implementar algoritmos de ordenamiento complejos y optimizaciones, haciéndolo adecuado para escenarios con datos extensos. Esto asegura que el cliente reciba datos ordenados previamente, minimizando la necesidad de un procesamiento extenso del lado del cliente.


:::info
El ordenamiento del servidor es una estrategia orientada al rendimiento para manejar conjuntos de datos que superan las capacidades del procesamiento eficiente del cliente y es el método por defecto utilizado por la `Table`.
:::

### Nombre de la propiedad de la columna {#column-property-name}

Por defecto, la `Table` utiliza el ID de una columna como el nombre de la propiedad al construir criterios de ordenamiento para un repositorio backend. Cuando el ID de visualización de una columna no coincide con la propiedad de datos subyacente, o cuando la columna muestra un valor calculado, use `setPropertyName()` para indicar explícitamente a la `Table` qué propiedad ordenar.

```java
// El ID de la columna es "Full Name", pero la propiedad del backend es "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

El nombre de la propiedad se reenvía a los `OrderCriteria` cuando se activa un evento de ordenamiento, permitiendo que repositorios backend como Spring Data JPA o adaptadores REST construyan la cláusula `ORDER BY` correcta.

:::warning
Sin `setPropertyName()`, la `Table` recurre al ID de columna. Si esto no coincide con una propiedad backend válida, el ordenamiento fallará silenciosamente o devolverá datos ordenados incorrectamente.
:::

Las rutas de propiedades anidadas también son compatibles utilizando la notación de punto:

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparadores {#comparators}

El componente `Column` permite a los desarrolladores utilizar `Comparators` de Java para ordenamientos dinámicos y personalizados. Un `Comparator` es un mecanismo utilizado para ordenar dos objetos de la misma clase, incluso si esa clase es definida por el usuario. Esta funcionalidad proporciona a los desarrolladores la flexibilidad para personalizar cómo se ordenan los datos, brindando un mayor control sobre el comportamiento de ordenamiento predeterminado basado en el orden natural.

Para aprovechar el ordenamiento con `Comparator` en una `Column`, puede usar el método `setComparator()`. Este método permite definir una función `Comparator` personalizada que dicta la lógica de ordenamiento.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

En el ejemplo anterior, se especifica una función de comparador personalizada que toma dos elementos (a y b), y define el orden de ordenamiento basado en los valores enteros analizados del atributo `Number`.

Usar Comparators para el ordenamiento de columnas es particularmente útil al manejar valores no numéricos. También son útiles para implementar algoritmos de ordenamiento complejos.

:::info
Por defecto, la `Table` utiliza el ordenamiento del lado del servidor y ordena los valores no primitivos usando el método `toString()` de Object, convirtiéndolos en sus valores de cadena y luego ordenándolos.
:::
