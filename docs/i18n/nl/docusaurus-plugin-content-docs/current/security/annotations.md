---
sidebar_position: 3
title: Security Annotations
_i18n_hash: 564a7d991d26edb972bc2c7c99366f37
---
Beveiligingsannotaties bieden een declaratieve manier om toegang tot routes in jouw webforJ-app te controleren. Door annotaties aan jouw routecomponenten toe te voegen, definieer je wie toegang heeft tot elke weergave zonder handmatige machtigingscontroles te schrijven. Het beveiligingssysteem handhaaft automatisch deze regels voordat een component wordt weergegeven.

:::info Implementatienotitie
Deze gids werkt met elke beveiligingsimplementatie. De getoonde voorbeelden werken zowel met Spring Security als met aangepaste implementaties. Als je geen gebruik maakt van Spring Boot, raadpleeg dan de [Beveiligingsarchitectuur gids](/docs/security/architecture/overview) om de basis te begrijpen en aangepaste beveiliging te implementeren.
:::

## `@AnonymousAccess` - openbare routes {#anonymousaccess-public-routes}

De annotatie `@AnonymousAccess` markeert een route als openbaar toegankelijk. Gebruikers hoeven niet geauthenticeerd te zijn om toegang te krijgen tot deze routes. Deze annotatie wordt meestal gebruikt voor inlogpagina's, openbare landingspagina's of andere inhoud die voor iedereen beschikbaar moet zijn.

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
- Elke gebruiker, geauthenticeerd of niet, kan de route `/login` benaderen.
- Wanneer `@AnonymousAccess` aanwezig is, mogen niet-geauthenticeerde gebruikers deze pagina bezoeken zonder omgeleid te worden.

:::tip Veelvoorkomende gebruiksscenario's
Gebruik `@AnonymousAccess` voor inlogpagina's, registratiepagina's, openbare startpagina's, gebruikersvoorwaarden, privacybeleid en andere inhoud die zonder authenticatie toegankelijk moet zijn.
:::

## `@PermitAll` - geverifieerde routes {#permitall-authenticated-routes}

De annotatie `@PermitAll` vereist dat gebruikers geauthenticeerd zijn, maar stelt geen specifieke rolvereisten. Elke ingelogde gebruiker kan deze routes benaderen, ongeacht hun rollen of machtigingen.

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.add(new Explore("Inbox"));
  }
}
```

In dit voorbeeld:
- Gebruikers moeten geauthenticeerd zijn om toegang te krijgen tot de inbox.
- Elke geauthenticeerde gebruiker kan deze pagina bekijken, ongeacht hun rol.
- Niet-geauthenticeerde gebruikers worden omgeleid naar de inlogpagina.

:::info Secure-by-default modus
Wanneer de secure-by-default modus is ingeschakeld, gedragen routes zonder beveiligingsannotatie zich hetzelfde als `@PermitAll`—ze vereisen authenticatie. Zie de [secure-by-default sectie](#secure-by-default) voor details.
:::

## `@RolesAllowed` - rolgebaseerde routes {#rolesallowed-role-based-routes}

De annotatie `@RolesAllowed` beperkt de toegang tot gebruikers met specifieke rollen. Je kunt een of meer rollen specificeren, en gebruikers moeten ten minste één van de vermelde rollen hebben om toegang te krijgen tot de route.

### Enkele rolvereiste {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TrashView() {
    self.setHeight("100%");
    self.add(new Explore("Trash"));
  }
}
```

In dit voorbeeld:
- Alleen gebruikers met de rol `ADMIN` kunnen de prullenbakweergave benaderen.
- Gebruikers zonder de rol `ADMIN` worden omgeleid naar de toegang geweigerd-pagina.

### Meerdere rolvereisten {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public SettingsView() {
    self.add(new Explore("Settings"));
  }
}
```

In dit voorbeeld:
- Gebruikers met ofwel de rol `ADMIN` of `MANAGER` kunnen de instellingen benaderen.
- De gebruiker heeft slechts één van de vermelde rollen nodig, niet allemaal.

:::tip Rolnaamconventies
Gebruik hoofdletters voor rolbenamingen (zoals `ADMIN`, `USER`, `MANAGER`) voor consistentie. Dit komt overeen met de gangbare conventies van beveiligingsframeworks en maakt je code leesbaarder.
:::

## `@DenyAll` - geblokkeerde routes {#denyall-blocked-routes}

De annotatie `@DenyAll` blokkeert de toegang tot een route voor alle gebruikers, ongeacht de authenticatiestatus of rollen. Dit is nuttig voor het tijdelijk uitschakelen van routes tijdens onderhoud of voor routes die nog in ontwikkeling zijn.

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public MaintenanceView() {
    self.add(new Paragraph("Deze pagina is in onderhoud."));
  }
}
```

In dit voorbeeld:
- Geen enkele gebruiker kan deze route benaderen, zelfs niet de beheerders.
- Alle pogingen tot toegang resulteren in omleiding naar de toegang geweigerd-pagina.

:::warning Tijdelijk gebruik
`@DenyAll` wordt doorgaans tijdelijk gebruikt tijdens ontwikkeling of onderhoud. Voor productie-apps kun je overwegen de route helemaal te verwijderen of in plaats daarvan gebruik te maken van de juiste rolbeperkingen.
:::

## Wat gebeurt er als de toegang wordt geweigerd {#what-happens-when-access-is-denied}

Wanneer een gebruiker probeert toegang te krijgen tot een route waarvoor ze niet zijn geautoriseerd, behandelt het beveiligingssysteem de weigering automatisch:

1. **Niet-geauthenticeerde gebruikers**: Worden omgeleid naar de inlogpagina die in jouw beveiligingsinstellingen is geconfigureerd.
2. **Geverifieerde gebruikers zonder vereiste rollen**: Worden omgeleid naar de toegang geweigerd-pagina.
3. **Alle gebruikers op `@DenyAll`-routes**: Worden omgeleid naar de toegang geweigerd-pagina.

Je kunt deze omleidingslocaties aanpassen om overeen te komen met de navigatiestructuur van jouw app, zodat toegang weigeringen en authenticatieverzoeken leiden naar de bedoelde pagina's. Zie [Configureer Spring Security](/docs/security/getting-started#configure-spring-security) voor configuratiedetails.

## Secure-by-default {#secure-by-default}

Secure-by-default is een configuratieoptie die bepaalt hoe routes zonder beveiligingsannotatie worden behandeld. Wanneer deze is ingeschakeld, vereisen alle routes standaard authenticatie, tenzij expliciet gemarkeerd met `@AnonymousAccess`.

### Ingeschakeld (aanbevolen voor productie) {#enabled-recommended-for-production}

Voeg dit toe aan jouw `application.properties`:

```properties title="application.properties"
webforj.security.secure-by-default=true
```

Met secure-by-default ingeschakeld:
- Routes zonder annotaties vereisen authenticatie (dezelfde als `@PermitAll`).
- Alleen `@AnonymousAccess` routes zijn openbaar toegankelijk.
- Je moet expliciet openbare routes markeren, waardoor het risico op per ongeluk blootstellen van beschermde inhoud vermindert.

```java
// Vereist authenticatie (geen annotatie)
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// Publieke toegang (expliciet gemarkeerd)
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### Uitgeschakeld (toestaan-standaard) {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

Met secure-by-default uitgeschakeld:
- Routes zonder annotaties zijn openbaar toegankelijk.
- Je moet expliciet `@PermitAll` of `@RolesAllowed` toevoegen om routes te beschermen.
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
Schakel `secure-by-default` in voor productie-apps. Met deze instelling zijn beschermde routes niet zichtbaar tenzij ze expliciet als publiek zijn gemarkeerd, waardoor het risico op accidentele blootstelling door ontbrekende annotaties wordt verminderd. Zet deze optie alleen uit tijdens de initiële ontwikkeling als je de extra annotaties onhandig vindt.
:::
