---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 2ca468b09b2ae9e2ab3813119d31bf44
---
在 webforJ 中，通过路由导航是根据用户操作或 URL 更改切换视图和组件的核心机制。导航允许用户在应用的不同部分之间无缝移动，而无需刷新页面。此客户端导航使应用保持响应迅速和平滑，同时保持应用的状态。

## 编程导航 {#programmatic-navigation}

您可以通过使用 `Router` 类在应用的任何地方触发导航。这允许根据按钮点击或其他用户交互等事件动态更改显示的组件。

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

在此示例中，以编程方式导航到 `DashboardView` 组件将导致 `DashboardView` 组件被渲染，并且浏览器的 URL 更新为 `/dashboard`。

也可以通过传递新的 `Location` 来导航到视图。

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip 类与位置：视图路由的方法
导航于视图之间时，开发人员有两种选择：可以选择传递视图或路由类，允许路由器自动生成 URL 并渲染视图，或者直接传递位置。这两种方法都是有效的，但**使用视图类是首选方法**，因为它提供了更好的未来变更灵活性。例如，如果您稍后决定更新路由，只需修改 `@Route` 注解，而无需更改使用视图类进行导航的任何代码。
:::

### 带参数的导航 {#navigation-with-parameters}

当您需要通过路由传递参数时，webforJ 允许您在 URL 中嵌入参数。以下是如何带参数导航至路由的示例：

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

这将导航到 `/user/JohnDoe`，其中 `JohnDoe` 可能代表一个用户 ID。此路由的组件随后可以提取参数并相应使用。

## 创建视图实例 {#created-view-instance}

`navigate` 方法接受一个 Java `Consumer`，在导航完成后被调用。`Consumer` 接收一个创建的视图组件实例，包装在 java `Optional` 中，允许开发人员在成功导航后与视图进行交互。

```java
Router.getCurrent().navigate(
    UserProfileView.class,
    ParametersBag.of("id=JohnDoe"), (component) -> {
      component.ifPresent(view -> {
        console().log("新的标题是: " + view.getTitle());
      });
    });
```

:::info 空实例
消费者收到一个 Java `Optional` 的组件，因为它可能是 `null`，或由于各种原因未创建。例如，如果导航观察者否决了导航并停止了该过程，则组件可能不会被渲染。
:::

## 导航选项 {#navigation-options}

`NavigationOptions` 类允许开发人员微调应用中的导航处理。通过设置特定选项，您可以控制导航的行为，例如是否更新浏览器的历史记录、调用生命周期观察者，甚至触发导航事件。

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### 设置导航选项 {#setting-navigation-options}

`NavigationOptions` 类提供了几种方法来定制导航行为。这些包括控制路由的处理方式、观察者是否被通知，以及浏览器历史如何更新。

以下是 `NavigationOptions` 中可用的主要配置选项：

1. **导航类型 (`setNavigationType`)**  
   此选项定义新的路由是应添加到浏览器历史中还是替换当前路由。

   - **`PUSH`**：将新的路由添加到历史堆栈中，保留当前的位置。
   - **`REPLACE`**：用新位置替换历史堆栈中的当前路由，防止后退按钮导航到先前的路由。

2. **触发事件 (`setFireEvents`)**  
   确定在导航过程中是否应触发 [生命周期事件](./navigation-lifecycle/navigation-events)。默认情况下，此设置为 `true`，会触发事件。如果设置为 `false`，则不会触发事件，这对于静默导航非常有用。

3. **调用观察者 (`setInvokeObservers`)**  
   此标志控制导航是否应触发导航组件内的 [观察者](./navigation-lifecycle/observers)。观察者通常处理路由进入或退出等事件。将此设置为 `false` 将阻止调用观察者。

4. **更新历史 (`setUpdateHistory`)**  
   设置为 `false` 时，此选项阻止历史位置更新。这在您想改变视图而不影响浏览器的后退或前进导航时非常有用。它仅影响历史管理，不影响组件生命周期或路由处理。

5. **状态对象 (`setState`)**  
   [状态对象](./state-management#saving-and-restoring-state-in-browser-history)允许您在更新浏览器历史时传递附加信息。此对象存储在浏览器的历史状态中，可以在以后的自定义用途上使用，例如在导航过程中保存应用的状态。

## 为视图生成位置 {#generating-locations-for-views}

路由器可以根据视图中定义的路由模式生成视图的位置。您还可以提供额外的参数以满足 URL 中的动态和必需段。这在构造链接或共享对应用中特定视图的直接访问点时很有用。

以下是如何根据视图类和路由参数生成 `Location` 的示例：

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

这将生成一个路径为 `/user/JohnDoe` 的 `Location` 对象，其完整的 URI 为字符串。
