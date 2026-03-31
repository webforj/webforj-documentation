---
sidebar_position: 7
title: Event Options
_i18n_hash: 64cfa37f974517956ccb3fd75618df50
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` ist ein vielseitiges webforJ-Tool, das entwickelt wurde, um Konfigurationseinstellungen für `Element`-Ereignisse innerhalb von webforJ-Anwendungen zu kapseln und zu verwalten. Als Container für verschiedene Optionen ermöglicht es Entwicklern, genau festzulegen, wie Ereignisse, die mit Elementen verbunden sind, verarbeitet werden sollen.

## Ereignisdaten {#event-data}

Ereignisdaten sind ein Schlüsselfeature von `ElementEventOptions`, das es Entwicklern ermöglicht, spezifische Informationen an die Ereignisoptionen anzuhängen. Diese Funktionalität erleichtert das Übermitteln von benutzerdefinierten Daten vom Client zum Server, wenn ein Ereignis ausgelöst wird. Diese Fähigkeit ist instrumental, um zusätzlichen Kontext oder Parameter, die mit dem Ereignis verbunden sind, zu vermitteln und ermöglicht den Zugriff und die Nutzung von Informationen, ohne dass zusätzliche Anfragen an den Client gestellt werden müssen.

Betrachten Sie beispielsweise ein Szenario, in dem Sie ein Button-Klick-Ereignis haben und den Benutzernamen des aktuellen Nutzers zusammen mit dem Ereignis übermitteln möchten. Anstatt bei jedem Klick den Benutzernamen vom Client abzufragen, senden Sie diese Information zusammen mit dem Ereignis als Daten.

:::tip
Für weitere Informationen siehe die Seiten [events](/docs/building-ui/events) und [Client/Server Interaction](/docs/architecture/client-server).
:::

Um Daten zu den Ereignisoptionen hinzuzufügen, können Sie die Methode `addData()` verwenden.

<!-- ### Beispiel -->

## Ausführen von JavaScript {#executing-javascript}

Die Klasse `ElementEventOptions` ermöglicht es Entwicklern, JavaScript-Code anzugeben, der auf der Client-Seite ausgewertet wird, bevor das zugehörige Ereignis ausgelöst wird. Diese Funktion ermöglicht es den Clients, Ereignisdaten vorzubereiten oder bei Bedarf zusätzliche Ereignisse auszulösen. Dies ist in vielen Fällen hilfreich, zum Beispiel wenn Sie Formulardaten auf der Client-Seite validieren möchten, bevor Sie sie über ein Formularereignis einreichen.

### Verwendung {#usage}
Um den Ereigniscode festzulegen, verwenden Sie die Methode `setCode()`.

## Filtern von Ereignissen {#filtering-events}

`ElementEventOptions` enthält eine Funktion zum Festlegen eines Filterausdrucks, der auf dem Client ausgewertet wird, bevor das Ereignis ausgelöst wird. Dieser Filterausdruck ermöglicht es dem Client zu entscheiden, ob das Ereignis fortfahren oder gestoppt werden soll, basierend auf bestimmten Bedingungen. Betrachten Sie ein Eingabefeld, bei dem Sie ein Ereignis nur auslösen möchten, wenn der eingegebene Text bestimmte Kriterien erfüllt, wie z.B. eine Mindestlänge.

### Verwendung {#usage-1}
Um den Ereignisfilter festzulegen, verwenden Sie die Methode `setFilter()`.

## Debouncing und Throttling {#debouncing-and-throttling}

### Zweck {#purpose}
`ElementEventOptions` bietet Mechanismen zum Debouncing und Throttling von Ereignissen. Diese Features sind nützlich, um die Häufigkeit von Ereignis-Listenern zu steuern und sicherzustellen, dass sie nur unter bestimmten Bedingungen ausgelöst werden.

### Verwendung {#usage-2}
- Um Debounce festzulegen, verwenden Sie die Methode `setDebounce`.
- Um Throttle festzulegen, verwenden Sie die Methode `setThrottle`.

### Beispiel {#example}
In Szenarien, in denen Sie schnelle Benutzereingaben wie Suchfelder behandeln möchten, können Sie Debounce verwenden, um die Ausführung zu verzögern, bis der Benutzer mit dem Tippen fertig ist.

## Merging von Ereignisoptionen {#merging-event-options}

Die Klasse `ElementEventOptions` unterstützt das Merging mit anderen Instanzen, wodurch Entwicklern ermöglicht wird, verschiedene Optionen zu aggregieren. Dieses Feature ist hilfreich, wenn Einstellungen aus verschiedenen Quellen kombiniert werden.

## Annotations {#annotations}

### Zweck {#purpose-1}
Zur Vereinfachung kann `ElementEventOptions` mithilfe von Annotations konfiguriert werden. Diese Annotations bieten eine prägnantere und ausdrucksvollere Möglichkeit, Ereignisoptionen festzulegen.

### Beispiel {#example-1}
Betrachten Sie das folgende Beispiel für eine Annotation:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
debounce = @DebounceSettings(value = 200))
```
