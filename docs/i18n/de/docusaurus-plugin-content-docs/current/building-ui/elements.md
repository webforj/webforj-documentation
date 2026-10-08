---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: dff3b1c4df821aad3c4c7a4c66cfff65
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Die Entwickler von webforJ haben die Möglichkeit, nicht nur aus der umfangreichen Bibliothek von Komponenten zu wählen, sondern auch Komponenten von anderen Quellen zu integrieren. Um dies zu erleichtern, kann die Komponente `Element` verwendet werden, um die Integration von einfachen HTML-Elementen bis hin zu komplexeren benutzerdefinierten Webkomponenten zu vereinfachen.

:::important
Die Komponente `Element` kann nicht erweitert werden und ist nicht die Basiskomponente für alle Komponenten innerhalb von webforJ. Um mehr über die Komponentenhierarchie von webforJ zu erfahren, lesen Sie [diesen Artikel](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementMeterView.java'
]}
height='250px'
/>

## Ereignisse hinzufügen {#adding-events}

Um Ereignisse zu nutzen, die möglicherweise mit Ihrem Element verbunden sind, können Sie die Methoden `addEventListener` der Komponente `Element` verwenden. Das Hinzufügen eines Ereignisses erfordert mindestens den Typ/Namen des Ereignisses, das die Komponente erwartet, und einen Listener, der dem Ereignis hinzugefügt werden soll.

Es gibt auch zusätzliche Optionen, um Ereignisse weiter anzupassen, indem die Event Options-Konfigurationen verwendet werden.

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementTagInputView.java',
  'src/main/frontend/css/element/elementtaginput.css',
]}
height='250px'
/>

## Interaktion zwischen Komponenten {#component-interaction}

Die Komponente `Element` agiert wie ein Container für andere Komponenten. Sie bietet eine Möglichkeit, Informationen für Kindkomponenten zu organisieren und abzurufen, und bietet eine klare Reihe von Funktionen, um diese Kindkomponenten nach Bedarf hinzuzufügen oder zu entfernen.

### Hinzufügen von Kindkomponenten {#adding-child-components}

Die Komponente `Element` unterstützt die Zusammensetzung von Kindkomponenten. Entwickler können komplexe UI-Strukturen organisieren und verwalten, indem sie Komponenten als Kinder zu `Element` hinzufügen. Es gibt drei Methoden, um Inhalte innerhalb eines `Element` festzulegen:

1. **`add(Component... components)`**: Diese Methode ermöglicht es, ein oder mehrere Komponenten zu einem optionalen `String` hinzuzufügen, der einen bestimmten Slot bezeichnet, wenn er mit einer Webkomponente verwendet wird. Das Weglassen des Slots fügt die Komponente zwischen den HTML-Tags hinzu.

2. **`setHtml(String html)`**: Diese Methode nimmt den an die Methode übergebenen `String` und injiziert ihn als HTML innerhalb der Komponente. Je nach `Element` kann dies auf unterschiedliche Weise gerendert werden.

3. **`setText(String text)`**: Diese Methode verhält sich ähnlich wie die Methode `setHtml()`, injiziert jedoch literalen Text in das `Element`.

<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementFigureView.java',
  'src/main/frontend/css/element/elementfigure.css',
]}
height='250px'
/>

:::warning Inhalt ersetzen
Der Aufruf von `setHtml()` oder `setText()` ersetzt den Inhalt, der sich derzeit zwischen den öffnenden und schließenden Tags des Elements befindet.
:::

### Entfernen von Komponenten {#removing-components}

Zusätzlich zum Hinzufügen von Komponenten zu einem `Element` sind die folgenden Methoden zur Entfernung verschiedener Kindkomponenten implementiert:

1. **`remove(Component... components)`**: Diese Methode nimmt ein oder mehrere Komponenten und entfernt sie als Kindkomponenten.

2. **`removeAll()`**: Diese Methode entfernt alle Kindkomponenten aus dem `Element`.

### Zugriff auf Komponenten {#accessing-components}

Um auf die verschiedenen Kindkomponenten zuzugreifen, die in einem `Element` vorhanden sind, oder Informationen über diese Komponenten zu erhalten, stehen die folgenden Methoden zur Verfügung:

1. **`getComponents()`**: Diese Methode gibt eine Java-`List` aller Kinder des `Element` zurück.

2. **`getComponents(String id)`**: Diese Methode ist ähnlich der oben genannten Methode, nimmt jedoch die serverseitige ID einer spezifischen Komponente und gibt sie zurück, wenn sie gefunden wird.

3. **`getComponentCount()`**: Gibt die Anzahl der im `Element` vorhandenen Kindkomponenten zurück.

## JavaScript-Funktionen aufrufen {#calling-javascript-functions}

Die Komponente `Element` bietet zwei API-Methoden, die es ermöglichen, JavaScript-Funktionen auf HTML-Elementen aufzurufen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Diese Methode nimmt einen Funktionsnamen als String und optional ein oder mehrere Objekte als Parameter für die Funktion. Diese Methode wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert wird**, bis die JS-Methode zurückgibt, was zu einer Hin- und Rückreise führt. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java gecastet und verwendet werden kann.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Ähnlich wie bei der vorherigen Methode kann ein Funktionsname und optional Argumente für die Funktion übergeben werden. Diese Methode wird asynchron ausgeführt und **blockiert den ausführenden Thread nicht**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, was eine weitere Interaktion mit der Funktion und deren Payload ermöglicht.

### Parameter übergeben {#passing-parameters}

Argumente, die an diese Methoden übergeben werden und zur Ausführung von JS-Funktionen verwendet werden, werden als JSON-Array serialisiert. Es gibt zwei bemerkenswerte Argumenttypen, die wie folgt behandelt werden:
- `this`: Die Verwendung des Schlüsselworts `this` gibt der Methode eine Referenz auf die clientseitige Version der aufrufenden Komponente.
- `Component`: Alle Java-Komponenteninstanzen, die in eine der JsFunction-Methoden übergeben werden, werden durch die clientseitige Version der Komponente ersetzt.

:::warning Warten auf Komponentenargumente
Sowohl der synchronen als auch der asynchronen Funktionsaufruf wartet, bis das `Element` im DOM hinzugefügt wurde, bevor eine Funktion ausgeführt wird. Der Aufruf von `callJsFunction()` wartet jedoch nicht auf das Anhängen von `component`-Argumenten, was zu einem Fehler führen kann. Umgekehrt könnte der Aufruf von `callJsFunctionAsync()` nie abgeschlossen werden, wenn ein Komponentenargument nie angehängt wird.
:::

Im nachfolgenden Demo wird die Auswahl von **Fokus suchen** die native Methode `focus()` für das Suchfeld mit `callJsFunctionAsync()` aufrufen. Das resultierende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird zur Bestätigung des Aufrufs mit einem Toast verwendet, sobald die asynchrone Funktion abgeschlossen ist.

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementSearchView.java',
  'src/main/frontend/css/element/elementsearch.css',
]}
height='250px'
/>

## JavaScript ausführen {#executing-javascript}

Über den Aufruf benannter Funktionen hinaus kann ein `Element` rohe Skripte, die auf dieses Element beschränkt sind, mit `executeJs`, `executeJsAsync` und `executeJsVoidAsync` ausführen. Siehe [JavaScript ausführen](./execute-javascript.md) für diese Methoden, ihr synchrones und asynchrones Verhalten und wie zurückgegebene Werte in Java-Typen umgewandelt werden.
