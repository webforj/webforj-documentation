---
title: Using the DSL
sidebar_position: 10
_i18n_hash: 05d1319dd97f2d32392408b2e4ae9058
---
De Kotlin DSL biedt bouwfuncties voor webforJ-componenten. Elke functie maakt een component, voegt het toe aan een bovenliggend container en voert een configuratieblok uit. Deze pagina behandelt de patronen en conventies die je zult gebruiken bij het bouwen van gebruikersinterfaces met de DSL.

## Naamgevingsconventies {#naming-conventions}

DSL-functies zijn beschikbaar voor alle standaard webforJ-componenten, waaronder knoppen, velden, lay-outs, dialoogvensters, laden, lijsten en HTML-elementen. Elke functie gebruikt de naam van de componentklasse in **camelCase**. `Button` wordt `button()`, `TextField` wordt `textField()` en `FlexLayout` wordt `flexLayout()`.

```kotlin
div {
  button("Klik op mij")
  textField("Gebruikersnaam")
  flexLayout {
    // geneste inhoud
  }
}
```

:::important Gebruik van de `Break`-component
Eén uitzondering: `Break` gebruikt backticks omdat `break` een Kotlin-trefwoord is:

```kotlin
div {
  span("Regel één")
  `break`()
  span("Regel twee")
}
```
:::

## Componenten creëren {#creating-components}

Creëer een component door de DSL-functie toe te voegen aan een bovenliggend blok, samen met de optionele argumenten en configuratieblok, zoals hieronder getoond:

```kotlin
div {
  // Creëert een Button, voegt deze toe aan deze div en voert vervolgens het blok uit
  button("Verzenden") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

Wanneer je de DSL-functie van een component gebruikt, creëert het de component, voegt deze toe aan het bovenliggende element en voert vervolgens het configuratieblok uit. Het configuratieblok ontvangt de component als zijn ontvanger (`this`), zodat je rechtstreeks toegang hebt tot eigenschappen en methoden:

```kotlin
textField("E-mail") {
  placeholder = "jij@example.com"   // this.placeholder
  required = true                   // this.required
  onModify { validate() }           // this.onModify(...)
}
```

## Componenten inbedden {#nesting-components}

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

De DSL handhaaft een goede scoping. Je kunt alleen kinderen toevoegen aan componenten die dit ondersteunen, en de compiler voorkomt onbedoelde verwijzingen naar externe scopes:

```kotlin
div {
  button("Verzenden") {
    // Dit lijkt alsof het een paragraaf binnen de knop toevoegt,
    // maar het zou deze eigenlijk aan de buitenste div toevoegen.
    // De DSL vangt deze fout op bij het compileren.
    paragraph("Verzenden...") // Compileert niet
  }
}
```

Als je aan een externe scope moet toevoegen, gebruik dan de gelabelde `this` om de intentie expliciet te maken:

```kotlin
div {
  button("Verzenden") {
    this@div.add(Paragraph("Verzenden..."))  // Expliciet is toegestaan
  }
}
```

Dit houdt de gebruikersinterface-code voorspelbaar door scope-sprongen zichtbaar te maken.

## Componenten stijlen {#styling-components}

De Kotlin DSL biedt een `styles` extensie-eigenschap die toegang biedt tot CSS-eigenschappen met behulp van haakjes, equivalent aan `setStyle()` en `getStyle()` in Java:

```kotlin
button("Gestileerd") {
  styles["background-color"] = "#007bff"
  styles["color"] = "wit"
  styles["padding"] = "12px 24px"
  styles["border-radius"] = "4px"
}
```

:::tip[CBS-klassen]
Voor herbruikbare stijlen, voeg CSS-klassen toe in plaats van inline stijlen. De `HasClassName` extensie maakt het mogelijk om klassennamen toe te voegen met `+=`:

```kotlin
button("Primaire Actie") {
  classNames += "btn-primary"
}
```
:::

## Evenementverwerking {#event-handling}

Componenten moeten bijna altijd reageren op gebruikerse interactie. De DSL biedt beknopte syntaxis voor gebeurtenisluisteraars met behulp van `on`-prefixmethoden die lambdas accepteren:

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

Naast configuratieblokken accepteren de meeste DSL-functies ook veelvoorkomende parameters vóór het blok voor vaak gebruikte opties:

```kotlin
// Tekstparameter voor labels/inhoud
button("Klik op mij")
h1("Pagina Titel")
paragraph("Body tekst")

// Label en placeholder voor velden
textField("Gebruikersnaam", placeholder = "Voer gebruikersnaam in")
passwordField("Wachtwoord", placeholder = "Voer wachtwoord in")

// Waarde parameters voor invoer
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

  private val self = boundComponent

  init {
    self.apply {
      styles["max-width"] = "400px"
      styles["padding"] = "20px"

      h2("Neem Contact Met Ons Op")

      val nameField = textField("Naam", placeholder = "Jouw naam") {
        styles["width"] = "100%"
        styles["margin-bottom"] = "16px"
      }

      val emailField = textField("E-mail", placeholder = "jij@example.com") {
        styles["width"] = "100%"
      }

      val messageField = textArea("Bericht", placeholder = "Hoe kunnen we helpen?") {
        styles["width"] = "100%"
      }

      button("Bericht Versturen") {
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
    // Verwerk formulierindiening
  }
}
```

De DSL houdt de UI-structuur leesbaar terwijl het je volledige toegang geeft tot componentconfiguratie.
