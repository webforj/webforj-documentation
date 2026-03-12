---
sidebar_position: 6
title: Custom Implementation Example
_i18n_hash: 9d0d249042bfbbccb42d3ce7a329a8e7
---
本指南介绍了如何构建一个完整的自定义安全实现，使用基于会话的身份验证。您将通过从头开始实现这四个核心接口，学习它们是如何协同工作的。

:::tip[大多数应用程序应使用 Spring Security]
[Spring Security 集成](/docs/security/getting-started) 会自动配置此处显示的所有内容。只有在您有特定要求或不使用 Spring Boot 时，才应构建自定义安全性。
:::

## 您将构建什么 {#what-youll-build}

一个包含四个类的工作安全系统：

- **SecurityConfiguration** - 定义安全行为和重定向位置
- **SecurityContext** - 使用 HTTP 会话跟踪谁已登录
- **SecurityManager** - 协调安全检查并提供登录/注销功能
- **SecurityRegistrar** - 在应用启动时将所有内容连接在一起

此示例使用基于会话的存储，但您可以使用数据库查询、LDAP 或任何其他身份验证后端实现相同的接口。

## 组件如何协同工作 {#how-the-pieces-work-together}

```mermaid
sequenceDiagram
    box 启动阶段
    participant Registrar as SecurityRegistrar
    end
    box 运行阶段
    participant Observer as RouteSecurityObserver
    participant Manager as SecurityManager
    participant Evaluators
    participant Context as SecurityContext
    participant Config as SecurityConfiguration
    end

    Note over Registrar: 应用程序启动
    Registrar->>Manager: 创建
    Registrar->>Evaluators: 注册
    Registrar->>Observer: 附加到路由器

    Note over Observer,Config: 用户导航到路由
    Observer->>Manager: 请求决策
    Manager->>Evaluators: 运行评估器
    Evaluators->>Context: 检查用户
    Evaluators->>Config: 获取重定向
    Evaluators-->>Manager: 决策
    Manager-->>Observer: 授予或拒绝
```

**流程：**
1. **`SecurityRegistrar`** 在启动时运行，创建管理器，注册评估器并附加观察者
2. **`SecurityManager`** 协调一切 - 它为评估器提供上下文和配置
3. **`SecurityContext`** 通过从 HTTP 会话读取数据来回答“谁已登录？”
4. **`SecurityConfiguration`** 回答“在哪里重定向？” 用于登录和访问拒绝页面
5. **`Evaluators`** 使用上下文和配置做出访问决策

## 步骤 1：定义安全配置 {#step-1-define-security-configuration}

配置告知安全系统如何运行以及将用户重定向到何处：

```java title="SecurityConfiguration.java"
package com.securityplain.security;

import com.webforj.router.history.Location;
import com.webforj.router.security.RouteSecurityConfiguration;
import java.util.Optional;

/**
 * 应用程序的安全配置。
 *
 * <p>
 * 定义当需要身份验证或访问被拒绝时将用户重定向到何处。
 * </p>
 */
public class SecurityConfiguration implements RouteSecurityConfiguration {

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isSecureByDefault() {
    return false;
  }

  @Override
  public Optional<Location> getAuthenticationLocation() {
    return Optional.of(new Location("/login"));
  }

  @Override
  public Optional<Location> getDenyLocation() {
    return Optional.of(new Location("/access-denied"));
  }
}
```

- `isEnabled() = true` - 安全功能处于活动状态
- `isSecureByDefault() = false` - 默认情况下，路由是公开的，除非有注释（使用 `true` 可以要求所有路由默认进行身份验证）
- `/login` - 未经身份验证的用户访问的地方
- `/access-denied` - 没有权限的经过身份验证用户访问的地方

## 步骤 2：实现安全上下文 {#step-2-implement-security-context}

上下文跟踪谁已登录。此实现使用 HTTP 会话存储用户信息：

<!-- vale off -->

<ExpandableCode title="SecurityContext.java" language="java">
{`package com.securityplain.security;

import com.webforj.Environment;
import com.webforj.router.security.RouteSecurityContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 简单的基于会话的安全上下文。
 *
 * <p>
 * 在 HTTP 会话中存储用户主体和角色。 这是一个用于教学的最小实现。
 * </p>
 */
public class SecurityContext implements RouteSecurityContext {
  private static final String SESSION_USER_KEY = "security.user";
  private static final String SESSION_ROLES_KEY = "security.roles";
  private static final String SESSION_ATTRS_KEY = "security.attributes";

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAuthenticated() {
    return getPrincipal().isPresent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Object> getPrincipal() {
    return getSessionAttribute(SESSION_USER_KEY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasRole(String role) {
    Optional<Object> rolesObj = getSessionAttribute(SESSION_ROLES_KEY);
    if (rolesObj.isPresent() && rolesObj.get() instanceof Set) {
      @SuppressWarnings("unchecked")
      Set<String> roles = (Set<String>) rolesObj.get();
      return roles.contains(role);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasAuthority(String authority) {
    // 在这个简单的实现中，权限与角色相同
    return hasRole(authority);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Object> getAttribute(String name) {
    Optional<Object> attrsObj = getSessionAttribute(SESSION_ATTRS_KEY);
    if (attrsObj.isPresent() && attrsObj.get() instanceof Map) {
      @SuppressWarnings("unchecked")
      Map<String, Object> attrs = (Map<String, Object>) attrsObj.get();
      return Optional.ofNullable(attrs.get(name));
    }
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAttribute(String name, Object value) {
    Environment.ifPresent(env -> {
      env.getSessionAccessor().ifPresent(accessor -> {
        accessor.access(session -> {
          @SuppressWarnings("unchecked")
          Map<String, Object> attrs =
              (Map<String, Object>) session.getAttribute(SESSION_ATTRS_KEY);
          if (attrs == null) {
            attrs = new HashMap<>();
            session.setAttribute(SESSION_ATTRS_KEY, attrs);
          }
          attrs.put(name, value);
        });
      });
    });
  }

  private Optional<Object> getSessionAttribute(String key) {
    final Object[] result = new Object[1];
    Environment.ifPresent(env -> {
      env.getSessionAccessor().ifPresent(accessor -> {
        accessor.access(session -> {
          result[0] = session.getAttribute(key);
        });
      });
    });
    return Optional.ofNullable(result[0]);
  }
}`}
</ExpandableCode>

<!-- vale on -->

**工作原理：**

- `isAuthenticated()` 检查会话中是否存在用户主体
- `getPrincipal()` 从会话存储中检索用户名
- `hasRole()` 检查用户的角色集是否包含指定角色
- `getAttribute()` / `setAttribute()` 管理自定义安全属性
- `Environment.getSessionAccessor()` 提供线程安全的会话访问

## 步骤 3：创建安全管理器 {#step-3-create-security-manager}

管理器协调安全决策。它扩展 `AbstractRouteSecurityManager`，处理评估器链和访问拒绝：

<!-- vale off -->

<ExpandableCode title="SecurityManager.java" language="java">
{`package com.securityplain.security;

import com.webforj.environment.ObjectTable;
import com.webforj.environment.SessionObjectTable;
import com.webforj.router.Router;
import com.webforj.router.security.AbstractRouteSecurityManager;
import com.webforj.router.security.RouteAccessDecision;
import com.webforj.router.security.RouteSecurityConfiguration;
import com.webforj.router.security.RouteSecurityContext;

import java.util.Set;

/**
 * 简单的安全管理器实现。
 *
 * <p>
 * 提供登录/注销的静态方法并管理安全上下文。
 * </p>
 */
public class SecurityManager extends AbstractRouteSecurityManager {
  private static final String SESSION_USER_KEY = "security.user";
  private static final String SESSION_ROLES_KEY = "security.roles";

  /**
   * {@inheritDoc}
   */
  @Override
  public RouteSecurityConfiguration getConfiguration() {
    return new SecurityConfiguration();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RouteSecurityContext getSecurityContext() {
    return new SecurityContext();
  }

  /**
   * 登录具有角色的用户。
   *
   * @param username 用户名
   * @param password 密码
   */
  public RouteAccessDecision login(String username, String password) {
    if ("user".equals(username) && "password".equals(password)) {
      Set<String> roles = Set.of("USER");
      persistUser(username, roles);
      return RouteAccessDecision.grant();
    } else if ("admin".equals(username) && "admin".equals(password)) {
      Set<String> roles = Set.of("USER", "ADMIN");
      persistUser(username, roles);
      return RouteAccessDecision.grant();
    }

    return RouteAccessDecision.deny("无效的用户名或密码");
  }

  /**
   * 注销当前用户并重定向到登录页面。
   */
  public void logout() {
    SessionObjectTable.clear(SESSION_USER_KEY);
    SessionObjectTable.clear(SESSION_ROLES_KEY);

    Router router = Router.getCurrent();
    if (router != null) {
      getConfiguration().getAuthenticationLocation().ifPresent(location -> router.navigate(location));
    }
  }

  /**
   * 获取当前管理器实例。
   *
   * @return 当前管理器实例
   */
  public static SecurityManager getCurrent() {
    String key = SecurityManager.class.getName();
    if (ObjectTable.contains(key)) {
      return (SecurityManager) ObjectTable.get(key);
    }

    SecurityManager instance = new SecurityManager();
    ObjectTable.put(key, instance);

    return instance;
  }

  void saveCurrent(SecurityManager manager) {
    String key = SecurityManager.class.getName();
    ObjectTable.put(key, manager);
  }

  private void persistUser(String username, Set<String> roles) {
    SessionObjectTable.put(SESSION_USER_KEY, username);
    SessionObjectTable.put(SESSION_ROLES_KEY, roles);
  }
}`}
</ExpandableCode>

<!-- vale on -->

**工作原理：**

- 扩展 `AbstractRouteSecurityManager` 以继承评估器链逻辑
- 提供 `getConfiguration()` 和 `getSecurityContext()` 的实现
- 添加 `login()` 以验证用户并在会话中存储凭据
- 添加 `logout()` 以清除会话并重定向到登录页面
- 使用 [`SessionObjectTable`](/docs/advanced/object-string-tables#sessionobjecttable) 进行简单的会话存储
- 将自身存储在 [`ObjectTable`](/docs/advanced/object-string-tables#objecttable) 中，以便全应用访问

## 步骤 4：在启动时连接所有内容 {#step-4-wire-everything-at-startup}

登记者在应用启动时连接所有组件：

```java title="SecurityRegistrar.java"
package com.securityplain.security;

import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.annotation.AppListenerPriority;
import com.webforj.router.Router;
import com.webforj.router.security.RouteSecurityObserver;
import com.webforj.router.security.evaluator.AnonymousAccessEvaluator;
import com.webforj.router.security.evaluator.DenyAllEvaluator;
import com.webforj.router.security.evaluator.PermitAllEvaluator;
import com.webforj.router.security.evaluator.RolesAllowedEvaluator;

/**
 * 在应用程序启动期间注册路由安全组件。
 *
 * <p>
 * 设置安全管理器和评估器与路由器配合使用。
 * </p>
 */
@AppListenerPriority(1)
public class SecurityRegistrar implements AppLifecycleListener {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onWillRun(App app) {
    // 创建安全管理器
    SecurityManager securityManager = new SecurityManager();
    securityManager.saveCurrent(securityManager);

    // 注册内置评估器，按照优先顺序
    securityManager.registerEvaluator(new DenyAllEvaluator(), 0);
    securityManager.registerEvaluator(new AnonymousAccessEvaluator(), 1);
    securityManager.registerEvaluator(new PermitAllEvaluator(), 2);
    securityManager.registerEvaluator(new RolesAllowedEvaluator(), 3);

    // 创建安全观察者并附加到路由器
    RouteSecurityObserver securityObserver = new RouteSecurityObserver(securityManager);
    Router router = Router.getCurrent();
    if (router != null) {
      router.getRenderer().addObserver(securityObserver);
    }
  }
}
```

**注册监听器：**

在 `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener` 中创建：

```text
com.securityplain.security.SecurityRegistrar
```

这会注册您的 [`AppLifecycleListener`](/docs/advanced/lifecycle-listeners)，以便在应用启动时运行。

**工作原理：**

- 提前运行（`@AppListenerPriority(1)`），以便在加载路由之前设置安全性
- 创建安全管理器并全局存储
- 按优先顺序注册内置评估器（较低的数字优先运行）
- 创建观察者以拦截导航
- 将观察者附加到路由器，以便自动进行安全检查

在这段代码运行后，所有导航的安全性将处于活动状态。

## 使用您的实现 {#using-your-implementation}

### 创建登录视图 {#create-a-login-view}

以下视图使用 [`Login`](/docs/components/login) 组件。

```java title="LoginView.java"
package com.securityplain.views;

import com.securityplain.security.SecurityManager;
import com.webforj.component.Composite;
import com.webforj.component.login.Login;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.Location;
import com.webforj.router.security.annotation.AnonymousAccess;

@Route("/login")
@FrameTitle("登录")
@AnonymousAccess
public class LoginView extends Composite<Login> {
  private final Login self = getBoundComponent();

  public LoginView() {
    self.onSubmit(e -> {
      var result = SecurityManager.getCurrent().login(
        e.getUsername(), e.getPassword()
      );
      
      if (result.isGranted()) {
        Router.getCurrent().navigate(new Location("/"));
      } else {
        self.setError(true);
        self.setEnabled(true);
      }
    });

    self.whenAttached().thenAccept(c -> self.open());
  }
}
```
