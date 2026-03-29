---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: a584e996523ba2b98ecb9d7ab2f366f3
---
观察者允许组件对生命周期事件做出反应，通过实现特定阶段的接口。此模式确保了关注点的清晰分离，并简化了导航逻辑的处理。

## 可用观察者 {#available-observers}

- **`WillEnterObserver`**：允许您在进入路由之前处理任务，例如获取必要数据或阻止导航。
- **`DidEnterObserver`**： ideal用于处理组件附加后的操作，例如渲染数据或触发动画。
- **`WillLeaveObserver`**：提供了一种在用户离开路由之前管理逻辑的方法，例如检查未保存的更改。
- **`DidLeaveObserver`**：用于清理操作或其他应在组件从DOM中分离后运行的任务。
- **`ActivateObserver`**：<DocChip chip='since' label='25.03' /> 当缓存的组件被重新激活时触发，例如在导航到具有不同参数的相同路由时。

## 示例：使用`WillEnterObserver`进行身份验证 {#example-authentication-with-willenterobserver}

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

在这里，`onWillEnter`检查用户是否已认证。如果没有，则拒绝导航，防止导航完成，并重定向到登录页面。

:::warning 经过身份验证的路由示例 - 不是生产级准备
这只是一个使用经过身份验证的路由的示例。
这**不是**您将如何编写生产级身份验证系统的示例。
您需要将此示例中的概念和模式进行调整，以适应您应用程序的身份验证流程/系统。
:::

## 示例：使用`DidEnterObserver`在路由进入时获取数据 {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // 代码更新UI以显示个人资料数据
  }
}
```

此示例演示了在组件附加到DOM后使用`DidEnterObserver`来获取和显示个人资料数据。

## 示例：使用`WillLeaveObserver`处理未保存的更改 {#example-handling-unsaved-changes-with-willleaveobserver}

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
          "有未保存的更改。您是否想放弃或保存它们？",
          "未保存的更改",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

在此示例中，如果存在未保存的更改，`onWillLeave`会提示用户确认对话框，如果用户选择留下，则会拒绝导航。

:::info 导航阻止和拒绝处理
有关阻止导航的更多信息，请参见[导航阻止和拒绝处理](./navigation-blocking)
:::

## 示例：使用`DidLeaveObserver`进行清理 {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

该示例在用户离开`NotificationsView`后清除通知，使用`DidLeaveObserver`进行清理。

## 示例：使用`ActivateObserver`刷新数据 <DocChip chip='since' label='25.03' /> {#example-refreshing-data-with-activateobserver}

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();
    
    // 组件正在使用不同的参数进行重用
    if (!productId.equals(currentProductId)) {
      currentProductId = productId;
      refreshProductData(productId);
    }
  }

  private void refreshProductData(String productId) {
    // 代码获取并显示新产品数据
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

该示例演示了使用`ActivateObserver`在导航到相同路由但使用不同参数时刷新数据。组件保持缓存并被重新激活，而不是重新创建，因此用户界面更新以显示正确的当前参数数据，而无需实例化新组件。

:::tip 组件层次中的激活
在导航到路由时，`Activate`事件会针对**当前路径中保持的所有缓存组件**触发。例如，从`/products/123`导航到`/products/456`时，如果它们已被缓存并保持在路由层次结构中，则父`ProductsLayout`组件和子`ProductView`组件都会接收到`Activate`事件。
:::
