---
sidebar_position: 3
title: Security Annotations
_i18n_hash: af9997b8bee96bfa4005a65998fddaf5
---
Beveiligingsannotaties bieden een declaratieve manier om toegang tot routes in uw webforJ-app te controleren. Door annotaties aan uw routecomponenten toe te voegen, definieert u wie toegang heeft tot elke weergave zonder handmatige toestemmingscontroles te schrijven. Het beveiligingssysteem handhaaft automatisch deze regels voordat een component wordt weergegeven.

:::info Implementatienoot
Deze gids werkt met elke beveiligingsimplementatie. De getoonde voorbeelden werken zowel met Spring Security als met aangepaste implementaties. Als u geen Spring Boot gebruikt, zie dan de [beveiligingsarchitectuurgids](/docs/security/architecture/overview) om de basis te begrijpen en aangepaste beveiliging te implementeren.
:::

## `@AnonymousAccess` - openbare routes {#anonymousaccess-public-routes}

De annotatie `@AnonymousAccess` markeert een route als openbaar toegankelijk. Gebruikers hoeven niet geauthenticeerd te zijn om toegang te krijgen tot deze routes. Deze annotatie wordt typisch gebruikt voor inlogpagina's, openbare landingspagina's of andere inhoud die voor iedereen beschikbaar moet zijn.

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {

  public LoginView() {
    // inlogweergave
  }
}
```

In dit voorbeeld:
- Elke gebruiker, geauthenticeerd of niet, kan de route `/login` openen.
- Wanneer `@AnonymousAccess` aanwezig is, mogen niet-geauthenticeerde gebruikers deze pagina openen zonder omgeleid te worden.

:::tip Veelvoorkomende gebruiksgevallen
Gebruik `@AnonymousAccess` voor inlogpagina's, registratiepagina's, openbare startpagina's, servicevoorwaarden, privacybeleid en andere inhoud die zonder authenticatie toegankelijk moet zijn.
:::

## `@PermitAll` - geauthenticeerde routes {#permitall-authenticated-routes}

De annotatie `@PermitAll` vereist dat gebruikers geauthenticeerd zijn, maar legt geen specifieke rolvereisten op. Elke ingelogde gebruiker kan toegang krijgen tot deze routes, ongeacht hun rollen of machtigingen.

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Inbox"));
  }
}
```

In dit voorbeeld:
- Gebruikers moeten geauthenticeerd zijn om toegang te krijgen tot de inbox.
- Elke geauthenticeerde gebruiker kan deze pagina bekijken, ongeacht hun rol.
- Niet-geauthenticeerde gebruikers worden omgeleid naar de inlogpagina.

:::info Beveiligd-per-default modus
Wanneer de beveiligd-per-default modus is ingeschakeld, gedragen routes zonder enige beveiligingsannotatie zich hetzelfde als `@PermitAll`— ze vereisen authenticatie. Zie de [beveiligd-per-default sectie](#secure-by-default) voor details.
:::

## `@RolesAllowed` - rolgebaseerde routes {#rolesallowed-role-based-routes}

De annotatie `@RolesAllowed` beperkt de toegang tot gebruikers met specifieke rollen. U kunt een of meer rollen specificeren, en gebruikers moeten minstens één van de vermelde rollen hebben om toegang te krijgen tot de route.

### Enkele rolvereiste {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {

  public TrashView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Trash"));
  }
}
```

In dit voorbeeld:
- Alleen gebruikers met de rol `ADMIN` kunnen de prullenbakweergave openen.
- Gebruikers zonder de rol `ADMIN` worden omgeleid naar de toegang geweigerd-pagina.

### Meervoudige rolvereisten {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {

  public SettingsView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Explore("Settings"));
  }
}
```

In dit voorbeeld:
- Gebruikers met de rol `ADMIN` of `MANAGER` kunnen toegang krijgen tot de instellingen.
- De gebruiker hoeft slechts één van de vermelde rollen te hebben, niet alle.

:::tip Rolbenamingsconventies
Gebruik hoofdletters voor rolbenamingen (zoals `ADMIN`, `USER`, `MANAGER`) voor consistentie. Dit komt overeen met de gangbare conventies van beveiligingskaders en maakt uw code leesbaarder.
:::

## `@DenyAll` - geblokkeerde routes {#denyall-blocked-routes}

De annotatie `@DenyAll` blokkeert de toegang tot een route voor alle gebruikers, ongeacht hun authenticatiestatus of rollen. Dit is nuttig voor het tijdelijk uitschakelen van routes tijdens onderhoud of voor routes die in ontwikkeling zijn.

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {

  public MaintenanceView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Paragraph("Deze pagina is in onderhoud."));
  }
}
```

In dit voorbeeld:
- Geen enkele gebruiker kan deze route openen, zelfs niet de beheerders.
- Alle toegangspogingen resulteren in een omleiding naar de toegang geweigerd-pagina.

:::warning Tijdelijk gebruik
`@DenyAll` wordt typisch tijdelijk gebruikt tijdens ontwikkeling of onderhoud. Voor productie-apps is het aan te raden de route geheel te verwijderen of in plaats daarvan de juiste rolbeperkingen te gebruiken.
:::

## Wat gebeurt er wanneer de toegang wordt geweigerd {#what-happens-when-access-is-denied}

Wanneer een gebruiker probeert toegang te krijgen tot een route waarvoor ze niet geautoriseerd zijn, handelt het beveiligingssysteem de weigering automatisch af:

1. **Niet-geauthenticeerde gebruikers**: Worden omgeleid naar de inlogpagina die is geconfigureerd in uw beveiligingsinstellingen.
2. **Geauthenticeerde gebruikers zonder vereiste rollen**: Worden omgeleid naar de toegang geweigerd-pagina.
3. **Alle gebruikers op `@DenyAll`-routes**: Worden omgeleid naar de toegang geweigerd-pagina.

U kunt deze omleidingslocaties aanpassen om overeen te komen met de navigatiestructuur van uw app, zodat toegang weigeringen en authenticatie-aanvragen leiden naar de bedoelde pagina's. Zie [Spring Security configureren](/docs/security/getting-started#configure-spring-security) voor configuratiedetails.

## Beveiligd-per-default {#secure-by-default}

Beveiligd-per-default is een configuratieoptie die bepaalt hoe routes zonder enige beveiligingsannotatie worden behandeld. Wanneer ingeschakeld, vereisen alle routes standaard authenticatie, tenzij expliciet gemarkeerd met `@AnonymousAccess`.

### Ingeschakeld (aanbevolen voor productie) {#enabled-recommended-for-production}

Voeg dit toe aan uw `application.properties`:

```properties title="application.properties"
webforj.security.secure-by-default=true
```

Met beveiligd-per-default ingeschakeld:
- Routes zonder annotaties vereisen authenticatie (dezelfde als `@PermitAll`).
- Alleen routes met `@AnonymousAccess` zijn openbaar toegankelijk.
- U moet openbaar toegankelijke routes expliciet markeren, waardoor het risico op onbedoelde blootstelling van beschermde inhoud wordt verminderd.

```java
// Vereist authenticatie (geen annotatie)
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// Publieke toegang (expliciet gemarkeerd)
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### Uitgeschakeld (toegestaan-per-default) {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

Met beveiligd-per-default uitgeschakeld:
- Routes zonder annotaties zijn openbaar toegankelijk.
- U moet expliciet `@PermitAll` of `@RolesAllowed` toevoegen om routes te beschermen.
- Gemakkelijker voor ontwikkeling, maar riskanter voor productie.

```java
// Publieke toegang (geen annotatie)
@Route("/about")
public class AboutView extends Composite<Div> { }

// Vereist authenticatie (expliciet gemarkeerd)
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> { }
```

:::tip Beste praktijk
Schakel `secure-by-default` in voor productie-apps. Met deze instelling worden beschermde routes niet blootgesteld tenzij ze expliciet gemarkeerd zijn als openbaar, waardoor het risico van onbedoelde blootstelling door ontbrekende annotaties wordt verminderd. Zet het alleen uit tijdens de eerste ontwikkeling als u de extra annotaties onhandig vindt.
:::
