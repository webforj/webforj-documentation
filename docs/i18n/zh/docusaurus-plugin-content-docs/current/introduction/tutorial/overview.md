---
title: 概述
hide_giscus_comments: true
_i18n_hash: 4174ea766ba47277c5bcb607c4111e29
---
本教程旨在逐步指导您创建应用程序的过程。此应用程序旨在管理客户信息，演示如何使用 webforJ 构建一个功能齐全且用户友好的界面，具有查看、添加和编辑客户数据的功能。每个部分都将在前一部分的基础上展开，但您可以根据需要自由跳过。

本教程中的每一步将生成一个编译成 WAR 文件的程序，该文件可部署到任何 Java Web 应用服务器。对于本教程，将使用 Maven Jetty 插件在本地部署应用程序。这个轻量级的设置确保应用程序能够快速运行，并且在开发过程中可以实时看到更改。

## 教程应用功能 {#tutorial-app-features}

 - 在表格中处理数据。
 - 使用 [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) 和资产管理。
 - [路由](../../routing/overview) 和 [导航](../../routing/route-navigation)
 - [数据绑定](../../data-binding/overview) 和 [验证](../../data-binding/validation/overview)

## 前提条件 {#prerequisites}

为了充分利用本教程，假设您对 Java 编程有基本了解，并且熟悉像 Maven 这样的工具。如果您是 webforJ 的新手，请不要担心——框架的基本知识将在过程中介绍。

您的开发机器上应具备以下工具/资源

<!-- vale off -->
- Java 17 或更高版本
- Maven
- Java IDE
- 网络浏览器
- Git（推荐但不是必需）
<!-- vale on -->

:::tip webforJ 前提条件
有关所需工具的更详细概述，请参阅 [本文](../prerequisites)。
:::

## 章节 {#sections}

本教程分为以下几个部分。按照顺序进行，以便全面了解，或者跳过特定信息。

:::tip 项目设置
对于那些希望跳过具体主题的读者，建议在继续之前先阅读项目设置部分。
:::

<DocCardList className="topics-section" />
