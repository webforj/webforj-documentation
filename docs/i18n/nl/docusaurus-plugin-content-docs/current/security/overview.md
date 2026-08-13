---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Protect webforJ routes with declarative annotations and centralized
  authentication and authorization enforcement.
_i18n_hash: 850b9636996cb17a07a7aff25ac3cd0e
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Beveiliging <DocChip chip='since' label='25.10' />

:::note Publieke Voorvertoning
Deze functie is in publieke voorvertoning en gereed voor productiegebruik. Tijdens de voorvertoningsperiode kunnen API's worden verfijnd op basis van feedback van de ontwikkelaarsgemeenschap. Eventuele wijzigingen worden van tevoren aangekondigd via release-opmerkingen en migratiegidsen worden verstrekt wanneer dat nodig is.
:::

In moderne webapplicaties verwijst **beveiliging** naar het beheersen van de toegang tot verschillende delen van uw app op basis van gebruikersidentiteit en machtigingen. In webforJ biedt beveiliging een framework voor **toegangscontrole op route-niveau**, waarbij u weergaven kunt beschermen, authenticatie kunt vereisen en rolgebaseerde machtigingen kunt handhaven.

<AISkillTip skill="webforj-securing-apps" />

## Traditioneel VS beveiligd routeren {#traditional-vs-secured-routing}

Bij traditioneel onbeveiligd routeren zijn alle routes in uw app toegankelijk voor iedereen die de URL kent. Dit betekent dat gebruikers naar gevoelige pagina's zoals adminpanelen of gebruikersdashboards kunnen navigeren zonder enige authenticatie of autorisatiecontroles. De last ligt bij ontwikkelaars om handmatig machtigingen in elke component te verifiëren, wat leidt tot inconsistente handhaving van beveiliging en potentiële kwetsbaarheden.

Deze aanpak introduceert verschillende problemen:

1. **Handmatige controles**: Ontwikkelaars moeten zich herinneren om beveiligingslogica toe te voegen in elke beschermde weergave of lay-out.
2. **Inconsistente handhaving**: Beveiligingscontroles verspreid over de codebase leiden tot hiaten en fouten.
3. **Onderhoudsbelasting**: Wijzigingen in toegangsregels vereisen het bijwerken van meerdere bestanden.
4. **Geen gecentraliseerde controle**: Geen enkele plek om app-beveiliging te begrijpen of te beheren.

**Beveiligd routeren** in webforJ lost dit op door toegangscontrole rechtstreeks op route-niveau mogelijk te maken. Het beveiligingssysteem handhaaft automatisch regels voordat een component wordt weergegeven, wat zorgt voor een gecentraliseerde, declaratieve benadering van app-beveiliging. Zo werkt het:

1. **Declaratieve annotaties**: Markeer routes met beveiligingsannotaties om toegangsvereisten te definiëren.
2. **Automatische handhaving**: Het beveiligingssysteem controleert machtigingen voordat een weergave wordt weergegeven.
3. **Gecentraliseerde configuratie**: Definieer beveiligingsgedrag op één plaats en pas het consistent toe.
4. **Flexibele implementaties**: Kies tussen integratie met Spring Security of een aangepaste gewone Java-implementatie.

Dit ontwerp maakt **authenticatie** (verifiëren van de identiteit van de gebruiker) en **autorisatie** (verifiëren van wat de gebruiker kan benaderen) mogelijk, zodat alleen geautoriseerde gebruikers toegang krijgen tot beschermde routes. Ongeregistreerde gebruikers worden automatisch omgeleid of de toegang geweigerd op basis van de geconfigureerde beveiligingsregels.

## Voorbeeld van beveiligd routeren in webforJ {#example-of-secured-routing-in-webforj}

Hier is een voorbeeld dat verschillende beveiligingsniveaus in een webforJ-app laat zien:

```java title="LoginView.java"
// Publieke inlogpagina - iedereen kan toegang hebben
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {
  private final Login self = getBoundComponent();

  public LoginView() {
    self.onSubmit(e -> {
      handleLogin(e.getUsername(), e.getPassword());
    });

    whenAttached().thenAccept(c -> {
      self.open();
    });
  }
}
```

```java title="ProductsView.java"
// Producten - vereist authenticatie
@Route(value = "/", outlet = MainLayout.class)
public class ProductsView extends Composite<FlexLayout> {

  public ProductsView() {
    // productenweergave
  }
}
```

```java title="InvoicesView.java"
// Facturen - vereist ACCOUNTANT rol
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // facturenweergave
  }
}
```

In deze opzet:

- De `LoginView` is gemarkeerd met `@AnonymousAccess`, waardoor niet-geauthenticeerde gebruikers toegang hebben.
- De `ProductsView` heeft geen beveiligingsannotatie, wat betekent dat het standaard authentificatie vereist (wanneer de modus `secure-by-default` is ingeschakeld).
- De `InvoicesView` vereist de rol `ACCOUNTANT`, zodat alleen gebruikers met boekhoudmachtigingen toegang hebben tot facturen.

## Hoe beveiliging werkt {#how-security-works}

Wanneer een gebruiker probeert naar een route te navigeren, volgt het beveiligingssysteem deze stroom:

1. **Navigatie geïnitieerd**: Gebruiker klikt op een link of voert een URL in.
2. **Beveiligingsverificatie**: Voordat de component wordt weergegeven, evalueert het systeem beveiligingsannotaties en -regels.
3. **Beslissing**: Op basis van de authenticatiestatus en rollen van de gebruiker:
   - **Toestaan**: Toestaan van navigatie en de component weergeven.
   - **Weigeren**: Navigatie blokkeren en omleiden naar de inlogpagina of de toegang geweigerd pagina.
4. **Weergeven of omleiden**: Ofwel wordt de aangevraagde component weergegeven, of de gebruiker wordt geschikt omgeleid.

Met automatische handhaving worden beveiligingsregels consistent toegepast over uw gehele app, zodat toegangscontrole wordt afgehandeld voordat een component wordt weergegeven en ontwikkelaars geen handmatige controles in elke weergave hoeven toe te voegen.

## Authenticatie VS autorisatie {#authentication-vs-authorization}

Om beveiliging in uw app correct te implementeren, is het belangrijk om het verschil tussen deze twee concepten te kennen:

- **Authenticatie**: Verifiëren wie de gebruiker is. Dit gebeurt meestal tijdens het inloggen wanneer de gebruiker inloggegevens (gebruikersnaam en wachtwoord) biedt. Zodra de gebruiker is geauthenticeerd, wordt de identiteit van de gebruiker opgeslagen in de sessie of beveiligingscontext.

- **Autorisatie**: Verifiëren wat de geauthenticeerde gebruiker kan benaderen. Dit houdt in dat wordt gecontroleerd of de gebruiker de vereiste rollen of machtigingen heeft om toegang te krijgen tot een specifieke route. Autorisatie vindt elke keer plaats wanneer een gebruiker naar een beschermde route navigeert.

Het beveiligingssysteem van webforJ behandelt beide aspecten:

- Annotaties zoals `@PermitAll` behandelen authenticatievereisten.
- Annotaties zoals `@RolesAllowed` behandelen autorisatievereisten.

## Aan de slag {#getting-started}

Deze gids gaat ervan uit dat u **Spring Boot met Spring Security** gebruikt, wat de aanbevolen aanpak is voor de meeste webforJ-applicaties. Spring Security biedt authentificatie en autorisatie conform de industriestandaard met automatische configuratie via Spring Boot.

De rest van deze documentatie leidt u door het beveiligen van uw routes met Spring Security, van basisinstelling tot geavanceerde functies. Als u geen gebruik maakt van Spring Boot of een aangepaste beveiligingsimplementatie nodig hebt, zie dan de [Beveiligingsarchitectuuromgids](/docs/security/architecture/overview) om te leren hoe het systeem werkt en hoe u aangepaste beveiliging kunt implementeren.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
