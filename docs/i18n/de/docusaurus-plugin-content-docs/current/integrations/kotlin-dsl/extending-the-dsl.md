---
title: Extending the DSL
sidebar_position: 20
_i18n_hash: d9b9528f9a0fb3489ff11391012158f5
---
Die Kotlin DSL ist erweiterbar und ermöglicht das Hinzufügen von DSL-Funktionen für benutzerdefinierte Komponenten oder Bibliotheken von Drittanbietern. Sie können zusammengesetzte Komponenten erstellen, die die DSL intern verwenden.

## Komponenten zur DSL hinzufügen {#adding-components-to-the-dsl}

Um eine Komponente in der DSL verfügbar zu machen, erstellen Sie eine Erweiterungsfunktion für `HasComponents`, die die Hilfsfunktion `init` verwendet.

### Grundlegende DSL-Funktion {#basic-dsl-function}

Hier ist das Muster für eine einfache Komponente. Dieses Beispiel verwendet eine benutzerdefinierte `StarRating`-Komponente:

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

Die `init`-Funktion macht drei Dinge:
1. Fügt die Komponente dem übergeordneten Container hinzu
2. Führt den Konfigurationsblock aus
3. Gibt die konfigurierte Komponente zurück

Jetzt können Sie die Komponente im DSL-Code verwenden:

```kotlin
div {
  starRating {
    value = 4
    max = 5
  }
}
```

### Parameter hinzufügen {#adding-parameters}

Die meisten DSL-Funktionen akzeptieren gängige Parameter vor dem Konfigurationsblock:

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

Die Verwendung wird damit prägnanter:

```kotlin
div {
  starRating(value = 4, max = 5)
  starRating(value = 3) {
    styles["color"] = "gold"
  }
}
```

## Zusammengesetzte Komponenten erstellen {#creating-composite-components}

Ein `Composite` fasst mehrere Komponenten in einer einzigen wiederverwendbaren Einheit zusammen. Die DSL eignet sich gut zur Definition der zusammengesetzten Struktur.

### Grundlegendes Composite {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

  private val self = boundComponent
  val searchField: TextField
  val searchButton: Button

  init {
    self.apply {
      styles["display"] = "flex"
      styles["gap"] = "8px"

      searchField = textField(placeholder = "Suche...") {
        styles["flex"] = "1"
      }

      searchButton = button("Suchen") {
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

Das Composite stellt Komponentenreferenzen für den externen Zugriff bereit und bietet Hilfsmethoden für häufige Operationen.

### DSL-Unterstützung hinzufügen {#adding-dsl-support}

Erstellen Sie eine DSL-Funktion, damit das Composite wie eingebaute Komponenten verwendet werden kann:

```kotlin
fun @WebforjDsl HasComponents.searchBox(
  block: @WebforjDsl SearchBox.() -> Unit = {}
): SearchBox {
  return init(SearchBox(), block)
}
```

Jetzt integriert es sich natürlich:

```kotlin
div {
  h1("Produktkatalog")

  searchBox {
    onSearch { query ->
      filterProducts(query)
    }
  }

  // Produktliste...
}
```

### Beispiel: Statusindikator {#example-status-indicator}

Hier ist ein komplettes Beispiel für ein Composite des Statusindikators:

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
        styles["background"] = "grau"
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

// DSL-Funktion
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

Verwendung:

```kotlin
div {
  statusIndicator("Datenbank", StatusIndicator.Status.ACTIVE)
  statusIndicator("Cache", StatusIndicator.Status.WARNING)
  statusIndicator("Externe API", StatusIndicator.Status.ERROR)
}
```
