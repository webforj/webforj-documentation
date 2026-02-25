---
title: Integrating the AppLayout
sidebar_position: 7
draft: true
description: Step 6 - Use the AppLayout component.
---

In this step, you will integrate the features implemented in previous steps, such as routing and views, into a cohesive app layout. This structure will provide a unified navigation system and dynamic content areas.

## Purpose of the app Layout {#purpose-of-the-app-layout}

The `AppLayout` serves as the foundation for managing the overall structure and flow of your app. It provides:
- **Global Navigation**: A consistent way to switch between key sections.
- **Dynamic Content Rendering**: A centralized layout for displaying routed views.

## Using `AppNav` {#using-appnav}

The `AppNav` component is used to create a navigation menu within the app's UI. This menu provides links to different views in your app, such as the `DemoView`:

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Dashboard", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

In this example:
- The navigation menu is added to the app's drawer.
- Each menu item is an `AppNavItem` that specifies:
  - The label, for example "Dashboard."
  - The target view for example `DemoView`.
  - An optional icon for example a columns icon.

## Layout routes and outlets {#layout-routes-and-outlets}

The layout uses routes and outlets to dynamically render content within a structured layout. In webforJ:
- **Routes** define how views map to specific paths.
- **Outlets** act as placeholders in layouts where the routed views are displayed.

### Example: Setting up a layout route {#example-setting-up-a-layout-route}

In the `MainLayout` class, the `@Route` annotation defines it as the base layout, and the `DemoView` is rendered through an outlet in this layout:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

The `@Route` annotation for `DemoView` specifies that it uses `MainLayout` as its outlet:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
    // DemoView logic
}
```

## Adding dynamic content with `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

A `RouteOutlet` dynamically displays views based on the active route. In the layout, views like `DemoView` are rendered through the `RouteOutlet`. While the `RouteOutlet` is implicitly handled by the outlet specification in the route annotations.

