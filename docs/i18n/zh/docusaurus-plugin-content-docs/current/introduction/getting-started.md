---
title: Getting Started
sidebar_position: 2
_i18n_hash: 24c0a494b270fb4ea83106005e173ae8
---
本文概述了使用 webforJ [archetypes](../building-ui/archetypes/overview.md) 创建新的 webforJ 应用程序的步骤。archetypes 提供预配置的项目结构和起始代码，使您能够快速启动并运行项目。
要从 archetype 创建新的 webforJ 应用程序，您可以使用 [startforJ](#using-startforj) 或 [命令行](#using-the-command-line)。 

:::tip 前提条件
在开始之前，请查看设置和使用 webforJ 的必要 [前提条件](./prerequisites)。
:::


## 使用 startforJ {#using-startforj}

创建新的 webforJ 应用程序最简单的方法是 [startforJ](https://docs.webforj.com/startforj)，它基于所选的 webforJ archetype 生成一个最小的起始项目。这个起始项目包含所有必需的依赖项、配置文件和预制的布局，因此您可以立即开始构建。

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>


### 使用 startforJ 自定义 {#customizing-with-startforj}

当您使用 [startforJ](https://docs.webforj.com/startforj) 创建应用程序时，可以通过提供以下信息来进行自定义：

- 基本项目元数据（应用名称、组 ID、构件 ID）  
- webforJ 版本和 Java 版本
- 主题颜色和图标
- archetype
- 风味

有两种风味选项可供选择，默认是“webforJ Only”：
  - **webforJ Only**：标准 webforJ 应用程序
  - **webforJ + Spring Boot**：具有 Spring Boot 支持的 webforJ 应用程序

:::caution Spring Boot 支持
Spring Boot 风味仅在 webforJ 版本 25.02 及更高版本中可用。如果选择此选项，请确保选择兼容的版本。
:::

:::tip 可用 Archetypes
webforJ 附带几个预定义的 archetypes，帮助您快速入门。有关可用 archetypes 的完整列表，请参见 [archetypes 目录](../building-ui/archetypes/overview)。
:::

使用这些信息，startforJ 将根据您选择的 archetype 和自定义内容创建一个基本项目。
您可以选择将项目下载为 ZIP 文件或直接发布到 GitHub。

下载项目后，打开项目文件夹，在您的 IDE 中继续 [运行应用程序](#running-the-app)。

## 使用命令行 {#using-the-command-line}


如果您更喜欢使用命令行，您可以使用 Maven archetype 直接生成项目：

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## 运行应用程序 {#running-the-app}

在运行应用程序之前，如果尚未安装 [前提条件](./prerequisites.md)，请先进行安装。
然后，导航到项目的根目录并运行以下命令：

```bash
# 对于标准的 webforj 应用程序
mvn jetty:run

# 对于 webforj + Spring Boot
mvn spring-boot:run
```

服务器运行后，打开浏览器并访问 [http://localhost:8080](http://localhost:8080) 来查看应用程序。
