---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 994cad91e2870d59f3c0eec7c2b47141
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` ist eine Benutzeroberflächenkomponente, die es Benutzern ermöglicht, Zeiten in Stunden, Minuten und optional Sekunden einzugeben oder auszuwählen. Sie bietet eine intuitive und effiziente Möglichkeit, zeitbezogene Informationen in verschiedenen Anwendungen zu verwalten.

<!-- INTRO_END -->

## Verwendung des `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` erweitert die gemeinsam genutzte `Field`-Klasse, die gemeinsame Funktionen für alle Feldkomponenten bereitstellt. Das folgende Beispiel erstellt ein Erinnerung `TimeField`, das auf die aktuelle Zeit eingestellt ist.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Anwendungsfälle {#usages}

Das `TimeField` ist ideal zum Auswählen und Anzeigen von Zeiten in Ihrer App. Hier sind einige Beispiele, wann das `TimeField` verwendet werden sollte:

1. **Ereignisplanung**: Zeitfelder sind entscheidend in Apps, die das Festlegen von Zeiten für Ereignisse, Termine oder Meetings erfordern.

2. **Zeiterfassung und Protokollierung**: Apps, die Zeit verfolgen, wie z.B. Stundennachweise, benötigen Zeitfelder für genaue Eingaben.

3. **Erinnerungen und Alarme**: Die Verwendung eines Zeitfeldes vereinfacht den Eingabeprozess für Benutzer, die Erinnerungen oder Alarme in Ihrer App einstellen.

## Minimal- und Maximalwert {#min-and-max-value}

Mit den Methoden `setMin()` und `setMax()` können Sie einen Bereich akzeptabler Zeiten spezifizieren.

- **Für `setMin()`**: Wenn der eingegebene Wert in die Komponente früher als die angegebene minimale Zeit ist, schlägt die Validierung der Einschränkung fehl. Wenn sowohl der Min- als auch der Max-Wert gesetzt sind, muss der Min-Wert eine Zeit sein, die gleich oder früher als der Max-Wert ist.

- **Für `setMax()`**: Wenn der eingegebene Wert in die Komponente später als die angegebene maximale Zeit ist, schlägt die Validierung der Einschränkung fehl. Wenn sowohl der Min- als auch der Max-Wert gesetzt sind, muss der Max-Wert eine Zeit sein, die gleich oder später als der Min-Wert ist.

## Wertbearbeitung und Lokalisierung {#value-handling-and-localization}

Intern repräsentiert die `TimeField`-Komponente ihren Wert mithilfe eines `LocalTime`-Objekts aus dem `java.time`-Paket. Dies ermöglicht Entwicklern, mit präzisen Zeitwerten zu interagieren, unabhängig davon, wie sie visuell dargestellt werden.

Während die **Client-seitige Komponente die Zeit unter Verwendung der Browsersprache des Benutzers anzeigt**, ist das analysierte und gespeicherte Format immer standardisiert als `HH:mm:ss`.

Wenn Sie einen Rohstringwert festlegen, verwenden Sie die `setText()`-Methode vorsichtig:

```java
timeField.setText("09:15:00"); // gültig
```

:::warning
 Wenn Sie die `setText()`-Methode verwenden, wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe im `HH:mm:ss`-Format nicht parsen kann.
:::


:::info Picker UI 
Das Erscheinungsbild der Zeitpicker-Eingabeoberfläche hängt nicht nur von der ausgewählten Sprache ab, sondern auch vom verwendeten Browser und Betriebssystem. Dies sorgt für eine automatische Konsistenz mit der Benutzeroberfläche, die die Benutzer bereits kennen.
:::

## Statische Hilfsfunktionen {#static-utilities}

Die `TimeField`-Klasse bietet auch die folgenden statischen Hilfsmethoden:

- `fromTime(String timeAsString)`: Konvertiert einen Zeitstring im HH:mm:ss-Format in ein LocalTime-Objekt, das dann mit dieser Klasse oder anderswo verwendet werden kann.

- `toTime(LocalTime time)`: Konvertiert ein LocalTime in einen Zeitstring im HH:mm:ss-Format.

- `isValidTime(String timeAsString)`: Überprüft, ob der angegebene String eine gültige HH:mm:ss-Zeit ist. Dies gibt einen booleschen Wert true zurück, wenn ja, andernfalls false.

## Best Practices {#best-practices}

- **Klare Beispiele für das Zeitformat bereitstellen**: Zeigen Sie den Benutzern deutlich das erwartete Zeitformat in der Nähe des `TimeField`. Verwenden Sie Beispiele oder Platzhalter, um ihnen zu helfen, die Zeit korrekt einzugeben. Wenn möglich, zeigen Sie das Zeitformat basierend auf dem Standort des Benutzers an.

- **Barrierefreiheit**: Nutzen Sie die `TimeField`-Komponente mit Blick auf die Barrierefreiheit und stellen Sie sicher, dass sie die Zugänglichkeitstandards erfüllt, z. B. durch Bereitstellung geeigneter Labels, ausreichenden Farbkontrasts und Kompatibilität mit unterstützenden Technologien.

- **Zurücksetzen-Option**: Bieten Sie eine Möglichkeit für Benutzer, das `TimeField` einfach in einen leeren oder Standardzustand zurückzusetzen.
