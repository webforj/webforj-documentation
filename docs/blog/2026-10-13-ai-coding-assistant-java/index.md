---
title: "AI coding assistant for Java: the webforJ AI plugin"
description: "Give your AI coding assistant for Java real webforJ knowledge. One plugin adds an MCP server and Agent Skills to Claude Code, Copilot, Gemini CLI and Codex."
slug: ai-coding-assistant-java
date: 2026-10-13
authors: webforJ
tags: [ai, mcp]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-13-ai-coding-assistant-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-13-ai-coding-assistant-java/cover.jpg)

An AI coding assistant for Java web UIs works best when it is connected to the framework's own knowledge. For webforJ, that is the webforJ AI plugin. One install adds an MCP server and a set of Agent Skills to Claude Code, GitHub Copilot, VS Code, Gemini CLI, Codex, Cursor, and other clients. Your assistant then looks up real class names, scaffolds projects, generates themes and validates CSS tokens instead of guessing.

This post is the hands-on version. It shows why general assistants get Java UI code wrong, what the plugin installs, the commands to run, the prompts to type, and the kind of code that comes back.

<!-- truncate -->

## Why general assistants get Java UI code wrong {#why-assistants-get-it-wrong}

A general-purpose assistant knows Java well and a UI framework only as far as its training data goes. That gap produces three kinds of mistakes you will recognize.

- **Guessed method names.** The assistant has seen a dozen UI toolkits, so it writes the call that would exist in a neighbor's API. It compiles in the assistant's head and fails in your build.
- **Stale versions.** Training data stops at a date. A framework that ships regularly has moved on, and the assistant describes the old shape of an API as if it were current.
- **Invented CSS tokens.** Styling is the worst case. A made-up custom property such as `--brand-primary-light` does not fail. The browser ignores it, the page looks wrong, and nothing in the console tells you why.

The fix is to stop the guessing. The assistant should ask the framework and get answers grounded in the real API. That is what the plugin sets up.

## What the webforJ AI plugin installs {#what-the-plugin-installs}

The [webforJ AI plugin](/docs/ai-tooling) bundles two pieces and installs them in one step.

| Piece | What it is | What it does |
| --- | --- | --- |
| [MCP server](/docs/ai-tooling/mcp) | Tools the assistant calls on demand | Searches the knowledge base, scaffolds projects, generates themes, reads component styling surfaces, validates `--dwc-*` tokens |
| [Agent Skills](/docs/ai-tooling/agent-skills) | Workflows the assistant loads when your prompt matches | Decides when to call those tools, in what order, and how to validate the result |

The split matters. The server makes accurate information available, but on its own it does not tell the assistant when to look something up. A skill is the playbook that does. The two together turn an assistant that guesses at webforJ conventions into one that follows them.

## An AI coding assistant for Java that works across tools {#works-across-tools}

You do not need to switch editors. Skills follow the open [Agent Skills](https://agentskills.io/specification) standard, and the plugin supports Claude Code, GitHub Copilot CLI, VS Code with Copilot, Gemini CLI and OpenAI Codex CLI directly. Cursor, Kiro, Goose, Junie, Antigravity and any other Agent Skills compatible client work through manual configuration.

## Install the plugin {#install-the-plugin}

Pick the command for your client.

**Claude Code**

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Run `/plugin` and `/mcp` inside Claude Code to check. The `webforj` plugin shows up under Installed, and the server appears as `plugin:webforj:webforj-mcp`.

**GitHub Copilot CLI**

```bash
copilot plugin marketplace add webforj/webforj-ai
copilot plugin install webforj@webforj-ai
```

**Gemini CLI**

```bash
gemini extensions install https://github.com/webforj/webforj-ai
```

**OpenAI Codex CLI**

```bash
codex plugin marketplace add webforj/webforj-ai
```

Then open a Codex session, run `/plugins`, select `webforj` and press Space to enable it. In VS Code, run `Chat: Install Plugin From Source` from the command palette and paste `webforj/webforj-ai`.

Codex does not load skills by matching your prompt, so you call them by name, for example `$webforj:webforj-styling-apps theme this app with a blue palette`. MCP tools work without the prefix.

If you want only the server, point your client at `https://mcp.webforj.com/mcp`. In Claude Code that is one command:

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

The [Claude Code walkthrough](/blog/mcp) covers that route step by step. The plugin is the better default because the skills are what make the assistant reach for the server on its own.

## What the MCP server can do {#what-the-mcp-server-can-do}

When the server is connected, the assistant gains these abilities, each triggered by plain language.

- **Target the right version.** For anything version-sensitive, the assistant reads your `pom.xml` for the webforJ version, or asks you, and scopes every later answer to it. This is the direct answer to stale training data.
- **Search the knowledge base.** Queries return docs, API signatures and code samples, and can be narrowed to Java, samples, docs or the Kotlin DSL.
- **Scaffold a project.** It builds the Maven archetype command from your requirements. Archetypes are `hello-world`, `blank`, `tabs` and `sidemenu`, in a plain `webforj` flavor or `webforj-spring` for Spring Boot.
- **Generate a theme.** From one brand color it produces primary, success, warning, danger, info, default and gray palettes with text contrast, plus the `@AppTheme` and `@StyleSheet` wiring.
- **Read the styling surface of a component.** It lists the real CSS custom properties, shadow parts, attributes and slots of a `dwc-*` component before writing CSS.
- **Validate `--dwc-*` tokens.** It checks CSS, Java or Markdown against the real token catalog for your version and suggests corrections for unknown names.

That last one removes the silent-failure case from earlier. A typo or an invented token is caught before it ships.

## What the skills add {#what-the-skills-add}

Skills are loaded by the assistant when your prompt matches the task. The plugin ships eight:

- `webforj-adding-servlets` for REST endpoints, webhooks and custom servlets
- `webforj-building-forms` for binding, validation and input masks
- `webforj-creating-components` for wrapping web components or JavaScript libraries
- `webforj-handling-timers-and-async` for intervals, debouncing and background work
- `webforj-localizing-apps` for translation and locale switching
- `webforj-securing-apps` for login and role-based route access
- `webforj-styling-apps` for themes and design tokens
- `webforj-upgrading-versions` for major version upgrades with OpenRewrite

The timers skill is a good example of why a playbook matters. A raw `java.util.Timer` or a thread created outside the webforJ environment throws `IllegalStateException` the moment it touches a UI component. The skill steers the assistant to `Interval`, `Debouncer` or `Environment.runLater` before the mistake happens.

## Prompts that work {#prompts-that-work}

Name the framework. Mentioning webforJ in the prompt is the cue that sends the assistant to the plugin instead of its general Java knowledge. A few that work well:

```
Scaffold a new webforJ sidemenu project called CustomerPortal.
Generate a theme from brand color #6366f1.
What CSS variables and parts does dwc-button expose?
Validate app.css for any unknown --dwc-* tokens.
Find the webforJ docs on @Route and routing.
```

Be specific, and ask for verification when it matters. "Check this CSS for bad `--dwc-*` tokens" nudges the assistant to use the tools rather than answer from memory.

## What the assistant produces {#what-the-assistant-produces}

Take a request like this: *"In webforJ, build an invoice card view with a primary Pay button, using my brand color #6366f1."*

With the plugin, the assistant resolves your version, looks up `Composite`, `Button` and `ButtonTheme`, and writes a view like this:

```java
@Route("invoice")
@StyleSheet("ws://css/invoice.css")
public class InvoiceCardView extends Composite<Div> {

  public InvoiceCardView() {
    Button pay = new Button("Pay now", ButtonTheme.PRIMARY);
    pay.onClick(e -> pay.setText("Paid").setEnabled(false));
    getBoundComponent().addClassName("invoice-card");
    getBoundComponent().add(new H2("Invoice 1042"), new Paragraph("Due in 14 days"), pay);
  }
}
```

The stylesheet uses tokens from the catalog, checked before it is saved:

```css
:root {
  --dwc-color-primary-seed: #6366f1;
}

.invoice-card {
  padding: var(--dwc-space-m);
}
```

Overriding the seed re-derives the whole primary palette, so the Pay button picks up the brand color and dark mode keeps working. For theme switching, the app class is three annotations:

```java
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
public class ThemedApp extends App {}
```

`@AppTheme("system")` follows the user's operating system preference. The [themes guide](/docs/styling/themes) explains the pieces.

## Why Java and webforJ suit AI assistants {#why-java-suits-assistants}

Assistants are good at code when mistakes are cheap to catch, and webforJ makes them cheap.

- **One language.** The view, the event handler, the validation and the service call are all Java. The assistant does not switch between a template language, a script and a stylesheet to finish one feature.
- **A compiler as a reviewer.** A wrong method name fails the build with a precise error. You find out in seconds, and so does an agent that runs the build.
- **Components are Java classes.** `Button`, `Table` and `TextField` are types with signatures. The assistant can search them, and the validator can check the imports against the real catalog.

None of this removes the need to read the output. Even with the plugin, an assistant can produce incorrect code in complex scenarios, so review and test what it writes. The [webforJ AI policy](/blog/webforj-human-owned) takes the same position, AI-assisted and human-owned.

## Editor assistant or craftforJ assistant {#editor-or-craftforj}

webforJ ships a second assistant that lives somewhere else. The [craftforJ assistant](/docs/ai-tooling/craftforj-assistant) runs inside your running app, and the two are built for different moments.

| | webforJ AI plugin | craftforJ assistant |
| --- | --- | --- |
| Lives in | Your editor | The running app |
| Reads | Your source files | Your app, live, with real values |
| Verifies by | Your next build | Compiling each edit before you see it |
| Suited to | Building something new from scratch | Understanding, fixing and prototyping the app in front of you |

They are complementary. Start features in your editor with the plugin, and use craftforJ when you want to inspect and adjust the result while it runs. You can also hand a craftforJ conversation off to your editor when the work outgrows it.

## Next step {#next-step}

Install the plugin for your client with the preceding commands, then generate a project with [startforJ](https://docs.webforj.com/startforj) or follow the [getting started guide](/docs/introduction/getting-started). Your first prompt: "Scaffold a webforJ project called CustomerPortal using the sidemenu archetype."

## Frequently asked questions {#faq}

### What is the best AI coding assistant for Java? {#faq-best-assistant}

The assistant matters less than what it is connected to. Claude Code, Copilot, Gemini CLI, Codex and Cursor all work with the webforJ AI plugin, so you can keep the one you already use and add real framework knowledge to it.

### What is an MCP server for a Java UI framework? {#faq-mcp-server}

Model Context Protocol is an open standard that lets an assistant call external tools on demand. The webforJ MCP server exposes tools for documentation search, project scaffolding, theme generation and design token validation, so answers come from the framework instead of the model's memory.

### Do I need the plugin, or is the MCP server enough? {#faq-plugin-or-server}

The server alone works, but assistants only call it when they decide it is relevant. The plugin adds Agent Skills that tell the assistant when to use the tools, which makes the behavior consistent.

### Why isn't my assistant using the webforJ tools? {#faq-not-using-tools}

Include "webforJ" in the prompt, since that is the cue to use the plugin. For stubborn cases, ask it to use the webforJ MCP server or to verify against the webforJ docs.
