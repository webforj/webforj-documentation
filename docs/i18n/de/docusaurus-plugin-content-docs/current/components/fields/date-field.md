---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: bf6829e0fafbd0c69a49a5563e8a298b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

Die `DateField`-Komponente ermöglicht es Benutzern, ein Datum nach Jahr, Monat und Tag einzugeben oder auszuwählen. Sie führt die Validierung automatisch durch, sodass falsch formatierte Daten noch vor dem Absenden des Formulars erkannt werden.

<!-- INTRO_END -->

## Verwendung der `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` erweitert die gemeinsame `Field`-Klasse, die allgemeine Funktionen für alle Feldkomponenten bereitstellt. Das folgende Beispiel erstellt Abflug- und Rückkehr-Datumsfelder, die synchron bleiben, mit Min- und Max-Beschränkungen, um den auswählbaren Bereich einzuschränken.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Feldwert (`LocalDate`) {#field-value-localdate}

Die `DateField`-Komponente speichert ihren Wert intern als `LocalDate`-Objekt, das ein Datum ohne Zeit- oder Zeitzoneninformationen darstellt. Dies ermöglicht eine präzise Verarbeitung kalenderbasierter Eingaben über verschiedene Systeme hinweg.

:::info Angezeigter Wert VS analysierter Wert 
Während der **angezeigte Wert** sich an die Browsersprache des Benutzers anpasst und eine regional vertraute Formatierung gewährleistet (z. B. `MM/DD/YYYY` in den Vereinigten Staaten oder `DD.MM.YYYY` in Europa), basiert der **analysierte Wert** stets auf dem festen Format `yyyy-MM-dd`.
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

Sie können auch einen Wert mithilfe eines Rohstrings zuweisen, aber dieser muss dem genauen Format `yyyy-MM-dd` folgen:

```java
dateField.setText("2024-04-27"); // gültig

dateField.setText("04/27/2024"); // ungültig
```

:::warning
 Wenn die Methode `setText()` verwendet wird, wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe nicht im Format `yyyy-MM-dd` analysieren kann.
:::

## Verwendungen {#usages}

Das `DateField` ist ideal, um Daten in Ihrer App auszuwählen und anzuzeigen. Hier sind einige Beispiele für die Verwendung des `DateField`:

1. **Ereignisplanung und Kalender**: Datumsfelder sind unerlässlich in Apps, die die Planung von Ereignissen, die Buchung von Terminen oder das Verfolgen wichtiger Daten beinhalten.

2. **Formular Eingaben**: Vereinfachen Sie den Auswahlprozess des Datums für einen Benutzer, der ein Formular ausfüllen muss, das ein Datum erfordert, wie ein Geburtsdatum.

3. **Buchungs- und Reservierungssysteme**: Apps, die Buchungs- und Reservierungssysteme beinhalten, erfordern häufig, dass Benutzer bestimmte Daten eingeben. Ein Datumsfeld vereinfacht den Prozess und sorgt für eine korrekte Datenauswahl.

4. **Aufgabenverwaltung und Fristen**: Datumsfelder sind wertvoll in Apps, die Aufgabenverwaltung oder das Setzen von Fristen betreffen. Benutzer können leicht Fälligkeitstermine, Startdaten oder andere zeitkritische Informationen angeben.

## Minimale und maximale Werte {#min-and-max-value}

### Der Mindestwert {#the-min-value}
Die Methode `setMin()` definiert das früheste Datum, das ein Benutzer in die Komponente eingeben kann. Wenn die Eingabe früher als das angegebene Minimum ist, schlägt die Validierung fehl. In Verbindung mit `setMax()` muss das Minimum ein Datum sein, das dasselbe oder früher als das Maximum ist.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Mindestwert: 1. Januar 2023
```

### Der Höchstwert {#the-max-value}
Die Methode `setMax()` definiert das späteste Datum, das die Komponente akzeptiert. Wenn das eingegebene Datum später als das angegebene Maximum ist, ist die Eingabe ungültig. Wenn beide Werte definiert sind, muss das Maximum ein Datum sein, das dasselbe oder später als das Minimum ist.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Höchstwert: 31. Dezember 2023
```

## Statische Hilfsfunktionen {#static-utilities}

Die `DateField`-Klasse bietet auch die folgenden statischen Hilfsfunktionen:

- `fromDate(String dateAsString)`: Konvertiert einen Datumsstring im Format `yyyy-MM-dd` in ein `LocalDate`-Objekt, das dann mit diesem Feld oder anderswo verwendet werden kann.

- `toDate(LocalDate date)`: Konvertiert ein `LocalDate`-Objekt in einen Datumsstring im Format `yyyy-MM-dd`.

- `isValidDate(String dateAsString)`: Überprüft, ob der angegebene String ein gültiges Datum im Format `yyyy-MM-dd` ist.

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `DateField`-Komponente zu gewährleisten, ziehen Sie die folgenden besten Praktiken in Betracht:

- **Barrierefreiheit**: Verwenden Sie geeignete Beschriftungen, um sicherzustellen, dass Benutzer mit unterstützenden Technologien leicht auf die Datumsfelder in Ihrer App zugreifen und diese verwenden können.

- **Automatische Befüllung des aktuellen Datums**: Wenn es für den Anwendungsfall Ihrer App geeignet ist, füllen Sie das Datumsfeld automatisch mit dem aktuellen Datum aus.
