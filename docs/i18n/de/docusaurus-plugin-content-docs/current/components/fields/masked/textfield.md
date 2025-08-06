---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 31f236456f3f30e15115a03275be9132
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

Die `MaskedTextField`-Komponente zielt darauf ab, ein konfigurierbares und leicht validierbares Texteingabefeld bereitzustellen. Sie eignet sich gut für Apps, die ein formatiertes Eingabefeld benötigen, wie Finanz-, E-Commerce- und Gesundheits-Apps.

## Grundlagen {#basics}

Die `MaskedTextField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhaltertext und einen Listener definieren, falls sich der Wert ändert.

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Mask: ZZZZ-0000 - zum Beispiel: SAVE-2025")
```

## Maskenregeln {#mask-rules}

Die `MaskedTextField` formatiert die Texteingabe mithilfe einer Maske - einem String, der definiert, welche Zeichen an jeder Position erlaubt sind. Dadurch wird eine konsistente und strukturierte Eingabe für Dinge wie Telefonnummern, Postleitzahlen und ID-Formate sichergestellt.

### Unterstützte Maskenzeichen {#supported-mask-characters}

| Zeichen | Beschreibung                                                                                 |
|---------|---------------------------------------------------------------------------------------------|
| `X`     | Jedes druckbare Zeichen                                                                     |
| `a`     | Jedes alphabetische Zeichen (groß oder klein geschrieben)                                   |
| `A`     | Jedes alphabetische Zeichen; Kleinbuchstaben werden in Großbuchstaben umgewandelt          |
| `0`     | Jede Ziffer (0–9)                                                                          |
| `z`     | Jede Ziffer oder Buchstabe (groß oder klein geschrieben)                                   |
| `Z`     | Jede Ziffer oder Buchstabe; Kleinbuchstaben werden in Großbuchstaben umgewandelt           |

Alle anderen Zeichen in der Maske werden als Literale behandelt und müssen genau eingegeben werden. 
Zum Beispiel, eine Maske wie `XX@XX` erfordert, dass der Benutzer ein `@` in der Mitte eingibt.

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
Wenn die Maske nur `X` enthält, verhält sich das Feld wie einstandard [`TextField`](../text-field.md), das jede druckbare Eingabe zulässt. 
Dies ist nützlich, wenn Sie die Möglichkeit zur Formatierung reservieren möchten, ohne strenge Zeichenregeln anzuwenden.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Validierungsmuster {#validation-patterns}

Während Masken die Struktur der Eingabe definieren, können Sie sie mit Validierungsmustern kombinieren, um spezifischere Eingaberegeln zu erzwingen. Dies fügt eine zusätzliche Schicht der clientseitigen Validierung mithilfe von regulären Ausdrücken hinzu.

Verwenden Sie die Methode `setPattern()`, um einen benutzerdefinierten regulären Ausdruck anzuwenden:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Erzwingt einen 10-Zeichen langen alphanumerischen Code
```

Dies stellt sicher, dass die Eingabe nicht nur mit der Maske übereinstimmt, sondern auch einer definierten Struktur, wie Länge oder erlaubten Zeichen, entspricht.

Dies ist besonders nützlich, wenn:

- Die Maske zu viel Flexibilität zulässt
- Sie eine exakte Länge oder ein bestimmtes Format erzwingen möchten (z.B. hex, Base64, UUID)

:::tip Format des regulären Ausdrucks
Das Muster muss ein gültiger [JavaScript regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom Typ `RegExp` verwendet wird. Weitere Details finden Sie in der [HTML-Musterattributdokumentation](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Wiederherstellen des Wertes {#restoring-the-value}

Die `MaskedTextField` umfasst eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. 
Dies kann nützlich sein, um Benutzeränderungen rückgängig zu machen oder zu einer Standard-Eingabe zurückzukehren.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Möglichkeiten, den Wert wiederherzustellen {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die Standardwiederherstellungstaste, sofern nicht von einem Ereignis-Listener überschrieben)

Sie können den Wert, der wiederhergestellt werden soll, mit `setRestoreValue()` festlegen. Wenn kein Wiederherstellungswert festgelegt wurde, kehrt das Feld zum ursprünglichen Wert zum Zeitpunkt der Anzeige zurück.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

Die `MaskedTextFieldSpinner` erweitert [`MaskedTextField`](#basics) um Spinner-Steuerelemente, die es Benutzern ermöglichen, durch eine Liste vordefinierter Werte zu blättern. 
Dies verbessert das Benutzererlebnis in Situationen, in denen die Eingabe auf eine feste Menge gültiger Optionen beschränkt sein sollte.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Hauptmerkmale {#key-features}

- **Optionenlistenunterstützung**  
  Füllen Sie den Spinner mit einer Liste gültiger Stringwerte mit `setOptions()`:

  ```java
  spinner.setOptions(List.of("Option A", "Option B", "Option C"));
  ```

- **Programmgesteuertes Drehen**  
  Verwenden Sie `spinUp()` und `spinDown()`, um durch die Optionen zu navigieren:

  ```java
  spinner.spinUp();   // Wählt die nächste Option aus
  spinner.spinDown(); // Wählt die vorherige Option aus
  ```

- **Indexsteuerung**  
  Setzen oder abrufen Sie den aktuellen Auswahlindex mit:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskenkompatibilität**  
  Erbt vollständig alle Formatierung, Maskenregeln und Mustervalidierungen von `MaskedTextField`.

## Styling {#styling}

<TableBuilder name="MaskedTextField" />
