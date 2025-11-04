---
sidebar_position: 6
title: Custom Evaluators
---

Custom evaluators extend webforJ's security system with specialized access control logic beyond basic authentication and role checks. Use them when you need to verify dynamic conditions that depend on request context, not just user permissions.

:::info Spring Security focused
This guide covers custom evaluators for Spring Security. If you're not using Spring Boot, see the [Evaluator Chain guide](/docs/security/architecture/evaluator-chain) to understand how evaluators work and [Complete Implementation](/docs/security/architecture/custom-implementation) for a working example.
:::

## What are custom evaluators {#what-are-custom-evaluators}

An evaluator determines whether a user can access a specific route based on custom logic. Evaluators are checked during navigation before any component is rendered, allowing you to intercept and control access dynamically.

webforJ includes built-in evaluators for standard Jakarta annotations:

- `AnonymousAccessEvaluator` - Handles `@AnonymousAccess`
- `PermitAllEvaluator` - Handles `@PermitAll`
- `RolesAllowedEvaluator` - Handles `@RolesAllowed`
- `DenyAllEvaluator` - Handles `@DenyAll`

Custom evaluators follow the same pattern, allowing you to create your own annotations and access control logic.

:::tip[Learn more about built-in annotations]
For details on `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, and `@DenyAll`, see the [Security Annotations guide](/docs/security/annotations).
:::

## Use case: Ownership verification {#use-case-ownership-verification}

A common requirement is allowing users to access only their own resources. For example, users should only be able to edit their own profile, not someone else's profile.

**The problem**: `@RolesAllowed("USER")` grants access to all authenticated users, but doesn't verify if the user is accessing their own resource. You need to compare the logged-in user ID with the resource ID in the URL.

**Example scenario:**
- User ID `123` is logged in
- They navigate to `/users/456/edit`
- Should they access this page? **NO** - they can only edit `/users/123/edit`

You can't solve this with roles because it depends on the route parameter `:userId`, which changes for every request.

### Creating a custom annotation {#creating-a-custom-annotation}

Define an annotation to mark routes that require ownership verification:

```java title="RequireOwnership.java"
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireOwnership {
  /**
   * The route parameter name that contains the user ID.
   */
  String value() default "userId";
}
```

Use it on routes that require ownership checks:

```java title="EditProfileView.java"
@Route(value = "/users/:userId/edit", outlet = MainLayout.class)
@RequireOwnership("userId")
public class EditProfileView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public EditProfileView() {
    self.setText("Edit Profile Page");
  }
}
```

### Implementing the evaluator {#implementing-the-evaluator}

Create a Spring-managed evaluator that compares the logged-in user ID with the route parameter:

```java title="OwnershipEvaluator.java"
@RegisteredEvaluator(priority = 10)
public class OwnershipEvaluator implements RouteSecurityEvaluator {

  @Override
  public boolean supports(Class<?> routeClass) {
    return routeClass.isAnnotationPresent(RequireOwnership.class);
  }

  @Override
  public RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context,
      RouteSecurityContext securityContext, SecurityEvaluatorChain chain) {

    // First check authentication
    if (!securityContext.isAuthenticated()) {
      return RouteAccessDecision.denyAuthentication();
    }

    // Get the annotation
    RequireOwnership annotation = routeClass.getAnnotation(RequireOwnership.class);
    String paramName = annotation.value();

    // Get logged-in user ID from security context
    String currentUserId = securityContext.getPrincipal()
        .filter(p -> p instanceof UserDetails)
        .map(p -> ((UserDetails) p).getUsername())
        .orElse(null);

    // Get :userId from route parameters
    String requestedUserId = context.getRouteParameters()
        .get(paramName)
        .orElse(null);

    // Check if they match
    if (currentUserId != null && currentUserId.equals(requestedUserId)) {
      // Ownership verified - continue chain to allow other evaluators
      return chain.evaluate(routeClass, context, securityContext);
    }

    return RouteAccessDecision.deny("You can only access your own resources");
  }
}
```

Spring automatically discovers and registers evaluators annotated with `@RegisteredEvaluator`.

### How it works {#how-it-works}

The evaluator implementation has two key methods:

#### `supports(Class<?> routeClass)` {#supports-method}

- Returns `true` if this evaluator should handle the route
- Only evaluators that return `true` will be invoked for the route
- Filters routes by checking for the `@RequireOwnership` annotation

#### `evaluate(...)` {#evaluate-method}

- Checks if the user is authenticated first
- Gets the logged-in user ID from `securityContext.getPrincipal()`
- Gets the route parameter value from `context.getRouteParameters().get(paramName)`
- Compares the two IDs
- If they match, delegates to `chain.evaluate()` to allow other evaluators to run
- If they don't match, returns `deny()` with a reason

### Flow example {#flow-example}

**When ownership check fails:**

1. User `123` logs in and navigates to `/users/456/edit`
2. `OwnershipEvaluator.supports()` returns `true` (route has `@RequireOwnership`)
3. `OwnershipEvaluator.evaluate()` runs:
   - `currentUserId = "123"` (from security context)
   - `requestedUserId = "456"` (from route parameter `:userId`)
   - `"123".equals("456")` → `false`
   - Returns `RouteAccessDecision.deny("You can only access your own resources")`
4. User is redirected to access denied page

**When ownership check passes:**

1. User `123` logs in and navigates to `/users/123/edit`
2. `OwnershipEvaluator.evaluate()` runs:
   - `currentUserId = "123"`, `requestedUserId = "123"`
   - IDs match → calls `chain.evaluate()` to continue
3. If no other evaluators deny access, the user is granted access

## Understanding the evaluator chain {#understanding-the-evaluator-chain}

The security system uses a **chain of responsibility pattern** where evaluators are processed in priority order. Evaluators can either make terminal decisions or delegate to the chain for combining multiple checks.

### How the chain works {#how-chain-works}

1. Evaluators are sorted by priority (lower numbers first)
2. For each evaluator, `supports(routeClass)` is called to check if it applies
3. If `supports()` returns `true`, the evaluator's `evaluate()` method is called
4. The evaluator can either:
   - **Return a terminal decision** (`grant()` or `deny()`) - **stops the chain**
   - **Delegate to the chain** by calling `chain.evaluate()` - **allows other evaluators to run**
5. If the chain completes without a decision and secure-by-default is enabled, unauthenticated users are denied

### Terminal decisions {#terminal-decisions}

Stop the chain immediately:

#### `RouteAccessDecision.grant()`

- Grants access and stops further evaluation
- Used by `@AnonymousAccess` and `@PermitAll` - these are complete authorizations that don't combine with other checks

#### `RouteAccessDecision.deny(reason)`

- Denies access and stops further evaluation
- Used by `@DenyAll` and when custom checks fail
- Example: `RouteAccessDecision.deny("You can only access your own resources")`

#### `RouteAccessDecision.denyAuthentication()`

- Redirects to login page
- Used when authentication is required but missing

### Chain delegation {#chain-delegation}

Allows combining checks:

#### `chain.evaluate(routeClass, context, securityContext)`

- Passes control to the next evaluator in the chain
- Enables combining multiple authorization checks
- Used by `@RolesAllowed` and `@RouteAccess` after their checks pass
- Custom evaluators should use this pattern when checks pass to allow composition

## Evaluator priority {#evaluator-priority}

Evaluators are checked in priority order (lower numbers first). Framework evaluators use priority 1-9, custom evaluators should use 10 or higher.

Built-in evaluators are registered in this order:

```java
// Priority 1: @DenyAll - blocks everything
// Priority 2: @AnonymousAccess - allows unauthenticated access
// Priority 3: AuthenticationRequiredEvaluator - ensures auth for @PermitAll/@RolesAllowed
// Priority 4: @PermitAll - requires authentication only
// Priority 5: @RolesAllowed - requires specific roles
// Priority 6: @RouteAccess - SpEL expressions (Spring Security only)
// Priority 10+: Custom evaluators (like @RequireOwnership)
```

### How priority affects evaluation {#priority-affects-evaluation}

- Lower priority evaluators run first and can "short-circuit" the chain
- `@DenyAll` (priority 1) runs first - if present, access is always denied
- `@AnonymousAccess` (priority 2) runs next - if present, access is always granted (even without auth)
- `AuthenticationRequiredEvaluator` (priority 3) checks if the route needs auth and user is authenticated
- If no evaluator handles the route, secure-by-default logic applies

### Setting priority {#setting-priority}

Set priority with the `@RegisteredEvaluator` annotation:

```java
@RegisteredEvaluator(priority = 10)  // Runs after built-in evaluators
public class OwnershipEvaluator implements RouteSecurityEvaluator {
  // ...
}
```

:::tip Priority range
Custom evaluators should use priority 10 or higher. Priorities 1-9 are reserved for framework evaluators. If you use a priority in the reserved range, you'll get a warning in the logs.
:::

## Combining evaluators {#combining-evaluators}

Evaluators that delegate to the chain can be combined to create complex authorization logic. Routes can have multiple security annotations:

### Combining role checks with custom logic {#combining-roles-custom}

```java
@Route("/users/:userId/settings")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class UserSettingsView extends Composite<Div> {
  // Must have USER role AND be accessing their own settings
}
```

**How it works:**
1. `RolesAllowedEvaluator` (priority 5) checks if the user has the "USER" role
2. If yes, calls `chain.evaluate()` to continue
3. `OwnershipEvaluator` (priority 10) checks if `userId` matches the logged-in user
4. If yes, calls `chain.evaluate()` to continue
5. Chain ends → access granted

### Combining SpEL expressions with custom logic {#combining-spel-custom}

```java
@Route("/admin/users/:userId/edit")
@RouteAccess("hasRole('ADMIN')")
@RequireOwnership("userId")
public class AdminEditUserView extends Composite<Div> {
  // Must be admin AND accessing their own account
}
```

### What can't be combined {#cant-combine}

`@AnonymousAccess` and `@PermitAll` make **terminal decisions** - they immediately grant access without calling the chain. You can't combine them with custom evaluators:

```java
// @PermitAll grants access immediately, @RequireOwnership never runs
@Route("/users/:userId/profile")
@PermitAll
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // ...
}
```

For resources that all authenticated users can access, use `@RolesAllowed` with a common role instead:

```java
// @RolesAllowed delegates to chain
@Route("/users/:userId/profile")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // Must be an authenticated user AND accessing their own profile
}
```