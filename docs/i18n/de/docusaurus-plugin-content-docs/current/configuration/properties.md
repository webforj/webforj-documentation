---
title: Property Configuration
sidebar_position: 30
_i18n_hash: dea9eb679150ca6124fb625c7d04d27c
---
# Konfigurierung der webforJ-Eigenschaften

Um eine webforJ-Anwendung erfolgreich bereitzustellen und auszuführen, sind einige wichtige Konfigurationsdateien erforderlich: `webforJ.conf` und `web.xml`. Jede dieser Dateien steuert verschiedene Aspekte des Verhaltens der Anwendung, von Einstiegspunkten und Debug-Einstellungen bis hin zu Servlet-Zuordnungen.

## Konfigurierung von `webforj.conf` {#configuring-webforjconf}

Die `webforJ.conf`-Datei ist eine zentrale Konfigurationsdatei in webforJ, die Anwendungseinstellungen wie Einstiegspunkte, Debug-Modus und Client-Server-Interaktion festlegt. Die Datei ist im [HOCON-Format](https://github.com/lightbend/config/blob/master/HOCON.md) geschrieben und sollte im `resources`-Verzeichnis gespeichert werden.

### Beispiel für die `webforj.conf`-Datei {#example-webforjconf-file}

```Ini
# Diese Konfigurationsdatei ist im HOCON-Format:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Konfigurationsoptionen {#configuration-options}

| Eigenschaft                       | Erklärung                                                                                                                                                                               | Standard         |
|----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| **`webforj.entry`**              | Definiert den Einstiegspunkt der Anwendung, indem der vollständig qualifizierte Name der Klasse angegeben wird, die `webforj.App` erweitert. Wenn kein Einstiegspunkt definiert ist, erkennt webforJ automatisch Klassen im Klassenpfad, die `webforj.App` erweitern. Wenn mehrere Klassen gefunden werden, tritt ein Fehler auf. Wenn ein Paket mehr als einen potenziellen Einstiegspunkt enthält, ist es erforderlich, dies explizit festzulegen, um Mehrdeutigkeiten zu vermeiden, oder alternativ kann die `AppEntry`-Annotation verwendet werden, um den Einstiegspunkt zur Laufzeit anzugeben. | `null`            |
| **`webforj.debug`**              | Aktiviert den Debug-Modus. Im Debug-Modus gibt webforJ zusätzliche Informationen in der Konsole aus und zeigt alle Ausnahmen im Browser an. Der Debug-Modus ist standardmäßig deaktiviert. | `null`            |
| **`webforj.reloadOnServerError`** | Bei Verwendung von Hot-Redeploy wird die gesamte WAR-Datei ausgetauscht. Wenn der Client versucht, eine Anfrage an den Server zu senden, während er neu startet, tritt ein Fehler auf. Diese Einstellung ermöglicht es dem Client, einen Seitenneuladevorgang zu versuchen, wenn der Server vorübergehend nicht verfügbar ist, in der Hoffnung, dass er bald wieder online ist. Dies gilt nur für Entwicklungsumgebungen und behandelt nur Fehler, die spezifisch für die Hot-Redeployment sind, nicht andere Arten von Fehlern. | `on`              |
| **`webforj.clientHeartbeatRate`** | Legt das Intervall fest, in dem der Client den Server anpingt, um zu überprüfen, ob er noch aktiv ist. Dies hilft, die Kommunikation aufrechtzuerhalten. Für die Entwicklung sollte dies auf ein kürzeres Intervall eingestellt werden, z. B. `8s`, um die Verfügbarkeit des Servers schnell zu prüfen. In der Produktion sollte dies nicht unter 50 Sekunden eingestellt werden, um übermäßige Anfragen zu vermeiden. | `50s`             |
| **`webforj.components`**         | Wenn angegeben, bestimmt der Basis-Pfad, von wo DWC-Komponenten geladen werden. Standardmäßig werden Komponenten vom Server geladen, auf dem die Anwendung gehostet wird. Durch Festlegen eines benutzerdefinierten Basis-Pfades können Komponenten von einem anderen Server oder CDN geladen werden. Beispielsweise, um Komponenten von jsdelivr.com zu laden, den Basis-Pfad auf: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} festlegen. Es ist wichtig, dass die geladenen Komponenten mit der Version des verwendeten webforJ-Frameworks kompatibel sind; andernfalls funktioniert die Anwendung möglicherweise nicht wie erwartet. Diese Einstellung wird ignoriert, wenn eine Standard-BBj-Installation ohne die Engine verwendet wird. Bei einer Standard-BBj-Installation kann die Einstellung mit dem `!COMPONENTS` STBL verwaltet werden. | `null`            |
| **`webforj.locale`**             | Definiert die Gebietsschema für die Anwendung, bestimmt Sprache, Regionseinstellungen und Formate für Daten, Zeiten und Zahlen.                                                          | `null`            |
| **`webforj.assetsDir`**         | Gibt den Routenamen an, der zum Bereitstellen statischer Dateien verwendet wird, während der physische Ordnername `static` bleibt. Diese Konfiguration ist hilfreich, wenn die Standardroute `static` mit einer in Ihrer Anwendung definierten Route in Konflikt steht, da Sie damit den Routenamen ändern können, ohne den Ordner selbst umzubenennen. | `static`          |
| **`webforj.stringTable`**        | Eine Zuordnung von Schlüssel-Wert-Paaren, die verwendet wird, um Strings für die Verwendung in der Anwendung zu speichern. Nützlich für das Speichern von Anwendungsnachrichten oder -etiketten. Weitere Informationen zu `StringTable` finden Sie [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`              |
| **`webforj.fileUpload.accept`**  | Gibt die zulässigen Dateitypen für Datei-Uploads an. Standardmäßig sind alle Dateitypen erlaubt. Unterstützte Formate umfassen MIME-Typen wie `image/*`, `application/pdf`, `text/plain` oder Dateiendungen wie `*.txt`. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `[]`              |
| **`webforj.fileUpload.maxSize`** | Definiert die maximale Dateigröße, die für Datei-Uploads erlaubt ist, in Bytes. Standardmäßig gibt es keine Begrenzung. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `null`            |
| **`license.cfg`**                | Konfiguriert das Verzeichnis für die Lizenzkonfiguration. Standardmäßig ist es dasselbe wie das Konfigurationsverzeichnis von webforJ, kann jedoch bei Bedarf angepasst werden. | `"."`             |

## Konfigurierung von `web.xml` {#configuring-webxml}

Die `web.xml`-Datei ist eine essentielle Konfigurationsdatei für Java-Webanwendungen, und in webforJ definiert sie wichtige Einstellungen wie die Servlet-Konfiguration, URL-Muster und Willkommensseiten. Diese Datei sollte im `WEB-INF`-Verzeichnis der Bereitstellungsstruktur Ihres Projekts abgelegt werden.

| Einstellung                              | Erklärung                                                                                                                                                                                             | Standardwert               |
|-----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| **`<display-name>`**                    | Legt den Anzeigenamen für die Webanwendung fest, der in der Regel aus dem Projektnamen abgeleitet ist. Dieser Name erscheint in den Verwaltungs-Konsolen der Anwendungsserver.                          | `${project.name}`           |
| **`<servlet>` und `<servlet-mapping>`** | Definiert den `WebforjServlet`, das zentrale Servlet für die Verarbeitung von webforJ-Anfragen. Dieses Servlet ist auf alle URLs (`/*`) abgebildet, was es zum Haupteinstiegspunkt für Webanfragen macht. | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Gibt an, dass `WebforjServlet` geladen werden soll, wenn die Anwendung startet. Durch das Setzen auf `1` wird sichergestellt, dass das Servlet sofort geladen wird, was die Bearbeitung anfänglicher Anfragen verbessert. | `1`                        |
