---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
sidebar_class_name: updated-content
_i18n_hash: 9f7f8e2c82305667ea1ace187df17915
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

<ParentLink parent="Field" />

Das `DateField` ist eine Feldkomponente, die es Benutzern ermöglicht, Daten nach Jahr, Monat und Tag einzugeben oder auszuwählen. Es bietet eine intuitive und effiziente Möglichkeit, datumsbezogene Informationen in verschiedenen Apps zu verarbeiten, und bietet die Flexibilität, die Eingaben der Benutzer zu validieren.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Feldwert (`LocalDate`) {#field-value-localdate}

Die `DateField`-Komponente speichert ihren Wert intern als ein `LocalDate`-Objekt, das ein Datum ohne Zeit- oder Zeitzoneninformationen darstellt. Dies ermöglicht eine genaue Verarbeitung von kalenderbasierten Eingaben über verschiedene Systeme hinweg.

:::info Angezeigter Wert VS geparster Wert 
Während der **angezeigte Wert** sich an die Browsersprache des Benutzers anpasst und sicherstellt, dass das Format regional vertraut ist (z. B. `MM/DD/YYYY` in den Vereinigten Staaten oder `DD.MM.YYYY` in Europa), basiert der **geparste Wert** immer auf dem festen Format `yyyy-MM-dd`.
:::

### Abrufen und Setzen des `LocalDate`-Wertes {#getting-and-setting-the-localdate-value}

Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`:

```java
LocalDate value = dateField.getValue();
```

Um den Wert programmgesteuert zu setzen, verwenden Sie die Methode `setValue()`:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Verwendung von `setText()` {#using-settext}

Sie können auch einen Wert mithilfe eines Rohstrings zuweisen, aber er muss dem exakten Format `yyyy-MM-dd` folgen:

```java
dateField.setText("2024-04-27"); // gültig

dateField.setText("04/27/2024"); // ungültig
```

:::warning
 Bei Verwendung der Methode `setText()` wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe im Format `yyyy-MM-dd` nicht parsen kann.
:::

## Verwendungen {#usages}

Das `DateField` ist ideal für die Auswahl und Anzeige von Daten in Ihrer App. Hier sind einige Beispiele, wann das `DateField` verwendet werden sollte:

1. **Veranstaltungsplanung und Kalender**: Datumsfelder sind unerlässlich in Apps, die die Planung von Veranstaltungen, Buchungen von Terminen oder das Verfolgen wichtiger Daten umfassen.

2. **Formulareingaben**: Vereinfachen Sie den Auswahlprozess für ein Benutzer, der ein Formular ausfüllt, das ein Datum erfordert, wie beispielsweise ein Geburtsdatum.

3. **Buchungs- und Reservierungssysteme**: Apps, die Buchungs- und Reservierungssysteme umfassen, erfordern oft, dass Benutzer spezifische Daten eingeben. Ein Datumsfeld vereinfacht den Prozess und gewährleistet eine genaue Datumswahl.

4. **Aufgabenverwaltung und Fristen**: Datumsfelder sind wertvoll in Apps, die Aufgabenverwaltung oder das Setzen von Fristen betreffen. Benutzer können leicht Fälligkeitstermine, Startdaten oder andere zeitkritische Informationen angeben.

## Minimal- und Maximalwert {#min-and-max-value}

### Der Minimalwert {#the-min-value}
Die Methode `setMin()` definiert das früheste Datum, das ein Benutzer in die Komponente eingeben kann. Wenn die Eingabe früher ist als das angegebene Minimum, schlägt die Validierung fehl. Wenn sie zusammen mit `setMax()` verwendet wird, muss das Minimum ein Datum sein, das gleich oder früher als das Maximum ist.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Mindestzulässig: 1. Januar 2023
```

### Der Maximalwert {#the-max-value}
Die Methode `setMax()` definiert das späteste Datum, das die Komponente akzeptiert. Wenn das eingegebene Datum später als das angegebene Maximum ist, ist die Eingabe ungültig. Wenn beide Werte definiert sind, muss das Maximum ein Datum sein, das gleich oder später als das Minimum ist.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Höchstens zulässig: 31. Dezember 2023
```

## Statische Hilfsfunktionen {#static-utilities}

Die `DateField`-Klasse bietet auch die folgenden statischen Hilfsmethoden:

- `fromDate(String dateAsString)`: Konvertiert einen Datumsstring im Format `yyyy-MM-dd` in ein `LocalDate`-Objekt, das dann mit diesem Feld oder anderswo verwendet werden kann.

- `toDate(LocalDate date)`: Konvertiert ein `LocalDate`-Objekt in einen Datumsstring im Format `yyyy-MM-dd`.

- `isValidDate(String dateAsString)`: Überprüft, ob der angegebene String ein gültiges Datum im Format `yyyy-MM-dd` ist.

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `DateField`-Komponente zu gewährleisten, berücksichtigen Sie die folgenden besten Praktiken:

- **Barrierefreiheit**: Verwenden Sie geeignete Beschriftungen, um sicherzustellen, dass Benutzer mit Hilfstechnologien leicht zu den Datumsfeldern in Ihrer App navigieren und diese verwenden können.

- **Auto-Populate aktuelles Datum**: Wenn passend für den Anwendungsfall Ihrer App, auto-populate das Datumsfeld mit dem aktuellen Datum.
