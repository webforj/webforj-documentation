---
title: Spring Framework
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 4ef41ed3a00ca782da0bba406fd4e902
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

许多 Java 团队已经使用 [Spring Boot](https://spring.io/projects/spring-boot) 来构建应用程序。Spring 与 webforJ 的集成现在允许您将 webforJ 的 UI 组件添加到现有的 Spring 应用程序中，或在新的 webforJ 项目中使用 Spring 的特性。

您的 Spring 服务、存储库和配置正常工作。您的 webforJ 组件可以 `@Autowired` 任何 Spring bean。 [Spring Data](https://spring.io/projects/spring-data) 存储库通过 `SpringDataRepository` 直接连接到 webforJ 表。通过 Spring DevTools 和 webforJ LiveReload 的自动浏览器刷新，开发变得更快。

这种集成让两个框架各自发挥所长 - Spring 处理后端问题，而 webforJ 处理 UI。

## Topics {#topics}

<DocCardList className="topics-section" />
