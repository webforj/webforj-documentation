---
sidebar_position: 1
title: Route Types
_i18n_hash: d297703f4a165ebcfbb540c3256f825e
---
Routes zijn ingedeeld in twee hoofdtypen: **View Routes** en **Layout Routes**. De keuze van het routetype bepaalt hoe componenten aan URL's worden gekoppeld en hoe ze interactie hebben met andere delen van je app.

## View routes {#view-routes}

View routes staan direct in verbinding met een URL-segment en vertegenwoordigen specifieke pagina's in je app. Deze routes worden weerspiegeld in de URL van de browser en worden doorgaans gebruikt voor afzonderlijke weergaven of pagina's.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  public HomeView() {
    Div content = getBoundComponent();
    content.add(new H1("Home Pagina"));
  }
}
```

- **URL**: `/home`
- **Weergavede Component**: `HomeView`

In dit voorbeeld rendert het navigeren naar `/home` de `HomeView` component.

## Layout routes {#layout-routes}

Layout routes omringen kindweergaven zonder bij te dragen aan de URL. Layouts bieden gedeelde UI-elementen zoals kopteksten of zijbalken die consistent zijn over meerdere weergaven. Kindroutes worden weergegeven in het inhoudsgebied van de layout.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

In dit geval is `MainLayout` een layout route die om kindweergaven heen wikkelt. Het definieert gemeenschappelijke UI-elementen zoals een koptekst en een lade. Kindroutes die aan deze layout zijn gekoppeld, worden geïnjecteerd in het inhoudsgebied van de `AppLayout` component.

## Auto-detectie van routetypen {#auto-detection-of-route-types}

Standaard wordt het routetype automatisch gedetecteerd of de route een **view** of **layout** is op basis van de klassenaam:

- Klassen die eindigen op `Layout` worden behandeld als **layout routes**.
- Klassen die eindigen op `View` worden behandeld als **view routes**.

Optioneel kunnen ontwikkelaars handmatig het routetype specificeren door `Route.Type` in de `@Route` annotatie in te stellen.

```java
// Automatisch gedetecteerd als Layout
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

```java
// Automatisch gedetecteerd als View
@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    Div content = getBoundComponent();
    content.add(new H1("Dashboard Inhoud"));
  }
}
```
