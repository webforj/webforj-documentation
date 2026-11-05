---
title: "MCP app in plain Java: no Spring Boot required"
description: "Build an MCP app in plain Java without Spring: one servlet initializer lets webforJ serve routed views as interactive UIs inside an AI chat."
slug: mcp-app-without-spring
date: 2026-10-05
authors: Hyyan Abo Fakher
tags: [ai, mcp, integrations, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-05-mcp-app-without-spring/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-05-mcp-app-without-spring/cover.jpg)

Not every Java web app runs on Spring Boot, and adding a framework just to put one screen inside an AI chat would be the biggest change in the project. The good news is that you don't have to.

You can build an MCP app in plain Java without Spring Boot. With webforJ, one servlet initializer assembles the MCP server, a routed view becomes the UI the AI opens, and the person clicks through the same live components they'd see in a browser. The code in this post comes from a working plain servlet app built on webforJ 26.02.

<!-- truncate -->

Most MCP tutorials assume Spring Boot, so a lot of teams with a plain servlet app conclude they have to migrate first. They don't. This post walks through the standard servlet setup end to end, explains what each piece of the initializer does, and names the places where I'd expect it to go wrong.

## Do I need Spring to build an MCP app? {#do-i-need-spring}

No. webforJ ships two ways to publish an MCP app. With Spring Boot, the integration discovers marked routes and adds them to the Spring AI MCP server for you. Without Spring, you assemble the server yourself with the MCP Java SDK and let webforJ contribute the tools and UI resources.

I'd pick the Spring path when Spring Boot is already in the app, because it removes the assembly code. I'd pick the servlet path when the app is a plain webforJ deployment in a servlet container and adding a framework only for MCP would be the largest change in the project.

One limit to know up front: MCP apps need control of the servlet context to install the cross-origin filter and cookie settings, plus OAuth 2.0 discovery handling. Deploy to a servlet container your app controls.

## What does a plain Java MCP app contain? {#what-it-contains}

Four parts, and only the first one is yours to design:

1. A routed view marked with `@McpApp`. This is the UI the AI opens.
2. A servlet initializer that builds the MCP server and mounts Streamable HTTP at `/mcp`.
3. A service loader file that names the initializer for the servlet container.
4. A few settings in `webforj.conf` for origins.

The view is an ordinary webforJ route. It still works at its URL in a browser. The MCP part is metadata on top of it, which is why I like this model: you aren't building a second UI for the assistant.

## How do I add the dependencies? {#add-the-dependencies}

The app imports the webforJ bill of materials and adds the MCP Apps module next to webforJ. The initializer implements `ServletContainerInitializer` and uses Jakarta Servlet types, so the Servlet API comes in with `provided` scope. The container supplies it at runtime, so you don't package a second implementation.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj</artifactId>
  <version>${webforj.version}</version>
</dependency>

<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-mcp-apps</artifactId>
  <version>${webforj.version}</version>
</dependency>

<!-- The MCP server initializer speaks the servlet API. Jetty provides it at runtime. -->
<dependency>
  <groupId>jakarta.servlet</groupId>
  <artifactId>jakarta.servlet-api</artifactId>
  <version>6.1.0</version>
  <scope>provided</scope>
</dependency>
```

`webforj-mcp-apps` brings the webforJ contribution and the MCP SDK used to assemble the server. MCP apps arrived in webforJ 26.02, so use that version or newer.

## How do I mark a view as an MCP app? {#mark-a-view}

Start with the view, because it is the part your users will judge. Give it a route and add `@McpApp`. The description matters more than it looks: the model reads it to decide when the tool is worth calling.

```java
@Route("/inventory")
@McpApp(description = "Shows the current warehouse inventory with the stock level of every"
    + " product.")
public class InventoryView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();

  public InventoryView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(440);
    self.setStyle("margin", "1em auto");

    self.add(new Paragraph("SKU-100 Standing Desk, 42 in stock"),
        new Paragraph("SKU-200 Office Chair, 7 in stock"),
        new Paragraph("SKU-300 Monitor Arm, out of stock"));
  }
}
```

This view sets no `name`, so webforJ derives the tool name from the route, and `/inventory` becomes `inventory`. Set `name` when other integrations need a tool name that won't change if you rename the route.

The view opens the same way every time. When you want the AI to choose what it shows, for example one warehouse, add an opening input. The [opening input docs](/docs/integrations/mcp-apps/opening-apps) show how a record and one `@McpAppInput` method do that.

## How do I assemble the MCP server? {#assemble-the-server}

This is the step Spring would otherwise do for you. The app does it in one class, `McpServerInitializer`, which the servlet container calls at startup. It reads in four short steps.

**1. Collect the webforJ views.** `McpAppContribution.ofPackages` scans the package you pass and produces two collections: the tools generated from your `@McpApp` views, and the `ui://webforj/...` resources that clients read to render them.

```java
McpAppOptions options = McpAppOptions.load();
McpAppContribution contribution = McpAppContribution.ofPackages(
    new String[] {"com.mcpplain.views"}, McpAppServletPath.of(context));
contribution.getOrigin().configure(options.getOrigin());
```

The package string `com.mcpplain.views` is the only thing that ties the initializer to your views. If you move the views, update it.

**2. Create the transport.** This is the Streamable HTTP endpoint at `/mcp`. The context extractor lets webforJ observe the public origin of incoming requests when `webforj.origin` isn't set.

```java
HttpServletStreamableServerTransportProvider transport =
    HttpServletStreamableServerTransportProvider.builder().mcpEndpoint("/mcp")
        .contextExtractor(request -> {
          contribution.getOrigin().observe(request);
          return McpTransportContext.EMPTY;
        }).build();
```

**3. Build the server.** The server is the app's own, so the webforJ tools go into a list together with a plain MCP tool of the app, `add`. One server then offers both: tools that return text, and views that render. Both collections from step 1 go on the server. Registering tools without their UI resources exposes calls the client can't display, so don't drop the `.resources(...)` part.

```java
List<SyncToolSpecification> tools = new ArrayList<>(contribution.getToolSpecifications());
tools.add(addTool());

McpSyncServer server = McpServer.sync(transport).serverInfo("mcp-plain", "1.0.0")
    .capabilities(ServerCapabilities.builder().tools(true).resources(false, true).build())
    .tools(tools).resources(contribution.getResourceSpecifications()).build();
```

The `add` tool itself is plain MCP Java SDK code with no webforJ in it:

```java
Tool tool = Tool.builder("add",
    Map.of("type", "object",
        "properties", Map.of(
            "a", Map.of("type", "integer", "description", "First number"),
            "b", Map.of("type", "integer", "description", "Second number")),
        "required", List.of("a", "b")))
    .description("Add two numbers together").build();
```

**4. Mount it and install the embed support.** The servlet is mapped to `/mcp/*` with async support, a listener closes the server on shutdown, and the final `install` call does the rest.

```java
ServletRegistration.Dynamic registration = context.addServlet("mcpServlet", transport);
// The streamable transport answers the stream with request.startAsync.
registration.setAsyncSupported(true);
registration.addMapping("/mcp/*");

context.addListener(new ServletContextListener() {
  @Override
  public void contextDestroyed(ServletContextEvent event) {
    server.close();
  }
});

contribution.install(context, options);
```

`install` is the call I'd check first when something breaks. It adds the app-resource policy and cross-origin handling, then session cookie settings, OAuth 2.0 discovery handling and favicon support. A server that publishes tools but skips `install` can expose a resource the client can't run correctly.

## How is the initializer found? {#register-the-initializer}

Through Java's service loader. Create `src/main/resources/META-INF/services/jakarta.servlet.ServletContainerInitializer` with the fully qualified class name:

```text
com.mcpplain.mcp.McpServerInitializer
```

The container loads it during app startup. Keep the class name in this file in sync with the initializer package, and check it first after a package rename.

## What settings does a deployment need? {#configure-the-deployment}

Standard deployments read MCP app settings from `webforj.conf`. Two of them matter most.

`webforj.origin` is the public origin the client can reach. It is used in the app resource, the content security policy and the webforJ component URLs. Don't put `/mcp` in it, because the path belongs to the endpoint and not the origin. When you test through a tunnel, set it to the tunnel's public HTTPS address.

`webforj.mcp.allowed-origins` controls which browser origins may embed the view and make cross-origin requests. webforJ already allows the known Codex app and Claude Desktop sandbox origin patterns, so you only add this for another client. If the embedded UI must load assets or call APIs on other domains, add `webforj.mcp.resource-domains` and `webforj.mcp.connect-domains`. The embedded frame starts with a restrictive content security policy, so add only what the UI needs.

## Can the view talk back to the AI? {#keep-working}

Yes, and this is where an MCP app stops being a fancy screenshot. The app's second view shows a table of orders with a button that sends a selection into the conversation for analysis. `McpHost` is the view's connection to the AI app it runs in:

```java
analyze.setTheme(ButtonTheme.PRIMARY)
    .addClickListener(e -> {
      if (!McpHost.isPresent()) {
        Toast.show("No MCP host present", Theme.GRAY);
        return;
      }

      McpHost host = McpHost.getCurrent();
      PendingResult<Void> delivery = host.updateModelContext(SELECTION);
      host.sendMessage(SELECTION);
      delivery.thenAccept(result -> Toast.show("The agent has the selection", Theme.SUCCESS));
      delivery.exceptionally(error -> {
        Toast.show("Not delivered: " + error.getMessage(), Theme.DANGER);
        return null;
      });
    });
```

`updateModelContext` tells the model about the selection, and `sendMessage` posts it into the chat. The `isPresent` check keeps the button safe when someone opens the same route in a normal browser tab. The AI can also call back into an open view through `@McpAppAction` methods, which the [actions and updates guide](/docs/integrations/mcp-apps/actions-updates) covers.

## How do I test it? {#test-it}

Run the app the way you normally do and point an MCP Apps-capable host at the endpoint. Local MCPJam can reach `http://localhost:8080/mcp` directly. The Codex app and Claude Desktop connect from outside your machine, so they need a public HTTPS URL. A Cloudflare Tunnel gives you one, and the [testing page](/docs/integrations/mcp-apps/testing) has the command.

Put the tunnel's origin in `webforj.origin`, then register `https://<your-tunnel>/mcp` as the server in the host. Ask it to open the inventory app. A tunnel makes the app publicly reachable, so use test data and expect a new hostname each time you restart a quick tunnel.

Host support varies, because MCP Apps is an evolving extension of the MCP specification and hosts adopt revisions at their own pace. The opening tool always returns text content, and the route still works as a normal browser page, so a host that declines to render your view doesn't leave the user with nothing.

## Can I embed a Java UI in AI chat? {#embed-without-a-rewrite}

That is the point of doing it this way. The view you embed is the same class that serves the browser. It keeps its components and state. There is no second frontend for the assistant and no REST layer for a separate UI to call. Existing servlet apps get the integration with one initializer and one service file, plus a few lines of configuration.

## Next step {#next-step}

Open the [standard setup page](/docs/integrations/mcp-apps/without-spring), copy the initializer, and point it at one existing view with a short `@McpApp` description. Test it with the steps in [Test an MCP App](/docs/integrations/mcp-apps/testing). When you need to tune origins, see [MCP App configuration](/docs/integrations/mcp-apps/configuration), and for opening input see [Open a view with input](/docs/integrations/mcp-apps/opening-apps). The [MCP Apps overview](/docs/integrations/mcp-apps/overview) explains the model.

## Frequently asked questions {#faq}

### Can I run an MCP server in Java without Spring Boot? {#faq-mcp-server-no-spring}

Yes. Register a `ServletContainerInitializer` that builds an MCP server with the MCP Java SDK, mounts Streamable HTTP at `/mcp`, and takes its tools and UI resources from `McpAppContribution`. The app needs no Spring dependency.

### Does the MCP UI work without Spring Boot? {#faq-mcp-ui-no-spring}

Yes. The UI is a routed webforJ view marked with `@McpApp`, and it doesn't depend on Spring. The view gets the same lifecycle in a servlet deployment as it does in a Spring Boot one.

### How do I show a Java UI inside an AI chat? {#faq-embed-java-ui}

Mark a routed view with `@McpApp` and publish it through the MCP server. When the assistant calls the generated tool, an MCP Apps-capable host reads the matching UI resource and renders your view inside the conversation.
