---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 3e14c2d47a7963fe901feda071971419
---
# Konfigurieren der webforJ-Eigenschaften

Um eine webforJ-App erfolgreich bereitzustellen und auszuführen, sind einige wichtige Konfigurationsdateien erforderlich: `webforj.conf` und `web.xml`. Jede dieser Dateien steuert unterschiedliche Aspekte des Verhaltens der App, von Einstiegspunkten und Debug-Einstellungen bis hin zu Servlet-Zuordnungen.

## Konfigurieren von `webforj.conf` {#configuring-webforjconf}

Die Datei `webforj.conf` ist eine zentrale Konfigurationsdatei in webforJ, die App-Einstellungen wie Einstiegspunkte, den Debug-Modus und die Client-Server-Interaktion spezifiziert. Die Datei ist im [HOCON-Format](https://github.com/lightbend/config/blob/master/HOCON.md) und sollte sich im Verzeichnis `resources` befinden.

:::tip
Wenn Sie mit dem [Spring Framework](../integrations/spring/overview.md) integrieren, können Sie diese Eigenschaften von `webforj.conf` in der Datei `application.properties` festlegen.
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

| Eigenschaft                             | Typ    | Erklärung                                                       | Standardwert            |
|----------------------------------------|---------|----------------------------------------------------------------|-------------------------|
| **`webforj.assetsCacheControl`**      | String  | Cache-Control-Header für statische Ressourcen.                 | `null`                  |
| **`webforj.assetsDir`**               | String  | Der Routenname, der zum Bereitstellen statischer Dateien verwendet wird, während der tatsächliche Ordnername `static` bleibt. Diese Konfiguration ist hilfreich, wenn die standardmäßige Route `static` mit einer in Ihrer App definierten Route in Konflikt steht, sodass Sie den Routenname ändern können, ohne den Ordner selbst umzubenennen. | `null`                  |
| **`webforj.assetsExt`**               | String  | Standarddateierweiterung für statische Dateien.                | `null`                  |
| **`webforj.assetsIndex`**             | String  | Standarddatei, die für Verzeichnisanfragen bereitgestellt wird (z. B. index.html). | `null`                  |
| **`webforj.clientHeartbeatRate`**     | String  | Das Intervall, in dem der Client den Server anpingt, um zu überprüfen, ob er noch aktiv ist. Für die Entwicklung setzen Sie dies auf ein kürzeres Intervall, z. B. `8s`, um die Serververfügbarkeit schnell zu erkennen. In der Produktion auf 50 Sekunden oder mehr setzen, um übermäßige Anfragen zu vermeiden. | `50s`                   |
| **`webforj.components`**              | String  | Wenn angegeben, bestimmt der Basispfad, von wo DWC-Komponenten geladen werden. Standardmäßig werden Komponenten vom Server geladen, der die App hostet. Das Festlegen eines benutzerdefinierten Basispfads ermöglicht es, Komponenten von einem alternativen Server oder CDN zu laden. Um beispielsweise Komponenten von jsdelivr.com zu laden, setzen Sie den Basispfad auf: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Es ist wichtig, dass die geladenen Komponenten mit der verwendeten Version des webforJ-Frameworks kompatibel sind; andernfalls funktioniert die App möglicherweise nicht wie erwartet. Diese Einstellung wird ignoriert, wenn eine standardmäßige BBj-Installation ohne die Engine verwendet wird. Bei einer standardmäßigen BBj-Installation kann die Einstellung mit der `!COMPONENTS` STBL verwaltet werden. | `null`                  |
| **`webforj.debug`**                   | Boolean | Aktiviert den Debug-Modus. Im Debug-Modus gibt webforJ zusätzliche Informationen in der Konsole aus und zeigt alle Ausnahmen im Browser an. Der Debug-Modus ist standardmäßig deaktiviert. | `null`                  |
| **`webforj.entry`**                   | String  | Definiert den Einstiegspunkt der App, indem der vollständig qualifizierte Name der Klasse angegeben wird, die `webforj.App` erweitert. Wenn kein Einstiegspunkt definiert ist, durchsucht webforJ automatisch den Klassenpfad nach Klassen, die `webforj.App` erweitern. Wenn mehrere Klassen gefunden werden, tritt ein Fehler auf. Wenn ein Paket mehr als einen potenziellen Einstiegspunkt enthält, muss dies ausdrücklich festgelegt werden, um Mehrdeutigkeiten zu vermeiden, oder alternativ kann die Annotation `AppEntry` zur Laufzeit zur Spezifizierung des Einstiegspunkts verwendet werden. | `null`                  |
| **`webforj.fileUpload.accept`**       | List    | Die zulässigen Dateitypen für Datei-Uploads. Standardmäßig sind alle Dateitypen erlaubt. Unterstützte Formate umfassen MIME-Typen wie `image/*`, `application/pdf`, `text/plain` oder Dateierweiterungen wie `*.txt`. Bei Verwendung einer standardmäßigen BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `[]`                    |
| **`webforj.fileUpload.maxSize`**      | Long    | Die maximal zulässige Dateigröße für Datei-Uploads in Byte. Standardmäßig gibt es kein Limit. Bei Verwendung einer standardmäßigen BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `null`                  |
| **`webforj.iconsDir`**                | String  | URL-Endpunkt für das Verzeichnis von Icons (Standard dient von `resources/icons/`). | `icons/`                |
| **`webforj.license.cfg`**             | String  | Das Verzeichnis für die Lizenzkonfiguration. Standardmäßig ist es dasselbe wie das Konfigurationsverzeichnis von webforJ, kann jedoch bei Bedarf angepasst werden. | `"."`                   |
| **`webforj.license.startupTimeout`**  | Integer | Lizenzstart-Timeout in Sekunden.                                   | `null`                  |
| **`webforj.locale`**                  | String  | Die Locale für die App, die Sprache, regionale Einstellungen und Formate für Daten, Zeiten und Zahlen bestimmt. | `null`                  |
| **`webforj.quiet`**                   | Boolean | Deaktiviert das Ladebild während des Anwendungsstarts. | `false`                 |
| **`webforj.reloadOnServerError`**     | Boolean | **Nur in Entwicklungsumgebungen.** In einer Entwicklungsumgebung die Seite bei Fehlern, die mit der heißen Bereitstellung zusammenhängen, automatisch neu laden, jedoch nicht bei anderen Fehlertypen. Bei Verwendung der heißen Bereitstellung kann ein Fehler auftreten, wenn der Client eine Anfrage an den Server sendet, während dieser neu gestartet wird, während die WAR-Datei ausgetauscht wird. Da der Server wahrscheinlich bald wieder online ist, ermöglicht diese Einstellung dem Client, zu versuchen, eine Seite automatisch neu zu laden. | `false`                 |
| **`webforj.servlets[n].name`**        | String  | Servlet-Name (verwendet den Klassennamen, wenn nicht angegeben). | `null`                  |
| **`webforj.servlets[n].className`**   | String  | Vollständig qualifizierter Klassenname des Servlets. | `null`                  |
| **`webforj.servlets[n].config.<key>`**| `Map<String,String>` | Servlet-Initialisierungsparameter. | `null`                  |
| **`webforj.sessionTimeout`**          | Integer | Sitzungs-Timeout-Dauer in Sekunden. | `60`                    |
| **`webforj.stringTable`**             | `Map<String,String>` | Eine Karte von Schlüssel-Wert-Paaren zur Speicherung von Strings für die Verwendung in der App. Nützlich zum Speichern von Nachrichten oder Labels der App. Weitere Informationen zur `StringTable` finden Sie [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                    |

## Konfigurieren von `web.xml` {#configuring-webxml}

Die Datei web.xml ist eine wichtige Konfigurationsdatei für Java-Web-Apps und definiert in webforJ wichtige Einstellungen wie die Servlet-Konfiguration, URL-Muster und Willkommensseiten. Diese Datei sollte sich im Verzeichnis `WEB-INF` der Bereitstellungsstruktur Ihres Projekts befinden.

| Einstellung                                | Erklärung                                                                                                                                                                                      | Standardwert              |
|-------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------|
| **`<display-name>`**                       | Legt den Anzeigenamen für die Web-App fest, der typischerweise aus dem Projektnamen abgeleitet wird. Dieser Name erscheint in den Verwaltungsoberflächen der Anwendungsserver.                  | `${project.name}`          |
| **`<servlet>` und `<servlet-mapping>`**   | Definiert das `WebforjServlet`, das Hauptservlet zur Bearbeitung von Webforj-Anfragen. Dieses Servlet ist auf alle URLs (`/*`) abgebildet, wodurch es den Haupt-Einstiegspunkt für Webanfragen darstellt. | `WebforjServlet`           |
| **`<load-on-startup>`**                    | Legt fest, dass `WebforjServlet` beim Start der App geladen werden soll. Das Setzen auf `1` bewirkt, dass das Servlet sofort geladen wird, was die Bearbeitung der ersten Anfrage verbessert. | `1`                        |
