---
sidebar_position: 5
title: SpEL Expressions
---

Spring Expression Language (`SpEL`) provides a declarative way to define authorization rules directly in annotations. The `@RouteAccess` annotation evaluates `SpEL` expressions using Spring Security's built-in authorization functions.

:::info Spring Security only
`SpEL` expressions are available only when using the Spring integration.
:::

## Basic usage {#basic-usage}

The `@RouteAccess` annotation accepts a `SpEL` expression that evaluates to boolean:

```java
@Route("/admin/dashboard")
@RouteAccess("hasRole('ADMIN')")
public class AdminDashboardView extends Composite<Div> {
  // Only users with ROLE_ADMIN authority can access
}
```

If the expression evaluates to `true`, access is granted. If `false`, the user is redirected to the access denied page.

## Built-in security functions {#built-in-security-functions}

Spring Security provides the following authorization functions through `SecurityExpressionRoot`:

| Function | Parameters | Description | Example |
|----------|-----------|-------------|---------|
| `hasRole` | `String role` | Checks if user has the specified role (automatically prefixes with `ROLE_`) | `hasRole('ADMIN')` matches `ROLE_ADMIN` |
| `hasAnyRole` | `String... roles` | Checks if user has any of the specified roles | `hasAnyRole('ADMIN', 'MANAGER')` |
| `hasAuthority` | `String authority` | Checks if user has the exact authority string | `hasAuthority('REPORTS:READ')` |
| `hasAnyAuthority` | `String... authorities` | Checks if user has any of the specified authorities | `hasAnyAuthority('REPORTS:READ', 'REPORTS:WRITE')` |
| `isAuthenticated` | None | Returns `true` if user is authenticated | `isAuthenticated()` |

### Examples {#examples}

```java
// Role check
@Route("/admin")
@RouteAccess("hasRole('ADMIN')")
public class AdminView extends Composite<Div> { }

// Multiple roles
@Route("/staff")
@RouteAccess("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
public class StaffView extends Composite<Div> { }

// Authority check
@Route("/reports")
@RouteAccess("hasAuthority('REPORTS:READ')")
public class ReportsView extends Composite<Div> { }

// Require authentication
@Route("/profile")
@RouteAccess("isAuthenticated()")
public class ProfileView extends Composite<Div> { }
```

## Combining conditions {#combining-conditions}

Use boolean operators (`and`, `or`, `!`) to create complex authorization rules:

```java
// Both conditions required
@Route("/moderator/reports")
@RouteAccess("hasRole('MODERATOR') and hasAuthority('REPORTS:VIEW')")
public class ModeratorReportsView extends Composite<Div> { }

// Either condition grants access
@Route("/support")
@RouteAccess("hasRole('ADMIN') or hasRole('SUPPORT')")
public class SupportView extends Composite<Div> { }

// Negation
@Route("/trial/features")
@RouteAccess("isAuthenticated() and !hasAuthority('PREMIUM')")
public class TrialFeaturesView extends Composite<Div> { }

// Complex multi-line expression
@Route("/reports/advanced")
@RouteAccess("""
  hasRole('ADMIN') or
  (hasRole('ANALYST') and hasAuthority('REPORTS:ADVANCED'))
  """)
public class AdvancedReportsView extends Composite<Div> { }
```

## Combining with other annotations {#combining-with-other-annotations}

`@RouteAccess` works alongside standard security annotations. Evaluators run in priority order:

```java
@Route("/team/admin")
@RolesAllowed("USER")
@RouteAccess("hasAuthority('TEAM:ADMIN')")
public class TeamAdminView extends Composite<Div> {
  // Must have USER role AND TEAM:ADMIN authority
}
```

Evaluation order:
1. `@RolesAllowed` evaluator (priority 5) verifies `USER` role
2. If passed, `@RouteAccess` evaluator (priority 6) evaluates `SpEL` expression
3. If passed, custom evaluators run (priority 10+)

## Custom error codes {#custom-error-codes}

Provide meaningful error codes for access denials:

```java
@Route("/premium/features")
@RouteAccess(
  value = "hasAuthority('PREMIUM')",
  code = "PREMIUM_SUBSCRIPTION_REQUIRED"
)
public class PremiumFeaturesView extends Composite<Div> { }
```

The `code` parameter identifies the denial reason when the expression evaluates to `false`.

## Available variables {#available-variables}

`SpEL` expressions have access to these variables in the evaluation context:

| Variable | Type | Description |
|----------|------|-------------|
| `authentication` | `Authentication` | Spring Security authentication object |
| `principal` | `Object` | The authenticated principal (usually `UserDetails`) |
| `routeClass` | `Class<? extends Component>` | The route component class being accessed |
| `context` | `NavigationContext` | webforJ navigation context |
| `securityContext` | `RouteSecurityContext` | webforJ route security context |

Example using variables:

```java
@Route("/admin")
@RouteAccess("authentication.name == 'superadmin'")
public class SuperAdminView extends Composite<Div> { }
```

## When to use `SpEL` vs custom evaluators {#when-to-use-spel-vs-custom-evaluators}

**Use `@RouteAccess` `SpEL` when:**
- Authorization is based purely on roles or authorities
- Combining built-in security functions with boolean logic
- Route-specific rules that don't require reuse

**Use custom evaluators when:**
- Authorization depends on route parameters (ownership checks)
- Complex business logic requiring Spring service integration
- Reusable authorization patterns across multiple routes
- Custom annotations that document the authorization intent

See the [Custom Evaluators guide](/docs/security/custom-evaluators) for implementing advanced authorization scenarios.
