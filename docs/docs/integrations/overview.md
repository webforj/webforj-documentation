---
title: Integrations
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
description: Combine webforJ with Spring Boot, Spring Data, Kotlin DSL, Webswing, JBang, and AI tooling while keeping your backend stack independent.
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ is the full-stack Java framework for business web apps, and it's backend agnostic. You build the whole app in Java, from screens and routing to forms, data binding and route security, while your persistence layer, dependency injection, services, and backend architecture stay your choice. That's why webforJ works with any Java technology stack, from traditional servlets to Spring Boot and microservices.

## Architecture philosophy {#architecture-philosophy}

webforJ covers everything your app needs to put data in front of users and act on it: components, layouts, routing, forms with binding and validation, tables backed by repositories, route security, and translation. It doesn't dictate how the rest of your system works. Your choice of persistence layer, dependency injection framework, identity provider, and service architecture remains independent of webforJ.

This approach recognizes that most organizations have established backend patterns, existing service layers, and preferred technology stacks. webforJ brings these apps to the web without requiring architectural changes or technology migrations. Your domain logic, data access patterns, and security implementations continue working exactly as before.

## Backend framework compatibility {#backend-framework-compatibility}

webforJ works with any Java backend framework or architecture pattern you already use. Whether you're building on Jakarta EE, using a microservices architecture, or working with a custom framework, webforJ connects to it without interfering with your backend design.

For certain popular frameworks, webforJ offers specific integrations that reduce boilerplate code and streamline development. These integrations provide conveniences like automatic dependency injection into UI components, simplified configuration, and framework-specific tooling support. If you don't see your framework listed below, it doesn't mean webforJ won't work with it. It means you'll configure the connection using your framework's standard patterns rather than using a pre-built integration.

The integrations below are entirely optional. They exist to improve developer experience when using specific frameworks, but webforJ's core features work identically whether you use an integration or not. Your backend framework continues to manage services, data access, and business logic while webforJ handles the screens, navigation, and user interaction.

## Topics {#topics}

<DocCardList className="topics-section" />