---
title: Scopes
sidebar_position: 16
description: >-
  Use WebforjSessionScope, EnvironmentScope, and RouteScope to control bean
  lifetimes across sessions, browser tabs, and route hierarchies.
_i18n_hash: ea33564c3dec0bc26426440f3b448c53
---
# 范围 <DocChip chip='since' label='25.03' />

Spring通过范围管理bean的生命周期。每个范围定义了何时创建bean、它的存活时间以及何时销毁。除了标准的Spring范围，webforJ还添加了三个自定义范围：`@WebforjSessionScope`、`@EnvironmentScope`和`@RouteScope`。

:::tip[了解更多关于Spring范围的信息]
有关Spring的作用机制和标准范围的全面覆盖，请参见 [Spring的bean范围文档](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html)。
:::

## 概述 {#overview}

webforJ提供了三个用于Web应用状态管理的自定义范围：

- **`@WebforjSessionScope`**：在同一用户会话中跨所有浏览器选项卡/窗口共享的beans。非常适合身份验证、用户偏好和购物车。
- **`@EnvironmentScope`**：仅与单个浏览器选项卡/窗口相关的beans。理想用于选项卡特定的工作流程、表单数据和独立文档编辑。
- **`@RouteScope`**：在路由层次结构内共享的beans。对于导航状态和在应用程序部分之间切换时应重置的数据非常有用。

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## 会话范围 {#session-scope}

`@WebforjSessionScope`注解创建的beans在整个webforJ会话中持久存在。与[环境范围](#environment-scope)不同，后者为每个浏览器窗口/选项卡隔离beans，会话范围的beans在所有来自同一浏览器的窗口和选项卡之间共享。这些beans的生存时间与webforJ会话的活动时间一致，通常直到用户注销或会话过期。

会话范围非常适合身份验证状态、用户偏好、购物车和应该跨多个浏览器选项卡保持持久但在不同用户之间保持隔离的数据。每个用户的浏览器会话都会接收其自己的会话范围beans实例。

:::info Beans需要可序列化
会话范围的beans需要实现`Serializable`，因为它们存储在HTTP会话属性中。所有非瞬态字段也必须是可序列化的（原始类型、`String`或实现`Serializable`的类）。如果某些字段不应持久化，请将其标记为`transient`。
:::

将`@WebforjSessionScope`添加到任何Spring组件中：

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // 认证用户
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

### 跨选项卡共享会话 {#session-sharing-across-tabs}

会话范围的beans在所有浏览器窗口和选项卡之间保持状态。在多个选项卡中打开应用程序会共享相同的bean实例：

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // 用户已在另一个选项卡中登录
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("登录");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // 用户现在已在所有选项卡中登录
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // 所有选项卡中的相同AuthenticationService实例
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // 显示用户仪表板
  }
}
```

当用户通过一个选项卡登录时，所有其他选项卡立即可以访问经过身份验证的状态。打开新选项卡或窗口会保持登录状态。从任何选项卡注销会影响所有选项卡，因为它们共享相同的会话范围bean。

## 环境范围 {#environment-scope}

`@EnvironmentScope`注解创建的beans仅在浏览器窗口或选项卡会话期间存在。当用户在浏览器窗口或选项卡中打开应用程序时，webforJ会创建一个环境。任何标记为`@EnvironmentScope`的bean在每个浏览器窗口/选项卡中创建一次，并在用户关闭选项卡或会话过期之前保持可用。

每个环境表示一个孤立的浏览器窗口或选项卡。环境范围的beans不能在不同的浏览器窗口或选项卡之间共享，因为每个窗口/选项卡都接收其自己的实例。

将`@EnvironmentScope`添加到任何Spring组件中：

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

`TabWorkspace` bean在浏览器窗口或选项卡的整个生命周期内保持状态。每个浏览器窗口/选项卡接收一个孤立实例。

### 使用环境范围的beans {#using-environment-scoped-beans}

通过构造函数注入的路由接收环境范围的beans：

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // 加载此选项卡的文档
    if (documentId == null) {
      // 创建新文档
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // 与此选项卡中的EditorView相同的TabWorkspace实例
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // 预览正在此选项卡中编辑的文档
  }
}
```

Spring将相同的`TabWorkspace`实例注入到同一浏览器窗口/选项卡中的两个视图中。编辑器和预览之间的导航保留了工作区实例。如果用户在新的浏览器窗口或选项卡中打开应用程序，该窗口将接收自己的独特`TabWorkspace`实例，允许独立编辑不同的文档。

## 路由范围 {#route-scope}

`@RouteScope`注解创建的beans在路由层次结构中共享。导航到`/admin/users`构建具有admin视图作为父组件和users视图作为子组件的组件层次结构。路由范围的beans在每个层次结构中实例化一次，并在父组件和子组件之间共享。

路由范围与环境范围在粒度上有所不同。虽然环境范围的beans在整个浏览器窗口/选项卡会话中存在，路由范围的beans仅在用户保持在特定路由层次结构内时存在。离开层次结构将销毁这些beans，而返回时将创建新的实例。这个范围非常适合在用户在应用程序不同部分之间导航时应该重置的状态。

将`@RouteScope`添加到任何Spring组件中：

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

通过`outlet`参数形成路由层次结构。父路由提供了一个出口，子路由在这里渲染。当你定义具有出口的路由时，webforJ构建一个组件树，其中出口组件成为父组件，而路由组件成为子组件。这个父子关系决定了哪些组件共享路由范围的beans。

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

`AdminView`和`UsersView`共享相同的`NavigationState`实例。布局建立了导航结构，而视图更新活动状态。导航到`admin`部分之外的路由（例如`/public`）会销毁当前的`NavigationState`实例并为随后的层次结构创建一个新的实例。

范围边界遵循路由树结构。从层次结构根部到叶子节点的所有组件共享相同的路由范围bean实例。在同一层次结构内导航到兄弟路由可以保留beans，而导航到不相关的层次结构会触发beans的销毁和重建。

### 使用`@SharedFrom`自定义范围边界 {#customizing-scope-boundaries}

默认情况下，路由范围的beans从最上层组件共享。`@SharedFrom`注解指定一个替代根组件。此注解改变了bean变得可用的位置，允许你限制对路由结构特定子树的访问：

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

该bean仅在`TeamSection`及其子组件内可访问：

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {

  public TeamSection(TeamContext context) {
    // 此处创建bean
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // 无法注入TeamContext - 它被限制在TeamSection中
    // 尝试注入将抛出IllegalStateException
  }
}
```

`@SharedFrom`注解实施了架构边界。指定范围外的组件无法访问该bean。当Spring尝试将`@SharedFrom` bean注入到其指定层次结构之外的组件时，注入会失败并抛出`IllegalStateException`。此强制执行发生在运行时，当访问路由时，因此beans保持适当地限于其预期的组件树。

该注解接受一个参数：应作为共享根的组件类。只有这个组件及其在路由层次结构中的后代可以访问该bean。父组件和兄弟层次结构无法注入它。
