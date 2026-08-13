---
sidebar_position: 11
title: Execute JavaScript
sidebar_class_name: new-content
description: >-
  Run client-side JavaScript from Java with executeJs, executeJsAsync, and
  executeJsVoidAsync at the app or element level.
slug: execute-javascript
_i18n_hash: c1d5b030c6f39ac6c83afc05ca4bb398
---
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

webforJ läuft auf dem Server, aber es gibt Zeiten, in denen Sie den Client erreichen müssen: das Fenster scrollen, ein Feld fokussieren, einen Wert des Browsers lesen oder eine Methode auf einer Web-Komponente aufrufen. Die <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> Schnittstelle bietet diese Brücke. Sie wird auf zwei Ebenen implementiert:

- Die [`Page`](#app-level-execution) führt Skripte im Kontext der gesamten Seite aus.
- Ein [`Element`](#element-level-execution) führt Skripte, die auf ein einzelnes Client-Element beschränkt sind, aus.

Beide stellen die gleichen drei Methoden bereit, sodass sie, wenn Sie die Formen unten kennen, unabhängig davon, ob Sie sie auf `Page` oder einem `Element` aufrufen, gleich gelesen werden.

## Ausführungsmethoden {#execution-methods}

Jede Ebene bietet eine synchrone Methode und zwei asynchrone Methoden. Der Unterschied liegt darin, ob der aufrufende Thread wartet und ob ein Ergebnis zurückkommt.

1. **`executeJs(String script)`**: führt das Skript synchron aus. Der **ausführende Thread wird blockiert**, bis der Client zurückkehrt, was eine Server-zu-Client-Rundreise kostet. Das Ergebnis kommt als `Object` zurück, das Sie in Java casten und verwenden können.

2. **`executeJsAsync(String script)`**: führt das Skript asynchron aus und **blockiert nicht den ausführenden Thread**. Es gibt ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das abgeschlossen wird, wenn das Skript beendet ist, sodass Sie später auf das Ergebnis reagieren können.

3. **`executeJsVoidAsync(String script)`**: führt das Skript asynchron aus und gibt nichts an den Server zurück. Verwenden Sie es für Fire-and-Forget-Arbeiten, bei denen Sie das Ergebnis nicht benötigen. Verfügbar seit `24.11`.

:::tip Auswahl einer Methode
Greifen Sie standardmäßig auf `executeJsVoidAsync` zu, wenn Sie nur einen Nebeneffekt auf dem Client verursachen (Scrollen, Fokussieren, eine Methode aufrufen). Verwenden Sie `executeJsAsync`, wenn Sie den Wert benötigen, aber nicht blockierend bleiben möchten, und reservieren Sie die synchrone `executeJs` für den seltenen Fall, dass Sie das Ergebnis vor der nächsten Java-Zeile benötigen, da es den Thread für eine volle Rundreise hält.
:::

### Ergebnisse lesen {#reading-results}

Wenn ein Skript einen Wert zurückgibt, konvertiert webforJ ihn in den entsprechenden Java-Typ:

| JavaScript-Wert        | Java-Typ                            |
| ----------------------- | ------------------------------------ |
| number                  | `Integer`, `Long` oder `Double`     |
| string                  | `String`                             |
| boolean                 | `Boolean`                            |
| `null` oder `undefined` | `null`                               |
| jeder andere Typ        | seine Zeichenfolgen-Darstellung      |

Lesen Sie Werte mit `executeJsAsync`, das die Konvertierung zuverlässig anwendet. Eine zurückgegebene Zahl kann als `Integer`, `Long` oder `Double` ankommen, lesen Sie sie daher über `Number`:

```java
Page.getCurrent()
    .executeJsAsync("return window.innerWidth;")
    .thenAccept(result -> {
      int width = ((Number) result).intValue();
      // verwenden Sie width
    });
```

:::warning Bevorzugen Sie die asynchrone Form, wenn Sie den Wert benötigen
Die synchrone `executeJs` gibt `null` zurück, wenn der Ausführungskontext nicht bereit ist, beispielsweise wenn sie aufgerufen wird, bevor die Komponente angeknüpft ist. Verwenden Sie `executeJsAsync`, wann immer Sie vom zurückgegebenen Wert abhängen, und vermeiden Sie es, ein synchrones Ergebnis an einen bestimmten Typ zu casten.
:::

## Ausführung auf Anwendungsebene {#app-level-execution}

Rufen Sie die Methoden auf <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> auf, wenn das Skript die gesamte Seite und nicht eine Komponente betrifft. Holen Sie sich die aktuelle Seite mit `Page.getCurrent()`.

Ein häufiger Fall ist das Zurückscrollen nach einem Routenwechsel. Nichts muss zurückkommen, also passt `executeJsVoidAsync`:

```java
Page.getCurrent().executeJsVoidAsync(
    "window.scrollTo({ top: 0, behavior: 'smooth' });");
```

Wenn Sie einen Client-Wert auf dem Server benötigen, lesen Sie ihn asynchron und handeln Sie, wenn das Ergebnis eintrifft:

```java
Page.getCurrent()
    .executeJsAsync("return navigator.language;")
    .thenAccept(language -> {
      // language ist die Browsersprache, zum Beispiel "en-US"
      applyLocale(String.valueOf(language));
    });
```

:::info Seite versus Elementbereich
Verwenden Sie [ausführung auf Elementebene](#element-level-execution), wenn das Skript auf ein bestimmtes Client-Element und nicht auf die gesamte Seite wirken soll.
:::

Im folgenden Demo führt die Auswahl von **Link kopieren** ein Skript über `Page` mit `executeJsVoidAsync` aus, um den Einladungslink in die Zwischenablage des Besuchers zu schreiben. Das Kopieren ist ein Nebeneffekt, der nichts zurückgeben muss, sodass die Fire-and-Forget-Methode gut geeignet ist.

<ComponentDemo
path='/webforj/executejavascript'
files={[
  'src/main/java/com/webforj/samples/views/javascript/ExecuteJavaScriptView.java',
]}
height='260px'
/>

## Ausführung auf Elementebene {#element-level-execution}

Wenn Sie die gleichen Methoden auf einem <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink> aufrufen, wird das Skript auf dieses Element anstelle der Seite beschränkt. Die Rückgabewerte sowie das synchrone und asynchrone Verhalten sind identisch zu den vorhergehenden Methoden auf Seitenebene.

Element-Skripte werden in die Warteschlange gestellt, bis das Element dem DOM angefügt ist, und laufen dann, sodass Sie sie während des Setups aufrufen können, ohne auf die Anheftung zu warten.

### Aufruf einer Funktion auf einem Element {#calling-a-function}

Wenn Sie eine benannte Client-seitige Funktion aufrufen möchten, anstatt einen Skript-String auszuführen, bietet das `Element` eine parallele Menge von Methoden. Anstelle eines Skripts übergeben Sie den Funktionsnamen und seine Argumente, die webforJ serialisiert und übergibt. Zwei Argumenttypen werden speziell behandelt: `this` wird durch das Client-Element ersetzt, und jedes `Component`-Argument wird durch seine Client-Instanz ersetzt, sobald es angeknüpft ist.

Diese spiegeln die Ausführungsmethoden wider, unterscheiden sich nur darin, ob der Thread wartet und ob ein Ergebnis zurückgegeben wird:

1. **`callJsFunction(String name, Object... args)`**: ruft die Funktion synchron auf und gibt ihr Ergebnis als `Object` zurück. Der ausführende Thread blockiert für eine Rundreise.

2. **`callJsFunctionAsync(String name, Object... args)`**: ruft die Funktion asynchron ohne Blockierung auf und gibt ein `PendingResult` zurück, das mit dem Ergebnis der Funktion abgeschlossen wird. Verfügbar seit `24.11`.

3. **`callJsFunctionVoidAsync(String name, Object... args)`**: ruft die Funktion asynchron auf und gibt nichts an den Server zurück. Verwenden Sie es für Fire-and-Forget-Aufrufe, bei denen Sie den Rückgabewert nicht benötigen. Verfügbar seit `24.11`.

Da der Aufruf wartet, bis jedes `Component`-Argument angeheftet ist, bevor er ausgeführt wird, wird ein Aufruf, der ein Element übergibt, das niemals angeheftet wird, nie abgeschlossen.

```java
// Fokussieren Sie die Eingabe einer Web-komponente, indem Sie ihre Client-seitige Methode aufrufen
searchElement.callJsFunctionVoidAsync("focus");
```
