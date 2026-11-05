---
title: "Replace React with Java: moving a front end to webforJ"
description: "Replace React with Java without rewriting your backend. See how a React or Angular front end maps to webforJ views, routes and Spring services in one project."
slug: migrate-angular-react-to-java
date: 2026-06-12
authors: webforJ
tags: [community, ai, spring, routing, modernization]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-06-12-migrate-angular-react-to-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-06-12-migrate-angular-react-to-java/cover.jpg)

Many Java teams run a React or Angular front end that only exists to show what their Spring backend already knows. Every feature gets built twice, once on each side of a REST layer, by two sets of people with two toolchains.

If you want to replace React with Java, or move from Angular to a Java UI, you can do it one screen at a time. Each screen becomes a webforJ view that calls your Spring services directly, and the front end and its REST layer fold into one Spring Boot project.

<!-- truncate -->

:::tip See a full Spring app
The [webforJ Bookstore](https://github.com/webforj/built-with-webforj/tree/main/webforj-bookstore) app on GitHub shows where a migration like this ends up: a sortable book table, a bound and validated edit form, search, and role-based pages, all in Java on Spring Boot. You only need Java and Maven to run it.
:::

This post walks through what changes, what stays, and how to migrate without a big-bang rewrite.

## Why replace a JavaScript front end? {#why-replace-a-javascript-front-end}

A typical Java shop running React or Angular maintains two applications. One is a Spring backend that exposes endpoints. The other is a TypeScript project with its own package manager, build, state management, and test setup. Every feature crosses the boundary twice: a controller and a transfer object on one side, a client, a type, and a component on the other.

With webforJ the UI is Java. A view can take a Spring service through its constructor and call it. There is no serialization contract to keep in sync because there is no second codebase to sync with.

We built webforJ for exactly this team: Java developers who want a modern browser UI without becoming a second, parallel front end team.

## What does a React screen become? {#what-does-a-react-screen-become}

Most of the concepts you rely on have a direct counterpart:

| In React or Angular | In webforJ |
|---|---|
| Component | A Java class, or a `Composite` that groups other components |
| Router and route params | `@Route` on a view class, with [route patterns and parameters](/docs/routing/overview) |
| `fetch` to a REST endpoint | A Spring bean injected into the view |
| Paged, sortable list | `Table` backed by `SpringDataRepository` |
| Form state and validation | Data binding with `BindingContext` |
| Calling a browser API | `executeJs` from Java |

A sortable list that would normally take a component, a service hook, an endpoint, and a transfer object is one class in webforJ. This is the example from the [Spring Data JPA guide](/docs/integrations/spring/spring-data-jpa):

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Wrap Spring Data repository for webforJ
    repository = new SpringDataRepository<>(personRepository);

    // Connect to table
    table.setRepository(repository);

    // Define columns
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Sort by actual JPA property
    table.addColumn("email", Person::getEmail);
    table.addColumn("city", Person::getCity);

    // Enable sorting
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

`PersonRepository` is an ordinary Spring Data JPA repository. [`SpringDataRepository`](/docs/integrations/spring/spring-data-jpa) adapts it to the table, so paging and sorting go through your existing repository without a controller in between.

## How does the browser side work? {#how-does-the-browser-side-work}

The UI still runs in the browser, and it is still built from web components. The difference is who drives it. Your Java code runs on the server and webforJ sends the browser the instructions to render and update the components. Calls such as `setText()` are batched and sent without waiting for an answer, and user events travel back to your handlers.

The initial load is small. The app loads about 2.5 kB of gzipped JavaScript to bootstrap the session, then fetches the code for each component the first time the app uses it. The [client and server interaction](/docs/architecture/client-server) page covers the details, including which calls cost a round trip to the browser.

You do not lose access to the browser either. When you need something the framework doesn't wrap, [`executeJs`](/docs/building-ui/execute-javascript) runs JavaScript from Java and returns the result.

## Can I move one screen at a time? {#can-i-move-one-screen-at-a-time}

Yes, and it is the plan we recommend. You do not need to freeze feature work for a rewrite.

Spring Boot with webforJ runs as a single executable JAR with an embedded Tomcat server. When webforJ is mapped to the root context, the `webforj.exclude-urls` property lists URL patterns that Spring MVC keeps. That lets your existing `@RestController` endpoints keep running next to webforJ routes, so mobile clients, partners and any screens still in React keep working while you migrate.

A practical order:

1. Start a webforJ project with Spring Boot from [startforJ](https://docs.webforj.com/startforj), or add the integration to your current Spring app.
2. Pick the screen with the most Java logic and the least custom client code. Back-office lists and forms are usually first.
3. Rebuild it as a view and inject the service your controller already calls.
4. Retire the endpoint once nothing else consumes it.
5. Repeat until the last JavaScript screen is gone.

Setup details live in the [Spring Boot integration guide](/docs/integrations/spring/spring-boot), and the [Spring routing page](/docs/integrations/spring/routing) covers how views receive injected beans.

## Can AI help with the migration? {#ai-migration}

Yes, and a screen-by-screen migration suits it well. Each screen is a small, self-contained job: hand an AI coding assistant one React or Angular component, the service it calls, and ask for the matching webforJ view.

The [webforJ AI plugin](/docs/ai-tooling) makes that output trustworthy. It connects your assistant to the [webforJ MCP server](/docs/ai-tooling/mcp), so it looks up real webforJ classes and methods instead of guessing them, and adds [Agent Skills](/docs/ai-tooling/agent-skills) that walk it through tasks such as forms and styling the way webforJ expects. It works with Claude Code, Copilot, Cursor, Gemini, and Codex.

You still review each screen before the old one is retired, but the repetitive part of the port, rewriting markup and state as Java components, goes much faster.

## What happens to my React logic? {#what-happens-to-my-react-logic}

Business rules that lived in the browser move to Java. This is usually a simplification: validation you duplicated on the client and the server becomes one set of rules. State you kept in a store becomes fields on a view or a Spring bean.

A form action that used to be a click handler, a POST request and a success message becomes a button listener in Java. The listener runs in your Java process, calls the injected service directly, and shows the result. Security checks, transactions and logging stay where your backend already enforces them.

## Where the migration is not trivial {#where-the-migration-is-not-trivial}

A few honest points before you plan:

- **Custom client code.** A heavily animated canvas or a niche JavaScript widget needs a deliberate decision. Wrap it as a web component, call it through `executeJs`, or keep it as a separate page during the transition.
- **Styling.** Your CSS carries over as CSS, but component markup differs, so expect to restyle against webforJ components and their theme tokens.
- **Team habits.** The mental model shifts from client state and API calls to server-side views. Front end specialists need time to learn the server-side view model.

## Which choice fits your team? {#which-choice-fits-your-team}

Keep React or Angular if your front end team is large, your UI is the product, and it must work offline as a standalone client. Move to webforJ if your team is mostly Java, your screens are forms, lists, dashboards and workflows over a Spring backend, and the REST layer exists only to feed your own UI. For that second team, webforJ removes the duplicated work and keeps everything in the language you already staff for.

For a worked example of a full app on this stack, see the [webforJ bookstore demo](/blog/webforj-bookstore).

## Next step {#next-step}

Generate a Spring Boot project in [startforJ](https://docs.webforj.com/startforj) or follow [getting started](/docs/introduction/getting-started), then rebuild your smallest list screen as a view. If the repository-to-table result looks like the preceding class, you have your migration pattern.

## Frequently asked questions {#faq}

### How do I replace React with Java? {#how-do-i-replace-react-with-java}

Create a webforJ project with Spring Boot, then rebuild each React screen as a Java view with a `@Route`. Inject the Spring services your REST endpoints already call and drop the endpoints once no other client uses them.

### Can I move from Angular to a Java UI without a rewrite? {#can-i-move-from-angular-to-a-java-ui-without-a-rewrite}

Yes. Run webforJ beside your existing Spring MVC controllers and migrate screen by screen. Existing REST endpoints keep working while Angular screens remain until their replacements ship.

### Can I drop the JavaScript front end in a Spring Boot app? {#can-i-drop-the-javascript-front-end-in-a-spring-boot-app}

Yes. In webforJ the UI is written in Java and runs in the same Spring Boot app as your services, so there is no separate front end project to build or deploy. The browser still renders web components, but you do not write or maintain that layer.

### Is using Java instead of React slower for users? {#is-using-java-instead-of-react-slower-for-users}

The browser loads about 2.5 kB of gzipped bootstrap JavaScript, then fetches component code on demand. webforJ batches server-to-client updates, and you avoid round trips by reading values from events instead of querying components.
