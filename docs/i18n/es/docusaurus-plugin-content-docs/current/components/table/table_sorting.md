---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 16c35fa416c4ebba3b680deb2d8925ef
---
La clasificación permite a los usuarios organizar datos en columnas por orden, haciendo que la información sea más fácil de leer y analizar. Esto es útil cuando los usuarios necesitan encontrar rápidamente los valores más altos o más bajos en una columna particular.

:::tip Gestión y consulta de datos
Para obtener información sobre cómo utilizar el patrón `Repository` para gestionar y consultar colecciones, consulte los [artículos de Repository](/docs/advanced/repository/overview).
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

Por defecto, una columna no es ordenable a menos que se habilite explícitamente. Para permitir la clasificación en una columna específica, utilice el método `setSortable(true)`:

```java 
table.getColumn("Age").setSortable(true);
```

## Clasificación múltiple {#multi-sorting}

:::warning Clasificación de múltiples columnas desactivada por defecto en webforJ `25.00`
Antes de webforj `25.00`, las tablas admitían la clasificación de múltiples columnas por defecto. A partir de la versión `25.00`, este comportamiento cambió: los desarrolladores ahora deben habilitar explícitamente la clasificación de múltiples columnas.
:::

Si se necesita clasificación múltiple, se debe aplicar `setMultiSorting(true)` a la tabla. Esto permite a los usuarios ordenar múltiples columnas en secuencia:

```java
table.setMultiSorting(true);
```

Con la clasificación múltiple habilitada, hacer clic en múltiples encabezados de columnas las ordenará secuencialmente. La prioridad de clasificación se indica visualmente en la interfaz de la tabla.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

También puede definir la prioridad de clasificación programáticamente para la clasificación del lado del servidor. Utilice `setSortOrder()` en las columnas que desea ordenar, en orden de prioridad:

```java
// Orden de clasificación del lado del servidor
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info El orden de las columnas importa
A menos que se utilice `setSortOrder()`, la tabla predetermina la clasificación según el orden en el que se declaran las columnas.
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

## Dirección de clasificación {#sort-direction}

Hay tres configuraciones disponibles para la dirección en la que se puede ordenar una columna:

- `SortDirection.ASC`: Ordena la columna en orden ascendente.
- `SortDirection.DESC`: Ordena la columna en orden descendente.
- `SortDirection.NONE`: No se aplica ninguna clasificación a la columna.

Cuando una columna tiene la clasificación habilitada, verá un conjunto de indicadores de flechas verticales aparecer en la parte superior de la columna establecida. Estas flechas permiten al usuario alternar entre las diferentes direcciones de clasificación.

Cuando se selecciona el orden ascendente, se mostrará un `^`, mientras que el orden descendente mostrará un `v`.

## Clasificación del lado del cliente vs. del lado del servidor {#client-vs-server-side-sorting}

La clasificación de datos se puede clasificar en dos enfoques principales: **Clasificación del Cliente** y **Clasificación del Servidor**.

### Clasificación del cliente {#client-sorting}

La clasificación del cliente implica organizar y mostrar datos directamente dentro de la interfaz de usuario de la aplicación cliente. Es la clasificación con la que interactúan los usuarios al hacer clic en los encabezados de las columnas, influyendo en la representación visual de los datos en la pantalla.

El desarrollador no tiene control directo sobre la clasificación del lado del cliente, sino que esta está determinada por el tipo de columna proporcionado en Java. Los siguientes tipos son actualmente compatibles:

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
La clasificación del cliente no funciona cuando solo está disponible una parte de los datos en el cliente.
:::

### Clasificación del servidor {#server-sorting}

A diferencia de la clasificación del lado del cliente, la clasificación del servidor implica organizar y clasificar datos en el servidor antes de transmitirlos al cliente. Este enfoque es particularmente beneficioso cuando se trabaja con conjuntos de datos grandes que podrían ser imprácticos para transferir completamente al cliente.

Los desarrolladores tienen más control sobre la lógica de la clasificación del servidor. Esto permite la implementación de algoritmos de clasificación complejos y optimizaciones, lo que lo hace adecuado para escenarios con datos extensos. Esto garantiza que el cliente reciba datos preordenados, minimizando la necesidad de procesamiento extensivo en el lado del cliente.

:::info
La clasificación del servidor es una estrategia orientada al rendimiento para manejar conjuntos de datos que superan las capacidades de procesamiento eficiente del lado del cliente, y es el método predeterminado utilizado por la `Table`.
:::

### Nombre de la propiedad de la columna {#column-property-name}

Por defecto, la `Table` utiliza el ID de una columna como el nombre de la propiedad al construir criterios de clasificación para un repositorio en el backend. Cuando el ID de visualización de una columna no coincide con la propiedad de datos subyacente, o cuando la columna muestra un valor calculado, utilice `setPropertyName()` para indicarle explícitamente a la `Table` qué propiedad ordenar.

```java
// El ID de la columna es "Full Name", pero la propiedad del backend es "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

El nombre de la propiedad se envía a los `OrderCriteria` cuando se activa un evento de clasificación, permitiendo que repositorios en el backend como Spring Data JPA o adaptadores REST construyan la cláusula `ORDER BY` correcta.

:::warning
Sin `setPropertyName()`, la `Table` vuelve al ID de la columna. Si esto no coincide con una propiedad válida del backend, la clasificación fallará silenciosamente o devolverá datos ordenados incorrectamente.
:::

También se admiten rutas de propiedades anidadas utilizando la notación de punto:

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparadores {#comparators}

El componente `Column` permite a los desarrolladores utilizar `Comparators` de Java para la clasificación dinámica y personalizada. Un `Comparator` es un mecanismo utilizado para ordenar dos objetos de la misma clase, incluso si esa clase está definida por el usuario. Esta funcionalidad brinda a los desarrolladores la flexibilidad para personalizar cómo se ordenan los datos, brindando un mayor control sobre el comportamiento de clasificación predeterminado basado en el orden natural.

Para aprovechar la clasificación por `Comparator` en una `Column`, puede utilizar el método `setComparator()`. Este método le permite definir una función `Comparator` personalizada que dictamina la lógica de clasificación.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

En el ejemplo anterior, se especifica una función de comparador personalizada que toma dos elementos (a y b), y define el orden de clasificación en función de los valores enteros analizados del atributo `Number`.

Usar Comparators para la clasificación de columnas es particularmente útil cuando se manejan valores no numéricos. También son útiles para implementar algoritmos de clasificación complejos.

:::info
Por defecto, la `Table` utiliza la clasificación del lado del servidor y ordena los valores no primitivos utilizando el método `toString()` de Object, convirtiéndolos en sus valores de cadena y luego ordenándolos.
:::
