---
title: Integrating the AppLayout
sidebar_position: 7
draft: true
description: Step 6 - Use the AppLayout component.
_i18n_hash: fece87dce53e7e41102e122c740f6ea8
---
In deze stap ga je de functies die in de vorige stappen zijn geïmplementeerd, zoals routering en weergaven, integreren in een samenhangende app-indeling. Deze structuur biedt een verenigd navigatiesysteem en dynamische inhoudsgebieden.

## De app uitvoeren {#running-the-app}

Terwijl je je app ontwikkelt, kun je [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) als vergelijking gebruiken. Om de app in actie te zien:

1. Navigeer naar de hoofdmap die het `pom.xml`-bestand bevat, dit is `6-integrating-an-app-layout` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

De app wordt automatisch geopend in een nieuwe browser op `http://localhost:8080`.

## Doel van de app-indeling {#purpose-of-the-app-layout}

De `AppLayout` dient als de basis voor het beheren van de algehele structuur en flow van je app. Het biedt:
- **Globale Navigatie**: Een consistente manier om tussen belangrijke secties te schakelen.
- **Dynamische Inhoud Weergave**: Een gecentraliseerde indeling voor het weergeven van gerouteerde weergaven.

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
  - Een optioneel pictogram, bijvoorbeeld een pictogram van kolommen.

## Indelingsroutes en uitgangen {#layout-routes-and-outlets}

De indeling gebruikt routes en uitgangen om dynamisch inhoud binnen een gestructureerde indeling weer te geven. In webforJ:
- **Routes** definiëren hoe weergaven aan specifieke paden zijn gekoppeld.
- **Uitgangen** fungeren als plaatsaanduidingen in indelingen waar de gerouteerde weergaven worden weergegeven.

### Voorbeeld: Een indelingsroute instellen {#example-setting-up-a-layout-route}

In de `MainLayout`-klasse definieert de `@Route`-annotatie deze als de basisindeling, en de `DemoView` wordt weergegeven via een uitgang in deze indeling:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

De `@Route`-annotatie voor `DemoView` specificeert dat deze `MainLayout` als uitgang gebruikt:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
    // DemoView-logica
}
```

## Dynamische inhoud toevoegen met `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

Een `RouteOutlet` geeft dynamisch weergaven weer op basis van de actieve route. In de indeling worden weergaven zoals `DemoView` weergegeven via de `RouteOutlet`. Terwijl de `RouteOutlet` impliciet wordt afgehandeld door de uitgangspecificatie in de route-annotaties.
