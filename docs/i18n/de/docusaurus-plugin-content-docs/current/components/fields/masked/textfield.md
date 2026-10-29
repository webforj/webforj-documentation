---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 9ee372191dc17b2510f020bc908f0060
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

Die Komponente `MaskedTextField` bietet ein konfigurierbares Texteingabefeld, das Formatierungsregeln und Validierungen durchsetzt. Es eignet sich gut für Anwendungen, die strukturierte Eingaben erfordern, wie Finanz-, E-Commerce- und Gesundheitssysteme.

Diese Komponente kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhaltertext und einen Listener definieren, falls sich der Wert ändert.

<!-- INTRO_END -->

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Maske: ZZZZ-0000 - zum Beispiel: SAVE-2025");
```

## Maskenregeln {#mask-rules}

Das `MaskedTextField` formatiert Texteingaben mithilfe einer Maske - einer Zeichenkette, die definiert, welche Zeichen an jeder Position erlaubt sind. Dies gewährleistet eine konsistente, strukturierte Eingabe für Dinge wie Telefonnummern, Postleitzahlen und ID-Formate.

:::tip Anwendung von Masken programmgesteuert
Um Zeichenfolgen mit derselben Maskensyntax außerhalb eines Feldes zu formatieren, z. B. beim Rendern von Daten in einem [`Table`](/docs/components/table/overview), verwenden Sie die Utility-Klasse [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Unterstützte Maskenzeichen {#supported-mask-characters}

| Zeichen    | Beschreibung                                                                             |
|------------|------------------------------------------------------------------------------------------|
| `X`        | Jedes druckbare Zeichen                                                                   |
| `a`        | Jedes alphabetische Zeichen (Groß- oder Kleinbuchstaben)                                 |
| `A`        | Jedes alphabetische Zeichen; Kleinbuchstaben werden in Großbuchstaben umgewandelt       |
| `0`        | Jede Ziffer (0–9)                                                                        |
| `z`        | Jede Ziffer oder Buchstabe (Groß- oder Kleinbuchstaben)                                  |
| `Z`        | Jede Ziffer oder Buchstabe; Kleinbuchstaben werden in Großbuchstaben umgewandelt        |

Alle anderen Zeichen in der Maske werden als Literale behandelt und müssen genau so eingegeben werden.
Ein Beispiel: Eine Maske wie `XX@XX` erfordert, dass der Benutzer ein `@` in der Mitte eingibt.

- **Ungültige Zeichen** werden stillschweigend ignoriert.
- **Kurze Eingaben** werden mit Leerzeichen aufgefüllt.
- **Lange Eingaben** werden gekürzt, um in die Maske zu passen.

### Beispiele {#examples}

```java
field.setMask("(000) 000-0000");     // Beispiel: (123) 456-7890
field.setMask("A0A 0A0");            // Beispiel: A1B 2C3 (kanadische Postleitzahl)
field.setMask("ZZZZ-0000");          // Beispiel: ABCD-1234
field.setMask("0000-0000-0000-0000"); // Beispiel: 1234-5678-9012-3456
```

:::tip Volle Eingabe erlaubt
Wenn die Maske nur `X` enthält, verhält sich das Feld wie ein Standard-[`TextField`](../textfield) und erlaubt jede druckbare Eingabe.
Dies ist nützlich, wenn Sie die Fähigkeit zur Formatierung ohne strenge Zeichenregeln reservieren möchten.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Validierungsmuster {#validation-patterns}

Während Masken die Struktur der Eingabe definieren, können Sie sie mit Validierungsmustern kombinieren, um spezifischere Eingaberegeln durchzusetzen. Dies fügt eine zusätzliche Schicht der clientseitigen Validierung mit regulären Ausdrücken hinzu.

Verwenden Sie die Methode `setPattern()`, um einen benutzerdefinierten regulären Ausdruck anzuwenden:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Erzwingt einen alphanumerischen Code mit 10 Zeichen
```

Das stellt sicher, dass die Eingabe nicht nur der Maske entspricht, sondern auch einer definierten Struktur wie Länge oder erlaubte Zeichen.

Dies ist besonders nützlich, wenn:

- Die Maske zu viel Flexibilität erlaubt
- Sie exakt eine bestimmte Länge oder ein bestimmtes Format (z. B. hex, Base64, UUID) durchsetzen möchten

:::tip Format des regulären Ausdrucks
Das Muster muss ein gültiger [JavaScript-regulärer Ausdruck](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) sein, wie er vom Typ `RegExp` verwendet wird. Weitere Informationen finden Sie in der [HTML-Musterattributdokumentation](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Wiederherstellung des Wertes {#restoring-the-value}

Das `MaskedTextField` enthält eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt.
Dies kann nützlich sein, um Benutzeränderungen rückgängig zu machen oder zu einer Standardeingabe zurückzukehren.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die Standard-Wiederherstellungstaste, es sei denn, sie wird von einem Ereignis-Listener überschrieben)

Sie können den Wiederherstellungswert mit `setRestoreValue()` festlegen. Wenn kein Wiederherstellungswert festgelegt ist, setzen sowohl `restoreValue()` als auch <kbd>ESC</kbd> den Feldwert auf `null`.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

Der `MaskedTextFieldSpinner` erweitert das `MaskedTextField`, indem er Steuerelemente hinzufügt, mit denen Benutzer durch eine Liste vordefinierter Werte blättern können.
Dies verbessert die Benutzererfahrung in Situationen, in denen die Eingabe auf eine feste Menge gültiger Optionen beschränkt sein sollte.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Hauptmerkmale {#key-features}

- **Listenunterstützung**
  Füllen Sie den Spinner mit einer Liste gültiger Zeichenfolgenwerte mithilfe von `setOptions()`:

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
  Setzen oder rufen Sie den aktuellen Auswahlindex mit:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskenkompatibilität**
  Erbt vollständig alle Formatierungen, Maskenregeln und Musterprüfungen vom `MaskedTextField`.

## Styling {#styling}

<TableBuilder name="MaskedTextField" />
