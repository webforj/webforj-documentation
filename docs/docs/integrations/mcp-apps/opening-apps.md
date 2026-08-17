---
title: Open a view with input
sidebar_position: 15
description: Accept structured opening input in a routed MCP App and choose its requested display mode.
---

Opening input lets the AI choose the initial state of a view. For example, an inventory app can accept a warehouse code when the client opens it and apply that value after the route renders.

## Describe the input {#describe-the-input}

Use one object type for the tool arguments. Jackson annotations add the details that the client uses to build and validate the call.

```java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

record InventoryInput(
    @JsonProperty(required = true)
    @JsonPropertyDescription("Warehouse code to show")
    String warehouseCode) {
}
```

The generated schema marks `warehouseCode` as required and includes its description. Clear property descriptions help the AI supply the intended values.

## Apply input after the view opens {#apply-opening-input}

Add one `@McpAppInput` method to the routed view. It must accept one object parameter.

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
    description = "Shows the current inventory for a warehouse.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public InventoryView() {
    self.add(warehouse);
  }

  @McpAppInput
  void applyOpeningInput(InventoryInput input) {
    warehouse.setText("Warehouse: " + input.warehouseCode());
  }
}
```

The client receives the generated schema on `inventory`. When it calls the tool, webforJ renders `/inventory` and then invokes `applyOpeningInput` on that view instance.

:::tip[Keep tool names stable]

Every `@McpApp` needs a nonblank description. If `name` is omitted, webforJ derives the tool name from the route: `/inventory` becomes `inventory`, `/sales/inventory` becomes `sales_inventory`, and the root route becomes `app`. Set `name` when integrations need a stable name that won't change with the route.
:::

:::tip[Choose one input declaration]

`@McpAppInput` isn't the only schema source. A view can instead set `input = InventoryInput.class` or provide a JSON Schema document with `inputSchema` on `@McpApp`. Choose exactly one form. Combining them is rejected during app discovery. Use `@McpAppInput` when the view must receive and apply the values after rendering.
:::

The input method can also live in a class listed by `@McpApp(actions = InventoryActions.class)`. In that case, it must accept the running `InventoryView` together with the one input object. Declare only one `@McpAppInput` method across the view and its listed classes.

## Keep the opening route navigable {#route-parameters}

The generated opening tool navigates without route parameters. A route with required parameters, such as `/inventory/:warehouse`, can't be exposed directly. Use a parameter-free route and opening input, or create a separate custom MCP tool that supplies the required route parameters. Optional parameters, wildcards, and layout segments are allowed when the router can generate a URL without values.

## Request a display mode {#display-mode}

`displayMode` asks the client how to present the view. `INLINE` keeps the inventory beside the conversation, `PIP` requests picture-in-picture, and `FULLSCREEN` requests the largest presentation. `FULLSCREEN` is the webforJ default. The client can choose a different mode based on what it supports.

[Actions and updates](./actions-updates) can change the same view after it opens.
