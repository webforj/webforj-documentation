---
title: Integrations
hide_table_of_contents: true
sidebar_class_name: new-content
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

webforJ provides everything needed to build web applications - components, routing, data binding, and more. You don't need any other framework to create complete applications.

However, many teams already use frameworks like [Spring Boot](https://spring.io/projects/spring-boot) for dependency injection, database access, security, and other enterprise features. Rather than forcing you to choose between webforJ and your existing tools, webforJ provides integrations that let you use both together.

These integrations maintain clear separation - webforJ handles the UI layer while your chosen framework manages services, data, and business logic. Your webforJ components can access framework features when needed through dependency injection and service layers.

<DocCardList className="topics-section" />