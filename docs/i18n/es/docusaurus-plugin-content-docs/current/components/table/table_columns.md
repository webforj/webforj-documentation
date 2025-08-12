---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 5ebe30c35548db6d3b603b8a72ed4c2a
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La clase `Table` utiliza clases `Column` para manejar la creación de columnas de datos dentro de ella. Esta clase tiene una amplia gama de funcionalidades que permiten al usuario personalizar lo que se muestra en cada una de las columnas. 
Para agregar una `Column` a una `Table`, use uno de los métodos de fábrica `addColumn`.

## Proveedores de valor {#value-providers}

Un proveedor de valor es una función responsable de traducir datos en bruto del conjunto de datos subyacente a un formato adecuado para su visualización dentro de una columna específica. La función, definida por el usuario, toma una instancia del tipo de datos de la fila (T) y devuelve el valor que se mostrará en la columna asociada para esa fila particular.

Para establecer un proveedor de valor en una columna, use uno de los métodos de fábrica `addColumn` que aceptan un proveedor como argumento:

```java
    List<String> columnsList = Arrays.asList("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");

    for (String column : columnsList) {
      table.addColumn(column, (JsonObject person) -> {
        JsonElement element = person.get(column);
        if (!element.isJsonNull()) {
          return element.getAsString();
        }
        return "";
      });
    }
```

En este ejemplo, una columna intentará acceder a datos de un objeto JSON, renderizándolos solo si los datos no son nulos.

## Dirección de fijación {#pin-direction}

La fijación de columnas es una característica que permite a los usuarios fijar o "anclar" una columna a un lado específico de la tabla, mejorando la visibilidad y accesibilidad. Esto es útil cuando ciertas columnas, como identificadores o información esencial, necesitan permanecer visibles mientras se desplazan horizontalmente a través de una tabla.

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Hay tres direcciones disponibles para fijar una columna:

- `PinDirection.LEFT`: Fija la columna al lado izquierdo.
- `PinDirection.RIGHT`: Fija la columna al lado derecho.
- `PinDirection.AUTO`: La columna aparece según el orden de inserción.

La fijación se puede establecer programáticamente, permitiendo a los usuarios cambiar la dirección de fijación según las interacciones del usuario o la lógica de la aplicación.

## Alineación {#alignment}

La alineación de columnas define la posición horizontal de los datos dentro de una columna. Influye en cómo se muestran los valores de los datos, proporcionando una guía visual a los usuarios sobre la naturaleza de la información.

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

El componente de la tabla admite tres opciones principales de alineación:

- `Column.Alignment.LEFT`: Adecuado para datos textuales o descriptivos donde mantener un flujo hacia la izquierda es intuitivo. Útil cuando se enfatiza el punto de partida del contenido.
- `Column.Alignment.CENTER`: Ideal para datos numéricos o categóricos donde se desea una presentación equilibrada. Crea una visualización centrada.
- `Column.Alignment.RIGHT`: Comúnmente utilizado para datos numéricos, especialmente cuando la magnitud o la precisión de los números es significativa. Alinea los datos hacia la derecha para un flujo de lectura natural.

En el ejemplo anterior, la columna final de `Cost` se ha alineado a la derecha para proporcionar una distinción visual más obvia.

## Visibilidad {#visibility}

Es posible establecer la visibilidad de una `Column`, determinando si se mostrará o no dentro de la tabla. Esto puede ser útil, entre otras cosas, para determinar si se debe mostrar información sensible.

Use el método `setHidden()`, como se muestra a continuación, para establecer esta propiedad en una `Column`:

`table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);`

## Navegable {#navigable}

El atributo navegable determina si los usuarios pueden interactuar con una columna durante la navegación. Establecer `setSuppressNavigable()` en verdadero restringe la interacción del usuario con la columna, proporcionando una experiencia de solo lectura.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Etiqueta {#label}

La etiqueta de una columna es su identificador público, contribuyendo a la claridad y comprensión de los datos mostrados. Use `setLabel` para establecer o modificar la etiqueta asociada con una columna.

:::tip
Por defecto, la etiqueta será la misma que el ID de la columna
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

## Ancho mínimo {#minimum-width}

El método `setMinWidth()` permite definir el ancho mínimo de una columna, asegurando un diseño consistente y estéticamente agradable.

Si no se proporciona el ancho mínimo, la tabla calculará el ancho mínimo en función del contenido de la columna.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100.0);
```

:::info
El valor pasado representa el ancho mínimo deseado en píxeles.  
:::
