---
sidebar_position: 3
title: Route Outlets
_i18n_hash: bab7ef02dabbb653741f7c8176913213
---
一个 **outlet** 是一个指定的组件，可以是 [路由布局](./route-types#layout-routes) 或 [路由视图](./route-types#view-routes)，子路由将在其上动态渲染。它定义了子路由的内容将在父路由中出现的位置。Outlets 对于创建模块化、嵌套的用户界面和灵活的导航结构是基本的。

## 定义 outlet {#defining-an-outlet}

Outlets 通常使用容器组件来实现，这些组件可以容纳和管理子内容。在 webforJ 中，任何实现 `HasComponents` 接口的组件，或由此类组件组合而成的组件，都可以作为 outlet。例如，[`FlexLayout`](../../components/flex-layout) 实现了 `HasComponents` 接口，使其成为子路由的有效 outlet。

如果没有明确为路由定义 outlet，则应用的第一个 `Frame` 被用作默认 outlet。此行为确保每个子路由都有一个可以渲染的位置。

:::tip 框架管理
在具有多个框架的应用程序中，可以通过在 `@Route` 注解中设置 `frame` 属性来指定使用哪个框架作为子路由的 outlet。`frame` 属性接受用于渲染的框架名称。
:::

### 示例: {#example}

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
    getBoundComponent().add(new H1("仪表板内容"));
  }
}
```

在这个例子中：

- `MainLayout` 作为布局容器，但由于没有定义特定的 outlet，因此使用应用的默认 `Frame`。
- `DashboardView` 在 `MainLayout` 中使用 `AppLayout` 的默认 outlet（内容区域）渲染。

因此，`MainLayout` 的子路由将自动在 `AppLayout` 的内容槽中渲染，除非指定了不同的 outlet 或框架。

## Outlet 生命周期 {#outlet-lifecycle}

Outlets 与路由的生命周期密切相关。当活动路由发生更改时，outlet 通过注入适当的子组件并移除不再需要的组件动态更新其内容。这确保了在任何给定时间仅渲染相关的视图。

- **创建**：在创建子组件之前初始化 outlets。
- **内容注入**：当匹配到子路由时，其组件被注入到 outlet 中。
- **更新**：在路由之间导航时，outlet 更新其内容，注入新的子组件并移除任何过时的组件。

## 自定义 outlets {#custom-outlets}

`RouteOutlet` 接口负责管理路由组件的生命周期，确定组件的渲染和移除方式。任何实现此接口的组件都可以作为其他组件的 outlet。

### `RouteOutlet` 中的关键方法: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**：负责在 outlet 中渲染提供的组件。当路由匹配，并且需要显示子组件时，会调用此方法。
- **`removeRouteContent(Component component)`**：处理从 outlet 中移除组件，通常在导航离开当前路由时调用。

通过实现 `RouteOutlet`，开发人员可以控制路由如何被注入到应用的特定区域。例如：

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {

  @Override
  public void showRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.remove(component);
  }
}
```

在这个例子中，`MainLayout` 类实现了 `RouteOutlet` 接口，允许根据路由导航动态向 AppLayout 的抽屉中添加或移除组件，而不是使用 `AppLayout` 组件中定义的默认内容区域。

## 缓存 outlet 组件 {#caching-outlet-components}

默认情况下，outlets 在导航到和离开路由时动态添加和移除组件。然而，在某些情况下，特别是对于具有复杂组件的视图，切换组件的可见性可能比完全从 DOM 中移除它们更可取。这时，`PersistentRouteOutlet` 允许组件保持在内存中，仅仅隐藏或显示，而不是被销毁和重新创建。

`PersistentRouteOutlet` 缓存渲染的组件，在用户导航离开时将它们保留在内存中。这通过避免不必要的组件销毁和重新创建来提升性能，这对于用户频繁在视图之间切换的应用程序尤其有益。

### `PersistentRouteOutlet` 的工作原理: {#how-persistentrouteoutlet-works}

- **组件缓存**：它保持一个所有在 outlet 中渲染过的组件的内存缓存。
- **可见性切换**：在离开路由时，它不会从 DOM 中移除组件，而是隐藏它们。
- **组件恢复**：当用户返回到之前缓存的路由时，该组件会再次显示，而无需重新创建。

这种行为对于复杂的用户界面特别有用，因为不断重新渲染组件会降低性能。然而，为了使这种可见性切换工作，管理的组件必须实现 `HasVisibility` 接口，这允许 `PersistentRouteOutlet` 控制它们的可见性。

:::tip 何时使用 `PersistentRouteOutlet`
当频繁创建和销毁组件导致应用的性能瓶颈时，请使用 `PersistentRouteOutlet`。通常建议在路由转换期间允许默认行为，即创建和销毁组件，因为这有助于避免与维护一致状态相关的潜在错误和问题。然而，在性能至关重要且组件复杂或重新创建成本高的场景中，`PersistentRouteOutlet` 通过缓存组件并管理其可见性，可以提供显著的改善。
:::

### `PersistentRouteOutlet` 实现示例: {#example-of-persistentrouteoutlet-implementation}

```java
@Route
public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  PersistentRouteOutlet outlet = new PersistentRouteOutlet(this);

  public MainLayout() {
    setHeader();
    setDrawer();
  }

  @Override
  public void removeRouteContent(Component component) {
    outlet.removeRouteContent(component);
  }

  @Override
  public void showRouteContent(Component component) {
    outlet.showRouteContent(component);
  }
}
```

在这个例子中，`MainLayout` 使用 `PersistentRouteOutlet` 来管理其子路由。在路由之间导航时，组件不会从 DOM 中移除，而是隐藏，确保它们在用户导航回时可以快速重新渲染。这种方法显著提升了性能，特别是对于内容复杂或资源使用量大的视图。
