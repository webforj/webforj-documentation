---
title: "Java to JavaScript compiler alternative: no transpile step"
description: "A Java to JavaScript compiler alternative: webforJ runs your Java on the JVM and the browser renders it, so you keep the full JDK and add no compile step."
slug: java-to-javascript-compiler-alternative
date: 2026-08-07
authors: webforJ
tags: [insights, javascript]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-08-07-java-to-javascript-compiler-alternative/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-08-07-java-to-javascript-compiler-alternative/cover.jpg)

You want your team to stay in Java, but the screen has to run in a browser, and the browser runs JavaScript. One answer is a compiler that turns your Java into JavaScript. The other is to keep your Java where it already runs and send the browser only what it needs to draw the screen. webforJ, the full-stack Java framework, takes the second path and is a Java to JavaScript compiler alternative for business web apps: your code runs on the JVM, so there is no compile-to-JavaScript step and no restricted subset of Java.

<!-- truncate -->

## What the compiler approach does {#what-a-compiler-does}

Tools in this group take Java and emit JavaScript, and in some cases WebAssembly, that the browser executes. Two well-known projects show the shape of the approach.

J2CL is a transpiler from Java to Closure style JavaScript, built with Bazel, and it is the technology behind Google apps such as Gmail and Docs. TeaVM is an ahead-of-time compiler of Java bytecode to JavaScript. Because it works on bytecode, it also compiles Kotlin and Scala, and it also has a Wasm GC backend.

Both also move your program into a different runtime, and that has consequences the projects document themselves.

## Where transpiling pinches {#where-it-pinches}

The browser is not a JVM. A compiler has to re-create the parts of Java it wants to support, and it leaves out the parts it cannot.

- **Reflection is restricted.** J2CL's limitations page says it supports none of `java.lang.reflect.*`. TeaVM's overview says it restricts reflection, resources, class loaders and JNI.
- **The JDK is emulated.** J2CL emulates a substantial portion of the Java standard library, and APIs such as `java.net.*` are intentionally left out. Your code has to stay inside the emulated set.
- **Existing code needs work.** TeaVM's overview says it is not for taking a large existing codebase and producing JavaScript, and that you will usually have to rewrite code to fit its constraints.
- **There is a second build.** J2CL is powered by Bazel. TeaVM compiles bytecode to JavaScript as its own step. Either way, the output of that step is what ships.

None of this makes the tools wrong. It makes them a specific trade: you get Java running inside the browser, and you pay with a smaller Java and an extra build stage. Libraries you depend on on the server, such as a JPA provider, a PDF generator or an in-house jar that uses reflection, may not come along.

## Java to JavaScript compiler alternative {#alternative}

webforJ does not translate your Java. It runs it. The app executes on the JVM, on the server, with the full JDK and every library on your classpath. The browser renders standard web components that ship with the framework, and the two sides talk over the connection the framework manages.

When your code calls a method such as `setText()`, the framework sends the change to the browser. When a user clicks a button, the browser sends the event to your Java handler. The [client/server model](/docs/architecture/client-server) page explains how these messages are batched so the app stays responsive.

That is the answer to the variants people search for. To run a Java UI in the browser without transpiling, keep the Java on the server and let the framework drive the page. For a TeaVM or J2CL alternative for web apps that need real Java libraries, this is the option that does not restrict them. For a Java web UI without a compile-to-JavaScript step, the only build is the Maven build you already have.

## What the screen code looks like {#screen-code}

A view is a plain Java class. This one, from the getting-started archetype, shows a text field and a button:

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

The click listener is ordinary Java running on the JVM. It can call a repository, read a file, use reflection or open a socket, because nothing here has to be translated. The [component fundamentals](/docs/building-ui/component-fundamentals) page describes how each component maps to a web component in the browser.

## What changes in your project {#project-changes}

| Question | Compile to JavaScript | webforJ |
| --- | --- | --- |
| Where does your Java run? | In the browser | On the JVM |
| Which JDK APIs can you use? | The emulated subset | The full JDK |
| Reflection, class loaders | Restricted | Available |
| Server libraries on the classpath | Only if the compiler supports them | All of them |
| Extra build stage | A Java to JavaScript compile | None beyond Maven |
| Needs a server connection | No, once loaded | Yes |

The last row matters, and the next section returns to it. The rest of the table is the case for this route: the code you write is the code that runs, and the libraries you already trust keep working. The same holds when the app needs the browser's own features. You can [run JavaScript from Java](/docs/building-ui/execute-javascript) or wrap a custom element, and the rest of the app stays in Java. For a longer look at what you no longer write, see [Java web app without JavaScript](/blog/java-web-app-without-javascript).

## When a transpiler is the right tool {#when-transpiler}

We would not pretend the compile-to-JavaScript route has no place. Pick it when one of these is true:

- **The app must work offline.** If the program has to run with no connection to a server, the logic has to live in the browser. A server-driven app needs its connection.
- **The work is heavy and local.** Computation that should run on the user's device, such as a parser, a game loop or an editor engine, belongs on the client.
- **You are shipping a library to JavaScript developers.** J2CL's own pitch is making Java code usable from a JavaScript app, and that is a different job from building a business screen.

If your app is forms, tables, dialogs, navigation and data from a database, with users who are online, the compile step buys you little and costs you the JDK. That describes many internal tools, admin consoles and line-of-business apps.

## Next step {#next-step}

Generate a project with [startforJ](https://docs.webforj.com/startforj) or follow [getting started](/docs/introduction/getting-started), run it with Maven like any Java project, and open the page. Your Java is running, and no JavaScript was generated from it.

## Frequently asked questions {#faq}

### Is there a Java to JavaScript compiler alternative? {#faq-alternative}

Yes. webforJ keeps your Java on the JVM and renders the interface in the browser with standard web components. You use the full JDK and your own libraries, and nothing is compiled to JavaScript.

### How do I run a Java UI in the browser without transpiling? {#faq-no-transpile}

Build the screens as Java classes with webforJ and run the app as a normal Maven project. The framework sends UI changes to the browser and returns user events to your Java handlers, so the browser never executes your Java.

### When should I still use a transpiler? {#faq-when}

When the code has to run in the browser itself, for example an offline app or heavy computation on the user's device. For connected business apps that need the full Java standard library, a server-side Java framework such as webforJ avoids the restricted subset and the extra build stage.
