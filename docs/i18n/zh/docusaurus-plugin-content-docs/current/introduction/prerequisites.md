---
title: 前提条件
description: >-
  What a webforJ development environment needs, a Java 21 or higher JDK, Maven
  or Gradle, and an editor with Java support.
sidebar_position: 1
_i18n_hash: 038e0cf692852d650329b263c25aaf55
---
开始使用 webforJ 非常简单，因为只有几个前提条件。使用本指南设置您的开发环境，配备您启动和运行 webforJ 所需的基本工具。

## Java 开发工具包 (JDK) {#java-development-kit-jdk-21}

webforJ 需要 Java **21** 或更高版本。任何该版本的分发版都可以使用，因此选择您的团队已经使用的版本。

:::tip 推荐用于开发  
在 [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) 构建上进行开发。它接受 `-XX:+AllowEnhancedClassRedefinition` 选项，这使得 [hotswap 工具](/docs/configuration/deploy-reload/hotswap) 可以将类的结构、更字段或新方法的更改引入正在运行的应用程序。

在其他构建上，方法体内的编辑仍然会原地应用，而类的结构更改则需要重启。选择仅涉及您所开发的机器，它不会影响您打包或部署的位置。
:::

版本管理工具是安装 JDK 的最简单方法，也是以后在版本之间移动的最简单方法。[SDKMAN!](https://sdkman.io/) 适用于 UNIX 系统，[Jabba](https://github.com/Jabba-Team/jabba) 适用于 UNIX 系统和 Windows。在 SDKMAN! 下，`sdk install java 21.0.11-jbr` 可以为您安装 JetBrains Runtime。

如果您想自己下载构建：

- **Oracle JDK**: [Java 下载](https://www.oracle.com/java/technologies/downloads/) 页面，以及 Oracle 的 [安装指南](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html)。
- **Eclipse Temurin**: [最新版本](https://adoptium.net/temurin/releases/) 页面，以及 Adoptium 的 [安装指南](https://adoptium.net/installation/)。
- **JetBrains Runtime**: [发布](https://github.com/JetBrains/JetBrainsRuntime/releases) 页面。

运行 `java -version` 以确认路径上使用的版本。

## 构建工具 {#build-tool}

webforJ 可以使用 Maven 或 Gradle 构建。[原型](/docs/introduction/getting-started) 生成 Maven 项目，因此 Maven 是创建新应用的最快方法，现有的 Gradle 构建也以相同的方式工作。

<Tabs>
<TabItem value="maven" label="Maven">

从 [Apache Maven 下载页面](https://maven.apache.org/download.cgi) 安装 Maven，按照 Maven 的 [安装说明](https://maven.apache.org/install.html) 或 Baeldung 的 [各操作系统指南](https://www.baeldung.com/install-maven-on-windows-linux-mac)。

运行 `mvn -v` 以确认安装。

</TabItem>
<TabItem value="gradle" label="Gradle">

通过遵循 Gradle 的 [安装指南](https://gradle.org/install/) 来安装 Gradle。

运行 `gradle -v` 以确认安装。一个提供 Gradle 包装器的项目完全不需要安装，因为 `./gradlew` 会获取项目所指向的版本。

</TabItem>
</Tabs>

任一构建都通过 [webforJ 构建插件](/docs/configuration/build-plugin) 运行 webforJ 的构建时工作，使用原型创建的项目已经具备该插件。

## 编辑器 {#java-ide}

任何支持 Java 的编辑器都可以使用，因此请使用适合您工作流程的编辑器。常见选择：

- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: 开箱即用的 Java 支持和插件生态系统。
- **[Visual Studio Code](https://code.visualstudio.com/Download)**: 一个轻量级编辑器，通过扩展获得 Java 支持。
- **[Zed](https://zed.dev/download)**: 一个代码编辑器，通过扩展支持 Java，扩展下载并为您管理 Eclipse Java 语言服务器。
