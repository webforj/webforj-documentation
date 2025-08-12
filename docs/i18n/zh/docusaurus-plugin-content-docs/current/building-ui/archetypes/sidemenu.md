---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
_i18n_hash: 0d0c302e47e1711d573c9bf6860547ae
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

对于需要结构化导航系统的项目，`sidemenu` 原型是一个很好的起点。这个原型包含一个侧菜单和一个内容区域，旨在帮助您创建具有清晰直观导航结构的应用程序，使用户更容易找到和访问应用程序的不同部分。

:::tip 使用 startforJ
为了更好地控制自定义和配置，您可以使用 [startforJ](https://docs.webforj.com/startforj/) 创建您的项目 - 只需在选择配置选项时选择 `SideMenu` 原型即可。
:::

## 使用 `sidemenu` 原型 {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
/>

## 运行应用程序 {#running-the-app}

在运行您的应用程序之前，如果尚未安装 [先决条件](../../introduction/prerequisites)，请先进行安装。 
然后，导航到项目的根目录并运行以下命令：

```bash
# 对于标准的 webforJ 应用
mvn jetty:run

# 对于 webforJ + Spring Boot
mvn spring-boot:run
```

服务器启动后，打开浏览器并访问 [http://localhost:8080](http://localhost:8080) 查看应用程序。
