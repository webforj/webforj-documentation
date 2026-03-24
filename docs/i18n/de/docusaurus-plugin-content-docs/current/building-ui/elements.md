---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 2ea3ba8ae8756dcea1ee5d0eb9fb0cf9
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-Entwickler haben die Möglichkeit, nicht nur aus der umfangreichen Bibliothek von bereitgestellten Komponenten zu wählen, sondern auch Komponenten von anderen Quellen zu integrieren. Um dies zu erleichtern, kann die `Element`-Komponente verwendet werden, um die Integration von einfachen HTML-Elementen bis hin zu komplexeren benutzerdefinierten Webkomponenten zu vereinfachen.

:::important
Die `Element`-Komponente kann nicht erweitert werden und ist nicht die Basiskomponente für alle Komponenten innerhalb von webforJ. Um mehr über die Komponenten-Hierarchie von webforJ zu erfahren, lesen Sie [diesen Artikel](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Hinzufügen von Ereignissen {#adding-events}

Um Ereignisse zu nutzen, die möglicherweise mit Ihrem Element geliefert werden, können Sie die Methoden `addEventListener` der `Element`-Komponente verwenden. Das Hinzufügen eines Ereignisses erfordert mindestens den Typ/Namen des Ereignisses, das die Komponente erwartet, und einen Listener, der dem Ereignis hinzugefügt werden soll.

Es gibt auch zusätzliche Optionen, um Ereignisse weiter anzupassen, indem die Konfigurationen der Ereignisoptionen verwendet werden.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Interaktion von Komponenten {#component-interaction}

Die `Element`-Komponente fungiert als Container für andere Komponenten. Sie bietet eine Möglichkeit, Informationen für untergeordnete Komponenten zu organisieren und abzurufen, und bietet eine klare Reihe von Funktionen zum Hinzufügen oder Entfernen dieser untergeordneten Komponenten nach Bedarf.

### Hinzufügen von untergeordneten Komponenten {#adding-child-components}

Die `Element`-Komponente unterstützt die Komposition von untergeordneten Komponenten. Entwickler können komplexe UI-Strukturen organisieren und verwalten, indem sie Komponenten als Kinder zur `Element` hinzufügen. Es gibt drei Methoden, um Inhalte innerhalb eines `Element` festzulegen:

1. **`add(Component... components)`**: Diese Methode ermöglicht es, ein oder mehrere Komponenten zu einem optionalen `String` hinzuzufügen, der einen bestimmten Slot angibt, wenn er mit einer Web-Komponente verwendet wird. Das Weglassen des Slots fügt die Komponente zwischen den HTML-Tags hinzu.

2. **`setHtml(String html)`**: Diese Methode nimmt den an die Methode übergebenen `String` und injiziert ihn als HTML innerhalb der Komponente. Je nach `Element` kann dies auf unterschiedliche Weise gerendert werden.

3. **`setText(String text)`**: Diese Methode verhält sich ähnlich wie die Methode `setHtml()`, injiziert jedoch reinen Text in das `Element`.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
Das Aufrufen von `setHtml()` oder `setText()` ersetzt den Inhalt, der derzeit zwischen den Öffnungs- und Schlusstags des Elements enthalten ist.
:::

### Entfernen von Komponenten {#removing-components}

Neben dem Hinzufügen von Komponenten zu einem `Element` sind die folgenden Methoden implementiert, um verschiedene untergeordnete Komponenten zu entfernen:

1. **`remove(Component... components)`**: Diese Methode nimmt eine oder mehrere Komponenten und entfernt sie als untergeordnete Komponenten.

2. **`removeAll()`**: Diese Methode entfernt alle untergeordneten Komponenten aus dem `Element`.

### Zugriff auf Komponenten {#accessing-components}

Um auf die verschiedenen untergeordneten Komponenten innerhalb eines `Element` oder Informationen über diese Komponenten zuzugreifen, stehen die folgenden Methoden zur Verfügung:

1. **`getComponents()`**: Diese Methode gibt eine Java `List` aller Kinder des `Element` zurück.

2. **`getComponents(String id)`**: Diese Methode ist ähnlich wie die oben genannte Methode, nimmt jedoch die serverseitige ID einer bestimmten Komponente und gibt sie zurück, wenn sie gefunden wird.

3. **`getComponentCount()`**: Gibt die Anzahl der vorhandenen untergeordneten Komponenten innerhalb des `Element` zurück.

## Aufrufen von JavaScript-Funktionen {#calling-javascript-functions}

Die `Element`-Komponente bietet zwei API-Methoden, die es ermöglichen, JavaScript-Funktionen auf HTML-Elementen aufzurufen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Diese Methode nimmt einen Funktionsnamen als `String` und optional ein oder mehrere Objekte als Parameter für die Funktion. Diese Methode wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert ist**, bis die JS-Methode zurückkehrt, und führt zu einer Rundreise. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java umgewandelt und verwendet werden kann.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Wie bei der vorherigen Methode kann ein Funktionsname und optionale Argumente für die Funktion übergeben werden. Diese Methode wird asynchron ausgeführt und **blockiert nicht den ausführenden Thread**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das eine weitere Interaktion mit der Funktion und ihrer Nutzlast ermöglicht.

### Parameterübergabe {#passing-parameters}

Argumente, die an diese Methoden übergeben werden, die beim Ausführen von JS-Funktionen verwendet werden, werden als JSON-Array serialisiert. Es gibt zwei bemerkenswerte Argumenttypen, die wie folgt behandelt werden:
- `this`: Die Verwendung des `this`-Schlüsselworts gibt der Methode eine Referenz auf die clientseitige Version der aufrufenden Komponente.
- `Component`: Jegliche Java-Komponenteninstanzen, die in eine der JsFunction-Methoden übergeben werden, werden durch die clientseitige Version der Komponente ersetzt.

:::info
Sowohl die synchrone als auch die asynchrone Funktionsaufrufe warten darauf, eine Methode aufzurufen, bis das `Element` dem DOM hinzugefügt wurde, bevor eine Funktion ausgeführt wird, aber `callJsFunction()` wartet nicht darauf, dass Komponentenargumente angehängt werden, was zu Fehlern führen kann. Im Gegensatz dazu kann das Aufrufen von `callJsFunctionAsync()` niemals abgeschlossen werden, wenn ein Komponentenargument niemals angehängt wird.
:::

Im folgenden Demo wird ein Ereignis zu einem HTML `Button` hinzugefügt. Dieses Ereignis wird dann programmatisch durch den Aufruf der Methode `callJsFunctionAsync()` ausgelöst. Das resultierende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird dann verwendet, um eine weitere Nachricht anzuzeigen, sobald die asynchrone Funktion abgeschlossen ist.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## Ausführen von JavaScript {#executing-javascript}

Neben der Ausführung von JavaScript auf Anwendungsebene ist es auch möglich, JavaScript auf Elementebene auszuführen. Diese Ausführung auf Elementebene ermöglicht es, den Kontext des HTML-Elements in die Ausführung einzubeziehen. Dies ist ein leistungsfähiges Werkzeug, das als Verbindung des Entwicklers zu interaktiven Fähigkeiten mit clientseitigen Umgebungen fungiert.

Ähnlich wie bei der Funktionsausführung kann die Ausführung von JavaScript synchron oder asynchron mit den folgenden Methoden erfolgen:

1. **`executeJs(String script)`**: Diese Methode nimmt einen `String`, der als JavaScript-Code auf dem Client ausgeführt wird. Dieses Skript wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert ist**, bis die JS-Ausführung zurückkehrt und zu einer Rundreise führt. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java umgewandelt und verwendet werden kann.

2. **`executeJsAsync(String script)`**: Wie bei der vorherigen Methode wird ein übergebener `String`-Parameter als JavaScript-Code auf dem Client ausgeführt. Diese Methode wird asynchron ausgeführt und **blockiert nicht den ausführenden Thread**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das eine weitere Interaktion mit der Funktion und ihrer Nutzlast ermöglicht.

:::tip
Diese Methoden haben Zugriff auf das Schlüsselwort `component`, das dem JavaScript-Code Zugriff auf die clientseitige Instanz der Komponente gibt, die das JavaScript ausführt.
:::
