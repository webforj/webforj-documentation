---
title: Prerequisites
description: >-
  Set up a webforJ development environment with JDK 21, Apache Maven, and a
  supported Java IDE such as IntelliJ, VS Code, or NetBeans.
sidebar_position: 1
_i18n_hash: 03fdfcdc58e52eabd51a8f9dbda568e6
---
开始使用webforJ很简单，因为只有几个先决条件。使用本指南设置开发环境，准备好您需要的必备工具，以便顺利开始使用webforJ。

## Java 开发工具包 (JDK) 21 {#java-development-kit-jdk-21}

Java 开发工具包 (JDK) 是使用webforJ开发的最重要要求，它提供了编译、运行和管理Java应用程序所需的必要工具。为了确保与webforJ的兼容性并访问Java生态系统的最新功能和安全更新，必须使用Java **21**。webforJ框架与官方Oracle JDK和开源Eclipse Temurin JDK兼容。
### JDK 安装链接: {#jdk-installation-links}
:::tip
如果您使用的是基于UNIX的操作系统，建议使用 [SDKMAN!](https://sdkman.io/) 来管理Java环境。它可以让您轻松切换不同的Java供应商，而不会有额外的麻烦。

或者，您可以使用 [Jabba](https://github.com/Jabba-Team/jabba)，它在基于UNIX的系统和Windows上都能工作。这是一个可靠的跨平台Java版本管理解决方案。
:::

- 官方Oracle JDK可以在Oracle的 [Java下载](https://www.oracle.com/java/technologies/downloads/) 页面上找到。
  - 选择Java版本 **21**。
  - 点击Linux、macOS或Windows的选项卡。
  - 点击与您计算机架构对应的链接。
  - 查看Oracle的 [JDK安装指南](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html)，以获取有关安装Oracle JDK的完整信息。
- 开源JDK可以在Adoptium的 [Eclipse Temurin™ 最新发布](https://adoptium.net/temurin/releases/) 页面上找到。
  - 使用下拉菜单选择操作系统、架构、包类型和JDK版本 **21**。
  - 点击表格中您希望下载的存档类型的链接。
  - 查看Adoptium的 [安装指南](https://adoptium.net/installation/)，以获取有关安装Eclipse Temurin JDK的完整信息。

### 验证您的JDK安装 {#verify-your-jdk-installation}
安装JDK后，通过在终端或命令提示符中运行以下命令来验证安装：

```bash
java -version
```

如果您的JDK安装正确，您将看到包含JDK版本详细信息的输出，指示版本 **21**。
## Apache Maven {#apache-maven}

[Apache Maven](https://maven.apache.org/index.html) 是一款构建自动化和依赖管理工具，简化了将外部库如webforJ包含到项目中的过程。除了帮助依赖管理外，Maven还可以自动执行编译代码、运行测试和打包应用程序等任务。

### Maven 安装链接 {#maven-installation-links}
- 要安装最新版本的Maven，请访问 [Apache Maven下载页面](https://maven.apache.org/download.cgi)。
  - Maven的 [安装Apache Maven](https://maven.apache.org/install.html) 页面概述了安装过程。
  - Baeldung的 [如何在Windows、Linux和Mac上安装Maven](https://www.baeldung.com/install-maven-on-windows-linux-mac) 是每个操作系统的更深入的安装指南。

### 验证您的Maven安装 {#verify-your-maven-installation}

安装Maven后，通过在终端或命令提示符中运行以下命令来验证安装：

```bash
mvn -v
```

如果Maven安装正确，输出应该显示Maven版本、Java版本和操作系统信息。

## Java IDE {#java-ide}

Java IDE提供了一个全面的环境，用于编写、测试和调试代码。可以选择很多IDE，因此您可以选择适合您工作流程的任何一个。一些流行的Java开发选择包括：

- **[Visual Studio Code](https://code.visualstudio.com/Download)**：一款轻量级、可扩展的代码编辑器，通过插件支持Java。
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**：以其强大的Java支持和丰富的插件生态系统而闻名。
- **[NetBeans](https://netbeans.apache.org/download/index.html)**：一款免费的开源IDE，支持Java和其他语言，以易用性和内置项目模板而闻名。
