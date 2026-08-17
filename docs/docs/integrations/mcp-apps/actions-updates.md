---
title: Add tools for an open view
sidebar_position: 20
description: Add tools that work with an MCP App already open in the current conversation.
---

An MCP App can publish tools in addition to the tool that opens its view. Use an action for a distinct operation with its own input. Implement the update observer when the app needs one `inventory_update` tool with the same input as its opening tool.

These tools don't open the app. A call is routed to the rendered `inventory` view associated with the same MCP session. If that view isn't open, the call returns an error that directs the client to call `inventory` first.

## Publish an action {#publish-an-action}

Add `@McpAppAction` to a view method. The annotation publishes another MCP tool; the method contains the operation that runs when the tool is called.

```java
@McpAppAction(description = "Refreshes stock levels for the open warehouse.")
Map<String, Object> refreshStock() {
  warehouse.setText(warehouse.getText() + " - refreshed");
  return Map.of(
      "warehouse", warehouse.getText(),
      "refreshed", true);
}
```

For an app named `inventory`, the method name `refreshStock` produces the tool name `inventory_refresh_stock`. Set `name` on `@McpAppAction` to choose the part after `inventory_` explicitly. Every action must have a nonblank description.

An action method can have no input parameter or one object input parameter. The object's properties become the tool's input schema. Its result is returned according to the method's return type:

- `CallToolResult` is returned directly.
- Any other non-`void` value becomes structured content.
- A `void` method returns a completion message.

:::info[The view must be open]

The action appears in the MCP tool list even when the app isn't open, but its call succeeds only while the matching app is rendered in the same MCP session.
:::

Actions can also be declared in a class listed by `@McpApp(actions = InventoryActions.class)`. An action in that class must accept the rendered `InventoryView` as a parameter, in addition to its optional object input.

## Publish the update tool {#publish-the-update-tool}

Implement `McpAppUpdateObserver` to publish one update tool for the app. For an app named `inventory`, webforJ publishes `inventory_update`. Its input schema is the same schema used by `inventory`.

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
    warehouse.setText("Warehouse: " + warehouseCode);
    return CallToolResult.builder()
        .addTextContent("Inventory warehouse updated.")
        .build();
  }
}
```

When `inventory_update` is called, webforJ passes its arguments to `onMcpAppUpdate` on the rendered `InventoryView`. The callback decides how to use those arguments and returns the tool result. webforJ doesn't apply the values to components automatically.

The update tool has no UI resource metadata. Calling it doesn't open the route or render another view.

:::tip[Choose by tool input]

Use an action for a separate operation with its own input schema. Use the update observer for the single `<app-name>_update` tool when its input must match the opening tool. A view can use both.
:::

[Host interaction](./host-interaction) covers requests that the rendered view sends to the MCP host.
