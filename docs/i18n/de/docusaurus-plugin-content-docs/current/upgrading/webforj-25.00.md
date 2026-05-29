---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 53afcc2a74e5569086bcf7daeb6582d7
---
Diese Dokumentation dient als Leitfaden zum Upgrade von webforJ-Apps von 24.00 auf 25.00. Hier sind die notwendigen Änderungen, damit bestehende Apps weiterhin reibungslos funktionieren. Wie immer finden Sie in der [GitHub-Veröffentlichungsübersicht](https://github.com/webforj/webforj/releases) eine umfassendere Liste der Änderungen zwischen den Versionen.

## Jetty 12-Webserver {#jetty-12-web-servers}

webforJ 25.00 und höher verwenden Jetty 12, das die Jakarta EE10-Servlet-Architektur nutzt. Wenn Sie das Jetty Maven-Plugin für die Entwicklung verwenden, migrieren Sie von Jakarta EE8 zu Jakarta EE10. Dieses Upgrade erfordert auch, alles, was auf das `javax.servlet`-Paket angewiesen war, durch das `Jakarta.servlet`-Paket zu ersetzen.

### POM-Dateiänderungen {#pom-file-changes}

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

Mehrere veraltete `App`-Methoden wurden in 25.00 entfernt. Die folgenden Abschnitte skizzieren, welche Methoden ersetzt wurden und welche Empfehlungen bestehen.

### Konsolenprotokollierung {#console-logging}

Die Dienstklasse [`BrowserConsole`](/docs/advanced/browser-console), die zum Erstellen von formatierte Protokollen für die Browserkonsole vorgesehen ist, ersetzt die Methoden `consoleLog()` und `consoleError()`. Holen Sie sich die `BrowserConsole`, indem Sie die `console()`-Methode verwenden:

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

Für Versionen vor webforJ 25.00 verfügt die `App`-Klasse über die Methoden `getLocalStorage()`, `getSessionStorage()` und `getCookieStorage()`, um Instanzen der Klassen `LocalStorage`, `SessionStorage` und `CookieStorage` zu erhalten. Zukünftig hat jede Klasse eine `getCurrent()`-Methode.

Siehe [Web Storage](/docs/advanced/web-storage) für weitere Informationen.

### `Request`-Klasse {#request-class}

Die `Request`-Klasse ist jetzt verantwortlich für das Abrufen der URL, des Ports, des Hosts und des Protokolls einer App. Verwenden Sie also anstelle von `App.getUrl()` die Methode `App.getCurrent().getUrl()`. Die Methode `getCurrent()` ersetzt auch die Methode `getRequest()`, um eine Instanz der `Request`-Klasse zu erhalten.

:::info
Die `Request`-Klasse hat auch entfernte Methoden, springen Sie zu [`Request`](#request-changes), um diese zu sehen.
:::

### `Page`-Klasse {#page-class}

Die Methode `getPage()` wird durch `Page.getCurrent()` ersetzt, um die aktuelle Seiteninstanz abzurufen.

### Optionsdialoge {#option-dialogs}

Verwenden Sie anstelle der Methode `msgbox()` die Methode [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message), um Nachrichten.dialoge zu erstellen.

### App-Beendigung {#app-termination}

Die Methode `cleanup()` wurde entfernt. Es gibt jetzt zwei Methoden für die Beendigung: `onWillTerminate()` und `onDidTerminate()`.

Siehe [Hooks für die Beendigung](/docs/advanced/terminate-and-error-actions#hooks-for-termination) für weitere Informationen.

## Tabellensortierung {#table-sorting}

Für webforJ 25.00 und höher verwenden Tabellen standardmäßig die Sortierung nach einer Spalte. Spalten werden nur nach der zuletzt ausgewählten Spaltenüberschrift sortiert. Um eine Tabelle mit Mehrspalten-Sortierung zu verwenden, rufen Sie die Methode [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting) auf:

```java
table.setMultiSorting(true);
```

## Versteckter `TabbedPane`-Körper {#hidden-tabbedpane-body}

Die Methode `hideBody()` wird durch `setBodyHidden()` ersetzt, um eine konsistente Benennungskonvention für Methoden beizubehalten.

## HTML innerhalb von Komponenten rendern {#rendering-html-inside-components}

In webforJ 25.00 und höher gibt es eine Methode `setHtml()`, um zwischen der Festlegung von literalem und HTML-Text innerhalb einer Komponente zu unterscheiden. Das Setzen von HTML mit der Methode `setText()` ist weiterhin möglich, erfordert jedoch nun, dass es explizit mit `<html>`-Tags umschlossen wird.

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

Das Paket `com.webforj.component.htmlcontainer` ist in webforJ nicht mehr vorhanden. Verwenden Sie stattdessen das funktionsreichere Paket `com.webforj.component.element`. Für eine Liste von webforJ-Klassen für Standard-HTML-Elemente gehen Sie zu [HTML Element Components](/docs/components/html-elements).

## Änderungen an `Request` {#request-changes}

- Genau wie die Entfernung der Methode `getCookieStorage()` für die `App`-Klasse hat `Request` nicht mehr die Methode `getCookie()`. Dies verstärkt die Verwendung von `CookieStorgage.getCurrent()`, um eine Instanz der `CookieStorage`-Klasse zu erhalten.

- Die Methode `getQueryParam()` heißt jetzt `getQueryParameter()`.

## Änderungen an `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Abrufen einer Instanz von `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

Die Klasse `Environment` hat die Methode `getWebforjHelper()` nicht mehr, verwenden Sie stattdessen `getBridge()`.

### Verwenden der Komponente `ConfirmDialog` für die Methode `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Frühere Versionen von webforJ verwendeten direkt Strings und Ganzzahlen für die `msgbox()`-Methode von `WebforjBBjBridge`. Nachrichten für `WebforjBBjBridge` in webforJ 25.00 und höher verwenden die Komponente [`ConfirmDialog`](/docs/components/option-dialogs/confirm). Dies bietet mehr Kontrolle über die angezeigten Schaltflächen und den Nachrichtentyp.

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

## Korrektur des Tippfehlers bei `PasswordMediation` {#passwordmediation-typo-correction}

Die Enum-Klasse `PasswordMediation`, die angibt, ob ein Benutzer bei jedem Besuch einer App mit einer `Login`-Komponente zur Anmeldung verpflichtet ist, hatte in früheren Versionen von webforJ einen Tippfehler. `SILENT` ersetzt den Tippfehler `SILIENT` für webforJ 25.00 und höher.

## Auto-Fokus-Methoden {#auto-focusing-methods}

Um webforJ konsistent zu halten, haben Methoden wie `setAutofocus()` und `isAutofocus()` jetzt eine einheitliche Großschreibung wie die Schnittstelle HasAutoFocus. Komponenten wie `Dialog` und `Drawer` verwenden ab 25.00 `setAutoFocus()` und `isAutoFocus()`.

## `BBjWindowAdapter` und `Panel` als `final` markiert {#bbjwindowadapter-and-panel-marked-as-final}

Die Klassen `BBjWindowAdapter` und `Panel` sind jetzt als `final` deklariert, was bedeutet, dass sie nicht mehr unterklassifiziert werden können. Diese Änderung verbessert die Stabilität und fördert konsistente Nutzungsmuster.
