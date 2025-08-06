---
sidebar_position: 3
title: Route Outlets
_i18n_hash: 1871c92c77115c99444f1d7a0c20aed9
---
Een **outlet** is een aangewezen component, hetzij een [route-indeling](./route-types#layout-routes) of een [route-weergave](./route-types#view-routes), waar kindroutes dynamisch worden weergegeven. Het definieert waar de inhoud van de kindroute zal verschijnen binnen de bovenliggende route. Outlets zijn fundamenteel voor het creëren van modulaire, geneste gebruikersinterfaces en flexibele navigatiestructuren.

## Een outlet definiëren {#defining-an-outlet}

Outlets worden doorgaans geïmplementeerd met behulp van containercomponenten die kindinhoud kunnen vasthouden en beheren. In webforJ kan elke component die de `HasComponents` interface implementeert, of een samengestelde van dergelijke componenten, dienen als een outlet. Bijvoorbeeld, [`FlexLayout`](../../components/flex-layout) implementeert de `HasComponents` interface, waardoor het een geldige outlet voor kindroutes is.

Als er geen outlet expliciet is gedefinieerd voor een route, wordt het eerste `Frame` van de app gebruikt als de standaard outlet. Dit gedrag zorgt ervoor dat elke kindroute een plek heeft om te worden weergegeven.

:::tip Frame-beheer
In applicaties met meerdere frames kunt u specificeren welke frame moet worden gebruikt als de outlet voor kindroutes door de `frame`-attribuut in de `@Route` annotatie in te stellen. Het `frame`-attribuut accepteert de naam van het frame dat moet worden gebruikt voor weergave.
:::

### Voorbeeld: {#example}

```java
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}

@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    getBoundComponent().add(new H1("Dashboard Inhoud"));
  }
}
```

In dit voorbeeld:

- `MainLayout` fungeert als de lay-outcontainer, maar aangezien er geen specifieke outlet is gedefinieerd, wordt het standaard `Frame` van de app gebruikt.
- De `DashboardView` wordt weergegeven binnen `MainLayout` met behulp van de standaard outlet (inhoudsgebied) van de `AppLayout`.

Daarom worden kindroutes van `MainLayout` automatisch weergegeven in de inhoudsslot van `AppLayout`, tenzij een andere outlet of frame is gedefinieerd.

## Outlet levenscyclus {#outlet-lifecycle}

Outlets zijn nauw verbonden met de levenscyclus van routes. Wanneer de actieve route verandert, werkt de outlet zijn inhoud dynamisch bij door de juiste kindcomponent in te voegen en componenten die niet meer nodig zijn te verwijderen. Dit zorgt ervoor dat alleen de relevante weergaven op elk gegeven moment worden weergegeven.

- **Creëren**: Outlets worden geïnitialiseerd voordat kindcomponenten worden gemaakt.
- **Inhoud Invoegen**: Wanneer een kindroute overeenkomt, wordt zijn component in de outlet ingevoegd.
- **Bijwerken**: Tijdens navigatie tussen routes werkt de outlet zijn inhoud bij, voegt de nieuwe kindcomponent in en verwijdert verouderde componenten.

## Aangepaste outlets {#custom-outlets}

De `RouteOutlet` interface is verantwoordelijk voor het beheren van de levenscyclus van routecomponenten, en bepaalt hoe componenten worden weergegeven en verwijderd. Elke component die deze interface implementeert, kan fungeren als een outlet voor andere componenten.

### Sleutelmethoden in `RouteOutlet`: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Verantwoordelijk voor het weergeven van de opgegeven component in de outlet. Dit wordt aangeroepen wanneer de router een route overeenkomt en de kindcomponent moet worden weergegeven.
- **`removeRouteContent(Component component)`**: Behandelt de verwijdering van de component uit de outlet, meestal aangeroepen bij navigeren weg van de huidige route.

Door `RouteOutlet` te implementeren, kunnen ontwikkelaars controleren hoe routes worden geïnjecteerd in specifieke gebieden van de app. Bijvoorbeeld

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {

  @Override
  public void showRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.remove(component);
  }
}
```

In dit voorbeeld implementeert de `MainLayout` klasse de `RouteOutlet` interface, waardoor componenten dynamisch aan de lades van de AppLayout kunnen worden toegevoegd of verwijderd op basis van de route-navigatie in plaats van het standaard inhoudsgebied dat is gedefinieerd in de `AppLayout` component.

## Caching van outletcomponenten {#caching-outlet-components}

Standaard voegen outlets dynamisch componenten toe en verwijderen ze deze bij navigeren naar en weg van routes. Echter, in bepaalde gevallen—bijzonder voor weergaven met complexe componenten—kan het beter zijn om de zichtbaarheid van componenten in te schakelen in plaats van ze volledig uit de DOM te verwijderen. Hier komt de `PersistentRouteOutlet` in het spel, waardoor componenten in het geheugen blijven en eenvoudig verborgen of getoond kunnen worden, in plaats van vernietigd en opnieuw gemaakt te worden.

De `PersistentRouteOutlet` cached weergegeven componenten, houdt ze in het geheugen wanneer de gebruiker weg navigeert. Dit verbetert de prestaties door onnodige vernietiging en recreatie van componenten te vermijden, wat vooral voordelig is voor applicaties waar gebruikers vaak tussen weergaven schakelen.

### Hoe `PersistentRouteOutlet` werkt: {#how-persistentrouteoutlet-works}

- **Component Caching**: Het houdt een in-memory cache bij van alle componenten die binnen de outlet zijn weergegeven.
- **Zichtbaarheid Inschakelen**: In plaats van componenten uit de DOM te verwijderen, verbergt het ze bij het navigeren weg van een route.
- **Component Herstel**: Wanneer de gebruiker terug navigeert naar een eerder gecachete route, wordt de component eenvoudig opnieuw getoond zonder recreatie.

Dit gedrag is bijzonder nuttig voor complexe gebruikersinterfaces waar constante hertekening van componenten de prestaties kan verminderen. Echter, om dit toggelen van zichtbaarheid te laten werken, moeten de beheerde componenten de `HasVisibility` interface implementeren, die de `PersistentRouteOutlet` in staat stelt hun zichtbaarheid te beheren.

:::tip Wanneer `PersistentRouteOutlet` te gebruiken
Gebruik `PersistentRouteOutlet` wanneer het creëren en vernietigen van componenten vaak leidt tot prestatiedrempels in uw app. Het wordt over het algemeen aanbevolen om het standaardgedrag van creëren en vernietigen van componenten tijdens route-overgangen toe te staan, aangezien dit helpt om potentiële bugs en problemen met het behouden van een consistente toestand te vermijden. Echter, in scenario's waar prestaties kritiek zijn en componenten complex of duur zijn om opnieuw te creëren, kan `PersistentRouteOutlet` significante verbeteringen bieden door componenten te cachen en hun zichtbaarheid te beheren.
:::

### Voorbeeld van `PersistentRouteOutlet` implementatie: {#example-of-persistentrouteoutlet-implementation}

```java
@Route
public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  PersistentRouteOutlet outlet = new PersistentRouteOutlet(this);

  public MainLayout() {
    setHeader();
    setDrawer();
  }

  @Override
  public void removeRouteContent(Component component) {
    outlet.removeRouteContent(component);
  }

  @Override
  public void showRouteContent(Component component) {
    outlet.showRouteContent(component);
  }
}
```

In dit voorbeeld gebruikt `MainLayout` de `PersistentRouteOutlet` om zijn kindroutes te beheren. Bij navigatie tussen routes worden componenten niet uit de DOM verwijderd, maar in plaats daarvan verborgen, zodat ze beschikbaar blijven voor snelle hertekening wanneer de gebruiker terug navigeert. Deze benadering verbetert de prestaties aanzienlijk, vooral voor weergaven met complexe inhoud of zwaar resourcegebruik.
