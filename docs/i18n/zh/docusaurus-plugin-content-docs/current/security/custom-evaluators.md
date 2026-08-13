---
sidebar_position: 6
title: Custom Evaluators
description: >-
  Write custom RouteSecurityEvaluators for context-aware checks like ownership
  verification beyond role-based permissions.
_i18n_hash: d1edb7260efb2928e988a2cdf313f380
---
自定义评估器扩展了 webforJ 的安全系统，提供了超出基本身份验证和角色检查的专门访问控制逻辑。当您需要验证依赖于请求上下文的动态条件，而不仅仅是用户权限时，请使用它们。

:::info 聚焦于 Spring Security
本指南涵盖了 Spring Security 的自定义评估器。如果您不使用 Spring Boot，请参见 [评估器链指南](/docs/security/architecture/evaluator-chain) 以了解评估器的工作原理，并参见 [完整实现](/docs/security/architecture/custom-implementation) 获取工作示例。
:::

## 什么是自定义评估器 {#what-are-custom-evaluators}

评估器根据自定义逻辑确定用户是否可以访问特定路由。评估器会在导航期间进行检查，组件渲染之前，让您能够动态截取和控制访问。

webforJ 包含针对标准 Jakarta 注释的内置评估器：

- `AnonymousAccessEvaluator` - 处理 `@AnonymousAccess`
- `PermitAllEvaluator` - 处理 `@PermitAll`
- `RolesAllowedEvaluator` - 处理 `@RolesAllowed`
- `DenyAllEvaluator` - 处理 `@DenyAll`

自定义评估器遵循相同的模式，允许您创建自己的注释和访问控制逻辑。

:::tip[了解更多内置注释]
有关 `@AnonymousAccess`、`@PermitAll`、`@RolesAllowed` 和 `@DenyAll` 的详细信息，请参阅 [安全注释指南](/docs/security/annotations)。
:::

## 用例：所有权验证 {#use-case-ownership-verification}

一个常见的需求是允许用户仅访问自己的资源。例如，用户只能编辑自己的个人资料，而不是他人的个人资料。

**问题**：`@RolesAllowed("USER")` 授予所有已验证用户访问权限，但不验证用户是否正在访问自己的资源。您需要比较登录用户 ID 和 URL 中的资源 ID。

**示例场景：**
- 用户 ID `123` 已登录
- 他们导航到 `/users/456/edit`
- 他们应该访问这个页面吗？ **不可以** - 他们只能编辑 `/users/123/edit`

您无法通过角色来解决这个问题，因为它取决于路由参数 `:userId`，该参数对每个请求都变化。

### 创建自定义注释 {#creating-a-custom-annotation}

定义一个注释以标记需要所有权验证的路由：

```java title="RequireOwnership.java"
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireOwnership {
  /**
   * 包含用户 ID 的路由参数名称。
   */
  String value() default "userId";
}
```

在需要所有权检查的路由上使用它：

```java title="EditProfileView.java"
@Route(value = "/users/:userId/edit", outlet = MainLayout.class)
@RequireOwnership("userId")
public class EditProfileView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public EditProfileView() {
    self.setText("编辑个人资料页面");
  }
}
```

### 实现评估器 {#implementing-the-evaluator}

创建一个 Spring 管理的评估器，该评估器比较登录用户 ID 和路由参数：

```java title="OwnershipEvaluator.java"
@RegisteredEvaluator(priority = 10)
public class OwnershipEvaluator implements RouteSecurityEvaluator {

  @Override
  public boolean supports(Class<?> routeClass) {
    return routeClass.isAnnotationPresent(RequireOwnership.class);
  }

  @Override
  public RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context,
      RouteSecurityContext securityContext, SecurityEvaluatorChain chain) {

    // 首先检查身份验证
    if (!securityContext.isAuthenticated()) {
      return RouteAccessDecision.denyAuthentication();
    }

    // 获取注释
    RequireOwnership annotation = routeClass.getAnnotation(RequireOwnership.class);
    String paramName = annotation.value();

    // 从安全上下文获取已登录用户 ID
    String currentUserId = securityContext.getPrincipal()
        .filter(p -> p instanceof UserDetails)
        .map(p -> ((UserDetails) p).getUsername())
        .orElse(null);

    // 从路由参数获取 :userId
    String requestedUserId = context.getRouteParameters()
        .get(paramName)
        .orElse(null);

    // 检查是否匹配
    if (currentUserId != null && currentUserId.equals(requestedUserId)) {
      // 所有权已验证 - 继续链以允许其他评估器
      return chain.evaluate(routeClass, context, securityContext);
    }

    return RouteAccessDecision.deny("您只能访问自己的资源");
  }
}
```

Spring 会自动发现和注册带有 `@RegisteredEvaluator` 注释的评估器。

### 它是如何工作的 {#how-it-works}

评估器实现有两个关键方法：

#### `supports(Class<?> routeClass)` {#supports-method}

- 如果此评估器应该处理该路由，则返回 `true`
- 只有返回 `true` 的评估器才会被调用以处理路由
- 通过检查 `@RequireOwnership` 注释筛选路由

#### `evaluate(...)` {#evaluate-method}

- 首先检查用户是否已通过身份验证
- 从 `securityContext.getPrincipal()` 获取已登录用户 ID
- 从 `context.getRouteParameters().get(paramName)` 获取路由参数值
- 比较两个 ID
- 如果它们匹配，则委托给 `chain.evaluate()` 以允许其他评估器运行
- 如果不匹配，则返回 `deny()` 及原因

### 流程示例 {#flow-example}

**当所有权检查失败时：**

1. 用户 `123` 登录并导航到 `/users/456/edit`
2. `OwnershipEvaluator.supports()` 返回 `true` （路由具有 `@RequireOwnership`）
3. `OwnershipEvaluator.evaluate()` 运行：
   - `currentUserId = "123"` （来自安全上下文）
   - `requestedUserId = "456"` （来自路由参数 `:userId`）
   - `"123".equals("456")` → `false`
   - 返回 `RouteAccessDecision.deny("您只能访问自己的资源")`
4. 用户被重定向到访问拒绝页面

**当所有权检查通过时：**

1. 用户 `123` 登录并导航到 `/users/123/edit`
2. `OwnershipEvaluator.evaluate()` 运行：
   - `currentUserId = "123"`，`requestedUserId = "123"`
   - ID 匹配 → 调用 `chain.evaluate()` 以继续
3. 如果没有其他评估器拒绝访问，则用户被授予访问权限

## 理解评估器链 {#understanding-the-evaluator-chain}

安全系统使用 **责任链模式**，根据优先级顺序处理评估器。评估器可以做出终端决策或将控制权委托给链以组合多个检查。

### 链如何工作 {#how-chain-works}

1. 评估器根据优先级（低数字优先）进行排序
2. 对于每个评估器，调用 `supports(routeClass)` 来检查它是否适用
3. 如果 `supports()` 返回 `true`，则调用评估器的 `evaluate()` 方法
4. 评估器可以：
   - **返回终端决策**（`grant()` 或 `deny()`） - **停止链**
   - **通过调用 `chain.evaluate()` 委托给链** - **允许其他评估器运行**
5. 如果链在没有决策的情况下完成，并且启用了默认安全，则未经过身份验证的用户被拒绝

### 终端决策 {#terminal-decisions}

立即停止链：

#### `RouteAccessDecision.grant()` {#routeaccessdecisiongrant}

- 授予访问权限并停止进一步评估
- 被 `@AnonymousAccess` 和 `@PermitAll` 使用 - 这些是完整的授权，不与其他检查组合

#### `RouteAccessDecision.deny(reason)` {#routeaccessdecisiondenyreason}

- 拒绝访问并停止进一步评估
- 被 `@DenyAll` 和当自定义检查失败时使用
- 示例： `RouteAccessDecision.deny("您只能访问自己的资源")`

#### `RouteAccessDecision.denyAuthentication()` {#routeaccessdecisiondenyauthentication}

- 重定向到登录页面
- 当需要身份验证但缺失时使用

### 链委托 {#chain-delegation}

允许组合检查：

#### `chain.evaluate(routeClass, context, securityContext)` {#chainevaluaterouteclass-context-securitycontext}

- 将控制权传递给链中的下一个评估器
- 使组合多个授权检查成为可能
- 用于 `@RolesAllowed` 和 `@RouteAccess` 在其检查通过后
- 自定义评估器在检查通过时应使用此模式以允许组合

## 评估器优先级 {#evaluator-priority}

评估器按优先级顺序（低数字优先）进行检查。框架评估器使用优先级 1-9，自定义评估器应使用 10 或更高。

内置评估器按以下顺序注册：

```java
// 优先级 1: @DenyAll - 阻止所有访问
// 优先级 2: @AnonymousAccess - 允许未验证访问
// 优先级 3: AuthenticationRequiredEvaluator - 确保 @PermitAll/@RolesAllowed 的身份验证
// 优先级 4: @PermitAll - 仅要求身份验证
// 优先级 5: @RolesAllowed - 需要特定角色
// 优先级 6: @RouteAccess - SpEL 表达式（仅限 Spring Security）
// 优先级 10+: 自定义评估器（如 @RequireOwnership）
```

### 优先级如何影响评估 {#priority-affects-evaluation}

- 低优先级评估器先运行，并可以“短路”链
- `@DenyAll`（优先级 1）先运行 - 如果存在，访问总是被拒绝
- `@AnonymousAccess`（优先级 2）接下来运行 - 如果存在，访问总是被授予（即使没有身份验证）
- `AuthenticationRequiredEvaluator`（优先级 3）检查路由是否需要身份验证以及用户是否已通过身份验证
- 如果没有评估器处理该路由，则应用默认安全逻辑

### 设置优先级 {#setting-priority}

使用 `@RegisteredEvaluator` 注释设置优先级：

```java
@RegisteredEvaluator(priority = 10)  // 在内置评估器之后运行
public class OwnershipEvaluator implements RouteSecurityEvaluator {
  // ...
}
```

:::tip 优先级范围
自定义评估器应使用优先级 10 或更高。优先级 1-9 保留给框架评估器。如果您使用保留范围内的优先级，将会在日志中收到警告。
:::

## 组合评估器 {#combining-evaluators}

委托给链的评估器可以组合在一起以创建复杂的授权逻辑。路由可以有多个安全注释：

### 结合角色检查和自定义逻辑 {#combining-roles-custom}

```java
@Route("/users/:userId/settings")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class UserSettingsView extends Composite<Div> {
  // 必须具有 USER 角色并且访问自己的设置
}
```

**如何工作：**
1. `RolesAllowedEvaluator`（优先级 5）检查用户是否具有“USER”角色
2. 如果是，调用 `chain.evaluate()` 继续
3. `OwnershipEvaluator`（优先级 10）检查 `userId` 是否与已登录用户匹配
4. 如果是，调用 `chain.evaluate()` 继续
5. 链结束 → 授予访问权限

### 结合 SpEL 表达式和自定义逻辑 {#combining-spel-custom}

```java
@Route("/admin/users/:userId/edit")
@RouteAccess("hasRole('ADMIN')")
@RequireOwnership("userId")
public class AdminEditUserView extends Composite<Div> {
  // 必须是管理员并且访问自己的账户
}
```

### 不能组合的情况 {#cant-combine}

`@AnonymousAccess` 和 `@PermitAll` 进行 **终端决策** - 它们立即授予访问权限，而不会调用链。您不能将它们与自定义评估器结合：

```java
// @PermitAll 立即授予访问权限，@RequireOwnership 永远不会运行
@Route("/users/:userId/profile")
@PermitAll
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // ...
}
```

对于所有已验证用户均可访问的资源，请使用 `@RolesAllowed` 与一个共同角色来代替：

```java
// @RolesAllowed 委托给链
@Route("/users/:userId/profile")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // 必须是已验证用户并且访问自己的个人资料
}
```
