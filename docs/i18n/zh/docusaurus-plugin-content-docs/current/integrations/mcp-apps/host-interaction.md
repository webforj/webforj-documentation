---
title: 使用 MCP 客户端
sidebar_position: 25
description: Connect a rendered webforJ view to its MCP client.
_i18n_hash: 082797b568bd8f308b625306c524d7ef
---
MCP 应用不必将每个交互都保留在其嵌入视图中。它可以向对话发送信息，在用户更改 UI 时保持模型知情，或请求客户端处理框架外的内容。

同一路由也可以在正常浏览器中打开。通过检查是否存在 MCP 主机来开始每个客户端交互。

## 从视图继续对话 {#send-a-message}

考虑一个库存应用，其中用户选择一个仓库，然后询问 AI 审查其库存。按钮可以将该请求作为下一个用户消息发送：

```java
Paragraph warehouse = new Paragraph("仓库: BER");
Button review = new Button("审查库存");

review.addClickListener(event -> McpHost.ifPresent(host ->
    host.sendMessage("审查 " + warehouse.getText() + " 的当前库存")));
```

`McpHost.ifPresent` 仅在视图连接到 MCP 客户端时运行回调。在正常浏览器中，按钮没有主机端效果。

## 保持模型知情 {#update-model-context}

并非每个 UI 更改都应创建另一个消息。当选择的仓库或过滤器更改时，应用可以替换其对模型贡献的上下文：

```java
McpHost host = McpHost.getCurrent();
if (host != null) {
  PendingResult<Void> result = host.updateModelContext(
      Map.of("warehouse", warehouse.getText(), "source", "inventory-app"));

  result.exceptionally(error -> {
    warehouse.setText("共享失败: " + error.getMessage());
    return null;
  });
}
```

更新的状态在不向对话添加可见消息的情况下可用于后续模型响应。主机调用是异步的，并返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，因此在处理完成或失败时无需阻塞 webforJ UI 线程。

## 离开嵌入视图 {#leave-the-view}

某些工作属于应用框架外。当用户需要在外部页面继续时使用 `openLink`。当当前内容需要不同的展示时，例如全屏显示详细表格时，使用 `requestDisplayMode`。客户端决定是否能满足这两个请求。

:::tip[保持浏览器体验完整]

将主机集成视为一种增强。无论在浏览器中运行还是连接的客户端不支持请求的能力，该路由都应保持有用。
:::

## 跟踪对话中的变化 {#host-events}

客户端可以在渲染后继续与应用程序进行交互。例如，当工具调用被取消时，视图可以清除加载状态，并在对话上下文发生变化时刷新说明文本：

```java
McpHost.ifPresent(host -> {
  host.onToolCancelled(event ->
      warehouse.setText("库存请求已取消。"));
  host.onHostContextChanged(event ->
      warehouse.setText("对话上下文已变化。"));
});
```

仅注册视图所需的监听器，不要假设每个客户端都会发送每个事件。请查看 `McpHost` Javadoc 以获取可用的请求、事件、有效载荷和方法签名。
