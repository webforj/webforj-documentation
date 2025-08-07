---
sidebar_position: 4
title: Route Navigation
_i18n_hash: cf1f9e79aa81f240306313a7c0c5a9c4
---
在 webforJ 中，路由之间的导航是根据用户操作或 URL 更改切换视图和组件的核心机制。导航允许用户在应用程序的不同部分之间无缝移动，而无需刷新页面。这种客户端导航使应用程序保持响应和流畅，同时保留应用程序的状态。

## 编程式导航 {#programmatic-navigation}

您可以通过使用 `Router` 类在应用程序中的任何地方触发导航。这允许根据按钮点击或其他用户交互等事件动态更改显示的组件。

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

在这个例子中，编程式导航到 `DashboardView` 组件会导致渲染该组件，并更新浏览器的 URL 为 `/dashboard`。

还可以通过传递一个新的 `Location` 来导航到视图。

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip 类与位置：视图路由的方法
在视图之间导航时，开发人员有两个选项：可以传递视图或路由类，允许路由器自动生成 URL 并渲染视图，或者直接传递位置。两种方法都是有效的，但**使用视图类是首选方法**，因为它提供了对未来更改的更好灵活性。例如，如果您后面决定更新路由，只需修改 `@Route` 注解，而无需更改任何使用视图类进行导航的代码。
:::

### 带参数的导航 {#navigation-with-parameters}

当您需要与路由一起传递参数时，webforJ 允许您在 URL 中嵌入参数。以下是如何导航到带参数的路由：

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  H1 title = new H1();

  public UserProfileView() {
    getBoundComponent().add(title);
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
// 导航到视图并传递用户 ID
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

这会导航到 `/user/JohnDoe`，其中 `JohnDoe` 可能代表用户 ID。该路由的组件可以提取参数并相应使用。

## 创建的视图实例 {#created-view-instance}

`navigate` 方法接受一个 Java `Consumer`，该 `Consumer` 在导航完成时被调用。它接收创建的视图组件的实例，包装在 Java `Optional` 中，允许开发人员在成功导航后与视图进行交互。

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
`Consumer` 接收组件的 Java `Optional`，因为它可能为 `null`，或由于各种原因未被创建。例如，如果导航观察者否决导航并停止过程，则该组件可能不会被渲染。
:::

## 导航选项 {#navigation-options}

`NavigationOptions` 类允许开发人员微调应用程序中的导航处理方式。通过设置特定选项，您可以控制导航的行为，例如是否更新浏览器的历史记录、调用生命周期观察者或甚至触发导航事件。

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### 设置导航选项 {#setting-navigation-options}

`NavigationOptions` 类提供了几种方法来自定义导航行为。这些包括控制如何处理路由、是否通知观察者以及浏览器的历史记录如何更新。

以下是 `NavigationOptions` 中可用的主要配置选项：

1. **导航类型 (`setNavigationType`)**  
   该选项定义新路由是否应添加到浏览器的历史记录中，或替换当前路由。

   - **`PUSH`**：将新路由添加到历史堆栈，保留当前地点。
   - **`REPLACE`**：用新位置替换历史堆栈中的当前路由，防止后退按钮导航到上一个路由。

2. **触发事件 (`setFireEvents`)**  
   确定是否在导航过程中触发导航 [生命周期事件](./navigation-lifecycle/navigation-events)。默认情况下，设置为 `true`，并会触发事件。如果设置为 `false`，则不会触发任何事件，这对于静默导航很有用。

3. **调用观察者 (`setInvokeObservers`)**  
   此标志控制导航是否应该触发已导航组件中的 [观察者](./navigation-lifecycle/observers)。观察者通常处理路由进入或退出等事件。将其设置为 `false` 会阻止调用观察者。

4. **更新历史 (`setUpdateHistory`)**  
   当设置为 `false` 时，此选项会阻止历史位置的更新。这在您希望更改视图而不影响浏览器的后退或前进导航时很有用。它仅影响历史管理，而不影响组件生命周期或路由处理。

5. **状态对象 (`setState`)**  
   [状态对象](./state-management#saving-and-restoring-state-in-browser-history) 允许您在更新浏览器的历史记录时传递附加信息。该对象存储在浏览器的历史状态中，并可以在以后的自定义用途上使用，例如在导航过程中保存应用程序的状态。

## 为视图生成位置 {#generating-locations-for-views}

路由器可以根据视图中定义的路由模式生成视图的位置。您还可以为 URL 中的动态和必需段提供附加参数。这在构建链接或分享特定视图的直接访问点时很有用。

以下是如何基于视图类和路由参数生成 `Location`：

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

这会生成一个路径为 `/user/JohnDoe` 的 `Location` 对象，完整的 URI 作为字符串。
