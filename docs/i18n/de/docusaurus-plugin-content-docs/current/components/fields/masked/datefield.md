---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: b327e838739be59acec7bfbedc3bb027
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Das `MaskedDateField` ist ein Texteingabefeld, das Benutzern ermöglicht, Daten als Zahlen einzugeben und die Eingabe automatisch basierend auf einer definierten Maske zu formatieren, wenn das Feld den Fokus verliert. Die Maske gibt das erwartete Datumsformat an und unterstützt sowohl die Eingabe als auch die Anzeige. Das Component unterstützt flexibles Parsen, Validierung, Lokalisierung und die Wiederherstellung von Werten für eine konsistente, regionsspezifische Datenverarbeitung.

<!-- INTRO_END -->

:::tip Suchen Sie nach einer Zeiteingabe?
Das `MaskedDateField` konzentriert sich ausschließlich auf **Datum**-Werte. Wenn Sie ein ähnliches Element zur Eingabe und Formatierung von **Zeit** benötigen, schauen Sie sich stattdessen das [`MaskedTimeField`](/docs/components/fields/masked/timefield) an.
:::

Das `MaskedDateField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskenregeln {#mask-rules}

Das `MaskedDateField` unterstützt verschiedene Datumsformate, die weltweit verwendet werden und sich nach der Reihenfolge von Tag, Monat und Jahr unterscheiden. Zu den gängigen Mustern gehören:

- **Tag/Monat/Jahr** (in den meisten europäischen Ländern verwendet)
- **Monat/Tag/Jahr** (in den Vereinigten Staaten verwendet)
- **Jahr/Monat/Tag** (in China, Japan und Korea verwendet; auch der ISO-Standard: `YYYY-MM-DD`)

Innerhalb dieser Formate gibt es lokale Variationen, einschließlich der Wahl des Trennzeichens (z.B. `-`, `/` oder `.`), ob Jahre zwei oder vier Ziffern haben und ob einstellige Monate oder Tage mit führenden Nullen aufgefüllt werden.

Um mit dieser Vielfalt umzugehen, verwendet das `MaskedDateField` Formatindikatoren, die jeweils mit `%` beginnen, gefolgt von einem Buchstaben, der einen bestimmten Teil des Datums darstellt. Diese Indikatoren definieren, wie die Eingabe geparst und wie das Datum angezeigt wird.

:::tip Masken programmatisch anwenden
Um Daten mit derselben Maskensyntax außerhalb eines Feldes zu formatieren oder zu parsen, verwenden Sie die Utility-Klasse [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Datumsformatindikatoren {#date-format-indicators}

| Format | Beschreibung |
| ------ | ------------ |
| `%Y`   | Jahr         |
| `%M`   | Monat        |
| `%D`   | Tag          |

### Modifikatoren {#modifiers}

Modifikatoren ermöglichen eine genauere Kontrolle darüber, wie die Komponenten des Datums formatiert sind:

| Modifikator | Beschreibung               |
| ----------- | -------------------------- |
| `z`         | Nullauffüllung            |
| `s`         | Kurze Textdarstellung      |
| `l`         | Lange Textdarstellung      |
| `p`         | Gepackte Zahl             |
| `d`         | Dezimal (Standardformat)   |

Diese können kombiniert werden, um eine Vielzahl von Datums-Masken zu erstellen.

## Lokalisierung des Datumsformats {#date-format-localization}

Das `MaskedDateField` passt sich an regionale Datumsformate an, indem es die entsprechende Locale festlegt. Dies stellt sicher, dass Daten in einer Weise angezeigt und geparst werden, die den Erwartungen der Benutzer entspricht.

| Region        | Format     | Beispiel      |
| ------------- | ---------- | ------------- |
| Vereinigte Staaten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO-Standard  | YYYY-MM-DD | `2023-07-04` |

Um die Lokalisierung anzuwenden, verwenden Sie die Methode `setLocale()`. Sie akzeptiert eine [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) und passt automatisch sowohl die Formatierung als auch das Parsen an:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing-Logik {#parsing-logic}

Das `MaskedDateField` parst Benutzereingaben basierend auf der definierten Datumsmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, was eine flexible Eingabe ermöglicht, während gültige Daten sichergestellt werden.
Das Parsing-Verhalten hängt von der im Muster definierten Formatreihenfolge ab (z.B. `%Mz/%Dz/%Yz` für Monat/Tag/Jahr). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

Beispielsweise, wenn heute der `15. September 2012` ist, würde dies so interpretiert werden:

### Beispielhafte Parsing-Szenarien {#example-parsing-scenarios}

| Eingabe                             | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>       | Eine einzelne Ziffer wird immer als Tagesnummer innerhalb des aktuellen Monats interpretiert, also wäre dies der 1. September 2012.                                                                  | Gleich wie YMD                                                                       | Gleich wie YMD                                                                                                              |
| <div align="center">`12`</div>      | Zwei Ziffern werden immer als Tagesnummer innerhalb des aktuellen Monats interpretiert, also wäre dies der 12. September 2012.                                                                     | Gleich wie YMD                                                                       | Gleich wie YMD                                                                                                              |
| <div align="center">`112`</div>     | Drei Ziffern werden als eine einstellige Monatsnummer gefolgt von einer zweistelligen Tagesnummer interpretiert, also wäre dies der 12. Januar 2012.                                                | Gleich wie YMD                                                                       | Drei Ziffern werden als eine einstellige Tagesnummer gefolgt von einer zweistelligen Monatsnummer interpretiert, also wäre dies der 1. Dezember 2012.  |
| <div align="center">`1004`</div>    | Vier Ziffern werden als MMDD interpretiert, also wäre dies der 4. Oktober 2012.                                                                                                                   | Gleich wie YMD                                                                       | Vier Ziffern werden als DDMM interpretiert, also wäre dies der 10. April 2012.                                             |
| <div align="center">`020304`</div>  | Sechs Ziffern werden als YYMMDD interpretiert, also wäre dies der 4. März 2002.                                                                                                                  | Sechs Ziffern werden als MMDDYY interpretiert, also wäre dies der 3. Februar 2004.    | Sechs Ziffern werden als DDMMYY interpretiert, also wäre dies der 2. März 2004.                                           |
| <div align="center">`8 Ziffern`</div>| Acht Ziffern werden als YYYYMMDD interpretiert. Beispielsweise ist `20040612` der 12. Juni 2004.                                                                                                   | Acht Ziffern werden als MMDDYYYY interpretiert. Beispielsweise ist `06122004` der 12. Juni 2004. | Acht Ziffern werden als DDMMYYYY interpretiert. Beispielsweise ist `06122004` der 6. Dezember 2004.                       |
| <div align="center">`12/6`</div>    | Zwei durch ein gültiges Trennzeichen getrennte Zahlen werden als MM/DD interpretiert, also wäre dies der 6. Dezember 2012. <br />Hinweis: Alle Zeichen außer Buchstaben und Ziffern gelten als gültige Trennzeichen. | Gleich wie YMD                                                                       | Zwei durch ein Trennzeichen getrennte Zahlen werden als DD/MM interpretiert, also wäre dies der 12. Juni 2012.             |
| <div align="center">`3/4/5`</div>    | 5. April 2012                                                                                                                                                                                        | 4. März 2005                                                                        | 3. April 2005                                                                                                              |


## Textuelles Datum Parsing <DocChip chip='since' label='25.10' /> {#textual-date-parsing}

Standardmäßig akzeptiert das `MaskedDateField` nur numerische Eingaben für Daten. Sie können jedoch **textuelles Datum Parsing** aktivieren, um Benutzern zu ermöglichen, Monats- und Tagesnamen einzugeben. Diese Funktion ist besonders nützlich, um eine natürlicheren Dateneingabe zu ermöglichen.

Um textuelles Parsing zu aktivieren, verwenden Sie die Methode `setTextualDateParsing()`:

```java
dateField.setTextualDateParsing(true);
```

### Monatname-Ersetzung {#month-name-substitution}

Wenn textuelles Parsing aktiviert ist, können Sie spezielle Modifikatoren in Ihrer Maske verwenden, um Monatsnamen anstelle von numerischen Werten zu akzeptieren:

- **`%Ms`** - Akzeptiert kurze Monatsnamen (Jan, Feb, Mär, etc.)
- **`%Ml`** - Akzeptiert lange Monatsnamen (Januar, Februar, März, etc.)

Monatsnamen können in jeder Position innerhalb der Maske erscheinen, und das Feld akzeptiert weiterhin numerische Eingaben als Fallback.

#### Beispiele {#examples}

| Maske              | Eingabe           | Ergebnis                            |
| ------------------ | ----------------- | ----------------------------------- |
| `%Ms/%Dz/%Yz`      | `Sep/01/25`       | **Gültig** - Parst als 1. September 2025 |
| `%Ml/%Dz/%Yz`      | `September/01/25` | **Gültig** - Parst als 1. September 2025 |
| `%Dz/%Ml/%Yz`      | `01/September/25` | **Gültig** - Parst als 1. September 2025 |
| `%Mz/%Dz/%Yz`      | `09/01/25`        | **Gültig** - Numerischer Fallback funktioniert weiterhin |

:::info
Alle 12 Monate werden in kurzer (Jan, Feb, Mär, Apr, Mai, Jun, Jul, Aug, Sep, Okt, Nov, Dez) und langer (Januar, Februar, etc.) Form unterstützt.
:::
### Dekoration des Tagesnamens {#day-name-decoration}

Tagnamen können in die Eingabe aufgenommen werden, um die Lesbarkeit zu verbessern, sind jedoch **nur dekorativ** und werden während des Parsings entfernt. Sie haben keinen Einfluss auf den tatsächlichen Datumswert.

- **`%Ds`** - Akzeptiert kurze Wochentagnamen (Mo, Di, Mi, etc.)
- **`%Dl`** - Akzeptiert lange Wochentagnamen (Montag, Dienstag, Mittwoch, etc.)

:::warning Wochentagnamen benötigen einen numerischen Tag
Wenn Sie Wochentagnamen (`%Ds` oder `%Dl`) verwenden, muss Ihre Maske **außerdem** `%Dz` oder `%Dd` enthalten, um die tatsächliche Tagesnummer anzugeben. Ohne eine numerische Tageskomponente ist die Eingabe ungültig.
:::

#### Beispiele {#examples-1}

| Maske               | Eingabe             | Ergebnis                         |
| ------------------- | ------------------- | -------------------------------- |
| `%Ds %Mz/%Dz/%Yz`   | `Mo 09/01/25`       | **Gültig** - Tagname ist dekorativ |
| `%Dl %Mz/%Dz/%Yz`   | `Montag 09/01/25`   | **Gültig** - Tagname ist dekorativ |
| `%Mz/%Dz/%Yz %Ds`   | `09/01/25 Di`       | **Gültig** - Tagname am Ende     |
| `%Dl/%Mz/%Yz`       | `Montag/09/25`      | **Ungültig** - Fehlendes `%Dz`   |
| `%Mz/%Dl/%Yz`       | `09/Montag/25`      | **Ungültig** - Fehlendes `%Dz`   |

Alle 7 Wochentage sind sowohl in kurzer (Mo, Di, Mi, Do, Fr, Sa, So) als auch in langer (Montag, Dienstag, etc.) Form unterstützt.

### Zusätzliche Parsing-Regeln {#additional-parsing-rules}

Textuelles Datum Parsing umfasst mehrere hilfreiche Funktionen:

- **Groß- und Kleinschreibung ignoriert:** Eingaben wie `MONDAY 09/01/25`, `monday 09/01/25` oder `Monday 09/01/25` funktionieren alle gleich.
- **Locale-bewusst:** Monats- und Tagesnamen müssen mit der konfigurierten Locale des Feldes übereinstimmen. Beispielsweise verwenden Sie bei französischer Locale `septembre` und nicht `September`. Englische Namen werden nicht erkannt, es sei denn, die Locale wird auf Englisch gesetzt.
  - Französische Locale: `septembre/01/25` wird als September erkannt
  - Deutsche Locale: `Montag 09/01/25` wird mit Montag als Tagesnamen erkannt

## Min-/Max-Beschränkungen festlegen {#setting-minmax-constraints}

Sie können den erlaubten Datumsbereich in einem `MaskedDateField` mit den Methoden `setMin()` und `setMax()` einschränken:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide Methoden akzeptieren Werte des Typs [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Eingaben außerhalb des definierten Bereichs gelten als ungültig.

## Wert wiederherstellen {#restoring-the-value}

Das `MaskedDateField` umfasst eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies ist nützlich, um Benutzereingaben zurückzusetzen oder auf ein Standarddatum zurückzusetzen.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Möglichkeiten, den Wert wiederherzustellen {#ways-to-restore-the-value}

- **Programmgesteuert**, indem `restoreValue()` aufgerufen wird
- **Per Tastatur**, indem <kbd>ESC</kbd> gedrückt wird (dies ist die Standardwiederherstellungstaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

Sie können den Wiederherstellungswert mit `setRestoreValue()` festlegen, indem Sie eine [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) Instanz übergeben.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mit regulären Ausdrücken mithilfe der Methode `setPattern()` anwenden:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dieses Muster stellt sicher, dass nur Werte im Format `MM/DD/YYYY` (zwei Ziffern, Schrägstrich, zwei Ziffern, Schrägstrich, vier Ziffern) als gültig betrachtet werden.

:::tip Format der regulären Ausdrücke
Das Muster muss der JavaScript RegExp-Syntax folgen, wie hier dokumentiert [https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Datumseingaben basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch falsch oder nicht parsiert ist (z.B. `99/99/9999`), kann sie Musterprüfungen bestehen, aber bei der logischen Validierung fehlschlagen.
Sie sollten den Eingabewert in Ihrer Anwendungslogik immer validieren, selbst wenn ein reguläres Ausdrucksmuster gesetzt ist, um sicherzustellen, dass das Datum sowohl korrekt formatiert als auch sinnvoll ist.
::::

## Datumsauswahl {#date-picker}

Das `MaskedDateField` umfasst einen integrierten Kalenderauswahl, der es Benutzern ermöglicht, ein Datum visuell auszuwählen, anstatt es einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technisches Publikum oder wenn präzise Eingaben erforderlich sind.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Zugriff auf den Wähler {#accessing-the-picker}

Sie können auf den Datumsauswahl zugreifen, indem Sie `getPicker()` verwenden:

```java
DatePicker picker = dateField.getPicker();
```

### Kalenderikone anzeigen/ausblenden {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Kalendersymbol neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatische Öffnungsverhalten {#auto-open-behavior}

Sie können konfigurieren, dass der Wähler automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z.B. klickt, die Eingabetaste oder Pfeiltasten drückt):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Wähler erzwingen
Um sicherzustellen, dass Benutzer nur ein Datum über den Kalenderwähler auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
dateField.getPicker().setAutoOpen(true); // Öffnet den Wähler bei Benutzerinteraktion
dateField.setAllowCustomValue(false);    // Deaktiviert manuelle Texteingabe
```

Diese Konfiguration garantiert, dass alle Datumseingaben über die Benutzeroberfläche des Wählers kommen, was nützlich ist, wenn Sie strenge Formatkontrolle wünschen und Parsing-Probleme durch eingegebene Werte beseitigen möchten.
:::

### Kalender manuell öffnen {#manually-open-the-calendar}

Um den Kalender programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // dasselbe wie open()
```

### Wochen im Kalender anzeigen {#show-weeks-in-the-calendar}

Der Wähler kann optional die Wochennummern in der Kalenderansicht anzeigen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Das `MaskedDateFieldSpinner` erweitert das `MaskedDateField`, indem es Spinner-Steuerelemente hinzufügt, mit denen Benutzer das Datum mit den Pfeiltasten oder UI-Schaltflächen erhöhen oder verringern können. Es bietet eine geführte Interaktion, die insbesondere in Desktop-Anwendungen nützlich ist.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Hauptmerkmale {#key-features}

- **Interaktive Datumsveränderung:**
  Verwenden Sie die Pfeiltasten oder Spin-Tasten, um den Datumswert zu erhöhen oder zu verringern.

- **Anpassbare Schrittgröße:**
  Wählen Sie, welchen Teil des Datums Sie ändern möchten, mit `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Optionen sind `DAY`, `WEEK`, `MONTH` und `YEAR`.

- **Min/Max-Grenzen:**
  Unterstützt auch minimale und maximale erlaubte Daten mithilfe von `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**
  Vollständig kompatibel mit Masken und Lokalisierungseinstellungen des `MaskedDateField`.

### Beispiel: Wöchentliche Schritte konfigurieren {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dies ermöglicht es, dass jeder Schritt beim Steuern das Datum um eine Woche vor- oder zurücksetzt.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
