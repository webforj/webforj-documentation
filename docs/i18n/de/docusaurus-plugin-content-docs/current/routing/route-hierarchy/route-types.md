---
sidebar_position: 1
title: Route Types
_i18n_hash: 75cb67715544b94ca99fc81c736ebcc7
---
Routen werden in zwei Haupttypen unterteilt: **Ansichts-Routen** und **Layout-Routen**. Die Wahl des Routentyps bestimmt, wie Komponenten den URLs zugeordnet werden und wie sie mit anderen Teilen Ihrer App interagieren.

## Ansichts-Routen {#view-routes}

Ansichts-Routen werden direkt einem URL-Segment zugeordnet und repräsentieren spezifische Seiten in Ihrer App. Diese Routen spiegeln sich in der URL des Browsers wider und werden typischerweise für unterschiedliche Ansichten oder Seiten verwendet.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public HomeView() {
    self.add(new H1("Startseite"));
  }
}
```

- **URL**: `/home`
- **Gerenderte Komponente**: `HomeView`

In diesem Beispiel rendert das Navigieren zu `/home` die Komponente `HomeView`.

## Layout-Routen {#layout-routes}

Layout-Routen umschließen untergeordnete Ansichten, ohne zur URL beizutragen. Layouts bieten gemeinsame UI-Elemente wie Köpfe oder Seitenleisten, die in mehreren Ansichten konsistent sind. Untergeordnete Routen werden im Inhaltsbereich des Layouts gerendert.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

In diesem Fall ist `MainLayout` eine Layout-Route, die um untergeordnete Ansichten herum gewickelt ist. Es definiert gemeinsame UI-Elemente wie einen Kopf und eine Schublade. Untergeordnete Routen, die mit diesem Layout verbunden sind, werden in den Inhaltsbereich der Komponente `AppLayout` injiziert.

## Automatische Erkennung der Routentypen {#auto-detection-of-route-types}

Standardmäßig wird der Routentyp automatisch erkannt, ob es sich um eine **Ansicht** oder **Layout** handelt, basierend auf dem Klassennamen:

- Klassen, die auf `Layout` enden, werden als **Layout-Routen** behandelt.
- Klassen, die auf `View` enden, werden als **Ansichts-Routen** behandelt.

Alternativ können Entwickler den Routentyp manuell angeben, indem sie `Route.Type` in der `@Route`-Annotation festlegen.

```java
// Automatisch als Layout erkannt
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

```java
// Automatisch als Ansicht erkannt
@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("Inhalt des Dashboards"));
  }
}
```
