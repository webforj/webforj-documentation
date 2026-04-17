---
title: Extending the DSL
sidebar_position: 20
_i18n_hash: d9b9528f9a0fb3489ff11391012158f5
---
Kotlin DSL on laajennettavissa, mikä mahdollistaa DSL-funktioiden lisäämisen mukautetuille komponenteille tai kolmannen osapuolen kirjastoille. Voit rakentaa yhdistettyjä komponentteja, jotka käyttävät DSL:ää sisäisesti.

## Komponenttien lisääminen DSL:ään {#adding-components-to-the-dsl}

Jotta mikä tahansa komponentti olisi saatavilla DSL:ssä, luo laajennusfunktio `HasComponents`-rajapinnalle, joka käyttää `init`-apufunktiota.

### Perus DSL-funktio {#basic-dsl-function}

Tässä on malli yksinkertaiselle komponentille. Tämä esimerkki käyttää mukautettua `StarRating`-komponenttia:

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

`init`-funktio tekee kolme asiaa:
1. Lisää komponentin vanhempaan säiliöön
2. Suorittaa konfigurointiblokin
3. Palauttaa konfiguroidun komponentin

Nyt voit käyttää komponenttia DSL-koodissa:

```kotlin
div {
  starRating {
    value = 4
    max = 5
  }
}
```

### Parametrien lisääminen {#adding-parameters}

Useimmat DSL-funktiot hyväksyvät yleisiä parametreja ennen konfigurointiblokkia:

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

Käyttö on tiiviimpää:

```kotlin
div {
  starRating(value = 4, max = 5)
  starRating(value = 3) {
    styles["color"] = "gold"
  }
}
```

## Yhdistettyjen komponenttien luominen {#creating-composite-components}

`Composite` yhdistää useita komponentteja yhdeksi uudelleenkäytettäväksi yksiköksi. DSL toimii hyvin yhdistetyn rakenteen määrittämisessä.

### Perusyhdistelmä {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

  private val self = boundComponent
  val searchField: TextField
  val searchButton: Button

  init {
    self.apply {
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

Yhdistelmä paljastaa komponentin viittaukset ulkoista käyttöä varten ja tarjoaa mukautettuja metodeja yleisiä toimintoja varten.

### DSL-tuen lisääminen {#adding-dsl-support}

Luo DSL-funktio, jotta yhdistelmää voidaan käyttää kuin sisäänrakennettuja komponentteja:

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

### Esimerkki: Tilan indikaattori {#example-status-indicator}

Tässä on täydellinen esimerkki tilan indikaattori yhdistelmästä:

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
  statusIndicator("Välimuisti", StatusIndicator.Status.WARNING)
  statusIndicator("Ulkoisen API", StatusIndicator.Status.ERROR)
}
```
