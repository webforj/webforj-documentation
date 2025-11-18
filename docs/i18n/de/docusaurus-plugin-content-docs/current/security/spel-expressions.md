---
sidebar_position: 5
title: SpEL Expressions
_i18n_hash: 1019aac355c5ef0efc8623660c3501e5
---
Spring Expression Language (`SpEL`) bietet eine deklarative Möglichkeit, Autorisierungsregeln direkt in Annotationen zu definieren. Die Annotation `@RouteAccess` evaluiert `SpEL`-Ausdrücke unter Verwendung der integrierten Autorisierungsfunktionen von Spring Security.

:::info Nur Spring Security
`SpEL`-Ausdrücke sind nur verfügbar, wenn die Spring-Integration verwendet wird.
:::

## Grundlegende Verwendung {#basic-usage}

Die Annotation `@RouteAccess` akzeptiert einen `SpEL`-Ausdruck, der zu einem boolean führt:

```java
@Route("/admin/dashboard")
@RouteAccess("hasRole('ADMIN')")
public class AdminDashboardView extends Composite<Div> {
  // Nur Benutzer mit der Rolle ROLE_ADMIN können zugreifen
}
```

Wenn der Ausdruck `true` ergibt, wird der Zugriff gewährt. Wenn `false`, wird der Benutzer zur Seite für den Zugriffsverweigerung umgeleitet.

## Eingebaute Sicherheitsfunktionen {#built-in-security-functions}

Spring Security bietet die folgenden Autorisierungsfunktionen über `SecurityExpressionRoot`:

| Funktion | Parameter | Beschreibung | Beispiel |
|----------|-----------|-------------|---------|
| `hasRole` | `String role` | Überprüft, ob der Benutzer die angegebene Rolle hat (automatisch mit `ROLE_` vorangestellt) | `hasRole('ADMIN')` entspricht `ROLE_ADMIN` |
| `hasAnyRole` | `String... roles` | Überprüft, ob der Benutzer eine der angegebenen Rollen hat | `hasAnyRole('ADMIN', 'MANAGER')` |
| `hasAuthority` | `String authority` | Überprüft, ob der Benutzer den genauen Autorisierungsstring hat | `hasAuthority('REPORTS:READ')` |
| `hasAnyAuthority` | `String... authorities` | Überprüft, ob der Benutzer eine der angegebenen Autorisierungen hat | `hasAnyAuthority('REPORTS:READ', 'REPORTS:WRITE')` |
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

// Autorisierungsüberprüfung
@Route("/reports")
@RouteAccess("hasAuthority('REPORTS:READ')")
public class ReportsView extends Composite<Div> { }

// Authentifizierung erforderlich
@Route("/profile")
@RouteAccess("isAuthenticated()")
public class ProfileView extends Composite<Div> { }
```

## Bedingungen kombinieren {#combining-conditions}

Verwenden Sie boolesche Operatoren (`and`, `or`, `!`), um komplexe Autorisierungsregeln zu erstellen:

```java
// Beide Bedingungen erforderlich
@Route("/moderator/reports")
@RouteAccess("hasRole('MODERATOR') and hasAuthority('REPORTS:VIEW')")
public class ModeratorReportsView extends Composite<Div> { }

// Jede Bedingung gewährt Zugang
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
  // Muss die Rolle USER UND die Autorisierung TEAM:ADMIN haben
}
```

Evaluierungsreihenfolge:
1. Der Evaluator von `@RolesAllowed` (Priorität 5) überprüft die Rolle `USER`
2. Wenn bestanden, wird der Evaluator von `@RouteAccess` (Priorität 6) den `SpEL`-Ausdruck auswerten
3. Wenn bestanden, werden benutzerdefinierte Evaluatoren (Priorität 10+) ausgeführt

## Benutzerdefinierte Fehlercodes {#custom-error-codes}

Liefern Sie aussagekräftige Fehlercodes für Zugriffsverweigerungen:

```java
@Route("/premium/features")
@RouteAccess(
  value = "hasAuthority('PREMIUM')",
  code = "PREMIUM_SUBSCRIPTION_REQUIRED"
)
public class PremiumFeaturesView extends Composite<Div> { }
```

Der Parameter `code` identifiziert den Grund für die Verweigerung, wenn der Ausdruck `false` ergibt.

## Verfügbare Variablen {#available-variables}

`SpEL`-Ausdrücke haben Zugriff auf diese Variablen im Auswertungskontext:

| Variable | Typ | Beschreibung |
|----------|------|-------------|
| `authentication` | `Authentication` | Spring Security Authentifizierungsobjekt |
| `principal` | `Object` | Der authentifizierte Principal (normalerweise `UserDetails`) |
| `routeClass` | `Class<? extends Component>` | Die Route-Komponentenklasse, die zugegriffen wird |
| `context` | `NavigationContext` | webforJ Navigationskontext |
| `securityContext` | `RouteSecurityContext` | webforJ Routen-Sicherheitskontext |

Beispiel für die Verwendung von Variablen:

```java
@Route("/admin")
@RouteAccess("authentication.name == 'superadmin'")
public class SuperAdminView extends Composite<Div> { }
```

## Wann `SpEL` VS benutzerdefinierte Evaluatoren verwenden {#when-to-use-spel-vs-custom-evaluators}

**Verwenden Sie `@RouteAccess` `SpEL`, wenn:**
- Die Autorisierung rein auf Rollen oder Berechtigungen basiert
- Eingebaute Sicherheitsfunktionen mit boolescher Logik kombiniert werden
- Routenspezifische Regeln benötigt werden, die keine Wiederverwendung erfordern

**Verwenden Sie benutzerdefinierte Evaluatoren, wenn:**
- Die Autorisierung von Routenparametern abhängt (Besitzüberprüfungen)
- Komplexe Geschäftslogik erforderlich ist, die eine Integration mit Spring-Diensten benötigt
- Wiederverwendbare Autorisierungsmuster über mehrere Routen hinweg erforderlich sind
- Benutzerdefinierte Annotationen, die die Autorisierungsabsicht dokumentieren

Siehe den [Leitfaden für benutzerdefinierte Evaluatoren](/docs/security/custom-evaluators) zur Implementierung fortgeschrittener Autorisierungsszenarien.
