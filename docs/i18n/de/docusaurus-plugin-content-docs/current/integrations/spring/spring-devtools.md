---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 5401d3aa92e9230c4f26c827dcf83162
---
Spring DevTools bietet automatische Neustarts der Anwendung bei Änderungen im Code. webforJ DevTools fügt die automatische Aktualisierung des Browsers hinzu - wenn Spring Ihre Anwendung neu startet, aktualisiert der Browser automatisch über den LiveReload-Server von webforJ.

Verschiedene Dateitypen lösen unterschiedliche Neuladungsverhalten aus. Änderungen am Java-Code führen zu einem vollständigen Neustart von Spring und einer Browseraktualisierung. Änderungen an CSS und Bildern aktualisieren sich ohne eine Seitenneuladung, wodurch Formulardaten und Anwendungszustand erhalten bleiben.

## Verständnis von webforJ DevTools {#understanding-webforj-devtools}

webforJ erweitert Spring DevTools mit der Synchronisierung des Browsers. Wenn Spring Dateiveränderungen erkennt und neu startet, aktualisiert webforJ DevTools automatisch Ihren Browser.

### Neuladungsverhalten {#reload-behavior}

Verschiedene Dateitypen lösen unterschiedliche Neuladungsstrategien aus:

- **Java-Dateien** - Vollständige Aktualisierung der Browserseite nach dem Neustart von Spring
- **CSS-Dateien** - Stile werden ohne Seitenneuladung aktualisiert  
- **JavaScript-Dateien** - Vollständige Aktualisierung der Browserseite nach dem Neustart von Spring
- **Bilder** - Aktualisierung vor Ort ohne Seitenneuladung

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

Aktivieren Sie webforJ DevTools in Ihren Anwendungs-Eigenschaften:

```Ini title="application.properties"
# Aktivieren Sie die automatische Aktualisierung des Browsers von webforJ
webforj.devtools.livereload.enabled=true

# Sofortige Abschaltung für schnellere Neustarts aktivieren
server.shutdown=immediate
```

### Erweiterte Konfiguration {#advanced-configuration}

Konfigurieren Sie die WebSocket-Verbindung und das Neuladungsverhalten:

```Ini title="application.properties"
# WebSocket-Serverport (Standard: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-Endpunktpfad (Standard: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Heartbeat-Intervall in Millisekunden (Standard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Aktivieren Sie das heiße Neuladen für statische Ressourcen (Standard: true)
webforj.devtools.livereload.static-resources-enabled=true
```
