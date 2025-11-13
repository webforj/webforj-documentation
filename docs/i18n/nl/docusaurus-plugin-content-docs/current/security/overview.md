---
sidebar_position: 1
title: Beveiliging
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: b6707cb6491075a82ac19fb808840245
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Beveiliging <DocChip chip='since' label='25.10' />

:::note Publieke Preview
Deze functie is in publieke preview en klaar voor gebruik in productie. Gedurende de previewperiode kunnen API's verfijnd worden op basis van feedback van de ontwikkelaarsgemeenschap. Wijzigingen worden van tevoren aangekondigd via release-opmerkingen en migratiegidsen worden verstrekt wanneer nodig.
:::

In moderne webtoepassingen verwijst **beveiliging** naar het beheren van toegang tot verschillende delen van uw app op basis van de identiteit en machtigingen van de gebruiker. In webforJ biedt beveiliging een raamwerk voor **toegangscontrole op route-niveau**, waar u weergaven kunt beschermen, authenticatie kunt vereisen en rolgebaseerde machtigingen kunt handhaven.

## Traditionele VS beveiligde routing {#traditional-vs-secured-routing}

Bij traditionele onbeveiligde routing zijn alle routes in uw app toegankelijk voor iedereen die de URL kent. Dit betekent dat gebruikers kunnen navigeren naar gevoelige pagina's zoals beheerderspanelen of gebruikersdashboards zonder enige authenticatie of autorisatiecontroles. De verantwoordelijkheid ligt bij ontwikkelaars om handmatig machtigingen te verifiëren in elke component, wat leidt tot inconsistente handhaving van beveiliging en potentiële kwetsbaarheden.

Deze aanpak introduceert verschillende problemen:

1. **Handmatige controles**: Ontwikkelaars moeten zich herinneren om beveiligingslogica toe te voegen in elke beschermde weergave of lay-out.
2. **Inconsistente handhaving**: Beveiligingscontroles verspreid over de codebase leiden tot hiaten en fouten.
3. **Onderhoudslast**: Het wijzigen van toegangsregels vereist het bijwerken van meerdere bestanden.
4. **Geen gecentraliseerde controle**: Geen enkele plek om de beveiliging van de app te begrijpen of te beheren.

**Beveiligde routing** in webforJ lost dit op door toegangscontrole direct op route-niveau mogelijk te maken. Het beveiligingssysteem handhaaft automatisch regels voordat een component wordt gerenderd, wat een gecentraliseerde, declaratieve benadering van app-beveiliging biedt. Zo werkt het:

1. **Declaratieve annotaties**: Markeer routes met beveiligingsannotaties om toegangseisen te definiëren.
2. **Automatische handhaving**: Het beveiligingssysteem controleert machtigingen voordat een weergave wordt gerenderd.
3. **Gecentraliseerde configuratie**: Definieer beveiligingsgedrag op één plek en pas het consistent toe.
4. **Flexibele implementaties**: Kies tussen integratie van Spring Security of een op maat gemaakte implementatie in gewone Java.

Dit ontwerp maakt **authenticatie** (het verifiëren van de identiteit van de gebruiker) en **autorisatie** (het verifiëren van wat de gebruiker kan openen) mogelijk, zodat alleen geautoriseerde gebruikers toegang krijgen tot beschermde routes. Ongeautoriseerde gebruikers worden automatisch omgeleid of de toegang geweigerd op basis van de geconfigureerde beveiligingsregels.

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

In deze setup:

- De `LoginView` is gemarkeerd met `@AnonymousAccess`, waardoor niet-geauthenticeerde gebruikers er toegang toe hebben.
- De `ProductsView` heeft geen beveiligingsannotatie, wat betekent dat deze standaard authenticatie vereist (wanneer de modus `secure-by-default` is ingeschakeld).
- De `InvoicesView` vereist de rol `ACCOUNTANT`, zodat alleen gebruikers met boekhoudmachtigingen toegang kunnen krijgen tot facturen.

## Hoe beveiliging werkt {#how-security-works}

Wanneer een gebruiker probeert te navigeren naar een route, volgt het beveiligingssysteem deze stroom:

1. **Navigatie geïnitieerd**: Gebruiker klikt op een link of voert een URL in.
2. **Beveiligingsverificatie**: Voordat de component wordt gerenderd, evalueert het systeem beveiligingsannotaties en regels.
3. **Beslissing**: Op basis van de authenticatiestatus en rollen van de gebruiker:
   - **Toevoegen**: Sta navigatie toe en render de component.
   - **Weigeren**: Blokkeer navigatie en leid om naar de inlogpagina of toegang geweigerd-pagina.
4. **Renderen of omleiden**: Ofwel wordt de aangevraagde component weergegeven, of de gebruiker wordt op de juiste manier omgeleid.

Met automatische handhaving worden beveiligingsregels consistent toegepast in uw hele app, zodat toegangscontrole wordt afgehandeld voordat een component wordt gerenderd en ontwikkelaars geen handmatige controles in elke weergave hoeven toe te voegen.

## Authenticatie VS autorisatie {#authentication-vs-authorization}

Om beveiliging in uw app correct te implementeren, is het belangrijk om het verschil tussen deze twee concepten te begrijpen:

- **Authenticatie**: Verifiëren wie de gebruiker is. Dit gebeurt meestal tijdens het inloggen wanneer de gebruiker inloggegevens (gebruikersnaam en wachtwoord) verstrekt. Zodra de gebruiker is geauthenticeerd, wordt de identiteit van de gebruiker opgeslagen in de sessie of beveiligingscontext.

- **Autorisatie**: Verifiëren waar de geauthenticeerde gebruiker toegang toe heeft. Dit houdt in dat gecontroleerd wordt of de gebruiker de vereiste rollen of machtigingen heeft om toegang te krijgen tot een specifieke route. Autorisatie vindt plaats elke keer dat een gebruiker naar een beschermde route navigeert.

Het beveiligingssysteem van webforJ behandelt beide aspecten:

- Annotaties zoals `@PermitAll` behandelen de vereisten voor authenticatie.
- Annotaties zoals `@RolesAllowed` behandelen de vereisten voor autorisatie.

## Aan de slag {#getting-started}

Deze gids gaat ervan uit dat u **Spring Boot met Spring Security** gebruikt, wat de aanbevolen aanpak is voor de meeste webforJ-applicaties. Spring Security biedt industriestandaard authenticatie en autorisatie met automatische configuratie via Spring Boot.

De rest van deze documentatie begeleidt u bij het beveiligen van uw routes met Spring Security, van basisopzet tot geavanceerde functies. Als u geen gebruikmaakt van Spring Boot of een aangepaste beveiligingsimplementatie nodig heeft, zie dan de [Beveiligingsarchitectuurgids](/docs/security/architecture/overview) om te leren hoe het systeem werkt en hoe u aangepaste beveiliging kunt implementeren.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
