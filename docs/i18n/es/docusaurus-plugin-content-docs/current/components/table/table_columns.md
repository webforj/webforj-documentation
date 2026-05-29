---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: fbae9063370715e9f6dc2cb490a27511
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La clase `Table` utiliza instancias de columna para definir y personalizar cómo se muestran los datos. Las columnas controlan qué datos se muestran, cómo se ven y cómo los usuarios pueden interactuar con ellos. Esta página cubre la identidad de la columna, la presentación, el tamaño, las interacciones del usuario y los eventos relacionados.

## Identidad de la columna {#column-identity}

La identidad de una columna define cómo se reconoce en la `Table`. Esto incluye su etiqueta, el valor que proporciona y si es visible o navegable.

### Etiqueta {#label}

La etiqueta de una columna es su identificador público, ayudando a clarificar los datos mostrados.

Utiliza `setLabel()` para establecer o modificar la etiqueta.

:::tip
Por defecto, la etiqueta será la misma que el ID de la columna.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Proveedores de valor {#value-providers}

Un proveedor de valor es una función responsable de traducir datos en bruto del conjunto de datos subyacente a un formato adecuado para mostrarse dentro de una columna específica. La función, que defines, toma una instancia del tipo de datos de la fila (T) y devuelve el valor que se mostrará en la columna asociada para esa fila en particular.

Para establecer un proveedor de valor en una columna, utiliza uno de los métodos `addColumn()` del componente `Table`.

En el siguiente fragmento, una columna intentará acceder a datos de un objeto JSON, renderizándolo solo si los datos no son nulos.

```java
List<String> columnsList = List.of("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");
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

Es posible establecer la visibilidad de una columna, determinando si se mostrará o no dentro de la `Table`. Esto puede ser útil, entre otras cosas, para decidir si se debe mostrar información sensible.

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### Navegable {#navigable}

El atributo navegable determina si los usuarios pueden interactuar con una columna durante la navegación. Establecer `setSuppressNavigable()` en true restringe la interacción del usuario con la columna, proporcionando una experiencia de solo lectura.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Diseño y formato {#layout-and-formatting}

Después de establecer la identidad de una columna, el siguiente paso es controlar cómo aparece su contenido para los usuarios. Las opciones de diseño, como la alineación y el anclaje, determinan dónde se ubican los datos y cómo se mantienen visibles mientras trabajas con una `Table`.

### Alineación {#alignment}

Establecer la alineación de una columna te permite crear tablas organizadas, lo que puede ayudar a los usuarios a identificar las diferentes secciones de la `Table`.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnalignment'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

El componente `Table` admite tres opciones de alineación:

- `Column.Alignment.LEFT`: Adecuado para datos textuales o descriptivos donde mantener un flujo hacia la izquierda es intuitivo. Útil al enfatizar el punto de partida del contenido.
- `Column.Alignment.CENTER`: Las columnas centradas son ideales para valores más cortos, como una clave de carácter, estado o cualquier otra cosa que tenga una presentación equilibrada.
- `Column.Alignment.RIGHT`: Considera usar una columna alineada a la derecha para valores numéricos que sean útiles para escanear rápidamente, como fechas, montos y porcentajes.

En el ejemplo anterior, la última columna para `Cost` ha sido alineada a la derecha para proporcionar una distinción visual más obvia.

### Anclado {#pinning}

El anclaje de columnas es una función que permite a los usuarios fijar o "anclar" una columna a un lado específico de la `Table`. Esto es útil cuando ciertas columnas, como identificadores o información esencial, necesitan permanecer visibles mientras se desplazan horizontalmente a través de una tabla.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnpinning'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Hay tres direcciones disponibles para anclar una columna:

- `PinDirection.LEFT`: Ancla la columna al lado izquierdo.
- `PinDirection.RIGHT`: Ancla la columna al lado derecho.
- `PinDirection.AUTO`: La columna aparece según el orden de inserción.

El anclaje puede configurarse programáticamente, permitiéndote cambiar la dirección de anclaje según las interacciones del usuario o la lógica de la aplicación.

## Tamaño de columna <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Ancho fijo {#fixed-width}

Establece un ancho exacto para una columna utilizando el método `setWidth()`, especificando el ancho deseado en píxeles:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

La propiedad de ancho define el ancho inicial deseado para la columna. Cómo se utiliza este ancho depende de otras propiedades y el tipo de columna:

- **Columnas regulares**: Con solo el ancho establecido, la columna se representa en el ancho especificado pero puede reducirse proporcionalmente cuando el contenedor es demasiado pequeño. El ancho original sirve como el ancho deseado, pero sin restricciones mínimas explícitas, la columna puede representarse más pequeña que el ancho establecido.
- [**Columnas ancladas**](#pinning): Siempre mantienen su ancho exacto, nunca participan en la reducción responsiva.
- [**Columnas flexibles**](#flex-sizing): Establecer el ancho es incompatible con flex. Usa o ancho (fijo) o flex (proporcional), no ambos.

Si no se especifica, la columna utilizará su ancho estimado en función del análisis del contenido de las primeras filas.

```java
// Obtener ancho actual
float currentWidth = column.getWidth();
```

### Ancho mínimo {#minimum-width}

El método `setMinWidth()` te permite definir el ancho mínimo de una columna. Si no se proporciona el ancho mínimo, la `Table` calculará el ancho mínimo en función del contenido de la columna.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100f);
```

El valor pasado representa el ancho mínimo en píxeles.

La propiedad de ancho mínimo controla el ancho más pequeño que puede tener una columna:

- **Columnas regulares**: Con solo el ancho mínimo establecido, la columna utiliza el ancho mínimo como ancho deseado y mínimo. Con ancho + ancho mínimo, la columna puede reducirse del ancho hasta el ancho mínimo pero no más.
- [**Columnas ancladas**](#pinning): Si solo se establece el ancho mínimo (sin ancho), se convierte en el ancho fijo.
- [**Columnas flexibles**](#flex-sizing): Evita que la columna se reduzca por debajo de este ancho incluso cuando el espacio del contenedor es limitado.

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

La propiedad `maxWidth` limita el crecimiento de la columna para todos los tipos de columna y nunca será excedida independientemente del contenido, tamaño del contenedor o configuraciones de flex.

```java
// Obtener ancho máximo actual
float maxWidth = column.getMaxWidth();
```

### Tamaño flexible {#flex-sizing}

El método `setFlex()` habilita el tamaño proporcional de columnas, permitiendo que las columnas compartan el espacio disponible después de que se asignen las columnas de ancho fijo:

```java
// La columna del título obtiene el doble de espacio que la columna del artista
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Comportamientos clave de flex:

- **Valor de flex**: Determina la proporción del espacio disponible. Una columna con flex=2 obtiene el doble de espacio que una columna con flex=1.
- **Incompatible con ancho**: No se puede usar junto con la propiedad de ancho. Cuando flex es mayor que cero, tiene efecto sobre la configuración de ancho.
- **Respeta restricciones**: Funciona con las restricciones de ancho mínimo/ancho máximo. Sin ancho mínimo, las columnas flexibles pueden reducirse a 0.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnflexsizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

:::info Ancho vs Flex
Las propiedades de ancho y flex son mutuamente excluyentes. Establecer una automáticamente borra la otra. Usa ancho para control preciso o flex para comportamiento responsivo.
:::

### Tamaño automático {#automatic-sizing}

Más allá de las configuraciones manuales de ancho y flex, las columnas también se pueden dimensionar automáticamente. El tamaño automático permite que la `Table` determine los anchos óptimos analizando el contenido o distribuyendo el espacio de manera proporcional.

#### Tamaño automático basado en contenido {#content-based-auto-sizing}

Dimensiona automáticamente las columnas en función de su contenido. La `Table` analiza los datos en cada columna y calcula el ancho óptimo para mostrar el contenido sin truncamientos.

```java
// Tamaño automático de todas las columnas para ajustarse al contenido
table.setColumnsToAutoSize().thenAccept(c -> {
  // Dimensionamiento completo - las columnas ahora se ajustan a su contenido
});

// Tamaño automático de una columna específica
table.setColumnToAutoSize("description");
```

#### Ajuste proporcional automático {#proportional-auto-fit}

Distribuye todas las columnas proporcionalmente a lo ancho disponible de la `Table`. Esta operación establece cada columna en flex=1, haciendo que compartan el ancho total de la `Table` de manera equitativa, independientemente de la longitud de su contenido. Las columnas se expandirán o contraerán para llenar las dimensiones exactas de la `Table` sin espacio restante.

```java
// Ajustar columnas al ancho de la tabla (equivalente a establecer flex=1 en todas)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Todas las columnas ahora comparten espacio de manera equitativa
});
```

:::info Operaciones Asíncronas
Los métodos de tamaño automático devuelven `PendingResult<Void>` porque requieren cálculos del lado del cliente. Utiliza `thenAccept()` para ejecutar código después de que se complete el dimensionamiento. Si no necesitas esperar a la finalización, puedes llamar a los métodos sin `thenAccept()`
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnautosizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

## Interacciones del usuario <DocChip chip='since' label='25.03' /> {#user-interactions}

### Redimensionamiento de columnas {#column-resizing}

El redimensionamiento de columnas brinda a los usuarios control sobre cuánto espacio ocupa cada columna al arrastrar los bordes de la columna.

Puedes controlar el comportamiento de redimensionamiento en columnas individuales al crear tu tabla:

```java
// Habilitar redimensionamiento por parte del usuario para esta columna
table.addColumn("Title", Product::getTitle).setResizable(true);

// Deshabilitar redimensionamiento
table.addColumn("ID", Product::getId).setResizable(false);

// Ver estado actual
boolean canResize = column.isResizable();
```

Para tablas donde deseas un comportamiento consistente en múltiples columnas, utiliza los métodos de configuración masiva:

```java
// Hacer que todas las columnas existentes sean redimensionables
table.setColumnsToResizable(true);

// Bloquear todas las columnas existentes de redimensionamiento
table.setColumnsToResizable(false);
```

### Reordenamiento de columnas {#column-reordering}

El reordenamiento de columnas permite a los usuarios arrastrar y soltar columnas en su orden preferido, personalizando el diseño de la `Table` para su flujo de trabajo.

Configura los permisos de movimiento de columnas al configurar tu tabla:

```java
// Permitir a los usuarios mover esta columna
table.addColumn("Title", Product::getTitle).setMovable(true);

// Prevenir movimiento de columna (útil para columnas de ID o acción)
table.addColumn("ID", Product::getId).setMovable(false);

// Ver estado actual
boolean canMove = column.isMovable();
```

Aplica configuraciones de movimiento a múltiples columnas simultáneamente:

```java
// Habilitar reordenamiento para todas las columnas existentes
table.setColumnsToMovable(true);

// Deshabilitar reordenamiento para todas las columnas existentes  
table.setColumnsToMovable(false);
```

:::note Operaciones Masivas
Los métodos `setColumnsToResizable()` y `setColumnsToMovable()` solo afectan a las columnas existentes en el momento de la invocación. No establecen valores predeterminados para columnas futuras.
:::

### Movimiento programático de columnas {#programmatic-column-movement}

Además de arrastrar y soltar, también puedes reposicionar columnas programáticamente por índice o ID. Ten en cuenta que el índice se basa únicamente en columnas visibles; cualquier columna oculta se ignora al calcular posiciones.

```java
// Mover columna a la primera posición
table.moveColumn("title", 0);

// Mover columna a la última posición
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Movimiento asíncrono con callback
table.moveColumn("description", 2).thenAccept(c -> {
  // Columna movida exitosamente
});
```

## Manejo de eventos {#event-handling}

El componente `Table` emite eventos cuando los usuarios interactúan con las columnas, permitiéndote responder a cambios en el diseño y guardar preferencias del usuario.

Eventos soportados:

- `TableColumnResizeEvent`: Se dispara cuando un usuario redimensiona una columna arrastrando su borde.
- `TableColumnMoveEvent`: Se dispara cuando un usuario reordena una columna arrastrando su encabezado.

Puedes adjuntar oyentes a la `Table` para responder cuando los usuarios modifiquen el diseño de la tabla.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Manejar el evento de redimensionamiento de la columna
  // Acceso: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Manejar el evento de movimiento de columna  
  // Acceso: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
