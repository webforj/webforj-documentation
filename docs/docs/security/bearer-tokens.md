---
title: Bearer Tokens
sidebar_position: 7
description: Sign in to webforJ routes with a JSON Web Token from a header or a cookie, and map token roles to Spring Security authorities.
---

<DocChip chip='since' label='26.10' />

An app behind an identity provider or a gateway often receives a JSON Web Token (JWT) instead of a login form submission. The webforJ Spring integration accepts such a bearer token as the login for routes. Spring Security checks the token, and webforJ keeps the login in the HTTP session, so route security annotations and `@RouteAccess` expressions apply to it, and `SecurityContextHolder` returns the token user.

:::tip[Learn more about resource servers]
Spring Security calls an app that accepts bearer tokens a resource server. See the [Spring Security JWT resource server documentation](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html) for every option of the token check.
:::

## Add the dependencies {#add-the-dependencies}

Add the Spring Security starter and the resource server starter to the `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security-oauth2-resource-server</artifactId>
</dependency>
```

## Configure the token check {#configure-the-token-check}

Spring Security checks the token signature with the keys of the identity provider. Set the address of the key set, or the issuer address, in `application.properties`:

```properties title="application.properties"
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://idp.example.com/.well-known/jwks.json
```

```properties title="application.properties"
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://idp.example.com
```

Then call `bearerToken` on the webforJ configurer and enable the resource server with `oauth2ResourceServer`:

```java title="SecurityConfig.java"
http.with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
    .loginPage(LoginView.class)
    .bearerToken("jwt"));
http.oauth2ResourceServer(server -> server.jwt(jwt -> jwt.jwtAuthenticationConverter(converter)));
```

The argument of `bearerToken` is the name of the cookie that holds the token. The `converter` turns the token claims into authorities, see [Map token roles](#map-token-roles).

:::warning Resource server required
`bearerToken` needs `oauth2ResourceServer` in the same filter chain. Without it, the app fails at startup with an `IllegalStateException`.
:::

## Token sources {#token-sources}

The token is read from two places:

- The `Authorization: Bearer <token>` header, on every request.
- The named cookie, only when the request carries no header token, and only on `GET`, `HEAD` and `OPTIONS` requests and on webforJ framework requests.

Spring Security exempts a request that carries a bearer token from CSRF checks. A browser also sends cookies on cross site requests, so the cookie isn't read on requests that change state. A form or API `POST` with only the cookie keeps its CSRF protection. webforJ framework requests have their own protection and read the cookie on every method.

:::tip Cookie settings
Let the identity provider or the gateway set the token cookie with the `HttpOnly` flag, so scripts in the page can't read the token.
:::

## Login behavior {#login-behavior}

| Request | Result |
|---------|--------|
| Valid token | The login is kept in the HTTP session. Route checks with `@RolesAllowed`, `@PermitAll` and `@RouteAccess` use it, and `SecurityContextHolder` returns it, also on later requests of the UI. The token is still checked on every request. |
| No token | The stored token login is removed and the user is anonymous. A form login isn't affected. |
| Rejected cookie token | The stored token login is removed and the request continues as anonymous. Route security sends the user to the login page, and public pages still open. |
| Rejected header token | The response is `401` with a `WWW-Authenticate: Bearer` header, the answer API clients expect. |

A token is rejected when it's expired, has a wrong signature or isn't signed. When the token expires or the cookie is removed while a page is open, the next navigation in the app runs as an anonymous user.

## Map token roles {#map-token-roles}

`@RolesAllowed` and `hasRole(...)` check authorities with the `ROLE_` prefix, while `hasAuthority(...)` checks the exact authority. See [Security annotations](/docs/security/annotations#rolesallowed-role-based-routes) and [`SpEL` expressions](/docs/security/spel-expressions#built-in-security-functions).

By default, Spring Security turns the `scope` or `scp` claim of a token into authorities with the `SCOPE_` prefix and knows no role claim. Without a mapping, `@RolesAllowed` denies every token user, while `@RouteAccess("hasAuthority('SCOPE_reports')")` works.

The app maps roles with Spring Security's converters. The following configuration maps a `roles` claim to `ROLE_` authorities and keeps the scopes:

```java title="SecurityConfig.java"
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import com.webforj.spring.security.WebforjSecurityConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter scopes = new JwtGrantedAuthoritiesConverter();
    JwtGrantedAuthoritiesConverter roles = new JwtGrantedAuthoritiesConverter();
    roles.setAuthoritiesClaimName("roles");
    roles.setAuthorityPrefix("ROLE_");

    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(
        new DelegatingJwtGrantedAuthoritiesConverter(List.of(scopes, roles)));
    return converter;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationConverter converter)
      throws Exception {
    http.with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
        .loginPage(LoginView.class)
        .bearerToken("jwt"));
    http.oauth2ResourceServer(server -> server.jwt(jwt -> jwt.jwtAuthenticationConverter(converter)));
    return http.build();
  }
}
```

A token with `"roles": ["ADMIN"]` and `"scope": "reports"` then carries the authorities `ROLE_ADMIN` and `SCOPE_reports`. Routes check both kinds:

```java title="AdminView.java"
@Route("/admin")
@RolesAllowed("ADMIN")
public class AdminView extends Composite<Div> {
  // Only tokens with the ADMIN role
}
```

```java title="ReportsView.java"
@Route("/reports")
@RouteAccess("hasAuthority('SCOPE_reports')")
public class ReportsView extends Composite<Div> {
  // Only tokens with the reports scope
}
```

### Roles in a nested claim {#roles-in-a-nested-claim}

Identity providers place roles in different claims. `setAuthoritiesClaimName` reads a top level claim. For a nested claim, such as a `roles` list inside a `realm_access` object, write a converter that returns the authorities:

```java title="RealmRolesConverter.java"
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class RealmRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
    if (realmAccess == null || !(realmAccess.get("roles") instanceof Collection<?> roles)) {
      return List.of();
    }

    return roles.stream()
        .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role))
        .toList();
  }
}
```

Pass it to the delegating converter in place of the `roles` converter:

```java
converter.setJwtGrantedAuthoritiesConverter(
    new DelegatingJwtGrantedAuthoritiesConverter(List.of(scopes, new RealmRolesConverter())));
```

## Limits {#limits}

- An authentication entry point set on `oauth2ResourceServer` isn't used for rejected header tokens. These always receive the `401` response described in [Login behavior](#login-behavior).
- A `POST` with only the cookie to a path outside webforJ counts as a request without a token and removes the stored token login. The next page load or webforJ request reads the cookie again.
- When a form login is stored in the session, a token login on top of it also receives the authorities of the form login.
