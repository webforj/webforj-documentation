---
title: Using the DSL
sidebar_position: 10
_i18n_hash: 05d1319dd97f2d32392408b2e4ae9058
---
Die Kotlin DSL bietet Builder-Funktionen für webforJ-Komponenten. Jede Funktion erstellt eine Komponente, fügt sie einem übergeordneten Container hinzu und führt einen Konfigurationsblock aus. Diese Seite behandelt die Muster und Konventionen, die Sie beim Erstellen von UIs mit der DSL verwenden werden.

## Namenskonventionen {#naming-conventions}

DSL-Funktionen sind für alle standardmäßigen webforJ-Komponenten verfügbar, einschließlich Schaltflächen, Feldern, Layouts, Dialogen, Schubladen, Listen und HTML-Elementen. Jede Funktion verwendet den Namen der Komponentenklasse in **camelCase**. `Button` wird zu `button()`, `TextField` wird zu `textField()` und `FlexLayout` wird zu `flexLayout()`.

```kotlin
div {
  button("Klicke mich")
  textField("Benutzername")
  flexLayout {
    // geschachtelter Inhalt
  }
}
```

:::important Verwendung der `Break`-Komponente
Eine Ausnahme: `Break` verwendet Backticks, da `break` ein Schlüsselwort in Kotlin ist:

```kotlin
div {
  span("Zeile eins")
  `break`()
  span("Zeile zwei")
}
```
:::

## Erstellen von Komponenten {#creating-components}

Erstellen Sie eine Komponente, indem Sie ihre DSL-Funktion zu einem Elternblock hinzufügen, zusammen mit den optionalen Argumenten und dem Konfigurationsblock, wie unten gezeigt:

```kotlin
div {
  // Erstellt eine Schaltfläche, fügt sie zu diesem div hinzu und führt dann den Block aus
  button("Absenden") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

Wenn Sie die DSL-Funktion einer Komponente verwenden, erstellt sie die Komponente, fügt sie dem Elternteil hinzu und führt dann den Konfigurationsblock aus. Der Konfigurationsblock erhält die Komponente als Empfänger (`this`), sodass Sie direkt auf Eigenschaften und Methoden zugreifen können:

```kotlin
textField("E-Mail") {
  placeholder = "you@example.com"   // this.placeholder
  required = true                   // this.required
  onModify { validate() }           // this.onModify(...)
}
```

## Geschachtelte Komponenten {#nesting-components}

Komponenten, die Kinder enthalten können, akzeptieren geschachtelte DSL-Aufrufe innerhalb ihres Blocks:

```kotlin
flexLayout {
  direction = FlexDirection.COLUMN

  h1("Dashboard")

  div {
    paragraph("Willkommen zurück!")
    button("Berichte anzeigen")
  }

  flexLayout {
    direction = FlexDirection.ROW
    button("Einstellungen")
    button("Abmelden")
  }
}
```

### Geltungsbereichssicherheit {#scope-safety}

Die DSL gewährleistet einen ordnungsgemäßen Geltungsbereich. Sie können nur Kinder zu Komponenten hinzufügen, die diese unterstützen, und der Compiler verhindert versehentliche Verweise auf äußere Bereiche:

```kotlin
div {
  button("Absenden") {
    // Dies sieht so aus, als würde es einen Absatz innerhalb der Schaltfläche hinzufügen,
    // aber es würde ihn tatsächlich zum äußeren div hinzufügen.
    // Die DSL erkennt diesen Fehler zur Compile-Zeit.
    paragraph("Wird gesendet...") // Kompiliert nicht
  }
}
```

Wenn Sie zu einem äußeren Geltungsbereich hinzufügen müssen, verwenden Sie das bezeichnete `this`, um die Absicht explizit zu machen:

```kotlin
div {
  button("Absenden") {
    this@div.add(Paragraph("Wird gesendet..."))  // Explizit ist erlaubt
  }
}
```

Dies hält den UI-Code vorhersehbar, indem es Geltungsbereichsübergänge sichtbar macht.

## Styling von Komponenten {#styling-components}

Die Kotlin DSL bietet eine `styles`-Erweiterungseigenschaft, die einen map-ähnlichen Zugriff auf CSS-Eigenschaften bietet, der äquivalent zu `setStyle()` und `getStyle()` in Java ist:

```kotlin
button("Stylisiert") {
  styles["background-color"] = "#007bff"
  styles["color"] = "weiß"
  styles["padding"] = "12px 24px"
  styles["border-radius"] = "4px"
}
```

:::tip[CSS-Klassen]
Für wiederverwendbare Stile fügen Sie CSS-Klassen anstelle von Inline-Stilen hinzu. Die `HasClassName`-Erweiterung ermöglicht es, Klassennamen mit `+=` hinzuzufügen:

```kotlin
button("Primäre Aktion") {
  classNames += "btn-primary"
}
```
:::

## Ereignisverarbeitung {#event-handling}

Komponenten müssen fast immer auf Benutzerinteraktionen reagieren. Die DSL bietet eine prägnante Ereignislistener-Syntax mit `on`-Präfixmethoden, die Lambdas akzeptieren:

```kotlin
button("Speichern") {
  onClick {
    saveData()
    showNotification("Gespeichert!")
  }
}

textField("Suchen") {
  onModify { event ->
    performSearch(event.text)
  }
}
```

## Häufige Parameter {#common-parameters}

Zusätzlich zu Konfigurationsblöcken akzeptieren die meisten DSL-Funktionen auch gängige Parameter vor dem Block für häufig verwendete Optionen:

```kotlin
// Textparameter für Beschriftungen/Inhalte
button("Klicke mich")
h1("Seitetitel")
paragraph("Textkörper")

// Beschriftung und Platzhalter für Felder
textField("Benutzername", placeholder = "Benutzernamen eingeben")
passwordField("Passwort", placeholder = "Passwort eingeben")

// Werteparameter für Eingaben
numberField("Menge", value = 1.0) {
  min = 0.0
  max = 100.0
}
```

:::tip Argumente mit angegebenen Namen
Benannte Argumente ermöglichen es Ihnen, Parameter in beliebiger Reihenfolge zu übergeben, unabhängig davon, wie sie in der Funktionssignatur erscheinen.
:::

## Erstellen einer vollständigen Ansicht {#building-a-complete-view}

Mit diesen Mustern im Hinterkopf finden Sie hier ein vollständiges Formular, das sie zusammenführt:

``` kotlin
@Route("kontakt")
class KontaktAnsicht : Composite<Div>() {

  private val self = boundComponent

  init {
    self.apply {
      styles["max-width"] = "400px"
      styles["padding"] = "20px"

      h2("Kontaktieren Sie uns")

      val nameField = textField("Name", placeholder = "Ihr Name") {
        styles["width"] = "100%"
        styles["margin-bottom"] = "16px"
      }

      val emailField = textField("E-Mail", placeholder = "you@example.com") {
        styles["width"] = "100%"
      }

      val messageField = textArea("Nachricht", placeholder = "Wie können wir helfen?") {
        styles["width"] = "100%"
      }

      button("Nachricht senden") {
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
    // Behandlung der Formularübermittlung
  }
}
```

Die DSL hält die UI-Struktur lesbar, während sie vollen Zugriff auf die Komponenten-Konfiguration bietet.
