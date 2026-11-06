---
title: "What a complete Java web application platform includes"
description: "A complete Java web application platform covers setup, routing, forms, tables, security, Spring, themes, i18n, PWA and testing. See what webforJ ships for each."
slug: complete-java-web-platform
date: 2026-10-09
authors: webforJ
tags: [community, spring, security, components]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-09-complete-java-web-platform/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-09-complete-java-web-platform/cover.jpg)

When a team picks a framework for a business app, the first screen is never the hard part. The hard part is everything that comes after it: navigation, a login, forms that validate, a table that holds real data, a second language, a test suite. A complete Java web app platform answers all of that before you ask. webforJ, the full-stack Java framework, covers it. It is a single framework, in a single language, that ships project setup, routing, a component library, data binding, security, Spring Boot integration, theming, translation, installable apps, and testing.

Because webforJ is a full-stack Java framework, the screens, the rules around them and the services behind them live in one Java codebase and one Maven build, with Spring Boot integration when you have it.

<!-- truncate -->

This post is a checklist. Each section names a need that every business app has and says what webforJ ships for it, with a link to the docs page. Use it to judge whether webforJ covers your project, or hand it to anyone who asks what webforJ includes.

## Project setup {#project-setup}

A new project should start from a working structure, not a blank folder. [startforJ](https://docs.webforj.com/startforj) generates a Maven project from a chosen archetype, with dependencies, configuration and a pre-made layout included. The archetypes are Hello World, Blank, SideMenu and Tabs, so a dashboard-style shell or a tabbed app is the first thing you run, not the last thing you build. The command line works too.

See [Getting started](/docs/introduction/getting-started) and [Archetypes](/docs/building-ui/archetypes/overview).

## Routing and navigation {#routing}

Views are Java classes. You mark one with `@Route`, and webforJ handles the URL, history, and navigation. The routing system also covers route patterns, query parameters, nested route hierarchies, navigation lifecycle observers and route transitions, so deep links and the browser back button behave the way users expect.

See [Routing](/docs/routing/overview) and [Defining routes](/docs/routing/defining-routes).

## Layouts and the component library {#layouts-components}

An app shell needs a header, a side navigation, and a content area. `AppLayout` provides that frame, and `FlexLayout` and `ColumnsLayout` arrange everything inside it. The component library covers the rest of a business UI: text, number, date and time fields, combo boxes, list boxes, dialogs, drawers, tabs, toasts, a tree, an upload component, and more. All of it is composed in Java, with no HTML templates and no JavaScript to write.

See [Components overview](/docs/components/overview) and [AppLayout](/docs/components/app-layout).

## Forms, data binding and validation {#forms-binding}

Business apps are mostly forms. A `BindingContext` connects your fields to a Java bean with two-way synchronization, and transformation handles the conversion between what a field shows and what the model stores. Validation runs through built-in validators or standard Jakarta Validation annotations on the bean, and the messages are locale aware. Nested beans are validated too.

See [Data binding](/docs/data-binding/overview) and [Jakarta Validation](/docs/data-binding/validation/jakarta-validation).

## Tables and data repositories {#tables-repositories}

The `Table` component is built for large datasets, with sorting, selection, custom cell renderers, and a full set of events. It reads from a `Repository`, an abstraction that decouples the table from where the data lives, so one table works against a list in memory, a REST call or a database. For Spring Data JPA, the `SpringDataRepository` adapter brings pagination, sorting, and Specifications straight into the UI.

See [Table](/docs/components/table/overview) and [Repository](/docs/advanced/repository/overview).

## Security {#security}

webforJ includes route security. You declare access with `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` and `@DenyAll`, and the framework enforces it before the view is created. With Spring Boot on the classpath it plugs into Spring Security, including SpEL rules through `@RouteAccess` and access to the current user. Without Spring, you can build your own implementation on the same foundational interfaces. There is also guidance on common threats, secrets, and production hardening.

See [Security](/docs/security/overview) and [Annotations](/docs/security/annotations).

## Spring Boot integration {#spring-boot}

Spring beans are injected into your routes through the constructor. webforJ adds three scopes (`@WebforjSessionScope`, `@EnvironmentScope` and `@RouteScope`), runs Spring `@Async` jobs with the results marshalled back to the UI, and supports live reload through Spring DevTools. Your services, repositories and configuration stay ordinary Spring code.

See [Spring](/docs/integrations/spring/overview) and [Scopes](/docs/integrations/spring/scopes).

## Styling and themes {#styling}

Appearance is controlled through a design system of CSS variables, with color palettes, spacing, sizing, borders and shadows as tokens, and light and dark modes built in. You restyle the whole app by changing tokens, and you reach inside a component through its documented shadow parts. The CSS you write sits next to your Java as plain CSS, with no preprocessor needed.

See [Styling](/docs/styling/overview) and [CSS variables](/docs/styling/css-variables).

## Internationalization {#i18n}

Translation is part of the framework. A `BundleTranslationResolver` reads standard Java `ResourceBundle` files, the `t()` method looks up a key in the user's locale, and the browser locale is detected automatically. A custom resolver can load strings from a database instead. Components can react to locale changes at runtime.

See [Translation](/docs/advanced/i18n-localization) and [Locale management](/docs/advanced/locale-management).

## Installable apps {#installable-apps}

The `@AppProfile` annotation generates a Web App Manifest with name, icons, screenshots and metadata, so users can install the app on their home screen or app menu. Push notifications are documented alongside it for apps that need to reach users outside the browser tab.

See [Installable apps](/docs/configuration/installable-apps) and [Push notifications](/docs/advanced/push-notifications).

## Async and background updates {#async}

UI changes must run on the UI thread, and `Environment.runLater` moves the result of background work onto it. `Interval` handles periodic updates, and a debouncer delays an action until the user stops typing. Together they cover live dashboards, progress bars and search-as-you-type without raw thread handling.

See [Asynchronous updates](/docs/advanced/asynchronous-updates) and [Interval](/docs/advanced/interval).

## Testing {#testing}

Tests use the tools Java teams already know. JUnit covers logic, Selenium and Playwright drive end-to-end user journeys, a property descriptor tester checks component properties, and the Bun test runner runs frontend sources as part of the same build.

See [Testing](/docs/testing/overview).

## AI tooling {#ai-tooling}

The webforJ AI plugin installs the MCP server and the Agent Skills into Claude Code, Copilot, Cursor, Gemini, and Codex in one step. The MCP server gives an assistant live access to the webforJ knowledge base, project scaffolding and design-token validation. The skills teach it the conventions for securing, styling, localizing, and building apps.

See [AI plugin](/docs/ai-tooling), [MCP server](/docs/ai-tooling/mcp) and [Agent skills](/docs/ai-tooling/agent-skills).

## Complete web app platform checklist {#checklist-table}

| Need | What webforJ ships | Docs page |
| --- | --- | --- |
| Project setup | startforJ, archetypes | [Archetypes](/docs/building-ui/archetypes/overview) |
| Routing | `@Route`, patterns, query parameters, lifecycle | [Routing](/docs/routing/overview) |
| Layouts and components | `AppLayout`, `FlexLayout`, fields, dialogs, drawers | [AppLayout](/docs/components/app-layout) |
| Forms | `BindingContext`, Jakarta Validation | [Data binding](/docs/data-binding/overview) |
| Tables and data | `Table`, `Repository`, `SpringDataRepository` | [Table](/docs/components/table/overview) |
| Security | Route annotations, Spring Security integration | [Security](/docs/security/overview) |
| Spring Boot | Injection, scopes, background jobs, live reload | [Spring](/docs/integrations/spring/overview) |
| Styling | Design tokens, light and dark modes | [Styling](/docs/styling/overview) |
| Translation | `BundleTranslationResolver`, locale detection | [Translation](/docs/advanced/i18n-localization) |
| Installable apps | `@AppProfile`, push notifications | [Installable apps](/docs/configuration/installable-apps) |
| Async updates | `Environment.runLater`, `Interval` | [Async updates](/docs/advanced/asynchronous-updates) |
| Testing | JUnit, Selenium, Playwright, Bun | [Testing](/docs/testing/overview) |
| AI tooling | MCP server, Agent Skills, AI plugin | [AI plugin](/docs/ai-tooling) |

## Where to go next {#next-steps}

Generate a project with [startforJ](https://docs.webforj.com/startforj) and open the archetype that matches your app. The [Java full stack framework](/blog/java-full-stack-framework) post shows a slice from database to screen, and the [Java CRUD app](/blog/java-crud-app) walkthrough builds one with Spring Boot.

## Frequently asked questions {#faq}

### Does webforJ cover only the screens? {#faq-only-screens}

No. It builds the screens, and it also ships routing, security, data binding, translation and testing, and it integrates with Spring Boot for your services. A new project can run as one Maven build with one codebase from the database to the screen.

### What does webforJ include for a business web app? {#faq-what-included}

It includes project setup, routing, a component library, data binding with validation, a table with repositories, route security, Spring Boot integration, theming, translation, installable apps, async updates, testing and AI tooling. Every item in this list links to its docs page.

### Do I need other libraries for security or forms? {#faq-other-libraries}

Not for the basics. Route security and form binding are part of webforJ, and Spring Security plugs in when Spring Boot is present. You add only the libraries your own domain needs, such as a JPA provider.
