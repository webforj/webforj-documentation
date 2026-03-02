---
title: Extending the DSL
sidebar_position: 20
sidebar_class_name: new-content
_i18n_hash: 04d9c37b735c8334be4ef4dd3540cb01
---
Kotlin DSL on laajennettavissa, ja se mahdollistaa DSL-funktioiden lisäämisen mukautetuille komponenteille tai kolmannen osapuolen kirjastoille. Voit rakentaa yhdistettyjä komponentteja, jotka käyttävät DSL:ää sisäisesti.

## Komponenttien lisääminen DSL:ään {#adding-components-to-the-dsl}

Jotta jokainen komponentti olisi saatavilla DSL:ssä, luo laajennusfunktio `HasComponents`-rajapintaan, joka käyttää `init`-apufunktiota.

### Perus DSL-funktio {#basic-dsl-function}

Tässä on malli yksinkertaiselle komponentille. Tämä esimerkki olettaa, että sinulla on mukautettu `Badge`-komponentti:

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

`init`-funktio tekee kolme asiaa:
1. Lisää komponentin vanhempaan säiliöön
2. Suorittaa konfigurointiblokin
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

Useimmat DSL-funktiot hyväksyvät yleisiä parametreja ennen konfigurointiblokkia:

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

Käytöstä tulee tiiviimpää:

```kotlin
div {
    badge("Uusi", Badge.Variant.PRIMARY)
    badge("Myynti") {
        styles["font-size"] = "12px"
    }
}
```

## Yhdistettyjen komponenttien luominen {#creating-composite-components}

`Composite` käärii useita komponentteja yhdeksi käytettäväksi yksiköksi. DSL toimii hyvin yhdistetyn rakenteen määrittämisessä.

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

Yhdistelmä altistaa komponenttiviittaukset ulkopuolista käyttöä varten ja tarjoaa kätevät menetelmät yleisiin toimiin.

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
    h1("Tuotekatalogi")

    searchBox {
        onSearch { query ->
            filterProducts(query)
        }
    }

    // Tuotelista...
}
```

### Esimerkki: Tilan ilmaisin {#example-status-indicator}

Tässä on täydellinen esimerkki tilan ilmaisimesta yhdistelmästä:

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
    statusIndicator("Välimuisti", StatusIndicator.Status.WARNING)
    statusIndicator("Ulkoinen API", StatusIndicator.Status.ERROR)
}
```
