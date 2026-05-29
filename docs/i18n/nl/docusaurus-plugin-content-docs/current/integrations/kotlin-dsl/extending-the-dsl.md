---
title: Extending the DSL
sidebar_position: 20
_i18n_hash: d9b9528f9a0fb3489ff11391012158f5
---
De Kotlin DSL is uitbreidbaar, waardoor je DSL-functies voor aangepaste componenten of derde-partijbibliotheken kunt toevoegen. Je kunt samengestelde componenten bouwen die de DSL intern gebruiken.

## Componenten aan de DSL toevoegen {#adding-components-to-the-dsl}

Om een component beschikbaar te maken in de DSL, maak je een extensiefunctie op `HasComponents` die de `init` hulpfunctie gebruikt.

### Basis DSL-functie {#basic-dsl-function}

Hier is het patroon voor een eenvoudige component. Dit voorbeeld gebruikt een aangepaste `StarRating` component:

```kotlin
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.init
import com.example.component.StarRating

fun @WebforjDsl HasComponents.starRating(
  block: @WebforjDsl StarRating.() -> Unit = {}
): StarRating {
  return init(StarRating(), block)
}
```

De `init` functie doet drie dingen:
1. Voegt de component toe aan de bovenliggende container
2. Voert de configuratieblok uit
3. Geeft de geconfigureerde component terug

Nu kun je de component gebruiken in DSL-code:

```kotlin
div {
  starRating {
    value = 4
    max = 5
  }
}
```

### Parameters toevoegen {#adding-parameters}

De meeste DSL-functies accepteren algemene parameters voordat de configuratieblok:

```kotlin
fun @WebforjDsl HasComponents.starRating(
  value: Int? = null,
  max: Int? = null,
  block: @WebforjDsl StarRating.() -> Unit = {}
): StarRating {
  val rating = StarRating()
  value?.let { rating.value = it }
  max?.let { rating.max = it }
  return init(rating, block)
}
```

Gebruik wordt beknopter:

```kotlin
div {
  starRating(value = 4, max = 5)
  starRating(value = 3) {
    styles["color"] = "gold"
  }
}
```

## Samengestelde componenten creëren {#creating-composite-components}

Een `Composite` wikkelt meerdere componenten in één herbruikbare eenheid. De DSL werkt goed voor het definiëren van een samengestelde structuur.

### Basis samengestelde {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

  private val self = boundComponent
  val searchField: TextField
  val searchButton: Button

  init {
    self.apply {
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

De samengestelde component geeft componentreferenties bloot voor externe toegang en biedt handige methoden voor veelvoorkomende bewerkingen.

### DSL-ondersteuning toevoegen {#adding-dsl-support}

Maak een DSL-functie zodat de samengestelde component kan worden gebruikt zoals ingebouwde componenten:

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

Hier is een compleet voorbeeld van een statusindicator-samengesteld:

```kotlin
class StatusIndicator : Composite<Div>() {

  private val self = boundComponent
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
    self.apply {
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
