---
title: "AI Java web framework: how webforJ is built for AI work"
description: "webforJ is the AI Java web framework: assistants that know its API, an agent in your running app, MCP Apps, agents that use your UI, and browserless tests."
slug: ai-java-web-framework
date: 2026-11-03T10:00:00Z
authors: Hyyan Abo Fakher
tags: [ai, mcp, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-03-ai-java-web-framework/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-03-ai-java-web-framework/cover.jpg)

webforJ is the AI Java web framework that covers both sides of the work. While you build, AI coding assistants get the real webforJ API through a plugin, an agent inside your running app writes Java that compiles before you see it, and browserless tests give every change a fast pass or fail. In the app you ship, AI hosts can open your views with MCP Apps, agents can use your UI with exactly the user's permissions, and your own AI features stream into Java components.

All of it is Java, checked by the compiler, and rendered from the server. That is what makes AI output in a webforJ app easy to verify.

<!-- truncate -->

## Where AI meets a webforJ app {#where-ai-meets-webforj}

AI shows up in six places, three while you build and three in the app your users run.

| | Piece | What it does |
| --- | --- | --- |
| While you build | [webforJ AI plugin](/docs/ai-tooling) | Gives the assistant in your editor the real API, versions and design tokens |
| While you build | [craftforJ assistant](/docs/ai-tooling/craftforj-assistant) | An agent inside the running app that writes and compiles Java |
| While you build | [TestBed](/docs/testing/testbed/overview) | Browserless tests that check AI-written code in seconds |
| In your app | [MCP Apps](/docs/integrations/mcp-apps/overview) | AI hosts open your views inside their conversation |
| In your app | Automation layer | Agents see, find and act in your UI as the user they work for |
| In your app | Spring AI features | Your own chat, summaries and drafting, streamed into components |

The sections below take them in order.

## Assistants in your editor {#assistants-in-your-editor}

A general assistant knows Java well and any single UI framework only as far as its training data goes. It guesses method names, describes old versions, and invents CSS variables the browser silently ignores.

The [webforJ AI plugin](/docs/ai-tooling) closes that gap with two pieces in one install. The [MCP server](/docs/ai-tooling/mcp) gives the assistant tools: knowledge search scoped to your webforJ version, project scaffolding, theme generation, the styling surface of every component, and validation of `--dwc-*` tokens. [Agent Skills](/docs/ai-tooling/agent-skills) give it the playbook for when to call those tools and how to check the result, covering forms, security, styling, async work, localization, components, and upgrades.

It works with Claude Code, GitHub Copilot, VS Code, Gemini CLI, OpenAI Codex, Cursor and other clients that support Agent Skills. In Claude Code:

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

## An agent inside your running app {#agent-in-your-running-app}

An editor assistant reads your files. The [craftforJ assistant](/docs/ai-tooling/craftforj-assistant) reads your app while it runs: the live component tree with real values, the routes and their access rules, the theme, and the Java that built each component.

It writes Java freely, and every edit is staged, sent to a real Java compiler and corrected against the diagnostics before it reaches your review. You choose how much it may do on its own, from asking before every action to running autonomously. It ships no model of its own, so you bring an API key or point it at a local model, and the key never passes through your server.

craftforJ runs in development only:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Every change it makes, including the ones the assistant takes on your behalf, can be undone and redone, and the undo reaches back into the Java file too.

## Tests that check AI-written code {#tests-for-ai-written-code}

An assistant is only as good as the speed at which it can check its own work. [TestBed](/docs/testing/testbed/overview) runs a view inside a plain JUnit test, with no browser and no server, so an assistant gets a pass or a fail right after each change. The tests read like the steps a user takes:

```java
@Test
void subscribesTheTypedEmail(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class).byText("Subscribe").locate();

  UserInteraction.type(email, "hello@example.com");
  UserInteraction.click(subscribe);

  verifyThat(ComponentLocator.of(Paragraph.class).locate())
      .hasText("Subscribed hello@example.com");
}
```

A [snapshot](/docs/testing/testbed/snapshots) writes down everything a user would see in a view, one line per component, as plain text next to the test. A model reads that file the way it reads code, so it gets the exact content of a view without a browser or a screenshot, and a regression shows up as a text diff.

## AI hosts that open your views {#mcp-apps}

[MCP Apps](/docs/integrations/mcp-apps/overview) turn a routed view into something an MCP-capable AI app can open inside its own conversation. The view stays part of your Java app, with the same components, services and state, and the person and the AI work in the same live UI.

```java
@Route("inventory")
@McpApp(
    name = "inventory",
    description = "Shows the current inventory for a warehouse.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  @McpAppAction(description = "Refreshes stock levels for the open warehouse.")
  Map<String, Object> refreshStock() {
    return Map.of("refreshed", true);
  }
}
```

`@McpApp` publishes a tool that opens the view, and each `@McpAppAction` publishes another tool that works on the view while it is open. The AI can pass input when it opens the view, call actions that change it, and receive context from the choices the person makes in it. The route still works in a browser at `/inventory`. With Spring Boot, the views join a Spring AI MCP server, as the [Spring setup](/docs/integrations/mcp-apps/spring) shows. MCP Apps is marked experimental, and hosts adopt the specification at their own pace.

## Agents that use your app {#automation-layer}

The automation layer lets an AI agent use a webforJ app the way a person does. It has three abilities:

- **See.** A snapshot describes the page as plain text, one line per component, so the agent reads exactly what is on the user's screen.
- **Find.** `ComponentLocator` picks out a component the way a person does, by its type and what it shows.
- **Act.** `UserInteraction` makes the gestures of a user: clicking, typing, choosing, sorting a table.

```java
String page = Snapshot.render(view);

var export = ComponentLocator.of(Button.class).byText("Export").locate();
UserInteraction.click(export);
```

The agent acts through the UI, so it is bound by the same rules as the user it works for. A disabled button stays disabled, and clicking it fails instead of running its listener. It sees only what the user sees and can't reach anything the user's permissions keep them from. There is no back door to secure separately.

You own the loop: the model, the prompt, and when the agent runs. The automation layer supplies the tools. It is the same API TestBed uses, so code that tests a view and an agent that uses it speak the same language.

## AI features in your own app {#ai-features-in-your-app}

Some apps need AI as a feature: a chat box, a summary panel, a drafting helper. webforJ runs on Spring Boot, so your views take Spring beans in the constructor and [Spring AI](https://spring.io/projects/spring-ai) handles the model. `MarkdownViewer` renders a streamed answer progressively as chunks arrive:

```java
@Route("ask")
public class AskView extends Composite<Div> {
  private final MarkdownViewer answer = new MarkdownViewer();

  public AskView(AnswerService service) {
    answer.setAutoScroll(true).setProgressiveRender(true);
    getBoundComponent().add(answer);
    service.stream("Summarize my open orders",
        chunk -> Environment.runLater(() -> answer.append(chunk)));
  }
}
```

The model's callbacks arrive on other threads, and `Environment.runLater()` hands each chunk back to the UI thread, as the [asynchronous updates guide](/docs/advanced/asynchronous-updates) describes. For a complete streaming chat with cancel and MCP-backed answers, read [Adding an AI chat feature to a Java app](/blog/add-ai-to-java-app).

## Why the architecture suits AI {#why-the-architecture-suits-ai}

**Everything is Java.** A view, its handlers, its validation and its service calls live in one language, so an assistant finishes a feature without coordinating a template, a script, and a separate frontend project.

**The compiler checks the output.** Components are typed classes. A guessed method fails the build with an exact error, and craftforJ runs that check before it shows you a change.

**The UI lives on the server.** The component tree your Java builds is the tree an agent sees, a test inspects and an MCP host opens. No API layer sits between your data and the screen, and no second codebase has to stay in sync.

**Styling has a catalog.** The `--dwc-*` tokens are a finite, queryable set per version, so an invented name is caught by a tool rather than found on screen.

## Where to start {#where-to-start}

Pick the piece that matches your problem. Install the plugin if you want better code from the assistant you already use. Turn on craftforJ to work against the app in front of you. Add TestBed when you want fast checks. Mark a view with `@McpApp` to put it in front of an AI host.

Generate a project with [startforJ](https://docs.webforj.com/startforj), or follow the [getting started guide](/docs/introduction/getting-started).

## Frequently asked questions {#faq}

### What is the best Java framework for AI-assisted development? {#faq-best-framework}

webforJ is built for it end to end. Assistants get version-scoped knowledge and token validation through the AI plugin, craftforJ puts a compiling agent in the running app, and TestBed gives fast browserless checks, all on typed Java.

### Can an AI agent use my webforJ app? {#faq-agent-use-app}

Yes. The automation layer lets an agent read the page as text, find components by what they show, and click, type, and choose like a user. It acts through the UI, so it has exactly the permissions of the user it works for.

### What are MCP Apps in webforJ? {#faq-mcp-apps}

MCP Apps let an MCP-capable AI app open one of your routed views inside its conversation. You add `@McpApp` to the view, and `@McpAppAction` methods become tools the AI can call while the view is open.

### Can I test webforJ views without a browser? {#faq-testbed}

Yes. TestBed runs views in plain JUnit tests with no browser or server. It uses the same locators and interactions as the automation layer. Snapshots store what a view shows as text an AI model can read.
