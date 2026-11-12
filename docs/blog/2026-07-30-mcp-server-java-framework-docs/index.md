---
title: "MCP server for Java developers: webforJ setup guide"
description: "An MCP server for Java developers: connect Claude Code or VS Code to the webforJ MCP server so your assistant checks APIs against live webforJ docs."
slug: mcp-server-java-framework-docs
date: 2026-07-30
authors: Matthew Hawkins
tags: [ai, mcp]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-07-30-mcp-server-java-framework-docs/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-07-30-mcp-server-java-framework-docs/cover.jpg)

Ask an AI assistant for a Java method and it will often hand you one that sounds right and does not exist. An MCP server for Java developers fixes that. The webforJ MCP server lets AI coding assistants look up webforJ documentation, APIs and design tokens while they write code, so the assistant asks the server instead of guessing a method name. You connect it once, and it works with Claude Code, VS Code, Gemini CLI and OpenAI Codex CLI. In this guide you connect Claude Code, check an API answer against the server, and see a prompt for each job the server does.

<!-- truncate -->

## What the MCP server does {#what-the-server-does}

The Model Context Protocol is an open standard that lets AI assistants call external tools on demand. The webforJ server uses that standard for four jobs. It searches the webforJ docs and API, scaffolds new projects from the official Maven archetypes, generates DWC themes from one brand color, and validates `--dwc-*` design tokens before they reach your CSS.

This guide uses the first job most. When your assistant needs to know whether a class, method or annotation exists, it gets the answer from the same documentation you read. That matters most when an API changes between major versions, because the assistant can check the version before it writes code.

## Add the MCP server to your assistant {#connect-claude-code}

Run this command once. The `-s user` flag registers the server for every project on your machine.

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

Other clients use their own configuration format. The [MCP server page](/docs/ai-tooling/mcp) has the setup for VS Code, Gemini CLI and OpenAI Codex CLI, along with the server URL. If you want the server together with Agent Skills that teach the assistant webforJ workflows, the [webforJ AI plugin](/docs/ai-tooling) installs both in one step.

Cursor, Kiro, Goose, Junie, Antigravity and any other MCP-over-HTTP client work too. They use their own configuration format, and the server URL stays the same.

To confirm the connection works, run the MCP inspector with `npx @modelcontextprotocol/inspector`, connect it to `https://mcp.webforj.com/mcp` and look at the tools the server lists. If your assistant is connected but never calls the server, name webforJ in the prompt or say "use the webforJ MCP server to answer".

## Ask for the answer, not a guess {#ask-for-the-answer}

The assistant only calls the server when it decides the question needs it, so name webforJ in your prompt. A prompt like this one works well:

> Create a webforJ view with a text field and a button. Verify every API against the webforJ docs before you write the code.

Behind that prompt, the assistant calls `search_knowledge_base` with a query such as "Button click listener" and reads the matching signature or example. Before it writes imports, it can call `fqcn_validate`, which checks each `com.webforj` import against the catalog for the version you target. If the assistant doesn't know your version, it calls `get_versions` first. The assistant reads `pom.xml` when it's available, so keep that file in the project root.

The result is code that uses the names the docs use, not names that sound plausible. You still review it, but you spend your time on the design instead of on finding out which method exists. If you are still choosing a tool, the guide to [picking an AI coding tool for a Java team](/blog/best-ai-coding-tools-java) compares the options.

## Prompts for the other jobs {#prompts-for-the-other-jobs}

The same server handles the other three jobs. Each one starts from a plain prompt that names webforJ:

- **Start a project.** *Generate a webforJ Spring Boot project with the tabs layout named Dashboard.* The assistant builds the Maven archetype command with the right archetype, name and group, so you run one command instead of looking up the flags.
- **Generate a theme.** *Generate a webforJ theme from brand color #6366f1.* From that one color, the assistant produces a complete DWC theme with readable text contrast, plus the code that registers it in your app.
- **Check design tokens.** *Check this CSS for bad `--dwc-*` tokens.* The assistant validates every token against the catalog for your webforJ version and suggests the right name for any it doesn't recognize.

## Pitfalls to check {#pitfalls}

Check each generated view with these steps:

1. Confirm that the version in `pom.xml` matches the version the assistant looked up.
2. Compile the output. A missing import or a wrong constructor shows up at compile time.
3. Read each `com.webforj` import and confirm it's the class you expect.
4. Run the page and try each control once. A listener attached to the wrong component compiles, but nothing happens when you use it.

If the assistant reaches for a React or HTML pattern instead of a webforJ component, add "use webforJ components" to the prompt and ask it to search again. The server is most useful when the question names the framework.

## Next step {#next-step}

Create a project with [startforJ](https://docs.webforj.com/startforj) or follow the [getting started guide](/docs/introduction/getting-started). Then ask your assistant to add a view using the prompt from this guide. For the wider picture of connecting assistants to webforJ, read [webforJ, meet Claude](/blog/mcp).
