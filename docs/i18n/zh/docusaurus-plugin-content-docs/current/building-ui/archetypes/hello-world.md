---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: e1da494f783aca68616cd374b92e700c
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# HelloWorld 原型
<!-- vale on -->

这个原型创建了一个简单的 hello world 应用程序，以演示使用 webforJ 构建用户界面的基础知识。这个模板非常适合初学者快速入门。它提供了一个直接的示例，演示如何设置和运行一个基本的 webforJ 应用程序，是新开发人员的优秀起点。

:::tip 从头开始
这个原型创建了一个简约的应用程序，包含一些组件和样式。对于希望创建一个最低限度搭建项目的开发者，请参见 [`blank` 原型](./blank)。
:::

:::tip 使用 startforJ
为了更好地控制自定义和配置，您可以使用 [startforJ](https://docs.webforj.com/startforj/) 来创建您的项目 - 只需在选择配置选项时选择 `HelloWorld` 原型。
:::

## 使用 `hello-world` 原型 {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## 运行应用 {#running-the-app}

在运行您的应用之前，如果您还没有安装 [先决条件](../../introduction/prerequisites)，请先安装。然后，导航到项目的根目录并运行以下命令：

```bash
# 标准 webforJ 应用
mvn jetty:run

# webforJ + Spring Boot
mvn spring-boot:run
```

一旦服务器运行，打开您的浏览器并访问 [http://localhost:8080](http://localhost:8080) 来查看应用。
