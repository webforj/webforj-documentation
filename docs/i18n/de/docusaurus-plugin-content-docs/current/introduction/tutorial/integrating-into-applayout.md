---
title: Integrating the AppLayout
sidebar_position: 7
draft: true
description: Step 6 - Use the AppLayout component.
_i18n_hash: 14b409262af6d7a8a25a67278f687250
---
In diesem Schritt integrieren Sie die in den vorherigen Schritten implementierten Funktionen, wie Routing und Ansichten, in ein einheitliches App-Layout. Diese Struktur bietet ein einheitliches Navigationssystem und dynamische Inhaltsbereiche.

## Ausführen der App {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die `pom.xml`-Datei enthält, dies ist `6-integrating-an-app-layout`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot App lokal auszuführen:
    ```bash
    mvn
    ```

Das Ausführen der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Zweck des App-Layouts {#purpose-of-the-app-layout}

Das `AppLayout` dient als Basis für die Verwaltung der gesamten Struktur und des Flusses Ihrer App. Es bietet:
- **Globale Navigation**: Eine konsistente Möglichkeit, zwischen den wichtigsten Abschnitten zu wechseln.
- **Dynamisches Inhalts-Rendering**: Ein zentrales Layout zum Anzeigen der gerouteten Ansichten.

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
- Das Navigationsmenü wird dem Schubladenteil der App hinzugefügt.
- Jedes Menüelement ist ein `AppNavItem`, das Folgendes angibt:
  - Das Label, zum Beispiel "Dashboard."
  - Die Zielansicht, zum Beispiel `DemoView`.
  - Ein optionales Icon, zum Beispiel ein Spalten-Icon.

## Layout-Routen und Ausgänge {#layout-routes-and-outlets}

Das Layout verwendet Routen und Ausgänge, um Inhalte dynamisch in einem strukturierten Layout darzustellen. In webforJ:
- **Routen** definieren, wie Ansichten bestimmten Pfaden zugeordnet sind.
- **Ausgänge** fungieren als Platzhalter in Layouts, wo die gerouteten Ansichten angezeigt werden.

### Beispiel: Einrichten einer Layout-Route {#example-setting-up-a-layout-route}

In der `MainLayout`-Klasse definiert die `@Route`-Annotation sie als Basislayout, und die `DemoView` wird über einen Ausgang in diesem Layout gerendert:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

Die `@Route`-Annotation für `DemoView` gibt an, dass sie `MainLayout` als ihren Ausgang verwendet:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView-Logik
}
```

## Hinzufügen von dynamischem Inhalt mit `RouteOutlet` {#adding-dynamic-content-with-routeoutlet}

Ein `RouteOutlet` zeigt dynamisch Ansichten basierend auf der aktiven Route an. Im Layout werden Ansichten wie `DemoView` über den `RouteOutlet` gerendert. Während der `RouteOutlet` implizit durch die Ausgangsangabe in den Routenannotationen behandelt wird.
