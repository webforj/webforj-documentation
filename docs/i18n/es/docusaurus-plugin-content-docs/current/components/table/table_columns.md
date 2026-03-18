---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 19fe294c57ad6b7d105039c25aedab11
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La clase `Table` utiliza instancias de columnas para definir y personalizar cómo se muestran los datos. Las columnas controlan qué datos se muestran, cómo lucen y cómo los usuarios pueden interactuar con ellos. Esta página cubre la identidad de la columna, la presentación, el tamaño, las interacciones del usuario y los eventos relacionados.

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

Un proveedor de valor es una función responsable de traducir datos en bruto del conjunto de datos subyacente a un formato adecuado para mostrar dentro de una columna específica. La función, que defines, toma una instancia del tipo de datos de la fila (T) y devuelve el valor que se mostrará en la columna asociada para esa fila en particular.

Para establecer un proveedor de valor en una columna, utiliza uno de los métodos `addColumn()` del componente `Table`.

En el siguiente fragmento, una columna intentará acceder a datos de un objeto JSON, renderizándose solo si los datos no son nulos.

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

Es posible establecer la visibilidad de una columna, determinando si se mostrará o no dentro de la `Table`. Esto puede ser útil, entre otras cosas, al determinar si mostrar información sensible.

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### Navegable {#navigable}

El atributo navegable determina si los usuarios pueden interactuar con una columna durante la navegación. Configurar `setSuppressNavigable()` como verdadero restringe la interacción del usuario con la columna, proporcionando una experiencia de solo lectura.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Diseño y formato {#layout-and-formatting}

Después de establecer la identidad de una columna, el siguiente paso es controlar cómo aparece su contenido para los usuarios. Las opciones de diseño, como la alineación y la fijación, determinan dónde se sitúan los datos y cómo permanecen visibles mientras trabajas con una `Table`.

### Alineación {#alignment}

Configurar la alineación de una columna te permite crear tablas organizadas, lo que puede ayudar a los usuarios a identificar las diferentes secciones en la `Table`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

El componente `Table` admite tres opciones de alineación:

- `Column.Alignment.LEFT`: Adecuado para datos textuales o descriptivos donde mantener un flujo hacia la izquierda es intuitivo. Útil cuando se enfatiza el punto de partida del contenido.
- `Column.Alignment.CENTER`: Las columnas alineadas al centro son ideales para valores más cortos, como una clave de carácter, estado o cualquier otro dato que tenga una presentación equilibrada.
- `Column.Alignment.RIGHT`: Considera utilizar una columna alineada a la derecha para valores numéricos que sean útiles para escanear rápidamente, como fechas, montos y porcentajes.

En el ejemplo anterior, la última columna de `Cost` ha sido alineada a la derecha para proporcionar una distinción visual más obvia.

### Fijación {#pinning}

La fijación de columnas es una función que permite a los usuarios fijar o "anclar" una columna a un lado específico de la `Table`. Esto es útil cuando ciertas columnas, como identificadores o información esencial, necesitan permanecer visibles mientras se desplazan horizontalmente por una tabla.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Hay tres direcciones disponibles para fijar una columna:

- `PinDirection.LEFT`: Fija la columna al lado izquierdo.
- `PinDirection.RIGHT`: Fija la columna al lado derecho.
- `PinDirection.AUTO`: La columna aparece según el orden de inserción.

La fijación se puede establecer programáticamente, lo que te permite cambiar la dirección de fijación según las interacciones del usuario o la lógica de la aplicación.

## Tamaño de la columna <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Ancho fijo {#fixed-width}

Establece un ancho exacto para una columna utilizando el método `setWidth()`, especificando el ancho deseado en píxeles:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

La propiedad de ancho define el ancho inicial deseado para la columna. Cómo se utiliza este ancho depende de otras propiedades y el tipo de columna:

- **Columnas regulares**: Con solo el ancho establecido, la columna se renderiza al ancho especificado pero puede reducirse proporcionalmente cuando el contenedor es demasiado pequeño. El ancho original sirve como el ancho deseado, pero sin restricciones mínimas explícitas, la columna puede renderizarse más pequeña que el ancho establecido.
- [**Columnas fijadas**](#pinning): Siempre mantienen su ancho exacto, nunca participando en el encogimiento responsivo.
- [**Columnas flexibles**](#flex-sizing): Establecer el ancho es incompatible con el flex. Usa ya sea ancho (fijo) o flex (proporcional), no ambos.

Si no se especifica, la columna utilizará su ancho estimado basado en el análisis del contenido de las primeras filas.

```java
// Obtener ancho actual
float currentWidth = column.getWidth();
```

### Ancho mínimo {#minimum-width}

El método `setMinWidth()` te permite definir el ancho mínimo de una columna. Si no se proporciona el ancho mínimo, la `Table` calculará el ancho mínimo basado en el contenido de la columna.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100f);
```

El valor pasado representa el ancho mínimo en píxeles.

La propiedad de ancho mínimo controla el ancho más pequeño que puede tener una columna:

- **Columnas regulares**: Con solo el ancho mínimo establecido, la columna utiliza el ancho mínimo como ancho deseado y mínimo. Con ancho + ancho mínimo, la columna puede reducirse desde el ancho hasta el ancho mínimo, pero no más.
- [**Columnas fijadas**](#pinning): Si solo se establece el ancho mínimo (sin ancho), se convierte en el ancho fijo.
- [**Columnas flexibles**](#flex-sizing): Previene que la columna se reduzca por debajo de este ancho incluso cuando el espacio del contenedor es limitado.

```java
// Obtener ancho mínimo actual
float minWidth = column.getMinWidth();
```

### Ancho máximo {#maximum-width}

El método `setMaxWidth()` limita cuánto puede crecer una columna, evitando que columnas con contenido largo se vuelvan demasiado anchas y afecten la legibilidad:

```java
table.addColumn("Description", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

La propiedad `maxWidth` limita el crecimiento de la columna para todos los tipos de columnas y nunca se excederá independientemente del contenido, tamaño del contenedor o configuraciones de flex.

```java
// Obtener ancho máximo actual
float maxWidth = column.getMaxWidth();
```

### Tamaño flexible {#flex-sizing}

El método `setFlex()` habilita el dimensionamiento proporcional de las columnas, haciendo que las columnas compartan el espacio disponible después de que se hayan asignado las columnas de ancho fijo:

```java
// La columna de Título obtiene el doble del espacio de la columna de Artista
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Comportamientos clave del flex:

- **Valor de flex**: Determina la proporción del espacio disponible. Una columna con `flex=2` obtiene el doble del espacio de una columna con `flex=1`.
- **Incompatible con ancho**: No se puede usar junto con la propiedad de ancho. Cuando el flex es mayor que cero, tiene efecto sobre la configuración de ancho.
- **Respeta restricciones**: Funciona con las restricciones de ancho mínimo/ancho máximo. Sin ancho mínimo, las columnas flexibles pueden reducirse a 0.

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
Las propiedades de ancho y flex son mutuamente excluyentes. Establecer una automáticamente elimina la otra. Utiliza el ancho para un control preciso o flex para un comportamiento responsivo.
:::

### Dimensionamiento automático {#automatic-sizing}

Más allá de los ajustes de ancho manual y flex, las columnas también pueden dimensionarse automáticamente. El dimensionamiento automático permite que la `Table` determine los anchos óptimos ya sea analizando el contenido o distribuyendo el espacio proporcionalmente.

#### Dimensionamiento automático basado en contenido {#content-based-auto-sizing}

Dimensiona automáticamente las columnas según su contenido. La `Table` analiza los datos en cada columna y calcula el ancho óptimo para mostrar el contenido sin truncamiento.

```java
// Ajustar automáticamente todos los columnas para que se ajusten al contenido
table.setColumnsToAutoSize().thenAccept(c -> {
  // Dimensionamiento completo - las columnas ahora se ajustan a su contenido
});

// Ajustar automáticamente una columna específica
table.setColumnToAutoSize("description");
```

#### Ajuste proporcional automático {#proportional-auto-fit}

Distribuye todas las columnas de manera proporcional a lo largo del ancho disponible de la `Table`. Esta operación establece cada columna en `flex=1`, haciendo que compartan el ancho total de la `Table` por igual, independientemente de la longitud de su contenido. Las columnas se expandirán o contraerán para llenar exactamente las dimensiones de la `Table` sin espacio restante.

```java
// Ajustar columnas al ancho de la tabla (equivalente a establecer flex=1 en todas)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Todas las columnas ahora comparten espacio por igual
});
```

:::info Operaciones Asíncronas
Los métodos de dimensionamiento automático devuelven `PendingResult<Void>` porque requieren cálculos del lado del cliente. Usa `thenAccept()` para ejecutar código después de que se complete el dimensionamiento. Si no necesitas esperar a que se complete, puedes llamar a los métodos sin `thenAccept()`
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

## Interacciones del usuario <DocChip chip='since' label='25.03' /> {#user-interactions}

### Redimensionamiento de columnas {#column-resizing}

El redimensionamiento de columnas le da a los usuarios control sobre cuánto espacio ocupa cada columna arrastrando los bordes de la columna.

Puedes controlar el comportamiento de redimensionamiento en columnas individuales al construir tu tabla:

```java
// Habilitar redimensionamiento de usuario para esta columna
table.addColumn("Title", Product::getTitle).setResizable(true);

// Deshabilitar el redimensionamiento
table.addColumn("ID", Product::getId).setResizable(false);

// Comprobar estado actual
boolean canResize = column.isResizable();
```

Para tablas donde deseas un comportamiento coherente en múltiples columnas, utiliza los métodos de configuración masiva:

```java
// Hacer que todas las columnas existentes sean redimensionables
table.setColumnsToResizable(true);

// Bloquear todas las columnas existentes de ser redimensionadas
table.setColumnsToResizable(false);
```

### Reordenamiento de columnas {#column-reordering}

El reordenamiento de columnas permite a los usuarios arrastrar y soltar columnas en su orden preferido, personalizando el diseño de la `Table` para su flujo de trabajo.

Configura los permisos de movimiento de columnas al configurar tu tabla:

```java
// Permitir que los usuarios muevan esta columna
table.addColumn("Title", Product::getTitle).setMovable(true);

// Evitar movimiento de columna (útil para ID o columnas de acción)
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

:::note Operaciones Masivas
Los métodos `setColumnsToResizable()` y `setColumnsToMovable()` solo afectan a las columnas existentes en el momento de la invocación. No establecen valores predeterminados para futuras columnas.
:::

### Movimiento de columnas programático {#programmatic-column-movement} 

Además de arrastrar y soltar, también puedes reposicionar columnas programáticamente por índice o ID. Ten en cuenta que el índice se basa solo en columnas visibles; cualquier columna oculta se ignora al calcular posiciones.

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

Puedes adjuntar oyentes al `Table` para responder cuando los usuarios modifiquen el diseño de la tabla.

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
