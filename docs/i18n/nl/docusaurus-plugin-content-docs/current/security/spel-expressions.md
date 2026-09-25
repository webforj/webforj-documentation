---
sidebar_position: 5
title: SpEL Expressions
description: >-
  Author route authorization rules with Spring Expression Language using
  @RouteAccess for role, authority, and custom checks.
_i18n_hash: 59601d0d83fe7eb4b05bf8eab47515c3
---
Spring Expression Language (`SpEL`) biedt een declaratieve manier om autorisatieregels rechtstreeks in annotaties te definiëren. De annotatie `@RouteAccess` evalueert `SpEL`-expressies met behulp van de ingebouwde autorisatiefuncties van Spring Security.

<!-- INTRO_END -->

:::info Alleen Spring Security
`SpEL`-expressies zijn alleen beschikbaar bij gebruik van de Spring-integratie.
:::

De annotatie `@RouteAccess` accepteert een `SpEL`-expressie die evalueert naar een boolean:

```java
@Route("/admin/dashboard")
@RouteAccess("hasRole('ADMIN')")
public class AdminDashboardView extends Composite<Div> {
  // Alleen gebruikers met de autoriteit ROLE_ADMIN hebben toegang
}
```

Als de expressie evalueert naar `true`, wordt de toegang verleend. Bij `false` wordt de gebruiker doorgestuurd naar de pagina voor toegang geweigerd.

## Ingebouwde beveiligingsfuncties {#built-in-security-functions}

Spring Security biedt de volgende autorisatiefuncties via `SecurityExpressionRoot`:

| Functie | Parameters | Omschrijving | Voorbeeld |
|---------|------------|--------------|-----------|
| `hasRole` | `String rol` | Controleert of de gebruiker de opgegeven rol heeft (voegt automatisch `ROLE_` voorop) | `hasRole('ADMIN')` komt overeen met `ROLE_ADMIN` |
| `hasAnyRole` | `String... rollen` | Controleert of de gebruiker een van de opgegeven rollen heeft | `hasAnyRole('ADMIN', 'MANAGER')` |
| `hasAuthority` | `String autoriteit` | Controleert of de gebruiker de exacte autoriteitstring heeft | `hasAuthority('REPORTS:READ')` |
| `hasAnyAuthority` | `String... autoriteiten` | Controleert of de gebruiker een van de opgegeven autoriteiten heeft | `hasAnyAuthority('REPORTS:READ', 'REPORTS:WRITE')` |
| `isAuthenticated` | Geen | Geeft `true` terug als de gebruiker geauthenticeerd is | `isAuthenticated()` |

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

## Voorwaarden combineren {#combining-conditions}

Gebruik boolean-operatoren (`and`, `or`, `!`) om complexe autorisatieregels te maken:

```java
// Beide voorwaarden vereist
@Route("/moderator/reports")
@RouteAccess("hasRole('MODERATOR') and hasAuthority('REPORTS:VIEW')")
public class ModeratorReportsView extends Composite<Div> { }

// Een van de voorwaarden verleent toegang
@Route("/support")
@RouteAccess("hasRole('ADMIN') or hasRole('SUPPORT')")
public class SupportView extends Composite<Div> { }

// Negatie
@Route("/trial/features")
@RouteAccess("isAuthenticated() and !hasAuthority('PREMIUM')")
public class TrialFeaturesView extends Composite<Div> { }

// Complexe multi-regel expressie
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
  // Moet de rol USER EN de autoriteit TEAM:ADMIN hebben
}
```

Evaluatievolgorde:
1. `@RolesAllowed` evaluatoren (prioriteit 5) controleren de rol `USER`
2. Als deze slaagt, evalueert `@RouteAccess` evaluatoren (prioriteit 6) de `SpEL`-expressie
3. Als deze slaagt, worden de aangepaste evaluatoren uitgevoerd (prioriteit 10+)

## Aangepaste foutcodes {#custom-error-codes}

Geef betekenisvolle foutcodes voor toegang weigeringen:

```java
@Route("/premium/features")
@RouteAccess(
  value = "hasAuthority('PREMIUM')",
  code = "PREMIUM_SUBSCRIPTION_REQUIRED"
)
public class PremiumFeaturesView extends Composite<Div> { }
```

De parameter `code` identificeert de reden voor de weigering wanneer de expressie evalueert naar `false`.

## Beschikbare variabelen {#available-variables}

`SpEL`-expressies hebben toegang tot deze variabelen in de evaluatiecontext:

| Variabele | Type | Omschrijving |
|-----------|------|--------------|
| `authentication` | `Authentication` | Spring Security authenticatieobject |
| `principal` | `Object` | De geauthenticeerde principal (meestal `UserDetails`) |
| `routeClass` | `Class<? extends Component>` | De routecomponentklasse die wordt geopend |
| `context` | `NavigationContext` | webforJ navigatiecontext |
| `securityContext` | `RouteSecurityContext` | webforJ routebeveiligingscontext |

Voorbeeld met variabelen:

```java
@Route("/admin")
@RouteAccess("authentication.name == 'superadmin'")
public class SuperAdminView extends Composite<Div> { }
```

## Wanneer `SpEL` VS aangepaste evaluatoren te gebruiken {#when-to-use-spel-vs-custom-evaluators}

**Gebruik `@RouteAccess` `SpEL` wanneer:**
- Autorisatie puur gebaseerd is op rollen of autoriteiten
- Gecombineerd met ingebouwde beveiligingsfuncties en boolean-logica
- Routespecifieke regels die geen hergebruik vereisen

**Gebruik aangepaste evaluatoren wanneer:**
- Autorisatie afhankelijk is van routeparameters (eigendomcontroles)
- Complexe bedrijfslogica die integratie met Spring-services vereist
- Herbruikbare autorisatiepatronen over meerdere routes
- Aangepaste annotaties die de autorisatie-intentie documenteren

Zie de [Custom Evaluators-gids](/docs/security/custom-evaluators) voor het implementeren van geavanceerde autorisatiescenario's.
