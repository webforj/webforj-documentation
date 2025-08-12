---
title: Integrating the AppLayout
sidebar_position: 6
draft: true
_i18n_hash: c0ed4864dc99a4665aef3f4ff808bc9d
---
In deze stap integreer je de functies die in de vorige stappen zijn geïmplementeerd, zoals routing en weergaven, in een samenhangende app-layout. Deze structuur biedt een verenigd navigatiesysteem en dynamische inhoudsgebieden.

## Doel van de app-layout {#purpose-of-the-app-layout}

De `AppLayout` dient als de basis voor het beheren van de algehele structuur en flow van je app. Het biedt:
- **Wereldwijde Navigatie**: Een consistente manier om tussen belangrijke secties te schakelen.
- **Dynamische Inhoudsweergave**: Een gecentraliseerde lay-out voor het weergeven van gerouteerde weergaven.

## Gebruik van `AppNav` {#using-appnav}

De `AppNav`-component wordt gebruikt om een navigatiemenu binnen de gebruikersinterface van de app te maken. Dit menu biedt links naar verschillende weergaven in je app, zoals de `DemoView`:

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Dashboard", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

In dit voorbeeld:
- Het navigatiemenu wordt aan de lade van de app toegevoegd.
- Elk menu-item is een `AppNavItem` dat specificeert:
  - Het label, bijvoorbeeld "Dashboard."
  - De doelweergave, bijvoorbeeld `DemoView`.
  - Een optioneel pictogram, bijvoorbeeld een kolompictogram.

## Layout-routes en uitgangen {#layout-routes-and-outlets}

De layout gebruikt routes en uitgangen om dynamisch inhoud binnen een gestructureerde lay-out weer te geven. In webforJ:
- **Routes** definiëren hoe weergaven aan specifieke paden zijn gekoppeld.
- **Uitgangen** fungeren als plaatsaanduidingen in lay-outs waar de gerouteerde weergaven worden weergegeven.

### Voorbeeld: Een layoutroute instellen {#example-setting-up-a-layout-route}

In de `MainLayout`-klasse definieert de `@Route`-annotatie deze als de basislay-out, en de `DemoView` wordt weergegeven via een uitgang in deze lay-out:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

De `@Route`-annotatie voor `DemoView` specificeert dat het `MainLayout` als zijn uitgang gebruikt:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
    // DemoView-logica
}
```

## Dynamische inhoud toevoegen met `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

Een `RouteOutlet` toont dynamisch weergaven op basis van de actieve route. In de lay-out worden weergaven zoals `DemoView` weergegeven via de `RouteOutlet`. Terwijl de `RouteOutlet` impliciet wordt behandeld door de uitgangspecificatie in de route-annotaties.
