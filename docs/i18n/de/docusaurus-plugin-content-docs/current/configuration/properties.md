---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
_i18n_hash: 2eb59302da44bcdd27d6366419bd78ad
---
# Konfigurieren der webforJ-Eigenschaften

Um eine webforJ-App erfolgreich bereitzustellen und auszuführen, sind einige wichtige Konfigurationsdateien erforderlich: `webforj.conf` und `web.xml`. Jede dieser Dateien steuert verschiedene Aspekte des Verhaltens der App, von Einstiegspunkten und Debug-Einstellungen bis hin zu Servlet-Zuordnungen.

## Konfigurieren von `webforj.conf` {#configuring-webforjconf}

Die Datei `webforj.conf` ist eine zentrale Konfigurationsdatei in webforJ, die App-Einstellungen wie Einstiegspunkte, Debug-Modus und die Interaktion zwischen Client und Server festlegt. Die Datei liegt im [HOCON-Format](https://github.com/lightbend/config/blob/master/HOCON.md) und sollte sich im Verzeichnis `resources` befinden.

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

| Eigenschaft                             | Typ    | Erklärung                                                       | Standard                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-Header für statische Ressourcen.                        | `null` |
| **`webforj.assetsDir`**              | String  | Der Routenname, der verwendet wird, um statische Dateien bereitzustellen, während der tatsächliche Ordnername `static` bleibt. Diese Konfiguration ist hilfreich, wenn die standardmäßige `static`-Route mit einer in Ihrer App definierten Route in Konflikt steht, da Sie den Routennamen ändern können, ohne den Ordner selbst umzubenennen.       | `null`               |
| **`webforj.assetsExt`**              | String  | Standard-Dateierweiterung für statische Dateien. | `null` |
| **`webforj.assetsIndex`**            | String  | Standarddatei, die für Verzeichnisanfragen bereitgestellt wird (z. B. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Das Intervall, in dem der Client den Server anpingt, um zu prüfen, ob er noch aktiv ist. Für die Entwicklung sollte dies auf ein kürzeres Intervall, z. B. `8s`, eingestellt werden, um die Serververfügbarkeit schnell zu erkennen. In der Produktion sollte es auf 50 Sekunden oder mehr gesetzt werden, um übermäßige Anfragen zu vermeiden. | `50s`           |
| **`webforj.components`**             | String  | Wenn angegeben, bestimmt der Basispfad, wo DWC-Komponenten geladen werden. Standardmäßig werden Komponenten von dem Server geladen, der die App hostet. Das Festlegen eines benutzerdefinierten Basispfads ermöglicht es, Komponenten von einem alternativen Server oder CDN zu laden. Um beispielsweise Komponenten von jsdelivr.com zu laden, setzen Sie den Basispfad auf: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Es ist wichtig, dass die geladenen Komponenten mit der Version des verwendeten webforJ-Frameworks kompatibel sind; andernfalls funktioniert die App möglicherweise nicht wie erwartet. Diese Einstellung wird ignoriert, wenn eine Standard-BBj-Installation ohne die Engine verwendet wird. Für eine Standard-BBj-Installation kann die Einstellung mit dem `!COMPONENTS` STBL verwaltet werden. | `null`          |
| **`webforj.debug`**                  | Boolean | Aktiviert den Debug-Modus. Im Debug-Modus gibt webforJ zusätzliche Informationen auf der Konsole aus und zeigt alle Ausnahmen im Browser an. Der Debug-Modus ist standardmäßig deaktiviert. | `null`          |
| **`webforj.entry`**                  | String  | Definiert den Einstiegspunkt der App, indem der vollqualifizierte Name der Klasse angegeben wird, die `webforj.App` erweitert. Wenn kein Einstiegspunkt definiert ist, durchsucht webforJ automatisch den Klassenpfad nach Klassen, die `webforj.App` erweitern. Wenn mehrere Klassen gefunden werden, tritt ein Fehler auf. Wenn ein Paket mehr als einen potenziellen Einstiegspunkt enthält, muss dieser explizit festgelegt werden, um Mehrdeutigkeiten zu vermeiden, oder alternativ kann die Annotation `AppEntry` verwendet werden, um den Einstiegspunkt zur Laufzeit anzugeben. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Liste | Liste unterstützter Lokalisierungen als BCP 47-Sprache-Tags (z. B. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Wenn die automatische Erkennung aktiviert ist, werden die bevorzugten Lokalisierungen des Browsers mit dieser Liste abgeglichen. Die erste Lokalisierung in der Liste wird als Standardfallback verwendet. Siehe [Übersetzung](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Wenn `true`, wird die Anwendungs-Lokalisierung beim Start automatisch aus der bevorzugten Sprache des Browsers eingestellt. Die Lokalisierung wird ermittelt, indem die bevorzugten Lokalisierungen des Browsers mit der Liste `supported-locales` abgeglichen werden. Wenn `false` oder wenn `supported-locales` leer ist, verwendet die Anwendung `webforj.locale`. Siehe [Übersetzung](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Liste    | Die zulässigen Dateitypen für Datei-Uploads. Standardmäßig sind alle Dateitypen erlaubt. Unterstützte Formate umfassen MIME-Typen wie `image/*`, `application/pdf`, `text/plain` oder Dateierweiterungen wie `*.txt`. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | Die maximal zulässige Dateigröße für Datei-Uploads in Byte. Standardmäßig gibt es keine Grenze. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-Endpunkt für das Icons-Verzeichnis (standardmäßig von `resources/icons/` bereitgestellt). | `icons/` |
| **`webforj.license.cfg`**            | String  | Das Verzeichnis für die Lizenzkonfiguration. Standardmäßig ist es dasselbe wie das Verzeichnis der webforJ-Konfiguration, kann aber bei Bedarf angepasst werden. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Lizenzstart-Zeitüberschreitung in Sekunden. | `null` |
| **`webforj.locale`**                 | String  | Die Lokalisierung für die App, die Sprache, Regionseinstellungen und Formate für Daten, Zeiten und Zahlen bestimmt. | `null` |
| **`webforj.quiet`**                  | Boolean | Deaktiviert das Ladesymbol während des Anwendungsstarts. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Nur in Entwicklungsumgebungen.** In einer Entwicklungsumgebung wird die Seite bei Fehlern im Zusammenhang mit der heißen Bereitstellung automatisch neu geladen, jedoch nicht bei anderen Fehlerarten. Bei Verwendung der heißen Bereitstellung kann es zu einem Fehler kommen, wenn der Client eine Anfrage an den Server sendet, während dieser neu gestartet wird, während die WAR-Datei ausgetauscht wird. Da der Server wahrscheinlich bald wieder online ist, ermöglicht diese Einstellung dem Client, einen Seitenneulad automatisch zu versuchen.  | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Die größte Anfrage, die die App akzeptiert, in Byte, als Schutzmaßnahme gegen übergroße Anfragen, die darauf abzielen, den Serverspeicher zu erschöpfen. Setzen Sie es auf `0`, um die Grenze zu deaktivieren. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Wie viele neue Anwendungssitzungen die App jede Minute starten wird, als Schutzmaßnahme gegen schnelle Sitzungscreationen, die darauf abzielen, die Serverressourcen zu erschöpfen. Setzen Sie es auf `0`, um die Ratenbeschränkung zu deaktivieren. | `0` |
| **`webforj.servlets[n].name`**       | String  | Servlet-Name (verwendet Klassennamen, wenn nicht angegeben). | `null` |
| **`webforj.servlets[n].className`**  | String | Vollständiger Klassenname des Servlets. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet-Initialisierungsparameter. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Sitzungszeitüberschreitung in Sekunden. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Eine Map aus Schlüssel-Wert-Paaren, die zur Speicherung von Strings für die Verwendung in der App dienen. Nützlich zum Speichern von App-Nachrichten oder Labels. Weitere Informationen zur `StringTable` finden Sie [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Benutzerdefinierte MIME-Typ-Zuordnungen für Dateierweiterungen beim Bereitstellen statischer Dateien. Ermöglicht es Ihnen, Standard-MIME-Typen zu überschreiben oder MIME-Typen für benutzerdefinierte Erweiterungen zu definieren. Der Schlüssel in der Map ist die Dateierweiterung (ohne Punkt), und der Wert ist der MIME-Typ. | `{}`            |

## Konfigurieren von `web.xml` {#configuring-webxml}

Die Datei `web.xml` ist eine wichtige Konfigurationsdatei für Java-Web-Apps, und in webforJ definiert sie wichtige Einstellungen wie die Servlet-Konfiguration, URL-Muster und Willkommensseiten. Diese Datei sollte sich im Verzeichnis `WEB-INF` der Bereitstellungsstruktur Ihres Projekts befinden.

| Einstellung                                 | Erklärung                                                                                                                                                                                   | Standardwert               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Legt den Anzeigenamen für die Web-App fest, der typischerweise aus dem Projektnamen abgeleitet wird. Dieser Name erscheint in den Verwaltungsoberflächen der App-Server.                                                        | `${project.name}`           |
| **`<servlet>` und `<servlet-mapping>`** | Definiert das `WebforjServlet`, das Kern-Servlet zum Umgang mit webforJ-Anfragen. Dieses Servlet ist allen URLs (`/*`) zugeordnet und stellt den Haupteinstiegspunkt für Webanfragen dar.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Gibt an, dass das `WebforjServlet` beim Start der App geladen werden soll. Wenn dies auf `1` gesetzt ist, wird das Servlet sofort geladen, was die Behandlung der ersten Anfragen verbessert.                | `1`                         |
