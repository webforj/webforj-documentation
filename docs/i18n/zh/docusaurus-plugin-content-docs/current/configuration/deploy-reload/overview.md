---
title: 重新部署与实时重载
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Apply code changes to a running webforJ app during development, on the server
  through hotswap or a restart, and in the browser through live reload.
_i18n_hash: 243a55fe41bec8aeb4dfc9f70561d80c
---
在开发过程中，webforJ 将保存的更改应用于正在运行的应用程序并更新浏览器。类更改通过 [hotswap 工具](/docs/configuration/deploy-reload/hotswap) 或通过重启应用到达应用程序。无论使用哪种方式，实时重载都会在之后更新浏览器。

从 [archetype](/docs/introduction/getting-started) 创建的项目是预先配置的。对于现有项目，请遵循 [Spring Boot](/docs/configuration/deploy-reload/spring-devtools)、[Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) 或 [BBjServices](/docs/configuration/deploy-reload/bbjservices)。

## 每个更改的应用方式 {#how-each-change-applies}

| 更改 | 结果 | 参考 |
|---|---|---|
| Java 类，附加 hotswap 工具 | 类在运行的应用中更新。受影响的页面部分重建，应用状态保持不变。 | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Java 类，没有 hotswap 工具 | 应用重启。当应用准备好时，浏览器重新加载。 | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools)、[Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| Java 类在 BBjServices 中，没有 hotswap 工具 | 更改在监视重新启动之前不会应用。 | [BBjServices](/docs/configuration/deploy-reload/bbjservices) |
| 样式表或图像 | 页面即时应用，而无需重新加载。 | [设置](#settings) |
| `src/main/frontend` 下的源代码 | 监视重建它并更新浏览器。 | [前端监视](/docs/configuration/deploy-reload/frontend-watch) |

## 设置 {#settings}

这些设置控制开发过程中的实时重载：

| 属性 | 默认值 | 描述 |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | 开启开发运行的实时重载。 |
| `webforj.devtools.livereload.websocket-port` | `35730` | 浏览器连接的端口。 |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | 浏览器连接的路径。 |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | 即时应用样式表和图像更改，而不是重新加载页面。 |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | 检测重启服务器的连接检查的间隔（以毫秒为单位）。 |

这些键在打包应用中没有效果。打包应用不包含开发工具。

## 主题 {#topics}

<DocCardList className="topics-section" />
