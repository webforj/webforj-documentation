---
title: "Animated Transitions, No JavaScript Required"
description: How webforJ's Transitions API brings the browser's View Transition API to Java, with zero JavaScript required.
slug: view-transitions
date: 2026-04-13
authors: Lauren Alamo
tags: [front end, integrations, layout, styling, routing]
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-04-03-view-transitions/blog-view-transitions-cover.png)
<!-- vale webforJ.BeDirect = NO -->

There's something about native mobile apps that's hard to copy on the web: things move. Tap a photo and it expands. Go back and it shrinks to where it came from. The browser's [View Transition API](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) has been closing that gap, and webforJ 25.11 brings it to Java with [webforJ View Transitions](/docs/advanced/view-transitions).

<!-- truncate -->

To see what this looks like in practice, I threw together a quick travel destinations app with a card grid and detail pages to browse through. It shows two things at once: slide animations between routes via `@RouteTransition`, and a shared element morph where the card thumbnail expands into the full hero image on the detail page.

<div class="videos-container">
<video controls preload="metadata">
  <source src="https://cdn.webforj.com/webforj-documentation/blogs/2026-04-03-view-transitions/view-transitions.mp4" type="video/mp4"/>
</video>
</div>

## How it works

Before getting into the Java API, it's worth knowing what the browser is actually doing. When a view transition fires, the browser takes a snapshot of the current page, applies your DOM changes, then animates between the old snapshot and the new live content. The browser handles all of that on its own.

The tricky part for server-side frameworks is timing. DOM mutations happen on the server, not instantly in the browser, so the browser needs a way to know when your changes are done. webforJ handles this by wrapping your component updates in a callback and signaling the browser once the server-side work is complete.

## Basic usage

Start with `Page.getCurrent().startViewTransition()`, which returns a transition builder, [`ViewTransition`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/ViewTransition.html). The only method you need to call before `start()` is `onUpdate()`, where you make your actual component changes:

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

`done.run()` signals to the browser that your DOM changes are complete; skip it and the transition hangs. Calling `start()` without an `onUpdate` callback throws an `IllegalStateException`.

Without any extra configuration, you get a crossfade. That's already a big improvement over a snap cut, and it only takes a few lines to set up.

## Declarative route transitions

For most navigation scenarios, you don't need any of the above. `@RouteTransition` on a route class is enough; the router handles the animation lifecycle automatically:

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<FlexLayout> {
  // view implementation
}
```

Set `enter` and `exit`, and you're done. It accepts `ViewTransition` constants, or a custom string if you've defined your own CSS. Reach for `startViewTransition()` directly when you need to animate non-navigation changes, combine enter and exit on different components, or work with shared element morphing.

## Predefined transition types

If you need more control over individual components, `enter()` and `exit()` let you target specific elements:

```java
// Sliding in a detail panel
Page.getCurrent().startViewTransition()
  .enter(detailPanel, ViewTransition.SLIDE_LEFT)
  .onUpdate(done -> {
    container.add(detailPanel);
    done.run();
  })
  .start();

// Fading out a summary panel
Page.getCurrent().startViewTransition()
  .exit(summaryPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(summaryPanel);
    done.run();
  })
  .start();
```

Both can go on the same transition:

```java
Page.getCurrent().startViewTransition()
  .exit(oldView, ViewTransition.SLIDE_LEFT)
  .enter(newView, ViewTransition.SLIDE_LEFT)
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Here's the full list of built-in types:

| Constant | Effect |
|---|---|
| `ViewTransition.NONE` | No animation |
| `ViewTransition.FADE` | Crossfade between old and new content |
| `ViewTransition.SLIDE_LEFT` | Content flows left (forward navigation feel) |
| `ViewTransition.SLIDE_RIGHT` | Content flows right (back navigation feel) |
| `ViewTransition.SLIDE_UP` | Content flows upward |
| `ViewTransition.SLIDE_DOWN` | Content flows downward |
| `ViewTransition.ZOOM` | Old content shrinks, new content grows in |
| `ViewTransition.ZOOM_OUT` | Old content grows away, new content shrinks in |

## Shared element transitions (morphing)

Click a product thumbnail and it expands into a full-width hero image (the large featured image at the top of a detail page) with no jarring cut, just a continuous motion between the two states. That's a shared element transition, and it's the one I found most satisfying to wire up in the travel app.

Give the same element in both views a matching name using `setViewTransitionName()`, available on any component that implements `HasStyle`. When the transition runs, the browser finds the matching names and animates between them automatically:

```java
// In the list view, before navigating
thumbnail.setViewTransitionName("product-image-" + productId);

// In the detail view, on creation
heroImage.setViewTransitionName("product-image-" + productId);

// Trigger the navigation inside a transition
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    Router.getCurrent().navigate(DetailView.class, params);
    done.run();
  })
  .start();
```

The browser handles the rest on its own.

One thing to watch out for with lists: every repeating element needs a unique name. If two visible components share the same transition name, behavior is undefined. Append the item ID, or whatever uniquely identifies each row.

## Custom CSS animations

The API supports fully custom keyframe animations if the built-in types don't cover your use case. webforJ automatically appends `-enter` or `-exit` to whatever name you pass into `enter()` or `exit()`, which you target in CSS using the `::view-transition-new` and `::view-transition-old` pseudo-elements:

```css
@keyframes flip-enter {
  from {
    opacity: 0;
    transform: perspective(1000px) rotateX(-90deg);
  }
  to {
    opacity: 1;
    transform: perspective(1000px) rotateX(0deg);
  }
}

::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms var(--dwc-ease-outBack);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

On the Java side, pass just the base name; webforJ adds the suffix:

```java
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();
```

To load the CSS, annotate the view that calls `startViewTransition()` with `@StyleSheet` or `@InlineStyleSheet`. Because `::view-transition-*` pseudo-elements are painted at the document level, the stylesheet only needs to be present on the page that triggers the transition—you don't need to add it to every route.

You don't have to go fully custom to get some CSS control. Targeting the built-in pseudo-elements is enough to adjust timing or easing:

```css
::view-transition-old(vt-slide-left-exit) {
  animation-duration: 400ms;
}

::view-transition-new(vt-slide-left-enter) {
  animation-timing-function: ease-out;
}
```

For all available easing variables, see [Transitions & Easing](/docs/styling/transitions-easing).

## Wrapping up

The Transitions API covers a lot of ground without touching JavaScript. If a browser doesn't support the View Transition API, webforJ doesn't error out: the `onUpdate` callback still runs, the DOM still updates, it just happens without animation. Safe to adopt incrementally.

Try dropping `@RouteTransition` onto one of your existing route classes and go from there.

For the full details, check out the [View Transitions](/docs/advanced/view-transitions) and [Route Transitions](/docs/routing/route-transitions) docs.
 