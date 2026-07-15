---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: 433c612e16b0476f720deb896cb73f4a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Das `MaskedDateField` ist ein Texteingabefeld, das es Benutzern ermöglicht, Daten als Zahlen einzugeben und die Eingabe automatisch basierend auf einer definierten Maske zu formatieren, wenn das Feld den Fokus verliert. Die Maske gibt das erwartete Datumsformat vor und leitet sowohl die Eingabe als auch die Anzeige. Die Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und die Wiederherstellung von Werten für eine konsistente, regionsspezifische Datumsbehandlung.

<!-- INTRO_END -->

## Grundlagen {#basics}

:::tip Suchen Sie nach einer Zeiteingabe?
Das `MaskedDateField` konzentriert sich ausschließlich auf **Datums**werte. Wenn Sie eine ähnliche Komponente zum Eingeben und Formatieren von **Zeit** benötigen, schauen Sie sich stattdessen das [`MaskedTimeField`](./timefield) an.
:::

Das `MaskedDateField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedDateField` unterstützt mehrere Datumsformate, die weltweit verwendet werden und sich nach der Reihenfolge von Tag, Monat und Jahr unterscheiden. Häufige Muster sind:

- **Tag/Monat/Jahr** (in den meisten europäischen Ländern verwendet)
- **Monat/Tag/Jahr** (in den Vereinigten Staaten verwendet)
- **Jahr/Monat/Tag** (in China, Japan und Korea verwendet; auch der ISO-Standard: `YYYY-MM-DD`)

Innerhalb dieser Formate gibt es lokale Variationen wie die Wahl des Trennzeichens (z. B. `-`, `/` oder `.`), ob Jahre zwei oder vier Ziffern haben und ob einstellige Monate oder Tage mit führenden Nullen erweitert werden.

Um mit dieser Vielfalt umzugehen, verwendet das `MaskedDateField` Formatindikatoren, die jeweils mit `%` beginnen, gefolgt von einem Buchstaben, der einen spezifischen Teil des Datums darstellt. Diese Indikatoren definieren, wie die Eingabe geparsed und wie das Datum angezeigt wird.

:::tip Anwendung von Masken programmgesteuert
Um Daten mit der gleichen Maskensyntax außerhalb eines Feldes zu formatieren oder zu parsen, verwenden Sie die [`MaskDecorator`](/docs/advanced/mask-decorator) Utility-Klasse.
:::

### Datumsformatindikatoren {#date-format-indicators}

| Format | Beschreibung   |
| ------ | --------------- |
| `%Y`   | Jahr            |
| `%M`   | Monat           |
| `%D`   | Tag             |

### Modifizierer {#modifiers}

Modifizierer erlauben eine genauere Kontrolle darüber, wie die Komponenten des Datums formatiert werden:

| Modifizierer | Beschreibung                       |
| ------------ | --------------------------------- |
| `z`          | Nullauffüllung                    |
| `s`          | Kurze Textdarstellung             |
| `l`          | Lange Textdarstellung              |
| `p`          | Gepackte Zahl                     |
| `d`          | Dezimal (Standardformat)           |

Diese können kombiniert werden, um eine Vielzahl von Datums­masken zu erstellen.

## Lokalisierung des Datumsformats {#date-format-localization}

Das `MaskedDateField` passt sich regionalen Datumsformaten an, indem es die geeignete Gebietsschemaeinstellung festlegt. Dies stellt sicher, dass Daten in einer Weise angezeigt und geparsed werden, die den Erwartungen der Benutzer entspricht.

| Region        | Format     | Beispiel      |
| ------------- | ---------- | ------------ |
| Vereinigte Staaten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO-Standard  | YYYY-MM-DD | `2023-07-04` |

Um die Lokalisierung anzuwenden, verwenden Sie die Methode `setLocale()`. Diese akzeptiert ein [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) und passt automatisch sowohl Formatierung als auch Parsing an:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing-Logik {#parsing-logic}

Das `MaskedDateField` parsed die Benutzereingabe basierend auf der definierten Datumsmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, was eine flexible Eingabe ermöglicht, während gültige Daten sichergestellt werden.
Das Parsing-Verhalten hängt von der durch die Maske definierten Formatordnung ab (z. B. `%Mz/%Dz/%Yz` für Monat/Tag/Jahr). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

Angenommen, heute ist `15. September 2012`, so würden verschiedene Eingaben folgendermaßen interpretiert werden:

### Beispiel-Parsingszenarien {#example-parsing-scenarios}

| Eingabe                      | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ---------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>       | Eine einzelne Ziffer wird immer als Tag im aktuellen Monat interpretiert, also wäre das der 1. September 2012.                                                                                 | Gleich wie YMD                                                                         | Gleich wie YMD                                                                                                                  |
| <div align="center">`12`</div>      | Zwei Ziffern werden immer als Tag im aktuellen Monat interpretiert, also wäre das der 12. September 2012.                                                                                     | Gleich wie YMD                                                                         | Gleich wie YMD                                                                                                                  |
| <div align="center">`112`</div>     | Drei Ziffern werden als 1-stellige Monatszahl gefolgt von einer 2-stelligen Tageszahl interpretiert, also wäre das der 12. Januar 2012.                                                            | Gleich wie YMD                                                                         | Drei Ziffern werden als 1-stellige Tageszahl gefolgt von einer zweistelligen Monatszahl interpretiert, also wäre das der 1. Dezember 2012. |
| <div align="center">`1004`</div>    | Vier Ziffern werden als MMDD interpretiert, also wäre das der 4. Oktober 2012.                                                                                                                   | Gleich wie YMD                                                                         | Vier Ziffern werden als DDMM interpretiert, also wäre das der 10. April 2012.                                                         |
| <div align="center">`020304`</div>  | Sechs Ziffern werden als YYMMDD interpretiert, also wäre das der 4. März 2002.                                                                                                                   | Sechs Ziffern werden als MMDDYY interpretiert, also wäre das der 3. Februar 2004.            | Sechs Ziffern werden als DDMMYY interpretiert, also wäre das der 2. März 2004.                                                         |
| <div align="center">`8 Ziffern`</div> | Acht Ziffern werden als YYYYMMDD interpretiert. Zum Beispiel, `20040612` ist der 12. Juni 2004.                                                                                                 | Acht Ziffern werden als MMDDYYYY interpretiert. Zum Beispiel, `06122004` ist der 12. Juni 2004. | Acht Ziffern werden als DDMMYYYY interpretiert. Zum Beispiel, `06122004` ist der 6. Dezember 2004.                                        |
| <div align="center">`12/6`</div>    | Zwei Zahlen, die durch ein gültiges Trennzeichen getrennt sind, werden als MM/DD interpretiert, also wäre das der 6. Dezember 2012. <br />Hinweis: Alle Zeichen außer Buchstaben und Ziffern gelten als gültige Trennzeichen. | Gleich wie YMD                                                                         | Zwei Zahlen, die durch ein Trennzeichen getrennt sind, werden als DD/MM interpretiert, also wäre das der 12. Juni 2012.                               |
| <div align="center">`3/4/5`</div>   | 5. April 2012                                                                                                                                                                                      | 4. März 2005                                                                       | 3. April 2005                                                                                                                 |


## Textuelles Datumsparsing <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standardmäßig akzeptiert das `MaskedDateField` nur numerische Eingaben für Daten. Sie können jedoch **textuelles Datumsparsing** aktivieren, um Benutzern die Eingabe von Monats- und Tagesnamen zu ermöglichen. Diese Funktion ist besonders nützlich für eine natürlichere Datumseingabe.

Um textuelles Parsen zu aktivieren, verwenden Sie die Methode `setTextualDateParsing()`:

```java
dateField.setTextualDateParsing(true);
```

### Monatsnamenersatz {#month-name-substitution}

Wenn textuelles Parsen aktiviert ist, können Sie spezielle Modifizierer in Ihrer Maske verwenden, um Monatsnamen anstelle von numerischen Werten zu akzeptieren:

- **`%Ms`** - Akzeptiert kurze Monatsnamen (Jan, Feb, Mär usw.)
- **`%Ml`** - Akzeptiert lange Monatsnamen (Januar, Februar, März usw.)

Monatsnamen können an beliebiger Stelle innerhalb der Maske erscheinen, und das Feld akzeptiert weiterhin numerische Eingaben als Fallback.

#### Beispiele {#examples}

| Maske         | Eingabe         | Ergebnis                            |
| ------------- | --------------- | ----------------------------------- |
| `%Ms/%Dz/%Yz` | `Sep/01/25`    | **Gültig** - Parse als 1. September 2025  |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Gültig** - Parse als 1. September 2025  |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Gültig** - Parse als 1. September 2025  |
| `%Mz/%Dz/%Yz` | `09/01/25`     | **Gültig** - Numerisches Fallback funktioniert weiterhin |

:::info
Alle 12 Monate werden sowohl in kurzer (Jan, Feb, Mär, Apr, Mai, Jun, Jul, Aug, Sep, Okt, Nov, Dez) als auch in langer (Januar, Februar usw.) Form unterstützt.
:::

### Dekoration von Tagesnamen {#day-name-decoration}

Tage der Woche können in die Eingabe aufgenommen werden, um die Lesbarkeit zu verbessern, aber sie sind **nur dekorativ** und werden beim Parsen entfernt. Sie beeinflussen nicht den tatsächlichen Datumswert.

- **`%Ds`** - Akzeptiert kurze Tagesnamen (Mo, Di, Mi usw.)
- **`%Dl`** - Akzeptiert lange Tagesnamen (Montag, Dienstag, Mittwoch usw.)

:::warning Tagesnamen erfordern numerischen Tag
Wenn Sie Tagesnamen ( `%Ds` oder `%Dl`) verwenden, muss Ihre Maske **auch** `%Dz` oder `%Dd` enthalten, um die tatsächliche Tageszahl anzugeben. Ohne eine numerische Tageskomponente ist die Eingabe ungültig.
:::

#### Beispiele {#examples-1}

| Maske                  | Eingabe           | Ergebnis                            |
| ---------------------- | ----------------- | ----------------------------------- |
| `%Ds %Mz/%Dz/%Yz`     | `Mo 09/01/25`     | **Gültig** - Tagesname ist dekorativ |
| `%Dl %Mz/%Dz/%Yz`     | `Montag 09/01/25` | **Gültig** - Tagesname ist dekorativ |
| `%Mz/%Dz/%Yz %Ds`     | `09/01/25 Di`     | **Gültig** - Tagesname am Ende |
| `%Dl/%Mz/%Yz`         | `Montag/09/25`    | **Ungültig** - Fehlender `%Dz`     |
| `%Mz/%Dl/%Yz`         | `09/Montag/25`    | **Ungültig** - Fehlender `%Dz`     |

Alle 7 Wochentage werden sowohl in kurzer (Mo, Di, Mi, Do, Fr, Sa, So) als auch in langer (Montag, Dienstag usw.) Form unterstützt.

### Zusätzliche Parsing-Regeln {#additional-parsing-rules}

Das textuelle Datumsparsing umfasst mehrere hilfreiche Funktionen:

- **Groß- und Kleinschreibung ignoriert:** Eingaben wie `MONTAG 09/01/25`, `montag 09/01/25` oder `Montag 09/01/25` funktionieren alle gleich.
- **Lokal abhängig:** Monats- und Tagesnamen müssen mit dem konfigurierten Gebietsschema des Feldes übereinstimmen. Beispielsweise müssen Sie bei einer französischen Gebietsschemaeinstellung `septembre` und nicht `September` verwenden. Englische Namen werden nicht erkannt, es sei denn, das Gebietsschema ist auf Englisch eingestellt.
  - Französisches Gebietsschema: `septembre/01/25` wird als September erkannt.
  - Deutsches Gebietsschema: `Montag 09/01/25` wird mit Montag als Tagesnamen erkannt.

## Festlegen von Min-/Max-Beschränkungen {#setting-minmax-constraints}

Sie können den zulässigen Datumsbereich in einem `MaskedDateField` mithilfe der Methoden `setMin()` und `setMax()` einschränken:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Eingaben außerhalb des definierten Bereichs gelten als ungültig.

## Wiederherstellen des Wertes {#restoring-the-value}

Das `MaskedDateField` umfasst eine Wiederherstellungsfunktion, die den Wert des Felds auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies ist nützlich, um Benutzereingaben zurückzusetzen oder auf ein Standarddatum zurückzusetzen.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert** durch Aufruf von `restoreValue()`
- **Über die Tastatur**, indem Sie <kbd>ESC</kbd> drücken (dies ist die Standard-Wiederherstellungstaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

Sie können den Wert, der wiederhergestellt werden soll, mit `setRestoreValue()` festlegen, indem Sie eine [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) Instanz übergeben.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mithilfe regulärer Ausdrücke mit der Methode `setPattern()` anwenden:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `MM/DD/YYYY` (zwei Ziffern, Schrägstrich, zwei Ziffern, Schrägstrich, vier Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format regulärer Ausdrücke
Das Muster muss der JavaScript-RegExp-Syntax entsprechen, wie hier dokumentiert [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Datumswerte basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin Werte manuell eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch falsch oder nicht parselbar ist (z. B. `99/99/9999`), kann sie Musterprüfungen bestehen, aber die logische Validierung fehlschlagen.
Sie sollten den Eingabewert in Ihrer Anwendungslogik immer validieren, auch wenn ein regulärer Ausdrucksmuster festgelegt ist, um sicherzustellen, dass das Datum sowohl korrekt formatiert als auch sinnvoll ist.
::::

## Datumsauswahl {#date-picker}

Das `MaskedDateField` enthält einen integrierten Kalenderauswahl, der es Benutzern ermöglicht, ein Datum visuell auszuwählen, anstatt es einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn präzise Eingaben erforderlich sind.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Zugriff auf den Picker {#accessing-the-picker}

Sie können auf den Datumsauswähler mit `getPicker()` zugreifen:

```java
DatePicker picker = dateField.getPicker();
```

### Anzeigen/Verstecken des Picker-Icons {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Kalendersymbol neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatisches Öffnungsverhalten {#auto-open-behavior}

Sie können den Picker so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z. B. klickt, Enter drückt oder mit den Pfeiltasten navigiert):

```java
picker.setAutoOpen(true);
```

:::tip Erzwingen der Auswahl über den Picker
Um sicherzustellen, dass Benutzer nur ein Datum über den Kalenderauswähler auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden zwei Einstellungen:

```java
dateField.getPicker().setAutoOpen(true); // Öffnet den Picker bei Benutzerinteraktion
dateField.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Diese Einrichtung garantiert, dass alle Datumseingaben über die Picker-Benutzeroberfläche erfolgen, was nützlich ist, wenn Sie strenge Formatkontrolle möchten und Parsing-Probleme von eingetippten Eingaben eliminieren möchten.
:::

### Kalender manuell öffnen {#manually-open-the-calendar}

Um den Kalender programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // dasselbe wie öffnen
```

### Kalenderwochen anzeigen {#show-weeks-in-the-calendar}

Der Picker kann optional die Wochennummern in der Kalenderansicht anzeigen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Der `MaskedDateFieldSpinner` erweitert das [`MaskedDateField`](#basics) um Spinner-Steuerelemente, mit denen Benutzer das Datum mit den Pfeiltasten oder UI-Schaltflächen erhöhen oder verringern können. Dies bietet eine geführte Interaktion, die besonders nützlich in Desktop-Anwendungen ist.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Hauptmerkmale {#key-features}

- **Interaktive Datumsschritte:**
  Verwenden Sie die Pfeiltasten oder Spin-Schaltflächen, um den Datumswert zu erhöhen oder zu verringern.

- **Anpassbare Schrittweite:**
  Wählen Sie, welchen Teil des Datums Sie mit `setSpinField()` ändern möchten:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Optionen sind `DAY`, `WEEK`, `MONTH` und `YEAR`.

- **Min/Max-Grenzen:**
  Unterstützt die Minimale und Maximalen erlaubten Daten mit `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**
  Vollständig kompatibel mit Masken- und Lokalisierungseinstellungen aus `MaskedDateField`.

### Beispiel: Wöchentliche Schritte konfigurieren {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dies macht jeden Drehschritt vorwärts oder rückwärts um eine Woche. 

## Stilgestaltung {#styling}

<TableBuilder name="MaskedDateField" />
