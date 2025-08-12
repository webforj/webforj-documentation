---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 0a4e46f57c88d52966be27b35070a027
---
在webforJ中，所有路由都在一个框架中呈现，该框架充当顶级容器，负责显示当前路由的内容。当用户在不同路由之间导航时，框架标题会动态更新，以反映活动视图，从而帮助提供用户在应用程序中的当前位置的清晰上下文。

框架的标题可以通过注解静态设置，或者通过代码在运行时动态设置。这种灵活的方法允许开发人员定义与各个视图的目的相一致的标题，同时根据特定场景或参数进行调整。

## 使用注解设置框架标题 {#frame-title-with-annotations}

在视图中设置框架标题的最简单方法是使用 `@FrameTitle` 注解。该注解允许您为任何路由组件定义静态标题，然后在组件渲染时应用于框架。

### 使用 `@FrameTitle` 注解 {#using-the-frametitle-annotation}

`@FrameTitle` 注解应用于类级别，允许您指定一个表示页面标题的字符串值。当路由器导航到带有此注解的组件时，指定的标题将自动设置为浏览器窗口的标题。

以下是一个示例：

```java
@Route
@FrameTitle("Dashboard")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // 视图逻辑
  }
}
```

在这个示例中：
- `DashboardView` 类使用 `@Route` 注解来定义路由。
- `@FrameTitle("Dashboard")` 注解将框架标题设置为 "Dashboard"。
- 当用户导航到 `/dashboard` 时，框架的标题将自动更新为指定的值。

这种方法适用于具有静态标题且不需要根据路由上下文频繁更新的路由。

:::tip `@AppTitle` 和 `@FrameTitle`  
如果设置了应用标题，则框架标题将包含该标题。例如，如果应用定义了标题为 `@AppTitle("webforJ")`，框架标题设置为 `@FrameTitle("Dashboard")`，则最终页面标题将为 `Dashboard - webforJ`。如果需要，您可以在 `@AppTitle` 注解中使用 `format` 属性自定义最终标题的格式。  
:::

## 动态框架标题 {#dynamic-frame-titles}

在需要根据应用状态或路由参数动态更改框架标题的情况下，webforJ提供了一个名为 `HasFrameTitle` 的接口。该接口允许组件根据当前导航上下文和路由参数提供框架标题。

### 实现 `HasFrameTitle` 接口 {#implementing-the-hasframetitle-interface}

`HasFrameTitle` 接口包含一个方法 `getFrameTitle()`，在框架的标题更新之前调用。该方法提供灵活性，以便根据导航上下文或其他动态因素动态生成标题。

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {

  public ProfileView() {
    getBoundComponent().add(new H1("Profile Page"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // 使用路由参数动态设置框架标题
    String userId = parameters.get("id").orElse("Unknown");
    return "Profile - User " + userId;
  }
}
```

在此示例中：
- `ProfileView` 组件实现了 `HasFrameTitle` 接口。
- `getFrameTitle()` 方法使用来自URL的 `id` 参数动态生成标题。
- 如果路由是 `/profile/123`，则标题将更新为 "Profile - User 123"。

:::tip 组合注解和动态标题
您可以结合静态和动态方法。如果路由组件同时具有 `@FrameTitle` 注解并实现 `HasFrameTitle` 接口，则 `getFrameTitle()` 动态提供的标题将优先于注解中的静态值。
:::
