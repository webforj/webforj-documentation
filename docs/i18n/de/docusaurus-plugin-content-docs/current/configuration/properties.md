---
title: Eigenschaftskonfiguration
sidebar_position: 1
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: fd84f72aa62a993485d33fe9a94ab0d1
---
# Konfiguration der webforJ-Eigenschaften

Um eine webforJ-Anwendung erfolgreich bereitzustellen und auszuführen, sind einige wichtige Konfigurationsdateien erforderlich: `webforj.conf` und `web.xml`. Jede dieser Dateien steuert verschiedene Aspekte des Verhaltens der Anwendung, von Einstiegspunkten und Debug-Einstellungen bis hin zu Servlet-Zuordnungen.

## Konfiguration von `webforj.conf` {#configuring-webforjconf}

Die Datei `webforj.conf` ist eine zentrale Konfigurationsdatei in webforJ, die Anwendungseinstellungen wie Einstiegspunkte, Debug-Modus und Client-Server-Interaktion angibt. Die Datei ist im [HOCON-Format](https://github.com/lightbend/config/blob/master/HOCON.md) und sollte im Verzeichnis `resources` abgelegt werden.

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
|-----------------------------------------|---------|-------------------------------------------------------------------|-------------------------|
| **`webforj.assetsCacheControl`**       | String  | Cache-Control-Header für statische Ressourcen.                    | `null`                  |
| **`webforj.assetsDir`**                | String  | Der Routenname, der verwendet wird, um statische Dateien bereitzustellen, während der tatsächliche Ordnername `static` bleibt. Diese Konfiguration ist hilfreich, wenn die Standardroute `static` mit einer in Ihrer Anwendung definierten Route kollidiert, da Sie den Routenname ändern können, ohne den Ordner selbst umzubenennen. | `null`                  |
| **`webforj.assetsExt`**                | String  | Standarddateierweiterung für statische Dateien.                  | `null`                  |
| **`webforj.assetsIndex`**              | String  | Standarddatei, die bei Verzeichnisanforderungen bereitgestellt wird (z. B. index.html). | `null`                  |
| **`webforj.clientHeartbeatRate`**      | String  | Das Intervall, in dem der Client den Server anpingt, um zu prüfen, ob er noch erreichbar ist. Für die Entwicklung sollten Sie dies auf ein kürzeres Intervall, z. B. `8s`, setzen, um die Erreichbarkeit des Servers schnell zu überprüfen. In der Produktion sollten Sie es auf 50 Sekunden oder mehr setzen, um übermäßige Anfragen zu vermeiden. | `50s`                   |
| **`webforj.components`**                | String  | Wenn angegeben, bestimmt der Basis-Pfad, wo DWC-Komponenten geladen werden. Standardmäßig werden Komponenten vom Server, der die Anwendung hostet, geladen. Wenn Sie jedoch einen benutzerdefinierten Basis-Pfad festlegen, können Komponenten von einem alternativen Server oder CDN geladen werden. Beispielsweise können Sie, um Komponenten von jsdelivr.com zu laden, den Basis-Pfad auf setzen: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Es ist wichtig, dass die geladenen Komponenten mit der Version des verwendeten webforJ-Frameworks kompatibel sind; andernfalls funktioniert die Anwendung möglicherweise nicht wie erwartet. Diese Einstellung wird ignoriert, wenn eine Standard-BBj-Installation ohne die Engine verwendet wird. Für eine Standard-BBj-Installation kann die Einstellung mit dem `!COMPONENTS` STBL verwaltet werden. | `null`                  |
| **`webforj.debug`**                    | Boolean | Aktiviert den Debug-Modus. Im Debug-Modus gibt webforJ zusätzliche Informationen in der Konsole aus und zeigt alle Ausnahmen im Browser an. Der Debug-Modus ist standardmäßig deaktiviert. | `null`                  |
| **`webforj.devtools.craftforj.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | **Nur Entwicklungsumgebungen.** Aktiviert [craftforJ](../craftforj/overview.md), die Entwicklungsumgebung, die die laufende Anwendung inspiziert, Komponenten-Eigenschaften ändert und Änderungen zurück in den Java-Quellcode schreibt. Erfordert auch, dass `webforj.debug` aktiviert ist. Keine der Eigenschaften ist allein ausreichend. | `false`                 |
| **`webforj.devtools.craftforj.hosts-allowed`**&nbsp;<DocChip chip='since' label='26.02' /> | Liste | Client-Adressen, die den Zugriff auf craftforJ über die Maschine, auf der die Anwendung läuft, hinaus erlauben. Standardmäßig kann nur ein Browser auf dieser Maschine darauf zugreifen. Ein Eintrag, der mit `*` endet, entspricht einem Präfix, und ein einzelnes `*` hebt die Einschränkung auf. Siehe [craftforJ-Sicherheit](../craftforj/security.md). | nur Loopback            |
| **`webforj.devtools.craftforj.source-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Ob craftforJ Änderungen an Eigenschaften und Zugriffsregeln in Ihren Java-Quellcode schreiben kann. | `true`                  |
| **`webforj.devtools.craftforj.stylesheet-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Ob craftforJ Themen und Stile in Ihr Anwendungsstylesheet speichern darf. | `true`                  |
| **`webforj.devtools.craftforj.ai.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Ob der [craftforJ AI-Assistent](../craftforj/ai.md) verfügbar ist. | `true`                  |
| **`webforj.devtools.craftforj.ai.freeform-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Ob der Assistent eigenen Java-Code schreiben kann, anstatt nur Eigenschaften zu ändern. Jede Änderung muss weiterhin kompiliert werden und benötigt Ihr Einverständnis. | `true`                  |
| **`webforj.devtools.project-root`**&nbsp;<DocChip chip='since' label='26.03' /> | String | Das Verzeichnis, in dem [craftforJ](../craftforj/configuration.md#project-root) nach Ihren Quellen sucht, falls es dies nicht aus der Art und Weise bestimmen kann, wie die Anwendung gestartet wurde. Ersetzt `webforj.devtools.craftforj.project-root`, das veraltet, aber weiterhin gelesen wird, wenn der Schlüssel nicht gesetzt ist. | erkannt                 |
| **`webforj.entry`**                    | String  | Definiert den Einstiegspunkt der Anwendung, indem der vollqualifizierte Name der Klasse, die `webforj.App` erweitert, angegeben wird. Wenn kein Einstiegspunkt definiert ist, durchsucht webforJ automatisch den Klassenpfad nach Klassen, die `webforj.App` erweitern. Wenn mehrere Klassen gefunden werden, tritt ein Fehler auf. Wenn ein Paket mehr als einen potenziellen Einstiegspunkt enthält, ist das explizite Setzen dieses Wertes erforderlich, um Mehrdeutigkeiten zu vermeiden, oder alternativ kann die Annotation `AppEntry` verwendet werden, um den Einstiegspunkt zur Laufzeit anzugeben. | `null`                  |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Liste | Liste unterstützter locales als BCP 47-Sprach-Tags (z. B. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Wenn die automatische Erkennung aktiviert ist, werden die bevorzugten locales des Browsers mit dieser Liste abgeglichen. Das erste locale in der Liste wird als Standardfallback verwendet. Siehe [Übersetzung](../advanced/i18n-localization.md). | `[]`                    |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Wenn `true`, wird die Anwendungs-Locale beim Starten automatisch aus der bevorzugten Sprache des Browsers gesetzt. Die Locale wird bestimmt, indem die bevorzugten locales des Browsers mit der Liste `supported-locales` abgeglichen werden. Wenn `false` oder wenn `supported-locales` leer ist, verwendet die Anwendung `webforj.locale`. Siehe [Übersetzung](../advanced/i18n-localization.md). | `false`                 |
| **`webforj.fileUpload.accept`**        | Liste    | Die zulässigen Dateitypen für Datei-Uploads. Standardmäßig sind alle Dateitypen erlaubt. Unterstützte Formate sind MIME-Typen wie `image/*`, `application/pdf`, `text/plain` oder Dateierweiterungen wie `*.txt`. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `[]`                     |
| **`webforj.fileUpload.maxSize`**       | Long    | Die maximale Dateigröße, die für Datei-Uploads erlaubt ist, in Bytes. Standardmäßig gibt es kein Limit. Bei Verwendung einer Standard-BBj-Installation wird diese Einstellung ignoriert und über `fileupload-accept.txt` verwaltet. | `null`                  |
| **`webforj.iconsDir`**                 | String  | URL-Endpunkt für das Verzeichnis von Symbolen (standardmäßig wird aus `resources/icons/` bedient). | `icons/`                |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Wenn `true`, wird ein in `<html>` gewickelter Wert so gerendert, dass sein Inhalt als HTML dargestellt wird. Wenn `false`, wird derselbe Wert buchstäblich angezeigt. | `true`                  |
| **`webforj.license.cfg`**              | String  | Das Verzeichnis für die Lizenzkonfiguration. Standardmäßig ist es dasselbe wie das Verzeichnis der webforJ-Konfiguration, kann jedoch bei Bedarf angepasst werden. | `"."`                    |
| **`webforj.license.startupTimeout`**   | Integer | Lizenz-Startzeitüberschreitung in Sekunden. | `null`                  |
| **`webforj.locale`**                   | String  | Die Locale für die Anwendung, die Sprache, Regionseinstellungen sowie Formate für Daten, Zeiten und Zahlen bestimmt. | `null`                  |
| **`webforj.quiet`**                    | Boolean | Deaktiviert das Ladebild während des Starts der Anwendung. | `false`                 |
| **`webforj.reloadOnServerError`**      | Boolean | **Nur Entwicklungsumgebungen.** In einer Entwicklungsumgebung wird die Seite bei Fehlern im Zusammenhang mit der Hot-Neuaufnahme automatisch neu geladen, jedoch nicht bei anderen Fehlertypen. Bei Verwendung von Hot-Neuaufnahme kann ein Fehler auftreten, wenn der Client eine Anfrage an den Server sendet, während dieser neu gestartet wird und die WAR-Datei getauscht wird. Da der Server wahrscheinlich bald wieder online sein wird, ermöglicht diese Einstellung dem Client, automatisch einen Seitenneuladevorgang zu versuchen. | `false`                 |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Die größte Anfrage, die die Anwendung akzeptiert, in Bytes, als Schutz gegen übergroße Anfragen, die darauf abzielen, den Serverspeicher zu erschöpfen. Setzen Sie dies auf `0`, um das Limit zu deaktivieren. | `0`                     |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Wie viele neue Anwendungssitzungen die Anwendung pro Minute starten wird, als Schutz gegen schnelle Sitzungsanfragen, die darauf abzielen, die Serverressourcen zu erschöpfen. Setzen Sie dies auf `0`, um die Ratenbegrenzung zu deaktivieren. | `0`                     |
| **`webforj.servlets[n].name`**         | String  | Servlet-Name (verwendet den Klassennamen, wenn nicht angegeben). | `null`                  |
| **`webforj.servlets[n].className`**    | String | Vollqualifizierter Klassenname des Servlets. | `null`                  |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet-Initialisierungsparameter. | `null`                  |
| **`webforj.sessionTimeout`**           | Integer | Dauer der Sitzungszeitüberschreitung in Sekunden. | `60`                    |
| **`webforj.stringTable`**              | `Map<String,String>` | Eine Zuordnung von Schlüssel-Wert-Paaren, die zum Speichern von Strings für die Verwendung in der Anwendung verwendet werden. Nützlich zum Speichern von Anwendungsnachrichten oder -beschriftungen. Weitere Informationen zur `StringTable` finden Sie [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                     |
| **`webforj.mime.extensions`**          | `Map<String,String>` | Benutzerdefinierte MIME-Typ-Zuordnungen für Dateierweiterungen beim Bereitstellen statischer Dateien. Ermöglicht es Ihnen, die Standard-MIME-Typen zu überschreiben oder MIME-Typen für benutzerdefinierte Erweiterungen zu definieren. Der Schlüssel der Zuordnung ist die Dateierweiterung (ohne Punkt), und der Wert ist der MIME-Typ. | `{}`                     |

## Konfiguration von `web.xml` {#configuring-webxml}

Die Datei `web.xml` ist eine wesentliche Konfigurationsdatei für Java-Webanwendungen, und in webforJ definiert sie wichtige Einstellungen wie die Servlet-Konfiguration, URL-Muster und Willkommensseiten. Diese Datei sollte im Verzeichnis `WEB-INF` der Bereitstellungsstruktur Ihres Projekts abgelegt werden.

| Einstellung                             | Erklärung                                                                                                                                                                                   | Standardwert               |
|-----------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------|
| **`<display-name>`**                    | Setzt den Anzeigenamen für die Webanwendung, der typischerweise vom Projektnamen abgeleitet wird. Dieser Name erscheint in den Management-Konsolen der Anwendungsserver.                    | `${project.name}`          |
| **`<servlet>` und `<servlet-mapping>`** | Definiert das `WebforjServlet`, das zentrale Servlet zum Bearbeiten von webforJ-Anfragen. Dieses Servlet ist auf alle URLs (`/*`) abgebildet, was es zum Haupteinstiegspunkt für Webanforderungen macht. | `WebforjServlet`           |
| **`<load-on-startup>`**                 | Gibt an, dass `WebforjServlet` beim Start der Anwendung geladen werden soll. Wenn dies auf `1` gesetzt wird, wird das Servlet sofort geladen, was die Bearbeitung der ersten Anfrage verbessert. | `1`                        |
