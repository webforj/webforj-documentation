---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 91739f35b8d47f6e90e276623864aac4
---
在 webforJ 中，路由之间的导航是根据用户操作或 URL 更改切换视图和组件的核心机制。导航允许用户在应用程序的不同部分之间无缝移动，而无需刷新页面。这种客户端导航使应用程序保持响应和流畅，同时保留应用程序的状态。

## 编程导航 {#programmatic-navigation}

您可以通过使用 `Router` 类从应用程序的任何地方触发导航。这允许根据按钮点击或其他用户交互等事件动态更改显示的组件。

以下是如何导航到特定路由的示例：

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // 组件逻辑在这里
}
```

```java
// 导航到视图
Router.getCurrent().navigate(DashboardView.class);
```

在这个例子中，以编程方式导航到 `DashboardView` 组件会导致渲染该 `DashboardView` 组件，同时浏览器的 URL 更新到 `/dashboard`。

也可以通过传递新的 `Location` 来导航到视图：

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip 类与位置：视图路由的方法
在不同视图之间导航时，开发人员有两个选择：要么传递视图或路由类，允许路由器自动生成 URL 并呈现视图，或者直接传递位置。这两种方法都是有效的，但**使用视图类是首选方法**，因为它为未来的更改提供了更好的灵活性。例如，如果您稍后决定更新路由，则只需修改 `@Route` 注解，而无需更改任何使用视图类进行导航的代码。
:::

### 带参数的导航 {#navigation-with-parameters}

当您需要传递参数与路由一起时，webforJ 允许您将参数嵌入 URL 中。以下是如何导航到带参数的路由：

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
    String id = parameters.getAlpha("id").orElse("Unknown");
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

这将导航到 `/user/JohnDoe`，其中 `JohnDoe` 可能代表用户 ID。该路由的组件可以提取该参数并相应地使用。

## 创建视图实例 {#created-view-instance}

`navigate` 方法接受一个 Java `Consumer`，该 `Consumer` 在导航完成后被调用。该 `Consumer` 接收创建的视图组件的实例，包装在 Java `Optional` 中，使开发人员可以在成功导航后与视图交互。

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
消费者接收一个 Java `Optional` 的组件，因为它可能是 `null`，或者由于各种原因未创建。例如，如果导航观察者否决导航并停止该过程，则该组件可能不会被渲染。
:::

## 导航选项 {#navigation-options}

`NavigationOptions` 类允许开发人员微调应用程序内部导航的处理方式。通过设置特定选项，您可以控制导航的行为，例如是否更新浏览器的历史记录、调用生命周期观察者，甚至触发导航事件。

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### 设置导航选项 {#setting-navigation-options}

`NavigationOptions` 类提供多个方法来自定义导航行为。这些包括控制如何处理路由、是否通知观察者以及如何更新浏览器历史记录。

以下是 `NavigationOptions` 中可用的主要配置选项：

1. **导航类型 (`setNavigationType`)**  
   此选项定义新路由是否应添加到浏览器的历史记录中，或替换当前路由。

   - **`PUSH`**：将新路由添加到历史堆栈中，保留当前的位置。
   - **`REPLACE`**：用新位置替换历史堆栈中的当前路由，防止后退按钮导航到之前的路由。

2. **触发事件 (`setFireEvents`)**  
   确定在导航期间是否应触发 [生命周期事件](./navigation-lifecycle/navigation-events)。默认情况下，这个设置为 `true`，事件会被触发。如果设置为 `false`，则不会触发任何事件，这在静默导航时十分有用。

3. **调用观察者 (`setInvokeObservers`)**  
   此标志控制导航是否应触发导航组件中的 [观察者](./navigation-lifecycle/observers)。观察者通常处理路由进入或退出等事件。将此设置为 `false` 将阻止观察者被调用。

4. **更新历史 (`setUpdateHistory`)**  
   当设置为 `false` 时，此选项将防止历史位置被更新。这在您想更改视图而不影响浏览器的前进或后退导航时很有用。它只影响历史管理，而不影响组件生命周期或路由处理。

5. **状态对象 (`setState`)**  
   [状态对象](./state-management#saving-and-restoring-state-in-browser-history) 允许您在更新浏览器历史记录时传递附加信息。该对象存储在浏览器的历史状态中，稍后可用于自定义目的，例如在导航过程中保存应用程序的状态。

## 为视图生成位置 {#generating-locations-for-views}

路由器可以根据视图中定义的路由模式为视图生成位置。您还可以提供额外的参数来处理 URL 中的动态和必要段。这在构建链接或共享对应用程序中特定视图的直接访问点时非常有用。

以下是如何根据视图类和路由参数生成 `Location`：

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

这将生成一个 `Location` 对象，其路径为 `/user/JohnDoe`，完整 URI 为字符串形式。
