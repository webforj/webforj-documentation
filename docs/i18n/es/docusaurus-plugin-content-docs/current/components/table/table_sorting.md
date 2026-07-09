---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: f577bea532193b97e6fef03a8bcb641b
---
Sorting permite a los usuarios organizar datos en columnas por orden, facilitando la lectura y el análisis de la información. Esto es útil cuando los usuarios necesitan encontrar rápidamente los valores más altos o más bajos en una columna específica.

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

Por defecto, una columna no es ordenable a menos que se habilite explícitamente. Para permitir el ordenamiento en una columna específica, utilice el método `setSortable(true)`:

```java
table.getColumn("Age").setSortable(true);
```

## Ordenamiento múltiple {#multi-sorting}

:::warning Ordenamiento de múltiples columnas deshabilitado por defecto en webforJ `25.00`
Antes de webforj `25.00`, las tablas admitían el ordenamiento de múltiples columnas por defecto. A partir de la versión `25.00`, este comportamiento cambió: los desarrolladores ahora deben habilitar explícitamente el ordenamiento de múltiples columnas.
:::

Si se necesita ordenamiento múltiple, se debe aplicar `setMultiSorting(true)` a la tabla. Esto permite a los usuarios ordenar múltiples columnas en secuencia:

```java
table.setMultiSorting(true);
```

Con el ordenamiento múltiple habilitado, hacer clic en los encabezados de múltiples columnas las ordenará secuencialmente. La prioridad de ordenamiento se indica visualmente en la interfaz de usuario de la tabla.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

También puede definir la prioridad de ordenamiento programáticamente para el ordenamiento del lado del servidor. Utilice `setSortOrder()` en las columnas que desea ordenar, en orden de prioridad:

```java
// Orden de clasificación del lado del servidor
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info El orden de las columnas importa
A menos que se utilice `setSortOrder()`, la tabla se ordenará por el orden en el que se declaran las columnas.
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
- `SortDirection.NONE`: No se aplica ordenamiento a la columna.

Cuando una columna tiene el ordenamiento habilitado, verá un conjunto de indicadores de flechas verticales aparecer en la parte superior de la columna establecida. Estas flechas permiten al usuario alternar entre las diferentes direcciones de ordenamiento.

Cuando se selecciona el orden ascendente, se mostrará un `^`, mientras que el orden descendente mostrará un `v`.

## Ordenamiento del lado del cliente vs del lado del servidor {#client-vs-server-side-sorting}

El ordenamiento de datos se puede clasificar en dos enfoques principales: **Ordenamiento del Cliente** y **Ordenamiento del Servidor**.

### Ordenamiento del cliente {#client-sorting}

El ordenamiento del cliente implica organizar y mostrar datos directamente dentro de la interfaz de usuario de la aplicación cliente. Es el ordenamiento con el que interactúan los usuarios cuando hacen clic en los encabezados de las columnas, influyendo en la representación visual de los datos en la pantalla.

El desarrollador no tiene control directo sobre el ordenamiento del lado del cliente, ya que se determina por el tipo de columna proporcionado en Java. Los siguientes tipos son actualmente compatibles:

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

A diferencia del ordenamiento del lado del cliente, el ordenamiento del servidor implica organizar y organizar datos en el servidor antes de transmitirlos al cliente. Este enfoque es particularmente beneficioso cuando se trata de conjuntos de datos grandes que podrían ser imprácticos de transferir completamente al cliente.

Los desarrolladores tienen más control sobre la lógica del ordenamiento del servidor. Esto permite la implementación de algoritmos de ordenamiento complejos y optimizaciones, lo que lo hace adecuado para escenarios con datos extensos. Esto asegura que el cliente reciba datos pre-ordenados, minimizando la necesidad de un procesamiento extensivo del lado del cliente.

:::info
El ordenamiento del servidor es una estrategia orientada al rendimiento para manejar conjuntos de datos que superan las capacidades del procesamiento eficiente del lado del cliente, y es el método por defecto utilizado por la `Table`.
:::

### Nombre de propiedad de columna {#column-property-name}

Por defecto, la `Table` utiliza el ID de una columna como el nombre de propiedad al construir criterios de ordenamiento para un repositorio en el backend. Cuando el ID de visualización de una columna no coincide con la propiedad de datos subyacente, o cuando la columna muestra un valor calculado, utilice `setPropertyName()` para indicar explícitamente a la `Table` qué propiedad ordenar.

```java
// El ID de la columna es "Full Name", pero la propiedad del backend es "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

El nombre de la propiedad se envía a los `OrderCriteria` cuando se dispara un evento de ordenamiento, permitiendo que repositorios en el backend como Spring Data JPA o adaptadores REST construyan la cláusula `ORDER BY` correcta.

:::warning
Sin `setPropertyName()`, la `Table` recurrirá al ID de la columna. Si esto no coincide con una propiedad válida en el backend, el ordenamiento fallará silenciosamente o devolverá datos ordenados incorrectamente.
:::

También se admiten rutas de propiedades anidadas utilizando notación de puntos:

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparadores {#comparators}

El componente `Column` permite a los desarrolladores utilizar `Comparators` de Java para ordenamiento dinámico y personalizado. Un `Comparator` es un mecanismo utilizado para ordenar dos objetos de la misma clase, incluso si esa clase es definida por el usuario. Esta funcionalidad proporciona a los desarrolladores la flexibilidad para personalizar cómo se ordenan los datos, proporcionando un mayor control sobre el comportamiento de ordenamiento por defecto basado en el orden natural.

Para aprovechar el ordenamiento `Comparator` en una `Column`, puede utilizar el método `setComparator()`. Este método le permite definir una función `Comparator` personalizada que dicta la lógica de ordenamiento.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

En el ejemplo anterior, se especifica una función comparadora personalizada que toma dos elementos (a y b), y define el orden de ordenamiento en función de los valores enteros analizados del atributo `Number`.

Utilizar Comparators para el ordenamiento de columnas es particularmente útil al manejar valores no numéricos. También son útiles para implementar algoritmos de ordenamiento complejos.

:::info
Por defecto, la `Table` utiliza ordenamiento del lado del servidor y ordena valores no primitivos utilizando el método `toString()` de Object, convirtiéndolos a sus valores de cadena y luego ordenándolos.
:::
