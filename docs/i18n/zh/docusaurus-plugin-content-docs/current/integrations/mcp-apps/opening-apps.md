---
title: Open a view with input
sidebar_position: 15
description: >-
  Accept structured opening input in a routed MCP App and choose its requested
  display mode.
_i18n_hash: 158831b08974dd001c1322c38213e331
---
打开输入允许 AI 选择视图的初始状态。例如，一个库存应用可以在客户端打开时接受一个仓库代码，并在路由渲染后应用该值。

## 描述输入 {#describe-the-input}

对工具参数使用一个对象类型。Jackson 注释添加客户端用于构建和验证调用的细节。

```java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

record InventoryInput(
    @JsonProperty(required = true)
    @JsonPropertyDescription("显示的仓库代码")
    String warehouseCode) {
}
```

生成的 schema 将 `warehouseCode` 标记为必需，并包含其描述。清晰的属性描述有助于 AI 提供预期的值。

## 在视图打开后应用输入 {#apply-opening-input}

向路由视图添加一个 `@McpAppInput` 方法。它必须接受一个对象参数。

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
    description = "显示某个仓库的当前库存。",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public InventoryView() {
    self.add(warehouse);
  }

  @McpAppInput
  void applyOpeningInput(InventoryInput input) {
    warehouse.setText("仓库: " + input.warehouseCode());
  }
}
```

客户端在 `inventory` 上接收生成的 schema。当它调用工具时，webforJ 渲染 `/inventory` 然后在该视图实例上调用 `applyOpeningInput`。

:::tip[保持工具名称稳定]

每个 `@McpApp` 需要一个非空描述。如果省略 `name`，webforJ 将从路由派生工具名称：`/inventory` 变为 `inventory`，`/sales/inventory` 变为 `sales_inventory`，根路由变为 `app`。当集成需要一个不会随路由变化的稳定名称时，设置 `name`。
:::

:::tip[选择一个输入声明]

`@McpAppInput` 不是唯一的 schema 源。视图可以改为设置 `input = InventoryInput.class` 或提供一个 JSON Schema 文档，使用 `inputSchema` 在 `@McpApp` 上。选择恰好一个形式。组合它们在应用发现过程中会被拒绝。当视图必须在渲染后接收和应用值时，使用 `@McpAppInput`。
:::

输入方法也可以位于 `@McpApp(actions = InventoryActions.class)` 列出的类中。在这种情况下，它必须同时接受正在运行的 `InventoryView` 和一个输入对象。在视图及其列出的类中仅声明一个 `@McpAppInput` 方法。

## 保持打开的路由可导航 {#route-parameters}

生成的打开工具在没有路由参数的情况下导航。具有必需参数的路由，如 `/inventory/:warehouse`，不能被直接暴露。使用无参数的路由和打开输入，或创建一个单独的自定义 MCP 工具，提供所需的路由参数。可选参数、通配符和布局段在路由器可以在没有值的情况下生成 URL 时被允许。

## 请求显示模式 {#display-mode}

`displayMode` 要求客户端如何呈现视图。`INLINE` 将库存保持在对话旁边，`PIP` 请求画中画，`FULLSCREEN` 请求最大的展示。`FULLSCREEN` 是 webforJ 的默认值。客户端可以根据其支持的内容选择不同的模式。

[操作和更新](./actions-updates) 可以在视图打开后更改相同的视图。
