---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 5c431e57443e65c98f6f9b2e1098ad99
---
嵌套路由允许子路由在父路由内渲染，从而创建模块化和可重用的用户界面。父路由定义共享组件，而子路由则被注入到这些父组件内的特定插槽中。

## 定义嵌套路由 {#defining-nested-routes}

嵌套路由是通过 `@Route` 注解中的 `outlet` 参数创建的，该参数建立了父子关系。`outlet` 确定子组件将在父路由内的哪个位置渲染。

```java
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}

@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    getBoundComponent().add(new H1("Dashboard Content"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  public SettingsView() {
    getBoundComponent().add(new H1("Settings Content"));
  }
}
```

在这个例子中：

- `MainLayout` 是一个 **[布局路由](./route-types#layout-routes)**。
- `DashboardView` 是一个嵌套在 `MainLayout` 内的 **[视图路由](./route-types#view-routes)**。
- `SettingsView` 是一个嵌套在 `DashboardView` 内的 **[视图路由](./route-types#view-routes)**。

当导航到 `/dashboard/settings` 时，路由器：
1. 渲染 `MainLayout`。
2. 将 `DashboardView` 注入到 `MainLayout` 的插槽中。
3. 最后，将 `SettingsView` 注入到 `DashboardView` 的插槽中。

这种层次结构反映在 URL 中，每个部分表示组件层次中的一个级别：

- **URL**: `/dashboard/settings`
- **层次结构**:
  - `MainLayout`: 布局路由
  - `DashboardView`: 视图路由
  - `SettingsView`: 视图路由

这种结构确保了共享用户界面组件（例如标题或导航菜单）的整体一致性，同时允许这些布局内的内容动态变化。
