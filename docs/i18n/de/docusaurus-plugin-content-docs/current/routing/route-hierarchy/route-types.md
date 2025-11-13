---
sidebar_position: 1
title: Route-Typen
_i18n_hash: ff067ccd8461640c772c1f8fa0dcc856
---
Routen werden in zwei Haupttypen unterteilt, **Ansichts-Routen** und **Layout-Routen**. Die Wahl des Routentyps bestimmt, wie Komponenten den URLs zugeordnet werden und wie sie mit anderen Teilen Ihrer App interagieren.

## Ansichts-Routen {#view-routes}

Ansichts-Routen werden direkt einem URL-Segment zugeordnet und repräsentieren spezifische Seiten in Ihrer App. Diese Routen spiegeln sich in der URL des Browsers wider und werden typischerweise für verschiedene Ansichten oder Seiten verwendet.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  public HomeView() {
    Div content = getBoundComponent();
    content.add(new H1("Startseite"));
  }
}
```

- **URL**: `/home`
- **Gerenderte Komponente**: `HomeView`

In diesem Beispiel wird beim Navigieren zu `/home` die `HomeView`-Komponente gerendert.

## Layout-Routen {#layout-routes}

Layout-Routen umschließen Kinderansichten, ohne zur URL beizutragen. Layouts bieten gemeinsam genutzte UI-Elemente wie Kopfzeilen oder Seitenleisten, die in mehreren Ansichten konsistent sind. Kinder-Routen werden im Inhaltsbereich des Layouts gerendert.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

In diesem Fall ist `MainLayout` eine Layout-Route, die um Kinderansichten gewickelt ist. Es definiert gemeinsame UI-Elemente wie eine Kopfzeile und Schublade. Kinder-Routen, die mit diesem Layout verbunden sind, werden in den Inhaltsbereich der `AppLayout`-Komponente eingefügt.

## Automatische Erkennung der Routentypen {#auto-detection-of-route-types}

Standardmäßig wird der Routentyp automatisch erkannt, ob die Route eine **Ansicht** oder ein **Layout** ist, basierend auf dem Klassennamen:

- Klassen, die auf `Layout` enden, werden als **Layout-Routen** behandelt.
- Klassen, die auf `View` enden, werden als **Ansichts-Routen** behandelt.

Alternativ können Entwickler den Routentyp manuell festlegen, indem sie `Route.Type` in der `@Route`-Annotation setzen.

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
  public DashboardView() {
    Div content = getBoundComponent();
    content.add(new H1("Dashboard-Inhalt"));
  }
}
```
