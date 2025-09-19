---
title: Communication
sidebar_position: 3
---

The `WebswingConnector` provides bidirectional communication between your webforJ app and the embedded Swing app. This enables you to send commands to the Swing app and receive notifications when events occur within it.

## Sending actions to Swing

The `performAction` method allows your webforJ app to trigger functionality in the Swing app. This is useful for synchronizing state, triggering updates, or controlling the Swing app's behavior from the web interface.

For example, if your Swing app has a custom action handler for refreshing data:

```java
// Trigger a refresh in the Swing application from webforJ
connector.performAction("refresh");
```

You can also send data along with the action. The Swing app receives this through its Webswing API integration:

```java
// Send a command with data from webforj
connector.performAction("selectRecord", "12345");

// Send binary data
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

The action names and expected data formats are defined by your Swing app's implementation.

## Receiving events from Swing

The connector fires three types of events that notify your webforJ app about the Swing app's state and actions.

### Lifecycle events

The **initialize event** fires when the Webswing connection is established and ready for communication:

```java
connector.onInitialize(event -> {
  // Connection established
  connector.getInstanceId().ifPresent(id ->
      console.log("Connected to Webswing instance: " + id)
  );
});
```

The **start event** fires when the Swing app has fully loaded and is running:

```java
connector.onStart(event -> {
  // Swing application is now visible and interactive
  console.log("Application ready for user interaction");
});
```

### Custom action events

When your Swing app sends custom actions back to the web interface using the [Webswing Java API](https://www.webswing.org/docs/25.1/integrate/api), these are received as action events:

```java
connector.onAction(event -> {
  String actionName = event.getActionName();

  switch(actionName) {
      case "dataUpdated":
        event.getActionData().ifPresent(data -> {
            // Handle the update notification
            updateWebInterface(data);
        });
        break;

      case "fileReady":
        event.getActionBinaryData().ifPresent(data -> {
            // Binary data
            saveFile(fileData);
        });
        break;
  }
});
```