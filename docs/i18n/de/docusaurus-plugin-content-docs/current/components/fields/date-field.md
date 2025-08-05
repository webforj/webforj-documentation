---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
sidebar_class_name: updated-content
_i18n_hash: ee9981c57d9964a3f759b116dbd75af2
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

<ParentLink parent="Field" />

Das `DateField` ist eine Feldkomponente, die es Benutzern ermöglicht, Daten nach Jahr, Monat und Tag einzugeben oder auszuwählen. Es bietet eine intuitive und effiziente Möglichkeit, datenbezogene Informationen in verschiedenen Apps zu verarbeiten, und bietet die Flexibilität, die Eingabe eines Benutzers zu validieren.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Feldwert (`LocalDate`) {#field-value-localdate}

Die `DateField`-Komponente speichert ihren Wert intern als ein `LocalDate`-Objekt, das ein Datum ohne Zeit- oder Zeitzoneninformationen darstellt. Dies ermöglicht eine genaue Handhabung von kalenderbasierten Eingaben in verschiedenen Systemen.

:::info Angezeigter Wert VS geparster Wert 
Während der **angezeigte Wert** sich an die Locale des Browsers des Benutzers anpasst und eine regional vertraute Formatierung gewährleistet (z.B. `MM/DD/YYYY` in den USA oder `DD.MM.YYYY` in Europa), basiert der **geparste Wert** stets auf dem festen Format `yyyy-MM-dd`.
:::

### Den `LocalDate`-Wert abrufen und setzen {#getting-and-setting-the-localdate-value}

Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`:

```java
LocalDate value = dateField.getValue();
```

Um den Wert programmgesteuert zu setzen, verwenden Sie die Methode `setValue()`:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### `setText()` verwenden {#using-settext}

Sie können auch einen Wert mit einem Rohstring zuweisen, jedoch muss er genau dem `yyyy-MM-dd`-Format folgen:

```java
dateField.setText("2024-04-27"); // gültig

dateField.setText("04/27/2024"); // ungültig
```

:::warning
 Wenn Sie die Methode `setText()` verwenden, wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente den Eingabewert nicht im Format `yyyy-MM-dd` parsen kann.
:::

## Verwendungen {#usages}

Das `DateField` eignet sich ideal zur Auswahl und Anzeige von Daten in Ihrer App. Hier sind einige Beispiele, wann Sie das `DateField` verwenden sollten:

1. **Veranstaltungsplanung und Kalender**: Datumsfelder sind wesentlich in Apps, die die Planung von Veranstaltungen, die Buchung von Terminen oder die Verfolgung wichtiger Daten beinhalten.

2. **Formulareingaben**: Vereinfachen Sie den Datumsauswahlprozess für einen Benutzer, der ein Formular ausfüllt, das ein Datum erfordert, wie z.B. ein Geburtsdatum.

3. **Buchungs- und Reservierungssysteme**: Apps, die Buchung und Reservierungssysteme beinhalten, erfordern oft, dass Benutzer spezifische Daten eingeben. Ein Datumsfeld rationalisiert den Prozess und gewährleistet eine genaue Datumsauswahl.

4. **Aufgabenmanagement und Fristen**: Datumsfelder sind wertvoll in Apps, die Aufgabenmanagement oder Fristen beinhalten. Benutzer können einfach Fälligkeitstermine, Starttermine oder andere zeitabhängige Informationen angeben.

## Minimal- und Maximalwert {#min-and-max-value}

### Der Minimalwert {#the-min-value}
Die Methode `setMin()` definiert das früheste Datum, das ein Benutzer in die Komponente eingeben kann. Wenn die Eingabe früher ist als das angegebene Minimum, schlägt die Einschränkungsvalidierung fehl. Wenn `setMax()` verwendet wird, muss das Minimum ein Datum sein, das gleich oder früher als das Maximum ist.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Mindestzulässig: 1. Januar 2023
```

### Der Maximalwert {#the-max-value}
Die Methode `setMax()` definiert das späteste Datum, das die Komponente akzeptiert. Wenn das eingegebene Datum später als das angegebene Maximum ist, ist die Eingabe ungültig. Wenn beide Werte definiert sind, muss das Maximum ein Datum sein, das gleich oder später als das Minimum ist.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Höchstzulässig: 31. Dezember 2023
```

## Statische Hilfsfunktionen {#static-utilities}

Die `DateField`-Klasse bietet auch die folgenden statischen Hilfsfunktionen:

- `fromDate(String dateAsString)`: Konvertiert einen Datumsstring im `yyyy-MM-dd`-Format in ein `LocalDate`-Objekt, das dann mit diesem Feld oder andernorts verwendet werden kann.

- `toDate(LocalDate date)`: Konvertiert ein `LocalDate`-Objekt in einen Datumsstring im `yyyy-MM-dd`-Format.

- `isValidDate(String dateAsString)`: Überprüft, ob der angegebene String ein gültiges `yyyy-MM-dd`-Datum ist.

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `DateField`-Komponente zu gewährleisten, sollten Sie die folgenden besten Praktiken in Betracht ziehen:

- **Zugänglichkeit**: Verwenden Sie geeignete Beschriftungen, damit Benutzer mit unterstützenden Technologien leicht zu den Datumsfeldern in Ihrer App navigieren und diese verwenden können.

- **Aktuelles Datum automatisch ausfüllen**: Wenn es für den Anwendungsfall Ihrer App geeignet ist, füllen Sie das Datumsfeld automatisch mit dem aktuellen Datum aus.
