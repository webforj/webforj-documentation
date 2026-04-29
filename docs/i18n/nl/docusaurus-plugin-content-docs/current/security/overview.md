---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: fe28b9f0c456b9880785afcc5d4d5f23
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Beveiliging <DocChip chip='since' label='25.10' />

:::note Openbare Preview
Deze functie is in openbare preview en klaar voor productiegebruik. Tijdens de previewperiode kunnen API's worden verfijnd op basis van feedback van de ontwikkelaarsgemeenschap. Wijzigingen worden vooraf aangekondigd via release-opmerkingen en migratiehandleidingen zullen worden verstrekt wanneer dat nodig is.
:::

In moderne webapplicaties verwijst **beveiliging** naar het controleren van de toegang tot verschillende delen van uw app op basis van gebruikersidentiteit en machtigingen. In webforJ biedt beveiliging een kader voor **route-niveau toegangscontrole**, waar u weergaven kunt beschermen, authenticatie kunt vereisen en rolgebaseerde machtigingen kunt handhaven.

<AISkillTip skill="webforj-securing-apps" />

## Traditionele VS beveiligde routing {#traditional-vs-secured-routing}

Bij traditionele onbeveiligde routing zijn alle routes in uw app toegankelijk voor iedereen die de URL kent. Dit betekent dat gebruikers gevoelige pagina's zoals beheerderspanelen of gebruikersdashboarden kunnen bezoeken zonder enige authenticatie of autorisatiecontroles. De verantwoordelijkheid ligt bij ontwikkelaars om handmatig machtigingen in elke component te verifiëren, wat leidt tot inconsistente beveiligingshandhaving en potentiële kwetsbaarheden.

Deze aanpak introduceert verschillende problemen:

1. **Handmatige controles**: Ontwikkelaars moeten onthouden om beveiligingslogica toe te voegen in elke beschermde weergave of lay-out.
2. **Inconsistente handhaving**: Beveiligingscontroles verspreid over de codebasis leiden tot hiaten en fouten.
3. **Onderhoudskosten**: Het wijzigen van toegangsregels vereist het bijwerken van meerdere bestanden.
4. **Geen gecentraliseerde controle**: Geen enkele plek om de beveiliging van de app te begrijpen of te beheren.

**Beveiligde routing** in webforJ lost dit op door toegangscontrole direct op het route-niveau mogelijk te maken. Het beveiligingssysteem handhaaft automatisch regels voordat een component wordt weergegeven, wat zorgt voor een gecentraliseerde, declaratieve benadering van app-beveiliging. Dit is hoe het werkt:

1. **Declaratieve annotaties**: Markeer routes met beveiligingsannotaties om toegangseisen te definiëren.
2. **Automatische handhaving**: Het beveiligingssysteem controleert machtigingen voordat enige weergave wordt weergegeven.
3. **Gecentraliseerde configuratie**: Definieer beveiligingsgedrag op één plek en pas dit consistent toe.
4. **Flexibele implementaties**: Kies tussen integratie met Spring Security of een aangepaste plain Java-implementatie.

Dit ontwerp maakt **authenticatie** (het verifiëren van de identiteit van de gebruiker) en **autorisatie** (het verifiëren van wat de gebruiker kan openen) mogelijk, zodat alleen geautoriseerde gebruikers toegang krijgen tot beschermde routes. Niet-geautoriseerde gebruikers worden automatisch omgeleid of de toegang ontzegd op basis van de geconfigureerde beveiligingsregels.

## Voorbeeld van beveiligde routing in webforJ {#example-of-secured-routing-in-webforj}

Hier is een voorbeeld dat verschillende beveiligingsniveaus in een webforJ-app toont:

```java title="LoginView.java"
// Publieke inlogpagina - iedereen kan toegang krijgen
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

- De `LoginView` is gemarkeerd met `@AnonymousAccess`, waardoor niet-geauthenticeerde gebruikers toegang krijgen tot deze.
- De `ProductsView` heeft geen beveiligingsannotatie, wat betekent dat authenticatie standaard vereist is (wanneer de modus `secure-by-default` is ingeschakeld).
- De `InvoicesView` vereist de `ACCOUNTANT` rol, zodat alleen gebruikers met boekhoudmachtigingen toegang krijgen tot facturen.

## Hoe beveiliging werkt {#how-security-works}

Wanneer een gebruiker probeert naar een route te navigeren, volgt het beveiligingssysteem deze stroom:

1. **Navigatie geïnitieerd**: De gebruiker klikt op een link of voert een URL in.
2. **Beveiligingsverificatie**: Voordat de component wordt weergegeven, evalueert het systeem beveiligingsannotaties en -regels.
3. **Beslissing**: Op basis van de authenticatiestatus en rollen van de gebruiker:
   - **Toestaan**: Sta navigatie toe en geef de component weer.
   - **Weigeren**: Blokkeer navigatie en omleid naar de inlogpagina of pagina voor toegang geweigerd.
4. **Weergeven of omleiden**: Ofwel wordt de aangevraagde component weergegeven, ofwel wordt de gebruiker dienovereenkomstig omgeleid.

Met automatische handhaving worden beveiligingsregels consistent toegepast in uw hele app, zodat toegangscontrole wordt behandeld voordat enige component wordt weergegeven en ontwikkelaars handmatige controles in elke weergave niet hoeven toe te voegen.

## Authenticatie VS autorisatie {#authentication-vs-authorization}

Om beveiliging correct in uw app te implementeren, is het belangrijk om het verschil tussen deze twee concepten te begrijpen:

- **Authenticatie**: Verifiëren wie de gebruiker is. Dit gebeurt doorgaans tijdens het inloggen wanneer de gebruiker zijn referenties (gebruikersnaam en wachtwoord) opgeeft. Zodra de gebruiker is geverifieerd, wordt de identiteit van de gebruiker opgeslagen in de sessie of beveiligingscontext.

- **Autorisatie**: Verifiëren welke toegang de geverifieerde gebruiker heeft. Dit houdt in dat gecontroleerd wordt of de gebruiker de vereiste rollen of machtigingen heeft om toegang te krijgen tot een specifieke route. Autorisatie vindt elke keer plaats dat een gebruiker naar een beschermde route navigeert.

Het beveiligingssysteem van webforJ behandelt beide aspecten:

- Annotaties zoals `@PermitAll` behandelen de authenticatievereisten.
- Annotaties zoals `@RolesAllowed` behandelen de autorisatievereisten.

## Aan de slag {#getting-started}

Deze gids gaat ervan uit dat u **Spring Boot met Spring Security** gebruikt, wat de aanbevolen aanpak is voor de meeste webforJ-applicaties. Spring Security biedt industriestandaard authenticatie en autorisatie met automatische configuratie via Spring Boot.

De rest van deze documentatie loopt u door het beveiligen van uw routes met Spring Security, van basisconfiguratie tot geavanceerde functies. Als u geen gebruik maakt van Spring Boot of een aangepaste beveiligingsimplementatie nodig heeft, zie dan de [Beveiligingsarchitectuur gids](/docs/security/architecture/overview) om te leren hoe het systeem werkt en hoe u aangepaste beveiliging kunt implementeren.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
