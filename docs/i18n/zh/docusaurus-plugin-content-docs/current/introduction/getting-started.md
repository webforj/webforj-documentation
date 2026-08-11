---
title: Getting Started
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: c1867c61e2072cb6657bad9492f22083
---
本文概述了使用 webforJ [archetypes](../building-ui/archetypes/overview.md) 创建新的 webforJ 应用的步骤。Archetypes 提供了预配置的项目结构和启动代码，让您能够快速启动并运行项目。
要从 archetype 创建新的 webforJ 应用，您可以使用 [startforJ](#using-startforj) 或 [命令行](#using-the-command-line)。

:::tip 前提条件
开始之前，请查看设置和使用 webforJ 所需的 [前提条件](./prerequisites)。
:::


## 使用 startforJ {#using-startforj}

创建新的 webforJ 应用的最简单方法是 [startforJ](https://docs.webforj.com/startforj)，它根据选择的 webforJ archetype 生成一个最小的启动项目。此启动项目包括所有必需的依赖项、配置文件和预制布局，您可以立即开始构建。

### 使用 startforJ 进行自定义 {#customizing-with-startforj}

当您使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，可以通过提供以下信息进行自定义：

- 基本项目元数据（应用名称、组 ID、工件 ID）
- webforJ 版本和 Java 版本
- 主题颜色和图标
- Archetype
- Flavor

有两个可供选择的 Flavor 选项，其中“webforJ Only”为默认选项：
  - **webforJ Only**：标准 webforJ 应用
  - **webforJ + Spring Boot**：支持 Spring Boot 的 webforJ 应用

:::tip 可用的 Archetypes
webforJ 附带多个预定义的 archetypes，以帮助您快速入门。有关可用 archetypes 的完整列表，请参见 [archetypes 目录](../building-ui/archetypes/overview)。
:::

使用这些信息，startforJ 将根据您选择的 archetype 和自定义选项创建基本项目。
您可以选择将项目下载为 ZIP 文件或直接发布到 GitHub。

下载项目后，在 IDE 中打开项目文件夹。

## 使用命令行 {#using-the-command-line}

如果您更喜欢使用命令行，则可以直接使用 Maven archetype 生成项目：

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
