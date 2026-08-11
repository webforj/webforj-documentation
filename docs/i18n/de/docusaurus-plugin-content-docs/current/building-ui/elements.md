---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: 2af99ca4f1e5c8c2f7c31b3d7f647b41
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

webforJ-Entwickler haben die Möglichkeit, nicht nur aus der umfangreichen Bibliothek von bereitgestellten Komponenten zu wählen, sondern auch Komponenten von anderen Quellen zu integrieren. Um dies zu erleichtern, kann die `Element`-Komponente verwendet werden, um die Integration von einfachen HTML-Elementen bis hin zu komplexeren benutzerdefinierten Webkomponenten zu vereinfachen.

:::important
Die `Element`-Komponente kann nicht erweitert werden und ist nicht die Basiskomponente für alle Komponenten innerhalb von webforJ. Um mehr über die Komponentenhierarchie von webforJ zu erfahren, lesen Sie [diesen Artikel](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
/>

## Hinzufügen von Ereignissen {#adding-events}

Um Ereignisse zu nutzen, die mit Ihrem Element kommen können, können Sie die `addEventListener`-Methoden der `Element`-Komponente verwenden. Das Hinzufügen eines Ereignisses erfordert mindestens den Typ/Namen des Ereignisses, das die Komponente erwartet, und einen Listener, der dem Ereignis hinzugefügt wird.

Es gibt auch zusätzliche Optionen, um Ereignisse weiter anzupassen, indem die Event Options-Konfigurationen verwendet werden.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/frontend/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Interaktion zwischen Komponenten {#component-interaction}

Die `Element`-Komponente fungiert als Container für andere Komponenten. Sie bietet eine Möglichkeit, Informationen für untergeordnete Komponenten zu organisieren und abzurufen, und bietet einen klaren Satz von Funktionen, um diese untergeordneten Komponenten nach Bedarf hinzuzufügen oder zu entfernen.

### Hinzufügen von untergeordneten Komponenten {#adding-child-components}

Die `Element`-Komponente unterstützt die Komposition untergeordneter Komponenten. Entwickler können komplexe UI-Strukturen organisieren und verwalten, indem sie Komponenten als Kinder zur `Element` hinzufügen. Es gibt drei Methoden, um Inhalte innerhalb eines `Element` festzulegen:

1. **`add(Component... components)`**: Diese Methode ermöglicht es, eine oder mehrere Komponenten zu einem optionalen `String` hinzuzufügen, der einen bestimmten Slot bezeichnet, wenn er mit einer Webkomponente verwendet wird. Das Weglassen des Slots fügt die Komponente zwischen den HTML-Tags hinzu.

2. **`setText(String text)`**: Diese Methode verhält sich ähnlich wie die Methode `setHtml()`, injiziert jedoch normalen Text in das `Element`.

  ```java
  // Wird als die wörtlichen Zeichen "<b>Status: ready</b>" angezeigt
  element.setText("<b>Status: ready</b>");
  ```

  :::note Verwendung des `<html>`-Tags
  Frühere Versionen von webforJ behandelten einen Wert, der in `<html>` eingeschlossen und an `setText()` übergeben wurde, als HTML. Dieses Verhalten ist veraltet und wird in webforJ 27.00 entfernt.

  Beim ersten Erreichen eines mit `<html>` umschlossenen Wertes bei `setText()` wird eine Warnung protokolliert, die die Komponente und den Aufrufstandort nennt, damit der Aufruf zu `setHtml()` verschoben werden kann.

  Um die Standardverhalten von webforJ 27.00 vorzeitig zu übernehmen, setzen Sie `webforj.legacyHtmlInText` auf `false`. In einer Spring-App wird derselbe Wert über `webforj.legacy-html-in-text` gesetzt.

  ```java
  // webforj.legacyHtmlInText = true (Standard)
  element.setText("<html><b>Status: ready</b></html>"); // rendered bold

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Status: ready</b></html>"); // zeigt die Zeichen <b>Status: ready</b>
  ```
  :::

3. **`setHtml(String html)`**: Diese Methode nimmt den an die Methode übergebenen `String` und injiziert ihn als HTML innerhalb der Komponente. Abhängig vom `Element` kann dies auf unterschiedliche Weise gerendert werden.

  :::danger Cross-Site-Scripting (XSS)
  Als Vorsichtsmaßnahme gegen [Cross-Site-Scripting (XSS)-Angriffe](/docs/security/application-security/common-threats#cross-site-scripting-xss) verwenden Sie `setHtml()` nur mit Inhalten, die Sie direkt kontrollieren.
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Das Aufrufen von `setHtml()` oder `setText()` ersetzt den Inhalt, der derzeit zwischen den Öffnungs- und Schließtags des Elements enthalten ist.
:::

### Entfernen von Komponenten {#removing-components}

Neben dem Hinzufügen von Komponenten zu einem `Element` sind die folgenden Methoden implementiert, um verschiedene untergeordnete Komponenten zu entfernen:

1. **`remove(Component... components)`**: Diese Methode nimmt eine oder mehrere Komponenten und entfernt sie als untergeordnete Komponenten.

2. **`removeAll()`**: Diese Methode entfernt alle untergeordneten Komponenten aus dem `Element`.

### Zugriff auf Komponenten {#accessing-components}

Um auf die verschiedenen untergeordneten Komponenten innerhalb eines `Element` zuzugreifen oder Informationen über diese Komponenten abzurufen, stehen die folgenden Methoden zur Verfügung:

1. **`getComponents()`**: Diese Methode gibt eine Java `List` aller Kinder des `Element` zurück.

2. **`getComponents(String id)`**: Diese Methode ähnelt der vorherigen Methode, nimmt jedoch die serverseitige ID einer bestimmten Komponente und gibt diese zurück, wenn sie gefunden wird.

3. **`getComponentCount()`**: Gibt die Anzahl der untergeordneten Komponenten innerhalb des `Element` zurück.


## Aufrufen von JavaScript-Funktionen {#calling-javascript-functions}

Die `Element`-Komponente bietet zwei API-Methoden, die es ermöglichen, JavaScript-Funktionen auf HTML-Elementen aufzurufen.

1. **`callJsFunction(String functionName, Object... arguments)`**: Diese Methode nimmt einen Funktionsnamen als String und optional ein oder mehrere Objekte als Parameter für die Funktion. Diese Methode wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert** ist, bis die JS-Methode zurückkehrt, und ergibt einen Round-Trip. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java umgewandelt und verwendet werden kann.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Wie bei der vorherigen Methode kann ein Funktionsname und optionale Argumente für die Funktion übergeben werden. Diese Methode wird asynchron ausgeführt und **blockiert den ausführenden Thread nicht**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das eine weitere Interaktion mit der Funktion und deren Nutzdaten ermöglicht.

### Parameter übergeben {#passing-parameters}

Argumente, die an diese Methoden übergeben werden und die bei der Ausführung von JS-Funktionen verwendet werden, werden als JSON-Array serialisiert. Es gibt zwei bemerkenswerte Argumenttypen, die wie folgt behandelt werden:
- `this`: Die Verwendung des `this`-Schlüsselworts gibt der Methode eine Referenz auf die clientseitige Version der aufrufenden Komponente.
- `Component`: Alle Java-Komponenteninstanzen, die an eine der JsFunction-Methoden übergeben werden, werden durch die clientseitige Version der Komponente ersetzt.

:::info
Sowohl synchrones als auch asynchrones Funktionsaufrufen warten, bis das `Element` zum DOM hinzugefügt wurde, bevor eine Funktion ausgeführt wird, aber `callJsFunction()` wartet nicht auf das Anhängen von `component`-Argumenten, was zu einem Fehler führen kann. Im Gegensatz dazu könnte der Aufruf von `callJsFunctionAsync()` möglicherweise nie abgeschlossen werden, wenn ein Komponentenargument nie angefügt wird.
:::

Im folgenden Demo wird ein Ereignis zu einem HTML `Button` hinzugefügt. Dieses Ereignis wird dann programmgesteuert durch den Aufruf der Methode `callJsFunctionAsync()` ausgelöst. Das resultierende <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> wird dann verwendet, um ein weiteres Nachrichtenfeld zu erstellen, sobald die asynchrone Funktion abgeschlossen ist.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='240px'
/>

## Ausführen von JavaScript {#executing-javascript}

Neben der Ausführung von JavaScript auf Anwendungsebene ist es auch möglich, JavaScript auf der `Element`-Ebene auszuführen. Die Ausführung auf der `Element`-Ebene ermöglicht es, den Kontext des HTML-Elements in die Ausführung einzubeziehen. Dies ist ein leistungsstarkes Werkzeug, das als Verbindung zwischen Entwicklern und interaktiven Fähigkeiten in clientseitigen Umgebungen fungiert.

Ähnlich wie bei der Funktionsausführung kann die Ausführung von JavaScript synchron oder asynchron mit den folgenden Methoden erfolgen:

1. **`executeJs(String script)`**: Diese Methode nimmt einen `String`, der als JavaScript-Code im Client ausgeführt wird. Dieses Skript wird synchron ausgeführt, was bedeutet, dass der **ausführende Thread blockiert** ist, bis die JS-Ausführung zurückkehrt, und ergibt einen Round-Trip. Die Ergebnisse der Funktion werden als `Object` zurückgegeben, das in Java umgewandelt und verwendet werden kann.

2. **`executeJsAsync(String script)`**: Wie bei der vorherigen Methode wird ein übergebener `String`-Parameter als JavaScript-Code auf dem Client ausgeführt. Diese Methode wird asynchron ausgeführt und **blockiert den ausführenden Thread nicht**. Sie gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das eine weitere Interaktion mit der Funktion und deren Nutzdaten ermöglicht.

:::tip
Diese Methoden haben Zugriff auf das `component`-Schlüsselwort, das dem JavaScript-Code den Zugriff auf die clientseitige Instanz der Komponente, die das JavaScript ausführt, ermöglicht.
:::
