---
sidebar_position: 3
title: Route Outlets
---

An **outlet** is a designated component, either a [route layout](./route-types#layout-routes) or a [route view](./route-types#view-routes), where child routes are dynamically rendered. It defines where the content of the child route will appear within the parent route. Outlets are fundamental to creating modular, nested UIs and flexible navigation structures.

## Defining an outlet

Outlets are typically implemented using container components that can hold and manage child content. In webforJ, any component that implements the `HasComponents` interface, or a composite of such components, can serve as an outlet. For example, [`FlexLayout`](../../components/flex-layout) implements the `HasComponents` interface, making it a valid outlet for child routes.

If no outlet is explicitly defined for a route, the first `Frame` of the app is used as the default outlet. This behavior ensures that every child route has a place to be rendered.

:::tip Frame Management
In applications with multiple frames, you can specify which frame to use as the outlet for child routes by setting the `frame` attribute in the `@Route` annotation. The `frame` attribute accepts the name of the frame to be used for rendering.
:::

### Example:

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
```

In this example:

- `MainLayout` acts as the layout container, but since no specific outlet is defined, the app's default `Frame` is used.
- The `DashboardView` is rendered within `MainLayout` using the default outlet (content area) of the `AppLayout`.

Thus, child routes of `MainLayout` will automatically be rendered in the content slot of `AppLayout`, unless a different outlet or frame is specified.

## Outlet lifecycle

Outlets are closely tied to the lifecycle of routes. When the active route changes, the outlet updates its content dynamically by injecting the appropriate child component and removing any components that are no longer needed. This ensures that only the relevant views are rendered at any given time.

- **Creation**: Outlets are initialized before child components are created.
- **Content Injection**: When a child route is matched, its component is injected into the outlet.
- **Updating**: When navigating between routes, the outlet updates its content, injecting the new child component and removing any outdated components.

## Custom outlets

The `RouteOutlet` interface is responsible for managing the lifecycle of route components, determining how components are rendered and removed. Any component that implements this interface can act as an outlet for other components.

### Key methods in `RouteOutlet`:

- **`showRouteContent(Component component)`**: Responsible for rendering the provided component in the outlet. This is called when the router matches a route, and the child component needs to be displayed.
- **`removeRouteContent(Component component)`**: Handles the removal of the component from the outlet, typically called when navigating away from the current route.

By implementing `RouteOutlet`, developers can control how routes are injected into specific areas of the app. for instance

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

In this example, the `MainLayout` class implements the `RouteOutlet` interface, allowing components to be added or removed from the AppLayout's drawer dynamically based on the route navigation instead
of the default content area defined in the `AppLayout` component

## Caching outlet components

By default, outlets dynamically add and remove components when navigating to and away from routes. However, in certain cases—particularly for views with complex components—it may be preferable to toggle the visibility of components rather than completely removing them from the DOM. This is where the `PersistentRouteOutlet` comes into play, allowing components to remain in memory and simply be hidden or shown, instead of being destroyed and recreated.

The `PersistentRouteOutlet` caches rendered components, keeping them in memory when the user navigates away. This improves performance by avoiding unnecessary component destruction and recreation, which is especially beneficial for applications where users frequently switch between views.

### How `PersistentRouteOutlet` works:

- **Component Caching**: It maintains an in-memory cache of all components that have been rendered within the outlet.
- **Visibility Toggle**: Instead of removing components from the DOM, it hides them when navigating away from a route.
- **Component Restoration**: When the user navigates back to a previously cached route, the component is simply shown again without the need for recreation.

This behavior is particularly useful for complex UIs where constant re-rendering of components can degrade performance. However, to make this toggling of visibility work, the managed components must implement the `HasVisibility` interface, which allows the `PersistentRouteOutlet` to control their visibility.

:::tip When to use `PersistentRouteOutlet`
Use `PersistentRouteOutlet` when creating and destroying components frequently leads to performance bottlenecks in your app. It's generally recommended to allow the default behavior of creating and destroying components during route transitions, as this helps avoid potential bugs and issues related to maintaining consistent state. However, in scenarios where performance is critical and components are complex or expensive to recreate, `PersistentRouteOutlet` can offer significant improvements by caching components and managing their visibility.
:::

### Example of `PersistentRouteOutlet` implementation:

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

In this example, `MainLayout` uses `PersistentRouteOutlet` to manage its child routes. When navigating between routes, components aren't removed from the DOM but instead hidden, ensuring that they remain available for quick re-rendering when the user navigates back. This approach significantly enhances performance, especially for views with complex content or heavy resource usage.
