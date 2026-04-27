---
title: MCP Server
sidebar_position: 5
---

The webforJ Model Context Protocol (MCP) server plugs AI coding assistants into webforJ's documentation, APIs, design tokens, and scaffolding tools. Instead of guessing at framework conventions, the assistant asks the server and gets answers grounded in the real webforJ.

:::tip Use the plugin
Unless you know you want only the MCP server, install the **[webforJ AI plugin](/docs/integrations/ai-tooling)** instead - it bundles this server with the matching [Agent Skills](/docs/integrations/ai-tooling/agent-skills) in a single install.
:::

## What's an MCP? {#whats-an-mcp}

Model Context Protocol is an open standard that lets AI assistants call external tools on demand. The webforJ MCP server implements this protocol so your assistant can:

- Look things up in the webforJ docs instead of hallucinating method names
- Scaffold new webforJ projects from official Maven archetypes
- Generate accessible DWC themes from a brand color
- Read the real styling surface of a DWC component, and validate any `--dwc-*` token before it lands in your CSS

:::warning AI Can Still Make Mistakes
The MCP server significantly improves accuracy, but AI assistants can still produce incorrect code in complex scenarios. Always review and test generated code before shipping.
:::

## Installation {#installation}

For the full experience, install the **[webforJ AI plugin](/docs/integrations/ai-tooling)** - it configures this server alongside the Agent Skills your assistant needs to use it well.

If you want only the MCP server (no skills), point your client at `https://mcp.webforj.com/mcp`:

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

</TabItem>
<TabItem value="copilot-cli" label="GitHub Copilot CLI">

The recommended path on Copilot CLI is the **[webforJ AI plugin](/docs/integrations/ai-tooling)** - it registers the MCP server for you in one step. For a raw MCP-only setup, see the per-client instructions in the [webforJ AI repository](https://github.com/webforj/webforj-ai#clients).

</TabItem>
<TabItem value="vscode" label="VS Code + Copilot">

Add to your VS Code settings:

```json
"mcp": {
  "servers": {
    "webforj-mcp": {
      "url": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="gemini" label="Gemini CLI">

Add to `~/.gemini/settings.json`:

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "httpUrl": "https://mcp.webforj.com/mcp"
    }
  }
}
```

</TabItem>
<TabItem value="codex" label="OpenAI Codex CLI">

Add to `~/.codex/config.toml`:

```toml
[mcp_servers.webforj-mcp]
url = "https://mcp.webforj.com/mcp"
```

</TabItem>
</Tabs>

### Other clients {#other-clients}

Cursor, Kiro, Goose, Junie, Antigravity, and any other MCP-over-HTTP client work too - they just use their own configuration format. See the [per-client install guide](https://github.com/webforj/webforj-ai#clients) for the exact snippet for each.

## What the server can do {#capabilities}

When the MCP server is connected, your AI assistant gains the following capabilities. Any of them can be triggered by a natural-language request - the assistant picks the right one automatically.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Target the right webforJ version</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Before answering version-sensitive questions (anything styling or API specific), the assistant resolves which webforJ version you're on. It reads `pom.xml` when available and otherwise asks you. Every subsequent answer is scoped to that version.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Look things up in the webforJ knowledge base</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      The assistant can query the full webforJ knowledge base for answers grounded in the real framework. Results are scoped to what you ask about - an API question, a guide, a code sample, or the Kotlin DSL.

      **Example prompts:**
      ```
      "Find the webforJ Button component event handling examples"

      "How do I set up routing with @Route in webforJ?"

      "Show me a webforJ form validation sample"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Scaffold a new webforJ project</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      The assistant generates the correct Maven archetype command for a new webforJ app from your requirements (archetype, Spring integration, name, group).

      **Archetypes:**
      - `hello-world` - starter app with sample components
      - `blank` - minimal project structure
      - `tabs` - tabbed interface layout
      - `sidemenu` - side navigation layout

      **Flavors:**
      - `webforj` - standard webforJ app
      - `webforj-spring` - webforJ integrated with Spring Boot

      **Example prompts:**
      ```
      "Create a webforJ project called CustomerPortal using the sidemenu archetype"

      "Generate a webforJ Spring Boot project with the tabs layout named Dashboard"
      ```

      :::tip Available Archetypes
      For the full list of archetypes, see the [archetypes catalog](/docs/building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Generate a DWC theme</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      From a single brand color, the assistant produces a complete DWC theme: primary, success, warning, danger, info, default, and gray palettes with automatic text contrast. Output includes the style sheet plus the `@AppTheme` / `@StyleSheet` wiring.

      **Example prompts:**
      ```
      "Generate a webforJ theme from brand color #6366f1"

      "Create an accessible theme with HSL 220, 70, 50 as primary"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Style DWC components correctly</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      The assistant reads the real styling surface of each DWC component - CSS custom properties, shadow parts, reflected attributes, and slots - before writing any CSS. It can also enumerate every DWC tag and resolve webforJ Java class names (`Button`, `TextField`) to their DWC equivalents.

      **Example prompts:**
      ```
      "What CSS variables and parts does dwc-button expose?"

      "Show me every slot available on dwc-dialog"

      "Which DWC tag does the webforJ TextField class map to?"
      ```

      Pair this with the [styling-apps agent skill](/docs/integrations/ai-tooling/agent-skills) for end-to-end styling workflows.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Work with DWC design tokens</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      The assistant can list the authoritative catalog of `--dwc-*` tokens for your webforJ version - palette seeds, shades, surfaces, spacing, typography, borders - filtered by prefix or substring. It will also validate any CSS, Java, or Markdown source you give it against the real token catalog and flag unknown names with suggested corrections.

      **Example prompts:**
      ```
      "List every --dwc-space-* token"

      "Validate app.css for any unknown --dwc-* tokens"

      "What primary-palette shades are available?"
      ```

      Validation catches typos and invented tokens before they ship as silently-failing CSS.
    </div>
  </AccordionDetails>
</Accordion>

## Writing good prompts {#writing-good-prompts}

The MCP server is only consulted when your assistant thinks it's relevant. A few habits keep it engaged:

- **Name the framework.** Mention "webforJ" in the prompt so the assistant reaches for the MCP server instead of its general Java knowledge.
- **Be specific.** `"Create a webforJ project called InventorySystem with the sidemenu archetype and Spring Boot"` beats `"make an app"`.
- **Ask for verification.** Phrases like `"verify against webforJ docs"` or `"check this CSS for bad --dwc-* tokens"` nudge the assistant to use the tools instead of guessing.

If your assistant still answers without consulting the server, install the [webforJ AI plugin](https://github.com/webforj/webforj-ai) - it ships matching Agent Skills that prompt the assistant to use the MCP tools automatically for webforJ tasks.

## FAQ {#faq}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Why isn't the AI assistant using the MCP server?</p>
    <p>Why isn't the AI assistant using the MCP server?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Most assistants only reach for MCP when they think the question needs it. Two fixes:

      1. **Install the [webforJ AI plugin](https://github.com/webforj/webforj-ai)**, which pairs the server with Agent Skills that tell the assistant to use MCP for webforJ tasks.
      2. **Be explicit in your prompt**: include "webforJ" in the question, and for stubborn cases say "use the webforJ MCP server to answer".
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>How to verify the MCP connection is working?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Use the MCP inspector:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Then in the inspector, connect to `https://mcp.webforj.com/mcp` and explore the available tools.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>How to report issues?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Open a ticket using the [webforJ MCP issue template](https://github.com/webforj/webforj/issues/new?template=mcp_report.yml). Include the prompt, the expected result, and what you got.
    </div>
  </AccordionDetails>
</Accordion>
<br />
