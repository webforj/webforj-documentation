---
title: JRebel
_i18n_hash: e0a60884cfab5835f788e6f225047d2c
---
JRebel 是一个 Java 开发工具，集成了 JVM，以检测代码更改并直接在内存中替换已修改的类，让开发者能够立即看到代码更改，无需重启服务器。

当类、方法或字段发生更改时，JRebel 会动态编译并注入更新的字节码，从而消除完全重启服务器的需要。通过直接对运行中的应用应用更改，JRebel 简化了开发工作流，节省时间并保留应用状态，包括用户会话。

## Installation {#installation}

官方 JRebel 网站提供 [快速入门说明](https://www.jrebel.com/products/jrebel/learn)，帮助您在各种流行的 IDE 中快速运行该产品。按照这些说明将 JRebel 集成到您的开发环境中。

设置完成后，打开一个 webforJ 项目，并确保 `pom.xml` 文件中的 jetty `scan` 属性设置为 `0`，以禁用服务器的自动重启。完成后，使用以下命令：

```bash
mvn jetty:run
```

如果操作正确，JRebel 将在终端输出日志信息，并且对您的程序所做的更改应该能够按需反映。

:::info 查看您的更改
如果对已显示的视图或组件进行了更改，JRebel 不会强制重新加载页面，因为服务器并未重启。
:::
