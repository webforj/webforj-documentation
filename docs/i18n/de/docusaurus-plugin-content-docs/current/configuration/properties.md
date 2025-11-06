---
title: Property Configuration
sidebar_position: 30
sidebar_class_name: updated-content
_i18n_hash: fe000276baa9ac8b0773e5c4372d8463
---
# Konfiguration der webforJ-Eigenschaften

Um eine webforJ-Anwendung erfolgreich bereitzustellen und auszuführen, sind einige wichtige Konfigurationsdateien erforderlich: `webforj.conf` und `web.xml`. Jede dieser Dateien steuert verschiedene Aspekte des Verhaltens der Anwendung, von Einstiegspunkten und Debug-Einstellungen bis hin zu Servlet-Zuordnungen.

## Konfiguration von `webforj.conf` {#configuring-webforjconf}

Die Datei `webforj.conf` ist eine zentrale Konfigurationsdatei in webforJ, die Anwendungseinstellungen wie Einstiegspunkte, Debug-Modus und Client-Server-Interaktion festlegt. Die Datei ist im [HOCON-Format](https://github.com/lightbend/config/blob/master/HOCON.md) und sollte im Verzeichnis `resources` abgelegt werden.

:::tip
Wenn Sie sich mit [Spring](../integrations/spring/overview.md) integrieren, können Sie diese `webforj.conf`-Eigenschaften in der Datei `application.properties` festlegen.
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
|-----------------------------------------|---------|-------------------------------------------------------------------|-------------------------|
| **`webforj.assetsCacheControl`**        | String  | Cache-Control-Header für statische Ressourcen.                    | `null` |
| **`webforj.assetsDir`**                 | String  | Der Routenname, der verwendet wird, um statische Dateien zu bedienen, während der tatsächliche Ordnername `static` bleibt. Diese Konfiguration ist hilfreich, wenn die Standardroute `static` mit einer im Ihre Anwendung definierten Route in Konflikt steht, sodass Sie den Routenamen ändern können, ohne den Ordner selbst umzubenennen.       | `null`               |
| **`webforj.assetsExt`**                 | String  | Standard-Dateierweiterung für statische Dateien.                  | `null` |
| **`webforj.assetsIndex`**               | String  | Standarddatei, die für Verzeichnisanfragen bedient wird (z. B. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**       | String  | Das Intervall, in dem der Client den Server anpingt, um zu sehen, ob er noch aktiv ist. Für die Entwicklung setzen Sie dies auf ein kürzeres Intervall, zum Beispiel `8s`, um die Verfügbarkeit des Servers schnell zu erkennen. In der Produktion auf 50 Sekunden oder mehr setzen, um übermäßige Anfragen zu vermeiden. | `50s`           |
| **`webforj.components`**                | String  | Wenn angegeben, bestimmt der Basis-Pfad, von wo DWC-Komponenten geladen werden. Standardmäßig werden Komponenten vom Server geladen, der die Anwendung hostet. Das Festlegen eines benutzerdefinierten Basis-Pfads ermöglicht das Laden von Komponenten von einem alternativen Server oder CDN. Zum Beispiel, um Komponenten von jsdelivr.com zu laden, setzen Sie den Basis-Pfad auf: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Es ist wichtig, dass die geladenen Komponenten mit der verwendeten Version des webforJ-Frameworks kompatibel sind; andernfalls funktioniert die Anwendung möglicherweise nicht wie erwartet. Diese Einstellung wird ignoriert, wenn eine Standard-BBj-Installation ohne die Engine verwendet wird. Bei einer Standard-BBj-Installation kann die Einstellung über das `!COMPONENTS` STBL verwaltet werden. | `null`          |
| **`webforj.debug`**                     | Boolean | Aktiviert den Debug-Modus. Im Debug-Modus druckt webforJ zusätzliche Informationen in die Konsole und zeigt alle Ausnahmen im Browser an. Der Debug-Modus ist standardmäßig deaktiviert. | `null`          |
| **`webforj.entry`**                     | String  | Definiert den Einstiegspunkt der Anwendung, indem der vollständig qualifizierte Name der Klasse angegeben wird, die `webforj.App` erweitert. Wenn kein Einstiegspunkt definiert ist, scannt webforJ automatisch den Klassenpfad nach Klassen, die `webforj.App` erweitern. Wenn mehrere Klassen gefunden werden, tritt ein Fehler auf. Wenn ein Paket mehr als einen möglichen Einstiegspunkt enthält, ist es erforderlich, dies ausdrücklich festzulegen, um Mehrdeutigkeiten zu vermeiden, oder alternativ kann die `AppEntry`-Annotation verwendet werden, um den Einstiegspunkt zur Laufzeit anzugeben. | `null`          |
| **`webforj.fileUpload.accept`**         | List    | Die zulässigen Dateitypen für Datei-Uploads. Standardmäßig sind alle Dateitypen erlaubt. Unterstützte Formate umfassen MIME-Typen wie `image/*`, `application/pdf`, `text/plain` oder Dateierweiterungen wie `*.txt`. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `[]`            |
| **`webforj.fileUpload.maxSize`**        | Long    | Die maximale Dateigröße, die für Datei-Uploads erlaubt ist, in Bytes. Standardmäßig gibt es keine Begrenzung. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `null`          |
| **`webforj.iconsDir`**                  | String  | URL-Endpunkt für das Verzeichnis der Symbole (standardmäßig von `resources/icons/` bedient). | `icons/` |
| **`webforj.license.cfg`**               | String  | Das Verzeichnis für die Lizenzkonfiguration. Standardmäßig ist es dasselbe wie das webforJ-Konfigurationsverzeichnis, kann jedoch bei Bedarf angepasst werden. | `"."`  |
| **`webforj.license.startupTimeout`**    | Integer | Lizenzstarttimeout in Sekunden. | `null` |
| **`webforj.locale`**                    | String  | Die Locale für die Anwendung, die Sprache, Regionseinstellungen und Formate für Daten, Zeiten und Zahlen bestimmt. | `null` |
| **`webforj.quiet`**                     | Boolean | Deaktiviert das Ladebild während des Anwendungsstarts. | `false` |
| **`webforj.reloadOnServerError`**       | Boolean | **Nur Entwicklungsumgebungen.** In einer Entwicklungsumgebung die Seite bei Fehlern im Zusammenhang mit Hot-Redeployment automatisch neu laden, nicht jedoch bei anderen Fehlerarten. Bei Verwendung von Hot-Redeployment kann ein Fehler auftreten, wenn der Client eine Anfrage an den Server sendet, während dieser neu gestartet wird, während die WAR-Datei ausgewechselt wird. Da der Server wahrscheinlich bald wieder online ist, erlaubt diese Einstellung dem Client, einen automatischen Seitenneuladevorgang zu versuchen.  | `false` |
| **`webforj.servlets[n].name`**          | String  | Servlet-Name (verwendet den Klassennamen, wenn nicht angegeben). | `null` |
| **`webforj.servlets[n].className`**     | String | Vollständig qualifizierter Klassenname des Servlets. | `null` |
| **`webforj.servlets[n].config.<key>`**  | `Map<String,String>` | Initialisierungsparameter für das Servlet. | `null` |
| **`webforj.sessionTimeout`**            | Integer | Dauer des Session-Timeouts in Sekunden. | `60` |
| **`webforj.stringTable`**               | `Map<String,String>` | Eine Karte von Schlüssel-Wert-Paaren, die zur Speicherung von Zeichenfolgen für die Verwendung in der Anwendung verwendet wird. Nützlich zum Speichern von Anwendungsnachrichten oder -beschriftungen. Weitere Informationen zur `StringTable` finden Sie [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**           | `Map<String,String>` | Benutzerdefinierte MIME-Typzuordnungen für Dateierweiterungen beim Bedienen von statischen Dateien. Ermöglicht das Überschreiben der Standard-MIME-Typen oder das Definieren von MIME-Typen für benutzerdefinierte Erweiterungen. Der Schlüssel der Karte ist die Dateierweiterung (ohne Punkt), der Wert ist der MIME-Typ. | `{}`            |

## Konfiguration von `web.xml` {#configuring-webxml}

Die Datei `web.xml` ist eine wesentliche Konfigurationsdatei für Java-Webanwendungen und definiert in webforJ wichtige Einstellungen wie die Servlet-Konfiguration, URL-Muster und Startseiten. Diese Datei sollte im Verzeichnis `WEB-INF` der Bereitstellungsstruktur Ihres Projekts abgelegt werden.

| Einstellung                             | Erklärung                                                                                                                                                                                   | Standardwert               |
|-----------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| **`<display-name>`**                    | Legt den angezeigten Namen der Webanwendung fest, der typischerweise aus dem Projektnamen abgeleitet wird. Dieser Name erscheint in den Verwaltungsoberflächen der Anwendungsserver.         | `${project.name}`           |
| **`<servlet>` und `<servlet-mapping>`** | Definiert das `WebforjServlet`, das Kernservlet zur Verarbeitung von webforJ-Anfragen. Dieses Servlet ist auf alle URLs (`/*`) abgebildet, wodurch es der Haupteinstiegspunkt für Webanfragen ist. | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Gibt an, dass `WebforjServlet` beim Start der Anwendung geladen werden soll. Diese auf `1` gesetzte Einstellung bewirkt, dass das Servlet sofort geladen wird, was die initiale Anfragebehandlung verbessert. | `1`                         |
