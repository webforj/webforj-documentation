---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 3cbff575fa5d819ab8602aa97c58d5be
---
Spring DevTools bietet automatische Neustarts der Anwendung, wenn Änderungen am Code vorgenommen werden. webforJ DevTools fügt automatisches Browser-Refresh hinzu - wenn Spring Ihre Anwendung neu startet, wird der Browser automatisch über den LiveReload-Server von webforJ aktualisiert.

Verschiedene Dateitypen lösen unterschiedliche Reload-Verhalten aus. Änderungen im Java-Code verursachen einen vollständigen Spring-Neustart und ein Browser-Refresh. CSS- und Bildänderungen werden ohne Neuladen der Seite aktualisiert, sodass Formulardaten und Anwendungszustand erhalten bleiben.

## Verständnis von webforJ DevTools {#understanding-webforj-devtools}

webforJ erweitert Spring DevTools um die Browser-Synchronisierung. Wenn Spring Dateiänderungen erkennt und neu startet, aktualisiert webforJ DevTools automatisch Ihren Browser.

### Reload-Verhalten {#reload-behavior}

Verschiedene Dateitypen lösen unterschiedliche Reload-Strategien aus:

- **Java-Dateien** - Vollständiges Neuladen der Browserseite nach dem Spring-Neustart
- **CSS-Dateien** - Stilaktualisierungen ohne Neuladen der Seite  
- **JavaScript-Dateien** - Vollständiges Neuladen der Browserseite nach dem Spring-Neustart
- **Bilder** - Aktualisierung vor Ort ohne Neuladen der Seite

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
# Aktivieren Sie das automatische Reload des Browsers von webforJ
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

# Herzschlagintervall in Millisekunden (Standard: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Hot Reload für statische Ressourcen aktivieren (Standard: true)
webforj.devtools.livereload.static-resources-enabled=true
```
