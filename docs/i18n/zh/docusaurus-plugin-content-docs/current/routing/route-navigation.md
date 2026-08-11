---
sidebar_position: 4
title: Route Navigation
description: >-
  Trigger client-side navigation programmatically with Router.navigate, pass
  parameters, and switch views without reloads.
_i18n_hash: c32517b16f185d4b54682b95c82d38d3
---
在webforJ中，路由之间的导航是根据用户操作或URL更改切换视图和组件的核心机制。导航允许用户在应用程序的不同部分之间无缝移动，而无需刷新页面。此客户端导航保持了应用程序的响应性和流畅性，同时保留了应用程序的状态。

## 程序化导航 {#programmatic-navigation}

您可以通过使用`Router`类在应用中的任何地方触发导航。这允许根据按钮点击或其他用户交互等事件动态更改所显示的组件。

以下是如何导航到特定路由的示例：

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // 组件逻辑在此
}
```

```java
// 导航到视图
Router.getCurrent().navigate(DashboardView.class);
```

在此示例中，程序化导航到`DashboardView`组件会导致`DashboardView`组件被渲染，并且浏览器的URL更新为`/dashboard`。

还可以通过传递新的`Location`进行导航

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip 类与位置：视图路由的方法
在视图之间导航时，开发人员有两个选项：他们可以传递视图或路由类，这样路由器可以自动生成URL并渲染视图，或者直接传递位置。这两种方法都是有效的，但**使用视图类是首选方法**，因为它为将来的更改提供了更好的灵活性。例如，如果您稍后决定更新路由，您只需要修改`@Route`注释，而不必更改任何使用视图类进行导航的代码。
:::

### 带参数的导航 {#navigation-with-parameters}

当您需要传递参数与路由时，webforJ允许您在URL中嵌入参数。以下是如何导航到带有参数的路由的方式：

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  private final Div self = getBoundComponent();
  H1 title = new H1();

  public UserProfileView() {
    self.add(title);
  }

  public void setTile(String title) {
    this.title.setText(title);
  }

  public String getTitle() {
    return title.getText();
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("未知");
    setTile(id);
  }
}
```

```java
// 导航到视图并传递用户ID
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

这将导航到`/user/JohnDoe`，其中`JohnDoe`可能代表一个用户ID。此路由的组件可以提取参数并相应使用它。

## 创建视图实例 {#created-view-instance}

`navigate`方法接受一个Java `Consumer`，该消费者在导航完成后被调用。该`Consumer`接收一个创建的视图组件的实例，包装在一个Java `Optional`中，允许开发人员在成功导航后与视图进行交互。

```java
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe"), (component) -> {
    component.ifPresent(view -> {
      console().log("新标题是: " + view.getTitle());
    });
  });
```

:::info 空实例
消费者接收一个Java `Optional`作为组件，因为它可能是`null`，或者由于各种原因未创建。例如，如果导航观察者否决导航并停止该过程，组件可能不会被渲染。
:::

## 导航选项 {#navigation-options}

`NavigationOptions`类允许开发人员微调应用中的导航处理方式。通过设置特定选项，您可以控制导航的行为，例如是否更新浏览器的历史记录、调用生命周期观察者，甚至触发导航事件。

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### 设置导航选项 {#setting-navigation-options}

`NavigationOptions`类提供了多个方法用于自定义导航行为。这些包括控制路由的处理方式、是否通知观察者以及浏览器历史记录的更新方式。

以下是在`NavigationOptions`中可用的主要配置选项：

1. **导航类型（`setNavigationType`）**
   此选项定义新路由是否应添加到浏览器的历史记录中或替换当前路由。

   - **`PUSH`**：将新路由添加到历史记录栈中，保留当前的位置。
   - **`REPLACE`**：用新位置替换历史记录栈中的当前路由，防止后退按钮导航到上一个路由。

2. **触发事件（`setFireEvents`）**
   确定在导航过程中是否应触发导航[生命周期事件](./navigation-lifecycle/navigation-events)。默认设置为`true`，事件将被触发。如果设置为`false`，则不会触发任何事件，这对静默导航很有用。

3. **调用观察者（`setInvokeObservers`）**
   此标志控制导航是否应在导航的组件内触发[观察者](./navigation-lifecycle/observers)。观察者通常处理如路由进入或退出等事件。将此设置为`false`将防止触发观察者。

4. **更新历史（`setUpdateHistory`）**
   设置为`false`时，此选项会阻止历史位置的更新。这在您想要更改视图而不影响浏览器的后退或前进导航时很有用。它仅影响历史管理，而不影响组件生命周期或路由处理。

5. **状态对象（`setState`）**
   [状态对象](./state-management#saving-and-restoring-state-in-browser-history)允许您在更新浏览器的历史记录时传递额外的信息。该对象存储在浏览器的历史状态中，可用于以后自定义目的，例如在导航期间保存应用的状态。

## 为视图生成位置 {#generating-locations-for-views}

路由器可以根据视图中定义的路线模式生成视图的位置。您还可以为URL中的动态和必需段提供额外参数。这在构造链接或共享直接访问应用中特定视图时非常有用。

以下是如何基于视图类和路由参数生成`Location`的方法：

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

这将生成一个`Location`对象，路径为`/user/JohnDoe`，完整的URI作为字符串。
