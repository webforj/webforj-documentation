---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 9d594a84516af29dde3f66726bc22825
---
In webforJ worden alle routes weergegeven binnen een Frame, dat fungeert als een top-level container verantwoordelijk voor het weergeven van de inhoud van de huidige route. Terwijl gebruikers tussen verschillende routes navigeren, wordt de Frame Title dynamisch bijgewerkt om het actieve zicht weer te geven, wat helpt om duidelijk context te bieden over de huidige locatie van de gebruiker binnen de app.

De titel van een frame kan ofwel statisch worden ingesteld met behulp van annotaties of dynamisch via code tijdens runtime. Deze flexibele aanpak stelt ontwikkelaars in staat om titels te definiëren die aansluiten bij het doel van elk zicht, terwijl ze ook kunnen worden aangepast aan specifieke scenario's of parameters indien nodig.

## Frame-titel met annotaties {#frame-title-with-annotations}

De eenvoudigste manier om de titel van een frame in weergave in te stellen, is door gebruik te maken van de `@FrameTitle` annotatie. Deze annotatie stelt je in staat om een statische titel voor een routecomponent te definiëren, die vervolgens op het frame wordt toegepast wanneer de component wordt weergegeven.

### Gebruik van de `@FrameTitle` annotatie {#using-the-frametitle-annotation}

De `@FrameTitle` annotatie wordt op het class-niveau toegepast en stelt je in staat om een tekenreekswaarde op te geven die de titel van de pagina vertegenwoordigt. Wanneer de router navigeert naar een component met deze annotatie, wordt de opgegeven titel automatisch ingesteld voor het browservenster.

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
- De `@FrameTitle("Dashboard")` annotatie stelt de frame-titel in op "Dashboard".
- Wanneer de gebruiker navigeert naar `/dashboard`, wordt de titel van het frame automatisch bijgewerkt naar de opgegeven waarde.

Deze methode is nuttig voor routes die een statische titel hebben en geen frequente updates vereisen op basis van de context van de route.

:::tip `@AppTitle` en `@FrameTitle`  
Als de app-titel is ingesteld, zal de frame-titel deze incorporeren. Bijvoorbeeld, als de app de titel definieert als `@AppTitle("webforJ")` en de frame-titel is ingesteld als `@FrameTitle("Dashboard")`, dan zal de uiteindelijke paginatitel `Dashboard - webforJ` zijn. Je kunt het formaat van de uiteindelijke titel aanpassen in de `@AppTitle` annotatie door de `format`-attribuut te gebruiken indien nodig.  
:::

## Dynamische frame-titels {#dynamic-frame-titles}

In gevallen waar de frame-titel dynamisch moet veranderen op basis van de status van de app of routeparameters, biedt webforJ een interface genaamd `HasFrameTitle`. Deze interface stelt componenten in staat om een frame-titel te bieden op basis van de huidige navigatiecontext en routeparameters.

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
    // Dynamisch de frame-titel instellen met behulp van routeparameters
    String userId = parameters.get("id").orElse("Onbekend");
    return "Profiel - Gebruiker " + userId;
  }
}
```

In dit voorbeeld:
- De `ProfileView` component implementeert de `HasFrameTitle` interface.
- De `getFrameTitle()` methode genereert dynamisch een titel met behulp van de `id` parameter uit de URL.
- Als de route `/profile/123` is, wordt de titel bijgewerkt naar "Profiel - Gebruiker 123".

:::tip Combineren van annotaties en dynamische titels
Je kunt zowel statische als dynamische methoden combineren. Als een routecomponent zowel een `@FrameTitle` annotatie heeft als de `HasFrameTitle` interface implementeert, zal de dynamisch aangeboden titel van `getFrameTitle()` voorrang krijgen boven de statische waarde van de annotatie.
:::
