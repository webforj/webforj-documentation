---
title: Getting Started
sidebar_position: 2
---

Spring Security provides authentication and authorization for Spring Boot applications. When integrated with webforJ, it protects routes using annotations while Spring handles user management and sessions.

This guide covers adding Spring Security to your webforJ app, configuring authentication, creating login views, and protecting routes with role-based access control.

:::tip[Learn more about Spring Security]
For a comprehensive understanding of Spring Security features and concepts, see [Spring Security documentation](https://docs.spring.io/spring-security/reference/).
:::

## Add Spring Security dependency {#add-spring-security-dependency}

Add the Spring Security starter to your `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

This single dependency brings in Spring Security's authentication framework, password encoders, and session management. The version is automatically managed by your Spring Boot parent POM.

## Configure Spring security {#configure-spring-security}

Create a security configuration class that connects Spring Security with webforJ. This class defines how users are authenticated and which pages handle login and access denial:

```java title="SecurityConfig.java"
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
            .loginPage(/signin)
            .accessDeniedPage(/access-denied)
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

This configuration creates four Spring beans that work together:

**`SecurityFilterChain`** connects Spring Security with webforJ's routing system. The `WebforjSecurityConfigurer.webforj()` method integrates Spring Security authentication with webforJ routing. You specify which component classes handle login and access denial and Spring will enforce authentication before rendering protected routes.

- The `loginPage()` method tells Spring Security where users should authenticate. Pass your login view component class, and webforJ automatically resolves the route path from the `@Route` annotation. When unauthenticated users try to access protected routes, they're redirected here.

- The `accessDeniedPage()` method defines where authenticated users go when they lack permissions for a route. For example, a user trying to access an admin-only route gets redirected to this page.

- The `logout()` method enables the logout endpoint at `/logout`. After logging out, users are redirected back to the login page with a `?logout` parameter.

**`PasswordEncoder`** uses BCrypt to hash passwords securely. Spring Security automatically applies this encoder during login to compare the submitted password with the stored hash.

**`UserDetailsService`** tells Spring Security where to find user information during authentication. This example uses an in-memory store with two users: `user/password` and `admin/admin`.

**`AuthenticationManager`** coordinates the authentication process. It uses a provider that loads users from the `UserDetailsService` and verifies passwords with the `PasswordEncoder`.

## Create login view {#create-login-view}

Create a view that presents a login dialog and submits credentials to Spring Security. The following view uses the [`Login`](/docs/components/login) component:

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
      Toast.show("Invalid username or password. Please try again.", Theme.DANGER);
    }

    if (queryParams.containsKey("logout")) {
      Toast.show("You have been logged out successfully.", Theme.GRAY);
    }
  }
}
```

The `@AnonymousAccess` annotation marks this route as public so unauthenticated users can access the login page. Without this annotation, users would be redirected away from the login page, creating an infinite loop.

The `setAction("/signin")` line is critical, it configures the Login component to POST credentials to Spring's authentication endpoint. Spring intercepts this submission, verifies the credentials, and either grants access or redirects back with an error parameter.

The `onDidEnter` observer checks for query parameters that Spring adds to communicate results. When authentication fails, Spring redirects to `/signin?error`. After logging out, it redirects to `/signin?logout`. The observer displays appropriate messages based on these parameters.

:::tip Endpoint matching
The path in `setAction("/signin")` must match your `@Route("/signin")` path. Spring intercepts form submissions to this exact path for authentication. If you need different paths for the login page and authentication processing, configure them separately in `SecurityConfig`:

```java
.loginPage("/signin", "/authenticate")
```

This displays the login page at `/signin` but processes authentication at `/authenticate`.
:::

## Create access denied view {#create-access-denied-view}

Create a view that displays when users lack permission to access a route:

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("Oops! This area is VIP only.");
    Paragraph subMessage = new Paragraph(
        "Looks like you tried to sneak into the executive lounge! Either grab better credentials or head back to the public areas where the coffee is free anyway.");

    self.add(message, subMessage);
  }
}
```

This view is rendered when authenticated users try to access routes they don't have permission for, like a user attempting to access an admin-only route.

## Protect your routes {#protect-your-routes}

With authentication configured, you can now protect your routes using security annotations. These annotations tell Spring Security who can access each view, and the security system automatically enforces these rules before rendering any component.

When a user navigates to a route, Spring Security intercepts the navigation and checks the route's security annotations. If the user is authenticated (logged in with valid credentials) and has the required permissions, the view renders normally. If not, they're redirected to either the login page or access denied page.

```java title="InboxView.java"
// Inbox - accessible to all authenticated users
@Route(value = "/", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Inbox"));
  }
}
```

```java title="TeamsView.java" {3}
// Teams - requires ADMIN role
@Route(value = "/teams", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TeamsView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public TeamsView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Teams"));
  }
}
```

The `InboxView` has no annotation, which means any authenticated user can access it. When a user successfully logs in with the credentials you defined in `UserDetailsService` (`user/password` or `admin/admin`), they can view this route. Unauthenticated users attempting to access this route are redirected to the login page.

The `TeamsView` uses `@RolesAllowed("ADMIN")` to restrict access to users with the admin role. Even though both the "user" and "admin" accounts are authenticated users, only users with the admin role can access this route because it has both `USER` and `ADMIN` roles. The user account only has the `USER` role, so attempting to access this route redirects them to the access denied page.

:::tip Security annotations
See the [Security Annotations guide](/docs/security/annotations) for all available annotations.
:::

## Add logout capability {#add-logout-capability}

Use `SpringSecurityFormSubmitter` to create a logout button:

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

When clicked, this submits a form to Spring Security's `/logout` endpoint, which clears the user's session and redirects to the login page with a logout success message.

## How it works together {#how-it-works-together}

When Spring Boot starts your app:

1. **Auto-configuration detects** both `webforj-spring-boot-starter` and `spring-boot-starter-security` dependencies
2. **Security manager is created** automatically to bridge webforJ routing and Spring Security authentication
3. **Security evaluators are registered** to handle `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, and `@DenyAll` annotations
4. **Route observer is attached** to intercept navigation and evaluate security rules before rendering components

You don't manually wire these components—Spring Boot's auto-configuration handles the integration. You only define your `SecurityConfig` with user management and page locations.

When a user navigates:

1. The security observer intercepts the navigation
2. Evaluators verify the route's security annotations
3. Spring Security's `SecurityContextHolder` provides authentication information
4. If authorized, the component renders; otherwise, the user is redirected

