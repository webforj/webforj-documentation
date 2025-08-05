---
title: Integrating the AppLayout
sidebar_position: 6
draft: true
_i18n_hash: fd2f3844bbcb102adf05ae01b07ff8d8
---
In deze stap integreer je de functies die in voorgaande stappen zijn geïmplementeerd, zoals routing en weergaven, in een samenhangende app-indeling. Deze structuur biedt een uniforme navigatiesysteem en dynamische inhoudsgebieden.

## Doel van de app-indeling {#purpose-of-the-app-layout}

De `AppLayout` dient als de basis voor het beheren van de algehele structuur en stroom van je app. Het biedt:
- **Globale Navigatie**: Een consistente manier om tussen de belangrijkste secties te schakelen.
- **Dynamische Inhoudsweergave**: Een gecentraliseerde indeling voor het weergeven van gerouteerde weergaven.

## Gebruik van `AppNav` {#using-appnav}

De `AppNav` component wordt gebruikt om een navigatiemenu binnen de gebruikersinterface van de app te creëren. Dit menu biedt links naar verschillende weergaven in je app, zoals de `DemoView`:

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
- Elk menu-item is een `AppNavItem` dat specificeert:
  - Het label, bijvoorbeeld "Dashboard."
  - De doelweergave, bijvoorbeeld `DemoView`.
  - Een optioneel pictogram, bijvoorbeeld een kolommenpictogram.

## Indelingsroutes en outlets {#layout-routes-and-outlets}

De indeling gebruikt routes en outlets om dynamisch inhoud weer te geven binnen een gestructureerde indeling. In webforJ:
- **Routes** definiëren hoe weergaven zijn gekoppeld aan specifieke paden.
- **Outlets** fungeren als tijdelijke plekken in indelingen waar de gerouteerde weergaven worden weergegeven.

### Voorbeeld: Het instellen van een indelingsroute {#example-setting-up-a-layout-route}

In de `MainLayout` klasse definieert de `@Route` annotatie deze als de basisindeling, en de `DemoView` wordt door een outlet binnen deze indeling weergegeven:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

De `@Route` annotatie voor `DemoView` specificeert dat deze `MainLayout` als zijn outlet gebruikt:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
    // DemoView logica
}
```

## Dynamische inhoud toevoegen met `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

Een `RouteOutlet` toont dynamisch weergaven op basis van de actieve route. In de indeling worden weergaven zoals `DemoView` weergegeven via de `RouteOutlet`. Terwijl de `RouteOutlet` impliciet wordt behandeld door de outlet-specificatie in de route-annotaties.
