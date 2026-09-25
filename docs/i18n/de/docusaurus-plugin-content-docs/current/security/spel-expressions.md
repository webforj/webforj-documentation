---
sidebar_position: 5
title: SpEL Expressions
description: >-
  Author route authorization rules with Spring Expression Language using
  @RouteAccess for role, authority, and custom checks.
_i18n_hash: 59601d0d83fe7eb4b05bf8eab47515c3
---
Spring Expression Language (`SpEL`) bietet eine deklarative Möglichkeit, Autorisierungsregeln direkt in Annotationen zu definieren. Die Annotation `@RouteAccess` bewertet `SpEL`-Ausdrücke mithilfe der integrierten Autorisierungsfunktionen von Spring Security.

<!-- INTRO_END -->

:::info Nur Spring Security
`SpEL`-Ausdrücke sind nur verfügbar, wenn die Spring-Integration verwendet wird.
:::

Die Annotation `@RouteAccess` akzeptiert einen `SpEL`-Ausdruck, der zu einem Boolean ausgewertet wird:

```java
@Route("/admin/dashboard")
@RouteAccess("hasRole('ADMIN')")
public class AdminDashboardView extends Composite<Div> {
  // Nur Benutzer mit der Berechtigung ROLE_ADMIN können darauf zugreifen
}
```

Wenn der Ausdruck zu `true` ausgewertet wird, wird der Zugriff gewährt. Bei `false` wird der Benutzer zur Seite für den Zugriff verweigert umgeleitet.

## Eingebaute Sicherheitsfunktionen {#built-in-security-functions}

Spring Security bietet die folgenden Autorisierungsfunktionen über `SecurityExpressionRoot` an:

| Funktion | Parameter | Beschreibung | Beispiel |
|----------|-----------|--------------|---------|
| `hasRole` | `String role` | Überprüft, ob der Benutzer die angegebene Rolle hat (automatisch mit `ROLE_` vorangestellt) | `hasRole('ADMIN')` entspricht `ROLE_ADMIN` |
| `hasAnyRole` | `String... roles` | Überprüft, ob der Benutzer eine der angegebenen Rollen hat | `hasAnyRole('ADMIN', 'MANAGER')` |
| `hasAuthority` | `String authority` | Überprüft, ob der Benutzer die genaue Berechtigungszeichenfolge hat | `hasAuthority('REPORTS:READ')` |
| `hasAnyAuthority` | `String... authorities` | Überprüft, ob der Benutzer eine der angegebenen Berechtigungen hat | `hasAnyAuthority('REPORTS:READ', 'REPORTS:WRITE')` |
| `isAuthenticated` | Keine | Gibt `true` zurück, wenn der Benutzer authentifiziert ist | `isAuthenticated()` |

### Beispiele {#examples}

```java
// Rollenüberprüfung
@Route("/admin")
@RouteAccess("hasRole('ADMIN')")
public class AdminView extends Composite<Div> { }

// Mehrere Rollen
@Route("/staff")
@RouteAccess("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
public class StaffView extends Composite<Div> { }

// Berechtigungsüberprüfung
@Route("/reports")
@RouteAccess("hasAuthority('REPORTS:READ')")
public class ReportsView extends Composite<Div> { }

// Authentifizierung erforderlich
@Route("/profile")
@RouteAccess("isAuthenticated()")
public class ProfileView extends Composite<Div> { }
```

## Kombination von Bedingungen {#combining-conditions}

Verwenden Sie boolesche Operatoren (`and`, `or`, `!`), um komplexe Autorisierungsregeln zu erstellen:

```java
// Beide Bedingungen erforderlich
@Route("/moderator/reports")
@RouteAccess("hasRole('MODERATOR') and hasAuthority('REPORTS:VIEW')")
public class ModeratorReportsView extends Composite<Div> { }

// Eine der Bedingungen gewährt Zugriff
@Route("/support")
@RouteAccess("hasRole('ADMIN') or hasRole('SUPPORT')")
public class SupportView extends Composite<Div> { }

// Negation
@Route("/trial/features")
@RouteAccess("isAuthenticated() and !hasAuthority('PREMIUM')")
public class TrialFeaturesView extends Composite<Div> { }

// Komplexer mehrzeiliger Ausdruck
@Route("/reports/advanced")
@RouteAccess("""
  hasRole('ADMIN') or
  (hasRole('ANALYST') and hasAuthority('REPORTS:ADVANCED'))
  """)
public class AdvancedReportsView extends Composite<Div> { }
```

## Kombination mit anderen Annotationen {#combining-with-other-annotations}

`@RouteAccess` funktioniert zusammen mit standardmäßigen Sicherheitsannotationen. Die Auswerter werden in der Prioritätsreihenfolge ausgeführt:

```java
@Route("/team/admin")
@RolesAllowed("USER")
@RouteAccess("hasAuthority('TEAM:ADMIN')")
public class TeamAdminView extends Composite<Div> {
  // Muss die Rolle USER UND die Berechtigung TEAM:ADMIN haben
}
```

Auswertungsreihenfolge:
1. `@RolesAllowed`-Auswerter (Priorität 5) überprüft die Rolle `USER`
2. Wenn erfolgreich, bewertet der `@RouteAccess`-Auswerter (Priorität 6) den `SpEL`-Ausdruck
3. Wenn erfolgreich, werden benutzerdefinierte Auswerter ausgeführt (Priorität 10+)

## Benutzerdefinierte Fehlermeldungen {#custom-error-codes}

Bieten Sie aussagekräftige Fehlermeldungen für Zugriffsverweigerungen an:

```java
@Route("/premium/features")
@RouteAccess(
  value = "hasAuthority('PREMIUM')",
  code = "PREMIUM_SUBSCRIPTION_REQUIRED"
)
public class PremiumFeaturesView extends Composite<Div> { }
```

Der Parameter `code` identifiziert den Grund für die Verweigerung, wenn der Ausdruck zu `false` ausgewertet wird.

## Verfügbare Variablen {#available-variables}

`SpEL`-Ausdrücke haben Zugriff auf diese Variablen im Auswertungskontext:

| Variable | Typ | Beschreibung |
|----------|------|--------------|
| `authentication` | `Authentication` | Spring Security-Autorisierungsobjekt |
| `principal` | `Object` | Der authentifizierte Principal (normalerweise `UserDetails`) |
| `routeClass` | `Class<? extends Component>` | Die Route-Komponentenklasse, die aufgerufen wird |
| `context` | `NavigationContext` | webforJ-Navigationskontext |
| `securityContext` | `RouteSecurityContext` | webforJ-Routen-Sicherheitskontext |

Beispiel zur Verwendung von Variablen:

```java
@Route("/admin")
@RouteAccess("authentication.name == 'superadmin'")
public class SuperAdminView extends Composite<Div> { }
```

## Wann `SpEL` VS benutzerdefinierte Auswerter verwenden {#when-to-use-spel-vs-custom-evaluators}

**Verwenden Sie `@RouteAccess` `SpEL`, wenn:**
- Die Autorisierung rein auf Rollen oder Berechtigungen basiert
- Eingebaute Sicherheitsfunktionen mit boolescher Logik kombiniert werden
- Routen-spezifische Regeln, die keine Wiederverwendung erfordern

**Verwenden Sie benutzerdefinierte Auswerter, wenn:**
- Die Autorisierung von Routenparametern abhängt (Besitzüberprüfungen)
- Komplexe Geschäftslogik erforderlich ist, die eine Integration von Spring-Diensten benötigt
- Wiederverwendbare Autorisierungsmuster über mehrere Routen hinweg
- Benutzerdefinierte Annotationen, die die Absicht der Autorisierung dokumentieren

Siehe den [Leitfaden zu benutzerdefinierten Auswertern](/docs/security/custom-evaluators) für die Implementierung fortgeschrittener Autorisierungsszenarien.
