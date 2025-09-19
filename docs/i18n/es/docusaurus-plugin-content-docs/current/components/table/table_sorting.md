---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 3c9156ad5da204816bd4ce783003cbf7
---
La clasificación permite a los usuarios organizar datos en columnas por orden, facilitando la lectura y el análisis de la información. Esto es útil cuando los usuarios necesitan encontrar rápidamente los valores más altos o más bajos en una columna determinada.

:::tip Gestión y consulta de datos
Para obtener información sobre cómo utilizar el patrón `Repository` para gestionar y consultar colecciones, consulta los [artículos del Repository](/docs/advanced/repository/overview).
:::

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Por defecto, una columna no es ordenable a menos que se habilite explícitamente. Para permitir la clasificación en una columna específica, usa el método `setSortable(true)`:

```java 
table.getColumn("Edad").setSortable(true);
```

## Clasificación múltiple {#multi-sorting}

:::advertencia Clasificación de múltiples columnas deshabilitada por defecto en webforJ `25.00`
Antes de webforj `25.00`, las tablas admitían la clasificación de múltiples columnas por defecto. A partir de la versión `25.00`, este comportamiento cambió: los desarrolladores deben habilitar explícitamente la clasificación de múltiples columnas.
:::

Si se necesita clasificación múltiple, se debe aplicar `setMultiSorting(true)` a la tabla. Esto permite a los usuarios ordenar múltiples columnas en secuencia:

```java
table.setMultiSorting(true);
```

Con la clasificación múltiple habilitada, hacer clic en múltiples encabezados de columna los ordenará secuencialmente. La prioridad de clasificación se indica visualmente en la interfaz de usuario de la tabla.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

También puedes definir la prioridad de clasificación de forma programática para la clasificación del lado del servidor. Usa `setSortOrder()` en las columnas que quieras ordenar, en orden de prioridad:

```java
// Orden de clasificación del lado del servidor
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info El orden de las columnas es importante
A menos que se utilice `setSortOrder()`, la tabla se ordena por defecto según el orden en el que se declaran las columnas.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Dirección de ordenación {#sort-direction}

Hay tres configuraciones disponibles para la dirección en la que se puede ordenar una columna:

- `SortDirection.ASC`: Ordena la columna en orden ascendente.
- `SortDirection.DESC`: Ordena la columna en orden descendente.
- `SortDirection.NONE`: No se aplica ninguna ordenación a la columna.

Cuando una columna tiene la ordenación habilitada, verás un conjunto de indicadores de flechas verticales aparecer en la parte superior de la columna. Estas flechas permiten al usuario alternar entre las diferentes direcciones de ordenación.

Cuando se selecciona el orden ascendente, se mostrará un `^`, mientras que el orden descendente mostrará un `v`.


## Clasificación del cliente vs. del lado del servidor {#client-vs-server-side-sorting}

La clasificación de datos se puede categorizar en dos enfoques principales: **Clasificación del Cliente** y **Clasificación del Servidor**.

### Clasificación del cliente {#client-sorting}

La clasificación del cliente implica organizar y mostrar datos directamente en la interfaz de usuario de la aplicación cliente. Es la clasificación con la que los usuarios interactúan cuando hacen clic en los encabezados de las columnas, influyendo en la representación visual de los datos en la pantalla.

El desarrollador no tiene control directo sobre la clasificación del lado del cliente, sino que está determinada por el tipo de columna proporcionado en Java. Actualmente, los siguientes tipos son admitidos:

- TEXTO
- NÚMERO
- BOOLEANO
- FECHA
- FECHA Y HORA
- HORA

:::info
La clasificación del cliente no funciona cuando solo una parte de los datos está disponible en el cliente.
:::

### Clasificación del servidor {#server-sorting}

A diferencia de la clasificación del lado del cliente, la clasificación del servidor implica organizar y estructurar datos en el servidor antes de transmitirlos al cliente. Este enfoque es especialmente beneficioso cuando se trabaja con conjuntos de datos grandes que podrían ser poco prácticos de transferir completamente al cliente.

Los desarrolladores tienen más control sobre la lógica de la clasificación del servidor. Esto permite la implementación de algoritmos de clasificación complejos y optimizaciones, lo que hace que sea adecuado para escenarios con grandes volúmenes de datos. Esto asegura que el cliente reciba datos preordenados, minimizando la necesidad de un procesamiento extenso del lado del cliente.

:::info
La clasificación del servidor es una estrategia orientada al rendimiento para manejar conjuntos de datos que exceden las capacidades de un procesamiento eficiente del lado del cliente, y es el método por defecto utilizado por la `Table`.
:::

#### Comparadores {#comparators}

El componente `Column` permite a los desarrolladores usar `Comparators` de Java para una clasificación dinámica y personalizada. Un `Comparator` es un mecanismo utilizado para ordenar dos objetos de la misma clase, incluso si esa clase está definida por el usuario. Esta funcionalidad proporciona a los desarrolladores la flexibilidad para personalizar cómo se ordenan los datos, ofreciendo un mayor control sobre el comportamiento de ordenación predeterminado basado en el orden natural.

Para aprovechar la clasificación `Comparator` en una `Column`, puedes utilizar el método `setComparator()`. Este método te permite definir una función `Comparator` personalizada que dictará la lógica de ordenación.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

En el ejemplo anterior, se especifica una función comparadora personalizada que toma dos elementos (a y b), y define el orden de clasificación según los valores enteros analizados del atributo `Number`.

Usar Comparators para la clasificación de columnas es particularmente útil al manejar valores no numéricos. También son útiles para implementar algoritmos de clasificación complejos.

:::info
Por defecto, la `Table` utiliza clasificación del lado del servidor, y ordena los valores que no son primitivos utilizando el método `toString()` de Object, convirtiéndolos a sus valores de cadena y luego ordenándolos.
:::
