---
sidebar_position: 5
title: SpEL Expressions
description: >-
  Author route authorization rules with Spring Expression Language using
  @RouteAccess for role, authority, and custom checks.
_i18n_hash: 59601d0d83fe7eb4b05bf8eab47515c3
---
Spring Expression Language (`SpEL`) tarjoaa deklaratiivisen tavan määrittää valtuutussäännöt suoraan annotaatioissa. `@RouteAccess`-annotaatio arvioi `SpEL`-ilmaisuja käyttäen Spring Securityn sisäänrakennettuja valtuutustoimintoja.

:::info Vain Spring Security
`SpEL`-ilmaisuja on saatavilla vain käytettäessä Spring-integraatiota.
:::

`@RouteAccess`-annotaatio hyväksyy `SpEL`-ilmaisuja, jotka arvioidaan boolean-arvoiksi:

```java
@Route("/admin/dashboard")
@RouteAccess("hasRole('ADMIN')")
public class AdminDashboardView extends Composite<Div> {
  // Vain käyttäjillä, joilla on ROLE_ADMIN -valtuutus, on pääsy
}
```

Jos ilmaisu arvioidaan `true`:ksi, pääsy myönnetään. Jos `false`, käyttäjä ohjataan pääsyn kielto-sivulle.

## Sisäänrakennetut turvallisuustoiminnot {#built-in-security-functions}

Spring Security tarjoaa seuraavat valtuutustoiminnot `SecurityExpressionRoot`in kautta:

| Toiminto      | Parametrit            | Kuvaus                                                   | Esimerkki                        |
|---------------|-----------------------|----------------------------------------------------------|----------------------------------|
| `hasRole`     | `String role`         | Tarkistaa, onko käyttäjällä määritetty rooli (automaattisesti etuliitteellä `ROLE_`) | `hasRole('ADMIN')` vastaa `ROLE_ADMIN`:ia |
| `hasAnyRole`  | `String... roles`     | Tarkistaa, onko käyttäjällä jokin määritetty rooli      | `hasAnyRole('ADMIN', 'MANAGER')` |
| `hasAuthority`| `String authority`    | Tarkistaa, onko käyttäjällä tarkka valtuutusteksti      | `hasAuthority('REPORTS:READ')`  |
| `hasAnyAuthority`| `String... authorities` | Tarkistaa, onko käyttäjällä jokin määritetty valtuutus   | `hasAnyAuthority('REPORTS:READ', 'REPORTS:WRITE')` |
| `isAuthenticated` | Ei mitään         | Palauttaa `true`, jos käyttäjä on todennettu            | `isAuthenticated()`              |

### Esimerkit {#examples}

```java
// Roolitarkistus
@Route("/admin")
@RouteAccess("hasRole('ADMIN')")
public class AdminView extends Composite<Div> { }

// Useita rooleja
@Route("/staff")
@RouteAccess("hasAnyRole('ADMIN', 'MANAGER', 'SUPERVISOR')")
public class StaffView extends Composite<Div> { }

// Valtuustarkistus
@Route("/reports")
@RouteAccess("hasAuthority('REPORTS:READ')")
public class ReportsView extends Composite<Div> { }

// Vaatimus todennuksesta
@Route("/profile")
@RouteAccess("isAuthenticated()")
public class ProfileView extends Composite<Div> { }
```

## Ehtojen yhdistäminen {#combining-conditions}

Käytä boolean-operaattoreita (`and`, `or`, `!`) luodaksesi monimutkaisempia valtuutussääntöjä:

```java
// Molemmat ehdot vaaditaan
@Route("/moderator/reports")
@RouteAccess("hasRole('MODERATOR') and hasAuthority('REPORTS:VIEW')")
public class ModeratorReportsView extends Composite<Div> { }

// Jokin ehto myöntää pääsyn
@Route("/support")
@RouteAccess("hasRole('ADMIN') or hasRole('SUPPORT')")
public class SupportView extends Composite<Div> { }

// Kieltämistä
@Route("/trial/features")
@RouteAccess("isAuthenticated() and !hasAuthority('PREMIUM')")
public class TrialFeaturesView extends Composite<Div> { }

// Monimutkainen monirivinen ilmaisu
@Route("/reports/advanced")
@RouteAccess("""
  hasRole('ADMIN') or
  (hasRole('ANALYST') and hasAuthority('REPORTS:ADVANCED'))
  """)
public class AdvancedReportsView extends Composite<Div> { }
```

## Yhdistäminen muihin annotaatioihin {#combining-with-other-annotations}

`@RouteAccess` toimii yhdessä vakioturva-annotaatioiden kanssa. Arvioijat toimivat prioriteettijärjestyksessä:

```java
@Route("/team/admin")
@RolesAllowed("USER")
@RouteAccess("hasAuthority('TEAM:ADMIN')")
public class TeamAdminView extends Composite<Div> {
  // On oltava USER-rooli JA TEAM:ADMIN -valtuutus
}
```

Arviointijärjestys:
1. `@RolesAllowed`-arvioija (prioriteetti 5) tarkistaa `USER`-roolin
2. Jos hyväksytään, `@RouteAccess`-arvioija (prioriteetti 6) arvioi `SpEL`-ilmaisuja
3. Jos hyväksytään, mukautetut arvioijat suoritetaan (prioriteetti 10+)

## Mukautetut virhekoodit {#custom-error-codes}

Tarjoa merkityksellisiä virhekoodit pääsyn kieltämiselle:

```java
@Route("/premium/features")
@RouteAccess(
  value = "hasAuthority('PREMIUM')",
  code = "PREMIUM_SUBSCRIPTION_REQUIRED"
)
public class PremiumFeaturesView extends Composite<Div> { }
```

`code`-parametri tunnistaa kieltämisen syyn, kun ilmaisu arvioituu `false`:ksi.

## Saatavilla olevat muuttujat {#available-variables}

`SpEL`-ilmaisuilla on pääsy seuraaviin muuttujii arviointikontekstissa:

| Muuttuja      | Tyyppi                | Kuvaus                                                   |
|---------------|-----------------------|----------------------------------------------------------|
| `authentication` | `Authentication`    | Spring Securityn todennusobjekti                        |
| `principal`   | `Object`              | Todennettu päähenkilö (yleensä `UserDetails`)           |
| `routeClass`  | `Class<? extends Component>` | Pääsyssä oleva reititys komponentin luokka          |
| `context`     | `NavigationContext`   | webforJ navigointikonteksti                              |
| `securityContext` | `RouteSecurityContext` | webforJ reititys turvallisuuskonteksti                |

Esimerkki muuttujien käytöstä:

```java
@Route("/admin")
@RouteAccess("authentication.name == 'superadmin'")
public class SuperAdminView extends Composite<Div> { }
```

## Milloin käyttää `SpEL` VS mukautettuja arvioijia {#when-to-use-spel-vs-custom-evaluators}

**Käytä `@RouteAccess` `SpEL`:ia kun:**
- Valtuutus perustuu täysin rooleihin tai valtuutuksiin
- Yhdistetään sisäänrakennettuja turvallisuustoimintoja boolean-logiikan kanssa
- Reitti-spesifisiä sääntöjä, joita ei tarvitse käyttää uudelleen

**Käytä mukautettuja arvioijia kun:**
- Valtuutus riippuu reitityksen parametreista (omistajuustarkistukset)
- Monimutkainen liiketoimintalogiikka, joka vaatii Spring-palvelun integraatiota
- Uudelleenkäytettäviä valtuutuspatterneja useilla reiteillä
- Mukautettuja annotaatioita, jotka dokumentoivat valtuutusintention

Katso [Mukautettujen arvioijien opas](/docs/security/custom-evaluators) kehitettävien edistyneinä valtuutus-skenaarioiden toteuttamiseen.
