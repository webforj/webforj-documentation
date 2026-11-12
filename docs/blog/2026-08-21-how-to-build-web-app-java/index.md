---
title: "How to build a web app in Java, step by step"
description: "Build a web app in Java with Maven and webforJ. Generate a project, run it, add views and routes, then see where Spring and deployment fit. No front end stack."
slug: how-to-build-web-app-java
date: 2026-08-21
authors: webforJ
tags: [tutorial, spring, routing]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-08-21-how-to-build-web-app-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-08-21-how-to-build-web-app-java/cover.jpg)

You know Java well, you can write a service in your sleep, and then someone asks for a screen in the browser. Suddenly the advice is to learn a JavaScript framework, a bundler, a templating language and a REST layer between them. You don't need any of that. To build a web app in Java with webforJ, a full-stack Java framework, generate a project with Maven, write each screen as a Java class, and run it with one command. The whole app, UI included, stays in Java.

<!-- truncate -->

:::tip Learn from real apps
Learn best by reading working code? Browse [built-with-webforJ](https://github.com/webforj/built-with-webforj), a collection of small, complete apps, from a todo list to a CRUD app with Spring Data. Pick one, clone it, run `mvn`, and you have something to explore alongside the [docs](/docs/introduction/getting-started).
:::

This tutorial walks through that path from an empty folder to a running app with two pages. It's meant for Java developers who have never built a web UI.

## What you need {#what-you-need}

Two tools: a JDK and a build tool. webforJ requires Java 21 or higher, and any distribution at that version works. Archetypes generate Maven projects, so Maven is the quickest route. Check both:

```bash
java -version
mvn -v
```

Any editor with Java support is fine. The [prerequisites](/docs/introduction/prerequisites) page lists install options if something is missing.

## Why the whole app is Java {#why-java}

A typical web stack splits into a browser app and a server app that talk over an API. That means two languages, two build systems and a contract to keep in sync.

webforJ removes the split. You create components such as buttons, fields and layouts in Java. The app runs on the server, and the framework renders those components in the browser. When a user clicks a button, the click event reaches your Java listener. When you call `setText()` on a component, the framework sends the change to the browser. There is no JSX, no HTML template and no endpoint you write only to feed your own screen.

For a Java developer this is the short answer to how to make a website with Java: you keep the language, the IDE, the debugger and the type checker you already use.

## Generate a project {#generate-a-project}

You have two ways to start. The first is [startforJ](https://docs.webforj.com/startforj), a web wizard where you pick a name, versions, theme and archetype, then download a ZIP or publish to GitHub. Under **Runs on**, choose **Spring Boot** if you want an executable JAR with an embedded server.

The second is one Maven command. Pick a folder and run:

```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```

The `hello-world` archetype is a minimal greeting app, and it suits beginners. The `flavor` value `webforj-spring` produces a Spring Boot project. The full list of options is in [Getting Started](/docs/introduction/getting-started).

## Run it {#run-it}

Open the project folder and run Maven with no arguments:

```bash
mvn
```

An archetype project sets its default Maven goal, so this compiles the app and starts it. A Spring Boot app listens on port 8080 with an embedded Tomcat server. Open `http://localhost:8080` and you see the hello world page.

## The first view {#first-view}

A view is a Java class. This is the shape of the greeting view the sample app uses:

```java
@Route("/")
@FrameTitle("Hello World Java")
public class HelloWorldJavaView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Paragraph hello = new Paragraph("Hello World!");
  private final Button btn = new Button("Say Hello");

  public HelloWorldJavaView() {
    btn.setTheme(ButtonTheme.SUCCESS)
        .setExpanse(Expanse.XLARGE)
        .addClickListener(e -> showMessageDialog("Hello World!", "Do it all in Java"));

    self.setDirection(FlexDirection.COLUMN).addClassName("mainFrame").add(hello, btn);
  }
}
```

Read it top to bottom:

1. `@Route("/")` maps the class to the root URL.
2. `Composite<FlexLayout>` makes the class a reusable component built around a layout.
3. The fields are ordinary Java objects: a paragraph and a button.
4. The constructor styles the button, attaches a click listener and adds both components to the layout.

The listener is plain Java. There is no event name to look up in a script file, and no handler registered somewhere else. With [live reload](/docs/configuration/deploy-reload/spring-devtools) turned on, Java changes apply after a compile and the browser refreshes.

## Add a route and a second view {#add-a-route}

A real app has more than one page. The `@Route` annotation binds a URL path to a view class:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Component logic here
}
```

Create that class in the same package as your first view, and `/dashboard` renders it. A path can also carry a parameter, so `user/:id` matches `/user/123`. The [Defining Routes](/docs/routing/defining-routes) page covers aliases and patterns.

To move between views from code, for example inside a click listener, call the router:

```java
// navigate to the view
Router.getCurrent().navigate(DashboardView.class);
```

Passing the class instead of a URL string is the preferred form. If you later rename the path in the `@Route` annotation, every call that navigates by class keeps working. More options, including parameters, are on the [Route Navigation](/docs/routing/route-navigation) page.

## Where data and Spring fit {#data-and-spring}

Most apps need data, and this is where the Java-only approach pays off. A view is a Java object, so it can use any Java library. With the Spring Boot flavor you inject services and repositories into your views through constructor injection, the same way you do in a controller.

A view that lists customers asks a service for them and puts them into a table component. The service talks to a database through whatever you already use. There is no serialization step between your domain objects and your screen, because both live in the same language.

For Spring Data JPA, webforJ ships an adapter that wraps your existing repository so a table can page and sort through it. The [Spring Boot](/docs/integrations/spring/spring-boot) page shows how the two frameworks work together.

## Deploy the app {#deploy}

Deployment depends on the **Runs on** choice you made at the start.

With Spring Boot, the app is an executable JAR with an embedded server. Run `mvn package` and you get an app without development tools, with no exclusion, profile, or property to set up. Copy the JAR to a server, a container or a cloud platform and start it like any other Spring Boot app. The [production builds](/docs/configuration/deploy-reload/spring-devtools#production-builds) section has the details.

With the servlet container option, the output is a WAR for Jetty, Tomcat, or another servlet container. Either way you deploy a Java artifact. You never deploy a separate front end next to it.

## Why this beats a split stack {#why-webforj}

Here is how the two approaches compare for a Java developer starting a new app.

| Question | Java front end and back end | webforJ |
|---|---|---|
| Languages to learn | Java plus JavaScript or TypeScript | Java |
| Build tools | Maven plus a JavaScript toolchain | Maven |
| Reusing domain classes in the UI | Through an API layer | Directly |
| Debugging a click handler | Browser tools and server logs | Your Java debugger |
| Artifacts to deploy | Two | One |

You still get a modern browser UI with themes and a full component set. You just never write the browser half yourself.

## Java web app examples to try next {#java-web-app-example}

If you want a Java web app example beyond hello world, these apps from [built-with-webforJ](https://github.com/webforj/built-with-webforj) build on the same steps:

- [Todo](https://github.com/webforj/built-with-webforj/tree/main/webforj-todo): add, toggle, filter and delete items, with Spring Boot and an MVC structure.
- [Howdy](https://github.com/webforj/built-with-webforj/tree/main/webforj-howdy): several routed views with tab navigation, a table and a chart.
- [CRUD](https://github.com/webforj/built-with-webforj/tree/main/webforj-crud): a table with search, an edit dialog with validation and data binding, stored through Spring Data.

The other archetypes are worth a look too: the catalog has a side menu layout, a tabs layout, and a blank project. The step-by-step tutorial in the docs builds a customer app with a table, a form and data binding on top of the project you just generated. A [beginner tutorial for a first Java web app](/blog/java-web-app-tutorial-beginners) goes deeper on the first view and the button click.

Ready to build? Generate a project with [startforJ](https://docs.webforj.com/startforj) and follow [Getting Started](/docs/introduction/getting-started). Run `mvn`, change the greeting text in the view class, and save the file.

## Frequently asked questions {#faq}

### How do I build a web app in Java as a beginner? {#faq-beginner}

Install a JDK (version 21 or higher) and Maven, generate a project from the `hello-world` archetype, and run `mvn`. Open `http://localhost:8080` and edit the view class. You need no JavaScript to follow this path.

### Do I need to learn JavaScript to make a website with Java? {#faq-javascript}

No. With webforJ you build pages from Java components, and event handling is Java code. You can add JavaScript later if you want to wrap a browser library, but a normal app never requires it.

### Do I have to use Spring to build a web app with Java? {#faq-spring}

No. startforJ lets you choose a servlet container, which produces a WAR, or Spring Boot, which produces an executable JAR. Pick Spring when you want dependency injection, data access, and the rest of its ecosystem.

### What's the quickest Java web app tutorial path? {#faq-simplest}

Use the `hello-world` archetype. It gives you a running app with one view and a click listener in a single command. From there, add a second class with a `@Route` annotation and you have a multi-page app.
