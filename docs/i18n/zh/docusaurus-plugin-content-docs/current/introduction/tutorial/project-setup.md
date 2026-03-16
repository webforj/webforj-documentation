---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: f9a028daac660f61634ad84d00cb0130
---
要开始本教程，您需要为您的项目选择一个可以管理您的类和资源的位置。以下部分描述了您可以为本教程创建 webforJ 项目的不同方式。

## 使用源代码 {#using-source-code}

遵循本教程的最简单方法是参考其源代码。您可以下载整个项目或从 GitHub 克隆它：

<!-- vale off -->
- 下载 ZIP：[webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub 仓库：直接从 GitHub 克隆项目 [directly from GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### 项目结构 {#project-structure}

该项目有五个子目录，每个子目录对应于教程中的一个步骤，并且每个子目录都包含一个可运行的应用程序。按步骤进行可以让您看到应用程序如何从基本设置逐步发展为一个功能齐全的客户管理系统。

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
└───5-validating-and-binding-data
```

## 使用 startforJ {#using-startforj}

如果您更喜欢创建一个新项目，可以使用 [startforJ](https://docs.webforj.com/startforj) 生成一个最小的启动项目。有关使用 startforJ 的更多详细信息，请参阅 [入门指南](/docs/introduction/getting-started)。

:::note 必需的设置
- 在 **webforJ 版本** 下拉列表中，选择 webforJ 版本 **25.10 或更高**。
- 在 **Flavor** 下拉列表中，选择 **webforJ + Spring Boot**。 
:::

## 使用命令行 {#using-command-line}

您还可以使用以下命令生成一个新项目：

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

## 配置

上述提到的两种创建新项目的方法使用 webforJ [原型](/docs/building-ui/archetypes/overview)，它们会自动将所需的配置添加到您的项目中，例如 Spring [依赖项](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) 到您的 POM 以及以下属性在 `src/main/resources/application.properties` 中：

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## 运行应用程序 {#running-the-app}

要在进度中查看应用程序：

1. 导航到所需步骤的目录。此目录应是该步骤的顶级目录，其中包含 `pom.xml`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序时会自动在 `http://localhost:8080` 打开一个新浏览器。
