---
title: Work with the MCP client
sidebar_position: 25
description: Connect a rendered webforJ view to its MCP client.
---

An MCP App doesn't have to keep every interaction inside its embedded view. It can send information to the conversation, keep the model informed as the user changes the UI, or ask the client to handle something outside the frame.

The same route can also open in a normal browser. Start every client interaction by checking whether an MCP host is present.

## Continue the conversation from the view {#send-a-message}

Consider an inventory app where the user selects a warehouse and then asks the AI to review its stock. The button can send that request as the next user message:

```java
Paragraph warehouse = new Paragraph("Warehouse: BER");
Button review = new Button("Review stock");

review.addClickListener(event -> McpHost.ifPresent(host ->
    host.sendMessage("Review the current stock for " + warehouse.getText())));
```

`McpHost.ifPresent` runs the callback only when the view is connected to an MCP client. In a normal browser, the button has no host-side effect.

## Keep the model informed {#update-model-context}

Not every UI change should create another message. When the selected warehouse or filters change, the app can replace the context it contributes to the model:

```java
McpHost host = McpHost.getCurrent();
if (host != null) {
  PendingResult<Void> result = host.updateModelContext(
      Map.of("warehouse", warehouse.getText(), "source", "inventory-app"));

  result.exceptionally(error -> {
    warehouse.setText("Sharing failed: " + error.getMessage());
    return null;
  });
}
```

The updated state becomes available to later model responses without adding a visible message to the conversation. Host calls are asynchronous and return a <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, so handle completion or failure without blocking the webforJ UI thread.

## Leave the embedded view {#leave-the-view}

Some work belongs outside the app frame. Use `openLink` when the user needs to continue on an external page. Use `requestDisplayMode` when the current content needs a different presentation, such as fullscreen for a detailed table. The client decides whether it can satisfy either request.

:::tip[Keep the browser experience complete]

Treat host integration as an enhancement. The route should remain useful when it runs in a browser or when the connected client doesn't support a requested capability.
:::

## Follow changes from the conversation {#host-events}

The client can continue working with the app after it renders. For example, the view can clear a loading state when a tool call is cancelled and refresh explanatory text when the conversation context changes:

```java
McpHost.ifPresent(host -> {
  host.onToolCancelled(event ->
      warehouse.setText("The inventory request was cancelled."));
  host.onHostContextChanged(event ->
      warehouse.setText("The conversation context changed."));
});
```

Register only the listeners the view needs, and don't assume every client sends every event. See the `McpHost` Javadocs for the available requests, events, payloads, and method signatures.
