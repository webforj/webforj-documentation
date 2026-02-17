---
title: Extending the DSL
sidebar_position: 20
---

The Kotlin DSL is extensible, allowing the addition of DSL functions for custom components or third-party libraries. You can build composite components that use the DSL internally.

## Adding components to the DSL {#adding-components-to-the-dsl}

To make any component available in the DSL, create an extension function on `HasComponents` that uses the `init` helper function.

### Basic DSL function {#basic-dsl-function}

Here's the pattern for a simple component. This example assumes you have a custom `Badge` component:

```kotlin
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.init
import com.example.component.Badge

fun @WebforjDsl HasComponents.badge(
    block: @WebforjDsl Badge.() -> Unit = {}
): Badge {
    return init(Badge(), block)
}
```

The `init` function does three things:
1. Adds the component to the parent container
2. Runs the configuration block
3. Returns the configured component

Now you can use the component in DSL code:

```kotlin
div {
    badge {
        text = "New"
        variant = Badge.Variant.PRIMARY
    }
}
```

### Adding parameters {#adding-parameters}

Most DSL functions accept common parameters before the configuration block:

```kotlin
fun @WebforjDsl HasComponents.badge(
    text: String? = null,
    variant: Badge.Variant? = null,
    block: @WebforjDsl Badge.() -> Unit = {}
): Badge {
    val badge = Badge()
    text?.let { badge.text = it }
    variant?.let { badge.variant = it }
    return init(badge, block)
}
```

Usage becomes more concise:

```kotlin
div {
    badge("New", Badge.Variant.PRIMARY)
    badge("Sale") {
        styles["font-size"] = "12px"
    }
}
```

## Creating composite components {#creating-composite-components}

A `Composite` wraps multiple components into a single reusable unit. The DSL works well for defining composite structure.

### Basic composite {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

    val searchField: TextField
    val searchButton: Button

    init {
        boundComponent.apply {
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
            handler(searchField.text)
        }
        searchField.onEnter {
            handler(searchField.text)
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
        boundComponent.apply {
            styles["display"] = "flex"
            styles["align-items"] = "center"
            styles["gap"] = "8px"

            dot = div {
                styles["width"] = "10px"
                styles["height"] = "10px"
                styles["border-radius"] = "50%"
                styles["background"] = "gray"
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
    text: String? = null,
    status: StatusIndicator.Status? = null,
    block: @WebforjDsl StatusIndicator.() -> Unit = {}
): StatusIndicator {
    val indicator = StatusIndicator()
    text?.let { indicator.text = it }
    status?.let { indicator.status = it }
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
