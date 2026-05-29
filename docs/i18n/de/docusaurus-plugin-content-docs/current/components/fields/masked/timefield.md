---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 97e5bc068e72cfd770c26fed4ceca434
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Das `MaskedTimeField` ist ein Texteingabefeld, das es Benutzern ermöglicht, Zeiten als **Zahlen** einzugeben und das Eingabefeld automatisch basierend auf einer definierten Maske zu formatieren, wenn das Feld den Fokus verliert. Die Maske gibt das erwartete Zeitformat an, das sowohl die Eingabe als auch die Anzeige steuert. Die Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und Wertwiederherstellung für einen konsistenten Umgang mit Zeiten.

<!-- INTRO_END -->

## Grundlagen {#basics}

:::tip Suchen Sie nach einem Datumsfeld?
Das `MaskedTimeField` ist für die **nur Zeit** Eingabe konzipiert. Wenn Sie eine Komponente suchen, die **Datumsangaben** mit ähnlicher maskenbasierter Formatierung verarbeitet, werfen Sie einen Blick auf das [`MaskedDateField`](./datefield.md).
:::

Das `MaskedTimeField` kann mit oder ohne Parameter instanziiert werden. Sie können einen anfänglichen Wert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedTimeField` verwendet Formatindikatoren, um zu definieren, wie Zeiten geparsed und angezeigt werden. Jeder Formatindikator beginnt mit einem `%` gefolgt von einem Buchstaben, der eine Zeitkomponente darstellt.

:::tip Masken programmgesteuert anwenden
Um Zeiten mit der gleichen Maskensyntax außerhalb eines Feldes zu formatieren oder zu parsen, verwenden Sie die [`MaskDecorator`](/docs/advanced/mask-decorator) Hilfsklasse.
:::

### Zeitformatindikatoren {#time-format-indicators}

| Format | Beschreibung        |
|--------|---------------------|
| `%H`   | Stunde (24-Stunden) |
| `%h`   | Stunde (12-Stunden) |
| `%m`   | Minute              |
| `%s`   | Sekunde             |
| `%p`   | AM/PM               |

### Modifizierer {#modifiers}

Modifizierer verfeinern die Anzeige von Zeitkomponenten:

| Modifizierer | Beschreibung                   |
|--------------|---------------------------------|
| `z`          | Nullauffüllung                 |
| `s`          | Kurze Textdarstellung           |
| `l`          | Lange Textdarstellung           |
| `p`          | Komprimierte Zahl               |
| `d`          | Dezimal (Standardformat)        |

Diese ermöglichen eine flexible und locale-freundliche Zeitformatierung.

## Lokalisierung des Zeitformats {#time-format-localization}

Das `MaskedTimeField` unterstützt die Lokalisierung durch Setzen der entsprechenden Locale. Dies stellt sicher, dass Eingabe und Ausgabe der Zeit den regionalen Konventionen entsprechen.

```java
field.setLocale(Locale.GERMANY);
```

Dies beeinflusst, wie AM/PM-Indikatoren angezeigt werden, wie Trennzeichen behandelt werden und wie Werte geparsed werden.

## Parsing-Logik {#parsing-logic}

Das `MaskedTimeField` parsed die Benutzereingabe basierend auf der definierten Zeitmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, was eine flexible Eingabe ermöglicht und gleichzeitig gültige Zeiten sicherstellt. Das Parsing-Verhalten hängt von der Formatreihenfolge ab, die durch die Maske definiert ist (z. B. `%Hz:%mz` für Stunde/Minute). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

### Beispiel für Parsing-Szenarien {#example-parsing-scenarios}

| Eingabe | Maske        | Interpretiert Als |
|---------|--------------|--------------------|
| `900`   | `%Hz:%mz`    | `09:00`            |
| `1345`  | `%Hz:%mz`    | `13:45`            |
| `0230`  | `%hz:%mz %p` | `02:30 AM`         |
| `1830`  | `%hz:%mz %p` | `06:30 PM`         |

## Minimale/maximale Einschränkungen festlegen {#setting-minmax-constraints}

Sie können den erlaubten Zeitbereich in einem `MaskedTimeField` mithilfe der Methoden `setMin()` und `setMax()` einschränken:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Eingaben außerhalb des definierten Bereichs gelten als ungültig.

## Wiederherstellen des Wertes {#restoring-the-value}

Das `MaskedTimeField` verfügt über eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies kann nützlich sein, um Änderungen rückgängig zu machen oder zu einer Standardzeit zurückzukehren.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Möglichkeiten, den Wert wiederherzustellen {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die Standard-Wiederherstellungstaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln unter Verwendung regulärer Ausdrücke mit der Methode `setPattern()` anwenden:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `HH:mm` (zwei Ziffern, Doppelpunkt, zwei Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format regulärer Ausdrücke
Das Muster muss der JavaScript-RegExp-Syntax folgen, wie [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) dokumentiert.
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Zeitwerte basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder nicht parsebar ist (z. B. `99:99`), kann sie den Musterprüfungen standhalten, aber die logische Validierung scheitern.
Sie sollten den Eingabewert in der Logik Ihrer App immer validieren, auch wenn ein reguläres Ausdrucksmuster gesetzt ist, um sicherzustellen, dass die Zeit sowohl korrekt formatiert als auch sinnvoll ist.
:::

## Zeitwähler {#time-picker}

Das `MaskedTimeField` enthält einen integrierten Zeitwähler, der es Benutzern ermöglicht, eine Zeit visuell auszuwählen, anstatt sie einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn präzise Eingaben erforderlich sind.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Zugriff auf den Wähler {#accessing-the-picker}

Sie können auf den Zeitwähler mit `getPicker()` zugreifen:

```java
TimePicker picker = field.getPicker();
```

### Anzeigen/ausblenden des Wähler-Icons {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Uhren-Icon neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Icon an
```

### Automatisches Öffnungsverhalten {#auto-open-behavior}

Sie können den Wähler so konfigurieren, dass er automatisch öffnet, wenn der Benutzer mit dem Feld interagiert (z. B. klickt, die Enter- oder Pfeiltasten drückt):

```java
picker.setAutoOpen(true);
```

:::tip Erzwingen der Auswahl über den Wähler
Um sicherzustellen, dass Benutzer eine Zeit nur über den Wähler auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
field.getPicker().setAutoOpen(true); // Öffnet den Wähler bei Benutzerinteraktion
field.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Diese Konfiguration gewährleistet, dass alle Zeiteingaben über die Wähler-UI erfolgen, was nützlich ist, wenn Sie strenge Formatkontrolle wünschen und Parsing-Probleme durch manuelle Eingaben vermeiden möchten.
:::

### Manuell den Wähler öffnen {#manually-open-the-picker}

Um den Zeitwähler programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // dasselbe wie open()
```

### Festlegen des Schrittes des Wählers {#setting-the-picker-step}

Sie können das Intervall zwischen wählbaren Zeiten im Wähler mit `setStep()` definieren. Dies ermöglicht es Ihnen, wie granular die Zeitoptionen sind - ideal für Szenarien wie die Planung in 15-Minuten-Intervallen.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Schrittbeschränkung
Der Schritt muss eine volle Stunde oder einen vollen Tag gleichmäßig teilen. Andernfalls wird eine Ausnahme ausgelöst.
:::

Dies stellt sicher, dass die Dropdown-Liste vorhersehbare, gleichmäßig verteilte Werte wie `09:00`, `09:15`, `09:30` usw. enthält.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Das `MaskedTimeFieldSpinner` erweitert das [`MaskedTimeField`](#basics) um Spinner-Steuerelemente, mit denen Benutzer die Zeit mithilfe von Pfeiltasten oder UI-Schaltflächen erhöhen oder verringern können. Es bietet einen geführten Interaktionsstil, der besonders nützlich in Desktop-Anwendungen ist.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Hauptmerkmale {#key-features}

- **Interaktives Zeitstepping:**  
  Verwenden Sie die Pfeiltasten oder Spin-Tasten, um den Zeitwert zu erhöhen oder zu verringern.

- **Anpassbare Spin-Einheit:**  
  Wählen Sie, welchen Teil der Zeit Sie ändern möchten, mithilfe von `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Optionen sind `HOUR`, `MINUTE`, `SECOND` und `MILLISECOND`.

- **Min/Max-Grenzen:**  
  Unterstützt die minimalen und maximalen zulässigen Zeiten mit `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**  
  Vollständig kompatibel mit Masken und Lokalisierungseinstellungen von `MaskedTimeField`.

### Beispiel: Stepping nach Stunden konfigurieren {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />
