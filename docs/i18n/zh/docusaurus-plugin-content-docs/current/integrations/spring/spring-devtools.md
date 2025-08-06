---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 3cbff575fa5d819ab8602aa97c58d5be
---
Spring DevTools 提供代码更改时的自动应用重启。webforJ DevTools 添加了自动浏览器刷新 - 当 Spring 重新启动您的应用时，浏览器会通过 webforJ 的 LiveReload 服务器自动刷新。

不同文件类型触发不同的重新加载行为。Java 代码更改会导致整个 Spring 重启和浏览器刷新。CSS 和图像更改则无须页面重新加载，保留表单数据和应用状态。

## Understanding webforJ DevTools {#understanding-webforj-devtools}

webforJ 扩展了 Spring DevTools，提供浏览器同步。当 Spring 检测到文件更改并重新启动时，webforJ DevTools 会自动刷新您的浏览器。

### Reload behavior {#reload-behavior}

不同文件类型触发不同的重新加载策略：

- **Java 文件** - Spring 重启后完整浏览器页面重新加载
- **CSS 文件** - 样式更新无需页面重新加载  
- **JavaScript 文件** - Spring 重启后完整浏览器页面重新加载
- **图像** - 原地刷新无需页面重新加载

## Dependencies {#dependencies}

将 Spring DevTools 和 webforJ DevTools 添加到您的项目中：

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

在您的应用属性中启用 webforJ DevTools：

```Ini title="application.properties"
# Enable webforJ browser auto-reload
webforj.devtools.livereload.enabled=true

# Enable immediate shutdown for faster restarts
server.shutdown=immediate
```

### Advanced configuration {#advanced-configuration}

配置 WebSocket 连接和重新加载行为：

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
