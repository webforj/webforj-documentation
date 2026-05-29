---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 820bed6c059dad74a523673f245f3b2a
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Die Entwickler von webforJ haben die Möglichkeit, nicht nur aus der umfangreichen Bibliothek von bereitgestellten Komponenten zu wählen, sondern auch Komponenten von externen Quellen zu integrieren. Um dies zu erleichtern, kann die `Element`-Komponente verwendet werden, um die Integration von allem, von einfachen HTML-Elementen bis hin zu komplexeren benutzerdefinierten Webkomponenten, zu vereinfachen.

:::important
Die `Element`-Komponente kann nicht erweitert werden und ist nicht die Basis-Komponente für alle Komponenten innerhalb von webforJ. Um mehr über die Komponenten-Hierarchie von webforJ zu erfahren, lesen Sie [diesen Artikel](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## Ereignisse hinzufügen {#adding-events}

Um Ereignisse, die möglicherweise mit Ihrem Element verbunden sind, nutzen zu können, können Sie die Methoden `addEventListener` der `Element`-Komponente verwenden. Das Hinzufügen eines Ereignisses erfordert mindestens den Typ/Namen des erwarteten Ereignisses und einen Listener, der zu diesem Ereignis hinzugefügt wird.

Es gibt auch zusätzliche Optionen, um Ereignisse weiter anzupassen, indem die Event-Options-Konfigurationen verwendet werden.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Komponenteninteraktion {#component-interaction}

Die `Element`-Komponente fungiert als Container für andere Komponenten. Sie bietet eine Möglichkeit, Informationen für untergeordnete Komponenten zu organisieren und abzurufen, und bietet eine klare Reihe von Funktionen, um diese untergeordneten Komponenten nach Bedarf hinzuzufügen oder zu entfernen.

### Hinzufügen von untergeordneten Komponenten {#adding-child-components}

Die `Element`-Komponente unterstützt die Komposition von untergeordneten Komponenten. Entwickler können komplexe UI-Strukturen organisieren und verwalten, indem sie Komponenten als Kinder zur `Element`-Komponente hinzufügen. Es gibt drei Methoden, um Inhalte innerhalb eines `Element` festzulegen:

1. **`add(Component... components)`**: Diese Methode ermöglicht es, ein oder mehrere Komponenten zu einem optionalen `String` hinzuzufügen, der einen bestimmten Slot angibt, wenn er mit einer Webkomponente verwendet wird. Das Weglassen des Slots fügt die Komponente zwischen den HTML-Tags hinzu.

2. **`setHtml(String html)`**: Diese Methode nimmt den `String`, der der Methode übergeben wird, und injiziert ihn als HTML innerhalb der Komponente. Je nach `Element` kann dies auf unterschiedliche Weise gerendert werden.

3. **`setText(String text)`**: Diese Methode verhält sich ähnlich wie die Methode `setHtml()`, injiziert aber den Literaltext in das `Element`.

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Das Aufrufen von `setHtml()` oder `setText()` ersetzt den Inhalt, der derzeit zwischen den öffnenden und schließenden Tags des Elements enthalten ist.
:::

### Entfernen von Komponenten {#removing-components}

Neben dem Hinzufügen von Komponenten zu einem `Element` sind die folgenden Methoden implementiert, um verschiedene Kind-Komponenten zu entfernen:

1. **`remove(Component... components)`**: Diese Methode nimmt eine oder mehrere Komponenten und entfernt sie als untergeordnete Komponenten.

2. **`removeAll()`**: Diese Methode entfernt alle untergeordneten Komponenten aus dem `Element`.

### Zugreifen auf Komponenten {#accessing-components}

Um auf die verschiedenen untergeordneten Komponenten innerhalb eines `Element` oder Informationen über diese Komponenten zuzugreifen, stehen die folgenden Methoden zur Verfügung:

1. **`getComponents()`**: Diese Methode gibt eine Java `List` aller Kinder des `Element` zurück.

2. **`getComponents(String id)`**: Diese Methode ist ähnlich wie die oben genannte, nimmt jedoch die serverseitige ID einer bestimmten Komponente und gibt sie zurück, wenn sie gefunden wird.

3. **`getComponentCount()`**: Gibt die Anzahl der untergeordneten Komponenten im `Element` zurück.

## Aufrufen von JavaScript-Funktionen {#calling-javascript-functions}

Die `Element`-Komponente bietet zwei API-Methoden, die es ermöglichen, JavaScript-Funktionen auf HTML-Elementen aufzurufen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Diese Methode nimmt einen Funktionsnamen als `String` und optional ein oder mehrere Objekte als Parameter für die Funktion. Diese Methode wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert wird**, bis die JS-Methode zurückkehrt, und zu einer Rundreise führt. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java geworfen und verwendet werden kann.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Wie bei der vorherigen Methode kann ein Funktionsname und optionale Argumente für die Funktion übergeben werden. Diese Methode wird asynchron ausgeführt und **blockiert nicht den ausführenden Thread**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das eine weitere Interaktion mit der Funktion und ihren Payload ermöglicht.

### Parameter übergeben {#passing-parameters}

Argumente, die an diese Methoden übergeben werden und in der Ausführung von JS-Funktionen verwendet werden, werden als JSON-Array serialisiert. Es gibt zwei bemerkenswerte Argumenttypen, die wie folgt behandelt werden:
- `this`: Die Verwendung des Schlüssels `this` gibt der Methode eine Referenz auf die clientseitige Version der aufrufenden Komponente.
- `Component`: Jede Java-Komponenteninstanz, die in eine der JsFunction-Methoden übergeben wird, wird durch die clientseitige Version der Komponente ersetzt.

:::info
Sowohl synchrones als auch asynchrones Funktionsaufrufen wartet, bis das `Element` zum DOM hinzugefügt wurde, bevor eine Funktion ausgeführt wird, jedoch wird `callJsFunction()` nicht auf `component`-Argumente warten, was zu einem Fehler führen kann. Umgekehrt kann das Aufrufen von `callJsFunctionAsync()` möglicherweise nie abgeschlossen werden, wenn ein Komponentenargument niemals angehängt wird.
:::

In der untenstehenden Demo wird ein Ereignis zu einer HTML `Button` hinzugefügt. Dieses Ereignis wird dann programmgesteuert durch den Aufruf der Methode `callJsFunctionAsync()` ausgelöst. Das resultierende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird dann verwendet, um eine weitere Nachrichtenbox zu erstellen, sobald die asynchrone Funktion abgeschlossen ist.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## Ausführen von JavaScript {#executing-javascript}

Neben der Ausführung von JavaScript auf Anwendungsebene ist es auch möglich, JavaScript auf Elementebene auszuführen. Diese Ausführung auf Elementebene ermöglicht es, den Kontext des HTML-Elements in die Ausführung einzubeziehen. Dies ist ein leistungsstarkes Werkzeug, das als Verbindung des Entwicklers zu interaktiven Fähigkeiten mit clientseitigen Umgebungen fungiert.

Ähnlich wie bei der Funktionsausführung kann die Ausführung von JavaScript synchron oder asynchron mit den folgenden Methoden erfolgen:

1. **`executeJs(String script)`**: Diese Methode nimmt einen `String`, der als JavaScript-Code im Client ausgeführt wird. Dieses Skript wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert wird**, bis die JS-Ausführung zurückkehrt, und zu einer Rundreise führt. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java geworfen und verwendet werden kann.

2. **`executeJsAsync(String script)`**: Wie bei der vorherigen Methode wird ein übergebener `String`-Parameter als JavaScript-Code im Client ausgeführt. Diese Methode wird asynchron ausgeführt und **blockiert nicht den ausführenden Thread**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das eine weitere Interaktion mit der Funktion und ihren Payload ermöglicht.

:::tip
Diese Methoden haben Zugriff auf das Schlüsselwort `component`, das dem JavaScript-Code Zugriff auf die clientseitige Instanz der Komponente gibt, die das JavaScript ausführt.
:::
