---
title: Getting Started
sidebar_position: 2
description: Add the webforj-bbj-security dependency, provide login and deny views, and protect the first routes of a BBj app.
---

This guide adds route security to a webforJ app that runs on BBjServices. It covers the dependency, the views for the authentication and deny paths, and the first protected route.

## Add the dependency {#add-the-dependency}

Add `webforj-bbj-security` to the `pom.xml` of the app. The app keeps `webforj` with `webforj-engine` excluded, and adds `webforj-engine` with the `provided` scope. The app needs no BBj JAR files, because BBjServices supplies the BBj classes at runtime.

```xml
<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
    <exclusions>
      <exclusion>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-engine</artifactId>
      </exclusion>
    </exclusions>
  </dependency>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-engine</artifactId>
    <version>${webforj.version}</version>
    <scope>provided</scope>
  </dependency>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-bbj-security</artifactId>
    <version>${webforj.version}</version>
  </dependency>
</dependencies>
```

The library registers itself when the app starts. The app needs no activation code.

Route security is on by default, and a route without a security annotation requires a signed-in user. The authentication path is `/login`. See [Configuration](/docs/configuration/bbj-security/configuration) to change these defaults and the sign in settings.

## Provide the login and deny views {#provide-the-login-and-deny-views}

The library ships no views. The app provides a view for the authentication path and a view for the deny path. Both carry `@AnonymousAccess` so that a visitor who isn't allowed in can still reach them.

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Div> {
  private final Login login = new Login();

  public LoginView() {
    getBoundComponent().add(login);
    login.onSubmit(BBjSecurity::login);
    login.whenAttached().thenAccept(c -> login.open());
  }
}
```

The view hands the submitted user name and password to `BBjSecurity`. See [Sign in users](/docs/configuration/bbj-security/sign-in#sign-in-users).

```java title="DenyView.java"
@Route("/denied")
@AnonymousAccess
public class DenyView extends Composite<Div> {

  public DenyView() {
    getBoundComponent().add(new Paragraph("You don't have access to this page."));
  }
}
```

## Protect routes {#protect-routes}

The annotations work as described in [Security Annotations](/docs/security/annotations). A route with `@PermitAll` requires a signed-in user. A visitor without a sign-in is redirected to the authentication path, and the app remembers the requested location.

```java title="ProtectedView.java"
@Route("/protected")
@PermitAll
public class ProtectedView extends Composite<Div> {

  public ProtectedView() {
    getBoundComponent().add(new Paragraph("Only signed-in users see this page."));
  }
}
```

With `secure-by-default` set to `true`, a route without any annotation behaves like `@PermitAll`. Mark public routes with `@AnonymousAccess`.
