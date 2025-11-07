---
sidebar_position: 5
title: SpEL Expressions
_i18n_hash: 1019aac355c5ef0efc8623660c3501e5
---
Spring Expression Language (`SpEL`) biedt een declaratieve manier om autorisatieregels direct in annotaties te definiëren. De `@RouteAccess` annotatie evalueert `SpEL`-uitdrukkingen met behulp van de ingebouwde autorisatiefuncties van Spring Security.

:::info Alleen Spring Security
`SpEL`-uitdrukkingen zijn alleen beschikbaar bij gebruik van de Spring-integratie.
:::

## Basisgebruik {#basic-usage}

De `@RouteAccess` annotatie accepteert een `SpEL`-uitdrukking die evalueert naar een boolean:

```java
@Route("/admin/dashboard")
@RouteAccess("hasRole('ADMIN')")
public class AdminDashboardView extends Composite<Div> {
  // Alleen gebruikers met de rol ROLE_ADMIN kunnen toegang krijgen
}
```

Als de uitdrukking evalueert naar `true`, wordt toegang verleend. Als `false`, wordt de gebruiker doorgestuurd naar de toegang geweigerd pagina.

## Ingebouwde beveiligingsfuncties {#built-in-security-functions}

Spring Security biedt de volgende autorisatiefuncties via `SecurityExpressionRoot`:

| Functie | Parameters | Beschrijving | Voorbeeld |
|----------|-----------|-------------|---------|
| `hasRole` | `String rol` | Controleert of de gebruiker de opgegeven rol heeft (voegt automatisch `ROLE_` vooraan toe) | `hasRole('ADMIN')` komt overeen met `ROLE_ADMIN` |
| `hasAnyRole` | `String... rollen` | Controleert of de gebruiker een van de opgegeven rollen heeft | `hasAnyRole('ADMIN', 'MANAGER')` |
| `hasAuthority` | `String autoriteit` | Controleert of de gebruiker de exacte autoriteitstring heeft | `hasAuthority('REPORTS:READ')` |
| `hasAnyAuthority` | `String... autoriteiten` | Controleert of de gebruiker een van de opgegeven autoriteiten heeft | `hasAnyAuthority('REPORTS:READ', 'REPORTS:WRITE')` |
| `isAuthenticated` | Geen | Retourneert `true` als de gebruiker geauthenticeerd is | `isAuthenticated()` |

### Voorbeelden {#examples}

```java
// Rolcontrole
@Route("/admin")
@RouteAccess("hasRole('ADMIN')")
public class AdminView extends Composite<Div> { }

// Meerdere rollen
@Route("/staff")
@RouteAccess("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
public class StaffView extends Composite<Div> { }

// Autoriteitscontrole
@Route("/reports")
@RouteAccess("hasAuthority('REPORTS:READ')")
public class ReportsView extends Composite<Div> { }

// Vereist authenticatie
@Route("/profile")
@RouteAccess("isAuthenticated()")
public class ProfileView extends Composite<Div> { }
```

## Combineren van voorwaarden {#combining-conditions}

Gebruik boolean-operatoren (`and`, `or`, `!`) om complexe autorisatieregels te creëren:

```java
// Beide voorwaarden vereist
@Route("/moderator/reports")
@RouteAccess("hasRole('MODERATOR') and hasAuthority('REPORTS:VIEW')")
public class ModeratorReportsView extends Composite<Div> { }

// Toegang op basis van één van de voorwaarden
@Route("/support")
@RouteAccess("hasRole('ADMIN') or hasRole('SUPPORT')")
public class SupportView extends Composite<Div> { }

// Negatie
@Route("/trial/features")
@RouteAccess("isAuthenticated() and !hasAuthority('PREMIUM')")
public class TrialFeaturesView extends Composite<Div> { }

// Complexe meerlinie-uitdrukking
@Route("/reports/advanced")
@RouteAccess("""
  hasRole('ADMIN') or
  (hasRole('ANALYST') and hasAuthority('REPORTS:ADVANCED'))
  """)
public class AdvancedReportsView extends Composite<Div> { }
```

## Combineren met andere annotaties {#combining-with-other-annotations}

`@RouteAccess` werkt samen met standaard beveiligingsannotaties. Evaluatoren worden in volgorde van prioriteit uitgevoerd:

```java
@Route("/team/admin")
@RolesAllowed("USER")
@RouteAccess("hasAuthority('TEAM:ADMIN')")
public class TeamAdminView extends Composite<Div> {
  // Moet zowel rol USER hebben EN autoriteit TEAM:ADMIN
}
```

Evaluatievolgorde:
1. `@RolesAllowed` evaluator (prioriteit 5) verifieert rol `USER`
2. Als dit is geslaagd, evalueert `@RouteAccess` evaluator (prioriteit 6) de `SpEL`-uitdrukking
3. Als dit is geslaagd, worden aangepaste evaluatoren uitgevoerd (prioriteit 10+)

## Aangepaste foutcodes {#custom-error-codes}

Bied zinvolle foutcodes voor toegang weigeringen:

```java
@Route("/premium/features")
@RouteAccess(
  value = "hasAuthority('PREMIUM')",
  code = "PREMIUM_SUBSCRIPTION_REQUIRED"
)
public class PremiumFeaturesView extends Composite<Div> { }
```

De parameter `code` identificeert de reden voor weigering wanneer de uitdrukking evalueert naar `false`.

## Beschikbare variabelen {#available-variables}

`SpEL`-uitdrukkingen hebben toegang tot deze variabelen in de evaluatiecontext:

| Variabele | Type | Beschrijving |
|----------|------|-------------|
| `authentication` | `Authentication` | Spring Security authenticatieobject |
| `principal` | `Object` | De geauthenticeerde principal (gewoonlijk `UserDetails`) |
| `routeClass` | `Class<? extends Component>` | De routecomponentklasse die wordt benaderd |
| `context` | `NavigationContext` | webforJ navigatiecontext |
| `securityContext` | `RouteSecurityContext` | webforJ route beveiligingscontext |

Voorbeeld van het gebruik van variabelen:

```java
@Route("/admin")
@RouteAccess("authentication.name == 'superadmin'")
public class SuperAdminView extends Composite<Div> { }
```

## Wanneer `SpEL` VS aangepaste evaluatoren te gebruiken {#when-to-use-spel-vs-custom-evaluators}

**Gebruik `@RouteAccess` `SpEL` wanneer:**
- Autorisatie uitsluitend is gebaseerd op rollen of autoriteiten
- Combineren van ingebouwde beveiligingsfuncties met boolean logica
- Specifieke regels voor routes die geen hergebruik vereisen

**Gebruik aangepaste evaluatoren wanneer:**
- Autorisatie afhankelijk is van routeparameters (eigenaarschapscontroles)
- Complexe zakelijke logica die integratie met de Spring-service vereist
- Herbruikbare autorisatiepatronen over meerdere routes
- Aangepaste annotaties die de autorisatie-intentie documenteren

Zie de [Custom Evaluators gids](/docs/security/custom-evaluators) voor het implementeren van geavanceerde autorisatiescenario's.
