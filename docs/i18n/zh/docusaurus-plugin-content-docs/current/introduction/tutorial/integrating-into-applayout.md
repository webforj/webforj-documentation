---
title: Integrating the AppLayout
sidebar_position: 7
draft: true
description: Step 6 - Use the AppLayout component.
_i18n_hash: fece87dce53e7e41102e122c740f6ea8
---
在这一步中，您将把之前步骤中实现的功能，如路由和视图，整合到一个统一的应用布局中。这种结构将提供统一的导航系统和动态内容区域。

## 运行应用 {#running-the-app}

在开发您的应用时，您可以使用 [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) 作为对比。要查看应用的运行情况：

1. 导航到包含 `pom.xml` 文件的顶级目录，这就是 `6-integrating-an-app-layout`，如果您正在按照 GitHub 上的版本进行操作。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用：
    ```bash
    mvn
    ```

运行应用将自动在 `http://localhost:8080` 打开一个新的浏览器窗口。

## 应用布局的目的 {#purpose-of-the-app-layout}

`AppLayout` 作为管理应用整体结构和流程的基础。它提供了：
- **全局导航**：一种在主要部分之间切换的一致方式。
- **动态内容渲染**：用于显示路由视图的集中布局。

## 使用 `AppNav` {#using-appnav}

`AppNav` 组件用于在应用的 UI 中创建导航菜单。此菜单提供指向应用不同视图的链接，如 `DemoView`：

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("仪表盘", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

在这个示例中：
- 导航菜单被添加到应用的抽屉中。
- 每个菜单项都是一个 `AppNavItem`，指定了：
  - 标签，例如 "仪表盘"。
  - 目标视图，例如 `DemoView`。
  - 可选图标，例如一个列的图标。

## 布局路由和插座 {#layout-routes-and-outlets}

布局使用路由和插座在结构化布局中动态渲染内容。在 webforJ 中：
- **路由** 定义视图如何映射到特定路径。
- **插座** 作为布局中的占位符，显示路由的视图。

### 示例：设置布局路由 {#example-setting-up-a-layout-route}

在 `MainLayout` 类中，`@Route` 注解将其定义为基本布局，并通过该布局中的插座渲染 `DemoView`：

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

`@Route` 注解对于 `DemoView` 指定它使用 `MainLayout` 作为其插座：

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
    // DemoView 逻辑
}
```

## 使用 `RouteOutlet` 添加动态内容 {#adding-dynamic-content-with-routeoutlet}

`RouteOutlet` 根据活动路由动态显示视图。在布局中，像 `DemoView` 这样的视图通过 `RouteOutlet` 渲染。虽然 `RouteOutlet` 是通过路由注释中的插座规范隐式处理的。
