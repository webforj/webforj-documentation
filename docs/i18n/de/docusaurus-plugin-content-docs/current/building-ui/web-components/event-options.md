---
sidebar_position: 4
title: Event Options
_i18n_hash: 8bf57e40eec8e571f3d62266e388f114
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` ist ein vielseitiges webforJ-Tool, das entwickelt wurde, um Konfigurationseinstellungen für `Element`-Ereignisse innerhalb von webforJ-Anwendungen zu kapseln und zu verwalten. Als Container für verschiedene Optionen ermöglicht es Entwicklern, genau festzulegen, wie mit Ereignissen, die mit Elementen verbunden sind, umgegangen werden soll.

## Ereignisdaten {#event-data}

Ereignisdaten sind ein Hauptmerkmal von `ElementEventOptions`, das es Entwicklern ermöglicht, spezifische Informationen an die Ereignisoptionen anzuhängen. Diese Funktionalität erleichtert das Übermitteln von benutzerdefinierten Daten vom Client zum Server, wenn ein Ereignis ausgelöst wird. Diese Fähigkeit ist entscheidend, um zusätzlichen Kontext oder Parameter im Zusammenhang mit dem Ereignis zu übermitteln und ermöglicht es, Informationen zuzugreifen und zu verwenden, ohne dass zusätzliche Anfragen vom Client erforderlich sind.

Betrachten Sie beispielsweise ein Szenario, in dem Sie ein Klickereignis auf eine Schaltfläche haben und den Benutzernamen des aktuellen Benutzers zusammen mit dem Ereignis übermitteln möchten. Anstatt bei jedem Mal den Benutzernamen eines Benutzers vom Client abzufragen, senden Sie diese Informationen zusammen mit dem Ereignis als Daten.

:::tip
Für weitere Informationen siehe die Seiten zu [Ereignissen](../../building-ui/events) und [Client/Server-Interaktion](../../architecture/client-server).
:::

Um Daten zu den Ereignisoptionen hinzuzufügen, können Sie die `addData()`-Methode verwenden.

## Ausführen von JavaScript {#executing-javascript}

Die `ElementEventOptions`-Klasse ermöglicht es Entwicklern, JavaScript-Code anzugeben, der auf der Client-Seite bewertet werden soll, bevor das zugehörige Ereignis ausgelöst wird. Diese Funktion ermöglicht es den Clients, Ereignisdaten vorzubereiten oder zusätzliche Ereignisse nach Bedarf auszulösen. Dies ist in vielen Fällen hilfreich, beispielsweise wenn Sie Formulardaten auf der Client-Seite validieren möchten, bevor Sie sie über ein Formularereignis einreichen.

### Verwendung {#usage}
Um den Ereigniscodes festzulegen, verwenden Sie die `setCode()`-Methode.

## Ereignisse filtern {#filtering-events}

`ElementEventOptions` enthält eine Funktion zum Festlegen eines Filterausdrucks, der auf dem Client bewertet werden soll, bevor das Ereignis ausgelöst wird. Dieser Filterausdruck ermöglicht es dem Client, zu bestimmen, ob das Ereignis fortgesetzt oder gestoppt werden soll, basierend auf bestimmten Bedingungen. Betrachten Sie ein Eingabefeld, in dem Sie ein Ereignis nur auslösen möchten, wenn der eingegebene Text bestimmte Kriterien erfüllt, wie etwa eine Mindestlänge.

### Verwendung {#usage-1}
Um den Ereignisfilter festzulegen, verwenden Sie die `setFilter()`-Methode.

## Entprellen und Drosseln {#debouncing-and-throttling}

### Zweck {#purpose}
`ElementEventOptions` bietet Mechanismen zum Entprellen und Drosseln von Ereignissen. Diese Funktionen sind nützlich, um die Häufigkeit von Ereignis-Listenern zu steuern und sicherzustellen, dass sie nur unter bestimmten Bedingungen ausgelöst werden.

### Verwendung {#usage-2}
- Um das Entprellen festzulegen, verwenden Sie die `setDebounce`-Methode.
- Um das Drosseln festzulegen, verwenden Sie die `setThrottle`-Methode.

### Beispiel {#example}
In Szenarien, in denen Sie schnelle Benutzereingaben, wie beispielsweise Eingabefelder für die Suche, behandeln möchten, können Sie das Entprellen verwenden, um die Ausführung zu verzögern, bis der Benutzer mit dem Tippen fertig ist.

## Ereignisoptionen zusammenführen {#merging-event-options}

Die `ElementEventOptions`-Klasse unterstützt das Zusammenführen mit anderen Instanzen, sodass Entwickler verschiedene Optionen aggregieren können. Diese Funktion ist hilfreich, wenn Einstellungen aus unterschiedlichen Quellen kombiniert werden.

## Anmerkungen {#annotations}

### Zweck {#purpose-1}
Zur Vereinfachung kann `ElementEventOptions` mithilfe von Anmerkungen konfiguriert werden. Diese Anmerkungen bieten eine prägnantere und ausdrucksvollere Möglichkeit, Ereignisoptionen festzulegen.

### Beispiel {#example-1}
Betrachten Sie das folgende Beispiel für eine Anmerkung:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
