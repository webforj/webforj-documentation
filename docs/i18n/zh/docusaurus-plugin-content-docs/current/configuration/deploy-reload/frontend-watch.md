---
title: Frontend watch
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild frontend sources while a webforJ app runs and refresh the browser,
  with stylesheet and image changes applied in place and script changes
  reloading the view.
_i18n_hash: efb22f8bcac71567979d21178e62ba7c
---
前端监视器在应用程序运行时重建你的 `src/main/frontend` 源，并刷新浏览器，因此前端更改会立即显示，无需手动构建或重启服务器。这是 [前端打包器](/docs/managing-resources/bundler/overview) 的开发端。

## 运行监视器 {#running-the-watch}

同时运行它和你的服务器，作为启动应用程序之前的目标。一个 Spring 项目将其设置为默认目标，因此不带参数的 `mvn` 启动两者：

```bash
mvn compile webforj:watch spring-boot:run
```

针对 [Maven Jetty 插件](/docs/configuration/deploy-reload/maven-jetty-plugin)，以相同的方式启动：

```bash
mvn compile webforj:watch jetty:run
```

有关监视器作为构建步骤的信息，请参见 [构建和测试](/docs/managing-resources/bundler/build-and-tests#the-development-watch)。

## 更改时发生的事情 {#what-happens-on-a-change}

当你保存源文件时，监视器会重建并将更改的文件发送到浏览器。浏览器所做的取决于输出，而不是你编辑的源：

- **样式表** 会在原地补丁，无需重新加载，因此表单数据和滚动位置保持不变。`.css`、`.scss`、`.sass` 或 `.less` 的编辑在此处生效。
- **图像** 会在原地交换，无需重新加载。
- **其他任何内容** 都会重新加载视图。`.ts`、`.tsx` 或 `.js` 的编辑在此处生效，因为新行为需要新页面。

如果重建修改了多个文件且所有文件都可以原地补丁，浏览器将保持不变。如果即使有一个文件不能，浏览器将重新加载，因此你永远不会看到更改半途而废。

## 经过服务器重启 {#across-a-server-restart}

Java 更改会通过 [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools)、[Maven Jetty 插件](/docs/configuration/deploy-reload/maven-jetty-plugin) 或 [JRebel](/docs/configuration/deploy-reload/jrebel) 重启服务器。监视器保持前端与之同步：

- **你的样式在重启间保持应用**，而不是闪烁未样式化。
- **应用程序准备好后页面才会重新加载**，而不是提前加载，因此你不会因过早重新加载而出现错误。服务器重启时会显示一个小指示器；手动重新加载不会显示任何内容。
- **添加或移除 `@BundleEntry` 在下次重启时生效。**

## 配置 {#configuration}

实时重载是一个 webforJ 设置，因此适用于你运行的任何服务器。Spring 应用程序从 `application.properties` 中读取这些键；独立服务器，如 [Maven Jetty 插件](/docs/configuration/deploy-reload/maven-jetty-plugin)，从 `webforj.conf` 中读取相同的键。

```ini title="application.properties"
# 在更改时刷新浏览器
webforj.devtools.livereload.enabled=true

# 在原地补丁样式表和图像而不是重新加载（默认为 true）
webforj.devtools.livereload.static-resources-enabled=true

# 浏览器连接的端口和路径（默认为：35730，/webforj-devtools-ws）
webforj.devtools.livereload.websocket-port=35730
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# 心跳间隔毫秒（默认为 30000）
webforj.devtools.livereload.heartbeat-interval=30000
```
