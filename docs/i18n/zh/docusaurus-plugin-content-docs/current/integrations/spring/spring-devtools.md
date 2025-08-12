---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 5401d3aa92e9230c4f26c827dcf83162
---
Spring DevTools 提供在代码更改时自动重启应用程序的功能。webforJ DevTools 添加了自动浏览器刷新 - 当 Spring 重启您的应用程序时，浏览器会通过 webforJ 的 LiveReload 服务器自动刷新。

不同文件类型触发不同的重载行为。Java 代码更改会导致完整的 Spring 重启和浏览器刷新。CSS 和图像更改则在不重新加载页面的情况下更新，从而保留表单数据和应用程序状态。

## Understanding webforJ DevTools {#understanding-webforj-devtools}

webforJ 扩展了 Spring DevTools 的浏览器同步功能。当 Spring 检测到文件更改并进行重启时，webforJ DevTools 会自动刷新您的浏览器。

### Reload behavior {#reload-behavior}

不同文件类型触发不同的重载策略：

- **Java 文件** - 在 Spring 重启后完全重新加载浏览器页面
- **CSS 文件** - 样式更新时不重新加载页面  
- **JavaScript 文件** - 在 Spring 重启后完全重新加载浏览器页面
- **图像** - 在不重新加载页面的情况下刷新

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

在您的应用程序属性中启用 webforJ DevTools：

```Ini title="application.properties"
# 启用 webforJ 浏览器自动重载
webforj.devtools.livereload.enabled=true

# 启用即时关闭以加快重启速度
server.shutdown=immediate
```

### Advanced configuration {#advanced-configuration}

配置 WebSocket 连接和重载行为：

```Ini title="application.properties"
# WebSocket 服务器端口 (默认: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket 端点路径 (默认: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# 心跳间隔（毫秒）(默认: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# 启用静态资源的热重载 (默认: true)
webforj.devtools.livereload.static-resources-enabled=true
```
