---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: e50a52f19876f98eec1bd825ca82cd6a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Das `MaskedTimeField` ist ein Texteingabefeld, das für präzise, strukturierte Zeiteingaben konzipiert ist. Es ermöglicht den Benutzern, Zeiten als **Zahlen** einzugeben, und formatiert die Eingabe automatisch basierend auf einer definierten Maske, wenn das Feld den Fokus verliert. Die Maske ist eine Zeichenfolge, die das erwartete Zeitformat angibt und sowohl Eingabe als auch Anzeige lenkt.

Diese Komponente unterstützt flexible Parsing-, Validierungs-, Lokalisierungs- und Wertwiederherstellungsfunktionen. Sie ist besonders nützlich bei zeitkritischen Formularen wie Zeitplänen, Stundenzetteln und Reservierungen.

:::tip Suchen Sie ein Datumseingabefeld?
Das `MaskedTimeField` ist für **nur Zeit** Eingaben konzipiert. Wenn Sie eine Komponente suchen, die **Datums**-Eingaben mit ähnlicher maskenbasierter Formatierung verarbeitet, werfen Sie einen Blick auf das [`MaskedDateField`](./datefield.md).
:::

## Grundlagen {#basics}

Das `MaskedTimeField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskenregeln {#mask-rules}

Das `MaskedTimeField` verwendet Formatindikatoren, um zu definieren, wie die Zeit geparsed und angezeigt wird. Jeder Formatindikator beginnt mit einem `%`, gefolgt von einem Buchstaben, der eine Zeitkomponente darstellt.

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
|-------------|---------------------------|
| `z`         | Nullauffüllung            |
| `s`         | Kurze Textdarstellung      |
| `l`         | Lange Textdarstellung      |
| `p`         | Kompakte Zahl             |
| `d`         | Dezimal (Standardformat)  |

Diese ermöglichen eine flexible und ortsunabhängige Zeitformatierung.

## Lokalisierung des Zeitformats {#time-format-localization}

Das `MaskedTimeField` unterstützt die Lokalisierung, indem die entsprechende Locale gesetzt wird. Dies stellt sicher, dass die zeitliche Eingabe und Ausgabe den regionalen Konventionen entsprechen.

```java
field.setLocale(Locale.GERMANY);
```

Dies beeinflusst, wie AM/PM-Indikatoren angezeigt werden, wie Trennzeichen behandelt werden und wie Werte geparsed werden.

## Parsing-Logik {#parsing-logic}

Das `MaskedTimeField` parst die Benutzereingabe basierend auf der definierten Zeitmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen und ermöglicht eine flexible Eingabe, während gültige Zeiten sichergestellt werden.
Das Parsing-Verhalten hängt von der Formatreihenfolge ab, die durch die Maske definiert ist (z.B. `%Hz:%mz` für Stunde/Minute). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

### Beispiel-Parsingszenarien {#example-parsing-scenarios}

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

Das `MaskedTimeField` umfasst eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies kann nützlich sein, um Änderungen rückgängig zu machen oder zu einer Standardzeit zurückzukehren.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Arten der Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die Standard-Wiederherstelltaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mit regulären Ausdrücken über die Methode `setPattern()` anwenden:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dieses Muster stellt sicher, dass nur Werte im Format `HH:mm` (zwei Ziffern, Doppelpunkt, zwei Ziffern) als gültig betrachtet werden.

:::tip Format des regulären Ausdrucks
Das Muster muss der JavaScript-RegExp-Syntax entsprechen, wie hier dokumentiert: [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Zeiteingaben basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder unparsebar ist (z.B. `99:99`), kann sie Musterprüfungen bestehen, aber die logische Validierung fehlschlagen.
Sie sollten den Eingabewert in Ihrer Anwendungslogik immer validieren, selbst wenn ein regulärer Ausdruck festgelegt ist, um sicherzustellen, dass die Zeit sowohl korrekt formatiert als auch sinnvoll ist.
:::

## Zeitwähler {#time-picker}

Das `MaskedTimeField` umfasst einen integrierten Zeitwähler, der es Benutzern ermöglicht, eine Zeit visuell auszuwählen, anstatt sie einzutippen. Dies verbessert die Benutzerfreundlichkeit für weniger technische Nutzer oder wenn eine präzise Eingabe erforderlich ist.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Zugriff auf den Wähler {#accessing-the-picker}

Sie können auf den Zeitwähler mit `getPicker()` zugreifen:

```java
TimePicker picker = field.getPicker();
```

### Wählen/Verstecken des Wähler-Icons {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Uhr-Icon neben dem Feld anzuzeigen oder zu verbergen:

```java
picker.setIconVisible(true); // zeigt das Icon an
```

### Auto-öffnen Verhalten {#auto-open-behavior}

Sie können den Wähler so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z.B. klickt, die Eingabetaste drückt oder Pfeiltasten verwendet):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Wähler erzwingen
Um sicherzustellen, dass Benutzer eine Zeit nur über den Wähler auswählen können (nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
field.getPicker().setAutoOpen(true); // Öffnet den Wähler bei Benutzerinteraktion
field.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Diese Konfiguration garantiert, dass alle Zeiteingaben über die Wählerschnittstelle erfolgen, was hilfreich ist, wenn Sie eine strenge Formatkontrolle wünschen und Parsing-Probleme durch getippte Eingaben ausschließen möchten.
:::

### Wähler manuell öffnen {#manually-open-the-picker}

Um den Zeitwähler programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // dasselbe wie open()
```

### Einstellung des Schrittwerts des Wählers {#setting-the-picker-step}

Sie können das Intervall zwischen auswählbaren Zeiten im Wähler mit `setStep()` definieren. Dies ermöglicht Ihnen, die Granularität der Zeitoptionen zu steuern—ideal für Szenarien wie Zeitpläne in 15-Minuten-Intervallen.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Schrittbeschränkung
Der Schritt muss gleichmäßig eine Stunde oder einen ganzen Tag teilen. Andernfalls wird eine Ausnahme ausgelöst.
:::

Dies stellt sicher, dass die Dropdown-Liste vorhersehbare, gleichmäßig verteilte Werte wie `09:00`, `09:15`, `09:30` usw. enthält.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Das `MaskedTimeFieldSpinner` erweitert [`MaskedTimeField`](#basics) durch Hinzufügen von Spinner-Steuerelementen, mit denen Benutzer die Zeit mithilfe von Pfeiltasten oder UI-Schaltflächen erhöhen oder verringern können. Es bietet einen geführten Interaktionsstil, der besonders in Desktop-Anwendungen nützlich ist.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Hauptmerkmale {#key-features}

- **Interaktive Zeitschritte:**  
  Verwenden Sie die Pfeiltasten oder Spin-Buttons, um den Zeitwert zu erhöhen oder zu verringern.

- **Anpassbare Schrittgröße:**  
  Wählen Sie aus, welcher Teil der Zeit geändert werden soll, indem Sie `setSpinField()` verwenden:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Optionen sind `HOUR`, `MINUTE`, `SECOND` und `MILLISECOND`.

- **Min/Max-Grenzen:**  
  Unterstützt weiterhin die Festlegung von minimalen und maximalen erlaubten Zeiten mit `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**  
  Vollständig kompatibel mit Masken- und Lokalisierungseinstellungen aus dem `MaskedTimeField`.

### Beispiel: Schrittweise Konfiguration nach Stunde {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />
