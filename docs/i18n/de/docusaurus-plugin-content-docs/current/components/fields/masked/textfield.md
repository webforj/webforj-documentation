---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: c50931f8465e3be081ecfee03a3ef559
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

Die `MaskedTextField`-Komponente bietet ein konfigurierbares Texteingabefeld, das Formatierungsregeln und Validierungen durchsetzt. Sie eignet sich gut für Apps, die strukturierte Eingaben erfordern, wie zum Beispiel Finanz-, E-Commerce- und Gesundheitssysteme.

<!-- INTRO_END -->

## Grundlagen {#basics}

Die `MaskedTextField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhaltertext und einen Listener definieren, falls sich der Wert ändert.

```java
MaskedTextField field = new MaskedTextField("Konto-ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Maske: ZZZZ-0000 - Beispiel: SAVE-2025")
```

## Maskenregeln {#mask-rules}

Die `MaskedTextField` formatiert die Texteingabe mit einer Maske - einer Zeichenkette, die definiert, welche Zeichen an jeder Position erlaubt sind. Dies gewährleistet konsistente, strukturierte Eingaben für Dinge wie Telefonnummern, Postleitzahlen und ID-Formate.

:::tip Masken programmatisch anwenden
Um Zeichenfolgen mit derselben Maskensyntax außerhalb eines Feldes zu formatieren, beispielsweise beim Rendern von Daten in einer [`Table`](/docs/components/table/overview), verwenden Sie die Utility-Klasse [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Unterstützte Maskenzeichen {#supported-mask-characters}

| Zeichen    | Beschreibung                                                                                         |
|------------|------------------------------------------------------------------------------------------------------|
| `X`        | Jedes druckbare Zeichen                                                                               |
| `a`        | Jedes alphabetische Zeichen (groß oder klein)                                                         |
| `A`        | Jedes alphabetische Zeichen; Kleinbuchstaben werden in Großbuchstaben umgewandelt                    |
| `0`        | Jede Ziffer (0–9)                                                                                    |
| `z`        | Jede Ziffer oder Buchstabe (groß oder klein)                                                         |
| `Z`        | Jede Ziffer oder Buchstabe; Kleinbuchstaben werden in Großbuchstaben umgewandelt                     |

Alle anderen Zeichen in der Maske werden als Literale behandelt und müssen genau eingegeben werden. 
Beispielsweise erfordert eine Maske wie `XX@XX`, dass der Benutzer ein `@` in der Mitte eingibt.

- **Ungültige Zeichen** werden stillschweigend ignoriert.
- **Kurze Eingaben** werden mit Leerzeichen aufgefüllt.
- **Lange Eingaben** werden abgeschnitten, um in die Maske zu passen.

### Beispiele {#examples}

```java
field.setMask("(000) 000-0000");     // Beispiel: (123) 456-7890
field.setMask("A00 000");            // Beispiel: A1B 2C3 (kanadische Postleitzahl)
field.setMask("ZZZZ-0000");          // Beispiel: ABCD-1234
field.setMask("0000-0000-0000-0000");// Beispiel: 1234-5678-9012-3456
```

:::tip Vollständige Eingabe erlaubt
Wenn die Maske nur `X` enthält, verhält sich das Feld wie ein standardmäßiges [`TextField`](../textfield) und erlaubt jede druckbare Eingabe.
Dies ist nützlich, wenn Sie die Möglichkeit reservieren möchten, zu formatieren, ohne strenge Zeichenregeln anzuwenden.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Validierungsmuster {#validation-patterns}

Während Masken die Struktur der Eingabe definieren, können Sie sie mit Validierungsmustern kombinieren, um spezifischere Eingaberegeln durchzusetzen. Dies fügt eine zusätzliche Schicht der clientseitigen Validierung mit regulären Ausdrücken hinzu.

Verwenden Sie die Methode `setPattern()`, um einen benutzerdefinierten regulären Ausdruck anzuwenden:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Erzwingt einen 10-stelligen alphanumerischen Code
```

Dies stellt sicher, dass die Eingabe nicht nur mit der Maske übereinstimmt, sondern auch einer definierten Struktur wie Länge oder zulässige Zeichen entspricht.

Dies ist besonders nützlich, wenn:

- Die Maske zu viel Flexibilität zulässt
- Sie eine exakte Länge oder ein bestimmtes Format (z. B. Hex, Base64, UUID) durchsetzen möchten

:::tip Format des regulären Ausdrucks
Das Muster muss ein gültiger [JavaScript-regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom Typ `RegExp` verwendet wird. Weitere Details finden Sie in der [Dokumentation zum HTML-Musterattribut](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Wiederherstellen des Wertes {#restoring-the-value}

Die `MaskedTextField` enthält eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. 
Dies kann nützlich sein, um Benutzeränderungen rückgängig zu machen oder zu einer Standard-Eingabe zurückzukehren.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmatisch**, durch den Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die Standard-Wiederherstellungstaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

Sie können den Wert, auf den Sie wiederherstellen möchten, mit `setRestoreValue()` festlegen. Wenn kein Wiederherstellungswert festgelegt ist, kehrt das Feld zum Anfangswert zum Zeitpunkt seiner Darstellung zurück.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

Die `MaskedTextFieldSpinner` erweitert [`MaskedTextField`](#basics) um Spinner-Steuerelemente, die es den Benutzern ermöglichen, durch eine Liste vordefinierter Werte zu blättern. 
Dies verbessert die Benutzererfahrung in Situationen, in denen die Eingabe auf eine feste Menge gültiger Optionen beschränkt sein sollte.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Hauptmerkmale {#key-features}

- **Optionenlistenunterstützung**  
  Füllen Sie den Spinner mit einer Liste gültiger Zeichenfolgenwerte mithilfe von `setOptions()`:

  ```java
  spinner.setOptions(List.of("Option A", "Option B", "Option C"));
  ```

- **Programmiertes Drehen**  
  Verwenden Sie `spinUp()` und `spinDown()`, um durch Optionen zu navigieren:

  ```java
  spinner.spinUp();   // Wählt die nächste Option aus
  spinner.spinDown(); // Wählt die vorherige Option aus
  ```

- **Indexkontrolle**  
  Setzen oder abrufen des aktuellen Auswahlindex mit:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskenkompatibilität**  
  Erbt vollständig alle Formatierungen, Maskenregeln und Musterüberprüfungen von `MaskedTextField`.

## Styling {#styling}

<TableBuilder name="MaskedTextField" />
