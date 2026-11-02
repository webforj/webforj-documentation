---
title: 开始使用
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: ea669974a5f1b3a02a162f89bb16c6e3
---
本文概述了使用 webforJ [原型](../building-ui/archetypes/overview.md) 创建新 webforJ 应用的步骤。原型提供了预配置的项目结构和启动代码，以便您能够快速启动和运行项目。

要从原型创建一个新的 webforJ 应用，您可以使用 [startforJ](#using-startforj) 或 [命令行](#using-the-command-line)。

:::tip 必备条件
在开始之前，请查看设置和使用 webforJ 所需的 [必备条件](./prerequisites)。
:::

## 使用 startforJ {#using-startforj}

创建新的 webforJ 应用的最简单方法是 [startforJ](https://docs.webforj.com/startforj)，它根据选择的 webforJ 原型生成一个最小的启动项目。此启动项目包含所有必需的依赖项、配置文件和预制布局，因此您可以直接开始构建。

### 使用 startforJ 定制 {#customizing-with-startforj}

当您使用 [startforJ](https://docs.webforj.com/startforj) 创建应用时，可以通过提供以下信息进行定制：

- 基本项目元数据（应用名称、组 ID、构件 ID）
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- 运行环境

**运行环境** 的选择决定了应用的运行方式，默认选项为 **Servlet 容器**：
  - **Servlet 容器**：一个适用于 Jetty、Tomcat 或其他 Servlet 容器的 WAR
  - **Spring Boot**：带有嵌入式服务器的可执行 JAR
  - **BBjServices**：通过 [BBjShip](/docs/configuration/bbjship/overview) 运行的应用

:::tip 可用原型
webforJ 附带多个预定义的原型，帮助您快速入门。有关可用原型的完整列表，请参见 [原型目录](../building-ui/archetypes/overview)。
:::

使用这些信息，startforJ 将根据您选择的原型和定制创建基本项目。您可以选择将项目下载为 ZIP 文件或直接发布到 GitHub。

下载项目后，在 IDE 中打开项目文件夹。

## 使用命令行 {#using-the-command-line}

如果您更喜欢使用命令行，可以直接使用 Maven 原型生成项目：

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
