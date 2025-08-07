---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
_i18n_hash: c5fb775f5867b54eb53b0e1e63b90e20
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# SideMenu 原型
<!-- vale on -->

对于需要结构化导航系统的项目，`sidemenu` 原型是一个很好的起点。该原型包含一个侧边菜单和一个内容区域，旨在帮助您创建具有清晰直观导航结构的应用程序，使用户更容易找到和访问应用程序的不同部分。

:::tip 使用 startforJ
为了获得更好的定制和配置控制，您可以使用 [startforJ](https://docs.webforj.com/startforj/) 来创建项目 - 只需在选择配置选项时选择 `SideMenu` 原型即可。
:::

## 使用 `sidemenu` 原型 {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
/>

## 运行应用程序 {#running-the-app}

在运行应用程序之前，请安装 [先决条件](../../introduction/prerequisites)，如果您尚未安装。 
然后，导航到项目的根目录并运行以下命令：

```bash
# 对于标准的 webforJ 应用程序
mvn jetty:run

# 对于 webforJ + Spring Boot
mvn spring-boot:run
```

一旦服务器运行，打开您的浏览器并访问 [http://localhost:8080](http://localhost:8080) 来查看应用程序。
