---
title: Overview
hide_giscus_comments: true
sidebar_class_name: updated-content
_i18n_hash: 9dc213747204cb02b3b1624b39293445
---
这个逐步教程指导您通过使用 webforJ 和 Spring Boot 构建客户管理应用程序的过程。它教您如何创建一个现代、用户友好的界面，以查看、添加和编辑客户数据。

每一步都引入新的概念，并产生一个可运行的 Spring Boot 应用程序 (JAR)。您可以使用 Maven 在本地启动您的应用程序，并在网页浏览器中与之交互。通过这种设置，您将获得快速的开发周期和一个准备生产的部署模型，使用 Spring Boot 的嵌入式服务器。

不需要之前的 Spring Boot 或 webforJ 经验，但您应该对 Java 和 Maven 有基本的理解，以便充分利用本教程。本教程将在出现时涵盖 Spring 概念，但对深入理解 Spring 感兴趣的用户可以访问 [Spring 的主要文档](https://spring.io/learn) 和关于 [Spring Boot](https://docs.spring.io/spring-boot/index.html) 的文档。

## 教程概念 {#tutorial-concepts}

教程的第一部分专门用于 [项目设置](/docs/introduction/tutorial/project-setup)，以准备您的 Spring Boot + webforJ 环境。然后，接下来的步骤引入新功能并推进您的项目。通过跟随这些步骤，您将清楚了解应用程序在实现功能时是如何演变的。

每一步都有一个对应的可运行应用程序可在 GitHub 上找到：

| 步骤 | 文档 | GitHub |
| ----- | ----- | ----- |
| 1 | [创建一个基本应用](/docs/introduction/tutorial/creating-a-basic-app)                               | [步骤 1 应用](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)
| 2 | [使用数据](/docs/introduction/tutorial/working-with-data)                                     | [步骤 2 应用](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)
| 3 | [路由和复合](/docs/introduction/tutorial/routing-and-composites)                           | [步骤 3 应用](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)
| 4 | [观察者和路由参数](/docs/introduction/tutorial/observers-and-route-parameters)           | [步骤 4 应用](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)
| 5 | [验证和绑定数据](/docs/introduction/tutorial/validating-and-binding-data)                 | [步骤 5 应用](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)

## 先决条件 {#prerequisites}

您应该在您的开发机器上拥有以下工具/资源：

- Java 17 或 21
- Maven
- Java IDE
- Git（推荐但不是必需）

:::info webforJ 先决条件
查看 [先决条件文章](/docs/introduction/prerequisites) 以获取有关您开发环境所需工具的详细概述。
:::

<DocCardList className="topics-section" />
