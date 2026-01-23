---
sidebar_position: 12
title: Route Transitions
sidebar_class_name: new-content
---

<!-- vale Google.Units = NO -->

<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Route transitions provide declarative animated transitions when navigating between routes. Built on the [View Transitions](/docs/advanced/view-transitions) API, adding the `@RouteTransition` annotation to your route components lets the router automatically handle the animation lifecycle during navigation.

:::warning Experimental API
This API is marked as experimental since 25.11 and may change in future releases. The API signature, behavior, and performance characteristics are subject to modification.
:::

:::info Programmatic control
For more complex transition scenarios or programmatic control, use the [View Transitions](/docs/advanced/view-transitions) API directly.
:::

## The `@RouteTransition` annotation {#the-routetransition-annotation}

The `@RouteTransition` annotation defines how a route component animates when entering or exiting the view:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // view implementation
}
```

The annotation accepts the following properties:

| Property | Description |
|----------|-------------|
| `enter` | Animation applied when this view appears |
| `exit` | Animation applied when this view leaves |

Both properties accept any of the predefined transition types or a custom string value:

| Constant | Effect |
|----------|--------|
| `ViewTransition.NONE` | No animation |
| `ViewTransition.FADE` | Crossfade between old and new content |
| `ViewTransition.SLIDE_LEFT` | Content flows left (like forward navigation) |
| `ViewTransition.SLIDE_RIGHT` | Content flows right (like back navigation) |
| `ViewTransition.SLIDE_UP` | Content flows upward |
| `ViewTransition.SLIDE_DOWN` | Content flows downward |
| `ViewTransition.ZOOM` | Old content shrinks away, new content grows in |
| `ViewTransition.ZOOM_OUT` | Old content grows away, new content shrinks in |

## Basic usage {#basic-usage}

Add the annotation to any route component to enable transitions:

```java title="InboxView.java"
@Route(value = "inbox", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.SLIDE_RIGHT)
@FrameTitle("Inbox")
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    getBoundComponent().add(new H1("Inbox"));
    // ...
  }
}
```

In this example:
- When navigating to `InboxView`, the component enters with a zoom animation
- When navigating away from `InboxView`, the component exits with content flowing right

## Navigation flow {#navigation-flow}

When navigating between two routes, the router coordinates the transition sequence:

1. The exiting component's `exit` animation begins
2. [DOM](/docs/glossary#dom) changes occur (old view removed, new view added)
3. The entering component's `enter` animation plays

If navigating to the same view that's already displayed, the transition is skipped to avoid unnecessary animations.

:::tip Consistent exit animations
Using the same exit animation across all views creates directional consistency. For example, configuring all views to exit with `SLIDE_RIGHT` establishes a uniform "back" motion pattern, making navigation behavior predictable regardless of the origin view.
:::

## Transition inheritance {#transition-inheritance}

Routes inherit transitions from their parent routes. When a route doesn't have `@RouteTransition`, the router walks up the hierarchy to find one.

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // Parent layout with transition
}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Inherits ZOOM from MainLayout
}

@Route(value = "/sub", outlet = InboxView.class)
public class SubView extends Composite<FlexLayout> {
  // Inherits ZOOM from MainLayout (through InboxView)
}
```

All child routes inherit the same animation style without repeating the annotation.

### Overriding inherited transitions {#overriding-inherited-transitions}

Child routes can override the inherited transition by defining their own `@RouteTransition`:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Inherits ZOOM
}

@Route(value = "/settings", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.SLIDE_UP, exit = ViewTransition.SLIDE_DOWN)
public class SettingsView extends Composite<FlexLayout> {
  // Overrides with SLIDE_UP/SLIDE_DOWN
}
```

## Shared component transitions {#shared-component-transitions}

You can combine route transitions with shared component animations to create connected experiences. Components with matching `view-transition-name` values morph between views. Use the `setViewTransitionName()` method, available on any component that implements the <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink> interface.

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

When navigating from the list to the detail view, the product thumbnail morphs into the hero image position while the rest of the content transitions with the fade animation.