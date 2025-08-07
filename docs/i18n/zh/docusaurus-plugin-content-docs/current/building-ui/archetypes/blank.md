---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 135ed95be60a01a6a5ccb297c6bcce8f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# 空白原型

`blank` 原型是 webforJ 应用程序的基础启动项目。此模板为您从头开始构建应用程序提供了一个干净的开端。它非常适合想要对应用程序的结构和组件拥有完全控制权的开发人员，而不受任何预定义约束的影响。

:::tip 使用 startforJ
为了获得更好的自定义和配置控制，您可以使用 [startforJ](https://docs.webforj.com/startforj/) 创建您的项目 - 在选择配置选项时只需选择 `Blank` 原型。
:::

## 使用 `blank` 原型 {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
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

服务器运行后，打开您的浏览器并访问 [http://localhost:8080](http://localhost:8080) 查看应用程序。
