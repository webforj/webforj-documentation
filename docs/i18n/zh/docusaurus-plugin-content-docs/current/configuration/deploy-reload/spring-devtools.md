---
title: Spring DevTools
sidebar_position: 30
description: >-
  Pair Spring DevTools with webforJ DevTools to auto-restart the app and refresh
  the browser when Java, CSS, or asset files change.
_i18n_hash: 183c4eb42a93904e03dff44faf2118e7
---
Spring DevTools 提供在代码更改时自动重启应用程序的功能。webforJ DevTools 添加了自动浏览器刷新功能 - 当 Spring 重启您的应用时，浏览器通过 webforJ 的 LiveReload 服务器自动刷新。

不同的文件类型触发不同的重载行为。Java 代码更改会导致完全的 Spring 重启和浏览器刷新。CSS 和图像更改则会在不重新加载页面的情况下更新，保留表单数据和应用状态。

:::tip 前端更改
`src/main/frontend` 下的更改由 [frontend watch](/docs/configuration/deploy-reload/frontend-watch) 处理，该功能会重建这些文件并在服务器的同时刷新浏览器。
:::

<!-- vale off -->
## 理解 webforJ DevTools {#understanding-webforj-devtools}
<!-- vale on -->

webforJ 通过浏览器同步扩展了 Spring DevTools。当 Spring 检测到文件更改并重启时，webforJ DevTools 会自动刷新您的浏览器。

### 重载行为 {#reload-behavior}

不同的文件类型触发不同的重载策略：

- **Java 文件**：在 Spring 重启后完全重新加载浏览器页面
- **JavaScript 文件**：在 Spring 重启后完全重新加载浏览器页面
- **CSS 文件**：样式在不重新加载页面的情况下更新
- **图像**：在不重新加载页面的情况下就地刷新

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

在您的 `application.properties` 文件中启用 webforJ DevTools：

```Ini title="application.properties"
# 启用 webforJ 浏览器自动重载
webforj.devtools.livereload.enabled=true

# 启用立即关闭以加快重启速度
server.shutdown=immediate
```

### 高级配置 {#advanced-configuration}

配置 WebSocket 连接和重载行为：

```Ini title="application.properties"
# WebSocket 服务器端口 (默认: 35730)
webforj.devtools.livereload.websocket-port=35730

# WebSocket 端点路径 (默认: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# 心跳间隔（单位：毫秒，默认：30000）
webforj.devtools.livereload.heartbeat-interval=30000

# 启用静态资源的热重载（默认：true）
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> 配置应用启动时的浏览器开启：

```Ini title="application.properties"
# 启用浏览器开启（默认：false）
webforj.devtools.browser.open=true

# localhost，主机名或 IP 地址（默认：localhost）
webforj.devtools.browser.host=localhost
```
