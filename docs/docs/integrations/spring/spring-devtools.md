---
title: Spring DevTools
sidebar_position: 30
---

This article explains how to configure Spring DevTools with webforJ for automatic browser refresh during development. The integration provides reload behavior - full page refresh for Java changes and hot updates for static resources like CSS and images.

## Understanding webforJ DevTools

webforJ extends Spring DevTools with browser synchronization through WebSocket connections. When Spring detects file changes, webforJ DevTools automatically refreshes your browser, eliminating manual refreshes during development.

:::tip
If you're new to Spring Boot integration with webforJ, start with the [Spring Boot setup](setup) guide to configure your project correctly.
:::

### Reload behavior

Different file types trigger different reload strategies:

- **Java files** - Full browser page reload after Spring restart
- **CSS files** - Style updates without page reload  
- **JavaScript files** - Full browser page reload after Spring restart
- **Images** - Refresh in place without page reload

## Dependencies

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

## Configuration

Enable webforJ DevTools in your app properties:

```Ini title="application.properties"
# Enable webforJ browser auto-reload
webforj.devtools.livereload.enabled=true

# Enable immediate shutdown for faster restarts
server.shutdown=immediate
```

### Advanced configuration

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
