---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: 18390849527056ed2780b761ae7919c1
---
观察者允许组件响应生命周期事件，通过实现特定阶段的接口。这种模式确保了关注点的清晰分离，并简化了导航逻辑的处理。

## 可用的观察者 {#available-observers}

- **`WillEnterObserver`**: 允许您在进入路由之前处理任务，如获取必要数据或阻止导航。
- **`DidEnterObserver`**: 适合处理组件附加后需要执行的操作，如渲染数据或触发动画。
- **`WillLeaveObserver`**: 提供了一种在用户离开路由之前管理逻辑的方法，比如检查未保存的更改。
- **`DidLeaveObserver`**: 用于清理操作或其他应在组件从 DOM 中分离后执行的任务。
- **`ActivateObserver`** (自 25.03 起): 当缓存的组件被重新激活时触发，例如在以不同参数导航到同一路由时。

## 示例：使用 `WillEnterObserver` 进行身份验证 {#example-authentication-with-willenterobserver}

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> implements WillEnterObserver {

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    boolean isAuthenticated = authService.isAuthenticated();
    event.veto(!isAuthenticated);

    if (!isAuthenticated) {
      event.getRouter().navigate(LoginView.class);
    }
  }
}
```

在这里，`onWillEnter` 检查用户是否经过身份验证。如果没有，导航将被否决，阻止导航完成，并重定向到登录页面。

:::warning 身份验证路由示例 - 不适合生产环境
上述示例仅展示如何使用身份验证路由。
这**不是**您编写生产级别身份验证系统的示例。
您需要将此示例中使用的概念和模式调整为适应您应用程序的身份验证流程/系统。
:::

## 示例：使用 `DidEnterObserver` 在路由进入时获取数据 {#example-fetching-data-on-route-entry-with-didenterobserver}

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String userId = parameters.get("userId").orElseThrow();
    UserService.fetchProfile(userId).thenAccept(
        profile -> updateProfileUI(profile));
  }

  private void updateProfileUI(Profile profile) {
    // 更新 UI 的代码
  }
}
```

此示例演示了使用 `DidEnterObserver` 在组件附加到 DOM 后获取和显示个人资料数据。

## 示例：使用 `WillLeaveObserver` 处理未保存的更改 {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // 检测未保存更改的逻辑
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "存在未保存的更改。您想要丢弃还是保存这些更改？",
          "未保存的更改",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

在此示例中，如果存在未保存的更改，`onWillLeave` 会提示用户确认对话框，如果用户选择留下，则否决导航。

:::info 导航阻止和否决处理
有关阻止导航的更多信息，请参见 [导航阻止和否决处理](./navigation-blocking)
:::

## 示例：使用 `DidLeaveObserver` 进行清理 {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

此示例在用户离开 `NotificationsView` 后清除通知，使用 `DidLeaveObserver` 进行清理。

## 示例：使用 `ActivateObserver` 刷新数据 {#example-refreshing-data-with-activateobserver}

:::info 自 25.03 起
`ActivateObserver` 和 `ActivateEvent` 从 webforJ 版本 `25.03` 开始可用。
:::

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();
    
    // 组件正在使用不同的参数被重用
    if (!productId.equals(currentProductId)) {
      currentProductId = productId;
      refreshProductData(productId);
    }
  }

  private void refreshProductData(String productId) {
    // 取得并显示新产品数据的代码
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

此示例演示了使用 `ActivateObserver` 在以不同参数导航到同一路由时刷新数据。组件保持缓存并被重新激活，而不是重新创建，因此 UI 更新以显示当前参数的正确数据，而无需实例化新组件。

:::tip 组件层次结构中的激活
导航到路由时，`Activate` 事件会针对保持在当前路径中的**所有缓存组件**触发。例如，当从 `/products/123` 导航到 `/products/456` 时，如果父组件 `ProductsLayout` 和子组件 `ProductView` 已缓存并仍在路由层次结构中，则两者都会收到 `Activate` 事件。
:::
