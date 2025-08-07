---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: be571bd197730689ba8346b2ef702a3f
---
观察者允许组件响应生命周期事件，通过实现特定阶段的接口。这种模式确保关注点的清晰分离，并简化导航逻辑的处理。

## 可用观察者 {#available-observers}

- **`WillEnterObserver`**：允许您在进入路由之前处理任务，例如获取必要的数据或阻止导航。
- **`DidEnterObserver`**：理想用于处理组件附加后的操作，例如渲染数据或触发动画。
- **`WillLeaveObserver`**：提供一种在用户离开路由之前管理逻辑的方法，例如检查未保存的更改。
- **`DidLeaveObserver`**：用于清理操作或其他在组件从 DOM 中分离后应运行的任务。

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

在这里，`onWillEnter` 检查用户是否经过身份验证。如果没有，导航将被拒绝，阻止导航完成，并重定向到登录页面。

:::warning 身份验证路由的示例 - 不适合生产环境
此示例仅说明如何使用受身份验证的路由。
这 **不是** 您如何编写生产级身份验证系统的示例。
您需要根据此示例中使用的概念和模式进行调整，以适应您的认证流程/系统。
:::

## 示例：使用 `DidEnterObserver` 在路由条目上获取数据 {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // 更新UI以显示个人资料数据的代码
  }
}
```

此示例演示了如何使用 `DidEnterObserver` 在组件附加到 DOM 后获取和显示个人资料数据。

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
          "有未保存的更改。您想放弃还是保存它们？",
          "未保存的更改",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

在这个示例中，如果有未保存的更改，`onWillLeave` 提示用户确认对话框，如果用户选择留下，则拒绝导航。

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
