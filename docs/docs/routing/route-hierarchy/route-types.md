---
sidebar_position: 1
title: Route Types
---

Routes are classified into two main types, **View Routes** and **Layout Routes**. The choice of route type determines how components are mapped to URLs and how they interact with other parts of your app.

## View routes

View routes map directly to a URL segment and represent specific pages in your app. These routes are reflected in the browser's URL and are typically used for distinct views or pages.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  public HomeView() {
    Div content = getBoundComponent();
    content.add(new H1("Home Page"));
  }
}
```

- **URL**: `/home`
- **Rendered Component**: `HomeView`

In this example, navigating to `/home` renders the `HomeView` component.

## Layout routes

Layout routes wrap child views without contributing to the URL. Layouts provide shared UI elements such as headers or sidebars that are consistent across multiple views. Child routes are rendered inside the layout’s content area.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

In this case, `MainLayout` is a layout route that wraps around child views. It defines common UI elements like a header and drawer. Child routes associated with this layout will be injected into the content area of the `AppLayout` component

## Auto-Detection of route types

By default, route type is automatically detected whether the route is a **view** or **layout** based on the class name:

- Classes ending in `Layout` are treated as **layout routes**.
- Classes ending in `View` are treated as **view routes**.

Alternatively, developers can manually specify the route type by setting `Route.Type` in the `@Route` annotation.

```java
// Automatically detected as Layout
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

```java
// Automatically detected as View
@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    Div content = getBoundComponent();
    content.add(new H1("Dashboard Content"));
  }
}
```