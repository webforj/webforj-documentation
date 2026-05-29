---
title: Integrating the AppLayout
sidebar_position: 7
draft: true
description: Step 6 - Use the AppLayout component.
_i18n_hash: 14b409262af6d7a8a25a67278f687250
---
在此步骤中，您将把之前步骤中实现的功能，如路由和视图，集成到一个统一的应用布局中。这一结构将提供统一的导航系统和动态内容区域。

## 运行应用 {#running-the-app}

在开发您的应用时，您可以使用 [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) 作为对比。要查看应用的运行情况：

1. 导航到包含 `pom.xml` 文件的顶层目录，如果您按照 GitHub 上的版本操作，则为 `6-integrating-an-app-layout`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用：
    ```bash
    mvn
    ```

运行应用将自动打开一个新浏览器，地址为 `http://localhost:8080`。

## 应用布局的目的 {#purpose-of-the-app-layout}

`AppLayout` 作为管理应用整体结构和流程的基础。它提供：
- **全局导航**：一种一致的方式在关键部分之间切换。
- **动态内容渲染**：用于显示路由视图的集中布局。

## 使用 `AppNav` {#using-appnav}

`AppNav` 组件用于在应用的用户界面中创建导航菜单。该菜单提供不同视图的链接，例如 `DemoView`：

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
- 每个菜单项都是一个 `AppNavItem`，指定：
  - 标签，例如“仪表板”。
  - 目标视图，例如 `DemoView`。
  - 可选图标，例如一个列的图标。

## 布局路由和出口 {#layout-routes-and-outlets}

该布局使用路由和出口在结构化布局内动态渲染内容。在 webforJ 中：
- **路由** 定义视图如何映射到特定路径。
- **出口** 作为布局中的占位符，显示路由视图。

### 示例：设置布局路由 {#example-setting-up-a-layout-route}

在 `MainLayout` 类中，`@Route` 注释将其定义为基础布局，`DemoView` 通过该布局中的出口进行渲染：

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

`DemoView` 的 `@Route` 注释指定它使用 `MainLayout` 作为其出口：

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("演示")
public class DemoView extends Composite<Div> {
  // DemoView 逻辑
}
```

## 使用 `RouteOutlet` 添加动态内容 {#adding-dynamic-content-with-routeoutlet}

`RouteOutlet` 根据活动路由动态显示视图。在布局中，`DemoView` 等视图通过 `RouteOutlet` 渲染。`RouteOutlet` 是通过路由注释中的出口规范隐式处理的。
