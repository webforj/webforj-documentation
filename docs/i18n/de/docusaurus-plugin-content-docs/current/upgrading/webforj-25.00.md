---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 2553d37a63c097b7520f2989849f016b
---
Dieses Dokument dient als Anleitung zum Upgrade von webforJ-Apps von 24.00 auf 25.00. Hier sind die Änderungen, die für bestehende Apps erforderlich sind, um weiterhin reibungslos zu funktionieren. Wie immer finden Sie in der [GitHub-Versionenübersicht](https://github.com/webforj/webforj/releases) eine umfassendere Liste von Änderungen zwischen den Versionen.

## Jetty 12 Webserver {#jetty-12-web-servers}

webforJ 25.00 und höher nutzen Jetty 12 und verwenden die Jakarta EE10 Servlet-Architektur. Wenn Sie das Jetty Maven-Plugin für die Entwicklung verwenden, migrieren Sie von Jakarta EE8 zu Jakarta EE10. Dieses Upgrade erfordert auch den Austausch von allem, was auf das `javax.servlet`-Paket angewiesen war, gegen das `Jakarta.servlet`-Paket.

### Änderungen der POM-Datei {#pom-file-changes}

**Vorher**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee8</groupId>
  <artifactId>jetty-ee8-maven-plugin</artifactId>
  <version>10.x.xx</version>
```
**Nachher**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee10</groupId>
  <artifactId>jetty-ee10-maven-plugin</artifactId>
  <version>12.x.xx</version>
```

## API-Änderungen für die `App`-Klasse {#api-changes-for-the-app-class}

Mehrere veraltete `App`-Methoden wurden in 25.00 entfernt. Die folgenden Abschnitte skizzieren, welche Methoden ersetzt wurden und welche empfohlenen Ersatzmethoden es gibt.

### Konsolenprotokollierung {#console-logging}

Die Hilfsklasse [`BrowserConsole`](../advanced/browser-console.md), die zum Erstellen von stilisierten Protokollen in der Browser-Konsole dient, ersetzt die Methoden `consoleLog()` und `consoleError()`. Holen Sie sich die `BrowserConsole`, indem Sie die Methode `console()` verwenden:

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("Protokollnachricht");
    console().error("Fehlermeldung");
  }
}
```

### Webspeicherung {#web-storage}

Für Versionen vor webforJ 25.00 hat die `App`-Klasse die Methoden `getLocalStorage()`, `getSessionStorage()` und `getCookieStorage()`, um Instanzen der Klassen `LocalStorage`, `SessionStorage` und `CookieStorage` zu erhalten. Zukünftig hat jede Klasse eine Methode `getCurrent()`.

Siehe [Web Storage](../advanced/web-storage.md) für weitere Informationen.

### `Request`-Klasse {#request-class}

Die `Request`-Klasse ist jetzt dafür verantwortlich, die URL, den Port, den Host und das Protokoll einer App zu erhalten. Anstatt `App.getUrl()` zu verwenden, verwenden Sie `App.getCurrent().getUrl()`. Die Methode `getCurrent()` ersetzt auch die Methode `getRequest()`, um eine Instanz der `Request`-Klasse zu erhalten.

:::info
Die `Request`-Klasse hat außerdem entfernte Methoden; springen Sie zu [`Request`](#request-changes), um diese zu sehen.
:::

### `Page`-Klasse {#page-class}

Die Methode `getPage()` wird durch `Page.getCurrent()` ersetzt, um die aktuelle Seiteninstanz zu erhalten.

### Optionsdialoge {#option-dialogs}

Anstelle der Verwendung der Methode `msgbox()`, verwenden Sie [`OptionDialog.showMessageDialog()`](../components/option-dialogs/message), um Nachrichtendialoge zu erstellen.

### App-Beendigung {#app-termination}

Die Methode `cleanup()` wurde entfernt. Es gibt jetzt zwei Methoden für Beendigungen, `onWillTerminate()` und `onDidTerminate()`.

Siehe [Hooks für die Beendigung](../advanced/terminate-and-error-actions.md#hooks-for-termination) für weitere Informationen.

## Tabellensortierung {#table-sorting}

Für webforJ 25.00 und höher verwenden Tabellen standardmäßig die Sortierung nach einer Spalte. Spalten werden nur nach dem zuletzt ausgewählten Spaltenkopf sortiert. Um eine Tabelle für die Mehrfachspaltensortierung zu verwenden, rufen Sie die Methode [`setMultiSorting()`](../components/table/sorting#multi-sorting) auf:

```java
table.setMultiSorting(true);
```

## Versteckter `TabbedPane`-Körper {#hidden-tabbedpane-body}

Die Methode `hideBody()` wird durch `setBodyHidden()` ersetzt, um eine konsistente Namensgebung für Methoden beizubehalten.

## HTML im Inneren von Komponenten rendern {#rendering-html-inside-components}

In webforJ 25.00 und höher gibt es eine Methode `setHtml()`, um zwischen der Festlegung von Literal- und HTML-Text in einer Komponente zu unterscheiden. Die Festlegung von HTML mit der Methode `setText()` ist weiterhin möglich, erfordert jetzt jedoch, dass sie ausdrücklich mit `<html>`-Tags umschlossen wird.

```java
// Gültige Verwendungen von setText() und setHtml()
Button home = new Button();

home.setText(""" 
  <html>
    <h1>Startseite</h1>
  </html>
""");

home.setHtml("<h1>Startseite</h1>");

home.setText("Startseite");
```

```java
// Ungültige Verwendungen von setText() und setHtml()
Button home = new Button();
home.setText("<h1>Startseite</h1>");
```

## HTML-Container {#html-containers}

Das Paket `com.webforj.component.htmlcontainer` ist nicht mehr in webforJ vorhanden. Verwenden Sie stattdessen das funktionsreichere Paket `com.webforj.component.element`. Für eine Liste von webforJ-Klassen für standardmäßige HTML-Elemente besuchen Sie [HTML Element Components](../building-ui/web-components/html-elements.md).

## `Request`-Änderungen {#request-changes}

- So wie die Methode `getCookieStorage()` für die `App`-Klasse entfernt wurde, verfügt `Request` auch nicht mehr über die Methode `getCookie()`. Dies verstärkt die Verwendung von `CookieStorgage.getCurrent()`, um eine Instanz der `CookieStorage`-Klasse zu erhalten.

- Die Methode `getQueryParam()` heißt jetzt `getQueryParameter()`.

## Änderungen bei `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Abrufen einer Instanz von `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

Die Klasse `Environment` hat die Methode `getWebforjHelper()` nicht mehr, verwenden Sie stattdessen `getBridge()`.

### Verwendung der `ConfirmDialog`-Komponente für die Methode `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Frühere Versionen von webforJ verwendeten Strings und Ganzzahlen direkt für die Methode `msgbox()` von `WebforjBBjBridge`. In webforJ 25.00 und höher verwenden Nachrichten für `WebforjBBjBridge` die Komponente [`ConfirmDialog`](../components/option-dialogs/confirm.md). Dies gibt mehr Kontrolle darüber, welche Schaltflächen angezeigt werden und welcher Nachrichtentyp verwendet wird.

**Vorher**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getWebforjHelper();

int msgboxResult = bridge.msgbox("Sind Sie sicher, dass Sie diese Datei löschen möchten?", 1, "Löschung");
```

**Nachher**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getBridge();

ConfirmDialog dialog = new ConfirmDialog(
      "Sind Sie sicher, dass Sie diese Datei löschen möchten?", "Löschung",
      ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);

int msgboxResult = bridge.msgbox(dialog);
```

## Rechtschreibkorrektur `PasswordMediation` {#passwordmediation-typo-correction}

Die Enum-Klasse `PasswordMediation`, die angibt, ob ein Benutzer bei jedem Besuch einer App mit einer `Login`-Komponente angemeldet werden muss, hatte in früheren Versionen von webforJ einen Schreibfehler. `SILENT` ersetzt den Schreibfehler `SILIENT` für webforJ 25.00 und höher.

## Autofokus-Methoden {#auto-focusing-methods}

Um webforJ konsistent zu halten, weisen Methoden wie `setAutofocus()` und `isAutofocus()` jetzt eine einheitliche Großschreibung auf, wie es das Interface HasAutoFocus vorsieht. Komponenten wie `Dialog` und `Drawer` verwenden jetzt `setAutoFocus()` und `isAutoFocus()` für 25.00 und höher.

## `BBjWindowAdapter` und `Panel` als `final` markiert {#bbjwindowadapter-and-panel-marked-as-final}

Die Klassen `BBjWindowAdapter` und `Panel` sind jetzt als `final` deklariert, was bedeutet, dass sie nicht mehr unterklassen werden können. Diese Änderung verbessert die Stabilität und erzwingt konsistente Nutzungsmuster.
