---
sidebar_position: 4
title: Event Options
_i18n_hash: d780e41b809f0e3df55f65a1c71983a0
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` ist ein vielseitiges webforJ-Tool, das entwickelt wurde, um Konfigurationseinstellungen für `Element`-Ereignisse innerhalb von webforJ-Anwendungen zu kapseln und zu verwalten. Als Container für verschiedene Optionen ermöglicht es Entwicklern, präzise festzulegen, wie Ereignisse, die mit Elementen verbunden sind, verarbeitet werden sollen.

## Ereignisdaten {#event-data}

Ereignisdaten sind eine Schlüsselkomponente von `ElementEventOptions`, die es Entwicklern ermöglichen, spezifische Informationen an die Ereignisoptionen anzuhängen. Diese Funktionalität erleichtert das Übermitteln von benutzerdefinierten Daten vom Client zum Server, wenn ein Ereignis ausgelöst wird. Diese Fähigkeit ist entscheidend, um zusätzlichen Kontext oder Parameter, die mit dem Ereignis verbunden sind, zu übermitteln und ermöglicht es, Informationen zu nutzen, ohne dass zusätzliche Anfragen an den Client gemacht werden müssen.

Betrachten Sie beispielsweise ein Szenario, in dem Sie ein Klickereignis eines Buttons haben und den aktuellen Benutzernamen des Benutzers zusammen mit dem Ereignis übermitteln möchten. Anstatt den Benutzernamen jedes Mal vom Client abzufragen, senden Sie diese Informationen zusammen mit dem Ereignis als Daten.

:::tip
Für weitere Informationen, siehe die Seiten [Ereignisse](../../building-ui/events) und [Client/Server-Interaktion](../../architecture/client-server).
:::

Um Daten zu den Ereignisoptionen hinzuzufügen, können Sie die Methode `addData()` verwenden.

## Ausführen von JavaScript {#executing-javascript}

Die Klasse `ElementEventOptions` ermöglicht es Entwicklern, JavaScript-Code zu spezifizieren, der auf der Client-Seite evaluiert werden soll, bevor das zugehörige Ereignis ausgelöst wird. Diese Funktion ermöglicht es den Clients, Ereignisdaten vorzubereiten oder zusätzliche Ereignisse nach Bedarf auszulösen. Dies ist in vielen Fällen hilfreich, zum Beispiel, wenn Formulardaten auf der Client-Seite validiert werden sollen, bevor sie über ein Formulareignis gesendet werden.

### Verwendung {#usage}
Um den Ereigniscode festzulegen, verwenden Sie die Methode `setCode()`.

## Filtern von Ereignissen {#filtering-events}

`ElementEventOptions` enthält eine Funktion zum Festlegen eines Filterausdrucks, der auf dem Client vor dem Auslösen des Ereignisses ausgewertet wird. Dieser Filterausdruck ermöglicht es dem Client zu bestimmen, ob das Ereignis fortgesetzt oder gestoppt werden soll, basierend auf bestimmten Bedingungen. Betrachten Sie ein Eingabefeld, bei dem Sie ein Ereignis nur auslösen möchten, wenn der eingegebene Text bestimmten Kriterien entspricht, z. B. einer Mindestlänge.

### Verwendung {#usage-1}
Um den Ereignisfilter festzulegen, verwenden Sie die Methode `setFilter()`.

## Debouncing und Throttling {#debouncing-and-throttling}

### Zweck {#purpose}
`ElementEventOptions` bietet Mechanismen zum Debouncing und Throttling von Ereignissen. Diese Funktionen sind nützlich, um die Häufigkeit von Ereignis-Listenern zu steuern und sicherzustellen, dass sie nur unter bestimmten Bedingungen ausgelöst werden.

### Verwendung {#usage-2}
- Um Debounce festzulegen, verwenden Sie die Methode `setDebounce`.
- Um Throttle festzulegen, verwenden Sie die Methode `setThrottle`.

### Beispiel {#example}
In Szenarien, in denen Sie rasches Benutzereingaben, wie zum Beispiel bei Suchfeldern, behandeln möchten, können Sie Debounce verwenden, um die Ausführung zu verzögern, bis der Benutzer mit dem Tippen fertig ist.

## Zusammenführen von Ereignisoptionen {#merging-event-options}

Die Klasse `ElementEventOptions` unterstützt das Zusammenführen mit anderen Instanzen, sodass Entwickler verschiedene Optionen aggregieren können. Diese Funktion ist hilfreich, wenn Einstellungen aus verschiedenen Quellen kombiniert werden.

## Anmerkungen {#annotations}

### Zweck {#purpose-1}
Zur Vereinfachung kann `ElementEventOptions` mithilfe von Anmerkungen konfiguriert werden. Diese Anmerkungen bieten eine prägnantere und ausdrucksstarke Möglichkeit, Ereignisoptionen festzulegen.

### Beispiel {#example-1}
Betrachten Sie das folgende Beispiel einer Anmerkung:

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")}, 
      debounce = @DebounceSettings(value = 200))
```
