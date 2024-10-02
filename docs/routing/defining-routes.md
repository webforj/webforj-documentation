---
sidebar_position: 3  
title: Defining Routes
---

Defining routes is essential for mapping URLs to specific components. This allows you to control how different parts of your UI are rendered based on the URL structure. The framework uses the `@Route` annotation to make this process declarative and straightforward, reducing the need for manual configuration.

:::info Routes Registration
Routes can be registered statically using the `@Route` annotation or dynamically through the `RouteRegistry` API. For more information, refer to the [Routes Registration documentation](./routes-registration).
:::

### Defining a route with `@Route`

The `@Route` annotation is used to bind a URL path to a specific component. This allows the component to be rendered whenever the app navigates to that URL. Here’s a simple example:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Component logic here
}
```

In this example:
- The `DashboardView` component is bound to the `/dashboard` URL.
- When a user navigates to `/dashboard`, the `DashboardView` will be dynamically rendered by the framework.

#### The `value` parameter

The `value` parameter in the `@Route` annotation defines the URL path. This can be a static path like `"dashboard"` or more dynamic, allowing for flexible routing.

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // Component logic here
}
```

In this case, navigating to `/user/123` will display the `UserView`.

:::tip Route Patterns
The `user/:id` is known as a route pattern. The router can handle both simple patterns, which match a single static segment, and complex patterns, which can match multiple static, required, and optional segments. For more information on configuring patterns, refer to the [deep dive into route patterns](./route-patterns).
:::

### Defining route aliases

In some cases, you might want to allow multiple URLs to point to the same component. For instance, you might want users to be able to access their profile through either `/profile` or `/user/me`. webforJ allows this through the **`@RouteAlias`** annotation, enabling you to define multiple aliases for a single route.

Here’s an example in which the component is accessible though both `/profile` and `/user/me`:

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // Component logic here
}
```

Defining route aliases increases flexibility in your navigation design, allowing users to access the same content through different URLs.

