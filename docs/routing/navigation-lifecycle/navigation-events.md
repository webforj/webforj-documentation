---
sidebar_position: 4
title: Navigation Events
---

In addition to component-specific lifecycle events, you can register **global event listeners** at the router level. This allows for tracking navigation globally across the entire app, making it useful for logging, analytics, or other cross-cutting concerns.

### Example: Global navigation listener

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navigated to: " + location.getFullURI());
});
```

In this example, a global listener is registered to log every navigation event in the app. This is useful for auditing or tracking page views.

### Registering Global lifecycle event listeners

Global listeners can be attached to various lifecycle events, including:

- **`WillEnterEvent`**: Fired before any route's component is attached to the DOM.
- **`DidEnterEvent`**: Fired after a component is attached to the DOM.
- **`WillLeaveEvent`**: Fired before a component is detached from the DOM.
- **`DidLeaveEvent`**: Fired after a component is detached from the DOM.
- **`NavigateEvent`**: Fired every time navigation occurs.

:::tip Using Observers to Hook into Lifecycle Events
You can also hook into the lifecycle events using observers. For more details, refer to the [Lifecycle Observers](./observers).
:::

### Example: Global `WillLeaveEvent` listener

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "Are you sure you want to leave this view?",
      "Leave View",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

In this case, the `WillLeaveEvent` is used globally to show a confirmation dialog before the user navigates away from any view.