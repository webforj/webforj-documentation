---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: d941e314cdd63d19471e80936ef5d6bc
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

WebforJ-Entwickler haben die Möglichkeit, nicht nur aus der umfangreichen Bibliothek von bereitgestellten Komponenten zu wählen, sondern auch Komponenten aus anderen Quellen zu integrieren. Um dies zu erleichtern, kann die `Element`-Komponente verwendet werden, um die Integration von allem, von einfachen HTML-Elementen bis hin zu komplexeren benutzerdefinierten Webkomponenten, zu vereinfachen.

:::important
Die `Element`-Komponente kann nicht erweitert werden und ist nicht die Basiskomponente für alle Komponenten innerhalb von webforJ. Um mehr über die Komponentenhierarchie von webforJ zu erfahren, lesen Sie [diesen Artikel](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## Hinzufügen von Ereignissen {#adding-events}

Um Ereignisse, die mit Ihrem Element verbunden sein können, zu nutzen, können Sie die Methoden `addEventListener` der `Element`-Komponente verwenden. Das Hinzufügen eines Ereignisses erfordert mindestens den Typ/Namen des Ereignisses, das die Komponente erwartet, und einen Listener, der zum Ereignis hinzugefügt werden soll.

Es gibt auch zusätzliche Optionen, um Ereignisse weiter anzupassen, indem Sie die Konfigurationen für Ereignisoptionen nutzen.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Komponenteninteraktion {#component-interaction}

Die `Element`-Komponente fungiert als Container für andere Komponenten. Sie bietet eine Möglichkeit, Informationen für Kindkomponenten zu organisieren und abzurufen, und bietet eine klare Reihe von Funktionen, um diese Kindkomponenten nach Bedarf hinzuzufügen oder zu entfernen.

### Hinzufügen von Kindkomponenten {#adding-child-components}

Die `Element`-Komponente unterstützt die Zusammensetzung von Kindkomponenten. Entwickler können komplexe UI-Strukturen organisieren und verwalten, indem sie Komponenten als Kinder zur `Element`-Komponente hinzufügen. Es stehen drei Methoden zur Verfügung, um Inhalte innerhalb eines `Element` festzulegen:

1. **`add(Component... components)`**: Diese Methode ermöglicht das Hinzufügen von einer oder mehreren Komponenten zu einem optionalen `String`, der einen bestimmten Slot bezeichnet, wenn sie mit einer Webkomponente verwendet wird. Wenn der Slot weggelassen wird, wird die Komponente zwischen den HTML-Tags hinzugefügt.

2. **`setText(String text)`**: Diese Methode verhält sich ähnlich wie die Methode `setHtml()`, injiziert jedoch wörtlichen Text in das `Element`.

  ```java
  // Angezeigt als die wörtlichen Zeichen "<b>Status: bereit</b>"
  element.setText("<b>Status: bereit</b>");
  ```

  :::note Verwendung des `<html>`-Tags
  Frühere Versionen von webforJ behandelten einen in `<html>` eingewickelten Wert, der an `setText()` übergeben wurde, als HTML. Dieses Verhalten ist veraltet und wird in webforJ 27.00 entfernt.

  Beim ersten Mal, dass ein in `<html>` gewickelter Wert `setText()` erreicht, wird eine Warnung protokolliert, die die Komponente und die Aufrufstelle benennt, damit der Aufruf zu `setHtml()` verschoben werden kann.

  Um die Standardwerte von webforJ 27.00 im Voraus zu übernehmen, setzen Sie `webforj.legacyHtmlInText` auf `false`. In einer Spring-Anwendung wird der gleiche Wert über `webforj.legacy-html-in-text` gesetzt.

  ```java
  // webforj.legacyHtmlInText = true (Standard)
  element.setText("<html><b>Status: bereit</b></html>"); // wird fett dargestellt

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Status: bereit</b></html>"); // zeigt die Zeichen <b>Status: bereit</b>
  ```
  :::

3. **`setHtml(String html)`**: Diese Methode nimmt den übergebenen `String` und injiziert ihn als HTML in die Komponente. Abhängig vom `Element` kann dies unterschiedlich gerendert werden.

  :::danger Cross-Site-Scripting (XSS)
  Als Vorsichtsmaßnahme gegen [Cross-Site-Scripting (XSS)-Angriffe](/docs/security/application-security/common-threats#cross-site-scripting-xss) verwenden Sie `setHtml()` nur mit Inhalten, die Sie direkt kontrollieren.
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Der Aufruf von `setHtml()` oder `setText()` ersetzt den Inhalt, der derzeit zwischen den öffnenden und schließenden Tags des Elements enthalten ist.
:::

### Entfernen von Komponenten {#removing-components}

Neben dem Hinzufügen von Komponenten zu einem `Element` sind die folgenden Methoden für das Entfernen verschiedener Kindkomponenten implementiert:

1. **`remove(Component... components)`**: Diese Methode nimmt eine oder mehrere Komponenten und entfernt sie als Kindkomponenten.

2. **`removeAll()`**: Diese Methode entfernt alle Kindkomponenten aus dem `Element`.

### Zugriff auf Komponenten {#accessing-components}

Um auf die verschiedenen Kindkomponenten, die innerhalb eines `Element` vorhanden sind, oder Informationen über diese Komponenten zuzugreifen, stehen die folgenden Methoden zur Verfügung:

1. **`getComponents()`**: Diese Methode gibt eine Java `List` aller Kinder des `Element` zurück.

2. **`getComponents(String id)`**: Diese Methode ähnelt der oben genannten Methode, benötigt jedoch die serverseitige ID einer bestimmten Komponente und gibt diese zurück, wenn sie gefunden wird.

3. **`getComponentCount()`**: Gibt die Anzahl der Kindkomponenten zurück, die im `Element` vorhanden sind.

## Aufruf von JavaScript-Funktionen {#calling-javascript-functions}

Die `Element`-Komponente bietet zwei API-Methoden, mit denen JavaScript-Funktionen auf HTML-Elementen aufgerufen werden können. 

1. **`callJsFunction(String functionName, Object... arguments)`**: Diese Methode nimmt einen Funktionsnamen als Zeichenfolge und optional ein oder mehrere Objekte als Parameter für die Funktion. Diese Methode wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert** wird, bis die JS-Methode zurückkehrt, und führt zu einer Hin- und Rückreise. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java verwendet werden kann.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Wie bei der vorherigen Methode kann ein Funktionsname und optionale Argumente für die Funktion übergeben werden. Diese Methode wird asynchron ausgeführt und **blockiert den ausführenden Thread nicht**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das eine weitere Interaktion mit der Funktion und ihrem Payload ermöglicht.

### Übergabe von Parametern {#passing-parameters}

Argumente, die an diese Methoden übergeben werden und bei der Ausführung von JS-Funktionen verwendet werden, werden als JSON-Array serialisiert. Es gibt zwei bemerkenswerte Argumenttypen, die wie folgt behandelt werden:
- `this`: Die Verwendung des Schlüssels `this` gibt der Methode eine Referenz auf die clientseitige Version der aufrufenden Komponente.
- `Component`: Alle Java-Komponenteninstanzen, die in eine der JsFunction-Methoden übergeben werden, werden durch die clientseitige Version der Komponente ersetzt.

:::info
Sowohl die synchrone als auch die asynchrone Funktionsaufrufe warten, bis das `Element` zum DOM hinzugefügt wurde, bevor eine Funktion ausgeführt wird, aber `callJsFunction()` wartet nicht darauf, dass irgendwelche `component` Argumente angehängt werden, was zu einem Fehler führen kann. Im Gegensatz dazu könnte der Aufruf von `callJsFunctionAsync()` niemals abgeschlossen werden, wenn ein Komponentenargument niemals angehängt wird.
:::

Im folgenden Demo wird ein Ereignis zu einem HTML `Button` hinzugefügt. Dieses Ereignis wird dann programmgemäß durch den Aufruf der Methode `callJsFunctionAsync()` ausgelöst. Das resultierende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird dann verwendet, um eine weitere Nachrichtenbox zu erstellen, sobald die asynchrone Funktion abgeschlossen ist.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## Ausführen von JavaScript {#executing-javascript}

Neben der Ausführung von JavaScript auf Anwendungsebene ist es auch möglich, JavaScript auf Elementebene auszuführen. Die Ausführung auf Elementebene ermöglicht es, den Kontext des HTML-Elements in die Ausführung einzubeziehen. Dies ist ein leistungsfähiges Werkzeug, das als Verbindung zwischen Entwicklern und interaktiven Möglichkeiten in clientseitigen Umgebungen wirkt.

Ähnlich wie bei der Funktionsausführung kann die Ausführung von JavaScript synchron oder asynchron mit den folgenden Methoden erfolgen:

1. **`executeJs(String script)`**: Diese Methode nimmt einen `String`, der als JavaScript-Code im Client ausgeführt wird. Dieses Skript wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert** wird, bis die JS-Ausführung zurückkehrt, und führt zu einer Hin- und Rückreise. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java verwendet werden kann.

2. **`executeJsAsync(String script)`**: Wie bei der vorherigen Methode wird ein übergebener `String`-Parameter als JavaScript-Code im Client ausgeführt. Diese Methode wird asynchron ausgeführt und **blockiert den ausführenden Thread nicht**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das eine weitere Interaktion mit der Funktion und ihrem Payload ermöglicht.

:::tip
Diese Methoden haben Zugriff auf das Schlüsselwort `component`, wodurch der JavaScript-Code Zugriff auf die clientseitige Instanz der Komponente hat, die das JavaScript ausführt.
:::
