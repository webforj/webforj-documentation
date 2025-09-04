---
title: Getting Started
sidebar_position: 2
_i18n_hash: 32a742a43fe6dd9e983eaf428e04e06d
---
本文概述了使用 webforJ [原型](../building-ui/archetypes/overview.md) 创建新的 webforJ 应用程序的步骤。原型提供了预配置的项目结构和初始代码，以便您可以快速启动和运行项目。
要从原型创建新的 webforJ 应用程序，可以使用 [startforJ](#using-startforj) 或 [命令行](#using-the-command-line)。

:::tip 前提条件
在开始之前，请查看设置和使用 webforJ 的必要 [前提条件](./prerequisites)。
:::

## 使用 startforJ {#using-startforj}

创建新的 webforJ 应用程序的最简单方法是 [startforJ](https://docs.webforj.com/startforj)，它基于所选的 webforJ 原型生成一个最小的初始项目。此初始项目包含所有必需的依赖项、配置文件和预制布局，以便您可以立即开始构建。

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### 使用 startforJ 自定义 {#customizing-with-startforj}

使用 [startforJ](https://docs.webforj.com/startforj) 创建应用程序时，可以通过提供以下信息来自定义它：

- 基本项目元数据（应用名称、组 ID、工件 ID）  
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- 风味

有两种风味可供选择，其中“仅 webforJ”是默认选项：
  - **仅 webforJ**：标准 webforJ 应用程序
  - **webforJ + Spring Boot**：支持 Spring Boot 的 webforJ 应用程序

:::caution Spring Boot 支持
Spring Boot 风味仅在 webforJ 版本 25.02 及更高版本中提供。如果选择此选项，请确保选择兼容的版本。
:::

:::tip 可用原型
webforJ 附带多个预定义原型，以帮助您快速启动。有关可用原型的完整列表，请参见 [原型目录](../building-ui/archetypes/overview)。
:::

使用这些信息，startforJ 将根据您选择的原型和自定义选项创建一个基本项目。
您可以选择将项目下载为 ZIP 文件，或直接发布到 GitHub。

下载项目后，在 IDE 中打开项目文件夹并继续 [运行应用程序](#running-the-app)。

## 使用命令行 {#using-the-command-line}

如果您更喜欢使用命令行，可以直接使用 Maven 原型生成项目：

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## 运行应用程序 {#running-the-app}

在运行应用程序之前，如果您还没有安装 [前提条件](./prerequisites.md)，请先安装它们。
然后，导航到项目的根目录并运行以下命令：

```bash
# 对于标准 webforj 应用程序
mvn jetty:run

# 对于 webforj + Spring Boot
mvn spring-boot:run
```

一旦服务器运行，请打开浏览器并访问 [http://localhost:8080](http://localhost:8080) 来查看应用程序。

:::info 许可证和水印
有关未授权项目中存在的水印的信息，请参见 [许可证和水印](../configuration/licensing-and-watermark)。
:::
