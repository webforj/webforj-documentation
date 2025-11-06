---
title: Spring
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Many Java teams already use [Spring Boot](https://spring.io/projects/spring-boot) for building applications. Spring integration with webforJ now lets you add webforJ's UI components to existing Spring applications, or use Spring's features in new webforJ projects.

Your Spring services, repositories, and configuration work as normal. Your webforJ components can `@Autowired` any Spring bean. [Spring Data](https://spring.io/projects/spring-data) repositories connect directly to webforJ tables through `SpringDataRepository`. Development gets faster with automatic browser refresh from [Spring DevTools and webforJ LiveReload](/docs/configuration/deploy-reload/spring-devtools).

The integration keeps both frameworks doing what they do best - Spring handles backend concerns while webforJ handles the UI.

## Topics {#topics}

<DocCardList className="topics-section" />