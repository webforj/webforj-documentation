---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 7a3b1c6780e7032040802a936bfb83fb
---
在 webforJ 中，所有路由都在一个 Frame 中渲染，该 Frame 作为一个顶层容器，负责显示当前路由的内容。随着用户在不同路由之间导航，Frame 标题会动态更新以反映活动视图，帮助提供用户在应用程序中当前位置的清晰上下文。

Frame 的标题可以通过注释静态设置，或者在运行时通过代码动态设置。这种灵活的方法允许开发人员定义与每个视图的目的相一致的标题，同时根据需要适应特定的场景或参数。

## 使用注释设置 Frame 标题 {#frame-title-with-annotations}

在视图中设置 Frame 标题的最简单方法是使用 `@FrameTitle` 注释。此注释允许您为任何路由组件定义静态标题，该标题在组件渲染时应用于 Frame。

### 使用 `@FrameTitle` 注释 {#using-the-frametitle-annotation}

`@FrameTitle` 注释应用于类级别，允许您指定一个字符串值，该值代表页面的标题。当路由器导航到带有此注释的组件时，指定的标题将自动设置为浏览器窗口的标题。

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

在此示例中：
- `DashboardView` 类带有 `@Route` 注释，以定义路由。
<!-- vale Google.Quotes = NO -->
- `@FrameTitle("Dashboard")` 注释将 Frame 标题设置为 "Dashboard"。
<!-- vale Google.Quotes = YES -->
- 当用户导航到 `/dashboard` 时，Frame 的标题将自动更新为指定的值。

此方法对于具有静态标题且不需要根据路由上下文频繁更新的路由特别有用。

:::tip `@AppTitle` 和 `@FrameTitle`  
如果设置了应用标题，Frame 标题将包含它。例如，如果应用程序将标题定义为 `@AppTitle("webforJ")`，并且 Frame 标题设置为 `@FrameTitle("Dashboard")`，则最终页面标题将为 `Dashboard - webforJ`。如果需要，您可以通过使用 `format` 属性自定义 `@AppTitle` 注释中的最终标题格式。  
:::

## 动态 Frame 标题 {#dynamic-frame-titles}

在需要根据应用程序的状态或路由参数动态更改 Frame 标题的情况下，webforJ 提供了一个名为 `HasFrameTitle` 的接口。该接口允许组件根据当前导航上下文和路由参数提供 Frame 标题。

### 实现 `HasFrameTitle` 接口 {#implementing-the-hasframetitle-interface}

`HasFrameTitle` 接口包含一个方法 `getFrameTitle()`，在更新 Frame 标题之前调用。该方法提供了灵活性，根据导航上下文或其他动态因素动态生成标题。

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {
  private final Div self = getBoundComponent();

  public ProfileView() {
    self.add(new H1("Profile Page"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // 使用路由参数动态设置 Frame 标题
    String userId = parameters.get("id").orElse("Unknown");
    return "Profile - User " + userId;
  }
}
```

在此示例中：
- `ProfileView` 组件实现了 `HasFrameTitle` 接口。
- `getFrameTitle()` 方法使用 URL 中的 `id` 参数动态生成标题。
<!-- vale Google.Quotes = NO -->
- 如果路由为 `/profile/123`，标题将更新为 "Profile - User 123"。
<!-- vale Google.Quotes = YES -->

:::tip 结合注释和动态标题
您可以结合静态和动态方法。如果路由组件既有 `@FrameTitle` 注释，又实现了 `HasFrameTitle` 接口，则 `getFrameTitle()` 动态提供的标题将优先于注释中的静态值。
:::
