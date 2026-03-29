---
title: Extending the DSL
sidebar_position: 20
_i18n_hash: 73b71a500428fdbc51cd490f19d1eef9
---
Kotlin DSL on laajennettavissa, mikä mahdollistaa DSL-funktioiden lisäämisen mukautetuille komponenteille tai kolmannen osapuolen kirjastoille. Voit rakentaa yhdistelmäkomponentteja, jotka käyttävät DSL:ää sisäisesti.

## Komponenttien lisääminen DSL:ään {#adding-components-to-the-dsl}

Jotta mikä tahansa komponentti olisi saatavilla DSL:ssä, luo laajennusfunktio `HasComponents`:lle, joka käyttää `init`-aputoimintoa.

### Perus DSL-funktio {#basic-dsl-function}

Tässä on malli yksinkertaiselle komponentille. Tämä esimerkki oletetaan, että sinulla on mukautettu `Badge`-komponentti:

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

`init`-toiminto tekee kolme asiaa:
1. Lisää komponentin vanhempaan säiliöön
2. Suorittaa konfigurointilohkon
3. Palauttaa konfiguroidun komponentin

Nyt voit käyttää komponenttia DSL-koodissa:

```kotlin
div {
  badge {
    text = "Uusi"
    variant = Badge.Variant.PRIMARY
  }
}
```

### Parametrien lisääminen {#adding-parameters}

Useimmat DSL-funktiot hyväksyvät yleisiä parametreja ennen konfigurointilohkoa:

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

Käyttö on tiivistynyt:

```kotlin
div {
  badge("Uusi", Badge.Variant.PRIMARY)
  badge("Ale") {
    styles["font-size"] = "12px"
  }
}
```

## Yhdistelmäkomponenttien luominen {#creating-composite-components}

`Composite` kääri useita komponentteja yhteen uudelleenkäytettävään yksikköön. DSL toimii hyvin yhdistelmän rakenteen määrittämisessä.

### Perusyhdistelmä {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

  val searchField: TextField
  val searchButton: Button

  init {
    boundComponent.apply {
      styles["display"] = "flex"
      styles["gap"] = "8px"

      searchField = textField(placeholder = "Hae...") {
        styles["flex"] = "1"
      }

      searchButton = button("Hae") {
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

Yhdistelmä altistaa komponentti viittauksia ulkoista käyttöä varten ja tarjoaa käteviä menetelmiä yleisiin toimintoihin.

### DSL-tuen lisääminen {#adding-dsl-support}

Luo DSL-funktio, jotta yhdistelmä voidaan käyttää kuin sisäänrakennettuja komponentteja:

```kotlin
fun @WebforjDsl HasComponents.searchBox(
  block: @WebforjDsl SearchBox.() -> Unit = {}
): SearchBox {
  return init(SearchBox(), block)
}
```

Nyt se integroituu luonnollisesti:

```kotlin
div {
  h1("Tuoteluettelo")

  searchBox {
    onSearch { query ->
      filterProducts(query)
    }
  }

  // Tuotelista...
}
```

### Esimerkki: Tilaindikaattori {#example-status-indicator}

Tässä on täydellinen esimerkki tilaindikaattorin yhdistelmästä:

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
        styles["background"] = "harmaa"
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

// DSL-funktio
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

Käyttö:

```kotlin
div {
  statusIndicator("Tietokanta", StatusIndicator.Status.ACTIVE)
  statusIndicator("Cache", StatusIndicator.Status.WARNING)
  statusIndicator("Ulkoisen API", StatusIndicator.Status.ERROR)
}
```
