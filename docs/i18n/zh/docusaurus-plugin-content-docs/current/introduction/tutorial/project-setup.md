---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 72ee1120081fa9f4d4fed86c13741d5b
---
要开始本教程，您需要一个项目的位置，在这里您可以管理您的类和资源。以下部分描述了您可以以不同方式创建本教程的 webforJ 项目。

## 使用源代码 {#using-source-code}

跟随本教程的最简单方法是参考其源代码。您可以下载整个项目或从 GitHub 克隆它：

<!-- vale off -->
- 下载 ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub 存储库: [直接从 GitHub 克隆项目](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### 项目结构 {#project-structure}

该项目有六个子目录，每个目录对应于教程的一个步骤，每个目录中包含一个可运行的应用程序。跟随 本教程可以让您看到应用程序如何从基本设置发展到一个完全功能的客户管理系统。

```
webforj-tutorial
│   .gitignore
│   LICENSE
│   README.md
│
├───1-creating-a-basic-app
├───2-working-with-data
├───3-routing-and-composites
├───4-observers-and-route-parameters
├───5-validating-and-binding-data
└───6-integrating-an-app-layout
```

## 使用 startforJ {#using-startforj}

如果您更愿意创建一个新项目，您可以使用 [startforJ](https://docs.webforj.com/startforj) 来生成一个最小的启动项目。有关使用 startforJ 的更多详细信息，请参见 [开始使用](/docs/introduction/getting-started)。

:::note 必需设置
- 在 **webforJ 版本** 下拉框中，选择 webforJ 版本 **26.01 或更高**。
- 在 **Flavor** 下拉框中，选择 **webforJ + Spring Boot**。

## 使用命令行 {#using-command-line}

您还可以使用以下命令生成一个新项目：

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=com.webforj.tutorial \
  -DartifactId=customer-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
```powershell
mvn -B archetype:generate `
  -DarchetypeGroupId="com.webforj" `
  -DarchetypeArtifactId="webforj-archetype-hello-world" `
  -DarchetypeVersion="LATEST" `
  -DgroupId="com.webforj.tutorial" `
  -DartifactId="customer-app" `
  -Dversion="1.0-SNAPSHOT" `
  -Dflavor="webforj-spring"
```
  </TabItem>
  <TabItem value="cmd" label="命令提示符">
```
mvn -B archetype:generate ^
  -DarchetypeGroupId="com.webforj" ^
  -DarchetypeArtifactId="webforj-archetype-hello-world" ^
  -DarchetypeVersion="LATEST" ^
  -DgroupId="com.webforj.tutorial" ^
  -DartifactId="customer-app" ^
  -Dversion="1.0-SNAPSHOT" ^
  -Dflavor="webforj-spring"
```
  </TabItem>
</Tabs>
<!-- vale on -->

## 配置 {#configurations}

前面提到的创建新项目的两种方法使用了 webforJ [原型](/docs/building-ui/archetypes/overview)，它们会自动将所需的配置添加到您的项目中。这包括 Spring [依赖项](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies)、构建和监视前端源的 webforJ Maven 插件，以及 `src/main/resources/application.properties` 中的以下属性：

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## 运行应用程序 {#running-the-app}

要查看应用程序在您学习本教程过程中的运行状态：

1. 导航到所需步骤的目录。这应该是该步骤的顶级目录，包含 `pom.xml`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

   生成的 POM 将此默认命令配置为编译应用程序，启动 webforJ 前端监视器，并运行 Spring Boot。

运行应用程序会自动在 `http://localhost:8080` 打开新的浏览器窗口。
