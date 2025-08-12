---
sidebar_position: 3
title: Route Outlets
_i18n_hash: bab7ef02dabbb653741f7c8176913213
---
Een **outlet** is een aangewezen component, ofwel een [route lay-out](./route-types#layout-routes) of een [route weergave](./route-types#view-routes), waar kindroutes dynamisch worden weergegeven. Het definieert waar de inhoud van de kindroute zal verschijnen binnen de ouderroute. Outlets zijn fundamenteel voor het creëren van modulaire, geneste gebruikersinterfaces en flexibele navigatiestructuren.

## Een outlet definiëren {#defining-an-outlet}

Outlets worden doorgaans geïmplementeerd met behulp van containercomponenten die kindinhoud kunnen vasthouden en beheren. In webforJ kan elke component die de `HasComponents` interface implementeert, of een samengesteld geheel van dergelijke componenten, dienen als een outlet. Bijvoorbeeld, [`FlexLayout`](../../components/flex-layout) implementeert de `HasComponents` interface, waardoor het een geldige outlet is voor kindroutes.

Als er geen outlet expliciet is gedefinieerd voor een route, wordt het eerste `Frame` van de app gebruikt als de standaard outlet. Dit gedrag zorgt ervoor dat elke kindroute een plaats heeft om weergegeven te worden.

:::tip Frame Beheer
In toepassingen met meerdere frames, kunt u specificeren welk frame als de outlet voor kindroutes moet worden gebruikt door de `frame`-eigenschap in de `@Route` annotatie in te stellen. De `frame`-eigenschap accepteert de naam van het frame dat moet worden gebruikt voor weergave.
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

- `MainLayout` fungeert als de lay-outcontainer, maar aangezien er geen specifieke outlet is gedefinieerd, wordt de standaard `Frame` van de app gebruikt.
- De `DashboardView` wordt weergegeven binnen `MainLayout` met behulp van de standaard outlet (inhoudsgebied) van de `AppLayout`.

Kindroutes van `MainLayout` zullen automatisch worden weergegeven in de inhoudsslot van `AppLayout`, tenzij een andere outlet of frame is gespecificeerd.

## Outlet levenscyclus {#outlet-lifecycle}

Outlets zijn nauw verbonden met de levenscyclus van routes. Wanneer de actieve route verandert, werkt de outlet zijn inhoud dynamisch bij door de juiste kindcomponent in te voegen en componenten die niet langer nodig zijn te verwijderen. Dit zorgt ervoor dat alleen de relevante weergaven op elk gegeven moment worden weergegeven.

- **Creatie**: Outlets worden geïnitialiseerd voordat kindcomponenten worden aangemaakt.
- **Inhoud Invoegen**: Wanneer een kindroute is gematcht, wordt de component in de outlet geïnjecteerd.
- **Bijwerken**: Bij het navigeren tussen routes, werkt de outlet zijn inhoud bij, waarbij de nieuwe kindcomponent wordt ingevoegd en verouderde componenten worden verwijderd.

## Aangepaste outlets {#custom-outlets}

De `RouteOutlet` interface is verantwoordelijk voor het beheren van de levenscyclus van routecomponenten, en bepaalt hoe componenten worden weergegeven en verwijderd. Elke component die deze interface implementeert, kan als een outlet voor andere componenten fungeren.

### Belangrijke methoden in `RouteOutlet`: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Verantwoordelijk voor het weergeven van de opgegeven component in de outlet. Dit wordt aangeroepen wanneer de router een route matcht en de kindcomponent moet worden weergegeven.
- **`removeRouteContent(Component component)`**: Behandelt het verwijderen van de component uit de outlet, meestal aangeroepen bij het navigeren weg van de huidige route.

Door `RouteOutlet` te implementeren, kunnen ontwikkelaars bepalen hoe routes in specifieke gebieden van de app worden geïnjecteerd. bijvoorbeeld

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

In dit voorbeeld implementeert de `MainLayout` klasse de `RouteOutlet` interface, waardoor componenten dynamisch aan of verwijderd kunnen worden uit de lade van de AppLayout op basis van de routenavigatie in plaats van het standaard inhoudsgebied dat is gedefinieerd in de `AppLayout` component.

## Caching van outletcomponenten {#caching-outlet-components}

Standaard voegen outlets dynamisch componenten toe en verwijderen ze deze bij het navigeren naar en van routes. In bepaalde gevallen—bijvoorbeeld voor weergaven met complexe componenten—kan het echter beter zijn om de zichtbaarheid van componenten te toggelen in plaats van ze volledig uit de DOM te verwijderen. Dit is waar de `PersistentRouteOutlet` in het spel komt, waardoor componenten in het geheugen blijven en simpelweg verborgen of getoond worden, in plaats van vernietigd en opnieuw aangemaakt te worden.

De `PersistentRouteOutlet` cachet weergegeven componenten, waardoor ze in het geheugen blijven wanneer de gebruiker wegnavigeert. Dit verbetert de prestaties door onnodige vernietiging en recreatie van componenten te vermijden, wat vooral voordelig is voor toepassingen waarbij gebruikers vaak tussen weergaven wisselen.

### Hoe `PersistentRouteOutlet` werkt: {#how-persistentrouteoutlet-works}

- **Component Caching**: Het houdt een in-memory cache bij van alle componenten die binnen de outlet zijn weergegeven.
- **Zichtbaarheid Toggle**: In plaats van componenten uit de DOM te verwijderen, verbergt het deze wanneer er weggenavigeerd wordt van een route.
- **Component Herstel**: Wanneer de gebruiker terugnavigeert naar een eerder gecachet route, wordt de component simpelweg opnieuw weergegeven zonder de noodzaak voor recreatie.

Dit gedrag is bijzonder nuttig voor complexe gebruikersinterfaces waar constant opnieuw weergeven van componenten de prestaties kan verslechteren. Om deze toggling van zichtbaarheid mogelijk te maken, moeten de beheerde componenten de `HasVisibility` interface implementeren, waardoor de `PersistentRouteOutlet` hun zichtbaarheid kan beheren.

:::tip Wanneer `PersistentRouteOutlet` te gebruiken
Gebruik `PersistentRouteOutlet` wanneer het frequent creëren en vernietigen van componenten leidt tot prestatieproblemen in uw app. Het wordt over het algemeen aanbevolen om het standaardgedrag van het creëren en vernietigen van componenten tijdens route-overgangen toe te staan, omdat dit helpt om potentiële bugs en problemen met het behouden van een consistente staat te vermijden. In scenario's waar prestaties cruciaal zijn en componenten complex of kostbaar zijn om opnieuw te creëren, kan `PersistentRouteOutlet` aanzienlijke verbeteringen bieden door componenten te cache en hun zichtbaarheid te beheren.
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

In dit voorbeeld gebruikt `MainLayout` `PersistentRouteOutlet` om zijn kindroutes te beheren. Bij het navigeren tussen routes worden componenten niet uit de DOM verwijderd maar verborgen, zodat ze beschikbaar blijven voor snelle herweergave wanneer de gebruiker terugnavigeert. Deze aanpak verbetert de prestaties aanzienlijk, vooral voor weergaven met complexe inhoud of zwaar gebruik van middelen.
