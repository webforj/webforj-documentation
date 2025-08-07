---
title: Getting Started
sidebar_position: 2
_i18n_hash: 37b65c983d6210a89474156b10af1e93
---
本文概述了使用 webforJ [原型](../building-ui/archetypes/overview.md) 创建新 webforJ 应用的步骤。原型提供预配置的项目结构和启动代码，使您能够快速启动并运行一个项目。要从原型创建新的 webforJ 应用，您可以使用 [startforJ](#using-startforj) 或 [命令行](#using-the-command-line)。

:::tip 前提条件
在开始之前，请查看设置和使用 webforJ 所需的 [前提条件](./prerequisites)。
:::

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

## 使用 startforJ {#using-startforj}

创建新 webforJ 应用的最简单方法是 [startforJ](https://docs.webforj.com/startforj)，它根据选择的 webforJ 原型生成一个最小的启动项目。此启动项目包括所有必需的依赖项、配置文件和预制布局，因此您可以立即开始构建。

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### 使用 startforJ 自定义 {#customizing-with-startforj}

使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，您可以通过提供以下信息来进行自定义：

- 基本项目元数据（应用名称、组 ID、工件 ID）
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- Flavor

有两种可供选择的 Flavor 选项，以 "webforJ Only" 为默认：
  - **webforJ Only**：标准的 webforJ 应用
  - **webforJ + Spring Boot**：支持 Spring Boot 的 webforJ 应用

:::caution Spring Boot 支持
Spring Boot Flavor 仅在 webforJ 版本 25.02 及更高版本中可用。如果选择此选项，请确保选择兼容版本。
:::

:::tip 可用原型
webforJ 附带多个预定义原型，帮助您快速入门。有关可用原型的完整列表，请参见 [原型目录](../building-ui/archetypes/overview)。
:::

使用这些信息，startforJ 将从您选择的原型和自定义信息创建一个基本项目。您可以选择将项目下载为 ZIP 文件或直接发布到 GitHub。

下载项目后，在 IDE 中打开项目文件夹，然后继续进行 [运行应用](#running-the-app)。

## 使用命令行 {#using-the-command-line}

如果您更喜欢使用命令行，您可以直接使用 Maven 原型生成项目：

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## 运行应用 {#running-the-app}

在运行您的应用之前，如果您还没有安装 [前提条件](./prerequisites.md)，请安装它们。然后，导航到项目的根目录并运行以下命令：

```bash
# 针对标准 webforj 应用
mvn jetty:run

# 针对 webforj + Spring Boot
mvn spring-boot:run
```

服务器启动后，打开浏览器并访问 [http://localhost:8080](http://localhost:8080) 查看应用。

:::info 许可证和水印
有关未许可项目中出现的水印的信息，请参见 [许可证和水印](../configuration/licensing-and-watermark)。
:::
