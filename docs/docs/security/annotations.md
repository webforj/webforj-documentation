---
sidebar_position: 3
title: Security Annotations
---

Security annotations provide a declarative way to control access to routes in your webforJ app. By adding annotations to your route components, you define who can access each view without writing manual permission checks. The security system automatically enforces these rules before any component is rendered.

:::info Implementation note
This guide works with any security implementation. The examples shown works with both Spring Security and custom implementations. If you're not using Spring Boot, see the [Security Architecture guide](/docs/security/architecture/overview) to understand the foundation and implement custom security.
:::

## `@AnonymousAccess` - public routes {#anonymousaccess-public-routes}

The `@AnonymousAccess` annotation marks a route as publicly accessible. Users don't need to be authenticated to access these routes. This annotation is typically used for login pages, public landing pages, or other content that should be available to everyone.

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {

  public LoginView() {
    // login view
  }
}
```

In this example:
- Any user, authenticated or not, can access the `/login` route.
- When `@AnonymousAccess` is present, unauthenticated users are allowed to access this page without being redirected.

:::tip Common use cases
Use `@AnonymousAccess` for login pages, registration pages, public homepages, terms of service, privacy policies, and any other content that should be accessible without authentication.
:::

## `@PermitAll` - authenticated routes {#permitall-authenticated-routes}

The `@PermitAll` annotation requires users to be authenticated but doesn't enforce any specific role requirements. Any logged-in user can access these routes, regardless of their roles or permissions.

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Inbox"));
  }
}
```

In this example:
- Users must be authenticated to access the inbox.
- Any authenticated user can view this page, regardless of their role.
- Unauthenticated users are redirected to the login page.

:::info Secure-by-default mode
When secure-by-default mode is enabled, routes without any security annotation behave the same as `@PermitAll`— they require authentication. See the [secure-by-default section](#secure-by-default) for details.
:::

## `@RolesAllowed` - role-based routes {#rolesallowed-role-based-routes}

The `@RolesAllowed` annotation restricts access to users with specific roles. You can specify one or more roles, and users must have at least one of the listed roles to access the route.

### Single role requirement {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {

  public TrashView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Trash"));
  }
}
```

In this example:
- Only users with the `ADMIN` role can access the trash view.
- Users without the `ADMIN` role are redirected to the access denied page.

### Multiple role requirements {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {

  public SettingsView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Explore("Settings"));
  }
}
```

In this example:
- Users with either the `ADMIN` or `MANAGER` role can access the settings.
- The user only needs one of the listed roles, not all of them.

:::tip Role naming conventions
Use uppercase role names (like `ADMIN`, `USER`, `MANAGER`) for consistency. This matches common security framework conventions and makes your code more readable.
:::

## `@DenyAll` - blocked routes {#denyall-blocked-routes}

The `@DenyAll` annotation blocks access to a route for all users, regardless of authentication status or roles. This is useful for temporarily disabling routes during maintenance or for routes that are under development.

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {

  public MaintenanceView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Paragraph("This page is under maintenance."));
  }
}
```

In this example:
- No user can access this route, even administrators.
- All access attempts result in redirection to the access denied page.

:::warning Temporary use
`@DenyAll` is typically used temporarily during development or maintenance. For production apps, consider removing the route entirely or using proper role restrictions instead.
:::

## What happens when access is denied {#what-happens-when-access-is-denied}

When a user attempts to access a route they're not authorized to view, the security system handles the denial automatically:

1. **Unauthenticated users**: Redirected to the login page configured in your security setup.
2. **Authenticated users without required roles**: Redirected to the access denied page.
3. **All users on `@DenyAll` routes**: Redirected to the access denied page.

You can customize these redirect locations to match your app's navigation structure so that access denials and authentication requests lead to the intended pages. See [Configure Spring Security](/docs/security/getting-started#configure-spring-security) for configuration details.

## Secure-by-default {#secure-by-default}

Secure-by-default is a configuration option that determines how routes without any security annotation are treated. When enabled, all routes require authentication by default unless explicitly marked with `@AnonymousAccess`.

### Enabled (recommended for production) {#enabled-recommended-for-production}

Add this to your `application.properties`:

```properties title="application.properties"
webforj.security.secure-by-default=true
```

With secure-by-default enabled:
- Routes without annotations require authentication (same as `@PermitAll`).
- Only `@AnonymousAccess` routes are publicly accessible.
- You must explicitly mark public routes, reducing the risk of accidentally exposing protected content.

```java
// Requires authentication (no annotation)
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// Public access (explicitly marked)
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### Disabled (allow-by-default) {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

With secure-by-default disabled:
- Routes without annotations are publicly accessible.
- You must explicitly add `@PermitAll` or `@RolesAllowed` to protect routes.
- Easier for development, but riskier for production.

```java
// Public access (no annotation)
@Route("/about")
public class AboutView extends Composite<Div> { }

// Requires authentication (explicitly marked)
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> { }
```

:::tip Best practice
Enable `secure-by-default` for production apps. With this setting, protected routes aren't exposed unless explicitly marked as public, reducing the risk of accidental exposure due to missing annotations. Only turn it off during initial development if you find the extra annotations cumbersome.
:::