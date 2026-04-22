---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: bfaa13bee2b1c6dd1c88c8af989a6532
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Das `MaskedTimeField` ist ein Texteingabefeld, das es Benutzern ermöglicht, Zeiten als **Zahlen** einzugeben, und das die Eingabe basierend auf einer definierten Maske automatisch formatiert, wenn das Feld den Fokus verliert. Die Maske gibt das erwartete Zeitformat vor und leitet sowohl die Eingabe als auch die Anzeige. Die Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und die Wiederherstellung von Werten für eine konsistente Zeitverarbeitung.

<!-- INTRO_END -->

## Grundlagen {#basics}

:::tip Suchen Sie ein Datumsfeld?
Das `MaskedTimeField` ist für die Eingabe von **nur Zeiten** konzipiert. Wenn Sie eine Komponente suchen, die **Daten** mit ähnlicher maskenbasierter Formatierung verarbeitet, sehen Sie sich das [`MaskedDateField`](./datefield.md) an.
:::

Das `MaskedTimeField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskenregeln {#mask-rules}

Das `MaskedTimeField` verwendet Formatindikatoren, um zu definieren, wie Zeit geparst und angezeigt wird. Jeder Formatindikator beginnt mit einem `%`, gefolgt von einem Buchstaben, der eine Zeitkomponente darstellt.

:::tip Masken programmgesteuert anwenden
Um Zeiten mit der gleichen Maskensyntax außerhalb eines Feldes zu formatieren oder zu parsen, verwenden Sie die Utility-Klasse [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Zeitformatindikatoren {#time-format-indicators}

| Format | Beschreibung       |
|--------|---------------------|
| `%H`   | Stunde (24-Stunden) |
| `%h`   | Stunde (12-Stunden) |
| `%m`   | Minute              |
| `%s`   | Sekunde             |
| `%p`   | AM/PM               |

### Modifikatoren {#modifiers}

Modifikatoren verfeinern die Anzeige von Zeitkomponenten:

| Modifikator | Beschreibung               |
|-------------|----------------------------|
| `z`         | Nullauffüllung             |
| `s`         | Kurze Textdarstellung      |
| `l`         | Lange Textdarstellung      |
| `p`         | Gepackte Zahl              |
| `d`         | Dezimal (Standardformat)   |

Diese ermöglichen eine flexible und lokalisierungsfreundliche Zeitformatierung.

## Lokalisierung des Zeitformats {#time-format-localization}

Das `MaskedTimeField` unterstützt die Lokalisierung durch Festlegung der entsprechenden Locale. Dadurch wird sichergestellt, dass die Zeit-Eingaben und -Ausgaben den regionalen Konventionen entsprechen.

```java
field.setLocale(Locale.GERMANY);
```

Dies beeinflusst, wie AM/PM-Indikatoren angezeigt werden, wie Trennzeichen behandelt werden und wie Werte geparst werden.

## Parsing-Logik {#parsing-logic}

Das `MaskedTimeField` parst die Benutzereingabe basierend auf der definierten Zeitmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, wodurch eine flexible Eingabe ermöglicht wird, während gültige Zeiten sichergestellt werden.
Das Parsing-Verhalten hängt von der Formatordnung ab, die durch die Maske definiert ist (z. B. `%Hz:%mz` für Stunde/Minute). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

### Beispiel Parsing-Szenarien {#example-parsing-scenarios}

| Eingabe | Maske        | Interpretiert Als |
|---------|--------------|--------------------|
| `900`   | `%Hz:%mz`    | `09:00`            |
| `1345`  | `%Hz:%mz`    | `13:45`            |
| `0230`  | `%hz:%mz %p` | `02:30 AM`         |
| `1830`  | `%hz:%mz %p` | `06:30 PM`         |

## Min/Max-Beschränkungen festlegen {#setting-minmax-constraints}

Sie können den erlaubten Zeitbereich in einem `MaskedTimeField` mithilfe der Methoden `setMin()` und `setMax()` einschränken:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Eingaben außerhalb des definierten Bereichs werden als ungültig betrachtet.

## Den Wert wiederherstellen {#restoring-the-value}

Das `MaskedTimeField` beinhaltet eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies kann nützlich sein, um Änderungen rückgängig zu machen oder zu einer Standardzeit zurückzukehren.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Möglichkeiten, den Wert wiederherzustellen {#ways-to-restore-the-value}

- **Programmgesteuert**, indem `restoreValue()` aufgerufen wird
- **Über die Tastatur**, indem <kbd>ESC</kbd> gedrückt wird (dies ist der Standardwiederherstellungstaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mit regulären Ausdrücken mithilfe der Methode `setPattern()` anwenden:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `HH:mm` (zwei Ziffern, Doppelpunkt, zwei Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format der regulären Ausdrücke
Das Muster muss der JavaScript-RegExp-Syntax entsprechen, wie hier dokumentiert [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Hinweise zur Eingabeverwaltung
Das Feld versucht, numerische Zeitwerte basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder nicht parsebar ist (z. B. `99:99`), kann sie die Musterüberprüfung bestehen, aber die logische Validierung nicht bestehen.
Sie sollten den Eingabewert in Ihrer App-Logik immer validieren, selbst wenn ein reguläres Ausdrucksmuster festgelegt ist, um sicherzustellen, dass die Zeit sowohl korrekt formatiert als auch aussagekräftig ist.
:::

## Zeitpicker {#time-picker}

Das `MaskedTimeField` enthält einen integrierten Zeitpicker, der es Benutzern ermöglicht, eine Zeit visuell auszuwählen, anstatt sie einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn präzise Eingaben erforderlich sind.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Zugriff auf den Picker {#accessing-the-picker}

Sie können auf den Zeitpicker mit `getPicker()` zugreifen:

```java
TimePicker picker = field.getPicker();
```

### Picker-Symbol anzeigen/ausblenden {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Uhren-Symbol neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatisches Öffnungsverhalten {#auto-open-behavior}

Sie können den Picker so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z. B. klickt, die Eingabetaste drückt oder die Pfeiltasten verwendet):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Picker erzwingen
Um sicherzustellen, dass Benutzer eine Zeit nur über den Picker auswählen können (und nicht manuell eingeben können), kombinieren Sie die folgenden beiden Einstellungen:

```java
field.getPicker().setAutoOpen(true); // Öffnet den Picker bei Benutzerinteraktion
field.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Diese Konfiguration garantiert, dass alle Zeitwerte über die Picker-Benutzeroberfläche eingegeben werden, was nützlich ist, wenn Sie strenge Formatkontrollen wünschen und Parsing-Probleme durch eingegebene Werte vermeiden möchten.
:::

### Manuelles Öffnen des Pickers {#manually-open-the-picker}

Um den Zeitpicker programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // gleichbedeutend mit open()
```

### Festlegen des Picker-Schrittes {#setting-the-picker-step}

Sie können das Intervall zwischen auswählbaren Zeiten im Picker mit `setStep()` definieren. So können Sie steuern, wie granular die Zeitoptionen sind – ideal für Szenarien wie Terminplanung in 15-Minuten-Abschnitten.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Schritt-Beschränkung
Der Schritt muss eine Stunde oder einen ganzen Tag gleichmäßig teilen. Andernfalls wird eine Ausnahme ausgelöst.
:::

Damit wird sichergestellt, dass die Dropdown-Liste vorhersehbare, gleichmäßig verteile Werte wie `09:00`, `09:15`, `09:30` usw. enthält.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Der `MaskedTimeFieldSpinner` erweitert [`MaskedTimeField`](#basics) um Spinner-Steuerelemente, die es den Benutzern ermöglichen, die Zeit mit den Pfeiltasten oder UI-Schaltflächen zu erhöhen oder zu verringern. Dies bietet einen geführten Interaktionsstil, der besonders nützlich in desktopartigen Anwendungen ist.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Hauptmerkmale {#key-features}

- **Interaktive Zeitschritte:**  
  Verwenden Sie die Pfeiltasten oder Spin-Schaltflächen, um den Zeitwert zu erhöhen oder zu verringern.

- **Anpassbare Drehungseinheit:**  
  Wählen Sie, welchen Teil der Zeit Sie modifizieren möchten, indem Sie `setSpinField()` verwenden:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Optionen umfassen `HOUR`, `MINUTE`, `SECOND` und `MILLISECOND`.

- **Min/Max-Grenzen:**  
  Erbt die Unterstützung für minimale und maximale erlaubte Zeiten mit `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**  
  Vollständig kompatibel mit Masken und Lokalisierungseinstellungen aus dem `MaskedTimeField`.

### Beispiel: Stepping nach Stunde konfigurieren {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />
