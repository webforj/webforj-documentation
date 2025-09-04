---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 71f184a02c2552f5af34bfc3ec47c385
---
Diese Dokumentation dient als Leitfaden zum Upgrade von webforJ-Anwendungen von 24.00 auf 25.00. Hier sind die Änderungen, die für bestehende Apps erforderlich sind, um weiterhin reibungslos zu funktionieren. Wie immer finden Sie die [GitHub-Release-Übersicht](https://github.com/webforj/webforj/releases) für eine umfassendere Liste der Änderungen zwischen den Versionen.

## Jetty 12-Webserver {#jetty-12-web-servers}

webforJ 25.00 und höher verwenden Jetty 12, das die Jakarta EE10-Servlet-Architektur nutzt. Wenn Sie das Jetty Maven-Plugin für die Entwicklung verwenden, migrieren Sie von Jakarta EE8 zu Jakarta EE10. Dieses Upgrade erfordert auch, alles, was auf dem Paket `javax.servlet` basierte, durch das Paket `Jakarta.servlet` zu ersetzen.

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

Mehrere veraltete `App`-Methoden wurden in 25.00 entfernt. Die folgenden Abschnitte umreißen, welche Methoden ersetzt wurden und welche empfohlenen Ersetzungen es gibt.

### Konsolenprotokollierung {#console-logging}

Die Utility-Klasse [`BrowserConsole`](../advanced/browser-console.md), die zum Erstellen von formatierten Protokollen in der Browserkonsole verwendet wird, ersetzt die Methoden `consoleLog()` und `consoleError()`. Holen Sie sich die `BrowserConsole`, indem Sie die Methode `console()` verwenden:

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("Protokollnachricht");
    console().error("Fehlermeldung");
  }
}
```

### Web-Speicher {#web-storage}

Für Versionen vor webforJ 25.00 hat die `App`-Klasse die Methoden `getLocalStorage()`, `getSessionStorage()` und `getCookieStorage()`, um Instanzen der Klassen `LocalStorage`, `SessionStorage` und `CookieStorage` zu erhalten. Zukünftig hat jede Klasse eine Methode `getCurrent()`.

Siehe [Web Storage](../advanced/web-storage.md) für weitere Informationen.

### `Request`-Klasse {#request-class}

Die `Request`-Klasse ist jetzt dafür verantwortlich, die URL, den Port, den Host und das Protokoll einer App abzurufen. Anstelle von `App.getUrl()` verwenden Sie `App.getCurrent().getUrl()`. Die Methode `getCurrent()` ersetzt auch die Methode `getRequest()`, um eine Instanz der `Request`-Klasse zu erhalten.

:::info
Die `Request`-Klasse hat ebenfalls entfernte Methoden, springen Sie zu [`Request`](#request-changes), um sie zu sehen.
:::

### `Page`-Klasse {#page-class}

Die Methode `getPage()` wurde durch `Page.getCurrent()` ersetzt, um die aktuelle Seiteninstanz abzurufen.

### Optionsdialoge {#option-dialogs}

Verwenden Sie anstelle der Methode `msgbox()` den [`OptionDialog.showMessageDialog()`](../components/option-dialogs/message), um Nachrichtendialoge zu erstellen.

### App-Beendigung {#app-termination}

Die Methode `cleanup()` wurde entfernt. Es gibt jetzt zwei Methoden für Beendigungen: `onWillTerminate()` und `onDidTerminate()`.

Siehe [Hooks für die Beendigung](../advanced/terminate-and-error-actions.md#hooks-for-termination) für weitere Informationen.

## Tabellensortierung {#table-sorting}

Für webforJ 25.00 und höher verwenden Tabellen standardmäßig die Sortierung nach einer einzelnen Spalte. Spalten werden nur nach dem zuletzt ausgewählten Spaltenkopf sortiert. Um eine Tabelle für die Mehrspaltensortierung zu verwenden, rufen Sie die Methode [`setMultiSorting()`](../components/table/sorting#multi-sorting) auf:

```java
table.setMultiSorting(true);
```

## Versteckter `TabbedPane`-Körper {#hidden-tabbedpane-body}

Die Methode `hideBody()` wurde durch `setBodyHidden()` ersetzt, um eine konsistente Namenskonvention für Methoden zu wahren.

## Rendering von HTML innerhalb von Komponenten {#rendering-html-inside-components}

In webforJ 25.00 und höher gibt es eine Methode `setHtml()`, um zwischen dem Setzen von literalem und HTML-Text innerhalb einer Komponente zu unterscheiden. Das Setzen von HTML mit der Methode `setText()` ist weiterhin möglich, erfordert jedoch jetzt explizites Umschließen mit `<html>`-Tags.

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

Das Paket `com.webforj.component.htmlcontainer` ist nicht mehr in webforJ vorhanden. Verwenden Sie stattdessen das funktionsreichere Paket `com.webforj.component.element`. Für eine Liste von webforJ-Klassen für Standard-HTML-Elemente siehe [HTML Element Components](../building-ui/web-components/html-elements.md).

## Änderungen bei `Request` {#request-changes}

- Wie die Entfernung der Methode `getCookieStorage()` für die `App`-Klasse hat `Request` auch nicht mehr die Methode `getCookie()`. Dies verstärkt die Verwendung von `CookieStorage.getCurrent()`, um eine Instanz der `CookieStorage`-Klasse zu erhalten.

- Die Methode `getQueryParam()` heißt jetzt `getQueryParameter()`.

## Änderungen bei `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Abrufen einer Instanz von `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

Die Klasse `Environment` hat nicht mehr die Methode `getWebforjHelper()`, verwenden Sie stattdessen `getBridge()`.

### Verwendung der `ConfirmDialog`-Komponente für die Methode `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Frühere Versionen von webforJ verwendeten Zeichenfolgen und Ganzzahlen direkt für die `msgbox()`-Methode von `WebforjBBjBridge`. Nachrichten für `WebforjBBjBridge` in webforJ 25.00 und höher verwenden die [`ConfirmDialog`](../components/option-dialogs/confirm.md)-Komponente. Dies bietet mehr Kontrolle über die angezeigten Schaltflächen und den Nachrichtentyp.


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

<!-- ## Environment.logError entfernt -->

## Rechtschreibkorrektur von `PasswordMediation` {#passwordmediation-typo-correction}

Die Enum-Klasse `PasswordMediation`, die angibt, ob ein Benutzer bei jedem Besuch einer App mit einer `Login`-Komponente angemeldet werden muss, hatte in früheren webforJ-Versionen einen Tippfehler. `SILENT` ersetzt den Tippfehler `SILIENT` für webforJ 25.00 und höher.

## Auto-Fokus-Methoden {#auto-focusing-methods}

Um webforJ konsistent zu halten, haben Methoden wie `setAutofocus()` und `isAutofocus()` jetzt eine einheitliche Großschreibung wie das Interface HasAutoFocus. Daher verwenden Komponenten wie `Dialog` und `Drawer` für 25.00 und höher `setAutoFocus()` und `isAutoFocus()`.

## `BBjWindowAdapter` und `Panel` als `final` markiert {#bbjwindowadapter-and-panel-marked-as-final}

Die Klassen `BBjWindowAdapter` und `Panel` sind jetzt als `final` deklariert, was bedeutet, dass sie nicht mehr unterklassenfähig sind. Diese Änderung verbessert die Stabilität und erzwingt konsistente Nutzungsmuster.
