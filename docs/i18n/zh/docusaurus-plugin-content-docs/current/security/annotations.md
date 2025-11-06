---
sidebar_position: 3
title: Security Annotations
_i18n_hash: af9997b8bee96bfa4005a65998fddaf5
---
安全注解提供了一种声明式方式来控制对你在 webforJ 应用中的路由的访问。通过在你的路由组件中添加注解，你可以定义谁可以访问每个视图，而无需编写手动权限检查。安全系统会在任何组件渲染之前自动强制执行这些规则。

:::info 实施说明
本指南适用于任何安全实现。所示示例适用于 Spring Security 和自定义实现。如果你不使用 Spring Boot，请参见 [安全架构指南](/docs/security/architecture/overview) 以了解基础并实现自定义安全。
:::

## `@AnonymousAccess` - 公开路由 {#anonymousaccess-public-routes}

`@AnonymousAccess` 注解标记一个路由为公开可访问。用户不需要经过身份验证即可访问这些路由。该注解通常用于登录页面、公共登录页面或任何其他应该向所有人开放的内容。

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
- 当存在 `@AnonymousAccess` 时，没有经过身份验证的用户可以访问此页面，而无需被重定向。

:::tip 常见用例
将 `@AnonymousAccess` 用于登录页面、注册页面、公共首页、服务条款、隐私政策和任何其他无需身份验证即可访问的内容。
:::

## `@PermitAll` - 身份验证路由 {#permitall-authenticated-routes}

`@PermitAll` 注解要求用户必须经过身份验证，但不强制执行任何特定的角色要求。任何登录用户都可以访问这些路由，无论他们的角色或权限如何。

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("收件箱"));
  }
}
```

在此示例中：
- 用户必须经过身份验证才能访问收件箱。
- 任何经过身份验证的用户都可以查看此页面，无论其角色如何。
- 未经过身份验证的用户会被重定向到登录页面。

:::info 默认安全模式
当启用默认安全模式时，没有任何安全注解的路由将表现得与 `@PermitAll` 一样——它们要求身份验证。有关详细信息，请参见 [默认安全部分](#secure-by-default)。
:::

## `@RolesAllowed` - 基于角色的路由 {#rolesallowed-role-based-routes}

`@RolesAllowed` 注解限制仅允许具有特定角色的用户访问。你可以指定一个或多个角色，用户必须拥有列出的至少一个角色才能访问该路由。

### 单一角色要求 {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {

  public TrashView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("垃圾箱"));
  }
}
```

在此示例中：
- 只有具有 `ADMIN` 角色的用户可以访问垃圾箱视图。
- 没有 `ADMIN` 角色的用户会被重定向到拒绝访问页面。

### 多重角色要求 {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {

  public SettingsView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Explore("设置"));
  }
}
```

在此示例中：
- 具有 `ADMIN` 或 `MANAGER` 角色的用户都可以访问设置。
- 用户只需要列出角色中的一个，而不是全部。

:::tip 角色命名约定
使用大写角色名称（如 `ADMIN`、`USER`、`MANAGER`）以保持一致。这与常见的安全框架约定相匹配，并使你的代码更具可读性。
:::

## `@DenyAll` - 被阻止的路由 {#denyall-blocked-routes}

`@DenyAll` 注解阻止所有用户访问一个路由，无论其身份验证状态或角色如何。这对于在维护期间临时禁用路由或正在开发中的路由非常有用。

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {

  public MaintenanceView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Paragraph("此页面正在维护。"));
  }
}
```

在此示例中：
- 没有用户可以访问此路由，即使是管理员。
- 所有访问尝试都会被重定向到拒绝访问页面。

:::warning 临时使用
`@DenyAll` 通常在开发或维护期间临时使用。对于生产应用，考虑完全删除该路由或使用适当的角色限制。
:::

## 访问被拒绝时发生什么 {#what-happens-when-access-is-denied}

当用户尝试访问他们没有权利查看的路由时，安全系统会自动处理拒绝：

1. **未经过身份验证的用户**：重定向到你在安全设置中配置的登录页面。
2. **没有所需角色的已验证用户**：重定向到拒绝访问页面。
3. **所有用户在 `@DenyAll` 路由上**：重定向到拒绝访问页面。

你可以自定义这些重定向位置，以匹配你应用的导航结构，使得访问拒绝和身份验证请求引导到预期的页面。有关配置详细信息，请参见 [配置 Spring Security](/docs/security/getting-started#configure-spring-security)。

## 默认安全 {#secure-by-default}

默认安全是一种配置选项，用于确定没有任何安全注解的路由如何处理。当启用时，所有路由默认需要身份验证，除非明确标记为 `@AnonymousAccess`。

### 启用（推荐用于生产） {#enabled-recommended-for-production}

将此添加到你的 `application.properties`：

```properties title="application.properties"
webforj.security.secure-by-default=true
```

启用默认安全后：
- 没有注解的路由需要身份验证（与 `@PermitAll` 相同）。
- 只有 `@AnonymousAccess` 路由可以公开访问。
- 你必须明确标记公共路由，从而降低意外暴露受保护内容的风险。

```java
// 需要身份验证（没有注解）
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// 公共访问（明确标记）
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### 禁用（默认允许） {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

禁用默认安全后：
- 没有注解的路由可以公开访问。
- 你必须明确添加 `@PermitAll` 或 `@RolesAllowed` 来保护路由。
- 对于开发更容易，但对于生产来说风险更大。

```java
// 公共访问（没有注解）
@Route("/about")
public class AboutView extends Composite<Div> { }

// 需要身份验证（明确标记）
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> { }
```

:::tip 最佳实践
为生产应用启用 `secure-by-default`。通过此设置，受保护的路由不会被暴露，除非明确标记为公共，从而减少因缺少注解而导致意外暴露的风险。除非你发现额外的注解繁琐，否则仅在初始开发期间关闭该设置。
:::
