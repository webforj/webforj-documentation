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

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: add REST endpoints, webhooks, and custom servlets
  </AccordionSummary>
  <AccordionDetails>
    <div>

Use this when you need a non-UI HTTP path - a REST endpoint, a webhook handler, or a third-party servlet such as Swagger UI or Spring Web. The assistant picks the right approach for your project (Spring `webforj.exclude-urls`, remapping `WebforjServlet` to a sub-path, or proxying through `webforj.conf`) and wires the endpoint without disrupting webforJ's UI routing.

**When it kicks in**

- *"Add a REST endpoint at `/api/orders`."*
- *"Wire up a webhook handler for Stripe."*
- *"Mount Swagger UI at `/api/docs`."*
- *"Expose a custom servlet that runs alongside the webforJ UI."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: build forms with binding, validation, and input masks
  </AccordionSummary>
  <AccordionDetails>
    <div>

Use this for any form work in a webforJ app: data entry forms, two-way binding to a Java bean, Jakarta validation, masked input components (phone, currency, IBAN, dates), formatting Table columns as currency or percentage, and responsive multi-column layouts. The assistant routes through `BindingContext`, the `Masked*Field` components, the Table mask renderers, and `ColumnsLayout`

**When it kicks in**

- *"Build a registration form bound to my `User` bean."*
- *"Add a phone number input with format-as-you-type."*
- *"Format this Table column as currency."*
- *"Validate this field with `@NotEmpty` and a custom email checker."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: wrap web components, JS libraries, or compositions
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
    <strong><code>webforj-handling-timers-and-async</code></strong>: schedule timers, debouncers, and async work
  </AccordionSummary>
  <AccordionDetails>
    <div>

Use this for periodic tasks, polling, debounced search-as-you-type, throttling, and long-running background work that updates the UI as it runs. The assistant picks the right primitive (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) and avoids the runtime traps from raw `java.util.Timer`, `javax.swing.Timer`, or threads created outside the webforJ environment, all of which throw `IllegalStateException` the moment they touch a UI component.

**When it kicks in**

- *"Refresh this dashboard every 30 seconds."*
- *"Add a search-as-you-type debouncer."*
- *"Run this CPU-heavy work in the background and update the progress bar."*
- *"Poll this REST endpoint until it returns `done`."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: add i18n and translation support
  </AccordionSummary>
  <AccordionDetails>
    <div>

Use this for any internationalization work: loading message bundles, switching languages at runtime, auto-detecting the user's browser locale, and translating component labels. The assistant routes through webforJ 25.12's `BundleTranslationResolver`, the `HasTranslation` concern, `LocaleObserver`, and pluggable custom resolvers, and covers both Spring and plain webforJ paths.

**When it kicks in**

- *"Add multi-language support with English and Spanish."*
- *"Detect the user's browser locale and apply it on startup."*
- *"Add a language switcher to the navbar."*
- *"Move all hardcoded strings into a messages bundle."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: protect routes with login and role-based access
  </AccordionSummary>
  <AccordionDetails>
    <div>

Use this for anything that protects routes in a webforJ app: login and logout, role-based access, public landing pages, admin-only sections, ownership rules, and secure-by-default policies. The assistant prefers Spring Security when Spring Boot is on the classpath and falls back to webforJ's plain security framework otherwise. It applies the right annotations (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) and explains which are terminal versus composable so secure-by-default still does what it says.

**When it kicks in**

- *"Protect `/admin` so only users with the `ADMIN` role can see it."*
- *"Add a public landing page that anyone can visit."*
- *"Show the logged-in user's name in the header."*
- *"Only let a user edit a record they own."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: theme apps with DWC design tokens
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

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: upgrade across webforJ major versions with OpenRewrite
  </AccordionSummary>
  <AccordionDetails>
    <div>

Use this for major version upgrades. The assistant runs the official `webforj-rewrite` OpenRewrite recipe for the target version, which bumps `<webforj.version>` and the Java release, rewrites renamed APIs and types, and inserts `TODO webforJ <major>:` comments at every removed method that needs a manual decision. For older targets with no published recipe (for example 24 to 25), it walks you through the manual fallback.

**When it kicks in**

- *"Upgrade this app from webforJ 25 to 26."*
- *"Run the rewrite recipe and resolve the TODOs."*
- *"Migrate from webforJ 24 to 25 manually since there's no recipe."*
- *"What removed APIs do I need to fix after upgrading?"*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
