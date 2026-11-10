---
title: "Java single page application framework: webforJ routing"
description: "Looking for a Java single page application framework? webforJ gives you one page load, client-side routes, history and deep links, all written in Java."
slug: java-single-page-application-framework
date: 2026-06-04
authors: webforJ
tags: [insights, routing, performance]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-06-04-java-single-page-application-framework/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-06-04-java-single-page-application-framework/cover.jpg)

You want an app that feels like a single page app: no white flash between screens, a working back button, links people can bookmark. You also want to keep your team in Java instead of adding a second codebase in JavaScript. A Java single page app framework has to cover three things: load the page once, change views in the browser without a reload, and keep the URL honest. webforJ does all three. Every webforJ app is a single page app, and the routes, parameters and navigation are written in Java.

<!-- truncate -->

## What makes an app a single page app {#what-makes-an-spa}

A single page app has one document load. After that, the browser swaps views in place instead of requesting a new HTML page for each link. The reader gets fast transitions, and the app keeps its state between screens. The router in webforJ is built around this model: a single initial request and URL management, then dynamic component rendering, with app state preserved between navigations.

In a traditional server-rendered site, each click asks the server for a new document, and the browser re-evaluates CSS and JavaScript. In webforJ, the browser loads the app once. The router listens for URL changes, maps the URL to a component and renders it without refreshing the page. Because routes are tied to URLs, deep links and bookmarks still work.

## How to build an SPA without JavaScript {#build-an-spa-without-javascript}

You declare a route on a Java class. The class is the view.

```java
@Route(value = "user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("");
    refreshProfile(id);
  }
}
```

Opening `/user/john` renders this component and hands `john` to your code as the `id` parameter. There is no template to wire up and no client-side router to configure. The [defining routes](/docs/routing/defining-routes) page covers `@Route`, route aliases and the patterns you can match.

## Java SPA routing in practice {#java-spa-routing}

Java SPA routing in webforJ comes down to a handful of building blocks, each documented in the routing section.

- **Route parameters.** Segments such as `:id` arrive in a `ParametersBag` with typed getters. See [route patterns](/docs/routing/route-patterns).
- **Query parameters.** Values such as `?category=electronics&sort=price` are read from the `Location` through the same bag. See [query parameters](/docs/routing/query-parameters).
- **Nested routes.** A parent route holds the shell, such as a header and a drawer, and child routes render in its outlet. The shell stays in place while the content changes. See [nested routes](/docs/routing/route-hierarchy/nested-routes).
- **Lifecycle observers.** Interfaces such as `WillEnterObserver` and `WillLeaveObserver` let a view fetch data before it appears or stop a user from leaving with unsaved changes.
- **Transitions.** The router can animate the move between views, covered in [Animated Transitions, No JavaScript Required](/blog/view-transitions).

For a quick tour of the annotation side, [Master webforJ routing in 10 minutes](/blog/master-webforj-routing-in-10-minutes) walks through it.

## Navigation without a page reload {#navigation-without-reload}

Moving to another view from code is one call on the router. Pass the view class and the router builds the URL.

```java
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

This renders the view and updates the browser URL to `/user/JohnDoe`. Passing the class is the preferred form, because the URL comes from the route definition and a renamed path can't leave a stale string behind.

## Back button and deep links {#back-button-and-deep-links}

Each navigation adds an entry to the browser history by default, so the back and forward buttons work as people expect. `NavigationOptions` lets you choose between `PUSH` and `REPLACE` when a step should not become a history entry, and lets you skip history updates entirely. Because every view has a URL, a user can paste a link to `/user/JohnDoe` into a new tab and land on that view directly.

If you need finer control over the address bar, the [state management](/docs/routing/state-management) page covers `pushState` and `replaceState` through the router history.

## Why the URL matters {#why-the-url-matters}

An SPA that ignores the address bar frustrates people. They copy a link, send it to a colleague and the colleague lands on the home screen. In webforJ the URL is the source of truth for which view is on screen. A route such as `products` can also read `category` and `sort` from the query string, so a filtered list can be shared as a link. That is why the same route definition serves the browser's address bar, the back button and your own `navigate` calls.

## Fast first load {#fast-first-load}

A single page app is only pleasant if the first load is light. A webforJ app loads about 2.5 kB of gzipped JavaScript to bootstrap the session. After that, the client downloads code on demand as the app uses it. The client only receives the JavaScript for a `Button` once, when the app creates its first `Button`. The details are on the [client/server interaction](/docs/architecture/client-server) page.

## When webforJ fits, and when it doesn't {#decision-guide}

Use this guide to decide.

| Your situation | What to pick |
|---|---|
| Your team writes Java and the app is a business tool, portal or admin UI | webforJ. One language, one codebase, routing included |
| You need deep links, bookmarks and a working back button | webforJ. Routes map directly to URLs |
| You want shared layouts such as a header and a drawer | webforJ. Nested routes with outlets |
| Your team already ships a large JavaScript front end and wants to keep it | Keep it. webforJ is for teams that want the whole app in Java |

If the first three rows describe your project, you can skip the JavaScript front end and its separate build.

The trade-off is plain. webforJ keeps the view logic on the server, so the Java code you write for a route runs there and the browser renders the result. A team that wants every screen to live in client-side JavaScript has a different architecture in mind. A team that wants to ship routed screens, forms and tables from Java does not need to carry two stacks.

## Next step {#next-step}

Generate a starter project at [startforJ](https://docs.webforj.com/startforj), add a second `@Route` class, and call `Router.getCurrent().navigate(...)` from a button. The [routing overview](/docs/routing/overview) explains the model, and the [route navigation](/docs/routing/route-navigation) page lists every navigation option.

## Frequently asked questions {#faq}

### Is webforJ a single page app framework? {#faq-is-spa}

Yes. A webforJ app loads once, and the router swaps views in the browser without a page reload. Routes, parameters and navigation are all written in Java.

### Can I build an SPA without writing JavaScript? {#faq-no-javascript}

Yes. You declare views as Java classes with `@Route` and move between them with `Router.getCurrent().navigate(...)`. The browser side is handled by the framework.

### Does the back button work in a webforJ app? {#faq-back-button}

Yes. Navigation adds entries to the browser history by default, so back and forward behave normally. You can switch a step to `REPLACE` through `NavigationOptions` when it should not add an entry.
