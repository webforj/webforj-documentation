---
sidebar_position: 2
title: 基础架构
_i18n_hash: 53db76e19a5bfcf2e476ac1efaaa2c48
---
webforJ安全系统建立在核心接口的基础上，这些接口协同工作以提供基于路由的访问控制。这些接口定义了安全行为的契约，允许不同的实现，无论是基于会话的、基于JSON Web令牌（JWT）、集成LDAP还是基于数据库的，都能接入相同的基础框架。

理解这种架构有助于您了解如`@RolesAllowed`和`@PermitAll`等安全注解是如何被评估的，导航拦截是如何工作的，以及您如何为特定需求构建自定义安全实现。

## 核心接口 {#the-four-core-interfaces}

安全基础建立在关键抽象之上，每个抽象都有特定的责任：

### `RouteSecurityManager` {#routesecuritymanager}

`RouteSecurityManager`是安全系统的中央协调者。它管理安全评估器，协调评估过程，并通过将用户重定向到适当的页面来处理访问拒绝。

**责任：**

- 注册并管理具有优先级的安全评估器
- 当用户导航到路由时协调评估过程
- 通过触发重定向到登录或访问拒绝页面来处理访问拒绝
- 存储和检索身份验证前的位置，以便在登录后重定向

```java
public interface RouteSecurityManager {
  RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context);
  void onAccessDenied(RouteAccessDecision decision, NavigationContext context);
  RouteSecurityContext getSecurityContext();
  RouteSecurityConfiguration getConfiguration();
  void registerEvaluator(RouteSecurityEvaluator evaluator, int priority);
  Optional<Location> consumePreAuthenticationLocation();
}
```

该管理器本身不做安全决策，它委托给评估器和配置。它是连接所有安全组件的粘合剂。

### `RouteSecurityContext` {#routesecuritycontext}

`RouteSecurityContext`提供对当前用户身份验证状态的访问。它回答诸如用户是否已认证、用户名是什么，以及用户是否具有`ADMIN`角色等问题。

**责任：**

- 确定当前用户是否经过身份验证
- 提供用户的主体（通常是他们的用户名或用户对象）
- 检查用户是否具有特定角色或权限
- 存储和检索自定义安全属性

```java
public interface RouteSecurityContext {
  boolean isAuthenticated();
  Optional<Object> getPrincipal();
  boolean hasRole(String role);
  boolean hasAuthority(String authority);
  Optional<Object> getAttribute(String name);
  void setAttribute(String name, Object value);
}
```

实现根据身份验证系统、HTTP会话存储、从标头解码的JWT令牌、数据库查询、LDAP查找或其他机制有所不同。

### `RouteSecurityConfiguration` {#routesecurityconfiguration}

`RouteSecurityConfiguration`定义安全行为和重定向位置。它告诉安全系统在需要身份验证或访问被拒绝时将用户发送到何处。

**责任：**

- 定义安全性是否启用
- 指定安全默认行为
- 提供身份验证页面位置（通常为`/login`）
- 提供访问拒绝页面位置

```java
public interface RouteSecurityConfiguration {
  default boolean isEnabled() { return true; }
  default boolean isSecureByDefault() { return true; }
  default Optional<Location> getAuthenticationLocation() {
    return Optional.of(new Location("/login"));
  }
  default Optional<Location> getDenyLocation() { /* ... */ }
}
```

该接口将安全策略与安全执行分开。您可以更改重定向位置或切换安全默认行为，而无需修改管理器或评估器。

### `RouteSecurityEvaluator` {#routesecurityevaluator}

`RouteSecurityEvaluator`是实际检查安全规则的地方。每个评估器检查一个路由并决定是否授予访问、拒绝访问或将决定委托给链中的下一个评估器。

**责任：**

- 确定该评估器是否处理给定的路由
- 评估路由类上的安全注解
- 授予访问、拒绝访问或委托给下一个评估器
- 参与责任链模式

```java
public interface RouteSecurityEvaluator {
  RouteAccessDecision evaluate(Class<?> routeClass,
                                NavigationContext context,
                                RouteSecurityContext securityContext,
                                SecurityEvaluatorChain chain);
  default boolean supports(Class<?> routeClass) { return true; }
}
```

内置评估器处理标准注解，如`@RolesAllowed`、`@PermitAll`、`@DenyAll`和`@AnonymousAccess`。您可以创建自定义评估器以实现领域特定的安全逻辑。

## 接口如何协同工作 {#how-the-interfaces-work-together}

这四个接口在导航过程中协作以执行安全规则：

```mermaid
flowchart TB
    User["用户导航到路由"] --> Observer["RouteSecurityObserver<br/>(拦截导航)"]
    Observer --> Manager["RouteSecurityManager<br/>(协调评估)"]

    Manager --> Config["RouteSecurityConfiguration<br/>(提供设置)"]
    Manager --> Context["RouteSecurityContext<br/>(提供用户信息)"]
    Manager --> Chain["Evaluato Chain<br/>(按优先级运行评估器)"]

    Chain --> Decision{"访问决策"}
    Decision -->|"授予"| Render["渲染组件"]
    Decision -->|"拒绝"| Redirect["RouteSecurityManager.onAccessDenied()<br/>重定向到登录或拒绝页面"]
```

当用户导航时，`RouteSecurityObserver`拦截导航并请求`RouteSecurityManager`评估访问。管理器咨询`RouteSecurityConfiguration`以获取设置，从`RouteSecurityContext`获取用户信息，并按优先级顺序运行每个`RouteSecurityEvaluator`，直到其中一个做出决策。

## 接口作为契约 {#the-interfaces-as-contracts}

每个接口定义了一个契约，即安全系统需要回答的一系列问题。 **如何**回答这些问题是您的实现选择：

**`RouteSecurityContext`契约：**

- "当前用户是否经过身份验证？" (`isAuthenticated()`)
- "用户是谁？" (`getPrincipal()`)
- "用户是否具有角色X？" (`hasRole()`)

您决定这些信息来源于HTTP会话、从标头解码的JWT令牌、数据库查找、LDAP查询或任何其他身份验证后端。

**`RouteSecurityConfiguration`契约：**

- "安全性是否启用？" (`isEnabled()`)
- "路由是否应默认安全？" (`isSecureByDefault()`)
- "未经身份验证的用户应该去哪里？" (`getAuthenticationLocation()`)

您决定如何获取这些值：硬编码、来自配置文件、来自环境变量、来自数据库或动态计算。

**`RouteSecurityManager`契约：**

- "此用户应访问此路由吗？" (`evaluate()`)
- "拒绝访问时会发生什么？" (`onAccessDenied()`)
- "应该运行哪些评估器？" (`registerEvaluator()`)

您决定身份验证流程、存储身份验证前位置的位置以及如何处理自定义拒绝场景。

基础架构定义了这些契约，但实施是灵活的。不同系统可以根据特定要求以完全不同的方式实现这些接口。

## `AbstractRouteSecurityManager` 基类 {#the-abstractroutesecuritymanager-base-class}

大多数实现不会直接实现`RouteSecurityManager`。相反，它们扩展`AbstractRouteSecurityManager`，该类提供：

- 评估器注册和基于优先级的排序
- 链执行逻辑
- 通过自动重定向处理访问拒绝
- 在HTTP会话中存储身份验证前的位置
- 默认为安全的回退行为

基类实现了`RouteSecurityManager`接口并提供评估器管理、访问评估和拒绝处理的具体实现。子类只需要提供安全上下文和配置。基类自动处理评估器管理、链执行和拒绝处理。
