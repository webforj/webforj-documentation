---
title: "JBang Java web app: a webforJ UI in a single file"
description: "Build a JBang Java web app in one file with webforJ. Write the UI in Java, start it with one jbang command, and move to Maven when the prototype grows."
slug: jbang-java-web-app
date: 2026-09-23
authors: Matthew Hawkins
tags: [integrations, tutorial, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-23-jbang-java-web-app/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-23-jbang-java-web-app/cover.jpg)

Sometimes you want to try an idea in a browser without setting up a project first. No `pom.xml`, no folder layout, no build tool, just a file you can run, share in a chat, or throw away when you're done.

A JBang Java web app gives you exactly that. With webforJ, one `.java` file holds the whole web UI, built from Java components instead of HTML templates. JBang compiles it, starts the server and opens your browser with one command.

<!-- truncate -->

In this post you'll run the hello world app from the webforJ docs, see what each part of the file does, and learn when a single file stops being the right size.

## Why build a web app in one file? {#why-one-file}

A standard webforJ project uses Maven or Gradle. It has a build file and a folder layout, and that setup suits production apps. For a demo, a workshop exercise, or a quick test of an idea, the setup can take longer than writing the UI.

JBang runs Java source files the way a script runs. The webforJ JBang starter arrived in 25.11. It bundles what a webforJ app needs to run: the Spring Boot starter, the development tools, and automatic browser opening. The file declares that one dependency at the top, so anyone you send the file to can run it with one command, as long as they have JBang installed.

## How do you install JBang? {#install-jbang}

[JBang](https://www.jbang.dev/) is a separate tool, so install it first. On macOS, Homebrew works:

```bash
brew install jbangdev/tap/jbang
jbang --version
```

If no JDK is installed, JBang downloads one on its first run. To choose the version and vendor, set `JBANG_DEFAULT_JAVA_VERSION` and `JBANG_JDK_VENDOR` before you run it. The example below asks for Java 21.

## How to build a JBang Java web app {#build-the-app}

Create a file named `HelloWorld.java` and paste in the example from the [JBang docs](/docs/integrations/jbang). The import lines are left out of the listing here, so your editor adds them as you type, and the docs page has the full file:

```java title="HelloWorld.java"
///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.webforj:webforj-jbang-starter:26.02
//JAVA 21

package bang;

@SpringBootApplication
@Routify
public class HelloWorld extends App {
  public static void main(String[] args) {
    SpringApplication.run(HelloWorld.class, args);
  }
}

@Route("/")
class MainView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("Welcome to webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

## What does the code do? {#what-the-code-does}

The file holds two classes with separate jobs. `HelloWorld` starts the app. It extends `App`, which makes it a webforJ app. `@SpringBootApplication` sets up Spring Boot, and `@Routify` on the same class makes webforJ scan for routes. The `main` method hands control to `SpringApplication.run`.

`MainView` is the page. It extends `Composite<FlexLayout>`, and `getBoundComponent()` returns the layout the composite is bound to. The text field and the button go into that layout. The `@Route("/")` annotation makes this class the home page, and the button's click listener shows a toast that greets the name you typed. The UI is written entirely in Java, so the file contains no HTML form and no JavaScript.

## What do the directive lines do? {#directive-lines}

The lines at the top of the file that start with `//` are JBang directives. Each one gives JBang an instruction before it compiles the code:

| Line | What it does |
|---|---|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Lets you run the file directly on macOS and Linux |
| `//DEPS com.webforj:webforj-jbang-starter:26.02` | Fetches the webforJ starter from Maven Central |
| `//JAVA 21` | Sets the Java version, and JBang downloads one if needed |

This example uses 26.02, the current release at the time of writing. The JBang starter arrived in 25.11, so any version from 25.11 on works. The starter line is the only dependency the app needs.

## How do you run the script? {#run-the-script}

Run the file from a terminal:

```bash
jbang HelloWorld.java
```

On the first run, JBang downloads the dependencies, compiles the file, starts the embedded server on a random available port, and opens your default browser to the app. The download happens only on that first run.

The video below shows two single-file apps started this way: a counter, and a process manager that lists and stops processes on the machine.

<div class="videos-container">
  <video controls muted preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/blogs/2026-09-23-jbang-java-web-app/jbang-muted.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

On macOS and Linux you can also make the file executable with `chmod +x HelloWorld.java`, then start it with `./HelloWorld.java`. The shebang line is what makes that work.

## How do you add a dependency? {#add-a-dependency}

Each Maven artifact gets its own `//DEPS` line in `groupId:artifactId:version` form. Put the new line below the starter line. For example, `//DEPS com.google.code.gson:gson:2.11.0` adds Gson to the script, and JBang fetches it from Maven Central on the next run.

## How do you add a second page? {#second-page}

Each class with a `@Route` annotation becomes a page, and its route path becomes the URL. Add another class with, for example, `@Route("/about")` below `MainView` in the same file. Both routes belong to the same app, so one server answers `/` and `/about`.

## When does the server shut down? {#server-shutdown}

By default, the server shuts down after the last browser tab connected to the app closes. It waits 5 seconds before stopping, which is the default for the `webforj.jbang.idle-timeout` property. Two system properties control this behavior:

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

The first line waits 30 seconds after the last tab closes. The second turns auto-shutdown off, so the server keeps running until you stop it with `Ctrl+C`.

## What are the limits of one file? {#limits}

JBang scripts don't support live reload. To see a change, stop the script, edit the file, and run `jbang HelloWorld.java` again. The starter is also aimed at learning, prototyping, and demos. Production apps use a full Maven or Gradle project, and a single file gets harder to read as the code grows.

## When should you move to Maven? {#when-to-move}

When the prototype outgrows one file, create a full project. The [Spring Boot integration](/docs/integrations/spring/spring-boot#option-2-using-the-command-line) shows how to generate one from the command line, and you can copy your script logic into the generated structure. For live reload during development, use a full Maven project with [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools).

Paste the listing into `HelloWorld.java`, run `jbang HelloWorld.java`, and change the greeting. When you're ready for a full project, read [Getting Started](/docs/introduction/getting-started).

## Frequently asked questions {#faq}

### Does a JBang webforJ app need Spring Boot? {#needs-spring-boot}

You don't add Spring Boot yourself. The starter includes the Spring Boot starter, and the example calls `SpringApplication.run`, so Spring Boot starts with the app. The single `//DEPS` line of the starter brings in the Spring Boot starter with it.

### Can a JBang webforJ app run in production? {#production}

The JBang integration is built for rapid prototyping, learning, and quick demos. For a production app, use a full Maven project. The [Spring Boot integration](/docs/integrations/spring/spring-boot) covers that setup.
