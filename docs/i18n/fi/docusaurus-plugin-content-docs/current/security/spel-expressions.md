---
sidebar_position: 5
title: SpEL Expressions
_i18n_hash: 1019aac355c5ef0efc8623660c3501e5
---
Spring Expression Language (`SpEL`) tarjoaa deklaratiivisen tavan määritellä valtuutus sääntöjä suoraan annotaatioissa. `@RouteAccess` annotaatio arvioi `SpEL`-lausekkeita hyödyntäen Spring Securityn sisäänrakennettuja valtuutusfunktioita.

:::info Vain Spring Security
`SpEL`-lausekkeet ovat käytettävissä vain Spring-integraation avulla.
:::

## Peruskäyttö {#basic-usage}

`@RouteAccess` annotaatio hyväksyy `SpEL`-lausekkeen, joka arvioituu booleaniksi:

```java
@Route("/admin/dashboard")
@RouteAccess("hasRole('ADMIN')")
public class AdminDashboardView extends Composite<Div> {
  // Vain käyttäjät, joilla on ROLE_ADMIN-oikeus, voivat käyttää
}
```

Jos lauseke arvioituu `true`, pääsy myönnetään. Jos `false`, käyttäjä ohjataan pääsy kielletty -sivulle.

## Sisäänrakennetut turvallisuusfunktionit {#built-in-security-functions}

Spring Security tarjoaa seuraavat valtuutusfunktiot `SecurityExpressionRoot` kautta:

| Funktio | Parametrit | Kuvaus | Esimerkki |
|---------|------------|--------|---------|
| `hasRole` | `String role` | Tarkistaa, onko käyttäjä määritellyt roolin (automaattisesti etuliite `ROLE_`) | `hasRole('ADMIN')` vastaa `ROLE_ADMIN` |
| `hasAnyRole` | `String... roles` | Tarkistaa, onko käyttäjällä jokin määritellyistä rooleista | `hasAnyRole('ADMIN', 'MANAGER')` |
| `hasAuthority` | `String authority` | Tarkistaa, onko käyttäjällä tarkka valtuutusmerkkijono | `hasAuthority('REPORTS:READ')` |
| `hasAnyAuthority` | `String... authorities` | Tarkistaa, onko käyttäjällä jokin määritellyistä valtuutuksista | `hasAnyAuthority('REPORTS:READ', 'REPORTS:WRITE')` |
| `isAuthenticated` | Ei mitään | Palauttaa `true`, jos käyttäjä on todennettu | `isAuthenticated()` |

### Esimerkit {#examples}

```java
// Roolin tarkistus
@Route("/admin")
@RouteAccess("hasRole('ADMIN')")
public class AdminView extends Composite<Div> { }

// Useita rooleja
@Route("/staff")
@RouteAccess("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
public class StaffView extends Composite<Div> { }

// Oikeuden tarkistus
@Route("/reports")
@RouteAccess("hasAuthority('REPORTS:READ')")
public class ReportsView extends Composite<Div> { }

// Vaatii todennuksen
@Route("/profile")
@RouteAccess("isAuthenticated()")
public class ProfileView extends Composite<Div> { }
```

## Ehtojen yhdistäminen {#combining-conditions}

Käytä boolean-operaattoreita (`and`, `or`, `!`) luodaksesi monimutkaisempia valtuutus sääntöjä:

```java
// Molemmat ehdot vaaditaan
@Route("/moderator/reports")
@RouteAccess("hasRole('MODERATOR') and hasAuthority('REPORTS:VIEW')")
public class ModeratorReportsView extends Composite<Div> { }

// Joku ehto myöntää pääsyn
@Route("/support")
@RouteAccess("hasRole('ADMIN') or hasRole('SUPPORT')")
public class SupportView extends Composite<Div> { }

// Kieltäminen
@Route("/trial/features")
@RouteAccess("isAuthenticated() and !hasAuthority('PREMIUM')")
public class TrialFeaturesView extends Composite<Div> { }

// Monimutkainen monirivinen lauseke
@Route("/reports/advanced")
@RouteAccess("""
  hasRole('ADMIN') or
  (hasRole('ANALYST') and hasAuthority('REPORTS:ADVANCED'))
  """)
public class AdvancedReportsView extends Composite<Div> { }
```

## Yhdistäminen muiden annotaatioiden kanssa {#combining-with-other-annotations}

`@RouteAccess` toimii yhdessä standardin turvallisuusannotaatioiden kanssa. Arvioijia suoritetaan prioriteettijärjestyksessä:

```java
@Route("/team/admin")
@RolesAllowed("USER")
@RouteAccess("hasAuthority('TEAM:ADMIN')")
public class TeamAdminView extends Composite<Div> {
  // On oltava USER-rooli JA TEAM:ADMIN-oikeus
}
```

Arviointijärjestys:
1. `@RolesAllowed` arvioija (prioriteetti 5) tarkistaa `USER`-roolin
2. Jos hyväksytty, `@RouteAccess` arvioija (prioriteetti 6) arvioi `SpEL`-lausekkeen
3. Jos hyväksytty, mukautetut arvioijat suoritetaan (prioriteetti 10+)

## Mukautetut virhekoodit {#custom-error-codes}

Anna merkityksellisiä virkoodeja pääsykieltojen yhteydessä:

```java
@Route("/premium/features")
@RouteAccess(
  value = "hasAuthority('PREMIUM')",
  code = "PREMIUM_SUBSCRIPTION_REQUIRED"
)
public class PremiumFeaturesView extends Composite<Div> { }
```

`code`-parametri tunnistaa hylkäyssyyn, kun lauseke arvioituu `false`.

## Saatavilla olevat muuttujat {#available-variables}

`SpEL`-lausekkeilla on pääsy näihin muuttujiiin arviointikontekstissa:

| Muuttuja | Tyyppi | Kuvaus |
|----------|--------|--------|
| `authentication` | `Authentication` | Spring Securityn todennusobjekti |
| `principal` | `Object` | Todennettu pääkäyttäjä (yleensä `UserDetails`) |
| `routeClass` | `Class<? extends Component>` | Käytettävä reittikomponentin luokka |
| `context` | `NavigationContext` | webforJ navigointikonteksti |
| `securityContext` | `RouteSecurityContext` | webforJ reitin turvallisuuskonteksti |

Esimerkki muuttujien käytöstä:

```java
@Route("/admin")
@RouteAccess("authentication.name == 'superadmin'")
public class SuperAdminView extends Composite<Div> { }
```

## Milloin käyttää `SpEL` VERSUS mukautettuja arvioijia {#when-to-use-spel-vs-custom-evaluators}

**Käytä `@RouteAccess` `SpEL` kun:**
- Valtuutus perustuu puhtaasti rooleihin tai valtuutuksiin
- Yhdistetään sisäänrakennettuja turvallisuusfunktioita boolean-logiikan kanssa
- Reitti-spesifiset säännöt, joita ei tarvitse käyttää uudelleen

**Käytä mukautettuja arvioijia kun:**
- Valtuutus riippuu reittiparametreista (omistajuuden tarkistukset)
- Monimutkainen liiketoimintalogiikka, joka vaatii Spring-palvelun integraatiota
- Uudelleen käytettävät valtuutuspatterit useilla reiteillä
- Mukautetut annotaatiot, jotka dokumentoivat valtuutuksen tarkoituksen

Katso [Mukautettujen arvioijien opas](/docs/security/custom-evaluators) edistyneiden valtuutus-skenaarioiden toteuttamiseksi.
