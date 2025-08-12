---
sidebar_position: 4
title: Navigation Events
_i18n_hash: efd06f0c5d04fb782fc27b187d1b063d
---
除了特定于组件的生命周期事件外，您还可以在路由器级别注册**全局事件监听器**。这允许在整个应用程序中全局跟踪导航，便于日志记录、分析或其他横切关注点。

## 示例：全局导航监听器 {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navigated to: " + location.getFullURI());
});
```

在此示例中，注册了一个全局监听器以记录应用中的每个导航事件。这对于审计或跟踪页面查看非常有用。

## 注册全局生命周期事件监听器 {#registering-global-lifecycle-event-listeners}

全局监听器可以附加到各种生命周期事件，包括：

- **`WillEnterEvent`**：在任何路由的组件附加到 DOM 之前触发。
- **`DidEnterEvent`**：在组件附加到 DOM 之后触发。
- **`WillLeaveEvent`**：在组件从 DOM 分离之前触发。
- **`DidLeaveEvent`**：在组件从 DOM 分离之后触发。
- **`NavigateEvent`**：每次发生导航时触发。

:::tip 使用观察者挂钩生命周期事件
您还可以使用观察者挂钩生命周期事件。有关更多详细信息，请参阅 [Lifecycle Observers](./observers)。
:::

## 示例：全局 `WillLeaveEvent` 监听器 {#example-global-willleaveevent-listener}

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

在这种情况下，`WillLeaveEvent` 被全球使用，以在用户导航离开任何视图之前显示确认对话框。
