---
title: App Info
sidebar_position: 10
description: >-
  Read the versions, Java runtime, and project root of the app craftforJ is
  attached to.
_i18n_hash: c2bd1fec7e37fa34291d3ca88047dc04
---
应用信息报告了您的应用程序实际运行的内容，这并不总是与您的 `pom.xml` 文件中所说的内容一致。除了 webforJ 和 BBj 服务版本，它还涵盖了 Java 运行时、操作系统以及应用程序在磁盘上的根位置。

![应用信息标签](/img/craftforj/app-info/app-info-tab.png#rounded-border)

其中两个值会影响 craftforJ 的行为：

- **项目根目录** 是 craftforJ 查找您的源代码的地方。当它错误时，[写入源代码](/docs/craftforj/source-changes) 是无法工作的，因此如果报告的值与您的项目不匹配，请设置 [`project-root`](/docs/craftforj/configuration#project-root)。
- **Java 运行时** 决定助理的 [Java 更改](/docs/craftforj/ai#it-writes-java) 的验证程度，因为完全验证需要一个编译器。

:::tip 提交问题
请包括此页面上的所有内容，以及从 craftforJ 故障排除设置下载的日志。
:::
