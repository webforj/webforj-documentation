---
title: Test an MCP App
sidebar_position: 10
description: Test a webforJ MCP App with an MCP Apps-capable host, including the Codex app, Claude Desktop, and MCPJam.
---

webforJ MCP Apps can run in any MCP Apps-capable host. The instructions here cover the Codex app and Claude Desktop through a reachable HTTPS endpoint, and MCPJam through localhost. The minimal no-input `inventory` tool from the [Spring Boot setup](./spring) is enough to confirm that a host can discover the tool and render the Inventory view.

## Remote clients

The Codex app and Claude Desktop connect from outside the development machine. They can't reach `http://localhost:8080/mcp`, so the running app needs a public HTTPS MCP URL.

### Expose a local app

Use a [Cloudflare Tunnel](https://developers.cloudflare.com/tunnel/setup/) to reserve and print a public HTTPS origin that forwards to the app on the default local port, `8080`. You can start the tunnel before the app:

```bash
cloudflared tunnel --url http://localhost:8080
```

The command prints an HTTPS origin, such as `https://example.trycloudflare.com`. Set that printed origin in `src/main/resources/application.properties`:

```Ini
webforj.origin=https://example.trycloudflare.com
```

Start the app through its normal workflow. The origin has no `/mcp`; the client URL adds `/mcp`:

```text
https://example.trycloudflare.com/mcp
```

:::warning[Development tunnel]

A development tunnel makes the app publicly reachable. Use test data, expect a new hostname each time the quick tunnel is started, and use a stable managed tunnel when the hostname must stay the same.
:::

### Codex app

<!-- Video: Connect and test the inventory MCP App in the Codex app. -->

OpenAI's [Plugins guide](https://developers.openai.com/codex/plugins) covers the current plugin controls.

1. In **Settings**, open **Plugins** and select **Add MCP server**.
2. Enter the public MCP URL:

```text
https://example.trycloudflare.com/mcp
```

3. Add the server, then start a new Codex conversation.
4. Prompt the Codex app:

```text
Open the inventory app.
```

5. Confirm that the rendered Inventory view appears.

<!-- vale Google.Headings = NO -->
### Claude Desktop

<!-- Video: Connect and test the inventory MCP App in Claude Desktop. -->
<!-- vale Google.Headings = YES -->

Claude Desktop's remote custom connector is brokered through Anthropic infrastructure, so it also needs the public HTTPS MCP URL. Anthropic's [connectors guide](https://support.claude.com/en/articles/11176164-use-connectors-to-extend-claude-s-capabilities) covers the current connector controls.

1. Open **Settings**, select **Connectors**, and click the add button.
2. Select **Add custom connector**, enter a name, and use the public MCP URL:

```text
https://example.trycloudflare.com/mcp
```

3. Add the connector.
4. In a conversation, Prompt Claude Desktop:

```text
Open the inventory app.
```

6. Confirm that the rendered Inventory view appears.

If the server requires OAuth 2.0, complete the sign-in flow before invoking the tool.

:::tip[Name the MCP server in the prompt]

If Codex or Claude doesn't choose the expected action, include the MCP server name in the prompt. This can happen when several tools could apply or the prompt is too vague. For example: `Using the inventory MCP server, open the inventory app.`
:::

## MCPJam

[MCPJam](https://github.com/MCPJam/inspector) can connect directly to an MCP server running on the same machine. Use the local inspector for a plain HTTP endpoint; the hosted MCPJam app accepts HTTPS endpoints only.

1. Start the local inspector and open the localhost URL it prints:

```bash
npx @mcpjam/inspector@latest
```

2. Before starting the webforJ app, configure its local origin and allow the MCPJam browser origin. Replace the representative MCPJam origin below if the inspector printed a different one:

```Ini
webforj.origin=http://localhost:8080
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

`webforj.origin` sets the location from which the rendered MCP App loads its webforJ resources. `webforj.mcp.allowed-origins` allows the MCPJam page to embed and communicate with the app.

3. Start the webforJ app through its normal workflow.

4. In MCPJam, open **Connect** and select **Add server**. Enter a name, select **HTTP** as the transport, and use the local MCP endpoint:

```text
http://localhost:8080/mcp
```

5. Select **No Authentication**, then connect the server. A successful connection makes the server's tools available to MCPJam.
6. Open **Playground**, then open **Tools** in the left rail.
7. Select `inventory` and click **Run**. The tool takes no input, and its Inventory view renders in the conversation.

:::warning[MCPJam content security policy mode]

Set **Content Security Policy (CSP) Mode** in the Playground toolbar to **Permissive** before running the tool. Strict mode blocks the dynamic JavaScript evaluation used during current webforJ startup. Use Permissive mode only with MCP servers and app code you trust.
:::

## Verify the app

Use this baseline for each client:

- The client connects to the MCP endpoint.
- The `inventory` tool is visible.
- Invoking `inventory` renders the **Inventory** heading.
- The rendered UI is interactive.

After the baseline works, add [opening input](./opening-apps), [actions and updates](./actions-updates), and [host interaction](./host-interaction) when the MCP App needs those features.

## Troubleshooting

| Problem | Check |
| --- | --- |
| Client can't connect | Confirm the app is running, the tunnel is running for remote clients, and the full client URL ends with `/mcp`. |
| Tool visible but resource or open fails | Confirm `webforj.origin` matches the current app origin and that the app is running. |
| MCPJam is blank or loading with a content security policy `eval` error | Turn off **Strict**. |
| Metadata is stale | Reconnect the client or start a new conversation. |
