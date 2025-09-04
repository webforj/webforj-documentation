---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: f76242de3a742ad3a930e1581f688592
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Das `MaskedDateField` ist ein Texteingabefeld, das für die strukturierte Dateneingabe konzipiert ist. Es ermöglicht Benutzern, Daten als **Zahlen** einzugeben, und formatiert die Eingabe automatisch basierend auf einer definierten Maske, wenn das Feld den Fokus verliert. Die Maske ist eine Zeichenkette, die das erwartete Datumsformat angibt und sowohl die Eingabe als auch die Anzeige steuert.

Diese Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und Wiederherstellung von Werten. Sie ist besonders nützlich in Formularen wie Registrierungen, Buchungen und Zeitplänen, wo konsistente und regionsspezifische Datumsformate erforderlich sind.

:::tip Suchen Sie nach einer Zeit-Eingabe? 
Das `MaskedDateField` konzentriert sich ausschließlich auf **Datum**-Werte. Wenn Sie eine ähnliche Komponente für die Eingabe und Formatierung von **Zeit** benötigen, sehen Sie sich stattdessen das [`MaskedTimeField`](./timefield) an.
:::

## Grundlagen {#basics}

Das `MaskedDateField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignis-Listener für Wertänderungen definieren.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskenregeln {#mask-rules}

Das `MaskedDateField` unterstützt mehrere Datumsformate, die weltweit verwendet werden und sich in der Reihenfolge von Tag, Monat und Jahr unterscheiden. Häufige Muster sind:

- **Tag/Monat/Jahr** (wird in den meisten Teilen Europas verwendet)
- **Monat/Tag/Jahr** (wird in den Vereinigten Staaten verwendet)
- **Jahr/Monat/Tag** (wird in China, Japan und Korea verwendet; auch der ISO-Standard: `JJJJ-MM-TT`)

Innerhalb dieser Formate gibt es lokale Variationen, einschließlich der Wahl des Trennzeichens (z. B. `-`, `/` oder `.`), ob Jahre zwei oder vier Ziffern haben und ob einstellige Monate oder Tage mit führenden Nullen ausgefüllt werden.

Um mit dieser Vielfalt umzugehen, verwendet das `MaskedDateField` Formatindikatoren, die jeweils mit `%` beginnen, gefolgt von einem Buchstaben, der einen bestimmten Teil des Datums darstellt. Diese Indikatoren definieren, wie Eingaben geparst und wie das Datum angezeigt wird.

### Datumsformat-Indikatoren {#date-format-indicators}

| Format | Beschreibung  |
| ------ | ------------- |
| `%Y`   | Jahr          |
| `%M`   | Monat         |
| `%D`   | Tag           |

### Modifikatoren {#modifiers}

Modifikatoren ermöglichen eine genauere Kontrolle darüber, wie Komponenten des Datums formatiert werden:

| Modifikator | Beschreibung                  |
| ----------- | ------------------------------ |
| `z`         | Nullauffüllung                |
| `s`         | Kurze Textdarstellung         |
| `l`         | Lange Textdarstellung         |
| `p`         | Gepackte Zahl                 |
| `d`         | Dezimal (Standardformat)       |

Diese können kombiniert werden, um eine Vielzahl von Datums-Masken zu erstellen.

## Lokalisierung des Datumsformats {#date-format-localization}

Das `MaskedDateField` passt sich regionalen Datumsformaten an, indem es die passende Lokalisierung festlegt. Dadurch wird sichergestellt, dass Daten entsprechend den Erwartungen der Benutzer angezeigt und geparst werden.

| Region        | Format     | Beispiel      |
| ------------- | ---------- | ------------  |
| Vereinigte Staaten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO-Standard  | YYYY-MM-DD | `2023-07-04` |

Um die Lokalisierung anzuwenden, verwenden Sie die Methode `setLocale()`. Sie akzeptiert eine [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) und passt sowohl die Formatierung als auch das Parsen automatisch an:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing-Logik {#parsing-logic}

Das `MaskedDateField` parst die Benutzereingabe basierend auf der definierten Datumsmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, was eine flexible Eingabe ermöglicht und gleichzeitig gültige Daten sicherstellt. 
Das Parsing-Verhalten hängt von der durch die Maske definierten Formatreihenfolge ab (z. B. `%Mz/%Dz/%Yz` für Monat/Tag/Jahr). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

Angenommen, heute ist der `15. September 2012`, so würden verschiedene Eingaben wie folgt interpretiert werden:

### Beispiel-Parsingszenarien {#example-parsing-scenarios}

| Eingabe                             | YMD (ISO)                                                                                                                                                                                             | MDY (US)                                                                            | DMY (EU)                                                                                                                       |
| ----------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| <div align="center">`1`</div>        | Eine einzelne Ziffer wird immer als Tag innerhalb des aktuellen Monats interpretiert, sodass dies der 1. September 2012 wäre.                                                                                  | Gleich wie YMD                                                                        | Gleich wie YMD                                                                                                                |
| <div align="center">`12`</div>       | Zwei Ziffern werden immer als Tag innerhalb des aktuellen Monats interpretiert, sodass dies der 12. September 2012 wäre.                                                                                | Gleich wie YMD                                                                        | Gleich wie YMD                                                                                                                |
| <div align="center">`112`</div>      | Drei Ziffern werden als 1-stelliger Monat gefolgt von einer 2-stelligen Tageszahl interpretiert, sodass dies der 12. Januar 2012 wäre.                                                                   | Gleich wie YMD                                                                        | Drei Ziffern werden als 1-stelliger Tag gefolgt von einer 2-stelligen Monatszahl interpretiert, sodass dies der 1. Dezember 2012 wäre. |
| <div align="center">`1004`</div>     | Vier Ziffern werden als MMDD interpretiert, sodass dies der 4. Oktober 2012 wäre.                                                                                                                      | Gleich wie YMD                                                                        | Vier Ziffern werden als DDMM interpretiert, sodass dies der 10. April 2012 wäre.                                              |
| <div align="center">`020304`</div>   | Sechs Ziffern werden als YYMMDD interpretiert, sodass dies der 4. März 2002 wäre.                                                                                                                     | Sechs Ziffern werden als MMDDYY interpretiert, sodass dies der 3. Februar 2004 wäre.   | Sechs Ziffern werden als DDMMYY interpretiert, sodass dies der 2. März 2004 wäre.                                            |
| <div align="center">`8 Ziffern`</div> | Acht Ziffern werden als YYYYMMDD interpretiert. Zum Beispiel ist `20040612` der 12. Juni 2004.                                                                                                         | Acht Ziffern werden als MMDDYYYY interpretiert. Zum Beispiel ist `06122004` der 12. Juni 2004. | Acht Ziffern werden als DDMMYYYY interpretiert. Zum Beispiel ist `06122004` der 6. Dezember 2004.                                       |
| <div align="center">`12/6`</div>     | Zwei Zahlen, die durch ein gültiges Trennzeichen getrennt sind, werden als MM/DD interpretiert, sodass dies der 6. Dezember 2012 wäre. <br />Hinweis: Alle Zeichen außer Buchstaben und Ziffern werden als gültige Trennzeichen angesehen. | Gleich wie YMD                                                                        | Zwei Zahlen, die durch ein Trennzeichen getrennt sind, werden als DD/MM interpretiert, sodass dies der 12. Juni 2012 wäre.        |
| <div align="center">`3/4/5`</div>    | 5. April 2012                                                                                                                                                                                       | 4. März 2005                                                                         | 3. April 2005                                                                                                                 |

## Setzen von Min/Max-Beschränkungen {#setting-minmax-constraints}

Sie können den zulässigen Datumsbereich in einem `MaskedDateField` mit den Methoden `setMin()` und `setMax()` einschränken:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Eingaben außerhalb des definierten Bereichs werden als ungültig betrachtet.

## Wiederherstellen des Wertes {#restoring-the-value}

Das `MaskedDateField` enthält eine Wiederherstellungsfunktion, die den Wert des Feldes in einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies ist nützlich, um Benutzereingaben zurückzusetzen oder auf ein Standarddatum zurückzusetzen.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Möglichkeiten zum Wiederherstellen des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufruf von `restoreValue()`
- **Über die Tastatur**, durch Drücken von <kbd>ESC</kbd> ( dies ist die Standard-Wiederherstelltaste, es sei denn, sie wird von einem Ereignis-Listener überschrieben)

Sie können den Wert, der wiederhergestellt werden soll, mit `setRestoreValue()` festlegen und eine [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) Instanz übergeben.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mithilfe von regulären Ausdrücken mit der Methode `setPattern()` anwenden:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `MM/DD/YYYY` (zwei Ziffern, Schrägstrich, zwei Ziffern, Schrägstrich, vier Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format des Regulären Ausdrucks
Das Muster muss der JavaScript-RegExp-Syntax entsprechen, wie hier dokumentiert [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Dateneingaben basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht mit dem erwarteten Format übereinstimmen. Wenn die Eingabe syntaktisch gültig, aber semantisch fehlerhaft oder nicht parsierbar ist (z. B. `99/99/9999`), kann sie Mustertests bestehen, aber logische Validierungen nicht bestehen. 
Sie sollten immer den Eingabewert in Ihrer Anwendungslogik validieren, selbst wenn ein reguläres Ausdrucksmuster festgelegt ist, um sicherzustellen, dass das Datum sowohl korrekt formatiert als auch sinnvoll ist.
::::

## Datumsauswahl {#date-picker}

Das `MaskedDateField` enthält einen integrierten Kalenderpicker, der es Benutzern ermöglicht, ein Datum visuell auszuwählen, anstatt es einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn präzise Eingaben erforderlich sind.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Zugriff auf den Picker {#accessing-the-picker}

Sie können auf den Datumsauswähler mit `getPicker()` zugreifen:

```java
DatePicker picker = dateField.getPicker();
```

### Zeige/Verstecke das Picker-Symbol {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Kalendersymbol neben dem Feld ein- oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatische Öffnungsverhalten {#auto-open-behavior}

Sie können den Picker so konfigurieren, dass er automatisch öffnet, wenn der Benutzer mit dem Feld interagiert (z. B. klickt, die Eingabetaste drückt oder Pfeiltasten verwendet):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Picker erzwingen
Um sicherzustellen, dass Benutzer nur ein Datum über den Kalenderpicker auswählen können (und nicht manuell eingeben können), kombinieren Sie die folgenden beiden Einstellungen:

```java
dateField.getPicker().setAutoOpen(true); // Öffnet den Picker bei Benutzerinteraktion
dateField.setAllowCustomValue(false);     // Deaktiviert die manuelle Texteingabe
```

Dieses Setup gewährleistet, dass alle Dateneingaben über die Picker-Benutzeroberfläche kommen, was nützlich ist, wenn Sie eine strenge Formatkontrolle wünschen und Parsing-Probleme durch eingegebene Werte vermeiden möchten.
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

Der Picker kann optional die Wochennummern in der Kalenderansicht anzeigen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Der `MaskedDateFieldSpinner` erweitert [`MaskedDateField`](#basics) um Spinner-Steuerungen, mit denen Benutzer das Datum mithilfe von Pfeiltasten oder UI-Schaltflächen erhöhen oder verringern können. Er bietet einen geführten Interaktionsstil, der besonders in Desktop-Anwendungen nützlich ist.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Hauptmerkmale {#key-features}

- **Interaktive Datumserhöhung:**  
  Verwenden Sie Pfeiltasten oder Spin-Schaltflächen, um den Datumswert zu erhöhen oder zu verringern.

- **Anpassbares Schrittmaß:**  
  Wählen Sie, welcher Teil des Datums geändert werden soll, mit `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Optionen sind `DAY`, `WEEK`, `MONTH` und `YEAR`.

- **Min/Max-Grenzen:**  
  Erbt die Unterstützung für Mindest- und Höchstwerte unter Verwendung von `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**  
  Vollständig kompatibel mit Masken- und Lokalisierungseinstellungen von `MaskedDateField`.

### Beispiel: Wöchentliche Schritte konfigurieren {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Damit wird jeder Spin-Schritt um eine Woche vor- oder zurückgedreht.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
