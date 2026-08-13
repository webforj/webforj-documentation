---
title: Property Configuration
sidebar_position: 1
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: 0f672146394b053aaa5d59a7e59841b2
---
# Konfiguration der webforJ-Eigenschaften

Um eine webforJ-App erfolgreich bereitzustellen und auszuführen, sind einige wichtige Konfigurationsdateien erforderlich: `webforj.conf` und `web.xml`. Jede dieser Dateien steuert verschiedene Aspekte des Verhaltens der App, von Einstiegspunkten und Debug-Einstellungen bis hin zu Servlet-Zuordnungen.

## Konfiguration von `webforj.conf` {#configuring-webforjconf}

Die `webforj.conf`-Datei ist eine zentrale Konfigurationsdatei in webforJ, die App-Einstellungen wie Einstiegspunkte, Debug-Modus und Client-Server-Interaktion angibt. Die Datei ist im [HOCON-Format](https://github.com/lightbend/config/blob/master/HOCON.md) und sollte im Verzeichnis `resources` abgelegt werden.

:::tip
Wenn Sie sich mit [Spring](../integrations/spring/overview.md) integrieren, können Sie diese `webforj.conf`-Eigenschaften in der Datei `application.properties` festlegen.
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
|-----------------------------------------|---------|-------------------------------------------------------------------|-------------------------|
| **`webforj.assetsCacheControl`**       | String  | Cache-Control-Header für statische Ressourcen.                   | `null` |
| **`webforj.assetsDir`**                | String  | Der Routenname, der verwendet wird, um statische Dateien zu bedienen, während der tatsächliche Ordnername `static` bleibt. Diese Konfiguration ist hilfreich, wenn die Standardroute `static` mit einer in Ihrer App definierten Route in Konflikt steht, sodass Sie den Routenname ändern können, ohne den Ordner selbst umzubenennen. | `null` |
| **`webforj.assetsExt`**                | String  | Standard-Dateierweiterung für statische Dateien.                  | `null` |
| **`webforj.assetsIndex`**              | String  | Standarddatei, die für Verzeichnisanfragen bereitgestellt wird (z. B. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**      | String  | Das Intervall, in dem der Client den Server anpingt, um zu prüfen, ob er noch aktiv ist. Für die Entwicklung sollten Sie dies auf ein kürzeres Intervall (z. B. `8s`) setzen, um die Serververfügbarkeit schnell zu erkennen. In der Produktion auf 50 Sekunden oder mehr setzen, um übermäßige Anfragen zu vermeiden. | `50s` |
| **`webforj.components`**                | String  | Wenn angegeben, bestimmt der Basis-Pfad, von wo DWC-Komponenten geladen werden. Standardmäßig werden Komponenten vom Server geladen, der die App hostet. Durch Setzen eines benutzerdefinierten Basis-Pfads können Komponenten von einem alternativen Server oder CDN geladen werden. Zum Beispiel, um Komponenten von jsdelivr.com zu laden, den Basis-Pfad auf: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} setzen. Es ist wichtig, dass die geladenen Komponenten mit der verwendeten Version des webforJ-Frameworks kompatibel sind; andernfalls funktioniert die App möglicherweise nicht wie erwartet. Diese Einstellung wird ignoriert, wenn eine Standard-BBj-Installation ohne die Engine verwendet wird. Bei einer Standard-BBj-Installation kann die Einstellung über die `!COMPONENTS` STBL verwaltet werden. | `null` |
| **`webforj.debug`**                    | Boolean | Aktiviert den Debug-Modus. Im Debug-Modus gibt webforJ zusätzliche Informationen in der Konsole aus und zeigt alle Ausnahmen im Browser an. Der Debug-Modus ist standardmäßig deaktiviert. | `null` |
| **`webforj.devtools.craftforj.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | **Nur in Entwicklungsumgebungen.** Schaltet [craftforJ](../craftforj/overview.md) ein, die Entwicklungsumgebung, die die laufende App inspiziert, Komponenten-Eigenschaften bearbeitet und Änderungen an den Java-Quellcode zurückschreibt. Erfordert auch, dass `webforj.debug` aktiviert ist. Keine der beiden Eigenschaften ist allein ausreichend. | `false` |
| **`webforj.devtools.craftforj.hosts-allowed`**&nbsp;<DocChip chip='since' label='26.02' /> | Liste | Client-Adressen, die es zulässig sind, craftforJ über die Maschine, auf der die App läuft, zu erreichen. Standardmäßig kann nur ein Browser auf dieser Maschine darauf zugreifen. Ein Eintrag, der mit `*` endet, entspricht einem Präfix, und ein einzelnes `*` hebt die Einschränkung auf. Siehe [craftforJ-Sicherheit](../craftforj/security.md). | nur Loopback |
| **`webforj.devtools.craftforj.project-root`**&nbsp;<DocChip chip='since' label='26.02' /> | String | Das Verzeichnis, in dem craftforJ nach Ihren Quellen sucht, für Fälle, in denen es dies nicht aus der Art und Weise erkennen kann, wie die App gestartet wurde. | erkannt |
| **`webforj.devtools.craftforj.source-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Ob craftforJ Änderungen an Eigenschaften und Zugriffsregeln in Ihre Java-Quellen schreiben darf. | `true` |
| **`webforj.devtools.craftforj.stylesheet-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Ob craftforJ Themen und Stile in Ihrem App-Stylesheet speichern darf. | `true` |
| **`webforj.devtools.craftforj.ai.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Ob der [craftforJ KI-Assistent](../craftforj/ai.md) verfügbar ist. | `true` |
| **`webforj.devtools.craftforj.ai.freeform-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Ob der Assistent eigenen Java-Code schreiben darf, anstatt nur Eigenschaften zu ändern. Jeder Edit muss dennoch kompilierbar sein und benötigt Ihre Genehmigung. | `true` |
| **`webforj.entry`**                    | String  | Definiert den Einstiegspunkt der App, indem der vollständig qualifizierte Name der Klasse angegeben wird, die `webforj.App` erweitert. Wenn kein Einstiegspunkt definiert ist, durchsucht webforJ automatisch den Klassenpfad nach Klassen, die `webforj.App` erweitern. Wenn mehrere Klassen gefunden werden, tritt ein Fehler auf. Wenn ein Paket mehr als einen potenziellen Einstiegspunkt enthält, ist das explizite Festlegen erforderlich, um Mehrdeutigkeiten zu vermeiden, oder alternativ kann die Annotation `AppEntry` verwendet werden, um den Einstiegspunkt zur Laufzeit anzugeben. | `null` |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Liste | Liste der unterstützten Lokalisierungen als BCP 47-Sprach-Tags (z. B. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Wenn die automatische Erkennung aktiviert ist, werden die bevorzugten Lokalisierungen des Browsers mit dieser Liste abgeglichen. Die erste Lokalisierung in der Liste wird als Standardfallback verwendet. Siehe [Übersetzung](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Wenn `true`, wird die Anwendungs-Lokalisierung beim Start automatisch aus der bevorzugten Sprache des Browsers festgelegt. Die Lokalisierung wird ermittelt, indem die bevorzugten Lokalisierungen des Browsers mit der Liste der `supported-locales` abgeglichen werden. Wenn `false` oder wenn `supported-locales` leer ist, verwendet die Anwendung `webforj.locale`. Siehe [Übersetzung](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**        | Liste   | Die erlaubten Dateitypen für Datei-Uploads. Standardmäßig sind alle Dateitypen erlaubt. Unterstützte Formate umfassen MIME-Typen wie `image/*`, `application/pdf`, `text/plain` oder Dateierweiterungen wie `*.txt`. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und durch `fileupload-accept.txt` verwaltet. | `[]` |
| **`webforj.fileUpload.maxSize`**       | Long    | Die maximale Dateigröße, die für Datei-Uploads erlaubt ist, in Bytes. Standardmäßig gibt es kein Limit. Bei einer Standard-BBj-Installation wird diese Einstellung ignoriert und durch `fileupload-accept.txt` verwaltet. | `null` |
| **`webforj.iconsDir`**                 | String  | URL-Endpunkt für das Icons-Verzeichnis (standardmäßig von `resources/icons/` bedient). | `icons/` |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Wenn `true`, wird ein in `<html>` gewickelter Wert so gerendert, dass sein Inhalt als HTML interpretiert wird. Wenn `false`, wird derselbe Wert wörtlich angezeigt. | `true` |
| **`webforj.license.cfg`**              | String  | Das Verzeichnis für die Lizenzkonfiguration. Standardmäßig ist es dasselbe wie das Verzeichnis der webforJ-Konfiguration, kann jedoch nach Bedarf angepasst werden. | `"."`  |
| **`webforj.license.startupTimeout`**   | Integer | Lizenz-Startup-Timeout in Sekunden. | `null` |
| **`webforj.locale`**                   | String  | Die Lokalisierung der App, die Sprache, Regionseinstellungen und Formate für Daten, Zeiten und Zahlen bestimmt. | `null` |
| **`webforj.quiet`**                    | Boolean | Deaktiviert das Ladebild während des Anwendungsstarts. | `false` |
| **`webforj.reloadOnServerError`**      | Boolean | **Nur in Entwicklungsumgebungen.** In einer Entwicklungsumgebung wird die Seite automatisch neu geladen, wenn Fehler im Zusammenhang mit der Hot-Re-Deployment auftreten, jedoch nicht bei anderen Fehlertypen. Bei Verwendung von Hot-Re-Deployment kann ein Fehler auftreten, wenn der Client eine Anfrage an den Server sendet, während dieser neu gestartet wird. Da der Server wahrscheinlich bald wieder online ist, ermöglicht diese Einstellung dem Client, automatisch einen Seitenneuladevorgang zu versuchen. | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Größte Anfrage, die die App akzeptiert, in Bytes, als Schutzmaßnahme gegen übergroße Anfragen, die darauf abzielen, den Server-Speicher zu erschöpfen. Auf `0` setzen, um das Limit zu deaktivieren. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Wie viele neue Anwendungssitzungen die App jede Minute starten wird, als Schutzmaßnahme gegen schnelle Sitzungsanfragen, die darauf abzielen, Serverressourcen zu erschöpfen. Auf `0` setzen, um die Ratenbegrenzung zu deaktivieren. | `0` |
| **`webforj.servlets[n].name`**         | String  | Servlet-Name (verwendet den Klassennamen, wenn nicht angegeben). | `null` |
| **`webforj.servlets[n].className`**    | String  | Vollständig qualifizierter Klassenname des Servlets. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet-Initialisierungsparameter. | `null` |
| **`webforj.sessionTimeout`**           | Integer | Sitzungszeitüberschreitung in Sekunden. | `60` |
| **`webforj.stringTable`**              | `Map<String,String>` | Eine Karte von Schlüssel-Wert-Paaren, die zum Speichern von Zeichenfolgen für die Verwendung in der App dient. Nützlich zum Speichern von App-Nachrichten oder Labels. Weitere Informationen zu `StringTable` finden Sie [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}` |
| **`webforj.mime.extensions`**           | `Map<String,String>` | Benutzerdefinierte MIME-Typ-Zuordnungen für Dateierweiterungen beim Bereitstellen statischer Dateien. Damit können Sie die Standard-MIME-Typen überschreiben oder MIME-Typen für benutzerdefinierte Erweiterungen definieren. Der Schlüssel der Karte ist die Dateierweiterung (ohne den Punkt), und der Wert ist der MIME-Typ. | `{}` |

## Konfiguration von `web.xml` {#configuring-webxml}

Die `web.xml`-Datei ist eine grundlegende Konfigurationsdatei für Java-Web-Apps, und in webforJ definiert sie wichtige Einstellungen wie die Servlet-Konfiguration, URL-Muster und Willkommensseiten. Diese Datei sollte im Verzeichnis `WEB-INF` Ihrer Bereitstellungsstruktur abgelegt werden.

| Einstellung                                | Erklärung                                                                                                                                                                                   | Standardwert               |
| ------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                       | Setzt den Anzeigenamen für die Web-App, der in der Regel vom Projektnamen abgeleitet wird. Dieser Name erscheint in den Verwaltungskonsolen der Anwendungsserver.                              | `${project.name}`           |
| **`<servlet>` und `<servlet-mapping>`**   | Definiert das `WebforjServlet`, das zentrale Servlet zur Handhabung von webforJ-Anfragen. Dieses Servlet wird auf alle URLs (`/*`) zugeordnet, wodurch es der Haupteinstiegspunkt für Webanfragen wird. | `WebforjServlet`            |
| **`<load-on-startup>`**                    | Gibt an, dass `WebforjServlet` geladen werden soll, wenn die App startet. Durch das Festlegen auf `1` wird das Servlet sofort geladen, was die Bearbeitung der ersten Anfrage verbessert.     | `1`                         |
