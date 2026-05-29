---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 1214ec1391242fb6b3ff7f60664a6f79
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

Die `DateTimeField`-Komponente ermöglicht es Benutzern, sowohl ein Datum als auch eine Uhrzeit in einem einzigen Feld einzugeben, und deckt Jahr, Monat, Tag, Stunden und Minuten ab. Sie validiert die Eingabe auf Richtigkeit und kann einen Datum-Uhrzeit-Auswahl-Dialog präsentieren, um die Auswahl zu erleichtern.

<!-- INTRO_END -->

## Verwendung von `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Field-Komponenten bereitstellt. Im folgenden Beispiel wird ein beschriftetes `DateTimeField` zum Auswählen eines Abfahrtsdatums und einer Uhrzeit erstellt.

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## Verwendungen {#usages}

Das `DateTimeField` eignet sich am besten in Szenarien, in denen das Erfassen oder Anzeigen von Datum **und** Uhrzeit für Ihre App wichtig ist. Hier sind einige Beispiele, wann Sie das `DateTimeField` verwenden sollten:

1. **Ereignisplanung und Kalender**: Ermöglichen Sie es Benutzern, Ereignisse effizient zu planen, Termine zu buchen und ihre Kalender zu verwalten, indem Sie ihnen eine einzige Komponente zur Verfügung stellen, die es ihnen ermöglicht, Datum und Uhrzeit auszuwählen.
<!-- vale off -->
2. **Check-in und Check-out**: Erleichtern Sie die Auswahl von Check-in- und Check-out-Zeiten, wenn der Zeitraum mehrere Tage umfassen kann.
<!-- vale on -->
3. **Datenprotokollierung und Zeitstempel**: Nutzen Sie `DateTimeFields` für Apps, die das Aufzeichnen des Datums und der Uhrzeit, zu der Ereignisse auftreten oder ein Benutzer Daten übermittelt, umfassen.

4. **Aufgabenmanagement und Fristen**: `DateTimeFields` sind wertvoll in Anwendungen, die Aufgabenmanagement oder das Setzen von Fristen erfordern, bei denen sowohl das Datum als auch die Uhrzeit für eine genaue Planung relevant sind.

## Feldwert (`LocalDateTime`) {#field-value-localdatetime}

Intern verwendet die `DateTimeField`-Komponente ein `LocalDateTime`-Objekt aus dem `java.time`-Paket, um ihren Wert darzustellen. Dies bietet eine präzise Kontrolle über sowohl das Datum als auch die Uhrzeit der Eingabe.

Während der **Client-seitige** Wert basierend auf der Browsersprache des Benutzers gerendert wird (z. B. Datum- und Uhrzeitformate, die den lokalen Konventionen entsprechen), folgt der **analysierte** Wert einer strengen und vorhersehbaren Struktur: **`yyyy-MM-ddTHH:mm:ss`**.

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

Wenn Sie den Wert über eine rohe Zeichenkette setzen möchten, muss sie dem exakten Format `yyyy-MM-ddTHH:mm:ss` entsprechen.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // gültig

dateTimeField.setText("24-04-27T14:30:00"); // ungültig
```

:::warning
 Bei Verwendung der Methode `setText()` wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe im Format `yyyy-MM-ddTHH:mm:ss` nicht analysieren kann.
:::

## Statische Hilfsfunktionen {#static-utilities}

Die Klasse DateTimeField bietet auch die folgenden statischen Hilfsmethoden:

- `fromDateTime(String dateTimeAsString)`: Konvertiert eine Datums- und Uhrzeitzeichenfolge im Format `yyyy-MM-ddTHH:mm:ss` in ein LocalDateTime-Objekt, das dann mit dieser Klasse oder anderswo verwendet werden kann.

- `toDateTime(LocalDateTime dateTime)`: Konvertiert ein LocalDateTime-Objekt in eine Datums- und Uhrzeitzeichenfolge im Format `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)`: Überprüft, ob die angegebene Zeichenfolge ein gültiges `yyyy-MM-ddTHH:mm:ss` Datum und Uhrzeit ist. Dies gibt einen booleschen Wert zurück, der true ist, wenn ja, und false andernfalls.

## Min- und Max-Wert {#min-and-max-value}

### Der Min-Wert {#the-min-value}

Wenn der in die Komponente eingegebene Wert früher ist als der angegebene Mindeststempel, schlägt die Einschränkung der Validierung fehl. Wenn sowohl die Min- als auch die Max-Werte festgelegt sind, muss der Min-Wert ein Stempel sein, der gleich oder früher als der Max-Wert ist.

```java
// Setze den minimal erlaubten Timestamp: 1. Januar 2023 um 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### Der Max-Wert {#the-max-value}

Wenn der in die Komponente eingegebene Wert später ist als der angegebene Maximalstempel, schlägt die Einschränkung der Validierung fehl. Wenn sowohl die Min- als auch die Max-Werte festgelegt sind, muss der Max-Wert ein Stempel sein, der gleich oder später als der Min-Wert ist.

```java
// Setze den maximal erlaubten Timestamp: 31. Dezember 2023 um 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `DateTimeField`-Komponente zu gewährleisten, sollten Sie die folgenden Best Practices berücksichtigen:

- **Lokalisierte Datumsanzeige**: Die Lokalisierung des Datumsformats und die Berücksichtigung regionaler Präferenzen stellen sicher, dass Daten in einem für den Benutzer vertrauten Format angezeigt werden.

- **Zeitzonen einbeziehen**: Wenn Ihre App mit zeitkritischen Informationen in verschiedenen Zeitzonen arbeitet, sollten Sie in Betracht ziehen, die Auswahl der Zeitzone neben dem Datumsfeld zu integrieren, um eine genaue Darstellung von Datum und Uhrzeit zu gewährleisten.

- **Barrierefreiheit**: Verwenden Sie das `DateTimeField` unter Berücksichtigung der Barrierefreiheit. Stellen Sie sicher, dass es den Barrierefreiheitsstandards entspricht, z. B. durch Bereitstellung geeigneter Bezeichnungen und Kompatibilität mit Unterstützungstechnologien.

- **Aktuelles Datum automatisch ausfüllen**: Überlegen Sie, ob Sie die Option anbieten möchten, das aktuelle Datum und die Uhrzeit als Standardwert im Datumsfeld automatisch auszufüllen, wenn dies für den Anwendungsfall Ihrer App geeignet ist.
