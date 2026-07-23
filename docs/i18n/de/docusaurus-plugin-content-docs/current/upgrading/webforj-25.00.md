---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
sidebar_position: 30
_i18n_hash: 6fdaf15e67e0015f7319572200ccc353
---
Diese Dokumentation dient als Leitfaden zur Aktualisierung von webforJ-Apps von 24.00 auf 25.00. Hier sind die Änderungen, die für bestehende Apps erforderlich sind, um weiterhin reibungslos zu funktionieren. Wie immer, siehe die [GitHub-Release-Übersicht](https://github.com/webforj/webforj/releases) für eine umfassendere Liste der Änderungen zwischen den Versionen.

## Jetty 12-Webserver {#jetty-12-web-servers}

webforJ 25.00 und höher nutzen Jetty 12 und verwenden die Servlet-Architektur Jakarta EE10. Wenn Sie das Jetty Maven-Plugin für die Entwicklung verwenden, migrieren Sie von Jakarta EE8 zu Jakarta EE10. Dieses Upgrade erfordert auch den Austausch aller Verweise auf das Paket `javax.servlet` durch das Paket `Jakarta.servlet`.

### POM-Datei Änderungen {#pom-file-changes}

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

Die Dienstklasse [`BrowserConsole`](/docs/advanced/browser-console), die zum Erstellen von gestylten Protokollen für die Browserkonsole verwendet wird, ersetzt die Methoden `consoleLog()` und `consoleError()`. Sie erhalten die `BrowserConsole`, indem Sie die Methode `console()` verwenden:

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

Siehe [Web Storage](/docs/advanced/web-storage) für weitere Informationen.

### `Request`-Klasse {#request-class}

Die `Request`-Klasse ist nun verantwortlich für das Abrufen der URL, des Ports, des Hosts und des Protokolls einer App. Verwenden Sie anstelle von `App.getUrl()` die Methode `App.getCurrent().getUrl()`. Die Methode `getCurrent()` ersetzt auch die Methode `getRequest()`, um eine Instanz der `Request`-Klasse zu erhalten.

:::info
Die `Request`-Klasse hat ebenfalls entfernte Methoden; springen Sie zu [`Request`](#request-changes), um sie zu sehen.
:::

### `Page`-Klasse {#page-class}

Die Methode `getPage()` wird durch `Page.getCurrent()` ersetzt, um die aktuelle Seiteninstanz zu erhalten.

### Optionsdialoge {#option-dialogs}

Verwenden Sie anstelle der Methode `msgbox()` die Methode [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message), um Nachrichtendialoge zu erstellen.

### App-Beendigung {#app-termination}

Die Methode `cleanup()` wurde entfernt. Es gibt jetzt zwei Methoden für die Beendigung: `onWillTerminate()` und `onDidTerminate()`.

Siehe [Hooks für die Beendigung](/docs/advanced/terminate-and-error-actions#hooks-for-termination) für weitere Informationen.

## Tabellen-Sortierung {#table-sorting}

Für webforJ 25.00 und höher verwenden Tabellen standardmäßig die Sortierung nach einer einzelnen Spalte. Spalten werden nur nach dem zuletzt ausgewählten Spaltenkopf sortiert. Um eine Tabelle mit Mehrspaltensortierung zu verwenden, rufen Sie die Methode [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting) auf:

```java
table.setMultiSorting(true);
```

## Versteckter `TabbedPane`-Inhalt {#hidden-tabbedpane-body}

Die Methode `hideBody()` wird durch `setBodyHidden()` ersetzt, um eine konsistente Namenskonvention für Methoden beizubehalten.

## Rendering von HTML in Komponenten {#rendering-html-inside-components}

In webforJ 25.00 und höher gibt es eine Methode `setHtml()`, um zwischen der Festlegung von purem und HTML-Text innerhalb einer Komponente zu unterscheiden. Das Festlegen von HTML mit der Methode `setText()` ist weiterhin möglich, erfordert jedoch nun, dass der Text explizit mit `<html>`-Tags umschlossen wird.

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

Das Paket `com.webforj.component.htmlcontainer` ist in webforJ nicht mehr vorhanden. Verwenden Sie stattdessen das funktionsreichere Paket `com.webforj.component.element`. Für eine Liste der webforJ-Klassen für Standard-HTML-Elemente gehen Sie zu [HTML Element Components](/docs/components/html-elements).

## Änderungen an `Request` {#request-changes}

- Genau wie die Entfernung der Methode `getCookieStorage()` für die `App`-Klasse hat `Request` auch die Methode `getCookie()` nicht mehr. Dies verstärkt die Verwendung von `CookieStorage.getCurrent()`, um eine Instanz der `CookieStorage`-Klasse zu erhalten.

- Die Methode `getQueryParam()` heißt jetzt `getQueryParameter()`.

## Änderungen an `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Abrufen einer Instanz von `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

Die `Environment`-Klasse hat die Methode `getWebforjHelper()` nicht mehr, verwenden Sie stattdessen `getBridge()`.

### Verwendung der Komponente `ConfirmDialog` für die Methode `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Frühere Versionen von webforJ verwenden Strings und Ganzzahlen direkt für die `msgbox()`-Methode des `WebforjBBjBridge`. In webforJ 25.00 und höher verwenden Nachrichten für `WebforjBBjBridge` die Komponente [`ConfirmDialog`](/docs/components/option-dialogs/confirm). Dies gibt mehr Kontrolle darüber, welche Schaltflächen angezeigt werden und welcher Nachrichtentyp verwendet wird.

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

## Tippfehlerkorrektur in `PasswordMediation` {#passwordmediation-typo-correction}

Die Enum-Klasse `PasswordMediation`, die angibt, ob ein Benutzer bei jedem Besuch einer App mit einer `Login`-Komponente anmelden muss, hatte in vorherigen webforJ-Versionen einen Tippfehler. `SILENT` ersetzt den Tippfehler `SILIENT` für webforJ 25.00 und höher.

## Auto-Fokus-Methoden {#auto-focusing-methods}

Um webforJ konsistent zu halten, haben Methoden wie `setAutofocus()` und `isAutofocus()` jetzt eine einheitliche Großschreibung, ähnlich wie die Schnittstelle HasAutoFocus. Komponenten wie `Dialog` und `Drawer` verwenden für 25.00 und höher `setAutoFocus()` und `isAutoFocus()`.

## `BBjWindowAdapter` und `Panel` als `final` markiert {#bbjwindowadapter-and-panel-marked-as-final}

Die Klassen `BBjWindowAdapter` und `Panel` sind jetzt als `final` deklariert, was bedeutet, dass sie nicht mehr untergeordnet werden können. Diese Änderung verbessert die Stabilität und zwingt zu konsistenten Nutzungsgewohnheiten.
