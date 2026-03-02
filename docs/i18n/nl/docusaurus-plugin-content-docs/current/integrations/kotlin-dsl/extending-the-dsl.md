---
title: Extending the DSL
sidebar_position: 20
sidebar_class_name: new-content
_i18n_hash: 04d9c37b735c8334be4ef4dd3540cb01
---
De Kotlin DSL is uitbreidbaar, waardoor je DSL-functies voor aangepaste componenten of derde-partij bibliotheken kunt toevoegen. Je kunt samengestelde componenten bouwen die de DSL intern gebruiken.

## Componenten toevoegen aan de DSL {#adding-components-to-the-dsl}

Om een component beschikbaar te maken in de DSL, maak je een extensiefunctie op `HasComponents` die de `init` hulpfunctie gebruikt.

### Basis DSL-functie {#basic-dsl-function}

Hier is het patroon voor een eenvoudige component. Dit voorbeeld gaat ervan uit dat je een aangepaste `Badge` component hebt:

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

De `init` functie doet drie dingen:
1. Voegt de component toe aan de bovenliggende container
2. Voert de configuratie-blok uit
3. Retourneert de geconfigureerde component

Nu kun je de component gebruiken in DSL-code:

```kotlin
div {
    badge {
        text = "Nieuw"
        variant = Badge.Variant.PRIMARY
    }
}
```

### Parameters toevoegen {#adding-parameters}

De meeste DSL-functies accepteren algemene parameters vóór de configuratie-blok:

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

Het gebruik wordt beknopter:

```kotlin
div {
    badge("Nieuw", Badge.Variant.PRIMARY)
    badge("Verkoop") {
        styles["font-size"] = "12px"
    }
}
```

## Samengestelde componenten maken {#creating-composite-components}

Een `Composite` wikkelt meerdere componenten in een enkele herbruikbare eenheid. De DSL werkt goed voor het definiëren van een samengestelde structuur.

### Basis samengestelde {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

    val searchField: TextField
    val searchButton: Button

    init {
        boundComponent.apply {
            styles["display"] = "flex"
            styles["gap"] = "8px"

            searchField = textField(placeholder = "Zoeken...") {
                styles["flex"] = "1"
            }

            searchButton = button("Zoeken") {
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

De samengestelde exposeert componentreferenties voor externe toegang en biedt handige methoden voor veelvoorkomende bewerkingen.

### DSL-ondersteuning toevoegen {#adding-dsl-support}

Maak een DSL-functie zodat de samengestelde kan worden gebruikt als ingebouwde componenten:

```kotlin
fun @WebforjDsl HasComponents.searchBox(
    block: @WebforjDsl SearchBox.() -> Unit = {}
): SearchBox {
    return init(SearchBox(), block)
}
```

Nu integreert het natuurlijk:

```kotlin
div {
    h1("Productcatalogus")

    searchBox {
        onSearch { query ->
            filterProducts(query)
        }
    }

    // Productlijst...
}
```

### Voorbeeld: Statusindicator {#example-status-indicator}

Hier is een compleet voorbeeld van een statusindicator-samengestelde:

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
                styles["background"] = "grijs"
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

// DSL-functie
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

Gebruik:

```kotlin
div {
    statusIndicator("Database", StatusIndicator.Status.ACTIVE)
    statusIndicator("Cache", StatusIndicator.Status.WARNING)
    statusIndicator("Externe API", StatusIndicator.Status.ERROR)
}
```
