---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 626c7e147632731dfdc761116a8abdc9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Das `MaskedNumberField` ist ein Texteingabefeld, das für die strukturierte Eingabe von Zahlen entwickelt wurde. Es stellt sicher, dass Zahlen konsistent basierend auf einer definierten Maske formatiert werden, was besonders nützlich für Finanzformulare, Preisfelder oder jegliche Eingaben ist, bei denen Präzision und Lesbarkeit wichtig sind.

Diese Komponente unterstützt die Nummernformatierung, die Lokalisierung von Dezimal- und Gruppierungszeichen sowie optionale Werteinschränkungen wie Mindest- oder Höchstwerte.

## Grundlagen {#basics}

Das `MaskedNumberField` kann mit oder ohne Parameter instanziiert werden. Es unterstützt das Setzen eines Anfangswertes, eines Labels, eines Platzhalters und eines Ereignislisteners, um auf Wertänderungen zu reagieren.

Diese Demo zeigt einen **Tippenrechner**, der `MaskedNumberField` für eine intuitive numerische Eingabe verwendet. Ein Feld ist so konfiguriert, dass es einen formatierten Rechnungsbetrag akzeptiert, während das andere einen Ganzzahl-Stipendienprozentsatz erfasst. Beide Felder wenden numerische Masken an, um eine konsistente und vorhersehbare Formatierung zu gewährleisten.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedNumberField` verwendet eine Maskenzeichenfolge, um zu steuern, wie die numerische Eingabe formatiert und angezeigt wird. 
Jedes Zeichen in der Maske definiert ein spezifisches Formatverhalten, das eine präzise Kontrolle darüber ermöglicht, wie Zahlen erscheinen.

### Maskenzeichen {#mask-characters}

| Zeichen   | Beschreibung |
|-----------|-------------|
| `0`       | Wird immer durch eine Ziffer (0–9) ersetzt. |
| `#`       | Unterdrückt führende Nullen. Wird durch das Füllzeichen links vom Dezimalpunkt ersetzt. Für nachfolgende Ziffern wird es durch ein Leerzeichen oder eine Null ersetzt. Andernfalls wird es durch eine Ziffer ersetzt. |
| `,`       | Wird als Gruppierungsseparator (z. B. Tausender) verwendet. Wird durch das Füllzeichen ersetzt, wenn keine Ziffern ihm vorausgehen. Andernfalls wird es als Komma angezeigt. |
| `-`       | Zeigt ein Minuszeichen (`-`) an, wenn die Zahl negativ ist. Wird durch das Füllzeichen ersetzt, wenn sie positiv ist. |
| `+`       | Zeigt `+` für positive oder `-` für negative Zahlen an. |
| `$`       | Führt immer zu einem Dollarzeichen. |
| `(`       | Fügt eine linke Klammer `(` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn sie positiv sind. |
| `)`       | Fügt eine rechte Klammer `)` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn sie positiv sind. |
| `CR`      | Zeigt `CR` für negative Zahlen an. Zeigt zwei Leerzeichen an, wenn die Zahl positiv ist. |
| `DR`      | Zeigt `CR` für negative Zahlen an. Zeigt `DR` für positive Zahlen an. |
| `*`       | Fügt einen Stern `*` ein. |
| `.`       | Markiert den Dezimalpunkt. Wenn keine Ziffern in der Ausgabe erscheinen, wird es durch das Füllzeichen ersetzt. Nach dem Dezimalpunkt werden Füllzeichen als Leerzeichen behandelt. |
| `B`       | Wird immer zu einem Leerzeichen. Jedes andere literale Zeichen wird unverändert angezeigt. |

Einige der oben genannten Zeichen können mehr als einmal in der Maske für die Formatierung erscheinen. Dazu gehören `-`, `+`, `$` und `(`. Wenn eines dieser Zeichen in der Maske vorhanden ist, wird das erste, das gefunden wird, an die letzte Position verschoben, an der ein `#` oder `,` durch das Füllzeichen ersetzt wurde. Wenn es keine solche Position gibt, bleibt das Doppelzeichen an seinem Platz.

:::info Keine automatische Rundung
Eine Maske innerhalb eines Feldes rundet **nicht**. Zum Beispiel, wenn ein Wert wie `12.34567` in ein Feld eingegeben wird, das mit `###0.00` maskiert ist, erhalten Sie `12.34`.
:::

## Gruppen- und Dezimaltrennzeichen {#group-and-decimal-separators}

Das `MaskedNumberField` unterstützt die Anpassung von **Gruppierungs**- und **Dezimal**zeichen, was es einfach macht, die Nummernformatierung an verschiedene Regionen oder Geschäftskonventionen anzupassen.

- Der **Gruppierungsseparator** wird verwendet, um Tausender visuell zu trennen (z. B. `1.000.000`).
- Der **Dezimalseparator** zeigt den Bruchteil einer Zahl an (z. B. `123,45`).

Dies ist in internationalen Anwendungen nützlich, in denen verschiedene Regionen unterschiedliche Zeichen verwenden (z. B. `.` vs `,`).

```java
field.setGroupCharacter(".");   // z. B. 1.000.000
field.setDecimalCharacter(","); // z. B. 123,45
```

:::tip Standardverhalten
Standardmäßig wendet `MaskedNumberField` Gruppierungs- und Dezimaltrennzeichen basierend auf der aktuellen Region der App an. Sie können sie jederzeit mit den bereitgestellten Settern überschreiben.
:::

## Negierbar {#negateable}

Das `MaskedNumberField` unterstützt eine Option, um zu steuern, ob negative Zahlen erlaubt sind.

Standardmäßig sind negative Werte wie `-123.45` erlaubt. Um dies zu verhindern, verwenden Sie `setNegateable(false)`, um die Eingabe nur auf positive Werte zu beschränken.

Dies ist in Geschäftsszenarien nützlich, in denen Werte wie Mengen, Gesamtbeträge oder Prozentsätze immer nicht-negativ sein müssen.

```java
field.setNegateable(false);
```

Wenn `negatable` auf `false` gesetzt ist, blockiert das Feld alle Versuche, ein Minuszeichen einzugeben oder anderweitig negative Werte einzugeben.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Min- und Max-Werte {#min-and-max-values}

Das `MaskedNumberField` unterstützt das Setzen von numerischen Grenzen mit `setMin()` und `setMax()`. 
Diese Einschränkungen helfen sicherzustellen, dass die Benutzereingaben innerhalb eines gültigen, erwarteten Bereichs bleiben.

- **Mindestwert**  
  Verwenden Sie `setMin()`, um die niedrigste akzeptable Zahl zu definieren:

  ```java
  field.setMin(10.0); // Mindestwert: 10
  ```

  Wenn der Benutzer eine Zahl unter diesem Schwellenwert eingibt, wird sie als ungültig betrachtet.

- **Höchstwert**  
  Verwenden Sie `setMax()`, um die höchste akzeptable Zahl zu definieren:

  ```java
  field.setMax(100.0); // Höchstwert: 100
  ```

  Werte über diesem Limit werden als ungültig gekennzeichnet.

## Wiederherstellung des Wertes {#restoring-the-value}

Das `MaskedNumberField` unterstützt eine Wiederherstellungsfunktion, die den Wert des Feldes in einen vordefinierten Zustand zurücksetzt. 
Dies kann nützlich sein, wenn Benutzer Änderungen rückgängig machen, versehentliche Bearbeitungen zurücksetzen oder zu einem bekannten Standardwert zurückkehren müssen.

Um dieses Verhalten zu aktivieren, definieren Sie den Zielwert mit `setRestoreValue()`. 
Bei Bedarf kann das Feld programmgesteuert mit `restoreValue()` zurückgesetzt werden.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert** mit `restoreValue()`
- **Über die Tastatur**, indem Sie <kbd>ESC</kbd> drücken (dies ist der Standard-Wiederherstellungsschlüssel, sofern nicht überschrieben)

Der Wiederherstellungswert muss ausdrücklich festgelegt werden. Wenn er nicht definiert ist, wird die Funktion das Feld nicht zurücksetzen.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Das `MaskedNumberFieldSpinner` erweitert [`MaskedNumberField`](#basics) um Spinner-Steuerelemente, mit denen Benutzer den Wert mit Schritt-Tasten oder Pfeiltasten erhöhen oder verringern können. 
Dies ist ideal für Eingaben wie Mengen, Preisänderungen, Bewertungssteuerelemente oder jedes Szenario, in dem Benutzer schrittweise Änderungen vornehmen.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Hauptmerkmale {#key-features}

- **Schritt-Inkremente**  
  Verwenden Sie `setStep()`, um zu definieren, um wie viel sich der Wert bei jedem Drehen ändern soll:

  ```java
  spinner.setStep(5.0); // Jeder Dreh fügt 5 hinzu oder subtrahiert 5
  ```

- **Interaktive Steuerelemente**  
  Benutzer können Spinner-Tasten anklicken oder die Tastatureingabe verwenden, um den Wert anzupassen.

- **Alle Funktionen von MaskedNumberField**  
  Unterstützt vollständig Masken, Formatierung, Gruppierungs-/Dezimalzeichen, Min/Max-Einschränkungen und Wiederherstellungslogik.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
