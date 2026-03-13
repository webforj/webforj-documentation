---
sidebar_position: 7
title: Event Options
_i18n_hash: 4311668d9a6bb9e9cebcf988e515d91a
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` ist ein vielseitiges webforJ-Werkzeug, das entwickelt wurde, um Konfigurationseinstellungen für `Element`-Ereignisse in webforJ-Anwendungen zu kapseln und zu verwalten. Als Container für verschiedene Optionen ermöglicht es Entwicklern, präzise festzulegen, wie Ereignisse, die mit Elementen verbunden sind, verarbeitet werden sollen.

## Ereignisdaten {#event-data}

Ereignisdaten sind ein zentrales Merkmal von `ElementEventOptions`, das es Entwicklern ermöglicht, spezifische Informationen an die Ereignisoptionen anzuhängen. Diese Funktionalität erleichtert das Übertragen von benutzerdefinierten Daten vom Client zum Server, wenn ein Ereignis ausgelöst wird. Diese Fähigkeit ist entscheidend, um zusätzlichen Kontext oder Parameter, die mit dem Ereignis verbunden sind, zu übermitteln und ermöglicht den Zugriff auf Informationen ohne zusätzliche Anfragen vom Client.

Betrachten Sie beispielsweise ein Szenario, in dem Sie ein Button-Klick-Ereignis haben und den aktuellen Benutzernamen des Nutzers zusammen mit dem Ereignis übergeben möchten. Anstatt bei jedem Klick den Benutzernamen abzufragen, senden Sie diese Informationen mit dem Ereignis als Daten.

:::tip
Für weitere Informationen siehe die Seiten [events](/docs/building-ui/events) und [Client/Server Interaction](/docs/architecture/client-server).
:::

Um Daten zu den Ereignisoptionen hinzuzufügen, können Sie die Methode `addData()` verwenden.

## Ausführen von JavaScript {#executing-javascript}

Die Klasse `ElementEventOptions` ermöglicht es Entwicklern, JavaScript-Code anzugeben, der auf der Client-Seite evaluiert werden soll, bevor das zugehörige Ereignis ausgelöst wird. Diese Funktion ermöglicht es den Clients, Ereignisdaten vorzubereiten oder zusätzliche Ereignisse nach Bedarf auszulösen. Dies ist in vielen Fällen hilfreich, z. B. wenn Sie Formulardaten auf der Client-Seite validieren möchten, bevor Sie sie über ein Formulareingabeereignis übermitteln.

### Verwendung {#usage}
Um den Ereigniscode festzulegen, verwenden Sie die Methode `setCode()`.

## Filterung von Ereignissen {#filtering-events}

`ElementEventOptions` umfasst eine Funktion zum Festlegen eines Filterausdrucks, der auf dem Client vor dem Auslösen des Ereignisses evaluiert werden soll. Dieser Filterausdruck ermöglicht es dem Client zu bestimmen, ob das Ereignis fortgesetzt oder gestoppt werden soll, basierend auf bestimmten Bedingungen. Betrachten Sie ein Eingabefeld, in dem Sie ein Ereignis nur auslösen möchten, wenn der eingegebene Text spezifischen Kriterien entspricht, wie z. B. einer Mindestlänge.

### Verwendung {#usage-1}
Um den Ereignisfilter festzulegen, verwenden Sie die Methode `setFilter()`.

## Entprellen und Drosseln {#debouncing-and-throttling}

### Zweck {#purpose}
`ElementEventOptions` bietet Mechanismen zum Entprellen und Drosseln von Ereignissen. Diese Funktionen sind nützlich, um die Häufigkeit der Ereignis-Listener zu steuern und sicherzustellen, dass sie nur unter bestimmten Bedingungen ausgelöst werden.

### Verwendung {#usage-2}
- Um das Entprellen festzulegen, verwenden Sie die Methode `setDebounce`.
- Um die Drosselung festzulegen, verwenden Sie die Methode `setThrottle`.

### Beispiel {#example}
In Szenarien, in denen Sie mit schnellem Benutzereingaben, wie z. B. Suchfeldern, umgehen möchten, können Sie Entprellen verwenden, um die Ausführung zu verzögern, bis der Benutzer mit dem Tippen fertig ist.

## Merging von Ereignisoptionen {#merging-event-options}

Die Klasse `ElementEventOptions` unterstützt das Zusammenführen mit anderen Instanzen, was es Entwicklern ermöglicht, verschiedene Optionen zu aggregieren. Diese Funktion ist hilfreich, um Einstellungen aus verschiedenen Quellen zu kombinieren.

## Annotations {#annotations}

### Zweck {#purpose-1}
Zur Vereinfachung kann `ElementEventOptions` mithilfe von Annotationen konfiguriert werden. Diese Annotationen bieten eine prägnantere und ausdrucksstärkere Möglichkeit, Ereignisoptionen festzulegen.

### Beispiel {#example-1}
Betrachten Sie das folgende Beispiel einer Annotation:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
