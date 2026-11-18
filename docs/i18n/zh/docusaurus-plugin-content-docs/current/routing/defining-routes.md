---
sidebar_position: 3
title: 定义路由
description: 使用 @Route 注解将 URL 映射到 webforJ 组件，并通过 @RouteAlias 为同一视图定义多个路径。
_i18n_title_hash: 2d0483918fc7d51246d68af1677b6b67
_i18n_description_hash: 7b8fcb2501a652a3bec3f747bc28428b
_i18n_hash: 4f7189d5ef27386506e9ecf950f145ed
---
定义路由对于将 URL 映射到特定组件是至关重要的。这使您能够根据 URL 结构控制 UI 的不同部分如何渲染。该框架使用 `@Route` 注释使此过程声明式和简单，从而减少手动配置的需要。

:::info 路由注册
路由可以通过 `@Route` 注释静态注册或通过 `RouteRegistry` API 动态注册。有关更多信息，请参阅 [路由注册文档](./routes-registration)。
:::

## 使用 `@Route` 定义路由 {#defining-a-route-with-route}

`@Route` 注释用于将 URL 路径绑定到特定组件。这允许在应用程序导航到该 URL 时渲染该组件。以下是一个简单的示例：

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // 组件逻辑在此
}
```

在此示例中：
- `DashboardView` 组件绑定到 `/dashboard` URL。
- 当用户导航到 `/dashboard` 时，`DashboardView` 将由框架动态渲染。

### `value` 参数 {#the-value-parameter}

`@Route` 注释中的 `value` 参数定义了 URL 路径。此路径可以是像 `"dashboard"` 这样的静态路径，也可以更动态，从而允许灵活的路由。

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // 组件逻辑在此
}
```

在这种情况下，导航到 `/user/123` 将显示 `UserView`。

:::tip 路由模式
`user/:id` 被称为路由模式。路由器可以处理简单模式（匹配单个静态段）和复杂模式（可以匹配多个静态、必需和可选段）。有关配置模式的更多信息，请参阅 [深入了解路由模式](./route-patterns)。
:::

## 定义路由别名 {#defining-route-aliases}

在某些情况下，您可能希望允许多个 URL 指向同一个组件。例如，您可能希望用户能够通过 `/profile` 或 `/user/me` 访问他们的个人资料。webforJ 通过 **`@RouteAlias`** 注释允许这种情况，使您能够为单一路由定义多个别名。

以下是一个示例，其中组件可以通过 `/profile` 和 `/user/me` 访问：

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // 组件逻辑在此
}
```

定义路由别名增加了您导航设计的灵活性，允许用户通过不同的 URL 访问相同的内容。
