---
sidebar_position: 2
title: Nested Routes
---

Nested routes allow child routes to be rendered within parent routes, creating a modular and reusable UI. Parent routes define shared components, while child routes are injected into specific outlets within these parent components.

## Defining nested routes

Nested routes are created using the `outlet` parameter in the `@Route` annotation, which establishes a parent-child relationship. The `outlet` determines where the child component will be rendered within the parent route.

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

In this example:

- `MainLayout` is a **[Layout Route](./route-types#layout-routes)**.
- `DashboardView` is a **[View Route](./route-types#view-routes)** nested inside `MainLayout`.
- `SettingsView` is a **[View Route](./route-types#view-routes)** nested inside `DashboardView`.

When navigating to `/dashboard/settings`, the router:
1. Renders `MainLayout`.
2. Injects `DashboardView` into the outlet of `MainLayout`.
3. Finally, injects `SettingsView` into the outlet of `DashboardView`.

This hierarchical structure is reflected in the URL, where each segment represents a level in the component hierarchy:

- **URL**: `/dashboard/settings`
- **Hierarchy**:
  - `MainLayout`: Layout Route
  - `DashboardView`: View Route
  - `SettingsView`: View Route

This structure ensures consistent shared UI components (such as headers or navigation menus) while allowing the content within those layouts to change dynamically.
