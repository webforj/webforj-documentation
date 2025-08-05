---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 2c6b6a04c5c33d9e3f1663d15c85a2e9
---
Ordenar permite a los usuarios organizar datos en columnas por orden, facilitando la lectura y análisis de la información. Esto es útil cuando los usuarios necesitan encontrar rápidamente los valores más altos o más bajos en una columna particular.

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

De forma predeterminada, una columna no se puede ordenar a menos que se habilite explícitamente. Para permitir la ordenación en una columna específica, utiliza el método `setSortable(true)`:

```java 
table.getColumn("Age").setSortable(true);
```

## Ordenación múltiple {#multi-sorting}

:::warning La ordenación de múltiples columnas está desactivada por defecto en webforJ `25.00`
Antes de webforj `25.00`, las tablas admitían la ordenación de múltiples columnas por defecto. A partir de la versión `25.00`, este comportamiento cambió: los desarrolladores ahora deben habilitar explícitamente la ordenación de múltiples columnas.
:::

Si se necesita ordenación múltiple, se debe aplicar `setMultiSorting(true)` a la tabla. Esto permite a los usuarios ordenar varias columnas en secuencia:

```java
table.setMultiSorting(true);
```

Con la ordenación múltiple habilitada, hacer clic en múltiples encabezados de columna los ordenará secuencialmente. La prioridad de ordenación se indica visualmente en la interfaz de usuario de la tabla.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

También puedes definir la prioridad de ordenación programáticamente para la ordenación del lado del servidor. Usa `setSortOrder()` en las columnas que deseas ordenar, en orden de prioridad:

```java
// Orden de clasificación del lado del servidor
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info El orden de las columnas es importante
A menos que se utilice `setSortOrder()`, la tabla por defecto ordena según el orden en que se declararon las columnas.
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

Cuando una columna tiene la ordenación habilitada, verás un conjunto de indicadores verticales de flecha aparecer en la parte superior de la columna. Estas flechas permiten al usuario alternar entre las diferentes direcciones de ordenación.

Cuando se selecciona el orden ascendente, se mostrará un `^`, mientras que el orden descendente mostrará un `v`.

## Ordenación del lado del cliente vs del servidor {#client-vs-server-side-sorting}

La ordenación de datos se puede clasificar en dos enfoques principales: **Ordenación del Cliente** y **Ordenación del Servidor**.

### Ordenación del cliente {#client-sorting}

La ordenación del cliente implica organizar y mostrar datos directamente en la interfaz de usuario de la aplicación del cliente. Es la ordenación con la que los usuarios interactúan cuando hacen clic en los encabezados de columna, influyendo en la representación visual de los datos en la pantalla.

El desarrollador no tiene control directo sobre la ordenación del lado del cliente, sino que está determinada por el tipo de columna proporcionado en Java. Los siguientes tipos son actualmente admitidos:

- TEXTO
- NÚMERO
- BOOLEANO
- FECHA
- FECHA/HORA
- HORA

:::info
La ordenación del cliente no funciona cuando solo está disponible una parte de los datos en el cliente.
:::

### Ordenación del servidor {#server-sorting}

A diferencia de la ordenación del lado del cliente, la ordenación del servidor implica organizar y clasificar datos en el servidor antes de enviarlos al cliente. Este enfoque es especialmente beneficioso cuando se trabaja con grandes conjuntos de datos que podrían ser imprácticos de transferir por completo al cliente.

Los desarrolladores tienen más control sobre la lógica de la ordenación del servidor. Esto permite la implementación de algoritmos de ordenación complejos y optimizaciones, haciéndolo adecuado para escenarios con datos extensos. Esto asegura que el cliente reciba datos pre-ordenados, minimizando la necesidad de un procesamiento extenso del lado del cliente.

:::info
La ordenación del servidor es una estrategia orientada al rendimiento para tratar conjuntos de datos que superan las capacidades de procesamiento eficiente del lado del cliente, y es el método predeterminado utilizado por el `Table`.
:::

#### Comparadores {#comparators}

El componente `Column` permite a los desarrolladores usar `Comparators` de Java para ordenación dinámica y personalizada. Un `Comparator` es un mecanismo usado para ordenar dos objetos de la misma clase, incluso si esa clase es definida por el usuario. Esta funcionalidad ofrece a los desarrolladores la flexibilidad de personalizar cómo se ordenan los datos, proporcionando un mayor control sobre el comportamiento de ordenación predeterminado basado en el orden natural.

Para aprovechar la ordenación `Comparator` en una `Column`, puedes usar el método `setComparator()`. Este método permite definir una función `Comparator` personalizada que dicta la lógica de ordenación.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

En el ejemplo anterior, se especifica una función de comparador personalizada que toma dos elementos (a y b), y define el orden de ordenación basado en los valores enteros analizados del atributo `Number`.

Usar comparadores para la ordenación de columnas es particularmente útil al manejar valores no numéricos. También son útiles para implementar algoritmos de ordenación complejos.

:::info
Por defecto, el `Table` utiliza la ordenación del lado del servidor, y ordena los valores no primitivos usando el método `toString()` de Object, convirtiéndolos a sus valores de cadena y luego ordenándolos.
:::
