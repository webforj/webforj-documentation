---
sidebar_position: 5
title: Columns
slug: columns
description: >-
  Define Table columns with labels, value providers, visibility, navigability,
  sizing, and user-interaction events.
_i18n_hash: 5ca9d9959c258db42780e52dad8c463d
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La clase `Table` utiliza instancias de columnas para definir y personalizar la forma en que se visualizan los datos. Las columnas controlan qué datos se muestran, cómo se ven y cómo pueden interactuar los usuarios con ellos. Esta página cubre la identidad de la columna, la presentación, el tamaño, las interacciones del usuario y los eventos relacionados.

## Identidad de columna {#column-identity}

La identidad de una columna define cómo se reconoce en la `Table`. Esto incluye su etiqueta, el valor que proporciona y si es visible o navegable.

### Etiqueta {#label}

La etiqueta de una columna es su identificador público, lo que ayuda a clarificar los datos mostrados.

Utiliza `setLabel()` para establecer o modificar la etiqueta.

:::tip
Por defecto, la etiqueta será la misma que el ID de la columna.
:::

```java
table.addColumn("ID del Producto", Product::getProductId).setLabel("ID");
```

### Proveedores de valor {#value-providers}

Un proveedor de valor es una función responsable de traducir datos en bruto del conjunto de datos subyacente a un formato adecuado para la visualización dentro de una columna específica. La función, que defines, toma una instancia del tipo de datos de la fila (T) y devuelve el valor que se mostrará en la columna asociada para esa fila en particular.

Para establecer un proveedor de valor en una columna, utiliza uno de los métodos `addColumn()` del componente `Table`.

En el siguiente fragmento, una columna intentará acceder a datos de un objeto JSON, renderizándolo solo si los datos no son nulos.

```java
List<String> columnsList = List.of("atleta", "edad", "país", "año", "deporte", "oro", "plata", "bronce", "total");
for (String column : columnsList) {
  table.addColumn(column, (JsonObject persona) -> {
    JsonElement elemento = persona.get(column);
    if (!elemento.isJsonNull()) {
      return elemento.getAsString();
    }
    return "";
  });
}
```

### Visibilidad {#visibility}

Es posible establecer la visibilidad de una columna, determinando si se mostrará o no dentro de la `Table`. Esto puede ser útil cuando, entre otras cosas, se decide si se debe mostrar información sensible.

```java
table.addColumn("Número de Tarjeta de Crédito", Customer::getCreditCardNumber).setHidden(true);
```

### Navegable {#navigable}

El atributo navegable determina si los usuarios pueden interactuar con una columna durante la navegación. Configurar `setSuppressNavigable()` en verdadero restringe la interacción del usuario con la columna, proporcionando una experiencia de solo lectura.

```java
table.addColumn("Columna Solo Lectura", Product::getDescription).setSuppressNavigable(true);
```

## Diseño y formato {#layout-and-formatting}

Después de establecer la identidad de una columna, el siguiente paso es controlar cómo aparece su contenido para los usuarios. Las opciones de diseño, como la alineación y el anclaje, determinan dónde se coloca el dato y cómo se mantiene visible al trabajar con una `Table`.

### Alineación {#alignment}

Establecer la alineación de una columna te permite crear tablas organizadas, lo que puede ayudar a los usuarios a identificar las diferentes secciones en la `Table`.

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
- `Column.Alignment.RIGHT`: Considera usar una columna alineada a la derecha para valores numéricos que son útiles para escanear rápidamente, como fechas, montos y porcentajes.

En el ejemplo anterior, la columna final para `Costo` se ha alineado a la derecha para proporcionar una distinción visual más obvia.

### Anclaje {#pinning}

El anclaje de columnas es una función que permite a los usuarios fijar o "anclar" una columna a un lado específico de la `Table`. Esto es útil cuando ciertas columnas, como identificadores o información esencial, necesitan permanecer visibles mientras se desplaza horizontalmente a través de una tabla.

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

El anclaje se puede establecer programáticamente, lo que te permite cambiar la dirección de anclaje según las interacciones del usuario o la lógica de la aplicación.

## Tamaño de columna <DocChip chip='since' label='25.03' /> {#column-sizing}

### Ancho fijo {#fixed-width}

Establece un ancho exacto para una columna utilizando el método `setWidth()`, especificando el ancho deseado en píxeles:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

La propiedad de ancho define el ancho inicial deseado para la columna. Cómo se utiliza este ancho depende de otras propiedades y el tipo de columna:

- **Columnas regulares**: Con solo el ancho establecido, la columna se renderiza al ancho especificado pero puede reducirse proporcionalmente cuando el contenedor es demasiado pequeño. El ancho original sirve como el ancho deseado, pero sin restricciones mínimas explícitas, la columna puede renderizarse más pequeña que el ancho establecido.
- [**Columnas ancladas**](#pinning): Siempre mantienen su ancho exacto, nunca participando en la reducción responsiva.
- [**Columnas flexibles**](#flex-sizing): Establecer el ancho es incompatible con flex. Usa ancho (fijo) o flex (proporcional), no ambos.

Si no se especifica, la columna utilizará su ancho estimado basado en el análisis de contenido de las primeras filas.

```java
// Obtener ancho actual
float currentWidth = column.getWidth();
```

### Ancho mínimo {#minimum-width}

El método `setMinWidth()` te permite definir el ancho mínimo de una columna. Si no se proporciona un ancho mínimo, la `Table` calculará el ancho mínimo basado en el contenido de la columna.

```java
table.addColumn("Precio", Product::getPrice).setMinWidth(100f);
```

El valor pasado representa el ancho mínimo en píxeles.

La propiedad de ancho mínimo controla el ancho más pequeño que puede tener una columna:

- **Columnas regulares**: Con solo el ancho mínimo establecido, la columna utiliza el ancho mínimo como tanto el ancho deseado como mínimo. Con ancho + ancho mínimo, la columna puede reducirse desde el ancho hasta el ancho mínimo pero no más allá.
- [**Columnas ancladas**](#pinning): Si solo se establece el ancho mínimo (sin ancho), se convierte en el ancho fijo.
- [**Columnas flexibles**](#flex-sizing): Impide que la columna se reduzca por debajo de este ancho incluso cuando el espacio en el contenedor es limitado.

```java
// Obtener ancho mínimo actual
float minWidth = column.getMinWidth();
```

### Ancho máximo {#maximum-width}

El método `setMaxWidth()` limita cuán ancho puede crecer una columna, evitando que columnas con contenido largo se vuelvan demasiado anchas y afecten la legibilidad:

```java
table.addColumn("Descripción", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

La propiedad `maxWidth` limita el crecimiento de la columna para todos los tipos de columnas y nunca se excederá independientemente del contenido, tamaño del contenedor o configuraciones de flex.

```java
// Obtener ancho máximo actual
float maxWidth = column.getMaxWidth();
```

### Tamaño flexible {#flex-sizing}

El método `setFlex()` habilita el tamaño de columnas proporcional, haciendo que las columnas compartan el espacio disponible después de que se asignan las columnas de ancho fijo:

```java
// La columna de Título obtiene el doble del espacio de la columna de Artista
table.addColumn("Título", Product::getTitle).setFlex(2f);
table.addColumn("Artista", Product::getArtist).setFlex(1f);
```

Comportamientos clave de flex:

- **Valor de flex**: Determina la proporción de espacio disponible. Una columna con flex=2 obtiene el doble del espacio de una columna con flex=1.
- **Incompatible con el ancho**: No se puede usar junto con la propiedad de ancho. Cuando flex es mayor que cero, tiene efecto sobre la configuración de ancho.
- **Respeta restricciones**: Funciona con restricciones de ancho mínimo/máximo. Sin un ancho mínimo, las columnas flexibles pueden reducirse a 0.

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
Las propiedades de ancho y flex son mutuamente excluyentes. Establecer una automáticamente anula la otra. Usa ancho para un control preciso o flex para un comportamiento responsivo.
:::

### Tamaño automático {#automatic-sizing}

Más allá de los ajustes manuales de ancho y flex, las columnas también pueden dimensionarse automáticamente. El tamaño automático permite a la `Table` determinar los anchos óptimos ya sea analizando el contenido o distribuyendo espacio proporcionalmente.

#### Autoajuste basado en contenido {#content-based-auto-sizing}

Dimensiona automáticamente las columnas basándose en su contenido. La `Table` analiza los datos en cada columna y calcula el ancho óptimo para mostrar el contenido sin truncamiento.

```java
// Autoajustar todas las columnas para que se ajusten al contenido
table.setColumnsToAutoSize().thenAccept(c -> {
  // Dimensionamiento completo - las columnas ahora se ajustan a su contenido
});

// Autoajustar columna específica
table.setColumnToAutoSize("descripción");
```

#### Ajuste proporcional automático {#proportional-auto-fit}

Distribuye todas las columnas proporcionalmente a través del ancho disponible de la `Table`. Esta operación establece cada columna en flex=1, haciendo que compartan el total del ancho de la `Table` de manera equitativa, independientemente de la longitud de su contenido. Las columnas se expandirán o contraerán para llenar exactamente las dimensiones de la `Table` sin espacio restante.

```java
// Ajustar columnas al ancho de la tabla (equivalente a establecer flex=1 en todas)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Todas las columnas ahora comparten el espacio de manera equitativa
});
```

:::info Operaciones Asíncronas
Los métodos de autoajuste devuelven `PendingResult<Void>` porque requieren cálculos en el lado del cliente. Usa `thenAccept()` para ejecutar código después de que se complete el ajuste. Si no necesitas esperar la finalización, puedes llamar a los métodos sin `thenAccept()`
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

El redimensionamiento de columnas le da a los usuarios control sobre cuánto espacio ocupa cada columna arrastrando los bordes de la columna.

Puedes controlar el comportamiento de redimensionamiento en columnas individuales al construir tu tabla:

```java
// Habilitar redimensionamiento por parte del usuario para esta columna
table.addColumn("Título", Product::getTitle).setResizable(true);

// Deshabilitar redimensionamiento
table.addColumn("ID", Product::getId).setResizable(false);

// Verificar estado actual
boolean canResize = column.isResizable();
```

Para tablas en las que deseas un comportamiento consistente en múltiples columnas, utiliza los métodos de configuración masiva:

```java
// Hacer que todas las columnas existentes sean redimensionables
table.setColumnsToResizable(true);

// Bloquear todas las columnas existentes del redimensionamiento
table.setColumnsToResizable(false);
```

### Reordenación de columnas {#column-reordering}

La reordenación de columnas permite a los usuarios arrastrar y soltar columnas en el orden que prefieran, personalizando el diseño de la `Table` para su flujo de trabajo.

Configura los permisos de movimiento de columna al configurar tu tabla:

```java
// Permitir que los usuarios muevan esta columna
table.addColumn("Título", Product::getTitle).setMovable(true);

// Prevenir movimiento de columna (útil para columnas ID o de acción)
table.addColumn("ID", Product::getId).setMovable(false);

// Verificar estado actual
boolean canMove = column.isMovable();
```

Aplica configuraciones de movimiento a múltiples columnas simultáneamente:

```java
// Habilitar reordenación para todas las columnas existentes
table.setColumnsToMovable(true);

// Deshabilitar reordenación para todas las columnas existentes
table.setColumnsToMovable(false);
```

:::note Operaciones Masivas
Los métodos `setColumnsToResizable()` y `setColumnsToMovable()` solo afectan a las columnas existentes en el momento de la invocación. No establecen valores predeterminados para futuras columnas.
:::

### Movimiento de columna programático {#programmatic-column-movement}

Además de arrastrar y soltar, también puedes reubicar columnas programáticamente por índice o ID. Ten en cuenta que el índice se basa únicamente en las columnas visibles; cualquier columna oculta se ignora al calcular posiciones.

```java
// Mover columna a la primera posición
table.moveColumn("título", 0);

// Mover columna a la última posición
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Movimiento asíncrono con callback
table.moveColumn("descripción", 2).thenAccept(c -> {
  // Columna movida con éxito
});
```

## Manejo de eventos {#event-handling}

El componente `Table` emite eventos cuando los usuarios interactúan con las columnas, lo que te permite responder a cambios en el diseño y guardar las preferencias del usuario.

Eventos soportados:

- `TableColumnResizeEvent`: Se activa cuando un usuario redimensiona una columna arrastrando su borde.
- `TableColumnMoveEvent`: Se activa cuando un usuario reordena una columna arrastrando su encabezado.

Puedes adjuntar listeners a la `Table` para responder cuando los usuarios modifican el diseño de la tabla.

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
