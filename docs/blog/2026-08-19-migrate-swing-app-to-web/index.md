---
title: "Java Swing to web migration: pick the right path"
description: "Java Swing to web migration compared: a JavaScript rewrite, Webswing, or a screen-by-screen rebuild in Java with webforJ, with a table to pick your path."
slug: migrate-swing-app-to-web
date: 2026-08-19
authors: webforJ
tags: [insights, modernization, webswing]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-08-19-migrate-swing-app-to-web/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-08-19-migrate-swing-app-to-web/cover.jpg)

Your Swing app still works. Users depend on it, the business rules inside it took years to get right, and now someone wants it in the browser. That is where a Java Swing to web migration starts, and most answers you find online begin with a new JavaScript front end.

To migrate a Java Swing app to web, you have three real paths and one combination: rewrite the UI in JavaScript against a new REST API, run the existing app in the browser with Webswing, or rebuild the UI screen by screen in Java with webforJ, the full-stack Java framework. For many Java teams the best long-term target is webforJ, because your developers stay in Java and your business logic carries over. The fastest route to a first browser release is often both together, Webswing first and webforJ second.

<!-- truncate -->

:::tip Try a Java-only web app
The [webforj-crud](https://github.com/webforj/built-with-webforj/tree/main/webforj-crud) app shows the kind of screen most Swing teams migrate first: a table, a search field and a form dialog with validation for adding, editing, and deleting entries. It is a Spring Boot app that runs with Java and Maven.
:::

## The paths a team really has {#paths}

Every Swing to web migration plan falls into one of these four shapes. The first three are the pure options, the fourth combines two of them.

1. **Rewrite with a JavaScript front end and a REST API.** You expose the business logic as services and build a new UI in a JavaScript framework.
2. **Run the Swing app in the browser as-is.** A server runs the desktop app and streams it to the browser. No UI code changes.
3. **Rebuild the UI in Java with webforJ.** You move one screen at a time into a Java web app and keep your domain code.
4. **Combine 2 and 3.** Users get the browser version now, and you replace screens behind the scenes.

The rest of this guide compares them on the things that decide a project: how much code you rewrite, which skills the team needs, how long users wait, and where you end up.

## Path 1: rewrite with JavaScript and REST {#javascript-rewrite}

This is the path most search results describe. You put an API in front of your services, then build every screen again in a different language, with a different component model, a different build tool and a different way of handling state.

It can work when you already have a front-end team and a clean service layer. For a Java team it means two stacks, two sets of skills and often a long stretch where nothing reaches users. The Swing screens carry hidden behavior in listeners, validation and enabled-state logic. A rewrite has to rediscover all of it, and a JSON API turns every one of those small interactions into a contract you must design and keep stable.

Choose it only if your organization has already decided on a JavaScript front end for other reasons.

## Path 2: run the Swing app in the browser {#run-swing-in-browser}

If you need to **run a Swing app in the browser** this quarter, Webswing is the direct answer. [Webswing](/docs/integrations/webswing/overview) is a web server technology that runs Java desktop apps (Swing, JavaFX, SWT) in a browser without changes to the original source code. The desktop app renders on the server and the interface streams to the browser using HTML5 canvas, with user input handled transparently.

Because the source stays untouched, it is the lowest-change first step. It also has a limit worth being honest about: you still have a Swing UI, rendered on the server and streamed to the browser as canvas output. Webswing gets you onto the web. It does not get you off Swing.

Choose it when the deadline is "users in a browser soon" and the UI itself is not what you are trying to fix.

## Path 3: rebuild the UI with webforJ {#rebuild-with-webforj}

This is the path to **rewrite a Swing UI in Java for the browser**. webforJ is a full-stack Java framework where you build the whole app in Java, screens included, with components, layouts and event listeners, and it runs in the browser as a real web app. There is no separate JavaScript project to build, and no REST layer you have to invent just to connect your own UI to your own logic.

Three things make it the natural target for a Swing team.

**Your developers keep their language.** A Swing developer already thinks in components, containers, listeners, and models. In webforJ, you create a component, set its properties, and register a listener. The vocabulary changes less than it would with any JavaScript framework.

**Your business logic stays where it is.** The domain classes, services and validation rules that have nothing to do with `javax.swing` can be called straight from the new screens. They do not need to become endpoints first.

**You migrate in slices.** A screen is the unit of work. You can ship one, watch it run, and move to the next.

For how the two sides exchange data and events during a migration, see [Swing and web communication](/blog/swing-web-communication). For the hands-on mapping of `JFrame`, layout managers, `JTable`, `JOptionPane` and `SwingWorker`, follow the [Swing to webforJ mapping guide](/blog/swing-to-web-migration). This post stays on the decision.

## Path 4: Webswing first, webforJ second {#combined-path}

The strongest plan for many teams is not a choice between 2 and 3. The webforJ Webswing integration provides the `WebswingConnector` component, which embeds a Webswing-hosted app directly inside a webforJ app. The sequence is:

1. Embed your existing Swing app with `WebswingConnector`.
2. Build new features in webforJ around the embedded app.
3. Gradually replace Swing components with webforJ equivalents.
4. Eventually phase out the legacy app entirely.

The two sides can talk. The webforJ app can send actions into the Swing app, and the Swing app can send events back:

```java
// Trigger a refresh in the Swing application from webforJ
connector.performAction("refresh");
```

So a user can sit in a new webforJ screen, click a button, and have the embedded Swing screen react, while the next screen to be migrated is still running unchanged. Nobody has to wait for the last screen before the first one ships. The [Webswing and webforJ roadmap](/blog/webswing-and-webforj) walks through this setup, and the [communication docs](/docs/integrations/webswing/communication) cover the full action and event model.

## Swing to web options by situation {#decision-table}

Use this table to match your situation to a path.

| Your situation | Best path | Why |
| --- | --- | --- |
| Users need the app in a browser within weeks, UI stays as it is | Webswing | No UI code changes |
| Java team, no JavaScript skills, UI needs to become a real web UI | webforJ | Same language, reuse domain code |
| Large app with many screens, cannot stop shipping | Webswing, then webforJ | Browser access now, replace screens over time |
| Few screens, simple forms and tables | webforJ directly | Small enough to rebuild without a bridge |
| Dedicated JavaScript front-end team and a ready REST API | JavaScript rewrite | Fits the team you already have |
| Specialized Swing editor or visualization too costly to rebuild | Webswing for that screen, webforJ elsewhere | Hybrid app with one embedded screen |

If two rows describe you, take the combined path. It is the only one where a delay in the rebuild does not delay the browser release.

## Why webforJ is the long-term target {#why-webforj}

Webswing and a JavaScript rewrite each solve one problem. Webswing solves access. A rewrite solves the UI technology, at the price of your team's language. webforJ addresses both for a Java team: the result is a browser-native UI written in the language your people already know.

A few practical consequences follow from that.

- **Hiring and onboarding.** Java developers can read the codebase. You do not need to split the team into front-end and back-end specialists to maintain it.
- **No translation layer.** Business rules are called as Java methods, not exposed as an API and called again from a second language.
- **Exit from the desktop.** After the last screen moves, the Webswing server is no longer needed. The destination is a webforJ app, not a permanent wrapper.
- **One toolchain.** Maven, your IDE, your debugger and your existing tests continue to apply.

## Let an AI assistant do the port {#ai-port}

Moving screen by screen is repetitive work, which suits an AI coding assistant. Give it one Swing screen and ask for the webforJ equivalent that calls your existing services, then review the result and move on to the next.

The assistant needs to know the real webforJ API instead of guessing at it. The [webforJ AI plugin](/docs/ai-tooling) connects your assistant to the [MCP server](/docs/ai-tooling/mcp), which looks up webforJ documentation and API details on demand, and to [Agent Skills](/docs/ai-tooling/agent-skills) that tell it how to use them in the right order. It works with Claude Code, Copilot, Cursor, Gemini, and Codex. AI can still produce incorrect code in complex scenarios, so review and test every screen before it ships. The usual review for a ported screen is the one you already do: compare behavior against the Swing original.

The assistant can also check its own work. Ask it for a test with every screen it ports, so each one comes back with a pass or a fail before you review it. That write, check, review cycle is [the AI verification loop for Java](/blog/ai-verification-loop-java), and it fits a migration well, because every screen you move is a small, testable unit.

## Pick your first screen {#next-step}

Pick one screen, ideally a simple list and edit form, and build it in webforJ. Generate a project with [startforJ](https://docs.webforj.com/startforj) or follow [getting started](/docs/introduction/getting-started). If the app is large, put Webswing in front of it first and follow the [Webswing docs](/docs/integrations/webswing/overview).

## Frequently asked questions {#faq}

### How do I migrate a Java Swing app to the web? {#faq-migrate}

Choose between a JavaScript rewrite, running the app in the browser with Webswing, or rebuilding the UI in Java with webforJ. For most Java teams, the best sequence is Webswing for immediate browser access, then webforJ screen by screen, so the business logic and the team's Java skills carry forward.

### Can I convert a Swing app to a web app without rewriting it? {#faq-convert}

Yes, in the sense of running it in a browser. Webswing runs the existing Swing, JavaFX or SWT app on a server and streams it to the browser without source changes. The interface is still the Swing interface, so a native web UI requires rebuilding screens, which webforJ lets you do one at a time.

### Do I need Webswing to move from Swing to webforJ? {#faq-webswing-needed}

No. For a small app you can rebuild the screens in webforJ directly. Webswing earns its place when the app is large and users need the browser version before the rebuild is finished.

### Can the old Swing screens and the new webforJ screens work together? {#faq-together}

Yes. The `WebswingConnector` embeds the Swing app inside a webforJ app, and the two exchange actions and events in both directions. That is what lets you replace screens gradually instead of switching everything on one day.
