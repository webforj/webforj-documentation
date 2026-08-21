---
title: MCP Apps
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: Expose routed webforJ views as interactive MCP applications that an MCP host can open and use inside its own interface.
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# MCP Apps <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />
<!-- vale Google.Headings = YES -->

MCP Apps let an [MCP](https://modelcontextprotocol.io/)-capable AI app, also called a host, open a routed webforJ view inside its conversation. The view remains part of the Java app, so it uses the same components, services, routing, and state as it does in a browser.

The person and the AI can work with the same live UI. The AI can supply input when it opens the view, call actions that change the open view, and receive context from choices the person makes in the UI. The person can continue using the rendered webforJ components directly.

Spring Boot with Spring AI is the primary way to publish an MCP App. The integration discovers marked routes and adds them to Spring AI's MCP server. Start with the [Spring Boot setup](./spring), then [test the connection](./testing) with the minimal published view. Applications that don't use Spring Boot can use the [standard servlet setup](./without-spring) instead.

<div class="videos-container">
    <video controls>
      <source src="https://cdn.webforj.com/webforj-documentation/video/mcp-apps/webforj-mcp-app.mp4" type="video/mp4" />
    </video>
</div>

:::info[Host support varies]

MCP Apps is an evolving extension of the MCP specification, so hosts adopt its revisions and security policies at their own pace. The app declares the origins its view loads from and connects to, and a host that permits them renders the view. Hosts can also apply stricter policies. The opening tool always returns its text content, and the route remains available as a regular browser page. Verify each host you target with the steps in [Testing](./testing).
:::

## Topics {#topics}

<DocCardList className="topics-section" />
