---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 067ff9e31676f6991dab011252151043
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Die Entwickler von webforJ haben die Möglichkeit, nicht nur aus der umfangreichen Bibliothek von bereitgestellten Komponenten zu wählen, sondern auch Komponenten aus anderen Quellen zu integrieren. Um dies zu erleichtern, kann die `Element`-Komponente verwendet werden, um die Integration von allem, von einfachen HTML-Elementen bis hin zu komplexeren benutzerdefinierten Webkomponenten, zu vereinfachen.

:::important
Die `Element`-Komponente kann nicht erweitert werden und ist nicht die Basis-Komponente für alle Komponenten innerhalb von webforJ. Um mehr über die Komponentenhierarchie von webforJ zu erfahren, lesen Sie [diesen Artikel](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Hinzufügen von Ereignissen {#adding-events}

Um Ereignisse zu nutzen, die mit Ihrem Element verbunden sein können, können Sie die Methoden `addEventListener` der `Element`-Komponente verwenden. Das Hinzufügen eines Ereignisses erfordert mindestens den Typ/Namen des Ereignisses, das die Komponente erwartet, und einen Listener, der zum Ereignis hinzugefügt wird. 

Es gibt auch zusätzliche Optionen, um Ereignisse weiter anzupassen, indem die Event Options-Konfigurationen verwendet werden.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Interaktion mit Komponenten {#component-interaction}

Die `Element`-Komponente fungiert als Container für andere Komponenten. Sie bietet eine Möglichkeit, Informationen für untergeordnete Komponenten zu organisieren und abzurufen, und bietet eine klare Reihe von Funktionen, um diese untergeordneten Komponenten nach Bedarf hinzuzufügen oder zu entfernen.

### Hinzufügen von untergeordneten Komponenten {#adding-child-components}

Die `Element`-Komponente unterstützt die Zusammenstellung untergeordneter Komponenten. Entwickler können komplexe UI-Strukturen organisieren und verwalten, indem sie Komponenten als Kinder zur `Element` hinzufügen. Es gibt drei Methoden, um Inhalte innerhalb eines `Element` festzulegen:

1. **`add(Component... components)`**: Diese Methode erlaubt es, ein oder mehrere Komponenten zu einem optionalen `String` hinzuzufügen, der einen bestimmten Slot bezeichnet, wenn er mit einer Web-Komponente verwendet wird. Das Weglassen des Slots fügt die Komponente zwischen den HTML-Tags hinzu.

2. **`setHtml(String html)`**: Diese Methode nimmt den `String`, der an die Methode übergeben wird, und injiziert ihn als HTML innerhalb der Komponente. Abhängig von der `Element` kann dies auf verschiedene Weisen gerendert werden.

3. **`setText(String text)`**: Diese Methode verhält sich ähnlich wie die Methode `setHtml()`, injiziert aber wörtlichen Text in das `Element`.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
Das Aufrufen von `setHtml()` oder `setText()` ersetzt den Inhalt, der derzeit zwischen den Öffnungs- und Schließtags des Elements enthalten ist.
:::

### Entfernen von Komponenten {#removing-components}

Neben dem Hinzufügen von Komponenten zu einem `Element` sind die folgenden Methoden zur Entfernung verschiedener untergeordneter Komponenten implementiert:

1. **`remove(Component... components)`**: Diese Methode nimmt eine oder mehrere Komponenten und entfernt sie als untergeordnete Komponenten.

2. **`removeAll()`**: Diese Methode entfernt alle untergeordneten Komponenten vom `Element`.

### Zugriff auf Komponenten {#accessing-components}

Um auf die verschiedenen untergeordneten Komponenten innerhalb eines `Element` oder Informationen zu diesen Komponenten zuzugreifen, stehen die folgenden Methoden zur Verfügung:

1. **`getComponents()`**: Diese Methode gibt eine Java `List` aller Kinder des `Element` zurück. 

2. **`getComponents(String id)`**: Diese Methode ist ähnlich wie die obige Methode, nimmt jedoch die serverseitige ID einer bestimmten Komponente und gibt sie zurück, wenn sie gefunden wird.

3. **`getComponentCount()`**: Gibt die Anzahl der untergeordneten Komponenten im `Element` zurück. 

## Aufrufen von JavaScript-Funktionen {#calling-javascript-functions}

Die `Element`-Komponente bietet zwei API-Methoden, die es ermöglichen, JavaScript-Funktionen auf HTML-Elementen aufzurufen. 

1. **`callJsFunction(String functionName, Object... arguments)`**: Diese Methode nimmt einen Funktionsnamen als String und optional ein oder mehrere Objekte als Parameter für die Funktion. Diese Methode wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert wird**, bis die JS-Methode zurückkehrt, und führt zu einer Hin- und Rückreise. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java gecastet und verwendet werden kann. 

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Wie bei der vorherigen Methode kann ein Funktionsname und optionale Argumente für die Funktion übergeben werden. Diese Methode wird asynchron ausgeführt und **blockiert nicht den ausführenden Thread**. Sie gibt einen <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, der eine weitere Interaktion mit der Funktion und ihrem Payload ermöglicht.

### Parameter übergeben {#passing-parameters}

Argumente, die an diese Methoden übergeben werden, die bei der Ausführung von JS-Funktionen verwendet werden, werden als JSON-Array serialisiert. Es gibt zwei bemerkenswerte Argumenttypen, die wie folgt behandelt werden:
- `this`: Die Verwendung des `this`-Schlüsselworts gibt der Methode eine Referenz auf die clientseitige Version der aufrufenden Komponente.
- `Component`: Alle Java-Komponenteninstanzen, die in eine der JsFunction-Methoden übergeben werden, werden durch die clientseitige Version der Komponente ersetzt.

:::info
Sowohl die synchrone als auch die asynchrone Funktionsaufrufe warten, bis das `Element` zum DOM hinzugefügt wurde, bevor sie eine Funktion ausführen. `callJsFunction()` wird jedoch nicht auf das Anschließen von `component`-Argumenten warten, was zu einem Fehlschlag führen kann. Im Gegensatz dazu könnte das Aufrufen von `callJsFunctionAsync()` möglicherweise nie abgeschlossen werden, wenn ein Komponentenargument niemals angeschlossen wird.
:::

In der Demo unten wird ein Ereignis zu einem HTML `Button` hinzugefügt. Dieses Ereignis wird dann programmgesteuert ausgelöst, indem die Methode `callJsFunctionAsync()` aufgerufen wird. Der resultierende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird dann verwendet, um eine weitere Nachrichtenbox zu erstellen, sobald die asynchrone Funktion abgeschlossen ist.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## Ausführen von JavaScript {#executing-javascript}

Neben der Ausführung von JavaScript auf Anwendungsebene ist es auch möglich, JavaScript auf `Element`-Ebene auszuführen. Diese Ausführung auf der `Element`-Ebene ermöglicht es, den Kontext des HTML-Elements in die Ausführung einzubeziehen. Dies ist ein leistungsstarkes Werkzeug, das als Verbindung des Entwicklers zu interaktiven Funktionen mit clientseitigen Umgebungen fungiert.

Ähnlich wie bei der Funktionsausführung kann das Ausführen von JavaScript synchron oder asynchron mit den folgenden Methoden erfolgen:

1. **`executeJs(String script)`**: Diese Methode nimmt einen `String`, der als JavaScript-Code im Client ausgeführt wird. Dieses Skript wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert wird**, bis die JS-Ausführung zurückkehrt, und führt zu einer Hin- und Rückreise. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java gecastet und verwendet werden kann.

2. **`executeJsAsync(String script)`**: Wie bei der vorherigen Methode wird ein übergebenes `String`-Parameter als JavaScript-Code im Client ausgeführt. Diese Methode wird asynchron ausgeführt und **blockiert nicht den ausführenden Thread**. Sie gibt einen <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, der eine weitere Interaktion mit der Funktion und ihrem Payload ermöglicht.

:::tip
Diese Methoden haben Zugriff auf das `component`-Schlüsselwort, das dem JavaScript-Code Zugriff auf die clientseitige Instanz der Komponente gibt, die das JavaScript ausführt.
:::
