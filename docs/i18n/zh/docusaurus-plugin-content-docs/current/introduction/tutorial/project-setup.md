---
title: 项目设置
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 95bc825bd041018765a732bf464d77b7
---
要开始本教程，你需要为项目准备一个位置，以便管理类和资源。以下各部分介绍了创建本教程所需的 webforJ 项目的不同方式。

## 使用源代码 {#using-source-code}

跟随本教程最简单的方式是参考其源代码。你可以下载整个项目，或从 GitHub 克隆：

<!-- vale off -->
- 下载 ZIP 文件：[webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub 仓库：从 [GitHub 直接克隆项目](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

<!-- <VideoPlayer video="tutorial/project-setup" width={1920} height={1080} title="Project setup" /> -->

### 项目结构 {#project-structure}

项目包含六个子目录，分别对应教程的每个步骤，每个子目录中都有一个可运行的应用。跟随教程操作，你可以看到应用如何从基本设置逐步发展为功能齐全的客户管理系统。

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

如果你更愿意创建一个新项目，可以使用 [startforJ](https://docs.webforj.com/startforj) 生成一个最小化的入门项目。有关使用 startforJ 的详细信息，请参阅[入门](/docs/introduction/getting-started)。

:::note 必需设置
- 在 **webforJ version** 下拉菜单中，选择 webforJ 版本 **26.01 或更高版本**。
- 在 **Runs on** 下，选择 **Spring Boot**。

## 使用命令行 {#using-command-line}

你也可以使用以下命令生成一个新项目：

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
  <TabItem value="cmd" label="Command Prompt">
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

上述两种创建新项目的方式都使用 webforJ [原型](/docs/building-ui/archetypes/overview)，它们会自动将所需配置添加到项目中。这包括 Spring [依赖项](/docs/integrations/spring/spring-boot)、用于构建和监视前端源文件的 webforJ Maven 插件，以及 `src/main/resources/application.properties` 中的以下属性：

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## 运行应用 {#running-the-app}

在完成教程的过程中，要查看应用的运行情况：

1. 进入所需步骤对应的目录。该目录应为此步骤的顶层目录，其中包含 `pom.xml`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用：
    ```bash
    mvn
    ```

   生成的 POM 将此默认命令配置为编译应用、启动 webforJ 前端监视器并运行 Spring Boot。

<!-- vale Google.WordList = NO -->
运行应用后，系统会自动在新浏览器窗口中打开 `http://localhost:8080`。
<!-- vale Google.WordList = YES -->
