---
title: Integrating the AppLayout
sidebar_position: 7
draft: true
description: Step 6 - Use the AppLayout component.
_i18n_hash: 14b409262af6d7a8a25a67278f687250
---
In deze stap integreer je de functies die in eerdere stappen zijn geïmplementeerd, zoals routering en weergaven, in een samenhangende app-indeling. Deze structuur biedt een uniforme navigatiesysteem en dynamische inhoudsgebieden.

## De app uitvoeren {#running-the-app}

Tijdens het ontwikkelen van je app kun je [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) als vergelijking gebruiken. Om de app in actie te zien:

1. Navigeer naar de bovenliggende directory die het `pom.xml`-bestand bevat, dit is `6-integrating-an-app-layout` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

De app wordt automatisch geopend in een nieuwe browser op `http://localhost:8080`.

## Doel van de app-indeling {#purpose-of-the-app-layout}

De `AppLayout` dient als de basis voor het beheren van de algehele structuur en flow van je app. Het biedt:
- **Globale Navigatie**: Een consistente manier om tussen belangrijke secties te schakelen.
- **Dynamische Inhoudsweergave**: Een gecentraliseerde indeling voor het weergeven van gerouteerde weergaven.

## Gebruik van `AppNav` {#using-appnav}

De `AppNav`-component wordt gebruikt om een navigatiemenu binnen de UI van de app te creëren. Dit menu biedt links naar verschillende weergaven in je app, zoals de `DemoView`:

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Dashboard", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

In dit voorbeeld:
- Het navigatiemenu wordt toegevoegd aan de lade van de app.
- Elk menu-item is een `AppNavItem` die specificeert:
  - Het label, bijvoorbeeld "Dashboard."
  - De doelweergave, bijvoorbeeld `DemoView`.
  - Een optioneel pictogram, bijvoorbeeld een kolommenpictogram.

## Indelingsroutes en outlets {#layout-routes-and-outlets}

De indeling maakt gebruik van routes en outlets om dynamisch inhoud binnen een gestructureerde indeling weer te geven. In webforJ:
- **Routes** definiëren hoe weergaven zich naar specifieke paden verhouden.
- **Outlets** fungeren als tijdelijke plaatsen in indelingen waar de gerouteerde weergaven worden weergegeven.

### Voorbeeld: Een indelingsroute instellen {#example-setting-up-a-layout-route}

In de `MainLayout`-klasse definieert de `@Route`-annotatie het als de basisindeling, en de `DemoView` wordt weergegeven via een outlet in deze indeling:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

De `@Route`-annotatie voor `DemoView` specificeert dat deze `MainLayout` als outlet gebruikt:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView-logica
}
```

## Dynamische inhoud toevoegen met `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

Een `RouteOutlet` geeft dynamisch weergaven weer op basis van de actieve route. In de indeling worden weergaven zoals `DemoView` weergegeven via de `RouteOutlet`. Terwijl de `RouteOutlet` impliciet wordt afgehandeld door de outlet-specificatie in de route-annotaties.
