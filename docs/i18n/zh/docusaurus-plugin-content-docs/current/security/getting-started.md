---
title: Getting Started
sidebar_position: 2
_i18n_hash: e8996d53f35e093d9ba65c54774d1935
---
Spring Security 为 Spring Boot 应用程序提供认证和授权。与 webforJ 集成时，它通过注解保护路由，同时 Spring 负责用户管理和会话。

本指南涵盖了将 Spring Security 添加到您的 webforJ 应用程序、配置认证、创建登录视图以及使用基于角色的访问控制保护路由。

:::tip[了解更多关于 Spring Security 的信息]
要全面了解 Spring Security 的特性和概念，请参阅 [Spring Security 文档](https://docs.spring.io/spring-security/reference/)。
:::

## 添加 Spring Security 依赖 {#add-spring-security-dependency}

将 Spring Security 启动器添加到您的 `pom.xml` 文件中：

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

这个单一的依赖引入了 Spring Security 的认证框架、密码编码器和会话管理。版本由您的 Spring Boot 父 POM 自动管理。

## 配置 Spring Security {#configure-spring-security}

创建一个安全配置类，将 Spring Security 与 webforJ 连接。此类定义用户如何进行认证以及哪个页面处理登录和访问拒绝：

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

此配置创建了四个协同工作的 Spring Bean：

**`SecurityFilterChain`** 将 Spring Security 连接到 webforJ 的路由系统。`WebforjSecurityConfigurer.webforj()` 方法将 Spring Security 认证与 webforJ 路由集成。您指定哪个组件类处理登录和访问拒绝，Spring 将在呈现受保护的路由之前强制执行认证。

- `loginPage()` 方法告诉 Spring Security 用户应该在哪里进行认证。传递您的登录视图组件类，webforJ 会根据 `@Route` 注解自动解析路由路径。当未认证的用户尝试访问受保护的路由时，他们会被重定向到这里。

- `accessDeniedPage()` 方法定义认证用户在缺少路由权限时的去向。例如，尝试访问仅管理员的路由的用户会被重定向到此页面。

- `logout()` 方法启用 `/logout` 的登出端点。用户登出后，将带着 `?logout` 参数重定向回登录页面。

**`PasswordEncoder`** 使用 BCrypt 来安全地哈希密码。Spring Security 在登录期间自动应用此编码器，以比较提交的密码与存储的哈希。

**`UserDetailsService`** 告诉 Spring Security 在认证期间在哪里找到用户信息。此示例使用内存存储，有两个用户：`user/password` 和 `admin/admin`。

**`AuthenticationManager`** 协调认证过程。它使用一个提供程序，从 `UserDetailsService` 加载用户并使用 `PasswordEncoder` 验证密码。

## 创建登录视图 {#create-login-view}

创建一个显示登录对话框并将凭据提交给 Spring Security 的视图。以下视图使用 [`Login`](/docs/components/login) 组件：

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
      Toast.show("用户名或密码无效。请重试。", Theme.DANGER);
    }

    if (queryParams.containsKey("logout")) {
      Toast.show("您已成功登出。", Theme.GRAY);
    }
  }
}
```

`@AnonymousAccess` 注解将此路由标记为公共，以便未认证的用户可以访问登录页面。如果没有这个注解，用户将不会被重定向至登录页面，导致无限循环。

`setAction("/signin")` 行是关键，它配置登录组件将凭据 POST 到 Spring 的认证端点。Spring 拦截此提交，验证凭据，或授予访问权限，或带着错误参数重定向回去。

`onDidEnter` 观察者检查 Spring 添加的查询参数以传达结果。当认证失败时，Spring 重定向到 `/signin?error`。登出后，它重定向到 `/signin?logout`。观察者会根据这些参数显示适当的消息。

:::tip 端点匹配
`setAction("/signin")` 中的路径必须与您的 `@Route("/signin")` 路径匹配。Spring 拦截对该确切路径的表单提交以进行认证。如果您需要不同的路径用于登录页面和认证处理，请在 `SecurityConfig` 中单独配置它们：

```java
.loginPage("/signin", "/authenticate")
```

这将在 `/signin` 显示登录页面，但在 `/authenticate` 处理认证。
:::

## 创建访问拒绝视图 {#create-access-denied-view}

创建一个视图，当用户没有权限访问某个路由时显示：

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("哎呀！该区域仅限 VIP。");
    Paragraph subMessage = new Paragraph(
        "看起来您试图潜入行政休息室！要么获取更好的凭据，要么返回公共区域，那里咖啡是免费的。");

    self.add(message, subMessage);
  }
}
```

当认证用户尝试访问他们没有权限的路由时，会呈现此视图，例如用户尝试访问仅管理员的路由时。

## 保护您的路由 {#protect-your-routes}

配置好认证后，您现在可以使用安全注解来保护路由。这些注解告诉 Spring Security 谁可以访问每个视图，安全系统会在呈现任何组件之前自动强制执行这些规则。

当用户导航到某个路由时，Spring Security 拦截导航并检查路由的安全注解。如果用户已认证（使用有效凭据登录）并拥有所需权限，视图会正常呈现。如果没有，他们将被重定向到登录页面或访问拒绝页面。

```java title="InboxView.java"
// 收件箱 - 所有认证用户均可访问
@Route(value = "/", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("收件箱"));
  }
}
```

```java title="TeamsView.java" {3}
// 团队 - 需要 ADMIN 角色
@Route(value = "/teams", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TeamsView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public TeamsView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("团队"));
  }
}
```

`InboxView` 没有注解，这意味着任何已认证用户都可以访问它。当用户使用您在 `UserDetailsService` 中定义的凭据（`user/password` 或 `admin/admin`）成功登录时，他们可以查看此路由。尝试访问此路由的未认证用户将被重定向到登录页面。

`TeamsView` 使用 `@RolesAllowed("ADMIN")` 来限制对具有管理员角色的用户的访问。尽管 "user" 和 "admin" 两个账户都是认证用户，但只有拥有管理员角色的用户才能访问该路由，因为它同时具备 `USER` 和 `ADMIN` 角色。用户账户只有 `USER` 角色，因此尝试访问该路由会被重定向到访问拒绝页面。

:::tip 安全注解
有关所有可用注解，请参见 [安全注解指南](/docs/security/annotations)。
:::

## 添加登出功能 {#add-logout-capability}

使用 `SpringSecurityFormSubmitter` 创建一个登出按钮：

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

单击后，它会将表单提交到 Spring Security 的 `/logout` 端点，这会清除用户的会话并重定向到登录页面，带有登出成功消息。

## 它是如何协同工作的 {#how-it-works-together}

当 Spring Boot 启动您的应用程序时：

1. **自动配置检测到** `webforj-spring-boot-starter` 和 `spring-boot-starter-security` 依赖。
2. **安全管理器自动创建，**以连接 webforJ 路由和 Spring Security 认证。
3. **安全评估器被注册，**以处理 `@AnonymousAccess`、`@PermitAll`、`@RolesAllowed` 和 `@DenyAll` 注解。
4. **路由观察者被附加，**以在呈现组件之前拦截导航并评估安全规则。

您不需要手动连接这些组件——Spring Boot 的自动配置处理集成。您只需定义 `SecurityConfig` 以进行用户管理和页面位置。

当用户导航时：

1. 安全观察者拦截导航。
2. 评估器验证路由的安全注解。
3. Spring Security 的 `SecurityContextHolder` 提供认证信息。
4. 如果授权，组件呈现；否则，用户被重定向。
