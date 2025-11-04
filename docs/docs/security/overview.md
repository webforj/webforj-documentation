---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Security <DocChip chip='since' label='25.10' />

:::note Public Preview
This feature is in public preview and ready for production use. During the preview period, APIs may be refined based on feedback from the developer community. Any changes will be announced in advance through release notes and migration guides will be provided when necessary.
:::

In modern web applications, **security** refers to controlling access to different parts of your app based on user identity and permissions. In webforJ, security provides a framework for **route-level access control**, where you can protect views, require authentication, and enforce role-based permissions.

## Traditional VS secured routing {#traditional-vs-secured-routing}

In traditional unsecured routing, all routes in your app are accessible to anyone who knows the URL. This means users can navigate to sensitive pages like admin panels or user dashboards without any authentication or authorization checks. The burden falls on developers to manually verify permissions in every component, leading to inconsistent security enforcement and potential vulnerabilities.

This approach introduces several problems:

1. **Manual checks**: Developers must remember to add security logic in every protected view or layout.
2. **Inconsistent enforcement**: Security checks scattered throughout the codebase lead to gaps and errors.
3. **Maintenance overhead**: Changing access rules requires updating multiple files.
4. **No centralized control**: No single place to understand or manage app security.

**Secured routing** in webforJ solves this by enabling access control directly at the route level. The security system automatically enforces rules before any component is rendered, providing a centralized, declarative approach to app security. Here's how it works:

1. **Declarative annotations**: Mark routes with security annotations to define access requirements.
2. **Automatic enforcement**: The security system checks permissions before rendering any view.
3. **Centralized configuration**: Define security behavior in one place and apply it consistently.
4. **Flexible implementations**: Choose between Spring Security integration or a custom plain Java implementation.

This design enables **authentication** (verifying the identity of the user) and **authorization** (verifying what the user can access), so only authorized users are granted access to protected routes. Unauthorized users are automatically redirected or denied access based on the configured security rules.

## Example of secured routing in webforJ {#example-of-secured-routing-in-webforj}

Here's an example showing different security levels in a webforJ app:

```java title="LoginView.java"
// Public login page - anyone can access
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {
  private final Login self = getBoundComponent();

  public LoginView() {  
    self.onSubmit(e -> {
      handleLogin(e.getUsername(), e.getPassword());
    });

    whenAttached().thenAccept(c -> {
      self.open();
    });
  }
}
```

```java title="ProductsView.java"
// Products - requires authentication
@Route(value = "/", outlet = MainLayout.class)
public class ProductsView extends Composite<FlexLayout> {

  public ProductsView() {
    // products view
  }
}
```

```java title="InvoicesView.java"
// Invoices - requires ACCOUNTANT role
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // invoices view
  }
}
```

In this setup:

- The `LoginView` is marked with `@AnonymousAccess`, allowing unauthenticated users to access it.
- The `ProductsView` has no security annotation, meaning it requires authentication by default (when `secure-by-default` mode is enabled).
- The `InvoicesView` requires the `ACCOUNTANT` role, so only users with accounting permissions can access invoices.

## How security works {#how-security-works}

When a user attempts to navigate to a route, the security system follows this flow:

1. **Navigation initiated**: User clicks a link or enters a URL.
2. **Security verification**: Before rendering the component, the system evaluates security annotations and rules.
3. **Decision**: Based on the user's authentication status and roles:
   - **Grant**: Allow navigation and render the component.
   - **Deny**: Block navigation and redirect to the login page or access denied page.
4. **Render or redirect**: Either the requested component displays, or the user is redirected appropriately.

With automatic enforcement, security rules are applied consistently across your entire app, so access control is handled before any component is rendered and developers don't need to add manual checks in each view.

## Authentication VS authorization {#authentication-vs-authorization}

To implement security in your app correctly, it's important to know the difference between these two concepts:

- **Authentication**: Verifying who the user is. This typically happens during login when the user provides credentials (username and password). Once authenticated, the user's identity is stored in the session or security context.

- **Authorization**: Verifying what the authenticated user can access. This involves checking if the user has the required roles or permissions to access a specific route. Authorization happens every time a user navigates to a protected route.

webforJ's security system handles both aspects:

- Annotations like `@PermitAll` handle authentication requirements.
- Annotations like `@RolesAllowed` handle authorization requirements.

## Getting started {#getting-started}

This guide assumes you're using **Spring Boot with Spring Security**, which is the recommended approach for most webforJ applications. Spring Security provides industry-standard authentication and authorization with automatic configuration through Spring Boot.

The rest of this documentation walks you through securing your routes with Spring Security, from basic setup to advanced features. If you're not using Spring Boot or need a custom security implementation, see the [Security Architecture guide](/docs/security/architecture/overview) to learn how the system works and how to implement custom security.

## Topics {#topics}

<DocCardList className="topics-section" />
