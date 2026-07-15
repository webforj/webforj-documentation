---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9688647e85d453578ccd59934e52e26b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` ist eine Benutzeroberflächenkomponente, die es Benutzern ermöglicht, Zeiten in Stunden, Minuten und optional Sekunden einzugeben oder auszuwählen. Es bietet eine intuitive und effiziente Möglichkeit, zeitbezogene Informationen in verschiedenen Anwendungen zu verarbeiten.

<!-- INTRO_END -->

## Verwendung des `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Feldkomponenten bereitstellt. Das folgende Beispiel erstellt ein Erinnerungs-`TimeField`, das auf die aktuelle Zeit initialisiert wird.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Verwendungen {#usages}

Das `TimeField` eignet sich ideal zum Auswählen und Anzeigen von Zeiten in Ihrer App. Hier sind einige Beispiele, wann das `TimeField` verwendet werden sollte:

1. **Ereignisplanung**: Zeitfelder sind in Apps, die die Festlegung von Zeiten für Veranstaltungen, Termine oder Besprechungen beinhalten, unerlässlich.

2. **Zeitverfolgung und -protokollierung**: Apps, die Zeit verfolgen, wie z. B. Stundennachweise, benötigen Zeitfelder für genaue Einträge.

3. **Erinnerungen und Alarme**: Die Verwendung eines Zeitfelds vereinfacht den Eingabeprozess für Benutzer, die Erinnerungen oder Alarme in Ihrer App festlegen.

## Minimal- und Maximalwert {#min-and-max-value}

Mit den Methoden `setMin()` und `setMax()` können Sie einen Bereich akzeptabler Zeiten festlegen.

- **Für `setMin()`**: Wenn der in die Komponente eingegebene Wert früher ist als die angegebene Mindestzeit, schlägt die Validierung der Einschränkung fehl. Wenn sowohl die min- als auch die max-Werte gesetzt sind, muss der min-Wert eine Zeit sein, die gleich oder früher als der max-Wert ist.

- **Für `setMax()`**: Wenn der in die Komponente eingegebene Wert später ist als die angegebene Höchstzeit, schlägt die Validierung der Einschränkung fehl. Wenn sowohl die min- als auch die max-Werte gesetzt sind, muss der max-Wert eine Zeit sein, die gleich oder später als der min-Wert ist.

## Werteverarbeitung und Lokalisierung {#value-handling-and-localization}

Intern vertreten die `TimeField`-Komponente ihre Werte unter Verwendung eines `LocalTime`-Objekts aus dem `java.time`-Paket. Dies ermöglicht Entwicklern, mit genauen Zeitwerten zu interagieren, unabhängig davon, wie sie visuell dargestellt sind.

Während die **clientseitige Komponente die Zeit unter Verwendung der Browsersprache des Benutzers anzeigt**, ist das geparste und gespeicherte Format immer als `HH:mm:ss` standardisiert.

Wenn Sie einen raw String-Wert festlegen, verwenden Sie die Methode `setText()` mit Bedacht:

```java
timeField.setText("09:15:00"); // gültig
```

:::warning
 Wenn Sie die Methode `setText()` verwenden, wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente den Eingabewert nicht im `HH:mm:ss`-Format parsen kann.
:::


:::info Picker-Benutzeroberfläche
Das Erscheinungsbild der Benutzeroberfläche für den Zeitwähler hängt nicht nur von der ausgewählten Sprache ab, sondern auch vom verwendeten Browser und Betriebssystem. Dies sorgt für automatische Konsistenz mit der Benutzeroberfläche, die die Benutzer bereits kennen.
:::

## Statische Hilfsprogramme {#static-utilities}

Die `TimeField`-Klasse bietet auch die folgenden statischen Hilfsmethoden:

- `fromTime(String timeAsString)`: Konvertiert einen Zeit-String im HH:mm:ss-Format in ein LocalTime-Objekt, das dann mit dieser Klasse oder anderswo verwendet werden kann.

- `toTime(LocalTime time)`: Konvertiert ein LocalTime in einen Zeit-String im HH:mm:ss-Format.

- `isValidTime(String timeAsString)`: Überprüft, ob der angegebene String eine gültige HH:mm:ss-Zeit ist. Dies gibt einen booleschen Wert true zurück, wenn dies der Fall ist, andernfalls false.

## Best Practices {#best-practices}

- **Klare Beispiele für das Zeitformat bereitstellen**: Zeigen Sie den Benutzern das erwartete Zeitformat klar in der Nähe des `TimeField` an. Verwenden Sie Beispiele oder Platzhalter, um ihnen zu helfen, die Zeit korrekt einzugeben. Wenn möglich, zeigen Sie das Zeitformat basierend auf dem Standort des Benutzers an.

- **Zugänglichkeit**: Nutzen Sie die `TimeField`-Komponente mit Blick auf die Zugänglichkeit und stellen Sie sicher, dass sie den Standards für Zugänglichkeit entspricht, wie z. B. durch Bereitstellung angemessener Beschriftungen, ausreichendem Farbkontrast und Kompatibilität mit Hilfstechnologien.

- **Zurücksetzen-Option**: Bieten Sie eine Möglichkeit, für Benutzer die `TimeField`-Eingabe einfach auf einen leeren oder Standardzustand zurückzusetzen.
