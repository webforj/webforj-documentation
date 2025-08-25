---
title: Scopes  
sidebar_position: 16
---

<!-- vale off -->
# Scopes <DocChip chip='since' label='25.03' />
<!-- vale on -->

Spring manages bean lifecycle through scopes. Each scope defines when a bean is created, how long it lives, and when it's destroyed. webforJ adds two custom scopes - `@EnvironmentScope` and `@RouteScope` - that map to how webforJ applications handle browser sessions and navigation.

:::tip[Learn more about Spring scopes]
For comprehensive coverage of Spring's scoping mechanism and standard scopes, see [Spring's bean scopes documentation](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Environment scope {#environment-scope}

The `@EnvironmentScope` annotation creates beans that live for the duration of a browser window or tab session. When a user opens the app in a browser window or tab, webforJ creates an Environment. Any bean marked with `@EnvironmentScope` is created once per browser window/tab and remains available until the user closes the tab or the session expires.

Each Environment represents an isolated browser window or tab. Environment-scoped beans can't be shared between different browser windows or tabs - each window/tab receives its own instance.

Add `@EnvironmentScope` to any Spring component:

```java title="UserSession.java" {2}
@Component
@EnvironmentScope
public class UserSession {
  private String userId;
  private Map<String, Object> attributes = new HashMap<>();
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getUserId() {
    return userId;
  }
  
  public void setAttribute(String key, Object value) {
    attributes.put(key, value);
  }
  
  public Object getAttribute(String key) {
    return attributes.get(key);
  }
}
```

The `UserSession` bean maintains state throughout the lifetime of a browser window or tab. Each browser window/tab receives an isolated instance.

### Using environment-scoped beans {#using-environment-scoped-beans}

Routes receive environment-scoped beans through constructor injection:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  public DashboardView(UserSession session) {
    String userId = session.getUserId();
    // Use the session data
    if (userId == null) {
      // Redirect to login
    }
  }
}

@Route("/profile")
public class ProfileView extends Composite<Div> {
  
  public ProfileView(UserSession session) {
    // Same UserSession instance as DashboardView
    session.setAttribute("lastView", "profile");
  }
}
```

Spring injects the same `UserSession` instance into both views for the same browser window/tab. Navigation between dashboard and profile preserves the session instance. If the user opens the app in a new browser window or tab, that window receives its own distinct `UserSession` instance.

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

```java
@Route("/admin")
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

The annotation accepts a single parameter - the component class that should serve as the root for sharing. Only this component and its descendants in the route hierarchy can access the bean. Parent components and sibling hierarchies can't inject it.