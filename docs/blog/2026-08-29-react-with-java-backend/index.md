---
title: "React with a Java backend: when it fits, and the all-Java option"
description: "React with a Java backend means two apps, an API and two builds. See what that costs, when it pays off, and the all-Java option with webforJ."
slug: react-with-java-backend
date: 2026-08-29
authors: webforJ
tags: [insights, spring, full-stack, web development]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-08-29-react-with-java-backend/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-08-29-react-with-java-backend/cover.jpg)

You have a Java team, a Spring Boot backend and a screen to build. Everyone says the next step is React, so you search for how to use React with Java and find a long list of tutorials that start the same way: build a REST API first.

React with a Java backend is a legitimate architecture, and for some products it is the right one. For most business apps built by Java developers it is more machinery than the job needs. webforJ, the full-stack Java framework, builds the screens in Java too, so a view calls your Spring services directly and the API layer between them is not needed for the screens.

<!-- truncate -->

This post is a decision guide. It lists what a React and Java stack involves in practice, when that stack earns its cost, and when an all-Java app is the better call.

## What React with Java involves {#what-react-with-java-involves}

When people say "Java and React", they usually mean two separate applications that agree on a contract.

The Java side is a Spring Boot app that exposes REST or GraphQL endpoints. The React side is a JavaScript project with its own package manager, build tool, test runner, and deployment. The two meet over HTTP, and everything that crosses that boundary has to be described twice.

In practice a single feature, say an editable customer list, touches these pieces:

- A controller, a service call and a DTO on the Java side, so the entity is never exposed directly.
- A matching TypeScript type, a fetch call, loading and error state and a component on the React side.
- Validation rules in Java and again in the browser, so users get fast feedback and the server stays safe.
- Authentication that works across both apps, usually with tokens or cookies and a login flow in the client.
- CORS configuration whenever the two apps run on different origins. The [MDN guide to CORS](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS) explains why the browser blocks those calls by default.
- Two builds, two dependency trees to patch and usually two deployment pipelines.

None of this is a defect. It is the price of a clean split between a client and a server. The question is whether your product needs that split.

## React and Java: what you pay for {#what-you-pay-for}

The cost is not the first screen. It is every screen after it, and every change that touches data.

| Concern | React with a Java backend | All Java with webforJ |
|---|---|---|
| Languages | Java and TypeScript | Java |
| Build and deploy | Two builds, often two pipelines | One Maven build, one deployable |
| Data transfer | Controllers, DTOs and client types kept in sync | The view calls a Spring service directly |
| Validation | Written in Java and again in the browser | Jakarta Validation annotations on the model, bound to the form |
| Authentication | Token or cookie flow shared by two apps | Spring Security and route annotations in one app |
| Cross-origin setup | CORS config when origins differ | None needed on one origin |
| Team shape | Often a Java team and a front end team | One Java team |
| Client-only features | Full control in the browser | Server-driven UI, with web components where you need them |

Two costs are easy to miss. The first is the contract. When a backend developer renames a field, the front end breaks at runtime, not at compile time, unless you add code generation and a process to run it. The second is the handoff, because a feature is not done until both halves exist. The post on [replacing React with Java](/blog/migrate-angular-react-to-java) maps React concepts to their webforJ counterparts if you want the screen-level detail.

## When React and Java is the right call {#when-react-is-the-right-call}

We build webforJ, and we still tell teams to choose the separate stack in these cases.

**A public consumer product.** If strangers on the open internet use the UI, you tend to want a static client served from a CDN, fast first paint and a public API that other clients can share. That is what a client-side app and an API are for.

**An existing React team.** If you already employ people who are productive in React and TypeScript, asking them to switch is a cost. The architecture should follow the team you have, and those developers can still write React components that a webforJ app uses.

**Rich client-only interactions.** A drawing canvas, a map with heavy panning, an editor with local undo, or a UI that must keep working with no connection to a server belongs in the browser. A server-driven UI sends interactions to your Java code, so it is a poor fit when the work should stay local.

**A mobile app on the same API.** If a native app, a partner integration and a web UI all consume one set of endpoints, build the API first and let React be one of its clients.

If two or more of these describe your product, take the React route and build it well. If none do, keep reading.

## The all-Java option with webforJ {#the-all-java-option}

Many Java business apps are internal tools, admin consoles, portals, back-office screens and line-of-business forms. The users are known, the data lives in your Spring services, and the front end exists to show it and collect changes. For that kind of app, we built webforJ so the whole app, screens included, is Java.

A screen is a class with a route. Spring injects the services it needs through the constructor, exactly as it would into any other bean. This view follows the pattern in [Spring routing](/docs/integrations/spring/routing):

```java title="DashboardView.java"
@Route("/dashboard")
public class DashboardView extends Composite<Div> {

  private final Div self = getBoundComponent();
  private final MetricsService metricsService;
  private final UserRepository userRepository;

  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;

    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());

    self.add(metricsPanel);
  }
}
```

There is no controller, no DTO, no fetch call and no client-side type. The view asks the service for data and puts it on the screen. The browser still renders real web components. Calls are batched, and the app loads only about 2.5 kB of gzipped JavaScript to start a session ([client and server interaction](/docs/architecture/client-server)).

## What disappears from the stack {#what-disappears}

Going back to the list from the first section, here is what happens to each item when the screens are Java.

**The API for the UI.** The view calls the service. If a mobile app or a partner needs endpoints later, a Spring Boot app can serve them next to the views.

**Duplicated validation.** You put Jakarta Bean Validation annotations on the model and bind the form to it. The [Jakarta validation adapter](/docs/data-binding/validation/jakarta-validation) reads them, and the same annotations drive the form's validation.

**The token dance.** Authentication is Spring Security with login views and annotation-based route protection, covered in the [security getting started guide](/docs/security/getting-started). Spring Security manages the user session, so there is no token storage or refresh logic to write in the client.

**CORS.** One app on one origin has no cross-origin calls to configure.

**The second language and the second build.** The team that owns the service owns the screen. One `mvn` build produces the app. Routing, layouts, tables, forms and security work with the same [Spring Boot](/docs/integrations/spring/spring-boot) setup, so you add features by writing Java, not by coordinating two repositories.

## How the two stacks differ in practice {#how-they-differ}

An honest comparison needs the downsides of both sides.

The separate stack gives you a front end that can be cached, deployed separately and served to many kinds of client, plus a large pool of React developers and libraries.

The all-Java stack gives you less to build and less to keep in sync. A change to a field is a change to one class that the compiler checks across the app. The trade-off is that the UI is driven from the server, which is the wrong shape for work that must stay entirely in the browser. When you need browser-side code, you can [run JavaScript from Java](/docs/building-ui/execute-javascript) and reuse web components.

A rough rule: if most of your screens are forms, tables, filters and dashboards over data your Java services already own, the API between them is overhead. If most of your screens are interactions that never need the server, the browser should own them.

## Can you mix them? {#can-you-mix-them}

Yes, and in two ways.

**React components inside a webforJ app.** The [frontend bundler](/docs/managing-resources/bundler/overview) lets you write a component in React and use it from Java, with no Node toolchain to install. You wrap the React component as a custom element, the bundler compiles it with your Maven build, and your Java views place it like any other component. The same bundler pulls packages from npm, so a component library your team already likes can come along. React stays where it adds something, and the app around it stays in Java.

**Two apps side by side.** Keep the public, consumer-facing part as a React app on a public API. Build the internal screens, such as admin, back office and reporting, as an all-Java webforJ app in the same Spring Boot project that serves that API. You get the right tool for each audience, and your internal team stops maintaining a second front end for screens only staff will ever see.

If you already have a React front end for an internal tool, move one screen at a time, starting with a read-only list, and let old and new screens live side by side.

## Build one screen in Java {#build-one-screen-in-java}

The cheapest way to decide is to build a single screen both ways and count the files. Pick one table or form from your current backlog, generate a project with [startforJ](https://docs.webforj.com/startforj) with Spring Boot selected, and inject the service you would have wrapped in a controller. The [getting started guide](/docs/introduction/getting-started) takes you from an empty folder to a running view.

For the full set of choices next to React, see the comparison of [Java frontend frameworks](/blog/java-frontend-frameworks).

## Frequently asked questions {#faq}

### Can you use React with a Java backend? {#faq-can-you-use-react-with-java}

Yes. The usual setup is a Spring Boot app that exposes REST or GraphQL endpoints and a separate React app that calls them over HTTP. It works well when the UI is public, when several clients share the API, or when a React team already exists.

### Can I use React components in webforJ? {#faq-react-in-webforj}

Yes. The webforJ [frontend bundler](/docs/managing-resources/bundler/overview) lets you write a component in React, wrap it as a custom element, and place it in your Java views like any other component. It also pulls UI libraries from npm, and it needs no Node toolchain on your machine.

### Do I need React with Spring Boot? {#faq-need-react-with-spring-boot}

No. Spring Boot does not require a JavaScript front end. You can build the screens in Java with webforJ in the same Spring Boot project and call your services from the views directly, so no REST layer for the UI is needed.

### What is the difference between React and Java? {#faq-react-vs-java}

They are not direct competitors. React is a JavaScript library for building user interfaces in the browser, and Java is a general-purpose language that usually runs the server side. The real comparison is between a stack with a React client and a Java API, and a stack where the screens are written in Java as well.

### Is it hard to add a webforJ screen to an existing Spring Boot app? {#faq-add-webforj-to-spring-boot}

You add the webforJ dependency, annotate a class with `@Route`, and Spring injects your existing services into it through the constructor. Your services, repositories, and configuration stay as they are. See the [Spring integration overview](/docs/integrations/spring/overview) for existing applications and the [Spring Boot guide](/docs/integrations/spring/spring-boot) for new projects.
