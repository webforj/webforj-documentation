---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
_i18n_hash: 8c977fdef41f6125ac21239e7e397f4d
---
# 范围 <DocChip chip='since' label='25.03' />

Spring 管理 bean 生命周期通过范围。每个范围定义了 bean 的创建时机、生命周期及销毁时机。除了标准的 Spring 范围外，webforJ 还增加了三个自定义范围：`@WebforjSessionScope`、`@EnvironmentScope` 和 `@RouteScope`。

:::tip[了解更多关于 Spring 范围的信息]
有关 Spring 的范围机制和标准范围的全面覆盖，请参见 [Spring 的 bean 范围文档](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html)。
:::

## 概述

webforJ 提供了三个自定义范围，旨在管理 Web 应用程序的状态：

- **`@WebforjSessionScope`**：在同一用户会话中共享的 bean，适用于所有浏览器标签页/窗口。这非常适合身份验证、用户偏好和购物车。
- **`@EnvironmentScope`**：局限于单个浏览器标签页/窗口的 bean。非常适合标签特定的工作流程、表单数据和独立的文档编辑。
- **`@RouteScope`**：在路由层次结构内共享的 bean。适用于导航状态和当用户在应用程序部分之间导航时应重置的数据。

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## 会话范围 {#session-scope}

`@WebforjSessionScope` 注解创建在整个 webforJ 会话中持久化的 bean。与 [环境范围](#environment-scope) 不同，后者每个浏览器窗口/标签页隔离 bean，会话范围的 bean 在同一浏览器的所有窗口和标签之间共享。这些 bean 的生命周期与 webforJ 会话保持一致，通常在用户注销或会话过期之前。

会话范围非常适合身份验证状态、用户偏好、购物车和应在多个浏览器标签之间保持持久的但对不同用户保持隔离的数据。每个用户的浏览器会话接收其自己的会话范围 bean 实例。

:::info bean 需要是可序列化的
会话范围的 bean 需要实现 `Serializable`，因为它们存储在 HTTP 会话属性中。所有非瞬态字段也必须是可序列化的（原始数据类型、`String` 或实现 `Serializable` 的类）。如果不希望持久化字段，请将字段标记为 `transient`。
:::

在任何 Spring 组件中添加 `@WebforjSessionScope`：

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // 身份验证用户
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

### 跨标签共享会话 {#session-sharing-across-tabs}

会话范围的 bean 在所有浏览器窗口和标签之间保持状态。在多个标签中打开应用程序将共享相同的 bean 实例：

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // 用户已在另一标签中登录
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
    // 在所有标签中相同的 AuthenticationService 实例
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // 显示用户仪表板
  }
}
```

当用户通过一个标签登录时，其他所有标签会立即访问到已认证状态。打开新标签或窗口将保持已登录状态。从任何标签注销都会影响所有标签，因为它们共享同一个会话范围 bean。

## 环境范围 {#environment-scope}

`@EnvironmentScope` 注解创建在浏览器窗口或标签会话期间存在的 bean。当用户在浏览器窗口或标签中打开应用程序时，webforJ 创建一个环境。任何标记为 `@EnvironmentScope` 的 bean 在每个浏览器窗口/标签中创建一次，并在用户关闭标签或会话过期之前保持可用。

每个环境代表一个独立的浏览器窗口或标签。环境范围的 bean 不能在不同的浏览器窗口或标签之间共享，因为每个窗口/标签获得自己的实例。

在任何 Spring 组件中添加 `@EnvironmentScope`：

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

`TabWorkspace` bean 在浏览器窗口或标签的生命周期内保持状态。每个浏览器窗口/标签接收一个独立的实例。

### 使用环境范围 bean {#using-environment-scoped-beans}

路由通过构造函数注入环境范围的 bean：

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // 加载此标签的文档
    if (documentId == null) {
      // 创建新文档
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // 与此标签中的 EditorView 相同的 TabWorkspace 实例
    workspace.setWorkspaceData("lastView", "预览");
    String documentId = workspace.getDocumentId();
    // 预览正在此标签中编辑的文档
  }
}
```

Spring 将相同的 `TabWorkspace` 实例注入到同一浏览器窗口/标签中的两个视图中。编辑器和预览之间的导航保持工作区实例。如果用户在新的浏览器窗口或标签中打开应用程序，该窗口将获得自己独特的 `TabWorkspace` 实例，允许独立编辑不同的文档。

## 路由范围 {#route-scope}

`@RouteScope` 注解创建在路由层次结构内共享的 bean。导航到 `/admin/users` 构建了一个组件层次结构，其中 admin 视图为父视图，用户视图为子视图。路由范围的 bean 在每个层次中实例化一次，并在父组件和子组件之间共享。

路由范围与环境范围的粒度不同。虽然环境范围的 bean 存在于整个浏览器窗口/标签会话中，但路由范围的 bean 仅在用户保持在特定路由层次结构内时存在。离开该层次会销毁 bean，而返回则会创建新的实例。这种范围非常适合作为当用户在应用程序的不同部分之间导航时应重置的状态。

在任何 Spring 组件中添加 `@RouteScope`：

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

### 路由层次与共享 {#route-hierarchies-and-sharing}

路由通过 `outlet` 参数形成层次结构。父路由提供一个出口，子路由在此渲染。当你定义一个带有 outlet 的路由时，webforJ 构建一个组件树，其中 outlet 组件成为父组件，路由组件成为子组件。这个父子关系决定了哪些组件共享路由范围的 bean。

```java {11}
@Route
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("主页");
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

`AdminView` 和 `UsersView` 共享相同的 `NavigationState` 实例。布局建立了导航结构，而视图更新了活动状态。导航到 `admin` 部分之外（例如到 `/public`）会销毁当前的 `NavigationState` 实例，并为后续层次创建一个新的实例。

范围边界遵循路由树结构。从层次根到叶子的所有组件共享相同的路由范围 bean 实例。在同一层次内导航到兄弟路由保持 bean，导航到不相关的层次会导致 bean 被销毁并重新创建。

### 使用 `@SharedFrom` 自定义范围边界 {#customizing-scope-boundaries}

路由范围的 bean 默认从最上面的组件共享。`@SharedFrom` 注解指定一个替代根组件。此注解更改了 bean 何时在层次结构中可用，从而允许你限制对路由结构中特定子树的访问：

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
    // Bean 在此创建
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // 不能注入 TeamContext - 它是范围限制在 TeamSection
    // 尝试注入将抛出 IllegalStateException
  }
}
```

`@SharedFrom` 注解强制执行架构边界。指定范围外的组件无法访问该 bean。当 Spring 尝试将 `@SharedFrom` bean 注入到其指定层次结构之外的组件时，注入失败并抛出 `IllegalStateException`。这种强制执行在运行时发生，当访问路由时，因此 bean 保持正确地范围在其预期的组件树内。

该注解接受一个参数：应作为共享根的组件类。只有这个组件及其后代在路由层次中可以访问该 bean。父组件和兄弟层次无法注入它。
