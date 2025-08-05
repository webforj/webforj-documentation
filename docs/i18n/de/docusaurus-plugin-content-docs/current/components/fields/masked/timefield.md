---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 3d719856c08ce148bcd2999878d8c161
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Das `MaskedTimeField` ist eine Texteingabekontrolle, die für präzise, strukturierte Zeiteingaben entwickelt wurde. Sie ermöglicht es den Benutzern, Zeiten als **Zahlen** einzugeben und formatiert die Eingabe automatisch basierend auf einer definierten Maske, wenn das Feld den Fokus verliert. Die Maske ist eine Zeichenfolge, die das erwartete Zeitformat angibt und sowohl die Eingabe als auch die Anzeige leitet.

Dieses Komponenten unterstützt flexibles Parsen, Validierung, Lokalisierung und Wertwiederherstellung. Es ist besonders nützlich in zeitkritischen Formularen wie Zeitplänen, Stundenzetteln und Reservierungen.

:::tip Suchen Sie ein Datumseingabefeld?
Das `MaskedTimeField` ist für die Eingabe von **nur Zeiten** ausgelegt. Wenn Sie eine Komponente suchen, die **Daten** mit ähnlicher maskenbasierter Formatierung behandelt, werfen Sie einen Blick auf das [`MaskedDateField`](./datefield.md).
:::

## Grundlagen {#basics}

Das `MaskedTimeField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskenregeln {#mask-rules}

Das `MaskedTimeField` verwendet Formatindikatoren, um zu definieren, wie Zeit geparst und angezeigt wird. Jeder Formatindikator beginnt mit einem `%`, gefolgt von einem Buchstaben, der eine Zeiteinheit repräsentiert.

### Zeitformatindikatoren {#time-format-indicators}

| Format | Beschreibung          |
|--------|-----------------------|
| `%H`   | Stunde (24-Stunden)   |
| `%h`   | Stunde (12-Stunden)   |
| `%m`   | Minute                |
| `%s`   | Sekunde               |
| `%p`   | AM/PM                 |

### Modifizierer {#modifiers}

Modifizierer verfeinern die Anzeige der Zeiteinheiten:

| Modifizierer | Beschreibung                   |
|--------------|---------------------------------|
| `z`          | Nullauffüllung                  |
| `s`          | Kurze Textdarstellung           |
| `l`          | Lange Textdarstellung           |
| `p`          | Verdichtete Zahl                |
| `d`          | Dezimal (Standardformat)        |

Diese ermöglichen eine flexible und an die Region angepasste Zeitformatierung.

## Lokalisierung des Zeitformats {#time-format-localization}

Das `MaskedTimeField` unterstützt die Lokalisierung durch Festlegen der entsprechenden Locale. Dies stellt sicher, dass die Eingabe und Ausgabe der Zeit den regionalen Konventionen entsprechen.

```java
field.setLocale(Locale.GERMANY);
```

Dies beeinflusst, wie AM/PM-Indikatoren angezeigt werden, wie Trennzeichen behandelt werden und wie Werte geparst werden.

## Parsing-Logik {#parsing-logic}

Das `MaskedTimeField` parst Benutzereingaben basierend auf der definierten Zeitmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, was eine flexible Eingabe ermöglicht und gleichzeitig gültige Zeiten sicherstellt. 
Das Parsing-Verhalten hängt von der Formatordnung ab, die durch die Maske definiert ist (z. B. `%Hz:%mz` für Stunde/Minute). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

### Beispielhafte Parsing-Szenarien {#example-parsing-scenarios}

| Eingabe | Maske        | Interpretiert Als |
|---------|--------------|--------------------|
| `900`   | `%Hz:%mz`    | `09:00`            |
| `1345`  | `%Hz:%mz`    | `13:45`            |
| `0230`  | `%hz:%mz %p` | `02:30 AM`         |
| `1830`  | `%hz:%mz %p` | `06:30 PM`         |

## Festlegen von min/max-Beschränkungen {#setting-minmax-constraints}

Sie können den zulässigen Zeitbereich in einem `MaskedTimeField` mithilfe der Methoden `setMin()` und `setMax()` einschränken:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide Methoden akzeptieren Werte des Typs [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Eingaben außerhalb des definierten Bereichs werden als ungültig betrachtet.

## Wiederherstellen des Wertes {#restoring-the-value}

Das `MaskedTimeField` enthält eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies kann nützlich sein, um Änderungen rückgängig zu machen oder zu einer Standardzeit zurückzukehren.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, indem Sie `restoreValue()` aufrufen
- **Über die Tastatur**, indem Sie <kbd>ESC</kbd> drücken (dies ist die Standardwiederherstelltaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mit regulären Ausdrücken mithilfe der Methode `setPattern()` anwenden:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `HH:mm` (zwei Ziffern, Doppelpunkt, zwei Ziffern) entsprechen, als gültig angesehen werden.

:::tip Format regulärer Ausdrücke
Das Muster muss der JavaScript-RegExp-Syntax entsprechen, wie hier dokumentiert [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Zeiteingaben basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin Werte manuell eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder nicht parsebar ist (z. B. `99:99`), kann sie Musterprüfungen bestehen, aber logische Validierungen fehlschlagen.
Sie sollten den Eingabewert in Ihrer Anwendungslogik immer validieren, auch wenn ein reguläres Ausdrucksmuster gesetzt ist, um sicherzustellen, dass die Zeit sowohl korrekt formatiert als auch sinnvoll ist.
:::

## Zeitpicker {#time-picker}

Das `MaskedTimeField` beinhaltet einen integrierten Zeitpicker, der es den Benutzern ermöglicht, eine Zeit visuell auszuwählen, anstatt sie einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn präzise Eingaben erforderlich sind.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Zugriff auf den Picker {#accessing-the-picker}

Sie können auf den Zeitpicker mit `getPicker()` zugreifen:

```java
TimePicker picker = field.getPicker();
```

### Sichtbarkeit des Picker-Symbols umschalten {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Uhrensymbol neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatisches Öffnungsverhalten {#auto-open-behavior}

Sie können den Picker so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z. B. klickt, die Eingabetaste drückt oder die Pfeiltasten benutzt):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Picker erzwingen
Um sicherzustellen, dass Benutzer eine Zeit nur über den Picker auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
field.getPicker().setAutoOpen(true); // Öffnet den Picker bei Benutzerinteraktion
field.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Diese Konfiguration garantiert, dass alle Zeiteingaben über die Picker-Benutzeroberfläche erfolgen, was nützlich ist, wenn Sie eine strikte Formatkontrolle wünschen und Probleme beim Parsen aus eingegebener Eingabe vermeiden möchten.
:::

### Manuelles Öffnen des Pickers {#manually-open-the-picker}

Um den Zeitpicker programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // dasselbe wie open()
```

### Einstellen des Picker-Schritts {#setting-the-picker-step}

Sie können das Intervall zwischen auswählbaren Zeiten im Picker mit `setStep()` definieren. Dies ermöglicht Ihnen, wie granular die Zeitoptionen sein sollen – ideal für Szenarien wie die Planung in 15-Minuten-Blöcken.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Schritt-Beschränkung
Der Schritt muss eine volle Stunde oder einen vollen Tag gleichmäßig teilen. Andernfalls wird eine Ausnahme ausgelöst.
:::

Dies stellt sicher, dass die Dropdown-Liste vorhersehbare, gleichmäßig verteilte Werte wie `09:00`, `09:15`, `09:30` usw. enthält.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Der `MaskedTimeFieldSpinner` erweitert [`MaskedTimeField`](#basics) um Spinner-Steuerelemente, die es Benutzern ermöglichen, die Zeit mit Pfeiltasten oder UI-Buttons zu erhöhen oder zu verringern. Es bietet einen geführten Interaktionsstil, der besonders nützlich in Desktop-Anwendungen ist.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Hauptmerkmale {#key-features}

- **Interaktives Zeitsteppen:**  
  Verwenden Sie die Pfeiltasten oder Spin-Schaltflächen, um den Zeitwert zu erhöhen oder zu verringern.

- **Anpassbare Dreh-Einheit:**  
  Wählen Sie, welchen Teil der Zeit Sie ändern möchten, indem Sie `setSpinField()` verwenden:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Optionen umfassen `HOUR`, `MINUTE`, `SECOND` und `MILLISECOND`.

- **Min/Max-Grenzen:**  
  Unterstützt die Festlegung von minimalen und maximalen zulässigen Zeiten mit `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**  
  Vollständig kompatibel mit Masken und Lokalisierungseinstellungen aus dem `MaskedTimeField`.

### Beispiel: Schrittweise Anpassung nach Stunde {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />
