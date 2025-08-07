---
title: Tabs
sidebar_position: 2
hide_table_of_contents: true
_i18n_hash: ba161760eed1006a71d42f2d566aff54
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Tabs 模板

`tabs` 启动项目生成一个具有简单选项卡界面的应用程序。该模板非常适合需要通过选项卡访问多个视图或部分的项目，提供了一种干净且组织良好的方式来管理应用程序的不同部分，使用户能够轻松浏览各个部分，而不会使用户界面显得杂乱无章。

:::tip 使用 startforJ
要获得更多的自定义和配置控制，您可以使用 [startforJ](https://docs.webforj.com/startforj/) 创建您的项目 - 只需在选择配置选项时选择 `Tabs` 模板即可。
:::

## 使用 `tabs` 模板 {#using-the-tabs-archetype}

<ComponentArchetype
project="tabs"
/>

## 运行应用程序 {#running-the-app}

在运行您的应用程序之前，如果您还没有安装 [先决条件](../../introduction/prerequisites)，请先进行安装。
然后，导航到项目的根目录并运行以下命令：

```bash
# 对于标准 webforJ 应用
mvn jetty:run

# 对于 webforJ + Spring Boot
mvn spring-boot:run
```

服务器启动后，打开您的浏览器并访问 [http://localhost:8080](http://localhost:8080) 查看应用程序。
