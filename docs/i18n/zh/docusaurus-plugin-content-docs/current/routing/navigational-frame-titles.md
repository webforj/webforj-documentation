---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: cbd0aa0a56b47ee6270000fc326a7967
---
在 webforJ 中，所有路由都在一个 Frame 中渲染，Frame 作为顶层容器，负责显示当前路由的内容。当用户在不同路由之间导航时，Frame 标题会动态更新，以反映当前活动视图，从而帮助提供用户在应用中的当前位置的清晰上下文。

Frame 的标题可以通过注解静态设置，也可以在运行时通过代码动态设置。这种灵活的方法使开发者能够根据每个视图的目的定义标题，同时根据特定场景或参数进行调整。

## 使用注解设置 Frame 标题 {#frame-title-with-annotations}

设置视图中 Frame 标题的最简单方法是使用 `@FrameTitle` 注解。这个注解允许你为任何路由组件定义一个静态标题，当组件渲染时，这个标题会被应用到 Frame 上。

### 使用 `@FrameTitle` 注解 {#using-the-frametitle-annotation}

`@FrameTitle` 注解应用在类级别，允许你指定一个字符串值，表示页面的标题。当路由器导航到具有此注解的组件时，指定的标题将自动设置为浏览器窗口的标题。

以下是一个示例：

```java
@Route
@FrameTitle("仪表盘")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // 视图逻辑
  }
}
```

在这个例子中：
- `DashboardView` 类用 `@Route` 注解定义路由。
- `@FrameTitle("仪表盘")` 注解将 Frame 标题设置为 "仪表盘"。
- 当用户导航到 `/dashboard` 时，Frame 的标题会自动更新为指定的值。

这种方法适用于具有静态标题且不需要频繁根据路由上下文更新的路由。

:::tip `@AppTitle` 和 `@FrameTitle`  
如果设置了应用标题，Frame 标题将包含它。例如，如果应用定义标题为 `@AppTitle("webforJ")`，而 Frame 标题设置为 `@FrameTitle("仪表盘")`，最终页面标题将为 `仪表盘 - webforJ`。如果需要，您可以在 `@AppTitle` 注解中使用 `format` 属性自定义最终标题的格式。  
:::

## 动态 Frame 标题 {#dynamic-frame-titles}

在需要根据应用状态或路由参数动态更改 Frame 标题的情况下，webforJ 提供了一个名为 `HasFrameTitle` 的接口。该接口允许组件根据当前导航上下文和路由参数提供 Frame 标题。

### 实现 `HasFrameTitle` 接口 {#implementing-the-hasframetitle-interface}

`HasFrameTitle` 接口包含一个方法 `getFrameTitle()`，在 Frame 的标题更新之前被调用。这个方法提供了根据导航上下文或其他动态因素动态生成标题的灵活性。

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {

  public ProfileView() {
    getBoundComponent().add(new H1("个人资料页面"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // 使用路由参数动态设置 Frame 标题
    String userId = parameters.get("id").orElse("未知");
    return "个人资料 - 用户 " + userId;
  }
}
```

在这个例子中：
- `ProfileView` 组件实现了 `HasFrameTitle` 接口。
- `getFrameTitle()` 方法使用 URL 中的 `id` 参数动态生成标题。
- 如果路由是 `/profile/123`，标题将更新为 "个人资料 - 用户 123"。

:::tip 结合注解和动态标题
可以结合使用静态和动态方法。如果路由组件同时具有 `@FrameTitle` 注解并实现了 `HasFrameTitle` 接口，则 `getFrameTitle()` 中动态提供的标题将优先于注解中的静态值。
:::
