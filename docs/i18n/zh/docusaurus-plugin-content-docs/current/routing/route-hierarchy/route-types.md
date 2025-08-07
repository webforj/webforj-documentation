---
sidebar_position: 1
title: Route Types
_i18n_hash: d297703f4a165ebcfbb540c3256f825e
---
路由分为两种主要类型，**视图路由**和**布局路由**。路由类型的选择决定了组件如何映射到URL，以及它们如何与应用的其他部分交互。

## 视图路由 {#view-routes}

视图路由直接映射到URL段，并代表应用中的特定页面。这些路由反映在浏览器的URL中，通常用于独特的视图或页面。

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  public HomeView() {
    Div content = getBoundComponent();
    content.add(new H1("主页"));
  }
}
```

- **URL**: `/home`
- **渲染的组件**: `HomeView`

在这个例子中，导航到 `/home` 渲染 `HomeView` 组件。

## 布局路由 {#layout-routes}

布局路由包裹子视图，而不对URL产生贡献。布局提供了共享的UI元素，例如在多个视图中一致的头部或侧边栏。子路由将在布局的内容区域内渲染。

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

在这种情况下，`MainLayout` 是一个布局路由，它包裹子视图。它定义了公共UI元素，例如头部和抽屉。与此布局相关的子路由将被注入到 `AppLayout` 组件的内容区域。

## 路由类型的自动检测 {#auto-detection-of-route-types}

默认情况下，路由类型根据类名自动检测是否为**视图**或**布局**：

- 以 `Layout` 结尾的类被视为**布局路由**。
- 以 `View` 结尾的类被视为**视图路由**。

或者，开发人员可以通过在 `@Route` 注解中设置 `Route.Type` 来手动指定路由类型。

```java
// 自动检测为布局
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

```java
// 自动检测为视图
@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    Div content = getBoundComponent();
    content.add(new H1("仪表板内容"));
  }
}
```
