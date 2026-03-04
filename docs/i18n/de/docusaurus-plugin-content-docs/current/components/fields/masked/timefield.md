---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 17c5f6ce7fa234dbeb848c4bcab41e60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Das `MaskedTimeField` ist ein Texteingabefeld, mit dem Benutzer Zeiten als **Zahlen** eingeben können, und formatiert die Eingabe automatisch basierend auf einer definierten Maske, wenn das Feld den Fokus verliert. Die Maske gibt das erwartete Zeitformat an, welches sowohl die Eingabe als auch die Anzeige leitet. Die Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und Wiederherstellung von Werten für eine konsistente Zeitverarbeitung.

<!-- INTRO_END -->

## Grundlagen {#basics}

:::tip Suchen Sie ein Datumsfeld?
Das `MaskedTimeField` ist für **nur Zeit** Eingaben konzipiert. Wenn Sie nach einer Komponente suchen, die **Daten** mit ähnlicher maskenbasierter Formatierung verarbeitet, werfen Sie einen Blick auf das [`MaskedDateField`](./datefield.md).
:::

Das `MaskedTimeField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Werteänderungen definieren.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskenregeln {#mask-rules}

Das `MaskedTimeField` verwendet Formatindikatoren, um zu definieren, wie Zeiten geparst und angezeigt werden. Jeder Formatindikator beginnt mit einem `%`, gefolgt von einem Buchstaben, der eine Zeitkomponente darstellt.

### Zeitformatindikatoren {#time-format-indicators}

| Format | Beschreibung         |
|--------|---------------------|
| `%H`   | Stunde (24-Stunden)  |
| `%h`   | Stunde (12-Stunden)  |
| `%m`   | Minute              |
| `%s`   | Sekunde             |
| `%p`   | AM/PM               |

### Modifizierer {#modifiers}

Modifizierer verfeinern die Anzeige von Zeitkomponenten:

| Modifizierer | Beschreibung               |
|--------------|---------------------------|
| `z`          | Nullfüllung               |
| `s`          | Kurze Textdarstellung     |
| `l`          | Lange Textdarstellung      |
| `p`          | Gepackte Zahl             |
| `d`          | Dezimal (Standardformat)  |

Diese ermöglichen eine flexible und lokalisierungsfreundliche Zeitformatierung.

## Lokalisierung des Zeitformats {#time-format-localization}

Das `MaskedTimeField` unterstützt die Lokalisierung, indem das entsprechende Gebietsschema gesetzt wird. Dies sorgt dafür, dass Eingabe und Ausgabe der Zeit mit regionalen Konventionen übereinstimmen.

```java
field.setLocale(Locale.GERMANY);
```

Dies beeinflusst, wie AM/PM-Indikatoren angezeigt werden, wie Trenner behandelt werden und wie Werte geparst werden.

## Parsing-Logik {#parsing-logic}

Das `MaskedTimeField` parst die Benutzereingabe basierend auf der definierten Zeitmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen und ermöglicht damit eine flexible Eingabe, wobei dennoch gültige Zeiten sichergestellt werden.
Das Parsing-Verhalten hängt von der Formatreihenfolge ab, die durch die Maske definiert wird (z.B. `%Hz:%mz` für Stunde/Minute). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

### Beispielhafte Parsing-Szenarien {#example-parsing-scenarios}

| Eingabe | Maske        | Interpretiert Als |
|---------|--------------|--------------------|
| `900`   | `%Hz:%mz`    | `09:00`            |
| `1345`  | `%Hz:%mz`    | `13:45`            |
| `0230`  | `%hz:%mz %p` | `02:30 AM`         |
| `1830`  | `%hz:%mz %p` | `06:30 PM`         |

## Einstellung von Min/Max-Beschränkungen {#setting-minmax-constraints}

Sie können den erlaubten Zeitrahmen in einem `MaskedTimeField` mit den Methoden `setMin()` und `setMax()` einschränken:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Eingaben außerhalb des definierten Bereichs gelten als ungültig.

## Wiederherstellung des Wertes {#restoring-the-value}

Das `MaskedTimeField` verfügt über eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies kann nützlich sein, um Änderungen rückgängig zu machen oder zu einer Standardzeit zurückzukehren.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist der Standard-Wiederherstellungsschlüssel, es sei denn, er wird von einem Ereignislistener überschrieben)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mithilfe von regulären Ausdrücken mit der Methode `setPattern()` anwenden:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `HH:mm` (zwei Ziffern, Doppelpunkt, zwei Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format des Regulären Ausdrucks
Das Muster muss der JavaScript-RegExp-Syntax entsprechen, wie [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) dokumentiert.
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Zeiteingaben basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder nicht parsebar ist (z.B. `99:99`), kann sie die Mustertests bestehen, aber die logische Validierung fehlschlagen.
Sie sollten den Eingabewert in Ihrer Anwendungslogik immer validieren, auch wenn ein Muster mit regulärem Ausdruck gesetzt ist, um sicherzustellen, dass die Zeit sowohl korrekt formatiert als auch sinnvoll ist.
:::

## Zeitpicker {#time-picker}

Das `MaskedTimeField` enthält einen integrierten Zeitpicker, der es Benutzern ermöglicht, eine Zeit visuell auszuwählen, anstatt sie einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn eine präzise Eingabe erforderlich ist.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Zugriff auf den Picker {#accessing-the-picker}

Sie können auf den Zeitpicker mit `getPicker()` zugreifen:

```java
TimePicker picker = field.getPicker();
```

### Anzeigen/Verbergen des Picker-Icons {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Uhrenicon neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Icon an
```

### Auto-Öffnungs-Verhalten {#auto-open-behavior}

Sie können den Picker so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z. B. klickt, Enter drückt oder Pfeiltasten verwendet):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Picker erzwingen
Um sicherzustellen, dass Benutzer eine Zeit nur über den Picker auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
field.getPicker().setAutoOpen(true); // Öffnet den Picker bei Benutzereingabe
field.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Dieses Setup gewährleistet, dass alle Zeiteingaben über die Picker-Benutzeroberfläche erfolgen, was nützlich ist, wenn Sie strenge Formatkontrolle wünschen und Parsing-Probleme bei eingegebener Eingabe vermeiden möchten.
:::

### Manuelles Öffnen des Pickers {#manually-open-the-picker}

Um den Zeitpicker programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // entspricht open()
```

### Schritt des Pickers einstellen {#setting-the-picker-step}

Sie können das Intervall zwischen wählbaren Zeiten im Picker mit `setStep()` festlegen. Dadurch können Sie steuern, wie granular die Zeitoptionen sind – ideal für Szenarien wie die Planung in 15-Minuten-Blöcken.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Schritt-Beschränkung
Der Schritt muss eine Stunde oder einen gesamten Tag gleichmäßig teilen. Andernfalls wird eine Ausnahme ausgelöst.
:::

Dies sorgt dafür, dass die Dropdown-Liste vorhersehbare, gleichmäßig verteilte Werte wie `09:00`, `09:15`, `09:30` usw. enthält.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Der `MaskedTimeFieldSpinner` erweitert das [`MaskedTimeField`](#basics), indem Spinner-Steuerelemente hinzugefügt werden, mit denen Benutzer die Zeit mithilfe von Pfeiltasten oder UI-Schaltflächen erhöhen oder verringern können. Es bietet einen geführteren Interaktionsstil, der besonders nützlich in Desktop-Anwendungen ist.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Hauptmerkmale {#key-features}

- **Interaktives Zeitstepping:**  
  Verwenden Sie die Pfeiltasten oder Drehknöpfe, um den Zeitwert zu erhöhen oder zu verringern.

- **Anpassbare Dreheinheit:**  
  Wählen Sie, welcher Teil der Zeit geändert werden soll, mit `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Optionen sind `HOUR`, `MINUTE`, `SECOND` und `MILLISECOND`.

- **Min/Max-Grenzen:**  
  Erbt die Unterstützung für minimale und maximale zulässige Zeiten mit `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**  
  Vollständig kompatibel mit Masken- und Lokalisierungseinstellungen des `MaskedTimeField`.

### Beispiel: Stepping nach Stunden konfigurieren {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Stilgestaltung {#styling}

<TableBuilder name="MaskedTimeField" />
