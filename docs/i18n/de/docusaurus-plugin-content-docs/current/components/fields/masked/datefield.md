---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: 6c75156564c20c2d451ebe7046213c37
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Das `MaskedDateField` ist ein Texteingabefeld, das es Benutzern ermöglicht, Daten als Zahlen einzugeben und die Eingabe automatisch basierend auf einer definierten Maske zu formatieren, wenn das Feld den Fokus verliert. Die Maske gibt das erwartete Datumsformat an und dient sowohl der Eingabe als auch der Anzeige als Leitfaden. Die Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und die Wiederherstellung von Werten für eine konsistente, regionsspezifische Datenverarbeitung.

<!-- INTRO_END -->

## Grundlagen {#basics}

:::tip Suchen Sie nach einer Zeiteingabe?
Das `MaskedDateField` konzentriert sich ausschließlich auf **Datums**werte. Wenn Sie eine ähnliche Komponente für die Eingabe und Formatierung von **Zeit** benötigen, sehen Sie sich stattdessen das [`MaskedTimeField`](./timefield) an.
:::

Das `MaskedDateField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskenregeln {#mask-rules}

Das `MaskedDateField` unterstützt verschiedene Datumsformate, die weltweit verwendet werden und sich in der Reihenfolge von Tag, Monat und Jahr unterscheiden. Zu den gängigen Mustern gehören:

- **Tag/Monat/Jahr** (in den meisten Teilen Europas verwendet)
- **Monat/Tag/Jahr** (in den Vereinigten Staaten verwendet)
- **Jahr/Monat/Tag** (in China, Japan und Korea verwendet; auch der ISO-Standard: `YYYY-MM-DD`)

Innerhalb dieser Formate beinhalten lokale Variationen die Wahl des Trennzeichens (z. B. `-`, `/` oder `.`), ob Jahre zwei oder vier Ziffern haben und ob einstellige Monate oder Tage mit führenden Nullen versehen sind.

Um mit dieser Vielfalt umzugehen, verwendet das `MaskedDateField` Formatindikatoren, die jeweils mit `%` beginnen, gefolgt von einem Buchstaben, der ein bestimmtes Datumsteil repräsentiert. Diese Indikatoren definieren, wie Eingaben geparst werden und wie das Datum angezeigt wird.

:::tip Masken programmgesteuert anwenden
Um Daten mit derselben Maskensyntax außerhalb eines Feldes zu formatieren oder zu parsen, verwenden Sie die [`MaskDecorator`](/docs/advanced/mask-decorator) Dienstklasse.
:::

### Datumsformatindikatoren {#date-format-indicators}

| Format | Beschreibung |
| ------ | ----------- |
| `%Y`   | Jahr        |
| `%M`   | Monat       |
| `%D`   | Tag         |

### Modifikatoren {#modifiers}

Modifikatoren ermöglichen eine genauere Kontrolle darüber, wie die Komponenten des Datums formatiert werden:

| Modifikator | Beschreibung               |
| ----------- | ------------------------- |
| `z`         | Nullauffüllung            |
| `s`         | Kurze Textdarstellung     |
| `l`         | Lange Textdarstellung      |
| `p`         | Gepackte Zahl             |
| `d`         | Dezimal (Standardformat)  |

Diese können kombiniert werden, um eine Vielzahl von Datumsmaske zu erstellen.

## Lokalisierung des Datumsformats {#date-format-localization}

Das `MaskedDateField` passt sich regionalen Datumsformaten an, indem es die entsprechende Spracheinstellung festlegt. Dadurch wird sichergestellt, dass Daten in einer Weise angezeigt und geparst werden, die den Erwartungen der Benutzer entspricht.

| Region        | Format     | Beispiel      |
| ------------- | ---------- | ------------ |
| Vereinigte Staaten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO Standard  | YYYY-MM-DD | `2023-07-04` |

Um die Lokalisierung anzuwenden, verwenden Sie die Methode `setLocale()`. Diese akzeptiert eine [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) und passt sowohl die Formatierung als auch das Parsen automatisch an:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing-Logik {#parsing-logic}

Das `MaskedDateField` parsed Eingaben des Benutzers basierend auf der definierten Datumsmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, sodass eine flexible Eingabe ermöglicht wird, während sichergestellt wird, dass die Daten gültig sind.
Das Parsing-Verhalten hängt von der im Masken definierten Formatreihenfolge ab (z. B. `%Mz/%Dz/%Yz` für Monat/Tag/Jahr). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

Beispielsweise, wenn heute der `15. September 2012` ist, würden verschiedene Eingaben wie folgt interpretiert werden:

### Beispiel-Parsingszenarien {#example-parsing-scenarios}

| Eingabe                                | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| -------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Eine einzelne Ziffer wird immer als Tag im aktuellen Monat interpretiert, also wäre dies der 1. September 2012.                                                                                 | Dasselbe wie YMD                                                                         | Dasselbe wie YMD                                                                                                                  |
| <div align="center">`12`</div>       | Zwei Ziffern werden immer als Tag im aktuellen Monat interpretiert, also wäre dies der 12. September 2012.                                                                                   | Dasselbe wie YMD                                                                         | Dasselbe wie YMD                                                                                                                  |
| <div align="center">`112`</div>      | Drei Ziffern werden als 1-stellige Monatsnummer gefolgt von einer 2-stelligen Tagesnummer interpretiert, also wäre dies der 12. Januar 2012.                                                                            | Dasselbe wie YMD                                                                         | Drei Ziffern werden als 1-stellige Tagesnummer gefolgt von einer 2-stelligen Monatsnummer interpretiert, also wäre dies der 1. Dezember 2012. |
| <div align="center">`1004`</div>     | Vier Ziffern werden als MMDD interpretiert, also wäre dies der 4. Oktober 2012.                                                                                                                             | Dasselbe wie YMD                                                                         | Vier Ziffern werden als DDMM interpretiert, also wäre dies der 10. April 2012.                                                         |
| <div align="center">`020304`</div>   | Sechs Ziffern werden als YYMMDD interpretiert, also wäre dies der 4. März 2002.                                                                                                                              | Sechs Ziffern werden als MMDDYY interpretiert, also wäre dies der 3. Februar 2004.            | Sechs Ziffern werden als DDMMYY interpretiert, also wäre dies der 2. März 2004.                                                         |
| <div align="center">`8 Ziffern`</div> | Acht Ziffern werden als YYYYMMDD interpretiert. Zum Beispiel, `20040612` ist der 12. Juni 2004.                                                                                                                | Acht Ziffern werden als MMDDYYYY interpretiert. Zum Beispiel, `06122004` ist der 12. Juni 2004. | Acht Ziffern werden als DDMMYYYY interpretiert. Zum Beispiel, `06122004` ist der 6. Dezember 2004.                                        |
| <div align="center">`12/6`</div>     | Zwei durch ein gültiges Trennzeichen getrennte Zahlen werden als MM/DD interpretiert, also wäre dies der 6. Dezember 2012. <br />Hinweis: Alle Zeichen außer Buchstaben und Ziffern werden als gültige Trennzeichen angesehen.| Dasselbe wie YMD                                                                         | Zwei durch ein Trennzeichen getrennte Zahlen werden als DD/MM interpretiert, also wäre dies der 12. Juni 2012.                               |
| <div align="center">`3/4/5`</div>    | 5. April 2012                                                                                                                                                                                      | 4. März 2005                                                                       | 3. April 2005                                                                                                                 |


## Textuelles Datumsparsing <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standardmäßig akzeptiert das `MaskedDateField` nur numerische Eingaben für Daten. Sie können jedoch das **textuelle Datumsparsing** aktivieren, um Benutzern zu ermöglichen, Monats- und Tagesnamen in ihre Eingaben einzugeben. Diese Funktion ist besonders nützlich, um eine natürlichere Datumseingabe zu ermöglichen.

Um textuelles Parsen zu aktivieren, verwenden Sie die Methode `setTextualDateParsing()`:

```java
dateField.setTextualDateParsing(true);
```

### Monatsnamenersetzung {#month-name-substitution}

Wenn das textuelle Parsen aktiviert ist, können Sie spezielle Modifikatoren in Ihrer Maske verwenden, um Monatsnamen anstelle von numerischen Werten zu akzeptieren:

- **`%Ms`** - Akzeptiert kurze Monatsnamen (Jan, Feb, Mär usw.)
- **`%Ml`** - Akzeptiert lange Monatsnamen (Januar, Februar, März usw.)

Monatsnamen können an jeder Position innerhalb der Maske erscheinen, und das Feld akzeptiert weiterhin numerische Eingaben als Rückfall.

#### Beispiele

| Maske | Eingabe | Ergebnis |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Gültig** - Parsed als 1. September 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Gültig** - Parsed als 1. September 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Gültig** - Parsed als 1. September 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Gültig** - Numerische Rückfall funktioniert weiterhin |

:::info
Alle 12 Monate werden sowohl in kurzer (Jan, Feb, Mär, Apr, Mai, Jun, Jul, Aug, Sep, Okt, Nov, Dez) als auch in langer (Januar, Februar usw.) Form unterstützt.
:::
### Wochentagsnamen-Dekoration {#day-name-decoration}

Namen der Wochentage können in die Eingabe aufgenommen werden, um die Lesbarkeit zu verbessern, sie sind jedoch **nur dekorativ** und werden beim Parsen entfernt. Sie beeinflussen nicht den tatsächlichen Datumwert.

- **`%Ds`** - Akzeptiert kurze Tagesnamen (Mo, Di, Mi usw.)
- **`%Dl`** - Akzeptiert lange Tagesnamen (Montag, Dienstag, Mittwoch usw.)

:::warning Tagesnamen erfordern einen numerischen Tag
Wenn Sie Wochentagsnamen (`%Ds` oder `%Dl`) verwenden, **muss Ihre Maske auch** `%Dz` oder `%Dd` enthalten, um die tatsächliche Tagesnummer anzugeben. Ohne eine numerische Tag-Komponente ist die Eingabe ungültig.
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

Das textuelle Datumsparsing umfasst mehrere hilfreiche Funktionen:

- **Groß-/Kleinschreibung:** Eingaben wie `MONDAY 09/01/25`, `monday 09/01/25` oder `Monday 09/01/25` funktionieren alle auf die gleiche Weise.
- **Sprachabhängig:** Monats- und Tagesnamen müssen mit der konfigurierten Spracheinstellung des Feldes übereinstimmen. Zum Beispiel, bei einer französischen Spracheinstellung, verwenden Sie `septembre` und nicht `September`. Englische Namen werden nicht erkannt, es sei denn, die Spracheinstellung ist auf Englisch gesetzt.
  - Französische Spracheinstellung: `septembre/01/25` wird als September erkannt
  - Deutsche Spracheinstellung: `Montag 09/01/25` wird mit Montag als Tagesnamen erkannt

## Festlegung von min/max-Beschränkungen {#setting-minmax-constraints}

Sie können den zulässigen Datumsbereich in einem `MaskedDateField` mit den Methoden `setMin()` und `setMax()` einschränken:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Eingaben außerhalb des definierten Bereichs werden als ungültig betrachtet.

## Wiederherstellung des Wertes {#restoring-the-value}

Das `MaskedDateField` umfasst eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies ist nützlich, um Benutzereingaben zurückzusetzen oder auf ein Standarddatum zurückzusetzen.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, indem `restoreValue()` aufgerufen wird
- **Über die Tastatur**, indem <kbd>ESC</kbd> gedrückt wird (dies ist der Standardwiederherstellungsschlüssel, es sei denn, er wird von einem Ereignislistener überschrieben)

Sie können den Wert, den Sie wiederherstellen möchten, mit `setRestoreValue()` festlegen, indem Sie eine [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) Instanz übergeben.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mit regulären Ausdrücken unter Verwendung der Methode `setPattern()` anwenden:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `MM/DD/YYYY` (zwei Ziffern, Schrägstrich, zwei Ziffern, Schrägstrich, vier Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format von regulären Ausdrücken
Das Muster muss der JavaScript-RegExp-Syntax entsprechen, die [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) dokumentiert ist.
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Datumswerte basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch falsch oder nicht parsierbar ist (z. B. `99/99/9999`), kann sie die Musterprüfungen bestehen, aber bei der logischen Validierung fehlschlagen.
Sie sollten den eingegebenen Wert in Ihrer Anwendungslogik immer validieren, selbst wenn ein regulärer Ausdrucksmuster gesetzt ist, um sicherzustellen, dass das Datum sowohl korrekt formatiert als auch sinnvoll ist.
::::

## Datumsauswahlfenster {#date-picker}

Das `MaskedDateField` beinhaltet einen integrierten Kalenderauswähler, der es Benutzern ermöglicht, ein Datum visuell auszuwählen, anstatt es einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technisch versierte Benutzer oder wenn eine präzise Eingabe erforderlich ist.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Zugriff auf den Wähler {#accessing-the-picker}

Sie können auf den Datumsauswähler mit `getPicker()` zugreifen:

```java
DatePicker picker = dateField.getPicker();
```

### Kalender-Icon anzeigen/ausblenden {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Kalendersymbol neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatisches Öffnungsverhalten {#auto-open-behavior}

Sie können den Wähler so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z. B. klickt, die Eingabetaste drückt oder die Pfeiltasten verwendet):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Wähler erzwingen
Um sicherzustellen, dass Benutzer nur ein Datum über den Kalenderauswähler auswählen können (und kein Datum manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
dateField.getPicker().setAutoOpen(true); // Öffnet den Wähler bei Benutzerinteraktion
dateField.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Diese Konfiguration stellt sicher, dass alle Datumseingaben über die Wählerbenutzeroberfläche erfolgen, was nützlich ist, wenn Sie eine strenge Formatkontrolle wünschen und Parsing-Probleme durch manuelle Eingaben eliminieren möchten.
:::

### Kalender manuell öffnen {#manually-open-the-calendar}

Um den Kalender programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwenden Sie den Alias:

```java
picker.show(); // dasselbe wie open()
```

### Wochen im Kalender anzeigen {#show-weeks-in-the-calendar}

Der Wähler kann optional die Wochennummern in der Kalenderansicht anzeigen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Der `MaskedDateFieldSpinner` erweitert [`MaskedDateField`](#basics) um Spinner-Steuerelemente, die es Benutzern ermöglichen, das Datum mit Pfeiltasten oder UI-Buttons zu erhöhen oder zu verringern. Es bietet einen interaktiveren Interaktionsstil, der besonders nützlich in Desktop-Anwendungen ist.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Schlüsselmerkmale {#key-features}

- **Interaktives Datumsschrittverhalten:**  
  Verwenden Sie die Pfeiltasten oder Spin-Buttons, um den Datumswert zu erhöhen oder zu verringern.

- **Anpassbare Schrittgröße:**  
  Wählen Sie aus, welchen Teil des Datums Sie ändern möchten, indem Sie `setSpinField()` verwenden:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Optionen sind `DAY`, `WEEK`, `MONTH` und `YEAR`.

- **Min/Max-Grenzen:**  
  Erbt die Unterstützung für minimal und maximal zulässige Daten mit `setMin()` und `setMax()`.

- **Formatierte Ausgaben:**  
  Vollständig kompatibel mit Masken- und Lokalisierungseinstellungen aus dem `MaskedDateField`.

### Beispiel: Wöchentliche Schritte konfigurieren {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dies sorgt dafür, dass jeder Spin-Schritt das Datum um eine Woche vor- oder zurücksetzt.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
