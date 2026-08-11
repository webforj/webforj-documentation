---
sidebar_position: 10
title: Navigational Frame Titles
description: >-
  Set browser frame titles per route with the @FrameTitle annotation or generate
  them dynamically using HasFrameTitle.
_i18n_hash: 7b190f89d8eeb58df6d8a25ce863cc5e
---
在webforJ中，所有路由都在一个框架内渲染，该框架作为一个顶层容器，负责显示当前路由的内容。当用户在不同路由之间导航时，框架标题会动态更新，以反映当前活动视图，帮助提供用户在应用程序中的当前位置的清晰上下文。

框架的标题可以通过注解静态设置，也可以通过代码在运行时动态设置。这种灵活的方法允许开发人员定义与每个视图的目的相一致的标题，同时根据需要适应特定场景或参数。

## 使用注解设置框架标题 {#frame-title-with-annotations}

在视图中设置框架标题的最简单方法是使用`@FrameTitle`注解。此注解允许您为任何路由组件定义一个静态标题，该标题将在组件渲染时应用到框架。

### 使用`@FrameTitle`注解 {#using-the-frametitle-annotation}

`@FrameTitle`注解应用于类级别，允许您指定一个表示页面标题的字符串值。当路由器导航到具有此注解的组件时，指定的标题将自动设置为浏览器窗口的标题。

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
- `DashboardView`类用`@Route`注解进行标注以定义路由。
<!-- vale Google.Quotes = NO -->
- `@FrameTitle("仪表板")`注解将框架标题设置为“仪表板”。
<!-- vale Google.Quotes = YES -->
- 当用户导航到`/dashboard`时，框架的标题将自动更新为指定的值。

这种方法对于具有静态标题且不需要根据路由上下文频繁更新的路由非常有用。

:::tip `@AppTitle`和`@FrameTitle`
如果设置了应用程序标题，则框架标题将结合使用。例如，如果应用程序将标题定义为`@AppTitle("webforJ")`，而框架标题设置为`@FrameTitle("仪表板")`，最终页面标题将为`仪表板 - webforJ`。如果需要，您可以在`@AppTitle`注解中使用`format`属性自定义最终标题的格式。
:::

## 动态框架标题 {#dynamic-frame-titles}

在需要根据应用程序状态或路由参数动态更改框架标题的情况下，webforJ提供了一个名为`HasFrameTitle`的接口。此接口允许组件根据当前导航上下文和路由参数提供框架标题。

### 实现`HasFrameTitle`接口 {#implementing-the-hasframetitle-interface}

`HasFrameTitle`接口包含一个名为`getFrameTitle()`的方法，在更新框架标题之前调用。此方法提供灵活性，可以根据导航上下文或其他动态因素动态生成标题。

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {
  private final Div self = getBoundComponent();

  public ProfileView() {
    self.add(new H1("个人资料页面"));
  }

  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // 使用路由参数动态设置框架标题
    String userId = parameters.get("id").orElse("未知");
    return "个人资料 - 用户 " + userId;
  }
}
```

在这个示例中：
- `ProfileView`组件实现了`HasFrameTitle`接口。
- `getFrameTitle()`方法使用URL中的`id`参数动态生成标题。
<!-- vale Google.Quotes = NO -->
- 如果路由为`/profile/123`，标题将更新为“个人资料 - 用户 123”。
<!-- vale Google.Quotes = YES -->

:::tip 结合注解和动态标题
您可以结合静态和动态方法。如果路由组件同时具有`@FrameTitle`注解并实现`HasFrameTitle`接口，则从`getFrameTitle()`动态提供的标题将优先于注解中的静态值。
:::
