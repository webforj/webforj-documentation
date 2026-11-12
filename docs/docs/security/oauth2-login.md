---
title: OAuth2 Login
sidebar_position: 7
sidebar_class_name: new-content
description: Sign users in through an external OAuth2 or OpenID Connect provider with Spring Security and return them to the route they requested.
---

# OAuth2 login <DocChip chip='since' label='26.10' />

OAuth2 login lets users sign in with an account they already have at an external provider, such as a company identity server or a social login service. The provider checks the credentials, and Spring Security creates the session in your app. webforJ routes stay protected by the same [security annotations](/docs/security/annotations), and after signing in, the user returns to the route they asked for.

This guide covers the dependency, the provider registration, the security configuration, the login view, and logout. It builds on [Getting Started](/docs/security/getting-started), which sets up Spring Security for form login.

:::info Spring Security only
OAuth2 login is available only when using the Spring integration.
:::

:::info Example provider
This guide shows how to configure OAuth2 login with [Keycloak](https://www.keycloak.org/) running locally, but it works with any OAuth2 or OpenID Connect provider. Other providers use their own registration ID, client ID, client secret, and issuer URI.
:::

:::tip[Learn more about Spring Security]
For provider specific options, see the [Spring Security documentation](https://docs.spring.io/spring-security/reference/).
:::

## Add the OAuth2 client dependency {#add-the-oauth2-client-dependency}

Add the Spring Security starter and the OAuth2 client starter to your `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security-oauth2-client</artifactId>
</dependency>
```

The versions are managed by your Spring Boot parent POM.

## Register the provider {#register-the-provider}

Each provider is a client registration with an ID of your choice. Add the registration to `application.properties` with the client ID and secret the provider issued for your app. For an OpenID Connect provider, the issuer URI is enough for Spring to discover the remaining provider endpoints:

```properties title="application.properties"
spring.security.oauth2.client.registration.keycloak.client-id=your-client-id
spring.security.oauth2.client.registration.keycloak.client-secret=your-client-secret
spring.security.oauth2.client.registration.keycloak.scope=openid,profile,email
spring.security.oauth2.client.provider.keycloak.issuer-uri=http://localhost:8180/realms/webforj
```

In this example the registration ID is `keycloak`. At the provider, register the redirect URI `http://localhost:8080/login/oauth2/code/keycloak`, with your app's address and the registration ID in place of the example values. The provider sends the user back to this address after signing in.

:::warning Keep secrets out of source control
The client secret grants access to the provider on behalf of your app. Supply it through an environment variable or a secret store instead of a committed file. See [Managing Secrets](/docs/security/application-security/managing-secrets).
:::

## Configure security {#configure-security}

Call `oauth2Login()` with the view that offers the providers:

```java title="SecurityConfig.java"
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
            .oauth2Login(LoginView.class)
            .accessDeniedPage(AccessDenyView.class)
            .logout())
        .build();
  }
}
```

The `oauth2Login()` method resolves the route path of the view from its `@Route` annotation and permits requests to that path. Unauthenticated users who open a protected route are redirected to this view. After the provider confirms the sign in, the user lands on the route they first requested.

This configuration needs no `PasswordEncoder`, `UserDetailsService` or `AuthenticationManager`, because the provider checks the credentials.

## Create the login view {#create-the-login-view}

The login view offers one button per provider. A button starts the provider login by sending the browser to `/oauth2/authorization/{registrationId}`:

```java title="LoginView.java"
@Route("/signin")
@AnonymousAccess
public class LoginView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public LoginView() {
    Button keycloak = new Button("Sign in with Keycloak", ButtonTheme.PRIMARY);
    keycloak.onClick(e -> Page.getCurrent().open("/oauth2/authorization/keycloak", "_self"));

    self.add(new H2("Sign in"), keycloak);
  }
}
```

The `@AnonymousAccess` annotation lets unauthenticated users open the view. The `_self` window name opens the provider page in the current tab, so the provider sends the user back to the same tab. Add one button for each registration in `application.properties`.

## Combine with form login {#combine-with-form-login}

`oauth2Login()` works alone or together with `loginPage()`. With both, users choose between a username and password and an external provider:

```java title="SecurityConfig.java"
http.with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
    .loginPage(LoginView.class)
    .oauth2Login(LoginView.class)
    .logout());
```

When both are configured, unauthenticated users are redirected to the form login page. Passing the same view to both methods shows both sign in options on one page. For example, the view from [Getting Started](/docs/security/getting-started#create-login-view) can add a provider button below the form with `addToAfterForm()`. The button opens `/oauth2/authorization/{registrationId}` the same way as in the [login view](#create-the-login-view).

## Logout {#logout}

The `logout()` method enables the logout endpoint at `/logout`, the same as with form login. The logout button from [Getting Started](/docs/security/getting-started#add-logout-capability) works unchanged:

```java
IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

Logout ends the session in your app and redirects the user to the logout success URL. This URL defaults to the login page with a `?logout` parameter, for example `/signin?logout`. The user stays signed in at the provider, so the next sign in through that provider needs no credentials.

### Provider logout {#provider-logout}

To end the session at the provider too, add `oauth2Logout()`:

```java title="SecurityConfig.java"
http.with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
    .oauth2Login(LoginView.class)
    .oauth2Logout()
    .logout());
```

What happens on logout then depends on how the user signed in:

- **OpenID Connect**: The app ends the session and sends the user to the provider to end the provider session too. The provider then redirects the user to the logout success URL.
- **Plain OAuth2 or form login**: The app ends the session and redirects the user to the logout success URL directly. This includes sessions from providers that only support OAuth2, such as GitHub.

With `oauth2Logout()`, the logout success URL defaults to the login page without a parameter, for example `/signin`. Pass a different URL as the second argument of `logout()`:

```java
.logout("/logout", "/goodbye")
```

:::warning Register the post logout redirect URI
For OpenID Connect sessions, the logout success URL is sent to the provider as the post logout redirect URI. Register the full address, for example `http://localhost:8080/signin`, as an allowed post logout redirect URI at the provider. The provider compares the address exactly, including any query parameters.
:::

## Authentication path property {#authentication-path-property}

The `webforj.security.authentication-path` property sets the path that unauthenticated users are redirected to. Without it, the path comes from the security configuration: the form login page when `loginPage()` is set, otherwise the `oauth2Login()` view.

```properties title="application.properties"
webforj.security.authentication-path=/signin
```

A configured property always wins over the security configuration. It also turns on form login at that path, even when the configuration only calls `oauth2Login()`.
