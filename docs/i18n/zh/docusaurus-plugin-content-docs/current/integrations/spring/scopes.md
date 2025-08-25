---
title: Scopes
sidebar_position: 16
_i18n_hash: 96d36a28887e551ba3f9acab0d7059bc
---
<!-- vale off -->
# 范围 <DocChip chip='since' label='25.03' />
<!-- vale on -->

Spring 通过范围管理 bean 的生命周期。每个范围定义了 bean 何时被创建，它的生命周期有多长，以及何时被销毁。webforJ 增加了两个自定义范围 - `@EnvironmentScope` 和 `@RouteScope` - 它们对应于 webforJ 应用如何处理浏览器会话和导航。

:::tip[了解更多关于 Spring 范围的信息]
有关 Spring 的范围机制和标准范围的全面覆盖，请参见 [Spring 的 bean 范围文档](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html)。
:::

## 环境范围 {#environment-scope}

`@EnvironmentScope` 注解创建的 beans 在浏览器窗口或标签会话期间存在。当用户在浏览器窗口或标签中打开应用时，webforJ 创建一个环境。任何标记为 `@EnvironmentScope` 的 bean 都会在每个浏览器窗口/标签中被创建一次，并一直可用，直到用户关闭标签或会话过期。

每个环境表示一个隔离的浏览器窗口或标签。环境范围的 bean 不能在不同的浏览器窗口或标签之间共享 - 每个窗口/标签接收自己的实例。

将 `@EnvironmentScope` 添加到任何 Spring 组件：

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

`UserSession` bean 在浏览器窗口或标签的整个生命周期中保持状态。每个浏览器窗口/标签接收一个隔离的实例。

### 使用环境范围的 beans {#using-environment-scoped-beans}

路由通过构造函数注入接收环境范围的 beans：

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  public DashboardView(UserSession session) {
    String userId = session.getUserId();
    // 使用会话数据
    if (userId == null) {
      // 重定向到登录
    }
  }
}

@Route("/profile")
public class ProfileView extends Composite<Div> {
  
  public ProfileView(UserSession session) {
    // 与 DashboardView 相同的 UserSession 实例
    session.setAttribute("lastView", "profile");
  }
}
```

Spring 将相同的 `UserSession` 实例注入到同一浏览器窗口/标签的两个视图中。导航在仪表板和个人资料之间保留会话实例。如果用户在新的浏览器窗口或标签中打开应用，则该窗口会接收其自己的不同 `UserSession` 实例。

## 路由范围 {#route-scope}

`@RouteScope` 注解创建的 beans 在路由层次结构中的共享。导航到 `/admin/users` 构建一个组件层次结构，以管理员视图作为父层，用户视图作为子层。路由范围的 beans 在每个层次结构中实例化一次，并在父组件和子组件之间共享。

路由范围在粒度上与环境范围不同。环境范围的 beans 在整个浏览器窗口/标签会话期间存在，而路由范围的 beans 仅在用户保持在特定路由层次结构内时存在。离开层次结构的导航会销毁 beans，返回会创建新实例。此范围非常适合在用户在应用的不同部分之间导航时应重置的状态。

将 `@RouteScope` 添加到任何 Spring 组件：

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

### 路由层次结构与共享 {#route-hierarchies-and-sharing}

路由通过 `outlet` 参数形成层次结构。父路由提供一个 outlet，在其中渲染子路由。当您定义一个带 outlet 的路由时，webforJ 构建一个组件树，在该树中，outlet 组件成为父组件，路由组件成为子组件。该父子关系决定了哪些组件共享路由范围的 beans。

```java
@Route("/admin")
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("首页");
    navState.addBreadcrumb("管理员");
    // ...
  }
}

@Route(value = "users", outlet = AdminView.class)
public class UsersView extends Composite<Div> {
  
  public UsersView(NavigationState navState) {
    // 与 AdminView 相同的 NavigationState 实例
    navState.setActiveTab("users");
    navState.addBreadcrumb("用户");
  }
}
```

`AdminView` 和 `UsersView` 共享相同的 `NavigationState` 实例。布局建立导航结构，而视图更新活动状态。在 `admin` 部分之外的导航（例如到 `/public`）会销毁当前的 `NavigationState` 实例，并为后续的层次结构创建一个新的实例。

范围边界遵循路由树结构。从层次结构的根部到叶子之间的所有组件共享相同的路由范围 bean 实例。在同一层次结构内导航到兄弟路由会保留 beans，而导航到无关的层次结构会触发 bean 销毁和重新创建。

### 使用 `@SharedFrom` 自定义范围边界 {#customizing-scope-boundaries}

路由范围的 beans 默认从最上层组件共享。`@SharedFrom` 注解指定一个替代的根组件。此注解改变了 bean 在层次结构中变得可用的位置，使您可以限制对路由结构特定子树的访问：

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

该 bean 仅在 `TeamSection` 及其子组件内可访问：

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {
  
  public TeamSection(TeamContext context) {
    // 在这里创建的 bean
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // 无法注入 TeamContext - 它属于 TeamSection 范围
    // 尝试注入会抛出 IllegalStateException
  }
}
```

`@SharedFrom` 注解强制执行架构边界。指定的范围外部的组件无法访问该 bean。当 Spring 尝试将 `@SharedFrom` bean 注入到其指定层次结构外的组件时，注入会失败并抛出 `IllegalStateException`。此强制性在运行时发生，即路由被访问时，因此 beans 保持适当地限于其预期的组件树。

该注解接受一个参数 - 应作为共享根的组件类。只有该组件及其在路由层次结构中的后代可以访问该 bean。父组件和兄弟层次结构无法注入它。
