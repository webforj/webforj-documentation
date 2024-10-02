---
sidebar_position: 11
title: Routes Registration
---

In addition to [registering routes using the `@Route` annotations](./defining-routes), it's possible to dynamically register, update, or remove routes at runtime based on app logic, user roles, or other conditions. This flexibility enables you to manage navigation more dynamically, rather than statically defining routes at compile time.


## Registering routes dynamically

You can register a route dynamically using the `RouteRegistry` class, which is accessible via the `Router`. This allows you to add new routes during runtime, enabling flexible navigation.

### Example: Registering a dynamic route

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Register the settings route dynamically
registry.register("/settings", SettingsView.class);

// Navigate to the settings view
router.navigate(SettingsView.class);
```

In this example, the `/settings` route is dynamically registered, and the app navigates to the newly registered view.

## Conditional route registration

Often, routes need to be added or removed based on specific conditions such as user roles or the app's state. With dynamic routing, you can register or unregister routes conditionally at runtime.

### Example: Conditional registration based on user role

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Check user role and register appropriate routes
if (user.hasRole("editor")) {
    registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Navigate to the appropriate dashboard
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

In this example:
- The `/editor/dashboard` or `/viewer/dashboard` route is dynamically registered based on the user's role.
- The app navigates to the appropriate dashboard based on the user's access rights.

## Removing routes

Just as routes can be added dynamically, they can also be removed at runtime when they're no longer needed, or when the app’s context changes.

### Example: Removing a registered route

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Remove the route for the settings view
registry.unregister("/settings");

// Optionally, remove by component class
registry.unregister(SettingsView.class);
```

In this example, the `/settings` route is removed dynamically when it's no longer required.

## Registering Routes at app startup

You can register dynamic routes during app initialization, allowing certain views to be available based on the environment or configuration at startup.

### Example: Registering routes during app startup

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Register a debug view only in development mode
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

In this example:
- A `DebugView` is dynamically registered during app startup, but only if the app is running in development mode.

## Registering `@Route` annotated components dynamically

In addition to manually defining routes, it's possible to dynamically register components already annotated with `@Route`. This is useful when you want to leverage pre-annotated classes but register them dynamically based on app logic.

### Example: Registering an `@Route` annotated component

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Profile view logic
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Dynamically register the ProfileView with its @Route annotation
registry.register(ProfileView.class);

// Navigate to the ProfileView
router.navigate(ProfileView.class);
```

In this example:
- The `ProfileView` class is annotated with `@Route("profile")`.
- The route is dynamically registered at runtime using `registry.register(ProfileView.class)`.

## Registering routes from an entire package

If your app has a large number of routes organized within a package, you can register all `@Route`-annotated components from the package dynamically.

### Example: Registering all routes from a package

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Register all routes within the "com.myapp.admin" package
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

In this example:
- The `ofPackage` method scans the `com.myapp.admin` package and registers all classes annotated with `@Route`.
- This is particularly useful for large applications with numerous routes organized by packages.

## Retrieving registered routes

To retrieve a list of all dynamically registered routes, use the `RouteRegistry` class. This is useful when you need to programmatically manage or display available routes.

### Example: Retrieving and displaying all registered routes

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Path: " + route.getPath()));
```

In this example, the app retrieves all currently registered routes and prints their paths.

## Managing route aliases dynamically

webforJ allows you to register multiple aliases for a single view. This means users can access the same view using different URL paths.

### Example: Registering route aliases dynamically

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Register a primary route
registry.register("/contact", ContactView.class);

// Register aliases for the contact view
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

In this example, the `ContactView` is accessible via three different paths: `/contact`, `/support`, and `/help`.