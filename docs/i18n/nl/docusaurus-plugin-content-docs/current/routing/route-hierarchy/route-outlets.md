---
sidebar_position: 3
title: Route Outlets
_i18n_hash: 8a64cd917fe9f1de3f37ee01254e80e7
---
Een **outlet** is een aangewezen component, hetzij een [route layout](./route-types#layout-routes) of een [route view](./route-types#view-routes), waar kindroutes dynamisch worden weergegeven. Het definieert waar de inhoud van de kindroute zal verschijnen binnen de ouderroute. Outlets zijn fundamenteel voor het creëren van modulaire, geneste UI's en flexibele navigatiestructuren.

## Definiëren van een outlet {#defining-an-outlet}

Outlets worden doorgaans geïmplementeerd met behulp van containercomponenten die kindinhoud kunnen vasthouden en beheren. In webforJ kan elke component die de `HasComponents` interface implementeert, of een samengesteld geheel van dergelijke componenten, als een outlet dienen. Bijvoorbeeld, [`FlexLayout`](../../components/flex-layout) implementeert de `HasComponents` interface, waardoor het een geldige outlet is voor kindroutes.

Als er geen outlet expliciet is gedefinieerd voor een route, wordt het eerste `Frame` van de app als de standaard outlet gebruikt. Dit gedrag zorgt ervoor dat elke kindroute een plek heeft om weergegeven te worden.

:::tip Frame Beheer
In toepassingen met meerdere frames kunt u specificeren welk frame moet worden gebruikt als de outlet voor kindroutes door de `frame` eigenschap in de `@Route` annotatie in te stellen. De `frame` eigenschap accepteert de naam van het frame dat moet worden gebruikt voor weergave.
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
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("Dashboard Inhoud"));
  }
}
```

In dit voorbeeld:

- `MainLayout` fungeert als de lay-outcontainer, maar aangezien er geen specifieke outlet is gedefinieerd, wordt de standaard `Frame` van de app gebruikt.
- De `DashboardView` wordt weergegeven binnen `MainLayout` met behulp van de standaard outlet (inhoudsgebied) van de `AppLayout`.

Kindroutes van `MainLayout` worden dus automatisch weergegeven in de inhoudsslot van `AppLayout`, tenzij een andere outlet of frame is gespecificeerd.

## Outlet levenscyclus {#outlet-lifecycle}

Outlets zijn nauw verbonden met de levenscyclus van routes. Wanneer de actieve route verandert, werkt de outlet zijn inhoud dynamisch bij door de juiste kindcomponent in te voegen en componenten die niet meer nodig zijn te verwijderen. Dit zorgt ervoor dat alleen de relevante weergaven op elk gegeven moment worden weergegeven.

- **Creatie**: Outlets worden geïnitialiseerd voordat kindcomponenten worden gemaakt.
- **Inhoudsinjectie**: Wanneer een kindroute wordt gematcht, wordt de bijbehorende component in de outlet geïnjecteerd.
- **Bijwerken**: Bij navigeren tussen routes, werkt de outlet zijn inhoud bij door de nieuwe kindcomponent in te voegen en verouderde componenten te verwijderen.

## Aangepaste outlets {#custom-outlets}

De `RouteOutlet` interface is verantwoordelijk voor het beheren van de levenscyclus van routecomponenten en bepaalt hoe componenten worden weergegeven en verwijderd. Elke component die deze interface implementeert, kan fungeren als een outlet voor andere componenten.

### Belangrijke methoden in `RouteOutlet`: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Verantwoordelijk voor het weergeven van de opgegeven component in de outlet. Dit wordt aangeroepen wanneer de router een route matcht en de kindcomponent moet worden weergegeven.
- **`removeRouteContent(Component component)`**: Behandelt het verwijderen van de component uit de outlet, meestal aangeroepen wanneer u van de huidige route navigeert.

Door `RouteOutlet` te implementeren, kunnen ontwikkelaars controleren hoe routes in specifieke gebieden van de app worden geïnjecteerd. Bijvoorbeeld

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  private final AppLayout self = getBoundComponent();

  @Override
  public void showRouteContent(Component component) {
    self.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    self.remove(component);
  }
}
```

In dit voorbeeld implementeert de `MainLayout` klasse de `RouteOutlet` interface, waarmee componenten dynamisch aan de lade van de AppLayout kunnen worden toegevoegd of verwijderd op basis van de routenavigatie in plaats van het standaard inhoudsgebied dat in de `AppLayout` component is gedefinieerd.

## Caching outletcomponenten {#caching-outlet-components}

Standaard voegen outlets dynamisch componenten toe en verwijderen ze bij het navigeren naar en weg van routes. In bepaalde gevallen—vooral voor weergaven met complexe componenten—kan het voordeliger zijn om de zichtbaarheid van componenten te toggelen in plaats van ze volledig uit de DOM te verwijderen. Dit is waar de `PersistentRouteOutlet` van pas komt, waarmee componenten in het geheugen kunnen blijven en eenvoudig kunnen worden verborgen of weergegeven, in plaats van dat ze worden vernietigd en opnieuw worden gemaakt.

De `PersistentRouteOutlet` cachet weergegeven componenten en houdt ze in het geheugen wanneer de gebruiker weg navigeert. Dit verbetert de prestaties door onnodige componentvernietiging en -hercreatie te vermijden, wat vooral voordelig is voor toepassingen waarin gebruikers vaak tussen weergaven schakelen.

### Hoe `PersistentRouteOutlet` werkt: {#how-persistentrouteoutlet-works}

- **Component Caching**: Het onderhoudt een in-memory cache van alle componenten die binnen de outlet zijn weergegeven.
- **Zichtbaarheids Toggling**: In plaats van componenten uit de DOM te verwijderen, verbergt het ze wanneer u weg navigeert van een route.
- **Component Herstel**: Wanneer de gebruiker terugnavigeert naar een eerder gecachte route, wordt de component eenvoudig weer weergegeven zonder dat recreatie nodig is.

Dit gedrag is bijzonder nuttig voor complexe UI's waarbij constante her-rendering van componenten de prestaties kan verlagen. Om deze zichtbaarheidstoggling te laten werken, moeten de beheerde componenten de `HasVisibility` interface implementeren, die de `PersistentRouteOutlet` in staat stelt hun zichtbaarheid te beheren.

:::tip Wanneer `PersistentRouteOutlet` te gebruiken
Gebruik `PersistentRouteOutlet` wanneer het maken en vernietigen van componenten frequent leidt tot prestatieproblemen in uw app. Het wordt over het algemeen aanbevolen om het standaardgedrag van het creëren en vernietigen van componenten tijdens route-overgangen toe te staan, aangezien dit helpt om mogelijke bugs en problemen met het handhaven van een consistente status te vermijden. Echter, in scenario's waar prestaties cruciaal zijn en componenten complex of duur zijn om opnieuw te maken, kan `PersistentRouteOutlet` aanzienlijke verbeteringen bieden door componenten te cachen en hun zichtbaarheid te beheren.
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

In dit voorbeeld gebruikt `MainLayout` `PersistentRouteOutlet` om zijn kindroutes te beheren. Bij het navigeren tussen routes worden componenten niet uit de DOM verwijderd, maar in plaats daarvan verborgen, zodat ze beschikbaar blijven voor snelle her-rendering wanneer de gebruiker terug navigeert. Deze benadering verbetert de prestaties aanzienlijk, vooral voor weergaven met complexe inhoud of zwaar bronnengebruik.
