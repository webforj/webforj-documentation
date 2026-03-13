---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 9d594a84516af29dde3f66726bc22825
---
在webforJ中，所有路由都在一个Frame内呈现，该Frame作为顶层容器，负责显示当前路由的内容。当用户在不同路由之间导航时，Frame标题会动态更新，以反映活动视图，帮助提供清晰的上下文，了解用户在应用中的当前位置。

Frame的标题可以通过注解静态设置，也可以在运行时通过代码动态设置。这种灵活的方法允许开发者定义符合每个视图目的的标题，同时根据需要适应特定场景或参数。

## 使用注解设置Frame标题 {#frame-title-with-annotations}

在视图中设置Frame标题的最简单方法是使用`@FrameTitle`注解。此注解允许您为任何路由组件定义一个静态标题，然后在组件渲染时应用到Frame。

### 使用`@FrameTitle`注解 {#using-the-frametitle-annotation}

`@FrameTitle`注解应用于类级别，并允许您指定一个表示页面标题的字符串值。当路由器导航到具有此注解的组件时，指定的标题将自动设置为浏览器窗口的标题。

以下是一个示例：

```java
@Route
@FrameTitle("仪表板")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // 视图逻辑
  }
}
```

在这个示例中：
- `DashboardView`类被`@Route`注解标记以定义路由。
- `@FrameTitle("仪表板")`注解将Frame标题设置为"仪表板"。
- 当用户导航到`/dashboard`时，Frame的标题将自动更新为指定的值。

此方法适用于具有静态标题且不需要根据路由上下文频繁更新的路由。

:::tip `@AppTitle`和`@FrameTitle`  
如果设置了应用标题，Frame标题将包含它。例如，如果应用将标题定义为`@AppTitle("webforJ")`，而Frame标题设置为`@FrameTitle("仪表板")`，最终页面标题将为`仪表板 - webforJ`。如果需要，您可以在`@AppTitle`注解中通过使用`format`属性自定义最终标题的格式。  
:::

## 动态Frame标题 {#dynamic-frame-titles}

在需要根据应用状态或路由参数动态更改Frame标题的情况下，webforJ提供了一个名为`HasFrameTitle`的接口。该接口允许组件根据当前导航上下文和路由参数提供Frame标题。

### 实现`HasFrameTitle`接口 {#implementing-the-hasframetitle-interface}

`HasFrameTitle`接口包含一个单一的方法`getFrameTitle()`，在Frame标题更新之前调用。此方法提供了根据导航上下文或其他动态因素动态生成标题的灵活性。

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {

  public ProfileView() {
    getBoundComponent().add(new H1("个人资料页面"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // 根据路由参数动态设置Frame标题
    String userId = parameters.get("id").orElse("未知");
    return "个人资料 - 用户 " + userId;
  }
}
```

在这个示例中：
- `ProfileView`组件实现了`HasFrameTitle`接口。
- `getFrameTitle()`方法使用URL中的`id`参数动态生成标题。
- 如果路由是`/profile/123`，标题将更新为"个人资料 - 用户 123"。

:::tip 结合注解和动态标题
可以结合静态和动态的方法。如果路由组件同时具有`@FrameTitle`注解并实现`HasFrameTitle`接口，则从`getFrameTitle()`动态提供的标题将优先于注解中的静态值。
:::
