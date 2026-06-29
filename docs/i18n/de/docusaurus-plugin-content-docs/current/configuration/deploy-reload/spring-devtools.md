---
title: Spring DevTools
sidebar_position: 30
description: >-
  Pair Spring DevTools with webforJ DevTools to auto-restart the app and refresh
  the browser when Java, CSS, or asset files change.
_i18n_hash: 3a552976cb9d962eb59dbfa25a10fb58
---
Spring DevTools bietet automatische Neustarts der Anwendung bei Codeänderungen. webforJ DevTools fügt eine automatische Browseraktualisierung hinzu - wenn Spring Ihre Anwendung neu startet, wird der Browser automatisch über den LiveReload-Server von webforJ aktualisiert.

Verschiedene Dateitypen lösen unterschiedliche Aktualisierungsverhalten aus. Änderungen im Java-Code verursachen einen vollständigen Neustart von Spring und eine Browseraktualisierung. CSS- und Bildänderungen werden ohne eine Seitenaktualisierung aktualisiert, wobei Formulardaten und Anwendungsstatus erhalten bleiben.

:::tip Frontend-Änderungen
Änderungen unter `src/main/frontend` werden durch das [frontend watch](/docs/configuration/deploy-reload/frontend-watch) verarbeitet, das diese neu aufbaut und den Browser neben dem Server aktualisiert.
:::

## Verständnis von webforJ DevTools {#understanding-webforj-devtools}

webforJ erweitert Spring DevTools um die Browser-Synchronisierung. Wenn Spring Dateiänderungen erkennt und neu startet, aktualisiert webforJ DevTools automatisch Ihren Browser.

### Aktualisierungsverhalten {#reload-behavior}

Verschiedene Dateitypen lösen unterschiedliche Aktualisierungsstrategien aus:

- **Java-Dateien**: Vollständige Aktualisierung der Browserseite nach dem Neustart von Spring
- **JavaScript-Dateien**: Vollständige Aktualisierung der Browserseite nach dem Neustart von Spring
- **CSS-Dateien**: Stilaktualisierungen ohne Seitenaktualisierung  
- **Bilder**: Aktualisierung an Ort und Stelle ohne Seitenaktualisierung

## Abhängigkeiten {#dependencies}

Fügen Sie sowohl Spring DevTools als auch webforJ DevTools zu Ihrem Projekt hinzu:

```xml title="pom.xml"
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optional>true</optional>
</dependency>

<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-devtools</artifactId>
  <version>${webforj.version}</version>
  <optional>true</optional>
</dependency>
```

## Konfiguration {#configuration}

Aktivieren Sie webforJ DevTools in Ihrer `application.properties`-Datei:

```Ini title="application.properties"
# Aktivieren Sie die automatische Browser-Aktualisierung von webforJ
webforj.devtools.livereload.enabled=true

# Sofortige Abschaltung für schnellere Neustarts aktivieren
server.shutdown=immediate
```

### Erweiterte Konfiguration {#advanced-configuration}

Konfigurieren Sie die WebSocket-Verbindung und das Aktualisierungsverhalten:

```Ini title="application.properties"
# WebSocket-Serverport (Standard: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-Endpunkt-Pfad (Standard: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Herzschlagintervall in Millisekunden (Standard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Hot Reload für statische Ressourcen aktivieren (Standard: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Konfigurieren Sie das Öffnen des Browsers beim Start der Anwendung:

```Ini title="application.properties"
# Aktivieren Sie das Öffnen des Browsers (Standard: false)
webforj.devtools.browser.open=true

# localhost, Hostname oder IP-Adresse (Standard: localhost)
webforj.devtools.browser.host=localhost
```
