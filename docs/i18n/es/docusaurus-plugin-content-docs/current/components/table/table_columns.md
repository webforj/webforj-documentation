---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 59dc1d0f2eff7880d818123654e8febf
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La clase `Table` utiliza instancias de columna para definir y personalizar cómo se muestran los datos. Las columnas controlan qué datos se muestran, cómo se ven y cómo los usuarios pueden interactuar con ellos. Esta página abarca la identidad de la columna, la presentación, el tamaño, las interacciones del usuario y eventos relacionados.

## Identidad de la columna {#column-identity}

La identidad de una columna define cómo se reconoce en la `Table`. Esto incluye su etiqueta, el valor que proporciona y si es visible o navegable.

### Etiqueta {#label}

La etiqueta de una columna es su identificador público, ayudando a aclarar los datos mostrados.

Utiliza `setLabel()` para establecer o modificar la etiqueta.

:::tip
Por defecto, la etiqueta será la misma que el ID de la columna.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Proveedores de valor {#value-providers}

Un proveedor de valor es una función responsable de traducir datos en bruto del conjunto de datos subyacente a un formato adecuado para mostrar dentro de una columna específica. La función que defines toma una instancia del tipo de datos de fila (T) y devuelve el valor que se mostrará en la columna asociada para esa fila en particular.

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

Es posible establecer la visibilidad de una columna, determinando si se mostrará o no dentro de la `Table`. Esto puede ser útil al determinar si se debe mostrar información sensible.

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### Navegable {#navigable}

El atributo navegable determina si los usuarios pueden interactuar con una columna durante la navegación. Establecer `setSuppressNavigable()` en true restringe la interacción del usuario con la columna, proporcionando una experiencia de solo lectura.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Diseño y formato {#layout-and-formatting}

Después de establecer la identidad de una columna, el siguiente paso es controlar cómo aparece su contenido a los usuarios. Las opciones de diseño, como la alineación y el anclaje, determinan dónde se sitúan los datos y cómo permanecen visibles mientras trabajas con una `Table`.

### Alineación {#alignment}

Establecer la alineación de una columna te permite crear tablas organizadas, lo que puede ayudar a los usuarios a identificar las diferentes secciones en la `Table`.

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

- `Column.Alignment.LEFT`: Adecuado para datos textuales o descriptivos donde mantener un flujo a la izquierda es intuitivo. Útil al enfatizar el punto de partida del contenido.
- `Column.Alignment.CENTER`: Las columnas centradas son ideales para valores más cortos, como una clave de carácter, estado o cualquier otra cosa que tenga una presentación equilibrada.
- `Column.Alignment.RIGHT`: Considera usar una columna alineada a la derecha para valores numéricos que son útiles para revisar rápidamente, como fechas, cantidades y porcentajes.

En el ejemplo anterior, la columna final para `Cost` se ha alineado a la derecha para proporcionar una distinción visual más obvia.

### Anclaje {#pinning}

El anclaje de columnas es una característica que permite a los usuarios fijar o "anclar" una columna a un lado específico de la `Table`. Esto es útil cuando ciertas columnas, como identificadores o información esencial, necesitan permanecer visibles mientras se desplazan horizontalmente a través de una tabla.

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

El anclaje se puede establecer programáticamente, permitiendo cambiar la dirección de anclaje según las interacciones del usuario o la lógica de la aplicación.

## Tamaño de la columna <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Ancho fijo {#fixed-width}

Establece un ancho exacto para una columna utilizando el método `setWidth()`, especificando el ancho deseado en píxeles:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

La propiedad de ancho define el ancho inicial deseado para la columna. Cómo se utiliza este ancho depende de otras propiedades y del tipo de columna:

- **Columnas normales**: Con solo ancho establecido, la columna se renderiza al ancho especificado, pero puede disminuir proporcionalmente cuando el contenedor es demasiado pequeño. El ancho original sirve como el ancho deseado, pero sin restricciones mínimas explícitas, la columna puede renderizarse más pequeña que el ancho establecido.
- [**Columnas ancladas**](#pinning): Siempre mantienen su ancho exacto, sin participar en la reducción responsiva.
- [**Columnas flexibles**](#flex-sizing): Establecer ancho es incompatible con flex. Utiliza ancho (fijo) o flex (proporcional), no ambos.

Si no se especifica, la columna utilizará su ancho estimado basado en el análisis de contenido de las primeras filas.

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

- **Columnas normales**: Con solo ancho mínimo establecido, la columna utiliza el ancho mínimo como tanto el ancho deseado como el mínimo. Con ancho + ancho mínimo, la columna puede disminuir desde el ancho hasta el ancho mínimo, pero no más.
- [**Columnas ancladas**](#pinning): Si solo se establece el ancho mínimo (sin ancho), se convierte en el ancho fijo.
- [**Columnas flexibles**](#flex-sizing): Impide que la columna se reduzca por debajo de este ancho incluso cuando el espacio del contenedor es limitado.

```java
// Obtener ancho mínimo actual
float minWidth = column.getMinWidth();
```

### Ancho máximo {#maximum-width}

El método `setMaxWidth()` limita cuán ancha puede crecer una columna, previniendo que columnas con contenido largo se vuelvan demasiado anchas y afecten la legibilidad:

```java
table.addColumn("Description", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

La propiedad `maxWidth` limita el crecimiento de la columna para todos los tipos de columna y nunca se excederá, independientemente del contenido, tamaño del contenedor o configuraciones de flex.

```java
// Obtener ancho máximo actual
float maxWidth = column.getMaxWidth();
```

### Tamaño flexible {#flex-sizing}

El método `setFlex()` habilita el tamaño proporcional de la columna, haciendo que las columnas compartan espacio disponible después de que se asignan las columnas de ancho fijo:

```java
// La columna de título obtiene el doble del espacio de la columna de artista
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Comportamientos clave de flex:

- **Valor de flex**: Determina la proporción del espacio disponible. Una columna con flex=2 obtiene el doble del espacio que una columna con flex=1.
- **Incompatible con ancho**: No se puede usar junto con la propiedad de ancho. Cuando flex es mayor que cero, tiene efecto sobre la configuración de ancho.
- **Respeta restricciones**: Funciona con restricciones de ancho mínimo/máximo. Sin ancho mínimo, las columnas flex pueden reducirse a 0.

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
Las propiedades de ancho y flex son mutuamente exclusivas. Establecer una automáticamente borra la otra. Usa ancho para control preciso o flex para un comportamiento responsivo.
:::

### Tamaño automático {#automatic-sizing}

Más allá de los ajustes manuales de ancho y flex, las columnas también se pueden dimensionar automáticamente. El tamaño automático permite que la `Table` determine los anchos óptimos, ya sea analizando contenido o distribuyendo espacio proporcionalmente.

#### Autoajuste basado en contenido {#content-based-auto-sizing}

Ajusta automáticamente el tamaño de las columnas según su contenido. La `Table` analiza los datos en cada columna y calcula el ancho óptimo para mostrar el contenido sin truncarlo.

```java
// Autoajustar todas las columnas para que quepan en el contenido
table.setColumnsToAutoSize().thenAccept(c -> {
    // Tamaño completo - las columnas ahora se ajustan a su contenido
});

// Autoajustar una columna específica
table.setColumnToAutoSize("description");
```

#### Ajuste automático proporcional {#proportional-auto-fit}

Distribuye todas las columnas proporcionalmente a lo largo del ancho disponible de la `Table`. Esta operación establece cada columna en flex=1, haciendo que compartan el ancho total de la `Table` de manera equitativa, independientemente de la longitud de su contenido. Las columnas se expandirán o contraerán para llenar las dimensiones exactas de la `Table` sin dejar espacio restante.

```java
// Ajustar columnas al ancho de la tabla (equivalente a establecer flex=1 en todas)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Todas las columnas ahora comparten espacio equitativamente
});
```

:::info Operaciones asíncronas
Los métodos de ajuste automático devuelven `PendingResult<Void>` porque requieren cálculos del lado del cliente. Usa `thenAccept()` para ejecutar código después de completar el ajuste. Si no necesitas esperar a que se complete, puedes llamar a los métodos sin `thenAccept()`
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
// Habilitar el redimensionamiento del usuario para esta columna
table.addColumn("Title", Product::getTitle).setResizable(true);

// Deshabilitar el redimensionamiento
table.addColumn("ID", Product::getId).setResizable(false);

// Comprobar el estado actual
boolean canResize = column.isResizable();
```

Para tablas donde deseas un comportamiento consistente en múltiples columnas, utiliza los métodos de configuración masiva:

```java
// Hacer todas las columnas existentes redimensionables
table.setColumnsToResizable(true);

// Bloquear todas las columnas existentes del redimensionamiento
table.setColumnsToResizable(false);
```

### Reordenamiento de columnas {#column-reordering}

El reordenamiento de columnas permite a los usuarios arrastrar y soltar columnas en su orden preferido, personalizando el diseño de la `Table` para su flujo de trabajo.

Configura los permisos de movimiento de columnas al configurar tu tabla:

```java
// Permitir a los usuarios mover esta columna
table.addColumn("Title", Product::getTitle).setMovable(true);

// Prevenir el movimiento de la columna (útil para columnas de ID o acción)
table.addColumn("ID", Product::getId).setMovable(false);

// Comprobar el estado actual
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
Los métodos `setColumnsToResizable()` y `setColumnsToMovable()` solo afectan a las columnas existentes en el momento de la invocación. No establecen valores predeterminados para columnas futuras.
:::

### Movimiento de columnas programático {#programmatic-column-movement} 

Además del arrastrar y soltar, también puedes reposicionar columnas programáticamente por índice o ID. Ten en cuenta que el índice se basa únicamente en las columnas visibles; cualquier columna oculta se ignora al calcular posiciones.

```java
// Mover la columna a la primera posición
table.moveColumn("title", 0);

// Mover la columna a la última posición
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Movimiento asíncrono con callback
table.moveColumn("description", 2).thenAccept(c -> {
    // Columna movida correctamente
});
```

## Manejo de eventos {#event-handling}

El componente `Table` emite eventos cuando los usuarios interactúan con columnas, permitiéndote responder a cambios de diseño y guardar preferencias del usuario.

Eventos soportados:

- `TableColumnResizeEvent`: Se dispara cuando un usuario redimensiona una columna arrastrando su borde.
- `TableColumnMoveEvent`: Se dispara cuando un usuario reordena una columna arrastrando su encabezado.

Puedes adjuntar escuchas al `Table` para responder cuando los usuarios modifican el diseño de la tabla.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Manejar evento de redimensionado de columna
  // Acceso: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Manejar evento de movimiento de columna  
  // Acceso: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
