---
sidebar_position: 3
title: Defining Routes
_i18n_hash: 6d7133c5636f63b82b13dd0a07a97620
---
定义路由对映射 URLs 到特定组件至关重要。这使您能够控制 UI 的不同部分如何根据 URL 结构进行渲染。该框架使用 `@Route` 注解使此过程具有声明性和简单性，从而减少手动配置的需要。

:::info 路由注册
路由可以使用 `@Route` 注解静态注册，或通过 `RouteRegistry` API 动态注册。有关更多信息，请参阅 [路由注册文档](./routes-registration)。
:::

## 使用 `@Route` 定义路由 {#defining-a-route-with-route}

`@Route` 注解用于将 URL 路径绑定到特定组件。这允许在应用程序导航到该 URL 时渲染组件。下面是一个简单的示例：

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // 组件逻辑在这里
}
```

在此示例中：
- `DashboardView` 组件绑定到 `/dashboard` URL。
- 当用户导航到 `/dashboard` 时，`DashboardView` 将由框架动态渲染。

### `value` 参数 {#the-value-parameter}

`@Route` 注解中的 `value` 参数定义了 URL 路径。这可以是一个静态路径，如 `"dashboard"`，或更动态，允许灵活路由。

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // 组件逻辑在这里
}
```

在这种情况下，导航到 `/user/123` 将显示 `UserView`。

:::tip 路由模式
`user/:id` 被称为路由模式。路由器可以处理简单模式，匹配单个静态段，以及复杂模式，可以匹配多个静态、必需和可选段。有关配置模式的更多信息，请参阅 [深入了解路由模式](./route-patterns)。
:::

## 定义路由别名 {#defining-route-aliases}

在某些情况下，您可能希望允许多个 URL 指向同一个组件。例如，您可能希望用户能够通过 `/profile` 或 `/user/me` 访问他们的个人资料。webforJ 通过 **`@RouteAlias`** 注解允许这样做，使您能够为单个路由定义多个别名。

以下是一个示例，组件可以通过 `/profile` 和 `/user/me` 两条路径访问：

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // 组件逻辑在这里
}
```

定义路由别名增强了导航设计的灵活性，使用户能够通过不同的 URL 访问相同的内容。
