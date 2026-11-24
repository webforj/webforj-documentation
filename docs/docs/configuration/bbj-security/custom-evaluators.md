---
title: Custom Evaluators
sidebar_position: 7
description: Add custom route rules to BBj apps with BBjRouteSecurityEvaluator, combine rules, and control the evaluator order.
---

Custom evaluators add rules that the annotations can't express, such as "all of" several permissions or a check on a route parameter.

## Add custom evaluators {#add-custom-evaluators}

An app adds its own rule for a route by implementing `BBjRouteSecurityEvaluator`. The interface extends the route security evaluator of webforJ. `supports` selects the routes the evaluator handles, and `evaluate` returns the decision. The evaluator calls `chain.evaluate(...)` to leave the decision to the next evaluator.

List the class in the provider file `META-INF/services/com.webforj.bbj.security.BBjRouteSecurityEvaluator`, one fully qualified class name per line. The file belongs in `src/main/resources`.

```text title="META-INF/services/com.webforj.bbj.security.BBjRouteSecurityEvaluator"
com.example.security.MarkedEvaluator
```

The example denies every route that carries the annotation `@Marked`.

```java title="Marked.java"
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Marked {
}
```

```java title="MarkedEvaluator.java"
public class MarkedEvaluator implements BBjRouteSecurityEvaluator {

  @Override
  public boolean supports(Class<?> routeClass) {
    return routeClass.isAnnotationPresent(Marked.class);
  }

  @Override
  public RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context,
      RouteSecurityContext securityContext, SecurityEvaluatorChain chain) {
    return RouteAccessDecision.deny("marked");
  }
}
```

```java title="MarkedView.java"
@Route("/marked")
@Marked
public class MarkedView extends Composite<Div> {

  public MarkedView() {
    getBoundComponent().add(new Paragraph("Marked view reached"));
  }
}
```

A visitor who opens `/marked` is redirected to the deny path with the reason, as in `/denied?reason=marked`.

## Combine rules {#combine-rules}

A route that carries `@RolesAllowed` and `@BBjPermission` needs both. Each of the two evaluators calls the chain when its check succeeds, so the other one still runs. The user needs one of the listed roles and one of the listed permissions.

```java title="ReportsView.java"
@Route("/reports")
@RolesAllowed("Sales EU")
@BBjPermission(BBjPermissions.ADMIN_ACCESS)
public class ReportsView extends Composite<Div> {

  public ReportsView() {
    getBoundComponent().add(new Paragraph("Members of Sales EU with admin-access see this page."));
  }
}
```

The annotations can't express an "or" between a role and a permission, "all of" several permissions, or a check on a route parameter such as ownership. An app writes such a rule as a custom evaluator with its own annotation, as in [Add custom evaluators](#add-custom-evaluators). BBj apps have no expression annotation like the `@RouteAccess` annotation of the Spring integration.

Every evaluator of the examples needs an entry in the provider file. See [Add custom evaluators](#add-custom-evaluators). Each one calls `chain.evaluate(...)` when its rule passes, so the other rules of the route still apply.

### Require all permissions {#require-all-permissions}

`@BBjPermission` passes with any one of its permissions. The annotation `@AllPermissions` requires every listed permission.

```java title="AllPermissions.java"
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AllPermissions {
  BBjPermissions[] value();
}
```

```java title="AllPermissionsEvaluator.java"
public class AllPermissionsEvaluator implements BBjRouteSecurityEvaluator {

  @Override
  public boolean supports(Class<?> routeClass) {
    return routeClass.isAnnotationPresent(AllPermissions.class);
  }

  @Override
  public RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context,
      RouteSecurityContext securityContext, SecurityEvaluatorChain chain) {
    if (!securityContext.isAuthenticated()) {
      return RouteAccessDecision.denyAuthentication();
    }

    BBjPermissions[] required = routeClass.getAnnotation(AllPermissions.class).value();
    boolean allowed = Arrays.stream(required).allMatch(BBjSecurity::hasAuthority);

    return allowed ? chain.evaluate(routeClass, context, securityContext)
        : RouteAccessDecision.deny("insufficient_permissions");
  }
}
```

```java title="UserAdminView.java"
@Route("/user-admin")
@AllPermissions({BBjPermissions.ADD_USER, BBjPermissions.SET_PASSWORDS})
public class UserAdminView extends Composite<Div> {

  public UserAdminView() {
    getBoundComponent().add(new Paragraph("Users with add-user and set-passwords see this page."));
  }
}
```

A user who lacks one of the permissions is redirected to the deny path as in `/denied?reason=insufficient_permissions`.

### Check the owner of a route parameter {#check-the-owner-of-a-route-parameter}

`NavigationContext.getRouteParameters()` returns the route parameters as a `ParametersBag`. Its `get(String)` method returns an `Optional<String>` for the name of a parameter. The annotation `@OwnUser` names the parameter that must hold the user name of the signed in user.

```java title="OwnUser.java"
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OwnUser {
  String value();
}
```

```java title="OwnUserEvaluator.java"
public class OwnUserEvaluator implements BBjRouteSecurityEvaluator {

  @Override
  public boolean supports(Class<?> routeClass) {
    return routeClass.isAnnotationPresent(OwnUser.class);
  }

  @Override
  public RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context,
      RouteSecurityContext securityContext, SecurityEvaluatorChain chain) {
    Optional<String> current = BBjSecurity.currentUser().map(BBjPrincipal::getUserName);
    if (current.isEmpty()) {
      return RouteAccessDecision.denyAuthentication();
    }

    String parameter = routeClass.getAnnotation(OwnUser.class).value();
    Optional<String> requested = context.getRouteParameters().get(parameter);

    return current.equals(requested) ? chain.evaluate(routeClass, context, securityContext)
        : RouteAccessDecision.deny("not_owner");
  }
}
```

```java title="UserSettingsView.java"
@Route("/users/:user/settings")
@RolesAllowed("Staff")
@OwnUser("user")
public class UserSettingsView extends Composite<Div> {

  public UserSettingsView() {
    getBoundComponent().add(new Paragraph("Staff members see their own settings."));
  }
}
```

A visitor who opens the settings of another user is redirected to the deny path as in `/denied?reason=not_owner`. The user name comparison is case sensitive.

## Evaluator order {#evaluator-order}

The app registers the evaluators of the core annotations first, each with a priority:

| Priority | Evaluator | Handles |
|----------|-----------|---------|
| 1 | `DenyAllEvaluator` | `@DenyAll` |
| 2 | `AnonymousAccessEvaluator` | `@AnonymousAccess` |
| 3 | `AuthenticationRequiredEvaluator` | Routes that require a signed-in user |
| 4 | `PermitAllEvaluator` | `@PermitAll` |
| 5 | `RolesAllowedEvaluator` | `@RolesAllowed` |
| 6 | `BBjPermissionEvaluator` | `@BBjPermission` |

Custom evaluators start at priority 10. An evaluator of the provider file gets priority 10 unless it overrides `getPriority()`. A lower number runs first. Evaluators with the same priority run in the order they were registered.

The first evaluator that supports the route decides. It either returns the decision or calls the chain to pass the route on to the next evaluator that supports it. `@DenyAll`, `@AnonymousAccess` and `@PermitAll` return their decision and never call the chain, so a custom evaluator never runs for a route that carries one of them. Don't combine a custom annotation with these three, or give the evaluator a priority below the evaluator it must run before.

A priority below 10 lies in the range of the core evaluators. The app logs a warning for it at the start, and registers the evaluator with that priority.
