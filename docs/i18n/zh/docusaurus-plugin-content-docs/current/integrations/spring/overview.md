---
title: Spring Framework
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: edf3c4087bb9491b2be06b67e32bb27e
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

许多 Java 团队已经使用 [Spring Boot](https://spring.io/projects/spring-boot) 来构建应用程序。现在，Spring 与 webforJ 的集成允许您将 webforJ 的 UI 组件添加到现有的 Spring 应用程序中，或在新的 webforJ 项目中使用 Spring 的功能。

您的 Spring 服务、存储库和配置正常工作。您的 webforJ 组件可以 `@Autowired` 任何 Spring bean。[Spring Data](https://spring.io/projects/spring-data) 存储库通过 `SpringDataRepository` 直接连接到 webforJ 表。借助 Spring DevTools 和 webforJ LiveReload 的自动浏览器刷新，开发速度加快。

该集成使两个框架各自发挥最佳性能——Spring 处理后端问题，而 webforJ 处理 UI。

## 主题 {#topics}

<DocCardList className="topics-section" />
