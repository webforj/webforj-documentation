---
title: Add tools for an open view
sidebar_position: 20
description: Add tools that work with an MCP App already open in the current conversation.
_i18n_hash: 0ad6819ba9550e2ffd2372c09b91a746
---
Una aplicación MCP puede publicar herramientas además de la herramienta que abre su vista. Usa una acción para una operación distinta con su propia entrada. Implementa el observador de actualizaciones cuando la aplicación necesite una herramienta `inventory_update` con la misma entrada que su herramienta de apertura.

Estas herramientas no abren la aplicación. Una llamada se dirige a la vista `inventory` renderizada asociada con la misma sesión MCP. Si esa vista no está abierta, la llamada devuelve un error que dirige al cliente a llamar primero a `inventory`.

## Publicar una acción {#publish-an-action}

Agrega `@McpAppAction` a un método de vista. La anotación publica otra herramienta MCP; el método contiene la operación que se ejecuta cuando se llama a la herramienta.

```java
@McpAppAction(description = "Refresca los niveles de stock para el almacén abierto.")
Map<String, Object> refreshStock() {
  warehouse.setText(warehouse.getText() + " - refrescado");
  return Map.of(
      "warehouse", warehouse.getText(),
      "refreshed", true);
}
```

Para una aplicación llamada `inventory`, el nombre del método `refreshStock` produce el nombre de la herramienta `inventory_refresh_stock`. Establece `name` en `@McpAppAction` para elegir explícitamente la parte después de `inventory_`. Cada acción debe tener una descripción no vacía.

Un método de acción puede no tener parámetros de entrada o tener un parámetro de entrada objeto. Las propiedades del objeto se convierten en el esquema de entrada de la herramienta. Su resultado se devuelve de acuerdo con el tipo de retorno del método:

- `CallToolResult` se devuelve directamente.
- Cualquier otro valor que no sea `void` se convierte en contenido estructurado.
- Un método `void` devuelve un mensaje de finalización.

:::info[La vista debe estar abierta]

La acción aparece en la lista de herramientas MCP incluso cuando la aplicación no está abierta, pero su llamada solo tiene éxito mientras la aplicación coincidente está renderizada en la misma sesión MCP.
:::

Las acciones también se pueden declarar en una clase listada por `@McpApp(actions = InventoryActions.class)`. Una acción en esa clase debe aceptar la `InventoryView` renderizada como parámetro, además de su parámetro de entrada objeto opcional.

## Publicar la herramienta de actualización {#publish-the-update-tool}

Implementa `McpAppUpdateObserver` para publicar una herramienta de actualización para la aplicación. Para una aplicación llamada `inventory`, webforJ publica `inventory_update`. Su esquema de entrada es el mismo esquema utilizado por `inventory`.

```java
public class InventoryView extends Composite<FlexLayout>
    implements McpAppUpdateObserver {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public InventoryView() {
    self.add(warehouse);
  }

  @Override
  public CallToolResult onMcpAppUpdate(McpAppUpdateEvent event) {
    String warehouseCode = event.getArguments().path("warehouseCode").asString();
    warehouse.setText("Almacén: " + warehouseCode);
    return CallToolResult.builder()
        .addTextContent("Almacén de inventario actualizado.")
        .build();
  }
}
```

Cuando se llama a `inventory_update`, webforJ pasa sus argumentos a `onMcpAppUpdate` en la `InventoryView` renderizada. La devolución de llamada decide cómo usar esos argumentos y devuelve el resultado de la herramienta. webforJ no aplica los valores a los componentes automáticamente.

La herramienta de actualización no tiene metadatos de recursos de UI. Llamarla no abre la ruta ni renderiza otra vista.

:::tip[Elegir por entrada de herramienta]

Usa una acción para una operación separada con su propio esquema de entrada. Usa el observador de actualizaciones para la única herramienta `<app-name>_update` cuando su entrada deba coincidir con la herramienta de apertura. Una vista puede usar ambas.
:::

[Interacción con el Host](./host-interaction) cubre las solicitudes que la vista renderizada envía al host MCP.
