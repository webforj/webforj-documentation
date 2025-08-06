---
title: Integrating the AppLayout
sidebar_position: 6
draft: true
_i18n_hash: fd2f3844bbcb102adf05ae01b07ff8d8
---
In diesem Schritt integrieren Sie die in den vorherigen Schritten implementierten Funktionen, wie Routing und Ansichten, in ein einheitliches App-Layout. Diese Struktur bietet ein einheitliches Navigationssystem und dynamische Inhaltsbereiche.

## Zweck des App-Layouts {#purpose-of-the-app-layout}

Das `AppLayout` dient als Grundlage für die Verwaltung der Gesamtstruktur und des Flusses Ihrer App. Es bietet:
- **Globale Navigation**: Eine konsistente Möglichkeit, zwischen wichtigen Abschnitten zu wechseln.
- **Dynamische Inhaltsdarstellung**: Ein zentrales Layout zum Anzeigen von gerouteten Ansichten.

## Verwendung von `AppNav` {#using-appnav}

Die `AppNav`-Komponente wird verwendet, um ein Navigationsmenü innerhalb der Benutzeroberfläche der App zu erstellen. Dieses Menü bietet Links zu verschiedenen Ansichten in Ihrer App, wie der `DemoView`:

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Dashboard", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

In diesem Beispiel:
- Das Navigationsmenü wird dem Drawer der App hinzugefügt.
- Jedes Menüelement ist ein `AppNavItem`, das folgendes spezifiziert:
  - Das Label, zum Beispiel "Dashboard."
  - Die Zielansicht, zum Beispiel `DemoView`.
  - Ein optionales Symbol, zum Beispiel ein Säulensymbol.

## Layout-Routen und Ausgänge {#layout-routes-and-outlets}

Das Layout verwendet Routen und Ausgänge, um Inhalte dynamisch innerhalb eines strukturierten Layouts darzustellen. In webforJ:
- **Routen** definieren, wie Ansichten bestimmten Pfaden zugeordnet werden.
- **Ausgänge** fungieren als Platzhalter in Layouts, in denen die gerouteten Ansichten angezeigt werden.

### Beispiel: Einrichten einer Layout-Route {#example-setting-up-a-layout-route}

In der `MainLayout`-Klasse definiert die `@Route`-Annotation sie als das Basislayout, und die `DemoView` wird über einen Ausgang in diesem Layout gerendert:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

Die `@Route`-Annotation für die `DemoView` gibt an, dass sie `MainLayout` als ihren Ausgang verwendet:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
    // Logik der DemoView
}
```

## Hinzufügen von dynamischen Inhalten mit `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

Ein `RouteOutlet` zeigt dynamisch Ansichten basierend auf der aktiven Route an. Im Layout werden Ansichten wie `DemoView` durch das `RouteOutlet` gerendert. Während das `RouteOutlet` implizit durch die Ausgangsangabe in den Routenannotationen behandelt wird.
