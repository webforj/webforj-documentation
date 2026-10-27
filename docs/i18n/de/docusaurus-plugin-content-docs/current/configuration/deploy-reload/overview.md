---
title: Redeployment und Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Apply code changes to a running webforJ app during development, on the server
  through hotswap or a restart, and in the browser through live reload.
_i18n_hash: 243a55fe41bec8aeb4dfc9f70561d80c
---
Während der Entwicklung wendet webforJ gespeicherte Änderungen auf die laufende App an und aktualisiert den Browser. Klassenänderungen gelangen über ein [Hotswap-Tool](/docs/configuration/deploy-reload/hotswap) oder durch einen Neustart zur App. Das Live-Reload aktualisiert den Browser nach jedem von beidem.

Projekte, die aus einem [Archetyp](/docs/introduction/getting-started) erstellt wurden, sind vorkonfiguriert. Für ein bestehendes Projekt folgen Sie [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) oder [BBjServices](/docs/configuration/deploy-reload/bbjservices).

## Wie jede Änderung angewendet wird {#how-each-change-applies}

| Änderung | Ergebnis | Referenz |
|---|---|---|
| Java-Klasse, Hotswap-Tool angeschlossen | Die Klasse wird in der laufenden App aktualisiert. Der betroffene Teil der Seite wird neu gebaut und der Anwendungszustand bleibt erhalten. | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Java-Klasse, kein Hotswap-Tool | Die App wird neu gestartet. Der Browser wird neu geladen, wenn die App bereit ist. | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| Java-Klasse in BBjServices, kein Hotswap-Tool | Die Änderung wird erst wirksam, wenn die Überwachung erneut startet. | [BBjServices](/docs/configuration/deploy-reload/bbjservices) |
| Stylesheet oder Image | Die Seite wendet es direkt an, ohne neu zu laden. | [Einstellungen](#settings) |
| Quelle unter `src/main/frontend` | Die Überwachung baut es neu und aktualisiert den Browser. | [Frontend-Überwachung](/docs/configuration/deploy-reload/frontend-watch) |

## Einstellungen {#settings}

Diese Einstellungen steuern das Live-Reload während der Entwicklung:

| Eigenschaft | Standard | Beschreibung |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | Aktiviert das Live-Reload für Entwicklungsdurchläufe. |
| `webforj.devtools.livereload.websocket-port` | `35730` | Port für die Browserverbindung. |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | Pfad für die Browserverbindung. |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | Wendet Änderungen an Stylesheets und Bildern direkt an, anstatt die Seite neu zu laden. |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | Intervall in Millisekunden für die Verbindungsüberprüfungen, die einen Neustart des Servers erkennen. |

Die Schlüssel haben keine Auswirkung in einer verpackten App. Verpackte Apps enthalten keine Entwicklungstools.

## Themen {#topics}

<DocCardList className="topics-section" />
