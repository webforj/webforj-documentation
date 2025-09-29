---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 66df4ab330f26adccbed654c27c6be23
---
# Konfiguration der webforJ-Eigenschaften

Um eine webforJ-Anwendung erfolgreich bereitzustellen und auszuführen, sind einige wichtige Konfigurationsdateien erforderlich: `webforj.conf` und `web.xml`. Jede dieser Dateien steuert unterschiedliche Aspekte des Verhaltens der Anwendung, von Einstiegspunkten und Debug-Einstellungen bis hin zu Servlet-Zuordnungen.

## Konfiguration von `webforj.conf` {#configuring-webforjconf}

Die Datei `webforj.conf` ist eine zentrale Konfigurationsdatei in webforJ, die Anwendungseinstellungen wie Einstiegspunkte, Debug-Modus und Client-Server-Interaktion speichert. Die Datei ist im [HOCON-Format](https://github.com/lightbend/config/blob/master/HOCON.md) und sollte im Verzeichnis `resources` abgelegt sein.

:::tip
Wenn Sie mit dem [Spring Framework](../integrations/spring/overview.md) integrieren, können Sie diese `webforj.conf`-Eigenschaften in der Datei `application.properties` festlegen.
:::



### Beispiel für eine `webforj.conf`-Datei {#example-webforjconf-file}

```Ini
# Diese Konfigurationsdatei ist im HOCON-Format:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Konfigurationsoptionen {#configuration-options}

<!-- Viele Wertprobleme behoben, aber für die Tabelle deaktiviert, da diese Beschreibungen direkt aus Kommentaren im Code stammen -->
<!-- vale off -->

| Eigenschaft                             | Typ    | Erklärung                                                       | Standard                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-Header für statische Ressourcen.                        | `null` |
| **`webforj.assetsDir`**              | String  | Der Routenname, der verwendet wird, um statische Dateien bereitzustellen, während der tatsächliche Ordnername `static` bleibt. Diese Konfiguration ist hilfreich, wenn die Standardroute `static` mit einer in Ihrer Anwendung definierten Route in Konflikt steht, sodass Sie den Routennamen ändern können, ohne den Ordner selbst umzubenennen.       | `null`               |
| **`webforj.assetsExt`**              | String  | Standard-Dateierweiterung für statische Dateien. | `null` |
| **`webforj.assetsIndex`**            | String  | Standarddatei, die bei Verzeichnisanfragen bereitgestellt wird (z. B. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Das Intervall, in dem der Client den Server anpingt, um zu prüfen, ob er noch aktiv ist. Für die Entwicklung sollte dies auf ein kürzeres Intervall, z. B. `8s`, eingestellt werden, um die Verfügbarkeit des Servers schnell zu überprüfen. In der Produktion auf 50 Sekunden oder mehr setzen, um übermäßige Anfragen zu vermeiden. | `50s`           |
| **`webforj.components`**             | String  | Wenn angegeben, bestimmt der Basis-Pfad, von wo aus DWC-Komponenten geladen werden. Standardmäßig werden die Komponenten vom Server geladen, der die Anwendung hostet. Durch das Festlegen eines benutzerdefinierten Basis-Pfades können Komponenten von einem alternativen Server oder CDN geladen werden. Zum Beispiel, um Komponenten von jsdelivr.com zu laden, setzen Sie den Basis-Pfad auf: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Es ist wichtig, dass die geladenen Komponenten mit der Version des verwendeten webforJ-Frameworks kompatibel sind; andernfalls kann die Anwendung möglicherweise nicht wie erwartet funktionieren. Diese Einstellung wird ignoriert, wenn eine Standard-BBj-Installation ohne die Engine verwendet wird. Für eine Standard-BBj-Installation kann die Einstellung mit der `!COMPONENTS` STBL verwaltet werden. | `null`          |
| **`webforj.debug`**                  | Boolean | Aktiviert den Debug-Modus. Im Debug-Modus gibt webforJ zusätzliche Informationen in der Konsole aus und zeigt alle Ausnahmen im Browser an. Der Debug-Modus ist standardmäßig deaktiviert. | `null`          |
| **`webforj.entry`**                  | String  | Definiert den Einstiegspunkt der Anwendung, indem der vollständig qualifizierte Name der Klasse angegeben wird, die `webforj.App` erweitert. Wenn kein Einstiegspunkt definiert ist, durchsucht webforJ automatisch den Klassenpfad nach Klassen, die `webforj.App` erweitern. Wenn mehrere Klassen gefunden werden, tritt ein Fehler auf. Wenn ein Paket mehr als einen potenziellen Einstiegspunkt enthält, ist es erforderlich, diesen ausdrücklich festzulegen, um Mehrdeutigkeiten zu vermeiden, oder alternativ kann die Annotation `AppEntry` verwendet werden, um den Einstiegspunkt zur Laufzeit anzugeben. | `null`          |
| **`webforj.fileUpload.accept`**      | List    | Die zulässigen Dateitypen für Datei-Uploads. Standardmäßig sind alle Dateitypen erlaubt. Unterstützte Formate umfassen MIME-Typen wie `image/*`, `application/pdf`, `text/plain` oder Dateierweiterungen wie `*.txt`. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | Die maximale Dateigröße für Datei-Uploads in Byte. Standardmäßig gibt es keine Begrenzung. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-Endpunkt für das Verzeichnis von Symbolen (standardmäßig von `resources/icons/`). | `icons/` |
| **`webforj.license.cfg`**            | String  | Das Verzeichnis für die Lizenzkonfiguration. Standardmäßig ist es dasselbe wie das Konfigurationsverzeichnis von webforJ, kann aber bei Bedarf angepasst werden. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Lizenzstartzeitüberschreitung in Sekunden. | `null` |
| **`webforj.locale`**                 | String  | Die Gebietssprache der Anwendung, die Sprache, Regionseinstellungen und Formate für Daten, Zeiten und Zahlen bestimmt. | `null` |
| **`webforj.quiet`**                  | Boolean | Deaktiviert das Ladebild während des Anwendungsstarts. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Nur Entwicklungsumgebungen.** In einer Entwicklungsumgebung wird die Seite bei Fehlern, die mit der heißen Bereitstellung verbunden sind, automatisch neu geladen, jedoch nicht bei anderen Fehlerarten. Bei Verwendung von Hot Deploy kann ein Fehler auftreten, wenn der Client eine Anfrage an den Server sendet, während dieser neu gestartet wird, während die WAR-Datei getauscht wird. Da der Server voraussichtlich bald wieder online ist, ermöglicht diese Einstellung dem Client, einen Seitenneuladeversuch automatisch durchzuführen. | `false` |
| **`webforj.servlets[n].name`**       | String  | Servlet-Name (verwendet den Klassennamen, wenn nicht angegeben). | `null` |
| **`webforj.servlets[n].className`**  | String | Vollständig qualifizierter Klassenname des Servlets. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet-Initiierungsparameter. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Dauer der Session-Timeout in Sekunden. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Eine Map von Schlüssel-Wert-Paaren, die verwendet werden, um Zeichenfolgen für die Anwendung zu speichern. Nützlich zum Speichern von Anwendungsnachrichten oder -beschriftungen. Weitere Informationen zur `StringTable` finden Sie [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |

<!-- vale on -->

## Konfiguration von `web.xml` {#configuring-webxml}

Die Datei `web.xml` ist eine essentielle Konfigurationsdatei für Java-Webanwendungen, und in webforJ definiert sie wichtige Einstellungen wie die Servlet-Konfiguration, URL-Muster und Willkommensseiten. Diese Datei sollte im Verzeichnis `WEB-INF` der Bereitstellungsstruktur Ihres Projekts abgelegt werden.

| Einstellung                                 | Erklärung                                                                                                                                                                                   | Standardwert               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Legt den Anzeigennamen für die Webanwendung fest, der typischerweise vom Projektnamen abgeleitet wird. Dieser Name erscheint in den Verwaltungskonsolen der Anwendungsserver.                                                        | `${project.name}`           |
| **`<servlet>` und `<servlet-mapping>`** | Definiert das `WebforjServlet`, das zentrale Servlet für die Verarbeitung von webforJ-Anfragen. Dieses Servlet ist auf alle URLs (`/*`) zugeordnet und ist somit der Hauptzugangspunkt für Webanfragen.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Gibt an, dass das `WebforjServlet` geladen werden soll, wenn die Anwendung gestartet wird. Wenn dies auf `1` gesetzt ist, wird das Servlet sofort geladen, was die Verarbeitung der anfänglichen Anfragen verbessert.                | `1`                         |
<!-- | **`<filter>` und `<filter-mapping>`**   | Konfiguriert den `WebforjCacheControlFilter`, um das Caching von Javascript-Dateien zu steuern. Dieser Filter verhindert das Caching von `.js`-Dateien, indem er spezifische HTTP-Header setzt, was den Entwicklungsfluss verbessert. | `WebforjCacheControlFilter` | -->

<!-- ## Konfiguration von `blsclient.conf` -->
