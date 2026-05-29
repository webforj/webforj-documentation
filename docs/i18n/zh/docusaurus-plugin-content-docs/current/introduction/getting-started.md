---
title: Getting Started
sidebar_position: 2
_i18n_hash: e0271a7db26a5c4b3fdc29508119aade
---
本文概述了使用 webforJ [原型](../building-ui/archetypes/overview.md) 创建新 webforJ 应用程序的步骤。原型提供了预配置的项目结构和起始代码，使您能够快速启动并运行一个项目。
要从原型创建新的 webforJ 应用程序，您可以使用 [startforJ](#using-startforj) 或 [命令行](#using-the-command-line)。

:::tip 先决条件
在开始之前，请查看设置和使用 webforJ 的必要 [先决条件](./prerequisites)。
:::


## 使用 startforJ {#using-startforj}

创建新 webforJ 应用程序的最简单方法是 [startforJ](https://docs.webforj.com/startforj)，它会根据所选的 webforJ 原型生成一个最小的启动项目。该启动项目包含所有必需的依赖项、配置文件和预制的布局，因此您可以立即开始构建。

### 使用 startforJ 自定义 {#customizing-with-startforj}

使用 [startforJ](https://docs.webforj.com/startforj) 创建应用程序时，您可以通过提供以下信息来进行自定义：

- 基本项目元数据（应用名称、组 ID、工件 ID）  
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- 风味

可选择两种风味选项，其中“仅 webforJ”是默认选项：
  - **仅 webforJ**：标准 webforJ 应用程序
  - **webforJ + Spring Boot**：带有 Spring Boot 支持的 webforJ 应用程序

:::tip 可用的原型
webforJ 提供了几个预定义的原型，帮助您快速启动。如需获取可用原型的完整列表，请参阅 [原型目录](../building-ui/archetypes/overview)。
:::

使用这些信息，startforJ 将根据您选择的原型和自定义创建一个基本项目。
您可以选择将项目下载为 ZIP 文件或直接发布到 GitHub。

下载项目后，在 IDE 中打开项目文件夹。

## 使用命令行 {#using-the-command-line}

如果您更喜欢使用命令行，可以直接使用 Maven 原型生成项目：

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
