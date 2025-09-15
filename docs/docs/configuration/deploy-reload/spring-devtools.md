---
title: Spring DevTools
sidebar_position: 30
sidebar_class_name: updated-content
---

Spring DevTools provides automatic app restarts when code changes. webforJ DevTools adds automatic browser refresh - when Spring restarts your app, the browser refreshes automatically through webforJ's LiveReload server.

Different file types trigger different reload behavior. Java code changes cause a full Spring restart and browser refresh. CSS and image changes update without a page reload, preserving form data and app state.

<!-- vale off -->
## Understanding webforJ DevTools {#understanding-webforj-devtools}
<!-- vale on -->

webforJ extends Spring DevTools with browser synchronization. When Spring detects file changes and restarts, webforJ DevTools automatically refreshes your browser.

### Reload behavior {#reload-behavior}

Different file types trigger different reload strategies:

- **Java files**: Full browser page reload after Spring restart
- **JavaScript files**: Full browser page reload after Spring restart
- **CSS files**: Style updates without page reload  
- **Images**: Refresh in place without page reload

## Dependencies {#dependencies}

Add both Spring DevTools and webforJ DevTools to your project:

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

## Configuration {#configuration}

Enable webforJ DevTools in your `application.properties` file:

```Ini title="application.properties"
# Enable webforJ browser auto-reload
webforj.devtools.livereload.enabled=true

# Enable immediate shutdown for faster restarts
server.shutdown=immediate
```

### Advanced configuration {#advanced-configuration}

Configure WebSocket connection and reload behavior:

```Ini title="application.properties"
# WebSocket server port (default: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket endpoint path (default: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Heartbeat interval in milliseconds (default: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Enable hot reload for static resources (default: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Configure browser opening on app startup:

```Ini title="application.properties"
# Enable browser opening (default: false)
webforj.devtools.browser.open=true

# localhost, hostname, or IP address (default: localhost)
webforj.devtools.browser.host=localhost
```
