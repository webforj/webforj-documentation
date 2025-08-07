---
sidebar_position: 4
title: Navigation Events
_i18n_hash: d7beed9a9d607e1decc18fa24417b213
---
除了特定组件的生命周期事件，您可以在路由级别注册**全局事件监听器**。这使得可以在整个应用程序中跟踪导航，这对于日志记录、分析或其他跨切关注点非常有用。

## 示例：全局导航监听器 {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navigated to: " + location.getFullURI());
});
```

在这个例子中，注册了一个全局监听器来记录应用程序中的每个导航事件。这对于审计或跟踪页面浏览量非常有用。

## 注册全局生命周期事件监听器 {#registering-global-lifecycle-event-listeners}

全局监听器可以附加到各种生命周期事件，包括：

- **`WillEnterEvent`**：在任何路由的组件附加到DOM之前触发。
- **`DidEnterEvent`**：在组件附加到DOM之后触发。
- **`WillLeaveEvent`**：在组件从DOM中分离之前触发。
- **`DidLeaveEvent`**：在组件从DOM中分离之后触发。
- **`NavigateEvent`**：每次发生导航时触发。

:::tip 使用观察者钩入生命周期事件
您还可以使用观察者钩入生命周期事件。有关更多详细信息，请参阅[生命周期观察者](./observers)。
:::

## 示例：全局 `WillLeaveEvent` 监听器 {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "您确定要离开此视图吗？",
      "离开视图",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

在这种情况下，`WillLeaveEvent` 被全局用于在用户导航离开任何视图之前显示确认对话框。
