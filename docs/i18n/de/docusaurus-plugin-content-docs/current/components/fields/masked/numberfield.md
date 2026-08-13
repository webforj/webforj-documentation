---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: 1ce8783919180d45f2f7321c559fc26a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Das `MaskedNumberField` ist ein Texteingabefeld, das für die strukturierte Eingabe von Zahlen konzipiert ist. Es stellt sicher, dass Zahlen konsistent formatiert werden, basierend auf einer definierten Maske, und eignet sich besonders gut für Finanzformulare, Preisfelder oder jede Eingabe, bei der Präzision und Lesbarkeit wichtig sind.

Diese Komponente unterstützt die Zahlenformatierung, die Lokalisierung von Dezimal- und Gruppierungszeichen sowie optionale Wertbeschränkungen wie Mindest- oder Höchstwerte.

<!-- INTRO_END -->

## Grundlagen {#basics}

Das `MaskedNumberField` kann mit oder ohne Parameter instanziiert werden. Es unterstützt das Setzen eines Anfangswertes, eines Labels, eines Platzhalters und eines Ereignislisteners, um auf Wertänderungen zu reagieren.

Diese Demo zeigt einen **Trinkgeldrechner**, der `MaskedNumberField` für intuitive numerische Eingaben verwendet. Ein Feld ist so konfiguriert, dass es einen formatierten Rechnungsbetrag akzeptiert, während das andere einen ganzzahligen Trinkgeldprozentsatz erfasst. Beide Felder wenden numerische Masken an, um eine konsistente und vorhersagbare Formatierung sicherzustellen.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedNumberField` verwendet eine Maskenzeichenfolge, um zu steuern, wie die numerische Eingabe formatiert und angezeigt wird. Jedes Zeichen in der Maske definiert ein spezifisches Formatverhalten und ermöglicht eine präzise Kontrolle über das Erscheinungsbild der Zahlen.

:::tip Programmatische Maskenanwendung
Um Zahlen außerhalb eines Feldes mit derselben Maskensyntax zu formatieren, zum Beispiel beim Rendern von Daten in einer [`Table`](/docs/components/table/overview), verwenden Sie die Utility-Klasse [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Maskenzeichen {#mask-characters}

| Zeichen    | Beschreibung |
|------------|--------------|
| `0`        | Wird immer durch eine Ziffer (0–9) ersetzt. |
| `#`        | Unterdrückt führende Nullen. Wird durch das Füllzeichen links vom Dezimalpunkt ersetzt. Für nachfolgende Ziffern wird es durch einen Raum oder Null ersetzt. Andernfalls wird es durch eine Ziffer ersetzt. |
| `,`        | Wird als Gruppierungsseparator verwendet (z. B. Tausender). Wird durch das Füllzeichen ersetzt, wenn keine Ziffern davor stehen. Andernfalls wird es als Komma angezeigt. |
| `-`        | Zeigt ein Minuszeichen (`-`) an, wenn die Zahl negativ ist. Wird durch das Füllzeichen ersetzt, wenn sie positiv ist. |
| `+`        | Zeigt `+` für positive oder `-` für negative Zahlen an. |
| `$`        | Führt immer zu einem Dollarzeichen. |
| `(`        | Fügt eine linke Klammer `(` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn sie positiv ist. |
| `)`        | Fügt eine rechte Klammer `)` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn sie positiv ist. |
| `CR`       | Zeigt `CR` für negative Zahlen an. Zeigt zwei Leerzeichen an, wenn die Zahl positiv ist. |
| `DR`       | Zeigt `CR` für negative Zahlen an. Zeigt `DR` für positive Zahlen an. |
| `*`        | Fügt ein Sternchen `*` ein. |
| `.`        | Kennzeichnet den Dezimalpunkt. Wenn keine Ziffern in der Ausgabe erscheinen, wird sie durch das Füllzeichen ersetzt. Nach dem Dezimalpunkt werden Füllzeichen als Leerzeichen behandelt. |
| `B`        | Wird immer zu einem Leerzeichen. Jedes andere literale Zeichen wird unverändert angezeigt. |

Einige der oben genannten Zeichen können mehr als einmal in der Maske für die Formatierung erscheinen. Dazu gehören `-`, `+`, `$` und `(`. Wenn eines dieser Zeichen in der Maske vorhanden ist, wird das erste, das gefunden wird, an die letzte Position verschoben, an der ein `#` oder `,` durch das Füllzeichen ersetzt wurde. Wenn keine solche Position existiert, bleibt das doppelte Zeichen an seiner Stelle.

:::info Keine automatische Rundung
Eine Maske innerhalb eines Feldes rundet **nicht**. Wenn Sie beispielsweise einen Wert wie `12.34567` in ein Feld eingeben, das mit `###0.00` maskiert ist, erhalten Sie `12.34`.
:::

## Gruppen- und Dezimaltrenner {#group-and-decimal-separators}

Das `MaskedNumberField` unterstützt die Anpassung von **Gruppierungs**- und **Dezimal**zeichen, wodurch die Anpassung der Zahlenformatierung an verschiedene Regionen oder Geschäftspraktiken erleichtert wird.

- Der **Gruppierungsseparator** wird verwendet, um Tausender visuell zu trennen (z. B. `1.000.000`).
- Der **Dezimalseparator** zeigt den Bruchteil einer Zahl an (z. B. `123.45`).

Dies ist nützlich in internationalen Anwendungen, in denen in verschiedenen Regionen unterschiedliche Zeichen verwendet werden (z. B. `.` vs `,`).

```java
field.setGroupCharacter(".");   // z.B. 1.000.000
field.setDecimalCharacter(","); // z.B. 123,45
```

:::tip Standardverhalten
Standardmäßig wendet das `MaskedNumberField` Gruppierungs- und Dezimaltrenner basierend auf der aktuellen Lokalisierung der App an. Sie können diese jederzeit mit den bereitgestellten Settern überschreiben.
:::

## Negierbar {#negateable}

Das `MaskedNumberField` unterstützt eine Option zur Steuerung, ob negative Zahlen erlaubt sind.

Standardmäßig sind negative Werte wie `-123.45` erlaubt. Um dies zu verhindern, verwenden Sie `setNegateable(false)`, um Eingaben auf positive Werte zu beschränken.

Dies ist in Geschäftsszenarien nützlich, in denen Werte wie Mengen, Summen oder Prozentsätze immer nicht negativ sein müssen.

```java
field.setNegateable(false);
```

Wenn `negatable` auf `false` gesetzt ist, blockiert das Feld alle Versuche, ein Minuszeichen einzugeben oder anderweitig negative Werte einzugeben.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Mindest- und Höchstwerte {#min-and-max-values}

Das `MaskedNumberField` unterstützt das Setzen numerischer Grenzen mithilfe von `setMin()` und `setMax()`. Diese Einschränkungen helfen, sicherzustellen, dass die Benutzereingabe innerhalb eines gültigen, erwarteten Bereichs bleibt.

- **Mindestwert**
  Verwenden Sie `setMin()`, um die niedrigste akzeptable Zahl zu definieren:

  ```java
  field.setMin(10.0); // Mindestwert: 10
  ```

  Wenn der Benutzer eine Zahl unterhalb dieses Schwellenwerts eingibt, wird sie als ungültig betrachtet.

- **Höchstwert**
  Verwenden Sie `setMax()`, um die höchste akzeptable Zahl zu definieren:

  ```java
  field.setMax(100.0); // Höchstwert: 100
  ```

  Werte oberhalb dieses Limits werden als ungültig gekennzeichnet.

## Wiederherstellen des Wertes {#restoring-the-value}

Das `MaskedNumberField` unterstützt eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten Zustand zurücksetzt. Dies kann nützlich sein, wenn Benutzer Änderungen rückgängig machen, versehentliche Bearbeitungen zurücksetzen oder zu einem bekannten Standardwert zurückkehren müssen.

Um dieses Verhalten zu aktivieren, definieren Sie den Zielwert mit `setRestoreValue()`. Bei Bedarf kann das Feld programmgesteuert mit `restoreValue()` zurückgesetzt werden.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Möglichkeiten zum Wiederherstellen des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert** mit `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die Standard-Wiederherstelltaste, es sei denn, sie wird überschrieben)

Der Wiederherstellungswert muss ausdrücklich festgelegt werden. Wenn er nicht definiert ist, wird die Funktion das Feld nicht zurücksetzen.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Das `MaskedNumberFieldSpinner` erweitert [`MaskedNumberField`](#basics) um Spinner-Steuerelemente, mit denen Benutzer den Wert mit Schritt-Tasten oder Pfeiltasten erhöhen oder verringern können. Dies ist ideal für Eingaben wie Mengen, Preisänderungen, Bewertungssteuerungen oder jedes Szenario, in dem Benutzer inkrementelle Änderungen vornehmen.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Hauptmerkmale {#key-features}

- **Schrittinkremente**
  Verwenden Sie `setStep()`, um festzulegen, wie viel sich der Wert mit jedem Dreh ändern soll:

  ```java
  spinner.setStep(5.0); // Jeder Dreh addiert oder subtrahiert 5
  ```

- **Interaktive Steuerungen**
  Benutzer können auf Spinner-Schaltflächen klicken oder die Tastatureingaben verwenden, um den Wert anzupassen.

- **Alle Funktionen des MaskedNumberField**
  Unterstützt vollständig Masken, Formatierung, Gruppierungs-/Dezimalzeichen, Mindest-/Höchstbeschränkungen und Wiederherstellungslogik.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
