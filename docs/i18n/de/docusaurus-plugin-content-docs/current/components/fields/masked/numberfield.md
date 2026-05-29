---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: a2b0a5275077beea1c053993d47aa861
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Das `MaskedNumberField` ist ein Texteingabefeld, das für strukturierte numerische Eingaben konzipiert ist. Es sorgt dafür, dass Zahlen entsprechend einer definierten Maske konsistent formatiert werden, was es besonders nützlich für Finanzformulare, Preisfelder oder jede Eingabe macht, bei der Genauigkeit und Lesbarkeit wichtig sind.

Diese Komponente unterstützt die Zahlenformatierung, die Lokalisierung von Dezimal-/Gruppierungszeichen und optionale Werteinschränkungen wie Mindest- oder Höchstwerte.

<!-- INTRO_END -->

## Grundlagen {#basics}

Das `MaskedNumberField` kann mit oder ohne Parameter instanziiert werden. Es unterstützt die Festlegung eines Anfangswerts, eines Labels, eines Platzhalters und eines Ereignislisteners, um auf Wertänderungen zu reagieren.

Diese Demo zeigt einen **Tip Calculator**, der das `MaskedNumberField` für intuitive numerische Eingaben verwendet. Ein Feld ist so konfiguriert, dass es einen formatierten Rechnungsbetrag akzeptiert, während das andere einen ganzzahligen Trinkgeldprozentsatz erfasst. Beide Felder wenden numerische Masken an, um eine konsistente und vorhersehbare Formatierung zu gewährleisten.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedNumberField` verwendet einen Maskenstring, um zu steuern, wie numerische Eingaben formatiert und angezeigt werden. 
Jedes Zeichen in der Maske definiert ein spezifisches Formatverhalten, das eine präzise Kontrolle über das Erscheinungsbild der Zahlen ermöglicht.

:::tip Masken programmgesteuert anwenden
Um Zahlen mit derselben Maskensyntax außerhalb eines Feldes zu formatieren, z.B. beim Rendern von Daten in einer [`Table`](/docs/components/table/overview), verwenden Sie die [`MaskDecorator`](/docs/advanced/mask-decorator) Hilfsklasse.
:::

### Maskenzeichen {#mask-characters}

| Zeichen   | Beschreibung |
|-----------|-------------|
| `0`       | Wird immer durch eine Ziffer (0–9) ersetzt. |
| `#`       | Unterdrückt führende Nullen. Wird durch das Füllzeichen links vom Dezimalpunkt ersetzt. Für nachfolgende Ziffern wird es durch ein Leerzeichen oder eine Null ersetzt. Ansonsten wird es durch eine Ziffer ersetzt. |
| `,`       | Wird als Gruppierungszeichen (z.B. Tausender) verwendet. Wird durch das Füllzeichen ersetzt, wenn keine Ziffern vorausgehen. Andernfalls wird es als Komma angezeigt. |
| `-`       | Zeigt ein Minuszeichen (`-`) an, wenn die Zahl negativ ist. Wird durch das Füllzeichen ersetzt, wenn die Zahl positiv ist. |
| `+`       | Zeigt `+` für positive oder `-` für negative Zahlen an. |
| `$`       | Führt immer zu einem Dollarzeichen. |
| `(`       | Führt eine linke Klammer `(` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn positiv. |
| `)`       | Führt eine rechte Klammer `)` für negative Werte ein. Wird durch das Füllzeichen ersetzt, wenn positiv. |
| `CR`      | Zeigt `CR` für negative Zahlen an. Zeigt zwei Leerzeichen an, wenn die Zahl positiv ist. |
| `DR`      | Zeigt `CR` für negative Zahlen an. Zeigt `DR` für positive Zahlen an. |
| `*`       | Fügt einen Stern `*` ein. |
| `.`       | Markiert den Dezimalpunkt. Wenn keine Ziffern in der Ausgabe erscheinen, wird es durch das Füllzeichen ersetzt. Nach dem Dezimalpunkt werden Füllzeichen als Leerzeichen behandelt. |
| `B`       | Wird immer zu einem Leerzeichen. Jedes andere Literalzeichen wird wie angegeben dargestellt. |

Einige der oben genannten Zeichen können mehr als einmal in der Maske für die Formatierung erscheinen. Dazu gehören `-`, `+`, `$` und
`(`. Wenn eines dieser Zeichen in der Maske vorhanden ist, wird das erste, auf das gestoßen wird, an die letzte Position verschoben, wo ein `#` oder `,` durch das Füllzeichen ersetzt wurde. Wenn keine solche Position vorhanden ist, bleibt das doppelte Zeichen an seinem Platz.

:::info Keine automatische Rundung
Eine Maske in einem Feld rundet **nicht** automatisch. Wenn Sie beispielsweise einen Wert wie `12.34567` in ein Feld eingeben, das mit `###0.00` maskiert ist, erhalten Sie `12.34`.
:::

## Gruppen- und Dezimaltrennzeichen {#group-and-decimal-separators}

Das `MaskedNumberField` unterstützt die Anpassung von **Gruppierungs**- und **Dezimal**zeichen, was es einfach macht, die Zahlenformatierung an unterschiedliche Regionen oder Geschäftspraktiken anzupassen.

- Das **Gruppentrenner** wird verwendet, um Tausender visuell zu trennen (z.B. `1.000.000`).
- Der **Dezimaltrennzeichen** zeigt den Bruchteil einer Zahl an (z.B. `123,45`).

Dies ist nützlich in internationalen Anwendungen, in denen verschiedene Regionen unterschiedliche Zeichen verwenden (z.B. `.` gegenüber `,`).

```java
field.setGroupCharacter(".");   // z.B. 1.000.000
field.setDecimalCharacter(","); // z.B. 123,45
```

:::tip Standardverhalten
Standardmäßig wendet das `MaskedNumberField` Gruppierungs- und Dezimaltrennzeichen basierend auf der aktuellen Sprache der App an. Sie können diese jederzeit mit den bereitgestellten Settern überschreiben.
:::

## Negierbar {#negateable}

Das `MaskedNumberField` unterstützt eine Option zur Steuerung, ob negative Zahlen zulässig sind.

Standardmäßig sind negative Werte wie `-123.45` zulässig. Um dies zu verhindern, verwenden Sie `setNegateable(false)`, um die Eingabe auf positive Werte zu beschränken.

Dies ist in Geschäftsszenarien nützlich, in denen Werte wie Mengen, Summen oder Prozentsätze immer nicht negativ sein müssen.

```java
field.setNegateable(false);
```

Wenn `negatable` auf `false` gesetzt ist, blockiert das Feld alle Versuche, ein Minuszeichen einzugeben oder negative Werte einzugeben.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Mindest- und Höchstwerte {#min-and-max-values}

Das `MaskedNumberField` unterstützt die Festlegung numerischer Grenzen mithilfe von `setMin()` und `setMax()`. 
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

  Werte über diesem Limit werden als ungültig markiert.

## Wert wiederherstellen {#restoring-the-value}

Das `MaskedNumberField` unterstützt eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten Zustand zurücksetzt. 
Dies kann nützlich sein, wenn Benutzer Änderungen rückgängig machen, versehentliche Bearbeitungen wiederherstellen oder zu einem bekannten Standardwert zurückkehren müssen.

Um dieses Verhalten zu aktivieren, definieren Sie den Zielwert mit `setRestoreValue()`. 
Bei Bedarf kann das Feld programmgesteuert mit `restoreValue()` zurückgesetzt werden.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Möglichkeiten, den Wert wiederherzustellen {#ways-to-restore-the-value}

- **Programmgesteuert** mit `restoreValue()`
- **Über die Tastatur**, indem Sie <kbd>ESC</kbd> drücken (dies ist die Standard-Wiederherstelltaste, sofern nicht überschrieben)

Der Wiederherstellungswert muss ausdrücklich festgelegt werden. Wenn er nicht definiert ist, wird die Funktion das Feld nicht zurücksetzen.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Das `MaskedNumberFieldSpinner` erweitert das [`MaskedNumberField`](#basics) um Spinner-Steuerelemente, die es Benutzern ermöglichen, den Wert mithilfe von Schritt-Tasten oder Pfeiltasten zu erhöhen oder zu verringern. 
Dies ist ideal für Eingaben wie Mengen, Preisänderungen, Bewertungssteuerungen oder jedes Szenario, in dem Benutzer schrittweise Änderungen vornehmen.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Hauptmerkmale {#key-features}

- **Schrittinkremente**  
  Verwenden Sie `setStep()`, um festzulegen, um wie viel sich der Wert bei jedem Spinnen ändern soll:

  ```java
  spinner.setStep(5.0); // Bei jedem Spin 5 hinzufügen oder subtrahieren
  ```

- **Interaktive Steuerungen**  
  Benutzer können auf Spinner-Tasten klicken oder die Tastatureingabe verwenden, um den Wert anzupassen.

- **Alle Funktionen des MaskedNumberField**  
  Unterstützt vollständig Masken, Formatierungen, Gruppen-/Dezimalzeichen, Min-/Max-Einschränkungen und Wiederherstellungslogik.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
