---
title: craftforJ Assistant
sidebar_position: 2
sidebar_class_name: new-content
description: A coding agent inside your running webforJ app that writes Java freely, compiles it, and applies it with your approval.
---

The craftforJ assistant is a coding agent that works inside your **running app**. It writes Java freely, compiles what it wrote before you ever see it, applies the change, and keeps working after your app restarts. It ships with webforJ as part of [craftforJ](/docs/craftforj), the development environment that gives you the component tree, routes, live properties, and theming of an app while it runs.

## How the two compare {#how-the-two-compare}

| | [webforJ AI plugin](/docs/ai-tooling) | craftforJ assistant |
|---|---|---|
| **Lives in** | Your editor | The running app |
| **Reads** | Your source files | Your app, live, with its real values |
| **Does** | Writes code | Writes code, and inspects, changes, navigates, and themes the running app |
| **Verifies by** | Your next build | Compiling each edit before you see it, then showing you the result running |
| **Suited to** | Building something new from scratch | Understanding, fixing, building, and prototyping against the app in front of you |

The two are complementary and can hand work to each other. Once the work outgrows craftforJ, you can [hand a craftforJ conversation off](/docs/craftforj/ai#conversations) to your editor.

## What it can do {#what-it-can-do}

You give the agent a goal rather than a command. It plans, inspects whatever it needs, acts, checks the result, and corrects itself across many steps in a single turn.

It writes Java freely, so it isn't limited to the property changes you can make by hand. Each edit is staged rather than written to disk, sent to a real Java compiler, and corrected by the agent against the diagnostics that come back, so what reaches your review already compiles against your running app. Applying it restarts the app, and the agent picks its plan up again once it's back.

Alongside that it reaches everything craftforJ knows: the live component tree and real property values, your Java source, the routing table and route access rules, the theme and stylesheet, the page itself for CSS and scripts, screenshots of a component, and the webforJ knowledge base and `--dwc-*` token tools built in. See [AI Assistant](/docs/craftforj/ai) for the detail.

## Configuring a model {#configuring-a-model}

craftforJ ships no model of its own, so you choose the one that runs it. Add an API key for one of the supported providers, or point craftforJ at a model running locally with Ollama. Your key is stored on the machine running your app and held in the browser only while the page is open, and the assistant talks to your provider from the browser rather than through your server. See [Configuring a model](/docs/craftforj/ai#configuring-a-model).

:::warning AI can still make mistakes
Working against the running app and compiling its own output makes the agent considerably more accurate than one writing blind. It can still be wrong. Review what it did before you keep it.
:::

## Getting started {#getting-started}

craftforJ is disabled until you turn it on, and it runs only in development:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Open craftforJ with <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> and switch to the AI Assistant tab. For the full setup, see [Getting Started](/docs/craftforj/getting-started).
