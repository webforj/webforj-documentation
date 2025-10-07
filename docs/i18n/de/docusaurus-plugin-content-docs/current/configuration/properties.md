---
title: Property Configuration
sidebar_position: 30
_i18n_hash: e7a922cb3f035dd19fdc282d245bdf2c
---
# Konfiguration der webforJ-Eigenschaften

Um eine webforJ-Anwendung erfolgreich bereitzustellen und auszuführen, sind einige wichtige Konfigurationsdateien erforderlich: `webforj.conf` und `web.xml`. Jede dieser Dateien steuert verschiedene Aspekte des Verhaltens der Anwendung, von Einstiegspunkten und Debug-Einstellungen bis hin zu Servlet-Zuordnungen.

## Konfiguration von `webforj.conf` {#configuring-webforjconf}

Die Datei `webforj.conf` ist eine zentrale Konfigurationsdatei in webforJ, die die Einstellungen der Anwendung wie Einstiegspunkte, Debug-Modus und Client-Server-Interaktion festlegt. Die Datei liegt im [HOCON-Format](https://github.com/lightbend/config/blob/master/HOCON.md) vor und sollte im Verzeichnis `resources` abgelegt sein.

:::tip
Wenn Sie mit [Spring](../integrations/spring/overview.md) integrieren, können Sie diese `webforj.conf`-Eigenschaften in der Datei `application.properties` festlegen.
:::



### Beispiel für die Datei `webforj.conf` {#example-webforjconf-file}

```Ini
# Diese Konfigurationsdatei ist im HOCON-Format:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Konfigurationsoptionen {#configuration-options}

| Eigenschaft                             | Typ    | Erläuterung                                                       | Standard                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-Header für statische Ressourcen.                        | `null` |
| **`webforj.assetsDir`**              | String  | Der Routenname, der verwendet wird, um statische Dateien zu bedienen, während der tatsächliche Ordnername `static` bleibt. Diese Konfiguration ist hilfreich, wenn die Standardroute `static` mit einer in Ihrer Anwendung definierten Route in Konflikt steht, sodass Sie den Routenname ändern können, ohne den Ordner selbst umzubenennen.       | `null`               |
| **`webforj.assetsExt`**              | String  | Standarddateierweiterung für statische Dateien. | `null` |
| **`webforj.assetsIndex`**            | String  | Standarddatei, die bei Verzeichnisanfragen bereitgestellt wird (z.B. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Das Intervall, in dem der Client den Server pingt, um zu sehen, ob er noch aktiv ist. Für die Entwicklung sollten Sie dies auf ein kürzeres Intervall wie `8s` einstellen, um die Serververfügbarkeit schnell zu erkennen. In der Produktion auf 50 Sekunden oder mehr einstellen, um übermäßige Anfragen zu vermeiden. | `50s`           |
| **`webforj.components`**             | String  | Wenn angegeben, bestimmt der Basisweg, von wo DWC-Komponenten geladen werden. Standardmäßig werden Komponenten von dem Server geladen, der die Anwendung hostet. Wenn Sie jedoch einen benutzerdefinierten Basisweg festlegen, können Komponenten von einem anderen Server oder CDN geladen werden. Beispielsweise, um Komponenten von jsdelivr.com zu laden, setzen Sie den Basisweg auf: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Es ist wichtig, dass die geladenen Komponenten mit der Version des verwendeten webforJ-Frameworks kompatibel sind; andernfalls funktioniert die Anwendung möglicherweise nicht wie erwartet. Diese Einstellung wird ignoriert, wenn eine Standard-BBj-Installation ohne die Engine verwendet wird. Für eine Standard-BBj-Installation kann die Einstellung mit der `!COMPONENTS` STBL verwaltet werden. | `null`          |
| **`webforj.debug`**                  | Boolean | Aktiviert den Debug-Modus. Im Debug-Modus wird webforJ zusätzliche Informationen auf der Konsole ausgeben und alle Ausnahmen im Browser anzeigen. Der Debug-Modus ist standardmäßig deaktiviert. | `null`          |
| **`webforj.entry`**                  | String  | Definiert den Einstiegspunkt der Anwendung, indem der vollständig qualifizierte Name der Klasse angegeben wird, die `webforj.App` erweitert. Wenn kein Einstiegspunkt definiert ist, wird webforJ automatisch den Klassenpfad nach Klassen durchsuchen, die `webforj.App` erweitern. Wenn mehrere Klassen gefunden werden, tritt ein Fehler auf. Wenn ein Paket mehr als einen potenziellen Einstiegspunkt enthält, ist es erforderlich, dies explizit festzulegen, um Mehrdeutigkeiten zu vermeiden, oder alternativ kann die Annotation `AppEntry` verwendet werden, um den Einstiegspunkt zur Laufzeit anzugeben. | `null`          |
| **`webforj.fileUpload.accept`**      | List    | Die zulässigen Dateitypen für Datei-Uploads. Standardmäßig sind alle Dateitypen erlaubt. Unterstützte Formate sind MIME-Typen wie `image/*`, `application/pdf`, `text/plain` oder Dateierweiterungen wie `*.txt`. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | Die maximal zulässige Dateigröße für Datei-Uploads in Bytes. Standardmäßig gibt es kein Limit. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-Endpunkt für das Symbolverzeichnis (standardmäßig von `resources/icons/` bedient). | `icons/` |
| **`webforj.license.cfg`**            | String  | Das Verzeichnis für die Lizenzkonfiguration. Standardmäßig ist es dasselbe wie das Verzeichnis der webforJ-Konfiguration, kann jedoch bei Bedarf angepasst werden. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Lizenz-Startzeitüberschreitung in Sekunden. | `null` |
| **`webforj.locale`**                 | String  | Die Locale für die Anwendung, die Sprache, regionale Einstellungen und Formate für Daten, Zeiten und Zahlen bestimmt. | `null` |
| **`webforj.quiet`**                  | Boolean | Deaktiviert das Ladebild während des Anwendungstarts. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Nur Entwicklungsumgebungen.** In einer Entwicklungsumgebung die Seite bei Fehlern, die mit der heißen Bereitstellung zusammenhängen, automatisch neu laden, jedoch nicht bei anderen Fehlerarten. Bei Verwendung der heißen Bereitstellung kann ein Fehler auftreten, wenn der Client eine Anfrage an den Server sendet, während er neu gestartet wird, während die WAR-Datei ausgetauscht wird. Da der Server wahrscheinlich bald wieder online ist, ermöglicht diese Einstellung dem Client, einen Seitenneuladevorgang automatisch zu versuchen.  | `false` |
| **`webforj.servlets[n].name`**       | String  | Servletename (verwendet Klassennamen, wenn nicht angegeben). | `null` |
| **`webforj.servlets[n].className`**  | String | Vollständig qualifizierter Klassenname des Servlets. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet-Initialisierungsparameter. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Dauer der Sitzungstimeout in Sekunden. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Eine Map von Schlüssel-Wert-Paaren, die zum Speichern von Zeichenfolgen für die Verwendung in der Anwendung verwendet wird. Nützlich zum Speichern von Anwendungsnachrichten oder Beschriftungen. Weitere Informationen zur `StringTable` finden Sie [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |

## Konfiguration von `web.xml` {#configuring-webxml}

Die Datei `web.xml` ist eine essentielle Konfigurationsdatei für Java-Webanwendungen und definiert in webforJ wichtige Einstellungen wie die Servlet-Konfiguration, URL-Muster und Willkommensseiten. Diese Datei sollte im Verzeichnis `WEB-INF` der Bereitstellungsstruktur Ihres Projekts abgelegt werden.

| Einstellung                                 | Erläuterung                                                                                                                                                                                   | Standardwert               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Legt den Anzeigenamen für die Webanwendung fest, der normalerweise vom Projektnamen abgeleitet wird. Dieser Name erscheint in den Verwaltungskonsolen der Anwendungsserver.                                                        | `${project.name}`           |
| **`<servlet>` und `<servlet-mapping>`** | Definiert den `WebforjServlet`, das Kernservlet für die Verarbeitung von webforJ-Anfragen. Dieses Servlet ist auf alle URLs (`/*`) abgebildet, was es zum Haupteinstiegspunkt für Webanfragen macht.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Gibt an, dass `WebforjServlet` beim Starten der Anwendung geladen werden soll. Wenn dies auf `1` gesetzt ist, wird das Servlet sofort geladen, was die Bearbeitung der ersten Anfrage verbessert.                | `1`                         |
