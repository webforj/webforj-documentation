---
sidebar_position: 11
title: Routes Registration
_i18n_hash: def139d3db58322c269afef10acdf5fd
---
除了[使用 `@Route` 注释注册路由](./defining-routes)之外，可以根据应用逻辑、用户角色或其他条件动态注册、更新或删除路由。这种灵活性使您能够更动态地管理导航，而不是在编译时静态定义路由。

## 动态注册路由 {#registering-routes-dynamically}

您可以使用 `RouteRegistry` 类动态注册路由，该类可以通过 `Router` 访问。这允许您在运行时添加新路由，从而实现灵活的导航。

### 示例：注册动态路由 {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// 动态注册设置路由
registry.register("/settings", SettingsView.class);

// 导航到设置视图
router.navigate(SettingsView.class);
```

在此示例中，`/settings` 路由被动态注册，应用程序导航到新注册的视图。

## 条件路由注册 {#conditional-route-registration}

通常，路由需要根据特定条件（例如用户角色或应用程序状态）进行添加或删除。通过动态路由，您可以在运行时根据条件注册或注销路由。

### 示例：基于用户角色的条件注册 {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// 检查用户角色并注册适当的路由
if (user.hasRole("editor")) {
  registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
  registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// 导航到适当的仪表板
if (user.hasRole("editor")) {
  router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
  router.navigate(ViewerDashboardView.class);
}
```

在此示例中：
- 根据用户的角色动态注册 `/editor/dashboard` 或 `/viewer/dashboard` 路由。
- 应用程序根据用户的访问权限导航到适当的仪表板。

## 移除路由 {#removing-routes}

就像路由可以动态添加一样，当不再需要时，也可以在运行时移除它们，或者当应用程序的上下文发生变化时。

### 示例：移除已注册路由 {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// 移除设置视图的路由
registry.unregister("/settings");

// 可选择按组件类移除
registry.unregister(SettingsView.class);
```

在此示例中，当不再需要时，`/settings` 路由被动态移除。

## 在应用启动时注册路由 {#registering-routes-at-app-startup}

您可以在应用初始化期间注册动态路由，使某些视图在启动时根据环境或配置可用。

### 示例：在应用启动期间注册路由 {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // 仅在开发模式下注册调试视图
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

在此示例中：
- 在应用启动时动态注册 `DebugView`，但仅当应用处于开发模式下运行时。

## 动态注册已注释 `@Route` 的组件 {#registering-route-annotated-components-dynamically}

除了手动定义路由之外，还可以动态注册已经用 `@Route` 注释的组件。当您希望利用预先注释的类，但根据应用逻辑动态注册它们时，这很有用。

### 示例：注册 `@Route` 注释的组件 {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
  // 个人资料视图逻辑
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// 使用其 @Route 注释动态注册 ProfileView
registry.register(ProfileView.class);

// 导航到 ProfileView
router.navigate(ProfileView.class);
```

在此示例中：
- `ProfileView` 类带有 `@Route("profile")` 注释。
- 使用 `registry.register(ProfileView.class)` 在运行时动态注册该路由。

## 从整个包注册路由 {#registering-routes-from-an-entire-package}

如果您的应用有大量的路由整理在一个包中，您可以动态注册来自该包的所有 `@Route` 注释的组件。

### 示例：从包中注册所有路由 {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// 注册 "com.myapp.admin" 包中的所有路由
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

在此示例中：
- `ofPackage` 方法扫描 `com.myapp.admin` 包并注册所有带有 `@Route` 注释的类。
- 这对于拥有大量路由并按包组织的大型应用程序特别有用。

:::info 自定义路由发现
从 25.11 开始，集成框架可以通过 `RouteRegistryProvider` SPI 提供自己的路由发现机制。这为动态注册路由提供了框架特定的功能，如依赖注入。有关详细信息，请参见[路由注册提供者](/docs/advanced/route-registry-provider)。
:::

## 检索已注册的路由 {#retrieving-registered-routes}

要检索所有动态注册路由的列表，请使用 `RouteRegistry` 类。这在您需要以编程方式管理或显示可用路由时非常有用。

### 示例：检索和显示所有已注册路由 {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Path: " + route.getPath()));
```

在此示例中，应用程序检索所有当前注册的路由并打印其路径。

## 动态管理路由别名 {#managing-route-aliases-dynamically}

webforJ 允许您为单个视图注册多个别名。这意味着用户可以通过不同的 URL 路径访问同一个视图。

### 示例：动态注册路由别名 {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// 注册主要路由
registry.register("/contact", ContactView.class);

// 注册联系视图的别名
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

在此示例中，`ContactView` 可以通过三种不同路径访问：`/contact`、`/support` 和 `/help`。
