---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: d63b5c4325ef201b54da5b78b4e66f1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Das `MaskedDateField` ist ein Texteingabefeld, das es Benutzern ermöglicht, Daten als Zahlen einzugeben. Beim Verlassen des Feldes wird die Eingabe automatisch basierend auf einer definierten Maske formatiert. Die Maske gibt das erwartete Datumsformat vor und dient sowohl der Eingabe als auch der Anzeige als Leitfaden. Die Komponente unterstützt flexible Analyse, Validierung, Lokalisierung und Wiederherstellung von Werten für eine konsistente, regionsspezifische Datenverarbeitung.

<!-- INTRO_END -->

:::tip Suchen Sie ein Zeit-Eingabefeld?
Das `MaskedDateField` konzentriert sich ausschließlich auf **Datum**-Werte. Wenn Sie eine ähnliche Komponente zum Eingeben und Formatieren von **Zeit** benötigen, sehen Sie sich stattdessen das [`MaskedTimeField`](/docs/components/fields/masked/timefield) an.
:::

Das `MaskedDateField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedDateField` unterstützt mehrere Datumsformate, die weltweit verwendet werden, die sich nach der Reihenfolge von Tag, Monat und Jahr richten. Zu den gängigen Mustern gehören:

- **Tag/Monat/Jahr** (verwendet in den meisten europäischen Ländern)
- **Monat/Tag/Jahr** (verwendet in den Vereinigten Staaten)
- **Jahr/Monat/Tag** (verwendet in China, Japan und Korea; auch ISO-Standard: `YYYY-MM-DD`)

Innerhalb dieser Formate umfassen lokale Variationen die Wahl des Trennzeichens (z.B. `-`, `/` oder `.`), ob Jahre zwei oder vier Ziffern haben, und ob einstellige Monate oder Tage mit führenden Nullen aufgefüllt sind.

Um mit dieser Vielfalt umzugehen, verwendet das `MaskedDateField` Formatindikatoren, die jeweils mit `%` beginnen, gefolgt von einem Buchstaben, der einen bestimmten Teil des Datums repräsentiert. Diese Indikatoren definieren, wie die Eingabe analysiert wird und wie das Datum angezeigt wird.

:::tip Masken programmgesteuert anwenden
Um Daten mit derselben Maskensyntax außerhalb eines Feldes zu formatieren oder zu analysieren, verwenden Sie die Utility-Klasse [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Datumsformatindikatoren {#date-format-indicators}

| Format | Beschreibung |
| ------ | ------------ |
| `%Y`   | Jahr         |
| `%M`   | Monat        |
| `%D`   | Tag          |

### Modifizierer {#modifiers}

Modifizierer ermöglichen eine genauere Steuerung darüber, wie die Komponenten des Datums formatiert werden:

| Modifizierer | Beschreibung                  |
| ------------ | ------------------------------ |
| `z`          | Nullauffüllung                |
| `s`          | Kurze Textdarstellung         |
| `l`          | Lange Textdarstellung         |
| `p`          | Komprimierte Zahl             |
| `d`          | Dezimal (Standardformat)      |

Diese können kombiniert werden, um eine Vielzahl von Datumsmasken zu erstellen.

## Lokalisierung des Datumsformats {#date-format-localization}

Das `MaskedDateField` passt sich an regionale Datumsformate an, indem das entsprechende Gebietsschema eingestellt wird. Dies stellt sicher, dass Daten auf eine Weise angezeigt und analysiert werden, die den Erwartungen der Benutzer entspricht.

| Region        | Format     | Beispiel      |
| ------------- | ---------- | -------------- |
| Vereinigte Staaten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO-Standard  | YYYY-MM-DD | `2023-07-04` |

Um die Lokalisierung anzuwenden, verwenden Sie die Methode `setLocale()`. Sie akzeptiert ein [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) und passt sowohl das Format als auch die Analyse automatisch an:

```java
dateField.setLocale(Locale.FRANCE);
```

## Analyse-Logik {#parsing-logic}

Das `MaskedDateField` analysiert die Benutzereingabe basierend auf der definierten Datumsmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, was eine flexible Eingabe ermöglicht und gleichzeitig gültige Daten sicherstellt. Das Analyseverhalten hängt von der im Muster definierten Reihenfolge ab (z.B. `%Mz/%Dz/%Yz` für Monat/Tag/Jahr). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

Zum Beispiel, vorausgesetzt, heute ist der `15. September 2012`, so würden verschiedene Eingaben wie folgt interpretiert werden:

### Beispielhafte Analyse-Szenarien {#example-parsing-scenarios}

| Eingabe                                | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| -------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>          | Eine einzelne Ziffer wird immer als Tag innerhalb des aktuellen Monats interpretiert, also wäre das der 1. September 2012.                                                                        | Gleich wie YMD                                                                         | Gleich wie YMD                                                                                                                  |
| <div align="center">`12`</div>         | Zwei Ziffern werden immer als Tag innerhalb des aktuellen Monats interpretiert, also wäre das der 12. September 2012.                                                                          | Gleich wie YMD                                                                         | Gleich wie YMD                                                                                                                  |
| <div align="center">`112`</div>        | Drei Ziffern werden als eine einstellige Monatszahl gefolgt von einer zweistelligen Tageszahl interpretiert, also wäre das der 12. Januar 2012.                                                | Gleich wie YMD                                                                         | Drei Ziffern werden als eine einstellige Tageszahl gefolgt von einer zweistelligen Monatszahl interpretiert, also wäre das der 1. Dezember 2012. |
| <div align="center">`1004`</div>       | Vier Ziffern werden als MMDD interpretiert, also wäre das der 4. Oktober 2012.                                                                                                                    | Gleich wie YMD                                                                         | Vier Ziffern werden als DDMM interpretiert, also wäre das der 10. April 2012.                                                         |
| <div align="center">`020304`</div>     | Sechs Ziffern werden als YYMMDD interpretiert, also wäre das der 4. März 2002.                                                                                                                  | Sechs Ziffern werden als MMDDYY interpretiert, also wäre das der 3. Februar 2004.            | Sechs Ziffern werden als DDMMYY interpretiert, also wäre das der 2. März 2004.                                                         |
| <div align="center">`8 Ziffern`</div>  | Acht Ziffern werden als YYYYMMDD interpretiert. Zum Beispiel wird `20040612` als der 12. Juni 2004 interpretiert.                                                                                 | Acht Ziffern werden als MMDDYYYY interpretiert. Zum Beispiel wird `06122004` als der 12. Juni 2004 interpretiert. | Acht Ziffern werden als DDMMYYYY interpretiert. Zum Beispiel wird `06122004` als der 6. Dezember 2004 interpretiert.                                        |
| <div align="center">`12/6`</div>       | Zwei Zahlen, die durch ein gültiges Trennzeichen getrennt sind, werden als MM/DD interpretiert, also wäre das der 6. Dezember 2012. <br />Hinweis: Alle Zeichen außer Buchstaben und Ziffern werden als gültige Trennzeichen betrachtet. | Gleich wie YMD                                                                         | Zwei Zahlen, die durch ein Trennzeichen getrennt sind, werden als DD/MM interpretiert, also wäre das der 12. Juni 2012.                               |
| <div align="center">`3/4/5`</div>      | 5. April 2012                                                                                                                                                                                      | 4. März 2005                                                                       | 3. April 2005                                                                                                                 |

## Textuelle Datumsanalyse <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standardmäßig akzeptiert das `MaskedDateField` nur numerische Eingaben für Daten. Sie können jedoch die **textuelle Datumsanalyse** aktivieren, um Benutzern zu ermöglichen, Monats- und Tagesnamen einzugeben. Diese Funktion ist besonders nützlich für eine natürliche Dateneingabe.

Um die textuelle Analyse zu aktivieren, verwenden Sie die Methode `setTextualDateParsing()`:

```java
dateField.setTextualDateParsing(true);
```

### Ersetzung von Monatsnamen {#month-name-substitution}

Wenn die textuelle Analyse aktiviert ist, können Sie spezielle Modifizierer in Ihrer Maske verwenden, um Monatsnamen anstelle von numerischen Werten zu akzeptieren:

- **`%Ms`** - Akzeptiert kurze Monatsnamen (Jan, Feb, Mär, etc.)
- **`%Ml`** - Akzeptiert lange Monatsnamen (Januar, Februar, März, etc.)

Monatsnamen können an jeder Stelle innerhalb der Maske auftauchen, und das Feld wird weiterhin auch numerische Eingaben als Fallback akzeptieren.

#### Beispiele {#examples}

| Maske | Eingabe | Ergebnis |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Gültig** - Wird als 1. September 2025 interpretiert |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Gültig** - Wird als 1. September 2025 interpretiert |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Gültig** - Wird als 1. September 2025 interpretiert |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Gültig** - Numerischer Fallback funktioniert weiterhin |

:::info
Alle 12 Monate werden sowohl in kurzer (Jan, Feb, Mär, Apr, Mai, Jun, Jul, Aug, Sep, Okt, Nov, Dez) als auch in langer (Januar, Februar usw.) Form unterstützt.
:::
### Dekoration von Tagesnamen {#day-name-decoration}

Tage der Woche können zur besseren Lesbarkeit in die Eingabe einbezogen werden, sind jedoch **nur dekorativ** und werden während der Analyse entfernt. Sie beeinflussen nicht den tatsächlichen Datumswert.

- **`%Ds`** - Akzeptiert kurze Tagesnamen (Mon, Die, Mit, etc.)
- **`%Dl`** - Akzeptiert lange Tagesnamen (Montag, Dienstag, Mittwoch, etc.)

:::warning Tagesnamen erfordern eine numerische Tageszahl
Wenn Sie Tagesnamen verwenden (`%Ds` oder `%Dl`), muss Ihre Maske **auch** `%Dz` oder `%Dd` enthalten, um die tatsächliche Tageszahl anzugeben. Ohne eine numerische Tageskomponente ist die Eingabe ungültig.
:::

#### Beispiele {#examples-1}

| Maske | Eingabe | Ergebnis |
| ---- | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Mon 09/01/25` | **Gültig** - Tagesname ist dekorativ |
| `%Dl %Mz/%Dz/%Yz` | `Montag 09/01/25` | **Gültig** - Tagesname ist dekorativ |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Tue` | **Gültig** - Tagesname am Ende |
| `%Dl/%Mz/%Yz` | `Montag/09/25` | **Ungültig** - Fehlende `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Montag/25` | **Ungültig** - Fehlende `%Dz` |

Alle 7 Wochentage werden sowohl in kurzer (Mon, Die, Mit, Don, Fre, Sam, Son) als auch in langer (Montag, Dienstag usw.) Form unterstützt.

### Weitere Analyse-Regeln {#additional-parsing-rules}

Die textuelle Datumsanalyse umfasst mehrere nützliche Funktionen:

- **Groß-/Kleinschreibung unabhängig:** Eingaben wie `MONDAY 09/01/25`, `monday 09/01/25` oder `Monday 09/01/25` funktionieren alle gleich.
- **Gebietsschema-bewusst:** Monats- und Tagesnamen müssen dem konfigurierten Gebietsschema des Feldes entsprechen. Beispielsweise verwenden Sie bei einem französischen Gebietsschema `septembre`, nicht `September`. Englischsprachige Namen werden nicht erkannt, es sei denn, das Gebietsschema ist auf Englisch eingestellt.
  - Französisches Gebietsschema: `septembre/01/25` wird als September erkannt
  - Deutsches Gebietsschema: `Montag 09/01/25` wird mit Montag als Tagesnamen erkannt

## Festlegen von Min-/Max-Beschränkungen {#setting-minmax-constraints}

Sie können den zulässigen Datumsbereich in einem `MaskedDateField` mithilfe der Methoden `setMin()` und `setMax()` einschränken:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Eingaben außerhalb des definierten Bereichs werden als ungültig betrachtet.

## Wert wiederherstellen {#restoring-the-value}

Das `MaskedDateField` enthält eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies ist nützlich, um Benutzereingaben zurückzusetzen oder auf ein standardmäßiges Datum zurückzusetzen.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufrufen von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die Standard-Wiederherstelltaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

Sie können den Wert zur Wiederherstellung mit `setRestoreValue()` festlegen und eine [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html)-Instanz übergeben.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mit regulären Ausdrücken über die Methode `setPattern()` anwenden:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dieses Muster stellt sicher, dass nur Werte im Format `MM/DD/YYYY` (zwei Ziffern, Schrägstrich, zwei Ziffern, Schrägstrich, vier Ziffern) als gültig betrachtet werden.

:::tip Format regulärer Ausdrücke
Das Muster muss der JavaScript-RegExp-Syntax folgen, die hier dokumentiert ist: [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Datumseingaben basierend auf der aktuellen Maske zu analysieren und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder nicht analysierbar ist (z.B. `99/99/9999`), kann sie die Musterprüfungen bestehen, aber bei der logischen Validierung scheitern.
Sie sollten den Eingabewert immer in Ihrer Anwendungslogik validieren, auch wenn ein reguläres Ausdrucksmuster festgelegt ist, um sicherzustellen, dass das Datum sowohl korrekt formatiert als auch sinnvoll ist.
::::

## Datumsauswahlfeld {#date-picker}

Das `MaskedDateField` enthält einen integrierten Kalenderauswahl, der es Benutzern ermöglicht, ein Datum visuell auszuwählen, anstatt es einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn präzise Eingaben erforderlich sind.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Zugriff auf den Picker {#accessing-the-picker}

Sie können auf den Datumsauswahl mit `getPicker()` zugreifen:

```java
DatePicker picker = dateField.getPicker();
```

### Picker-Symbol anzeigen/ausblenden {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Kalendersymbol neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatische Öffnungsfunktion {#auto-open-behavior}

Sie können den Picker so konfigurieren, dass er sich automatisch öffnet, wenn der Benutzer mit dem Feld interagiert (z.B. klickt, die Eingabetaste oder die Pfeiltasten drückt):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Picker durchsetzen
Um sicherzustellen, dass Benutzer ein Datum ausschließlich über den Kalenderpicker auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
dateField.getPicker().setAutoOpen(true); // Öffnet den Picker bei Benutzerinteraktion
dateField.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Dieses Setup garantiert, dass alle Datumseingaben über die Picker-Benutzeroberfläche erfolgen, was nützlich ist, wenn Sie eine strenge Formatkontrolle durchführen und Parsing-Probleme beim Tippen von Eingaben vermeiden möchten.
:::

### Kalender manuell öffnen {#manually-open-the-calendar}

Um den Kalender programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // das gleiche wie open()
```

### Wochen im Kalender anzeigen {#show-weeks-in-the-calendar}

Der Picker kann optional die Wochennummern in der Kalenderansicht anzeigen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Das `MaskedDateFieldSpinner` erweitert das `MaskedDateField` um Spinner-Steuerelemente, die es Benutzern ermöglichen, das Datum mithilfe von Pfeiltasten oder UI-Schaltflächen zu erhöhen oder zu verringern. Es bietet einen geführten Interaktionsstil, der besonders in Desktop-Anwendungen nützlich ist.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Hauptmerkmale {#key-features}

- **Interaktives Datumsschritt:** Verwenden Sie die Pfeiltasten oder Spin-Tasten, um den Datumswert zu erhöhen oder zu verringern.

- **Anpassbare Schrittgröße:** Wählen Sie aus, welchen Teil des Datums Sie ändern möchten, indem Sie `setSpinField()` verwenden:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Optionen sind `DAY`, `WEEK`, `MONTH` und `YEAR`.

- **Min-/Max-Grenzen:** Unterstützt die Minimale und Maximale zulässige Daten mithilfe von `setMin()` und `setMax()`.

- **Formatierte Ausgabe:** Vollständig kompatibel mit Masken und Lokalisierungseinstellungen vom `MaskedDateField`.

### Beispiel: Wöchentliche Schritte konfigurieren {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dies lässt jeden Schritt nach vorne oder hinten um eine Woche im Datum laufen.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
