---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: e90e93f7db172a33b2ce205bfd6a6b3c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

Die `DateTimeField`-Komponente ermöglicht es Benutzern, sowohl ein Datum als auch eine Uhrzeit in einem einzigen Feld einzugeben, einschließlich Jahr, Monat, Tag, Stunden und Minuten. Sie validiert die Eingabe auf Genauigkeit und kann einen Datum-Uhrzeit-Wähler anzeigen, um die Auswahl zu erleichtern.

<!-- INTRO_END -->

## Verwendung von `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Feldkomponenten bereitstellt. Das folgende Beispiel erstellt ein beschriftetes `DateTimeField`, um ein Abfahrtsdatum und eine Abfahrtszeit auszuwählen.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Anwendungsfälle {#usages}

Das `DateTimeField` wird am besten in Szenarien verwendet, in denen das Erfassen oder Anzeigen sowohl von Datum **als auch** Uhrzeit für Ihre App von wesentlicher Bedeutung ist. Hier sind einige Beispiele, wann das `DateTimeField` verwendet werden sollte:

1. **Ereignisplanung und Kalender**: Ermöglichen Sie es Benutzern, effizient Ereignisse zu planen, Termine zu buchen und ihre Kalender zu verwalten, indem Sie ihnen eine einzige Komponente geben, mit der sie Datum und Uhrzeit auswählen können.
<!-- vale off -->
2. **Check-in und Check-out**: Erleichtern Sie die Auswahl von Check-in- und Check-out-Zeiten, wenn der Zeitraum mehrere Tage umfassen kann.
<!-- vale on -->
3. **Datenprotokollierung und Zeitstempel**: Nutzen Sie `DateTimeFields` für Apps, die das Datum und die Uhrzeit aufzeichnen, zu denen Ereignisse auftreten oder wann ein Benutzer Daten übermittelt.

4. **Aufgabenverwaltung und Fristen**: `DateTimeFields` sind wertvoll in Anwendungen, die mit Aufgabenverwaltung oder Fristsetzung zu tun haben, bei denen sowohl Datum als auch Uhrzeit für eine genaue Planung relevant sind.

## Feldwert (`LocalDateTime`) {#field-value-localdatetime}

Intern repräsentiert die `DateTimeField`-Komponente ihren Wert mithilfe eines `LocalDateTime`-Objekts aus dem `java.time`-Paket. Dies bietet präzise Kontrolle über sowohl die Datums- als auch die Uhrzeitkomponenten der Eingabe.

Während der **Client-seitige** Wert basierend auf der Locale des Browsers des Benutzers gerendert wird (z. B. Datums- und Uhrzeitformate, die lokalen Konventionen entsprechen), folgt der **geparste** Wert einer strengen und vorhersehbaren Struktur: **`yyyy-MM-ddTHH:mm:ss`**.

### Abrufen und Setzen des Wertes {#getting-and-setting-the-value}

Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`:

```java
LocalDateTime value = dateTimeField.getValue();
```

Um den Wert programmgesteuert zu setzen, verwenden Sie die Methode `setValue()`:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Verwendung von `setText()` {#using-settext}

Wenn Sie den Wert über einen Rohstring setzen möchten, muss er dem exakten Format `yyyy-MM-ddTHH:mm:ss` entsprechen.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // gültig

dateTimeField.setText("24-04-27T14:30:00"); // ungültig
```

:::warning
 Bei Verwendung der Methode `setText()` wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe im Format `yyyy-MM-ddTHH:mm:ss` nicht parsen kann.
:::

## Statische Hilfsfunktionen {#static-utilities}

Die DateTimeField-Klasse bietet auch die folgenden statischen Hilfsmethoden:

- `fromDateTime(String dateTimeAsString)`: Konvertiert einen Datum-Uhrzeit-String im Format `yyyy-MM-ddTHH:mm:ss` in ein `LocalDateTime`-Objekt, das dann mit dieser Klasse oder anderswo verwendet werden kann.

- `toDateTime(LocalDateTime dateTime)`: Konvertiert ein `LocalDateTime`-Objekt in einen Datum-Uhrzeit-String im Format `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)`: Überprüft, ob der gegebene String ein gültiges Datum und Uhrzeit im Format `yyyy-MM-ddTHH:mm:ss` darstellt. Dies gibt einen booleschen Wert zurück: true, wenn dies der Fall ist, andernfalls false.

## Min- und Max-Wert {#min-and-max-value}

### Der Min-Wert {#the-min-value}

Wenn der eingegebene Wert in die Komponente früher ist als die angegebene Mindestzeitstempel, wird die Einschränkungsvalidierung der Komponente fehlschlagen. Wenn sowohl die Min- als auch die Max-Werte gesetzt sind, muss der Min-Wert ein Zeitstempel sein, der gleich oder früher als der Max-Wert ist.

```java
// Setze den minimal erlaubten Zeitstempel: 1. Januar 2023 um 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### Der Max-Wert {#the-max-value}

Wenn der eingegebene Wert in die Komponente später ist als der angegebene Maximalzeitstempel, wird die Einschränkungsvalidierung der Komponente fehlschlagen. Wenn sowohl die Min- als auch die Max-Werte gesetzt sind, muss der Max-Wert ein Zeitstempel sein, der gleich oder später als der Min-Wert ist.

```java
// Setze den maximal erlaubten Zeitstempel: 31. Dezember 2023 um 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `DateTimeField`-Komponente sicherzustellen, sollten folgende beste Praktiken berücksichtigt werden:

- **Lokalisierte Anzeige von Daten**: Die Lokalisierung des Datumsformats und die Berücksichtigung regionaler Präferenzen stellt sicher, dass Daten in einem vertrauten Format für den Benutzer angezeigt werden.

- **Zeitzonen einbeziehen**: Wenn Ihre App zeitabhängige Informationen über verschiedene Zeitzonen hinweg behandelt, sollten Sie die Auswahl der Zeitzone zusammen mit dem Datumsfeld einbeziehen, um eine genaue Darstellung von Datum und Uhrzeit sicherzustellen.

- **Barrierefreiheit**: Nutzen Sie das `DateTimeField` mit Blick auf die Barrierefreiheit. Stellen Sie sicher, dass es den Standards für Barrierefreiheit entspricht, z. B. durch Bereitstellung geeigneter Beschriftungen und Kompatibilität mit Hilfstechnologien.

- **Automatisches Ausfüllen des aktuellen Datums**: Überlegen Sie, die Möglichkeit anzubieten, das aktuelle Datum und die aktuelle Uhrzeit als Standardwert im Datums- und Uhrzeitfeld automatisch auszufüllen, wenn dies für den Anwendungsfall Ihrer App angemessen ist.
