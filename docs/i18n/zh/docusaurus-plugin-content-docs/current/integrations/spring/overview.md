---
title: Spring
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Combine webforJ UI components with Spring Boot for dependency injection,
  Spring Data repositories, custom scopes, and live reload.
_i18n_hash: 7af3520db108b976dda9856890c61979
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

许多 Java 团队已经使用 [Spring Boot](https://spring.io/projects/spring-boot) 来构建应用程序。Spring 与 webforJ 的集成现在允许您将 webforJ 的 UI 组件添加到现有的 Spring 应用程序中，或者在新的 webforJ 项目中使用 Spring 的功能。

您的 Spring 服务、仓库和配置照常工作。您的 webforJ 组件可以 `@Autowired` 任何 Spring bean。[Spring Data](https://spring.io/projects/spring-data) 仓库通过 `SpringDataRepository` 直接连接到 webforJ 表。借助 [live reload](/docs/configuration/deploy-reload/spring-devtools)，开发速度更快，它会在您更改代码时刷新浏览器。

该集成让两个框架各司其职 - Spring 处理后端问题，而 webforJ 处理 UI。

## Topics {#topics}

<DocCardList className="topics-section" />
