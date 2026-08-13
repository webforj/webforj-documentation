---
sidebar_position: 5
title: Elements
sidebar_class_name: updated-content
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: 988b2a49584036eee3b0475215a707ae
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Die Entwickler von webforJ haben die Möglichkeit, nicht nur aus der umfangreichen Bibliothek von bereitgestellten Komponenten zu wählen, sondern auch Komponenten aus anderen Quellen zu integrieren. Um dies zu erleichtern, kann die `Element`-Komponente verwendet werden, um die Integration von allem, von einfachen HTML-Elementen bis hin zu komplexeren benutzerdefinierten Webkomponenten, zu vereinfachen.

:::important
Die `Element`-Komponente kann nicht erweitert werden und ist nicht die Basiskomponente für alle Komponenten innerhalb von webforJ. Um mehr über die Komponenten-Hierarchie von webforJ zu erfahren, lesen Sie [diesen Artikel](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementMeterView.java',
  'src/main/resources/static/css/element/elementMeter.css',
]}
height='240px'
/>

## Ereignisse hinzufügen {#adding-events}

Um Ereignisse zu nutzen, die möglicherweise mit Ihrem Element geliefert werden, können Sie die Methoden `addEventListener` der `Element`-Komponente verwenden. Das Hinzufügen eines Ereignisses erfordert mindestens den Typ/Namen des Ereignisses, das die Komponente erwartet, und einen Listener, der dem Ereignis hinzugefügt werden soll.

Es gibt auch zusätzliche Optionen, um Ereignisse weiter anzupassen, indem die Event-Options-Konfigurationen verwendet werden.

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementTagInputView.java',
  'src/main/resources/static/css/element/elementTagInput.css',
]}
height='240px'
/>

## Komponenteninteraktion {#component-interaction}

Die `Element`-Komponente fungiert als Container für andere Komponenten. Sie bietet eine Möglichkeit, Informationen für untergeordnete Komponenten zu organisieren und abzurufen, und bietet einen klaren Satz von Funktionen, um diese untergeordneten Komponenten nach Bedarf hinzuzufügen oder zu entfernen.

### Unterkomponenten hinzufügen {#adding-child-components}

Die `Element`-Komponente unterstützt die Zusammenstellung von untergeordneten Komponenten. Entwickler können komplexe UI-Strukturen organisieren und verwalten, indem sie Komponenten als Kinder zur `Element`-Komponente hinzufügen. Es gibt drei Methoden, um den Inhalt innerhalb eines `Element` festzulegen:

1. **`add(Component... components)`**: Diese Methode ermöglicht das Hinzufügen eines oder mehrerer Komponenten zu einem optionalen `String`, der einen bestimmten Slot angibt, wenn er mit einer Webkomponente verwendet wird. Wird der Slot weggelassen, wird die Komponente zwischen den HTML-Tags hinzugefügt.

2. **`setHtml(String html)`**: Diese Methode nimmt den `String`, der an die Methode übergeben wird, und injiziert ihn als HTML innerhalb der Komponente. Je nach `Element` kann dies auf unterschiedliche Weise gerendert werden.

3. **`setText(String text)`**: Diese Methode verhält sich ähnlich wie die Methode `setHtml()`, injiziert jedoch literalen Text in das `Element`.

<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementFigureView.java',
  'src/main/resources/static/css/element/elementFigure.css',
]}
height='240px'
/>

:::warning Inhalt ersetzen
Das Aufrufen von `setHtml()` oder `setText()` ersetzt den Inhalt, der derzeit zwischen den Öffnungs- und Schlusstags des Elements enthalten ist.
:::

### Komponenten entfernen {#removing-components}

Neben dem Hinzufügen von Komponenten zu einem `Element` sind die folgenden Methoden zur Entfernung verschiedener untergeordneter Komponenten implementiert:

1. **`remove(Component... components)`**: Diese Methode nimmt ein oder mehrere Komponenten und entfernt sie als untergeordnete Komponenten.

2. **`removeAll()`**: Diese Methode entfernt alle untergeordneten Komponenten aus dem `Element`.

### Komponenten zugreifen {#accessing-components}

Um auf die verschiedenen untergeordneten Komponenten innerhalb eines `Element` oder Informationen über diese Komponenten zuzugreifen, stehen die folgenden Methoden zur Verfügung:

1. **`getComponents()`**: Diese Methode gibt eine Java `List` aller Kinder des `Element` zurück.

2. **`getComponents(String id)`**: Diese Methode ähnelt der obigen Methode, nimmt jedoch die serverseitige ID einer bestimmten Komponente und gibt sie zurück, wenn sie gefunden wird.

3. **`getComponentCount()`**: Gibt die Anzahl der untergeordneten Komponenten innerhalb des `Element` zurück.

## JavaScript-Funktionen aufrufen {#calling-javascript-functions}

Die `Element`-Komponente bietet zwei API-Methoden, mit denen JavaScript-Funktionen auf HTML-Elementen aufgerufen werden können.

1. **`callJsFunction(String functionName, Object... arguments)`**: Diese Methode nimmt einen Funktionsnamen als String und optional ein oder mehrere Objekte als Parameter für die Funktion. Diese Methode wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert** wird, bis die JS-Methode zurückgibt, was zu einer Round-Trip führt. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java umgewandelt und verwendet werden kann.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Wie bei der vorherigen Methode kann ein Funktionsname und optionale Argumente für die Funktion übergeben werden. Diese Methode wird asynchron ausgeführt und **blockiert nicht den ausführenden Thread**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das eine weitere Interaktion mit der Funktion und ihrem Payload ermöglicht.

### Parameter übergeben {#passing-parameters}

Argumente, die an diese Methoden übergeben werden, die bei der Ausführung von JS-Funktionen verwendet werden, werden als JSON-Array serialisiert. Es gibt zwei bemerkenswerte Argumenttypen, die wie folgt behandelt werden:
- `this`: Die Verwendung des Schlüssels `this` gibt der Methode eine Referenz auf die clientseitige Version der aufrufenden Komponente.
- `Component`: Alle Java-Komponenteninstanzen, die in eine der JsFunction-Methoden übergeben werden, werden durch die clientseitige Version der Komponente ersetzt.

:::warning Warten auf Komponentenargumente
Sowohl das synchrone als auch das asynchrone Aufrufen von Funktionen warten, bis das `Element` zum DOM hinzugefügt wurde, bevor eine Funktion ausgeführt wird, aber `callJsFunction()` wartet nicht darauf, dass Komponentenargumente angehängt werden, was zu einem Fehler führen kann. Im Gegensatz dazu könnte das Aufrufen von `callJsFunctionAsync()` nie abgeschlossen werden, wenn ein Komponentenargument nie angehängt wird.
:::

Im folgenden Demo ruft das Auswählen von **Suchfokus** die native `focus()`-Methode auf dem Suchfeld mit `callJsFunctionAsync()` auf. Das resultierende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird verwendet, um den Aufruf mit einem Toast zu bestätigen, sobald die asynchrone Funktion abgeschlossen ist.

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementSearchView.java',
  'src/main/resources/static/css/element/elementSearch.css',
]}
height='240px'
/>

## JavaScript ausführen {#executing-javascript}

Über das Aufrufen benannter Funktionen hinaus kann ein `Element` rohe Skripte ausführen, die auf dieses Element beschränkt sind, mit `executeJs`, `executeJsAsync` und `executeJsVoidAsync`. Siehe [JavaScript ausführen](./execute-javascript.md) für diese Methoden, ihr synchrones und asynchrones Verhalten und wie zurückgegebene Werte in Java-Typen umgewandelt werden.
