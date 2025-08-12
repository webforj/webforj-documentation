---
title: Integrating the AppLayout
sidebar_position: 6
draft: true
_i18n_hash: c0ed4864dc99a4665aef3f4ff808bc9d
---
在此步骤中，您将把之前步骤中实现的功能（如路由和视图）集成到一个统一的应用布局中。该结构将提供统一的导航系统和动态内容区域。

## 应用布局的目的 {#purpose-of-the-app-layout}

`AppLayout` 作为管理应用整体结构和流程的基础。它提供：
- **全局导航**：在关键部分之间切换的一致方式。
- **动态内容渲染**：用于显示路由视图的集中布局。

## 使用 `AppNav` {#using-appnav}

`AppNav` 组件用于在应用的用户界面中创建导航菜单。该菜单提供指向应用中不同视图的链接，例如 `DemoView`：

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("仪表板", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

在此示例中：
- 导航菜单被添加到应用的抽屉中。
- 每个菜单项都是一个 `AppNavItem`，指定了：
  - 标签，例如“仪表板”。
  - 目标视图，例如 `DemoView`。
  - 可选图标，例如列图标。

## 布局路由和出口 {#layout-routes-and-outlets}

该布局使用路由和出口在结构化布局中动态渲染内容。在 webforJ 中：
- **路由** 定义视图与特定路径的对应关系。
- **出口** 作为布局中的占位符，用于显示路由视图。

### 示例：设置布局路由 {#example-setting-up-a-layout-route}

在 `MainLayout` 类中，`@Route` 注解将其定义为基础布局，而 `DemoView` 通过该布局中的出口进行渲染：

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

`DemoView` 的 `@Route` 注解指定它使用 `MainLayout` 作为其出口：

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("演示")
public class DemoView extends Composite<Div> {
    // DemoView 逻辑
}
```

## 使用 `RouteOutlet` 添加动态内容 {#adding-dynamic-content-with-routeoutlet}

`RouteOutlet` 根据活动路由动态显示视图。在布局中，像 `DemoView` 这样的视图通过 `RouteOutlet` 渲染。虽然 `RouteOutlet` 由路由注解中的出口规范隐式处理。
