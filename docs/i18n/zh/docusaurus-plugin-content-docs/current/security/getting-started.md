---
title: Getting Started
sidebar_position: 2
_i18n_hash: becd2e7bd488a077c08ef5a64dbe0f61
---
Spring Security 为 Spring Boot 应用程序提供身份验证和授权。当与 webforJ 集成时，它通过注释保护路由，而 Spring 处理用户管理和会话。

本指南涵盖了如何将 Spring Security 添加到您的 webforJ 应用程序，配置身份验证，创建登录视图，以及使用基于角色的访问控制保护路由。

:::tip[了解更多关于 Spring Security 的信息]
有关 Spring Security 特性和概念的全面理解，请参见 [Spring Security 文档](https://docs.spring.io/spring-security/reference/)。
:::

## 添加 Spring Security 依赖项 {#add-spring-security-dependency}

将 Spring Security 启动器添加到您的 `pom.xml` 中：

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

此单个依赖项引入了 Spring Security 的身份验证框架、密码编码器和会话管理。版本由您的 Spring Boot 父 POM 自动管理。

## 配置 Spring Security {#configure-spring-security}

创建一个安全配置类，将 Spring Security 与 webforJ 连接起来。此类定义如何验证用户以及哪些页面处理登录和访问拒绝：

```java title="SecurityConfig.java"
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
            .loginPage(LoginView.class)
            .accessDeniedPage(AccessDenyView.class)
            .logout())
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails user = User.builder()
        .username("user")
        .password(passwordEncoder.encode("password"))
        .roles("USER")
        .build();

    UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder.encode("admin"))
        .roles("USER", "ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(authenticationProvider);
  }
}
```

此配置创建了四个 Spring Bean，它们协同工作：

**`SecurityFilterChain`** 将 Spring Security 连接到 webforJ 的路由系统。`WebforjSecurityConfigurer.webforj()` 方法将 Spring Security 身份验证与 webforJ 路由集成。您指定处理登录和访问拒绝的组件类，Spring 将在呈现受保护的路由之前强制执行身份验证。

- `loginPage()` 方法告诉 Spring Security 用户应该在哪里进行身份验证。传递您的登录视图组件类，webforJ 会自动从 `@Route` 注释中解析路由路径。当未经过身份验证的用户尝试访问受保护的路由时，他们会被重定向到此处。

- `accessDeniedPage()` 方法定义了当经过身份验证的用户在某个路由缺乏权限时的去向。例如，试图访问仅限管理员的路由的用户会被重定向到此页面。

- `logout()` 方法启用 `/logout` 的注销端点。注销后，用户会被重定向回登录页面，并附带 `?logout` 参数。

**`PasswordEncoder`** 使用 BCrypt 安全地对密码进行哈希。Spring Security 会在登录期间自动应用此编码器，以比较提交的密码与存储的哈希值。

**`UserDetailsService`** 告诉 Spring Security 在身份验证期间在哪里查找用户信息。本示例使用内存存储，包含两个用户：`user/password` 和 `admin/admin`。

**`AuthenticationManager`** 协调身份验证过程。它使用一个提供程序从 `UserDetailsService` 加载用户并使用 `PasswordEncoder` 验证密码。

## 创建登录视图 {#create-login-view}

创建一个展示登录对话框并将凭据提交给 Spring Security 的视图。以下视图使用 [`Login`](/docs/components/login) 组件：

```java title="LoginView.java"
@Route("/signin")
@AnonymousAccess
public class LoginView extends Composite<Login> implements DidEnterObserver {
  private final Login self = getBoundComponent();

  public LoginView() {
    self.setAction("/signin");
    whenAttached().thenAccept(c -> self.open());
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag params) {
    ParametersBag queryParams = event.getLocation().getQueryParameters();

    if (queryParams.containsKey("error")) {
      Toast.show("用户名或密码无效。请再试一次。", Theme.DANGER);
    }

    if (queryParams.containsKey("logout")) {
      Toast.show("您已成功注销。", Theme.GRAY);
    }
  }
}
```

`@AnonymousAccess` 注释将此路由标记为公共，因此未经过身份验证的用户可以访问登录页面。没有此注释，用户将被重定向离开登录页面，从而创建一个无限循环。

`setAction("/signin")` 行至关重要，它配置 Login 组件将凭据 POST 到 Spring 的身份验证端点。Spring 拦截此提交，验证凭据，并在授予访问权限或带有错误参数的重定向之间做出选择。

`onDidEnter` 观察者检查 Spring 添加的查询参数以传达结果。当身份验证失败时，Spring 重定向到 `/signin?error`。注销后，它重定向到 `/signin?logout`。观察者根据这些参数显示适当的消息。

:::tip 端点匹配
`setAction("/signin")` 中的路径必须与您的 `@Route("/signin")` 路径匹配。Spring 拦截表单提交至此确切路径进行身份验证。如果您需要不同的路径用于登录页面和身份验证处理，请在 `SecurityConfig` 中分别配置它们：

```java
.loginPage("/signin", "/authenticate")
```

这会将登录页面显示在 `/signin`，但在 `/authenticate` 处理身份验证。
:::

## 创建访问拒绝视图 {#create-access-denied-view}

创建一个视图，当用户缺乏访问某个路由的权限时显示：

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("哎呀！此区域仅限 VIP。");
    Paragraph subMessage = new Paragraph(
        "看起来您试图潜入行政休息室！要么获取更好的凭据，要么返回公共区域，那里咖啡是免费的。");

    self.add(message, subMessage);
  }
}
```

当经过身份验证的用户尝试访问他们没有权限的路由时，此视图将被呈现，例如用户试图访问仅限管理员的路由。

## 保护您的路由 {#protect-your-routes}

在配置了身份验证后，您现在可以使用安全注释来保护您的路由。这些注释告诉 Spring Security 谁可以访问每个视图，安全系统会在呈现任何组件之前自动强制执行这些规则。

当用户导航到路由时，Spring Security 拦截导航并检查路由的安全注释。如果用户经过身份验证（使用有效凭据登录）并且具有所需权限，则视图将正常呈现。如果没有，他们会被重定向到登录页面或访问拒绝页面。

```java title="InboxView.java"
// 收件箱 - 任何经过身份验证的用户均可访问
@Route(value = "/", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("收件箱"));
  }
}
```

```java title="TeamsView.java" {3}
// 球队 - 需要 ADMIN 角色
@Route(value = "/teams", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TeamsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TeamsView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("球队"));
  }
}
```

`InboxView` 没有任何注释，这意味着任何经过身份验证的用户都可以访问它。当用户使用您在 `UserDetailsService` 中定义的凭据（`user/password` 或 `admin/admin`）成功登录时，他们可以查看此路由。未经过身份验证的用户尝试访问此路由将被重定向到登录页面。

`TeamsView` 使用 `@RolesAllowed("ADMIN")` 来限制仅允许具有管理员角色的用户访问。尽管 "user" 和 "admin" 账户都是经过身份验证的用户，但只有具有管理员角色的用户可以访问此路由，因为它具有 `USER` 和 `ADMIN` 角色。用户账户仅具有 `USER` 角色，因此尝试访问此路由会将他们重定向到访问拒绝页面。

:::tip 安全注释
有关所有可用注释，请参见 [安全注释指南](/docs/security/annotations)。
:::

## 添加注销功能 {#add-logout-capability}

使用 `SpringSecurityFormSubmitter` 创建一个注销按钮：

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

单击时，这将向 Spring Security 的 `/logout` 端点提交表单，该端点清除用户的会话并重定向到登录页面，并附带注销成功消息。

## 它是如何一起工作的 {#how-it-works-together}

当 Spring Boot 启动您的应用程序时：

1. **自动配置检测** 到 `webforj-spring-boot-starter` 和 `spring-boot-starter-security` 依赖项
2. **安全管理器自动创建** 以桥接 webforJ 路由和 Spring Security 身份验证
3. **注册安全评估器** 以处理 `@AnonymousAccess`、`@PermitAll`、`@RolesAllowed` 和 `@DenyAll` 注释
4. **路由观察者附加** 以拦截导航并在呈现组件之前评估安全规则

您无需手动连接这些组件——Spring Boot 的自动配置处理集成。您只需定义 `SecurityConfig` 和用户管理及页面位置。

当用户导航时：

1. 安全观察者拦截导航
2. 评估器验证路由的安全注释
3. Spring Security 的 `SecurityContextHolder` 提供身份验证信息
4. 如果已授权，则组件呈现；否则，用户被重定向
