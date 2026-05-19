---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: 5bd41c7d02fb7ae0c934db0a4e2ffb60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Das `MaskedDateField` ist eine Texteingabe, die es Benutzern ermöglicht, Daten als Zahlen einzugeben und das Eingabefeld automatisch basierend auf einer definierten Maske zu formatieren, wenn das Feld den Fokus verliert. Die Maske gibt das erwartete Datumsformat an und dient sowohl der Eingabe als auch der Anzeige als Anleitung. Die Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und Wertwiederherstellung für eine konsistente, regionsspezifische Datenverarbeitung.

<!-- INTRO_END -->

## Grundlagen {#basics}

:::tip Suchen Sie ein Zeiteingabefeld?
Das `MaskedDateField` konzentriert sich ausschließlich auf **Datums**werte. Wenn Sie eine ähnliche Komponente für die Eingabe und Formatierung von **Zeit** benötigen, schauen Sie sich stattdessen das [`MaskedTimeField`](./timefield) an.
:::

Das `MaskedDateField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedDateField` unterstützt verschiedene Datumsformate, die weltweit verwendet werden und sich durch die Reihenfolge von Tag, Monat und Jahr unterscheiden. Zu den gängigen Mustern gehören:

- **Tag/Monat/Jahr** (in den meisten Teilen Europas verwendet)
- **Monat/Tag/Jahr** (in den Vereinigten Staaten verwendet)
- **Jahr/Monat/Tag** (in China, Japan und Korea verwendet; auch der ISO-Standard: `YYYY-MM-DD`)

Innerhalb dieser Formate umfassen lokale Variationen die Wahl des Separators (z. B. `-`, `/` oder `.`), ob die Jahre zwei oder vier Ziffern haben und ob einstellige Monate oder Tage mit führenden Nullen aufgefüllt werden.

Um mit dieser Vielfalt umzugehen, verwendet das `MaskedDateField` Formatindikatoren, die jeweils mit `%` beginnen, gefolgt von einem Buchstaben, der einen bestimmten Teil des Datums darstellt. Diese Indikatoren definieren, wie die Eingabe geparst wird und wie das Datum angezeigt wird.

:::tip Masken programmgesteuert anwenden
Um Daten mit derselben Maskensyntax außerhalb eines Feldes zu formatieren oder zu parsen, verwenden Sie die [`MaskDecorator`](/docs/advanced/mask-decorator) Dienstklasse.
:::

### Datumsformatindikatoren {#date-format-indicators}

| Format | Beschreibung |
| ------ | ------------ |
| `%Y`   | Jahr         |
| `%M`   | Monat        |
| `%D`   | Tag          |

### Modifizierer {#modifiers}

Modifizierer ermöglichen eine genauere Kontrolle über die Formatierung der Bestandteile des Datums:

| Modifizierer | Beschreibung               |
| ------------ | -------------------------- |
| `z`          | Nullfüllung                |
| `s`          | Kurze Textdarstellung      |
| `l`          | Lange Textdarstellung      |
| `p`          | Komprimierte Zahl          |
| `d`          | Dezimal (Standardformat)   |

Diese können kombiniert werden, um eine Vielzahl von Datumsmasken zu erstellen.

## Lokalisierung des Datumsformats {#date-format-localization}

Das `MaskedDateField` passt sich regionalen Datumsformaten an, indem es die entsprechende Locale festlegt. Dies stellt sicher, dass Daten auf eine Weise angezeigt und geparst werden, die den Erwartungen der Benutzer entspricht.

| Region        | Format     | Beispiel      |
| ------------- | ---------- | -------------- |
| Vereinigte Staaten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO-Standard  | YYYY-MM-DD | `2023-07-04` |

Um die Lokalisierung anzuwenden, verwenden Sie die Methode `setLocale()`. Diese akzeptiert ein [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) und passt automatisch sowohl Formatierung als auch Parsing an:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing-Logik {#parsing-logic}

Das `MaskedDateField` parst die Benutzereingaben basierend auf der definierten Datumsmaske. Es akzeptiert sowohl vollständige als auch verkürzte numerische Eingaben mit oder ohne Trennzeichen, was eine flexible Eingabe ermöglicht und gleichzeitig gültige Daten gewährleistet. Das Parsing-Verhalten hängt von der Formatreihenfolge ab, die durch die Maske definiert ist (z. B. `%Mz/%Dz/%Yz` für Monat/Tag/Jahr). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

Beispielsweise, sofern heute der `15. September 2012` ist, könnte jede der folgenden Eingaben wie folgt interpretiert werden:

### Beispiel Parsing-Szenarien {#example-parsing-scenarios}

| Eingabe                              | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Eine einzelne Ziffer wird stets als Tagesnummer innerhalb des aktuellen Monats interpretiert, sodass dies der 1. September 2012 wäre.                                                           | Dasselbe wie YMD                                                                     | Dasselbe wie YMD                                                                                                            |
| <div align="center">`12`</div>       | Zwei Ziffern werden immer als Tagesnummer innerhalb des aktuellen Monats interpretiert, sodass dies der 12. September 2012 wäre.                                                              | Dasselbe wie YMD                                                                     | Dasselbe wie YMD                                                                                                            |
| <div align="center">`112`</div>      | Drei Ziffern werden als einstellige Monatszahl gefolgt von einer zweiziffrigen Tageszahl interpretiert, sodass dies der 12. Januar 2012 wäre.                                                | Dasselbe wie YMD                                                                     | Drei Ziffern werden als eine eintägige Tageszahl gefolgt von einer zweiziffrigen Monatszahl interpretiert, sodass dies der 1. Dezember 2012 wäre. |
| <div align="center">`1004`</div>     | Vier Ziffern werden als MMDD interpretiert, sodass dies der 4. Oktober 2012 wäre.                                                                                                                   | Dasselbe wie YMD                                                                     | Vier Ziffern werden als DDMM interpretiert, sodass dies der 10. April 2012 wäre.                                             |
| <div align="center">`020304`</div>   | Sechs Ziffern werden als YYMMDD interpretiert, sodass dies der 4. März 2002 wäre.                                                                                                                   | Sechs Ziffern werden als MMDDYY interpretiert, sodass dies der 3. Februar 2004 wäre.           | Sechs Ziffern werden als DDMMYY interpretiert, sodass dies der 2. März 2004 wäre.                                         |
| <div align="center">`8 Ziffern`</div> | Acht Ziffern werden als YYYYMMDD interpretiert. Zum Beispiel wird `20040612` als 12. Juni 2004 interpretiert.                                                                                           | Acht Ziffern werden als MMDDYYYY interpretiert. Zum Beispiel wird `06122004` als 12. Juni 2004 interpretiert. | Acht Ziffern werden als DDMMYYYY interpretiert. Zum Beispiel wird `06122004` als 6. Dezember 2004 interpretiert.            |
| <div align="center">`12/6`</div>     | Zwei durch ein gültiges Trennzeichen getrennte Zahlen werden als MM/DD interpretiert, sodass dies der 6. Dezember 2012 wäre. <br />Hinweis: Alle Zeichen außer Buchstaben und Ziffern gelten als gültige Trennzeichen. | Dasselbe wie YMD                                                                     | Zwei durch ein Trennzeichen getrennte Zahlen werden als DD/MM interpretiert, sodass dies der 12. Juni 2012 wäre.                |
| <div align="center">`3/4/5`</div>    | 5. April 2012                                                                                                                                                                                      | 4. März 2005                                                                           | 3. April 2005                                                                                                               |


## Textuelles Datum Parsen <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standardmäßig akzeptiert das `MaskedDateField` nur numerische Eingaben für Daten. Sie können jedoch das **textuelle Datum Parsen** aktivieren, um es Benutzern zu ermöglichen, Monats- und Tagesnamen in ihre Eingabe einzugeben. Diese Funktion ist besonders nützlich, um eine natürlichere Datumsangabe zu erstellen.

Um das textuelle Parsen zu aktivieren, verwenden Sie die Methode `setTextualDateParsing()`:

```java
dateField.setTextualDateParsing(true);
```

### Ersetzung von Monatsnamen {#month-name-substitution}

Wenn das textuelle Parsen aktiviert ist, können Sie spezielle Modifizierer in Ihrer Maske verwenden, um Monatsnamen anstelle von numerischen Werten zu akzeptieren:

- **`%Ms`** - Akzeptiert kurze Monatsnamen (Jan, Feb, Mär usw.)
- **`%Ml`** - Akzeptiert lange Monatsnamen (Januar, Februar, März usw.)

Monatsnamen können in jeder Position innerhalb der Maske erscheinen, und das Feld akzeptiert dennoch numerische Eingaben als Fallback.

#### Beispiele

| Maske | Eingabe | Ergebnis |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Gültig** - Wird als 1. September 2025 geparst |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Gültig** - Wird als 1. September 2025 geparst |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Gültig** - Wird als 1. September 2025 geparst |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Gültig** - Numerische Rückfall funktioniert weiterhin |

:::info
Alle 12 Monate werden sowohl in kurzer (Jan, Feb, Mär, Apr, Mai, Jun, Jul, Aug, Sep, Okt, Nov, Dez) als auch in langer (Januar, Februar usw.) Form unterstützt.
:::
### Dekoration von Tagesnamen {#day-name-decoration}

Tage der Woche können in die Eingabe aufgenommen werden, um die Lesbarkeit zu verbessern, werden jedoch **nur dekorativ** verwendet und beim Parsen entfernt. Sie beeinflussen nicht den tatsächlichen Datumwert.

- **`%Ds`** - Akzeptiert kurze Tagesnamen (Mo, Di, Mi usw.)
- **`%Dl`** - Akzeptiert lange Tagesnamen (Montag, Dienstag, Mittwoch usw.)

:::warning Tagesnamen erfordern numerischen Tag
Wenn Sie Tagesnamene (`%Ds` oder `%Dl`) verwenden, **muss** Ihre Maske auch `%Dz` oder `%Dd` enthalten, um die tatsächliche Tageszahl anzugeben. Ohne eine numerische Tageskomponente ist die Eingabe ungültig.
:::

#### Beispiele

| Maske | Eingabe | Ergebnis |
| ---- | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Mo 09/01/25` | **Gültig** - Tagesname ist dekorativ |
| `%Dl %Mz/%Dz/%Yz` | `Montag 09/01/25` | **Gültig** - Tagesname ist dekorativ |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Di` | **Gültig** - Tagesname am Ende |
| `%Dl/%Mz/%Yz` | `Montag/09/25` | **Ungültig** - Fehlendes `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Montag/25` | **Ungültig** - Fehlendes `%Dz` |

Alle 7 Wochentage werden sowohl in kurzer (Mo, Di, Mi, Do, Fr, Sa, So) als auch in langer (Montag, Dienstag usw.) Form unterstützt.

### Zusätzliche Parsing-Regeln {#additional-parsing-rules}

Das textuelle Datum Parsen umfasst mehrere nützliche Funktionen:

- **Groß-/Kleinschreibung ignorieren:** Eingaben wie `MONDAY 09/01/25`, `monday 09/01/25` oder `Monday 09/01/25` funktionieren auf die gleiche Weise.
- **Locale-bewusst:** Monats- und Tagesnamen müssen mit der konfigurierten Sprache des Feldes übereinstimmen. Bei einer französischen Locale verwenden Sie z.B. `septembre`, nicht `September`. Englische Namen werden nicht erkannt, es sei denn, die Sprache ist auf Englisch festgelegt.
  - Französische Locale: `septembre/01/25` wird als September erkannt
  - Deutsche Locale: `Montag 09/01/25` wird mit Montag als Tagesnamen erkannt

## Minimale/maximale Einschränkungen festlegen {#setting-minmax-constraints}

Sie können den erlaubten Datumsbereich in einem `MaskedDateField` mit den Methoden `setMin()` und `setMax()` einschränken:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide Methoden akzeptieren Werte des Typs [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Eingaben außerhalb des definierten Bereichs werden als ungültig betrachtet.

## Wiederherstellung des Wertes {#restoring-the-value}

Das `MaskedDateField` umfasst eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies ist nützlich, um Benutzereingaben zurückzusetzen oder auf ein Standarddatum zurückzusetzen.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist die Standard-Wiederherstelltaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

Sie können den wiederherzustellen Wert mit `setRestoreValue()` festlegen, indem Sie eine [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) Instanz übergeben.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mithilfe von regulären Ausdrücken mit der Methode `setPattern()` anwenden:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `MM/DD/YYYY` (zwei Ziffern, Schrägstrich, zwei Ziffern, Schrägstrich, vier Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format für reguläre Ausdrücke
Das Muster muss der JavaScript RegExp-Syntax folgen, wie sie [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) dokumentiert ist.
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Datumswerte basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch dennoch manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder unverarbeitbar ist (z. B. `99/99/9999`), kann sie Musterprüfungen bestehen, aber logische Validierung fehlschlagen.
Sie sollten den Eingabewert in Ihrer Anwendungslogik immer validieren, auch wenn ein reguläres Ausdrucksmuster festgelegt ist, um sicherzustellen, dass das Datum sowohl korrekt formatiert als auch sinnvoll ist.
::::

## Datumswahlschalter {#date-picker}

Das `MaskedDateField` umfasst einen integrierten Kalenderwahlschalter, der es Benutzern ermöglicht, ein Datum visuell auszuwählen, anstatt es einzutippen. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn eine präzise Eingabe erforderlich ist.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Zugriff auf den Wahlschalter {#accessing-the-picker}

Sie können auf den Datumswahlschalter mit `getPicker()` zugreifen:

```java
DatePicker picker = dateField.getPicker();
```

### Anzeige/Verbergen des Wähler-Symbols {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Kalendersymbol neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatische Öffnungsverhalten {#auto-open-behavior}

Sie können den Wahlschalter so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z. B. klickt, Enter oder Pfeiltasten drückt):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Picker erzwingen
Um sicherzustellen, dass Benutzer nur über den Kalenderwahlschalter ein Datum auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
dateField.getPicker().setAutoOpen(true); // Öffnet den Picker bei Benutzerinteraktion
dateField.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Diese Einrichtung stellt sicher, dass alle Datumswerte über die Picker-Benutzeroberfläche eingegeben werden, was nützlich ist, wenn Sie eine strikte Formatkontrolle wünschen und Parsing-Probleme durch eingegebene Werte vermeiden möchten.
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

Der Wahlschalter kann optional Wochennummern in der Kalenderansicht anzeigen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Der `MaskedDateFieldSpinner` erweitert das [`MaskedDateField`](#basics) um Spinner-Steuerelemente, mit denen Benutzer das Datum mithilfe von Pfeiltasten oder UI-Schaltflächen erhöhen oder verringern können. Dies bietet einen geführteren Interaktionsstil, der insbesondere in Desktop-Anwendungen nützlich ist.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Hauptmerkmale {#key-features}

- **Interaktive Datumsschritte:**  
  Verwenden Sie die Pfeiltasten oder Spin-Schaltflächen, um den Datumswert zu erhöhen oder zu verringern.

- **Anpassbare Schrittgröße:**  
  Wählen Sie den Teil des Datums, den Sie ändern möchten, mit `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Optionen sind `DAY`, `WEEK`, `MONTH` und `YEAR`.

- **Min/Max-Grenzen:**  
  Erbt den Support für minimale und maximale erlaubte Daten mit `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**  
  Vollständig kompatibel mit Masken- und Lokalisierungseinstellungen aus dem `MaskedDateField`.

### Beispiel: Wöchentliche Schritte konfigurieren {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dies sorgt dafür, dass jeder Spin-Schritt das Datum um eine Woche vor- oder zurücksetzt.

## Stil {#styling}

<TableBuilder name="MaskedDateField" />
