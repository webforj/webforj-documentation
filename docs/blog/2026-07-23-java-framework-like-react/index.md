---
title: "A Java framework like React: components, state, routing"
description: "Looking for a Java framework like React? webforJ gives you components, events, state and routing without page reloads, all written in Java on the server."
slug: java-framework-like-react
date: 2026-07-23
authors: webforJ
tags: [insights]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-07-23-java-framework-like-react/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-07-23-java-framework-like-react/cover.jpg)

You know React's ideas well: build small components, compose them into screens, keep state next to the UI that uses it, and move between views without the page reloading. Now your team works in Java and you want those same ideas without writing a second app in JavaScript. A Java framework like React exists, and it is webforJ, the full-stack Java framework. You write components as Java classes, handle events with listeners, route URLs to components, and get a single page app in the browser, all from one Java codebase.

The one real difference is where the code runs. In webforJ the UI lives on the server in Java and the browser renders it. The rest of this post maps what you know from React onto webforJ.

<!-- truncate -->

:::tip See it in a real app
Want to see these ideas in a working app? The [Todo app](https://github.com/webforj/built-with-webforj/tree/main/webforj-todo) in built-with-webforJ splits its UI into small reusable components for the item, the list and the footer, the way you would in React. Clone it, run `mvn`, and read it next to the [docs](/docs/building-ui/composing-components).
:::

## Java version of a React component {#java-version-of-a-component}

On react.dev, a component is described as a piece of UI with its own logic and appearance, written as a JavaScript function that returns markup. In webforJ the same unit is a Java class that extends `Composite`. You pick a bound component such as `FlexLayout` or `Div`, and the class is the reusable piece you drop into other views.

Here is a complete small component from the docs samples. It holds a counter, shows it, and updates it on click:

```java title="ButtonEventView.java"
@Route
@FrameTitle("Button Event")
public class ButtonEventView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private int counter = 0;
  private final Div text = new Div("Current Counter: 0");
  private final Button button = new Button("Click Me!");

  public ButtonEventView() {
    button
        .setWidth("150px")
        .onClick(
            e -> {
              text.setText("Current Counter: " + (++counter));
            });

    self.setDirection(FlexDirection.ROW)
        .setSpacing("var(--dwc-space-l)")
        .setPadding("var(--dwc-space-l)")
        .add(button, text);
  }
}
```

There is no JSX and no template language. The structure is the Java code, so your IDE, your refactoring tools and your compiler all see the UI. webforJ builds UIs through composition: you assemble new components out of existing ones instead of extending them, as [Composing components](/docs/building-ui/composing-components) explains.

## Composing components in Java {#composing-components}

You compose the way you do in React: a component contains other components. A parent view creates `TextField`, `Button` and `Table` objects, or your own `Composite` classes, and adds them to a layout with `add(...)`. Because each custom component is a normal class, you pass data in through constructors and setters, and you read results out through getters and events.

A form made of a name field, an email field and a submit button is one class. Put it in three different views and you reuse it three times. Layout is handled by components too, so you arrange children with `FlexLayout` rather than a separate stylesheet for every screen. The [Understanding Components](/docs/building-ui/component-fundamentals) page covers the component hierarchy and lifecycle.

## Where state lives in a Java UI {#where-state-lives}

In React, you reach for the `useState` Hook, which react.dev describes as the way a component remembers information between renders. webforJ has no render cycle to replay, so you do not need that machinery. State is a field on your Java object, like `counter` in the preceding sample. When something changes, you call the setter on the component that shows it, and the browser updates.

That has a practical effect. A value your service layer holds, a list loaded from a database and the text in a label are all ordinary Java values in the same process. You do not serialize them into JSON for a client store and you do not keep two copies in sync. For state that should survive navigation or show up in the address bar, the routing layer helps, which the next sections cover.

## Events and listeners {#events-and-listeners}

React passes handler functions as props like `onClick`. In webforJ you attach a listener to the component. Most components expose an `addXxxListener` method and a shorter `on` alias, so a `Button` has both `addClickListener` and `onClick`, as the counter sample shows. You can pass a lambda or a method reference, and the handler receives an event object with the payload.

Adding a listener returns a `ListenerRegistration`, so you can remove it later. The handler is plain Java running next to your services. If a click should save an order, the listener calls the order service directly. The [Events page](/docs/building-ui/events) shows payloads, element events, and custom events.

## Routing without page reloads {#routing-without-reloads}

Yes. webforJ routing is client-side navigation: the router swaps views as the URL changes, without a full page reload. A route is a component with an annotation:

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // Component logic here
}
```

Navigating to `/user/123` renders `UserView`. That gives you deep links, bookmarks, and a working back button. You can also move to a view from code with `Router.getCurrent().navigate(...)`, and reflect state in the URL with `BrowserHistory` using `pushState` and `replaceState`. If you want a guided tour, [Master webforJ routing in 10 minutes](/blog/master-webforj-routing-in-10-minutes) walks through it, and the [routing overview](/docs/routing/overview) lists every option.

## A Java single page app {#single-page-application}

Behaviorally, yes. The browser loads the app once, then the router changes the visible view in place. The URL changes with each view, and the app does not reload. If you searched for a [Java single page app framework](/blog/java-single-page-application-framework), this is what you get, minus the second codebase that a typical JavaScript front end brings.

The part that stays on the server is your logic. Views, handlers and service calls are Java. The browser receives the rendered interface and sends user actions back. Because there is no separate client app talking to a REST API for the UI, you skip the API layer whose only job is to feed your own screens.

## Reusing web components {#reusing-web-components}

If you use a library of web components, you do not have to give it up. `ElementComposite` wraps a custom HTML element in a Java class. You declare the tag with `@NodeName`, load assets with `@JavaScript` or `@StyleSheet`, and expose its properties as typed methods.

The docs build a full wrapper for a [Web Awesome](https://webawesome.com/docs/components/relative-time/) component this way, and the result is a typed Java API that composes like any other component. See [Element Composite](/docs/building-ui/element-composite) for properties, attributes, and events. That covers the question of keeping the web components you already like.

## The real differences from React {#what-is-different}

Three differences matter, and they are worth stating plainly.

1. **Where the UI code runs.** React components run in the browser, and react.dev describes React as a library for building user interfaces from components. webforJ components run on the server in Java, and the browser renders them as web components.
2. **How many languages you write.** A React front end is JavaScript or TypeScript, so a Java team ends up with two stacks, two build chains and an API between them. In webforJ the UI, the logic and the data access are Java.
3. **Connectivity.** Because the server drives the UI, the app needs a connection to run. If your users must work fully offline, a browser-side framework fits that need better.

Everything else on the list, components, composition, events, state and routing, has a direct counterpart. For a team of Java developers building business apps, that trade is the point: you keep the component model and drop the second language.

## Which should you pick {#which-to-pick}

| Your situation | Better fit |
| --- | --- |
| Java team, business app, one codebase wanted | webforJ |
| Logic and data already in Spring services | webforJ |
| UI must work fully offline | A browser-side framework |
| You want JSX and the React ecosystem itself | React |

If the first two rows describe you, webforJ gives you the component model you like in the language you already ship.

## Try it {#try-it}

Generate a project with [startforJ](https://docs.webforj.com/startforj), then follow [Getting started](/docs/introduction/getting-started). Add a `Composite`, give it an `@Route`, and you have a routed component in a few lines of Java.

## Frequently asked questions {#faq}

### Is there a Java alternative to React? {#faq-java-alternative-to-react}

Yes. webforJ lets you build the same kind of component-based UI entirely in Java. You get components, events, routing and single page behavior without writing JavaScript for your own screens.

### What is a Java single page app framework? {#faq-java-spa-framework}

It is a framework that loads the app once and swaps views in the browser without full page reloads. webforJ does this with its router, and your views, handlers and services stay in Java on the server.

### Do I need to know JavaScript to use webforJ? {#faq-need-javascript}

No. You build screens with Java classes and listeners. If you want to bring a web component into the app, `ElementComposite` wraps it so you use it through a Java API.

### How do I build a component-based UI in Java with webforJ? {#faq-component-based-ui}

Extend `Composite` with a bound component such as `FlexLayout`, add child components and listeners in the constructor, and annotate the class with `@Route` to make it a page. Reuse the class anywhere you need the same piece of UI.
