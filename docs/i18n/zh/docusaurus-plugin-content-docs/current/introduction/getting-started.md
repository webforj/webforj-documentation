---
title: 入门
sidebar_position: 2
_i18n_hash: 5c658711bfa3dc70787cccbf2dfb6d2d
---
本文概述了使用 webforJ [原型](../building-ui/archetypes/overview.md) 创建新的 webforJ 应用程序的步骤。原型提供了预先配置的项目结构和起始代码，让您能够快速启动项目并投入运行。
要从原型创建新的 webforJ 应用程序，可以使用 [startforJ](#using-startforj) 或 [命令行](#using-the-command-line)。

:::tip 前提条件
在开始之前，请查看设置和使用 webforJ 的必要 [前提条件](./prerequisites)。
:::

## 使用 startforJ {#using-startforj}

创建新的 webforJ 应用程序最简单的方法是 [startforJ](https://docs.webforj.com/startforj)，它基于所选的 webforJ 原型生成一个最小化的起始项目。这个起始项目包括所有必需的依赖项、配置文件和预制布局，让您能够立刻开始构建。

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### 使用 startforJ 进行自定义 {#customizing-with-startforj}

当您使用 [startforJ](https://docs.webforj.com/startforj) 创建应用程序时，您可以通过提供以下信息进行自定义：

- 基本项目元数据（应用名称、组 ID、工件 ID）  
- webforJ 版本和 Java 版本
- 主题颜色和图标
- 原型
- 风味

有两种风味选项可供选择，其中“webforJ Only”是默认选项：
  - **webforJ Only**：标准 webforJ 应用
  - **webforJ + Spring Boot**：支持 Spring Boot 的 webforJ 应用

:::tip 可用原型
webforJ 提供了几个预定义的原型，可以帮助您快速入门。有关可用原型的完整列表，请参阅 [原型目录](../building-ui/archetypes/overview)。
:::

使用这些信息，startforJ 将根据您选择的原型及自定义选项创建基本项目。
您可以选择将项目下载为 ZIP 文件或直接发布到 GitHub。

下载项目后，请在 IDE 中打开项目文件夹并继续 [运行应用程序](#running-the-app)。

## 使用命令行 {#using-the-command-line}

如果您更喜欢使用命令行，您可以使用 Maven 原型直接生成项目：

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
