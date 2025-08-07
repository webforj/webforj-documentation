---
title: Overview
hide_giscus_comments: true
_i18n_hash: 4d70b1e894fa3ca05afb5a4bc6ed982d
---
本教程旨在逐步指导您创建应用程序的过程。该应用程序旨在管理客户信息，展示如何使用 webforJ 构建一个功能齐全且用户友好的界面，具有查看、添加和编辑客户数据的功能。每个部分都将建立在上一个部分的基础上，但您可以根据需要跳过。

本教程中的每一步都会生成一个能够编译成 WAR 文件的程序，该文件可以部署到任何 Java Web 应用服务器上。在本教程中，将使用 Maven Jetty 插件在本地部署应用程序。这个轻量级的设置确保应用程序可以快速运行，并且在开发过程中更改可以实时看到。

## 教程应用程序功能 {#tutorial-app-features}

 - 在表格中处理数据。
 - 使用 [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) 和资产管理。
 - [路由](../../routing/overview) 和 [导航](../../routing/route-navigation)
 - [数据绑定](../../data-binding/overview) 和 [验证](../../data-binding/validation/overview)

## 先决条件 {#prerequisites}

为了充分利用本教程，假设您具备基本的 Java 编程知识，并熟悉 Maven 等工具。如果您是 webforJ 的新手，请不要担心 - 框架的基本概念会在过程中得到介绍。

以下工具/资源应在您的开发机器上存在

<!-- vale off -->
- Java 17 或更高版本
- Maven
- Java IDE
- 网页浏览器
- Git（推荐但不是必需）
<!-- vale on -->

:::tip webforJ 先决条件
请参阅 [这篇文章](../prerequisites) 以获取所需工具的更详细概述。
:::

## 章节 {#sections}

本教程分为以下章节。请顺序进行，以获得全面的指导，或直接跳到特定信息。

:::tip 项目设置
对于那些希望直接跳到特定主题的人，建议首先阅读项目设置部分，然后再继续。
:::

<DocCardList className="topics-section" />
