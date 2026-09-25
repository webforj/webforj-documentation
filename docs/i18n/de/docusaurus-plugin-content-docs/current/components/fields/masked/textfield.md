---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 5f6c175ffd4b8d75f3b65c7b77bb13fe
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

Die `MaskedTextField`-Komponente bietet ein konfigurierbares Texteingabefeld, das Formatierungsregeln und Validierung durchsetzt. Es eignet sich gut für Anwendungen, die strukturierte Eingaben erfordern, wie z.B. Finanz-, E-Commerce- und Gesundheitssysteme.

Diese Komponente kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhaltertext und einen Listener definieren, falls sich der Wert ändert.

<!-- INTRO_END -->

```java
MaskedTextField field = new MaskedTextField("Kontonummer");
field.setMask("ZZZZ-0000")
  .setHelperText("Maske: ZZZZ-0000 - zum Beispiel: SAVE-2025")
```

## Maskenregeln {#mask-rules}

Das `MaskedTextField` formatiert die Texteingabe mithilfe einer Maske - einer Zeichenkette, die definiert, welche Zeichen an jeder Position erlaubt sind. Dies sorgt für eine konsistente, strukturierte Eingabe für Dinge wie Telefonnummern, Postleitzahlen und ID-Formate.

:::tip Masken programmgesteuert anwenden
Um Zeichenfolgen mit der gleichen Maskensyntax außerhalb eines Feldes zu formatieren, beispielsweise beim Rendern von Daten in einer [`Tabelle`](/docs/components/table/overview), verwenden Sie die [`MaskDecorator`](/docs/advanced/mask-decorator) Hilfsklasse.
:::

### Unterstützte Maskenzeichen {#supported-mask-characters}

| Zeichen    | Beschreibung                                                                              |
|------------|------------------------------------------------------------------------------------------|
| `X`        | Jedes druckbare Zeichen                                                                   |
| `a`        | Jedes alphabetische Zeichen (Groß- oder Kleinbuchstaben)                                 |
| `A`        | Jedes alphabetische Zeichen; Kleinbuchstaben werden in Großbuchstaben umgewandelt       |
| `0`        | Jede Ziffer (0–9)                                                                        |
| `z`        | Jede Ziffer oder Buchstabe (Groß- oder Kleinbuchstaben)                                  |
| `Z`        | Jede Ziffer oder Buchstabe; Kleinbuchstaben werden in Großbuchstaben umgewandelt        |

Alle anderen Zeichen in der Maske werden als Literale behandelt und müssen genau eingegeben werden. 
Zum Beispiel erfordert eine Maske wie `XX@XX`, dass der Benutzer ein `@` in der Mitte eingibt.

- **Ungültige Zeichen** werden stillschweigend ignoriert.
- **Kurze Eingaben** werden mit Leerzeichen aufgefüllt.
- **Lange Eingaben** werden abgeschnitten, um zur Maske zu passen.

### Beispiele {#examples}

```java
field.setMask("(000) 000-0000");     // Beispiel: (123) 456-7890
field.setMask("A00 000");            // Beispiel: A1B 2C3 (kanadische Postleitzahl)
field.setMask("ZZZZ-0000");          // Beispiel: ABCD-1234
field.setMask("0000-0000-0000-0000");// Beispiel: 1234-5678-9012-3456
```

:::tip Volle Eingabe erlaubt
Wenn die Maske nur `X` enthält, verhält sich das Feld wie ein Standard-[`TextField`](../textfield), das jede druckbare Eingabe erlaubt. 
Dies ist nützlich, wenn Sie die Möglichkeit zur Formatierung ohne strenge Zeichenregeln reservieren möchten.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Validierungsmuster {#validation-patterns}

Während Masken die Struktur der Eingabe definieren, können Sie sie mit Validierungsmustern kombinieren, um spezifischere Eingaberegeln durchzusetzen. Dies fügt eine zusätzliche Schicht der clientseitigen Validierung mithilfe von regulären Ausdrücken hinzu.

Verwenden Sie die Methode `setPattern()`, um einen benutzerdefinierten regulären Ausdruck anzuwenden:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Erzwingt einen 10-stelligen alphanumerischen Code
```

Dies stellt sicher, dass die Eingabe nicht nur der Maske entspricht, sondern auch einer definierten Struktur, wie z.B. Länge oder erlaubte Zeichen, entspricht.

Dies ist besonders nützlich, wenn:

- Die Maske zu viel Flexibilität zulässt.
- Sie eine exakte Länge oder ein spezifisches Format (z.B. hex, Base64, UUID) durchsetzen möchten.

:::tip Format regulärer Ausdrücke
Das Muster muss ein gültiger [JavaScript-regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom `RegExp`-Typ verwendet wird. Weitere Informationen finden Sie in der [Dokumentation zum HTML-Musterattribut](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Wiederherstellen des Wertes {#restoring-the-value}

Das `MaskedTextField` verfügt über eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. 
Dies kann nützlich sein, um Benutzereingaben rückgängig zu machen oder auf eine Standardsituation zurückzukehren.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Wege zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, durch den Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist der Standard-Wiederherstellungsschlüssel, es sei denn, er wird durch einen Ereignislistener überschrieben)

Sie können den Wert, auf den wiederhergestellt werden soll, mit `setRestoreValue()` festlegen. Wenn kein Wiederherstellungswert festgelegt ist, kehrt das Feld zum Anfangswert zum Zeitpunkt seiner Erstellung zurück.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

Das `MaskedTextFieldSpinner` erweitert das `MaskedTextField` um Spinner-Steuerelemente, mit denen Benutzer durch eine Liste vordefinierter Werte blättern können. 
Dies verbessert die Benutzererfahrung in Situationen, in denen die Eingabe auf eine feste Menge gültiger Optionen beschränkt werden sollte.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Hauptmerkmale {#key-features}

- **Unterstützung für Optionslisten**
  Füllen Sie den Spinner mit einer Liste gültiger Zeichenfolgenwerte mithilfe von `setOptions()`:

  ```java
  spinner.setOptions(List.of("Option A", "Option B", "Option C"));
  ```

- **Programmgesteuertes Blättern**
  Verwenden Sie `spinUp()` und `spinDown()`, um durch die Optionen zu scrollen:

  ```java
  spinner.spinUp();   // Wählt die nächste Option
  spinner.spinDown(); // Wählt die vorherige Option
  ```

- **Indizes steuern**
  Setzen oder abrufen des aktuellen Auswahlindex mit:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskenkompatibilität**
  Erbt vollständig alle Formatierungen, Maskenregeln und Mustervalidierungen von `MaskedTextField`.

## Styling {#styling}

<TableBuilder name="MaskedTextField" />
