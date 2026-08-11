---
sidebar_position: 10
title: Navigational Frame Titles
description: >-
  Set browser frame titles per route with the @FrameTitle annotation or generate
  them dynamically using HasFrameTitle.
_i18n_hash: 7b190f89d8eeb58df6d8a25ce863cc5e
---
In webforJ worden alle routes weergegeven binnen een Frame, dat dient als een bovenliggende container die verantwoordelijk is voor het weergeven van de inhoud van de huidige route. Terwijl gebruikers tussen verschillende routes navigeren, wordt de Frame Title dynamisch bijgewerkt om de actieve weergave weer te geven, wat helpt om duidelijke context te bieden over de huidige locatie van de gebruiker binnen de app.

De titel van een frame kan statisch worden ingesteld met behulp van annotaties of dynamisch via code tijdens runtime. Deze flexibele benadering stelt ontwikkelaars in staat om titels te definiëren die aansluiten bij het doel van elke weergave, terwijl deze ook kunnen worden aangepast aan specifieke scenario's of parameters indien nodig.

## Frame-titel met annotaties {#frame-title-with-annotations}

De eenvoudigste manier om de titel van een frame in weergave in te stellen, is door de `@FrameTitle` annotatie te gebruiken. Deze annotatie stelt je in staat om een statische titel voor een routecomponent te definiëren, die vervolgens op het frame wordt toegepast wanneer de component wordt weergegeven.

### De `@FrameTitle` annotatie gebruiken {#using-the-frametitle-annotation}

De `@FrameTitle` annotatie wordt op het klasse-niveau toegepast en stelt je in staat om een tekenreekswaarde op te geven die de titel van de pagina vertegenwoordigt. Wanneer de router naar een component met deze annotatie navigeert, zal de opgegeven titel automatisch worden ingesteld voor het browservenster.

Hier is een voorbeeld:

```java
@Route
@FrameTitle("Dashboard")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // weergavelogica
  }
}
```

In dit voorbeeld:
- De `DashboardView` klasse is geannoteerd met `@Route` om de route te definiëren.
- De `@FrameTitle("Dashboard")` annotatie stelt de frame-titel in op "Dashboard".
- Wanneer de gebruiker naar `/dashboard` navigeert, wordt de titel van het frame automatisch bijgewerkt naar de opgegeven waarde.

Deze methode is nuttig voor routes die een statische titel hebben en niet regelmatig updates vereisen op basis van de context van de route.

:::tip `@AppTitle` en `@FrameTitle`
Als de app-titel is ingesteld, zal de frame-titel deze opnemen. Bijvoorbeeld, als de app de titel definieert als `@AppTitle("webforJ")` en de frame-titel is ingesteld als `@FrameTitle("Dashboard")`, dan wordt de uiteindelijke paginatitel `Dashboard - webforJ`. Je kunt het formaat van de uiteindelijke titel aanpassen in de `@AppTitle` annotatie met behulp van de `format` eigenschap indien nodig.
:::

## Dynamische frame-titels {#dynamic-frame-titles}

In gevallen waar de frame-titel dynamisch moet veranderen op basis van de staat van de app of routeparameters, biedt webforJ een interface genaamd `HasFrameTitle`. Deze interface maakt het mogelijk voor componenten om een frame-titel te bieden op basis van de huidige navigatiecontext en routeparameters.

### De `HasFrameTitle` interface implementeren {#implementing-the-hasframetitle-interface}

De `HasFrameTitle` interface bevat een enkele methode `getFrameTitle()`, die wordt aangeroepen voordat de titel van het frame wordt bijgewerkt. Deze methode biedt de flexibiliteit om een titel dynamisch te genereren op basis van de navigatiecontext of andere dynamische factoren.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {
  private final Div self = getBoundComponent();

  public ProfileView() {
    self.add(new H1("Profielpagina"));
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

:::tip Het combineren van annotaties en dynamische titels
Je kunt zowel statische als dynamische methoden combineren. Als een routecomponent zowel een `@FrameTitle` annotatie heeft als de `HasFrameTitle` interface implementeert, krijgt de dynamisch opgegeven titel van `getFrameTitle()` voorrang boven de statische waarde van de annotatie.
:::
