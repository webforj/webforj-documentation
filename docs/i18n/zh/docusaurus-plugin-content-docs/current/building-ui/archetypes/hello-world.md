---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: 145d1e89a5f688fa0c912b87056a35d1
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

此原型创建一个简单的 hello world 应用程序，以演示使用 webforJ 构建用户界面的基础知识。这个模板非常适合初学者快速入门。它提供了一个关于如何设置和运行基本 webforJ 应用程序的直接示例，是新开发者的优良起点。

:::tip 从头开始
此原型创建一个简约的应用程序，包含一些组件和样式。对于希望以最小脚手架创建项目的开发者，请参阅 [`blank` 原型](./blank)。
:::

:::tip 使用 startforJ
要获得更多的定制和配置控制，您可以使用 [startforJ](https://docs.webforj.com/startforj/) 来创建您的项目 - 只需在选择配置选项时选择 `HelloWorld` 原型。
:::

## 使用 `hello-world` 原型 {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## 运行应用程序 {#running-the-app}

在运行您的应用程序之前，如果您还没有安装 [前提条件](../../introduction/prerequisites)，请先进行安装。
然后，导航到项目的根目录并运行以下命令：

```bash
# 对于标准 webforJ 应用
mvn jetty:run

# 对于 webforJ + Spring Boot
mvn spring-boot:run
```

服务器启动后，打开您的浏览器并访问 [http://localhost:8080](http://localhost:8080) 来查看应用程序。
