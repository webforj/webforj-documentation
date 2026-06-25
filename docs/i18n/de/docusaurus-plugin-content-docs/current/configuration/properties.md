---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: c58a4908cfbde685bc0b30f6023e1df6
---
# Konfiguration der webforJ-Eigenschaften

Um eine webforJ-App erfolgreich bereitzustellen und auszuführen, sind einige wichtige Konfigurationsdateien erforderlich: `webforj.conf` und `web.xml`. Jede dieser Dateien steuert verschiedene Aspekte des Verhaltens der App, von Einstiegspunkten und Debug-Einstellungen bis hin zu Servlet-Zuordnungen.

## Konfiguration von `webforj.conf` {#configuring-webforjconf}

Die Datei `webforj.conf` ist eine Kernkonfigurationsdatei in webforJ und legt die Einstellungen der App fest, wie z.B. Einstiegspunkte, Debug-Modus und Client-Server-Interaktion. Die Datei befindet sich im [HOCON-Format](https://github.com/lightbend/config/blob/master/HOCON.md) und sollte im Verzeichnis `resources` abgelegt sein.

:::tip
Wenn Sie mit [Spring](../integrations/spring/overview.md) integrieren, können Sie diese Eigenschaften von `webforj.conf` in der Datei `application.properties` festlegen.
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
| **`webforj.assetsDir`**              | String  | Der Routenname, der zum Bereitstellen statischer Dateien verwendet wird, während der tatsächliche Ordnername `static` bleibt. Diese Konfiguration ist hilfreich, wenn die Standardroute `static` mit einer in Ihrer App definierten Route in Konflikt steht. | `null`               |
| **`webforj.assetsExt`**              | String  | Standard-Dateierweiterung für statische Dateien. | `null` |
| **`webforj.assetsIndex`**            | String  | Standarddatei, die für Verzeichnisanfragen bereitgestellt wird (z.B. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Das Intervall, in dem der Client den Server anpingt, um zu sehen, ob er noch aktiv ist. Für die Entwicklung sollte dies auf ein kürzeres Intervall, zum Beispiel `8s`, eingestellt werden, um die Verfügbarkeit des Servers schnell zu erkennen. Setzen Sie in der Produktion auf 50 Sekunden oder mehr, um übermäßige Anfragen zu vermeiden. | `50s`           |
| **`webforj.components`**             | String  | Wenn angegeben, bestimmt der Basis-Pfad, wo DWC-Komponenten geladen werden. Standardmäßig werden die Komponenten vom Server geladen, der die App hostet. Wenn ein benutzerdefinierter Basis-Pfad festgelegt wird, können die Komponenten von einem alternativen Server oder CDN geladen werden. Beispielsweise können Sie die Basis-Pfad auf setzen: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Es ist wichtig, dass die geladenen Komponenten mit der verwendeten Version des webforJ-Frameworks kompatibel sind; andernfalls funktioniert die App möglicherweise nicht wie erwartet. Diese Einstellung wird bei einer standardmäßigen BBj-Installation ohne die Engine ignoriert. Für eine standardmäßige BBj-Installation kann die Einstellung mit dem `!COMPONENTS` STBL verwaltet werden. | `null`          |
| **`webforj.debug`**                  | Boolean | Aktiviert den Debug-Modus. Im Debug-Modus druckt webforJ zusätzliche Informationen auf die Konsole und zeigt alle Ausnahmen im Browser an. Der Debug-Modus ist standardmäßig deaktiviert. | `null`          |
| **`webforj.entry`**                  | String  | Definiert den Einstiegspunkt der App, indem der vollqualifizierte Name der Klasse angegeben wird, die `webforj.App` erweitert. Wenn kein Einstiegspunkt definiert ist, scannt webforJ automatisch den Klassenpfad nach Klassen, die `webforj.App` erweitern. Wenn mehrere Klassen gefunden werden, tritt ein Fehler auf. Wenn ein Paket mehr als einen potenziellen Einstiegspunkt enthält, ist das explizite Festlegen erforderlich, um Mehrdeutigkeiten zu vermeiden, alternativ kann die `AppEntry`-Annotation verwendet werden, um den Einstiegspunkt zur Laufzeit anzugeben. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Liste | Liste unterstützter Lokalisierungen als BCP 47-Sprach-Tags (z.B. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Wenn die automatische Erkennung aktiviert ist, werden die bevorzugten Lokalisierungen des Browsers mit dieser Liste abgeglichen. Die erste Lokalisierung in der Liste wird als Standardsicherung verwendet. Siehe [Übersetzung](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Wenn `true`, wird die Anwendungs-Lokalisierung beim Startup automatisch aus der bevorzugten Sprache des Browsers festgelegt. Die Lokalisierung wird bestimmt, indem die bevorzugten Lokalisierungen des Browsers mit der Liste der `supported-locales` abgeglichen werden. Wenn `false` oder wenn `supported-locales` leer ist, verwendet die Anwendung `webforj.locale`. Siehe [Übersetzung](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Liste    | Die erlaubten Dateitypen für Datei-Uploads. Standardmäßig sind alle Dateitypen erlaubt. Unterstützte Formate umfassen MIME-Typen wie `image/*`, `application/pdf`, `text/plain` oder Dateierweiterungen wie `*.txt`. Bei Verwendung einer standardmäßigen BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | Die maximale Dateigröße, die für Datei-Uploads zulässig ist, in Bytes. Standardmäßig gibt es kein Limit. Bei Verwendung einer standardmäßigen BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-Endpunkt für das Icons-Verzeichnis (standardmäßig von `resources/icons/` bereitgestellt). | `icons/` |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Wenn `true`, wird ein in `<html>` eingeschlossener Wert als HTML gerendert. Wenn `false`, wird derselbe Wert wörtlich angezeigt. | `true` |
| **`webforj.license.cfg`**            | String  | Das Verzeichnis für die Lizenzkonfiguration. Standardmäßig ist es dasselbe wie das Verzeichnis für die webforJ-Konfiguration, dies kann jedoch angepasst werden, wenn nötig. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Lizenzstart-Timeout in Sekunden. | `null` |
| **`webforj.locale`**                 | String  | Die Lokalisierung der App, die Sprache, Regionseinstellungen und Formate für Daten, Zeiten und Zahlen bestimmt. | `null` |
| **`webforj.quiet`**                  | Boolean | Deaktiviert das Ladebild während des Anwendungsstarts. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Nur für Entwicklungsumgebungen.** In einer Entwicklungsumgebung wird die Seite bei Fehlern im Zusammenhang mit der Hot-Neuladefunktion automatisch neu geladen, jedoch nicht bei anderen Fehlertypen. Bei Verwendung der Hot-Neuladefunktion kann ein Fehler auftreten, wenn der Client eine Anfrage an den Server sendet, während dieser neu startet, während die WAR-Datei ausgetauscht wird. Da der Server wahrscheinlich bald wieder online ist, ermöglicht diese Einstellung dem Client, automatisch zu versuchen, die Seite neu zu laden. | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Größte Anfrage, die die App akzeptiert, in Bytes, als Schutzmaßnahme gegen übergroße Anfragen, die darauf abzielen, den Serverspeicher zu erschöpfen. Auf `0` setzen, um die Begrenzung zu deaktivieren. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Wie viele neue Anwendungssitzungen die App pro Minute starten wird, als Schutzmaßnahme gegen schnelle Sitzungsanfragen, die darauf abzielen, die Serverressourcen zu erschöpfen. Auf `0` setzen, um die Ratenbegrenzung zu deaktivieren. | `0` |
| **`webforj.servlets[n].name`**       | String  | Servletename (verwendet den Klassennamen, wenn nicht angegeben). | `null` |
| **`webforj.servlets[n].className`**  | String | Vollqualifizierter Klassenname des Servlets. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Initialisierungsparameter für das Servlet. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Sitzungs-Timeout-Dauer in Sekunden. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Eine Karte von Schlüssel-Wert-Paaren, die verwendet wird, um Zeichenfolgen für die Verwendung in der App zu speichern. Nützlich für die Speicherung von Nachrichten oder Bezeichnungen der App. Weitere Informationen zu `StringTable` finden Sie [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Benutzerdefinierte MIME-Typ-Zuordnungen für Dateierweiterungen beim Bereitstellen statischer Dateien. Ermöglicht es Ihnen, Standard-MIME-Typen zu überschreiben oder MIME-Typen für benutzerdefinierte Erweiterungen zu definieren. Der Schlüssel der Karte ist die Dateierweiterung (ohne den Punkt), und der Wert ist der MIME-Typ. | `{}`            |

## Konfiguration von `web.xml` {#configuring-webxml}

Die Datei `web.xml` ist eine wesentliche Konfigurationsdatei für Java-Web-Apps, und in webforJ definiert sie wichtige Einstellungen wie die Servlet-Konfiguration, URL-Muster und Willkommensseiten. Diese Datei sollte im Verzeichnis `WEB-INF` der Bereitstellungsstruktur Ihres Projekts abgelegt sein.

| Einstellung                                 | Erklärung                                                                                                                                                                                   | Standardwert               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Legt den Anzeigenamen für die Web-App fest, der typischerweise vom Projektnamen abgeleitet wird. Dieser Name erscheint in den Verwaltungsoberflächen von Anwendungsservern.                    | `${project.name}`           |
| **`<servlet>` und `<servlet-mapping>`** | Definiert den `WebforjServlet`, das Hauptservlet für die Verarbeitung von webforJ-Anfragen. Dieses Servlet ist auf alle URLs (`/*`) abgebildet, sodass es der Haupteinstiegspunkt für Webanfragen ist.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Gibt an, dass `WebforjServlet` beim Starten der App geladen werden soll. Wenn dies auf `1` gesetzt wird, wird das Servlet sofort geladen, was die Verarbeitung der ursprünglichen Anfrage verbessert.| `1`                         |
