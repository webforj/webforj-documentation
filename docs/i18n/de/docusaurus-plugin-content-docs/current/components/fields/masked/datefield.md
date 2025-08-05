---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: e2073fda6d7853bbacc6431c615e8cff
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Das `MaskedDateField` ist ein Texteingabefeld, das für die strukturierte Eingabe von Daten entwickelt wurde. Es ermöglicht Benutzern, Daten als **Zahlen** einzugeben, und formatiert die Eingabe automatisch basierend auf einer definierten Maske, wenn das Feld den Fokus verliert. Die Maske ist ein String, der das erwartete Datumsformat angibt und sowohl die Eingabe als auch die Anzeige leitet.

Diese Komponente unterstützt flexibles Parsen, Validierung, Lokalisierung und Wertwiederherstellung. Sie ist besonders nützlich in Formularen wie Anmeldungen, Buchungen und Terminplanungen, wo konsistente und regionsspezifische Datumsformate erforderlich sind.

:::tip Auf der Suche nach einer Zeiteingabe?
Das `MaskedDateField` konzentriert sich ausschließlich auf **Datumswerte**. Wenn Sie eine ähnliche Komponente zum Eingeben und Formatieren von **Zeit** benötigen, werfen Sie einen Blick auf das [`MaskedTimeField`](./timefield).
:::

## Grundlagen {#basics}

Das `MaskedDateField` kann mit oder ohne Parameter instanziiert werden. Sie können einen Anfangswert, ein Label, einen Platzhalter und einen Ereignislistener für Wertänderungen definieren.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskenregeln {#mask-rules}

Das `MaskedDateField` unterstützt mehrere Datumsformate, die weltweit verwendet werden und je nach Reihenfolge von Tag, Monat und Jahr variieren. Zu den gängigen Mustern gehören:

- **Tag/Monat/Jahr** (in den meisten Teilen Europas verwendet)
- **Monat/Tag/Jahr** (in den Vereinigten Staaten verwendet)
- **Jahr/Monat/Tag** (in China, Japan und Korea verwendet; auch der ISO-Standard: `YYYY-MM-DD`)

Innerhalb dieser Formate umfassen lokale Variationen die Wahl des Trennzeichens (z. B. `-`, `/` oder `.`), ob Jahre zwei oder vier Ziffern haben und ob einstellige Monate oder Tage mit führenden Nullen aufgefüllt werden.

Um mit dieser Vielfalt umzugehen, verwendet das `MaskedDateField` Formatindikatoren, die jeweils mit `%` beginnen, gefolgt von einem Buchstaben, der einen bestimmten Teil des Datums bezeichnet. Diese Indikatoren definieren, wie die Eingabe geparst wird und wie das Datum angezeigt wird.

### Datumsformatindikatoren {#date-format-indicators}

| Format | Beschreibung   |
| ------ | --------------- |
| `%Y`   | Jahr           |
| `%M`   | Monat          |
| `%D`   | Tag            |

### Modifikatoren {#modifiers}

Modifikatoren erlauben eine genauere Kontrolle über die Formatierung der Komponenten des Datums:

| Modifikator | Beschreibung                  |
| ------------| ------------------------------ |
| `z`         | Nullauffüllung                |
| `s`         | Kurze Textdarstellung          |
| `l`         | Lange Textdarstellung          |
| `p`         | Gepackte Zahl                 |
| `d`         | Dezimal (Standardformat)      |

Diese können kombiniert werden, um eine Vielzahl von DatumsMasken zu erstellen.

## Lokalisierung des Datumsformats {#date-format-localization}

Das `MaskedDateField` passt sich regionalen Datumsformaten an, indem die entsprechende Locale eingestellt wird. Dies stellt sicher, dass Daten in einer Weise angezeigt und geparst werden, die den Erwartungen der Benutzer entspricht.

| Region        | Format     | Beispiel      |
| ------------- | ---------- | ------------- |
| Vereinigte Staaten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO-Standard  | YYYY-MM-DD | `2023-07-04` |

Um die Lokalisierung anzuwenden, verwenden Sie die Methode `setLocale()`. Sie akzeptiert eine [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) und passt sowohl die Formatierung als auch das Parsen automatisch an:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing-Logik {#parsing-logic}

Das `MaskedDateField` parst die Benutzereingabe basierend auf der definierten Datumsmaske. Es akzeptiert sowohl vollständige als auch abgekürzte numerische Eingaben mit oder ohne Trennzeichen, sodass eine flexible Eingabe möglich ist, während gültige Daten sichergestellt werden.
Das Parsing-Verhalten hängt von der durch die Maske definierten Formatreihenfolge ab (z. B. `%Mz/%Dz/%Yz` für Monat/Tag/Jahr). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

Zum Beispiel, vorausgesetzt, heute ist `15. September 2012`, so würde Folgendes interpretiert werden:

### Beispiel für Parsing-Szenarien {#example-parsing-scenarios}

| Eingabe                              | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Eine einzelne Ziffer wird immer als Tag innerhalb des aktuellen Monats interpretiert, also wäre dies der 1. September 2012.                                                                         | Dasselbe wie YMD                                                                     | Dasselbe wie YMD                                                                                                          |
| <div align="center">`12`</div>       | Zwei Ziffern werden immer als Tag innerhalb des aktuellen Monats interpretiert, also wäre dies der 12. September 2012.                                                                         | Dasselbe wie YMD                                                                     | Dasselbe wie YMD                                                                                                          |
| <div align="center">`112`</div>      | Drei Ziffern werden als eine einstellige Monatszahl gefolgt von einer zweistelligen Tageszahl interpretiert, also wäre dies der 12. Januar 2012.                                                 | Dasselbe wie YMD                                                                     | Drei Ziffern werden als eine einstellige Tageszahl gefolgt von einer zweistelligen Monatszahl interpretiert, also wäre dies der 1. Dezember 2012. |
| <div align="center">`1004`</div>     | Vier Ziffern werden als MMDD interpretiert, also wäre dies der 4. Oktober 2012.                                                                                                                 | Dasselbe wie YMD                                                                     | Vier Ziffern werden als DDMM interpretiert, also wäre dies der 10. April 2012.                                             |
| <div align="center">`020304`</div>   | Sechs Ziffern werden als YYMMDD interpretiert, also wäre dies der 4. März 2002.                                                                                                               | Sechs Ziffern werden als MMDDYY interpretiert, also wäre dies der 3. Februar 2004.    | Sechs Ziffern werden als DDMMYY interpretiert, also wäre dies der 2. März 2004.                                         |
| <div align="center">`8 digits`</div> | Acht Ziffern werden als YYYYMMDD interpretiert. Zum Beispiel ist `20040612` der 12. Juni 2004.                                                                                                  | Acht Ziffern werden als MMDDYYYY interpretiert. Zum Beispiel ist `06122004` der 12. Juni 2004. | Acht Ziffern werden als DDMMYYYY interpretiert. Zum Beispiel ist `06122004` der 6. Dezember 2004.                           |
| <div align="center">`12/6`</div>     | Zwei Zahlen, die durch ein gültiges Trennzeichen getrennt sind, werden als MM/DD interpretiert, also wäre dies der 6. Dezember 2012. <br />Hinweis: Alle Zeichen außer Buchstaben und Ziffern gelten als gültige Trennzeichen. | Dasselbe wie YMD                                                                     | Zwei Zahlen, die durch ein Trennzeichen getrennt sind, werden als DD/MM interpretiert, also wäre dies der 12. Juni 2012.    |
| <div align="center">`3/4/5`</div>    | 5. April 2012                                                                                                                                                                                     | 4. März 2005                                                                        | 3. April 2005                                                                                                            |

## Minimale/maximale Einschränkungen festlegen {#setting-minmax-constraints}

Sie können den zulässigen Datumsbereich in einem `MaskedDateField` mit den Methoden `setMin()` und `setMax()` beschränken:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Eingaben außerhalb des definierten Bereichs werden als ungültig betrachtet.

## Wiederherstellung des Wertes {#restoring-the-value}

Das `MaskedDateField` enthält eine Wiederherstellungsfunktion, die den Wert des Feldes auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies ist nützlich, um Benutzereingaben zurückzusetzen oder auf ein Standarddatum zurückzukehren.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmatisch**, durch Aufruf von `restoreValue()`
- **Über die Tastatur**, indem Sie <kbd>ESC</kbd> drücken (dies ist die Standardwiederherstellungstaste, es sei denn, sie wird von einem Ereignislistener überschrieben)

Sie können den Wert, auf den wiederhergestellt werden soll, mit `setRestoreValue()` festlegen, indem Sie eine [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html)-Instanz übergeben.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validierungsmuster {#validation-patterns}

Sie können clientseitige Validierungsregeln mit regulären Ausdrücken mithilfe der Methode `setPattern()` anwenden:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `MM/DD/YYYY` (zwei Ziffern, Schrägstrich, zwei Ziffern, Schrägstrich, vier Ziffern) entsprechen, als gültig betrachtet werden.

:::tip Format des regulären Ausdrucks
Das Muster muss der JavaScript RegExp-Syntax entsprechen, wie [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) dokumentiert.
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Dateneingaben basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, aber semantisch inkorrekt oder nicht parsetzbar ist (z. B. `99/99/9999`), kann sie Musterprüfungen bestehen, aber von der logischen Validierung abgelehnt werden.
Sie sollten den Eingabewert in Ihrer Anwendungslogik immer validieren, auch wenn ein reguläres Ausdrucksmuster festgelegt ist, um sicherzustellen, dass das Datum sowohl korrekt formatiert als auch sinnvoll ist.
::::

## Datumsauswahl {#date-picker}

Das `MaskedDateField` enthält einen integrierten Kalendereingabewähler, mit dem Benutzer ein Datum visuell auswählen können, anstatt es einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technische Benutzer oder wenn präzise Eingaben erforderlich sind.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Zugreifen auf den Wähler {#accessing-the-picker}

Sie können auf den Datumsauswähler mit `getPicker()` zugreifen:

```java
DatePicker picker = dateField.getPicker();
```

### Das Wähler-Symbol anzeigen/ausblenden {#showhide-the-picker-icon}

Verwenden Sie `setIconVisible()`, um das Kalendersymbol neben dem Feld anzuzeigen oder auszublenden:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatisches Öffnen {#auto-open-behavior}

Sie können den Wähler so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z. B. klickt, die Eingabetaste drückt oder Pfeiltasten verwendet):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Wähler erzwingen
Um sicherzustellen, dass Benutzer ein Datum nur mit dem Kalendermesser auswählen können (und nicht manuell eingeben), kombinieren Sie die folgenden beiden Einstellungen:

```java
dateField.getPicker().setAutoOpen(true); // Öffnet den Wähler bei Benutzerinteraktion
dateField.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Diese Konfiguration stellt sicher, dass alle Dateneingaben über die Wähler-UI kommen, was nützlich ist, wenn Sie eine strenge Formatkontrolle wünschen und Parsing-Probleme aus eingegebenen Werten ausschließen möchten.
:::

### Den Kalender manuell öffnen {#manually-open-the-calendar}

Um den Kalender programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwenden Sie das Alias:

```java
picker.show(); // dasselbe wie open()
```

### Wochen im Kalender anzeigen {#show-weeks-in-the-calendar}

Der Wähler kann optional die Wochennummern in der Kalendersicht anzeigen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Der `MaskedDateFieldSpinner` erweitert das [`MaskedDateField`](#basics) um Drehregler, die es Benutzern ermöglichen, das Datum mithilfe von Pfeiltasten oder UI-Schaltflächen zu erhöhen oder zu verringern. Es bietet einen geführteren Interaktionsstil, insbesondere in Desktop-Anwendungen.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Hauptmerkmale {#key-features}

- **Interaktive Datumsanpassung:**  
  Verwenden Sie Pfeiltasten oder Drehtasten, um den Datumwert zu erhöhen oder zu verringern.

- **Anpassbare Schrittgröße:**  
  Wählen Sie aus, welchen Teil des Datums Sie mit `setSpinField()` verändern möchten:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Die Optionen sind `DAY`, `WEEK`, `MONTH` und `YEAR`.

- **Min/Max-Grenzen:**  
  Erbt die Unterstützung für minimale und maximale zulässige Daten mithilfe von `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**  
  Vollständig kompatibel mit Masken und Lokalisierungseinstellungen aus `MaskedDateField`.

### Beispiel: Wöchentliche Schritte konfigurieren {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dadurch wird jeder Schritt des Drehen oder Zurückein Datum um eine Woche vorwärts oder rückwärts bewegt.

## Stil {#styling}

<TableBuilder name="MaskedDateField" />
