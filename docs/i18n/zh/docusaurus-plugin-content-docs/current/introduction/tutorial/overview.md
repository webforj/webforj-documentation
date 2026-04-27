---
title: Overview
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 781bf0258ed2366e2125e99587cda439
---
这个逐步教程将指导您通过使用 webforJ 和 Spring Boot 构建客户管理应用程序的过程。它教您如何创建一个现代化、用户友好的界面，以查看、添加和编辑客户数据。

每一步都引入新的概念，并生成一个可运行的 Spring Boot 应用程序（JAR）。您可以使用 Maven 在本地启动应用程序，并在 Web 浏览器中与其交互。通过此设置，您可以实现快速的开发周期和生产就绪的部署模型，使用 Spring Boot 的嵌入式服务器。

无需拥有之前的 Spring Boot 或 webforJ 经验，但您应该对 Java 和 Maven 有基本的了解，以充分利用本教程。此教程将涵盖 Spring 概念，但对 Spring 的深入理解感兴趣的读者可以访问 [Spring 的主要文档](https://spring.io/learn) 和关于 [Spring Boot 的文档](https://docs.spring.io/spring-boot/index.html)。

## 教程概念 {#tutorial-concepts}

教程的第一部分专门用于 [项目设置](/docs/introduction/tutorial/project-setup)，以准备您的 Spring Boot + webforJ 环境。接下来的步骤引入新功能并推进您的项目。通过跟随这些步骤，您将清楚地理解应用程序如何在实现功能过程中不断演变。

每个步骤都有一个可在 GitHub 上运行的应用程序：

| 步骤 | 文档 | GitHub |
| ----- | ----- | ----- |
| 1 | [创建基本应用](/docs/introduction/tutorial/creating-a-basic-app)                               | [步骤 1 应用](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)
| 2 | [处理数据](/docs/introduction/tutorial/working-with-data)                                     | [步骤 2 应用](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)
| 3 | [路由和复合](/docs/introduction/tutorial/routing-and-composites)                           | [步骤 3 应用](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)
| 4 | [观察者和路由参数](/docs/introduction/tutorial/observers-and-route-parameters)           | [步骤 4 应用](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)
| 5 | [验证和绑定数据](/docs/introduction/tutorial/validating-and-binding-data)                 | [步骤 5 应用](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)
| 6 | [集成应用布局](/docs/introduction/tutorial/integrating-an-app-layout)                     | [步骤 6 应用](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout)

## 先决条件 {#prerequisites}

您应在开发机器上拥有以下工具/资源：

- Java 21 或 25
- Maven
- 一个 Java IDE
- Git（推荐但不是必需的）

:::info webforJ 先决条件
请查看 [先决条件文章](/docs/introduction/prerequisites)，以获取您开发环境所需工具的更详细概述。
:::

<DocCardList className="topics-section" />
