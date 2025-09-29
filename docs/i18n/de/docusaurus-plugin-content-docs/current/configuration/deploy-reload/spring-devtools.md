---
title: Spring DevTools
sidebar_position: 30
sidebar_class_name: updated-content
_i18n_hash: 147474b17005c532723aacd8af9391ea
---
Spring DevTools bietet automatische Anwendungsneustarts bei Codeänderungen. webforJ DevTools fügt eine automatische Browseraktualisierung hinzu - wenn Spring Ihre Anwendung neu startet, wird der Browser automatisch über den LiveReload-Server von webforJ aktualisiert.

Verschiedene Dateitypen lösen unterschiedliche Neuladeverhalten aus. Änderungen am Java-Code führen zu einem vollständigen Spring-Neustart und einer Browseraktualisierung. Änderungen an CSS und Bildern werden ohne Neuladen der Seite aktualisiert, wodurch Formulardaten und Anwendungsstatus beibehalten werden.

<!-- vale off -->
## Verständnis von webforJ DevTools {#understanding-webforj-devtools}
<!-- vale on -->

webforJ erweitert Spring DevTools mit Browser-Synchronisation. Wenn Spring Dateiänderungen erkennt und neu startet, aktualisiert webforJ DevTools automatisch Ihren Browser.

### Neuladeverhalten {#reload-behavior}

Verschiedene Dateitypen lösen unterschiedliche Neulademechanismen aus:

- **Java-Dateien**: Vollständiges Neuladen der Browserseite nach dem Spring-Neustart
- **JavaScript-Dateien**: Vollständiges Neuladen der Browserseite nach dem Spring-Neustart
- **CSS-Dateien**: Stilaktualisierungen ohne Neuladen der Seite  
- **Bilder**: Aktualisierung vor Ort ohne Neuladen der Seite

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

Aktivieren Sie webforJ DevTools in Ihrer Datei `application.properties`:

```Ini title="application.properties"
# Aktivieren Sie die automatische Browseraktualisierung von webforJ
webforj.devtools.livereload.enabled=true

# Sofortiges Herunterfahren für schnellere Neustarts aktivieren
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

# Hot Reload für statische Ressourcen aktivieren (Standard: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Konfigurieren Sie das Öffnen des Browsers beim Anwendungsstart:

```Ini title="application.properties"
# Öffnen des Browsers aktivieren (Standard: false)
webforj.devtools.browser.open=true

# localhost, Hostname oder IP-Adresse (Standard: localhost)
webforj.devtools.browser.host=localhost
```
