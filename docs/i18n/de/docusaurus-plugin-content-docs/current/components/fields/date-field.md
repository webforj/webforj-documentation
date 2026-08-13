---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: 173c4a1d080dc6e0c01828131af61c08
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

Die `DateField`-Komponente ermöglicht es Benutzern, ein Datum nach Jahr, Monat und Tag einzugeben oder auszuwählen. Sie verarbeitet die Validierung automatisch, sodass falsch formatierte Daten erfasst werden, bevor das Formular eingereicht wird.

<!-- INTRO_END -->

## Verwendung von `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Feldkomponenten bereitstellt. Das folgende Beispiel erstellt Abreise- und Rückkehr-Datenfelder, die synchron bleiben, mit Mindest- und Höchstbeschränkungen, um den auswählbaren Bereich zu beschränken.

<ComponentDemo
path='/webforj/datefield'
files={['src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java']}
/>

## Feldwert (`LocalDate`) {#field-value-localdate}

Die `DateField`-Komponente speichert ihren Wert intern als `LocalDate`-Objekt, das ein Datum ohne Zeit- oder Zeitzoneninformationen darstellt. Dies ermöglicht eine präzise Handhabung von kalenderbasierten Eingaben über verschiedene Systeme hinweg.

:::info Anzeigewert VS geparster Wert
Während der **Anzeigewert** sich an die Browsersprache des Benutzers anpasst und sicherstellt, dass die formatierung regional vertraut ist (z. B. `MM/DD/YYYY` in den Vereinigten Staaten oder `DD.MM.YYYY` in Europa), basiert der **geparste Wert** stets auf dem festen Format `yyyy-MM-dd`.
:::

### Abrufen und Setzen des `LocalDate`-Wertes {#getting-and-setting-the-localdate-value}

Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`:

```java
LocalDate value = dateField.getValue();
```

Um den Wert programmgesteuert festzulegen, verwenden Sie die Methode `setValue()`:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Verwendung von `setText()` {#using-settext}

Sie können auch einen Wert mithilfe eines rohen Strings zuweisen, dieser muss jedoch dem genauen Format `yyyy-MM-dd` entsprechen:

```java
dateField.setText("2024-04-27"); // gültig

dateField.setText("04/27/2024"); // ungültig
```

:::warning
 Bei der Verwendung der Methode `setText()` wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe im Format `yyyy-MM-dd` nicht parsen kann.
:::

## Verwendungen {#usages}

Das `DateField` ist ideal, um Daten in Ihrer App auszuwählen und anzuzeigen. Hier sind einige Beispiele, wann das `DateField` verwendet werden sollte:

1. **Veranstaltungsplanung und Kalender**: Datumsfelder sind unerlässlich in Apps, die die Planung von Veranstaltungen, die Buchung von Terminen oder das Festhalten wichtiger Daten beinhalten.

2. **Formulareingaben**: Vereinfachen Sie den Auswahlprozess für ein Datum für einen Benutzer, der ein Formular ausfüllt, das ein Datum erfordert, wie zum Beispiel einen Geburtstag.

3. **Buchungs- und Reservierungssysteme**: Apps, die Buchungs- und Reservierungssysteme beinhalten, benötigen oft von den Benutzern die Eingabe spezifischer Daten. Ein Datumsfeld vereinfacht den Prozess und sorgt für eine akkurate Datenauswahl.

4. **Aufgabenmanagement und Fristen**: Datumsfelder sind von Wert in Apps, die Aufgabenmanagement oder das Setzen von Fristen beinhalten. Benutzer können Fälligkeitsdaten, Startdaten oder andere zeitkritische Informationen einfach angeben.

## Min- und Max-Wert {#min-and-max-value}

### Der Min-Wert {#the-min-value}
Die Methode `setMin()` definiert das früheste Datum, das ein Benutzer in die Komponente eingeben kann. Wenn die Eingabe früher als das angegebene Minimum ist, schlägt die Validierung fehl. Wenn sie zusammen mit `setMax()` verwendet wird, muss das Minimum ein Datum sein, das dasselbe oder früher als das Maximum ist.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Minimal erlaubt: 1. Januar 2023
```

### Der Max-Wert {#the-max-value}
Die Methode `setMax()` definiert das späteste Datum, das die Komponente akzeptiert. Wenn das eingegebene Datum später als das angegebene Maximum ist, ist die Eingabe ungültig. Wenn beide Werte definiert sind, muss das Maximum ein Datum sein, das dasselbe oder später als das Minimum ist.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maximal erlaubt: 31. Dezember 2023
```

## Statische Hilfsmittel {#static-utilities}

Die `DateField`-Klasse bietet auch die folgenden statischen Hilfsmethoden:

- `fromDate(String dateAsString)`: Konvertiert einen Datumsstring im Format `yyyy-MM-dd` in ein `LocalDate`-Objekt, das dann mit diesem Feld oder anderswo verwendet werden kann.

- `toDate(LocalDate date)`: Konvertiert ein `LocalDate`-Objekt in einen Datumsstring im Format `yyyy-MM-dd`.

- `isValidDate(String dateAsString)`: Überprüft, ob der angegebene String ein gültiges Datum im Format `yyyy-MM-dd` ist.

## Best Practices {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `DateField`-Komponente sicherzustellen, beachten Sie die folgenden Best Practices:

- **Barrierefreiheit**: Nutzen Sie geeignete Beschriftungen, um sicherzustellen, dass Benutzer mit assistiven Technologien leicht auf die Datumsfelder in Ihrer App zugreifen und diese verwenden können.

- **Automatische Vervollständigung des aktuellen Datums**: Wenn es für den Anwendungsfall Ihrer App geeignet ist, füllen Sie das Datumsfeld automatisch mit dem aktuellen Datum aus.
