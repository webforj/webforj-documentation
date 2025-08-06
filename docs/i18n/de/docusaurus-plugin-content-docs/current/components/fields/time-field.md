---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: aa5cbd6fb54c91be419380eeaf26e65b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` ist eine Benutzeroberflächenkomponente, die es Benutzern ermöglicht, Zeiten in Stunden, Minuten und optional Sekunden einzugeben oder auszuwählen. Sie bietet eine intuitive und effiziente Möglichkeit, zeitbezogene Informationen in verschiedenen Anwendungen zu verwalten.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Verwendungen {#usages}

Das `TimeField` eignet sich ideal zum Auswählen und Anzeigen von Zeiten in Ihrer App. Hier sind einige Beispiele, wann Sie das `TimeField` verwenden sollten:

1. **Ereignisplanung**: Zeitfelder sind in Apps, die Zeiten für Ereignisse, Termine oder Meetings festlegen, unerlässlich.

2. **Zeiterfassung und Protokollierung**: Apps, die Zeit erfassen, wie z. B. Stundenzettel, benötigen Zeitfelder für genaue Einträge.

3. **Erinnerungen und Alarme**: Die Verwendung eines Zeitfelds vereinfacht den Eingabeprozess für Benutzer, die Erinnerungen oder Alarme in Ihrer App festlegen.

## Mindest- und Höchstwert {#min-and-max-value}

Mit den Methoden `setMin()` und `setMax()` können Sie einen Bereich akzeptabler Zeiten festlegen.

- **Für `setMin()`**: Wenn der eingegebene Wert in die Komponente früher ist als die angegebene Mindestzeit, wird die Komponente die Validierung der Einschränkungen nicht bestehen. Wenn sowohl die Min- als auch die Max-Werte festgelegt sind, muss der Min-Wert eine Zeit sein, die gleich oder früher als der Max-Wert ist.

- **Für `setMax()`**: Wenn der eingegebene Wert in die Komponente später ist als die angegebene Höchstzeit, wird die Komponente die Validierung der Einschränkungen nicht bestehen. Wenn sowohl die Min- als auch die Max-Werte festgelegt sind, muss der Max-Wert eine Zeit sein, die gleich oder später als der Min-Wert ist.

## Wertbehandlung und Lokalisierung {#value-handling-and-localization}

Intern repräsentiert die `TimeField`-Komponente ihren Wert mit einem `LocalTime`-Objekt aus dem `java.time`-Paket. Dies ermöglicht Entwicklern den Umgang mit präzisen Zeitwerten, unabhängig davon, wie sie visuell dargestellt werden.

Während die **Client-seitige Komponente die Zeit unter Verwendung der Benutzer-Browser-Lokalisierung anzeigt**, ist das analysierte und gespeicherte Format immer standardisiert als `HH:mm:ss`.

Wenn Sie einen Rohzeichenwert festlegen, verwenden Sie die Methode `setText()` mit Bedacht:

```java
timeField.setText("09:15:00"); // gültig
```

:::warning
 Wenn Sie die Methode `setText()` verwenden, wird eine `IllegalArgumentException` ausgelöst, wenn die Komponente die Eingabe im `HH:mm:ss`-Format nicht analysieren kann.
:::

:::info Picker UI 
Das Erscheinungsbild der Zeitpicker-Eingabebedienoberfläche hängt nicht nur von der ausgewählten Lokalisierung, sondern auch vom verwendeten Browser und Betriebssystem ab. Dies sorgt für automatische Konsistenz mit der Benutzeroberfläche, die den Benutzern bereits vertraut ist.
:::

## Statische Funktionen {#static-utilities}

Die `TimeField`-Klasse bietet auch die folgenden statischen Hilfsmethoden:

- `fromTime(String timeAsString)`: Konvertieren Sie eine Zeitzeichenfolge im HH:mm:ss-Format in ein LocalTime-Objekt, das dann in dieser Klasse oder anderswo verwendet werden kann.

- `toTime(LocalTime time)`: Konvertieren Sie ein LocalTime in eine Zeitzeichenfolge im HH:mm:ss-Format.

- `isValidTime(String timeAsString)`: Überprüfen, ob die angegebene Zeichenfolge eine gültige HH:mm:ss-Zeit ist. Dies gibt einen booleschen Wert true zurück, wenn dies der Fall ist, andernfalls false.

## Beste Praktiken {#best-practices}

- **Klare Zeitformatbeispiele bereitstellen**: Zeigen Sie den Benutzern klar das erwartete Zeitformat in der Nähe des `TimeField` an. Verwenden Sie Beispiele oder Platzhalter, um ihnen zu helfen, die Zeit korrekt einzugeben. Wenn möglich, zeigen Sie das Zeitformat basierend auf dem Standort des Benutzers an.

- **Barrierefreiheit**: Verwenden Sie die `TimeField`-Komponente mit Blick auf die Barrierefreiheit und stellen Sie sicher, dass sie die Barrierefreiheitsstandards erfüllt, z. B. indem Sie ordnungsgemäße Labels, ausreichenden Farbkontrast und die Kompatibilität mit Hilfstechnologien bereitstellen.

- **Zurücksetzen-Option**: Bieten Sie den Benutzern eine Möglichkeit, das `TimeField` einfach auf einen leeren oder Standardzustand zurückzusetzen.
