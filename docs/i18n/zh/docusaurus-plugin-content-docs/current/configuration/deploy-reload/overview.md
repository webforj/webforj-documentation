---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Apply code changes to a running webforJ app during development, on the server
  through hotswap or a restart, and in the browser through live reload.
_i18n_hash: 1f91b81b074c81af64ded435e068729c
---
在开发过程中，webforJ 将保存的更改应用到运行中的应用程序并更新浏览器。类更改通过 [hotswap tool](/docs/configuration/deploy-reload/hotswap) 或通过重启到达应用。实时重载会在任一操作后更新浏览器。

从 [archetype](/docs/introduction/getting-started) 创建的项目已经配置完成。对于现有项目，请按照 [Spring Boot](/docs/configuration/deploy-reload/spring-devtools) 或 [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) 进行操作。

## 每个更改的应用方式 {#how-each-change-applies}

| 更改 | 结果 | 参考 |
|---|---|---|
| Java 类，已连接 hotswap 工具 | 类在运行中的应用程序中更新。页面的受影响部分重建，应用程序状态保持不变。 | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Java 类，无 hotswap 工具 | 应用程序重新启动。当应用准备好时，浏览器重新加载。 | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| 样式表或图像 | 页面在原地应用更改，无需重新加载。 | [Settings](#settings) |
| `src/main/frontend` 下的源代码 | 监视器重建并更新浏览器。 | [Frontend watch](/docs/configuration/deploy-reload/frontend-watch) |

## 设置 {#settings}

这些设置控制开发过程中的实时重载：

| 属性 | 默认值 | 描述 |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | 在开发运行中开启实时重载。 |
| `webforj.devtools.livereload.websocket-port` | `35730` | 浏览器连接的端口。 |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | 浏览器连接的路径。 |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | 在原地应用样式表和图像更改，而不是重新加载页面。 |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | 用于连接检查的毫秒间隔，以检测重启的服务器。 |

这些键在打包的应用程序中没有效果。打包的应用程序不包含开发工具。

## 主题 {#topics}

<DocCardList className="topics-section" />
