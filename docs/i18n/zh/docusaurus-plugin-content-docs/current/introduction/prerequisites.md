---
title: Prerequisites
description: >-
  Set up a webforJ development environment with JDK 21, Apache Maven, and a
  supported Java IDE such as IntelliJ, VS Code, or NetBeans.
sidebar_position: 1
_i18n_hash: d5a639b85898cdb73710fdbbd8ff8033
---
开始使用 webforJ 非常简单，因为只有几个前提条件。使用本指南设置您的开发环境，以便您能够顺利开始使用 webforJ。

## Java 开发工具包 (JDK) 21 {#java-development-kit-jdk-21}

Java 开发工具包 (JDK) 是使用 webforJ 开发的最重要要求，提供了编译、运行和管理 Java 应用程序所需的工具。为了确保与 webforJ 的兼容性，并访问 Java 生态系统的最新功能和安全更新，必须使用 Java **21**。webforJ 框架与官方 Oracle JDK 和开源 Eclipse Temurin JDK 兼容。

### JDK 安装链接: {#jdk-installation-links}
:::tip  
如果您使用的是基于 UNIX 的操作系统，建议使用 [SDKMAN!](https://sdkman.io/) 来管理您的 Java 环境。它使您能够轻松切换不同的 Java 供应商，而无需额外的麻烦。  

另外，您也可以使用 [Jabba](https://github.com/Jabba-Team/jabba)，它在基于 UNIX 的系统和 Windows 上均可工作。这是一个可靠的跨平台解决方案，用于管理 Java 版本。  
:::

- 官方 Oracle JDK 可以在 Oracle 的 [Java 下载](https://www.oracle.com/java/technologies/downloads/) 页面找到。 
  - 选择 Java 版本 **21**。
  - 点击 Linux、macOS 或 Windows 标签。
  - 点击与计算机架构相对应的链接。 
  - 查看 Oracle 的 [JDK 安装指南](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html)，获取有关安装 Oracle JDK 的完整信息。
- 开源 JDK 可以在 Adoptium 的 [Eclipse Temurin™ 最新发布](https://adoptium.net/temurin/releases/) 页面找到。 
  - 使用下拉菜单选择操作系统、架构、包类型和 JDK 版本 **21**。
  - 点击您希望下载的归档类型对应的表格中的链接。
  - 查看 Adoptium 的 [安装指南](https://adoptium.net/installation/)，获取有关安装 Eclipse Temurin JDK 的完整信息。

### 验证您的 JDK 安装 {#verify-your-jdk-installation}
安装 JDK 后，通过在终端或命令提示符下运行以下命令来验证安装：

```bash
java -version
```

如果您的 JDK 正确安装，您将看到包含 JDK 版本详细信息的输出，指示版本为 **21**。

## Apache Maven {#apache-maven}

[Apache Maven](https://maven.apache.org/index.html) 是一种构建自动化和依赖管理工具，简化了将外部库（如 webforJ）包含在您的项目中的过程。除了帮助管理依赖关系外，Maven 还可以自动化任务，如编译代码、运行测试和打包应用程序。

### Maven 安装链接 {#maven-installation-links}
- 要安装最新版本的 Maven，请访问 [Apache Maven 下载页面](https://maven.apache.org/download.cgi)。 
  - Maven 的 [安装 Apache Maven](https://maven.apache.org/install.html) 页面概述了安装过程。 
  - Baeldung 的 [如何在 Windows、Linux 和 Mac 上安装 Maven](https://www.baeldung.com/install-maven-on-windows-linux-mac) 是针对每种操作系统的更详细的安装指南。

### 验证您的 Maven 安装 {#verify-your-maven-installation}

安装 Maven 后，通过在终端或命令提示符下运行以下命令来验证安装：

```bash
mvn -v
```

如果 Maven 正确安装，输出应显示 Maven 版本、Java 版本和操作系统信息。

## Java IDE {#java-ide}

Java IDE 提供了一个全面的环境，用于编写、测试和调试代码。您可以选择许多 IDE，以选择最适合您工作流程的。用于 Java 开发的一些流行选择包括：

- **[Visual Studio Code](https://code.visualstudio.com/Download)**：一个轻量级的可扩展代码编辑器，通过插件支持 Java。
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**：因其强大的 Java 支持和丰富的插件生态系统而闻名。
- **[NetBeans](https://netbeans.apache.org/download/index.html)**：一个免费开源的 Java 和其他语言的 IDE，以易于使用和内置项目模板而闻名。
