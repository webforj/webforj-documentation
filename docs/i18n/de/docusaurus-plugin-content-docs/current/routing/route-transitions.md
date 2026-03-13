---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
_i18n_hash: 34159c78405282a71774c6148a31f18a
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Routenübergänge bieten deklarative animierte Übergänge beim Navigieren zwischen Routen. Basierend auf der [View Transitions](/docs/advanced/view-transitions) API ermöglicht das Hinzufügen der Annotation `@RouteTransition` zu Ihren Routenkomponenten dem Router, den Animationslebenszyklus während der Navigation automatisch zu verwalten.

:::warning Experimentelle API
Diese API ist seit 25.11 als experimentell gekennzeichnet und kann sich in zukünftigen Versionen ändern. Die API-Signatur, das Verhalten und die Leistungsmerkmale können geändert werden.
:::

:::info Programmatische Kontrolle
Für komplexere Übergangsszenarien oder programmatische Kontrolle verwenden Sie die [View Transitions](/docs/advanced/view-transitions) API direkt.
:::

## Die `@RouteTransition` Annotation {#the-routetransition-annotation}

Die `@RouteTransition` Annotation definiert, wie eine Routenkomponente animiert, wenn sie in die Ansicht eintritt oder diese verlässt:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // Ansicht Implementierung
}
```

Die Annotation akzeptiert die folgenden Eigenschaften:

| Eigenschaft | Beschreibung |
|-------------|--------------|
| `enter`     | Animation, die angewendet wird, wenn diese Ansicht erscheint |
| `exit`      | Animation, die angewendet wird, wenn diese Ansicht verlässt |

Beide Eigenschaften akzeptieren einen der vordefinierten Übergangstypen oder einen benutzerdefinierten Zeichenfolgenwert:

| Konstante                       | Effekt                                   |
|---------------------------------|------------------------------------------|
| `ViewTransition.NONE`           | Keine Animation                          |
| `ViewTransition.FADE`           | Überblendung zwischen alter und neuer Inhalte |
| `ViewTransition.SLIDE_LEFT`     | Inhalt fließt nach links (wie vorwärts Navigation) |
| `ViewTransition.SLIDE_RIGHT`    | Inhalt fließt nach rechts (wie Rücknavigation) |
| `ViewTransition.SLIDE_UP`       | Inhalt fließt nach oben                  |
| `ViewTransition.SLIDE_DOWN`     | Inhalt fließt nach unten                 |
| `ViewTransition.ZOOM`           | Alter Inhalt verkleinert sich, neuer Inhalt vergrößert sich |
| `ViewTransition.ZOOM_OUT`       | Alter Inhalt vergrößert sich, neuer Inhalt verkleinert sich |

## Grundlegende Verwendung {#basic-usage}

Fügen Sie die Annotation zu jeder Routenkomponente hinzu, um Übergänge zu aktivieren:

```java title="InboxView.java"
@Route(value = "inbox", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.SLIDE_RIGHT)
@FrameTitle("Posteingang")
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.add(new H1("Posteingang"));
    // ...
  }
}
```

In diesem Beispiel:
- Beim Navigieren zu `InboxView` tritt die Komponente mit einer Zoom-Animation ein.
- Beim Navigieren von `InboxView` verlässt die Komponente mit Inhalt, der nach rechts fließt.

## Navigationsfluss {#navigation-flow}

Beim Navigieren zwischen zwei Routen koordiniert der Router die Übergangssequenz:

1. Die `exit` Animation der ausgehenden Komponente beginnt
2. [DOM](/docs/glossary#dom) Änderungen erfolgen (alte Ansicht wird entfernt, neue Ansicht wird hinzugefügt)
3. Die `enter` Animation der eingehenden Komponente wird abgespielt

Wenn zu der gleichen Ansicht navigiert wird, die bereits angezeigt wird, wird der Übergang übersprungen, um unnötige Animationen zu vermeiden.

:::tip Konsistente Ausgangsanimationen
Die Verwendung derselben Ausgangsanimation über alle Ansichten hinweg schafft eine richtungsweisende Konsistenz. Zum Beispiel etabliert das Konfigurieren aller Ansichten zum Verlassen mit `SLIDE_RIGHT` ein einheitliches "Zurück"-Bewegungsmuster, was das Navigationsverhalten vorhersehbar macht, unabhängig von der Ursprungsansicht.
:::

## Übergangsvererbung {#transition-inheritance}

Routen erben Übergänge von ihren übergeordneten Routen. Wenn eine Route keine `@RouteTransition` hat, geht der Router der Hierarchie nach, um eine zu finden.

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // Übergeordnetes Layout mit Übergang
}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Erbt ZOOM von MainLayout
}

@Route(value = "/sub", outlet = InboxView.class)
public class SubView extends Composite<FlexLayout> {
  // Erbt ZOOM von MainLayout (durch InboxView)
}
```

Alle untergeordneten Routen erben denselben Animationsstil, ohne die Annotation zu wiederholen.

### Überschreiben erblicher Übergänge {#overriding-inherited-transitions}

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

Sie können Routenübergänge mit gemeinsamen Komponentenanimationen kombinieren, um verbundene Erfahrungen zu schaffen. Komponenten mit übereinstimmenden `view-transition-name` Werten morphieren zwischen Ansichten. Verwenden Sie die Methode `setViewTransitionName()`, die auf jeder Komponente verfügbar ist, die das <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> Interface implementiert.

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

Beim Navigieren von der Liste zur Detailansicht morphiert das Produktthumbnail in die Position des Heldenbildes, während der Rest des Inhalts mit der Überblendungsanimation übergeht.
