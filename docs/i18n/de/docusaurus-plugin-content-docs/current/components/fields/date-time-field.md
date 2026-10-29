---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 9ccb17716ffd5fa2d32781fc603d5042
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

Die `DateTimeField`-Komponente ermöglicht es Benutzern, sowohl ein Datum als auch eine Uhrzeit in einem einzigen Feld einzugeben, wobei Jahr, Monat, Tag, Stunden und Minuten abgedeckt sind. Sie validiert die Eingabe auf Genauigkeit und kann einen Datums- und Uhrzeitwähler bereitstellen, um die Auswahl zu erleichtern.

<!-- INTRO_END -->

## Verwendung von `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Feldkomponenten bietet. Das folgende Beispiel erstellt ein beschriftetes `DateTimeField` zur Auswahl eines Abfahrtsdatums und einer Abfahrtszeit.

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## Anwendungsfälle {#usages}

Das `DateTimeField` eignet sich am besten für Szenarien, in denen das Erfassen oder Anzeigen von sowohl Datum **als auch** Uhrzeit für Ihre App entscheidend ist. Hier sind einige Beispiele, wann das `DateTimeField` eingesetzt werden sollte:

1. **Veranstaltungsplanung und Kalendersysteme**: Ermöglichen Sie es Benutzern, Veranstaltungen effizient zu planen, Termine zu buchen und ihre Kalender zu verwalten, indem Sie ihnen eine einzige Komponente zur Verfügung stellen, die es ihnen ermöglicht, Datum und Uhrzeit auszuwählen.
<!-- vale off -->
2. **Check-in und Check-out**: Erleichtern Sie die Auswahl der Check-in- und Check-out-Zeiten durch die Benutzer, wenn der Zeitraum mehrere Tage umfasst.
<!-- vale on -->
3. **Datenprotokollierung und Zeitstempel**: Nutzen Sie `DateTimeFields` für Apps, die die Dokumentation des Datums und der Uhrzeit beinhalten, wann Ereignisse auftreten oder wenn ein Benutzer Daten übermittelt.

4. **Aufgabenverwaltung und Fristen**: `DateTimeFields` sind in Anwendungen wertvoll, die Aufgabenverwaltung oder die Festlegung von Fristen beinhalten, bei denen sowohl das Datum als auch die Uhrzeit für eine genaue Planung relevant sind.

## Feldwert (`LocalDateTime`) {#field-value-localdatetime}

Intern repräsentiert die `DateTimeField`-Komponente ihren Wert mithilfe eines `LocalDateTime`-Objekts aus dem `java.time`-Paket. Werte werden auf Minuten gerundet, sodass Sekunden und Bruchteile von Sekunden verworfen werden.

Der Browser zeigt das Datum und die Uhrzeit gemäß der Region des Benutzers an, während der Textwert des Feldes das Format `yyyy-MM-ddTHH:mm` verwendet. `LocalDateTime` enthält keine Zeitzone, und Eingaben mit einem Zeitzonen-Suffix wie `Z` oder `+02:00` werden nicht akzeptiert.

### Abrufen und Festlegen des Wertes {#getting-and-setting-the-value}

Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`:

```java
LocalDateTime value = dateTimeField.getValue();
```

Um den Wert programmatisch festzulegen, verwenden Sie die Methode `setValue()`:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30));
```

### Verwendung von `setText()` {#using-settext}

Um den Wert aus einer Rohzeichenfolge festzulegen, verwenden Sie `setText()` mit einem lokalen Datum und einer Uhrzeit, z. B. `yyyy-MM-ddTHH:mm`. Die Eingabe kann auch Sekunden oder Bruchteile von Sekunden enthalten, die verworfen werden.

```java
dateTimeField.setText("2024-04-27T14:30");       // gültig
dateTimeField.setText("2024-04-27T14:30:45");    // gültig; Sekunden werden verworfen

dateTimeField.setText("24-04-27T14:30");        // ungültig; verwenden Sie ein vierstelliges Jahr
dateTimeField.setText("2024-04-27T14:30Z");      // ungültig; Zeitzonen-Suffixe werden nicht akzeptiert
```

:::warning
Wenn Sie `setText()` verwenden, wird eine `IllegalArgumentException` ausgelöst, wenn die Eingabe nicht als gültiges lokales Datum und Uhrzeit analysiert werden kann. Eingaben mit Minuten-Präzision und Eingaben mit Sekunden werden akzeptiert, jedoch keine Zeitzonen-Suffixe.
:::

## Statische Hilfsmethoden {#static-utilities}

Die `DateTimeField`-Klasse bietet auch die folgenden statischen Hilfsmethoden:

- `fromDateTime(String dateTimeAsString)`: analysiert eine lokale Datum-Uhrzeit-Zeichenfolge, mit oder ohne Sekunden, in ein `LocalDateTime`, das auf Minuten gerundet ist.

- `toDateTime(LocalDateTime dateTime)`: konvertiert ein `LocalDateTime` in eine Zeichenfolge im Format `yyyy-MM-ddTHH:mm`. Sekunden und Bruchteile von Sekunden werden verworfen.

- `isValidDateTime(String dateTimeAsString)`: überprüft, ob eine Zeichenfolge ein gültiges lokales Datum und Uhrzeit ist. Eingaben mit Sekunden oder Bruchteilen von Sekunden werden akzeptiert. Gibt `false` für ungültige Eingaben oder Zeitzonen-Suffixe zurück.

## Min- und Max-Wert {#min-and-max-value}

### Der Minimalwert {#the-min-value}

Wenn der in die Komponente eingegebene Wert früher ist als der angegebene Mindestwert, schlägt die Validierung der Einschränkung fehl. Wenn sowohl die Mindest- als auch die Höchstwerte festgelegt sind, muss der Mindestwert ein Zeitstempel sein, der gleich oder früher als der Höchstwert ist.

```java
// Mindestzulässigen Zeitstempel festlegen: 1. Januar 2023 um 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### Der Maximalwert {#the-max-value}

Wenn der in die Komponente eingegebene Wert später ist als der angegebene Höchstwert, schlägt die Validierung der Einschränkung fehl. Wenn sowohl der Mindest- als auch der Höchstwert festgelegt sind, muss der Höchstwert ein Zeitstempel sein, der gleich oder später als der Mindestwert ist.

```java
// Maximalzulässigen Zeitstempel festlegen: 31. Dezember 2023 um 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `DateTimeField`-Komponente sicherzustellen, berücksichtigen Sie folgende Best Practices:

- **Lokalisierte Anzeige von Datumsangaben**: Die Lokalisierung des Datumsformats und die Berücksichtigung regionaler Präferenzen stellen sicher, dass Daten in einem für den Benutzer vertrauten Format präsentiert werden.

- **Zeitzonen getrennt behandeln**: Wenn Ihre App in mehreren Zeitzonen arbeitet, stellen Sie einen separaten Zeitzonenauswähler zur Verfügung und behandeln Sie Umrechnungen in Ihrer App. Das `DateTimeField` speichert nur das lokale Datum und die Uhrzeit.

- **Barrierefreiheit**: Nutzen Sie das `DateTimeField` mit Blick auf die Barrierefreiheit. Stellen Sie sicher, dass es den Standards für Barrierefreiheit entspricht, indem Sie etwa angemessene Beschriftungen bereitstellen und die Kompatibilität mit unterstützenden Technologien sicherstellen.

- **Aktuelles Datum automatisch ausfüllen**: Überlegen Sie, eine Option anzubieten, um das aktuelle Datum und die Uhrzeit als Standardwert im Datums-Zeit-Feld automatisch auszufüllen, wenn dies für den Anwendungsfall Ihrer App angemessen ist.
