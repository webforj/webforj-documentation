---
title: "Claude Code skills for Java: teach your agent webforJ"
description: "Install webforJ agent skills so Claude Code writes Java web UIs with the right forms, timers, route security, and styling on the first try, without guessing."
slug: ai-agent-skills-java
date: 2026-08-04
authors: webforJ
tags: [ai, tutorial, 'web development']
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-08-04-ai-agent-skills-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-08-04-ai-agent-skills-java/cover.jpg)

Ask a general AI assistant for a search field that waits until the user stops typing, and you'll often get a raw thread and a sleep call. It compiles, but it isn't how a webforJ app should do it, and you end up rewriting it by hand.

Claude Code skills for Java fix that by teaching the assistant how the framework expects things to be done. webforJ ships eight skills in its AI plugin, and Claude Code loads the right one when your prompt matches the task, so the same request comes back with a `Debouncer` instead.

<!-- truncate -->

## What a skill changes {#what-a-skill-changes}

Without a skill, an assistant guesses at framework conventions. It might create a `java.util.Timer` and update a UI component from it, which throws `IllegalStateException` at runtime.

With a skill, the assistant follows a fixed workflow:

1. It reads your prompt and picks the matching skill.
2. It checks the webforJ APIs it plans to use through the MCP server.
3. It writes code that uses webforJ's own primitives.
4. For styling work, it validates every CSS variable before handing the code back.

The skills follow the open Agent Skills standard. They work with Claude Code, GitHub Copilot, Cursor, Gemini CLI, and OpenAI Codex.

## The eight skills {#the-eight-skills}

- `webforj-adding-servlets`: REST endpoints, webhooks, and custom servlets.
- `webforj-building-forms`: data binding, Jakarta validation, and masked inputs.
- `webforj-creating-components`: wrapping web components and JavaScript libraries.
- `webforj-handling-timers-and-async`: intervals, debouncers, and background work.
- `webforj-localizing-apps`: message bundles and runtime language switching.
- `webforj-securing-apps`: login, logout, and role-based route access.
- `webforj-styling-apps`: themes and CSS built on design tokens.
- `webforj-upgrading-versions`: major version upgrades with OpenRewrite.

Each skill covers one task area. The assistant loads only the ones your prompt needs.

## Install the plugin {#install-the-plugin}

1. Install the [webforJ AI plugin](/docs/ai-tooling). It includes the skills and the [MCP server](/docs/ai-tooling/mcp) in one install.
2. If your client does not support plugins, copy the skill folders in by hand. The [webforJ AI repository](https://github.com/webforj/webforj-ai#clients) lists the folder each client reads from.
3. Open your webforJ project and describe the feature in one sentence.

The assistant does not need a special command. It loads a skill when the task matches its description.

## Prompts and the skill they trigger {#prompts-and-skills}

You don't pick a skill yourself. You describe the feature, and the matching skill loads. These four prompts come from the [agent skills docs](/docs/ai-tooling/agent-skills), each with the skill it triggers and what changes in the result:

- **"Add a search-as-you-type debouncer."** Loads `webforj-handling-timers-and-async`. The assistant uses webforJ's `Debouncer` instead of a raw thread with a sleep call, and cancels it when the view is destroyed.
- **"Build a registration form bound to my `User` bean."** Loads `webforj-building-forms`. The fields are bound to the bean with webforJ data binding and checked with Jakarta validation, instead of copying values field by field.
- **"Protect `/admin` so only users with the `ADMIN` role can see it."** Loads `webforj-securing-apps`. Access is declared on the route, so the rule lives in one place instead of inside the view.
- **"Theme this app with a blue palette."** Loads `webforj-styling-apps`. The theme is built on webforJ design tokens, and every CSS variable is validated before the code comes back.

In each case the request is one plain sentence. The skill supplies the framework knowledge the sentence leaves out.

## Checklist before you accept code {#checklist}

- [ ] The prompt names the feature, so the matching skill loads.
- [ ] Every class import resolves to a webforJ class for your version.
- [ ] Timers and debouncers are cancelled in `onDidDestroy()`.
- [ ] No `java.util.Timer` or raw thread touches a UI component.
- [ ] The project compiles before you run it.

## Write prompts that match a skill {#write-prompts}

A skill loads when your prompt matches what it handles. Mention webforJ by name. That cue tells the assistant to use the plugin instead of general Java knowledge.

1. Name the task: "Protect `/admin` so only users with the `ADMIN` role can see it."
2. Name the framework: "In webforJ, add a REST endpoint at `/api/orders`."
3. Ask for one feature per prompt, so you can review one change at a time.

Codex does not load skills by prompt match. There you call them by name, for example `$webforj:webforj-styling-apps theme this app with a blue palette`.

## Start with a working app {#start-with-a-working-app}

If you do not have a project yet, follow the [getting started guide](/docs/introduction/getting-started). Once the app runs, ask the assistant for one feature at a time. Check each change with the compiler before you ask for the next.

## Frequently asked questions {#faq}

### Do skills need one specific assistant? {#do-i-need-claude-code}

No. Skills follow the open Agent Skills standard, so GitHub Copilot, Cursor, Gemini CLI, and OpenAI Codex can load them too. Each client reads skills from its own folder. The webforJ AI repository lists those folders.

### What does the MCP server add to a skill? {#what-the-mcp-server-adds}

The MCP server gives the assistant live lookups. It can search the webforJ knowledge base, fetch full documents, and validate class names. A skill directs the assistant to make those calls and sets the order.

### Can the assistant write code for the wrong version? {#version-mismatch}

The MCP tools take a webforJ version. Tell the assistant the version your `pom.xml` uses, so class names and CSS tokens match that release. Upgrades have their own skill, `webforj-upgrading-versions`, which runs the OpenRewrite recipe.

### Do skills replace reading the docs? {#do-skills-replace-docs}

No. A skill decides how the assistant approaches a task. The docs still define the behavior. Read the feature page before you rely on a default. The [Interval page](/docs/advanced/interval) and the [asynchronous updates page](/docs/advanced/asynchronous-updates) are good starting points.

### Which skill handles login? {#which-skill-handles-login}

The `webforj-securing-apps` skill handles login, logout, and role-based access. It prefers Spring Security when Spring Boot is on the classpath. Otherwise it uses webforJ's own security framework. For the older MCP background, see [webforJ, meet Claude](/blog/mcp).
