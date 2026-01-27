---
title: MaskedDateField
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 93973075b9f8f9bcc3eddf18e8b01017
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Das `MaskedDateField` ist ein Texteingabefeld, das für die strukturierte Eingabe von Daten konzipiert wurde. Es ermöglicht Benutzern, Daten als **Zahlen** einzugeben und formatiert die Eingabe automatisch basierend auf einer definierten Maske, wenn das Feld den Fokus verliert. Die Maske ist eine Zeichenkette, die das erwartete Datumsformat spezifiziert und sowohl die Eingabe als auch die Anzeige leitet.

Diese Komponente unterstützt flexible Parsing-, Validierungs-, Lokalisierungs- und Wiederherstellungsfunktionen. Sie ist besonders nützlich in Formularen wie Registrierungen, Buchungen und Zeitplänen, in denen konsistente und regionsspezifische Datumsformate erforderlich sind.

:::tip Auf der Suche nach einer Zeit-Eingabe?
Das `MaskedDateField` konzentriert sich ausschließlich auf **Datum**-Werte. Wenn du eine ähnliche Komponente zum Eingeben und Formatieren von **Zeit** benötigst, schaue stattdessen in das [`MaskedTimeField`](./timefield).
:::

## Grundlagen {#basics}

Das `MaskedDateField` kann mit oder ohne Parameter instanziiert werden. Du kannst einen Startwert, ein Label, einen Platzhalter und einen Ereignis-Listener für Wertänderungen definieren.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskenregeln {#mask-rules}

Das `MaskedDateField` unterstützt mehrere Datumsformate, die weltweit verwendet werden, die sich nach der Reihenfolge von Tag, Monat und Jahr unterscheiden. Zu den häufigsten Mustern gehören:

- **Tag/Monat/Jahr** (verwendet in den meisten europäischen Ländern)
- **Monat/Tag/Jahr** (verwendet in den Vereinigten Staaten)
- **Jahr/Monat/Tag** (verwendet in China, Japan und Korea; auch der ISO-Standard: `YYYY-MM-DD`)

Innerhalb dieser Formate umfassen lokale Variationen die Wahl des Trennzeichens (z.B. `-`, `/` oder `.`), ob Jahre zwei oder vier Ziffern haben und ob einstellige Monate oder Tage mit führenden Nullen aufgefüllt werden.

Um mit dieser Vielfalt umzugehen, verwendet das `MaskedDateField` Formatindikatoren, die jeweils mit `%` beginnen, gefolgt von einem Buchstaben, der einen bestimmten Teil des Datums repräsentiert. Diese Indikatoren definieren, wie Eingaben geparst und wie das Datum angezeigt wird.

### Datumsformatindikatoren {#date-format-indicators}

| Format | Beschreibung |
| ------ | ------------ |
| `%Y`   | Jahr         |
| `%M`   | Monat        |
| `%D`   | Tag          |

### Modifikatoren {#modifiers}

Modifikatoren ermöglichen eine genauere Kontrolle über die Formatierung der Komponenten des Datums:

| Modifikator | Beschreibung                  |
| ----------- | ----------------------------- |
| `z`         | Nullauffüllen                 |
| `s`         | Kurze Textdarstellung         |
| `l`         | Lange Textdarstellung         |
| `p`         | Gepackte Zahl                 |
| `d`         | Dezimal (Standardformat)      |

Diese können kombiniert werden, um eine Vielzahl von Datums-Masken zu erstellen.

## Lokalisierung des Datumsformats {#date-format-localization}

Das `MaskedDateField` passt sich regionalen Datumsformaten an, indem das entsprechende Gebietsschema festgelegt wird. Dies stellt sicher, dass Daten in einer Weise angezeigt und geparst werden, die den Erwartungen der Benutzer entspricht.

| Region        | Format     | Beispiel      |
| ------------- | ---------- | ------------- |
| Vereinigte Staaten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO-Standard  | YYYY-MM-DD | `2023-07-04` |

Um die Lokalisierung anzuwenden, verwende die Methode `setLocale()`. Diese akzeptiert ein [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) und passt sowohl die Formatierung als auch das Parsing automatisch an:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing-Logik {#parsing-logic}

Das `MaskedDateField` parst Benutzereingaben basierend auf der definierten Datumsmaske. Es akzeptiert sowohl vollständige als auch verkürzte numerische Eingaben mit oder ohne Trennzeichen, was eine flexible Eingabe ermöglicht und gleichzeitig gültige Daten sicherstellt.
Das Parsing-Verhalten hängt von der Formatreihenfolge ab, die durch die Maske definiert ist (z.B. `%Mz/%Dz/%Yz` für Monat/Tag/Jahr). Dieses Format bestimmt, wie numerische Sequenzen interpretiert werden.

Zum Beispiel, wenn heute der `15. September 2012` ist, wird die Eingabe folgendermaßen interpretiert:

### Beispielhafte Parsing-Szenarien {#example-parsing-scenarios}

| Eingabe                            | YMD (ISO)                                                                                                                                                                                          | MDY (USA)                                                                            | DMY (EU)                                                                                                                     |
| ---------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>      | Eine einzelne Ziffer wird immer als Tag innerhalb des aktuellen Monats interpretiert, sodass dies der 1. September 2012 wäre.                                                                                 | Gleich wie YMD                                                                         | Gleich wie YMD                                                                                                                  |
| <div align="center">`12`</div>     | Zwei Ziffern werden immer als Tag innerhalb des aktuellen Monats interpretiert, sodass dies der 12. September 2012 wäre.                                                                                   | Gleich wie YMD                                                                         | Gleich wie YMD                                                                                                                  |
| <div align="center">`112`</div>    | Drei Ziffern werden als 1-stellige Monatszahl gefolgt von einer 2-stelligen Tageszahl interpretiert, sodass dies der 12. Januar 2012 wäre.                                                                        | Gleich wie YMD                                                                         | Drei Ziffern werden als 1-stellige Tageszahl gefolgt von einer 2-stelligen Monatszahl interpretiert, sodass dies der 1. Dezember 2012 wäre. |
| <div align="center">`1004`</div>   | Vier Ziffern werden als MMDD interpretiert, sodass dies der 4. Oktober 2012 wäre.                                                                                                                             | Gleich wie YMD                                                                         | Vier Ziffern werden als DDMM interpretiert, sodass dies der 10. April 2012 wäre.                                                         |
| <div align="center">`020304`</div> | Sechs Ziffern werden als YYMMDD interpretiert, sodass dies der 4. März 2002 wäre.                                                                                                                              | Sechs Ziffern werden als MMDDYY interpretiert, sodass dies der 3. Februar 2004 wäre.            | Sechs Ziffern werden als DDMMYY interpretiert, sodass dies der 2. März 2004 wäre.                                                         |
| <div align="center">`8 Ziffern`</div> | Acht Ziffern werden als YYYYMMDD interpretiert. Beispielsweise ergibt `20040612` den 12. Juni 2004.                                                                                                                | Acht Ziffern werden als MMDDYYYY interpretiert. Beispielsweise ergibt `06122004` den 12. Juni 2004. | Acht Ziffern werden als DDMMYYYY interpretiert. Beispielsweise ergibt `06122004` den 6. Dezember 2004.                                        |
| <div align="center">`12/6`</div>   | Zwei Zahlen, die durch ein gültiges Trennzeichen getrennt sind, werden als MM/DD interpretiert, sodass dies der 6. Dezember 2012 wäre. <br />Hinweis: Alle Zeichen außer Buchstaben und Ziffern gelten als gültige Trennzeichen. | Gleich wie YMD                                                                         | Zwei Zahlen, die durch ein Trennzeichen getrennt sind, werden als DD/MM interpretiert, sodass dies der 12. Juni 2012 wäre.                               |
| <div align="center">`3/4/5`</div>  | 5. April 2012                                                                                                                                                                                      | 4. März 2005                                                                       | 3. April 2005                                                                                                                 |


## Textuelle Datumsverarbeitung <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standardmäßig akzeptiert das `MaskedDateField` nur numerische Eingaben für Daten. Du kannst jedoch die **textuelle Datumsverarbeitung** aktivieren, um Benutzern zu ermöglichen, Monats- und Tagesnamen in ihre Eingabe einzugeben. Diese Funktion ist besonders nützlich, um eine natürlichere Datumseingabe zu schaffen.

Um die textuelle Verarbeitung zu aktivieren, verwende die Methode `setTextualDateParsing()`:

```java
dateField.setTextualDateParsing(true);
```

### Ersatz von Monatsnamen {#month-name-substitution}

Wenn die textuelle Verarbeitung aktiviert ist, kannst du spezielle Modifikatoren in deiner Maske verwenden, um Monatsnamen anstelle von numerischen Werten zu akzeptieren:

- **`%Ms`** - Akzeptiert kurze Monatsnamen (Jan, Feb, Mär usw.)
- **`%Ml`** - Akzeptiert lange Monatsnamen (Januar, Februar, März usw.)

Monatsnamen können an jeder Stelle innerhalb der Maske erscheinen, und das Feld akzeptiert weiterhin numerische Eingaben als Fallback.

#### Beispiele

| Maske | Eingabe             | Ergebnis |
| ----- | ------------------- | -------- |
| `%Ms/%Dz/%Yz`            | `Sep/01/25`       | **Gültig** - Wird als 1. September 2025 geparst |
| `%Ml/%Dz/%Yz`            | `September/01/25` | **Gültig** - Wird als 1. September 2025 geparst |
| `%Dz/%Ml/%Yz`            | `01/September/25` | **Gültig** - Wird als 1. September 2025 geparst |
| `%Mz/%Dz/%Yz`            | `09/01/25`        | **Gültig** - Numerischer Fallback funktioniert weiterhin |

:::info
Alle 12 Monate werden sowohl in kurzer (Jan, Feb, Mär, Apr, Mai, Jun, Jul, Aug, Sep, Okt, Nov, Dez) als auch in langer (Januar, Februar usw.) Form unterstützt.
:::
### Dekoration von Tagesnamen {#day-name-decoration}

Die Tagesnamen können in die Eingabe aufgenommen werden, um die Lesbarkeit zu verbessern, werden jedoch **nur dekorativ** behandelt und während des Parsens entfernt. Sie beeinflussen nicht den tatsächlichen Datumswert.

- **`%Ds`** - Akzeptiert kurze Tagesnamen (Mo, Di, Mi usw.)
- **`%Dl`** - Akzeptiert lange Tagesnamen (Montag, Dienstag, Mittwoch usw.)

:::warning Tagesnamen erfordern eine numerische Tagesangabe
Wenn du Tagesnamen (`%Ds` oder `%Dl`) verwendest, muss deine Maske **auch** `%Dz` oder `%Dd` enthalten, um die tatsächliche Tageszahl anzugeben. Ohne eine numerische Tageskomponente wird die Eingabe ungültig sein.
:::

#### Beispiele

| Maske | Eingabe              | Ergebnis |
| ----- | -------------------- | -------- |
| `%Ds %Mz/%Dz/%Yz`       | `Mo 09/01/25`      | **Gültig** - Tagname ist dekorativ |
| `%Dl %Mz/%Dz/%Yz`       | `Montag 09/01/25`  | **Gültig** - Tagname ist dekorativ |
| `%Mz/%Dz/%Yz %Ds`       | `09/01/25 Di`      | **Gültig** - Tagname am Ende |
| `%Dl/%Mz/%Yz`           | `Montag/09/25`     | **Ungültig** - `%Dz` fehlt |
| `%Mz/%Dl/%Yz`           | `09/Montag/25`     | **Ungültig** - `%Dz` fehlt |

Alle 7 Wochentage werden sowohl in kurzer (Mo, Di, Mi, Do, Fr, Sa, So) als auch in langer (Montag, Dienstag usw.) Form unterstützt.

### Zusätzliche Parsing-Regeln {#additional-parsing-rules}

Die textuelle Datumsverarbeitung enthält mehrere hilfreiche Funktionen:

- **Groß-/Kleinschreibung ignoriert:** Eingaben wie `MONTAG 09/01/25`, `montag 09/01/25` oder `Montag 09/01/25` funktionieren alle gleich.
- **Locale-bewusst:** Monats- und Tagesnamen müssen mit dem konfigurierten Gebietsschema des Feldes übereinstimmen. Verwende beispielsweise mit einem französischen Gebietsschema `septembre`, nicht `September`. Englische Namen werden nicht erkannt, es sei denn, das Gebietsschema ist auf Englisch eingestellt.
  - Französisches Gebietsschema: `septembre/01/25` wird als September erkannt.
  - Deutsches Gebietsschema: `Montag 09/01/25` wird mit Montag als Tagesnamen erkannt.

## Min/Max-Beschränkungen festlegen {#setting-minmax-constraints}

Du kannst den zulässigen Datumsbereich in einem `MaskedDateField` mit den Methoden `setMin()` und `setMax()` einschränken:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide Methoden akzeptieren Werte vom Typ [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Eingaben außerhalb des definierten Bereichs werden als ungültig betrachtet.

## Wiederherstellung des Wertes {#restoring-the-value}

Das `MaskedDateField` enthält eine Wiederherstellungsfunktion, die den Wert des Felds auf einen vordefinierten oder ursprünglichen Zustand zurücksetzt. Dies ist nützlich, um Benutzereingaben zurückzusetzen oder auf ein Standarddatum zurückzusetzen.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Möglichkeiten zur Wiederherstellung des Wertes {#ways-to-restore-the-value}

- **Programmgesteuert**, durch Aufrufen von `restoreValue()`
- **Über die Tastatur**, indem die Taste <kbd>ESC</kbd> gedrückt wird (dies ist die standardmäßige Wiederherstellungstaste, es sei denn, sie wird von einem Ereignis-Listener überschrieben)

Du kannst den Wert, auf den zurückgestellt werden soll, mit `setRestoreValue()` festlegen, indem du eine [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) Instanz übergibst.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validierungs-Muster {#validation-patterns}

Du kannst clientseitige Validierungsregeln mithilfe von regulären Ausdrücken mit der Methode `setPattern()` anwenden:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dieses Muster stellt sicher, dass nur Werte, die dem Format `MM/DD/YYYY` (zwei Ziffern, Schrägstrich, zwei Ziffern, Schrägstrich, vier Ziffern) entsprechen, als gültig angesehen werden.

:::tip Format für reguläre Ausdrücke
Das Muster muss der JavaScript RegExp-Syntax entsprechen, die hier dokumentiert ist: [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Hinweise zur Eingabeverarbeitung
Das Feld versucht, numerische Datumseingaben basierend auf der aktuellen Maske zu parsen und zu formatieren. Benutzer können jedoch weiterhin manuell Werte eingeben, die nicht dem erwarteten Format entsprechen. Wenn die Eingabe syntaktisch gültig, jedoch semantisch falsch oder unparsbar ist (z.B. `99/99/9999`), kann sie die Musterüberprüfungen bestehen, aber gegen die logische Validierung fehlschlagen.
Du solltest den Eingabewert immer in deiner Anwendungslogik validieren, selbst wenn ein reguläres Ausdrucksmuster festgelegt ist, um sicherzustellen, dass das Datum sowohl korrekt formatiert als auch sinnvoll ist.
::::

## Datumsauswahl {#date-picker}

Das `MaskedDateField` enthält einen integrierten Kalenderauswähler, der es Benutzern ermöglicht, ein Datum visuell auszuwählen, anstatt es einzugeben. Dies verbessert die Benutzerfreundlichkeit für weniger technikaffine Benutzer oder wenn eine präzise Eingabe erforderlich ist.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Zugriff auf den Auswähler {#accessing-the-picker}

Du kannst auf den Datumswähler mit `getPicker()` zugreifen:

```java
DatePicker picker = dateField.getPicker();
```

### Kalender-Symbol anzeigen/verbergen {#showhide-the-picker-icon}

Verwende `setIconVisible()`, um das Kalendersymbol neben dem Feld anzuzeigen oder zu verstecken:

```java
picker.setIconVisible(true); // zeigt das Symbol an
```

### Automatisches Öffnungsverhalten {#auto-open-behavior}

Du kannst den Picker so konfigurieren, dass er automatisch geöffnet wird, wenn der Benutzer mit dem Feld interagiert (z.B. klickt, Enter drückt oder Pfeiltasten verwendet):

```java
picker.setAutoOpen(true);
```

:::tip Auswahl über den Wähler erzwingen
Um sicherzustellen, dass Benutzer nur ein Datum über den Kalenderauswähler auswählen können (und nicht manuell eingeben), kombiniere die folgenden beiden Einstellungen:

```java
dateField.getPicker().setAutoOpen(true); // Öffnet den Wähler bei Benutzerinteraktion
dateField.setAllowCustomValue(false);    // Deaktiviert die manuelle Texteingabe
```

Diese Konfiguration garantiert, dass alle Datumseingaben über die Benutzeroberfläche des Wählers erfolgen, was nützlich ist, wenn du strikte Formatkontrolle und das Elimieren von Parsing-Problemen durch manuelle Eingaben wünschst.
:::

### Kalender manuell öffnen {#manually-open-the-calendar}

Um den Kalender programmgesteuert zu öffnen:

```java
picker.open();
```

Oder verwende den Alias:

```java
picker.show(); // dasselbe wie open()
```

### Wochen im Kalender anzeigen {#show-weeks-in-the-calendar}

Der Picker kann optional Wochenzahlen in der Kalenderansicht anzeigen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Das `MaskedDateFieldSpinner` erweitert das [`MaskedDateField`](#basics) um Steuerelemente, die es Benutzern ermöglichen, das Datum mit Pfeiltasten oder UI-Schaltflächen zu erhöhen oder zu verringern. Es bietet einen geführten Interaktionsstil, besonders nützlich in Desktop-Anwendungen.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Hauptmerkmale {#key-features}

- **Interaktives Datumsschritt:**  
  Verwende die Pfeiltasten oder Spin-Schaltflächen, um den Datumswert zu erhöhen oder zu verringern.

- **Anpassbare Schritt-Einheit:**  
  Wähle, welchen Teil des Datums du ändern möchtest, mit `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Optionen sind `TAG`, `WOCHE`, `MONAT` und `JAHR`.

- **Min/Max-Grenzen:**  
  Unterstützt weiterhin die Mindest- und Höchstwerte mit `setMin()` und `setMax()`.

- **Formatierte Ausgabe:**  
  Vollständig kompatibel mit Masken- und Lokalisierungseinstellungen aus `MaskedDateField`.

### Beispiel: Wöchentliche Schritte konfigurieren {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dies bewirkt, dass jeder Schritt beim Drehen das Datum um eine Woche vor- oder zurücksetzt.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
