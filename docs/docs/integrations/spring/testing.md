---
sidebar_position: 30
title: Testing
description: Test a webforJ Spring Boot app without a browser with @SpringBootTest and @WebforjTest, with injected beans and Spring Security on routes.
sidebar_class_name: new-content
---

# Testing <DocChip chip='since' label='26.03' />

The [TestBed](/docs/testing/testbed/overview) runs views in a plain JUnit test and needs no browser. In a Spring Boot app, the views get their beans from Spring and their routes are guarded by Spring Security, so the test needs the Spring context as well. A test class carries `@SpringBootTest` for the context and `@WebforjTest` for the views, and the two work together without further setup.

This page covers what Spring adds. Finding components, acting on them, assertions and snapshots work the same way with or without Spring, and the [TestBed](/docs/testing/testbed/overview) pages cover them.

<!-- INTRO_END -->

## Adding the dependency {#adding-the-dependency}

Add `webforj-spring-testbed` with test scope. The webforJ bill of materials manages its version, and the module brings in the JUnit TestBed:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-testbed</artifactId>
  <scope>test</scope>
</dependency>
```

A project generated with the `webforj-spring` flavor already imports the bill of materials and `spring-boot-starter-test`, which provides JUnit.

## Views with Spring beans {#views-with-spring-beans}

The following view receives a service through its constructor and greets the name the user types:

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

The test carries both annotations. `@SpringBootTest` starts the app context from the `@SpringBootApplication` class, the way any Spring Boot test does, and `@WebforjTest` registers the view. The test receives the same service bean with `@Autowired`, so it can compare what the view shows with what the service returns:

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

`navigate` creates the view through Spring, so the view gets the service the same way it does in the running app. Views also honor their bean [scopes](./scopes).

Every test gets its own `WebforjTestbed`, which starts before the test and ends after it, so each test begins with an empty page. The Spring context is shared and cached across tests the way Spring caches it for any `@SpringBootTest`, so a singleton bean keeps its state from one test to the next.

## Choosing the routes {#choosing-the-routes}

`@WebforjTest` decides what the test starts with, as in any TestBed test. `routeClasses` registers exactly the listed views, `routePackages` every `@Route` class in the listed packages, and `app` the routes of the app class through its `@Routify` annotation, with the app running as it does in production. A test of one view registers that view. A test of route security launches the app, because [security runs only with the app](#testing-secured-routes).

## Testing secured routes {#testing-secured-routes}

With [Spring Security](/docs/security/getting-started), route annotations such as `@RolesAllowed` and `@AnonymousAccess` apply during navigation in a test, as they do in the running app. The following configuration guards the routes and names the login and the access denied pages:

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

The following view is open to administrators only:

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

The test launches the app and signs in a user with `@WithMockUser` from `spring-security-test`, which Spring Boot manages. A test without it runs as an anonymous visitor:

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

With the role the view requires, `navigate(Class)` opens the view. When access is denied, route security redirects to the access denied page or the login page the configuration names, and `navigate(String)` returns the view that rendered there, so the test checks the redirect by its type. `navigate(Class)` with a denied view fails with a message that names the view that rendered instead.

Route security runs when the test launches the app with `@WebforjTest(app = ...)`. With `routeClasses` or `routePackages`, the views render without route security, so a test can check a secured view on its own, without signing in.
