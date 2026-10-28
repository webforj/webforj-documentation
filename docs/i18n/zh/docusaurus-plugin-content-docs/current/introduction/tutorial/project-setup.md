---
title: 项目设置
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 8c9cc05d141898612beff7f1c3f73fae
---
要开始本教程，您需要为您的项目选择一个位置，以便可以管理您的类和资源。以下部分描述了您可以为本教程创建webforJ项目的不同方法。

## 使用源代码 {#using-source-code}

遵循本教程最简单的方法是参考其源代码。您可以下载整个项目或从GitHub克隆它：

<!-- vale off -->
- 下载ZIP： [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- GitHub仓库： [直接从GitHub克隆项目](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### 项目结构 {#project-structure}

该项目有六个子目录，每个目录对应教程的一个步骤，每个步骤都包含一个可运行的应用程序。跟随这些步骤可以让您看到应用程序如何从基本设置发展到一个功能完整的客户管理系统。

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
## 使用startforJ {#using-startforj}
<!-- vale on -->

如果您更愿意创建一个新项目，可以使用[startforJ](https://docs.webforj.com/startforj)生成一个最小的起始项目。请参阅[入门](/docs/introduction/getting-started)以获取关于使用startforJ的更多详细信息。

:::note 必需设置
- 在**webforJ版本**下拉菜单中，选择webforJ版本**26.01或更高**。
- 在**运行于**下，选择**Spring Boot**。

## 使用命令行 {#using-command-line}

您也可以使用以下命令生成一个新项目：

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

上述两种创建新项目的方法使用webforJ [原型](/docs/building-ui/archetypes/overview)，这些原型会自动将所需的配置添加到您的项目中。这包括Spring [依赖](/docs/integrations/spring/spring-boot)，构建和监视前端源的webforJ Maven插件，以及`src/main/resources/application.properties`中的以下属性：

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## 运行应用程序 {#running-the-app}

要在您进行教程的过程中查看应用程序的运行情况：

1. 导航到所需步骤的目录。该目录应为该步骤的顶层目录，包含`pom.xml`。

2. 使用以下Maven命令在本地运行Spring Boot应用程序：
    ```bash
    mvn
    ```

   生成的POM将此默认命令配置为编译应用程序，启动webforJ前端监视器并运行Spring Boot。

<!-- vale Google.WordList = NO -->
运行应用程序会自动在`http://localhost:8080`打开一个新的浏览器窗口。
<!-- vale Google.WordList = YES -->
