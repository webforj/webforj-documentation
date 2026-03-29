---
title: Using the DSL
sidebar_position: 10
_i18n_hash: cde3a82377e800021761e5d430328ed9
---
De Kotlin DSL biedt builderfuncties voor webforJ-componenten. Elke functie creëert een component, voegt deze toe aan een bovenliggend container en voert een configuratieblok uit. Deze pagina behandelt de patronen en conventies die je zult gebruiken bij het bouwen van gebruikersinterfaces met de DSL.

## Naamgevingsconventies {#naming-conventions}

DSL-functies worden geleverd voor alle standaard webforJ-componenten, waaronder knoppen, velden, lay-outs, dialoogvensters, lades, lijsten en HTML-elementen. Elke functie gebruikt de componentclassnaam in **camelCase**. `Button` wordt `button()`, `TextField` wordt `textField()` en `FlexLayout` wordt `flexLayout()`.

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

Maak een component door zijn DSL-functie toe te voegen aan een bovenliggend blok, samen met de optionele argumenten en configuratieblok, zoals hieronder weergegeven:

```kotlin
div {
  // Creëert een Button, voegt deze toe aan deze div en voert vervolgens het blok uit
  button("Verzenden") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

Wanneer je de DSL-functie van een component gebruikt, creëert deze de component, voegt deze toe aan de bovenligger en voert dan het configuratieblok uit. 
Het configuratieblok ontvangt de component als zijn ontvanger (`this`), zodat je eigenschappen en methoden direct kunt benaderen:

```kotlin
textField("E-mail") {
  placeholder = "je@example.com"   // this.placeholder
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

De DSL handhaaft een goede scoping. Je kunt alleen kinderen toevoegen aan componenten die dit ondersteunen, en de compiler voorkomt per ongeluk verwijzingen naar buitenste scopes:

```kotlin
div {
  button("Verzenden") {
    // Dit lijkt alsof het een paragraaf binnen de knop toevoegt,
    // maar het zou deze daadwerkelijk aan de buitenste div toevoegen.
    // De DSL vangt deze fout bij het compileren.
    paragraph("Verzendt...") // Compileert niet
  }
}
```

Als je iets aan een buitenste scope moet toevoegen, gebruik dan de gelabelde `this` om de intentie expliciet te maken:

```kotlin
div {
  button("Verzenden") {
    this@div.add(Paragraph("Verzendt..."))  // Expliciet is toegestaan
  }
}
```

Dit houdt de UI-code voorspelbaar doordat het scope-jumps zichtbaar maakt.

## Componenten stijlen {#styling-components}

De Kotlin DSL biedt een `styles` extensie-eigenschap die bracket-toegang biedt tot CSS-eigenschappen, equivalent aan `setStyle()` en `getStyle()` in Java:

```kotlin
button("Gestyled") {
  styles["background-color"] = "#007bff"
  styles["color"] = "wit"
  styles["padding"] = "12px 24px"
  styles["border-radius"] = "4px"
}
```

:::tip[C.S.S.-klassen]
Voor herbruikbare stijlen, voeg CSS-klassen toe in plaats van inline stijlen. De `HasClassName` extensie maakt het mogelijk om klassennamen toe te voegen met `+=`:

```kotlin
button("Primaire Actie") {
  classNames += "btn-primary"
}
```
:::

## Evenementafhandeling {#event-handling}

Componenten moeten bijna altijd reageren op gebruikersinteractie. De DSL biedt beknopte syntaxis voor evenementlisteners met behulp van `on` prefixmethoden die lambdas accepteren:

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
h1("Paginatitel")
paragraph("Bodytekst")

// Label en placeholder voor velden
textField("Gebruikersnaam", placeholder = "Voer gebruikersnaam in")
passwordField("Wachtwoord", placeholder = "Voer wachtwoord in")

// Waardeparameters voor invoer
numberField("Hoeveelheid", value = 1.0) {
  min = 0.0
  max = 100.0
}
```

:::tip Argumenten met gespecificeerde namen
Genoemde argumenten stellen je in staat om parameters in om het even welke volgorde door te geven, ongeacht hoe ze in de functiehandtekening verschijnen.
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

      val emailField = textField("E-mail", placeholder = "je@example.com") {
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
    // Behandel het indienen van het formulier
  }
}
```

De DSL houdt de UI-structuur leesbaar terwijl je volledige toegang hebt tot de configuratie van componenten.
