---
title: Tabs
sidebar_position: 2
hide_table_of_contents: true
_i18n_hash: bd6e6de9bb8396f7926e01ac2f34cfc3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# 标签原型

`tabs` 启动项目生成一个具有简单选项卡界面的应用程序。非常适合需要多个视图或通过选项卡访问的部分的项目，此原型提供了一种干净且有组织的方式来管理应用程序的不同部分，使用户能够轻松在各个部分之间导航，而不会使用户界面杂乱无章。

:::tip 使用 startforJ
为了获得更多的自定义和配置控制，您可以使用 [startforJ](https://docs.webforj.com/startforj/) 来创建您的项目 - 只需在选择配置选项时选择 `Tabs` 原型即可。
:::

## 使用 `tabs` 原型 {#using-the-tabs-archetype}

<ComponentArchetype
project="tabs"
/>

## 运行应用程序 {#running-the-app}

在运行应用程序之前，如果您还没有安装 [前置条件](../../introduction/prerequisites)，请先安装它们。 然后，导航到项目的根目录并运行以下命令：

```bash
# 对于标准的 webforJ 应用程序
mvn jetty:run

# 对于 webforJ + Spring Boot
mvn spring-boot:run
```

一旦服务器正在运行，打开您的浏览器并访问 [http://localhost:8080](http://localhost:8080) 来查看应用程序。
