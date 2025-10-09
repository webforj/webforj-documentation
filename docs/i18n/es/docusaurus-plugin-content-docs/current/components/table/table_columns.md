---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 280a70bb65c45d3b200157f3462d7b10
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La clase `Table` utiliza instancias de columnas para definir y personalizar cómo se muestra la información. Las columnas controlan qué datos se muestran, cómo lucen y cómo los usuarios pueden interactuar con ellos. Esta página cubre la identidad de las columnas, la presentación, el tamaño, las interacciones de los usuarios y eventos relacionados.

## Identidad de la columna {#column-identity}

La identidad de una columna define cómo se reconoce en el `Table`. Esto incluye su etiqueta, el valor que proporciona y si es visible o navegable.

### Etiqueta {#label}

La etiqueta de una columna es su identificador visible al público, ayudando a aclarar los datos mostrados.

Utiliza `setLabel()` para establecer o modificar la etiqueta.

:::tip
Por defecto, la etiqueta será la misma que el ID de la columna.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Proveedores de valor {#value-providers}

Un proveedor de valor es una función responsable de traducir datos en bruto del conjunto de datos subyacente a un formato adecuado para mostrar dentro de una columna específica. La función, que defines, toma una instancia del tipo de dato de la fila (T) y devuelve el valor que se mostrará en la columna asociada para esa fila en particular.

Para establecer un proveedor de valor en una columna, utiliza uno de los métodos `addColumn()` del componente `Table`.

En el siguiente fragmento, una columna intentará acceder a datos de un objeto JSON, renderizándolo solo si los datos no son nulos.

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

### Visibilidad {#visibility}

Es posible establecer la visibilidad de una columna, determinando si se mostrará o no dentro del `Table`. Esto puede ser útil al decidir si mostrar o no información sensible.

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### Navegable {#navigable}

El atributo navegable determina si los usuarios pueden interactuar con una columna durante la navegación. Establecer `setSuppressNavigable()` en verdadero restringe la interacción del usuario con la columna, proporcionando una experiencia de solo lectura.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Diseño y formato {#layout-and-formatting}

Después de establecer la identidad de una columna, el siguiente paso es controlar cómo aparece su contenido a los usuarios. Las opciones de diseño como la alineación y el anclaje determinan dónde se sitúa la información y cómo se mantiene visible mientras trabajas con un `Table`.

### Alineación {#alignment}

Establecer la alineación de una columna te permite crear tablas organizadas, lo que puede ayudar a los usuarios a identificar las diferentes secciones en el `Table`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

El componente `Table` soporta tres opciones de alineación:

- `Column.Alignment.LEFT`: Adecuado para datos textuales o descriptivos donde mantener un flujo hacia la izquierda es intuitivo. Útil al enfatizar el punto de inicio del contenido.
- `Column.Alignment.CENTER`: Las columnas centradas son ideales para valores más cortos, como una clave de carácter, estado, o cualquier otro que tenga una presentación balanceada.
- `Column.Alignment.RIGHT`: Considera usar una columna alineada a la derecha para valores numéricos que son útiles para revisar rápidamente, como fechas, cantidades y porcentajes.

En el ejemplo anterior, la columna final de `Cost` se ha alineado a la derecha para proporcionar una distinción visual más obvia.

### Anclaje {#pinning}

El anclaje de columnas es una característica que permite a los usuarios fijar o "anclar" una columna a un lado específico del `Table`. Esto es útil cuando ciertas columnas, como identificadores o información esencial, necesitan permanecer visibles mientras se desplaza horizontalmente a través de una tabla.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Hay tres direcciones disponibles para anclar una columna:

- `PinDirection.LEFT`: Ancla la columna al lado izquierdo.
- `PinDirection.RIGHT`: Ancla la columna al lado derecho.
- `PinDirection.AUTO`: La columna aparece según el orden de inserción.

El anclaje se puede establecer programáticamente, lo que te permite cambiar la dirección de anclaje en función de las interacciones del usuario o por la lógica de la aplicación.

## Tamaño de la columna <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Ancho fijo {#fixed-width}

Establece un ancho exacto para una columna utilizando el método `setWidth()`, especificando el ancho deseado en píxeles:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

La propiedad de ancho define el ancho inicial deseado para la columna. Cómo se utiliza este ancho depende de otras propiedades y del tipo de columna:

- **Columnas regulares**: Con solo el ancho establecido, la columna renderiza al ancho especificado pero puede reducirse proporcionalmente cuando el contenedor es demasiado pequeño. El ancho original sirve como el ancho deseado, pero sin restricciones mínimas explícitas, la columna puede renderizarse más pequeña que el ancho establecido.
- [**Columnas ancladas**](#pinning): Siempre mantienen su ancho exacto, nunca participando en un encogimiento responsivo.
- [**Columnas flexibles**](#flex-sizing): Establecer ancho es incompatible con flex. Usa ancho (fijo) o flex (proporcional), no ambos.

Si no se especifica, la columna utilizará su ancho estimado basado en el análisis de contenido de las primeras filas.

```java
// Obtener ancho actual
float currentWidth = column.getWidth();
```

### Ancho mínimo {#minimum-width}

El método `setMinWidth()` te permite definir el ancho mínimo de una columna. Si no se proporciona un ancho mínimo, el `Table` calculará el ancho mínimo basado en el contenido de la columna.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100f);
```

El valor pasado representa el ancho mínimo en píxeles.

La propiedad de ancho mínimo controla el menor ancho que puede tener una columna:

- **Columnas regulares**: Con solo el ancho mínimo establecido, la columna utiliza el ancho mínimo como tanto el ancho deseado como el mínimo. Con ancho + ancho mínimo, la columna puede reducirse desde el ancho hasta el ancho mínimo, pero no más.
- [**Columnas ancladas**](#pinning): Si solo se establece el ancho mínimo (sin ancho), se convierte en el ancho fijo.
- [**Columnas flexibles**](#flex-sizing): Impide que la columna se reduzca por debajo de este ancho incluso cuando el espacio del contenedor es limitado.

```java
// Obtener ancho mínimo actual
float minWidth = column.getMinWidth();
```

### Ancho máximo {#maximum-width}

El método `setMaxWidth()` limita cuán ancha puede crecer una columna, evitando que las columnas con contenido largo se vuelvan demasiado anchas y afecten la legibilidad:

```java
table.addColumn("Description", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

La propiedad `maxWidth` limita el crecimiento de la columna para todos los tipos de columna y nunca se superará independientemente del contenido, el tamaño del contenedor o la configuración de flex.

```java
// Obtener ancho máximo actual
float maxWidth = column.getMaxWidth();
```

### Tamaño flexible {#flex-sizing}

El método `setFlex()` permite un tamaño de columna proporcional, haciendo que las columnas compartan espacio disponible después de que se hayan asignado las columnas de ancho fijo:

```java
// La columna de título recibe el doble de espacio que la columna de artista
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Comportamientos clave de flex:

- **Valor flex**: Determina la proporción de espacio disponible. Una columna con flex=2 obtiene el doble de espacio que una columna con flex=1.
- **Incompatible con ancho**: No se puede usar junto con la propiedad de ancho. Cuando flex es mayor que cero, tiene efecto sobre la configuración de ancho.
- **Respeta las restricciones**: Funciona con las restricciones de ancho mínimo/ancho máximo. Sin ancho mínimo, las columnas flexibles pueden reducirse a 0.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnflexsizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

:::info Ancho vs Flex
Las propiedades de ancho y flex son mutuamente excluyentes. Establecer una automáticamente limpia la otra. Usa ancho para un control preciso o flex para un comportamiento responsivo.
:::

### Tamaño automático {#automatic-sizing}

Más allá de los ajustes manuales de ancho y flex, las columnas también se pueden dimensionar automáticamente. El tamaño automático permite que el `Table` determine los anchos óptimos mediante el análisis del contenido o distribuyendo el espacio de manera proporcional.

#### Tamaño automático basado en contenido {#content-based-auto-sizing}

Dimensiona automáticamente las columnas según su contenido. El `Table` analiza los datos en cada columna y calcula el ancho óptimo para mostrar el contenido sin truncamiento.

```java
// Tamaño automático para todas las columnas para que se ajusten al contenido
table.setColumnsToAutoSize().thenAccept(c -> {
    // Dimensionamiento completo - las columnas ahora se ajustan a su contenido
});

// Tamaño automático para una columna específica
table.setColumnToAutoSize("description");
```

#### Ajuste automático proporcional {#proportional-auto-fit}

Distribuye todas las columnas de forma proporcional a lo largo del ancho disponible del `Table`. Esta operación establece cada columna a flex=1, haciendo que compartan el ancho total del `Table` de manera equitativa, independientemente de la longitud de su contenido. Las columnas se expandirán o contraerán para llenar exactamente las dimensiones del `Table` sin espacio restante.

```java
// Ajustar columnas al ancho de la tabla (equivalente a establecer flex=1 en todas)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Todas las columnas ahora comparten espacio de manera equitativa
});
```

:::info Operaciones asíncronas
Los métodos de ajuste automático devuelven `PendingResult<Void>` porque requieren cálculos del lado del cliente. Usa `thenAccept()` para ejecutar código después de que se complete el dimensionado. Si no necesitas esperar a que se complete, puedes llamar a los métodos sin `thenAccept()`
:::

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnautosizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

## Interacciones de usuario <DocChip chip='since' label='25.03' /> {#user-interactions}

### Redimensionamiento de columnas {#column-resizing}

El redimensionamiento de columnas permite a los usuarios controlar cuánto espacio ocupa cada columna arrastrando los bordes de la columna.

Puedes controlar el comportamiento de redimensionamiento en columnas individuales al construir tu tabla:

```java
// Habilitar redimensionamiento del usuario para esta columna
table.addColumn("Title", Product::getTitle).setResizable(true);

// Deshabilitar redimensionamiento
table.addColumn("ID", Product::getId).setResizable(false);

// Comprobar estado actual
boolean canResize = column.isResizable();
```

Para tablas donde desees un comportamiento consistente en múltiples columnas, usa los métodos de configuración masiva:

```java
// Hacer que todas las columnas existentes sean redimensionables
table.setColumnsToResizable(true);

// Bloquear todas las columnas existentes de redimensionarse
table.setColumnsToResizable(false);
```

### Reordenamiento de columnas {#column-reordering}

El reordenamiento de columnas permite a los usuarios arrastrar y soltar columnas en su orden preferido, personalizando el diseño del `Table` para su flujo de trabajo.

Configura los permisos de movimiento de columnas al configurar tu tabla:

```java
// Permitir que los usuarios muevan esta columna
table.addColumn("Title", Product::getTitle).setMovable(true);

// Prevenir el movimiento de la columna (útil para columnas de ID o de acción)
table.addColumn("ID", Product::getId).setMovable(false);

// Comprobar estado actual
boolean canMove = column.isMovable();
```

Aplica configuraciones de movimiento a múltiples columnas simultáneamente:

```java
// Habilitar reordenamiento para todas las columnas existentes
table.setColumnsToMovable(true);

// Deshabilitar reordenamiento para todas las columnas existentes  
table.setColumnsToMovable(false);
```

:::note Operaciones masivas
Los métodos `setColumnsToResizable()` y `setColumnsToMovable()` solo afectan a las columnas existentes en el momento de la invocación. No establecen valores predeterminados para las futuras columnas.
:::

### Movimiento programático de columnas {#programmatic-column-movement} 

Además del arrastre y la suelta, también puedes reposicionar columnas programáticamente por índice o ID. Ten en cuenta que el índice se basa solo en las columnas visibles; cualquier columna oculta se ignora al calcular posiciones.

```java
// Mover columna a la primera posición
table.moveColumn("title", 0);

// Mover columna a la última posición
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Movimiento asíncrono con callback
table.moveColumn("description", 2).thenAccept(c -> {
    // Columna movida con éxito
});
```

## Manejo de eventos {#event-handling}

El componente `Table` emite eventos cuando los usuarios interactúan con las columnas, permitiéndote responder a cambios en el diseño y guardar preferencias de usuario.

Eventos soportados:

- `TableColumnResizeEvent`: Se dispara cuando un usuario redimensiona una columna arrastrando su borde.
- `TableColumnMoveEvent`: Se dispara cuando un usuario reordena una columna arrastrando su encabezado.

Puedes adjuntar oyentes al `Table` para responder cuando los usuarios modifican el diseño de la tabla.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Manejar evento de redimensionamiento de columna
  // Acceso: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Manejar evento de movimiento de columna  
  // Acceso: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
