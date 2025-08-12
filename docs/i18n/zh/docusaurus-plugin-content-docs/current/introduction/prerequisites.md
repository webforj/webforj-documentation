---
title: Prerequisites
sidebar_position: 1
_i18n_hash: 079539f07a72647e2faa9a9a5eda5634
---
开始使用 webforJ 非常简单，因为只有几个先决条件。使用此指南设置您的开发环境，配备您需要的基本工具，以便快速启动 webforJ。

## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

Java Development Kit (JDK) 是使用 webforJ 开发的最重要的要求，它提供了编译、运行和管理 Java 应用程序所需的工具。为确保与 webforJ 的兼容性，并访问 Java 生态系统的最新功能和安全更新，必须使用 Java **21**。 webforJ 框架与官方 Oracle JDK 和开源 Eclipse Temurin JDK 兼容。

### JDK 安装链接: {#jdk-installation-links}
:::tip  
如果您使用的是基于 UNIX 的操作系统，建议使用 [SDKMAN!](https://sdkman.io/) 来管理您的 Java 环境。它让您可以轻松在不同的 Java 供应商之间切换，毫无麻烦。  

另外，您可以使用 [Jabba](https://github.com/shyiko/jabba)，它适用于基于 UNIX 的系统和 Windows。这是一个管理 Java 版本的可靠跨平台解决方案。  
:::

- 官方 Oracle JDK 可以在 Oracle 的 [Java Downloads](https://www.oracle.com/java/technologies/downloads/) 页面找到。 
  - 选择 Java 版本 **21**。
  - 点击 Linux、macOS 或 Windows 的标签。
  - 点击与您计算机架构相对应的链接。 
  - 请参阅 Oracle 的 [JDK Installation Guide](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) 获取有关安装 Oracle JDK 的完整信息。
- 开源 JDK 可以在 Adoptium 的 [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) 页面找到。 
  - 使用下拉菜单选择操作系统、架构、包类型和 JDK 版本 **21**。 
  - 点击表格中您希望下载的存档类型的链接。
  - 请参阅 Adoptium 的 [Installation Guide](https://adoptium.net/installation/) 获取有关安装 Eclipse Temurin JDK 的完整信息。

### 验证您的 JDK 安装 {#verify-your-jdk-installation}
安装 JDK 后，通过在终端或命令提示符中运行以下命令来验证安装：

```bash
java -version
```

如果您的 JDK 安装正确，您将看到包含 JDK 版本详细信息的输出，指示版本为 **21**。

## Apache Maven {#apache-maven}

[Apache Maven](https://maven.apache.org/index.html) 是一个构建自动化和依赖管理工具，它简化了将外部库（如 webforJ）包含到项目中的过程。 除了解决依赖管理问题，Maven 还可以自动执行诸如编译代码、运行测试和打包应用程序等任务。

### Maven 安装链接 {#maven-installation-links}
- 要安装最新版本的 Maven，请访问 [Apache Maven Download Page](https://maven.apache.org/download.cgi)。 
  - Maven 的 [Installing Apache Maven](https://maven.apache.org/install.html) 页面概述了安装过程。 
  - Baeldung 的 [How to Install Maven on Windows, Linux, and Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) 是每个操作系统更详细的安装指南。

### 验证您的 Maven 安装 {#verify-your-maven-installation}

安装 Maven 后，通过在终端或命令提示符中运行以下命令来验证安装：

```bash
mvn -v
```

如果 Maven 安装正确，输出应显示 Maven 版本、Java 版本和操作系统信息。

## Java IDE {#java-ide}

Java IDE 提供了一个全面的环境，用于编写、测试和调试代码。您可以选择许多 IDE，因此可以选择适合您工作流程的任何一个。 一些流行的 Java 开发选择包括：

- **[Visual Studio Code](https://code.visualstudio.com/Download)**：一个轻量、可扩展的代码编辑器，通过插件支持 Java。
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**：以其强大的 Java 支持和丰富的插件生态系统而闻名。
- **[NetBeans](https://netbeans.apache.org/download/index.html)**：一个免费的开源 IDE，支持 Java 和其他语言，以易用和内置项目模板而闻名。
