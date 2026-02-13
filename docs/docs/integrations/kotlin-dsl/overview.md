---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ provides a Kotlin *Domain Specific Language*, or DSL, that lets you build UIs with concise, type-safe syntax. Instead of imperative Java code, you write declarative code that reads like a description *of your UI structure.

```java title="Java"
FlexLayout layout = new FlexLayout();
layout.setDirection(FlexDirection.COLUMN);
layout.setSpacing("10px");

TextField name = new TextField();
name.setLabel("Name");
name.setPlaceholder("Your name");
layout.add(name);

Button submit = new Button("Submit", ButtonTheme.PRIMARY);
submit.onClick(e -> handleSubmit());
layout.add(submit);
```

```kotlin title="Kotlin DSL"
flexLayout {
    direction = FlexDirection.COLUMN
    styles["gap"] = "10px"

    textField("Name", placeholder = "Your name")
    button("Submit", ButtonTheme.PRIMARY) {
        onClick { handleSubmit() }
    }
}
```

The DSL uses Kotlin's language features such as extension functions, lambdas with receivers, and default parameters to create a natural builder syntax. Components nest inside each other, configuration happens in blocks, and the compiler catches structural mistakes before runtime.

{/* TODO: Add setup/dependency section once webforj-kotlin is merged and coordinates are finalized. Should cover: Maven/Gradle dependency, required Kotlin version, kotlin-maven-plugin config. */}

:::tip[Java interoperability]
Kotlin compiles to JVM bytecode, so it works alongside existing Java code. You can use DSL-built Kotlin composites from Java classes, nest standard Java components inside DSL blocks with `add()`, and mix Kotlin and Java files in the same project.
:::

## Topics {#topics}

The following topics cover using the DSL, as well as extending it to any custom components or composites you create.

<DocCardList className="topics-section" />

## Kotlin for Java developers {#kotlin-for-java-developers}

<details>
<summary>New to Kotlin? Here are some of the key language features the DSL relies on.</summary>

### Null safety {#null-safety}

Kotlin distinguishes nullable and non-nullable types at compile time:

```kotlin
// Java - any reference can be null
String name = null;

// Kotlin - explicit nullability
var name: String? = null        // Nullable, can be null
var safeName: String = "value"  // Non-null, compiler enforces this

// Safe call operator - returns null if name is null
println(name?.length)

// Elvis operator - provides default when null
println(name ?: "default")
```

### Extension functions {#extension-functions}

Kotlin lets you add methods to existing classes without inheritance:

```kotlin
// Java approach - static utility class
public class StringUtils {
    public static String addExclamation(String input) {
        return input + "!";
    }
}
String result = StringUtils.addExclamation("Hello");

// Kotlin approach - extension function
fun String.addExclamation(): String = this + "!"
val result = "Hello".addExclamation()  // Reads like a method call
```

The DSL uses extension functions to add builder methods to components.

### Lambdas and trailing lambda syntax {#lambdas-and-trailing-lambda-syntax}

Kotlin lambdas are more concise than Java's, and when a lambda is the last parameter, it can go outside the parentheses:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Clicked"));

// Kotlin - lambda as last parameter goes outside parentheses
button.onClick { println("Clicked") }

// With explicit parameter
button.onClick { event -> println("Clicked: $event") }
```

This trailing lambda syntax is what makes DSL blocks possible.

### Default parameters {#default-parameters}

Kotlin functions can have default parameter values, reducing the need for overloaded methods:

```kotlin
// Java - multiple constructors needed
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - one function with defaults
fun button(
    text: String = "",
    theme: ButtonTheme = ButtonTheme.DEFAULT,
    block: Button.() -> Unit = {}
): Button
```

</details>
