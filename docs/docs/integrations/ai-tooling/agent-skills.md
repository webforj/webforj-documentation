---
title: Agent Skills
sidebar_position: 10
---

Agent skills teach AI coding assistants how to build webforJ applications using the correct APIs, design tokens, and component patterns. Instead of guessing at framework conventions, an AI assistant loads a skill and follows its structured workflow to produce code that compiles and follows best practices on the first attempt.

Skills follow the open [Agent Skills](https://agentskills.io/specification) specification and work across multiple AI assistants, including Claude Code, GitHub Copilot in VS Code, and Cursor.

Agent skills for webforJ are available from the GitHub repository [webforj/webforj-agent-skills](https://github.com/webforj/webforJ-agent-skills). 

## What are agent skills? {#what-are-agent-skills}

A skill is a folder with a defined structure:

- **`SKILL.md`**: the main instruction file that describes the skill's purpose, workflow steps, and decision logic
- **`references/`**: supporting documentation the AI consults for API details, patterns, and rules
- **`scripts/`**: helper scripts the AI runs to extract component metadata or look up valid CSS variables

The AI loads these files automatically when it detects a relevant task. For example, asking an AI to "theme this app with a blue palette" triggers the `styling-apps` skill, which walks the AI through looking up valid DWC tokens, writing scoped CSS, and validating every variable name before producing output.

### Why use skills? {#why-use-skills}

Without skills, AI assistants often produce webforJ code that looks plausible but fails in practice. Common problems include:

- Inventing `--dwc-*` token names that don't exist (CSS compiles but has no effect)
- Using the wrong base class for component wrappers (`Composite` instead of `ElementComposite`, or vice versa)
- Missing `PropertyDescriptor` patterns, event annotations, or concern interfaces
- Hardcoding colors that break dark mode
- Skipping validation steps that catch silent failures

Skills eliminate these issues by giving the AI exact decision tables, lookup scripts, and validation checklists for each task type.

### How skills differ from MCP {#how-skills-differ-from-mcp}

Skills and the [webforJ MCP server](./mcp) serve complementary roles. MCP provides live tools the AI can call at runtime to search documentation or generate projects. Skills provide static knowledge and step-by-step workflows that guide how the AI approaches a task.

| | MCP server | Agent skills |
|---|---|---|
| **What it provides** | Live tools: documentation search, project scaffolding, theme generation | Static knowledge: workflows, decision tables, reference docs, helper scripts |
| **When it acts** | On demand, when the AI calls a tool | Automatically, when the AI detects a matching task |
| **Best for** | Looking up specific APIs, generating starter projects, creating theme palettes | End-to-end tasks that require following framework conventions and multi-step workflows |

In practice, the two work well together. The MCP server's `webforj-create-theme` tool generates a valid palette from a single color, and the `styling-apps` skill then guides the AI through component-level styling and dark mode validation using that palette.

Skills are static files read from disk—they don't add runtime overhead or make external API calls. The AI loads a skill's reference material into its context window when relevant, which uses some context tokens, but the resulting output quality for framework-specific work is significantly higher.

## Installation {#installation}

Clone the [webforJ agent skills repository](https://github.com/webforj/webforJ-agent-skills), then copy the skill folders into the location your AI tool expects. Each tool supports two scopes:

- **Project scope**: the skill is available only in that project
- **Personal scope**: the skill is available across all your projects

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Project scope
cp -r creating-components /path/to/your/project/.claude/skills/
cp -r styling-apps /path/to/your/project/.claude/skills/

# Personal scope
cp -r creating-components ~/.claude/skills/
cp -r styling-apps ~/.claude/skills/
```

</TabItem>
<TabItem value="vscode" label="VS Code Copilot">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Project scope
cp -r creating-components /path/to/your/project/.github/skills/
cp -r styling-apps /path/to/your/project/.github/skills/

# Personal scope
cp -r creating-components ~/.copilot/skills/
cp -r styling-apps ~/.copilot/skills/
```

</TabItem>
<TabItem value="cursor" label="Cursor">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Project scope
cp -r creating-components /path/to/your/project/.cursor/skills/
cp -r styling-apps /path/to/your/project/.cursor/skills/

# Personal scope
cp -r creating-components ~/.cursor/skills/
cp -r styling-apps ~/.cursor/skills/
```

</TabItem>
</Tabs>

:::tip[Which scope to use]
Use **project scope** when collaborating with a team so everyone on the project benefits from the same skills. Use **personal scope** when you work on multiple webforJ projects and want the skills available everywhere without copying them into each repository.
:::

## Available skills {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>creating-components</code></strong>: Build reusable webforJ components from web component libraries, JavaScript libraries, or existing webforJ components
  </AccordionSummary>
  <AccordionDetails>
    <div>

[This skill](https://github.com/webforj/webforJ-agent-skills/tree/main/creating-components) guides an AI assistant through building reusable Java components from any source, whether that's an existing web component library, a plain JavaScript library, or a composition of existing webforJ components.

**What it covers**

The skill defines five paths for creating components, and teaches the AI to select the right one based on the task:

| Path | When to use | Base class |
|---|---|---|
| Wrap an existing Custom Element library | Library ships Custom Elements (`<x-button>`, `<x-dialog>`) | `ElementComposite` / `ElementCompositeContainer` |
| Build a Custom Element, then wrap it | New visual component or wrapping a plain JS library | `ElementComposite` / `ElementCompositeContainer` |
| Compose webforJ components | Combining existing webforJ components into a reusable unit | `Composite<T>` |
| Extend an HTML element | Lightweight one-off integration with no Shadow DOM | `Div`, `Span`, etc. |
| Page-level utility | Browser API or global feature with no DOM widget | Plain Java class + `EventDispatcher` |

**Workflow**

For Custom Element wrapping (the most common path), the skill walks the AI through a structured workflow:

1. **Setup**: download third-party JS/CSS into the project's `src/main/resources/static/libs/` directory. The skill instructs the AI to prefer local resources over CDN links for offline reliability
2. **Extract component data**: use the included `extract_components.mjs` script to parse a Custom Elements Manifest and produce a structured specification of each component's properties, events, slots, and CSS custom properties
3. **Write Java wrappers**: create `ElementComposite` or `ElementCompositeContainer` classes with `PropertyDescriptor` fields, event classes, slot methods, and concern interfaces, all following webforJ conventions
4. **Write tests**: generate JUnit 5 tests using `PropertyDescriptorTester` and structured test patterns for properties, slots, and events

**Reference material**

The skill includes eight reference documents covering `ElementComposite` patterns, component composition, property descriptors, event handling, JavaScript interop, testing patterns, and common anti-patterns.

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>styling-apps</code></strong>: Theme and style webforJ applications using the DWC design-token system
  </AccordionSummary>
  <AccordionDetails>
    <div>

[This skill](https://github.com/webforj/webforJ-agent-skills/tree/main/styling-apps) teaches an AI assistant how to style webforJ applications using the DWC design-token system. The core principle is that all visual values use `--dwc-*` CSS custom properties. The skill enforces this by providing validation steps and lookup scripts that prevent the AI from inventing token names or hardcoding colors.

**What it covers**

| Task | Approach the skill teaches |
|------|---------------------------|
| Color reskin | Override palette hue, saturation, and contrast tokens at `:root` |
| Component styling | Look up the component's CSS variables first, fall back to `::part()` only when needed |
| Layout and spacing | Use `--dwc-space-*` and `--dwc-size-*` tokens |
| Typography | Use `--dwc-font-*` tokens |
| Full theme | Palette configuration with semantic token remapping |
| Table styling | `::part()` selectors only (tables expose no CSS variables) |
| Google Charts | JSON theme file loaded via `Assets.contentOf()` and Gson |

**Workflow**

The skill enforces a strict lookup-before-write discipline:

1. **Classify the task**: determine whether this is a palette reskin, component styling, layout work, or a full theme.
2. **Scan the app**: read the Java source to find every component, theme variant, and expanse in use.
3. **Look up every component**: run the included `component_styles.py` script to retrieve the exact CSS variables, `::part()` names, and reflected attributes each component supports. The AI writes no CSS until this step is complete.
4. **Write CSS**: produce nested, compact CSS that follows DWC conventions: global tokens first, then component CSS variables, then `::part()` overrides as a last resort.
5. **Validate**: re-run the lookup script and verify that every token, part name, and selector in the output actually exists. Fix anything that fails.

**Key rules the skill enforces**

- **Seven palettes only**: `primary`, `success`, `warning`, `danger`, `info`, `default`, and `gray`. Names like `secondary` or `accent` don't exist in DWC and silently fail.
- **No hardcoded colors**: every color value must be a `var()` reference, including inside `box-shadow` and `border`. Hardcoded values break dark mode.
- **CSS variables over `::part()`**: component CSS variables are the intended styling API. `::part()` is the escape hatch for cases where no variable exists.
- **Scoped selectors**: bare tag selectors on components with `theme` or `expanse` attributes override all variants. The skill requires `:not([theme])` or `[theme~="value"]` scoping.

</div>
  </AccordionDetails>
</Accordion>
