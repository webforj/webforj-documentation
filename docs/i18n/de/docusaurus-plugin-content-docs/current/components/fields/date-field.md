---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: a996ccdd786de35de1dece0a5fc8f27a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

Die `DateField`-Komponente ermöglicht es Benutzern, ein Datum nach Jahr, Monat und Tag einzugeben oder auszuwählen. Sie verarbeitet die Validierung automatisch, sodass falsch formatierte Daten vor dem Absenden des Formulars erfasst werden.

<!-- INTRO_END -->

## Verwendung von `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Komponentenfelder bereitstellt. Das folgende Beispiel erstellt Abfahrts- und Rückkehr-DateFields, die synchron bleiben, mit Min- und Max-Beschränkungen, um den wählbaren Bereich einzuschränken.

<ComponentDemo
path='/webforj/datefield'
files={['src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java']}
/>

## Feldwert (`LocalDate`) {#field-value-localdate}

Die `DateField`-Komponente speichert ihren Wert intern als `LocalDate`-Objekt, das ein Datum ohne Zeit- oder Zeitzoneninformationen darstellt. Dies ermöglicht eine genaue Verarbeitung von kalenderbasierten Eingaben über unterschiedliche Systeme hinweg.

:::info Angezeigter Wert VS geparster Wert 
Während der **angezeigte Wert** sich an die Spracheinstellungen des Browsers des Benutzers anpasst und somit regional vertraute Formate sicherstellt (z. B. `MM/DD/YYYY` in den Vereinigten Staaten oder `DD.MM.YYYY` in Europa), basiert der **geparste Wert** immer auf dem festen Format `yyyy-MM-dd`.
:::

### Abrufen und Setzen des `LocalDate`-Werts {#getting-and-setting-the-localdate-value}

Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`:

```java
LocalDate value = dateField.getValue();
```

Um den Wert programmgesteuert festzulegen, verwenden Sie die Methode `setValue()`:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Verwendung von `setText()` {#using-settext}

Sie können auch einen Wert mit einer Rohzeichenfolge zuweisen, diese muss jedoch dem genauen Format `yyyy-MM-dd` folgen:

```java
dateField.setText("2024-04-27"); // gültig

dateField.setText("04/27/2024"); // ungültig
```

:::warning
 Beim Verwenden der Methode `setText()` wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe im Format `yyyy-MM-dd` nicht parsen kann.
:::

## Anwendungen {#usages}

Das `DateField` ist ideal zum Auswählen und Anzeigen von Daten in Ihrer Anwendung. Hier sind einige Beispiele, wann Sie das `DateField` verwenden sollten:

1. **Ereignisplanung und Kalender**: Datumsfelder sind unerlässlich in Apps, die die Planung von Ereignissen, die Buchung von Terminen oder das Verfolgen wichtiger Daten beinhalten.

2. **Formulareingaben**: Vereinfachen Sie den Auswahlprozess für ein Datum für einen Benutzer, der ein Formular ausfüllt, das ein Datum erfordert, wie z.B. ein Geburtstag.

3. **Buchungs- und Reservierungssysteme**: Apps, die Buchungs- und Reservierungssysteme beinhalten, erfordern oft, dass Benutzer spezifische Daten eingeben. Ein Datumsfeld vereinfacht den Prozess und sorgt für eine genaue Datenauswahl.

4. **Aufgabenverwaltung und Fristen**: Datumsfelder sind wertvoll in Apps, die Aufgabenverwaltung oder das Setzen von Fristen beinhalten. Benutzer können einfach Fälligkeitstermine, Startdaten oder andere zeitkritische Informationen angeben.

## Min- und Max-Wert {#min-and-max-value}

### Der Min-Wert {#the-min-value}
Die Methode `setMin()` definiert das früheste Datum, das ein Benutzer in die Komponente eingeben kann. Wenn die Eingabe früher als das angegebene Minimum ist, schlägt die Validierung fehl. Wenn sie zusammen mit `setMax()` verwendet wird, muss das Minimum ein Datum sein, das dasselbe oder früher als das Maximum ist.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Mindestzulässig: 1. Januar 2023
```

### Der Max-Wert {#the-max-value}
Die Methode `setMax()` definiert das späteste Datum, das die Komponente akzeptiert. Wenn das eingegebene Datum später als das angegebene Maximum ist, ist die Eingabe ungültig. Wenn beide Werte definiert sind, muss das Maximum ein Datum sein, das dasselbe oder später als das Minimum ist.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Höchstens zulässig: 31. Dezember 2023
```

## Statische Hilfsfunktionen {#static-utilities}

Die `DateField`-Klasse bietet auch die folgenden statischen Hilfsmethoden:

- `fromDate(String dateAsString)`: Konvertiert eine Datumszeichenfolge im Format `yyyy-MM-dd` in ein `LocalDate`-Objekt, das dann mit diesem Feld oder anderswo verwendet werden kann.

- `toDate(LocalDate date)`: Konvertiert ein `LocalDate`-Objekt in eine Datumszeichenfolge im Format `yyyy-MM-dd`.

- `isValidDate(String dateAsString)`: Überprüft, ob die angegebene Zeichenfolge ein gültiges Datum im Format `yyyy-MM-dd` ist.

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `DateField`-Komponente zu gewährleisten, sollten Sie die folgenden Best Practices berücksichtigen:

- **Barrierefreiheit**: Verwenden Sie geeignete Beschriftungen, um sicherzustellen, dass Benutzer mit unterstützenden Technologien die Datumsfelder in Ihrer Anwendung leicht navigieren und verwenden können.

- **Automatisches Ausfüllen des aktuellen Datums**: Wenn es für den Anwendungsfall Ihrer App sinnvoll ist, füllen Sie das Datumsfeld automatisch mit dem aktuellen Datum aus.
