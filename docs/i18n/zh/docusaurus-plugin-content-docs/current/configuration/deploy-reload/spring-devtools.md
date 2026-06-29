---
title: Spring DevTools
sidebar_position: 30
description: >-
  Pair Spring DevTools with webforJ DevTools to auto-restart the app and refresh
  the browser when Java, CSS, or asset files change.
_i18n_hash: 3a552976cb9d962eb59dbfa25a10fb58
---
Spring DevTools 提供了在代码更改时自动重启应用程序的功能。webforJ DevTools 增加了自动浏览器刷新 - 当 Spring 重新启动您的应用程序时，浏览器通过 webforJ 的 LiveReload 服务器自动刷新。

不同类型的文件会触发不同的重载行为。Java 代码的更改会导致 Spring 完全重启和浏览器刷新。CSS 和图像的更改会在不重新加载页面的情况下更新，保留表单数据和应用状态。

:::tip 前端更改
位于 `src/main/frontend` 下的更改将由 [frontend watch](/docs/configuration/deploy-reload/frontend-watch) 处理，它会重新构建这些更改并刷新浏览器，同时与服务器一起工作。
:::

## 理解 webforJ DevTools {#understanding-webforj-devtools}

webforJ 扩展了 Spring DevTools 以实现浏览器同步。当 Spring 检测到文件更改并重新启动时，webforJ DevTools 会自动刷新您的浏览器。

### 重载行为 {#reload-behavior}

不同类型的文件会触发不同的重载策略：

- **Java 文件**：Spring 重启后完全重新加载浏览器页面
- **JavaScript 文件**：Spring 重启后完全重新加载浏览器页面
- **CSS 文件**：样式更新无需重新加载页面  
- **图像**：无需重新加载页面就原地刷新

## 依赖项 {#dependencies}

将 Spring DevTools 和 webforJ DevTools 都添加到您的项目中：

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

## 配置 {#configuration}

在 `application.properties` 文件中启用 webforJ DevTools：

```Ini title="application.properties"
# 启用 webforJ 浏览器自动重载
webforj.devtools.livereload.enabled=true

# 启用即时关闭以实现更快的重启
server.shutdown=immediate
```

### 高级配置 {#advanced-configuration}

配置 WebSocket 连接和重载行为：

```Ini title="application.properties"
# WebSocket 服务器端口（默认值：35730）
webforj.devtools.livereload.websocket-port=35730

# WebSocket 端点路径（默认值：/webforj-devtools-ws）
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# 心跳间隔（默认值：30000 毫秒）
webforj.devtools.livereload.heartbeat-interval=30000

# 启用静态资源的热重载（默认值：true）
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> 配置应用程序启动时的浏览器开启：

```Ini title="application.properties"
# 启用浏览器开启（默认值：false）
webforj.devtools.browser.open=true

# localhost、主机名或 IP 地址（默认值：localhost）
webforj.devtools.browser.host=localhost
```
