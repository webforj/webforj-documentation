---
title: Extending the DSL
sidebar_position: 20
---

The Kotlin DSL is extensible. You can add DSL functions for custom components or third-party libraries, and you can build composite components that use the DSL internally. This page shows both patterns.

## Adding components to the DSL {#adding-components-to-the-dsl}

To make any component available in the DSL, create an extension function on `HasComponents` that uses the `init` helper function.

### Basic DSL function {#basic-dsl-function}

Here's the pattern for a simple component:

```kotlin
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.init

fun @WebforjDsl HasComponents.progressBar(
    block: @WebforjDsl ProgressBar.() -> Unit = {}
): ProgressBar {
    return init(ProgressBar(), block)
}
```

The `init` function does three things:
1. Adds the component to the parent container
2. Runs the configuration block
3. Returns the configured component

Now you can use the component in DSL code:

```kotlin
div {
    progressBar {
        value = 75.0
        max = 100.0
    }
}
```

### Adding parameters {#adding-parameters}

Most DSL functions accept common parameters before the configuration block:

```kotlin
fun @WebforjDsl HasComponents.progressBar(
    value: Double? = null,
    max: Double? = null,
    block: @WebforjDsl ProgressBar.() -> Unit = {}
): ProgressBar {
    val progressBar = ProgressBar()
    value?.let { progressBar.value = it }
    max?.let { progressBar.max = it }
    return init(progressBar, block)
}
```

Usage becomes more concise:

```kotlin
div {
    progressBar(75.0, 100.0)
    progressBar(50.0) {
        styles["width"] = "200px"
    }
}
```

### Components with slots {#components-with-slots}

Some components have named slots for different content areas. Use `HasComponentsProxy` to handle these:

```kotlin
import com.webforj.kotlin.dsl.HasComponentsProxy

fun @WebforjDsl HasComponents.card(
    title: String? = null,
    block: @WebforjDsl Card.() -> Unit = {}
): Card {
    val card = Card()
    title?.let { card.title = it }
    return init(card, block)
}

// Slot functions as extensions on the component
fun @WebforjDsl Card.header(block: @WebforjDsl HasComponents.() -> Unit) {
    HasComponentsProxy(block).setSlotSingle(this, Card::setHeader)
}

fun @WebforjDsl Card.content(block: @WebforjDsl HasComponents.() -> Unit) {
    HasComponentsProxy(block).setSlot(this, Card::setContent)
}

fun @WebforjDsl Card.footer(block: @WebforjDsl HasComponents.() -> Unit) {
    HasComponentsProxy(block).setSlotSingle(this, Card::setFooter)
}
```

Slot functions enable structured content placement:

```kotlin
card("User Profile") {
    header {
        icon("user")
        span("Profile Settings")
    }

    content {
        textField("Display Name")
        textField("Email")
    }

    footer {
        button("Save")
        button("Cancel")
    }
}
```

:::info[setSlot vs setSlotSingle]
Use `setSlotSingle` when the slot accepts exactly one component. Use `setSlot` when the slot accepts multiple components.
:::

### Documentation {#documentation}

Add KDoc comments to help IDE autocompletion:

```kotlin
/**
 * Creates a [ProgressBar] with optional initial [value] and [max].
 *
 * ```
 * div {
 *     progressBar(75.0, 100.0) {
 *         styles["height"] = "20px"
 *     }
 * }
 * ```
 *
 * @param value Current progress value
 * @param max Maximum value (defaults to 100)
 * @param block Configuration block
 * @return The configured ProgressBar
 */
fun @WebforjDsl HasComponents.progressBar(
    value: Double? = null,
    max: Double? = null,
    block: @WebforjDsl ProgressBar.() -> Unit = {}
): ProgressBar
```

## Creating composite components {#creating-composite-components}

A `Composite` wraps multiple components into a single reusable unit. The DSL works well for defining composite structure.

### Basic composite {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

    val searchField: TextField
    val searchButton: Button

    init {
        boundComponent = div {
            styles["display"] = "flex"
            styles["gap"] = "8px"

            searchField = textField(placeholder = "Search...") {
                styles["flex"] = "1"
            }

            searchButton = button("Search") {
                theme = ButtonTheme.PRIMARY
            }
        }
    }

    fun onSearch(handler: (String) -> Unit) {
        searchButton.onClick {
            handler(searchField.text ?: "")
        }
        searchField.onEnter {
            handler(searchField.text ?: "")
        }
    }
}
```

The composite exposes component references for external access and provides convenience methods for common operations.

### Adding DSL support {#adding-dsl-support}

Create a DSL function so the composite can be used like built-in components:

```kotlin
fun @WebforjDsl HasComponents.searchBox(
    block: @WebforjDsl SearchBox.() -> Unit = {}
): SearchBox {
    return init(SearchBox(), block)
}
```

Now it integrates naturally:

```kotlin
div {
    h1("Product Catalog")

    searchBox {
        onSearch { query ->
            filterProducts(query)
        }
    }

    // Product list...
}
```

### Example: Status indicator {#example-status-indicator}

Here's a complete example of a status indicator composite:

```kotlin
class StatusIndicator : Composite<Div>() {

    private val dot: Div
    private val label: Span

    var status: Status = Status.INACTIVE
        set(value) {
            field = value
            updateDisplay()
        }

    var text: String = ""
        set(value) {
            field = value
            label.text = value
        }

    init {
        boundComponent = div {
            styles["display"] = "flex"
            styles["align-items"] = "center"
            styles["gap"] = "8px"

            dot = div {
                styles["width"] = "10px"
                styles["height"] = "10px"
                styles["border-radius"] = "50%"
                styles["background"] = "#gray"
            }

            label = span()
        }
        updateDisplay()
    }

    private fun updateDisplay() {
        dot.styles["background"] = when (status) {
            Status.ACTIVE -> "#22c55e"
            Status.WARNING -> "#f59e0b"
            Status.ERROR -> "#ef4444"
            Status.INACTIVE -> "#9ca3af"
        }
    }

    enum class Status { ACTIVE, WARNING, ERROR, INACTIVE }
}

// DSL function
fun @WebforjDsl HasComponents.statusIndicator(
    text: String = "",
    status: StatusIndicator.Status = StatusIndicator.Status.INACTIVE,
    block: @WebforjDsl StatusIndicator.() -> Unit = {}
): StatusIndicator {
    val indicator = StatusIndicator()
    indicator.text = text
    indicator.status = status
    return init(indicator, block)
}
```

Usage:

```kotlin
div {
    statusIndicator("Database", StatusIndicator.Status.ACTIVE)
    statusIndicator("Cache", StatusIndicator.Status.WARNING)
    statusIndicator("External API", StatusIndicator.Status.ERROR)
}
```

## Best practices {#best-practices}

**Use nullable parameters with defaults.** This keeps function calls concise when callers don't need every option:

```kotlin
// Good - callers can omit parameters
fun @WebforjDsl HasComponents.card(
    title: String? = null,
    subtitle: String? = null,
    block: @WebforjDsl Card.() -> Unit = {}
)

// Usage
card()
card("Title")
card("Title", "Subtitle") { }
```

**Expose component references when needed.** If external code needs to access internal components, expose them as properties:

```kotlin
class FormGroup : Composite<Div>() {
    lateinit var input: TextField
        private set  // Readable but not settable externally

    init {
        boundComponent = div {
            input = textField()
        }
    }
}
```

**Provide convenience methods.** Wrap common operations so callers don't need to know internal structure:

```kotlin
class LoginForm : Composite<Div>() {
    private lateinit var usernameField: TextField
    private lateinit var passwordField: TextField

    // Convenience methods
    fun getCredentials(): Pair<String, String> =
        Pair(usernameField.text ?: "", passwordField.text ?: "")

    fun clear() {
        usernameField.text = ""
        passwordField.text = ""
    }
}
```

**Keep DSL functions in a consistent location.** Group DSL functions near their component classes or in a dedicated DSL package. This makes them easier to find and maintain.
