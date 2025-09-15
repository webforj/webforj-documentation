---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 5401d3aa92e9230c4f26c827dcf83162
---
Spring DevTools bietet automatische Neustarts der Anwendung bei Codeänderungen. webforJ DevTools fügt eine automatische Browseraktualisierung hinzu - wenn Spring Ihre Anwendung neu startet, wird der Browser automatisch über den LiveReload-Server von webforJ aktualisiert.

Verschiedene Dateitypen lösen unterschiedliche Neuladeverhalten aus. Änderungen im Java-Code verursachen einen vollständigen Spring-Neustart und eine Browseraktualisierung. Änderungen an CSS und Bildern werden ohne Seitenneuladen aktualisiert und bewahren dabei Formulardaten und Anwendungszustand.

## Configuración de webforJ DevTools {#understanding-webforj-devtools}

webforJ erweitert Spring DevTools mit Browser-Synchronisierung. Wenn Spring Dateiänderungen erkennt und neu startet, aktualisiert webforJ DevTools automatisch Ihren Browser.

### Reload-Verhalten {#reload-behavior}

Verschiedene Dateitypen lösen unterschiedliche Neuladstrategien aus:

- **Java-Dateien** - Vollständiges Neuladen der Browserseite nach dem Spring-Neustart
- **CSS-Dateien** - Stilaktualisierungen ohne Seitenneuladen  
- **JavaScript-Dateien** - Vollständiges Neuladen der Browserseite nach dem Spring-Neustart
- **Bilder** - Aktualisierung vor Ort ohne Seitenneuladen

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

## Configuración {#configuration}

Aktivieren Sie webforJ DevTools in Ihren Anwendungs-Eigenschaften:

```Ini title="application.properties"
# Aktivieren Sie die automatische Browser-Aktualisierung von webforJ
webforj.devtools.livereload.enabled=true

# Aktivieren Sie sofortige Beendigung für schnellere Neustarts
server.shutdown=immediate
```

### Erweiterte Konfiguration {#advanced-configuration}

Konfigurieren Sie die WebSocket-Verbindung und das Neuladeverhalten:

```Ini title="application.properties"
# WebSocket-Serverport (Standard: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket-Endpunktpfad (Standard: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Herzschlagintervall in Millisekunden (Standard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Aktivieren Sie das Hot-Reload für statische Ressourcen (Standard: true)
webforj.devtools.livereload.static-resources-enabled=true
```
