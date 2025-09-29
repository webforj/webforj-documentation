---
title: Spring Framework
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 2bd69e8c9fad1e483d3c087f0e00e229
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

许多 Java 团队已经使用 [Spring Boot](https://spring.io/projects/spring-boot) 来构建应用程序。Spring 与 webforJ 的集成现在允许您将 webforJ 的 UI 组件添加到现有的 Spring 应用程序中，或在新的 webforJ 项目中使用 Spring 的功能。

您的 Spring 服务、存储库和配置工作如常。您的 webforJ 组件可以 `@Autowired` 任何 Spring bean。[Spring Data](https://spring.io/projects/spring-data) 存储库通过 `SpringDataRepository` 直接连接到 webforJ 表。通过 [Spring DevTools 和 webforJ LiveReload](/docs/configuration/deploy-reload/spring-devtools)，开发速度得以提升，自动浏览器刷新功能为您提供便利。

这种集成使得两个框架各自发挥最佳性能 - Spring 处理后端问题，而 webforJ 则处理 UI。

## Topics {#topics}

<DocCardList className="topics-section" />
