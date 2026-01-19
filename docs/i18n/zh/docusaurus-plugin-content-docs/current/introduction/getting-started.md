---
title: Getting Started
sidebar_position: 2
_i18n_hash: 5a051bf7c5a9494b21ba5df3629c35b4
---
本文概述了使用 webforJ [archetypes](../building-ui/archetypes/overview.md) 创建新 webforJ 应用的步骤。Archetypes 提供了预配置的项目结构和启动代码，使您能够快速启动并运行项目。
要从 archetype 创建新的 webforJ 应用，您可以使用 [startforJ](#using-startforj) 或 [命令行](#using-the-command-line)。

:::tip 先决条件
在开始之前，请查看设置和使用 webforJ 的必要 [先决条件](./prerequisites)。
:::

## 使用 startforJ {#using-startforj}

创建新的 webforJ 应用的最简单方法是 [startforJ](https://docs.webforj.com/startforj)，它基于所选择的 webforJ archetype 生成一个最小的启动项目。该启动项目包括所有必需的依赖项、配置文件和预制布局，因此您可以立即开始构建。

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### 使用 startforJ 进行自定义 {#customizing-with-startforj}

使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，您可以通过提供以下信息进行自定义：

- 基本项目元数据（应用名称、组 ID、工件 ID）  
- webforJ 版本和 Java 版本
- 主题颜色和图标
- Archetype
- Flavor

有两个风味选项可供选择，其中“仅 webforJ”是默认选项：
  - **仅 webforJ**：标准 webforJ 应用
  - **webforJ + Spring Boot**：具有 Spring Boot 支持的 webforJ 应用

:::tip 可用的 Archetypes
webforJ 附带多个预定义的 archetypes，以帮助您快速入门。有关可用 archetypes 的完整列表，请参见 [archetypes 目录](../building-ui/archetypes/overview)。
:::

使用此信息，startforJ 将根据您选择的 archetype 和自定义选项创建基本项目。
您可以选择将项目下载为 ZIP 文件或直接发布到 GitHub。

下载项目后，在您的 IDE 中打开项目文件夹。

## 使用命令行 {#using-the-command-line}

如果您更喜欢使用命令行，可以直接使用 Maven archetype 生成项目：

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
