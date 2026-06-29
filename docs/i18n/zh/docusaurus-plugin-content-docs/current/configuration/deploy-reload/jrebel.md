---
title: JRebel
description: >-
  Use JRebel with webforJ to hot-swap modified classes into a running Jetty
  server and skip full restarts during development.
_i18n_hash: dfb60fdaf7a9ffd31ee6fb920e0e8289
---
JRebel 是一款 Java 开发工具，它与 JVM 集成，以检测代码更改并直接在内存中替换修改过的类，使开发者能够立即看到代码更改，而无需重启服务器。

当对类、方法或字段进行更改时，JRebel 会在运行时编译并注入更新后的字节码，消除了完全重启服务器的需要。通过直接对运行中的应用程序应用更改，JRebel 简化了开发工作流程，节省了时间并保持应用状态，包括用户会话。

:::tip 前端更改
在 `src/main/frontend` 下的更改由 [frontend watch](/docs/configuration/deploy-reload/frontend-watch) 处理，该功能重建这些更改并在服务器的同时刷新浏览器。
:::

## 安装 {#installation}

官方 JRebel 网站提供了 [快速启动指南](https://www.jrebel.com/products/jrebel/learn)，以便在各种流行的 IDE 中启动和运行该产品。按照这些说明将 JRebel 集成到您的开发环境中。

设置完成后，打开一个 webforJ 项目，并确保 `pom.xml` 文件中的 jetty `scan` 属性设置为 `0`，以禁用服务器的自动重启。完成后，使用以下命令：

```bash
mvn jetty:run
```

如果操作正确，JRebel 将在终端输出日志信息，您对程序所做的更改应该会即时反映出来。

:::info 查看您的更改
如果对已显示的视图或组件进行了更改，JRebel 不会强制重新加载页面，因为服务器并没有重启。
:::
