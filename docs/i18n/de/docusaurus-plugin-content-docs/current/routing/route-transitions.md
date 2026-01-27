---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
_i18n_hash: 5991e12089a2044ef0fd6b15cae1fb13
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Routenübergänge bieten deklarative animierte Übergänge beim Navigieren zwischen Routen. Basierend auf der [View Transitions](/docs/advanced/view-transitions) API ermöglicht die Hinzufügung der Annotation `@RouteTransition` zu Ihren Routenkomponenten dem Router, den Animationslebenszyklus während der Navigation automatisch zu verwalten.

:::warning Experimentelle API
Diese API ist seit 25.11 als experimentell gekennzeichnet und kann sich in zukünftigen Versionen ändern. Die API-Signatur, das Verhalten und die Leistungsmerkmale können modifiziert werden.
:::

:::info Programmgesteuerte Kontrolle
Für komplexere Übergangsszenarien oder programmgesteuerte Kontrollen verwenden Sie direkt die [View Transitions](/docs/advanced/view-transitions) API.
:::

## Die `@RouteTransition` Annotation {#the-routetransition-annotation}

Die `@RouteTransition` Annotation definiert, wie sich eine Routenkomponente beim Eintritt in oder Verlassen der Ansicht animiert:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // Ansichtimplementierung
}
```

Die Annotation akzeptiert folgende Eigenschaften:

| Eigenschaft | Beschreibung |
|-------------|--------------|
| `enter`     | Animation, die angewendet wird, wenn diese Ansicht erscheint |
| `exit`      | Animation, die angewendet wird, wenn diese Ansicht verlassen wird |

Beide Eigenschaften akzeptieren beliebige der vordefinierten Übergangstypen oder einen benutzerdefinierten Stringwert:

| Konstante                       | Effekt                                          |
|---------------------------------|------------------------------------------------|
| `ViewTransition.NONE`           | Keine Animation                                 |
| `ViewTransition.FADE`           | Überblenden zwischen altem und neuem Inhalt   |
| `ViewTransition.SLIDE_LEFT`     | Inhalt fließt nach links (wie Vorwärtsnavigation) |
| `ViewTransition.SLIDE_RIGHT`    | Inhalt fließt nach rechts (wie Rücknavigation) |
| `ViewTransition.SLIDE_UP`       | Inhalt fließt nach oben                         |
| `ViewTransition.SLIDE_DOWN`     | Inhalt fließt nach unten                       |
| `ViewTransition.ZOOM`           | Alter Inhalt schrumpft, neuer Inhalt wächst    |
| `ViewTransition.ZOOM_OUT`       | Alter Inhalt wächst, neuer Inhalt schrumpft    |

## Grundlegende Nutzung {#basic-usage}

Fügen Sie die Annotation zu jeder Routenkomponente hinzu, um Übergänge zu aktivieren:

```java title="InboxView.java"
@Route(value = "inbox", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.SLIDE_RIGHT)
@FrameTitle("Posteingang")
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    getBoundComponent().add(new H1("Posteingang"));
    // ...
  }
}
```

In diesem Beispiel:
- Beim Navigieren zu `InboxView` tritt die Komponente mit einer Zoom-Animation ein
- Beim Navigieren von `InboxView` verlässt die Komponente mit Inhalt, der nach rechts fließt

## Navigationsfluss {#navigation-flow}

Beim Navigieren zwischen zwei Routen koordiniert der Router die Übergangssequenz:

1. Die `exit`-Animation der ausgehenden Komponente beginnt
2. [DOM](/docs/glossary#dom) Änderungen erfolgen (alte Ansicht entfernt, neue Ansicht hinzugefügt)
3. Die `enter`-Animation der eingehenden Komponente wird abgespielt

Wenn zu derselben Ansicht navigiert wird, die bereits angezeigt wird, wird der Übergang übersprungen, um unnötige Animationen zu vermeiden.

:::tip Konsistente Ausgangsanimationen
Die Verwendung derselben Ausgangsanimation über alle Ansichten hinweg schafft eine richtungsgebundene Konsistenz. Wenn zum Beispiel alle Ansichten so konfiguriert sind, dass sie mit `SLIDE_RIGHT` ausgehen, wird ein einheitliches "Zurück"-Bewegungsmuster etabliert, das das Navigationsverhalten vorhersehbar macht, unabhängig von der Ursprungsansicht.
:::

## Übergangsvererbung {#transition-inheritance}

Routen erben Übergänge von ihren übergeordneten Routen. Wenn eine Route keine `@RouteTransition` hat, durchsucht der Router die Hierarchie, um eine zu finden.

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // Übergeordnete Layout mit Übergang
}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Erbt ZOOM von MainLayout
}

@Route(value = "/sub", outlet = InboxView.class)
public class SubView extends Composite<FlexLayout> {
  // Erbt ZOOM von MainLayout (über InboxView)
}
```

Alle untergeordneten Routen erben denselben Animationsstil, ohne die Annotation zu wiederholen.

### Überschreiben von geerbten Übergängen {#overriding-inherited-transitions}

Untergeordnete Routen können den geerbten Übergang überschreiben, indem sie ihre eigene `@RouteTransition` definieren:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Erbt ZOOM
}

@Route(value = "/settings", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.SLIDE_UP, exit = ViewTransition.SLIDE_DOWN)
public class SettingsView extends Composite<FlexLayout> {
  // Überschreibt mit SLIDE_UP/SLIDE_DOWN
}
```

## Gemeinsame Komponentenübergänge {#shared-component-transitions}

Sie können Routenübergänge mit gemeinsamen Komponentenanimationen kombinieren, um verbundene Erlebnisse zu schaffen. Komponenten mit übereinstimmenden `view-transition-name` Werten morphieren zwischen Ansichten. Verwenden Sie die Methode `setViewTransitionName()`, die für jede Komponente verfügbar ist, die das <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> Interface implementiert.

```java title="ProductListView.java"
@Route(value = "products", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.FADE)
public class ProductListView extends Composite<FlexLayout> {

  private void buildProductCard(Product product) {
      Img thumbnail = new Img(product.getImageUrl());
      thumbnail.setViewTransitionName("product-image-" + product.getId());
      // ...
  }
}
```

```java title="ProductDetailView.java"
@Route(value = "products/:id", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.FADE)
public class ProductDetailView extends Composite<FlexLayout> implements DidEnterObserver {

  private Img heroImage = new Img();

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
      String id = parameters.get("id").orElse("");
      heroImage.setViewTransitionName("product-image-" + id);
      // ...
  }
}
```

Beim Navigieren von der Liste zur Detailansicht morphiert das Produktthumbnail in die Position des Heldenbildes, während der Rest des Inhalts mit der Fade-Animation übergeht.
