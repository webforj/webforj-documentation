---
title: MCP Server
sidebar_position: 2.5
sidebar_class_name: new-content
---

The webforJ Model Context Protocol (MCP) server provides AI assistants with direct access to official webforJ documentation, verified code examples, and framework-specific patterns, enabling accurate answers and automated project generation specifically for webforJ development.

## What's an MCP?

Model Context Protocol is an open standard that enables AI assistants to connect with external tools and documentation. The webforJ MCP server implements this protocol to provide:

- **Knowledge Search** - Natural language search across webforJ documentation, code examples, and patterns
- **Project Generation** - Create webforJ applications from official templates with proper structure
- **Theme Creation** - Generate accessible CSS themes following webforJ design patterns

## Why use MCP?

While AI coding assistants excel at answering basic questions, they struggle with complex webforJ-specific queries that span multiple documentation sections. Without direct access to official sources, they may:

- Generate methods that don't exist in webforJ
- Reference outdated or incorrect API patterns  
- Provide code that won't compile
- Confuse webforJ syntax with other Java frameworks
- Misunderstand webforJ-specific patterns

With MCP integration, AI responses are anchored to actual webforJ documentation, code examples, and framework patterns, providing verifiable answers with direct links to official sources for deeper exploration.

## Installation

The webforJ MCP server is hosted at `https://mcp.webforj.com` with two endpoints:

- **MCP endpoint** (`/mcp`) - For Claude, VS Code, Cursor
- **SSE endpoint** (`/sse`) - For legacy clients

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code">

Add this configuration to your VS Code settings.json file:

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
<TabItem value="cursor" label="Cursor">

Add this configuration to your Cursor settings:

```json
"mcpServers": {
  "webforj-mcp": {
    "url": "https://mcp.webforj.com/mcp"
  }
}
```

</TabItem>
<TabItem value="claude-code" label="Claude Code" default>

Use the Claude CLI command to register the server:

```bash
claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user
```

This will automatically configure the MCP server in your Claude Code environment.

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

Add this server using the Integrations panel in Claude Desktop settings:

1. Open Claude Desktop and go to Settings
2. Click on "Integrations" in the sidebar
3. Click "Add Integration" and paste the URL: `https://mcp.webforj.com/mcp`
4. Follow the setup wizard to complete the configuration

For detailed instructions, see the [official integration guide](https://support.anthropic.com/en/articles/11175166-about-custom-integrations-using-remote-mcp).

</TabItem>
<TabItem value="windsurf" label="Windsurf">

Add this server configuration to your Windsurf MCP settings:

```json
{
  "mcpServers": {
    "webforj-mcp": {
      "serverUrl": "https://mcp.webforj.com/sse"
    }
  }
}
```

</TabItem>
</Tabs>

## Available tools

Tools are specialized functions that the MCP server provides to AI assistants. When you ask a question or make a request, the AI can call these tools to search documentation, generate projects, or create themes. Each tool accepts specific parameters and returns structured data that helps the AI provide accurate, context-aware assistance.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-knowledge-base</code></strong> - Search documentation and examples
  </AccordionSummary>
  <AccordionDetails>
    <div>
      This tool provides semantic search capabilities across the entire webforJ documentation ecosystem. It understands context and relationships between different framework concepts, returning relevant documentation sections, API references, and working code examples.

      **Example queries:**
      ```
      "Search webforJ documentation for Button component with icon examples"

      "Find webforJ form validation patterns in the latest documentation"

      "Show me current webforJ routing setup with @Route annotation"

      "Search webforJ docs for FlexLayout responsive design patterns"

      "Find webforJ web component integration in official documentation"
      ```
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-project</code></strong> - Generate new webforJ projects  
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Scaffolds complete webforJ applications using official Maven archetypes. The tool creates a standardized project directory layout and includes starter code based on the selected template. Generated projects include a ready-to-use build system, resource folders, and configuration files for immediate development and deployment.

      **Example prompts:**
      ```
      "Create a webforJ project named CustomerPortal using the hello-world archetype"

      "Generate a webforJ Spring Boot project with tabs layout named Dashboard"

      "Create a new webforJ app with sidemenu archetype for AdminPanel project"

      "Generate a webforJ blank project named TestApp with com.example groupId"

      "Create webforJ project InventorySystem using sidemenu archetype with Spring Boot"
      ```

      When using this tool, you can choose from several project templates:

      **Archetypes** (project templates):
      - `hello-world` - Basic app with sample components to demonstrate webforJ features
      - `blank` - Minimal project structure for starting from scratch
      - `tabs` - Pre-built tabbed interface layout for multi-view applications
      - `sidemenu` - Side navigation menu layout for administrator panels or dashboards

      **Flavors** (framework integration):
      - `webforj` - Standard webforJ app
      - `webforj-spring` - webforJ integrated with Spring Boot for dependency injection and enterprise features

      :::tip Available Archetypes
      webforJ comes with several predefined archetypes to help you get started quickly. For a complete list of available archetypes, see the [archetypes catalog](../building-ui/archetypes/overview).
      :::
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-create-theme</code></strong> - Create accessible CSS themes
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Generates webforJ theme configurations using [DWC HueCraft](https://huecraft.dwc.style/). The tool creates complete CSS custom property sets with primary, secondary, success, warning, danger, and neutral color variants.

      **Example requests:**
      ```
      "Generate a webforJ theme with HSL 220, 70, 50 as primary color for our corporate brand"

      "Create webforJ accessible theme named 'ocean' with primary color #0066CC"

      "Generate a webforJ theme using our brand color #FF5733"

      "Create webforJ theme with HSL 30, 100, 50 named 'sunset' for our app"

      "Generate accessible webforJ theme with primary RGB 44, 123, 229"
      ```
    </div>
  </AccordionDetails>
</Accordion>

## Available prompts {#available-prompts}

Prompts are pre-configured AI instructions that combine multiple tools and workflows for common tasks. They guide the AI through specific steps and parameters to deliver reliable, repeatable outcomes for each supported workflow.

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-app</code></strong> - Create and run a webforJ app
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Arguments:**
      - `appName` (required) - Application name (e.g., MyApp, TodoList, Dashboard)
      - `archetype` (required) - Choose from: `blank`, `hello-world`, `tabs`, `sidemenu`
      - `runServer` (optional) - Automatically run the development server (yes/no)
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>create-theme</code></strong> - Generate a webforJ theme from a primary color
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Arguments:**
      - `primaryColor` (required) - Color in hex (#FF5733), rgb (255,87,51), or hsl (9,100,60) format
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>search-webforj</code></strong> - Advanced search with autonomous problem-solving
  </AccordionSummary>
  <AccordionDetails>
    <div>
      The prompt configures the AI to:

      1. Search the knowledge base extensively
      2. Write complete, production-ready code
      3. Compile the project using `mvn compile` to verify that there are no build errors
      4. Fix errors iteratively until everything works
    </div>
  </AccordionDetails>
</Accordion>

### How to use prompts

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code, Forks & Claude Code">

1. Type `/` in the chat to see available prompts
2. Select a prompt from the dropdown menu
3. Fill in the required parameters when prompted

</TabItem>
<TabItem value="claude-desktop" label="Claude Desktop">

1. Click the **+** (plus) icon in the prompt input area
2. Select **"Add from webforJ"** from the menu
3. Choose the desired prompt (e.g., `create-app`, `create-theme`, `search-webforj`)
4. Claude will prompt you to enter the required arguments
5. Fill in the parameters as requested

:::tip Verify MCP is connected
Look for the tools icon in the bottom corner of the input area to confirm the webforJ MCP server is connected.
:::

</TabItem>
</Tabs>

## Best practices

To get the most accurate and up-to-date webforJ assistance, follow these guidelines to make full use of the MCP server features.

### Ensuring MCP server usage

AI models may skip the MCP server if they believe they already know the answer. To ensure the MCP server is actually used:

- **Be explicit about webforJ**: Always mention "webforJ" in your query to trigger framework-specific searches
- **Request current information**: Include phrases like "latest webforJ documentation" or "current webforJ patterns"
- **Ask for verified examples**: Request "working webforJ code examples" to force documentation lookup
- **Reference specific versions**: Mention your webforJ version (e.g., "webforJ `25.02`") to get accurate results

### Writing clear prompts

**Good examples:**
```
"Search webforJ documentation for Button component event handling with examples"

"Create a webforJ project named InventorySystem using the sidemenu archetype with Spring Boot"

"Generate a webforJ theme with HSL 220, 70, 50 as primary color for corporate brand"
```

**Poor examples:**
```
"How do buttons work"

"Make an app"

"Make it blue"
```

### Force MCP tool usage

If the AI provides generic answers without using the MCP server:

1. Explicitly request: "Use the webforJ MCP server to search for `[query]`"
2. Ask for documentation references: "Find in webforJ docs how to `[query]`"
3. Request verification: "Verify this solution against webforJ documentation"
4. Be framework-specific: Always include "webforJ" in your queries

## AI customization {#ai-customization}

Configure your AI assistants to automatically use the MCP server and follow webforJ best practices. Add project-specific instructions so that your AI assistants always use the MCP server, follow webforJ documentation standards, and provide accurate, up-to-date answers that match your team's requirements.

### Project configuration files

<Tabs groupId="ide">
<TabItem value="vscode" label="VS Code & Copilot">

Create `.github/copilot-instructions.md`:

```markdown
## Use the webforJ MCP server to answer any webforJ questions

- Always call the "webforj-knowledge-base" tool to fetch docs relevant to the question
- Verify all API signatures against the official documentation
- Never assume method names or parameters exist without checking

Always verify code compiles with `mvn compile` before suggesting.
```

</TabItem>
<TabItem value="claude" label="Claude Code">

Create `CLAUDE.md` in your project root:

```markdown
## Use the webforJ MCP server to answer any webforJ questions

- Always call the "webforj-knowledge-base" tool to fetch docs relevant to the question
- Verify all API signatures against the official documentation
- Never assume method names or parameters exist without checking

Always verify code compiles with `mvn compile` before suggesting.
```

</TabItem>
</Tabs>


### FAQ

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Why isn't the AI using the webforJ MCP server?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Most AI assistants require explicit instructions to use MCP servers. Configure your AI client with the instructions from the [AI customization](#ai-customization) section. Without these instructions, AI assistants may default to their training data instead of querying the MCP server.

      **Quick fix:**
      Include "use webforJ MCP" in your prompt or create the appropriate configuration file (`.github/copilot-instructions.md` or `CLAUDE.md`).
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>How to verify the MCP connection is working</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Use the MCP inspector to debug connections:

      ```bash
      npx @modelcontextprotocol/inspector
      ```

      Wait for the message: `🔍 MCP Inspector is up and running at http://127.0.0.1:5274`

      Then in the inspector:
      1. Enter the MCP server URL: `https://mcp.webforj.com/mcp`
      2. Click "Connect" to establish connection
      3. View available tools and test queries
      4. Monitor request/response logs for debugging
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>What's the difference between MCP and SSE endpoints?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      The webforJ MCP server provides two endpoints:

      - **MCP endpoint** (`/mcp`) - Modern protocol for Claude, VS Code, Cursor
      - **SSE endpoint** (`/sse`) - Server-Sent Events for legacy clients like Windsurf

      Most users should use the MCP endpoint. Only use SSE if your client doesn't support the standard MCP protocol.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>Is it possible to use the MCP server without configuration files?</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Yes, but it's not recommended. Without configuration files, you must manually prompt the AI to use the MCP server in every conversation. Configuration files automatically instruct the AI to use the MCP server for every interaction, so you don't have to repeat instructions each time.

      **Manual approach:**
      Start prompts with: "Use the webforJ MCP server to..."

      **Alternative: Use pre-configured prompts**
      The MCP server provides prompts that work without configuration files:
      - `/create-app` - Generate new webforJ applications
      - `/create-theme` - Create accessible CSS themes
      - `/search-webforj` - Advanced documentation search

      See [Available prompts](#available-prompts) for details.
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>How to contribute or report issues</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      **Report issues:** [webforJ MCP Feedback](https://github.com/webforj/webforj-mcp-feedback/issues)
      
      **Common issues to report:**
      - Outdated documentation in search results
      - Missing API methods or components
      - Incorrect code examples
      - Tool execution errors

      Include your query, expected result, and actual result when reporting issues.
    </div>
  </AccordionDetails>
</Accordion>
<br />
