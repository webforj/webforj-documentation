---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
sidebar_class_name: updated-content
_i18n_hash: dd6fe3e8a737f5b016f92629d9767dbb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

Das `DateTimeField`-Komponente wurde entwickelt, um Benutzern die Eingabe sowohl eines Datums als auch einer Uhrzeit zu ermöglichen. Dazu gehört die Angabe des Jahres, Monats und Tages sowie der Uhrzeit in Stunden und Minuten. Es bietet den Benutzern die Möglichkeit, ihre Eingaben auf Richtigkeit zu validieren oder eine spezielle Datum-Uhrzeit-Auswahloberfläche zu nutzen, um den Auswahlprozess zu erleichtern.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Verwendungen {#usages}

Das `DateTimeField` eignet sich am besten für Szenarien, in denen die Erfassung oder Anzeige von sowohl Datum **als auch** Uhrzeit für Ihre App entscheidend ist. Hier sind einige Beispiele, wann das `DateTimeField` verwendet werden sollte:

1. **Veranstaltungstermine und Kalender**: Ermöglichen Sie Benutzern, Veranstaltungen effizient zu planen, Termine zu buchen und ihre Kalender zu verwalten, indem Sie ihnen eine einzige Komponente zur Verfügung stellen, mit der sie Datum und Uhrzeit auswählen können.
<!-- vale off -->
2. **Check-in und Check-out**: Erleichtern Sie die Auswahl von Check-in- und Check-out-Zeiten, wenn der Zeitraum mehrere Tage umfassen kann.
<!-- vale on -->
3. **Datenprotokollierung und Zeitstempel**: Verwenden Sie `DateTimeFields` für Apps, die das Datum und die Uhrzeit erfassen, zu denen Ereignisse stattfinden oder ein Benutzer Daten übermittelt.

4. **Aufgabenverwaltung und Fristen**: `DateTimeFields` sind wertvoll in Anwendungen, die Aufgabenverwaltung oder das Setzen von Fristen beinhalten, wo sowohl Datum als auch Uhrzeit für eine genaue Planung relevant sind.

## Feldwert (`LocalDateTime`) {#field-value-localdatetime}

Intern verwendet die `DateTimeField`-Komponente ein `LocalDateTime`-Objekt aus dem Paket `java.time`, um ihren Wert darzustellen. Dies ermöglicht eine präzise Kontrolle sowohl über die Datums- als auch die Uhrzeitkomponenten der Eingabe.

Während der **clientseitige** Wert basierend auf der Browsersprache des Benutzers gerendert wird (z.B. Datums- und Uhrzeitformate, die den lokalen Gepflogenheiten entsprechen), folgt der **geparste** Wert einer strengen und vorhersehbaren Struktur: **`yyyy-MM-ddTHH:mm:ss`**.

### Abrufen und Setzen des Wertes {#getting-and-setting-the-value}

Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`:

```java
LocalDateTime value = dateTimeField.getValue();
```

Um den Wert programmgesteuert festzulegen, verwenden Sie die Methode `setValue()`:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Verwendung von `setText()` {#using-settext}

Wenn Sie den Wert über einen Rohstring festlegen möchten, muss er dem exakten Format `yyyy-MM-ddTHH:mm:ss` folgen.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // gültig

dateTimeField.setText("24-04-27T14:30:00"); // ungültig
```

:::warning
 Bei Verwendung der Methode `setText()` wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe nicht im Format `yyyy-MM-ddTHH:mm:ss` parsen kann.
:::

## Statische Dienstprogramme {#static-utilities}

Die DateTimeField-Klasse bietet auch die folgenden statischen Dienstprogrammmethoden:

- `fromDateTime(String dateTimeAsString)`: Konvertiert einen Datums- und Uhrzeitstring im Format `yyyy-MM-ddTHH:mm:ss` in ein LocalDateTime-Objekt, das dann mit dieser Klasse oder anderswo verwendet werden kann.

- `toDateTime(LocalDateTime dateTime)`: Konvertiert ein LocalDateTime-Objekt in einen Datums- und Uhrzeitstring im Format `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)`: Überprüft, ob der gegebene String ein gültiges `yyyy-MM-ddTHH:mm:ss`-Datum und eine Uhrzeit ist. Dies gibt einen booleschen Wert zurück: true, wenn dies der Fall ist, andernfalls false.

## Min- und Max-Wert {#min-and-max-value}

### Der Min-Wert {#the-min-value}

Wenn der in die Komponente eingegebene Wert früher als der angegebene Mindestzeitstempel ist, wird die Einschränkungsvalidierung der Komponente fehlschlagen. Wenn sowohl die Min- als auch die Max-Werte gesetzt sind, muss der Min-Wert ein Zeitstempel sein, der gleich oder früher als der Max-Wert ist.

```java
// Mindestzeitstempel festlegen: 1. Januar 2023 um 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### Der Max-Wert {#the-max-value}

Wenn der in die Komponente eingegebene Wert später als der angegebene Höchstzeitstempel ist, wird die Einschränkungsvalidierung der Komponente fehlschlagen. Wenn sowohl die Min- als auch die Max-Werte gesetzt sind, muss der Max-Wert ein Zeitstempel sein, der gleich oder später als der Min-Wert ist.

```java
// Höchstzeitstempel festlegen: 31. Dezember 2023 um 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Best Practices {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `DateTimeField`-Komponente zu gewährleisten, berücksichtigen Sie die folgenden Best Practices:

- **Lokalisierte Datum-Anzeige**: Durch die Lokalisierung des Datumsformats und die Berücksichtigung regionaler Präferenzen wird sichergestellt, dass Daten in einem vertrauten Format für den Benutzer angezeigt werden.

- **Zeitzonen einbeziehen**: Wenn Ihre App mit zeitkritischen Informationen über verschiedene Zeitzonen hinweg arbeitet, sollten Sie die Auswahl der Zeitzone zusammen mit dem Datumsfeld einbeziehen, um eine genaue Darstellung von Datum und Uhrzeit sicherzustellen.

- **Barrierefreiheit**: Verwenden Sie das `DateTimeField` mit Blick auf die Barrierefreiheit. Stellen Sie sicher, dass es den Standards für Barrierefreiheit entspricht, wie z.B. die Bereitstellung geeigneter Beschriftungen und die Kompatibilität mit unterstützenden Technologien.

- **Automatisches Ausfüllen des aktuellen Datums**: Ziehen Sie in Betracht, eine Option anzubieten, um das aktuelle Datum und die Uhrzeit als Standardwert im Datumsfeld automatisch auszufüllen, wenn dies für den Anwendungsfall Ihrer App angemessen ist.
