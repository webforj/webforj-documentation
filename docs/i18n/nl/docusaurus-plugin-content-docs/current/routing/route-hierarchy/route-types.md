---
sidebar_position: 1
title: Route Types
_i18n_hash: ff067ccd8461640c772c1f8fa0dcc856
---
Routes worden ingedeeld in twee hoofdtypen, **View Routes** en **Layout Routes**. De keuze voor het type route bepaalt hoe componenten aan URL's worden toegewezen en hoe ze interageren met andere delen van je app.

## View routes {#view-routes}

View routes zijn direct gekoppeld aan een URL-segment en vertegenwoordigen specifieke pagina's in je app. Deze routes worden weerspiegeld in de URL van de browser en worden doorgaans gebruikt voor afzonderlijke weergaven of pagina's.

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
- **Gerenderde Component**: `HomeView`

In dit voorbeeld rendert navigeren naar `/home` de `HomeView` component.

## Layout routes {#layout-routes}

Layout routes omhullen kindweergaven zonder bij te dragen aan de URL. Layouts bieden gedeelde UI-elementen zoals kopteksten of zijbalken die consistent zijn over meerdere weergaven. Kindroutes worden binnen het inhoudsgebied van de lay-out gerenderd.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

In dit geval is `MainLayout` een layout route die kindweergaven omhult. Het definieert gemeenschappelijke UI-elementen zoals een koptekst en lade. Kindroutes die aan deze lay-out zijn gekoppeld, worden geïnjecteerd in het inhoudsgebied van de `AppLayout` component.

## Auto-detectie van routetypes {#auto-detection-of-route-types}

Standaard wordt het routetype automatisch gedetecteerd, of de route nu een **view** of **layout** is, op basis van de class-naam:

- Klassen die eindigen op `Layout` worden behandeld als **layout routes**.
- Klassen die eindigen op `View` worden behandeld als **view routes**.

Als alternatief kunnen ontwikkelaars handmatig het routetype specificeren door `Route.Type` in de `@Route` annotatie in te stellen.

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
