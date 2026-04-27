---
sidebar_position: 12
title: Route Transitions
_i18n_hash: 98050ac6a061f4dc3728af3888aa44b0
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Routenübergänge bieten deklarative animierte Übergänge beim Navigieren zwischen Routen. Basierend auf der [View Transitions](/docs/advanced/view-transitions) API, ermöglicht es das Hinzufügen der `@RouteTransition` Annotation zu Ihren Routenkomponenten, dass der Router den Animationslebenszyklus während der Navigation automatisch verwaltet.

<ExperimentalWarning />

:::info Programmatische Steuerung
Für komplexere Übergangsszenarien oder programmatische Steuerung verwenden Sie direkt die [View Transitions](/docs/advanced/view-transitions) API.
:::

## Die `@RouteTransition` Annotation {#the-routetransition-annotation}

Die `@RouteTransition` Annotation definiert, wie eine Routenkomponente animiert wird, wenn sie in die Ansicht eintritt oder sie verlässt:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // Ansichtsimplementierung
}
```

Die Annotation akzeptiert die folgenden Eigenschaften:

| Eigenschaft | Beschreibung |
|-------------|--------------|
| `enter`     | Animation, die angewendet wird, wenn diese Ansicht erscheint |
| `exit`      | Animation, die angewendet wird, wenn diese Ansicht verschwindet |

Beide Eigenschaften akzeptieren jeden der vordefinierten Übergangstypen oder einen benutzerdefinierten Stringwert:

| Konstante                  | Effekt                        |
|---------------------------|-------------------------------|
| `ViewTransition.NONE`     | Keine Animation               |
| `ViewTransition.FADE`     | Überblenden zwischen alter und neuer Inhalte |
| `ViewTransition.SLIDE_LEFT` | Inhalte fließen nach links (wie Vorwärtsnavigation) |
| `ViewTransition.SLIDE_RIGHT`| Inhalte fließen nach rechts (wie Rücknavigation) |
| `ViewTransition.SLIDE_UP` | Inhalte fließen nach oben     |
| `ViewTransition.SLIDE_DOWN`| Inhalte fließen nach unten    |
| `ViewTransition.ZOOM`     | Alte Inhalte schrumpfen, neue Inhalte wachsen heran |
| `ViewTransition.ZOOM_OUT` | Alte Inhalte wachsen, neue Inhalte schrumpfen |

## Grundlegende Nutzung {#basic-usage}

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
- Beim Navigieren zu `InboxView` tritt die Komponente mit einer Zoomanimation ein.
- Beim Navigieren von `InboxView` verlässt die Komponente die Ansicht mit rechtlich fließendem Inhalt.

## Navigationsfluss {#navigation-flow}

Beim Navigieren zwischen zwei Routen koordiniert der Router die Übergangssequenz:

1. Die `exit` Animation der ausgehenden Komponente beginnt.
2. Änderungen im [DOM](/docs/glossary#dom) erfolgen (alte Ansicht entfernt, neue Ansicht hinzugefügt).
3. Die `enter` Animation der eintretenden Komponente wird abgespielt.

Wenn zu derselben Ansicht navigiert wird, die bereits angezeigt wird, wird der Übergang übersprungen, um unnötige Animationen zu vermeiden.

:::tip Konsistente Ausgangsanimationen
Die Verwendung derselben Ausgangsanimation über alle Ansichten sorgt für eine richtungsgebundene Konsistenz. Zum Beispiel, wenn alle Ansichten so konfiguriert sind, dass sie mit `SLIDE_RIGHT` verlassen, wird ein einheitliches "Zurück"-Bewegungsmuster etabliert, wodurch das Navigationsverhalten unabhängig von der Ursprungsansicht vorhersehbar wird.
:::

## Übergangsvererbung {#transition-inheritance}

Routen erben Übergänge von ihren übergeordneten Routen. Wenn eine Route keine `@RouteTransition` hat, durchläuft der Router die Hierarchie, um eine zu finden.

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
  // Erbt ZOOM von MainLayout (über InboxView)
}
```

Alle untergeordneten Routen erben den gleichen Animationsstil, ohne die Annotation zu wiederholen.

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

Sie können Routenübergänge mit gemeinsamen Komponentenanimationen kombinieren, um verbundene Erlebnisse zu schaffen. Komponenten mit übereinstimmenden `view-transition-name` Werten verwandeln sich zwischen den Ansichten. Verwenden Sie die Methode `setViewTransitionName()`, die für jede Komponente verfügbar ist, die das <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> Interface implementiert.

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

Beim Navigieren von der Liste zur Detailansicht verwandelt sich das Produktthumbnail in die Position des Heldenbildes, während der Rest des Inhalts mit der Überblendanimation übergeht.
