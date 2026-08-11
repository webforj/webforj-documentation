---
title: JRebel
description: >-
  Use JRebel with webforJ to hot-swap modified classes into a running Jetty
  server and skip full restarts during development.
_i18n_hash: 639c97ac6892efd7261824c13b7162da
---
JRebel 是一个 Java 开发工具，它与 JVM 集成以检测代码更改，并直接在内存中替换修改后的类，使开发人员能够立即看到代码更改，而无需重新启动服务器。

当对类、方法或字段进行更改时，JRebel 会即时编译并注入更新的字节码，从而消除完全重启服务器的需要。通过直接将更改应用于正在运行的应用程序，JRebel 简化了开发工作流程，节省了时间并保持应用状态，包括用户会话。

:::tip 前端更改
在 `src/main/frontend` 下的更改由 [frontend watch](/docs/configuration/deploy-reload/frontend-watch) 处理，它重建这些更改并在浏览器和服务器之间进行刷新。
:::

## 安装 {#installation}

官方 JRebel 网站提供了 [快速启动说明](https://www.jrebel.com/products/jrebel/learn)，以使该产品在各种流行的 IDE 中运行。按照这些说明将 JRebel 集成到您的开发环境中。

设置完成后，打开一个 webforJ 项目，并确保 `pom.xml` 文件中的 jetty `scan` 属性设置为 `0`，以禁用服务器的自动重启。完成后，使用以下命令：

```bash
mvn jetty:run
```

如果操作正确，JRebel 将在终端输出日志信息，对程序所做的更改应能按需反映出来。

:::info 查看您的更改
如果对已经显示的视图或组件进行了更改，JRebel 不会强制重新加载页面，因为服务器没有重新启动。
:::
