---
title: Configure an MCP App
sidebar_position: 30
description: Configure the public app origin, allowed embedding clients, and external browser domains.
---

Add MCP App settings to `application.properties`, or to `webforj.conf` when using the standard webforJ setup. Set the address where the client can reach the app, then add only the client and browser origins the deployment requires.

## Set the app origin {#app-origin}

`webforj.origin` is the public origin used in the app resource, content security policy, and webforJ component URLs. During local testing, it's the address of the app:

```Ini
webforj.origin=http://localhost:8080
```

When a tunnel or reverse proxy exposes the app, use the public origin that the MCP client can reach:

```Ini
webforj.origin=https://example.trycloudflare.com
```

Don't include `/mcp` in this property. The path belongs to the MCP endpoint, not the app origin.

## Allow the embedding client {#allowed-origins}

`webforj.mcp.allowed-origins` controls which browser origins can make cross-origin requests and embed the view. For a local [MCPJam](./testing#mcpjam) browser running at the representative origin `http://127.0.0.1:6274`, use:

```Ini
webforj.mcp.allowed-origins=http://127.0.0.1:6274
```

Use the origin shown in the client's browser address bar because local tools can choose a different port. The tunnel address isn't an allowed client origin; it belongs in `webforj.origin`.

webforJ already allows the known Codex app and Claude Desktop sandbox origin patterns. Add this property only for another client origin. A wildcard such as `https://*.example.com` matches host labels, not arbitrary URL text.

## Allow external resources and connections {#browser-domains}

The embedded frame starts with a restrictive content security policy. Add `resource-domains` when the UI must load a script, style, image, font, or other browser resource from another origin:

```Ini
webforj.mcp.resource-domains=https://cdn.example.com
```

Add `connect-domains` when browser code in the frame must connect to an external API, WebSocket, or similar endpoint:

```Ini
webforj.mcp.connect-domains=https://api.example.com
```

These properties extend what the embedded frame can load or contact. They don't allow another client to embed the app; use `allowed-origins` for that.

## Configure a standard deployment {#standard-deployment}

Spring Boot reads these values from `application.properties`. A standard servlet deployment uses `webforj.conf` with the equivalent values:

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
webforj.mcp.resource-domains = ["https://cdn.example.com"]
webforj.mcp.connect-domains = ["https://api.example.com"]
```

Only add domains the app needs. [Client testing](./testing) shows where to find the local client origin and when a public app origin is required.
