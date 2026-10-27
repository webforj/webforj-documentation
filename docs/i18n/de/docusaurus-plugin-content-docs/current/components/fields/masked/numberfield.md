---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: bba6de4e793a65cc887af236d206bb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Das `MaskedNumberField` ist ein Texteingabefeld, das zum einheitlichen Formatieren numerischer Eingaben basierend auf einer definierten Maske entwickelt wurde. Es ist nützlich für Finanzformulare, Preisfelder oder jede Eingabe, bei der Präzision und Lesbarkeit wichtig sind.

Diese Komponente kann mit oder ohne Parameter instanziiert werden. Sie unterstützt die Zahlenformatierung, die Lokalisierung von Dezimal-/Gruppierungszeichen und optionale Wertbeschränkungen wie Mindest- oder Höchstwerte. Sie unterstützt auch das Festlegen eines Anfangswerts, eines Labels, eines Platzhalters und eines Ereignislisteners, um auf Wertänderungen zu reagieren.

<!-- INTRO_END -->

Das folgende Beispiel zeigt einen **Tip Calculator**, der `MaskedNumberField` für eine intuitive numerische Eingabe verwendet. Ein Feld ist so konfiguriert, dass es einen formatierten Rechnungsbetrag akzeptiert, während das andere einen ganzzahligen Trinkgeldprozentsatz erfasst.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedNumberField` verwendet eine Maskenzeichenfolge, um zu steuern, wie numerische Eingaben formatiert und angezeigt werden. Jedes Zeichen in der Maske definiert ein bestimmtes Formatverhalten, das eine präzise Kontrolle darüber ermöglicht, wie Zahlen dargestellt werden.

:::tip Masken programmatisch anwenden
Um Zahlen mit derselben Maskensyntax außerhalb eines Feldes zu formatieren, beispielsweise beim Rendern von Daten in einer [`Table`](/docs/components/table/overview), verwenden Sie die Dienstprogrammklasse [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Maskenzeichen {#mask-characters}

| Zeichen    | Beschreibung |
|------------|--------------|
| `0`        | Wird immer durch eine Ziffer (0–9) ersetzt. |
| `#`        | Unterdrückt führende Nullen. Wird durch das Füllzeichen links vom Dezimalpunkt ersetzt. Für nachfolgende Ziffern wird es durch ein Leerzeichen oder Null ersetzt. Andernfalls wird es durch eine Ziffer ersetzt. |
| `,`        | Wird als Gruppierungszeichen (z.B. für Tausender) verwendet. Wird durch das Füllzeichen ersetzt, wenn keine Ziffern davor stehen. Andernfalls wird es als Komma angezeigt. |
| `-`        | Zeigt ein Minuszeichen (`-`) an, wenn die Zahl negativ ist. Wird durch das Füllzeichen ersetzt, wenn sie positiv ist. |
| `+`        | Zeigt `+` für positive oder `-` für negative Zahlen an. |
| `$`        | Führt immer zu einem Dollarzeichen. |
| `(`        | Fügt eine linke Klammer `(` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn sie positiv sind. |
| `)`        | Fügt eine rechte Klammer `)` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn sie positiv sind. |
| `CR`       | Zeigt `CR` für negative Zahlen an. Zeigt zwei Leerzeichen an, wenn die Zahl positiv ist. |
| `DR`       | Zeigt `CR` für negative Zahlen an. Zeigt `DR` für positive Zahlen an. |
| `*`        | Fügt einen Asterisk `*` ein. |
| `.`        | Kennzeichnet den Dezimalpunkt. Wenn keine Ziffern in der Ausgabe erscheinen, wird es durch das Füllzeichen ersetzt. Nach dem Dezimalpunkt werden Füllzeichen als Leerzeichen behandelt. |
| `B`        | Wird immer zu einem Leerzeichen. Jedes andere literale Zeichen wird unverändert angezeigt. |

Einige der oben genannten Zeichen können mehr als einmal in der Maske erscheinen, um das Format zu steuern. Dazu gehören `-`, `+`, `$` und `(`. Wenn eines dieser Zeichen in der Maske vorhanden ist, wird das erste Zeichen, das gefunden wird, an die letzte Position verschoben, an der ein `#` oder `,` durch das Füllzeichen ersetzt wurde. Wenn keine solche Position existiert, bleibt das doppelte Zeichen an seinem Platz.

:::info Keine automatische Rundung
Eine Maske innerhalb eines Feldes rundet **NICHT**. Wenn Sie beispielsweise einen Wert wie `12.34567` in ein mit `###0.00` maskiertes Feld eingeben, erhalten Sie `12.34`.
:::

## Gruppen- und Dezimalseparatoren {#group-and-decimal-separators}

Das `MaskedNumberField` unterstützt die Anpassung von **Gruppierungs**- und **Dezimal**zeichen, was es einfach macht, die Zahlenformatierung an verschiedene Regionen oder Geschäftspraktiken anzupassen.

- Der **Gruppierungsseparator** wird verwendet, um Tausender visuell zu trennen (z.B. `1.000.000`).
- Der **Dezimalseparator** zeigt den Bruchteil einer Zahl an (z.B. `123.45`).

Dies ist nützlich in internationalen Anwendungen, in denen verschiedene Regionen unterschiedliche Zeichen verwenden (z.B. `.` vs `,`).

```java
field.setGroupCharacter(".");   // z.B. 1.000.000
field.setDecimalCharacter(","); // z.B. 123,45
```

:::tip Standardverhalten
Standardmäßig wendet `MaskedNumberField` Gruppierungs- und Dezimalseparatoren basierend auf der aktuellen Sprache des Apps an. Sie können diese jederzeit mit den bereitgestellten Settern überschreiben.
:::

## Negierbar {#negateable}

Das `MaskedNumberField` unterstützt eine Option zur Steuerung, ob negative Zahlen zulässig sind.

Standardmäßig sind negative Werte wie `-123.45` zulässig. Um dies zu verhindern, verwenden Sie `setNegateable(false)`, um die Eingabe auf positive Werte zu beschränken.

Dies ist nützlich in Geschäftsszenarien, in denen Werte wie Mengen, Summen oder Prozentsätze immer nicht negativ sein müssen.

```java
field.setNegateable(false);
```

Wenn `negatable` auf `false` gesetzt ist, blockiert das Feld alle Versuche, ein Minuszeichen einzugeben oder andere negative Werte einzugeben.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Mindest- und Höchstwerte {#min-and-max-values}

Das `MaskedNumberField` unterstützt das Festlegen numerischer Grenzen mithilfe von `setMin()` und `setMax()`. Diese Einschränkungen helfen sicherzustellen, dass die Benutzereingaben innerhalb eines gültigen, erwarteten Bereichs bleiben.

- **Mindestwert**
  Verwenden Sie `setMin()`, um die niedrigste akzeptable Zahl zu definieren:

  ```java
  field.setMin(10.0); // Mindestwert: 10
  ```

  Wenn der Benutzer eine Zahl unter diesem Schwellenwert eingibt, wird sie als ungültig angesehen.

- **Höchstwert**
  Verwenden Sie `setMax()`, um die höchste akzeptable Zahl zu definieren:

  ```java
  field.setMax(100.0); // Höchstwert: 100
  ```

  Werte über diesem Limit werden als ungültig markiert.

## Wiederherstellung des Wertes {#restoring-the-value}

Das `MaskedNumberField` unterstützt eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten Zustand zurücksetzt. Dies kann nützlich sein, wenn Benutzer Änderungen rückgängig machen, versehentliche Bearbeitungen zurücksetzen oder zu einem bekannten Standardwert zurückkehren müssen.

Um dieses Verhalten zu aktivieren, definieren Sie den Zielwert mit `setRestoreValue()`. Bei Bedarf kann das Feld programmgesteuert mit `restoreValue()` zurückgesetzt werden.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert** mit `restoreValue()`
- **Über die Tastatur**, indem die Taste <kbd>ESC</kbd> gedrückt wird (dies ist die Standardwiederherstelltaste, es sei denn, sie wird überschrieben)

Der Wiederherstellungswert muss ausdrücklich festgelegt werden. Wenn er nicht definiert ist, wird die Funktion das Feld nicht zurücksetzen.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Das `MaskedNumberFieldSpinner` erweitert das `MaskedNumberField`, indem es Spinner-Steuerelemente hinzufügt, mit denen Benutzer den Wert mithilfe von Schritt-Schaltflächen oder Pfeiltasten erhöhen oder verringern können. Dies ist ideal für Eingaben wie Mengen, Preisänderungen, Bewertungskontrollen oder jedes Szenario, in dem Benutzer schrittweise Änderungen vornehmen.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Hauptmerkmale {#key-features}

- **Schritt-Increments**
  Verwenden Sie `setStep()`, um festzulegen, um wie viel sich der Wert bei jedem Dreh ändern soll:

  ```java
  spinner.setStep(5.0); // Jeder Dreh addiert oder subtrahiert 5
  ```

- **Interaktive Steuerungen**
  Benutzer können auf Spinner-Schaltflächen klicken oder die Tastatureingabe verwenden, um den Wert anzupassen.

- **Alle Funktionen des MaskedNumberField**
  Unterstützt vollständig Masken, Formatierungen, Gruppierungs-/Dezimalzeichen, Mindest-/Höchstwerte und Wiederherstellungslogik.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
