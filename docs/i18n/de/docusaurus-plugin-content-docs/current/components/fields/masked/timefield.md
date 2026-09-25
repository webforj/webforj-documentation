---
title: MaskedTimeField
sidebar_position: 20
description: >-
  Capture time input with the MaskedTimeField, applying 12 or 24-hour masks,
  format indicators, locale-aware parsing, and validation.
_i18n_hash: 07256952a84572a67b1fe2b66dd5b5a5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Das `MaskedTimeField` ist ein Texteingabefeld, das es Benutzern ermöglicht, Zeiten als **Zahlen** einzugeben und die Eingabe automatisch basierend auf einer definierten Maske zu formatieren, wenn das Feld den Fokus verliert. Die Maske gibt das erwartete Zeitformat an und dient sowohl der Eingabe als auch der Anzeige als Leitfaden. Die Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und Wertwiederherstellung für eine konsistente Zeitverarbeitung.

<!-- INTRO_END -->

:::tip Suchen Sie ein Datumseingabefeld?
Das `MaskedTimeField` ist für **nur Zeit** Eingaben konzipiert. Wenn Sie eine Komponente suchen, die **Datumsangaben** mit ähnlicher maskenbasierter Formatierung behandelt, werfen Sie einen Blick auf das [`MaskedDateField`](/docs/components/fields/masked/datefield).
:::

Das `MaskedTimeField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedTimeField` verwendet Formatindikatoren, um zu definieren, wie Zeiten geparsed und angezeigt werden. Jeder Formatindikator beginnt mit einem `%`, gefolgt von einem Buchstaben, der eine Zeiteinheit darstellt.

:::tip Masken programmatisch anwenden
Um Zeiten mit derselben Maskensyntax außerhalb eines Feldes zu formatieren oder zu parsen, verwenden Sie die [`MaskDecorator`](/docs/advanced/mask-decorator) Hilfsklasse.
:::

### Zeitformatindikatoren {#time-format-indicators}

| Format | Beschreibung         |
|--------|----------------------|
| `%H`   | Stunde (24-Stunden)  |
| `%h`   | Stunde (12-Stunden)  |
| `%m`   | Minute               |
| `%s`   | Sekunde              |
| `%p`   | AM/PM                |

### Modifikatoren {#modifiers}

Modifikatoren verfeinern die Darstellung von Zeiteinheiten:

| Modifikator | Beschreibung                  |
|-------------|-------------------------------|
| `z`         | Nullauffüllen                 |
| `s`         | Kurze Textdarstellung         |
| `l`         | Lange Textdarstellung         |
| `p`         | Kompakte Zahl                 |
| `d`         | Dezimal (Standardformat)      |

Diese ermöglichen eine flexible und lokalisierungsfreundliche Zeitformatierung.

## Lokalisierung des Zeitformats {#time-format-localization}

Das `MaskedTimeField` unterstützt die Lokalisierung durch Setzen der entsprechenden Locale. Dies stellt sicher, dass Eingaben und Ausgaben von Zeiten regionalen Konventionen entsprechen.

```java
field.setLocale(Locale.GERMANY);
```

Dies beeinflusst, wie AM/PM-Indikatoren angezeigt werden, wie Trenner behandelt werden und wie Werte geparsed werden.

## Parsing-Logik {#parsing-logic}

Das `MaskedTimeField` parsed Benutzereingaben basierend auf der definierten Zeitmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, was eine flexible Eingabe ermöglicht und gleichzeitig gültige Zeiten sichert. Das Parsing-Verhalten hängt von der Formatreihenfolge ab, die durch die Maske definiert ist (z.B. `%Hz:%mz` für Stunde/Minute). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

### Beispielhafte Parsing-Szenarien {#example-parsing-scenarios}

| Eingabe | Maske        | Interpretiert Als |
|---------|--------------|--------------------|
| `900`   | `%Hz:%mz`    | `09:00`            |
| `1345`  | `%Hz:%mz`    | `13:45`            |
| `0230`  | `%hz:%mz %p` | `02:30 AM`         |
| `1830`  | `%hz:%mz %p` | `06:30 PM`         |

## Minimale/maximale Einschränkungen festlegen {#setting-minmax-constraints}

Sie können den erlaubten Zeitbereich in einem `MaskedTimeField` mit den Methoden `setMin()` und `setMax()` einschränken:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide Methoden akzeptieren Werte des Typs [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Eingaben außerhalb des definierten Bereichs werden als ungültig betrachtet.

## Wiederherstellung des Wertes {#restoring-the-value}

Das `MaskedTimeField` enthält eine Wiederherstellungsfunktion, die den Wert des Feldes in einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies kann nützlich sein, um Änderungen rückgängig zu machen oder zu einer Standardzeit zurückzukehren.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmatisch**, durch Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die standardmäßige Wiederherstellungstaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mit regulären Ausdrücken unter Verwendung der Methode `setPattern()` anwenden:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `HH:mm` (zwei Ziffern, Doppelpunkt, zwei Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format für reguläre Ausdrücke
Das Muster muss der JavaScript RegExp-Syntax entsprechen, wie hier dokumentiert [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Zeitwerte basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder nicht parsebar ist (z.B. `99:99`), kann sie Prüfungsmuster bestehen, aber bei logischen Validierungen fehlschlagen. 
Sie sollten den Eingabewert in Ihrer Anwendungslogik immer validieren, selbst wenn ein reguläres Ausdrucksmuster festgelegt ist, um sicherzustellen, dass die Zeit sowohl korrekt formatiert als auch sinnvoll ist.
:::

## Zeitwähler {#time-picker}

Das `MaskedTimeField` enthält einen eingebauten Zeitwähler, der es Benutzern ermöglicht, eine Zeit visuell auszuwählen, anstatt sie einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technologische Benutzer oder wenn eine präzise Eingabe erforderlich ist.

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

### Anzeige/Verstecken des Wähler-Icons {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Uhrensymbol neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatisches Öffnungsverhalten {#auto-open-behavior}

Sie können den Wähler so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z.B. klickt, die Eingabetaste drückt oder die Pfeiltasten verwendet):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Wähler erzwingen
Um sicherzustellen, dass Benutzer nur eine Zeit über den Wähler auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
field.getPicker().setAutoOpen(true); // Öffnet den Wähler bei Benutzerinteraktion
field.setAllowCustomValue(false);     // Deaktiviert die manuelle Texteingabe
```

Diese Konfiguration garantiert, dass alle Zeiteingaben über die Wähler-Benutzeroberfläche erfolgen, was nützlich ist, wenn Sie eine strenge Formatkontrolle wünschen und Parsing-Probleme bei manuell eingegebene Werte ausschließen möchten.
:::

### Wähler manuell öffnen {#manually-open-the-picker}

Um den Zeitwähler programmatisch zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // dasselbe wie open()
```

### Einstellung des Wähler-Schrittes {#setting-the-picker-step}

Sie können das Intervall zwischen auswählbaren Zeiten im Wähler mithilfe von `setStep()` definieren. Dies ermöglicht Ihnen, die Granularität der Zeitoptionen zu steuern – ideal für Szenarien wie die Planung in 15-Minuten-Intervallen.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Schrittbegrenzung
Der Schritt muss eine vollständige Stunde oder einen vollen Tag gleichmäßig teilen. Andernfalls wird eine Ausnahme ausgelöst.
:::

Dies stellt sicher, dass die Dropdown-Liste vorhersehbare, gleichmäßig verteilte Werte wie `09:00`, `09:15`, `09:30` usw. enthält.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Der `MaskedTimeFieldSpinner` erweitert das `MaskedTimeField`, indem er Spinner-Steuerelemente hinzufügt, mit denen Benutzer die Zeit mit Pfeiltasten oder UI-Buttons erhöhen oder verringern können. Es bietet einen geführten Interaktionsstil, der besonders nützlich in Desktop-Anwendungen ist.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Hauptmerkmale {#key-features}

- **Interaktives Zeitstepping:**
  Verwenden Sie die Pfeiltasten oder Drehknöpfe, um den Zeitwert zu erhöhen oder zu verringern.

- **Anpassbare Drehfeldgröße:**
  Wählen Sie, welchen Teil der Zeit Sie mithilfe von `setSpinField()` modifizieren möchten:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Optionen umfassen `HOUR`, `MINUTE`, `SECOND` und `MILLISECOND`.

- **Min/Max-Grenzen:**
  Erbt Unterstützung für minimal und maximal erlaubte Zeiten mit `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**
  Vollständig kompatibel mit Masken und Lokalisierungseinstellungen aus `MaskedTimeField`.

### Beispiel: Konfigurieren des Steppings nach Stunde {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />
