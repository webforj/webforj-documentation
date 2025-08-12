---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: 6421e3007af8e795adefa317a13363f0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` ist eine Benutzeroberflächenkomponente, die es Benutzern ermöglicht, Zeiten in Stunden, Minuten und optional Sekunden einzugeben oder auszuwählen. Sie bietet eine intuitive und effiziente Möglichkeit, zeitbezogene Informationen in verschiedenen Anwendungen zu verarbeiten.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Anwendungen {#usages}

Der `TimeField` ist ideal, um Zeiten in Ihrer App auszuwählen und anzuzeigen. Hier sind einige Beispiele, wann Sie den `TimeField` verwenden sollten:

1. **Veranstaltungsplanung**: Zeitfelder sind in Apps, die das Setzen von Zeiten für Veranstaltungen, Termine oder Besprechungen umfassen, unerlässlich.

2. **Zeiterfassung und Protokollierung**: Apps, die Zeit verfolgen, wie beispielsweise Stundenzettel, benötigen Zeitfelder für genaue Einträge.

3. **Erinnerungen und Alarme**: Die Verwendung eines Zeitfelds vereinfacht den Eingabeprozess für Benutzer, die Erinnerungen oder Alarme in Ihrer App einstellen möchten.

## Min- und Max-Wert {#min-and-max-value}

Mit den Methoden `setMin()` und `setMax()` können Sie einen Bereich akzeptabler Zeiten angeben.

- **Für `setMin()`**: Wenn der in die Komponente eingegebene Wert früher ist als die angegebene Mindestzeit, wird die Komponentenvalidierung fehlschlagen. Wenn sowohl die Min- als auch die Max-Werte gesetzt sind, muss der Min-Wert eine Zeit sein, die gleich oder früher als der Max-Wert ist.

- **Für `setMax()`**: Wenn der in die Komponente eingegebene Wert später ist als die angegebene Höchstzeit, wird die Komponentenvalidierung fehlschlagen. Wenn sowohl die Min- als auch die Max-Werte gesetzt sind, muss der Max-Wert eine Zeit sein, die gleich oder später als der Min-Wert ist.

## Wertverarbeitung und Lokalisierung {#value-handling-and-localization}

Intern repräsentiert die `TimeField`-Komponente ihren Wert mit einem `LocalTime`-Objekt aus dem `java.time`-Paket. Dies ermöglicht Entwicklern, mit präzisen Zeitwerten zu interagieren, unabhängig davon, wie sie visuell dargestellt werden.

Während die **clientseitige Komponente die Zeit unter Verwendung der Browsersprache des Benutzers anzeigt**, ist das geparste und gespeicherte Format stets standardisiert als `HH:mm:ss`.

Bei der Festlegung eines Rohwerts als Zeichenfolge verwenden Sie die Methode `setText()` sorgfältig:

```java
timeField.setText("09:15:00"); // gültig
```

:::warning
 Wenn Sie die Methode `setText()` verwenden, wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe im `HH:mm:ss`-Format nicht parsen kann.
:::


:::info Picker UI 
Das Erscheinungsbild der Benutzeroberfläche für den Zeitpicker hängt nicht nur von der gewählten Sprache ab, sondern auch vom verwendeten Browser und Betriebssystem. Dies gewährleistet eine automatische Konsistenz mit der Benutzeroberfläche, die den Benutzern bereits vertraut ist.
:::

## Statische Hilfsfunktionen {#static-utilities}

Die `TimeField`-Klasse bietet auch die folgenden statischen Hilfsfunktionen:

- `fromTime(String timeAsString)`: Konvertiert eine Zeitzeichenfolge im HH:mm:ss-Format in ein `LocalTime`-Objekt, das dann mit dieser Klasse oder an anderer Stelle verwendet werden kann.

- `toTime(LocalTime time)`: Konvertiert ein `LocalTime` in eine Zeitzeichenfolge im HH:mm:ss-Format.

- `isValidTime(String timeAsString)`: Überprüft, ob die angegebene Zeichenfolge eine gültige HH:mm:ss-Zeit ist. Dies gibt einen booleschen Wert true zurück, wenn dies der Fall ist, andernfalls false.

## Best Practices {#best-practices}

- **Klare Zeitformatbeispiele bereitstellen**: Zeigen Sie den Benutzern klar das erwartete Zeitformat in der Nähe des `TimeField` an. Verwenden Sie Beispiele oder Platzhalter, um ihnen zu helfen, die Zeit korrekt einzugeben. Wenn möglich, zeigen Sie das Zeitformat basierend auf dem Standort des Benutzers an.

- **Barrierefreiheit**: Nutzen Sie die `TimeField`-Komponente unter Berücksichtigung der Barrierefreiheit, um sicherzustellen, dass sie den Barrierefreiheitsstandards entspricht, z. B. durch Bereitstellung geeigneter Beschriftungen, ausreichenden Farbkontrasts und Kompatibilität mit unterstützenden Technologien.

- **Zurücksetzen-Option**: Bieten Sie eine Möglichkeit, für Benutzer, das `TimeField` einfach auf einen leeren oder Standardzustand zurückzusetzen.
