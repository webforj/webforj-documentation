---
title: Using the DSL
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: 54b936e846c3049cd3d6528e37c864d6
---
De Kotlin DSL biedt bouwfuncties voor webforJ-componenten. Elke functie creëert een component, voegt deze toe aan een bovenliggend container, en voert een configuratieblok uit. Deze pagina behandelt de patronen en conventies die je zult gebruiken bij het bouwen van UI's met de DSL.

## Naamgevingsconventies {#naming-conventions}

DSL-functies zijn beschikbaar voor alle standaard webforJ-componenten, waaronder knoppen, velden, lay-outs, dialoogvensters, lades, lijsten en HTML-elementen. Elke functie gebruikt de naam van de componentklasse in **camelCase**. `Button` wordt `button()`, `TextField` wordt `textField()`, en `FlexLayout` wordt `flexLayout()`.

```kotlin
div {
    button("Klik op mij")
    textField(" gebruikersnaam")
    flexLayout {
        // geneste inhoud
    }
}
```
:::important `Header` en `Footer` methoden
De `header` en `footer` DSL-methoden zijn hernoemd naar `nativeHeader` en `nativeFooter` om conflicten met header- en footer-slots van andere componenten te vermijden.
:::

:::important Gebruik van de `Break` component
Één uitzondering: `Break` gebruikt backticks omdat `break` een Kotlin-sleutelwoord is:

```kotlin
div {
    span("Regel één")
    `break`()
    span("Regel twee")
}
```
:::

## Componenten maken {#creating-components}

Maak een component door de DSL-functie toe te voegen aan een bovenliggend blok, samen met de optionele argumenten en configuratieblok, zoals hieronder weergegeven:

```kotlin
div {
    // Creëert een Button, voegt deze toe aan deze div en voert vervolgens het blok uit
    button("Verzenden") {
        theme = ButtonTheme.PRIMARY
        onClick { handleSubmit() }
    }
}
```

Wanneer je de DSL-functie van een component gebruikt, creëert deze de component, voegt deze toe aan de ouder en voert vervolgens het configuratieblok uit. Het configuratieblok ontvangt de component als zijn ontvanger (`this`), zodat je eigenschappen en methoden rechtstreeks kunt benaderen:

```kotlin
textField("Email") {
    placeholder = "jij@example.com"   // this.placeholder
    required = true                   // this.required
    onModify { validate() }           // this.onModify(...)
}
```

## Componenten nesten {#nesting-components}

Componenten die kinderen kunnen bevatten, accepteren geneste DSL-aanroepen binnen hun blok:

```kotlin
flexLayout {
    direction = FlexDirection.COLUMN

    h1("Dashboard")

    div {
        paragraph("Welkom terug!")
        button("Bekijk rapporten")
    }

    flexLayout {
        direction = FlexDirection.ROW
        button("Instellingen")
        button("Uitloggen")
    }
}
```

### Scope veiligheid {#scope-safety}

De DSL handhaaft een juiste afbakening. Je kunt alleen kinderen toevoegen aan componenten die ze ondersteunen, en de compiler voorkomt onbedoelde verwijzingen naar externe scopes:

```kotlin
div {
    button("Verzenden") {
        // Dit lijkt alsof het een paragraaf binnen de knop toevoegt,
        // maar het zou het eigenlijk aan de buitenste div toevoegen.
        // De DSL vangt deze fout op compileertijd op.
        paragraph("Verzendt...") // Compileert niet
    }
}
```

Als je aan een externe scope wilt toevoegen, gebruik dan gelabeld `this` om de intentie expliciet te maken:

```kotlin
div {
    button("Verzenden") {
        this@div.add(Paragraph("Verzendt..."))  // Expliciet is toegestaan
    }
}
```

Dit houdt UI-code voorspelbaar door de scope-wisselingen zichtbaar te maken.

## Componenten stijlen {#styling-components}

De Kotlin DSL biedt een `styles` extensie-eigenschap die toegang geeft via haakjes tot CSS-eigenschappen, gelijkwaardig aan `setStyle()` en `getStyle()` in Java:

```kotlin
button("Gestoeld") {
    styles["background-color"] = "#007bff"
    styles["color"] = "wit"
    styles["padding"] = "12px 24px"
    styles["border-radius"] = "4px"
}
```

:::tip[CSS-klassen]
Voor herbruikbare stijlen, voeg CSS-klassen toe in plaats van inline-stijlen. De `HasClassName` extensie maakt het mogelijk om klassennamen toe te voegen met `+=`:

```kotlin
button("Primaire Actie") {
    classNames += "btn-primary"
}
```
:::

## Evenementverwerking {#event-handling}

Componenten moeten bijna altijd reageren op gebruikersinteractie. De DSL biedt beknopte syntaxis voor gebeurtenisluisteraars met behulp van `on` prefixmethoden die lambdas accepteren:

```kotlin
button("Opslaan") {
    onClick {
        saveData()
        showNotification("Opgeslagen!")
    }
}

textField("Zoeken") {
    onModify { event ->
        performSearch(event.text)
    }
}
```

## Veelvoorkomende parameters {#common-parameters}

Naast configuratieblokken accepteren de meeste DSL-functies ook veelvoorkomende parameters voor vaak gebruikte opties:

```kotlin
// Tekstparameter voor labels/inhoud
button("Klik op mij")
h1("Pagina titel")
paragraph("Lichaam tekst")

// Label en placeholder voor velden
textField("Gebruikersnaam", placeholder = "Voer gebruikersnaam in")
passwordField("Wachtwoord", placeholder = "Voer wachtwoord in")

// Waardeparameters voor invoeren
numberField("Hoeveelheid", value = 1.0) {
    min = 0.0
    max = 100.0
}
```

:::tip Argumenten met gespecificeerde namen
Genoemde argumenten stellen je in staat om parameters in elke volgorde door te geven, ongeacht hoe ze in de functiehandtekening verschijnen.
:::


## Een complete weergave bouwen {#building-a-complete-view}

Met deze patronen in de hand, hier is een compleet formulier dat ze samenbrengt:

```kotlin
@Route("contact")
class ContactView : Composite<Div>() {

    init {
        boundComponent.apply {
            styles["max-width"] = "400px"
            styles["padding"] = "20px"

            h2("Neem contact met ons op")

            val nameField = textField("Naam", placeholder = "Jouw naam") {
                styles["width"] = "100%"
                styles["margin-bottom"] = "16px"
            }

            val emailField = textField("Email", placeholder = "jij@example.com") {
                styles["width"] = "100%"
            }

            val messageField = textArea("Bericht", placeholder = "Hoe kunnen we helpen?") {
                styles["width"] = "100%"
            }

            button("Bericht verzenden") {
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
        // Verwerk formulierinzending
    }
}
```

De DSL houdt de UI-structuur leesbaar terwijl je volledige toegang hebt tot componentconfiguratie.
