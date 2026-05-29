---
sidebar_position: 3
title: Security Annotations
_i18n_hash: 564a7d991d26edb972bc2c7c99366f37
---
安全注释提供了一种声明性的方法来控制您在 webforJ 应用中的路由访问。通过将注释添加到路由组件中，您定义了谁可以访问每个视图，而不需要写手动的权限检查。安全系统会在任何组件渲染之前自动强制执行这些规则。

:::info 实施注意事项
本指南适用于任何安全实现。所示示例适用于 Spring Security 和自定义实现。如果您未使用 Spring Boot，请参阅 [安全架构指南](/docs/security/architecture/overview) 以了解基础并实现自定义安全性。
:::

## `@AnonymousAccess` - 公开路由 {#anonymousaccess-public-routes}

`@AnonymousAccess` 注释将路由标记为公开可访问。用户无需进行身份验证即可访问这些路由。通常用于登录页面、公共着陆页或应向所有人提供的其他内容。

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {

  public LoginView() {
    // 登录视图
  }
}
```

在此示例中：
- 任何用户，无论是否经过身份验证，都可以访问 `/login` 路由。
- 当存在 `@AnonymousAccess` 时，未经过身份验证的用户可以访问此页面，而不会被重定向。

:::tip 常见用例
在登录页面、注册页面、公共主页、服务条款、隐私政策以及任何其他无需身份验证即可访问的内容中使用 `@AnonymousAccess`。
:::

## `@PermitAll` - 经过身份验证的路由 {#permitall-authenticated-routes}

`@PermitAll` 注释要求用户经过身份验证，但不强制任何特定的角色要求。任何已登录的用户均可访问这些路由，无论其角色或权限如何。

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.add(new Explore("收件箱"));
  }
}
```

在此示例中：
- 用户必须经过身份验证才能访问收件箱。
- 任何经过身份验证的用户都可以查看此页面，无论其角色如何。
- 未经过身份验证的用户会被重定向到登录页面。

:::info 默认安全模式
当启用默认安全模式时，没有任何安全注释的路由行为与 `@PermitAll` 相同——它们需要身份验证。有关详细信息，请参见 [默认安全部分](#secure-by-default)。
:::

## `@RolesAllowed` - 基于角色的路由 {#rolesallowed-role-based-routes}

`@RolesAllowed` 注释限制特定角色用户的访问。您可以指定一个或多个角色，用户必须至少具有列出的一个角色才能访问该路由。

### 单个角色要求 {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TrashView() {
    self.setHeight("100%");
    self.add(new Explore("垃圾箱"));
  }
}
```

在此示例中：
- 仅具有 `ADMIN` 角色的用户可以访问垃圾箱视图。
- 没有 `ADMIN` 角色的用户将被重定向到访问被拒绝页面。

### 多个角色要求 {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public SettingsView() {
    self.add(new Explore("设置"));
  }
}
```

在此示例中：
- 具有 `ADMIN` 或 `MANAGER` 角色的用户均可访问设置。
- 用户只需具有列出的一个角色，而不是所有角色。

:::tip 角色命名约定
使用大写角色名称（如 `ADMIN`、`USER`、`MANAGER`）以保持一致。这符合通用安全框架的约定，使您的代码更具可读性。
:::

## `@DenyAll` - 被阻止的路由 {#denyall-blocked-routes}

`@DenyAll` 注释阻止所有用户访问某个路由，无论其身份验证状态或角色。这对于在维护期间临时禁用路由或正在开发中的路由非常有用。

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public MaintenanceView() {
    self.add(new Paragraph("此页面正在维护中。"));
  }
}
```

在此示例中：
- 没有用户可以访问此路由，即使是管理员。
- 所有访问尝试都会重定向到访问被拒绝页面。

:::warning 临时使用
`@DenyAll` 通常在开发或维护期间临时使用。对于生产应用，考虑完全删除路由或使用适当的角色限制。
:::

## 当访问被拒绝时会发生什么 {#what-happens-when-access-is-denied}

当用户尝试访问他们没有授权查看的路由时，安全系统会自动处理拒绝：

1. **未经过身份验证的用户**：重定向到您在安全设置中配置的登录页面。
2. **未具有所需角色的经过身份验证的用户**：重定向到访问被拒绝页面。
3. **所有用户在 `@DenyAll` 路由上**：重定向到访问被拒绝页面。

您可以自定义这些重定向位置以匹配您应用的导航结构，以使访问拒绝和身份验证请求指向意图页面。有关配置详细信息，请参见 [配置 Spring Security](/docs/security/getting-started#configure-spring-security)。

## 默认安全 {#secure-by-default}

默认安全是一个配置选项，确定没有任何安全注释的路由的处理方式。启用时，所有路由默认需要身份验证，除非明确标记为 `@AnonymousAccess`。

### 启用（推荐用于生产） {#enabled-recommended-for-production}

将此添加到您的 `application.properties`：

```properties title="application.properties"
webforj.security.secure-by-default=true
```

启用默认安全后：
- 没有注释的路由需要身份验证（与 `@PermitAll` 相同）。
- 只有 `@AnonymousAccess` 路由可以公开访问。
- 您必须显式标记公共路由，从而减少意外曝光受保护内容的风险。

```java
// 需要身份验证（没有注释）
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// 公开访问（显式标记）
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### 禁用（默认允许） {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

禁用默认安全后：
- 没有注释的路由可以公开访问。
- 您必须显式添加 `@PermitAll` 或 `@RolesAllowed` 来保护路由。
- 对于开发更容易，但对生产风险更大。

```java
// 公开访问（没有注释）
@Route("/about")
public class AboutView extends Composite<Div> { }

// 需要身份验证（显式标记）
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> { }
```

:::tip 最佳实践
对于生产应用，启用 `secure-by-default`。使用此设置，受保护的路由不会被公开，除非明确标记为公共，从而减少因缺少注释而意外曝光的风险。只有在初始开发期间，如果您觉得额外的注释繁琐，可以关闭此选项。
:::
