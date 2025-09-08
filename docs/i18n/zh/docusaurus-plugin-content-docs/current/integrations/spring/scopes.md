---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
_i18n_hash: 79be5bc5e8982111f185b0f474a1f746
---
<!-- vale off -->

# 作用域 <DocChip chip='since' label='25.03' />

<!-- vale on -->

Spring通过作用域管理bean生命周期。每个作用域定义了bean何时被创建、持续时间以及何时被销毁。除了标准的Spring作用域，webforJ还添加了三个自定义作用域：`@WebforjSessionScope`、`@EnvironmentScope`和`@RouteScope`。

:::tip[了解更多关于Spring作用域的信息]
有关Spring作用域机制和标准作用域的全面覆盖，请参见[Spring的bean作用域文档](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html)。
:::

## 概述

webforJ提供了三个为Web应用状态管理设计的自定义作用域：

- **`@WebforjSessionScope`**：在相同用户会话的所有浏览器标签/窗口中共享的bean。非常适合身份验证、用户偏好和购物车。
- **`@EnvironmentScope`**：仅限于单个浏览器标签/窗口的bean。理想用于特定标签的工作流、表单数据和独立文档编辑。
- **`@RouteScope`**：在路由层级内共享的bean。对于导航状态和在用户浏览应用程序不同部分时应该重置的数据非常有用。

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## 会话作用域 {#session-scope}

`@WebforjSessionScope`注解创建的bean在整个webforJ会话中有效。与[环境作用域](#environment-scope)不同，后者为每个浏览器窗口/标签孤立bean，会话作用域的bean在来自同一浏览器的所有窗口和标签之间共享。这些bean的生命周期与webforJ会话的有效期一致，通常直到用户注销或会话过期。

会话作用域非常适合身份验证状态、用户偏好和购物车。数据应在多个浏览器标签之间持续存在，但在不同用户之间保持隔离。每个用户的浏览器会话接收其会话作用域bean的独立实例。

:::info bean需要可序列化
会话作用域的bean需要实现`Serializable`，因为它们存储在HTTP会话属性中。所有非瞬态字段也必须是可序列化的（基本类型、`String`或实现了`Serializable`的类）。如果字段不应被持久化，标记为`transient`。
:::

将`@WebforjSessionScope`添加到任何Spring组件：

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // 验证用户
    authenticatedUser = authenticate(username, password);
    loginTime = Instant.now();
  }

  public void logout() {
    authenticatedUser = null;
    loginTime = null;
    // 使会话无效
  }

  public boolean isAuthenticated() {
    return authenticatedUser != null;
  }

  public User getCurrentUser() {
    return authenticatedUser;
  }
}
```

### 标签间会话共享 {#session-sharing-across-tabs}

会话作用域的bean在所有浏览器窗口和标签之间维护状态。在多个标签中打开应用程序共享相同的bean实例：

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // 用户已从另一个标签中登录
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("登录");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // 用户现在在所有标签中登录
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // 在所有标签中共享同一个AuthenticationService实例
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // 显示用户仪表板
  }
}
```

当用户通过一个标签登录时，所有其他标签立即可以访问身份验证状态。打开新标签或窗口会保持登录状态。从任何标签注销会影响所有标签，因为它们共享相同的会话作用域bean。

## 环境作用域 {#environment-scope}

`@EnvironmentScope`注解创建的bean在浏览器窗口或标签会话的持续时间内有效。当用户在浏览器窗口或标签中打开应用程序时，webforJ创建一个环境。任何标记为`@EnvironmentScope`的bean在每个浏览器窗口/标签中创建一次，并在用户关闭标签或会话过期之前保持可用。

每个环境代表一个孤立的浏览器窗口或标签。环境作用域的bean不能在不同的浏览器窗口或标签之间共享，因为每个窗口/标签接收其自己的实例。

将`@EnvironmentScope`添加到任何Spring组件：

```java title="TabWorkspace.java" {2}
@Component
@EnvironmentScope
public class TabWorkspace {
  private String documentId;
  private Map<String, Object> workspaceData = new HashMap<>();

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setWorkspaceData(String key, Object value) {
    workspaceData.put(key, value);
  }

  public Object getWorkspaceData(String key) {
    return workspaceData.get(key);
  }
}
```

`TabWorkspace` bean在浏览器窗口或标签的整个生命周期内保持状态。每个浏览器窗口/标签接收一个孤立的实例。

### 使用环境作用域的bean {#using-environment-scoped-beans}

路由通过构造函数注入环境作用域的bean：

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // 加载该标签的文档
    if (documentId == null) {
      // 创建新文档
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // 与EditorView中相同的TabWorkspace实例
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // 预览在该标签中编辑的文档
  }
}
```

Spring将相同的`TabWorkspace`实例注入到同一浏览器窗口/标签的两个视图中。编辑器和预览之间的导航保留工作区实例。如果用户在新的浏览器窗口或标签中打开应用程序，该窗口将接收其独特的`TabWorkspace`实例，允许独立编辑不同的文档。

## 路由作用域 {#route-scope}

`@RouteScope`注解创建在路由层级内共享的bean。导航到`/admin/users`构建一个组件层级，其中admin视图作为父组件，用户视图作为子组件。路由作用域的bean在每个层级中实例化一次，并在父组件和子组件之间共享。

路由作用域在粒度上不同于环境作用域。尽管环境作用域的bean在整个浏览器窗口/标签会话中存在，但路由作用域的bean仅在用户保持在特定路由层级内时存在。离开层级会销毁bean，而返回时会创建新的实例。此作用域适用于在用户在应用程序的不同部分之间导航时应重置的状态。

将`@RouteScope`添加到任何Spring组件：

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

### 路由层级和共享 {#route-hierarchies-and-sharing}

通过`outlet`参数，路由形成层级。父路由提供一个出口，子路由在其中渲染。当您定义一个带有出口的路由时，webforJ构建组件树，其中出口组件变为父级，路由组件变为子级。这种父子关系决定了哪些组件共享路由作用域的bean。

```java {11}
@Route
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("首页");
    navState.addBreadcrumb("管理");
    // ...
  }
}

@Route(value = "users", outlet = AdminView.class)
public class UsersView extends Composite<Div> {

  public UsersView(NavigationState navState) {
    // 与AdminView相同的NavigationState实例
    navState.setActiveTab("users");
    navState.addBreadcrumb("用户");
  }
}
```

`AdminView`和`UsersView`共享相同的`NavigationState`实例。布局建立了导航结构，而视图更新活动状态。导航到`admin`部分以外的地方（例如`/public`）会销毁当前的`NavigationState`实例并为后续层级创建一个新实例。

作用域边界遵循路由树结构。来自层级根部的所有组件直到叶子节点共享相同的路由作用域bean实例。在同一层级内导航到兄弟路由会保留bean，而导航到无关的层级会触发bean的销毁和重建。

### 使用`@SharedFrom`自定义作用域边界 {#customizing-scope-boundaries}

路由作用域的bean默认从最上层组件共享。`@SharedFrom`注解指定一个替代的根组件。此注解更改bean可用的层级位置，允许您限制对路由结构特定子树的访问：

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

该bean仅在`TeamSection`及其子组件中可访问：

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {

  public TeamSection(TeamContext context) {
    // 在此创建bean
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // 无法注入TeamContext - 它的作用域限于TeamSection
    // 尝试注入会抛出IllegalStateException
  }
}
```

`@SharedFrom`注解强制实施架构边界。外部指定作用域的组件无法访问该bean。当Spring尝试向其指定层级以外的组件注入`@SharedFrom`bean时，注入会失败，并抛出`IllegalStateException`。这种强制在运行时访问路由时发生，因此bean保持适当的作用域。 

该注解接受一个参数 - 应作为共享根的组件类。只有该组件及其在路由层级中的后代可以访问该bean。父组件和兄弟层级无法注入它。
