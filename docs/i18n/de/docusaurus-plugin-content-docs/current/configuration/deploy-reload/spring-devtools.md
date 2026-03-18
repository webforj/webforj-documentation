---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 8feae38bceaabbc49e058a8d2f56f3ba
---
Spring DevTools bietet automatische Neustarts der Anwendung bei Codeänderungen. webforJ DevTools fügt eine automatische Aktualisierung des Browsers hinzu - wenn Spring Ihre Anwendung neu startet, wird der Browser automatisch über den LiveReload-Server von webforJ aktualisiert.

Verschiedene Dateitypen lösen unterschiedliche Neu ladungsverhalten aus. Änderungen im Java-Code verursachen einen vollständigen Spring-Neustart und eine Browseraktualisierung. CSS- und Bildänderungen werden ohne eine Seitenaktualisierung aktualisiert, wobei Formulardaten und Anwendungszustand erhalten bleiben.

## Verständnis von webforJ DevTools {#understanding-webforj-devtools}

webforJ erweitert Spring DevTools mit Browser-Synchronisation. Wenn Spring Date Änderungen erkennt und neu startet, aktualisiert webforJ DevTools automatisch Ihren Browser.

### Reload-Verhalten {#reload-behavior}

Verschiedene Dateitypen lösen unterschiedliche Reload-Strategien aus:

- **Java-Dateien**: Vollständige Aktualisierung der Browserseite nach dem Spring-Neustart
- **JavaScript-Dateien**: Vollständige Aktualisierung der Browserseite nach dem Spring-Neustart
- **CSS-Dateien**: Stiländerungen ohne Seitenaktualisierung  
- **Bilder**: Aktualisierung vor Ort ohne Seitenaktualisierung

## Abhängigkeiten {#dependencies}

Fügen Sie sowohl Spring DevTools als auch webforJ DevTools zu Ihrem Projekt hinzu:

```xml title="pom.xml"
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optionale>true</optionale>
</dependency>

<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-devtools</artifactId>
  <version>${webforj.version}</version>
  <optionale>true</optionale>
</dependency>
```

## Konfiguration {#configuration}

Aktivieren Sie webforJ DevTools in Ihrer `application.properties`-Datei:

```Ini title="application.properties"
# Aktivieren Sie die automatische Aktualisierung des Browsers von webforJ
webforj.devtools.livereload.enabled=true

# Sofortiges Herunterfahren für schnellere Neustarts aktivieren
server.shutdown=immediate
```

### Erweiterte Konfiguration {#advanced-configuration}

Konfigurieren Sie die WebSocket-Verbindung und das Reload-Verhalten:

```Ini title="application.properties"
# WebSocket-Serverport (Standard: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-Endpunktpfad (Standard: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Heartbeat-Intervall in Millisekunden (Standard: 30000)
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
