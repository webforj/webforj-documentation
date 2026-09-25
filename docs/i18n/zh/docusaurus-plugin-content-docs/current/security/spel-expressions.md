---
sidebar_position: 5
title: SpEL Expressions
description: >-
  Author route authorization rules with Spring Expression Language using
  @RouteAccess for role, authority, and custom checks.
_i18n_hash: 59601d0d83fe7eb4b05bf8eab47515c3
---
Spring 表达式语言（`SpEL`）提供了一种声明性方式，直接在注解中定义授权规则。`@RouteAccess` 注解使用 Spring Security 的内置授权函数评估 `SpEL` 表达式。

<!-- INTRO_END -->

:::info 仅限 Spring Security
`SpEL` 表达式仅在使用 Spring 集成时可用。
:::

`@RouteAccess` 注解接受一个 `SpEL` 表达式，该表达式评估为布尔值：

```java
@Route("/admin/dashboard")
@RouteAccess("hasRole('ADMIN')")
public class AdminDashboardView extends Composite<Div> {
  // 只有具有 ROLE_ADMIN 权限的用户才能访问
}
```

如果表达式评估为 `true`，则授予访问权限。如果为 `false`，用户将被重定向到访问拒绝页面。

## 内置安全功能 {#built-in-security-functions}

Spring Security 通过 `SecurityExpressionRoot` 提供以下授权函数：

| 函数 | 参数 | 描述 | 示例 |
|------|------|------|------|
| `hasRole` | `String role` | 检查用户是否具有指定的角色（自动添加前缀`ROLE_`） | `hasRole('ADMIN')` 匹配 `ROLE_ADMIN` |
| `hasAnyRole` | `String... roles` | 检查用户是否具有任何指定的角色 | `hasAnyRole('ADMIN', 'MANAGER')` |
| `hasAuthority` | `String authority` | 检查用户是否具有确切的权限字符串 | `hasAuthority('REPORTS:READ')` |
| `hasAnyAuthority` | `String... authorities` | 检查用户是否具有任何指定的权限 | `hasAnyAuthority('REPORTS:READ', 'REPORTS:WRITE')` |
| `isAuthenticated` | 无 | 如果用户已认证，则返回 `true` | `isAuthenticated()` |

### 示例 {#examples}

```java
// 角色检查
@Route("/admin")
@RouteAccess("hasRole('ADMIN')")
public class AdminView extends Composite<Div> { }

// 多个角色
@Route("/staff")
@RouteAccess("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
public class StaffView extends Composite<Div> { }

// 权限检查
@Route("/reports")
@RouteAccess("hasAuthority('REPORTS:READ')")
public class ReportsView extends Composite<Div> { }

// 需要认证
@Route("/profile")
@RouteAccess("isAuthenticated()")
public class ProfileView extends Composite<Div> { }
```

## 组合条件 {#combining-conditions}

使用布尔运算符（`and`、`or`、`!`）创建复杂的授权规则：

```java
// 两个条件都需满足
@Route("/moderator/reports")
@RouteAccess("hasRole('MODERATOR') and hasAuthority('REPORTS:VIEW')")
public class ModeratorReportsView extends Composite<Div> { }

// 任一条件可授予访问权限
@Route("/support")
@RouteAccess("hasRole('ADMIN') or hasRole('SUPPORT')")
public class SupportView extends Composite<Div> { }

// 否定
@Route("/trial/features")
@RouteAccess("isAuthenticated() and !hasAuthority('PREMIUM')")
public class TrialFeaturesView extends Composite<Div> { }

// 复杂的多行表达式
@Route("/reports/advanced")
@RouteAccess("""
  hasRole('ADMIN') or
  (hasRole('ANALYST') and hasAuthority('REPORTS:ADVANCED'))
  """)
public class AdvancedReportsView extends Composite<Div> { }
```

## 与其他注解组合 {#combining-with-other-annotations}

`@RouteAccess` 与标准安全注解一起工作。评估器按优先顺序运行：

```java
@Route("/team/admin")
@RolesAllowed("USER")
@RouteAccess("hasAuthority('TEAM:ADMIN')")
public class TeamAdminView extends Composite<Div> {
  // 必须同时具有 USER 角色和 TEAM:ADMIN 权限
}
```

评估顺序：
1. `@RolesAllowed` 评估器（优先级 5）验证 `USER` 角色
2. 如果通过，则 `@RouteAccess` 评估器（优先级 6）评估 `SpEL` 表达式
3. 如果通过，运行自定义评估器（优先级 10+）

## 自定义错误代码 {#custom-error-codes}

为访问拒绝提供有意义的错误代码：

```java
@Route("/premium/features")
@RouteAccess(
  value = "hasAuthority('PREMIUM')",
  code = "PREMIUM_SUBSCRIPTION_REQUIRED"
)
public class PremiumFeaturesView extends Composite<Div> { }
```

`code` 参数在表达式评估为 `false` 时标识拒绝原因。

## 可用变量 {#available-variables}

`SpEL` 表达式可以访问评估上下文中的以下变量：

| 变量 | 类型 | 描述 |
|------|------|------|
| `authentication` | `Authentication` | Spring Security 认证对象 |
| `principal` | `Object` | 当前认证的主体（通常是 `UserDetails`） |
| `routeClass` | `Class<? extends Component>` | 正在访问的路由组件类 |
| `context` | `NavigationContext` | webforJ 导航上下文 |
| `securityContext` | `RouteSecurityContext` | webforJ 路由安全上下文 |

使用变量的示例：

```java
@Route("/admin")
@RouteAccess("authentication.name == 'superadmin'")
public class SuperAdminView extends Composite<Div> { }
```

## 何时使用 `SpEL` 与自定义评估器 {#when-to-use-spel-vs-custom-evaluators}

**在以下情况下使用 `@RouteAccess` `SpEL`：**
- 授权纯粹基于角色或权限
- 将内置安全功能与布尔逻辑组合
- 不需要重用的路由特定规则

**在以下情况下使用自定义评估器：**
- 授权依赖于路由参数（所有权检查）
- 需要 Spring 服务集成的复杂业务逻辑
- 多个路由之间可重用的授权模式
- 文档化授权意图的自定义注解

请参阅 [自定义评估器指南](/docs/security/custom-evaluators) 以实现高级授权场景。
