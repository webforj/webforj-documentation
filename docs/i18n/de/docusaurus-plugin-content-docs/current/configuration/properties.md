---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 668649d2e0f92ebc4012e0c58cd1b706
---
# Konfiguration der webforJ-Eigenschaften

Um eine webforJ-App erfolgreich bereitzustellen und auszuführen, sind einige wichtige Konfigurationsdateien erforderlich: `webforj.conf` und `web.xml`. Jede dieser Dateien steuert verschiedene Aspekte des Verhaltens der App, von Einstiegspunkten und Debug-Einstellungen bis hin zu Servlet-Zuordnungen.

## Konfiguration von `webforj.conf` {#configuring-webforjconf}

Die `webforj.conf`-Datei ist eine Kernkonfigurationsdatei in webforJ, die Einstellungen der App wie Einstiegspunkte, Debug-Modus und Client-Server-Interaktion spezifiziert. Die Datei liegt im [HOCON-Format](https://github.com/lightbend/config/blob/master/HOCON.md) vor und sollte sich im Verzeichnis `resources` befinden.

:::tip
Wenn Sie mit [Spring](../integrations/spring/overview.md) integrieren, können Sie diese `webforj.conf`-Eigenschaften in der Datei `application.properties` festlegen.
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

| Eigenschaft                             | Typ    | Erklärung                                                       | Standard                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-Header für statische Ressourcen.                        | `null` |
| **`webforj.assetsDir`**              | String  | Der Routenname, der verwendet wird, um statische Dateien bereitzustellen, während der tatsächliche Ordnername `static` bleibt. Diese Konfiguration ist hilfreich, wenn die Standardroute `static` mit einer in Ihrer App definierten Route in Konflikt steht, sodass Sie den Routenname ändern können, ohne den Ordner selbst umzubenennen.       | `null`               |
| **`webforj.assetsExt`**              | String  | Standarddateiendung für statische Dateien. | `null` |
| **`webforj.assetsIndex`**            | String  | Standarddatei, die für Verzeichnisanforderungen bereitgestellt wird (z. B. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Das Intervall, in dem der Client den Server pingt, um zu sehen, ob er noch lebt. Für die Entwicklung setzen Sie dies auf ein kürzeres Intervall, zum Beispiel `8s`, um die Verfügbarkeit des Servers schnell zu erkennen. Setzen Sie es in der Produktion auf 50 Sekunden oder mehr, um übermäßige Anfragen zu vermeiden. | `50s`           |
| **`webforj.components`**             | String  | Wenn angegeben, bestimmt der Basis-Pfad, von wo DWC-Komponenten geladen werden. Standardmäßig werden Komponenten von dem Server geladen, der die App hostet. Das Festlegen eines benutzerdefinierten Basis-Pfads ermöglicht es, Komponenten von einem alternativen Server oder CDN zu laden. Beispielsweise, um Komponenten von jsdelivr.com zu laden, setzen Sie den Basis-Pfad auf: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Es ist wichtig, dass die geladenen Komponenten mit der Version des verwendeten webforJ-Frameworks kompatibel sind; andernfalls funktioniert die App möglicherweise nicht wie erwartet. Diese Einstellung wird ignoriert, wenn eine Standard-BBj-Installation ohne die Engine verwendet wird. Für eine Standard-BBj-Installation kann die Einstellung über den `!COMPONENTS` STBL verwaltet werden. | `null`          |
| **`webforj.debug`**                  | Boolean | Aktiviert den Debug-Modus. Im Debug-Modus gibt webforJ zusätzliche Informationen in die Konsole aus und zeigt alle Ausnahmen im Browser an. Der Debug-Modus ist standardmäßig deaktiviert. | `null`          |
| **`webforj.entry`**                  | String  | Definiert den Einstiegspunkt der App, indem der vollqualifizierte Name der Klasse angegeben wird, die `webforj.App` erweitert. Wenn kein Einstiegspunkt definiert ist, durchsucht webforJ automatisch den Klassenpfad nach Klassen, die `webforj.App` erweitern. Wenn mehrere Klassen gefunden werden, tritt ein Fehler auf. Wenn ein Paket mehr als einen potenziellen Einstiegspunkt enthält, ist es erforderlich, dies explizit festzulegen, um Mehrdeutigkeiten zu vermeiden, oder alternativ kann die `AppEntry`-Annotation verwendet werden, um den Einstiegspunkt zur Laufzeit anzugeben. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Liste | Liste der unterstützten Regionen als BCP 47-Sprach-Tags (z. B. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Wenn die automatische Erkennung aktiviert ist, werden die bevorzugten Regionen des Browsers mit dieser Liste abgeglichen. Die erste Region in der Liste wird als Standardfallback verwendet. Siehe [Übersetzung](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Wenn `true`, wird die Anwendungssprache beim Start automatisch aus der bevorzugten Sprache des Browsers festgelegt. Die Sprache wird durch den Abgleich der bevorzugten Sprachen des Browsers mit der Liste `supported-locales` ermittelt. Wenn `false` oder wenn `supported-locales` leer ist, verwendet die Anwendung `webforj.locale`. Siehe [Übersetzung](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Liste    | Die zulässigen Dateitypen für Datei-Uploads. Standardmäßig sind alle Dateitypen erlaubt. Unterstützte Formate umfassen MIME-Typen wie `image/*`, `application/pdf`, `text/plain` oder Dateiendungen wie `*.txt`. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | Die maximale Dateigröße, die für Datei-Uploads erlaubt ist, in Bytes. Standardmäßig gibt es kein Limit. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-Endpunkt für das Verzeichnis der Icons (standardmäßig wird von `resources/icons/` bereitgestellt). | `icons/` |
| **`webforj.license.cfg`**            | String  | Das Verzeichnis für die Lizenzkonfiguration. Standardmäßig ist es dasselbe wie das Verzeichnis für die webforJ-Konfiguration, kann jedoch bei Bedarf angepasst werden. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Lizenzstartzeitüberschreitung in Sekunden. | `null` |
| **`webforj.locale`**                 | String  | Die Sprache für die App, die Sprache, Regionseinstellungen und Formate für Daten, Zeiten und Zahlen festlegt. | `null` |
| **`webforj.quiet`**                  | Boolean | Deaktiviert das Ladebild während des Starts der Anwendung. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Nur für Entwicklungsumgebungen.** In einer Entwicklungsumgebung wird die Seite automatisch bei Fehlern, die mit der Hot-Redeployment zusammenhängen, neu geladen, jedoch nicht für andere Fehlerarten. Bei Verwendung von Hot-Redeploy kann ein Fehler auftreten, wenn der Client eine Anfrage an den Server sendet, während dieser neu gestartet wird, während die WAR-Datei ausgetauscht wird. Da der Server wahrscheinlich bald wieder online sein wird, ermöglicht diese Einstellung dem Client, einen Seitenreload automatisch zu versuchen.  | `false` |
| **`webforj.servlets[n].name`**       | String  | Servlet-Name (verwendet den Klassennamen, wenn nicht angegeben). | `null` |
| **`webforj.servlets[n].className`**  | String | Vollqualifizierter Klassenname des Servlets. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet-Initialisierungsparameter. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Sitzungszeitüberschreitung in Sekunden. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Eine Karte von Schlüssel-Werte-Paaren, die zum Speichern von Zeichenfolgen zur Verwendung in der App verwendet wird. Nützlich zum Speichern von App-Nachrichten oder -Bezeichnungen. Weitere Informationen zur `StringTable` finden Sie [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Benutzerdefinierte MIME-Typ-Zuordnungen für Dateiendungen, wenn statische Dateien bereitgestellt werden. Ermöglicht das Überschreiben von Standard-MIME-Typen oder das Definieren von MIME-Typen für benutzerdefinierte Erweiterungen. Der Schlüssel der Karte ist die Dateiendung (ohne Punkt), und der Wert ist der MIME-Typ. | `{}`            |

## Konfiguration von `web.xml` {#configuring-webxml}

Die `web.xml`-Datei ist eine wesentliche Konfigurationsdatei für Java-Web-Apps, und in webforJ definiert sie wichtige Einstellungen wie die Servlet-Konfiguration, URL-Muster und Willkommensseiten. Diese Datei sollte sich im Verzeichnis `WEB-INF` Ihrer Bereitstellungsstruktur befinden.

| Einstellung                                 | Erklärung                                                                                                                                                                                   | Standardwert               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Setzt den angezeigten Namen für die Web-App, der typischerweise aus dem Projektnamen abgeleitet wird. Dieser Name erscheint in den Verwaltungsoberflächen der Anwendungsserver.                                                        | `${project.name}`           |
| **`<servlet>` und `<servlet-mapping>`** | Definiert das `WebforjServlet`, das zentrale Servlet zur Verarbeitung von webforJ-Anfragen. Dieses Servlet ist auf alle URLs (`/*`) abgebildet, wodurch es der Haupteinstiegspunkt für Webanfragen wird.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Gibt an, dass `WebforjServlet` beim Start der App geladen werden soll. Das Setzen auf `1` sorgt dafür, dass das Servlet sofort geladen wird, was die Verarbeitung der ersten Anfrage verbessert.                | `1`                         |
