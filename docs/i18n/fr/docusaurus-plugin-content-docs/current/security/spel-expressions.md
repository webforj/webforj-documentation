---
sidebar_position: 5
title: SpEL Expressions
_i18n_hash: 1019aac355c5ef0efc8623660c3501e5
---
Spring Expression Language (`SpEL`) fournit une manière déclarative de définir des règles d'autorisation directement dans les annotations. L'annotation `@RouteAccess` évalue les expressions `SpEL` en utilisant les fonctions d'autorisation intégrées de Spring Security.

:::info Spring Security uniquement
Les expressions `SpEL` ne sont disponibles que lors de l'utilisation de l'intégration Spring.
:::

## Utilisation de base {#basic-usage}

L'annotation `@RouteAccess` accepte une expression `SpEL` qui évalue à un booléen :

```java
@Route("/admin/dashboard")
@RouteAccess("hasRole('ADMIN')")
public class AdminDashboardView extends Composite<Div> {
  // Seuls les utilisateurs avec l'autorité ROLE_ADMIN peuvent accéder
}
```

Si l'expression évalue à `true`, l'accès est accordé. Si `false`, l'utilisateur est redirigé vers la page d'accès refusé.

## Fonctions de sécurité intégrées {#built-in-security-functions}

Spring Security fournit les fonctions d'autorisation suivantes via `SecurityExpressionRoot` :

| Fonction | Paramètres | Description | Exemple |
|----------|-----------|-------------|---------|
| `hasRole` | `String role` | Vérifie si l'utilisateur a le rôle spécifié (préfère automatiquement par `ROLE_`) | `hasRole('ADMIN')` correspond à `ROLE_ADMIN` |
| `hasAnyRole` | `String... roles` | Vérifie si l'utilisateur a l'un des rôles spécifiés | `hasAnyRole('ADMIN', 'MANAGER')` |
| `hasAuthority` | `String authority` | Vérifie si l'utilisateur a la chaîne d'autorité exacte | `hasAuthority('REPORTS:READ')` |
| `hasAnyAuthority` | `String... authorities` | Vérifie si l'utilisateur a l'une des autorités spécifiées | `hasAnyAuthority('REPORTS:READ', 'REPORTS:WRITE')` |
| `isAuthenticated` | Aucun | Renvoie `true` si l'utilisateur est authentifié | `isAuthenticated()` |

### Exemples {#examples}

```java
// Vérification de rôle
@Route("/admin")
@RouteAccess("hasRole('ADMIN')")
public class AdminView extends Composite<Div> { }

// Rôles multiples
@Route("/staff")
@RouteAccess("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
public class StaffView extends Composite<Div> { }

// Vérification d'autorité
@Route("/reports")
@RouteAccess("hasAuthority('REPORTS:READ')")
public class ReportsView extends Composite<Div> { }

// Exiger une authentification
@Route("/profile")
@RouteAccess("isAuthenticated()")
public class ProfileView extends Composite<Div> { }
```

## Combiner des conditions {#combining-conditions}

Utilisez des opérateurs booléens (`and`, `or`, `!`) pour créer des règles d'autorisation complexes :

```java
// Les deux conditions requises
@Route("/moderator/reports")
@RouteAccess("hasRole('MODERATOR') and hasAuthority('REPORTS:VIEW')")
public class ModeratorReportsView extends Composite<Div> { }

// L'une ou l'autre condition accorde l'accès
@Route("/support")
@RouteAccess("hasRole('ADMIN') or hasRole('SUPPORT')")
public class SupportView extends Composite<Div> { }

// Négation
@Route("/trial/features")
@RouteAccess("isAuthenticated() and !hasAuthority('PREMIUM')")
public class TrialFeaturesView extends Composite<Div> { }

// Expression multi-ligne complexe
@Route("/reports/advanced")
@RouteAccess("""
  hasRole('ADMIN') or
  (hasRole('ANALYST') and hasAuthority('REPORTS:ADVANCED'))
  """)
public class AdvancedReportsView extends Composite<Div> { }
```

## Combiner avec d'autres annotations {#combining-with-other-annotations}

`@RouteAccess` fonctionne avec les annotations de sécurité standard. Les évaluateurs s'exécutent dans un ordre de priorité :

```java
@Route("/team/admin")
@RolesAllowed("USER")
@RouteAccess("hasAuthority('TEAM:ADMIN')")
public class TeamAdminView extends Composite<Div> {
  // Doit avoir le rôle USER ET l'autorité TEAM:ADMIN
}
```

Ordre d'évaluation :
1. L'évaluateur `@RolesAllowed` (priorité 5) vérifie le rôle `USER`
2. Si réussi, l'évaluateur `@RouteAccess` (priorité 6) évalue l'expression `SpEL`
3. Si réussi, les évaluateurs personnalisés s'exécutent (priorité 10+)

## Codes d'erreur personnalisés {#custom-error-codes}

Fournissez des codes d'erreur significatifs pour les refus d'accès :

```java
@Route("/premium/features")
@RouteAccess(
  value = "hasAuthority('PREMIUM')",
  code = "PREMIUM_SUBSCRIPTION_REQUIRED"
)
public class PremiumFeaturesView extends Composite<Div> { }
```

Le paramètre `code` identifie la raison du refus lorsque l'expression évalue à `false`.

## Variables disponibles {#available-variables}

Les expressions `SpEL` ont accès à ces variables dans le contexte d'évaluation :

| Variable | Type | Description |
|----------|------|-------------|
| `authentication` | `Authentication` | Objet d'authentification Spring Security |
| `principal` | `Object` | Le principal authentifié (généralement `UserDetails`) |
| `routeClass` | `Class<? extends Component>` | La classe de composant de route en cours d'accès |
| `context` | `NavigationContext` | contexte de navigation webforJ |
| `securityContext` | `RouteSecurityContext` | contexte de sécurité de route webforJ |

Exemple utilisant des variables :

```java
@Route("/admin")
@RouteAccess("authentication.name == 'superadmin'")
public class SuperAdminView extends Composite<Div> { }
```

## Quand utiliser `SpEL` contre des évaluateurs personnalisés {#when-to-use-spel-vs-custom-evaluators}

**Utilisez `@RouteAccess` `SpEL` lorsque :**
- L'autorisation est basée uniquement sur des rôles ou des autorités
- Combinaison de fonctions de sécurité intégrées avec une logique booléenne
- Règles spécifiques à la route qui ne nécessitent pas de réutilisation

**Utilisez des évaluateurs personnalisés lorsque :**
- L'autorisation dépend des paramètres de la route (vérifications de propriété)
- Logique métier complexe nécessitant une intégration de service Spring
- Modèles d'autorisation réutilisables à travers plusieurs routes
- Annotations personnalisées qui documentent l'intention d'autorisation

Consultez le [guide des évaluateurs personnalisés](/docs/security/custom-evaluators) pour la mise en œuvre de scénarios d'autorisation avancés.
