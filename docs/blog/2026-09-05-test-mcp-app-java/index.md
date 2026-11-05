---
title: "How to test a Java MCP App without an AI host"
description: "Test a webforJ MCP App without an AI host. Run it locally, call its tool from the MCPJam Playground, and confirm the view renders as a chat client would show it."
slug: test-mcp-app-java
date: 2026-09-05
authors: Eric Handtke
tags: [ai, mcp, spring, tutorial]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-05-test-mcp-app-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-05-test-mcp-app-java/cover.jpg)

Building a tool for an AI app has an awkward test loop. You change the code, restart, open a chat, and phrase a request so the model decides to call your tool, then hope it passes the arguments you meant. When something looks wrong, you can't tell whether the bug is in your code or in the conversation.

You can test a Java MCP App without an AI host, and skip that loop entirely. A webforJ MCP App is a view that your app's MCP server exposes as a tool. Run the app locally, connect [MCPJam](https://github.com/MCPJam/inspector) to it over HTTP, and call the tool yourself from its Playground. The view renders exactly as a chat client would show it, with no model in between.

<!-- truncate -->

## What a webforJ MCP App is {#what-an-mcp-app-is}

A typical MCP tool hands back text or JSON for the model to read. An MCP App hands back something the user can see and use: a view that opens inside the AI app's own conversation. The official MCP Apps starter templates cover JavaScript frameworks such as React, Vue and Svelte. webforJ brings MCP Apps to Java as a feature of the framework itself: the view is an ordinary routed view with one extra annotation, `@McpApp`, not a separate frontend project. It stays part of your Java app and uses the same components, services, routing and state it uses in a browser, so you don't build a second UI for the AI app.

<div class="videos-container">
  <video controls muted preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/mcp-apps/webforj-mcp-app.mp4" type="video/mp4"/>
  </video>
</div>

Spring and webforJ split the work cleanly. [Spring AI's MCP server](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html) handles the protocol: it registers tools, serves resources and talks to the AI app. What Spring doesn't give you is the interactive interface that the [MCP Apps extension](https://modelcontextprotocol.io/docs/extensions/apps) expects a server to provide. webforJ fills that gap. Its [Spring Boot integration](/docs/integrations/mcp-apps/spring) publishes your routed views through the Spring AI server, so one Java app ships both the tool and the UI the user sees in the chat.

Because the result is a real UI, it's worth seeing it render before you put it in front of a model. That's what the rest of this post sets up.

## Before you start {#before-you-start}

You need:

1. A webforJ 26.02 or later app with the [Spring Boot MCP Apps](/docs/integrations/mcp-apps/spring) setup.
2. [Node.js](https://nodejs.org/), which provides `npx` for the inspector.
3. The app running on the default port, `8080`.

## Add a view with a tool {#add-a-view-with-a-tool}

Each routed view with `@McpApp` becomes an MCP tool. The `name` sets the tool name, so this view publishes a tool called `inventory`. The tool takes no input.

```java
@Route("/inventory")
@McpApp(
    name = "inventory",
    description = "Shows the current inventory for a warehouse.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public InventoryView() {
    self.add(new H1("Inventory"));
  }
}
```

The view also works as a normal page at `/inventory` in a browser. If the tool misbehaves, open that page first. It isolates the view from the MCP client.

## Configure the local origins {#configure-the-local-origins}

Add these lines to `src/main/resources/application.properties`:

```Ini
spring.ai.mcp.server.protocol=STREAMABLE
webforj.origin=http://localhost:8080
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

`webforj.origin` sets the address the app uses for its resources. `webforj.mcp.allowed-origins` lets the inspector's browser page embed the view. The value `http://127.0.0.1:6274` is the representative origin from the docs. Local tools can pick another port, so use the address in the inspector's browser bar.

## Call the tool from MCPJam {#call-the-tool-from-mcpjam}

### Start the inspector {#start-the-inspector}

1. Start the app with your normal run workflow. The MCP endpoint is `http://localhost:8080/mcp`.
2. Run the inspector:

```bash
npx @mcpjam/inspector@latest
```

3. Open the localhost URL the inspector prints. If its port isn't `6274`, change `allowed-origins` to match and restart the app.

### Connect the server {#connect-the-server}

1. Open **Connect** and select **Add server**.
2. Enter a name, select **HTTP** as the transport, and enter `http://localhost:8080/mcp`.
3. Select **No Authentication**, then connect.

A successful connection makes the server's tools available in the inspector.

### Run the tool {#run-the-tool}

1. Open **Playground**, then open **Tools** in the left rail.
2. Set **Content Security Policy (CSP) Mode** to **Permissive**. Strict mode blocks the dynamic JavaScript evaluation that current webforJ startup uses.
3. Select `inventory` and click **Run**.
4. Confirm that the **Inventory** heading renders and that the view responds to clicks.

Use Permissive mode only with code you trust.

## Test a tool with input {#test-a-tool-with-input}

A view can accept structured input when the client opens it. Declare the input as a record. Jackson annotations add the field's required flag and description to the tool's schema.

```java
record InventoryInput(
    @JsonProperty(required = true)
    @JsonPropertyDescription("Warehouse code to show")
    String warehouseCode) {
}
```

The `@McpAppInput` method receives that record after the route renders:

```java
@Route("/warehouse")
@McpApp(
    name = "warehouse",
    description = "Shows the inventory for one warehouse.",
    displayMode = McpAppDisplayMode.INLINE)
public class WarehouseView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public WarehouseView() {
    self.add(warehouse);
  }

  @McpAppInput
  void applyOpeningInput(InventoryInput input) {
    warehouse.setText("Warehouse: " + input.warehouseCode());
  }
}
```

Before you run `warehouse`, check the tool's input schema in the inspector. It should list `warehouseCode` as required with its description. Then confirm that the paragraph shows the code you supplied.

## Debug the MCP app locally {#debug-the-mcp-app-locally}

Most failures fall into four groups:

- **Blank view or a CSP `eval` error:** switch CSP mode from Strict to Permissive.
- **Client can't connect:** confirm the app is running and the URL ends with `/mcp`.
- **Tool is listed but the view fails:** confirm `webforj.origin` matches the running app's origin.
- **Old tool metadata after a change:** reconnect the server in the inspector, then run the tool again.

## Move to a real host {#move-to-a-real-host}

The inspector runs on your machine, so it reaches `localhost`. Codex and Claude Desktop connect from outside, so they need a public HTTPS URL. A [Cloudflare Tunnel](https://developers.cloudflare.com/tunnel/setup/) gives you one:

```bash
cloudflared tunnel --url http://localhost:8080
```

Set the printed origin as `webforj.origin`, without `/mcp`, then add `/mcp` to the client URL. A quick tunnel makes the app public and gets a new hostname each time it starts, so use test data only.

## Checklist {#checklist}

- The app runs on port `8080`, and `/mcp` answers.
- `webforj.mcp.allowed-origins` matches the inspector's browser origin.
- The `inventory` tool is listed.
- CSP mode is Permissive when you run the tool.
- The **Inventory** heading renders and responds to clicks.
- The `warehouse` schema lists `warehouseCode` as required.

## Next step {#next-step}

If you don't have an app yet, start with [startforJ](https://docs.webforj.com/startforj) or the [getting started guide](/docs/introduction/getting-started). Then add the MCP App setup from the [Spring Boot guide](/docs/integrations/mcp-apps/spring) and work through the checklist.

## Frequently asked questions {#faq}

### Can I build MCP Apps in Java? {#build-mcp-apps-in-java}

Yes. With webforJ 26.02 or later, an MCP App is a routed Java view with the `@McpApp` annotation. Add the `webforj-mcp-apps` dependency and the Spring AI MCP server starter, and the view becomes a tool that an MCP Apps-capable AI app can open inside its conversation. The UI, the logic and the tool all stay in Java, with no separate frontend project. The [MCP Apps docs](/docs/integrations/mcp-apps/overview) cover the setup for Spring Boot and for plain servlet apps.

### How do I debug an MCP app locally? {#debug-an-mcp-app-locally}

Run the app on `localhost:8080`, connect the inspector over HTTP to `http://localhost:8080/mcp`, and run the tool from the Playground. Open `/inventory` in a browser to check the view without any MCP client. Use the [testing guide](/docs/integrations/mcp-apps/testing) for the full baseline.

### How does an MCP inspector test Java apps? {#mcp-inspector-test-java-apps}

An MCP inspector is a client you run on your machine to connect to an MCP server and call its tools. MCPJam connects to a webforJ app at `/mcp` over HTTP, and its Playground calls a tool directly. No chat model is involved in that step.

### Can I test MCP tools without Claude? {#test-mcp-tools-without-claude}

Yes. The local inspector needs only the running app and the `/mcp` endpoint. Claude Desktop and Codex are optional and need a public HTTPS URL through a tunnel.

### Why does the inspector show a blank view? {#why-blank-view}

A blank view with a CSP `eval` error is fixed by switching CSP mode from Strict to Permissive. Then run the tool again. If the view is still blank, check that `webforj.origin` matches the address the app runs on.

### Which setting must match the inspector? {#which-setting-must-match}

Set `webforj.mcp.allowed-origins` to the origin in the inspector's browser address bar. The tunnel address isn't a client origin, so it belongs in `webforj.origin` instead. See [MCP App configuration](/docs/integrations/mcp-apps/configuration) for the other origin settings.
