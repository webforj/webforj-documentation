---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
---

<!-- vale off -->

# Scopes <DocChip chip='since' label='25.03' />

<!-- vale on -->

Spring manages bean lifecycle through scopes. Each scope defines when a bean is created, how long it lives, and when it's destroyed. In addition to standard Spring scopes, webforJ adds three custom scopes: `@WebforjSessionScope`, `@EnvironmentScope` and `@RouteScope`.

:::tip[Learn more about Spring scopes]
For comprehensive coverage of Spring's scoping mechanism and standard scopes, see [Spring's bean scopes documentation](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Overview

webforJ provides three custom scopes designed for web app state management:

- **`@WebforjSessionScope`**: Beans shared across all browser tabs/windows for the same user session. Perfect for authentication, user preferences, and shopping carts.
- **`@EnvironmentScope`**: Beans isolated to a single browser tab/window. Ideal for tab-specific workflows, form data, and independent document editing.
- **`@RouteScope`**: Beans shared within a route hierarchy. Useful for navigation state and data that should reset when users navigate between app sections.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Session scope {#session-scope}

The `@WebforjSessionScope` annotation creates beans that persist across the entire webforJ session. Unlike [environment scope](#environment-scope) which isolates beans per browser window/tab, session-scoped beans are shared across all windows and tabs from the same browser. These beans live as long as the webforJ session remains active, typically until the user logs out or the session expires.

Session scope is ideal for authentication state, user preferences, shopping carts, and data that should persist across multiple browser tabs but remain isolated between different users. Each user's browser session receives its own instance of session-scoped beans.

:::info Beans need to be Serializable
Session-scoped beans need to implement `Serializable` since they're stored in HTTP session attributes. All non-transient fields must also be serializable (primitives, `String`, or classes implementing `Serializable`). Mark fields as `transient` if they shouldn't be persisted.
:::

Add `@WebforjSessionScope` to any Spring component:

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // Authenticate user
    authenticatedUser = authenticate(username, password);
    loginTime = Instant.now();
  }

  public void logout() {
    authenticatedUser = null;
    loginTime = null;
    // Invalidate session
  }

  public boolean isAuthenticated() {
    return authenticatedUser != null;
  }

  public User getCurrentUser() {
    return authenticatedUser;
  }
}
```

### Session sharing across tabs {#session-sharing-across-tabs}

Session-scoped beans maintain state across all browser windows and tabs. Opening the app in multiple tabs shares the same bean instance:

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // User already logged in from another tab
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("Login");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // User is now logged in across all tabs
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // Same AuthenticationService instance across all tabs
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Display user dashboard
  }
}
```

When a user logs in through one tab, all other tabs immediately have access to the authenticated state. Opening new tabs or windows maintains the logged-in state. Logging out from any tab affects all tabs, since they share the same session-scoped bean.

## Environment scope {#environment-scope}

The `@EnvironmentScope` annotation creates beans that live for the duration of a browser window or tab session. When a user opens the app in a browser window or tab, webforJ creates an Environment. Any bean marked with `@EnvironmentScope` is created once per browser window/tab and remains available until the user closes the tab or the session expires.

Each Environment represents an isolated browser window or tab. Environment-scoped beans can't be shared between different browser windows or tabs because each window/tab receives its own instance.

Add `@EnvironmentScope` to any Spring component:

```java title="TabWorkspace.java" {2}
@Component
@EnvironmentScope
public class TabWorkspace {
  private String documentId;
  private Map<String, Object> workspaceData = new HashMap<>();

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setWorkspaceData(String key, Object value) {
    workspaceData.put(key, value);
  }

  public Object getWorkspaceData(String key) {
    return workspaceData.get(key);
  }
}
```

The `TabWorkspace` bean maintains state throughout the lifetime of a browser window or tab. Each browser window/tab receives an isolated instance.

### Using environment-scoped beans {#using-environment-scoped-beans}

Routes receive environment-scoped beans through constructor injection:

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // Load document for this tab
    if (documentId == null) {
      // Create new document
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // Same TabWorkspace instance as EditorView in this tab
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Preview the document being edited in this tab
  }
}
```

Spring injects the same `TabWorkspace` instance into both views for the same browser window/tab. Navigation between editor and preview preserves the workspace instance. If the user opens the app in a new browser window or tab, that window receives its own distinct `TabWorkspace` instance, allowing independent editing of different documents.

## Route scope {#route-scope}

The `@RouteScope` annotation creates beans shared within a route hierarchy. Navigation to `/admin/users` builds a component hierarchy with the admin view as parent and the users view as child. Route-scoped beans are instantiated once per hierarchy and shared between parent and child components.

Route scope differs from environment scope in granularity. While environment-scoped beans exist for the entire browser window/tab session, route-scoped beans exist only while the user remains within a specific route hierarchy. Navigating away from the hierarchy destroys the beans, and returning creates fresh instances. This scope is ideal for state that should reset when users navigate between different sections of your app.

Add `@RouteScope` to any Spring component:

```java title="NavigationState" {2}
@Component
@RouteScope
public class NavigationState {
  private String activeTab;
  private List<String> breadcrumbs = new ArrayList<>();

  public void setActiveTab(String tab) {
    this.activeTab = tab;
  }

  public void addBreadcrumb(String crumb) {
    breadcrumbs.add(crumb);
  }

  public List<String> getBreadcrumbs() {
    return Collections.unmodifiableList(breadcrumbs);
  }
}
```

### Route hierarchies and sharing {#route-hierarchies-and-sharing}

Routes form hierarchies through the `outlet` parameter. The parent route provides an outlet where child routes render. When you define a route with an outlet, webforJ constructs a component tree where the outlet component becomes the parent and the route component becomes the child. This parent-child relationship determines which components share route-scoped beans.

```java {11}
@Route
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("Home");
    navState.addBreadcrumb("Admin");
    // ...
  }
}

@Route(value = "users", outlet = AdminView.class)
public class UsersView extends Composite<Div> {

  public UsersView(NavigationState navState) {
    // Same NavigationState instance as AdminView
    navState.setActiveTab("users");
    navState.addBreadcrumb("Users");
  }
}
```

The `AdminView` and `UsersView` share the same `NavigationState` instance. The layout establishes the navigation structure while the view updates the active state. Navigation outside the `admin` section (to `/public` for instance) destroys the current `NavigationState` instance and creates a new one for the subsequent hierarchy.

The scope boundary follows the route tree structure. All components from the root of a hierarchy down to the leaves share the same route-scoped bean instances. Navigation to sibling routes within the same hierarchy preserves the beans, while navigation to unrelated hierarchies triggers bean destruction and recreation.

### Customizing scope boundaries with `@SharedFrom` {#customizing-scope-boundaries}

Route-scoped beans are shared from the topmost component by default. The `@SharedFrom` annotation specifies an alternative root component. This annotation changes where in the hierarchy a bean becomes available, allowing you to restrict access to specific subtrees of your route structure:

```java title="TeamContext" {2,3}
@Component
@RouteScope
@SharedFrom(TeamSection.class)
public class TeamContext {
  private String teamId;
  private List<String> permissions = new ArrayList<>();

  public void setTeamId(String id) {
    this.teamId = id;
  }

  public String getTeamId() {
    return teamId;
  }
}
```

The bean is accessible exclusively within `TeamSection` and its child components:

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {

  public TeamSection(TeamContext context) {
    // Bean created here
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Cannot inject TeamContext - it's scoped to TeamSection
    // Attempting injection throws IllegalStateException
  }
}
```

The `@SharedFrom` annotation enforces architectural boundaries. Components outside the specified scope can't access the bean. When Spring attempts to inject a `@SharedFrom` bean into a component outside its designated hierarchy, the injection fails with an `IllegalStateException`. This enforcement happens at runtime when the route is accessed, so beans remain properly scoped to their intended component trees.

The annotation accepts a single parameter: the component class that should serve as the root for sharing. Only this component and its descendants in the route hierarchy can access the bean. Parent components and sibling hierarchies can't inject it.
