---
title: MaskedDateField
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 981d5cd2686c83144433a0135b1222dc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Das `MaskedDateField` ist ein Texteingabefeld, das Benutzern ermöglicht, Daten als Zahlen einzugeben und das Eingabefeld automatisch basierend auf einer definierten Maske zu formatieren, wenn das Feld den Fokus verliert. Die Maske gibt das erwartete Datumsformat vor und leitet sowohl die Eingabe als auch die Anzeige. Die Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und Wiederherstellung von Werten für eine konsistente, regional spezifische Handhabung von Daten.

<!-- INTRO_END -->

## Grundlagen {#basics}

:::tip Auf der Suche nach einer Zeiteingabe?
Das `MaskedDateField` konzentriert sich ausschließlich auf **Datum**-Werte. Wenn Sie eine ähnliche Komponente für die Eingabe und Formatierung von **Zeit** benötigen, schauen Sie sich stattdessen das [`MaskedTimeField`](./timefield) an.
:::

Das `MaskedDateField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskenregeln {#mask-rules}

Das `MaskedDateField` unterstützt mehrere Datumsformate, die weltweit verwendet werden und je nach Reihenfolge von Tag, Monat und Jahr variieren. Häufige Muster sind:

- **Tag/Monat/Jahr** (in den meisten europäischen Ländern verwendet)
- **Monat/Tag/Jahr** (in den Vereinigten Staaten verwendet)
- **Jahr/Monat/Tag** (in China, Japan und Korea verwendet; auch der ISO-Standard: `YYYY-MM-DD`)

Innerhalb dieser Formate umfasst die lokale Variation die Wahl des Trennzeichens (z.B. `-`, `/` oder `.`), ob Jahre zwei oder vier Ziffern haben und ob einstellig ausgegebene Monate oder Tage mit führenden Nullen gepolstert werden.

Um diese Vielfalt zu bewältigen, verwendet das `MaskedDateField` Formatindikatoren, die jeweils mit `%` beginnen, gefolgt von einem Buchstaben, der einen bestimmten Teil des Datums repräsentiert. Diese Indikatoren definieren, wie die Eingabe geparst und wie das Datum angezeigt wird.

### Datumsformatindikatoren {#date-format-indicators}

| Format | Beschreibung |
| ------ | ------------ |
| `%Y`   | Jahr         |
| `%M`   | Monat        |
| `%D`   | Tag          |

### Modifikatoren {#modifiers}

Modifikatoren ermöglichen eine größere Kontrolle über die Formatierung von Datumsbestandteilen:

| Modifikator | Beschreibung                |
| ----------- | --------------------------- |
| `z`         | Nullauffüllung              |
| `s`         | Kurze Textdarstellung       |
| `l`         | Lange Textdarstellung       |
| `p`         | Gepackte Zahl                |
| `d`         | Dezimal (Standardformat)     |

Diese können kombiniert werden, um eine Vielzahl von Datums_maske_ zu erstellen.

## Lokalisierung des Datumsformats {#date-format-localization}

Das `MaskedDateField` passt sich regionalen Datumsformaten an, indem die entsprechende Locale gesetzt wird. Dies stellt sicher, dass Daten so angezeigt und geparst werden, dass sie den Erwartungen der Benutzer entsprechen.

| Region        | Format     | Beispiel      |
| ------------- | ---------- | ------------ |
| Vereinigte Staaten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO-Standard  | YYYY-MM-DD | `2023-07-04` |

Um die Lokalisierung anzuwenden, verwenden Sie die Methode `setLocale()`. Diese akzeptiert eine [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) und passt automatisch sowohl die Formatierung als auch das Parsen an:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing-Logik {#parsing-logic}

Das `MaskedDateField` parst die Benutzereingabe basierend auf der definierten Datumsmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, was eine flexible Eingabe ermöglicht und gleichzeitig gültige Daten gewährleistet. 
Das Parserverhalten hängt von der im Format definierten Reihenfolge ab (z. B. `%Mz/%Dz/%Yz` für Monat/Tag/Jahr). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

Angenommen, heute ist der `15. September 2012`, so würden verschiedene Eingaben folgendermaßen interpretiert:

### Beispiel Parsing-Szenarien {#example-parsing-scenarios}

| Eingabe                              | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Eine einzelne Ziffer wird immer als Tagesnummer im aktuellen Monat interpretiert, also wäre dies der 1. September 2012.                                                                             | Dasselbe wie YMD                                                                     | Dasselbe wie YMD                                                                                                           |
| <div align="center">`12`</div>       | Zwei Ziffern werden immer als Tagesnummer im aktuellen Monat interpretiert, also wäre dies der 12. September 2012.                                                                               | Dasselbe wie YMD                                                                    | Dasselbe wie YMD                                                                                                           |
| <div align="center">`112`</div>      | Drei Ziffern werden als eine 1-stellige Monatsnummer gefolgt von einer 2-stelligen Tagesnummer interpretiert, also wäre dies der 12. Januar 2012.                                               | Dasselbe wie YMD                                                                    | Drei Ziffern werden als eine 1-stellige Tagesnummer gefolgt von einer 2-stelligen Monatsnummer interpretiert, also wäre dies der 1. Dezember 2012. |
| <div align="center">`1004`</div>     | Vier Ziffern werden als MMDD interpretiert, also wäre dies der 4. Oktober 2012.                                                                                                                  | Dasselbe wie YMD                                                                    | Vier Ziffern werden als DDMM interpretiert, also wäre dies der 10. April 2012.                                                        |
| <div align="center">`020304`</div>   | Sechs Ziffern werden als YYMMDD interpretiert, also wäre dies der 4. März 2002.                                                                                                                  | Sechs Ziffern werden als MMDDYY interpretiert, also wäre dies der 3. Februar 2004.  | Sechs Ziffern werden als DDMMYY interpretiert, also wäre dies der 2. März 2004.                                                |
| <div align="center">`8 Ziffern`</div> | Acht Ziffern werden als YYYYMMDD interpretiert. Zum Beispiel, `20040612` entspricht dem 12. Juni 2004.                                                                                            | Acht Ziffern werden als MMDDYYYY interpretiert. Zum Beispiel, `06122004` entspricht dem 12. Juni 2004. | Acht Ziffern werden als DDMMYYYY interpretiert. Zum Beispiel, `06122004` entspricht dem 6. Dezember 2004. |
| <div align="center">`12/6`</div>     | Zwei Zahlen, die durch ein gültiges Trennzeichen getrennt sind, werden als MM/DD interpretiert, also wäre dies der 6. Dezember 2012. <br />Hinweis: Alle Zeichen außer Buchstaben und Ziffern werden als gültige Trennzeichen betrachtet. | Dasselbe wie YMD                                                                    | Zwei Zahlen, die durch ein Trennzeichen getrennt sind, werden als DD/MM interpretiert, also wäre dies der 12. Juni 2012.   |
| <div align="center">`3/4/5`</div>    | 5. April 2012                                                                                                                                                                                      | 4. März 2005                                                                         | 3. April 2005                                                                                                                 |


## Textuelle Datumseingabe <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standardmäßig akzeptiert das `MaskedDateField` nur numerische Eingaben für Daten. Sie können jedoch **textuelles Datumparsing** aktivieren, um Benutzern die Eingabe von Monats- und Tagesnamen zu ermöglichen. Diese Funktion ist besonders nützlich für eine natürlichere Dateneingabe.

Um das textuelle Parsing zu aktivieren, verwenden Sie die Methode `setTextualDateParsing()`:

```java
dateField.setTextualDateParsing(true);
```

### Ersetzung von Monatsnamen {#month-name-substitution}

Wenn das textuelle Parsing aktiviert ist, können Sie spezielle Modifikatoren in Ihrer Maske verwenden, um Monatsnamen anstelle von numerischen Werten zu akzeptieren:

- **`%Ms`** - Akzeptiert kurze Monatsnamen (Jan, Feb, Mär usw.)
- **`%Ml`** - Akzeptiert lange Monatsnamen (Januar, Februar, März usw.)

Monatsnamen können an beliebiger Stelle innerhalb der Maske erscheinen, und das Feld akzeptiert weiterhin numerische Eingaben als Fallback.

#### Beispiele

| Maske | Eingabe | Ergebnis |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Gültig** - Wird als 1. September 2025 geparst |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Gültig** - Wird als 1. September 2025 geparst |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Gültig** - Wird als 1. September 2025 geparst |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Gültig** - Numerische Fallback funktioniert immer noch |

:::info
Alle 12 Monate werden sowohl in kurzer (Jan, Feb, Mär, Apr, Mai, Jun, Jul, Aug, Sep, Okt, Nov, Dez) als auch in langer (Januar, Februar usw.) Form unterstützt.
:::
### Dekoration von Tagesnamen {#day-name-decoration}

Tage-of-week-Namen können in die Eingabe aufgenommen werden, um die Lesbarkeit zu verbessern, sind aber **nur dekorativ** und werden während des Parsens entfernt. Sie beeinflussen nicht den tatsächlichen Wert des Datums.

- **`%Ds`** - Akzeptiert kurze Tagesnamen (Mo, Di, Mi usw.)
- **`%Dl`** - Akzeptiert lange Tagesnamen (Montag, Dienstag, Mittwoch usw.)

:::warning Tagesnamen erfordern numerische Tage
Wenn Sie Tagesnamen ( `%Ds` oder `%Dl` ) verwenden, muss Ihre Maske **auch** `%Dz` oder `%Dd` enthalten, um die tatsächliche Tagesnummer anzugeben. Ohne einen numerischen Tagesbestandteil ist die Eingabe ungültig.
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

Das textuelle Datumparsing umfasst mehrere hilfreiche Funktionen:

- **Groß-/Kleinschreibung ist nicht empfindlich:** Eingaben wie `MONTAG 09/01/25`, `montag 09/01/25` oder `Montag 09/01/25` funktionieren alle gleich.
- **Locale-bewusst:** Monats- und Tagesnamen müssen mit der konfigurierten Locale des Feldes übereinstimmen. Beispielsweise verwenden Sie bei einer französischen Locale `septembre` und nicht `September`. Englische Namen werden nicht erkannt, es sei denn, die Locale ist auf Englisch eingestellt.
  - Französische Locale: `septembre/01/25` wird als September erkannt
  - Deutsche Locale: `Montag 09/01/25` wird mit Montag als Tagesname erkannt

## Minimale/maximale Einschränkungen festlegen {#setting-minmax-constraints}

Sie können den erlaubten Datumsbereich in einem `MaskedDateField` mithilfe der Methoden `setMin()` und `setMax()` einschränken:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Eingaben außerhalb des definierten Bereichs werden als ungültig betrachtet.

## Wert wiederherstellen {#restoring-the-value}

Das `MaskedDateField` umfasst eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies ist nützlich, um Benutzereingaben zurückzusetzen oder auf ein Standarddatum zurückzusetzen.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Möglichkeiten, den Wert wiederherzustellen {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufrufen von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> (dies ist der Standardwiederherstellungsschlüssel, es sei denn, er wird von einem Ereignislistener überschrieben)

Sie können den Wert, auf den zurückgesetzt werden soll, mit `setRestoreValue()` setzen, wobei eine [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html)-Instanz übergeben wird.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mithilfe von regulären Ausdrücken mit der Methode `setPattern()` anwenden:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dieses Muster garantiert, dass nur Werte, die dem Format `MM/DD/YYYY` (zwei Ziffern, Schrägstrich, zwei Ziffern, Schrägstrich, vier Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format regulärer Ausdrücke
Das Muster muss der Syntax von JavaScript-RegExp entsprechen, wie [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) dokumentiert.
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Datumseingaben basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder nichtparsenbar ist (z.B. `99/99/9999`), kann sie Musterprüfungen bestehen, aber logische Validierungen fehlerhaft sein.
Sie sollten den Eingabewert in Ihrer Anwendungslogik immer validieren, selbst wenn ein regulärer Ausdrucksmuster festgelegt ist, um sicherzustellen, dass das Datum sowohl korrekt formatiert als auch sinnvoll ist.
::::

## Datumsauswähler {#date-picker}

Das `MaskedDateField` enthält einen integrierten Kalenderauswähler, der es Benutzern ermöglicht, ein Datum visuell auszuwählen, anstatt es einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn eine präzise Eingabe erforderlich ist.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Zugriff auf den Auswähler {#accessing-the-picker}

Sie können auf den Datumsauswähler mit `getPicker()` zugreifen:

```java
DatePicker picker = dateField.getPicker();
```

### Anzeigen/ausblenden des Auswahl-Icons {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Kalendersymbol neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol
```

### Automatische Öffnungsfunktion {#auto-open-behavior}

Sie können den Auswähler so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z.B. klickt, die Eingabetaste drückt oder Pfeiltasten verwendet):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Auswähler erzwingen
Um sicherzustellen, dass Benutzer ein Datum nur über den Kalenderauswähler auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
dateField.getPicker().setAutoOpen(true); // Öffnet den Auswähler bei Benutzereingaben
dateField.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Diese Konfiguration garantiert, dass alle Datumseingaben über die Benutzeroberfläche des Auswählers erfolgen, was nützlich ist, wenn Sie strenge Formatkontrolle wünschen und Parsing-Probleme aus eingegebenen Daten eliminieren möchten.
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

Der Auswähler kann optional Wochenzahlen in der Kalenderansicht anzeigen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Der `MaskedDateFieldSpinner` erweitert [`MaskedDateField`](#basics) um Spinner-Steuerelemente, die es Benutzern ermöglichen, das Datum mithilfe von Pfeiltasten oder UI-Schaltflächen zu erhöhen oder zu verringern. Es bietet einen geführten Interaktionsstil, der insbesondere in Desktop-Anwendungen nützlich ist.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Hauptmerkmale {#key-features}

- **Interaktive Datumsschritte:**  
  Verwenden Sie die Pfeiltasten oder Spin-Schaltflächen, um den Datumswert zu erhöhen oder zu verringern.

- **Anpassbare Schrittgröße:**  
  Wählen Sie aus, welcher Teil des Datums geändert werden soll, indem Sie `setSpinField()` verwenden:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Optionen sind `DAY`, `WEEK`, `MONTH` und `YEAR`.

- **Min/Max-Grenzen:**  
  Unterstützt minimalen und maximalen Datumsbereich mithilfe von `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**  
  Vollständig kompatibel mit Masken und Lokalisierungseinstellungen von `MaskedDateField`.

### Beispiel: Wöchentliche Schritte konfigurieren {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

So wird jeder Schritt des Spinnens das Datum um eine Woche vor- oder zurückschreiten. 

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
