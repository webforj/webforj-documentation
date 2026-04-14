---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 99281603bebefd43f033e9d0c958c366
---
La clasificación permite a los usuarios organizar datos en columnas por orden, lo que facilita la lectura y el análisis de la información. Esto es útil cuando los usuarios necesitan encontrar rápidamente los valores más altos o más bajos en una columna en particular.

:::tip Gestión y consulta de datos
Para obtener información sobre cómo usar el patrón `Repository` para gestionar y consultar colecciones, consulte los [artículos del Repositorio](/docs/advanced/repository/overview).
:::

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Por defecto, una columna no es clasificable a menos que se habilite explícitamente. Para permitir la clasificación en una columna específica, use el método `setSortable(true)`:

```java 
table.getColumn("Age").setSortable(true);
```

## Clasificación múltiple {#multi-sorting}

:::warning Clasificación en varias columnas desactivada por defecto en webforJ `25.00`
Antes de webforj `25.00`, las tablas admitían la clasificación en varias columnas por defecto. A partir de la versión `25.00`, este comportamiento cambió: los desarrolladores ahora deben habilitar explícitamente la clasificación en varias columnas.
:::

Si se necesita clasificación múltiple, se debe aplicar `setMultiSorting(true)` a la tabla. Esto permite a los usuarios clasificar múltiples columnas en secuencia:

```java
table.setMultiSorting(true);
```

Con la clasificación múltiple habilitada, hacer clic en varios encabezados de columna los clasificará secuencialmente. La prioridad de clasificación se indica visualmente en la interfaz de usuario de la tabla.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

También puede definir la prioridad de clasificación programáticamente para la clasificación del lado del servidor. Use `setSortOrder()` en las columnas que desea clasificar, en orden de prioridad:

```java
// Orden de clasificación del lado del servidor
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info El orden de las columnas importa
A menos que se use `setSortOrder()`, la tabla se clasifica por defecto según el orden en que se declaran las columnas.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Dirección de clasificación {#sort-direction}

Hay tres configuraciones disponibles para la dirección en la que se puede clasificar una columna:

- `SortDirection.ASC`: Clasifica la columna en orden ascendente.
- `SortDirection.DESC`: Clasifica la columna en orden descendente.
- `SortDirection.NONE`: Sin clasificación aplicada a la columna.

Cuando una columna tiene clasificación habilitada, verá un conjunto de indicadores de flecha vertical en la parte superior de esa columna. Estas flechas permiten al usuario alternar entre las diferentes direcciones de clasificación.

Cuando se selecciona el orden ascendente, se mostrará un `^`, mientras que el orden descendente mostrará un `v`.


## Clasificación del lado del cliente vs del servidor {#client-vs-server-side-sorting}

La clasificación de datos se puede clasificar en dos enfoques principales: **Clasificación del Cliente** y **Clasificación del Servidor**.

### Clasificación del cliente {#client-sorting}

La clasificación del cliente implica organizar y mostrar datos directamente dentro de la interfaz de usuario de la aplicación cliente. Es la clasificación con la que los usuarios interactúan cuando hacen clic en los encabezados de las columnas, influyendo en la representación visual de los datos en la pantalla.

El desarrollador no tiene control directo sobre la clasificación del lado del cliente, que está determinada por el tipo de columna proporcionado en Java. Los siguientes tipos son actualmente compatibles:

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
La clasificación del cliente no funciona cuando solo una porción de los datos está disponible en el cliente.
:::

### Clasificación del servidor {#server-sorting}

A diferencia de la clasificación del lado del cliente, la clasificación del servidor implica organizar y clasificar datos en el servidor antes de transmitirlos al cliente. Este enfoque es particularmente beneficioso cuando se trabaja con grandes conjuntos de datos que pueden ser poco prácticos de transferir completamente al cliente.

Los desarrolladores tienen más control sobre la lógica de la clasificación del servidor. Esto permite implementar algoritmos de clasificación complejos y optimizaciones, lo que lo hace adecuado para escenarios con datos extensos. Así, se asegura que el cliente reciba datos preclASIFICADOS, minimizando la necesidad de procesamiento extenso del lado del cliente.


:::info
La clasificación del servidor es una estrategia orientada al rendimiento para manejar conjuntos de datos que superan las capacidades de un procesamiento eficiente del lado del cliente y es el método por defecto utilizado por la `Table`.
:::

### Nombre de propiedad de columna {#column-property-name}

Por defecto, la `Table` utiliza el ID de una columna como el nombre de propiedad al construir criterios de clasificación para un repositorio de backend. Cuando el ID de visualización de una columna no coincide con la propiedad de datos subyacente, o cuando la columna muestra un valor calculado, utilice `setPropertyName()` para indicar explícitamente a la `Table` qué propiedad clasificar.

```java
// El ID de la columna es "Full Name", pero la propiedad de backend es "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

El nombre de la propiedad se reenvía a los `OrderCriteria` cuando se activa un evento de clasificación, permitiendo que los repositorios de backend como Spring Data JPA o adaptadores REST construyan la cláusula `ORDER BY` correcta.

:::warning
Sin `setPropertyName()`, la `Table` vuelve al ID de la columna. Si esto no coincide con una propiedad válida del backend, la clasificación fallará silenciosamente o devolverá datos ordenados incorrectamente.
:::

También se admiten rutas de propiedades anidadas utilizando notación de punto:

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparadores {#comparators}

El componente `Column` permite a los desarrolladores usar `Comparators` de Java para una clasificación dinámica y personalizada. Un `Comparator` es un mecanismo utilizado para ordenar dos objetos de la misma clase, incluso si esa clase es definida por el usuario. Esta funcionalidad brinda a los desarrolladores la flexibilidad de personalizar cómo se ordenan los datos, brindando un mayor control sobre el comportamiento de clasificación predeterminado basado en el orden natural.

Para aprovechar la clasificación con un `Comparator` en una `Column`, puede usar el método `setComparator()`. Este método le permite definir una función `Comparator` personalizada que dicta la lógica de clasificación.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

En el ejemplo anterior, se especifica una función comparadora personalizada que toma dos elementos (a y b) y define el orden de clasificación basado en los valores enteros analizados del atributo `Number`.

Usar Comparators para la clasificación de columnas es particularmente útil cuando se manejan valores no numéricos. También son útiles para implementar algoritmos de clasificación complejos.

:::info
Por defecto, la `Table` utiliza la clasificación del lado del servidor y clasifica valores no primitivos utilizando el método `toString()` de Object, convirtiéndolos en sus valores de cadena y luego ordenándolos.
:::
