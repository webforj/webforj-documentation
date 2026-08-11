---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 10866226b1025c8c4c0a28499d46de38
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

Die `MaskedTextField`-Komponente bietet ein konfigurierbares Texteingabefeld, das Formatierungsregeln und Validierung durchsetzt. Sie eignet sich gut für Anwendungen, die strukturierte Eingaben erfordern, wie z. B. Finanz-, E-Commerce- und Gesundheitsysteme.

<!-- INTRO_END -->

## Grundlagen {#basics}

Die `MaskedTextField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhaltertext und einen Listener definieren, falls sich der Wert ändert.

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Maske: ZZZZ-0000 - zum Beispiel: SAVE-2025")
```

## Maskenregeln {#mask-rules}

Die `MaskedTextField` formatiert die Texteingabe mithilfe einer Maske - einer Zeichenkette, die definiert, welche Zeichen an jeder Position erlaubt sind. Dies gewährleistet eine konsistente, strukturierte Eingabe für Dinge wie Telefonnummern, Postleitzahlen und ID-Formate.

:::tip Anwendung von Masken programmatisch
Um Zeichenfolgen außerhalb eines Feldes mit derselben Maskensyntax zu formatieren, z. B. beim Rendern von Daten in einer [`Table`](/docs/components/table/overview), verwenden Sie die [`MaskDecorator`](/docs/advanced/mask-decorator) Hilfsklasse.
:::

### Unterstützte Maskenzeichen {#supported-mask-characters}

| Zeichen    | Beschreibung                                                                                 |
|------------|---------------------------------------------------------------------------------------------|
| `X`        | Jedes druckbare Zeichen                                                                      |
| `a`        | Jedes alphabetische Zeichen (Groß- oder Kleinbuchstaben)                                     |
| `A`        | Jedes alphabetische Zeichen; Kleinbuchstaben werden in Großbuchstaben umgewandelt           |
| `0`        | Jede Ziffer (0–9)                                                                            |
| `z`        | Jede Ziffer oder jedes Zeichen (Groß- oder Kleinbuchstaben)                                  |
| `Z`        | Jede Ziffer oder jedes Zeichen; Kleinbuchstaben werden in Großbuchstaben umgewandelt         |

Alle anderen Zeichen in der Maske werden als Literale behandelt und müssen genau eingegeben werden.
Zum Beispiel erfordert eine Maske wie `XX@XX`, dass der Benutzer ein `@` in der Mitte eingibt.

- **Ungültige Zeichen** werden stillschweigend ignoriert.
- **Kurze Eingaben** werden mit Leerzeichen aufgefüllt.
- **Lange Eingaben** werden angepasst, um in die Maske zu passen.

### Beispiele {#examples}

```java
field.setMask("(000) 000-0000");     // Beispiel: (123) 456-7890
field.setMask("A00 000");            // Beispiel: A1B 2C3 (kanadische Postleitzahl)
field.setMask("ZZZZ-0000");          // Beispiel: ABCD-1234
field.setMask("0000-0000-0000-0000");// Beispiel: 1234-5678-9012-3456
```

:::tip Vollständige Eingabe erlaubt
Wenn die Maske nur `X` enthält, verhält sich das Feld wie ein standardmäßiges [`TextField`](../textfield), das beliebige druckbare Eingaben zulässt.
Dies ist nützlich, wenn Sie die Möglichkeit zur Formatierung reservieren möchten, ohne strenge Zeichenregeln anzuwenden.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Validierungsmuster {#validation-patterns}

Während Masken die Struktur der Eingabe definieren, können Sie sie mit Validierungsmustern kombinieren, um spezifischere Eingaberegeln durchzusetzen. Dies fügt eine zusätzliche Schicht der Client-seitigen Validierung mithilfe von regulären Ausdrücken hinzu.

Verwenden Sie die Methode `setPattern()`, um einen benutzerdefinierten regulären Ausdruck anzuwenden:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Erzwingt einen alphanumerischen Code mit 10 Zeichen
```

Dies stellt sicher, dass die Eingabe nicht nur der Maske entspricht, sondern auch einer definierten Struktur wie Länge oder erlaubten Zeichen entspricht.

Dies ist besonders nützlich, wenn:

- Die Maske zu viel Flexibilität zulässt
- Sie eine genaue Länge oder ein spezifisches Format (z. B. hexadezimal, Base64, UUID) durchsetzen möchten

:::tip Format für reguläre Ausdrücke
Das Muster muss ein gültiger [JavaScript regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom Typ `RegExp` verwendet wird. Weitere Einzelheiten finden Sie in der [Dokumentation zum HTML-Musterattribut](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Wiederherstellung des Wertes {#restoring-the-value}

Die `MaskedTextField` umfasst eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt.
Dies kann nützlich sein, um Benutzeränderungen rückgängig zu machen oder zu einer Standardeingabe zurückzukehren.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die Standard-Wiederherstelltaste, es sei denn, sie wird durch einen Ereignislistener überschrieben)

Sie können den Wert, auf den zurückzugreifen ist, mit `setRestoreValue()` festlegen. Wenn kein Wiederherstellungswert festgelegt ist, kehrt das Feld zum Anfangswert zurück, als es gerendert wurde.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

Die `MaskedTextFieldSpinner` erweitert [`MaskedTextField`](#basics) um Spin-Steuerelemente, die es Benutzern ermöglichen, durch eine Liste vordefinierter Werte zu blättern.
Dies verbessert die Benutzererfahrung in Situationen, in denen die Eingabe auf eine feste Menge gültiger Optionen beschränkt sein sollte.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Besondere Merkmale {#key-features}

- **Unterstützung für Optionslisten**
  Füllen Sie den Spinner mit einer Liste gültiger Zeichenfolgenwerte mit `setOptions()`:

  ```java
  spinner.setOptions(List.of("Option A", "Option B", "Option C"));
  ```

- **Programmgesteuertes Drehen**
  Verwenden Sie `spinUp()` und `spinDown()`, um durch die Optionen zu navigieren:

  ```java
  spinner.spinUp();   // Wählt die nächste Option aus
  spinner.spinDown(); // Wählt die vorherige Option aus
  ```

- **Indexkontrolle**
  Setzen oder ermitteln Sie den aktuellen Auswahlindex mit:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskenkompatibilität**
  Erbt alle Formatierungs-, Maskenregeln und Mustervalidierungen vollständig von `MaskedTextField`.

## Styling {#styling}

<TableBuilder name="MaskedTextField" />
