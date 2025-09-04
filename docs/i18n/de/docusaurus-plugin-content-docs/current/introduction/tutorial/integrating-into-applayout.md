---
title: Integrating the AppLayout
sidebar_position: 6
draft: true
_i18n_hash: c0ed4864dc99a4665aef3f4ff808bc9d
---
In diesem Schritt integrieren Sie die in den vorherigen Schritten implementierten Funktionen wie Routing und Ansichten in ein kohärentes App-Layout. Diese Struktur bietet ein einheitliches Navigationssystem und dynamische Inhaltsbereiche.

## Zweck des App-Layouts {#purpose-of-the-app-layout}

Das `AppLayout` dient als Fundament für das Management der gesamten Struktur und des Flusses Ihrer App. Es bietet:
- **Globale Navigation**: Eine konsistente Möglichkeit, zwischen den wichtigsten Abschnitten zu wechseln.
- **Dynamische Inhaltsdarstellung**: Ein zentrales Layout zur Anzeige der gerouteten Ansichten.

## Verwendung von `AppNav` {#using-appnav}

Die Komponente `AppNav` wird verwendet, um ein Navigationsmenü innerhalb der Benutzeroberfläche der App zu erstellen. Dieses Menü bietet Links zu verschiedenen Ansichten in Ihrer App, wie z. B. der `DemoView`:

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
- Jedes Menüelement ist ein `AppNavItem`, das angibt:
  - Das Label, zum Beispiel "Dashboard."
  - Die Zielansicht, zum Beispiel `DemoView`.
  - Ein optionales Symbol, zum Beispiel ein Symbol für Spalten.

## Layout-Routen und -Outlets {#layout-routes-and-outlets}

Das Layout verwendet Routen und Outlets, um Inhalte dynamisch innerhalb eines strukturierten Layouts darzustellen. In webforJ:
- **Routen** definieren, wie Ansichten bestimmten Pfaden zugeordnet sind.
- **Outlets** fungieren als Platzhalter in Layouts, in denen die gerouteten Ansichten angezeigt werden.

### Beispiel: Einrichten einer Layout-Route {#example-setting-up-a-layout-route}

In der Klasse `MainLayout` definiert die Annotation `@Route`, dass es sich um das Basishlayout handelt, und die `DemoView` wird über ein Outlet in diesem Layout gerendert:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

Die `@Route`-Annotation für `DemoView` gibt an, dass es `MainLayout` als sein Outlet verwendet:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
    // Logik der DemoView
}
```

## Hinzufügen von dynamischem Inhalt mit `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

Ein `RouteOutlet` zeigt dynamisch Ansichten basierend auf der aktiven Route an. Im Layout werden Ansichten wie `DemoView` durch das `RouteOutlet` gerendert. Während das `RouteOutlet` implizit durch die Outlet-Spezifikation in den Routen-Annotationen verarbeitet wird.
