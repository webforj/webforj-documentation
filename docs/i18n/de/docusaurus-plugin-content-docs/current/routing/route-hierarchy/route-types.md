---
sidebar_position: 1
title: Route Types
_i18n_hash: d297703f4a165ebcfbb540c3256f825e
---
Routen werden in zwei Haupttypen unterteilt, **Ansichts-Routen** und **Layout-Routen**. Die Wahl des Routentyps bestimmt, wie Komponenten auf URLs abgebildet werden und wie sie mit anderen Teilen Ihrer App interagieren.

## Ansichts-Routen {#view-routes}

Ansichts-Routen sind direkt mit einem URL-Segment verknüpft und stellen spezifische Seiten in Ihrer App dar. Diese Routen spiegeln sich in der URL des Browsers wider und werden typischerweise für verschiedene Ansichten oder Seiten verwendet.

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

In diesem Beispiel rendert die Navigation zu `/home` die `HomeView`-Komponente.

## Layout-Routen {#layout-routes}

Layout-Routen umschließen untergeordnete Ansichten, ohne zur URL beizutragen. Layouts bieten gemeinsame UI-Elemente wie Kopfzeilen oder Seitenleisten, die über mehrere Ansichten hinweg konsistent sind. Untergeordnete Routen werden im Inhaltsbereich des Layouts gerendert.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

In diesem Fall ist `MainLayout` eine Layout-Route, die um untergeordnete Ansichten herum eingewickelt ist. Es definiert gemeinsame UI-Elemente wie eine Kopfzeile und eine Schublade. Untergeordnete Routen, die mit diesem Layout verbunden sind, werden im Inhaltsbereich der `AppLayout`-Komponente eingefügt.

## Automatische Erkennung von Routentypen {#auto-detection-of-route-types}

Standardmäßig wird der Routentyp automatisch erkannt, ob die Route eine **Ansicht** oder ein **Layout** ist, basierend auf dem Klassennamen:

- Klassen, die mit `Layout` enden, werden als **Layout-Routen** behandelt.
- Klassen, die mit `View` enden, werden als **Ansichts-Routen** behandelt.

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
