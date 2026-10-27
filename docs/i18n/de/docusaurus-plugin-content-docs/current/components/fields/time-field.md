---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9b4c187f1c86207e13b38812f0eb7e6c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` ist ein Benutzeroberflächenkomponente, die es den Benutzern ermöglicht, Zeiten mit Stunden- und Minutenpräzision einzugeben oder auszuwählen. Wenn Sekunden angegeben werden, werden sie von der Komponente verworfen.

<!-- INTRO_END -->

## Verwendung des `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Feldkomponenten bereitstellt. Das folgende Beispiel erstellt ein Erinnerungs-`TimeField`, das auf die aktuelle Zeit initialisiert ist.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Verwendungen {#usages}

Das `TimeField` ist ideal zur Auswahl und Anzeige von Zeiten in Ihrer App. Hier sind einige Beispiele, wann das `TimeField` verwendet werden sollte:

1. **Veranstaltungsplanung**: Zeitfelder sind essenziell in Apps, die Zeiten für Veranstaltungen, Termine oder Meetings festlegen.

2. **Zeitverfolgung und Protokollierung**: Apps, die Zeit verfolgen, wie zum Beispiel Stundenzettel, benötigen Zeitfelder für genaue Einträge.

3. **Erinnerungen und Alarme**: Die Verwendung eines Zeitfeldes vereinfacht den Eingabeprozess für Benutzer, die Erinnerungen oder Alarme in Ihrer App einstellen möchten.

## Min- und Max-Wert {#min-and-max-value}

Mit den Methoden `setMin()` und `setMax()` können Sie einen Bereich akzeptabler Zeiten festlegen.

- **Für `setMin()`**: Wenn der eingegebene Wert in die Komponente früher ist als die angegebene minimale Zeit, wird die Eingabe gegen die Validierungsbeschränkung nicht bestanden. Wenn sowohl der Min- als auch der Max-Wert festgelegt sind, muss der Min-Wert eine Zeit sein, die gleich oder früher als der Max-Wert ist.

- **Für `setMax()`**: Wenn der eingegebene Wert in die Komponente später ist als die angegebene maximale Zeit, wird die Eingabe gegen die Validierungsbeschränkung nicht bestanden. Wenn sowohl der Min- als auch der Max-Wert festgelegt sind, muss der Max-Wert eine Zeit sein, die gleich oder später als der Min-Wert ist.

## Wertverarbeitung und Lokalisierung {#value-handling-and-localization}

Intern repräsentiert die `TimeField`-Komponente ihren Wert mit einem `LocalTime`-Objekt aus dem `java.time`-Paket. Dies ermöglicht Entwicklern, mit präzisen Zeitwerten zu interagieren, unabhängig davon, wie sie visuell dargestellt werden.

Der Browser bestimmt, wie der Picker die Zeit für die Locale des Benutzers anzeigt. Der Textwert des Feldes verwendet das 24-Stunden-Format `HH:mm`, und der `LocalTime`-Wert wird auf Minuten verkürzt.

Beim Setzen eines Rohzeichenwerts verwenden Sie die `setText()`-Methode vorsichtig:

```java
timeField.setText("09:15");    // gültig
timeField.setText("09:15:30"); // ebenfalls gültig; Sekunden werden verworfen, was 09:15 ergibt
```

:::warning
Bei der Verwendung von `setText()` wird eine `IllegalArgumentException` ausgelöst, wenn die Eingabe nicht als gültige Zeit analysiert werden kann. Sowohl `HH:mm`- als auch `HH:mm:ss`-Eingaben werden akzeptiert, aber Sekunden werden verworfen.
:::

:::info Picker UI
Das Erscheinungsbild der Benutzeroberfläche des Zeitpicker-Eingabefelds hängt von der ausgewählten Locale, dem Browser und dem Betriebssystem ab. Dies schafft eine automatische Konsistenz mit der Benutzeroberfläche, mit der die Benutzer bereits vertraut sind.
:::

## Statische Hilfsfunktionen {#static-utilities}

Die `TimeField`-Klasse bietet auch die folgenden statischen Hilfsfunktionen:

- `fromTime(String timeAsString)`: Analysiert eine Zeitzeichenfolge, mit oder ohne Sekunden, in ein `LocalTime`, das auf Minuten verkürzt ist.

- `toTime(LocalTime time)`: Konvertiert ein `LocalTime` in eine Zeichenfolge im `HH:mm`-Format und verwirft Sekunden.

- `isValidTime(String timeAsString)`: Überprüft, ob eine Zeitzeichenfolge gültig ist, einschließlich `HH:mm`- und `HH:mm:ss`-Eingaben. Gibt `true` zurück, wenn es gültig ist, und `false` andernfalls.

## Beste Praktiken {#best-practices}

- **Klare Zeitformatbeispiele bereitstellen**: Zeigen Sie den Benutzern klar das erwartete Zeitformat in der Nähe des `TimeField`. Verwenden Sie Beispiele oder Platzhalter, um ihnen zu helfen, die Zeit korrekt einzugeben. Wenn möglich, zeigen Sie das Zeitformat basierend auf dem Standort des Benutzers an.

- **Barrierefreiheit**: Verwenden Sie die `TimeField`-Komponente mit Blick auf die Barrierefreiheit und erfüllen Sie Barrierefreiheitsstandards wie richtige Beschriftungen, ausreichenden Farbkontrast und Kompatibilität mit unterstützenden Technologien.

- **Zurücksetzen-Option**: Bieten Sie den Benutzern eine Möglichkeit, das `TimeField` einfach auf einen leeren oder standardmäßigen Zustand zurückzusetzen.
