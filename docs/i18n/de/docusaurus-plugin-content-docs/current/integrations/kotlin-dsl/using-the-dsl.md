---
title: Using the DSL
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: 54b936e846c3049cd3d6528e37c864d6
---
Die Kotlin DSL bietet Builder-Funktionen für webforJ-Komponenten. Jede Funktion erstellt eine Komponente, fügt sie einem übergeordneten Container hinzu und führt einen Konfigurationsblock aus. Diese Seite behandelt die Muster und Konventionen, die Sie beim Erstellen von UIs mit der DSL verwenden werden.

## Namenskonventionen {#naming-conventions}

DSL-Funktionen werden für alle standardmäßigen webforJ-Komponenten bereitgestellt, einschließlich Schaltflächen, Feldern, Layouts, Dialogen, Schubladen, Listen und HTML-Elementen. Jede Funktion verwendet den Klassennamen der Komponente in **camelCase**. `Button` wird zu `button()`, `TextField` wird zu `textField()` und `FlexLayout` wird zu `flexLayout()`.

```kotlin
div {
    button("Klicke mich")
    textField("Benutzername")
    flexLayout {
        // geschachteter Inhalt
    }
}
```
:::important `Header` und `Footer` Methoden
Die `header`- und `footer`-DSL-Methoden wurden in `nativeHeader` und `nativeFooter` umbenannt, um Konflikte mit Header- und Footer-Slots anderer Komponenten zu vermeiden.
:::

:::important Verwendung der `Break`-Komponente
Eine Ausnahme: `Break` verwendet Backticks, da `break` ein Kotlin-Schlüsselwort ist:

```kotlin
div {
    span("Linie eins")
    `break`()
    span("Linie zwei")
}
```
:::

## Komponenten erstellen {#creating-components}

Erstellen Sie eine Komponente, indem Sie ihre DSL-Funktion zu einem übergeordneten Block hinzufügen, zusammen mit den optionalen Argumenten und dem Konfigurationsblock, wie unten gezeigt:

```kotlin
div {
    // Erstellt einen Button, fügt ihn zu diesem div hinzu und führt dann den Block aus
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

### Umfangssicherheit {#scope-safety}

Die DSL erzwingt eine korrekte Bereichseinschränkung. Sie können nur Kindern zu Komponenten hinzufügen, die sie unterstützen, und der Compiler verhindert versehentliche Verweise auf äußere Bereiche:

```kotlin
div {
    button("Absenden") {
        // Dies sieht so aus, als würde es einen Absatz innerhalb der Schaltfläche hinzufügen,
        // aber es würde tatsächlich zum äußeren div hinzugefügt werden.
        // Die DSL erfasst diesen Fehler zur Kompilierzeit.
        paragraph("Absenden...") // Wird nicht kompiliert
    }
}
```

Wenn Sie zu einem äußeren Bereich hinzufügen müssen, verwenden Sie das bezeichnete `this`, um die Absicht explizit zu machen:

```kotlin
div {
    button("Absenden") {
        this@div.add(Paragraph("Absenden..."))  // Explizit ist erlaubt
    }
}
```

Dies hält den UI-Code vorhersehbar, indem es Bereichswechsel sichtbar macht.

## Komponenten stylen {#styling-components}

Die Kotlin DSL bietet eine `styles`-Erweiterungseigenschaft, die einen map-ähnlichen Zugriff auf CSS-Eigenschaften ermöglicht, der `setStyle()` und `getStyle()` in Java entspricht:

```kotlin
button("Stylish") {
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

## Ereignisbehandlung {#event-handling}

Komponenten müssen fast immer auf Nutzerinteraktionen reagieren. Die DSL bietet eine prägnante Syntax für Ereignislistener mit Methoden, die das `on`-Präfix verwenden und Lambdas akzeptieren:

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

Zusätzlich zu den Konfigurationsblöcken akzeptieren die meisten DSL-Funktionen auch gemeinsame Parameter vor dem Block für häufig verwendete Optionen:

```kotlin
// Textparameter für Beschriftungen/Inhalte
button("Klicke mich")
h1("Seitenüberschrift")
paragraph("Fließtext")

// Beschriftung und Platzhalter für Felder
textField("Benutzername", placeholder = "Benutzername eingeben")
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

## Erstellung einer vollständigen Ansicht {#building-a-complete-view}

Mit diesen Mustern im Hinterkopf ist hier ein vollständiges Formular, das sie zusammenbringt:

```kotlin
@Route("kontakt")
class ContactView : Composite<Div>() {

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
        // Formularübermittlung verarbeiten
    }
}
```

Die DSL hält die UI-Struktur lesbar, während Sie vollen Zugriff auf die Konfiguration der Komponenten haben.
