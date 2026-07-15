---
title: Spring DevTools
sidebar_position: 30
description: >-
  Pair Spring DevTools with webforJ DevTools to auto-restart the app and refresh
  the browser when Java, CSS, or asset files change.
_i18n_hash: 183c4eb42a93904e03dff44faf2118e7
---
Spring DevTools bietet automatische Neustarts der Anwendung bei Codeänderungen. webforJ DevTools fügt eine automatische Aktualisierung des Browsers hinzu - wenn Spring Ihre Anwendung neu startet, wird der Browser automatisch über den LiveReload-Server von webforJ aktualisiert.

Verschiedene Dateitypen lösen unterschiedliche Reload-Verhalten aus. Änderungen am Java-Code führen zu einem vollständigen Neustart von Spring und einer Aktualisierung des Browsers. Änderungen an CSS und Bildern werden ohne Seitenneuladen aktualisiert und bewahren die Formulardaten und den Anwendungszustand.

:::tip Frontend-Änderungen
Änderungen im `src/main/frontend` werden vom [frontend watch](/docs/configuration/deploy-reload/frontend-watch) behandelt, welches sie neu kompiliert und den Browser zusammen mit dem Server aktualisiert.
:::

<!-- vale off -->
## Verständnis von webforJ DevTools {#understanding-webforj-devtools}
<!-- vale on -->

webforJ erweitert Spring DevTools um die Browser-Synchronisation. Wenn Spring Dateiänderungen erkennt und neu startet, aktualisiert webforJ DevTools automatisch Ihren Browser.

### Reload-Verhalten {#reload-behavior}

Verschiedene Dateitypen lösen unterschiedliche Reload-Strategien aus:

- **Java-Dateien**: Vollständige Seitenaktualisierung des Browsers nach dem Neustart von Spring
- **JavaScript-Dateien**: Vollständige Seitenaktualisierung des Browsers nach dem Neustart von Spring
- **CSS-Dateien**: Stilaktualisierungen ohne Seitenneuladen
- **Bilder**: Aktuallisierung vor Ort ohne Seitenneuladen

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
# Aktivieren Sie die automatische Aktualisierung des Browsers von webforJ
webforj.devtools.livereload.enabled=true

# Sofortige Abschaltung für schnellere Neustarts aktivieren
server.shutdown=immediate
```

### Erweiterte Konfiguration {#advanced-configuration}

Konfigurieren Sie die WebSocket-Verbindung und das Reload-Verhalten:

```Ini title="application.properties"
# WebSocket-Serverport (Standard: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-Endpunkt-Pfad (Standard: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Herzschlag-Intervall in Millisekunden (Standard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Hot Reload für statische Ressourcen aktivieren (Standard: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Konfigurieren Sie das Öffnen des Browsers beim Start der Anwendung:

```Ini title="application.properties"
# Öffnen des Browsers aktivieren (Standard: false)
webforj.devtools.browser.open=true

# localhost, Hostname oder IP-Adresse (Standard: localhost)
webforj.devtools.browser.host=localhost
```
