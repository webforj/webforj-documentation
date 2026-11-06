---
title: "Kotlin web framework: build web UIs with the webforJ DSL"
description: "Use webforJ as your Kotlin web framework. Build web UIs with the Kotlin DSL, handle events, mix in Java, add custom components and inject Spring services."
slug: kotlin-web-framework
date: 2026-09-16
authors: Hyyan Abo Fakher
tags: [integrations, web development, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-16-kotlin-web-framework/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-16-kotlin-web-framework/cover.jpg)

If you want a Kotlin web framework that builds the whole UI in Kotlin, webforJ has one. Add the `webforj-kotlin` module and the Kotlin Maven plugin to a webforJ project, and you build web UIs with a type-safe Kotlin DSL: `flexLayout { button("Save") { onClick { save() } } }`. There is no HTML template and no JavaScript.

The DSL has been available since webforJ 25.12. It covers the standard components, nests like the UI it describes, mixes with Java classes in the same project, and works with Spring Boot. This post shows how to build a web app in Kotlin with it, from setup to your own DSL functions.

<!-- truncate -->

## What does a Kotlin web UI look like in webforJ? {#what-it-looks-like}

webforJ renders its UI from server-side code, so a view is a class that builds components. The Kotlin DSL turns that construction code into a block that reads like the UI itself:

```kotlin
@Route("contact")
class ContactView : Composite<Div>() {
  init {
    boundComponent.apply {
      h1("Contact us")
      flexLayout {
        direction = FlexDirection.COLUMN
        styles["gap"] = "10px"

        val name = textField("Name", placeholder = "Your name")
        val reply = paragraph()
        button("Send", ButtonTheme.PRIMARY) {
          onClick { reply.text = "Thanks, ${name.text}" }
        }
      }
    }
  }
}
```

Every call such as `textField(...)` or `button(...)` creates the component, adds it to the enclosing container and runs the configuration block with the component as `this`. You can read the structure of the page straight from the indentation. The same view in plain Java needs a variable, a constructor call, a setter call and an `add()` for each component.

## How do you set up Kotlin in a webforJ project? {#setup}

No separate Kotlin install is needed. Maven compiles Kotlin through the Kotlin Maven plugin, so any webforJ project that builds with Maven can adopt it with a dependency and a plugin block.

Add the DSL module and the Kotlin standard library to your `pom.xml`:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-kotlin</artifactId>
  <version>${webforj.version}</version>
</dependency>
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-stdlib-jdk8</artifactId>
  <version>${kotlin.version}</version>
</dependency>
```

Then add the Kotlin Maven plugin so Kotlin and Java files compile in the same project:

```xml
<plugin>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-maven-plugin</artifactId>
  <version>${kotlin.version}</version>
  <executions>
    <execution>
      <id>compile</id>
      <phase>compile</phase>
      <goals>
        <goal>compile</goal>
      </goals>
      <configuration>
        <sourceDirs>
          <sourceDir>src/main/java</sourceDir>
          <sourceDir>target/generated-sources/annotations</sourceDir>
          <sourceDir>src/main/kotlin</sourceDir>
        </sourceDirs>
      </configuration>
    </execution>
  </executions>
  <configuration>
    <jvmTarget>${maven.compiler.target}</jvmTarget>
  </configuration>
</plugin>
```

To skip the configuration, clone the [webforJ Kotlin starter](https://github.com/webforj/webforj-kotlin-starter), a ready-to-run project with the dependencies and plugin already in place.

## How do the DSL functions work? {#dsl-functions}

DSL functions use the component class name with a lowercase first letter. `Button` becomes `button()`, `TextField` becomes `textField()`, and `FlexLayout` becomes `flexLayout()`. They exist for the standard components: buttons, fields, layouts, dialogs, drawers, lists and HTML elements.

Most functions take the common parameters first, then an optional block:

```kotlin
textField("Username", placeholder = "Enter username")
numberField("Quantity", value = 1.0) {
  min = 0.0
  max = 100.0
}
```

Named arguments work as in any Kotlin call, so you can pass them in any order. The one naming exception is `Break`, because `break` is a Kotlin keyword. You call it as `` `break`() `` with backticks.

### Nesting and scope safety {#nesting-and-scope-safety}

Containers accept child calls inside their block. The DSL is annotated with a Kotlin `@DslMarker`, which stops a child block from silently calling functions of an outer container:

```kotlin
div {
  button("Submit") {
    paragraph("Submitting...") // does not compile
  }
}
```

Without the marker, that `paragraph` would land in the outer `div`, which is rarely what you meant. The compiler rejects it instead. When you do want the outer container, say so with a labeled receiver, `this@div.add(...)`.

### Styling {#styling}

The DSL adds a `styles` property with map-style access to CSS, equivalent to `setStyle()` in Java. For anything reusable, add a CSS class with `classNames += "btn-primary"` and keep the rules in a stylesheet.

## How do you handle events in the Kotlin DSL? {#events}

Event listeners are `on` functions that take a lambda. `onClick` and `onModify` are two:

```kotlin
button("Save") {
  onClick { saveData() }
}

textField("Search") {
  onModify { event -> performSearch(event.text) }
}
```

Because the listener runs on the server, the lambda can access other components, call your services, and update the view directly. There is no request to define and no client-side state to sync. See [event handling](/docs/integrations/kotlin-dsl/using-the-dsl#event-handling) for the pattern.

## Can you mix Kotlin and Java in one webforJ app? {#kotlin-and-java}

Yes. Kotlin compiles to JVM bytecode, so a Kotlin view can sit next to Java views in the same Maven module. Inside a DSL block, `add()` places any Java-built component, and Java classes can use Kotlin composites like any other component.

```kotlin
class Mixed : Composite<Div>() {
  init {
    boundComponent.apply {
      add(Button("Plain Java button"))
      priceTag("19.99")
    }
  }
}
```

This makes migration gradual. You can write new views in Kotlin and leave working Java views alone. Routing, layouts and the rest of the framework behave the same in both languages. The [routing guide](/blog/master-webforj-routing-in-10-minutes) applies to both.

## How do you extend the DSL? {#extending-the-dsl}

A DSL function is an extension function on `HasComponents` that calls the `init` helper. `init` adds the component to the parent, runs the configuration block, and returns the component. The `priceTag` call in the previous example comes from this:

```kotlin
class PriceTag(text: String) : Composite<Div>() {
  init { boundComponent.span(text) }
}

fun @WebforjDsl HasComponents.priceTag(
  text: String,
  block: @WebforjDsl PriceTag.() -> Unit = {}
): PriceTag = init(PriceTag(text), block)
```

The same pattern works for any component you write, and for third-party components you wrap. A wrapped custom element, such as the one in [Web components in Java](/blog/web-components-in-java), gets a DSL function the same way. Put the function in a Kotlin file next to the class, and every DSL block in your project can call it.

Composites can use the DSL internally too. A `Composite<Div>` that builds its children with `textField` and `button` becomes a reusable unit, and a one-line extension function makes it behave like a built-in. The [extending the DSL](/docs/integrations/kotlin-dsl/extending-the-dsl) page walks through a search box and a status indicator built this way.

## Does the Kotlin DSL work with Spring Boot? {#spring-boot}

Yes. With the Spring integration on the classpath, webforJ creates route instances through Spring's container, so a route can receive services by constructor injection. In Kotlin, that's the primary constructor:

```kotlin
@Service
class GreetingService {
  fun greet(name: String) = "Hello, $name"
}

@Route("greeting")
class GreetingView(private val greetings: GreetingService) : Composite<Div>() {
  init {
    boundComponent.apply {
      val name = textField("Name")
      val out = paragraph()
      button("Greet") { onClick { out.text = greetings.greet(name.text) } }
    }
  }
}
```

The route needs no Spring annotation. `@Route` alone enables both discovery and injection, and webforJ creates a fresh instance on each navigation. The [Spring routing guide](/docs/integrations/spring/routing) explains the lifecycle, and the [Spring Boot setup page](/docs/integrations/spring/spring-boot) shows how to generate a project.

## Why webforJ for Kotlin? {#why-webforj}

webforJ is one framework with one language across the stack. The UI, the event handling, and the business logic are all Kotlin or Java code, so you work in one codebase with one type system. The components come from the same library Java developers use, including tables, layouts, dialogs and form fields, so you aren't limited to a reduced Kotlin subset. Because the DSL sits on top of the Java API, anything the framework can do remains reachable from Kotlin.

You also keep the JVM ecosystem. Spring services, repositories and any Kotlin or Java library work as they do in any other JVM project.

## Next step {#next-step}

Clone the [Kotlin starter](https://github.com/webforj/webforj-kotlin-starter) and run it, then rebuild one of your own screens with `flexLayout`, `textField` and `button`. For a fresh Spring Boot project, generate one with [startforJ](https://docs.webforj.com/startforj) and add the Kotlin setup from the [DSL overview](/docs/integrations/kotlin-dsl/overview).

## Frequently asked questions {#faq}

### Is there a Kotlin web framework for building UIs without HTML or JavaScript? {#faq-no-html}
Yes. webforJ with its Kotlin DSL lets you describe the whole UI in Kotlin. Components, layouts and events are Kotlin code that runs on the server, and the browser renders the result.

### Which webforJ version do I need for the Kotlin DSL? {#faq-version}
The DSL is available since webforJ 25.12. It ships as the `webforj-kotlin` module, which you add next to the core webforJ dependency.

### Do I need to rewrite my Java app to use Kotlin? {#faq-rewrite}
No. Kotlin and Java files compile together in one Maven module, and you can add Java components inside DSL blocks with `add()`. Existing Java views keep working while you write new ones in Kotlin.

### Can I use a component that the DSL doesn't cover? {#faq-custom}
Yes. Write an extension function on `HasComponents` that calls `init` with your component, and it becomes available in every DSL block. This works for your own composites and for wrapped third-party components.
