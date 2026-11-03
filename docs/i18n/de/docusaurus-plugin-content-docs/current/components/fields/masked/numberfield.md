---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: f247d28107239e0db4a24823c8a1ef60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Das `MaskedNumberField` ist ein Texteingabefeld, das dazu konzipiert wurde, numerische Eingaben konsistent zu formatieren, basierend auf einer definierten Maske. Es ist nützlich für Finanzformulare, Preisfelder oder jede Eingabe, bei der Genauigkeit und Lesbarkeit wichtig sind.

Diese Komponente kann mit oder ohne Parameter instanziiert werden. Sie unterstützt die Formatierung von Zahlen, die Lokalisierung von Dezimal- und Gruppierungszeichen sowie optionale Werteinschränkungen wie Minimal- oder Maximalwerte. Außerdem unterstützt es die Festlegung eines Anfangswerts, eines Labels, eines Platzhalters und eines Ereignislisteners, um auf Änderungen der Werte zu reagieren.

<!-- INTRO_END -->

Das folgende Beispiel zeigt einen **Tip Calculator**, der das `MaskedNumberField` für eine intuitive numerische Eingabe verwendet. Ein Feld ist so konfiguriert, dass es einen formatierten Rechnungsbetrag akzeptiert, während das andere einen ganzen Prozentsatz für das Trinkgeld erfasst.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedNumberField` verwendet eine Maskenzeichenfolge, um zu steuern, wie numerische Eingaben formatiert und angezeigt werden. Jedes Zeichen in der Maske definiert ein bestimmtes Formatverhalten und ermöglicht eine präzise Kontrolle darüber, wie Zahlen erscheinen.

:::tip Anwendung von Masken programmatisch
Um Zahlen außerhalb eines Feldes mit derselben Maskensyntax zu formatieren, zum Beispiel beim Rendern von Daten in einer [`Table`](/docs/components/table/overview), verwenden Sie die Utility-Klasse [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Maskenzeichen {#mask-characters}

| Zeichen    | Beschreibung |
|------------|--------------|
| `0`        | Wird immer durch eine Ziffer (0–9) ersetzt. |
| `#`        | Unterdrückt führende Nullen. Wird durch das Füllzeichen links vom Dezimalpunkt ersetzt. Für nachgestellte Ziffern wird durch ein Leerzeichen oder eine Null ersetzt. Ansonsten wird es durch eine Ziffer ersetzt. |
| `,`        | Wird als Gruppierungstrennzeichen verwendet (z.B. Tausender). Wird durch das Füllzeichen ersetzt, wenn keine Ziffern davor stehen. Ansonsten wird es als Komma angezeigt. |
| `-`        | Zeigt ein Minuszeichen (`-`) an, wenn die Zahl negativ ist. Wird durch das Füllzeichen ersetzt, wenn sie positiv ist. |
| `+`        | Zeigt `+` für positive oder `-` für negative Zahlen an. |
| `$`        | Führt immer zu einem Dollarzeichen. |
| `(`        | Fügt eine linke Klammer `(` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn sie positiv ist. |
| `)`        | Fügt eine rechte Klammer `)` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn sie positiv ist. |
| `CR`       | Zeigt `CR` für negative Zahlen an. Zeigt zwei Leerzeichen an, wenn die Zahl positiv ist. |
| `DR`       | Zeigt `CR` für negative Zahlen an. Zeigt `DR` für positive Zahlen an. |
| `*`        | Fügt einen Asterisk `*` ein. |
| `.`        | Kennzeichnet den Dezimalpunkt. Wenn im Output keine Ziffern erscheinen, wird es durch das Füllzeichen ersetzt. Nach dem Dezimalpunkt werden Füllzeichen als Leerzeichen behandelt. |
| `B`        | Wird immer zu einem Leerzeichen. Jedes andere Literalzeichen wird unverändert angezeigt. |

Einige der oben genannten Zeichen können mehr als einmal in der Maske erscheinen. Dazu gehören `-`, `+`, `$` und `(`. Wenn eines dieser Zeichen in der Maske vorhanden ist, wird das erste, das auftritt, an die letzte Position verschoben, an der ein `#` oder `,` durch das Füllzeichen ersetzt wurde. Wenn keine solche Position existiert, bleibt das Doppelzeichen an seiner Stelle.

:::info Keine automatische Rundung
Eine Maske innerhalb eines Feldes rundet **NICHT**. Zum Beispiel, wenn ein Wert wie `12.34567` in ein mit `###0.00` maskiertes Feld eingegeben wird, erhalten Sie `12.34`.
:::

## Gruppen- und Dezimaltrennzeichen {#group-and-decimal-separators}

Das `MaskedNumberField` unterstützt die Anpassung von **Gruppierungs**- und **Dezimal**zeichen, wodurch es einfach ist, die Zahlformatierung an verschiedene Regionen oder Geschäftskonventionen anzupassen.

- Der **Gruppentrennzeichen** wird verwendet, um Tausende visuell zu trennen (z.B. `1.000.000`).
- Der **Dezimaltrennzeichen** zeigt den Bruchteil einer Zahl an (z.B. `123,45`).

Dies ist nützlich in internationalen Anwendungen, in denen in verschiedenen Regionen unterschiedliche Zeichen verwendet werden (z.B. `.` vs `,`).

```java
field.setGroupCharacter(".");   // z.B. 1.000.000
field.setDecimalCharacter(","); // z.B. 123,45
```

:::tip Standardverhalten
Standardmäßig wendet `MaskedNumberField` Gruppierungs- und Dezimaltrennzeichen basierend auf der aktuellen Region der App an. Sie können diese jederzeit mit den bereitgestellten Settern überschreiben.
:::

## Negierbar {#negateable}

Das `MaskedNumberField` unterstützt eine Option, um zu steuern, ob negative Zahlen erlaubt sind.

Standardmäßig sind negative Werte wie `-123.45` erlaubt. Um dies zu verhindern, verwenden Sie `setNegateable(false)`, um die Eingabe nur auf positive Werte zu beschränken.

Dies ist nützlich in Geschäftsszenarien, in denen Werte wie Mengen, Summen oder Prozentsätze immer nicht negativ sein müssen.

```java
field.setNegateable(false);
```

Wenn `negatable` auf `false` gesetzt ist, blockiert das Feld jeden Versuch, ein Minuszeichen einzugeben oder anderweitig negative Werte einzugeben.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Mindest- und Höchstwerte {#min-and-max-values}

Das `MaskedNumberField` unterstützt die Festlegung numerischer Grenzen mithilfe von `setMin()` und `setMax()`. Diese Einschränkungen helfen sicherzustellen, dass die Benutzereingabe innerhalb eines gültigen, erwarteten Bereichs bleibt.

- **Mindestwert**
  Verwenden Sie `setMin()`, um die niedrigste akzeptable Zahl zu definieren:

  ```java
  field.setMin(10.0); // Mindestwert: 10
  ```

  Wenn der Benutzer eine Zahl unter diesem Schwellenwert eingibt, wird dies als ungültig angesehen.

- **Höchstwert**
  Verwenden Sie `setMax()`, um die höchste akzeptable Zahl zu definieren:

  ```java
  field.setMax(100.0); // Höchstwert: 100
  ```

  Werte über dieses Limit werden als ungültig markiert.

## Wiederherstellung des Wertes {#restoring-the-value}

Das `MaskedNumberField` unterstützt eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten Zustand zurücksetzt. Dies kann nützlich sein, wenn Benutzer Änderungen rückgängig machen, versehentliche Bearbeitungen zurücksetzen oder zu einem bekannten Standardwert zurückkehren müssen.

Um einen bestimmten Wert wiederherzustellen, definieren Sie ihn mit `setRestoreValue()`. Bei Bedarf kann das Feld programmgemäß mit `restoreValue()` zurückgesetzt werden.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgemäß** mit `restoreValue()`
- **Über die Tastatur**, indem Sie <kbd>ESC</kbd> drücken (dies ist die Standard-Wiederherstellungstaste, es sei denn, es wird eine andere festgelegt)

Wenn kein Wiederherstellungswert festgelegt ist, setzen sowohl `restoreValue()` als auch <kbd>ESC</kbd> den Feldwert auf `null`.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Das `MaskedNumberFieldSpinner` erweitert das `MaskedNumberField`, indem Spinner-Steuerelemente hinzugefügt werden, mit denen Benutzer den Wert mit Schritt-Schaltflächen oder Pfeiltasten erhöhen oder verringern können. Dies ist ideal für Eingaben wie Mengen, Preisänderungen, Bewertungssteuerelemente oder jede Situation, in der Benutzer schrittweise Änderungen vornehmen.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Hauptmerkmale {#key-features}

- **Schrittinkremente**
  Verwenden Sie `setStep()`, um zu definieren, wie viel sich der Wert bei jedem Spin ändern soll:

  ```java
  spinner.setStep(5.0); // Jeder Spin addiert oder subtrahiert 5
  ```

- **Interaktive Steuerelemente**
  Benutzer können auf die Spinner-Schaltflächen klicken oder die Tastatureingabe verwenden, um den Wert anzupassen.

- **Alle Funktionen des MaskedNumberField**
  Unterstützt vollständig Masken, Formatierung, Gruppen- und Dezimalzeichen, Min/Max-Beschränkungen und Wiederherstellungslogik.

## Stilgestaltung {#styling}

<TableBuilder name="MaskedNumberField" />
