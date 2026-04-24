---
title: webforJ AI Plugin
sidebar_position: 1
slug: /integrations/ai-tooling
sidebar_class_name: new-content
---

The **webforJ AI plugin** is the recommended way to connect your AI coding assistant to webforJ. One install gives your assistant the full toolkit: live access to webforJ docs, project scaffolding, theme generation, design-token validation, and structured workflows that teach it how to use all of it correctly.

## What you get {#what-you-get}

Installing the plugin connects two complementary pieces in a single step:

- **[webforJ MCP server](/docs/integrations/ai-tooling/mcp)** - live tools the assistant can call on demand: look things up in the webforJ knowledge base, scaffold Maven projects, generate DWC themes, read the styling surface of any DWC component, and validate `--dwc-*` tokens before they land in your CSS.
- **[Agent Skills](/docs/integrations/ai-tooling/agent-skills)** - structured workflows that tell the assistant _when_ to reach for those tools, what order to do things in, and how to validate the result. Covers building reusable components and styling webforJ apps end-to-end.

Together they turn an AI assistant that guesses at webforJ conventions into one that follows them.

:::warning AI Can Still Make Mistakes
Even with the plugin, AI assistants can produce incorrect code in complex scenarios. Always review and test generated code before shipping.
:::

## Installation {#installation}

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai
```

Verify inside Claude Code:

```
/plugin
/mcp
```

The `webforj` plugin appears under **Installed**. The MCP server appears as `plugin:webforj:webforj-mcp` under connected servers.

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

```bash
copilot plugin marketplace add webforj/webforj-ai
copilot plugin install webforj@webforj-ai
```

Verify:

```bash
copilot plugin list
```

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

From the command palette, run `Chat: Install Plugin From Source` and paste:

```
webforj/webforj-ai
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

```bash
gemini extensions install https://github.com/webforj/webforj-ai
```

Verify:

```bash
gemini extensions list
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

```bash
codex plugin marketplace add webforj/webforj-ai
```

Then open a Codex session, run `/plugins`, select `webforj`, and press **Space** to enable it.

Codex doesn't auto-load skills by prompt match like other clients. Invoke them explicitly:

```
$webforj:webforj-styling-apps theme this app with a blue palette
$webforj:webforj-creating-components wrap this Custom Element as a webforJ component
```

MCP tools work automatically without the `$` prefix.

</TabItem>
</Tabs>

### Other clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity, and any other Agent Skills compatible client support the plugin too - they just use manual configuration rather than a marketplace command. See the [per-client install guide](https://github.com/webforj/webforj-ai#clients) for the exact steps.

## Using it {#using-it}

Once installed, most assistants load the right piece automatically based on your prompt:

- *"Wrap this Custom Element library as a webforJ component."* - triggers the creating-components skill
- *"Style this view with the DWC design tokens."* - triggers the styling-apps skill
- *"Scaffold a new webforJ sidemenu project called CustomerPortal."* - calls the MCP project scaffolder
- *"Generate a theme from brand color `#6366f1`."* - calls the MCP theme generator
- *"Find the webforJ docs on `@Route` and routing."* - calls the MCP knowledge search

For best results, always mention **webforJ** in your prompts - that's the cue the assistant uses to reach for the plugin instead of general Java knowledge.

## Updating and uninstalling {#updating-and-uninstalling}

Each supported client has its own update and uninstall commands. See the [webforj-ai README](https://github.com/webforj/webforj-ai#clients) for per-client instructions.
