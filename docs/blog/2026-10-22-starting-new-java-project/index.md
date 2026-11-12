---
title: "Starting a New Java Project: The Decisions That Shape the Stack"
description: "Starting a new Java project for a business web app? The decisions a team settles first, from UI and backend to security, testing, AI and upgrades."
slug: starting-new-java-project
date: 2026-10-22
authors: webforJ
tags: [insights, web development, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-22-starting-new-java-project/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-22-starting-new-java-project/cover.jpg)

A blank repository feels like freedom for about a day. Then the questions arrive: where does the UI live, who owns the front end, how do users sign in, and what happens when the framework ships a new major version? Most teams answer these one at a time, and the answers add up to the stack.

When you are starting a new Java project, settle the decisions in the order the team meets them. This post walks through that list for a business web app and gives the webforJ answer to each one. webforJ is the full-stack Java framework for business web apps: you build the whole app, screens included, in Java.

<!-- truncate -->

## What should I decide first? {#decide-first}

What should you decide before starting a new Java project? Nine things, in this order: where the UI lives, the backend, the data, security, testing, translation, how the app reaches users, how the team uses AI assistants, and how upgrades are handled. The first choice shapes most of the others, so it comes first. A table at the end of the list sums them up.

## Do I need a separate front end? {#separate-front-end}

No. Business apps can keep their screens in Java.

There are three common places for the UI to live. A separate JavaScript front end, built with a framework such as React or Angular, talks to a Java API and needs its own team, its own tooling, and its own release cycle. Server-side templates keep the code on the server but mix markup and Java. Screens written in Java put the whole app in one language and one codebase.

webforJ takes the third route. You compose screens from [components](/docs/building-ui/overview) in Java and define pages with routes, so the team that owns the backend owns the screens too. Our guide to the [full-stack Java framework](/blog/java-full-stack-framework) covers the whole picture.

[The JavaScript ecosystem stays within reach](/blog/webforj-ecosystem). The [frontend bundler](/docs/managing-resources/bundler/overview) lets a Java project draw on npm packages and write components in React, Svelte or Lit, with no Node toolchain to install.

## Is a separate front end ever better? {#when-separate}

When the product is a public site or a consumer experience where the browser does the heavy lifting. A public content site that lives on search traffic, an offline-first app, and a game or animation-heavy consumer product are better served by tools built for those jobs, because webforJ keeps the app logic on the server. Business apps are the other shape: forms, tables, roles and workflows that a Java team maintains for years. The [fit guide for business web apps](/blog/when-to-use-webforj) lists the project types webforJ suits and the three it doesn't.

## Should I use Spring Boot? {#spring-boot}

Use it if your team already does, and skip it if not. Both paths work.

webforJ runs standalone or with Spring Boot, which adds dependency injection and its ecosystem. The [startforJ](https://docs.webforj.com/startforj) wizard has a **Runs on** choice that covers both, as the [getting started guide](/docs/introduction/getting-started) explains.

With Spring, [your components can use any Spring bean](/docs/integrations/spring/overview) and Spring handles backend concerns while webforJ handles the screens.

## Where does the data come from? {#data}

From the source you already have. A repository is the contract between your data and a screen. When the data lives in a REST API, a database or an external service, [`DelegatingRepository`](/docs/advanced/repository/delegating-repository) connects it with three functions.

If the data sits behind Spring Data, `SpringDataRepository` connects those repositories to the Table component through the [Spring Data JPA integration](/docs/integrations/spring/spring-data-jpa). Forms use [data binding](/docs/data-binding/overview), which synchronizes components with a Java model in both directions and adds validation and transformation.

## How should we handle security? {#security}

Put access rules on the routes, in one place. The alternative is a permission check scattered through every view, which is where gaps appear.

[Route security](/docs/security/overview) in webforJ works with annotations that mark who may open a view, and the system checks them before any component renders. You choose between Spring Security and a plain Java implementation, so the answer holds with or without Spring.

## How do we test the app? {#testing}

With JUnit for logic and a browser tool for full journeys. The [testing guide](/docs/testing/overview) combines unit tests, end-to-end tests with Selenium or Playwright, and frontend tests for projects that author their own frontend sources.

Because the app is Java, the build that compiles it also runs its tests. No second test stack is needed for a second language.

## How do we translate the UI? {#translation}

With the built-in system, set up early so every screen uses it from the start.

The [translation system](/docs/advanced/i18n-localization) looks up localized strings by key, loads them from standard Java resource bundles and detects the browser's locale. A custom source such as a database plugs in too.

## How does the app reach users? {#reach-users}

Through the browser, and optionally as an installed app. A business app rarely needs an app store.

The [installable apps](/docs/configuration/installable-apps) feature generates a web app manifest from an annotation, so the app can be installed from supported browsers and appear on the home screen or app menu like a native app. The page lists which desktop and mobile browsers support it.

## How will the team use AI assistants? {#ai}

Connect the assistant to the framework, so it stops guessing. A general assistant knows Java well and a framework only as far as its training data goes.

The [webforJ AI plugin](/docs/ai-tooling) adds an MCP server and Agent Skills to assistants such as Claude Code, Copilot, Cursor, Gemini, and Codex in one install. The MCP server looks things up in the knowledge base and validates design tokens. The skills tell the assistant which steps to take. Our post on the [AI coding assistant for Java](/blog/ai-coding-assistant-java) shows it in use.

## How will upgrades be handled? {#upgrades}

Plan for them on day one, because a framework you adopt will keep releasing.

webforJ ships a major release each year and minor releases about every six to eight weeks, with patches for critical fixes. The [upgrading guides](/docs/upgrading/overview) describe the cadence, and [automated upgrades](/docs/upgrading/automated-upgrades) use OpenRewrite recipes to move a project off deprecated APIs and flag what needs a manual fix.

## The decisions at a glance {#at-a-glance}

| Decision | What to settle | The webforJ answer |
|---|---|---|
| UI | Separate front end, templates or Java screens | Java screens from components and routes |
| Backend | Standalone or Spring Boot | Both, chosen in startforJ |
| Data | Which source feeds screens | Repositories, Spring Data included |
| Security | Where access rules live | Route annotations, Spring Security or plain Java |
| Testing | What the build verifies | JUnit, Selenium or Playwright, frontend tests |
| Translation | How labels are localized | Built-in resource bundle system |
| Delivery | Browser only or installable | Installable from an annotation |
| AI | How assistants learn the framework | AI plugin with MCP server and Agent Skills |
| Upgrades | How versions are adopted | Regular releases and OpenRewrite recipes |

## FAQ {#faq}

### What should I decide before starting a new Java project? {#faq-decide}

Where the UI lives, the backend and data source, security, testing, translation, delivery, AI assistants and upgrades. The UI choice comes first because it shapes the rest.

### Which Java stack should I choose for a new web app? {#faq-stack}

For a business web app, choose one language end to end. webforJ is the full-stack Java framework for that: components, routing, forms, tables, security and translation in Java.

## Build one real screen {#build-one-screen}

Generate a project with [startforJ](https://docs.webforj.com/startforj), pick the screen your users open most, and build it: a table of records with an edit form. One real screen settles more of this list than any comparison will.
