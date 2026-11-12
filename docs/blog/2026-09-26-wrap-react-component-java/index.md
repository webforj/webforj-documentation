---
title: "Use a React component in Java: wrap it with webforJ"
description: "Use a React component in Java by turning it into a custom element and wrapping it with ElementComposite. A star rating built step by step in webforJ."
slug: wrap-react-component-java
date: 2026-09-26
authors: Lauren Alamo
tags: [components, javascript, events]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-26-wrap-react-component-java/cover-v2.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-26-wrap-react-component-java/cover-v2.jpg)

Your team already has a React component it likes, a star rating, a date picker, a small chart. Now the app around it is moving to Java, and you'd rather not rebuild that widget or keep a second frontend just for it.

You don't have to. To use a React component in Java with webforJ, you turn it into a custom element and wrap that element in a Java class. The React code stays React, and the rest of the app sees an ordinary webforJ component.

<!-- truncate -->

## How the pieces fit {#how-it-fits}

A [custom element](https://developer.mozilla.org/en-US/docs/Web/API/Web_components/Using_custom_elements) is the meeting point. The browser treats it as one tag, and React renders whatever it likes inside. That gives three small layers:

1. **The React component**, unchanged, under `src/main/frontend`.
2. **An entry file** that registers it as a tag, here `star-rating`, using a library such as [`@r2wc/react-to-web-component`](https://www.npmjs.com/package/@r2wc/react-to-web-component).
3. **A Java class** that wraps the tag with `ElementComposite` and gives it typed properties and events.

The third layer is the same wrapper you would write for any web component, as shown in [web components in Java](/blog/web-components-in-java).

## The build does the frontend work {#build}

The [frontend bundler](/docs/managing-resources/bundler/overview) keeps the frontend next to the Java class that needs it. On the wrapper class, `@BundlePackage` declares `react`, `react-dom` and the custom element library, and `@BundleEntry` names the entry file. The build installs the packages, compiles the entry and loads it when the component is created.

React needs no extra bundler extension, and the [webforJ build plugin](/docs/configuration/build-plugin) manages Bun for you. There is no Node toolchain to install and no separate frontend repository. The same bundler path covers [installing any npm package in a Java web app](/blog/npm-packages-java-web-app).

## Use it like any component {#use-it}

On the Java side, the wrapper exposes `setValue`, `setMax` and an `onRate` listener. From there the React widget behaves like any other component in a view.

```java title="ReviewView.java"
@Route("/review")
public class ReviewView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final StarRating rating = new StarRating().setMax(5).setValue(3);
  private final Paragraph label = new Paragraph("Current rating: 3");

  public ReviewView() {
    rating.onRate(e -> {
      rating.setValue(e.getStars());
      label.setText("Current rating: " + e.getStars());
    });

    self.add(rating, label);
  }
}
```

The click happens in React, the event arrives in the Java listener, and the listener updates a webforJ `Paragraph`.

## Keep the boundary small {#keep-it-small}

This approach suits a self-contained piece of UI. The wider options for [using JavaScript libraries from webforJ](/blog/webforj-javascript-integration) go beyond wrapping one tag.

- **One component per tag.** A React app that owns its own routing and state belongs in a separate frontend.
- **Pass small values.** Numbers, strings and small JSON objects cross the boundary well. Keep heavy logic in Java.
- **Check the built-in components first.** Reach for a wrapper when webforJ doesn't already provide what you need.

## Wrap your first React component {#next-step}

Pick one widget you already have in React and wrap it. The [Element Composite guide](/docs/building-ui/element-composite) covers the Java wrapper, and the bundler overview covers packages and entries.

## Frequently asked questions {#faq}

### Do I need Node installed? {#faq-node}

No. The webforJ build plugin manages Bun, installs the declared packages and compiles the entry during your normal Maven or Gradle build.

### How do events from React reach Java? {#faq-events}

The custom element dispatches a DOM event, and a listener on the Java wrapper receives it as a typed event.
