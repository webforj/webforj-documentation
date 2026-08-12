---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 1704f647af5396bd4efd4fdbcc4da978
---
要开始本教程，您需要一个项目位置，以便管理您的类和资源。以下部分描述了您可以为本教程创建 webforJ 项目的不同方式。

## 使用源代码 {#using-source-code}

遵循本教程的最简单方式是参考它的源代码。您可以从 GitHub 下载整个项目或克隆它：

<!-- vale off -->
- 下载 ZIP 文件: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub 仓库: [直接从 GitHub 克隆项目](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### 项目结构 {#project-structure}

该项目有六个子目录，每个子目录对应于教程的一个步骤，每个子目录都包含一个可运行的应用程序。跟随本教程可以看到应用程序从基本设置逐步发展到一个完整的客户管理系统。

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

如果您更愿意创建一个新项目，可以使用 [startforJ](https://docs.webforj.com/startforj) 来生成一个最小的起始项目。有关如何使用 startforJ 的更多详细信息，请参见 [快速入门](/docs/introduction/getting-started)。

:::note 必需设置
- 在 **webforJ 版本** 下拉菜单中，选择 webforJ 版本 **26.01 或更高版本**。
- 在 **Flavor** 下拉菜单中，选择 **webforJ + Spring Boot**。

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

创建新项目的两种方法使用了 webforJ [archetypes](/docs/building-ui/archetypes/overview)，这些 archetypes 会自动将所需的配置添加到您的项目中。这包括 Spring [dependencies](/docs/integrations/spring/spring-boot)、构建和监视前端源的 webforJ Maven 插件，以及以下属性在 `src/main/resources/application.properties` 中：

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## 运行应用程序 {#running-the-app}

要查看应用程序在您完成教程时的运行情况：

1. 导航到所需步骤的目录。这应该是该步骤的顶级目录，包含 `pom.xml`。

2. 使用以下 Maven 命令本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

   生成的 POM 配置了该默认命令，以编译应用程序，启动 webforJ 前端监视器，并运行 Spring Boot。

运行应用程序将自动打开一个新浏览器，地址为 `http://localhost:8080`。
