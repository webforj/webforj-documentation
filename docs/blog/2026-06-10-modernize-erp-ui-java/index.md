---
title: "Modernize an ERP UI in Java without a big-bang rewrite"
description: "Modernize your ERP UI in Java one screen at a time. Embed the legacy Swing app with webforJ and Webswing, then replace each screen with a Java web view."
slug: modernize-erp-ui-java
date: 2026-06-10
authors: webforJ
tags: [insights, modernization, webswing]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-06-10-modernize-erp-ui-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-06-10-modernize-erp-ui-java/cover.jpg)

Your ERP works. The accounting rules, the pricing logic and the warehouse flows took years to get right, and the Swing screens in front of them are what everyone wants to replace. To modernize an enterprise resource planning (ERP) UI in Java, keep the domain code, and move the screens in steps. webforJ, the full-stack Java framework, embeds the running desktop app in a web page through Webswing, so users can reach the app in a browser early, and your team then replaces each screen with a Java view.

<!-- truncate -->

If you are looking for a Java framework for ERP software, the question is rarely which framework looks best in a demo. It is which one lets a Java team keep the working system, keep shipping new screens without stopping the business. That is the job webforJ is meant to help with. [BASIS](https://webforj.com/company) software and software tools have been used to develop business solutions for both large international corporations and SMEs for almost 40 years, with 20+ years of delivering technology built on Java.

This post goes through the myths that keep legacy business app web UI projects stuck, and how webforJ answers each one.

## Myth: you must rewrite everything {#myth-rewrite}

**Fact:** you don't. Webswing is a web server technology that runs Java desktop applications (Swing, JavaFX, SWT) in a browser without changes to the original source code. It renders the desktop app on the server and streams the interface to the browser using HTML5 canvas, handling user input along the way.

webforJ adds the `WebswingConnector` component, available since 25.10. It embeds a Webswing-hosted app inside a webforJ page. The ERP screens your accountants and warehouse staff use today stay exactly as they are, and they now live inside a web app you control.

The reasons teams give for not rewriting are complex domain logic that is risky to recreate, integration with desktop-specific libraries or hardware, time and cost, and the need to keep feature parity. [Embedding](/docs/integrations/webswing/overview) addresses all four, because the old app's source does not change.

## Java framework for ERP software {#java-framework-erp}

An ERP front end is forms, dense tables, role-based access and long-lived sessions. webforJ covers all of it in Java, from the route to the button, with views, event handlers and data access in the same language, the same IDE and the same build as the rest of your system. Your Java developers keep working in Java, and nobody has to design an API between the screens and the server.

How the browser and the server talk is handled for you. Calls from the server to the client, such as `setText()`, are sent without waiting for a response, and webforJ batches them automatically. Client events, such as `Button.onClick()`, flow back to your Java code. Traffic between the two sides falls into three kinds. See [client and server interaction](/docs/architecture/client-server).

If you already run Spring Boot services, webforJ integrates with them. Your views can `@Autowired` any Spring bean, and Spring Data repositories connect directly to the webforJ `Table` through `SpringDataRepository`. See the [Spring integration](/docs/integrations/spring/overview) docs. Without Spring, webforJ runs standalone.

## Myth: embedding means a bolted-on UI {#myth-bolted-on}

**Fact:** the embedded app is a component like any other. This view places a Webswing-hosted app in a route:

```java title="SwingAppView.java"
@Route
public class SwingAppView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private WebswingConnector connector;

  public SwingAppView() {
    // Initialize the connector with your Webswing application URL
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Set the display dimensions
    connector.setSize("100%", "600px");

    // Add to the view container
    self.add(connector);
  }
}
```

The connector is two-way. `performAction()` sends a command to the Swing app, and `onAction()` receives the custom actions the Swing app sends back through the Webswing Java API. The action names and data formats are defined by your legacy app, so you choose which events cross the boundary. See [communication](/docs/integrations/webswing/communication) for lifecycle events (`onInitialize`, `onStart`) and binary payloads.

That is the seam that makes a gradual migration possible. A web toolbar, a new search box or a report can sit next to the old screen and react to what happens inside it.

## Myth: it's all or nothing {#myth-all-or-nothing}

**Fact:** the path has four steps:

1. Start by embedding your existing Swing app via `WebswingConnector`.
2. Build new features in webforJ around the embedded app.
3. Gradually replace Swing components with webforJ equivalents.
4. Eventually phase out the legacy app entirely.

Each step ships. After step one, the whole ERP is reachable in a browser. After step two, new requests from the business land in modern screens instead of piling onto the old code. Step three moves one screen at a time, picked by pain or by usage rather than by a calendar. Step four happens when the last embedded screen has a webforJ replacement, not before.

A practical order for an internal tool:

- Start with read-heavy screens such as reports and lists. They carry the least risk and the most visible improvement. The [Table](/docs/components/table/overview) component is designed to present large data sets.
- Move simple data-entry forms next.
- Leave the specialized editors and visualizations in Swing the longest. In this hybrid case, use webforJ for user-facing interfaces, dashboards and reports, and keep Swing for complex visualizations or specialized editors.

The [earlier roadmap post on Webswing and webforJ](/blog/webswing-and-webforj) goes further into planning that sequence.

## Let an AI assistant move the screens {#ai-assistant}

Screen-by-screen work is repetitive, which makes it a good fit for an AI coding agent. Point the agent at one legacy screen, describe what it does, and let it build the webforJ view. Because you move one screen at a time, every result is small enough to review and run before the next one starts.

The [webforJ AI plugin](/docs/ai-tooling) is what keeps that port correct. One install adds two pieces to Claude Code, Copilot, Cursor, Gemini and Codex:

- The [MCP server](/docs/ai-tooling/mcp) lets the agent look things up in the real webforJ docs and APIs instead of guessing method names.
- The [Agent Skills](/docs/ai-tooling/agent-skills) give it structured workflows for building forms and components, so the agent builds against the real APIs.

Review and test what the agent writes, as you would for any teammate. Your Java developers stay in charge of the decisions, and the agent does the typing.

## Myth: no shared login {#myth-login}

**Fact:** you decide where authentication lives. The Webswing options cover connection management, authentication, debugging and monitoring, so the web app can own the session while the embedded app runs behind it. On the webforJ side, the [route security](/docs/security/overview) framework (since 25.10) protects views with annotations before they render, so new screens can be protected with role-based access from the start.

One setup detail to plan for: Webswing and webforJ must run on different ports, and when they run on different origins you must configure CORS in Webswing. The [setup guide](/docs/integrations/webswing/setup) walks through the server install, the `webforj-webswing` dependency and the app registration.

## Beyond ERP: any internal tool {#internal-tools}

The same approach fits any business system. A claims platform, a warehouse console and a back-office admin panel share the shape: years of working rules, users who know the old screens by heart, and a team that can't pause feature work for a year. The business logic your Swing app calls today stays in Java, and new views reach it directly.

## Decisions at a glance {#decide}

| Question | If yes | If no |
|---|---|---|
| Is the legacy UI Swing, JavaFX or SWT? | Embed it with Webswing now | Rebuild the screens directly in webforJ |
| Do users need browser access early? | Embed first, modernize later | Start with the highest-traffic screen |
| Is there specialized desktop-only capability? | Keep it embedded longest | Replace it early |

## Start with one screen {#next-step}

Pick the screen your users complain about most, host it in Webswing and render it in a webforJ route using the preceding snippet. Follow [Getting started](/docs/introduction/getting-started) to create the webforJ project, or generate one at [startforJ](https://docs.webforj.com/startforj). Then build the next feature next to it, in Java.

## Frequently asked questions {#faq}

### How to modernize an ERP UI in Java? {#faq-modernize}

Embed the existing desktop app in a webforJ page with `WebswingConnector`, then replace screens with webforJ views one at a time. The domain code stays in Java throughout, and users get browser access at the first step.

### What is the best Java framework for ERP software? {#faq-best-framework}

webforJ is the full-stack Java framework for business web apps, built by BASIS International. It gives you routing, forms, tables, route security and Spring Boot integration in one language, plus a Webswing path for the desktop screens you already have.

### Can a legacy business app get a web UI without source changes? {#faq-no-source-change}

Yes for Swing, JavaFX and SWT apps. Webswing runs them in the browser without modifications to the original source, and webforJ embeds the result in your web app.

### Is a Java ERP front end in webforJ written in JavaScript? {#faq-javascript}

No. Views, events and data access are written in Java. webforJ handles the communication between server and browser for you.

### Which webforJ version supports Webswing? {#faq-version}

Version 25.10 or later. Add the `webforj-webswing` dependency to your `pom.xml` to get `WebswingConnector`.
