---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: cbd0aa0a56b47ee6270000fc326a7967
---
In webforJ worden alle routes weergegeven binnen een Frame, dat functioneert als een top-level container die verantwoordelijk is voor het weergeven van de inhoud van de huidige route. Terwijl gebruikers navigeren tussen verschillende routes, wordt de Frame Titel dynamisch bijgewerkt om de actieve weergave weer te geven, waardoor duidelijke context wordt geboden over de huidige locatie van de gebruiker binnen de app.

De titel van een frame kan statisch worden ingesteld met behulp van annotaties of dynamisch via code tijdens runtime. Deze flexibele aanpak stelt ontwikkelaars in staat om titels te definiëren die passen bij het doel van elke weergave, terwijl ze zich ook kunnen aanpassen aan specifieke scenario's of parameters indien nodig.

## Frame titel met annotaties {#frame-title-with-annotations}

De eenvoudigste manier om de titel van een frame in een weergave in te stellen, is door de `@FrameTitle` annotatie te gebruiken. Deze annotatie stelt je in staat om een statische titel te definiëren voor elk routecomponent, die wordt toegepast op het frame wanneer het component wordt weergegeven.

### Gebruik van de `@FrameTitle` annotatie {#using-the-frametitle-annotation}

De `@FrameTitle` annotatie wordt toegepast op het class-niveau en maakt het mogelijk om een stringwaarde op te geven die de titel van de pagina vertegenwoordigt. Wanneer de router naar een component met deze annotatie navigeert, wordt de opgegeven titel automatisch ingesteld voor het browservenster.

Hier is een voorbeeld:

```java
@Route
@FrameTitle("Dashboard")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // view logic
  }
}
```

In dit voorbeeld:
- De `DashboardView` klasse is geannoteerd met `@Route` om de route te definiëren.
- De `@FrameTitle("Dashboard")` annotatie stelt de frame titel in op "Dashboard".
- Wanneer de gebruiker navigeert naar `/dashboard`, wordt de titel van het frame automatisch bijgewerkt naar de opgegeven waarde.

Deze methode is nuttig voor routes die een statische titel hebben en geen frequente updates vereisen op basis van de context van de route.

:::tip `@AppTitle` en `@FrameTitle`  
Als de app-titel is ingesteld, zal de frame-titel deze opnemen. Bijvoorbeeld, als de app de titel definieert als `@AppTitle("webforJ")` en de frame-titel is ingesteld als `@FrameTitle("Dashboard")`, zal de uiteindelijke paginatitel zijn `Dashboard - webforJ`. Je kunt het formaat van de uiteindelijke titel in de `@AppTitle` annotatie aanpassen door de `format` attribuut indien nodig te gebruiken.  
:::

## Dynamische frame titels {#dynamic-frame-titles}

In situaties waarin de frame-titel dynamisch moet veranderen op basis van de status van de app of routeparameters, biedt webforJ een interface genaamd `HasFrameTitle`. Deze interface stelt componenten in staat om een frame-titel te bieden op basis van de huidige navigatiecontext en routeparameters.

### Implementeren van de `HasFrameTitle` interface {#implementing-the-hasframetitle-interface}

De `HasFrameTitle` interface bevat een enkele methode `getFrameTitle()`, die wordt aangeroepen voordat de titel van het frame wordt bijgewerkt. Deze methode biedt de flexibiliteit om een titel dynamisch te genereren op basis van de navigatiecontext of andere dynamische factoren.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {

  public ProfileView() {
    getBoundComponent().add(new H1("Profielpagina"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Dynamisch de frame titel instellen met behulp van routeparameters
    String userId = parameters.get("id").orElse("Onbekend");
    return "Profiel - Gebruiker " + userId;
  }
}
```

In dit voorbeeld:
- Het `ProfileView` component implementeert de `HasFrameTitle` interface.
- De `getFrameTitle()` methode genereert dynamisch een titel met behulp van de `id` parameter uit de URL.
- Als de route is `/profile/123`, wordt de titel bijgewerkt naar "Profiel - Gebruiker 123".

:::tip Combineren van annotaties en dynamische titels
Je kunt zowel statische als dynamische methoden combineren. Als een routecomponent zowel een `@FrameTitle` annotatie heeft als de `HasFrameTitle` interface implementeert, heeft de dynamisch opgegeven titel uit `getFrameTitle()` voorrang boven de statische waarde uit de annotatie.
:::
