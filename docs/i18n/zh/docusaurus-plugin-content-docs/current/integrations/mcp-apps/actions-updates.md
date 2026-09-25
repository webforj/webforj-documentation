---
title: 为已打开的视图添加工具
sidebar_position: 20
description: Add tools that work with an MCP App already open in the current conversation.
_i18n_hash: 0ad6819ba9550e2ffd2372c09b91a746
---
MCP 应用可以发布工具，除了打开其视图的工具外。使用一个操作来进行一个独立的操作，并具有自己的输入。当应用需要一个与其打开工具具有相同输入的 `inventory_update` 工具时，实现更新观察者。

这些工具不会打开应用。调用被路由到与同一 MCP 会话关联的已呈现 `inventory` 视图。如果该视图未打开，调用将返回一个错误，指示客户端首先调用 `inventory`。

## 发布一个操作 {#publish-an-action}

向视图方法添加 `@McpAppAction`。该注解发布另一个 MCP 工具；方法包含在调用工具时运行的操作。

```java
@McpAppAction(description = "刷新开放仓库的库存水平。")
Map<String, Object> refreshStock() {
  warehouse.setText(warehouse.getText() + " - refreshed");
  return Map.of(
      "warehouse", warehouse.getText(),
      "refreshed", true);
}
```

对于名为 `inventory` 的应用，方法名 `refreshStock` 产生工具名 `inventory_refresh_stock`。在 `@McpAppAction` 上设置 `name` 以明确选择 `inventory_` 之后的部分。每个操作必须具有非空描述。

操作方法可以没有输入参数或一个对象输入参数。对象的属性成为工具的输入模式。其结果根据方法的返回类型返回：

- `CallToolResult` 直接返回。
- 任何其他非 `void` 值成为结构化内容。
- `void` 方法返回完成消息。

:::info[视图必须打开]

即使应用未打开，操作也会出现在 MCP 工具列表中，但它的调用仅在匹配的应用在同一 MCP 会话中呈现时成功。
:::

操作还可以在通过 `@McpApp(actions = InventoryActions.class)` 列出的类中声明。该类中的操作必须接受呈现的 `InventoryView` 作为参数，除了其可选的对象输入。

## 发布更新工具 {#publish-the-update-tool}

实现 `McpAppUpdateObserver` 为应用发布一个更新工具。对于名为 `inventory` 的应用，webforJ 发布 `inventory_update`。其输入模式与 `inventory` 使用的相同模式。

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
    warehouse.setText("仓库: " + warehouseCode);
    return CallToolResult.builder()
        .addTextContent("库存仓库已更新。")
        .build();
  }
}
```

当调用 `inventory_update` 时，webforJ 将其参数传递到已呈现的 `InventoryView` 上的 `onMcpAppUpdate`。回调决定如何使用这些参数并返回工具结果。webforJ 不会自动将这些值应用于组件。

更新工具没有 UI 资源元数据。调用它不会打开路由或呈现另一个视图。

:::tip[按工具输入选择]

对于具有自己输入模式的单独操作，请使用操作。对于输入必须与打开工具匹配的单个 `<app-name>_update` 工具，请使用更新观察者。视图可以同时使用两者。
:::

[主机交互](./host-interaction) 涉及已呈现视图发送给 MCP 主机的请求。
