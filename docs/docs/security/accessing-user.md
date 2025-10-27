---
title: Accessing User
sidebar_position: 4
---

Spring Security stores authenticated user information in the `SecurityContextHolder`, providing access to username, roles, and authorities throughout your app. This section shows how to retrieve and use this information in webforJ views and components.

## Get current user information {#get-current-user-information}

Spring Security stores authenticated user information in the `SecurityContextHolder`, which provides thread-safe access anywhere in your app:

```java
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

// Get current authentication
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

// Get username
String username = authentication.getName();

// Get authorities (roles)
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

// Check if user has specific role
boolean isAdmin = authorities.stream()
  .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
```

The `SecurityContextHolder` is Spring Security's central mechanism for accessing the current user's authentication details. It works throughout your app, including in webforJ view constructors and methods.

## Display user information in views {#display-user-information-in-views}

Access user information directly in your webforJ views to display personalized content:

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Display user information
    H1 welcome = new H1("Welcome, " + username + "!");

    self.add(welcome);
  }
}
```

## Conditional rendering based on roles {#conditional-rendering-based-on-roles}

Show or hide UI elements based on user roles to control what features users see:

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // Check for specific role
    boolean isAdmin = auth.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // Conditionally add admin-only button
    if (isAdmin) {
      Button adminPanel = new Button("Admin Panel");
      adminPanel.onClick(e -> Router.getCurrent().navigate(AdminView.class));
      self.add(adminPanel);
    }
  }
}
```