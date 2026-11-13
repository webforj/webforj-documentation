---
title: "Spring Boot MCP server: tools plus a live UI in webforJ"
description: "Build a Spring Boot MCP server that exposes Java methods as tools and opens a live webforJ view in the AI client, all from one app and one set of services."
slug: spring-boot-mcp-server-ui
date: 2026-10-30
authors: Hyyan Abo Fakher
tags: [ai, mcp, spring-ai, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-30-spring-boot-mcp-server-ui/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-30-spring-boot-mcp-server-ui/cover.jpg)

Ask an AI assistant how much a customer owes and a one-line answer is perfect. Ask it for every unpaid invoice due this month, so you can chase the overdue ones, and a paragraph of text stops being useful. You want the invoices in front of you.

A Spring Boot MCP server can give you both. Spring AI runs the MCP server and talks to the AI client, and webforJ adds what Spring AI doesn't: a live view that the client opens inside the conversation, with tools the model can call to change it.

<!-- truncate -->

The code in this post comes from a working invoice workspace built with Spring Boot, Spring AI and webforJ 26.02. This is the workspace running inside an AI client:

<VideoPlayer video="mcp-apps/webforj-mcp-app" width={1920} height={1080} title="WebforJ MCP app" muted />

## The problem with tools alone {#problem-with-tools-alone}

The [Model Context Protocol](https://modelcontextprotocol.io/) lets an AI client call tools on a server you run. Spring AI ships a Spring Boot MCP server starter, so publishing a tool takes very little code. That part is solved and pleasant.

The trouble starts with what a tool returns. A tool result is text or structured data. The model reads it, summarizes it, and the person gets a paragraph. For "how much does Acme owe" that's exactly right. For "show me everything due this month, let me pick the overdue ones and send reminders" a paragraph is the wrong output. People want a table they can work with.

The usual answer is a second project: a React or Angular front end, a REST API so it can read the data, and a separate sign-in story for it. You then wire the AI client to the tool and hope the two stay in sync.

I'd skip that. The services already exist in the Spring app and the UI can live next to them, in Java.

## Set up the Spring Boot MCP server {#set-up-the-server}

The app imports the webforJ bill of materials and adds three dependencies: the webforJ Spring Boot starter, the MCP Apps module, and Spring AI's WebMVC MCP server starter. Without Spring, you can build an [MCP app in plain Java](/blog/mcp-app-without-spring) with one servlet initializer.

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
  <version>${spring-ai.version}</version>
</dependency>
```

The project sets `webforj.version` to 26.02, the release that brought MCP Apps, and `spring-ai.version` to 2.0.0. In `application.properties`, Spring AI owns the MCP server:

```properties
# MCP server, owned by Spring AI
spring.ai.mcp.server.protocol=STREAMABLE
spring.ai.mcp.server.name=mcp-spring
```

Spring AI serves the transport at `/mcp` by default. webforJ leaves that path, and the OAuth 2.0 discovery paths, to Spring instead of treating them as UI routes. When a remote client has to reach the app through a tunnel, set `webforj.origin` to the tunnel's address, without `/mcp`. The [configuration page](/docs/integrations/mcp-apps/configuration) covers proxies, tunnels, and the allowed client origins.

## Turn a view into an MCP App {#mcp-app-view}

The invoice workspace is a normal routed webforJ view. One annotation, `@McpApp`, publishes it to the MCP server. The description tells the model what the app does and when to open it, and the `name` keeps the tool name stable if the route changes. The view receives `InvoiceService` through constructor injection, the same way any [webforJ route works with Spring](/docs/integrations/spring/routing).

```java
@Route(value = "section/invoices", outlet = MainLayout.class)
@FrameTitle("Invoices")
@McpApp(
  name = "invoices",
  description = "Opens the webforJ invoice workspace. The host can filter invoices by search" +
    " text, status, or due window; visualize the current result as a chart; or prepare payment" +
    " reminders for user review. Reminder messages are never sent without user confirmation."
)
public class InvoicesView
  extends Composite<SectionCard> {

  private final SectionCard self = getBoundComponent();
  private final InvoiceService service;
  private final InvoiceToolbar toolbar = new InvoiceToolbar();
  private final InvoiceTable table = new InvoiceTable();
  private final InvoiceSummary summary = new InvoiceSummary();
```

The view still works at `/section/invoices` in a browser, inside the app's main layout. Spring auto-configuration publishes an `invoices` MCP tool and a UI resource that opens this route, and it installs the servlet filters and session settings needed to embed webforJ. There's no controller, no DTO layer, and no front-end build.

The description does real work. It's what the model reads when it decides whether to open the app, and here it also states a rule the model should respect: reminders are never sent without the user's confirmation.

## Let the AI choose what the view shows {#opening-input}

A workspace that always opens on every invoice isn't as useful as one that opens on the right ones. The model should be able to ask for unpaid invoices due within 30 days. The opening input is a record, and its Jackson annotations become the schema the client uses to build the call:

```java
public record InvoiceOpeningInput(
    @JsonPropertyDescription("Search invoice number, customer name, or billing email")
    String search,
    @JsonPropertyDescription("Filter the invoice status") StatusFilter status,
    @JsonPropertyDescription("Show unpaid invoices due within this many days")
    Integer dueWithinDays) {
}
```

All three fields are optional, so the model can send any combination. The view takes the input in one `@McpAppInput` method, which runs after the route renders:

```java
@McpAppInput
void receiveOpeningInput(InvoiceOpeningInput input) {
  applyFilter(input.search(), input.status(), input.dueWithinDays());
}
```

`applyFilter` is the same method the view's own toolbar uses, so the workspace opens filtered exactly as if the user had typed the search. See [opening input](/docs/integrations/mcp-apps/opening-apps) for the other ways to declare the schema.

## Give the model tools for the open view {#actions-for-open-view}

Once the workspace is open, the user keeps talking: show only the overdue ones, chart them, remind those customers. The model shouldn't reopen the view for each request. It should operate on the one that's already there. Each `@McpAppAction` method publishes a tool that runs against the open view:

```java
@McpAppAction(description = "Filters the open invoice workspace by text, status, or due date.")
Map<String, Object> filter(InvoiceFilterInput input) {
  return applyFilter(input.search(), input.status(), input.dueWithinDays());
}

@McpAppAction(description = "Opens a chart for the invoices visible in the open workspace.")
Map<String, Object> chart(InvoiceChartInput input) {
  return showChart(input.view());
}

@McpAppAction(description = "Prepares reminders for open invoices overdue beyond a chosen day"
    + " count. The user reviews and sends them.")
Map<String, Object> prepareReminders(InvoiceReminderInput input) {
  return prepareReminders(input.overdueDays());
}
```

For an app named `invoices`, these become the tools `invoices_filter`, `invoices_chart` and `invoices_prepare_reminders`. Each call is routed to the rendered view in the same MCP session. Every action needs a nonblank description, and the returned map becomes structured content the model can read. In this app every action returns the same summary of what's on screen:

```java
private Map<String, Object> createResultContext() {
  List<Invoice> visible = table.getVisibleInvoices();
  double outstanding = visible
    .stream()
    .filter(invoice -> invoice.status() != Invoice.Status.PAID)
    .mapToDouble(Invoice::amount)
    .sum();

  Map<String, Object> context = new LinkedHashMap<>();
  context.put("count", visible.size());
  context.put("outstanding", outstanding);
  context.put("invoices", describeInvoices(visible));
  return context;
}
```

So after the user asks for only the overdue ones, the model doesn't have to guess what the user sees. It gets the count, the outstanding total and the invoice list back from the call. Notice also what `prepareReminders` does not do: it opens the reminder dialog for review, and the user presses Send. The model prepares, the person decides. A separate post goes deeper on how an AI can [open a view and then update it with actions](/blog/mcp-tool-update-ui).

The [actions and updates page](/docs/integrations/mcp-apps/actions-updates) covers actions in detail, along with the single-update alternative.

## Talk back to the AI client {#talk-to-the-host}

The conversation can also flow the other way. `McpHost` gives the view access to the client it runs in. When the user selects invoices and presses Share, the view sends them to the conversation as a message:

```java
PendingResult<Void> result = host.sendMessage(prompt);
result.thenAccept(ignored ->
  Toast.show("Selected invoices shared for analysis")
);
```

And after the user sends reminders, the view quietly updates what the model knows, so the next answer reflects what just happened:

```java
McpHost.ifPresent(host ->
  host.updateModelContext(
    Map.of(
      "action",
      "payment-reminders-sent",
      "count",
      reminded.size(),
      "invoices",
      describeInvoices(reminded)
    )
  )
);
```

`McpHost.ifPresent` runs only when the view is open inside an AI client, so the same code is safe when someone opens the route in a normal browser tab. The [host interaction page](/docs/integrations/mcp-apps/host-interaction) lists the rest of what the view can ask of the client.

## Control who can embed the app {#embedding-clients}

An MCP server that renders a view in someone else's page needs explicit boundaries. webforJ already allows the known Codex app and Claude Desktop sandbox origin patterns. For any other client, such as a local debugging tool, add its browser origin and nothing more:

```properties
webforj.mcp.allowed-origins=http://localhost:8177
```

The embedded frame also starts with a restrictive content security policy. If the view must load a script or image from a CDN, list that domain in `webforj.mcp.resource-domains`. If browser code in the frame calls an external API, use `webforj.mcp.connect-domains`. I keep these lists as short as the deployment allows, because every entry is a hole you chose to open.

## Plain tools or an MCP App? {#tools-or-app}

My rule of thumb is simple.

| The answer is | Use |
| --- | --- |
| One value or a short sentence | A plain Spring AI tool |
| Something the person will scan, filter, or act on | An `@McpApp` view |
| A follow-up operation on an open view | An `@McpAppAction` |

They coexist on one server, and the model sees them in one tool list. Everything calls the same Spring services, so business rules live in one place and a bug fix lands everywhere. A second front-end project can't offer that, because its copy of the rules drifts from the service the moment someone edits one side.

There is a trade-off. Host support varies, because MCP Apps is an evolving extension of the MCP specification and each host adopts revisions and security policies at its own pace. The opening tool always returns its text content, and the route stays available as a regular browser page, so nothing breaks when a host can't render the view. Check each client you target with the steps in [testing](/docs/integrations/mcp-apps/testing).

## Try it from an AI client {#try-it}

A remote client such as Claude Desktop can't reach `localhost`, so start a tunnel, set its HTTPS origin as `webforj.origin`, and register `https://<your-tunnel>/mcp` as the connector URL. Then ask the client to open the invoices app. The [testing page](/docs/integrations/mcp-apps/testing) walks through each client, including MCPJam for localhost. A tunnel makes the app publicly reachable, so use test data.

To check the view before any AI client is involved, follow [How to test a Java MCP App without an AI host](/blog/test-mcp-app-java). For a walkthrough that focuses on the rendered view and host interaction, read [MCP Apps in Java](/blog/mcp-apps-java).

## Next step {#next-step}

Take an existing Spring Boot app, pick the one screen people would want to see from an AI chat, and add `@McpApp` to it. Start from the [Spring Boot MCP Apps guide](/docs/integrations/mcp-apps/spring), or generate a project at [startforJ](https://docs.webforj.com/startforj) first.

## Frequently asked questions {#faq}

### How do I build an MCP server in Java with Spring Boot? {#faq-build-mcp-server}

Add `spring-ai-starter-mcp-server-webmvc` and set `spring.ai.mcp.server.protocol=STREAMABLE`. Spring AI serves the server at `/mcp` by default. Add `webforj-mcp-apps` and annotate a routed view with `@McpApp` when you also want to publish a UI.

### Can the same server expose tools and a UI? {#faq-tools-and-ui}

Yes. Plain Spring AI tools, `@McpApp` views and their `@McpAppAction` tools all register on the same Spring AI MCP server, so the client sees them in one tool list. They can all inject the same Spring services.
