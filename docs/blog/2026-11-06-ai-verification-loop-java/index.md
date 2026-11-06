---
title: "The AI coding agent verification loop for Java"
description: "How webforJ closes the AI coding agent verification loop in Java: skills, MCP docs checks, compiled edits and browserless tests, installed with one plugin."
slug: ai-verification-loop-java
date: 2026-11-06
authors: webforJ
tags: [ai, mcp, integrations]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-06-ai-verification-loop-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-06-ai-verification-loop-java/cover.jpg)

You ask a coding agent for a new screen and it comes back fast, confident, and half wrong. A method that does not exist, a CSS token it made up, a build that fails on the second line, and a button that nobody ever clicked. The model is rarely the problem. The missing piece is verification.

The AI coding agent verification loop is the set of checks that turns a guess into code you can trust: write with known workflows, check every API against the real docs, compile every edit, and run a test on every change. webforJ, the full-stack Java framework, closes that whole loop with Agent Skills, an MCP server, the craftforJ Assistant and the TestBed, and one plugin installs the first two for Claude Code, Copilot, Gemini, and Codex. Cursor and other clients use manual setup. Because of that loop, webforJ is the most AI-ready framework in the Java ecosystem.

<!-- truncate -->

## The problem with unverified agents {#the-problem}

An agent that writes code without checking it is doing autocomplete with extra steps. It recalls what a class probably looks like from training data, and for a framework that changes between versions that memory is often stale.

The failures fall into four groups, and each one needs a different check:

- The agent invents or misremembers an API.
- The agent follows generic habits instead of the conventions of your framework.
- The code does not compile, and you find out when you build.
- The code compiles and still does the wrong thing, and you find out in production.

Reviewing all four by hand is the cost people complain about when they say AI code is not worth the trouble. A single check cannot remove it. A loop of checks can, because each step catches what the step before it let through. The rest of this post walks the loop in order.

## Step 1: skills write the code {#skills}

The first check happens before any code exists. [Agent Skills](/docs/ai-tooling/agent-skills) are structured webforJ workflows that the assistant loads when your prompt matches a task. They tell it how to classify the work, which patterns fit, which tools to consult and how to validate the result, so the code compiles and follows best practices on the first attempt.

The docs list skills for adding servlets, building forms, creating components, handling timers and async work, localizing apps, securing apps, styling apps, and upgrading versions. This is the difference between code that is technically valid and code that looks like it belongs in your project.

What it catches: convention drift. The agent reaches for webforJ's own approach instead of a generic one.

## Step 2: the MCP server checks every API {#mcp}

Skills say when to look something up. The [MCP server](/docs/ai-tooling/mcp) is what answers. It plugs the assistant into the webforJ documentation, APIs and design tokens, so it asks the server instead of guessing at method names.

According to the docs, the assistant can target the webforJ version you use, search the knowledge base, scaffold a project from the official Maven archetypes, generate an accessible DWC theme from a brand color, read the styling surface of a component and validate any `--dwc-*` token before it lands in your CSS.

That last one is a real example of the pattern. A token the agent invented fails validation against the catalog, so it never reaches your stylesheet.

What it catches: invented classes, wrong signatures and made-up design tokens.

## Step 3: a real compiler checks each edit {#craftforj}

Some mistakes survive a docs lookup, so the next check has to be a compiler. A type is wrong, an import is missing, a call does not fit the object it is called on. Only a compiler sees those.

The [craftforJ Assistant](/docs/ai-tooling/craftforj-assistant) is a coding agent that works inside your running app. Each edit is staged rather than written to disk, sent to a real Java compiler, and corrected by the agent against the diagnostics that come back. What reaches your review already compiles against your running app. Applying the edit restarts the app, and the agent picks its plan up again once it is back. This is its own agent, so it needs a model of your choice configured, and craftforJ runs only in development.

It also reads the live component tree, real property values, the routing table and screenshots of a component, so it works from what the app does and not from a description of it.

What it catches: compile errors, before you ever see them.

## Step 4: TestBed proves the behavior {#testbed}

Compiling is not the same as working. The last gap is behavior, and the usual answer, a browser test, is slow enough that agents and humans both skip it.

The [TestBed](/docs/testing/testbed/overview) runs a view inside a plain JUnit test. No browser is opened and no server is started. Every interaction finishes before the next line of the test runs, so nothing waits and nothing flakes. That is what makes it practical to run tests on every change an agent makes. This is the example from the getting started page:

```java title="GreetingFormTest.java"
@WebforjTest
class GreetingFormTest {
  @Test
  void shouldGreetTheNameTheUserTyped(WebforjTestbed context) {
    context.add(new GreetingForm());

    var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
    var greet = ComponentLocator.of(Button.class).byText("Greet").locate();

    UserInteraction.type(name, "Ada");
    UserInteraction.click(greet);

    verifyThat(ComponentLocator.of(Paragraph.class).locate())
        .hasText("Hello, Ada!");
  }
}
```

The test reads like the steps of a user, and `UserInteraction` only makes gestures a user could make. Clicking a disabled button fails the test instead of running its listener, which is exactly the kind of bug an unverified agent ships. Snapshots compare a whole view against a stored description of it, so an agent that changes more than you asked for shows up as a diff. The mechanics are covered in [Java UI testing without a browser](/blog/java-ui-testing-without-browser).

Browser tests still have their place for styling, layout, and client behavior. The TestBed covers the rest: what your views do when a user works with them.

What it catches: behavior that compiles and is still wrong.

## Step 5: one plugin installs the loop {#plugin}

You do not wire these pieces together yourself. The [webforJ AI plugin](/docs/ai-tooling) installs the MCP server and the Agent Skills in one step for Claude Code, GitHub Copilot, Gemini CLI and OpenAI Codex CLI. Cursor and other clients that support Agent Skills use manual configuration. The craftforJ Assistant ships with webforJ itself, so there is nothing extra to install beyond turning it on in development, and the TestBed is a test-scope dependency.

For Claude Code it is two commands:

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

## The loop at a glance {#loop-table}

| Step | What it checks | Docs page |
|---|---|---|
| Agent Skills | The agent follows webforJ workflows and conventions | [Agent Skills](/docs/ai-tooling/agent-skills) |
| MCP server | APIs, docs answers and `--dwc-*` tokens against the real sources | [MCP server](/docs/ai-tooling/mcp) |
| craftforJ Assistant | Each edit compiles with a real Java compiler | [craftforJ Assistant](/docs/ai-tooling/craftforj-assistant) |
| TestBed | Behavior, through JUnit tests with no browser and no server | [TestBed](/docs/testing/testbed/overview) |
| AI plugin | Skills and MCP installed together in one step | [AI plugin](/docs/ai-tooling) |

## Why this is a loop, not a toolbox {#why-a-loop}

Each tool alone is useful. Together they cover the failure modes in order. Skills reduce wrong approaches, the MCP server reduces wrong facts, the compiler removes wrong code, and the tests remove wrong behavior. By the time something reaches your review, the mistakes each layer targets are already filtered out.

This works because the whole app is Java. There is no template language or separate front end for the agent to get out of sync with, so the compiler and a JUnit test can see everything the agent changed. For the framework-level argument, see [why webforJ fits AI coding](/blog/java-framework-for-ai-coding).

To be clear about the limit: even with the plugin, AI assistants can produce incorrect code in complex scenarios. The loop lowers what you have to catch yourself. It does not replace your review.

## Set it up in your project {#next-step}

Install the plugin from the [AI tooling page](/docs/ai-tooling), mention webforJ in your prompts so the assistant reaches for it, and add the `webforj-testbed-junit` dependency with test scope. Then ask for one small screen and ask for a test with it. Spring Boot apps use the `webforj-spring-testbed` module instead.

## Frequently asked questions {#faq}

### What is an AI coding agent verification loop? {#faq-what-is-it}

It is the chain of checks between a prompt and merged code: the agent writes with known workflows, checks APIs against real documentation, compiles its edits, and runs tests. In webforJ those steps are Agent Skills, the MCP server, the craftforJ Assistant and the TestBed.

### How do I make AI-generated Java code reliable? {#faq-reliable}

Do not rely on one check. Ground the agent in the real docs, make it compile each edit and require a test for each change. With webforJ the AI plugin covers the first part, the craftforJ Assistant covers compiling and the TestBed covers tests.

### Can I test AI-generated code without a browser? {#faq-no-browser}

Yes. The webforJ TestBed runs views and components in plain JUnit tests without a browser or a server. The test adds the view, acts on it the way a user does and checks the result from Java.

### Which AI assistants work with the webforJ plugin? {#faq-assistants}

The plugin installs for Claude Code, GitHub Copilot, Gemini CLI and OpenAI Codex CLI. Cursor and other clients that support Agent Skills, such as Kiro, Goose, Junie and Antigravity, use manual configuration.
