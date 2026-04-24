---
title: Agent Skills
sidebar_position: 10
---

Agent Skills teach AI coding assistants how to build webforJ applications using the correct APIs, design tokens, and component patterns. Instead of guessing at framework conventions, the assistant loads a skill and follows a structured workflow to produce code that compiles and follows best practices on the first attempt.

:::tip Use the plugin
The skills below ship inside the **[webforJ AI plugin](/docs/integrations/ai-tooling)** together with the [MCP server](/docs/integrations/ai-tooling/mcp). One install gives your assistant both pieces.
:::

Skills follow the open [Agent Skills](https://agentskills.io/specification) standard and work across many AI assistants, including Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex, and more. A skill tells the assistant what kind of task it handles; the assistant loads it automatically when your prompt matches. For example, asking "theme this app with a blue palette" triggers the `webforj-styling-apps` skill, which walks the assistant through looking up valid DWC tokens, writing scoped CSS, and validating every variable name before writing anything to disk.

## Why use skills? {#why-use-skills}

The MCP server makes accurate webforJ information available on demand, but on its own it doesn't tell the assistant _when_ to look something up, _which_ approach fits the task, or _what order_ to do things in. That's where skills come in.

Skills give the assistant a task-specific playbook: how to classify the work in front of it, which webforJ patterns fit, which MCP tools to consult at each step, and how to validate the output before handing it back. The result is consistent, convention-following webforJ code rather than a collection of technically valid but stylistically mismatched snippets.

## How skills differ from MCP {#how-skills-differ-from-mcp}

Skills and the [webforJ MCP server](/docs/integrations/ai-tooling/mcp) serve complementary roles. The MCP server provides live tools the assistant can call to fetch information or generate output. Skills provide the workflow that tells the assistant _when_ to reach for those tools, what order to do things in, and how to validate the result.

| | MCP server | Agent Skills |
|---|---|---|
| **What it provides** | Tools the assistant calls on demand (doc search, scaffolding, theme generation, token validation) | Workflows and decision tables that guide how the assistant approaches a task |
| **When it acts** | When the assistant decides to call a tool | Automatically, when the assistant detects a matching task |
| **Best for** | Answering specific questions, generating artifacts | End-to-end tasks that need a consistent webforJ approach |

In practice the two work best together - and the [webforJ AI plugin](https://github.com/webforj/webforj-ai) ships them as one install.

## Installation {#installation}

Install the **[webforJ AI plugin](/docs/integrations/ai-tooling)** - it ships both skills below alongside the MCP server. For clients that don't support plugins, the [webforJ AI repository](https://github.com/webforj/webforj-ai#clients) lists the skill directory each tool reads from, so you can copy the skill folders in by hand.

## Available skills {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: build reusable webforJ components from web component libraries, JavaScript libraries, or existing webforJ components
  </AccordionSummary>
  <AccordionDetails>
    <div>

Use this when you need a reusable Java component wrapped around any source - an existing Custom Element library, a plain JavaScript library, or a composition of existing webforJ components. The assistant picks the right webforJ base class for the job, wires properties, events, and slots with the correct patterns, and produces tests that follow webforJ conventions.

**When it kicks in**

- *"Wrap this Custom Element library as webforJ components."*
- *"Compose these webforJ components into a reusable card."*
- *"Integrate this plain JavaScript library as a webforJ component."*
- *"Expose this Browser API as a webforJ utility."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: theme and style webforJ applications using the DWC design-token system
  </AccordionSummary>
  <AccordionDetails>
    <div>

Use this for any visual work on a webforJ app: palette reskins, component-level styling, layout and spacing, typography, full themes, table appearance, or coordinated Google Charts colors. The assistant writes CSS that sticks to DWC design tokens, scopes selectors correctly, and validates every `--dwc-*` reference against the real catalog for your webforJ version - so dark mode and theme switching keep working.

**When it kicks in**

- *"Theme this app with a blue palette."*
- *"Style the dwc-button to match the brand guidelines."*
- *"Make this layout tighter - adjust spacing and typography."*

</div>
  </AccordionDetails>
</Accordion>
