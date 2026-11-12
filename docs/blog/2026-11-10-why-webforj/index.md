---
title: "Why webforJ? Straight Answers to Questions Java Teams Ask"
description: "Why webforJ for your next business web app? What sets it apart, why we built it, who stands behind it, and how it fits your backend and your own components."
slug: why-webforj
date: 2026-11-10
authors: webforJ
tags: [insights, web development, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-10-why-webforj/cover-v2.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-10-why-webforj/cover-v2.jpg)

Your Java team has a new business web app to build, and the shortlist of frameworks keeps growing. Every option promises productivity, so the useful question is the one developers keep asking in public: why webforJ, and what do you get that you would not get elsewhere?

Here are straight answers. webforJ is the full-stack Java framework for business web apps: you build the whole app, screens included, in Java. The rest of this post shows what that means in practice, why we built it, and how it fits the backend, the tools, and the components you already have.

<!-- truncate -->

## What makes webforJ different? {#different}

Writing a web UI in Java is the starting point, not the answer. What sets webforJ apart is everything it puts around that code.

1. **Your AI assistant's work gets checked.** An AI plugin teaches coding tools the real API, browserless tests give them a pass or a fail, and an in-app assistant compiles every edit before you see it.
2. **Your desktop apps come along.** Swing, JavaFX and SWT apps run in the browser today and move to web screens one at a time.
3. **You edit the app while it runs.** Live editing changes the running app and writes the result back into your Java source.
4. **Your backend stays yours.** webforJ runs standalone or with Spring Boot, and data comes from Spring Data or from any source you choose.
5. **The whole stack is Java.** Components, routing, forms, tables, security, translation and tests come from one framework, built by one team.

The sections below go through each one, with the documentation behind it.

## Everything in one Java framework {#full-stack}

A business app needs far more than buttons. webforJ covers the list in Java:

- **Components and routing.** You compose screens from [components](/docs/building-ui/overview) and define pages with [routes](/docs/routing/overview).
- **Forms.** [Data binding](/docs/data-binding/overview) connects components to your Java models with two-way synchronization, validation and transformation.
- **Tables.** The [Table](/docs/components/table/overview) component binds an entity class to a repository to populate columns and rows.
- **Security.** [Route security](/docs/security/overview) protects views with declarative annotations and central authentication and authorization.
- **Translation.** The built-in [translation system](/docs/advanced/i18n-localization) looks up localized strings by key and detects the browser locale.
- **Testing.** The [TestBed](/docs/testing/testbed/overview) runs views in plain JUnit tests, with no browser and no server.

Because it's one framework, these pieces share the same conventions. For a deeper walk through the stack, read our guide to the [full-stack Java framework](/blog/java-full-stack-framework).

## Why we built webforJ {#why-built}

Java teams run business-critical software. They know the domain, the data and the build tools, and the browser shouldn't force them to staff a second codebase in another language. We built webforJ so that the same Java skills carry the app from the database to the screen.

That goal shaped the framework. webforJ handles the user interface in Java while your services, repositories, and configuration stay yours. With Spring on the classpath, Spring handles backend concerns and webforJ handles the screens. For standard components there is no second codebase in JavaScript to staff and maintain, so fewer moving parts need to stay current.

The same goal explains the desktop story. Many organizations have years of business logic in Java desktop apps that can't be rewritten overnight. We wanted those teams to reach the web without throwing that work away.

## Who is behind webforJ? {#who-is-behind}

webforJ is built by BASIS International. [BASIS](https://webforj.com/company) software and software tools have been used to develop business solutions for both large international corporations and SMEs for almost 40 years, with 20+ years of delivering technology built on Java.

Operating its own sites in North America and Europe and working with partners in more than 30 countries, BASIS is well positioned on the international stage.

Releases are regular and public. Version 24.00 went live on April 8, 2024, and 26.02 followed on August 31, 2026, with artifacts on Maven Central. The full case, with the release record and customer stories, is in [Is webforJ production ready](/blog/is-webforj-production-ready).

## Does it work with any backend? {#any-backend}

Yes. You choose the backend, and webforJ adapts to it.

You can run webforJ standalone, or add [Spring Boot](/docs/integrations/spring/spring-boot) through the same startforJ generator. With Spring, your components can inject any Spring bean through the constructor, and your routes keep working exactly as in a plain app.

Data follows the same freedom. [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) repositories connect to tables through `SpringDataRepository`. Data that lives elsewhere, such as a REST API or an external service, connects through a [custom data source](/docs/advanced/repository/delegating-repository) with `DelegatingRepository`, where you supply the find, count and key-lookup functions.

Nothing here ties you to one persistence layer or one service style. The wider picture, from built-in components to npm packages and the Java ecosystem, is covered in [how big the webforJ ecosystem is](/blog/webforj-ecosystem).

## Can I use my own components? {#own-components}

Yes. webforJ's own components are built on web components, so wrapping a third-party custom element uses the same mechanism in the open. With [`ElementComposite`](/docs/building-ui/element-composite) you expose the element's properties, attributes and events through a typed Java class and use it like any other component.

JavaScript isn't off limits either. You can [run client-side JavaScript from Java](/docs/building-ui/execute-javascript), and the [frontend bundler](/docs/managing-resources/bundler/overview) lets a class draw on npm packages and write components in React, Svelte or Lit from a Java project.

To see the full wrapping pattern from script loading to typed events, read [Web components in Java](/blog/web-components-in-java).

## Ready for AI-assisted development {#ai-ready}

AI coding tools are fast, and their output still needs checking. webforJ gives them three ways to check.

- **The AI plugin.** The [webforJ AI plugin](/docs/ai-tooling) installs the MCP server and Agent Skills in one step for Claude Code, Copilot, Cursor, Gemini and Codex. The MCP server gives the assistant live access to the knowledge base, and the skills teach it which workflows to follow.
- **Browserless tests.** The TestBed lets an assistant run a view in a JUnit test and get a clear pass or fail.
- **An assistant in the running app.** The [craftforJ Assistant](/docs/ai-tooling/craftforj-assistant) writes Java, compiles each edit before you see it, and applies it to the running app.

Together they form a loop of write, check, and review. We cover it in [the AI verification loop for Java](/blog/ai-verification-loop-java).

## Built for developers {#for-developers}

A framework should make the team faster on day one and keep it fast. That's why webforJ comes with tools around the code.

- **One language, one team.** The same Java developers who write the services write the screens.
- **A project generator.** [startforJ](/docs/introduction/getting-started) creates a Maven project from an archetype with the dependencies and a pre-made layout.
- **Live editing.** [craftforJ](/docs/craftforj) shows the component tree of your running app, lets you change properties live, and writes the changes you keep back into your Java source.
- **One design system.** The DWC [design system](/docs/styling/overview) ships built-in [themes](/docs/styling/themes) and an official Figma kit, so designers and developers work from the same tokens and components.

## From Swing to the web {#swing-to-web}

If your business runs on a Swing, JavaFX or SWT desktop app, you don't have to rewrite it to reach the browser. The [Webswing integration](/docs/integrations/webswing/overview) runs those apps in a web browser without changes to the source, and the `WebswingConnector` component embeds them inside a webforJ app.

From there the move is gradual. You build new screens in webforJ around the embedded app, replace old screens one at a time, and phase out the legacy app when you are ready. During the move the old and new screens stay connected, because the connector supports [two-way communication](/docs/integrations/webswing/communication): your webforJ app sends actions to the Swing app and receives its events.

The full migration path is in [Swing to web migration](/blog/swing-to-web-migration).

## Frequently asked questions {#faq}

### What makes webforJ different? {#faq-different}

webforJ is the full-stack Java framework for business web apps, and it's built for how teams work now. AI assistants get their work checked against the real API and by browserless tests, existing Swing apps move to the web one screen at a time, and the whole app, screens included, stays in Java.

### Who is behind webforJ? {#faq-who-behind}

BASIS International. BASIS software and software tools have been used to develop business solutions for both large international corporations and SMEs for almost 40 years, with 20+ years of delivering technology built on Java.

### Does webforJ lock me into one backend? {#faq-backend}

No. webforJ runs standalone or with Spring Boot, works with Spring Data repositories, and connects to any other data source through a custom repository.

### Can I use my own web components? {#faq-components}

Yes. `ElementComposite` wraps any custom element as a typed Java component, and you can also run JavaScript from Java or bundle npm packages.

### Can AI assistants build with webforJ? {#faq-ai}

Yes. The webforJ AI plugin gives assistants the MCP server and Agent Skills, and the TestBed and the craftforJ Assistant check the result.

## Start a project with startforJ {#start}

The quickest way to judge these answers is to build something. Generate a project with [startforJ](https://docs.webforj.com/startforj), follow the [getting started guide](/docs/introduction/getting-started), and build one real screen of your own app in Java.

If you are still comparing options, [Java framework for web applications](/blog/java-framework-for-web-applications) lists what to require from any of them. The decisions a team settles first are laid out in [starting a new Java project](/blog/starting-new-java-project).
