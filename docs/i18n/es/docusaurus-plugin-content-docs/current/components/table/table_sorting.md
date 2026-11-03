---
sidebar_position: 15
title: Ordenación
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 7a910f5bdcaa8a9e053bcda6706808c2
---
La ordenación permite que los usuarios organicen los datos por columnas, lo que facilita su lectura y análisis. Esto resulta útil cuando necesitan encontrar rápidamente los valores más altos o más bajos de una columna determinada.

:::tip Gestión y consulta de datos
Para obtener información sobre cómo usar el patrón `Repository` para gestionar y consultar colecciones, consulta los [artículos sobre Repository](/docs/advanced/repository/overview).
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

De forma predeterminada, las columnas no se pueden ordenar, a menos que se habilite explícitamente esta opción. Para permitir la ordenación de una columna específica, usa el método `setSortable(true)`:

```java
table.getColumn("Age").setSortable(true);
```

## Ordenación múltiple {#multi-sorting}

:::warning La ordenación por varias columnas está deshabilitada de forma predeterminada en webforJ `25.00`
Antes de webforj `25.00`, las tablas admitían la ordenación por varias columnas de forma predeterminada. A partir de la versión `25.00`, este comportamiento cambió: ahora los desarrolladores deben habilitar explícitamente la ordenación por varias columnas.
:::

Si se necesita ordenar varias columnas, se debe aplicar `setMultiSorting(true)` a la tabla. Esto permite a los usuarios ordenar varias columnas en secuencia:

```java
table.setMultiSorting(true);
```

Con la ordenación múltiple habilitada, al hacer clic en varios encabezados de columna, estas se ordenarán secuencialmente. La prioridad de ordenación se indica visualmente en la interfaz de la tabla.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

También puedes definir la prioridad de ordenación mediante programación para la ordenación en el servidor. Usa `setSortIndex()` en las columnas que quieras ordenar, según su prioridad:

```java
// Server-side sort order
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info El orden de las columnas importa
A menos que se use `setSortIndex()`, la tabla ordena de forma predeterminada según el orden en que se declaran las columnas.
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortOrderView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Dirección de ordenación {#sort-direction}

Hay tres opciones disponibles para la dirección en que se puede ordenar una columna:

- `SortDirection.ASC`: Ordena la columna de forma ascendente.
- `SortDirection.DESC`: Ordena la columna de forma descendente.
- `SortDirection.NONE`: No aplica ninguna ordenación a la columna.

Cuando la ordenación está habilitada para una columna, verás un conjunto de indicadores de flecha vertical en la parte superior de esa columna. Estas flechas permiten al usuario alternar entre las distintas direcciones de ordenación.

Cuando se selecciona el orden ascendente, se muestra `^`; para el orden descendente, se muestra `v`.


## Ordenación en el cliente y en el servidor {#client-vs-server-side-sorting}

La ordenación de datos se puede clasificar, en términos generales, en dos enfoques principales: **ordenación en el cliente** y **ordenación en el servidor**.

### Ordenación en el cliente {#client-sorting}

La ordenación en el cliente consiste en organizar y mostrar los datos directamente en la interfaz de usuario de la aplicación cliente. Es el tipo de ordenación con el que interactúan los usuarios al hacer clic en los encabezados de las columnas, lo que influye en la representación visual de los datos en pantalla.

El desarrollador no tiene control directo sobre la ordenación en el cliente; esta depende del tipo de columna definido en Java. Actualmente se admiten los siguientes tipos:

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
La ordenación en el cliente no funciona cuando solo una parte de los datos está disponible en el cliente.
:::

### Ordenación en el servidor {#server-sorting}

A diferencia de la ordenación en el cliente, la ordenación en el servidor consiste en organizar los datos en el servidor antes de transmitirlos al cliente. Este enfoque resulta especialmente beneficioso cuando se trabaja con grandes conjuntos de datos que sería poco práctico transferir por completo al cliente.

Los desarrolladores tienen más control sobre la lógica de ordenación en el servidor. Esto permite implementar algoritmos de ordenación complejos y optimizaciones, por lo que resulta adecuado para situaciones con grandes volúmenes de datos. Así, el cliente recibe los datos ya ordenados, lo que minimiza la necesidad de procesarlos extensamente en el cliente.


:::info
La ordenación en el servidor es una estrategia orientada al rendimiento para trabajar con conjuntos de datos que superan las capacidades del procesamiento eficiente en el cliente, y es el método predeterminado que usa `Table`.
:::

### Nombre de la propiedad de la columna {#column-property-name}

De forma predeterminada, `Table` usa el ID de una columna como nombre de propiedad al crear los criterios de ordenación para un repositorio de backend. Si el ID visible de una columna no coincide con la propiedad de los datos subyacentes, o si la columna muestra un valor calculado, usa `setPropertyName()` para indicar explícitamente a `Table` por qué propiedad debe ordenar.

```java
// Column ID is "Full Name", but the backend property is "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

El nombre de la propiedad se transfiere a `OrderCriteria` cuando se genera un evento de ordenación, lo que permite que los repositorios de backend, como Spring Data JPA, o los adaptadores REST creen la cláusula `ORDER BY` correcta.

:::warning
Sin `setPropertyName()`, `Table` usa el ID de la columna como alternativa. Si este no coincide con una propiedad válida del backend, la ordenación fallará silenciosamente o devolverá datos en un orden incorrecto.
:::

También se admiten rutas a propiedades anidadas mediante la notación con puntos:

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparadores {#comparators}

El componente `Column` permite a los desarrolladores usar `Comparators` de Java para la ordenación dinámica y personalizada. Un `Comparator` es un mecanismo que se usa para ordenar dos objetos de la misma clase, incluso si esa clase está definida por el usuario. Esta funcionalidad ofrece a los desarrolladores la flexibilidad de personalizar cómo se ordenan los datos y un mayor control sobre el comportamiento de ordenación predeterminado basado en el orden natural.

Para usar la ordenación con `Comparator` en un `Column`, puedes usar el método `setComparator()`. Este método permite definir una función `Comparator` personalizada que determina la lógica de ordenación.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

En el ejemplo anterior, se especifica una función de comparación personalizada que toma dos elementos (`a` y `b`) y define el orden de clasificación según los valores enteros analizados del atributo `Number`.

El uso de comparadores para ordenar columnas resulta especialmente útil al trabajar con valores no numéricos. También es útil para implementar algoritmos de ordenación complejos.

:::info
De forma predeterminada, `Table` usa la ordenación en el servidor y ordena los valores no primitivos mediante el método `toString()` de `Object`, convirtiéndolos en cadenas y ordenándolos después.
:::
