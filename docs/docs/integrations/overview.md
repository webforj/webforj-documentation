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

webforJ is designed as a framework-agnostic UI layer for Java applications. It focuses exclusively on building rich, component-based user interfaces while leaving backend architecture decisions entirely up to you. This clear separation of concerns allows webforJ to work with any Java technology stack, from traditional servlets to modern microservices.

## Architecture philosophy

webforJ maintains a deliberate boundary between UI and backend concerns. Unlike full-stack frameworks that dictate your entire app structure, webforJ provides only what you need for building sophisticated user interfaces. Your choice of persistence layer, dependency injection framework, security implementation, and service architecture remains completely independent of your UI technology.

This approach recognizes that most organizations have established backend patterns, existing service layers, and preferred technology stacks. webforJ improve these applications with a modern UI framework without requiring architectural changes or technology migrations. Your domain logic, data access patterns, and security implementations continue working exactly as before.

## Backend framework compatibility

webforJ works with any Java backend framework or architecture pattern you're already using. Whether you're building on Jakarta EE, using a microservices architecture, or working with a custom framework, webforJ provides the UI layer without interfering with your backend design.

For certain popular frameworks, webforJ offers specific integrations that reduce boilerplate code and streamline development. These integrations provide conveniences like automatic dependency injection into UI components, simplified configuration, and framework-specific tooling support. If you don't see your framework listed below, it doesn't mean webforJ won't work with it - it simply means you'll configure the connection using your framework's standard patterns rather than using a pre-built integration.

The integrations below are entirely optional. They exist to improve developer experience when using specific frameworks, but webforJ's core features works identically whether you use an integration or not. Your backend framework continues to manage services, data access, and business logic while webforJ handles the presentation layer.

## Topics

<DocCardList className="topics-section" />