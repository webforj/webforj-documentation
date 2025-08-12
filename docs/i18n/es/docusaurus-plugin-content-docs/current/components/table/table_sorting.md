---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: a51ea10e855e94a24cb6e74d8f774abe
---
La clasificación permite a los usuarios organizar datos en columnas por orden, lo que facilita la lectura y el análisis de la información. Esto es útil cuando los usuarios necesitan encontrar rápidamente los valores más altos o más bajos en una columna particular.

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Por defecto, una columna no se puede clasificar a menos que se habilite explícitamente. Para permitir la clasificación en una columna específica, utiliza el método `setSortable(true)`:

```java 
table.getColumn("Age").setSortable(true);
```

## Clasificación múltiple {#multi-sorting}

:::warning Clasificación de Múltiples Columnas Desactivada por Defecto en webforJ `25.00`
Antes de webforj `25.00`, las tablas admitían la clasificación de múltiples columnas por defecto. A partir de la versión `25.00`, este comportamiento cambió; los desarrolladores ahora deben habilitar explícitamente la clasificación de múltiples columnas.
:::

Si se necesita clasificación múltiple, se debe aplicar `setMultiSorting(true)` a la tabla. Esto permite a los usuarios clasificar varias columnas en secuencia:

```java
table.setMultiSorting(true);
```

Con la clasificación múltiple habilitada, hacer clic en múltiples encabezados de columna los clasificarán secuencialmente. La prioridad de clasificación se indica visualmente en la interfaz de usuario de la tabla.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

También puedes definir la prioridad de clasificación programáticamente para la clasificación del lado del servidor. Usa `setSortOrder()` en las columnas que deseas clasificar, en orden de prioridad:

```java
// Orden de clasificación del lado del servidor
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info El Orden de las Columnas Importa
A menos que se use `setSortOrder()`, la tabla se clasifica por defecto en el orden en que se declaran las columnas.
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
- `SortDirection.NONE`: No se aplica clasificación a la columna.

Cuando una columna tiene la clasificación habilitada, verás un conjunto de indicadores de flechas verticales aparecer en la parte superior de la columna clasificada. Estas flechas permiten al usuario alternar entre las diferentes direcciones de clasificación.

Cuando se selecciona el orden ascendente, se mostrará un `^`, mientras que el orden descendente mostrará un `v`.

## Clasificación del lado del cliente vs. del lado del servidor {#client-vs-server-side-sorting}

La clasificación de datos se puede categorizar en dos enfoques principales: **Clasificación del Cliente** y **Clasificación del Servidor**.

### Clasificación del cliente {#client-sorting}

La clasificación del cliente implica organizar y mostrar datos directamente dentro de la interfaz de usuario de la aplicación cliente. Es la clasificación con la que los usuarios interactúan cuando hacen clic en los encabezados de las columnas, influyendo en la representación visual de los datos en la pantalla.

El desarrollador no tiene control directo sobre la clasificación del lado del cliente, sino que esta se determina por el tipo de columna proporcionado en Java. Los siguientes tipos son actualmente compatibles:

- TEXTO
- NÚMERO
- BOOLEANO
- FECHA
- FECHA HORA
- HORA

:::info
La clasificación del cliente no funciona cuando solo una parte de los datos está disponible en el cliente.
:::

### Clasificación del servidor {#server-sorting}

En contraste con la clasificación del lado del cliente, la clasificación del servidor implica organizar y clasificar datos en el servidor antes de transmitirlos al cliente. Este enfoque es particularmente beneficioso cuando se trabaja con conjuntos de datos grandes que podrían ser imprácticos de transferir completamente al cliente.

Los desarrolladores tienen más control sobre la lógica de la clasificación del servidor. Esto permite la implementación de algoritmos complejos de clasificación y optimizaciones, lo que lo hace adecuado para escenarios con datos extensos. Esto asegura que el cliente reciba datos preclasificados, minimizando la necesidad de un procesamiento extenso del lado del cliente.

:::info
La clasificación del servidor es una estrategia orientada al rendimiento para manejar conjuntos de datos que superan las capacidades de procesamiento eficiente del lado del cliente, y es el método predeterminado utilizado por la `Table`.
:::

#### Comparadores {#comparators}

El componente `Column` permite a los desarrolladores usar `Comparators` de Java para clasificación dinámica y personalizada. Un `Comparator` es un mecanismo utilizado para ordenar dos objetos de la misma clase, incluso si esa clase es definida por el usuario. Esta funcionalidad proporciona a los desarrolladores la flexibilidad para personalizar cómo se clasifican los datos, ofreciendo un mayor control sobre el comportamiento de clasificación predeterminado basado en el orden natural.

Para aprovechar la clasificación mediante `Comparator` en una `Column`, puedes utilizar el método `setComparator()`. Este método te permite definir una función de `Comparator` personalizada que dicta la lógica de clasificación.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

En el ejemplo anterior, se especifica una función de comparador personalizada que toma dos elementos (a y b) y define el orden de clasificación según los valores enteros analizados del atributo `Number`.

Usar Comparators para la clasificación de columnas es particularmente útil al manejar valores no numéricos. También son útiles para implementar algoritmos de clasificación complejos.

:::info
Por defecto, la `Table` utiliza clasificación del lado del servidor, y clasifica valores no primitivos utilizando el método `toString()` de Object, convirtiéndolos a sus valores de cadena y luego clasificándolos.
:::
