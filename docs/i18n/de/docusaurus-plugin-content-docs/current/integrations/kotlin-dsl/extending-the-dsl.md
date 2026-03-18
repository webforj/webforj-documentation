---
title: Extending the DSL
sidebar_position: 20
_i18n_hash: 73b71a500428fdbc51cd490f19d1eef9
---
Die Kotlin DSL ist erweiterbar und ermöglicht das Hinzufügen von DSL-Funktionen für benutzerdefinierte Komponenten oder Drittanbieter-Bibliotheken. Sie können zusammengesetzte Komponenten erstellen, die die DSL intern verwenden.

## Komponenten zur DSL hinzufügen {#adding-components-to-the-dsl}

Um eine Komponente in der DSL verfügbar zu machen, erstellen Sie eine Erweiterungsfunktion auf `HasComponents`, die die `init`-Helferfunktion verwendet.

### Grundlegende DSL-Funktion {#basic-dsl-function}

Hier ist das Muster für eine einfache Komponente. Dieses Beispiel geht davon aus, dass Sie eine benutzerdefinierte `Badge`-Komponente haben:

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

Die `init`-Funktion macht drei Dinge:
1. Fügt die Komponente dem übergeordneten Container hinzu
2. Führt den Konfigurationsblock aus
3. Gibt die konfigurierte Komponente zurück

Jetzt können Sie die Komponente im DSL-Code verwenden:

```kotlin
div {
  badge {
    text = "Neu"
    variant = Badge.Variant.PRIMARY
  }
}
```

### Parameter hinzufügen {#adding-parameters}

Die meisten DSL-Funktionen akzeptieren gemeinsame Parameter vor dem Konfigurationsblock:

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

Die Verwendung wird prägnanter:

```kotlin
div {
  badge("Neu", Badge.Variant.PRIMARY)
  badge("Verkauf") {
    styles["font-size"] = "12px"
  }
}
```

## Zusammengesetzte Komponenten erstellen {#creating-composite-components}

Ein `Composite` wickelt mehrere Komponenten in eine einzelne wiederverwendbare Einheit ein. Die DSL eignet sich gut zur Definition von zusammengesetzten Strukturen.

### Grundlegendes Composite {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

  val searchField: TextField
  val searchButton: Button

  init {
    boundComponent.apply {
      styles["display"] = "flex"
      styles["gap"] = "8px"

      searchField = textField(placeholder = "Suchen...") {
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

Das Composite exponiert Komponentenreferenzen für den externen Zugriff und bietet praktische Methoden für häufige Operationen.

### DSL-Unterstützung hinzufügen {#adding-dsl-support}

Erstellen Sie eine DSL-Funktion, damit das Composite wie integrierte Komponenten verwendet werden kann:

```kotlin
fun @WebforjDsl HasComponents.searchBox(
  block: @WebforjDsl SearchBox.() -> Unit = {}
): SearchBox {
  return init(SearchBox(), block)
}
```

Jetzt integriert es sich nahtlos:

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

### Beispiel: Statusanzeige {#example-status-indicator}

Hier ist ein vollständiges Beispiel für eine Statusanzeige:

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
