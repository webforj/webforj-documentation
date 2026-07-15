---
title: MaskedTimeField
sidebar_position: 20
description: >-
  Capture time input with the MaskedTimeField, applying 12 or 24-hour masks,
  format indicators, locale-aware parsing, and validation.
_i18n_hash: 2631f01d383c134ba92d8ad03f5a57d3
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Das `MaskedTimeField` ist ein Texteingabefeld, das Benutzern das Eingeben von Zeiten als **Zahlen** ermöglicht und die Eingabe automatisch anhand einer definierten Maske formatiert, wenn das Feld den Fokus verliert. Die Maske gibt das erwartete Zeitformat vor und weist sowohl bei der Eingabe als auch bei der Anzeige den richtigen Weg. Die Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und Wiederherstellung von Werten für eine konsistente Zeitbehandlung.

<!-- INTRO_END -->

## Grundlagen {#basics}

:::tip Suchen Sie nach einer Datums-Eingabe?
Das `MaskedTimeField` ist für **nur Zeit** Eingaben konzipiert. Wenn Sie nach einer Komponente suchen, die **Daten** mit ähnlicher maskenbasierter Formatierung verwaltet, sehen Sie sich das [`MaskedDateField`](./datefield.md) an.
:::

Das `MaskedTimeField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Event-Listener für Wertänderungen definieren.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedTimeField` verwendet Formatindikatoren, um zu definieren, wie die Zeit geparst und angezeigt wird. Jeder Formatindikator beginnt mit einem `%`, gefolgt von einem Buchstaben, der eine Zeitkomponente darstellt.

:::tip Masken programmatisch anwenden
Um Zeiten mit derselben Maskensyntax außerhalb eines Feldes zu formatieren oder zu parsen, verwenden Sie die [`MaskDecorator`](/docs/advanced/mask-decorator) Dienstklasse.
:::

### Zeitformatindikatoren {#time-format-indicators}

| Format | Beschreibung         |
|--------|---------------------|
| `%H`   | Stunde (24-Stunden) |
| `%h`   | Stunde (12-Stunden) |
| `%m`   | Minute              |
| `%s`   | Sekunde             |
| `%p`   | AM/PM               |

### Modifikatoren {#modifiers}

Modifikatoren verfeinern die Anzeige von Zeitkomponenten:

| Modifikator | Beschreibung               |
|-------------|-----------------------------|
| `z`         | Nullfüllung                 |
| `s`         | Kurze Textdarstellung       |
| `l`         | Lange Textdarstellung       |
| `p`         | Gepackte Zahl               |
| `d`         | Dezimal (Standardformat)    |

Diese ermöglichen eine flexible und lokal freundliche Zeitformatierung.

## Lokalisierung des Zeitformats {#time-format-localization}

Das `MaskedTimeField` unterstützt die Lokalisierung durch das Setzen der entsprechenden Sprache. Dies stellt sicher, dass die Zeiteingaben und -ausgaben den regionalen Konventionen entsprechen.

```java
field.setLocale(Locale.GERMANY);
```

Dies beeinflusst, wie AM/PM-Indikatoren angezeigt werden, wie Trennzeichen behandelt werden und wie Werte geparst werden.

## Parsing-Logik {#parsing-logic}

Das `MaskedTimeField` parst Benutzereingaben basierend auf der definierten Zeitmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen und erlaubt eine flexible Eingabe, während es gültige Zeiten sicherstellt.
Das Parsing-Verhalten hängt von der Formatreihenfolge ab, die durch die Maske definiert ist (z. B. `%Hz:%mz` für Stunde/Minute). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

### Beispiel-Parszenarien {#example-parsing-scenarios}

| Eingabe | Maske         | Interpretiert Als |
|---------|---------------|--------------------|
| `900`   | `%Hz:%mz`    | `09:00`            |
| `1345`  | `%Hz:%mz`    | `13:45`            |
| `0230`  | `%hz:%mz %p` | `02:30 AM`         |
| `1830`  | `%hz:%mz %p` | `06:30 PM`         |

## Min-/Max-Beschränkungen festlegen {#setting-minmax-constraints}

Sie können den zulässigen Zeitbereich in einem `MaskedTimeField` mithilfe der `setMin()`- und `setMax()`-Methoden einschränken:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Eingaben außerhalb des definierten Bereichs werden als ungültig angesehen.

## Wert wiederherstellen {#restoring-the-value}

Das `MaskedTimeField` verfügt über eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies kann nützlich sein, um Änderungen rückgängig zu machen oder zu einer Standardzeit zurückzukehren.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Möglichkeiten, den Wert wiederherzustellen {#ways-to-restore-the-value}

- **Programmatisch**, durch Aufrufen von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die Standard-Wiederherstelltaste, es sei denn, sie wird durch einen Event-Listener überschrieben)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mithilfe von regulären Ausdrücken mit der `setPattern()`-Methode anwenden:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem `HH:mm`-Format (zwei Ziffern, Doppelpunkt, zwei Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format für reguläre Ausdrücke
Das Muster muss der JavaScript RegExp-Syntax entsprechen, wie hier [dokumentiert](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Zeiteingaben basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder nicht parsierbar ist (z. B. `99:99`), kann sie das Muster durchlaufen, jedoch bei der logischen Validierung fehlschlagen.
Sie sollten den Eingabewert in Ihrer Anwendungslogik immer validieren, auch wenn ein reguläres Ausdrucksmuster gesetzt ist, um sicherzustellen, dass die Zeit sowohl korrekt formatiert als auch sinnvoll ist.
:::

## Zeitpicker {#time-picker}

Das `MaskedTimeField` enthält einen integrierten Zeitpicker, der es Benutzern ermöglicht, eine Zeit visuell auszuwählen, anstatt sie einzutippen. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn eine präzise Eingabe erforderlich ist.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Zugriff auf den Picker {#accessing-the-picker}

Sie können auf den Zeitpicker mit `getPicker()` zugreifen:

```java
TimePicker picker = field.getPicker();
```

### Picker-Icon einblenden/ausblenden {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Uhrensymbol neben dem Feld ein- oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatische Öffnungsfunktion {#auto-open-behavior}

Sie können den Picker so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z. B. klickt, die Eingabetaste drückt oder Pfeiltasten benutzt):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Picker erzwingen
Um sicherzustellen, dass Benutzer nur eine Zeit über den Picker auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
field.getPicker().setAutoOpen(true); // Öffnet den Picker bei Benutzerinteraktion
field.setAllowCustomValue(false);     // Deaktiviert die manuelle Texteingabe
```

Diese Konfiguration stellt sicher, dass alle Zeiteingaben über die Picker-Benutzeroberfläche erfolgen, was nützlich ist, wenn Sie eine strikte Formatkontrolle wünschen und Parsing-Probleme durch eingegebene Werte beseitigen möchten.
:::

### Picker manuell öffnen {#manually-open-the-picker}

Um den Zeitpicker programmatisch zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // dasselbe wie open()
```

### Schritt des Pickers festlegen {#setting-the-picker-step}

Sie können das Intervall zwischen wählbaren Zeiten im Picker mit `setStep()` definieren. Damit können Sie steuern, wie detailliert die Zeitoptionen sind – ideal für Szenarien wie die Planung in 15-Minuten-Intervallen.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Schritt-Beschränkung
Der Schritt muss eine Stunde oder einen ganzen Tag gleichmäßig teilen. Andernfalls wird eine Ausnahme ausgelöst.
:::

Dies stellt sicher, dass die Dropdown-Liste vorhersehbare, gleichmäßig verteilte Werte wie `09:00`, `09:15`, `09:30` usw. enthält.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Der `MaskedTimeFieldSpinner` erweitert das [`MaskedTimeField`](#basics) um Spinner-Steuerelemente, mit denen Benutzer die Zeit mit den Pfeiltasten oder UI-Schaltflächen erhöhen oder verringern können. Es bietet einen geführten Interaktionsstil, der besonders in Desktop-Anwendungen nützlich ist.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Hauptmerkmale {#key-features}

- **Interaktive Zeitänderung:**
  Verwenden Sie die Pfeiltasten oder Drehknöpfe, um den Zeitwert zu erhöhen oder zu verringern.

- **Anpassbare Spin-Einheit:**
  Wählen Sie, welchen Teil der Zeit Sie ändern möchten, indem Sie `setSpinField()` verwenden:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Optionen sind `HOUR`, `MINUTE`, `SECOND` und `MILLISECOND`.

- **Min-/Max-Grenzen:**
  Erbt die Unterstützung für minimal und maximal erlaubte Zeiten mit `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**
  Vollständig kompatibel mit Masken und Lokalisierungseinstellungen aus dem `MaskedTimeField`.

### Beispiel: Stepping nach Stunden konfigurieren {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />
