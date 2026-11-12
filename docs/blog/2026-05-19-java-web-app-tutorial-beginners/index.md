---
title: "Java web app tutorial for beginners: build your first app"
description: "A Java web app tutorial for beginners. Generate a project, run it, read the first view and wire a button click, all in plain Java with webforJ and Maven."
slug: java-web-app-tutorial-beginners
date: 2026-05-19
authors: Eric Handtke
tags: [tutorial, routing]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-05-19-java-web-app-tutorial-beginners/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-05-19-java-web-app-tutorial-beginners/cover.jpg)

You know some Java, you have written console programs or a few classes, and now you want to see something running in a browser. Most guides at this point throw HTML, JavaScript, a template engine and a build pipeline at you before the first page shows up.

This java web app tutorial for beginners takes a shorter path. With webforJ, a full-stack Java framework, you build the page in Java, generate a working project in a minute and run it with one Maven command. By the end you will have a first Java web app on screen, a button that reacts to clicks, and a clear idea of where to go next.

<!-- truncate -->

## What you need before you start {#what-you-need}

Two tools are enough:

- A JDK, version 21 or higher.
- Maven, to build and run the project.

Run `java -version` and `mvn -v` in a terminal to check both. If one is missing, the [prerequisites](/docs/introduction/prerequisites) page lists where to download each and how to install them. Any editor with Java support works, such as IntelliJ IDEA or Visual Studio Code.

You do not need to know HTML, CSS or JavaScript to follow along.

## Step 1: Generate the project {#step-generate}

The quickest way to make a website with Java is to let a generator create the skeleton. Open [startforJ](https://docs.webforj.com/startforj) in your browser and fill in a few fields:

1. Give the app a name, a group ID and an artifact ID.
2. Pick the **HelloWorld** archetype. It is the starter made for beginners.
3. Leave **Runs on** at its default, the servlet container.
4. Download the ZIP and open the folder in your editor.

Prefer the terminal? The [getting started](/docs/introduction/getting-started) page also shows the Maven command that generates the same project. The [HelloWorld archetype](/docs/building-ui/archetypes/hello-world) page describes what the starter contains.

## Step 2: Start the app {#step-run}

Open a terminal in the project folder, the one that holds `pom.xml`, and run:

```bash
mvn
```

A generated project sets its default Maven goal, so plain `mvn` compiles the code and serves the app on an embedded Jetty server. If you want the explicit form, `mvn jetty:run` starts the same server. The [Jetty page](/docs/configuration/deploy-reload/maven-jetty-plugin) explains what each part does.

Open the address the console prints and you will see a text field and a button. That is a working first Java web app, and you have not written a line yet.

## Step 3: Read the entry class {#step-entry-class}

Every webforJ app has one class that extends `App`. In the starter it is called `Application`:

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

The class body is empty because the annotations do the work:

- `@Routify` tells webforJ which package holds your views.
- `@AppTitle` sets the title on the browser tab.
- `@StyleSheet` links a CSS file for custom styling.

Change the `@AppTitle` text, save, and reload the page to see the tab title change. Small edits like this are the fastest way to learn what each piece does.

## Step 4: Read the first view {#step-first-view}

The page you saw in the browser comes from a view class. A view is a normal Java class that builds its UI with components:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e ->
          Toast.show("Welcome to webforJ Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

Take it in small pieces:

- `@Route("/")` maps this class to the home URL.
- `Composite<FlexLayout>` makes the class a reusable component built around a layout. `getBoundComponent()` gives you that layout.
- `TextField` and `Button` are webforJ components. You create them with `new`, like any Java object.
- `self.add(hello, btn)` puts them on the page.

The layout, the field and the button all live in one Java file. No template sits next to it and no second language is involved. The [app basics](/docs/introduction/basics) page walks through both classes in more detail.

## Step 5: Handle a button click {#step-button-click}

Look at the middle of the constructor. The button gets a click listener with `addClickListener`, and the listener shows a toast that reads the text field with `hello.getValue()`.

Here is what happens when you click:

1. The browser reports the click to your Java code.
2. Your lambda runs on the server, in Java.
3. The lambda reads the field's value and shows the toast.

You never write a fetch call or an endpoint. The event handler is plain Java, so you can use the debugger, set breakpoints and reuse your own classes inside it.

Try changing the message in the lambda, or change the button label in the `Button` constructor. Reload and click. Each change takes seconds, which is the point of starting with a tiny app.

## Step 6: Add a second page {#step-second-page}

A real app has more than one page. Each view class carries its own `@Route`, and `@Routify` finds them in the views package. To add a page, create another class next to the first, give it a different route such as `@Route("about")`, and build its content the same way.

Routes also support parameters, nesting and navigation guards when your app grows. The [routing overview](/docs/routing/overview) covers them.

## What to build after this {#where-next}

This small Java web example has the shape of every webforJ app: one entry class, any number of views, components added in Java and events handled in Java. From here, pick the direction that matches what you want to build:

| You want to | Read |
| --- | --- |
| Add more pages and links between them | [Routing](/docs/routing/overview) |
| See every ready-made component | [UI components](/docs/components/overview) |
| Go deeper on the button you just used | [Button](/docs/components/button) |

When you are ready for a longer build, the documentation also has a step-by-step tutorial that grows a customer management app, starting from [project setup](/docs/introduction/tutorial/project-setup).

## Next step {#next-step}

Generate your own project at [startforJ](https://docs.webforj.com/startforj), run `mvn`, and change one thing in the first view. Then follow the [getting started guide](/docs/introduction/getting-started) for the remaining options.

## Frequently asked questions {#faq}

### Do I need to learn JavaScript to build a Java web app? {#faq-javascript}

No. In webforJ you write the UI, the layout and the event handling in Java. The browser side is handled by the framework, so a beginner can [stay in one language](/blog/java-web-app-without-javascript) from the first view to a finished app.

### What is the simplest way to make a website with Java? {#faq-simplest-way}

Generate a project with startforJ, run `mvn` and edit the starter view. That gives you a running page in minutes, with routing and components already in place, and you grow it one view at a time. If you are still comparing options, [eight checks for an easy Java web framework](/blog/easiest-java-web-framework) show what to look for.

### Which Java version and build tool does a first Java web app need? {#faq-requirements}

Java 21 or higher and Maven. Archetypes generate Maven projects, and an existing Gradle build also works. The prerequisites page lists the download links and the commands to check your setup.
