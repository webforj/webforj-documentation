---
sidebar_position: 16
title: MaskDecorator
sidebar_class_name: new-content
_i18n_hash: 30ecd8eeaa79a3e5f963e319373d1378
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/MaskDecorator" top='true'/>

`MaskDecorator` ist eine statische Dienstprogrammklasse zum Anwenden von Masken auf Zeichenfolgen, Zahlen, Daten und Zeiten außerhalb eines Eingabefelds. Es verwendet die gleiche Maskensyntax wie die [masked field components](/docs/components/fields/masked/overview) von webforJ, was es einfach macht, Werte konsistent zu formatieren und zu analysieren—ob in einem Anzeigelabel, einem [`Table`](/docs/components/table/overview) Renderer oder an jedem anderen Ort in Ihrer App.

Verwenden Sie `MaskDecorator`, wenn Sie Werte programmatisch zur Anzeige formatieren müssen, anstatt für die interaktive Eingabe, z. B. in Tabellenzellen-Renderern, schreibgeschützten Labels, exportierten Berichten oder in jedem Kontext, in dem ein Formularfeld nicht geeignet ist. Für interaktive Formatierungen während des Tippens des Benutzers verwenden Sie stattdessen eine Maskenfeldkomponente.

## Maskierung von Zeichenfolgen {#masking-strings}

Verwenden Sie `forString()`, um eine Zeichenmaskierung auf einen normalen Zeichenfolgenwert anzuwenden:

```java
String result = MaskDecorator.forString("abc123", "AAA-000");
// → "ABC-123"
```

Die Maske steuert, welche Zeichen an jeder Position akzeptiert werden.

### Maskenzeichen {#string-mask-characters}

| Zeichen | Beschreibung |
|---------|-------------|
| `X`     | Jedes druckbare Zeichen |
| `a`     | Jedes alphabetische Zeichen |
| `A`     | Jedes alphabetische Zeichen; Kleinbuchstaben werden in Großbuchstaben umgewandelt |
| `0`     | Jede Ziffer (0–9) |
| `z`     | Jede Ziffer oder jeder Buchstabe |
| `Z`     | Jede Ziffer oder jeder Buchstabe; Kleinbuchstaben werden in Großbuchstaben umgewandelt |

Jedes andere Zeichen in der Maske wird als Literal behandelt und unverändert in die Ausgabe eingefügt. Ungültige Zeichen in der Eingabe werden stillschweigend ignoriert, kurze Eingaben werden mit Leerzeichen aufgefüllt und lange Eingaben werden beschnitten, um in die Maske zu passen.

### Beispiele {#string-examples}

```java
MaskDecorator.forString("1234567890", "(000) 000-0000");  // → "(123) 456-7890"
MaskDecorator.forString("a1b2c3",     "A0A 0A0");         // → "A1B 2C3"
MaskDecorator.forString("1234",       "ZZZZ-0000");        // → "1234-    " (aufgefüllt)
```

## Maskierung von Zahlen {#masking-numbers}

Verwenden Sie `forNumber()`, um einen numerischen Wert mit einer Zahlenmaske zu formatieren:

```java
String result = MaskDecorator.forNumber(1234567.89, "#,###,##0.00");
// → "1,234,567.89"
```

### Maskenzeichen {#number-mask-characters}

| Zeichen | Beschreibung |
|---------|-------------|
| `0`     | Wird immer durch eine Ziffer (0–9) ersetzt |
| `#`     | Unterdrückt führende Nullen. Wird durch das Füllzeichen links vom Dezimalpunkt ersetzt. Für nachfolgende Ziffern rechts, durch ein Leerzeichen oder eine Null ersetzt. Andernfalls durch eine Ziffer ersetzt |
| `,`     | Wird als Gruppierungsseparator verwendet. Wird durch das Füllzeichen ersetzt, wenn noch keine Ziffern plaziert wurden; andernfalls wird es als Komma angezeigt |
| `-`     | Zeigt `-` an, wenn die Zahl negativ ist; wird durch das Füllzeichen ersetzt, wenn positiv |
| `+`     | Zeigt `+` an, wenn positiv, oder `-`, wenn negativ |
| `$`     | Führt immer zu einem Dollarzeichen |
| `(`     | Fügt `(` ein, wenn die Zahl negativ ist; wird durch das Füllzeichen ersetzt, wenn positiv |
| `)`     | Fügt `)` ein, wenn die Zahl negativ ist; wird durch das Füllzeichen ersetzt, wenn positiv |
| `CR`    | Fügt `CR` für negative Zahlen ein; zwei Leerzeichen für positive Zahlen |
| `DR`    | Fügt `CR` für negative Zahlen ein; `DR` für positive Zahlen |
| `*`     | Fügt immer ein Sternchen ein |
| `.`     | Kennzeichnet das Dezimalzeichen. Wird durch das Füllzeichen ersetzt, wenn keine Ziffern in der Ausgabe erscheinen. Nach dem Dezimalzeichen wird das Füllzeichen zu einem Leerzeichen |
| `B`     | Wird immer zu einem Leerzeichen; jedes andere literale Zeichen wird unverändert kopiert |

Die Zeichen `-`, `+`, `$` und `(` können schwimmen: Das erste Vorkommen wird an die letzte Stelle verschoben, an der ein `#` oder `,` durch das Füllzeichen ersetzt wurde.

:::info Rundungsverhalten
`forNumber()` rundet Werte, um die Dezimalgenauigkeit in der Maske zu entsprechen. Zum Beispiel erzeugt `MaskDecorator.forNumber(12.34567, "###0.00")` `"  12.35"`.
:::

### Beispiele {#number-examples}

```java
MaskDecorator.forNumber(1234.5,    "###,##0.00");  // → "  1,234.50"
MaskDecorator.forNumber(-9876.0,   "###,##0.00-"); // → "  9,876.00-"
MaskDecorator.forNumber(42.0,      "$###,##0.00"); // → "     $42.00"
MaskDecorator.forNumber(0.5,       "#0.000");      // → " 0.500"
```

## Maskierung von Daten {#masking-dates}

Verwenden Sie `forDate()`, um einen `LocalDate`-Wert mit einer Datenmaske zu formatieren:

```java
LocalDate date = LocalDate.of(2025, 7, 4);
String result = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
// → "07/04/2025"
```

Verwenden Sie `parseDate()`, um eine maskierte Datumszeichenfolge in einen `LocalDate` zurückzupassen:

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl");
// → LocalDate.of(2025, 7, 4)
```

Eine lokalbewusste Überladung ist verfügbar, wenn Sie Zeichenfolgen analysieren, die Wochenziffernreferenzen enthalten:

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl", Locale.US);
```

### Datumsformat-Indikatoren {#date-format-indicators}

| Format | Beschreibung |
|--------|-------------|
| `%Y`   | Jahr        |
| `%M`   | Monat       |
| `%D`   | Tag         |

### Modifikatoren {#date-modifiers}

Ein optionaler Modifikator folgt unmittelbar dem Formatindikator:

| Modifikator | Beschreibung               |
|-------------|---------------------------|
| `z`         | Nullauffüllung            |
| `s`         | Kurze textliche Darstellung |
| `l`         | Lange textliche Darstellung |
| `p`         | Gepackte Zahl             |
| `d`         | Dezimal (Standardformat)  |

### Beispiele {#date-examples}

```java
LocalDate d = LocalDate.of(2025, 3, 5);

MaskDecorator.forDate(d, "%Mz/%Dz/%Yl");  // → "03/05/2025"
MaskDecorator.forDate(d, "%Dz.%Mz.%Yz");  // → "05.03.25"
MaskDecorator.forDate(d, "%Dl, %Ml %Dz");  // → " Mittwoch, 5. März "
MaskDecorator.forDate(d, "%Yl-%Mz-%Dz");  // → "2025-03-05"
```

## Maskierung von Zeiten {#masking-times}

Verwenden Sie `forTime()`, um einen `LocalTime`-Wert mit einer Zeitmaske zu formatieren:

```java
LocalTime time = LocalTime.of(14, 30, 0);
String result = MaskDecorator.forTime(time, "%Hz:%mz");
// → "14:30"
```

Verwenden Sie `parseTime()`, um eine maskierte Zeitzeichenfolge in `LocalTime` zurückzuarbeiten:

```java
LocalTime time = MaskDecorator.parseTime("14:30", "%Hz:%mz");
// → LocalTime.of(14, 30)
```

Eine lokalbewusste Überladung ist verfügbar, wenn Sie Zeichenfolgen analysieren, die lokalisierte AM/PM-Werte enthalten:

```java
LocalTime time = MaskDecorator.parseTime("02:30 pm", "%hz:%mz %p", Locale.US);
```

### Zeitformat-Indikatoren {#time-format-indicators}

| Format | Beschreibung          |
|--------|----------------------|
| `%H`   | Stunde (24-Stunden-Format) |
| `%h`   | Stunde (12-Stunden-Format) |
| `%m`   | Minute               |
| `%s`   | Sekunde              |
| `%p`   | am/pm                |

### Modifikatoren {#time-modifiers}

Zeitmasken verwenden die gleichen Modifikatoren wie Datenmasken. Siehe [Datumsmodifikatoren](#date-modifiers).

### Beispiele {#time-examples}

```java
LocalTime t = LocalTime.of(9, 5, 30);

MaskDecorator.forTime(t, "%Hz:%mz:%sz");  // → "09:05:30"
MaskDecorator.forTime(t, "%hz:%mz %p");   // → "09:05 am"
MaskDecorator.forTime(t, "%Hz%mz");       // → "0905"
```

## Maskierung von Datum und Zeit {#masking-datetime}

Verwenden Sie `forDateTime()`, um einen `LocalDateTime`-Wert mit einer kombinierten Daten- und Zeitmaske zu formatieren:

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);
String result = MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");
// → "07/04/2025 14:30"
```

### Format-Indikatoren {#datetime-format-indicators}

`forDateTime()` unterstützt alle Daten- und Zeitformatindikatoren in beliebiger Kombination. Siehe [Datumsformat-Indikatoren](#date-format-indicators) und [Zeitformat-Indikatoren](#time-format-indicators) für die vollständige Liste.

### Modifikatoren {#datetime-modifiers}

Alle in [Datumsmodifikatoren](#date-modifiers) beschriebenen Modifikatoren gelten sowohl für den Daten- als auch für den Zeitteil einer kombinierten Maske.

### Beispiele {#datetime-examples}

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);

MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");      // → "07/04/2025 14:30"
MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz:%sz");  // → "07/04/2025 14:30:00"
MaskDecorator.forDateTime(dt, "%Dz.%Mz.%Yz %hz:%mz %p");  // → "04.07.25 02:30 pm"
```

## Umgang mit null-Ergebnissen {#handling-null-results}

:::warning
Alle `for*()`- und `parse*()`-Methoden geben `null` zurück, wenn die Eingabe ungültig ist oder nicht analysiert werden kann. Überprüfen Sie immer, ob das Ergebnis nicht null ist, bevor Sie es in Ihrer App-Logik verwenden.
:::

```java
String formatted = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
if (formatted != null) {
  label.setText(formatted);
}
```
