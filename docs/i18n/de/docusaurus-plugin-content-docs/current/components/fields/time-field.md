---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: ca6e544259fc218b59cebd14d34e4530
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` ist eine Benutzeroberflächenkomponente, die es Benutzern ermöglicht, Zeiten in Stunden, Minuten und optional Sekunden einzugeben oder auszuwählen. Sie bietet eine intuitive und effiziente Möglichkeit, zeitbezogene Informationen in verschiedenen Anwendungen zu verarbeiten.

<!-- INTRO_END -->

## Verwendung des `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` erweitert die gemeinsame `Field`-Klasse, die gemeinsame Funktionen für alle Feldkomponenten bereitstellt. Das folgende Beispiel erstellt ein Erinnerungs-`TimeField`, das auf die aktuelle Zeit gesetzt ist.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Verwendungen {#usages}

Das `TimeField` ist ideal, um Zeiten in Ihrer App auszuwählen und anzuzeigen. Hier sind einige Beispiele, wann das `TimeField` verwendet werden sollte:

1. **Ereignisplanung**: Zeitfelder sind in Apps, die die Zeiten für Ereignisse, Termine oder Meetings festlegen, unerlässlich.

2. **Zeitverfolgung und Protokollierung**: Apps, die die Zeit verfolgen, wie z. B. Stundenzettel, benötigen Zeitfelder für genaue Einträge.

3. **Erinnerungen und Alarme**: Die Verwendung eines Zeitfelds vereinfacht den Eingabeprozess für Benutzer, die Erinnerungen oder Alarme in Ihrer App einstellen möchten.

## Min- und Max-Werte {#min-and-max-value}

Mit den Methoden `setMin()` und `setMax()` können Sie einen Bereich akzeptabler Zeiten angeben.

- **Für `setMin()`**: Wenn der in die Komponente eingegebene Wert früher ist als die festgelegte Mindestzeit, schlägt die Einschränkung validierung fehl. Wenn sowohl die Min- als auch die Max-Werte festgelegt sind, muss der Min-Wert eine Zeit sein, die gleich oder früher als der Max-Wert ist.

- **Für `setMax()`**: Wenn der in die Komponente eingegebene Wert später ist als die festgelegte Höchstzeit, schlägt die Einschränkung validierung fehl. Wenn sowohl die Min- als auch die Max-Werte festgelegt sind, muss der Max-Wert eine Zeit sein, die gleich oder später als der Min-Wert ist.

## Wertverarbeitung und Lokalisierung {#value-handling-and-localization}

Intern repräsentiert die `TimeField`-Komponente ihren Wert mit einem `LocalTime`-Objekt aus dem `java.time`-Paket. Dies ermöglicht Entwicklern den Umgang mit präzisen Zeitwerten, unabhängig davon, wie sie visuell dargestellt werden.

Während die **Client-Seitenkomponente die Zeit gemäß der Browsersprache des Benutzers anzeigt**, ist das geparste und gespeicherte Format immer standardisiert als `HH:mm:ss`.

Wenn Sie einen rohen String-Wert festlegen, verwenden Sie die Methode `setText()` mit Vorsicht:

```java
timeField.setText("09:15:00"); // gültig
```

:::warning
 Bei der Verwendung der Methode `setText()` wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe im `HH:mm:ss`-Format nicht analysieren kann.
:::


:::info Picker-Benutzeroberfläche 
Das Erscheinungsbild der Zeitwähler-Benutzeroberfläche hängt nicht nur von der ausgewählten Lokalisierung, sondern auch von dem verwendeten Browser und Betriebssystem ab. Dies gewährleistet eine automatische Konsistenz mit der Benutzeroberfläche, mit der die Benutzer bereits vertraut sind.
:::

## Statische Hilfsfunktionen {#static-utilities}

Die `TimeField`-Klasse bietet auch die folgenden statischen Hilfsfunktionen:

- `fromTime(String timeAsString)`: Konvertiert einen Zeitstring im HH:mm:ss-Format in ein LocalTime-Objekt, das dann mit dieser Klasse oder an anderer Stelle verwendet werden kann.

- `toTime(LocalTime time)`: Konvertiert ein LocalTime in einen Zeitstring im HH:mm:ss-Format.

- `isValidTime(String timeAsString)`: Überprüft, ob der angegebene String eine gültige HH:mm:ss-Zeit darstellt. Dies gibt einen booleschen Wert true zurück, wenn ja, false andernfalls.

## Beste Praktiken {#best-practices}

- **Klare Zeitformatbeispiele bereitstellen**: Zeigen Sie den Benutzern deutlich an, welches Zeitformat in der Nähe des `TimeField` erwartet wird. Verwenden Sie Beispiele oder Platzhalter, um ihnen zu helfen, die Zeit korrekt einzugeben. Wenn möglich, zeigen Sie das Zeitformat basierend auf dem Standort des Benutzers an.

- **Barrierefreiheit**: Nutzen Sie die `TimeField`-Komponente mit Blick auf die Barrierefreiheit und stellen Sie sicher, dass sie den Barrierefreiheitsstandards entspricht, z. B. durch Bereitstellung geeigneter Labels, ausreichenden Farbkontrasts und Kompatibilität mit Hilfstechnologien.

- **Zurücksetzen-Option**: Bieten Sie den Benutzern eine Möglichkeit, das `TimeField` einfach in einen leeren oder Standardzustand zurückzusetzen.
