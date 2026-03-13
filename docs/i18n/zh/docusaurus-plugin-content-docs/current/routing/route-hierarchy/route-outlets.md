---
sidebar_position: 3
title: Route Outlets
_i18n_hash: 8a64cd917fe9f1de3f37ee01254e80e7
---
一个 **outlet** 是一个指定的组件，可以是 [路由布局](./route-types#layout-routes) 或 [路由视图](./route-types#view-routes)，子路由将在此动态呈现。它定义了子路由的内容在父路由中的出现位置。 outlets 对创建模块化、嵌套 UI 和灵活导航结构至关重要。

## 定义 outlet {#defining-an-outlet}

outlets 通常使用可以容纳和管理子内容的容器组件来实现。在 webforJ 中，任何实现 `HasComponents` 接口的组件，或者这样的组件的组合，都可以作为 outlet。例如，[`FlexLayout`](../../components/flex-layout) 实现了 `HasComponents` 接口，成为子路由的有效 outlet。

如果没有为路由显式定义 outlet，则应用程序的第一个 `Frame` 用作默认 outlet。这种行为确保每个子路由都有一个可以呈现的地方。

:::tip 帧管理
在具有多个帧的应用程序中，您可以通过在 `@Route` 注释中设置 `frame` 属性来指定用于子路由的 outlet 的帧。`frame` 属性接受要用于渲染的帧的名称。
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
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("仪表板内容"));
  }
}
```

在此示例中：

- `MainLayout` 充当布局容器，但由于没有定义特定的 outlet，因此使用应用程序的默认 `Frame`。
- `DashboardView` 使用 `AppLayout` 的默认 outlet（内容区域）在 `MainLayout` 中呈现。

因此，`MainLayout` 的子路由将自动在 `AppLayout` 的内容插槽中呈现，除非指定了不同的 outlet 或帧。

## Outlet 生命周期 {#outlet-lifecycle}

outlets 与路由的生命周期密切相关。当活动路由发生变化时，outlet 会通过注入适当的子组件并移除任何不再需要的组件来动态更新其内容。这确保在任何给定时间仅呈现相关的视图。

- **创建**: 在创建子组件之前初始化 outlets。
- **内容注入**: 当匹配到子路由时，其组件被注入到 outlet 中。
- **更新**: 在路由之间导航时，outlet 更新其内容，注入新的子组件并移除任何过时的组件。

## 自定义 outlets {#custom-outlets}

`RouteOutlet` 接口负责管理路由组件的生命周期，确定组件如何呈现和移除。任何实现该接口的组件都可以充当其他组件的 outlet。

### `RouteOutlet` 中的关键方法: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: 负责在 outlet 中呈现提供的组件。当路由匹配时调用此方法，当需要显示子组件时调用。
- **`removeRouteContent(Component component)`**: 处理从 outlet 中移除组件，通常在离开当前路由时调用。

通过实现 `RouteOutlet`，开发人员可以控制如何将路由注入到应用程序的特定区域。例如：

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  private final AppLayout self = getBoundComponent();

  @Override
  public void showRouteContent(Component component) {
    self.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    self.remove(component);
  }
}
```

在此示例中，`MainLayout` 类实现了 `RouteOutlet` 接口，允许根据路由导航动态地向 `AppLayout` 的抽屉中添加或移除组件，而不是使用 `AppLayout` 组件中定义的默认内容区域。

## 缓存 outlet 组件 {#caching-outlet-components}

默认情况下，outlets 在导航到和离开路由时动态添加和移除组件。然而，在某些情况下——尤其是对于具有复杂组件的视图——切换组件的可见性而不是完全从 DOM 中移除它们可能更可取。这就是 `PersistentRouteOutlet` 发挥作用的地方，它允许组件保持在内存中，并简单地隐藏或显示，而不是被销毁和重新创建。

`PersistentRouteOutlet` 缓存渲染的组件，当用户导航离开时将它们保留在内存中。这通过避免不必要的组件销毁和重新创建来提高性能，特别是对于用户频繁在视图之间切换的应用程序来说尤为有益。

### `PersistentRouteOutlet` 的工作原理: {#how-persistentrouteoutlet-works}

- **组件缓存**: 它维护一个所有在 outlet 中渲染的组件的内存缓存。
- **可见性切换**: 在离开路由时，它不会从 DOM 中移除组件，而是将其隐藏。
- **组件恢复**: 当用户导航回先前缓存的路由时，组件会简单地再次显示，而无需重新创建。

这种行为对于复杂的 UI 特别有用，因为不断重新渲染组件可能会降低性能。然而，为使这种可见性切换能正常工作，所管理的组件必须实现 `HasVisibility` 接口，以允许 `PersistentRouteOutlet` 控制其可见性。

:::tip 何时使用 `PersistentRouteOutlet`
在创建和销毁组件频繁导致应用程序性能瓶颈时使用 `PersistentRouteOutlet`。一般建议允许在路由过渡期间创建和销毁组件的默认行为，因为这有助于避免与保持一致状态相关的潜在错误和问题。然而，在性能至关重要且组件复杂或重建成本高的场景中，`PersistentRouteOutlet` 通过缓存组件和管理其可见性可以提供显著的改进。
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

在此示例中，`MainLayout` 使用 `PersistentRouteOutlet` 来管理其子路由。在路由之间导航时，组件不会从 DOM 中移除，而是被隐藏，确保它们在用户返回时能够快速重新呈现。这种方法显著提高了性能，尤其是对于具有复杂内容或资源使用量大的视图。
