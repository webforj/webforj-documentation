---
title: Using the DSL
sidebar_position: 10
_i18n_hash: cde3a82377e800021761e5d430328ed9
---
Die Kotlin DSL bietet Builder-Funktionen für webforJ-Komponenten. Jede Funktion erstellt eine Komponente, fügt sie einem übergeordneten Container hinzu und führt einen Konfigurationsblock aus. Diese Seite behandelt die Muster und Konventionen, die Sie beim Erstellen von UIs mit der DSL verwenden werden.

## Benennungskonventionen {#naming-conventions}

DSL-Funktionen werden für alle Standard-webforJ-Komponenten bereitgestellt, einschließlich Schaltflächen, Feldern, Layouts, Dialogen, Schubladen, Listen und HTML-Elementen. Jede Funktion verwendet den Klassennamen der Komponente in **camelCase**. `Button` wird zu `button()`, `TextField` wird zu `textField()` und `FlexLayout` wird zu `flexLayout()`.

```kotlin
div {
  button("Klicke mich")
  textField("Benutzername")
  flexLayout {
    // verschachtelter Inhalt
  }
}
```

:::important Verwendung der `Break`-Komponente
Eine Ausnahme: `Break` verwendet rückgängig gemachte Anführungszeichen, da `break` ein Schlüsselwort in Kotlin ist:

```kotlin
div {
  span("Zeile eins")
  `break`()
  span("Zeile zwei")
}
```
:::

## Komponenten erstellen {#creating-components}

Erstellen Sie eine Komponente, indem Sie ihre DSL-Funktion in einen übergeordneten Block einfügen, zusammen mit den optionalen Argumenten und einem Konfigurationsblock, wie unten gezeigt:

```kotlin
div {
  // Erstellt eine Schaltfläche, fügt sie zu diesem div hinzu und führt dann den Block aus
  button("Absenden") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

Wenn Sie die DSL-Funktion einer Komponente verwenden, erstellt sie die Komponente, fügt sie dem Übergeordneten hinzu und führt dann den Konfigurationsblock aus. 
Der Konfigurationsblock erhält die Komponente als seinen Empfänger (`this`), sodass Sie auf Eigenschaften und Methoden direkt zugreifen können:

```kotlin
textField("E-Mail") {
  placeholder = "you@example.com"   // this.placeholder
  required = true                   // this.required
  onModify { validate() }           // this.onModify(...)
}
```

## Komponenten schachteln {#nesting-components}

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

### Bereichssicherheit {#scope-safety}

Die DSL gewährleistet eine ordnungsgemäße Bereichsverwaltung. Sie können nur Kinder zu Komponenten hinzufügen, die sie unterstützen, und der Compiler verhindert versehentliche Verweise auf äußere Bereiche:

```kotlin
div {
  button("Absenden") {
    // Dies sieht so aus, als würde es einen Absatz in die Schaltfläche einfügen,
    // aber er würde tatsächlich zum äußeren div hinzugefügt.
    // Die DSL erkennt diesen Fehler zur Kompilierzeit.
    paragraph("Wird gesendet...") // Wird nicht kompiliert
  }
}
```

Wenn Sie zu einem äußeren Bereich hinzufügen müssen, verwenden Sie das benannte `this`, um die Absicht explizit zu machen:

```kotlin
div {
  button("Absenden") {
    this@div.add(Paragraph("Wird gesendet..."))  // Explicite Erlaubt
  }
}
```

Dies hält den UI-Code vorhersehbar, indem Bereichssprünge sichtbar gemacht werden.

## Komponenten stylen {#styling-components}

Die Kotlin DSL bietet eine `styles`-Erweiterungseigenschaft, die eine map-ähnliche Klammerzugriff auf CSS-Eigenschaften ermöglicht, die `setStyle()` und `getStyle()` in Java entsprechen:

```kotlin
button("Stylized") {
  styles["background-color"] = "#007bff"
  styles["color"] = "white"
  styles["padding"] = "12px 24px"
  styles["border-radius"] = "4px"
}
```

:::tip[CSS-Klassen]
Für wiederverwendbare Stile fügen Sie CSS-Klassen anstelle von Inline-Stilen hinzu. Die `HasClassName`-Erweiterung ermöglicht das Hinzufügen von Klassennamen mit `+=`:

```kotlin
button("Primäre Aktion") {
  classNames += "btn-primary"
}
```
:::

## Ereignisverarbeitung {#event-handling}

Komponenten müssen fast immer auf Benutzerinteraktionen reagieren. Die DSL bietet eine prägnante Syntax für Ereignislistener mit `on`-Präfixmethoden, die Lambdas akzeptieren:

```kotlin
button("Speichern") {
  onClick {
    saveData()
    showNotification("Gespeichert!")
  }
}

textField("Suche") {
  onModify { event ->
    performSearch(event.text)
  }
}
```

## Gemeinsame Parameter {#common-parameters}

Zusätzlich zu Konfigurationsblöcken akzeptieren die meisten DSL-Funktionen auch gemeinsame Parameter vor dem Block für häufig verwendete Optionen:

```kotlin
// Textparameter für Beschriftungen/Inhalte
button("Klicke mich")
h1("Seitentitel")
paragraph("Textkörper")

// Beschriftung und Platzhalter für Felder
textField("Benutzername", placeholder = "Benutzernamen eingeben")
passwordField("Passwort", placeholder = "Passwort eingeben")

// Wertparameter für Eingaben
numberField("Menge", value = 1.0) {
  min = 0.0
  max = 100.0
}
```

:::tip Argumente mit angegebenen Namen
Benannte Argumente ermöglichen es Ihnen, Parameter in beliebiger Reihenfolge zu übergeben, unabhängig davon, wie sie in der Funktionssignatur erscheinen.
:::

## Eine vollständige Ansicht erstellen {#building-a-complete-view}

Mit diesen Mustern im Hinterkopf hier ist ein vollständiges Formular, das sie zusammenführt:

```kotlin
@Route("kontakt")
class Kontaktansicht : Composite<Div>() {

  init {
    boundComponent.apply {
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
    // Verarbeitung der Formularübermittlung
  }
}
```

Die DSL hält die UI-Struktur lesbar und gibt Ihnen gleichzeitig vollen Zugriff auf die Komponenten-Konfiguration.
