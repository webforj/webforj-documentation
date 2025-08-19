---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 5e7b116f0fea5cee2aa0d880d6fee05a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# 空白原型

`blank` 原型是 webforJ 应用程序的基础启动项目。此模板为您提供了一个干净的起点，可以从头开始构建您的应用程序。它非常适合希望完全控制应用程序结构和组件而没有任何预定义限制的开发人员。

:::tip 使用 startforJ
为了获得更多的定制和配置控制，您可以使用 [startforJ](https://docs.webforj.com/startforj/) 来创建您的项目——只需在选择配置选项时选择 `Blank` 原型。
:::

## 使用 `blank` 原型 {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## 运行应用程序 {#running-the-app}

在运行您的应用程序之前，如果您还没有安装 [先决条件](../../introduction/prerequisites)，请先安装。然后，导航到项目的根目录并运行以下命令：

```bash
# 用于标准的 webforJ 应用程序
mvn jetty:run

# 用于 webforJ + Spring Boot
mvn spring-boot:run
```

一旦服务器运行，打开浏览器并访问 [http://localhost:8080](http://localhost:8080) 来查看应用程序。
