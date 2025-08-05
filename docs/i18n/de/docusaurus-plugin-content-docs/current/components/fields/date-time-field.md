---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
sidebar_class_name: updated-content
_i18n_hash: 70f471320621b40dc1bb4170e4cbf752
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

Die `DateTimeField`-Komponente ist so konzipiert, dass Benutzer sowohl ein Datum als auch eine Uhrzeit eingeben können. Dies umfasst die Angabe von Jahr, Monat und Tag sowie die Zeit in Stunden und Minuten. Sie bietet den Benutzern die Möglichkeit, ihre Eingaben auf Genauigkeit zu validieren oder eine spezielle Datums-Uhrzeit-Auswahloberfläche zu nutzen, um den Auswahlprozess zu vereinfachen.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Verwendungen {#usages}

Das `DateTimeField` eignet sich am besten für Szenarien, in denen das Erfassen oder Anzeigen von sowohl Datum **als auch** Uhrzeit für Ihre App von wesentlicher Bedeutung ist. Hier sind einige Beispiele, wann das `DateTimeField` verwendet werden sollte:

1. **Ereignisplanung und Kalender**: Ermöglichen Sie es den Benutzern, effizient Ereignisse zu planen, Termine zu buchen und ihre Kalender zu verwalten, indem Sie ihnen eine einzige Komponente zur Verfügung stellen, mit der sie Datum und Uhrzeit auswählen können.
<!-- vale off -->
2. **Check-in und Check-out**: Erleichtern Sie die Auswahl von Check-in- und Check-out-Zeiten, wenn der Zeitraum mehrere Tage umfassen kann.
<!-- vale on -->
3. **Datenprotokollierung und Zeitstempel**: Nutzen Sie `DateTimeFields` für Apps, die das Aufzeichnen von Datum und Uhrzeit, zu dem Ereignisse auftreten oder ein Benutzer Daten eingibt, beinhalten.

4. **Aufgabenverwaltung und Fristen**: `DateTimeFields` sind wertvoll in Anwendungen, die Aufgabenmanagement oder das Setzen von Fristen beinhalten, bei denen sowohl das Datum als auch die Uhrzeit für eine genaue Planung relevant sind.

## Feldwert (`LocalDateTime`) {#field-value-localdatetime}

Intern repräsentiert die `DateTimeField`-Komponente ihren Wert mit einem `LocalDateTime`-Objekt aus dem `java.time`-Paket. Dies bietet eine präzise Kontrolle über die Eingabewerte für Datum und Uhrzeit.

Während der **Client-seitige** Wert basierend auf der Standortkonvention des Browsers des Benutzers (z.B. Datums- und Zeitformate, die lokalen Gepflogenheiten entsprechen) gerendert wird, folgt der **geparste** Wert einer strengen und vorhersehbaren Struktur: **`yyyy-MM-ddTHH:mm:ss`**.

### Wert abrufen und festlegen {#getting-and-setting-the-value}

Um den aktuellen Wert abzurufen, verwenden Sie die Methode `getValue()`:

```java
LocalDateTime value = dateTimeField.getValue();
```

Um den Wert programmgesteuert festzulegen, verwenden Sie die Methode `setValue()`:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Verwendung von `setText()` {#using-settext}

Wenn Sie den Wert über eine rohe Zeichenfolge festlegen möchten, muss er dem genauen Format `yyyy-MM-ddTHH:mm:ss` entsprechen.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // gültig

dateTimeField.setText("24-04-27T14:30:00"); // ungültig
```

:::warning
Wenn Sie die Methode `setText()` verwenden, wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe im Format `yyyy-MM-ddTHH:mm:ss` nicht parsen kann.
:::

## Statische Hilfsmittel {#static-utilities}

Die DateTimeField-Klasse bietet auch die folgenden statischen Hilfsmethoden:

- `fromDateTime(String dateTimeAsString)`: Konvertiert eine Datum- und Uhrzeit-Zeichenfolge im Format `yyyy-MM-ddTHH:mm:ss` in ein LocalDateTime-Objekt, das dann mit dieser Klasse oder anderswo verwendet werden kann.

- `toDateTime(LocalDateTime dateTime)`: Konvertiert ein LocalDateTime-Objekt in eine Datum- und Uhrzeit-Zeichenfolge im Format `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)`: Überprüft, ob die angegebene Zeichenfolge ein gültiges Datum und eine gültige Uhrzeit im Format `yyyy-MM-ddTHH:mm:ss` ist. Dies gibt einen booleschen Wert zurück: true, wenn ja, andernfalls false.

## Minimale und maximale Werte {#min-and-max-value}

### Der minimale Wert {#the-min-value}

Wenn der in die Komponente eingegebene Wert früher ist als der angegebene Mindestzeitstempel, schlägt die Komponente bei der Validierung der Einschränkungen fehl. Wenn sowohl die Minimal- als auch die Maximalwerte festgelegt sind, muss der Minimalwert ein Zeitstempel sein, der derselbe ist oder früher als der Maximalwert.

```java
// Setzen des minimal erlaubten Zeitstempels: 1. Januar 2023 um 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### Der maximale Wert {#the-max-value}

Wenn der in die Komponente eingegebene Wert später ist als der angegebene Höchstzeitstempel, schlägt die Komponente bei der Validierung der Einschränkungen fehl. Wenn sowohl die Minimal- als auch die Maximalwerte festgelegt sind, muss der Maximalwert ein Zeitstempel sein, der derselbe ist oder später als der Minimalwert.

```java
// Setzen des maximal erlaubten Zeitstempels: 31. Dezember 2023 um 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `DateTimeField`-Komponente sicherzustellen, sollten Sie die folgenden Best Practices berücksichtigen:

- **Lokalisierte Datumsanzeige**: Die Lokalisierung des Datumsformats und die Einbeziehung regionaler Präferenzen stellen sicher, dass Daten in einem vertrauten Format für den Benutzer angezeigt werden.

- **Zeitzonen einbeziehen**: Wenn Ihre App mit zeitsensiblen Informationen über verschiedene Zeitzonen hinweg arbeitet, sollten Sie die Auswahl der Zeitzone neben dem Datumsfeld in Betracht ziehen, um eine genaue Darstellung von Datum und Uhrzeit zu gewährleisten.

- **Barrierefreiheit**: Verwenden Sie das `DateTimeField` unter Berücksichtigung der Barrierefreiheit. Stellen Sie sicher, dass es den Zugänglichkeitsstandards entspricht, wie z.B. ordnungsgemäße Bezeichnungen zu bieten und mit Unterstützungstechnologien kompatibel zu sein.

- **Automatisches Ausfüllen des aktuellen Datums**: Berücksichtigen Sie die Möglichkeit, das aktuelle Datum und die aktuelle Uhrzeit als Standardwert im Datumszeitfeld automatisch auszufüllen, wenn dies für den Anwendungsfall Ihrer App geeignet ist.
