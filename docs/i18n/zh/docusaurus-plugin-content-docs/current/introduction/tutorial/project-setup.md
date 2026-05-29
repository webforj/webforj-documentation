---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 02dbd05d1fdaba50c25155904013471b
---
要开始本教程，您需要一个可以管理类和资源的项目位置。以下部分描述了您可以创建 webforJ 项目的不同方法。

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

项目包含六个子目录，每个目录对应教程的一个步骤，并且每个目录都包含一个可运行的应用程序。跟随教程可以看到应用程序如何从基本设置逐步发展为一个完整的客户管理系统。

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

<!-- vale off -->
## 使用 startforJ {#using-startforj}
<!-- vale on -->

如果您希望创建一个新项目，可以使用 [startforJ](https://docs.webforj.com/startforj) 来生成一个最小的入门项目。有关使用 startforJ 的更详细信息，请参见 [开始使用](/docs/introduction/getting-started)。

:::note 必需设置
- 在 **webforJ 版本** 下拉菜单中，选择 webforJ 版本 **26.00 或更高**。
- 在 **Flavor** 下拉菜单中，选择 **webforJ + Spring Boot**。 
:::

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

## 配置

上述两种创建新项目的方法都使用 webforJ [原型](/docs/building-ui/archetypes/overview)，它们会自动为您的项目添加所需的配置，例如 Spring [依赖关系](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) 到您的 POM 以及以下属性到 `src/main/resources/application.properties`：

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## 运行应用程序 {#running-the-app}

要在您逐步完成教程时查看应用程序的运行情况：

1. 导航到所需步骤的目录。该目录应为该步骤的顶级目录，包含 `pom.xml`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动打开一个新浏览器窗口，地址为 `http://localhost:8080`。
