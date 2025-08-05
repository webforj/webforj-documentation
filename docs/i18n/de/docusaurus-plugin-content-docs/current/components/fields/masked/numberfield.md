---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 00d399f2bcfb22c884608aa8a0975573
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Das `MaskedNumberField` ist ein Texteingabefeld, das für die strukturierte Eingabe von Zahlen entwickelt wurde. Es sorgt dafür, dass Zahlen konsistent basierend auf einer definierten Maske formatiert werden, was es besonders nützlich für Finanzformulare, Preisfelder oder Eingaben macht, bei denen Genauigkeit und Lesbarkeit wichtig sind.

Diese Komponente unterstützt die Zahlenformatierung, die Lokalisierung von Dezimal- und Gruppierungzeichen und optionale Werteinschränkungen wie Mindest- oder Höchstwerte.

## Grundlagen {#basics}

Das `MaskedNumberField` kann mit oder ohne Parameter instanziiert werden. Es unterstützt die Festlegung eines Anfangswerts, eines Labels, eines Platzhalters und eines Ereignislisteners, um auf Wertänderungen zu reagieren.

Diese Demo zeigt einen **Tip Rechner**, der das `MaskedNumberField` für intuitive numerische Eingaben verwendet. Ein Feld ist so konfiguriert, dass es einen formatierten Rechnungsbetrag akzeptiert, während das andere einen Ganzzahl-Tippercent erfasst. Beide Felder wenden numerische Masken an, um eine konsistente und vorhersehbare Formatierung zu gewährleisten.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height='270px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedNumberField` verwendet eine Maskenzeichenfolge, um zu steuern, wie die numerische Eingabe formatiert und angezeigt wird. 
Jedes Zeichen in der Maske definiert ein spezifisches Formatverhalten und ermöglicht eine präzise Kontrolle darüber, wie Zahlen erscheinen.

### Maskenzeichen {#mask-characters}

| Zeichen    | Beschreibung |
|------------|--------------|
| `0`        | Immer durch eine Ziffer (0–9) ersetzt. |
| `#`        | Unterdrückt führende Nullen. Wird durch das Füllzeichen links vom Dezimalpunkt ersetzt. Für nachfolgende Ziffern wird es durch ein Leerzeichen oder Null ersetzt. Ansonsten wird es durch eine Ziffer ersetzt. |
| `,`        | Wird als Gruppierungstrenner verwendet (z.B. Tausender). Wird durch das Füllzeichen ersetzt, wenn keine Ziffern davor stehen. Ansonsten wird es als Komma angezeigt. |
| `-`        | Zeigt ein Minuszeichen (`-`) an, wenn die Zahl negativ ist. Wird durch das Füllzeichen ersetzt, wenn die Zahl positiv ist. |
| `+`        | Zeigt `+` für positive oder `-` für negative Zahlen an. |
| `$`        | Führt immer zu einem Dollarzeichen. |
| `(`        | Fügt eine linke Klammer `(` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn positiv. |
| `)`        | Fügt eine rechte Klammer `)` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn positiv. |
| `CR`       | Zeigt `CR` für negative Zahlen an. Zeigt zwei Leerzeichen an, wenn die Zahl positiv ist. |
| `DR`       | Zeigt `CR` für negative Zahlen an. Zeigt `DR` für positive Zahlen an. |
| `*`        | Fügt ein Asterisk `*` ein. |
| `.`        | Markiert den Dezimalpunkt. Wenn keine Ziffern im Output erscheinen, wird es durch das Füllzeichen ersetzt. Füllzeichen nach dem Dezimalpunkt werden als Leerzeichen behandelt. |
| `B`        | Wird immer zu einem Leerzeichen. Jedes andere Literalzeichen wird so angezeigt, wie es ist. |

Einige der oben genannten Zeichen können mehr als einmal in der Maske erscheinen, um das Format anzupassen. Dazu gehören `-`, `+`, `$` und `(`. Wenn eines dieser Zeichen in der Maske vorhanden ist, wird das erste, das gefunden wird, an die letzte Position verschoben, an der ein `#` oder `,` durch das Füllzeichen ersetzt wurde. Existiert keine solche Position, bleibt das Doppelzeichen dort, wo es ist.

:::info Keine automatische Rundung
Eine Maske in einem Feld rundet **nicht** automatisch. Wenn Sie beispielsweise einen Wert wie `12.34567` in ein Feld eingeben, das mit `###0.00` maskiert ist, erhalten Sie `12.34`.
:::

## Gruppen- und Dezimaltrennzeichen {#group-and-decimal-separators}

Das `MaskedNumberField` unterstützt die Anpassung von **Gruppierung** und **Dezimal**zeichen, was es einfach macht, die Zahlenformatierung an verschiedene Lokale oder Geschäftsgewohnheiten anzupassen.

- Der **Gruppentrenner** wird verwendet, um Tausender visuell zu trennen (z.B. `1,000,000`).
- Der **Dezimaltrennzeichen** zeigt den Bruchteil einer Zahl an (z.B. `123.45`).

Dies ist in internationalen Anwendungen nützlich, in denen verschiedene Regionen unterschiedliche Zeichen verwenden (z.B. `.` vs `,`).

```java
field.setGroupCharacter(".");   // z.B. 1.000.000
field.setDecimalCharacter(","); // z.B. 123,45
```

:::tip Standardverhalten
Standardmäßig wendet das `MaskedNumberField` Gruppen- und Dezimaltrennzeichen basierend auf der aktuellen Locale der App an. Sie können diese jederzeit mit den bereitgestellten Settern überschreiben.
:::

## Negierbar {#negateable}

Das `MaskedNumberField` unterstützt eine Option zur Steuerung, ob negative Zahlen zulässig sind.

Standardmäßig sind negative Werte wie `-123.45` erlaubt. Um dies zu verhindern, verwenden Sie `setNegateable(false)` um die Eingabe auf positive Werte zu beschränken.

Dies ist in Geschäftsszenarien nützlich, in denen Werte wie Mengen, Gesamtergebnisse oder Prozentsätze immer nicht negativ sein müssen.

```java
field.setNegateable(false);
```

Wenn `negatable` auf `false` gesetzt ist, blockiert das Feld alle Versuche, ein Minuszeichen einzugeben oder auf andere Weise negative Werte einzugeben.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height='150px'
/>

## Mindest- und Höchstwerte {#min-and-max-values}

Das `MaskedNumberField` unterstützt die Festlegung numerischer Grenzen mit `setMin()` und `setMax()`. 
Diese Einschränkungen helfen sicherzustellen, dass die Benutzereingabe innerhalb eines gültigen, erwarteten Bereichs bleibt.

- **Mindestwert**  
  Verwenden Sie `setMin()`, um die niedrigste akzeptable Zahl zu definieren:

  ```java
  field.setMin(10.0); // Mindestwert: 10
  ```

  Wenn der Benutzer eine Zahl unter diesem Schwellenwert eingibt, wird dies als ungültig betrachtet.

- **Höchstwert**  
  Verwenden Sie `setMax()`, um die höchste akzeptable Zahl zu definieren:

  ```java
  field.setMax(100.0); // Höchstwert: 100
  ```

  Werte über diesem Limit werden als ungültig eingestuft.

## Wiederherstellung des Werts {#restoring-the-value}

Das `MaskedNumberField` unterstützt eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten Zustand zurücksetzt. 
Dies kann nützlich sein, wenn Benutzer Änderungen rückgängig machen, versehentliche Bearbeitungen zurücksetzen oder zu einem bekannten Standardwert zurückkehren müssen.

Um dieses Verhalten zu aktivieren, definieren Sie den Zielwert mit `setRestoreValue()`. 
Bei Bedarf kann das Feld programmgesteuert mit `restoreValue()` zurückgesetzt werden.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Werts {#ways-to-restore-the-value}

- **Programmgesteuert** mit `restoreValue()`
- **Über die Tastatur**, indem Sie <kbd>ESC</kbd> drücken (dies ist die Standard-Wiederherstellungstaste, es sei denn, sie wird überschrieben)

Der Wiederherstellungswert muss ausdrücklich festgelegt werden. Wenn er nicht definiert ist, wird die Funktion das Feld nicht zurücksetzen.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Der `MaskedNumberFieldSpinner` erweitert das [`MaskedNumberField`](#basics) um Spinner-Steuerelemente, mit denen Benutzer den Wert mit Schritt-Buttons oder Pfeiltasten erhöhen oder verringern können. 
Dies ist ideal für Eingaben wie Mengen, Preisänderungen, Bewertungssteuerungen oder jede Situation, in der Benutzer inkrementelle Änderungen vornehmen.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height='120px'
/>

### Hauptmerkmale {#key-features}

- **Schrittbeträge**  
  Verwenden Sie `setStep()`, um festzulegen, wie viel sich der Wert mit jedem Spin ändern soll:

  ```java
  spinner.setStep(5.0); // Jeder Spin addiert oder subtrahiert 5
  ```

- **Interaktive Steuerelemente**  
  Benutzer können auf Spinner-Tasten klicken oder die Tastatureingabe verwenden, um den Wert anzupassen.

- **Alle Funktionen des MaskedNumberField**  
  Unterstützt vollständig Masken, Formatierungen, Gruppierungs- und Dezimalzeichen, Mindest-/Höchstwerte und Wiederherstellungslogik.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
