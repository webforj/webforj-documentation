---
title: Using the DSL
sidebar_position: 10
---

The Kotlin DSL provides builder functions for webforJ components. Each function creates a component, adds it to a parent container, and runs a configuration block. This page covers the patterns and conventions you'll use when building UIs with the DSL.

## Naming conventions {#naming-conventions}

DSL functions use the component class name in **camelCase**. `Button` becomes `button()`, `TextField` becomes `textField()`, and `FlexLayout` becomes `flexLayout()`.

```kotlin
div {
    button("Click me")
    textField("Username")
    flexLayout {
        // nested content
    }
}
```

One exception: `Break` uses backticks because `break` is a Kotlin keyword:

```kotlin
div {
    span("Line one")
    `break`()
    span("Line two")
}
```

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

## The DSL marker {#the-dsl-marker}

With nested structures, it's easy to accidentally reference the wrong scope. The `@WebforjDsl` annotation prevents this by restricting access to outer component scopes:

```kotlin
div {
    button("Outer") {
        // This would be confusing - adding to outer div from inside button
        // button("Accidental") // Won't compile - DSL marker prevents this
    }
}
```

If you need to reference an outer scope explicitly, use labeled `this`:

```kotlin
div {
    button("Inner") {
        this@div.add(Paragraph("Added to outer div"))  // Explicit is allowed
    }
}
```

This keeps UI code predictable by making scope jumps visible.

## Styling components {#styling-components}

Now that you can build component structures, you'll want to style them. Access the `styles` map to set inline CSS properties:

```kotlin
button("Styled") {
    styles["background-color"] = "#007bff"
    styles["color"] = "white"
    styles["padding"] = "12px 24px"
    styles["border-radius"] = "4px"
}
```

For multiple styles, you can chain assignments:

```kotlin
div {
    styles["display"] = "flex"
    styles["gap"] = "16px"
    styles["padding"] = "20px"

    button("One")
    button("Two")
    button("Three")
}
```

:::tip[CSS classes]
For reusable styles, add CSS classes instead of inline styles:

```kotlin
button("Primary Action") {
    addClassName("btn-primary")
}
```
:::

## Event handling {#event-handling}

Styled components need to respond to user interaction. The DSL provides concise event listener syntax using `on` prefix methods that accept lambdas:

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

Common event methods include:

| Method | Triggered when |
|--------|---------------|
| `onClick` | User clicks the component |
| `onModify` | Field value changes |
| `onAttach` | Component attaches to the DOM |
| `onDetach` | Component detaches from the DOM |
| `onFocus` | Component gains focus |
| `onBlur` | Component loses focus |

## Common parameters {#common-parameters}

You've seen configuration blocks used throughout these examples. Most DSL functions also accept common parameters before the block for frequently-used options:

```kotlin
// Text parameter for labels/content
button("Click me") { }
h1("Page Title") { }
paragraph("Body text") { }

// Label and placeholder for fields
textField("Username", placeholder = "Enter username") { }
passwordField("Password", placeholder = "Enter password") { }

// Value parameters for inputs
numberField("Quantity", value = 1.0, min = 0.0, max = 100.0) { }
```

These parameters are optional with sensible defaults:

```kotlin
// All of these work
button { text = "Set later" }
button("Immediate text")
button("Text") { theme = ButtonTheme.DANGER }
```

## Building a complete view {#building-a-complete-view}

With these patterns in hand, here's a complete form that brings them together:

``` kotlin
@Route("contact")
class ContactView : Composite<Div>() {

    init {
        boundComponent = div {
            styles["max-width"] = "400px"
            styles["margin"] = "40px auto"
            styles["padding"] = "20px"

            h2("Contact Us")

            val nameField = textField("Name", placeholder = "Your name") {
                styles["width"] = "100%"
                styles["margin-bottom"] = "16px"
            }

            val emailField = textField("Email", placeholder = "you@example.com") {
                styles["width"] = "100%"
                styles["margin-bottom"] = "16px"
            }

            val messageField = textArea("Message", placeholder = "How can we help?") {
                styles["width"] = "100%"
                styles["min-height"] = "120px"
                styles["margin-bottom"] = "16px"
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
