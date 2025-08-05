---
sidebar_position: 3
title: Elements
slug: element
_i18n_hash: d77ff55b483b72de9ee1d36473d7751d
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Die Entwickler von webforJ haben die Möglichkeit, nicht nur aus der umfangreichen Bibliothek von bereitgestellten Komponenten auszuwählen, sondern auch Komponenten von anderen Quellen zu integrieren. Um dies zu erleichtern, kann die `Element`-Komponente verwendet werden, um die Integration von allem, von einfachen HTML-Elementen bis hin zu komplexeren benutzerdefinierten Webkomponenten, zu vereinfachen.

:::important
Die `Element`-Komponente kann nicht erweitert werden und ist nicht die Basis-Komponente für alle Komponenten innerhalb von webforJ. Um mehr über die Komponentenhierarchie von webforJ zu erfahren, lesen Sie [diesen Artikel](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Ereignisse hinzufügen {#adding-events}

Um die Ereignisse zu nutzen, die möglicherweise mit Ihrem Element kommen, können Sie die Methoden `addEventListener` der `Element`-Komponente verwenden. Zum Hinzufügen eines Ereignisses ist mindestens der Typ/Name des Ereignisses erforderlich, das die Komponente erwartet, sowie ein Listener, der dem Ereignis hinzugefügt werden soll.

Es gibt auch zusätzliche Optionen, um Ereignisse weiter anzupassen, indem die Event Options-Konfigurationen verwendet werden.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Komponenteninteraktion {#component-interaction}

Die `Element`-Komponente fungiert als Container für andere Komponenten. Sie bietet eine Möglichkeit, Informationen für untergeordnete Komponenten zu organisieren und abzurufen, und bietet eine klare Reihe von Funktionen, um diese untergeordneten Komponenten nach Bedarf hinzuzufügen oder zu entfernen.

### Hinzufügen von untergeordneten Komponenten {#adding-child-components}

Die `Element`-Komponente unterstützt die Komposition von untergeordneten Komponenten. Entwickler können komplexe UI-Strukturen organisieren und verwalten, indem sie Komponenten als Kinder zur `Element` hinzufügen. Es gibt drei Methoden, um Inhalte innerhalb eines `Element` festzulegen:

1. **`add(Component... components)`**: Diese Methode ermöglicht es, eine oder mehrere Komponenten zu einem optionalen `String` hinzuzufügen, der einen bestimmten Slot bezeichnet, wenn er mit einer Webkomponente verwendet wird. Wird der Slot weggelassen, wird die Komponente zwischen den HTML-Tags hinzugefügt.

2. **`setHtml(String html)`**: Diese Methode nimmt den `String`, der an die Methode übergeben wird, und injiziert ihn als HTML innerhalb der Komponente. Abhängig von der `Element`-Komponente kann dies auf unterschiedliche Weise gerendert werden.

3. **`setText(String text)`**: Diese Methode verhält sich ähnlich wie die Methode `setHtml()`, injiziert jedoch wörtlichen Text in das `Element`.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
Das Aufrufen von `setHtml()` oder `setText()` ersetzt den Inhalt, der derzeit zwischen den öffnenden und schließenden Tags des Elements enthalten ist.
:::

### Komponenten entfernen {#removing-components}

Neben dem Hinzufügen von Komponenten zu einem `Element` sind die folgenden Methoden implementiert, um verschiedene untergeordnete Komponenten zu entfernen:

1. **`remove(Component... components)`**: Diese Methode nimmt eine oder mehrere Komponenten und entfernt sie als untergeordnete Komponenten.

2. **`removeAll()`**: Diese Methode entfernt alle untergeordneten Komponenten aus dem `Element`.

### Komponenten zugreifen {#accessing-components}

Um auf die verschiedenen untergeordneten Komponenten zuzugreifen, die innerhalb eines `Element` vorhanden sind, oder Informationen über diese Komponenten zu erhalten, stehen die folgenden Methoden zur Verfügung:

1. **`getComponents()`**: Diese Methode gibt eine Java `List` aller Kinder des `Element` zurück.

2. **`getComponents(String id)`**: Diese Methode ähnelt der obigen Methode, nimmt jedoch die serverseitige ID einer bestimmten Komponente und gibt sie zurück, wenn sie gefunden wird.

3. **`getComponentCount()`**: Gibt die Anzahl der untergeordneten Komponenten innerhalb des `Element` zurück.

## JavaScript-Funktionen aufrufen {#calling-javascript-functions}

Die `Element`-Komponente bietet zwei API-Methoden, die es ermöglichen, JavaScript-Funktionen auf HTML-Elemente aufzurufen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Diese Methode nimmt einen Funktionsnamen als String und optional ein oder mehrere Objekte als Parameter für die Funktion. Diese Methode wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert** wird, bis die JS-Methode zurückkehrt, was zu einer Rundreise führt. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java verwendet werden kann.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Wie bei der vorherigen Methode kann ein Funktionsname und optional Argumente für die Funktion übergeben werden. Diese Methode wird asynchron ausgeführt und **blockiert den ausführenden Thread nicht**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das weitere Interaktionen mit der Funktion und ihrer Nutzlast ermöglicht.

### Parameter übergeben {#passing-parameters}

Argumente, die an diese Methoden übergeben werden und in der Ausführung von JS-Funktionen verwendet werden, werden als JSON-Array serialisiert. Es gibt zwei bemerkenswerte Argumenttypen, die wie folgt behandelt werden:
- `this`: Die Verwendung des `this`-Schlüsselworts gibt der Methode eine Referenz auf die clientseitige Version der aufrufenden Komponente.
- `Component`: Alle Java-Komponenteninstanzen, die in eine der JsFunction-Methoden übergeben werden, werden durch die clientseitige Version der Komponente ersetzt.

:::info
Sowohl die synchrone als auch die asynchrone Funktionsaufrufe warten darauf, eine Methode aufzurufen, bis das `Element` zum DOM hinzugefügt wurde, bevor sie eine Funktion ausführen, jedoch wartet `callJsFunction()` nicht auf Anhänge von `component`-Argumenten, was zu einem Fehlschlag führen kann. Umgekehrt kann das Aufrufen von `callJsFunctionAsync()` möglicherweise niemals abgeschlossen werden, wenn ein Komponentenargument niemals angehängt wird.
:::

Im folgenden Demo wird ein Ereignis zu einem HTML `Button` hinzugefügt. Dieses Ereignis wird dann programmgesteuert durch den Aufruf der Methode `callJsFunctionAsync()` ausgelöst. Das resultierende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird dann verwendet, um eine weitere Message-Box zu erstellen, nachdem die asynchrone Funktion abgeschlossen wurde.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## JavaScript ausführen {#executing-javascript}

Zusätzlich zur Ausführung von JavaScript auf Anwendungsebene ist es auch möglich, JavaScript auf Elementebene auszuführen. Diese Ausführung auf der Elementebene ermöglicht es, den Kontext des HTML-Elements in die Ausführung einzubeziehen. Dies ist ein leistungsstarkes Werkzeug, das als Verbindung für Entwickler mit interaktiven Fähigkeiten in clientseitigen Umgebungen dient.

Ähnlich wie bei der Funktionsausführung kann die Ausführung von JavaScript synchron oder asynchron mit den folgenden Methoden erfolgen:

1. **`executeJs(String script)`**: Diese Methode nimmt einen `String`, der als JavaScript-Code im Client ausgeführt wird. Dieses Skript wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert** wird, bis die JS-Ausführung zurückkehrt und eine Rundreise resultiert. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java verwendet werden kann.

2. **`executeJsAsync(String script)`**: Wie bei der vorherigen Methode wird ein übergebenes `String`-Parameter als JavaScript-Code auf dem Client ausgeführt. Diese Methode wird asynchron ausgeführt und **blockiert den ausführenden Thread nicht**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das weitere Interaktionen mit der Funktion und ihrer Nutzlast ermöglicht.

:::tip
Diese Methoden haben Zugriff auf das Schlüsselwort `component`, das dem JavaScript-Code Zugriff auf die clientseitige Instanz der Komponente gibt, die das JavaScript ausführt.
:::
