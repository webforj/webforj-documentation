---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Apply code changes to a running webforJ app during development, on the server
  through hotswap or a restart, and in the browser through live reload.
_i18n_hash: 1f91b81b074c81af64ded435e068729c
---
Während der Entwicklung wendet webforJ gespeicherte Änderungen auf die laufende App an und aktualisiert den Browser. Klassenänderungen erreichen die App entweder über ein [Hotswap-Tool](/docs/configuration/deploy-reload/hotswap) oder durch einen Neustart. Live-Reload aktualisiert den Browser nach beiden Vorgängen.

Projekte, die aus einem [Archetyp](/docs/introduction/getting-started) erstellt wurden, sind vorkonfiguriert. Für ein bestehendes Projekt befolgen Sie [Spring Boot](/docs/configuration/deploy-reload/spring-devtools) oder [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin).

## Wie jede Änderung angewendet wird {#how-each-change-applies}

| Änderung | Ergebnis | Verweis |
|---|---|---|
| Java-Klasse, Hotswap-Tool angehängt | Die Klasse wird in der laufenden App aktualisiert. Der betroffene Teile der Seite wird neu aufgebaut, und der App-Zustand bleibt erhalten. | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Java-Klasse, kein Hotswap-Tool | Die App wird neu gestartet. Der Browser lädt neu, wenn die App bereit ist. | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| Stylesheet oder Bild | Die Seite wendet es direkt an, ohne einen Reload. | [Einstellungen](#settings) |
| Quellcode unter `src/main/frontend` | Die Watch baut es neu und aktualisiert den Browser. | [Frontend Watch](/docs/configuration/deploy-reload/frontend-watch) |

## Einstellungen {#settings}

Diese Einstellungen steuern das Live-Reload während der Entwicklung:

| Eigenschaft | Standard | Beschreibung |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | Aktiviert Live-Reload für Entwicklungsdurchläufe. |
| `webforj.devtools.livereload.websocket-port` | `35730` | Port für die Browserverbindung. |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | Pfad für die Browserverbindung. |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | Wendet Stylesheet- und Bildänderungen direkt an, anstatt die Seite neu zu laden. |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | Intervall in Millisekunden für die Verbindungsprüfungen, die einen neu startenden Server erkennen. |

Die Schlüssel haben keine Auswirkungen in einer verpackten App. Verpackte Apps enthalten keine Entwicklungstools.

## Themen {#topics}

<DocCardList className="topics-section" />
