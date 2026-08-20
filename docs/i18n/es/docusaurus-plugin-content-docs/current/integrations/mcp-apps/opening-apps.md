---
title: Open a view with input
sidebar_position: 15
description: >-
  Accept structured opening input in a routed MCP App and choose its requested
  display mode.
_i18n_hash: 158831b08974dd001c1322c38213e331
---
La entrada de apertura permite que la IA elija el estado inicial de una vista. Por ejemplo, una aplicación de inventario puede aceptar un código de almacén cuando el cliente la abre y aplicar ese valor después de que se renderice la ruta.

## Describir la entrada {#describe-the-input}

Utilice un tipo de objeto para los argumentos de la herramienta. Las anotaciones de Jackson añaden los detalles que el cliente utiliza para construir y validar la llamada.

```java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

record InventoryInput(
    @JsonProperty(required = true)
    @JsonPropertyDescription("Código de almacén a mostrar")
    String warehouseCode) {
}
```

El esquema generado marca `warehouseCode` como requerido e incluye su descripción. Descripciones claras de las propiedades ayudan a la IA a proporcionar los valores deseados.

## Aplicar la entrada después de que se abra la vista {#apply-opening-input}

Agregue un método `@McpAppInput` a la vista enrutada. Debe aceptar un parámetro de objeto.

```java
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.mcp.McpAppDisplayMode;
import com.webforj.mcp.annotation.McpApp;
import com.webforj.mcp.annotation.McpAppInput;
import com.webforj.router.annotation.Route;

@Route("/inventory")
@McpApp(
    name = "inventory",
    description = "Muestra el inventario actual de un almacén.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public InventoryView() {
    self.add(warehouse);
  }

  @McpAppInput
  void applyOpeningInput(InventoryInput input) {
    warehouse.setText("Almacén: " + input.warehouseCode());
  }
}
```

El cliente recibe el esquema generado en `inventory`. Cuando llama a la herramienta, webforJ renderiza `/inventory` y luego invoca `applyOpeningInput` en esa instancia de vista.

:::tip[Mantener los nombres de las herramientas estables]

Cada `@McpApp` necesita una descripción no en blanco. Si se omite el `name`, webforJ deriva el nombre de la herramienta de la ruta: `/inventory` se convierte en `inventory`, `/sales/inventory` se convierte en `sales_inventory`, y la ruta raíz se convierte en `app`. Establezca `name` cuando las integraciones necesiten un nombre estable que no cambie con la ruta.
:::

:::tip[Elegir una declaración de entrada]

`@McpAppInput` no es la única fuente de esquema. Una vista puede, en su lugar, establecer `input = InventoryInput.class` o proporcionar un documento de JSON Schema con `inputSchema` en `@McpApp`. Elija exactamente una forma. Combinar ambas es rechazado durante el descubrimiento de la aplicación. Use `@McpAppInput` cuando la vista deba recibir y aplicar los valores después de la renderización.
:::

El método de entrada también puede vivir en una clase listada por `@McpApp(actions = InventoryActions.class)`. En ese caso, debe aceptar el `InventoryView` en ejecución junto con el único objeto de entrada. Declare solo un método `@McpAppInput` en toda la vista y sus clases listadas.

## Mantener la ruta de apertura navegable {#route-parameters}

La herramienta de apertura generada navega sin parámetros de ruta. Una ruta con parámetros requeridos, como `/inventory/:warehouse`, no se puede exponer directamente. Utilice una ruta sin parámetros y entrada de apertura, o cree una herramienta MCP personalizada separada que proporcione los parámetros de ruta requeridos. Se permiten parámetros opcionales, comodines y segmentos de diseño cuando el enrutador puede generar una URL sin valores.

## Solicitar un modo de presentación {#display-mode}

`displayMode` pregunta al cliente cómo presentar la vista. `INLINE` mantiene el inventario al lado de la conversación, `PIP` solicita imagen dentro de la imagen, y `FULLSCREEN` solicita la presentación más grande. `FULLSCREEN` es el valor predeterminado de webforJ. El cliente puede elegir un modo diferente según lo que soporte.

[Acciones y actualizaciones](./actions-updates) pueden cambiar la misma vista después de que se abra.
