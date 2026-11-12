---
title: "MCP Apps in Java: show a webforJ UI inside AI chat"
description: "Build MCP Apps in Java with webforJ. Add @McpApp to a routed view and an MCP host such as Claude Desktop opens your Java UI inside the conversation."
slug: mcp-apps-java
date: 2026-10-17
authors: Matthew Hawkins
tags: [ai, mcp, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-17-mcp-apps-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-17-mcp-apps-java/cover.jpg)

To build an MCP app in Java with webforJ, add `@McpApp` to a routed view and an MCP host such as Claude Desktop can open that view inside its conversation, with the same components, services, and state your app already has. The AI can pass input when it opens the view, call actions on it, and receive context from what the person clicks.

An MCP server in Java usually returns text or JSON. With webforJ it can return a live interface instead. You write the view in Java, and the MCP host renders it next to the chat.

<!-- truncate -->

## What's an MCP app? {#what-is-an-mcp-app}

MCP Apps are an extension of the [Model Context Protocol](https://modelcontextprotocol.io/extensions/apps/overview). A tool declares a reference to a `ui://` resource. The host fetches that resource and renders it in a sandboxed iframe inside the conversation. The page and the host talk over a `postMessage` channel, so the UI can call tools, send messages and update the model's context.

The result is a form, dashboard or editor that appears in the chat instead of a paragraph describing one. The person can keep working in it, and the AI can keep working with it.

In webforJ you don't write the iframe page, the resource, or the tool schema by hand. The framework generates them from a routed view.

## How do you build an MCP app? {#build-an-mcp-app}

Take a normal webforJ route and add `@McpApp`. The description signals to the AI when the tool is useful. The explicit name keeps the tool name stable if you later change the route.

```java
@Route("/inventory")
@McpApp(
    name = "inventory",
    description = "Shows the current inventory for a warehouse.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  public InventoryView() {
    getBoundComponent().add(new H1("Inventory"));
  }
}
```

webforJ publishes an `inventory` MCP tool and a UI resource that opens this route. The view keeps working at `/inventory` in a normal browser, so you don't maintain a second UI for AI hosts.

`displayMode` asks the host how to present the view. `INLINE` keeps it beside the conversation, `PIP` requests picture-in-picture, and `FULLSCREEN` requests the largest presentation. `FULLSCREEN` is the default, and the host can choose a different mode based on what it supports.

Every `@McpApp` needs a nonblank description. If you leave out `name`, webforJ derives it from the route, so `/sales/inventory` becomes `sales_inventory` and the root route becomes `app`.

## How do you pass input to the app? {#pass-input}

Describe the input as one object type. Jackson annotations give the host the schema it uses to build and validate the call.

```java
record InventoryInput(
    @JsonProperty(required = true)
    @JsonPropertyDescription("Warehouse code to show")
    String warehouseCode) {
}
```

Then add one `@McpAppInput` method to the view. webforJ renders the route first and then calls the method on that view instance.

```java
@McpAppInput
void applyOpeningInput(InventoryInput input) {
  warehouse.setText("Warehouse: " + input.warehouseCode());
}
```

The generated schema marks `warehouseCode` as required and carries its description. Clear property descriptions help the AI supply the value you intend. Instead of `@McpAppInput`, you can set `input` on `@McpApp` or supply a JSON Schema through `inputSchema`. Pick one form, because combining them is rejected when the app is discovered.

The generated opening tool navigates without route parameters. A route such as `/inventory/:warehouse` can't be exposed directly, so use a parameter-free route and opening input.

## Can the AI act on an open app? {#actions}

Add `@McpAppAction` to a view method and webforJ publishes it as another MCP tool. The method body is the operation.

```java
@McpAppAction(description = "Refreshes stock levels for the open warehouse.")
Map<String, Object> refreshStock() {
  warehouse.setText(warehouse.getText() + " - refreshed");
  return Map.of("warehouse", warehouse.getText(), "refreshed", true);
}
```

For an app named `inventory`, the method `refreshStock` becomes the tool `inventory_refresh_stock`. Set `name` on the annotation to choose the part after `inventory_`. An action takes no parameter or one object parameter, and that object's properties become the tool's input schema.

The return type decides the result. A `CallToolResult` goes back as is, any other value becomes structured content, and a `void` method returns a completion message.

Actions don't open the app. A call is routed to the rendered view in the same MCP session, and if that view isn't open the call returns an error telling the client to call `inventory` first.

When one tool must take the same input as the opening tool, implement `McpAppUpdateObserver`. webforJ publishes `inventory_update` and passes its arguments to `onMcpAppUpdate`, where you decide how to apply them.

## How does the app talk to the host? {#host-interaction}

`McpHost` is the view's connection to the MCP client. The same route can open in a plain browser, so start every interaction by checking that a host is present.

```java
review.addClickListener(event -> McpHost.ifPresent(host ->
    host.sendMessage("Review the current stock for " + warehouse.getText())));
```

`sendMessage` posts the next user message into the conversation, so a button in the UI can continue the chat. To inform the model without adding a visible message, call `updateModelContext` with a map, and the updated state is available to later responses. Host calls return a `PendingResult`, so handle failure with `exceptionally` instead of blocking the UI thread.

`openLink` hands an external page to the host, and `requestDisplayMode` asks for a different presentation such as fullscreen. The host decides whether it can honor either request. Listeners such as `onToolCancelled` and `onHostContextChanged` let the view follow changes from the conversation.

Treat host features as additions. The route should stay useful in a browser or in a host that lacks a capability.

## How do you set it up with Spring? {#spring-setup}

Spring Boot with Spring AI is the primary path. Import the webforJ bill of materials, then add the Spring Boot starter, the MCP Apps module and the Spring AI WebMVC MCP server starter.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-boot-starter</artifactId>
</dependency>
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-mcp-apps</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
  <version>2.0.0</version>
</dependency>
```

Then set the transport and the app origin in `application.properties`:

```ini
spring.ai.mcp.server.protocol=STREAMABLE
webforj.origin=http://localhost:8080
```

Spring AI serves the MCP endpoint at `/mcp` by default, and webforJ leaves that path to Spring instead of treating it as a UI route. Spring auto-configuration finds the `@McpApp` routes, adds their tools and UI resources to the MCP server, and installs the servlet filters and session settings needed to embed webforJ.

`webforj.origin` is the public origin used in the app resource, content security policy, and component URLs. Don't put `/mcp` in it. Add `webforj.mcp.allowed-origins` for a client origin the framework doesn't already allow, and `webforj.mcp.resource-domains` or `webforj.mcp.connect-domains` when the frame must load from or call another origin. The [configuration page](/docs/integrations/mcp-apps/configuration) covers each one.

## Can you use MCP Apps without Spring? {#without-spring}

Yes. A servlet-based webforJ app can assemble the MCP server itself. Add `webforj-mcp-apps` and the Jakarta Servlet API with `provided` scope, then write a `ServletContainerInitializer`. It calls `McpAppContribution.ofPackages(...)` with the package that holds your routed views, builds a Streamable HTTP server mounted at `/mcp` from `getToolSpecifications()` and `getResourceSpecifications()`, and finishes with `contribution.install(context, options)`. A [plain Java MCP app with no Spring Boot](/blog/mcp-app-without-spring) covers the full setup.

Register the tools and their resources together, because a tool without its `ui://` resource gives the host something it can't display. Settings live in `webforj.conf` instead of `application.properties`. The full initializer is on the [standard setup page](/docs/integrations/mcp-apps/without-spring).

## How do you test an MCP app? {#testing}

Start with the minimal no-input `inventory` view. If a host discovers the tool and renders the heading, the connection works.

Claude Desktop and the Codex app connect from outside your machine, so they need a public HTTPS URL. A Cloudflare quick tunnel gives you one:

```bash
cloudflared tunnel --url http://localhost:8080
```

Set the printed origin as `webforj.origin`, start the app, and add `https://<your-tunnel>/mcp` as a custom connector in Claude Desktop or as an MCP server under Plugins in the Codex app. Then ask the host to open the inventory app.

For a [fully local loop](/blog/test-mcp-app-java), run MCPJam with `npx @mcpjam/inspector@latest`, add `http://localhost:8080/mcp` over HTTP, and allow the inspector's origin with `webforj.mcp.allowed-origins`. Set its Content Security Policy mode to Permissive before you run the tool. The [testing guide](/docs/integrations/mcp-apps/testing) lists the steps and a troubleshooting table.

Host support varies, because hosts adopt revisions of the MCP Apps extension at their own pace. The opening tool always returns text, and the route stays available in a browser. Check each host you target.

## Why build MCP apps with webforJ? {#why-webforj}

An MCP app is a UI the AI can drive, so it benefits from being real app code. In webforJ the view is a Java class with the components, routing, validation and Spring services you already use. Your security rules, data access and business logic don't get rewritten for a second front end.

There is no JavaScript bundle to write for the embedded page and no REST layer to expose to it. The tool schema comes from a Java record, and the annotations sit on the methods that do the work. You can also start from an existing webforJ app and publish one of its views as an MCP app.

If you want an AI assistant that helps you write this code, the [webforJ AI plugin](/blog/ai-coding-assistant-java) connects coding assistants to webforJ's documentation and APIs. For the broader picture of AI in a Java web app, see [add AI to a Java app](/blog/add-ai-to-java-app) and the earlier post on the [webforJ MCP server](/blog/mcp).

## Next step {#next-step}

Create a project with [startforJ](https://docs.webforj.com/startforj), add `webforj-mcp-apps`, and publish one view with `@McpApp`. Then follow the [MCP Apps overview](/docs/integrations/mcp-apps/overview) and the [Spring Boot setup](/docs/integrations/mcp-apps/spring) to connect a host.

## Frequently asked questions {#faq}

### What's an MCP app? {#faq-what-is}

An MCP app is an interactive interface that an MCP server returns for a tool call. The host renders it in a sandboxed iframe inside the conversation. With webforJ, that interface is a routed Java view marked with `@McpApp`.

### Which hosts can open the app? {#faq-hosts}

Claude Desktop, the Codex app and MCPJam all open webforJ MCP apps. Other hosts that support the MCP Apps extension can render the view too, and the [MCP Apps page](https://modelcontextprotocol.io/extensions/apps/overview) lists current clients. Each host adopts the extension on its own schedule, so test the ones you target.

### Do I need Spring Boot? {#faq-spring}

No. Spring Boot with Spring AI is the primary setup and discovers `@McpApp` routes for you. A standard servlet app can register the MCP server through a `ServletContainerInitializer` instead.

### Does it work in a browser too? {#faq-browser}

Yes. The route stays available at its own URL. Use `McpHost.ifPresent` so host-only behavior runs only when an MCP client is connected.
