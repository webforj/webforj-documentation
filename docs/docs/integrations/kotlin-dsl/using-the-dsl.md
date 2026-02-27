---
title: Using the DSL
sidebar_position: 10
---

The Kotlin DSL provides builder functions for webforJ components. Each function creates a component, adds it to a parent container, and runs a configuration block. This page covers the patterns and conventions you'll use when building UIs with the DSL.

## Naming conventions {#naming-conventions}

DSL functions are provided for all standard webforJ components, including buttons, fields, layouts, dialogs, drawers, lists, and HTML elements. Each function uses the component class name in **camelCase**. `Button` becomes `button()`, `TextField` becomes `textField()`, and `FlexLayout` becomes `flexLayout()`.

```kotlin
div {
    button("Click me")
    textField("Username")
    flexLayout {
        // nested content
    }
}
```
:::important `Header` and `Footer` methods
The `header` and `footer` DSL methods were renamed to `nativeHeader` and `nativeFooter` to avoid conflicts with header and footer slots of other components.
:::

:::important Using the `Break` component
One exception: `Break` uses backticks because `break` is a Kotlin keyword:

```kotlin
div {
    span("Line one")
    `break`()
    span("Line two")
}
```
:::

## Creating components {#creating-components}

With the naming pattern in mind, here's how component creation works. Every DSL function follows the same pattern: create a component, add it to the parent, then run the configuration block:

```kotlin
div {
    // Creates a Button, adds it to this div, then runs the block
    button("Submit") {
        theme = ButtonTheme.PRIMARY
        onClick { handleSubmit() }
    }
}
```

The configuration block receives the component as its receiver (`this`), so you can access properties and methods directly:

```kotlin
textField("Email") {
    placeholder = "you@example.com"   // this.placeholder
    required = true                    // this.required
    onModify { validate() }           // this.onModify(...)
}
```

## Nesting components {#nesting-components}

Once you can create individual components, you'll want to combine them. Components that can contain children accept nested DSL calls inside their block:

```kotlin
flexLayout {
    direction = FlexDirection.COLUMN

    h1("Dashboard")

    div {
        paragraph("Welcome back!")
        button("View Reports")
    }

    flexLayout {
        direction = FlexDirection.ROW
        button("Settings")
        button("Logout")
    }
}
```

The DSL enforces proper scoping. You can only add children to components that support them, and the compiler prevents accidental references to outer scopes.

## Scope safety {#scope-safety}

With nested structures, it's easy to accidentally reference the wrong scope. The DSL prevents this automatically - builder functions from outer scopes aren't accessible inside inner blocks:

```kotlin
div {
    button("Submit") {
        // This looks like it adds a paragraph inside the button,
        // but it would actually add it to the outer div.
        // The DSL catches this mistake at compile time.
        paragraph("Submitting...") // Won't compile
    }
}
```

If you genuinely need to add to an outer scope, use labeled `this` to make the intent explicit:

```kotlin
div {
    button("Submit") {
        this@div.add(Paragraph("Submitting..."))  // Explicit is allowed
    }
}
```

This keeps UI code predictable by making scope jumps visible.

## Styling components {#styling-components}

Now that you can build component structures, you'll want to style them. The Kotlin DSL provides a `styles` extension property that gives map-like bracket access to CSS properties, equivalent to `setStyle()` and `getStyle()` in Java:

```kotlin
button("Styled") {
    styles["background-color"] = "#007bff"
    styles["color"] = "white"
    styles["padding"] = "12px 24px"
    styles["border-radius"] = "4px"
}
```

:::tip[CSS classes]
For reusable styles, add CSS classes instead of inline styles. The `HasClassName` extension allows adding class names with `+=`:

```kotlin
button("Primary Action") {
    classNames += "btn-primary"
}
```
:::

## Event handling {#event-handling}

Components almost always need to respond to user interaction. The DSL provides concise event listener syntax using `on` prefix methods that accept lambdas:

```kotlin
button("Save") {
    onClick {
        saveData()
        showNotification("Saved!")
    }
}

textField("Search") {
    onModify { event ->
        performSearch(event.text)
    }
}
```

## Common parameters {#common-parameters}

You've seen configuration blocks used throughout these examples. Most DSL functions also accept common parameters before the block for frequently used options:

```kotlin
// Text parameter for labels/content
button("Click me")
h1("Page Title")
paragraph("Body text")

// Label and placeholder for fields
textField("Username", placeholder = "Enter username")
passwordField("Password", placeholder = "Enter password")

// Value parameters for inputs
numberField("Quantity", value = 1.0) {
    min = 0.0
    max = 100.0
}
```

:::tip Arguments with specified names
Named arguments let you pass parameters in any order, regardless of how they appear in the function signature.
:::


## Building a complete view {#building-a-complete-view}

With these patterns in hand, here's a complete form that brings them together:

``` kotlin
@Route("contact")
class ContactView : Composite<Div>() {

    init {
        boundComponent.apply {
            styles["max-width"] = "400px"
            styles["padding"] = "20px"

            h2("Contact Us")

            val nameField = textField("Name", placeholder = "Your name") {
                styles["width"] = "100%"
                styles["margin-bottom"] = "16px"
            }

            val emailField = textField("Email", placeholder = "you@example.com") {
                styles["width"] = "100%"
            }

            val messageField = textArea("Message", placeholder = "How can we help?") {
                styles["width"] = "100%"
            }

            button("Send Message") {
                theme = ButtonTheme.PRIMARY
                styles["width"] = "100%"

                onClick {
                    submitForm(
                        name = nameField.text,
                        email = emailField.text,
                        message = messageField.text
                    )
                }
            }
        }
    }

    private fun submitForm(name: String, email: String, message: String) {
        // Handle form submission
    }
}
```

The DSL keeps the UI structure readable while giving you full access to component configuration.
