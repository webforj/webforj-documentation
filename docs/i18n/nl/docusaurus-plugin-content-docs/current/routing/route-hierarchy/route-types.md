---
sidebar_position: 1
title: Route Types
_i18n_hash: 75cb67715544b94ca99fc81c736ebcc7
---
Routes zijn ingedeeld in twee hoofdtypen, **View Routes** en **Layout Routes**. De keuze van het roettype bepaalt hoe componenten aan URL's worden gekoppeld en hoe ze interactie hebben met andere delen van uw app.

## View routes {#view-routes}

View routes worden direct aan een URL-segment gekoppeld en vertegenwoordigen specifieke pagina's in uw app. Deze routes worden weerspiegeld in de URL van de browser en worden doorgaans gebruikt voor afzonderlijke weergaven of pagina's.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public HomeView() {
    self.add(new H1("Home Pagina"));
  }
}
```

- **URL**: `/home`
- **Gerenderde Component**: `HomeView`

In dit voorbeeld, het navigeren naar `/home` rendert de `HomeView` component.

## Layout routes {#layout-routes}

Layout routes omhullen kinderweergaven zonder bij te dragen aan de URL. Layouts bieden gedeelde UI-elementen zoals kopteksten of zijbalken die consistent zijn over meerdere weergaven. Kinderoutes worden gerenderd binnen het inhoudsgebied van de layout.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

In dit geval is `MainLayout` een layout route die om kinderweergaven heen is gewikkeld. Het definieert gemeenschappelijke UI-elementen zoals een koptekst en lade. Kinderoutes die aan deze layout zijn gekoppeld, worden geïnjecteerd in het inhoudsgebied van de `AppLayout` component.

## Auto-detectie van roettypen {#auto-detection-of-route-types}

Standaard wordt het roettype automatisch gedetecteerd of de route een **view** of **layout** is op basis van de klassenaam:

- Klassen eindigend op `Layout` worden behandeld als **layout routes**.
- Klassen eindigend op `View` worden behandeld als **view routes**.

Als alternatief kunnen ontwikkelaars handmatig het roettype specificeren door `Route.Type` in de `@Route` annotatie in te stellen.

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
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("Dashboard Inhoud"));
  }
}
```
