---
sidebar_position: 30
title: 测试
description: >-
  Test a webforJ Spring Boot app without a browser with @SpringBootTest and
  @WebforjTest, with injected beans and Spring Security on routes.
sidebar_class_name: new-content
_i18n_hash: be6b059b304f8b957535c4ce318ac31c
---
# 测试 <DocChip chip='since' label='26.03' />

[TestBed](/docs/testing/testbed/overview) 会在普通的 JUnit 测试中运行视图，无需浏览器。在 Spring Boot 应用中，视图从 Spring 获取其 bean，路由也受 Spring Security 保护，因此测试还需要 Spring 上下文。测试类使用 `@SpringBootTest` 加载上下文，并使用 `@WebforjTest` 加载视图；两者可以协同工作，无需其他设置。

本页介绍 Spring 带来的功能。无论是否使用 Spring，查找组件、与组件交互、断言和快照的用法都相同，详见 [TestBed](/docs/testing/testbed/overview) 文档。

<!-- INTRO_END -->

## 添加依赖 {#adding-the-dependency}

将 `webforj-spring-testbed` 添加为测试范围依赖。webforJ 物料清单会管理其版本，该模块还会引入 JUnit TestBed：

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-testbed</artifactId>
  <scope>test</scope>
</dependency>
```

使用 `webforj-spring` 风格生成的项目已经导入了物料清单和提供 JUnit 的 `spring-boot-starter-test`。

## 使用 Spring bean 的视图 {#views-with-spring-beans}

下面的视图通过构造函数接收一个服务，并向用户输入的姓名问好：

```java title="WelcomeView.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import com.webforj.router.security.annotation.AnonymousAccess;
import com.webforj.samples.spring.GreetingService;

@Route("/")
@AnonymousAccess
public class WelcomeView extends Composite<Div> {

  public WelcomeView(GreetingService greetings) {
    TextField name = new TextField("Name");
    Paragraph greeting = new Paragraph();
    name.onModify(e -> greeting.setText(greetings.greet(name.getValue())));

    getBoundComponent().add(name, greeting);
  }
}
```

```java title="GreetingService.java" showLineNumbers
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

  public String greet(String name) {
    return "Welcome, " + name + "!";
  }
}
```

测试同时使用这两个注解。`@SpringBootTest` 会从 `@SpringBootApplication` 类加载应用上下文，和其他 Spring Boot 测试一样；`@WebforjTest` 则会注册视图。测试通过 `@Autowired` 获取同一个服务 bean，因此可以将视图显示的内容与服务返回的内容进行比较：

```java title="WelcomeViewTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.samples.spring.GreetingService;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@WebforjTest(routeClasses = WelcomeView.class)
class WelcomeViewTest {
  @Autowired
  private GreetingService greetings;

  @Test
  void shouldGreetWithTheServiceSpringInjected(WebforjTestbed context) {
    context.navigate(WelcomeView.class);

    UserInteraction.type(
        ComponentLocator.of(TextField.class).byLabel("Name").locate(), "Ada");

    verifyThat(ComponentLocator.of(Paragraph.class).locate())
        .hasText(greetings.greet("Ada"));
  }
}
```

`navigate` 会通过 Spring 创建视图，因此视图获取服务的方式与应用运行时相同。视图也遵循其 bean [作用域](./scopes)。

每个测试都会获得独立的 `WebforjTestbed`，它在测试前启动、测试后结束，因此每个测试开始时页面都是空的。Spring 上下文会像其他 `@SpringBootTest` 一样在测试之间共享并缓存，因此单例 bean 会保留其在前一个测试中的状态。

## 选择路由 {#choosing-the-routes}

与任何 TestBed 测试一样，`@WebforjTest` 用于决定测试启动时包含哪些内容。`routeClasses` 会注册列出的视图，`routePackages` 会注册列出的包中所有带有 `@Route` 的类，而 `app` 会通过应用类的 `@Routify` 注解注册其路由，使应用以生产环境中的方式运行。测试单个视图时，只注册该视图。测试路由安全性时，则要启动应用，因为[只有应用启动时才会执行安全检查](#testing-secured-routes)。

## 测试受保护的路由 {#testing-secured-routes}

使用 [Spring Security](/docs/security/getting-started) 时，`@RolesAllowed` 和 `@AnonymousAccess` 等路由注解会在测试导航期间生效，与应用运行时一样。以下配置会保护路由，并指定登录页面和拒绝访问页面：

```java title="SecurityConfig.java" showLineNumbers
import com.webforj.samples.spring.views.DeniedView;
import com.webforj.samples.spring.views.LoginView;
import com.webforj.spring.security.WebforjSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .with(
            WebforjSecurityConfigurer.webforj(),
            configurer -> configurer.loginPage(LoginView.class)
                .accessDeniedPage(DeniedView.class))
        .build();
  }

  @Bean
  UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(
        User.withDefaultPasswordEncoder()
            .username("ada")
            .password("secret")
            .roles("ADMIN")
            .build(),
        User.withDefaultPasswordEncoder()
            .username("bob")
            .password("secret")
            .roles("USER")
            .build());
  }
}
```

下面的视图仅对管理员开放：

```java title="AdminView.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.router.annotation.Route;
import jakarta.annotation.security.RolesAllowed;

@Route("admin")
@RolesAllowed("ADMIN")
public class AdminView extends Composite<Div> {

  public AdminView() {
    getBoundComponent().add(new H1("Administration"));
  }
}
```

测试会启动应用，并使用由 Spring Boot 管理的 `spring-security-test` 中的 `@WithMockUser` 登录。没有该注解的测试会以匿名访客身份运行：

```java title="AdminViewTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.webforj.samples.spring.Application;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@WebforjTest(app = Application.class)
class AdminViewTest {
  @Test
  @WithMockUser(username = "ada", roles = "ADMIN")
  void shouldOpenTheAdministrationForAnAdmin(WebforjTestbed context) {
    AdminView admin = context.navigate(AdminView.class);

    verifyThat(admin).isAttached();
  }

  @Test
  @WithMockUser(username = "bob", roles = "USER")
  void shouldDenyAUserWithoutTheRole(WebforjTestbed context) {
    assertInstanceOf(DeniedView.class, context.navigate("admin"));
  }

  @Test
  void shouldSendAnAnonymousVisitorToTheLogin(WebforjTestbed context) {
    assertInstanceOf(LoginView.class, context.navigate("admin"));
  }
}
```

用户拥有视图要求的角色时，`navigate(Class)` 会打开该视图。访问遭拒时，路由安全机制会将用户重定向到配置中指定的拒绝访问页面或登录页面；`navigate(String)` 会返回在该页面上呈现的视图，因此测试可以通过视图类型来检查重定向。使用被拒绝访问的视图调用 `navigate(Class)` 时会失败，并显示实际呈现的视图名称。

使用 `@WebforjTest(app = ...)` 启动应用时，路由安全机制才会运行。使用 `routeClasses` 或 `routePackages` 时，视图会在没有路由安全机制的情况下呈现，因此测试可以单独检查受保护的视图，无需登录。
